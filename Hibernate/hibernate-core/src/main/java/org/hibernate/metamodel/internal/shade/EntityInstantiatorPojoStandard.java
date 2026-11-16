/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.metamodel.internal.shade;

import com.guicedee.client.IGuiceContext;
import org.hibernate.InstantiationException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.internal.AbstractEntityInstantiatorPojo;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.descriptor.java.JavaType;

import java.lang.reflect.Constructor;

import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import static org.hibernate.internal.util.ReflectHelper.getDefaultConstructor;

/**
 * Support for instantiating entity values as POJO representations through GuicedEE.
 */
public class EntityInstantiatorPojoStandard extends AbstractEntityInstantiatorPojo
{
    private final Constructor<?> constructor;
    private final GuiceEntityProvider entityProvider;

    public EntityInstantiatorPojoStandard(
            EntityPersister persister,
            PersistentClass persistentClass,
            JavaType<?> javaType)
    {
        this(persister, persistentClass, javaType, IGuiceContext.instance());
    }

    public EntityInstantiatorPojoStandard(
            EntityPersister persister,
            PersistentClass persistentClass,
            JavaType<?> javaType,
            IGuiceContext guiceContext)
    {
        super(persister, persistentClass, javaType);
        entityProvider = new GuiceEntityProvider(guiceContext, getMappedPojoClass());
        constructor = isAbstract() ? null : resolveConstructor(getMappedPojoClass());
    }

    protected static Constructor<?> resolveConstructor(Class<?> mappedPojoClass)
    {
        try
        {
            return getDefaultConstructor(mappedPojoClass);
        }
        catch (PropertyNotFoundException e)
        {
            CORE_LOGGER.noDefaultConstructor(mappedPojoClass.getName());
            return null;
        }
    }

    @Override
    public boolean canBeInstantiated()
    {
        return constructor != null;
    }

    @Override
    public Object instantiate()
    {
        if (isAbstract())
        {
            throw new InstantiationException("Cannot instantiate abstract class or interface", getMappedPojoClass());
        }
        if (constructor == null)
        {
            throw new InstantiationException("No default constructor for entity", getMappedPojoClass());
        }
        try
        {
            return applyInterception(entityProvider.get());
        }
        catch (Exception e)
        {
            throw new InstantiationException("Could not instantiate entity", getMappedPojoClass(), e);
        }
    }
}
