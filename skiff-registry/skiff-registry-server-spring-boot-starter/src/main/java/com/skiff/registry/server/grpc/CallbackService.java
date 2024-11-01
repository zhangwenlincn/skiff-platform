package com.skiff.registry.server.grpc;

import com.skiff.common.core.exception.SkiffException;
import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.CallbackServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.bean.RegisteredCallback;
import com.skiff.registry.server.bean.RegisteredServer;
import com.skiff.registry.server.service.RegisteredService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CallbackService {


    private static final Logger log = LoggerFactory.getLogger(CallbackService.class);
    private final RegisteredService registeredService;

    public CallbackService(RegisteredService registeredService) {
        this.registeredService = registeredService;
    }


    public RpcResult.Result callback(RpcCallback.CallbackRequest callbackRequest, RpcRegistry.RegistryRequest registryRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(registryRequest.getIp(), registryRequest.getPort())
                .usePlaintext()
                .build();
        CallbackServiceGrpc.CallbackServiceBlockingStub callbackStub = CallbackServiceGrpc.newBlockingStub(channel);
        return callbackStub.callback(callbackRequest);
    }

    public void callback(String serviceName, String bean, String params) {
        RegisteredCallback callback = getCallback(serviceName, bean, params);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(callback.getIp(), callback.getPort())
                .usePlaintext()
                .build();
        CallbackServiceGrpc.CallbackServiceBlockingStub callbackStub = CallbackServiceGrpc.newBlockingStub(channel);
        RpcResult.Result result = callbackStub.callback(getCallback(callback));
        log.info("callback code:{}, message:{}", result.getCode(), result.getMessage());
    }

    public RpcCallback.CallbackRequest getCallback(RegisteredCallback callback) {
        return RpcCallback.CallbackRequest.newBuilder()
                .setServiceName(callback.getServiceName())
                .setBean(callback.getBean())
                .setParams(callback.getParams())
                .build();
    }

    public RegisteredCallback getCallback(String serviceName, String bean, String params) {
        RegisteredServer registeredServer = Optional.ofNullable(registeredService.getRegistered(serviceName)).orElseThrow(() -> new SkiffException("service not exits"));
        RegisteredCallback callback = new RegisteredCallback();
        callback.setServiceName(registeredServer.getServiceName());
        String address = registeredServer.getAddress().stream().findAny().orElseThrow(() -> new SkiffException("callback not exits address"));
        String[] ipPort = address.split(":");
        callback.setIp(ipPort[0]);
        callback.setPort(Integer.parseInt(ipPort[1]));
        callback.setBean(bean);
        callback.setParams(params);
        return callback;
    }

}
