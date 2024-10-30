package com.skiff.registry.client.grpc;

import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.RegistryServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.client.properties.RegistryClientProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;


@Component
public class RegistryServiceImpl {


    private final RegistryClientProperties registryClientProperties;

    public RegistryServiceImpl(RegistryClientProperties registryClientProperties) {
        this.registryClientProperties = registryClientProperties;
    }

    public RpcResult.Result registry(RpcRegistry.RegistryRequest registryRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(registryClientProperties.getRegistry().getIp(), registryClientProperties.getRegistry().getPort())
                .usePlaintext()
                .build();
        RegistryServiceGrpc.RegistryServiceBlockingStub registryStub = RegistryServiceGrpc.newBlockingStub(channel);
        return registryStub.register(registryRequest);
    }
}
