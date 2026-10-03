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

import java.util.ArrayList;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle.
 * {@code JsonDeserializer} became {@link ValueDeserializer} and {@code textValue()} became
 * {@code stringValue(String)} (which, like the Jackson 2 accessor, yields the default for
 * non-textual nodes).
 */
public class StringOrArrayDeserializer extends ValueDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        JsonNode jsonNode = deserializationContext.readTree(jsonParser);
        if (jsonNode.isArray()) {
            ArrayList<String> a = new ArrayList<>(1);
            for (JsonNode node : jsonNode) {
                a.add(node.stringValue(null));
            }
            return a.toArray(new String[0]);
        } else {
            return new String[] { jsonNode.stringValue(null) };
        }
    }

}

