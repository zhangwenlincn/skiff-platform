package skiff.message.core.register;


import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import skiff.message.core.annotation.MessageActuator;
import skiff.message.core.base.Actuator;
import skiff.message.core.helper.MessageActuatorHelper;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class DefaultMessageActuatorRegister implements MessageActuatorRegister {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageActuatorRegister.class);

    @Override
    public void register(String... packages) {
        Arrays.stream(packages).forEach(pack -> {
            try {
                List<Class<?>> classes = ClassUtil.getClasses(pack);
                // 遍历所有类，找到实现MessageActuator接口的实现类
                for (Class<?> clazz : classes) {
                    if (clazz.isAnnotationPresent(MessageActuator.class)) {
                        MessageActuator messageActuator = clazz.getAnnotation(MessageActuator.class);
                        String key = messageActuator.group() + ":" + messageActuator.topic();
                        if (!MessageActuatorHelper.exits(key)) {
                            Actuator actuator = (Actuator) clazz.getDeclaredConstructor().newInstance();
                            MessageActuatorHelper.add(key, actuator);
                        } else {
                            logger.error("Message actuator {} already exists", key);
                            throw new SkiffException("Message actuator " + key + " already exists");
                        }

                    }
                }
            } catch (Exception e) {
                logger.error("Failed to register message actuators in package {} ", pack, e);
                throw new SkiffException("Failed to register message actuators in package " + pack, e);
            }
        });
    }


}
