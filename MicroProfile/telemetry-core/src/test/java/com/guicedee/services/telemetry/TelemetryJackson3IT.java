package com.guicedee.services.telemetry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarFile;

import static org.junit.jupiter.api.Assertions.*;

/** Exercises the packaged, named shade: ordinary unit tests would load the upstream classes. */
class TelemetryJackson3IT {
    private static final String MODULE = "com.guicedee.modules.services.opentelemetry";
    private static ModuleLayer layer;
    private static ClassLoader loader;

    @BeforeAll
    static void loadPackagedModuleWithoutJackson2() throws Exception {
        Path shade = Path.of(System.getProperty("telemetry.shaded.jar"));
        assertTrue(Files.isRegularFile(shade), "Run verify to test the packaged shade");
        var paths = new ArrayList<Path>();
        paths.add(shade);
        for (String entry : System.getProperty("surefire.test.class.path", System.getProperty("java.class.path"))
                .split(File.pathSeparator)) {
            Path path = Path.of(entry);
            String name = path.getFileName().toString();
            if (Files.isRegularFile(path) && name.endsWith(".jar") && !path.equals(shade)
                    && !name.startsWith("jackson-core-2.") && !name.startsWith("jackson-databind-2.")) {
                paths.add(path);
            }
        }
        var finder = ModuleFinder.of(paths.toArray(Path[]::new));
        var configuration = ModuleLayer.boot().configuration().resolve(finder, ModuleFinder.of(), Set.of(MODULE));
        layer = ModuleLayer.boot().defineModulesWithOneLoader(configuration, ClassLoader.getPlatformClassLoader());
        loader = layer.findLoader(MODULE);
        assertFalse(layer.findModule(MODULE).orElseThrow().getDescriptor().isAutomatic());
        assertTrue(layer.findModule("tools.jackson.databind").isPresent());
        assertTrue(layer.findModule("com.fasterxml.jackson.core").isEmpty());
        assertTrue(layer.findModule("com.fasterxml.jackson.databind").isEmpty());
    }

    @Test
    void packagedBytecodeDoesNotReferenceJackson2CoreOrDatabind() throws Exception {
        try (var jar = new JarFile(System.getProperty("telemetry.shaded.jar"))) {
            for (var entry : jar.stream().filter(e -> e.getName().endsWith(".class")).toList()) {
                try (var input = jar.getInputStream(entry)) {
                    String bytes = new String(input.readAllBytes(), StandardCharsets.ISO_8859_1);
                    assertFalse(bytes.contains("com/fasterxml/jackson/core"), entry.getName());
                    assertFalse(bytes.contains("com/fasterxml/jackson/databind"), entry.getName());
                }
            }
        }
    }

    @Test
    void configurationConversionAcceptsJackson3ObjectMapper() throws Exception {
        Class<?> propertiesType = type("io.opentelemetry.api.incubator.config.DeclarativeConfigProperties");
        Object properties = Proxy.newProxyInstance(loader, new Class<?>[]{propertiesType}, (proxy, method, args) ->
                switch (method.getName()) {
                    case "getStructured" -> proxy;
                    case "getPropertyKeys" -> Set.of("message");
                    case "getString" -> "Jackson 3";
                    default -> null;
                });
        Class<?> providerType = type("io.opentelemetry.api.incubator.config.ConfigProvider");
        Object provider = Proxy.newProxyInstance(loader, new Class<?>[]{providerType}, (proxy, method, args) -> properties);
        Object mapper = type("tools.jackson.databind.json.JsonMapper").getConstructor().newInstance();
        Object converted = type("io.opentelemetry.api.incubator.config.InstrumentationConfigUtil")
                .getMethod("getInstrumentationConfigModel", providerType, String.class,
                        type("tools.jackson.databind.ObjectMapper"), Class.class)
                .invoke(null, provider, "test", mapper, Map.class);
        assertEquals(Map.of("message", "Jackson 3"), converted);
    }

    @Test
    void batchLogWorkerSerializesOtlpJsonInsideNamedModule() throws Exception {
        var json = new AtomicReference<String>();
        var failure = new AtomicReference<Throwable>();
        Class<?> resultType = type("io.opentelemetry.sdk.common.CompletableResultCode");
        Class<?> exporterType = type("io.opentelemetry.sdk.logs.export.LogRecordExporter");
        Object exporter = Proxy.newProxyInstance(loader, new Class<?>[]{exporterType}, (proxy, method, args) -> {
            if (method.getName().equals("export")) {
                try {
                    Object marshaler = type("io.opentelemetry.exporter.internal.otlp.logs.LogsRequestMarshaler")
                            .getMethod("create", Collection.class).invoke(null, args[0]);
                    var output = new ByteArrayOutputStream();
                    type("io.opentelemetry.exporter.internal.marshal.Marshaler")
                            .getMethod("writeJsonTo", OutputStream.class).invoke(marshaler, output);
                    json.set(output.toString(StandardCharsets.UTF_8));
                } catch (Throwable error) {
                    failure.set(error);
                    return resultType.getMethod("ofFailure").invoke(null);
                }
            }
            return resultType.getMethod("ofSuccess").invoke(null);
        });
        Object processorBuilder = type("io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor")
                .getMethod("builder", exporterType).invoke(null, exporter);
        Object processor = processorBuilder.getClass().getMethod("build").invoke(processorBuilder);
        Class<?> providerType = type("io.opentelemetry.sdk.logs.SdkLoggerProvider");
        Object providerBuilder = providerType.getMethod("builder").invoke(null);
        providerBuilder.getClass().getMethod("addLogRecordProcessor", type("io.opentelemetry.sdk.logs.LogRecordProcessor"))
                .invoke(providerBuilder, processor);
        Object provider = providerBuilder.getClass().getMethod("build").invoke(providerBuilder);
        try {
            Object logger = providerType.getMethod("get", String.class).invoke(provider, "jackson3-regression");
            Object record = type("io.opentelemetry.api.logs.Logger").getMethod("logRecordBuilder").invoke(logger);
            String body = "quoted \"value\"\nUnicode: \u2713";
            Class<?> recordType = type("io.opentelemetry.api.logs.LogRecordBuilder");
            recordType.getMethod("setBody", String.class).invoke(record, body);
            recordType.getMethod("emit").invoke(record);
            Object flush = providerType.getMethod("forceFlush").invoke(provider);
            resultType.getMethod("join", long.class, TimeUnit.class).invoke(flush, 10L, TimeUnit.SECONDS);
            assertNull(failure.get(), () -> String.valueOf(failure.get()));
            assertEquals(true, resultType.getMethod("isSuccess").invoke(flush));
            assertNotNull(json.get(), "The batch worker must serialize a log record");
            var tree = JsonMapper.builder().build().readTree(json.get());
            assertEquals(body, tree.path("resourceLogs").get(0).path("scopeLogs").get(0)
                    .path("logRecords").get(0).path("body").path("stringValue").asString());
        } finally {
            providerType.getMethod("close").invoke(provider);
        }
    }

    private static Class<?> type(String name) throws ClassNotFoundException {
        return Class.forName(name, true, loader);
    }
}
