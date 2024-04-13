package com.guicedee.services.jsonrepresentation.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

import static com.guicedee.services.jsonrepresentation.json.StaticStrings.*;


public class InstantDeserializer
		extends JsonDeserializer<Instant>
{
	private static final DateTimeFormatter[] formats = new DateTimeFormatter[]
			                                                   {
					                                                   new DateTimeFormatterBuilder().append(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
					                                                                                                          .withLocale(Locale.UK)
					                                                                                                          .withZone(ZoneId.systemDefault()))
					                                                                                 .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1L)
					                                                                                 .parseDefaulting(ChronoField.DAY_OF_MONTH, 1L)
					                                                                                 .parseDefaulting(ChronoField.HOUR_OF_DAY, 0L)
					                                                                                 .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0L)
					                                                                                 .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0L)
					                                                                                 .parseDefaulting(ChronoField.NANO_OF_SECOND, 0L)
							                                                   .toFormatter()
			                                                   };

	@Override
	public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
		String name = p.getValueAsString();
		return convert(name);
	}

	public Instant convert(String value) throws IOException
	{
		if (Strings.isNullOrEmpty(value) || STRING_NULL.equals(value) || STRING_0.equals(value))
		{
			return null;
		}
		if (value.contains(E))
		{
			value = value.replaceAll(STRING_DOT_ESCAPED, STRING_EMPTY)
			             .substring(0, value.indexOf(E) - 1);
		}
		LocalDateTime time = null;
		for (DateTimeFormatter format : formats)
		{
			try
			{
				time = LocalDateTime.parse(value, format);
				break;
			}
			catch (DateTimeParseException dtpe)
			{
				//try the next one
			}
		}
		if (time == null)
		{
			throw new IOException("Unable to determine local date time from string - [" + value + "]");

		}
		return time.atZone(ZoneId.systemDefault())
		           .toInstant();
	}
}
