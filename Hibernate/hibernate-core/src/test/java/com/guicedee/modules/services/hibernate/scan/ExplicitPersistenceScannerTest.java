package com.guicedee.modules.services.hibernate.scan;

import com.guicedee.client.IGuiceContext;
import jakarta.persistence.*;
import org.hibernate.boot.archive.scan.spi.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class ExplicitPersistenceScannerTest {
    @TempDir Path directory;
    @Entity static class Model { @Id Long id; static { throwIfInitialized(); } }
    @Embeddable static class Embedded { String value; }
    @MappedSuperclass static class Base { Long version; }
    @Converter static class Conversion implements AttributeConverter<String,String> {
        public String convertToDatabaseColumn(String value) { return value; }
        public String convertToEntityAttribute(String value) { return value; }
    }
    static void throwIfInitialized() { throw new AssertionError("Discovery initialized the entity"); }
    private final List<Class<?>> models = List.of(Model.class, Embedded.class, Base.class, Conversion.class, String.class, Model.class);
    private URL root() { return getClass().getProtectionDomain().getCodeSource().getLocation(); }
    private ScanResult scan(ScanEnvironment environment, ScanOptions options) {
        return new ExplicitPersistenceScanner(models, List.of(getClass().getPackage())).scan(environment, options, new ScanParameters() {});
    }
    @Test void classifiesRegisteredModelsWithoutInitializationOrDuplicates() throws Exception {
        var result = scan(environment(root(), List.of(), List.of(), List.of()), options(true, false));
        assertEquals(4, result.getLocatedClasses().size());
        assertEquals(1, result.getLocatedClasses().stream().filter(c -> c.getCategorization() == ClassDescriptor.Categorization.CONVERTER).count());
        for (var descriptor : result.getLocatedClasses()) {
            try (var input = descriptor.getStreamAccess().accessInputStream()) { assertEquals(0xCA, input.read()); }
        }
    }
    @Test void excludeUnlistedClassesAndOtherUnitRootsAreRespected() throws Exception {
        var excluded = scan(environment(root(), List.of(), List.of(Model.class.getName()), List.of()), options(false, false));
        assertEquals(Set.of(Model.class.getName()), names(excluded));
        assertTrue(scan(environment(directory.toUri().toURL(), List.of(), List.of(), List.of()), options(true, true)).getLocatedClasses().isEmpty());
    }
    @Test void nonRootArchivesUseTheirOwnDetectionSetting() throws Exception {
        var environment = environment(directory.toUri().toURL(), List.of(root()), List.of(), List.of());
        assertTrue(scan(environment, options(true, false)).getLocatedClasses().isEmpty());
        assertEquals(4, scan(environment, options(false, true)).getLocatedClasses().size());
    }
    @Test void packageMetadataIsAvailableWithoutClassScanning() throws Exception {
        var result = scan(environment(root(), List.of(), List.of(), List.of()), options(true, false));
        assertTrue(result.getLocatedPackages().stream().anyMatch(p -> p.getName().equals(getClass().getPackageName())));
        for (var descriptor : result.getLocatedPackages()) {
            try (var input = descriptor.getStreamAccess().accessInputStream()) { assertEquals(0xCA, input.read()); }
        }
    }
    @Test void readsDefaultAndExplicitMappingsWithoutEnumeratingOtherFiles() throws Exception {
        Files.createDirectories(directory.resolve("META-INF"));
        Files.writeString(directory.resolve("META-INF/orm.xml"), "default");
        Files.writeString(directory.resolve("selected.hbm.xml"), "selected");
        Files.writeString(directory.resolve("unlisted.hbm.xml"), "unlisted");
        var result = scan(environment(directory.toUri().toURL(), List.of(), List.of(), List.of("selected.hbm.xml")), options(false, false));
        assertEquals(Set.of("META-INF/orm.xml", "selected.hbm.xml"), result.getLocatedMappingFiles().stream().map(MappingFileDescriptor::getName).collect(java.util.stream.Collectors.toSet()));
        for (var mapping : result.getLocatedMappingFiles()) {
            try (var input = mapping.getStreamAccess().accessInputStream()) { assertTrue(input.readAllBytes().length > 0); }
        }
    }
    @Test void hibernateEntryPointNeverRequestsClassGraphInSpiMode() {
        String previous = System.getProperty("GUICEDEE_DISCOVERY_MODE");
        try {
            System.setProperty("GUICEDEE_DISCOVERY_MODE", "spi");
            IGuiceContext context = (IGuiceContext) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{IGuiceContext.class},
                    (proxy, method, args) -> { throw new AssertionError("Unexpected context call: " + method.getName()); });
            var result = new ClassGraphScanner(context).scan(environment(root(), List.of(), List.of(), List.of()), options(true, false), new ScanParameters() {});
            assertTrue(names(result).contains(Model.class.getName()));
        } finally {
            if (previous == null) System.clearProperty("GUICEDEE_DISCOVERY_MODE"); else System.setProperty("GUICEDEE_DISCOVERY_MODE", previous);
        }
    }
    @Test void missingOptionalMappingInRuntimeImageIsNotAnIoFailure() throws Exception {
        var result = scan(environment(java.net.URI.create("jrt:/java.base").toURL(), List.of(), List.of(), List.of()), options(false, false));
        assertTrue(result.getLocatedMappingFiles().isEmpty());
    }
    @Test void existingRuntimeImageResourceRemainsReadable() throws Exception {
        String resource = "java/lang/Object.class";
        var result = scan(environment(java.net.URI.create("jrt:/java.base").toURL(), List.of(), List.of(), List.of(resource)), options(false, false));
        assertEquals(1, result.getLocatedMappingFiles().size());
        var mapping = result.getLocatedMappingFiles().iterator().next();
        assertEquals(resource, mapping.getName());
        try (var input = mapping.getStreamAccess().accessInputStream()) { assertEquals(0xCA, input.read()); }
    }
    private static Set<String> names(ScanResult result) { return result.getLocatedClasses().stream().map(ClassDescriptor::getName).collect(java.util.stream.Collectors.toSet()); }
    private static ScanEnvironment environment(URL root, List<URL> others, List<String> classes, List<String> mappings) {
        return new ScanEnvironment() {
            public URL getRootUrl() { return root; }
            public List<URL> getNonRootUrls() { return others; }
            public List<String> getExplicitlyListedClassNames() { return classes; }
            public List<String> getExplicitlyListedMappingFiles() { return mappings; }
        };
    }
    private static ScanOptions options(boolean root, boolean others) {
        return new ScanOptions() {
            public boolean canDetectUnlistedClassesInRoot() { return root; }
            public boolean canDetectUnlistedClassesInNonRoot() { return others; }
            public boolean canDetectHibernateMappingFiles() { return false; }
        };
    }
}
