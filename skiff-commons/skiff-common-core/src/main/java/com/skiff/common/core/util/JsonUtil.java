package com.skiff.common.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skiff.common.core.enums.JacksonEnum;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static ObjectMapper getObjectMapper() {
        return JacksonEnum.INSTANCE.getInstance().getObjectMapper();
    }

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> clazz) {
        return getObjectMapper().readValue(json, clazz);
    }

    @SneakyThrows
    public static String toJson(Object obj) {
        return getObjectMapper().writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> List<T> toList(String json) {
        return getObjectMapper().readValue(json, new TypeReference<>() {
        });
    }

    @SneakyThrows
    public static Map<String, Object> toMap(String json) {
        return getObjectMapper().readValue(json, new TypeReference<>() {
        });
    }
}
