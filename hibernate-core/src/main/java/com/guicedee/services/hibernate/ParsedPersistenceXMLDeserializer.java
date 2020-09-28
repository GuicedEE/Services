package com.guicedee.services.hibernate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.hibernate.jpa.boot.internal.ParsedPersistenceXmlDescriptor;

import java.io.IOException;

public class ParsedPersistenceXMLDeserializer extends JsonDeserializer<ParsedPersistenceXmlDescriptor> {
    @Override
    public ParsedPersistenceXmlDescriptor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ParsedPersistenceXmlDescriptor pers = new ParsedPersistenceXmlDescriptor(null);
        return pers;
    }
}
