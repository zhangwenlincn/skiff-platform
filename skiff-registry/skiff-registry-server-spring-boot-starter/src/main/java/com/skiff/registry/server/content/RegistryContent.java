package com.skiff.registry.server.content;

import com.skiff.protobuf.registry.RpcRegistry;

import java.util.List;

public interface RegistryContent {

    void registry(RpcRegistry.RegistryRequest request);

    List<RpcRegistry.RegistryRequest> getRegistryServiceList(String serviceName);
}
