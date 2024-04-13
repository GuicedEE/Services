package com.guicedee.services.jsonrepresentation.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.guicedee.services.jsonrepresentation.json.LocalDateTimeDeserializer.formats;
import static com.guicedee.services.jsonrepresentation.json.StaticStrings.STRING_0;
import static com.guicedee.services.jsonrepresentation.json.StaticStrings.STRING_NULL;


public class LocalTimeDeserializer
		extends JsonDeserializer<LocalTime>
{
	@Override
	public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
	{
		String name = p.getValueAsString();
		return convert(name);
	}
	
	public LocalTime convert(String value)
	{
		if (Strings.isNullOrEmpty(value) || STRING_NULL.equalsIgnoreCase(value) || STRING_0.equals(value))
		{
			return null;
		}
		LocalTime time = null;
		for (DateTimeFormatter format : formats)
		{
			try
			{
				time = LocalTime.parse(value, format);
				if (time != null)
				{
					break;
				}
			}
			catch (DateTimeParseException p)
			{
			
			}
		}
		
		return time;
	}
}
