/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.metamodel.internal.shade;

import com.guicedee.client.IGuiceContext;
import org.hibernate.InstantiationException;
import org.hibernate.bytecode.spi.ReflectionOptimizer.InstantiationOptimizer;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.internal.AbstractEntityInstantiatorPojo;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.descriptor.java.JavaType;

import java.util.Objects;

/**
 * GuicedEE entity instantiator used when Hibernate enables its reflection optimizer.
 */
public class EntityInstantiatorPojoOptimized extends AbstractEntityInstantiatorPojo
{
    private final IGuiceContext guiceContext;

    public EntityInstantiatorPojoOptimized(
            EntityPersister persister,
            PersistentClass persistentClass,
            JavaType<?> javaType,
            InstantiationOptimizer instantiationOptimizer)
    {
        this(persister, persistentClass, javaType, instantiationOptimizer, IGuiceContext.instance());
    }

    public EntityInstantiatorPojoOptimized(
            EntityPersister persister,
            PersistentClass persistentClass,
            JavaType<?> javaType,
            InstantiationOptimizer instantiationOptimizer,
            IGuiceContext guiceContext)
    {
        super(persister, persistentClass, javaType);
        this.guiceContext = Objects.requireNonNull(guiceContext, "guiceContext");
    }

    @Override
    public Object instantiate()
    {
        if (isAbstract())
        {
            throw new InstantiationException("Cannot instantiate abstract class or interface", getMappedPojoClass());
        }
        try
        {
            return applyInterception(guiceContext.inject().getInstance(getMappedPojoClass()));
        }
        catch (Exception e)
        {
            throw new InstantiationException("Could not instantiate entity", getMappedPojoClass(), e);
        }
    }
}
