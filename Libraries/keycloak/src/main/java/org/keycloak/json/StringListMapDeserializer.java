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

package org.keycloak.json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle.
 * {@code JsonDeserializer} became {@link ValueDeserializer}, {@code JsonNode.fields()} became
 * {@code properties()} and {@code ArrayNode.elements()} became {@code values()}.
 */
public class StringListMapDeserializer extends ValueDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode jsonNode = deserializationContext.readTree(jsonParser);
        Map<String, List<String>> map = new HashMap<>();
        for (Map.Entry<String, JsonNode> e : jsonNode.properties()) {
            List<String> values = new LinkedList<>();
            if (!e.getValue().isArray()) {
                values.add(e.getValue().isNull() ? null : e.getValue().asString());
            } else {
                for (JsonNode node : e.getValue().values()) {
                    values.add(node.isNull() ? null : node.asString());
                }
            }
            map.put(e.getKey(), values);
        }
        return map;
    }

}

