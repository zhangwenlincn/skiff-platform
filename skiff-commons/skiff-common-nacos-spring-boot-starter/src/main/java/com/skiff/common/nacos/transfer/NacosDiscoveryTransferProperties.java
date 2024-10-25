package com.skiff.common.nacos.transfer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "skiff.transfer")
public class NacosDiscoveryTransferProperties {

    private boolean enabled;

    private List<Transfer> transfers;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }


    public static class Transfer{
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

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }
    }
}
