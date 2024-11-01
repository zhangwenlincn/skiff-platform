package com.skiff.registry.client.properties;

import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.common.core.exception.SkiffException;
import com.skiff.common.core.util.IpUtil;
import com.skiff.common.util.SpringUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Data
@ConfigurationProperties(prefix = "skiff.registry")
public class RegistryClientProperties {

    private static final Logger log = LoggerFactory.getLogger(RegistryClientProperties.class);

    /**
     * 客户端配置
     */
    private client client;
    /**
     * 服务注册中心配置
     */
    private Registry registry;

    public RegistryClientProperties.client getClient() {
        return Optional.ofNullable(client).orElseGet(() -> {
            client = new client();
            return client;
        });
    }

    public Registry getRegistry() {
        return Optional.ofNullable(registry).orElseGet(() -> {
            registry = new Registry();
            return registry;
        });
    }

    @Data
    public static class client {
        private String serviceName;
        private String ip;
        private Integer port = 50051;

        public String getServiceName() {
            return Optional.ofNullable(serviceName).orElseGet(() -> {
                serviceName = SpringUtil.getBean(Environment.class).getProperty("spring.application.name");
                return serviceName;
            });
        }

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
    }

    @Data
    public static class Registry {
        private String ip = "127.0.0.1";
        private Integer port = 50050;
    }
}
