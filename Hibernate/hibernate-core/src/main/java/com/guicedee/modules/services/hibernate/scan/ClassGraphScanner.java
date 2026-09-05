package com.guicedee.modules.services.hibernate.scan;

import com.guicedee.client.IGuiceContext;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.Resource;
import org.hibernate.boot.archive.scan.spi.ClassDescriptor;
import org.hibernate.boot.archive.scan.spi.MappingFileDescriptor;
import org.hibernate.boot.archive.scan.spi.PackageDescriptor;
import org.hibernate.boot.archive.scan.spi.ScanEnvironment;
import org.hibernate.boot.archive.scan.spi.ScanOptions;
import org.hibernate.boot.archive.scan.spi.ScanParameters;
import org.hibernate.boot.archive.scan.spi.ScanResult;
import org.hibernate.boot.archive.scan.spi.Scanner;
import org.hibernate.boot.archive.spi.ArchiveException;
import org.hibernate.boot.archive.spi.InputStreamAccess;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Hibernate scanner that classifies resources from the GuicedEE ClassGraph scan.
 */
public final class ClassGraphScanner implements Scanner
{
    private static final String ENTITY = "jakarta.persistence.Entity";
    private static final String EMBEDDABLE = "jakarta.persistence.Embeddable";
    private static final String MAPPED_SUPERCLASS = "jakarta.persistence.MappedSuperclass";
    private static final String CONVERTER = "jakarta.persistence.Converter";

    private final IGuiceContext guiceContext;

    public ClassGraphScanner()
    {
        this(IGuiceContext.instance());
    }

    public ClassGraphScanner(IGuiceContext guiceContext)
    {
        this.guiceContext = Objects.requireNonNull(guiceContext, "guiceContext");
    }

    @Override
    public ScanResult scan(ScanEnvironment environment, ScanOptions options, ScanParameters parameters)
    {
        Set<ClassDescriptor> classes = new LinkedHashSet<>();
        Set<PackageDescriptor> packages = new LinkedHashSet<>();
        Set<MappingFileDescriptor> mappings = new LinkedHashSet<>();

        var scanResult = guiceContext.getScanResult();
        locateAnnotatedClasses(scanResult, environment, options, classes);
        locatePackages(scanResult, environment, options, packages);
        locateMappingFiles(scanResult, environment, options, mappings);

        return new ScanResultValue(packages, classes, mappings);
    }

    private static void locateAnnotatedClasses(
            io.github.classgraph.ScanResult scanResult,
            ScanEnvironment environment,
            ScanOptions options,
            Set<ClassDescriptor> classes)
    {
        Map<String, ClassDescriptor.Categorization> categorizedClasses = new LinkedHashMap<>();
        addAnnotatedClasses(scanResult, ENTITY, ClassDescriptor.Categorization.MODEL, categorizedClasses);
        addAnnotatedClasses(scanResult, EMBEDDABLE, ClassDescriptor.Categorization.MODEL, categorizedClasses);
        addAnnotatedClasses(scanResult, MAPPED_SUPERCLASS, ClassDescriptor.Categorization.MODEL, categorizedClasses);
        addAnnotatedClasses(scanResult, CONVERTER, ClassDescriptor.Categorization.CONVERTER, categorizedClasses);

        for (Map.Entry<String, ClassDescriptor.Categorization> entry : categorizedClasses.entrySet())
        {
            ClassInfo classInfo = scanResult.getClassInfo(entry.getKey());
            Resource resource = classInfo == null ? null : classInfo.getResource();
            boolean root = classInfo != null && belongsTo(classInfo.getClasspathElementURI(), environment.getRootUrl());
            boolean nonRoot = classInfo != null && belongsTo(classInfo.getClasspathElementURI(), environment.getNonRootUrls());
            if (resource != null && (root || nonRoot) && isListedOrDetectable(entry.getKey(), root, environment, options))
            {
                classes.add(new ClassDescriptorValue(entry.getKey(), entry.getValue(), streamAccess(resource)));
            }
        }
    }

    private static void addAnnotatedClasses(
            io.github.classgraph.ScanResult scanResult,
            String annotationName,
            ClassDescriptor.Categorization categorization,
            Map<String, ClassDescriptor.Categorization> categorizedClasses)
    {
        for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(annotationName))
        {
            categorizedClasses.merge(classInfo.getName(), categorization, (existing, ignored) -> existing);
        }
    }

    private static void locatePackages(
            io.github.classgraph.ScanResult scanResult,
            ScanEnvironment environment,
            ScanOptions options,
            Set<PackageDescriptor> packages)
    {
        for (Resource resource : scanResult.getResourcesWithLeafName("package-info.class"))
        {
            boolean root = belongsTo(resource, environment.getRootUrl());
            boolean nonRoot = belongsTo(resource, environment.getNonRootUrls());
            String path = resource.getPathRelativeToClasspathElement();
            String packageName = path.substring(0, path.length() - "/package-info.class".length()).replace('/', '.');
            if ((root || nonRoot) && isListedOrDetectable(packageName, root, environment, options))
            {
                packages.add(new PackageDescriptorValue(packageName, streamAccess(resource)));
            }
        }
    }

    private static void locateMappingFiles(
            io.github.classgraph.ScanResult scanResult,
            ScanEnvironment environment,
            ScanOptions options,
            Set<MappingFileDescriptor> mappings)
    {
        if (options.canDetectHibernateMappingFiles())
        {
            addMappingResources(scanResult.getResourcesWithExtension("hbm.xml"), environment, options, mappings);
        }
        addMappingResources(scanResult.getResourcesWithPath("META-INF/orm.xml"), environment, options, mappings);
        for (String mappingFile : environment.getExplicitlyListedMappingFiles())
        {
            addMappingResources(scanResult.getResourcesWithPath(mappingFile), environment, options, mappings);
        }
    }

    private static void addMappingResources(
            Iterable<Resource> resources,
            ScanEnvironment environment,
            ScanOptions options,
            Set<MappingFileDescriptor> mappings)
    {
        for (Resource resource : resources)
        {
            boolean root = belongsTo(resource, environment.getRootUrl());
            boolean nonRoot = belongsTo(resource, environment.getNonRootUrls());
            String path = resource.getPathRelativeToClasspathElement();
            if ((root || nonRoot) && isMappingFile(path, root, environment, options))
            {
                mappings.add(new MappingFileDescriptorValue(path, streamAccess(resource)));
            }
        }
    }

    private static boolean isListedOrDetectable(
            String name,
            boolean root,
            ScanEnvironment environment,
            ScanOptions options)
    {
        return root
                ? options.canDetectUnlistedClassesInRoot() || environment.getExplicitlyListedClassNames().contains(name)
                : options.canDetectUnlistedClassesInNonRoot() || environment.getExplicitlyListedClassNames().contains(name);
    }

    private static boolean isMappingFile(String path, boolean root, ScanEnvironment environment, ScanOptions options)
    {
        if (path.endsWith("hbm.xml"))
        {
            return options.canDetectHibernateMappingFiles();
        }
        if (path.endsWith("META-INF/orm.xml"))
        {
            return !environment.getExplicitlyListedMappingFiles().contains("META-INF/orm.xml") || root;
        }
        return environment.getExplicitlyListedMappingFiles().contains(path);
    }

    private static boolean belongsTo(Resource resource, URL url)
    {
        return resource != null && belongsTo(resource.getClasspathElementURI(), url);
    }

    private static boolean belongsTo(URI resourceClasspathElementUri, URL url)
    {
        if (url == null || resourceClasspathElementUri == null)
        {
            return false;
        }
        try
        {
            return normalize(url.toURI()).equals(normalize(resourceClasspathElementUri));
        }
        catch (URISyntaxException e)
        {
            return false;
        }
    }

    private static boolean belongsTo(Resource resource, List<URL> urls)
    {
        if (urls == null)
        {
            return false;
        }
        for (URL url : urls)
        {
            if (belongsTo(resource, url))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean belongsTo(URI resourceClasspathElementUri, List<URL> urls)
    {
        if (urls == null)
        {
            return false;
        }
        for (URL url : urls)
        {
            if (belongsTo(resourceClasspathElementUri, url))
            {
                return true;
            }
        }
        return false;
    }

    private static URI normalize(URI uri)
    {
        String value = uri.normalize().toString();
        return URI.create(value.endsWith("/") ? value.substring(0, value.length() - 1) : value);
    }

    private static InputStreamAccess streamAccess(Resource resource)
    {
        return new InputStreamAccess()
        {
            @Override
            public String getStreamName()
            {
                return resource.getPath();
            }

            @Override
            public InputStream accessInputStream()
            {
                try
                {
                    return resource.open();
                }
                catch (IOException e)
                {
                    throw new ArchiveException("Unable to access ClassGraph resource [" + resource.getURI() + "]", e);
                }
            }
        };
    }

    private record ScanResultValue(
            Set<PackageDescriptor> packages,
            Set<ClassDescriptor> classes,
            Set<MappingFileDescriptor> mappings) implements ScanResult
    {
        @Override
        public Set<PackageDescriptor> getLocatedPackages()
        {
            return packages;
        }

        @Override
        public Set<ClassDescriptor> getLocatedClasses()
        {
            return classes;
        }

        @Override
        public Set<MappingFileDescriptor> getLocatedMappingFiles()
        {
            return mappings;
        }
    }

    private record ClassDescriptorValue(String name, Categorization categorization, InputStreamAccess streamAccess)
            implements ClassDescriptor
    {
        @Override
        public boolean equals(Object object)
        {
            return object instanceof ClassDescriptor descriptor && name.equals(descriptor.getName());
        }

        @Override
        public int hashCode()
        {
            return name.hashCode();
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public Categorization getCategorization()
        {
            return categorization;
        }

        @Override
        public InputStreamAccess getStreamAccess()
        {
            return streamAccess;
        }
    }

    private record PackageDescriptorValue(String name, InputStreamAccess streamAccess) implements PackageDescriptor
    {
        @Override
        public boolean equals(Object object)
        {
            return object instanceof PackageDescriptor descriptor && name.equals(descriptor.getName());
        }

        @Override
        public int hashCode()
        {
            return name.hashCode();
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public InputStreamAccess getStreamAccess()
        {
            return streamAccess;
        }
    }

    private record MappingFileDescriptorValue(String name, InputStreamAccess streamAccess)
            implements MappingFileDescriptor
    {
        @Override
        public boolean equals(Object object)
        {
            return object instanceof MappingFileDescriptor descriptor && name.equals(descriptor.getName());
        }

        @Override
        public int hashCode()
        {
            return name.hashCode();
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public InputStreamAccess getStreamAccess()
        {
            return streamAccess;
        }
    }
}
