package com.guicedee.services.jsonrepresentation.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;

import java.io.IOException;

/**
 * Converts most of the string knowns to boolean
 */
public class StringToIntRelaxed
		extends JsonDeserializer
{
	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
	{
		String value = p.getValueAsString();
		return convert(value);
	}

	public int convert( String value)
	{
		if (Strings.isNullOrEmpty(value))
		{
			return 0;
		}
		value = value.trim();
		double d = Double.parseDouble(value);
		return (int) d;
	}
}
