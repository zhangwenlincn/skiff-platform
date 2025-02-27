package skiff.message.core.base;

import com.skiff.common.core.result.BaseResult;

/**
 * 消息处理器接口
 */
public interface Actuator<M extends Message> {
    BaseResult actuate(M message);
}
