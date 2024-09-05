package com.skiff.common.message.helper;

import com.skiff.common.message.core.MessageTimeout;
import org.slf4j.Logger;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageTimeoutHelper {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MessageTimeoutHelper.class);

    private static final ConcurrentMap<String, MessageTimeout> executes = new ConcurrentHashMap<>();

    /**
     * Add a timeout for the given key.
     *
     * @param key     message key.
     * @param timeout the timeout to add.
     */
    public static void add(String key, MessageTimeout timeout) {
        executes.put(key, timeout);
    }

    /**
     * Get the timeout for the given key.
     *
     * @param key message key.
     * @return the timeout for the given key.
     */
    public static MessageTimeout get(String key) {
        return executes.get(key);
    }

    /**
     * Cancel the timeout for the given key.
     *
     * @param key message key.
     */
    public static void cancel(String key) {
        executes.get(key).getTimeout().cancel();
    }

    /**
     * Remove all expired or cancelled timeouts from the map.
     */
    public static void remove() {
        Set<String> keys = executes.keySet();
        for (String key : keys) {
            if (executes.get(key).getTimestamp() + 1000 * 10 < System.currentTimeMillis()
                    && (executes.get(key).getTimeout().isCancelled() || executes.get(key).getTimeout().isExpired())) {
                logger.debug("Removing expired cancelled timeout 1000 * 10  for key: " + key);
                executes.remove(key);
            }
        }
    }

    /**
     * Get the effective keys in the map.
     *
     * @return the effective keys in the map.
     */
    public static Set<String> effective() {
        remove();
        return executes.keySet();
    }

}
