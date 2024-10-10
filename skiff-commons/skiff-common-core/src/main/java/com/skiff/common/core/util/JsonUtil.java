package com.skiff.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.json.JacksonEnum;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static ObjectMapper getObjectMapper() {
        return JacksonEnum.INSTANCE.getInstance().getObjectMapper();
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.DESERIALIZATION_FAIL, e);
        }
    }

    public static Map<String, Object> toMap(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.DESERIALIZATION_FAIL, e);
        }
    }

    public static <T> List<T> toList(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.DESERIALIZATION_FAIL, e);
        }
    }

    public static String toJson(Object obj) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.SERIALIZATION_FAIL, e);
        }
    }

    public static <T> String toJson(T obj, List<String> fields) {
        return toJson(obj, fields, true);
    }

    public static <T> String toJson(T obj, List<String> fields, boolean exclude) {
        String json = JsonUtil.toJson(obj);
        Map<String, Object> map = JsonUtil.toMap(json);
        List<String> excludeFields;
        if (exclude) {
            //移除不在fields中的字段
            excludeFields = map.keySet().stream().filter(x -> !fields.contains(x)).toList();
        } else {
            excludeFields = fields;
        }
        return toJson(filter(map, excludeFields));
    }

    public static <T> Map<String, T> filter(Map<String, T> map, List<String> excludeFields) {
        if (excludeFields != null && !excludeFields.isEmpty()) {
            excludeFields.forEach(map::remove);
        }
        return map;
    }

    public static JsonNode toJsonNode(String json) {
        try {
            return getObjectMapper().readTree(json);
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.DESERIALIZATION_FAIL, e);
        }
    }
}