package com.guicedee.services.jsonrepresentation.json.mapkeys;

import com.fasterxml.jackson.databind.*;
import com.guicedee.services.jsonrepresentation.json.LocalDateDeserializer;


import java.io.*;

public class LocalDateDeserializerKey
		extends KeyDeserializer
{
	@Override
	public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException
	{
		return new LocalDateDeserializer().convert(key);
	}
}
