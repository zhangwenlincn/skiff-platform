package com.skiff.registry.server.service;


import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.bean.RegisteredServer;

import java.util.List;

public interface RegisteredService {
    void registry(RpcRegistry.RegistryRequest request);

    /**
     * 获取注册的服务
     *
     * @return 获取注册的服务
     */
    List<RegisteredServer> getRegistered();
    /**
     * 获取指定的注册的服务
     *
     * @return 获取指定的注册的服务
     */
    RegisteredServer getRegistered(String serviceName);

}
