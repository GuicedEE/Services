/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.models.internal.shade;

import java.net.URL;
import java.util.Collection;

import org.hibernate.boot.registry.classloading.internal.ClassLoaderServiceImpl;
import org.hibernate.boot.registry.classloading.spi.ClassLoaderService;
import org.hibernate.boot.registry.classloading.spi.ClassLoadingException;
import org.hibernate.models.spi.ClassLoading;

/**
 * Loads metadata types without running static initializers. Instantiation and
 * static member access retain normal JVM initialization. Custom class-loading
 * services retain their own classForName implementation.
 */
public class ClassLoaderServiceLoading implements ClassLoading {
    private final ClassLoaderService classLoaderService;
    private final boolean standardLoader;

    public ClassLoaderServiceLoading(ClassLoaderService classLoaderService) {
        this.classLoaderService = classLoaderService;
        standardLoader = classLoaderService.getClass() == ClassLoaderServiceImpl.class;
    }

    @Override
    public <T> Class<T> classForName(String name) {
        if (!standardLoader) return classLoaderService.classForName(name);
        try {
            return classLoaderService.workWithClassLoader(loader -> {
                try {
                    @SuppressWarnings("unchecked")
                    Class<T> type = (Class<T>) Class.forName(name, false, loader);
                    return type;
                } catch (ClassNotFoundException failure) {
                    throw new ClassLoadingException("Unable to load class [" + name + "]", failure);
                }
            });
        } catch (ClassLoadingException failure) {
            throw failure;
        } catch (Exception | LinkageError failure) {
            throw new ClassLoadingException("Unable to load class [" + name + "]", failure);
        }
    }

    @Override
    public <T> Class<T> findClassForName(String name) {
        try { return classForName(name); }
        catch (ClassLoadingException missing) { return null; }
    }

    @Override
    public URL locateResource(String resourceName) {
        return classLoaderService.locateResource(resourceName);
    }

    @Override
    public <S> Collection<S> loadJavaServices(Class<S> serviceType) {
        return classLoaderService.loadJavaServices(serviceType);
    }
}
