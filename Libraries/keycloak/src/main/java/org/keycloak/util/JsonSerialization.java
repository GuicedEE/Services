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

package org.keycloak.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.annotation.JsonInclude;

import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

/**
 * Utility class to handle simple JSON serializable for Keycloak.
 * <p>
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle. The upstream
 * Keycloak implementation is built against Jackson 2 ({@code com.fasterxml.jackson.databind})
 * and registers {@code Jdk8Module} / {@code JavaTimeModule}; both of those are folded into
 * {@code tools.jackson.databind} core in Jackson 3 so they are no longer registered here.
 * The mapper is also immutable in Jackson 3, hence the builder based construction.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JsonSerialization {
    public static final ObjectMapper mapper = createObjectMapperWithDefaults();
    public static final ObjectMapper prettyMapper = createPrettyObjectMapperWithDefaults();

    public static ObjectMapper createObjectMapperWithDefaults() {
        return JsonMapper.builder()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
                .build();
    }

    public static ObjectMapper createPrettyObjectMapperWithDefaults() {
        return JsonMapper.builder()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
                .build();
    }

    public static String valueAsString(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String valueAsPrettyString(Object obj) {
        try {
            return prettyMapper.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T valueFromString(String string, Class<T> type) {
        try {
            return mapper.readValue(string, type);
        } catch (JacksonException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void writeValueToStream(OutputStream os, Object obj) throws IOException {
        mapper.writeValue(os, obj);
    }

    public static void writeValuePrettyToStream(OutputStream os, Object obj) throws IOException {
        prettyMapper.writeValue(os, obj);
    }

    public static String writeValueAsPrettyString(Object obj) throws IOException {
        return prettyMapper.writeValueAsString(obj);
    }

    public static String writeValueAsString(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static byte[] writeValueAsBytes(Object obj) throws IOException {
        return mapper.writeValueAsBytes(obj);
    }

    public static JsonNode writeValueAsNode(Object obj) {
        return mapper.valueToTree(obj);
    }

    public static <T> T readValue(byte[] bytes, Class<T> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    public static <T> T readValue(String bytes, Class<T> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    public static <T> T readValue(InputStream bytes, Class<T> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    public static <T> T readValue(byte[] bytes, TypeReference<T> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    public static <T> T readValue(String string, TypeReference<T> type) throws IOException {
        return mapper.readValue(string, type);
    }

    public static <T> T readValue(InputStream bytes, TypeReference<T> type) throws IOException {
        return mapper.readValue(bytes, type);
    }

    /**
     * Creates an {@link ObjectNode} based on the given {@code pojo}, copying all its properties to the resulting {@link ObjectNode}.
     *
     * @param pojo a pojo which properties will be populates into the resulting a {@link ObjectNode}
     * @return a {@link ObjectNode} with all the properties from the given pojo
     * @throws IOException if the resulting a {@link ObjectNode} can not be created
     */
    public static ObjectNode createObjectNode(Object pojo) throws IOException {
        if (pojo == null) {
            throw new IllegalArgumentException("Pojo can not be null.");
        }

        JsonNode jsonNode = mapper.valueToTree(pojo);

        if (!jsonNode.isObject()) {
            throw new RuntimeException("JsonNode [" + jsonNode + "] is not a object.");
        }

        ObjectNode objectNode = createObjectNode();

        objectNode.setAll((ObjectNode) jsonNode);

        return objectNode;
    }

    public static ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

}

