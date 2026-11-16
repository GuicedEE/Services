/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.reactive.provider.impl.shade;

import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.spi.PersistenceUnitDescriptor;
import org.hibernate.reactive.provider.ReactiveServiceRegistryBuilder;
import org.hibernate.reactive.provider.service.ReactiveSessionFactoryBuilder;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.Map;

/**
 * Heavily inspired by {@link EntityManagerFactoryBuilderImpl}.
 * This is intentionally not supporting several integration points of Hibernate ORM:
 * we can't test them all, better to build up integration points gradually.
 */
public final class ReactiveEntityManagerFactoryBuilder extends EntityManagerFactoryBuilderImpl {

    public ReactiveEntityManagerFactoryBuilder(PersistenceUnitDescriptor persistenceUnitDescriptor, Map integration) {
        super( persistenceUnitDescriptor, integration );
    }

    //Overridden so to use a customized service registry: see ReactiveServiceRegistryBuilder
    @Override
    protected StandardServiceRegistryBuilder getStandardServiceRegistryBuilder(BootstrapServiceRegistry bsr) {
        return ReactiveServiceRegistryBuilder.forJpa( bsr );
    }

    //Overridden to so provide a custom SessionFactoryBuilder; also, we want to avoid loading random
    //SessionFactoryBuilder implementations that might be found on the classpath.
    @Override
    public EntityManagerFactory build() {
        final MetadataImplementor metadata = metadata();
        // populateSessionFactoryBuilder resolves the registered builder service and SPI
        // factories. ReactiveSessionFactoryBuilderService already constructs and configures
        // the options used by the factory; a separate options builder here is discarded.
        final SessionFactoryBuilderImplementor reactiveSessionFactoryBuilder = new ReactiveSessionFactoryBuilder(
                metadata,
                (SessionFactoryBuilderImplementor) populateSessionFactoryBuilder()
		);

        try {
            return reactiveSessionFactoryBuilder.build();
        }
        catch (Exception e) {
            throw new PersistenceException( "Unable to build Hibernate SessionFactory ", e );
        }
    }
}
