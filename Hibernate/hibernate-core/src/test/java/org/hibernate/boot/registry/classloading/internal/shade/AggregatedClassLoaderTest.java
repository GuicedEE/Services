package org.hibernate.boot.registry.classloading.internal.shade;

import org.hibernate.boot.registry.classloading.internal.TcclLookupPrecedence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggregatedClassLoaderTest {
    private static final String RESOURCE = "guicedee-test/nonexistent-aggregate-resource";
    private ClassLoader originalTccl;

    @BeforeEach void rememberTccl() { originalTccl = Thread.currentThread().getContextClassLoader(); }
    @AfterEach void restoreTccl() { Thread.currentThread().setContextClassLoader(originalTccl); }

    @Test void repeatedDelegateIsEnumeratedOnceForEveryPrecedence() throws Exception {
        for (var precedence : TcclLookupPrecedence.values()) {
            var delegate = new ResourceLoader("shared");
            Thread.currentThread().setContextClassLoader(delegate);
            var aggregate = loader(precedence, delegate);
            assertEquals(delegate.urls, Collections.list(aggregate.getResources(RESOURCE)));
            assertEquals(1, delegate.resourceCalls, precedence.name());
        }
    }

    @Test void resourceOrderFollowsTcclPrecedenceAndDuplicateUrlsAreRemoved() throws Exception {
        var first = new ResourceLoader("common", "first");
        var second = new ResourceLoader("common", "second");
        Thread.currentThread().setContextClassLoader(second);
        assertEquals(List.of(url("common"), url("second"), url("first")),
                Collections.list(loader(TcclLookupPrecedence.BEFORE, first, second).getResources(RESOURCE)));
        assertEquals(List.of(url("common"), url("first"), url("second")),
                Collections.list(loader(TcclLookupPrecedence.AFTER, first, second).getResources(RESOURCE)));
    }

    @Test void changedTcclIsDiscoveredAndNeverModeIgnoresIt() throws Exception {
        var first = new ResourceLoader("first");
        var second = new ResourceLoader("second");
        var aggregate = loader(TcclLookupPrecedence.AFTER);
        Thread.currentThread().setContextClassLoader(first);
        assertEquals(first.urls, Collections.list(aggregate.getResources(RESOURCE)));
        Thread.currentThread().setContextClassLoader(second);
        assertEquals(second.urls, Collections.list(aggregate.getResources(RESOURCE)));
        assertTrue(Collections.list(loader(TcclLookupPrecedence.NEVER).getResources(RESOURCE)).isEmpty());
        assertEquals(1, first.resourceCalls);
        assertEquals(1, second.resourceCalls);
    }

    @Test void classResolutionPreservesBeforeAndAfterOrder() throws Exception {
        for (var precedence : List.of(TcclLookupPrecedence.BEFORE, TcclLookupPrecedence.AFTER)) {
            var supplied = new ResourceLoader("supplied");
            var context = new ResourceLoader("context");
            Thread.currentThread().setContextClassLoader(context);
            assertSame(AggregatedClassLoaderTest.class,
                    loader(precedence, supplied).loadClass(AggregatedClassLoaderTest.class.getName()));
            assertEquals(precedence == TcclLookupPrecedence.BEFORE ? 1 : 0, context.classCalls);
            assertEquals(precedence == TcclLookupPrecedence.AFTER ? 1 : 0, supplied.classCalls);
        }
    }

    @Test void failedLookupVisitsEachDelegateOnceAndKeepsFailureCauses() throws Exception {
        var delegate = new ResourceLoader("missing");
        Thread.currentThread().setContextClassLoader(delegate);
        var failure = assertThrows(ClassNotFoundException.class,
                () -> loader(TcclLookupPrecedence.AFTER, delegate).loadClass("guicedee.test.MissingType"));
        assertEquals(1, delegate.classCalls);
        assertEquals(2, failure.getSuppressed().length, "Supplied loader and system loader each explain failure");
        assertTrue(failure.getSuppressed()[0].getMessage().contains("missing"));
    }

    @Test void systemLoaderIsNotRepeatedWhenAlsoSuppliedAndContextLoader() {
        var system = ClassLoader.getSystemClassLoader();
        Thread.currentThread().setContextClassLoader(system);
        for (var precedence : TcclLookupPrecedence.values()) {
            var delegates = loader(precedence, system).newClassLoaderIterator();
            assertSame(system, delegates.next());
            assertFalse(delegates.hasNext());
        }
    }

    private static AggregatedClassLoader loader(TcclLookupPrecedence precedence, ClassLoader... delegates) {
        return new AggregatedClassLoader(new LinkedHashSet<>(List.of(delegates)), precedence);
    }

    private static URL url(String name) throws Exception { return URI.create("file:/" + name).toURL(); }

    private static final class ResourceLoader extends ClassLoader {
        private final List<URL> urls;
        private int resourceCalls;
        private int classCalls;

        ResourceLoader(String... resources) throws Exception {
            super(null);
            var entries = new java.util.ArrayList<URL>();
            for (String resource : resources) entries.add(url(resource));
            urls = List.copyOf(entries);
        }

        @Override public Enumeration<URL> getResources(String name) {
            resourceCalls++;
            return Collections.enumeration(urls);
        }

        @Override public Class<?> loadClass(String name) throws ClassNotFoundException {
            classCalls++;
            if (AggregatedClassLoaderTest.class.getName().equals(name)) return AggregatedClassLoaderTest.class;
            throw new ClassNotFoundException(urls.getFirst() + ": " + name);
        }
    }
}
