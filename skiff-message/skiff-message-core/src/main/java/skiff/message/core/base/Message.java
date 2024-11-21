package skiff.message.core.base;

/**
 * Message接口
 */
public interface Message {

    /**
     * 获取消息ID
     *
     * @return 消息ID
     */
    String getId();

    /**
     * 获取消息内容
     *
     * @return 消息内容
     */
    String getMessage();

    /**
     * 执行时间戳
     *
     * @return 执行时间
     */
    Long getExecuteTimestamp();

    /**
     * 获取消息重试次数
     *
     * @return 消息重试次数
     */
    Integer getRetryTimes();
}
