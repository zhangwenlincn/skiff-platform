package skiff.message.core.helper;

import com.skiff.common.core.exception.SkiffException;
import org.slf4j.Logger;
import skiff.message.core.base.Actuator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("all")
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
