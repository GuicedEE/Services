package com.guicedee.services.jsonrepresentation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.guicedee.services.jsonrepresentation.implementations.ObjectMapperBinder;
import com.guicedee.services.jsonrepresentation.json.LaxJsonModule;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS;

@SuppressWarnings("unused")
public interface IJsonRepresentation<J> extends Serializable
{
    static void configureObjectMapper(ObjectMapper mapper)
    {
        mapper.registerModule(new LaxJsonModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true)
                .configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature(), true)
                .configure(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature(), true)
                .enable(ALLOW_UNQUOTED_CONTROL_CHARS)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
    }

    /**
     * Serializes this object as JSON
     *
     * @return The rendered JSON or an empty string
     */
    default String toJson()
    {
        return toJson(false);
    }

    /**
     * Serializes this object as JSON
     *
     * @return The rendered JSON or an empty string
     */
    default String toJson(boolean tiny)
    {
        ObjectMapper objectMapper = ObjectMapperBinder.getObjectMapper();
        try
        {
            if (tiny)
            {
                return objectMapper.disable(SerializationFeature.INDENT_OUTPUT)
                        .writeValueAsString(this);
            } else
            {
                return objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(this);
            }
        } catch (JsonProcessingException e)
        {
            throw new JsonRenderException("Unable to serialize as JSON", e);
        }
    }

    /**
     * Deserializes this object from a JSON String (updates the current object)
     *
     * @param json The JSON String
     * @return This object updated
     */
    default J fromJson(String json)
    {
        ObjectMapper objectMapper = ObjectMapperBinder.getObjectMapper();
        try
        {
            return objectMapper.readerForUpdating(this)
                    .readValue(json);
        } catch (IOException e)
        {
            throw new JsonRenderException("Unable to serialize as JSON", e);
        }
    }

    /**
     * Deserializes this object from a JSON String (updates the current object)
     *
     * @param json The JSON String
     * @return This object updated
     */
    @SuppressWarnings({"UnusedReturnValue"})
    default List<J> fromJsonArray(String json)
    {
        ObjectMapper objectMapper = ObjectMapperBinder.getObjectMapper();
        try
        {
            return objectMapper.readerFor(new TypeReference<List<J>>()
                    {
                    })
                    .readValue(json);
        } catch (IOException e)
        {
            throw new JsonRenderException("Unable to serialize as JSON", e);
        }
    }

    /**
     * Deserializes this object from a JSON String (updates the current object)
     *
     * @param json The JSON String
     * @return This object updated
     */
    @SuppressWarnings({"UnusedReturnValue"})
    default Set<J> fromJsonArrayUnique(String json, @SuppressWarnings("unused")
    Class<J> type)
    {
        ObjectMapper objectMapper = ObjectMapperBinder.getObjectMapper();
        try
        {
            return objectMapper.readerFor(new TypeReference<TreeSet<J>>()
                    {
                    })
                    .readValue(json);
        } catch (IOException e)
        {
            throw new JsonRenderException("Unable to serialize as JSON", e);
        }
    }

    /**
     * Read direct from the stream
     *
     * @param <T>
     * @param file  the stream
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> T From(InputStream file, Class<T> clazz) throws IOException
    {
        return getJsonObjectReader().forType(clazz)
                .readValue(file);
    }


    /**
     * Read from a file
     *
     * @param <T>
     * @param file
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> T From(File file, Class<T> clazz) throws IOException
    {
        return getJsonObjectReader().forType(clazz)
                .readValue(file);
    }

    /**
     * Read from a reader
     *
     * @param <T>
     * @param file
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> T From(Reader file, Class<T> clazz) throws IOException
    {
        return getJsonObjectReader().forType(clazz)
                .readValue(file);
    }

    /**
     * Returns the mapped object mapper
     *
     * @return
     */
    static ObjectMapper getObjectMapper()
    {
        return ObjectMapperBinder.getObjectMapper();
    }

    static ObjectReader getJsonObjectReader()
    {
        return ObjectMapperBinder.getObjectMapper().reader()
                .with(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                .with(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * Read from a content string
     *
     * @param <T>
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> T From(String content, Class<T> clazz) throws IOException
    {
        return getJsonObjectReader().forType(clazz)
                .readValue(content);
    }

    /**
     * Read from a URL
     *
     * @param <T>
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> T From(URL content, Class<T> clazz) throws IOException
    {
        return getJsonObjectReader().forType(clazz)
                .readValue(content);
    }


    /**
     * Read direct from the stream
     *
     * @param <T>
     * @param file  the stream
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> List<T> fromToList(InputStream file, Class<T> clazz)
    {
        T list = null;
        try
        {
            list = ObjectMapperBinder.getObjectMapper()
                    .reader()
                    .forType(clazz)
                    .readValue(file);
        } catch (IOException e)
        {
            throw new JsonRenderException("Unable to read the input stream ", e);
        }
        ArrayList<T> lists = new ArrayList<>();
        lists.addAll(Arrays.asList((T[]) list));
        return lists;
    }

    /**
     * Read from a URL
     *
     * @param <T>
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> List<T> fromToList(URL content, Class<T> clazz) throws IOException
    {
        T list = ObjectMapperBinder.getObjectMapper()
                .reader()
                .forType(clazz)
                .readValue(content);
        ArrayList<T> lists = new ArrayList<>();
        lists.addAll(Arrays.asList((T[]) list));
        return lists;
    }

    /**
     * Read from a file
     *
     * @param <T>
     * @param file
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> List<T> fromToList(File file, Class<T> clazz) throws IOException
    {
        T list = ObjectMapperBinder.getObjectMapper()
                .reader()
                .forType(clazz)
                .readValue(file);
        ArrayList<T> lists = new ArrayList<>();
        lists.addAll(Arrays.asList((T[]) list));
        return lists;
    }

    /**
     * Read from a reader
     *
     * @param <T>
     * @param file
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> List<T> fromToList(Reader file, Class<T> clazz) throws IOException
    {
        T list = ObjectMapperBinder.getObjectMapper()
                .reader()
                .forType(clazz)
                .readValue(file);
        ArrayList<T> lists = new ArrayList<>();
        lists.addAll(Arrays.asList((T[]) list));
        return lists;
    }

    /**
     * Read from a content string
     *
     * @param <T>
     * @param content
     * @param clazz
     * @return
     * @throws IOException
     */
    static <T> List<T> fromToList(String content, Class<T> clazz) throws IOException
    {
        T list = ObjectMapperBinder.getObjectMapper()
                .reader()
                .forType(clazz)
                .readValue(content);
        ArrayList<T> lists = new ArrayList<>();
        lists.addAll(Arrays.asList((T[]) list));
        return lists;
    }

}
