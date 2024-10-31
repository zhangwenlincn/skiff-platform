package com.skiff.registry.client.properties;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.IpUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "skiff.registry.client")
public class RegistryClientProperties {

    private static final Logger log = LoggerFactory.getLogger(RegistryClientProperties.class);

    private String serviceName;

    private String ip;

    private Integer port = 50051;

    private Registry registry = new Registry();

    public String getIp() {
        if (ip != null) {
            return ip;
        }
        try {
            return IpUtil.getInetAddress().getHostAddress();
        } catch (Exception e) {
            log.error("get ip error", e);
            throw new SkiffException(BaseCodeEnum.NET_IP_FAIL, e);
        }
    }

    @Data
    public static class Registry {
        private String ip = "127.0.0.1";
        private Integer port = 50050;
    }
}
