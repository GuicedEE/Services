package org.keycloak.representations.workflows;

import java.util.Map.Entry;

import org.keycloak.common.util.MultivaluedHashMap;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

/**
 * Jackson 3 override supplied by the {@code keycloak-api} compile-only bundle.
 * <p>
 * Upstream extends the raw {@code com.fasterxml.jackson.databind.JsonDeserializer} and reads the
 * tree via {@code JsonParser.getCodec()}. Jackson 3 removed {@code ObjectCodec}, so the tree is
 * read from the {@link DeserializationContext} instead.
 */
public final class MultivaluedHashMapValueDeserializer extends ValueDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        JsonNode node = ctxt.readTree(p);

        if (node.isObject()) {
            for (Entry<String, JsonNode> property : node.properties()) {
                String key = property.getKey();
                JsonNode values = property.getValue();

                if (values.isArray()) {
                    for (JsonNode value : values) {
                        map.add(key, value.asString());
                    }
                } else {
                    map.add(key, values.asString());
                }
            }
        }

        return map;
    }
}

