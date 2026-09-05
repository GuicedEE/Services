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
import org.hibernate.bytecode.enhance.spi.interceptor.LazyAttributeLoadingInterceptor;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.internal.AbstractEntityInstantiatorPojo;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.type.descriptor.java.JavaType;

import java.lang.reflect.Constructor;
import java.util.Objects;

import static org.hibernate.engine.internal.ManagedTypeHelper.asPersistentAttributeInterceptable;
import static org.hibernate.engine.internal.ManagedTypeHelper.isPersistentAttributeInterceptableType;
import static org.hibernate.internal.CoreMessageLogger.CORE_LOGGER;
import static org.hibernate.internal.util.ReflectHelper.getDefaultConstructor;

/**
 * Support for instantiating entity values as POJO representations through GuicedEE.
 */
public class EntityInstantiatorPojoStandard extends AbstractEntityInstantiatorPojo
{
    private final Class<?> proxyInterface;
    private final boolean applyBytecodeInterception;
    private final LazyAttributeLoadingInterceptor.EntityRelatedState loadingInterceptorState;
    private final Constructor<?> constructor;
    private final IGuiceContext guiceContext;

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
        this.guiceContext = Objects.requireNonNull(guiceContext, "guiceContext");
        proxyInterface = persistentClass.getProxyInterface();
        constructor = isAbstract() ? null : resolveConstructor(getMappedPojoClass());
        applyBytecodeInterception = isPersistentAttributeInterceptableType(persistentClass.getMappedClass());
        if (applyBytecodeInterception)
        {
            loadingInterceptorState = new LazyAttributeLoadingInterceptor.EntityRelatedState(
                    persister.getEntityName(),
                    persister.getBytecodeEnhancementMetadata().getLazyAttributesMetadata().getLazyAttributeNames());
        }
        else
        {
            loadingInterceptorState = null;
        }
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
    protected Object applyInterception(Object entity)
    {
        if (applyBytecodeInterception)
        {
            asPersistentAttributeInterceptable(entity).$$_hibernate_setInterceptor(new LazyAttributeLoadingInterceptor(
                    loadingInterceptorState,
                    null,
                    null));
        }
        return entity;
    }

    @Override
    public boolean isInstance(Object object)
    {
        return super.isInstance(object) || proxyInterface != null && proxyInterface.isInstance(object);
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
            return applyInterception(guiceContext.inject().getInstance(getMappedPojoClass()));
        }
        catch (Exception e)
        {
            throw new InstantiationException("Could not instantiate entity", getMappedPojoClass(), e);
        }
    }
}
