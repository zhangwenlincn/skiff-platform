package com.skiff.registry.server.content;

import com.skiff.protobuf.registry.RpcRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MapRegistryContent implements RegistryContent {

    private static final Logger log = LoggerFactory.getLogger(MapRegistryContent.class);
    private final Map<String, List<RpcRegistry.RegistryRequest>> registryMap = new ConcurrentHashMap<>();

    @Override
    public void registry(RpcRegistry.RegistryRequest request) {
        log.debug("map registry content: serviceName:{},ip:{},port:{}", request.getServiceName(), request.getIp(), request.getIp());
        boolean exits = registryMap.containsKey(request.getServiceName());
        if (!exits) {
            registryMap.put(request.getServiceName(), new ArrayList<>(Collections.singletonList(request)));
        } else {
            List<RpcRegistry.RegistryRequest> registryRequests = registryMap.get(request.getServiceName());
            boolean exitsExample = registryRequests.stream().anyMatch(x -> Objects.equals(x.getIp(), request.getIp()) && Objects.equals(x.getPort(), request.getPort()));
            if (!exitsExample) {
                registryRequests.add(request);
                registryMap.put(request.getServiceName(), registryRequests);
            }
        }
    }

    @Override
    public List<RpcRegistry.RegistryRequest> getRegistryServiceList(String serviceName) {
        return Optional.ofNullable(registryMap.get(serviceName)).orElse(new ArrayList<>());
    }
}
