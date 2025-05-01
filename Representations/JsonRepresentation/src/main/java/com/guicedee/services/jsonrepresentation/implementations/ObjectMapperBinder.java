package com.guicedee.services.jsonrepresentation.implementations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import com.guicedee.guicedinjection.interfaces.ObjectBinderKeys;
import com.guicedee.services.jsonrepresentation.json.LaxJsonModule;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.java.Log;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;
import static com.guicedee.guicedinjection.interfaces.ObjectBinderKeys.DefaultObjectMapper;
import static com.guicedee.guicedinjection.interfaces.ObjectBinderKeys.JavaScriptObjectWriter;

@Log
public class ObjectMapperBinder
        extends AbstractModule
        implements IGuiceModule<ObjectMapperBinder>
{

    /**
     * If the object mapper must behave as a singleton
     */
    public static boolean singleton = true;

    @Getter
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Getter
    private static final ObjectMapper javaScriptObjectMapper = new ObjectMapper();

    static
    {
        //IJsonRepresentation.configureObjectMapper(objectMapper);
        javaScriptObjectMapper
                .registerModule(new LaxJsonModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
                .configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false)
                .enable(ALLOW_UNQUOTED_CONTROL_CHARS)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);

        objectMapper.registerModule(new LaxJsonModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
                .enable(ALLOW_UNQUOTED_CONTROL_CHARS)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);

    }

    /**
     * Applies a default set of configurations for jackson mapper
     * @param mapper The mapper to configure
     */


    /**
     * Method onBind ...
     */
    @SuppressWarnings("deprecation")
    @Override
    public void configure()
    {
        log.config("Bound ObjectMapper (DefaultObjectMapper) as singleton [" + singleton + "]");
        var p = (Provider<ObjectMapper>) () -> objectMapper;
        if (singleton)
        {
            bind(DefaultObjectMapper)
                    .toProvider(p)
                    .in(Singleton.class);
        }
        else
        {
            bind(DefaultObjectMapper)
                    .toProvider(p);
        }

        log.fine("Bound ObjectWriter.class @Named(JSON)");

        bind(ObjectBinderKeys.JSONObjectWriter)
                .toProvider(() ->
                        objectMapper
                                .writerWithDefaultPrettyPrinter()
                                .with(SerializationFeature.INDENT_OUTPUT)
                                .with(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                                .with(JsonGenerator.Feature.QUOTE_FIELD_NAMES)
                                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                .withoutFeatures(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS));

        bind(ObjectBinderKeys.JSONObjectWriterTiny)
                .toProvider(() ->
                        objectMapper
                                .writer()
                                .without(SerializationFeature.INDENT_OUTPUT)
                                .with(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                                .with(JsonGenerator.Feature.QUOTE_FIELD_NAMES)
                                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                .withoutFeatures(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS));

        bind(ObjectBinderKeys.JSONObjectReader)
                .toProvider(() ->
                        objectMapper
                                .reader()
                                .with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                                .with(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                );

        log.fine("Bound ObjectWriter.class @Named(JavaScriptObjectReader)");
        bind(ObjectBinderKeys.JavascriptObjectMapper)
                .toInstance(javaScriptObjectMapper);


        bind(JavaScriptObjectWriter)
                .toProvider(() ->
                        javaScriptObjectMapper
                                .writerWithDefaultPrettyPrinter()
                                .with(SerializationFeature.INDENT_OUTPUT)
                                .with(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                                .without(JsonGenerator.Feature.QUOTE_FIELD_NAMES)
                                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                .withoutFeatures(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS));

        bind(ObjectBinderKeys.JavaScriptObjectWriterTiny)
                .toProvider(() ->
                        javaScriptObjectMapper
                                .writer()
                                .without(SerializationFeature.INDENT_OUTPUT)
                                .with(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
                                .without(JsonGenerator.Feature.QUOTE_FIELD_NAMES)
                                .without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                                .withoutFeatures(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS));

        bind(ObjectBinderKeys.JavaScriptObjectReader)
                .toProvider(() ->
                        javaScriptObjectMapper
                                .reader()
                                .with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                                .with(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                );
    }
}
