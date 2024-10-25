package com.skiff.common.nacos.transfer;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnDiscoveryEnabled
@ConditionalOnNacosDiscoveryEnabled
@AutoConfigureBefore({NacosDiscoveryAutoConfiguration.class})
@EnableConfigurationProperties(NacosDiscoveryTransferProperties.class)
@ConditionalOnProperty(value = "skiff.transfer.enabled")
public class NacosConfiguration {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NacosConfiguration.class);

    private final NacosDiscoveryTransferProperties nacosDiscoveryTransferProperties;

    public NacosConfiguration(NacosDiscoveryTransferProperties nacosDiscoveryTransferProperties) {
        this.nacosDiscoveryTransferProperties = nacosDiscoveryTransferProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public NacosServiceDiscovery nacosServiceDiscovery(NacosDiscoveryProperties nacosDiscoveryProperties, NacosServiceManager nacosServiceManager) {
        log.info("开启nacos namespace group 漂移功能");
        return new TransferNacosServiceDiscovery(nacosDiscoveryProperties, nacosServiceManager, nacosDiscoveryTransferProperties);
    }
}