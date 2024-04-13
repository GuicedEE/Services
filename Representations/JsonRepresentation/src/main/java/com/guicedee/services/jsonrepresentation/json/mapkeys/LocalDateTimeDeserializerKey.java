package com.guicedee.services.jsonrepresentation.json.mapkeys;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.guicedee.services.jsonrepresentation.json.LocalDateTimeDeserializer;

import java.io.IOException;

public class LocalDateTimeDeserializerKey
		extends KeyDeserializer
{
	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException
	{
		return new LocalDateTimeDeserializer().convert(key);
	}
}
