package com.guicedee.services.jsonrepresentation.json.mapkeys;

import com.fasterxml.jackson.databind.*;
import com.guicedee.services.jsonrepresentation.json.LocalDateTimeDeserializer;

import java.io.*;

public class LocalDateTimeDeserializerKey
		extends KeyDeserializer
{
	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException
	{
		return new LocalDateTimeDeserializer().convert(key);
	}
}
