package com.skiff.common.message.helper;

import io.netty.util.Timeout;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MessageTimeoutHelper {
    private static final ConcurrentMap<String, Timeout> executes = new ConcurrentHashMap<>();

    /**
     * Add a timeout for the given key.
     *
     * @param key     message key.
     * @param timeout the timeout to add.
     */
    public static void add(String key, Timeout timeout) {
        executes.put(key, timeout);
    }

    /**
     * Get the timeout for the given key.
     *
     * @param key message key.
     * @return the timeout for the given key.
     */
    public static Timeout get(String key) {
        return executes.get(key);
    }

    /**
     * Cancel the timeout for the given key.
     *
     * @param key message key.
     */
    public static void cancel(String key) {
        executes.get(key).cancel();
    }

    /**
     * Remove all expired or cancelled timeouts from the map.
     */
    public static void remove() {
        executes.entrySet().removeIf(entry -> entry.getValue().isExpired() || entry.getValue().isCancelled());
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
