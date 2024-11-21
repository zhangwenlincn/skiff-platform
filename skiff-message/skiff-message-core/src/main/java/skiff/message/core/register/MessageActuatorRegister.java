package skiff.message.core.register;

/**
 * 消息执行器注册
 */
public interface MessageActuatorRegister {

    void register(String... packages);
}