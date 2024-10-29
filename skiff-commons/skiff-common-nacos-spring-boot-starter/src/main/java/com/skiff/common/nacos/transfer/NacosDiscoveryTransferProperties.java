package com.skiff.common.nacos.transfer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "skiff.transfer.openfeign")
public class NacosDiscoveryTransferProperties {

    private boolean enabled;

    private List<Transfer> transfers;


    @Setter
    @Getter
    public static class Transfer {
        /**
         * 服务名
         */
        String serviceId;
        /**
         * 漂移group
         */
        String group;
        /**
         * 漂移namespace
         */
        String namespace;
    }
}
