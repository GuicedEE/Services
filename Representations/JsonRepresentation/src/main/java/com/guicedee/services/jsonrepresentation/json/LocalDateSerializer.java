package com.guicedee.services.jsonrepresentation.json;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer
        extends JsonSerializer<LocalDate> {
    public static String LocalDateFormat = "yyyy-MM-dd";

    public LocalDateSerializer() {
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(convert(value));
    }
    
    public String convert(LocalDate value)
    {
        if (value == null)
        {
            return null;
        }
        return value.format(DateTimeFormatter.ofPattern(LocalDateFormat));
    }
}
