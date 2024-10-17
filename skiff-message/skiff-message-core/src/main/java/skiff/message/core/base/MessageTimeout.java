package skiff.message.core.base;

import io.netty.util.Timeout;
import lombok.Data;

@Data
public class MessageTimeout {
    private Long timestamp;
    private Timeout timeout;

    public MessageTimeout(Long timestamp, Timeout timeout) {
        this.timestamp = timestamp;
        this.timeout = timeout;
    }
}
