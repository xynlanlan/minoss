package io.javac.minoss.minosscommon.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Map;

/**
 * Json utilities.
 *
 * @author johnniang
 * @date 3/18/19
 */
@Validated
public class JsonUtils {

    /**
     * Default json mapper.
     */
    public final static ObjectMapper DEFAULT_JSON_MAPPER = createDefaultJsonMapper();

    private JsonUtils() {
    }


    /**
     * Creates a default json mapper.
     *
     * @return object mapper
     */
    public static ObjectMapper createDefaultJsonMapper() {
        return createDefaultJsonMapper(null);
    }

    /**
     * Creates a default json mapper.
     *
     * @param strategy property naming strategy
     * @return object mapper
     */
    @NotNull
    public static ObjectMapper createDefaultJsonMapper(@Nullable PropertyNamingStrategy strategy) {
        // Create object mapper
        ObjectMapper mapper = new ObjectMapper();
        // Configure
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Set property naming strategy
        if (strategy != null) {
            mapper.setPropertyNamingStrategy(strategy);
        }
        return mapper;
    }

    /**
     * Converts json to the object specified type.
     *
     * @param json json content must not be blank
     * @param type object type must not be null
     * @param <T>  target object type
     * @return object specified type
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static <T> T jsonToObject(@NotNull String json, @NotNull Class<T> type) throws IOException {
        return jsonToObject(json, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts json to the object specified type.
     *
     * @param json         json content must not be blank
     * @param type         object type must not be null
     * @param objectMapper object mapper must not be null
     * @param <T>          target object type
     * @return object specified type
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static <T> T jsonToObject(@NotNull String json, @NotNull Class<T> type, @NotNull ObjectMapper objectMapper) throws IOException {
        Assert.hasText(json, "Json content must not be blank");
        Assert.notNull(type, "Target type must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");

        return objectMapper.readValue(json, type);
    }


    /**
     * Converts object to json format.
     *
     * @param source source object must not be null
     * @return json format of the source object
     * @throws JsonProcessingException throws when fail to convert
     */
    @NotNull
    public static String objectToJson(@NotNull Object source) throws JsonProcessingException {
        return objectToJson(source, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts object to json format.
     *
     * @param source       source object must not be null
     * @param objectMapper object mapper must not be null
     * @return json format of the source object
     * @throws JsonProcessingException throws when fail to convert
     */
    @NotNull
    public static String objectToJson(@NotNull Object source, @NotNull ObjectMapper objectMapper) throws JsonProcessingException {
        Assert.notNull(source, "Source object must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");

        return objectMapper.writeValueAsString(source);
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap source map must not be empty
     * @param type      object type must not be null
     * @param <T>       target object type
     * @return the object specified type
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static <T> T mapToObject(@NotNull Map<String, ?> sourceMap, @NotNull Class<T> type) throws IOException {
        return mapToObject(sourceMap, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap    source map must not be empty
     * @param type         object type must not be null
     * @param objectMapper object mapper must not be null
     * @param <T>          target object type
     * @return the object specified type
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static <T> T mapToObject(@NotNull Map<String, ?> sourceMap, @NotNull Class<T> type, @NotNull ObjectMapper objectMapper) throws IOException {
        Assert.notEmpty(sourceMap, "Source map must not be empty");

        // Serialize the map
        String json = objectToJson(sourceMap, objectMapper);

        // Deserialize the json format of the map
        return jsonToObject(json, type, objectMapper);
    }

    /**
     * Converts a source object to a map
     *
     * @param source source object must not be null
     * @return a map
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static Map<?, ?> objectToMap(@NotNull Object source) throws IOException {
        return objectToMap(source, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts a source object to a map
     *
     * @param source       source object must not be null
     * @param objectMapper object mapper must not be null
     * @return a map
     * @throws IOException throws when fail to convert
     */
    @NotNull
    public static Map<?, ?> objectToMap(@NotNull Object source, @NotNull ObjectMapper objectMapper) throws IOException {

        // Serialize the source object
        String json = objectToJson(source, objectMapper);

        // Deserialize the json
        return jsonToObject(json, Map.class, objectMapper);
    }

}