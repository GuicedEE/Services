package io.vrap.rmf.base.client.utils.json;

import discovery.fixture.Marker;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import static org.junit.jupiter.api.Assertions.*;

class ModelDiscoveryTest {
    private static final String PREFIX = "discovery.fixture";

    static class CountingLoader extends ClassLoader {
        int scans;
        CountingLoader(ClassLoader parent) { super(parent); }
        @Override public Enumeration<URL> getResources(String name) throws IOException {
            if (name.equals("discovery/fixture")) scans++;
            return super.getResources(name);
        }
    }

    private Set<Class<?>> query() { return ModelDiscovery.annotatedTypes(PREFIX, Marker.class); }

    @Test void matchesOriginalIncludingInheritedTypesAndReleasesEachScope() {
        var expected = new Reflections(PREFIX).getTypesAnnotatedWith(Marker.class);
        assertTrue(expected.contains(discovery.fixture.Parent.class));
        assertTrue(expected.contains(discovery.fixture.Child.class));
        var thread = Thread.currentThread();
        var previous = thread.getContextClassLoader();
        var loader = new CountingLoader(previous);
        thread.setContextClassLoader(loader);
        try {
            ModelDiscovery.inScope(true, () -> {
                assertEquals(expected, query());
                assertEquals(expected, query());
                assertEquals(1, loader.scans);
                return null;
            });
            assertEquals(expected, query());
            assertEquals(2, loader.scans, "A completed mapper must not retain its index");
            ModelDiscovery.inScope(false, () -> { query(); query(); return null; });
            assertEquals(4, loader.scans, "The override must restore independent scans");
        } finally { thread.setContextClassLoader(previous); }
    }

    @Test void isolatesNestedScopesAndContextLoadersAndCleansUpAfterFailure() {
        var thread = Thread.currentThread();
        var previous = thread.getContextClassLoader();
        var first = new CountingLoader(previous);
        var second = new CountingLoader(previous);
        thread.setContextClassLoader(first);
        try {
            assertThrows(IllegalStateException.class, () -> ModelDiscovery.inScope(true, () -> {
                query();
                ModelDiscovery.inScope(true, () -> { query(); return null; });
                query();
                assertEquals(2, first.scans, "Nested construction must restore the outer scope");
                thread.setContextClassLoader(second);
                query();
                assertEquals(1, second.scans, "A different loader needs its own scan");
                thread.setContextClassLoader(first);
                throw new IllegalStateException("provider failure");
            }));
            query();
            assertEquals(3, first.scans, "Failed construction must also release its index");
        } finally { thread.setContextClassLoader(previous); }
    }
}
