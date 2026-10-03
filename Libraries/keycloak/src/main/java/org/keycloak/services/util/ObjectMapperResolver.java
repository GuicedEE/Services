/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.services.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Any class with package org.jboss.resteasy.skeleton.key will use NON_DEFAULT inclusion
 * <p>
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle. Upstream
 * Keycloak registers {@code com.fasterxml.jackson.datatype.jdk8.StreamSerializer} to teach
 * Jackson 2 about {@link java.util.stream.Stream}. Jackson 3 serialises {@code Stream}
 * (and the rest of the jdk8/java.time datatypes) out of the box, so the extra module is gone.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
    protected ObjectMapper mapper;

    public ObjectMapperResolver() {
        mapper = ObjectMapperInitializer.OBJECT_MAPPER;
    }

    public static ObjectMapper createStreamSerializer() {
        JsonMapper.Builder builder = JsonMapper.builder()
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL));

        if (Boolean.parseBoolean(System.getProperty("keycloak.jsonPrettyPrint", "false"))) {
            builder.enable(SerializationFeature.INDENT_OUTPUT);
        }

        // allow to discover jackson mappers on the classpath
        if (Boolean.parseBoolean(System.getProperty("keycloak.jsonEnableJacksonModuleDiscovery", "true"))) {
            builder.findAndAddModules();
        }

        return builder.build();
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }

    private static class ObjectMapperInitializer {

        private static final ObjectMapper OBJECT_MAPPER = createStreamSerializer();
    }
}

