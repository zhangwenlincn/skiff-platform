package com.skiff.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiff.common.core.enums.BaseCodeEnum;
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


    public static String toJson(Object obj) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.SERIALIZATION_FAIL, e);
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

    public static Map<String, Object> toMap(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new SkiffException(BaseCodeEnum.DESERIALIZATION_FAIL, e);
        }
    }
}
