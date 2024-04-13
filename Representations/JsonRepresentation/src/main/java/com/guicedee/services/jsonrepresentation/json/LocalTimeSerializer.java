package com.guicedee.services.jsonrepresentation.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeSerializer
		extends JsonSerializer<LocalTime>
{
	public static final DateTimeFormatter[] formats = new DateTimeFormatter[]{
			DateTimeFormatter.ofPattern("HHmmss"),
			DateTimeFormatter.ofPattern("HH:mm:ss"),
			DateTimeFormatter.ofPattern("HH:mm:ss.SSS"),
			DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS"),
			DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS"),
			DateTimeFormatter.ofPattern("HHmm"),
	};
	
	public LocalTimeSerializer()
	{
	}
	
	@Override
	public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider provider) throws IOException
	{
		generator.writeString(convert(value));
	}
	
	public String convert(LocalTime value)
	{
		if (value == null)
		{
			return null;
		}
		return value.format(DateTimeFormatter.ofPattern("HHmmss"));
	}
}
