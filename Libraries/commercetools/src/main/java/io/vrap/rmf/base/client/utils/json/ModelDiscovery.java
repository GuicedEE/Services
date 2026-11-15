package io.vrap.rmf.base.client.utils.json;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.reflections.Reflections;

/**
 * Reuses the SDK's normal Reflections scan during one mapper construction.
 * No class list or package exclusions are substituted. Each construction starts
 * fresh, and its index is released even if a service provider throws.
 */
public final class ModelDiscovery {
    /** Set this ModuleOptions value to "false" to request independent scans. */
    public static final String REUSE_OPTION = "guicedee.reuseModelDiscovery";
    private static final ThreadLocal<Map<ClassLoader, Map<String, Reflections>>> CURRENT = new ThreadLocal<>();

    private ModelDiscovery() { }

    public static <T> T inScope(boolean reuse, Supplier<T> action) {
        // Nested mapper creation gets its own scope; no cache crosses threads,
        // context class loaders, or successive application reloads.
        var previous = CURRENT.get();
        if (reuse) CURRENT.set(new IdentityHashMap<>());
        else CURRENT.remove();
        try {
            return action.get();
        } finally {
            if (previous == null) CURRENT.remove();
            else CURRENT.set(previous);
        }
    }

    public static Set<Class<?>> annotatedTypes(String prefix, Class<? extends Annotation> annotation) {
        var scope = CURRENT.get();
        if (scope == null) return new Reflections(prefix).getTypesAnnotatedWith(annotation);
        var byPrefix = scope.computeIfAbsent(Thread.currentThread().getContextClassLoader(), ignored -> new HashMap<>());
        return byPrefix.computeIfAbsent(prefix, Reflections::new).getTypesAnnotatedWith(annotation);
    }
}
