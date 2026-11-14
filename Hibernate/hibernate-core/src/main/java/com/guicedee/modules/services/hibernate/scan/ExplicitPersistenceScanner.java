package com.guicedee.modules.services.hibernate.scan;

import org.hibernate.boot.archive.scan.spi.*;
import org.hibernate.boot.archive.spi.ArchiveException;
import org.hibernate.boot.archive.spi.InputStreamAccess;

import java.io.*;
import java.net.*;
import java.util.*;

/** Reads explicitly registered model metadata and named mapping resources without enumerating archives. */
final class ExplicitPersistenceScanner implements org.hibernate.boot.archive.scan.spi.Scanner {
    private final List<Class<?>> registeredClasses;
    private final List<Package> registeredPackages;

    ExplicitPersistenceScanner(List<Class<?>> classes, List<Package> packages) {
        registeredClasses = classes;
        registeredPackages = packages;
    }

    @Override
    public ScanResult scan(ScanEnvironment environment, ScanOptions options, ScanParameters parameters) {
        allowPersistenceUnitModules(environment);
        Set<ClassDescriptor> classes = new LinkedHashSet<>();
        Set<PackageDescriptor> packages = new LinkedHashSet<>();
        Set<MappingFileDescriptor> mappings = new LinkedHashSet<>();
        List<String> listed = environment.getExplicitlyListedClassNames();

        // Explicit persistence.xml classes are processed by Hibernate itself, including XML-only models.
        // Discovery registrations supplement that list under the unit's exclude-unlisted-classes rules.
        for (Class<?> type : registeredClasses) {
            ClassDescriptor.Categorization category = category(type);
            if (category == null) continue;
            URL origin = origin(type);
            boolean root = sameLocation(origin, environment.getRootUrl());
            boolean nonRoot = environment.getNonRootUrls() != null
                    && environment.getNonRootUrls().stream().anyMatch(url -> sameLocation(origin, url));
            if (!(root || nonRoot) || !(listed.contains(type.getName())
                    || (root ? options.canDetectUnlistedClassesInRoot() : options.canDetectUnlistedClassesInNonRoot()))) continue;
            String path = type.getName().replace('.', '/') + ".class";
            URL resource = type.getResource("/" + path);
            if (resource == null) throw new ArchiveException("Cannot read registered persistence class " + type.getName());
            classes.add(new ClassGraphScanner.ClassDescriptorValue(type.getName(), category, streamAccess(resource, path)));
        }

        Set<String> packageNames = new LinkedHashSet<>();
        registeredPackages.forEach(pkg -> packageNames.add(pkg.getName()));
        registeredClasses.forEach(type -> packageNames.add(type.getPackageName()));
        for (URL location : locations(environment)) {
            boolean root = sameLocation(location, environment.getRootUrl());
            for (String name : packageNames) {
                if (name.isEmpty() || !(listed.contains(name)
                        || (root ? options.canDetectUnlistedClassesInRoot() : options.canDetectUnlistedClassesInNonRoot()))) continue;
                String path = name.replace('.', '/') + "/package-info.class";
                URL resource = resourceAt(location, path);
                if (resource != null) packages.add(new ClassGraphScanner.PackageDescriptorValue(name, streamAccess(resource, path)));
            }
            Set<String> mappingPaths = new LinkedHashSet<>(environment.getExplicitlyListedMappingFiles());
            mappingPaths.add("META-INF/orm.xml");
            for (String path : mappingPaths) {
                if (path.equals("META-INF/orm.xml") && !root
                        && environment.getExplicitlyListedMappingFiles().contains(path)) continue;
                URL resource = resourceAt(location, path);
                if (resource != null) mappings.add(new ClassGraphScanner.MappingFileDescriptorValue(path, streamAccess(resource, path)));
            }
        }
        return new ClassGraphScanner.ScanResultValue(packages, classes, mappings);
    }

    private static ClassDescriptor.Categorization category(Class<?> type) {
        Set<String> annotations = new HashSet<>();
        for (var annotation : type.getDeclaredAnnotations()) annotations.add(annotation.annotationType().getName());
        if (annotations.contains("jakarta.persistence.Entity") || annotations.contains("jakarta.persistence.Embeddable")
                || annotations.contains("jakarta.persistence.MappedSuperclass")) return ClassDescriptor.Categorization.MODEL;
        return annotations.contains("jakarta.persistence.Converter") ? ClassDescriptor.Categorization.CONVERTER : null;
    }

    private static List<URL> locations(ScanEnvironment environment) {
        Set<URL> locations = new LinkedHashSet<>();
        if (environment.getRootUrl() != null) locations.add(environment.getRootUrl());
        if (environment.getNonRootUrls() != null) locations.addAll(environment.getNonRootUrls());
        return List.copyOf(locations);
    }

    /** Hibernate's MethodHandles-based proxy factory needs a read edge as well as the application's opens. */
    private void allowPersistenceUnitModules(ScanEnvironment environment) {
        Set<ModuleLayer> layers = new LinkedHashSet<>();
        addLayer(layers, ModuleLayer.boot());
        registeredClasses.forEach(type -> addLayer(layers, type.getModule().getLayer()));
        com.guicedee.client.IGuiceContext.scanRoots.forEach(type -> addLayer(layers, type.getModule().getLayer()));
        List<URL> roots = locations(environment);
        Module hibernate = ExplicitPersistenceScanner.class.getModule();
        for (ModuleLayer layer : layers) for (Module module : layer.modules()) {
            var location = layer.configuration().findModule(module.getName()).flatMap(resolved -> resolved.reference().location());
            if (location.isEmpty()) continue;
            URI source = normalized(location.get());
            if (roots.stream().anyMatch(root -> normalized(root).equals(source))) hibernate.addReads(module);
        }
    }

    private static void addLayer(Set<ModuleLayer> layers, ModuleLayer layer) {
        if (layer != null && layers.add(layer)) layer.parents().forEach(parent -> addLayer(layers, parent));
    }

    private static URL origin(Class<?> type) {
        var module = type.getModule();
        if (module.isNamed() && module.getLayer() != null) {
            var location = module.getLayer().configuration().findModule(module.getName())
                    .flatMap(resolved -> resolved.reference().location());
            if (location.isPresent()) {
                try { return location.get().toURL(); }
                catch (MalformedURLException invalid) { throw new ArchiveException("Invalid module location for " + type.getName(), invalid); }
            }
        }
        var source = type.getProtectionDomain().getCodeSource();
        return source == null ? null : source.getLocation();
    }

    private static boolean sameLocation(URL left, URL right) {
        return left != null && right != null && normalized(left).equals(normalized(right));
    }

    private static URI normalized(URL url) {
        try {
            return normalized(url.toURI());
        } catch (URISyntaxException invalid) {
            throw new ArchiveException("Invalid persistence location " + url, invalid);
        }
    }

    private static URI normalized(URI uri) {
        String value = uri.normalize().toString();
        // Hibernate can supply a jar root while a class code source supplies its file URL.
        if (value.startsWith("jar:") && value.endsWith("!/")) value = value.substring(4, value.length() - 2);
        return URI.create(value.endsWith("/") ? value.substring(0, value.length() - 1) : value);
    }

    private static URL resourceAt(URL location, String path) {
        try {
            String base = location.toExternalForm();
            if (base.endsWith(".jar") || base.endsWith(".zip")) base = "jar:" + base + "!/";
            else if (!base.endsWith("/")) base += "/";
            URL resource = URI.create(base + new URI(null, null, path, null).getRawPath()).toURL();
            try (InputStream ignored = open(resource)) { return resource; }
            catch (FileNotFoundException absent) { return null; }
        } catch (IOException | URISyntaxException failure) {
            throw new ArchiveException("Cannot read persistence resource " + path + " in " + location, failure);
        }
    }

    private static InputStream open(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection.getInputStream();
    }

    private static InputStreamAccess streamAccess(URL resource, String name) {
        return new InputStreamAccess() {
            public String getStreamName() { return name; }
            public InputStream accessInputStream() {
                try { return open(resource); }
                catch (IOException failure) { throw new ArchiveException("Cannot read persistence resource " + resource, failure); }
            }
        };
    }
}
