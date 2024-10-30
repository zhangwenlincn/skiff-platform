package com.skiff.registry.server.grpc;

import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.CallbackServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class CallbackServiceImpl {


    public RpcResult.Result callback(RpcCallback.CallbackRequest callbackRequest, RpcRegistry.RegistryRequest registryRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(registryRequest.getIp(), registryRequest.getPort())
                .usePlaintext()
                .build();
        CallbackServiceGrpc.CallbackServiceBlockingStub callbackStub = CallbackServiceGrpc.newBlockingStub(channel);
        return callbackStub.callback(callbackRequest);
    }

}
