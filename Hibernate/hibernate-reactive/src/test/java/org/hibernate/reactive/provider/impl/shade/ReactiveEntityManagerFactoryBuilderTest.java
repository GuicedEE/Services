package org.hibernate.reactive.provider.impl.shade;

import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.reactive.session.impl.ReactiveSessionFactoryImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReactiveEntityManagerFactoryBuilderTest {
    private ReactiveEntityManagerFactoryBuilder builder(MetadataImplementor metadata) throws Exception {
        // Skip the database-dependent constructor, but execute the real JPA population
        // and reactive build methods against the builder selected by metadata's SPI.
        var builder = mock(ReactiveEntityManagerFactoryBuilder.class, CALLS_REAL_METHODS);
        doReturn(metadata).when(builder).metadata();
        var settings = EntityManagerFactoryBuilderImpl.class.getDeclaredField("configurationValues");
        settings.setAccessible(true);
        settings.set(builder, new HashMap<>());
        return builder;
    }

    @Test void retainsSpiSelectedBuilderOptionsAndJpaLifecycleConfiguration() throws Exception {
        var metadata = mock(MetadataImplementor.class, RETURNS_DEEP_STUBS);
        var selected = mock(SessionFactoryBuilderImplementor.class);
        when(metadata.getSessionFactoryBuilder()).thenReturn(selected);
        var options = mock(SessionFactoryOptions.class);
        when(selected.buildSessionFactoryOptions()).thenReturn(options);
        var bootstrap = metadata.getTypeConfiguration().getMetadataBuildingContext().getBootstrapContext();
        var builder = builder(metadata);
        try (var factories = mockConstruction(ReactiveSessionFactoryImpl.class, (factory, context) -> {
            assertSame(metadata, context.arguments().get(0));
            assertSame(options, context.arguments().get(1));
            assertSame(bootstrap, context.arguments().get(2));
        })) {
            var result = builder.build();
            assertEquals(1, factories.constructed().size());
            assertSame(factories.constructed().getFirst(), result);
            verify(metadata).getSessionFactoryBuilder();
            verify(selected).buildSessionFactoryOptions();
            verify(selected).addSessionFactoryObservers(any());
            verify(selected).applyEntityNotFoundDelegate(any());
            verify(selected).disableJtaTransactionAccess();
        }
    }

    @Test void factoryBuildFailuresRetainThePersistenceExceptionBoundary() throws Exception {
        var metadata = mock(MetadataImplementor.class);
        var selected = mock(SessionFactoryBuilderImplementor.class);
        when(metadata.getSessionFactoryBuilder()).thenReturn(selected);
        var failure = new IllegalStateException("configured factory failed");
        when(selected.buildSessionFactoryOptions()).thenThrow(failure);
        var error = assertThrows(PersistenceException.class, () -> builder(metadata).build());
        assertSame(failure, error.getCause());
    }
}
