/*
 * Adapted from commercetools JVM SDK 20.4.0 (Apache-2.0).
 * GuicedEE modification: share identical Reflections scans within one mapper
 * construction. Public SDK behavior and service loading remain unchanged.
 * Upstream: https://github.com/commercetools/commercetools-sdk-java-v2/tree/20.4.0
 */

package io.vrap.rmf.base.client.utils.json.modules;

import java.util.Set;

import io.vrap.rmf.base.client.utils.json.SubType;

import io.vrap.rmf.base.client.utils.json.ModelDiscovery;

import tools.jackson.databind.jsontype.NamedType;
import tools.jackson.databind.module.SimpleModule;

public class SubTypeModule extends SimpleModule {
    public SubTypeModule(final String prefix) {

        Set<Class<?>> subtypes = ModelDiscovery.annotatedTypes(prefix, SubType.class);

        for (Class<?> subType : subtypes) {
            SubType annotation = subType.getAnnotation(SubType.class);
            if (annotation != null) {
                String typeName = annotation.value();
                registerSubtypes(new NamedType(subType, typeName));
            }
        }
    }
}
