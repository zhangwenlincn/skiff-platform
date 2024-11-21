package com.skiff.common.core.util;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.skiff.common.core.code.LanguageEnum;

import java.util.HashMap;

public class ThreadLocalUtil {


    public final static String language = "language";

    private static final TransmittableThreadLocal<HashMap<String, Object>> localMap = new TransmittableThreadLocal<>();

    public static void setLanguage(String value) {
        localMap.get().put(language, value);
    }

    public static LanguageEnum getLanguage() {

        LanguageEnum result = LanguageEnum.zh_CN;
        try {
            String value = (String) localMap.get().get(language);
            if (value != null) {
                result = LanguageEnum.valueOf(value);
            }
        } catch (Exception ex) {
            // ignore
        }
        return result;
    }

    public static void set(String key, Object value) {
        localMap.get().put(key, value);
    }

    public static Object get(String key) {
        return localMap.get().get(key);
    }

    public static void remove() {
        localMap.remove();
    }


}
