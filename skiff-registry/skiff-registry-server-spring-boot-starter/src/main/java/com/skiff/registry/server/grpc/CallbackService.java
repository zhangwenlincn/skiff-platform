package com.skiff.registry.server.grpc;

import com.skiff.common.core.exception.SkiffException;
import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.CallbackServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.bean.RegisteredServer;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

@Component
public class CallbackService {


    public RpcResult.Result callback(RpcCallback.CallbackRequest callbackRequest, RpcRegistry.RegistryRequest registryRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(registryRequest.getIp(), registryRequest.getPort())
                .usePlaintext()
                .build();
        CallbackServiceGrpc.CallbackServiceBlockingStub callbackStub = CallbackServiceGrpc.newBlockingStub(channel);
        return callbackStub.callback(callbackRequest);
    }

    public void callback(RegisteredServer registeredServer) {
        String address = registeredServer.getAddress().stream().findAny().orElseThrow(() -> new SkiffException("callback not exits address"));
        String[] ipPort = address.split(":");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ipPort[0], Integer.parseInt(ipPort[1]))
                .usePlaintext()
                .build();
        CallbackServiceGrpc.CallbackServiceBlockingStub callbackStub = CallbackServiceGrpc.newBlockingStub(channel);
        RpcCallback.CallbackRequest callbackRequest = RpcCallback.CallbackRequest.newBuilder().setParams("{hello}").setBean("aa").build();
        RpcResult.Result callback = callbackStub.callback(callbackRequest);
    }

}
