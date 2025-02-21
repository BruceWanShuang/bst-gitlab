package com.bst.gitlab.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Log4j2
public class JsonMapperHelper extends ObjectMapper {
    private static JsonMapperHelper mapper;

    public JsonMapperHelper() {
        this(Include.NON_NULL);
    }

    public JsonMapperHelper(Include include) {
        if (include != null) {
            this.setSerializationInclusion(include);
        }

        this.enableSimple();
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        this.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true) ;
        this.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.registerModule((new SimpleModule()).addSerializer(String.class, new JsonSerializer<String>() {
            public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                jgen.writeString(StringEscapeUtils.unescapeHtml4(value));
            }
        }));
        this.setTimeZone(TimeZone.getDefault());
    }

    public static JsonMapperHelper getInstance() {
        if (mapper == null) {
            mapper = (new JsonMapperHelper()).enableSimple();
        }

        return mapper;
    }

    public static JsonMapperHelper nonDefaultMapper() {
        if (mapper == null) {
            mapper = new JsonMapperHelper(Include.NON_DEFAULT);
        }

        return mapper;
    }

    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException var3) {
            log.warn("write to json string error:" + object, var3);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.readValue(jsonString, clazz);
            } catch (IOException var4) {
                log.warn("parse json string error:" + jsonString, var4);
                return null;
            }
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.readValue(jsonString, javaType);
            } catch (IOException var4) {
                log.warn("parse json string error:" + jsonString, var4);
                return null;
            }
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> valueTypeRef) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        } else {
            try {
                return this.readValue(jsonString, valueTypeRef);
            } catch (IOException var4) {
                log.warn("parse json string error:" + jsonString, var4);
                return null;
            }
        }
    }

    public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public <T> T update(String jsonString, T object) {
        try {
            return this.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException var4) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", var4);
            return null;
        } catch (IOException var5) {
            log.warn("update json string:" + jsonString + " to object:" + object + " error.", var5);
            return null;
        }
    }

    public String toJsonP(String functionName, Object object) {
        return this.toJson(new JSONPObject(functionName, object));
    }

    public JsonMapperHelper enableEnumUseToString() {
        this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        return this;
    }

    public JsonMapperHelper enableSimple() {
        this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    public ObjectMapper getMapper() {
        return this;
    }

    public static String toJsonString(Object object) {
        return getInstance().toJson(object);
    }

    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        return getInstance().fromJson(jsonString, clazz);
    }

    public static <T> T convertToObject(Object obj, Class<T> clazz) {
        String json = toJsonString(obj);
        return fromJsonString(json, clazz);
    }

    public static <T> List<T> convertToList(Object obj, Class<T> clazz) {
        String json = toJsonString(obj);
        JavaType javaType = getInstance().createCollectionType(ArrayList.class, clazz);
        List<T> result = getInstance().fromJson(json, javaType);
        return result;
    }

}
