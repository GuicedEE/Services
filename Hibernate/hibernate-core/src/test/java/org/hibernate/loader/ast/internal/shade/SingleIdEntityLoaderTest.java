package org.hibernate.loader.ast.internal.shade;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.loader.ast.internal.SingleIdLoadPlan;
import org.hibernate.loader.ast.spi.CascadingFetchProfile;
import org.hibernate.metamodel.mapping.EntityMappingType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SingleIdEntityLoaderTest {
    private final EntityMappingType mapping = mock(EntityMappingType.class);
    private final SessionFactoryImplementor factory = mock(SessionFactoryImplementor.class);
    private final LoadQueryInfluencers influencers = mock(LoadQueryInfluencers.class);
    private final AtomicInteger creations = new AtomicInteger();

    private class Loader extends SingleIdEntityLoaderStandardImpl<Object> {
        Loader(BiFunction<LockOptions, LoadQueryInfluencers, SingleIdLoadPlan<Object>> creator) {
            super(mapping, influencers, creator);
        }
    }

    private Loader loader(Map<String, Object> settings) {
        when(influencers.getSessionFactory()).thenReturn(factory);
        when(factory.getProperties()).thenReturn(settings);
        return new Loader((locks, context) -> {
            creations.incrementAndGet();
            return mock(SingleIdLoadPlan.class);
        });
    }

    @Test void constructionDoesNotBuildUnusedPlansAndFirstLookupCaches() {
        var loader = loader(Map.of());
        assertEquals(0, creations.get());
        var first = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertSame(first, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        assertEquals(1, creations.get());
        assertNotSame(first, loader.resolveLoadPlan(new LockOptions(LockMode.PESSIMISTIC_WRITE), influencers));
        assertEquals(2, creations.get());
    }

    @Test void eagerOptionRetainsPrewarming() {
        for (Object enabled : new Object[]{true, "true"}) {
            creations.set(0);
            var loader = loader(Map.of("guicedee.persistence.eager_entity_load_plans", enabled));
            assertEquals(1, creations.get());
            loader.resolveLoadPlan(LockOptions.NONE, influencers);
            assertEquals(1, creations.get());
        }
    }

    @Test void filtersBypassAndDoNotPolluteTheRegularCache() {
        var loader = loader(Map.of());
        when(mapping.isAffectedByEnabledFilters(influencers, true)).thenReturn(true);
        var filtered = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertNotSame(filtered, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        when(mapping.isAffectedByEnabledFilters(influencers, true)).thenReturn(false);
        var ordinary = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertNotSame(filtered, ordinary);
        assertSame(ordinary, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        assertEquals(3, creations.get());
    }

    @Test void entityGraphsFetchProfilesAndCustomLockOptionsRemainUncached() {
        var loader = loader(Map.of());
        when(mapping.isAffectedByEntityGraph(influencers)).thenReturn(true);
        assertNotSame(loader.resolveLoadPlan(LockOptions.NONE, influencers), loader.resolveLoadPlan(LockOptions.NONE, influencers));
        when(mapping.isAffectedByEntityGraph(influencers)).thenReturn(false);
        when(mapping.isAffectedByEnabledFetchProfiles(influencers)).thenReturn(true);
        assertNotSame(loader.resolveLoadPlan(LockOptions.NONE, influencers), loader.resolveLoadPlan(LockOptions.NONE, influencers));
        when(mapping.isAffectedByEnabledFetchProfiles(influencers)).thenReturn(false);
        var locks = new LockOptions(LockMode.PESSIMISTIC_WRITE).setTimeOut(25);
        assertNotSame(loader.resolveLoadPlan(locks, influencers), loader.resolveLoadPlan(locks, influencers));
        assertEquals(6, creations.get());
    }

    @Test void auditedTemporalLoadsNeverReuseTheOrdinaryPlan() {
        var loader = loader(Map.of());
        var ordinary = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        var persister = mock(org.hibernate.persister.entity.EntityPersister.class);
        when(mapping.getEntityPersister()).thenReturn(persister);
        when(persister.getAuditMapping()).thenReturn(mock(org.hibernate.metamodel.mapping.AuditMapping.class));
        when(influencers.getTemporalIdentifier()).thenReturn(7L);
        var historical = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertNotSame(ordinary, historical);
        assertNotSame(historical, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        when(influencers.getTemporalIdentifier()).thenReturn(null);
        assertSame(ordinary, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        assertEquals(3, creations.get());
    }

    @Test void cascadingProfilesHaveIndependentCachesAndFiltersTakePrecedence() {
        var loader = loader(Map.of());
        var ordinary = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        when(influencers.hasEnabledCascadingFetchProfile()).thenReturn(true);
        when(influencers.getEnabledCascadingFetchProfile()).thenReturn(CascadingFetchProfile.MERGE);
        var merge = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertNotSame(ordinary, merge);
        assertSame(merge, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        when(influencers.getEnabledCascadingFetchProfile()).thenReturn(CascadingFetchProfile.REFRESH);
        var refresh = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertNotSame(merge, refresh);
        when(mapping.isAffectedByEnabledFilters(influencers, true)).thenReturn(true);
        assertNotSame(refresh, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        assertEquals(4, creations.get());
    }

    @Test void concurrentFirstUsePublishesOneReusablePlan() throws Exception {
        var loader = loader(Map.of());
        var start = new CountDownLatch(1);
        try (var executor = Executors.newFixedThreadPool(8)) {
            var results = new ArrayList<java.util.concurrent.Future<SingleIdLoadPlan<Object>>>();
            for (int i = 0; i < 8; i++) results.add(executor.submit(() -> {
                assertTrue(start.await(10, TimeUnit.SECONDS));
                return loader.resolveLoadPlan(LockOptions.NONE, influencers);
            }));
            start.countDown();
            var selected = results.getFirst().get(10, TimeUnit.SECONDS);
            for (var result : results) assertSame(selected, result.get(10, TimeUnit.SECONDS));
            int built = creations.get();
            assertTrue(built >= 1 && built <= 8);
            assertSame(selected, loader.resolveLoadPlan(LockOptions.NONE, influencers));
            assertEquals(built, creations.get());
        }
    }

    @Test void failedPlanConstructionCanBeRetried() {
        when(influencers.getSessionFactory()).thenReturn(factory);
        when(factory.getProperties()).thenReturn(Map.of());
        var loader = new Loader((locks, context) -> {
            if (creations.getAndIncrement() == 0) throw new IllegalStateException("mapping failure");
            return mock(SingleIdLoadPlan.class);
        });
        assertThrows(IllegalStateException.class, () -> loader.resolveLoadPlan(LockOptions.NONE, influencers));
        var successful = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        assertSame(successful, loader.resolveLoadPlan(LockOptions.NONE, influencers));
        assertEquals(2, creations.get());
    }

    @Test void firstLoadDelegatesToTheCreatedPlanWithTheCurrentSession() {
        var loader = loader(Map.of());
        var session = mock(SharedSessionContractImplementor.class);
        when(session.getLoadQueryInfluencers()).thenReturn(influencers);
        loader.load(42L, LockOptions.NONE, true, session);
        var plan = loader.resolveLoadPlan(LockOptions.NONE, influencers);
        verify(plan).load(42L, true, true, session);
        assertEquals(1, creations.get());
    }
}
