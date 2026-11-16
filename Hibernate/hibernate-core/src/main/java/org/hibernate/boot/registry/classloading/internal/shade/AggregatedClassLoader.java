/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.boot.registry.classloading.internal.shade;

import org.hibernate.boot.registry.classloading.internal.TcclLookupPrecedence;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Hibernate 7.4 aggregate loader with identity-deduplicated delegates. TCCL is
 * resolved for every lookup; a thread's custom loader is never cached here.
 * Relocated onto Hibernate's implementation when this wrapper is packaged.
 */
public class AggregatedClassLoader extends ClassLoader {
    private final ClassLoader[] individualClassLoaders;
    private final TcclLookupPrecedence tcclLookupPrecedence;
    private final ClassLoader systemClassLoader;
    private final List<ClassLoader> withoutTccl;

    public AggregatedClassLoader(LinkedHashSet<ClassLoader> orderedClassLoaderSet,
                                 TcclLookupPrecedence precedence) {
        super(null);
        individualClassLoaders = orderedClassLoaderSet.toArray(ClassLoader[]::new);
        tcclLookupPrecedence = precedence;
        systemClassLoader = locateSystemClassLoader();
        var delegates = new ArrayList<ClassLoader>(orderedClassLoaderSet);
        addDistinct(delegates, systemClassLoader);
        withoutTccl = List.copyOf(delegates);
    }

    Iterator<ClassLoader> newClassLoaderIterator() {
        ClassLoader tccl = locateTCCL();
        if (tcclLookupPrecedence == TcclLookupPrecedence.NEVER || tccl == null
                || tcclLookupPrecedence == TcclLookupPrecedence.AFTER && containsIdentity(withoutTccl, tccl)
                || tcclLookupPrecedence == TcclLookupPrecedence.BEFORE
                    && !withoutTccl.isEmpty() && withoutTccl.getFirst() == tccl) {
            return withoutTccl.iterator();
        }
        var delegates = new ArrayList<ClassLoader>(individualClassLoaders.length + 2);
        if (tcclLookupPrecedence == TcclLookupPrecedence.BEFORE) addDistinct(delegates, tccl);
        for (ClassLoader loader : individualClassLoaders) addDistinct(delegates, loader);
        if (tcclLookupPrecedence == TcclLookupPrecedence.AFTER) addDistinct(delegates, tccl);
        else if (tcclLookupPrecedence != TcclLookupPrecedence.BEFORE)
            throw new IllegalStateException("Unknown precedence: " + tcclLookupPrecedence);
        addDistinct(delegates, systemClassLoader);
        return delegates.iterator();
    }

    private static void addDistinct(List<ClassLoader> loaders, ClassLoader candidate) {
        if (candidate != null && !containsIdentity(loaders, candidate)) loaders.add(candidate);
    }

    private static boolean containsIdentity(List<ClassLoader> loaders, ClassLoader candidate) {
        for (ClassLoader loader : loaders) if (loader == candidate) return true;
        return false;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        var resourceUrls = new LinkedHashSet<URL>();
        var delegates = newClassLoaderIterator();
        while (delegates.hasNext()) {
            var urls = delegates.next().getResources(name);
            while (urls.hasMoreElements()) resourceUrls.add(urls.nextElement());
        }
        return Collections.enumeration(resourceUrls);
    }

    @Override
    protected URL findResource(String name) {
        var delegates = newClassLoaderIterator();
        while (delegates.hasNext()) {
            URL resource = delegates.next().getResource(name);
            if (resource != null) return resource;
        }
        return super.findResource(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        var delegates = newClassLoaderIterator();
        ClassNotFoundException failure = null;
        while (delegates.hasNext()) {
            try {
                return delegates.next().loadClass(name);
            } catch (Exception | LinkageError cause) {
                // A successful lookup must not allocate and fill an exception stack.
                if (failure == null) failure = new ClassNotFoundException("Could not load requested class: " + name);
                failure.addSuppressed(cause);
            }
        }
        throw failure != null ? failure : new ClassNotFoundException("Could not load requested class: " + name);
    }

    private static ClassLoader locateSystemClassLoader() {
        try { return ClassLoader.getSystemClassLoader(); }
        catch (Exception ignored) { return null; }
    }

    private static ClassLoader locateTCCL() {
        try { return Thread.currentThread().getContextClassLoader(); }
        catch (Exception ignored) { return null; }
    }
}
