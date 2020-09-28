package com.guicedee.services.hibernate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.Properties;

public class StringToPropertiesDeserializer extends JsonDeserializer<Properties> {

    @Override
    public Properties deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Properties props = new Properties();
        JsonNode node = p.readValueAsTree();
        node.fields().forEachRemaining(a->{
            ArrayNode node1 = (ArrayNode) a.getValue();
            node1.elements().forEachRemaining(b->{
                String key = b.findValue("name").asText();
                String val = b.findValue("value").asText();
                props.put(key, val);
            });
        });
        return props;
    }
}
