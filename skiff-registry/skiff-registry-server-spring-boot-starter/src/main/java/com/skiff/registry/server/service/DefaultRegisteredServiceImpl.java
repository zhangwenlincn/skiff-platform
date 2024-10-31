package com.skiff.registry.server.service;

import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.bean.RegisteredServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class DefaultRegisteredServiceImpl implements RegisteredService {

    private final ConcurrentHashMap<String, RegisteredServer> map = new ConcurrentHashMap<>();

    @Override
    public void registry(RpcRegistry.RegistryRequest request) {
        RegisteredServer registered = getRegistered(request);
        RegisteredServer registeredServer = map.getOrDefault(registered.getServiceName(), new RegisteredServer(registered.getServiceName(), new HashSet<>()));
        registeredServer.getAddress().addAll(registered.getAddress());
        map.put(registered.getServiceName(), registeredServer);
    }

    @Override
    public List<RegisteredServer> getRegistered() {
        if (!map.isEmpty()) {
            return new ArrayList<>(map.values());
        }
        return List.of();
    }

    @Override
    public RegisteredServer getRegistered(String serviceName) {
        if (map.containsKey(serviceName)) {
            return map.get(serviceName);
        }
        return null;
    }

    private RegisteredServer getRegistered(RpcRegistry.RegistryRequest request) {
        RegisteredServer registeredServer = new RegisteredServer();
        registeredServer.setServiceName(request.getServiceName());
        String address = request.getIp() + ":" + request.getPort();
        registeredServer.setAddress(new HashSet<>(Collections.singleton(address)));
        return registeredServer;
    }
}
