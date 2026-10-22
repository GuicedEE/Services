package com.guicedee.services.hazelcast;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.lang.module.ModuleFinder;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarFile;
import static org.junit.jupiter.api.Assertions.*;

/** Exercises the actual delivered module, without Jackson 2 or the upstream Hazelcast jar. */
class HazelcastJackson3IT {
    private static ClassLoader loader;
    private static final String MODULE = "com.hazelcast.all";

    @BeforeAll
    static void loadPackagedModule() throws Exception {
        Path shade = Path.of(System.getProperty("hazelcast.shaded.jar"));
        var paths = new ArrayList<Path>();
        paths.add(shade);
        for (String entry : System.getProperty("surefire.test.class.path", System.getProperty("java.class.path")).split(File.pathSeparator)) {
            Path path = Path.of(entry);
            String name = path.getFileName().toString();
            // Only mandatory runtime modules: optional upstream test dependencies include non-modular Groovy.
            if (Files.isRegularFile(path) && name.endsWith(".jar") && !path.equals(shade)
                    && (name.startsWith("cache-api-") || name.startsWith("jackson-core-3.")
                    || name.startsWith("jackson-databind-3.") || name.startsWith("jackson-annotations-"))) paths.add(path);
        }
        var finder = ModuleFinder.of(paths.toArray(Path[]::new));
        var config = ModuleLayer.boot().configuration().resolve(finder, ModuleFinder.of(), Set.of(MODULE));
        var layer = ModuleLayer.boot().defineModulesWithOneLoader(config, ClassLoader.getPlatformClassLoader());
        loader = layer.findLoader(MODULE);
        assertFalse(layer.findModule(MODULE).orElseThrow().getDescriptor().isAutomatic());
        assertEquals(System.getProperty("jackson.expected.version"), layer.findModule("tools.jackson.databind").orElseThrow().getDescriptor().rawVersion().orElseThrow());
        assertTrue(layer.findModule("com.fasterxml.jackson.databind").isEmpty());
        assertTrue(layer.findModule("com.fasterxml.jackson.core").isEmpty());
    }

    @Test
    void packagedClassesAndMetadataContainNoJackson2() throws Exception {
        try (var jar = new JarFile(System.getProperty("hazelcast.shaded.jar"))) {
            for (var entry : jar.stream().toList()) {
                String name = entry.getName();
                assertFalse(name.contains("com/fasterxml/jackson/core/"), name);
                assertFalse(name.contains("com/fasterxml/jackson/databind/"), name);
                assertFalse(name.contains("com.hazelcast.shaded.com.fasterxml.jackson"), name);
                assertFalse(name.contains("META-INF/maven/com.fasterxml.jackson.core/jackson-"), name);
                assertFalse(name.contains("com/hazelcast/shaded/tools/jackson/core/"), name);
                assertFalse(name.contains("com/hazelcast/shaded/tools/jackson/databind/"), name);
                if (name.endsWith(".class")) {
                    try (var input = jar.getInputStream(entry)) {
                        String bytes = new String(input.readAllBytes(), StandardCharsets.ISO_8859_1);
                        assertFalse(bytes.contains("com/fasterxml/jackson/core/"), name);
                        assertFalse(bytes.contains("com/fasterxml/jackson/databind/"), name);
                    }
                }
            }
            var properties = new Properties();
            try (var input = jar.getInputStream(jar.getJarEntry("META-INF/maven/tools.jackson.jr/jackson-jr-objects/pom.properties"))) { properties.load(input); }
            assertEquals(System.getProperty("jackson.expected.version"), properties.getProperty("version"));
        }
    }

    @Test
    void jetJsonRoundTripUsesJackson3() throws Exception {
        Class<?> json = type("com.hazelcast.jet.json.JsonUtil");
        String encoded = (String) json.getMethod("toJson", Object.class).invoke(null, Map.of("message", "Jackson 3", "count", 3));
        assertEquals(Map.of("message", "Jackson 3", "count", 3), json.getMethod("mapFrom", Object.class).invoke(null, encoded));
        assertThrows(ClassNotFoundException.class, () -> type("com.hazelcast.shaded.com.fasterxml.jackson.databind.ObjectMapper"));
    }

    @Test
    void twoMembersReplicateJsonValues() throws Exception {
        int port;
        try (var socket = new ServerSocket(0)) { port = socket.getLocalPort(); }
        String cluster = "jackson3-" + UUID.randomUUID();
        Object first = member(cluster, port, port);
        Object second = null;
        Class<?> instance = type("com.hazelcast.core.HazelcastInstance");
        try {
            second = member(cluster, port, port);
            Class<?> clusterType = type("com.hazelcast.cluster.Cluster");
            long deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(30);
            while (((Set<?>) clusterType.getMethod("getMembers").invoke(instance.getMethod("getCluster").invoke(first))).size() != 2 && System.nanoTime() < deadline) Thread.sleep(100);
            assertEquals(2, ((Set<?>) clusterType.getMethod("getMembers").invoke(instance.getMethod("getCluster").invoke(first))).size());
            Object map1 = instance.getMethod("getMap", String.class).invoke(first, "json");
            Object map2 = instance.getMethod("getMap", String.class).invoke(second, "json");
            Class<?> mapType = type("com.hazelcast.map.IMap");
            Object value = type("com.hazelcast.core.HazelcastJsonValue").getConstructor(String.class).newInstance("{\"version\":3}");
            mapType.getMethod("put", Object.class, Object.class).invoke(map1, "key", value);
            assertEquals("{\"version\":3}", mapType.getMethod("get", Object.class).invoke(map2, "key").toString());
        } finally {
            if (second != null) instance.getMethod("shutdown").invoke(second);
            instance.getMethod("shutdown").invoke(first);
        }
    }

    private static Object member(String cluster, int port, int seed) throws Exception {
        Class<?> configType = type("com.hazelcast.config.Config");
        Object config = configType.getConstructor().newInstance();
        configType.getMethod("setClusterName", String.class).invoke(config, cluster);
        for (String property : new String[]{"hazelcast.operation.thread.count", "hazelcast.operation.generic.thread.count", "hazelcast.io.input.thread.count", "hazelcast.io.output.thread.count", "hazelcast.event.thread.count"}) configType.getMethod("setProperty", String.class, String.class).invoke(config, property, "2");
        configType.getMethod("setProperty", String.class, String.class).invoke(config, "hazelcast.logging.type", "none");
        Object network = configType.getMethod("getNetworkConfig").invoke(config);
        network.getClass().getMethod("setPort", int.class).invoke(network, port);
        network.getClass().getMethod("setPortAutoIncrement", boolean.class).invoke(network, true);
        Object join = network.getClass().getMethod("getJoin").invoke(network);
        for (String getter : new String[]{"getMulticastConfig", "getAutoDetectionConfig"}) {
            Object section = join.getClass().getMethod(getter).invoke(join);
            section.getClass().getMethod("setEnabled", boolean.class).invoke(section, false);
        }
        Object tcp = join.getClass().getMethod("getTcpIpConfig").invoke(join);
        tcp.getClass().getMethod("setEnabled", boolean.class).invoke(tcp, true);
        tcp.getClass().getMethod("addMember", String.class).invoke(tcp, "127.0.0.1:" + seed);
        return type("com.hazelcast.core.Hazelcast").getMethod("newHazelcastInstance", configType).invoke(null, config);
    }

    private static Class<?> type(String name) throws ClassNotFoundException { return Class.forName(name, true, loader); }
}
