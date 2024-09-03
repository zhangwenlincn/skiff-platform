package com.skiff.common.message.context;

import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.message.core.Actuator;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageActuatorHelper {
    private static final ConcurrentMap<String, Actuator> executes = new ConcurrentHashMap<>();

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageActuatorHelper.class);

    public static void add(String key, Actuator actuator) {
        executes.put(key, actuator);
    }

    public static Actuator get(String key) {
        return executes.computeIfAbsent(key, k -> {
            logger.error("Actuator not found for key: {}", k);
            throw new SkiffException("Actuator not found for key: " + k);
        });
    }

    public static boolean exits(String key) {
        return executes.containsKey(key);
    }


}
