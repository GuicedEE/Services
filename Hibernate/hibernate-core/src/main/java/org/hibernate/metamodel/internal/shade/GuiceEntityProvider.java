package org.hibernate.metamodel.internal.shade;

import com.google.inject.Provider;
import com.guicedee.client.IGuiceContext;
import java.util.Objects;

/** Resolves an entity binding once per instantiator, without caching entity instances. */
final class GuiceEntityProvider {
    private final IGuiceContext context;
    private final Class<?> entityType;
    private volatile Provider<?> provider;

    GuiceEntityProvider(IGuiceContext context, Class<?> entityType) {
        this.context = Objects.requireNonNull(context, "guiceContext");
        this.entityType = entityType;
    }

    Object get() {
        Provider<?> current = provider;
        if (current == null) {
            synchronized (this) {
                current = provider;
                if (current == null) provider = current = context.inject().getProvider(entityType);
            }
        }
        // Guice still applies custom providers, scopes, AOP and member injection on every call.
        return current.get();
    }
}
