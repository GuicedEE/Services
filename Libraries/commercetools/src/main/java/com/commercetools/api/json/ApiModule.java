/*
 * Adapted from commercetools JVM SDK 20.4.0 (Apache-2.0).
 * GuicedEE modification: share identical Reflections scans within one mapper
 * construction. Public SDK behavior and service loading remain unchanged.
 * Upstream: https://github.com/commercetools/commercetools-sdk-java-v2/tree/20.4.0
 */

package com.commercetools.api.json;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.commercetools.api.RegisterSimpleModuleMixin;
import com.commercetools.api.SimpleModuleMixin;

import io.vrap.rmf.base.client.utils.json.modules.ModuleOptions;

import io.vrap.rmf.base.client.utils.json.ModelDiscovery;

import tools.jackson.databind.module.SimpleModule;

/**
 * Module to configure the default jackson {@link tools.jackson.databind.ObjectMapper} e.g. to deserialize attributes and custom fields
 */
public class ApiModule extends SimpleModule {
    private static final long serialVersionUID = 0L;

    public ApiModule(ModuleOptions options) {
        Set<Class<?>> modules = ModelDiscovery.annotatedTypes("com.commercetools", RegisterSimpleModuleMixin.class);

        for (Class<?> module : modules) {
            if (SimpleModuleMixin.class.isAssignableFrom(module)) {
                SimpleModuleMixin mixin;
                try {
                    mixin = module.asSubclass(SimpleModuleMixin.class).getDeclaredConstructor().newInstance();
                }
                catch (InstantiationException | IllegalAccessException | InvocationTargetException
                        | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                mixin.mixin(this, options);
            }
        }
    }
}
