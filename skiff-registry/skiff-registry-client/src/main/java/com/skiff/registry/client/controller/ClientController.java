package com.skiff.registry.client.controller;

import com.skiff.common.core.result.BaseResult;
import com.skiff.common.core.result.Results;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.client.grpc.RegistryServiceImpl;
import com.skiff.registry.client.properties.RegistryClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final RegistryClientProperties registryClientProperties;

    private final RegistryServiceImpl registryServiceImpl;

    public ClientController(RegistryClientProperties registryClientProperties
            , RegistryServiceImpl registryServiceImpl) {
        this.registryClientProperties = registryClientProperties;
        this.registryServiceImpl = registryServiceImpl;
    }

    @GetMapping(value = "/register")
    public BaseResult register() {
        RpcRegistry.RegistryRequest request = RpcRegistry.RegistryRequest.newBuilder()
                .setServiceName("registry-client")
                .setIp("127.0.0.1")
                .setPort(registryClientProperties.getPort())
                .build();

        RpcResult.Result registry = registryServiceImpl.registry(request);

        return Results.baseResult(registry.getCode(), registry.getMessage());
    }

}

