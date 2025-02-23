package com.guicedee.client;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.guicedee.guicedinjection.interfaces.IGuiceConfig;
import com.guicedee.guicedinjection.interfaces.IGuicePreDestroy;
import com.guicedee.guicedinjection.interfaces.IGuicePreStartup;
import com.guicedee.guicedinjection.interfaces.IGuiceProvider;
import com.guicedee.guicedinjection.interfaces.annotations.INotEnhanceable;
import com.guicedee.guicedinjection.interfaces.annotations.INotInjectable;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.vertx.core.Future;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface IGuiceContext {
    Map<String, IGuiceContext> contexts = new HashMap<>();
    Set<String> registerModuleForScanning = new LinkedHashSet<>();
    List<com.google.inject.Module> modules = new ArrayList<>();
    Map<Class, Set> allLoadedServices = new LinkedHashMap<>();

    static IGuiceContext getContext() {
        if (contexts.isEmpty()) {
            ServiceLoader<IGuiceProvider> load = ServiceLoader.load(IGuiceProvider.class);
            for (IGuiceProvider iGuiceProvider : load) {
                IGuiceContext iGuiceContext = iGuiceProvider.get();
                contexts.put("default", iGuiceContext);
                break;
            }
        }
        return contexts.get("default");
    }

    static IGuiceContext instance() {
        return getContext();
    }

    static Map<Class, Set> getAllLoadedServices() {
        return allLoadedServices;
    }

    Future<Void> getLoadingFinished();

    Injector inject();

    IGuiceConfig<?> getConfig();

    void destroy();

    static <T> T get(Key<T> type) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) type
                .getTypeLiteral()
                .getRawType();
        T instance;
        boolean isEntityType = isEntityType(clazz);
        if (isNotEnhanceable(clazz) || isEntityType) {
            try {
                instance = clazz
                        .getDeclaredConstructor()
                        .newInstance();
                if (!isNotInjectable(clazz)) {
                    getContext()
                            .inject()
                            .injectMembers(instance);
                }
            } catch (Exception e) {
                Logger.getLogger("IGuiceContext").log(Level.SEVERE, "Unable to construct [" + clazz.getCanonicalName() + "]. Not Enhanceable or an Entity.", e);
                throw new RuntimeException(e);
            }
        } else {
            instance = getContext()
                    .inject()
                    .getInstance(type);
        }
        return instance;
    }

    private static boolean isNotEnhanceable(Class<?> clazz) {
        return clazz.isAnnotationPresent(INotEnhanceable.class);
    }

    private static boolean isNotInjectable(Class<?> clazz) {
        return clazz.isAnnotationPresent(INotInjectable.class);
    }

    private static boolean isEntityType(Class<?> clazz) {
        try {
            for (Annotation annotation : clazz.getAnnotations()) {
                if (annotation
                        .annotationType()
                        .getCanonicalName()
                        .equalsIgnoreCase("jakarta.persistence.Entity")) {
                    return true;
                }
            }
        } catch (NullPointerException npe) {
            return false;
        }
        return false;
    }

    static <T> T get(Class<T> type, Class<? extends Annotation> annotation) {
        if (annotation == null) {
            return get(Key.get(type));
        }
        return get(Key.get(type, annotation));
    }

    static <T> T get(Class<T> type) {
        return get(type, null);
    }

    ScanResult getScanResult();

    Map<String, Set<Class>> loaderClasses = new ConcurrentHashMap<>();

    static <T extends Comparable<T>> Set<T> loaderToSet(ServiceLoader<T> loader) {
        @SuppressWarnings("rawtypes")
        Set<Class> loadeds = new HashSet<>();

        String type = loader.toString();
        type = type.replace("java.util.ServiceLoader[", "");
        type = type.substring(0, type.length() - 1);

        if (!loaderClasses.containsKey(type)) {
            IGuiceConfig<?> config = getContext().getConfig();
            if (config.isServiceLoadWithClassPath()) {
                for (ClassInfo classInfo : instance()
                        .getScanResult()
                        .getClassesImplementing(type)) {
                    Class<T> load = (Class<T>) classInfo.loadClass();
                    loadeds.add(load);
                }
            }
            try {
                for (T newInstance : loader) {
                    loadeds.add(newInstance.getClass());
                }
            } catch (Throwable T) {
                Logger.getLogger("IGuiceContext").log(Level.SEVERE, "Unable to provide instance of " + type + " to TreeSet", T);
            }
            loaderClasses.put(type, loadeds);
        }

        Set<T> outcomes = new TreeSet<>();
        for (Class<?> aClass : loaderClasses.get(type)) {
            outcomes.add((T) IGuiceContext.get(aClass));
        }
        return outcomes;
    }

    static <T> Set<T> loaderToSetNoInjection(ServiceLoader<T> loader) {
        Set<Class<T>> loadeds = new HashSet<>();
        IGuiceConfig<?> config = getContext().getConfig();
        String type = loader.toString();
        type = type.replace("java.util.ServiceLoader[", "");
        type = type.substring(0, type.length() - 1);
        if (config.isServiceLoadWithClassPath() && instance().getScanResult() != null) {
            for (ClassInfo classInfo : instance()
                    .getScanResult()
                    .getClassesImplementing(type)) {
                Class<T> load = (Class<T>) classInfo.loadClass();
                loadeds.add(load);
            }
        }
        Set<Class<T>> completed = new LinkedHashSet<>();
        Set<T> output = new LinkedHashSet<>();
        try {
            for (T newInstance : loader) {
                output.add(newInstance);
                completed.add((Class<T>) newInstance.getClass());
            }
        } catch (java.util.ServiceConfigurationError T) {
            Logger.getLogger("IGuiceContext").log(Level.WARNING, "Cannot load services - ", T);
        } catch (Throwable T) {
            Logger.getLogger("IGuiceContext").log(Level.SEVERE, "Cannot load services - ", T);
        }
        for (Class<T> newInstance : loadeds) {
            if (completed.contains(newInstance)) {
                continue;
            }
            try {
                output.add((T) newInstance.getDeclaredConstructor());
            } catch (NoSuchMethodException e) {
                Logger.getLogger("IGuiceContext").log(Level.SEVERE, "Cannot load a service through default constructor", e);
            }
        }
        return output;
    }

    static <T extends Comparable<T>> Set<Class<T>> loadClassSet(ServiceLoader<T> loader) {
        String type = loader.toString();
        type = type.replace("java.util.ServiceLoader[", "");
        type = type.substring(0, type.length() - 1);

        if (!loaderClasses.containsKey(type)) {
            Set<Class> loadeds = new HashSet<>();
            IGuiceConfig<?> config = getContext().getConfig();
            if (config.isServiceLoadWithClassPath()) {
                for (ClassInfo classInfo : instance()
                        .getScanResult()
                        .getClassesImplementing(type)) {
                    @SuppressWarnings("unchecked")
                    Class<T> load = (Class<T>) classInfo.loadClass();
                    loadeds.add(load);
                }
            }
            try {
                for (T newInstance : loader) {
                    //noinspection unchecked
                    loadeds.add((Class<T>) newInstance.getClass());
                }
            } catch (Throwable T) {
                Logger.getLogger("IGuiceContext").log(Level.SEVERE, "Unable to provide instance of " + type + " to TreeSet", T);
            }
            loaderClasses.put(type, loadeds);
        }
        //noinspection unchecked
        return (Set) loaderClasses.get(type);
    }

    <T extends Comparable<T>> Set<T> getLoader(Class<T> loaderType, ServiceLoader<T> serviceLoader);

    <T> Set<T> getLoader(Class<T> loaderType, @SuppressWarnings("unused") boolean dontInject, ServiceLoader<T> serviceLoader);

    boolean isBuildingInjector();

    /**
     * Registers a module for scanning when filtering is enabled
     *
     * @param javaModuleName The name in the module-info.java file
     * @return This instance
     */
    @SuppressWarnings("unchecked")
    static void registerModule(String javaModuleName) {
        registerModuleForScanning.add(javaModuleName);
        instance()
                .getConfig()
                .setIncludeModuleAndJars(true);
    }

    /**
     * Adds a guice module to the injector for processing
     *
     * @param module
     */
    static void registerModule(com.google.inject.Module module) {
        modules.add(module);
    }

    Set<IGuicePreDestroy> loadPreDestroyServices();

    Set<IGuicePreStartup> loadPreStartupServices();
}
