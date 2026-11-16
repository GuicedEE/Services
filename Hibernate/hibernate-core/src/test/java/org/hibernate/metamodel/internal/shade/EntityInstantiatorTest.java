package org.hibernate.metamodel.internal.shade;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.guicedee.client.IGuiceContext;
import org.hibernate.InstantiationException;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributesMetadata;
import org.hibernate.bytecode.spi.BytecodeEnhancementMetadata;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.hibernate.mapping.RootClass;
import org.hibernate.metamodel.spi.EntityInstantiator;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.descriptor.java.JavaType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

class EntityInstantiatorTest {
    public static class Dependency { }
    public static class Entity { @Inject Dependency dependency; }
    public static class Replacement extends Entity { }
    public abstract static class AbstractEntity { }
    public static class NoDefault { public NoDefault(String ignored) { } }
    public static class Enhanced extends Entity implements PersistentAttributeInterceptable {
        private PersistentAttributeInterceptor interceptor;
        public PersistentAttributeInterceptor $$_hibernate_getInterceptor() { return interceptor; }
        public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor value) { interceptor = value; }
    }

    private static IGuiceContext context(Injector injector, AtomicInteger calls) {
        return (IGuiceContext) Proxy.newProxyInstance(EntityInstantiatorTest.class.getClassLoader(),
                new Class<?>[]{IGuiceContext.class}, (proxy, method, args) -> {
                    if (method.getName().equals("inject")) { calls.incrementAndGet(); return injector; }
                    throw new AssertionError(method.getName());
                });
    }

    @SuppressWarnings("unchecked")
    private static EntityInstantiator instantiator(boolean optimized, Class<?> type, IGuiceContext context) {
        var mapping = org.mockito.Mockito.mock(RootClass.class);
        org.mockito.Mockito.doReturn(type).when(mapping).getMappedClass();
        JavaType<?> javaType = (JavaType<?>) Proxy.newProxyInstance(type.getClassLoader(),
                new Class<?>[]{JavaType.class}, (proxy, method, args) -> {
                    if (method.getName().equals("getJavaTypeClass")) return type;
                    throw new AssertionError(method.getName());
                });
        BytecodeEnhancementMetadata enhancement = (BytecodeEnhancementMetadata) Proxy.newProxyInstance(
                type.getClassLoader(), new Class<?>[]{BytecodeEnhancementMetadata.class}, (proxy, method, args) -> {
                    if (method.getName().equals("getLazyAttributesMetadata")) return LazyAttributesMetadata.nonEnhanced(type.getName());
                    throw new AssertionError(method.getName());
                });
        EntityPersister persister = (EntityPersister) Proxy.newProxyInstance(type.getClassLoader(),
                new Class<?>[]{EntityPersister.class}, (proxy, method, args) -> switch (method.getName()) {
                    case "getEntityName" -> type.getName();
                    case "getBytecodeEnhancementMetadata" -> enhancement;
                    default -> throw new AssertionError(method.getName());
                });
        return optimized ? new EntityInstantiatorPojoOptimized(persister, mapping, javaType, null, context)
                : new EntityInstantiatorPojoStandard(persister, mapping, javaType, context);
    }

    @Test void bothInstantiatorsRetainBindingOverridesAndMemberInjection() {
        var dependency = new Dependency();
        var injector = Guice.createInjector(binder -> {
            binder.bind(Dependency.class).toInstance(dependency);
            binder.bind(Entity.class).to(Replacement.class);
        });
        for (boolean optimized : new boolean[]{false, true}) {
            var calls = new AtomicInteger();
            var factory = instantiator(optimized, Entity.class, context(injector, calls));
            assertEquals(0, calls.get());
            var first = (Entity) factory.instantiate();
            var second = (Entity) factory.instantiate();
            assertInstanceOf(Replacement.class, first);
            assertSame(dependency, first.dependency);
            assertSame(dependency, second.dependency);
            assertNotSame(first, second);
            assertEquals(1, calls.get());
        }
    }

    @Test void scopedAndCustomProvidersStillRunThroughGuice() {
        var provisions = new AtomicInteger();
        var injector = Guice.createInjector(binder -> binder.bind(Entity.class)
                .toProvider(() -> { provisions.incrementAndGet(); return new Entity(); }));
        var factory = instantiator(false, Entity.class, context(injector, new AtomicInteger()));
        assertNotSame(factory.instantiate(), factory.instantiate());
        assertEquals(2, provisions.get());
        var scoped = Guice.createInjector(binder -> binder.bind(Entity.class).in(Scopes.SINGLETON));
        factory = instantiator(true, Entity.class, context(scoped, new AtomicInteger()));
        assertSame(factory.instantiate(), factory.instantiate());
    }

    @Test void enhancementInterceptorsRemainSeparateForEachInstance() {
        for (boolean optimized : new boolean[]{false, true}) {
            var factory = instantiator(optimized, Enhanced.class, context(Guice.createInjector(), new AtomicInteger()));
            var first = (Enhanced) factory.instantiate();
            var second = (Enhanced) factory.instantiate();
            assertInstanceOf(LazyAttributeLoadingInterceptor.class, first.$$_hibernate_getInterceptor());
            assertNotSame(first.$$_hibernate_getInterceptor(), second.$$_hibernate_getInterceptor());
            assertNotNull(first.dependency);
        }
    }

    @Test void invalidTypesDoNotResolveProviders() {
        var calls = new AtomicInteger();
        var context = context(Guice.createInjector(), calls);
        for (boolean optimized : new boolean[]{false, true}) {
            var factory = instantiator(optimized, AbstractEntity.class, context);
            assertThrows(InstantiationException.class, factory::instantiate);
        }
        var factory = instantiator(false, NoDefault.class, context);
        assertThrows(InstantiationException.class, factory::instantiate);
        assertEquals(0, calls.get());
    }

    @Test void concurrentFirstUseResolvesOneProvider() throws Exception {
        var calls = new AtomicInteger();
        var factory = instantiator(false, Entity.class, context(Guice.createInjector(), calls));
        try (var executor = Executors.newFixedThreadPool(8)) {
            var results = new java.util.ArrayList<java.util.concurrent.Future<Object>>();
            for (int i = 0; i < 40; i++) results.add(executor.submit(factory::instantiate));
            var identities = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
            for (var result : results) identities.add(result.get(10, TimeUnit.SECONDS));
            assertEquals(40, identities.size());
            assertEquals(1, calls.get());
        }
    }

    @Test void bindingsAreNotSharedBetweenFactories() {
        var first = instantiator(false, Entity.class, context(Guice.createInjector(), new AtomicInteger()));
        var second = instantiator(false, Entity.class, context(Guice.createInjector(
                binder -> binder.bind(Entity.class).to(Replacement.class)), new AtomicInteger()));
        assertEquals(Entity.class, first.instantiate().getClass());
        assertInstanceOf(Replacement.class, second.instantiate());
    }
}
