package com.guicedee.services.jsonrepresentation.json.mapkeys;

import com.fasterxml.jackson.databind.*;
import com.guicedee.services.jsonrepresentation.json.OffsetDateTimeDeserializer;

import java.io.*;

public class OffsetDateTimeDeserializerKey
		extends KeyDeserializer
{
	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException
	{
		return new OffsetDateTimeDeserializer().convert(key);
	}
}
