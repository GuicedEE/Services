package com.guicedee.services.jsonrepresentation.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;

import java.io.IOException;

/**
 * Converts most of the string knowns to boolean
 */
public class StringToIntegerRelaxed extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return convert(value);
    }

    public Integer convert(  String value)
    {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        value = value.trim();
        double d = Double.parseDouble(value);
        return (int) d;
    }
}
