package com.guicedee.modules.services.hibernate.scan;

import org.hibernate.boot.archive.scan.spi.*;
import org.hibernate.boot.archive.spi.ArchiveException;
import org.hibernate.boot.archive.spi.InputStreamAccess;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
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
        List<URL> locations = locations(environment);
        Set<URI> unitRoots = new HashSet<>();
        locations.forEach(location -> unitRoots.add(normalized(location)));
        URI rootLocation = environment.getRootUrl() == null ? null : normalized(environment.getRootUrl());
        allowPersistenceUnitModules(unitRoots);
        Set<ClassDescriptor> classes = new LinkedHashSet<>();
        Set<PackageDescriptor> packages = new LinkedHashSet<>();
        Set<MappingFileDescriptor> mappings = new LinkedHashSet<>();
        Set<String> listed = new HashSet<>(environment.getExplicitlyListedClassNames());
        boolean detectRoot = options.canDetectUnlistedClassesInRoot();
        boolean detectNonRoot = options.canDetectUnlistedClassesInNonRoot();
        Map<Module, URL> namedModuleLocations = new HashMap<>();

        // Explicit persistence.xml classes are processed by Hibernate itself, including XML-only models.
        // Discovery registrations supplement that list under the unit's exclude-unlisted-classes rules.
        for (Class<?> type : new LinkedHashSet<>(registeredClasses)) {
            boolean explicitlyListed = listed.contains(type.getName());
            if (!explicitlyListed && !detectRoot && !detectNonRoot) continue;
            URL origin = type.getModule().isNamed()
                    ? namedModuleLocations.computeIfAbsent(type.getModule(), ignored -> origin(type)) : origin(type);
            URI source = origin == null ? null : normalized(origin);
            boolean root = Objects.equals(source, rootLocation) && source != null;
            if (source == null || !unitRoots.contains(source)
                    || !(explicitlyListed || (root ? detectRoot : detectNonRoot))) continue;
            // Do not reflect on unrelated units or excluded application classes. This can
            // otherwise load annotation types and their dependencies that this unit never uses.
            ClassDescriptor.Categorization category = category(type);
            if (category == null) continue;
            String path = type.getName().replace('.', '/') + ".class";
            URL resource = type.getResource("/" + path);
            if (resource == null) throw new ArchiveException("Cannot read registered persistence class " + type.getName());
            classes.add(new ClassGraphScanner.ClassDescriptorValue(type.getName(), category, streamAccess(resource, path)));
        }

        Set<String> packageNames = new LinkedHashSet<>();
        registeredPackages.forEach(pkg -> packageNames.add(pkg.getName()));
        registeredClasses.forEach(type -> packageNames.add(type.getPackageName()));
        Set<String> mappingPaths = new LinkedHashSet<>(environment.getExplicitlyListedMappingFiles());
        mappingPaths.add("META-INF/orm.xml");
        for (URL location : locations) {
            boolean root = normalized(location).equals(rootLocation);
            for (String name : packageNames) {
                if (name.isEmpty() || !(listed.contains(name)
                        || (root ? detectRoot : detectNonRoot))) continue;
                String path = name.replace('.', '/') + "/package-info.class";
                URL resource = resourceAt(location, path);
                if (resource != null) packages.add(new ClassGraphScanner.PackageDescriptorValue(name, streamAccess(resource, path)));
            }
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
        boolean converter = false;
        for (var annotation : type.getDeclaredAnnotations()) {
            String name = annotation.annotationType().getName();
            if (name.equals("jakarta.persistence.Entity") || name.equals("jakarta.persistence.Embeddable")
                    || name.equals("jakarta.persistence.MappedSuperclass")) return ClassDescriptor.Categorization.MODEL;
            converter |= name.equals("jakarta.persistence.Converter");
        }
        return converter ? ClassDescriptor.Categorization.CONVERTER : null;
    }

    private static List<URL> locations(ScanEnvironment environment) {
        // URL equality can perform DNS and treats file:/model.jar and jar:file:/model.jar!/
        // as different roots. Compare normalized URIs once, keeping the root first.
        Map<URI, URL> locations = new LinkedHashMap<>();
        if (environment.getRootUrl() != null) locations.put(normalized(environment.getRootUrl()), environment.getRootUrl());
        if (environment.getNonRootUrls() != null) {
            for (URL location : environment.getNonRootUrls()) locations.putIfAbsent(normalized(location), location);
        }
        return List.copyOf(locations.values());
    }

    /** Hibernate's MethodHandles-based proxy factory needs a read edge as well as the application's opens. */
    private void allowPersistenceUnitModules(Set<URI> roots) {
        Set<ModuleLayer> layers = new LinkedHashSet<>();
        addLayer(layers, ModuleLayer.boot());
        registeredClasses.forEach(type -> addLayer(layers, type.getModule().getLayer()));
        com.guicedee.client.IGuiceContext.scanRoots.forEach(type -> addLayer(layers, type.getModule().getLayer()));
        Module hibernate = ExplicitPersistenceScanner.class.getModule();
        for (ModuleLayer layer : layers) for (Module module : layer.modules()) {
            if (hibernate.canRead(module)) continue;
            var location = layer.configuration().findModule(module.getName()).flatMap(resolved -> resolved.reference().location());
            if (location.isEmpty()) continue;
            URI source = normalized(location.get());
            if (roots.contains(source)) hibernate.addReads(module);
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
        if ("jrt".equals(url.getProtocol())) {
            // The jrt URL handler reports an absent optional resource as a generic IOException.
            // Its filesystem provider distinguishes absence from real I/O errors, as file/jar URLs do.
            try { return Files.newInputStream(Path.of(url.toURI())); }
            catch (NoSuchFileException absent) { throw new FileNotFoundException(url.toExternalForm()); }
            catch (URISyntaxException invalid) { throw new IOException("Invalid runtime-image resource " + url, invalid); }
        }
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
