package com.skiff.common.nacos.transfer;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.alibaba.nacos.client.naming.NacosNamingService;
import org.springframework.cloud.client.ServiceInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;


import static com.alibaba.nacos.api.PropertyKeyConst.NAMESPACE;

public class TransferNacosServiceDiscovery extends NacosServiceDiscovery {

    private final NacosDiscoveryProperties discoveryProperties;

    private final NacosServiceManager nacosServiceManager;

    private final NacosDiscoveryTransferProperties nacosDiscoveryTransferProperties;

    public TransferNacosServiceDiscovery(NacosDiscoveryProperties discoveryProperties, NacosServiceManager nacosServiceManager, NacosDiscoveryTransferProperties nacosDiscoveryTransferProperties) {
        super(discoveryProperties, nacosServiceManager);
        this.discoveryProperties = discoveryProperties;
        this.nacosServiceManager = nacosServiceManager;
        this.nacosDiscoveryTransferProperties = nacosDiscoveryTransferProperties;
    }

    /**
     * Return all instances for the given service.
     *
     * @param serviceId id of service
     * @return list of instances
     * @throws NacosException nacosException
     */
    public List<ServiceInstance> getInstances(String serviceId) throws NacosException {
        String group = discoveryProperties.getGroup();
        List<NacosDiscoveryTransferProperties.Transfer> transfers = nacosDiscoveryTransferProperties.getTransfers();
        boolean match = transfers.stream().anyMatch(x -> x.getServiceId().equals(serviceId));
        if (match) {
            NacosDiscoveryTransferProperties.Transfer transfer = transfers.stream().filter(x -> Objects.equals(x.getServiceId(), serviceId)).toList().getFirst();
            Properties nacosProperties = discoveryProperties.getNacosProperties();
            nacosProperties.put(NAMESPACE, transfer.namespace);
            NamingService namingService = new NacosNamingService(nacosProperties);
            List<Instance> instances = namingService.selectInstances(serviceId, transfer.group,
                    true);
            return hostToServiceInstanceList(instances, serviceId);
        }
        List<Instance> instances = namingService().selectInstances(serviceId, group,
                true);
        return hostToServiceInstanceList(instances, serviceId);
    }

    /**
     * Return the names of all services.
     *
     * @return list of service names
     * @throws NacosException nacosException
     */
    public List<String> getServices() throws NacosException {
        String group = discoveryProperties.getGroup();
        ListView<String> services = namingService().getServicesOfServer(1,
                Integer.MAX_VALUE, group);
        return services.getData();
    }

    public static List<ServiceInstance> hostToServiceInstanceList(
            List<Instance> instances, String serviceId) {
        List<ServiceInstance> result = new ArrayList<>(instances.size());
        for (Instance instance : instances) {
            ServiceInstance serviceInstance = hostToServiceInstance(instance, serviceId);
            if (serviceInstance != null) {
                result.add(serviceInstance);
            }
        }
        return result;
    }

    public static ServiceInstance hostToServiceInstance(Instance instance,
                                                        String serviceId) {
        if (instance == null || !instance.isEnabled() || !instance.isHealthy()) {
            return null;
        }
        NacosServiceInstance nacosServiceInstance = new NacosServiceInstance();
        nacosServiceInstance.setHost(instance.getIp());
        nacosServiceInstance.setPort(instance.getPort());
        nacosServiceInstance.setServiceId(serviceId);
        nacosServiceInstance.setInstanceId(instance.getInstanceId());

        Map<String, String> metadata = new HashMap<>();
        metadata.put("nacos.instanceId", instance.getInstanceId());
        metadata.put("nacos.weight", instance.getWeight() + "");
        metadata.put("nacos.healthy", instance.isHealthy() + "");
        metadata.put("nacos.cluster", instance.getClusterName() + "");
        if (instance.getMetadata() != null) {
            metadata.putAll(instance.getMetadata());
        }
        metadata.put("nacos.ephemeral", String.valueOf(instance.isEphemeral()));
        nacosServiceInstance.setMetadata(metadata);

        if (metadata.containsKey("secure")) {
            boolean secure = Boolean.parseBoolean(metadata.get("secure"));
            nacosServiceInstance.setSecure(secure);
        }
        return nacosServiceInstance;
    }

    private NamingService namingService() {
        return nacosServiceManager.getNamingService();
    }


}
