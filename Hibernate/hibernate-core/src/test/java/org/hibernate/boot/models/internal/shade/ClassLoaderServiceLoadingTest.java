package org.hibernate.boot.models.internal.shade;

import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import org.hibernate.models.spi.ModelsConfiguration;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class ClassLoaderServiceLoadingTest {
    private static final AtomicInteger initialized = new AtomicInteger();
    private static final AtomicInteger modelInitialized = new AtomicInteger();

    public static class MetadataType {
        static { modelInitialized.incrementAndGet(); }
        public java.math.BigDecimal amount;
        public java.time.LocalDate date;
    }

    public static class Deferred {
        static { initialized.incrementAndGet(); }
        public Deferred() {}
        public String value;
    }

    public static class InvalidInitializer {
        static { if (Boolean.parseBoolean("true")) throw new IllegalStateException("initializer failed"); }
        public InvalidInitializer() {}
    }

    @Test void metadataInspectionDoesNotInitializeButConstructionDoes() throws Exception {
        var service = new ClassLoaderServiceImpl(getClass().getClassLoader());
        try {
            var loading = new ClassLoaderServiceLoading(service);
            var type = loading.classForName(Deferred.class.getName());
            assertSame(Deferred.class, type);
            assertEquals(1, type.getDeclaredFields().length);
            assertEquals(0, initialized.get());
            type.getConstructor().newInstance();
            assertEquals(1, initialized.get());
        } finally { service.stop(); }
    }

    @Test void initializationErrorsAreStillReportedAtActualUse() throws Exception {
        var service = new ClassLoaderServiceImpl(getClass().getClassLoader());
        try {
            var loading = new ClassLoaderServiceLoading(service);
            var type = assertDoesNotThrow(() -> loading.classForName(InvalidInitializer.class.getName()));
            assertThrows(ExceptionInInitializerError.class, () -> type.getConstructor().newInstance());
        } finally { service.stop(); }
    }

    @Test void missingAndArrayTypesKeepTheClassLoadingContract() {
        var service = new ClassLoaderServiceImpl(getClass().getClassLoader());
        try {
            var loading = new ClassLoaderServiceLoading(service);
            assertSame(String[].class, loading.classForName("[Ljava.lang.String;"));
            assertSame(int[].class, loading.classForName("[I"));
            assertThrows(ClassLoadingException.class, () -> loading.classForName("missing.MetadataType"));
            assertNull(loading.findClassForName("missing.MetadataType"));
        } finally { service.stop(); }
    }

    @Test void customServiceRetainsControlOfClassResolution() {
        var calls = new AtomicInteger();
        var service = new ClassLoaderServiceImpl() {
            @Override @SuppressWarnings("unchecked") public <T> Class<T> classForName(String name) {
                calls.incrementAndGet();
                assertEquals("custom.type", name);
                return (Class<T>) Deferred.class;
            }
        };
        try {
            var loading = new ClassLoaderServiceLoading(service);
            assertSame(Deferred.class, loading.classForName("custom.type"));
            assertSame(Deferred.class, loading.findClassForName("custom.type"));
            assertEquals(2, calls.get());
        } finally { service.stop(); }
    }

    @Test void stoppedServicePreservesStrictAndOptionalLookupFailures() {
        var service = new ClassLoaderServiceImpl(getClass().getClassLoader());
        var loading = new ClassLoaderServiceLoading(service);
        service.stop();
        assertThrows(ClassLoadingException.class, () -> loading.classForName(String.class.getName()));
        assertNull(loading.findClassForName(String.class.getName()));
    }

    @Test void hibernateModelsCanInspectTypesWithoutInitializingThem() throws Exception {
        var service = new ClassLoaderServiceImpl(getClass().getClassLoader());
        try {
            var configuration = new ModelsConfiguration();
            configuration.setClassLoading(new ClassLoaderServiceLoading(service));
            var models = configuration.bootstrap();
            var details = models.getClassDetailsRegistry().resolveClassDetails(MetadataType.class.getName());
            assertSame(MetadataType.class, details.toJavaClass());
            assertEquals(2, details.getFields().size());
            assertEquals(0, modelInitialized.get());
            details.toJavaClass().getConstructor().newInstance();
            assertEquals(1, modelInitialized.get());
        } finally { service.stop(); }
    }
}
