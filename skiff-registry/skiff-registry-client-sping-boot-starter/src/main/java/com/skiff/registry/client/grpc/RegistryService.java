package com.skiff.registry.client.grpc;

import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.RegistryServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.client.properties.RegistryClientProperties;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RegistryService implements InitializingBean {


    private static final Logger log = LoggerFactory.getLogger(RegistryService.class);
    private final RegistryClientProperties registryClientProperties;
    private RpcRegistry.RegistryRequest request;

    public RegistryService(RegistryClientProperties registryClientProperties) {
        this.registryClientProperties = registryClientProperties;
    }

    public RpcResult.Result registry(RpcRegistry.RegistryRequest registryRequest) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(registryClientProperties.getRegistry().getIp(), registryClientProperties.getRegistry().getPort())
                .usePlaintext()
                .build();
        RegistryServiceGrpc.RegistryServiceBlockingStub registryStub = RegistryServiceGrpc.newBlockingStub(channel);
        return registryStub.register(registryRequest);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        // 自动注册
        autoRegistry();
    }

    private void autoRegistry() {
        log.info("auto register start serviceName:{},ip:{},port:{}", registryClientProperties.getClient().getServiceName()
                , registryClientProperties.getClient().getIp()
                , registryClientProperties.getClient().getPort());
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    RpcResult.Result result = registry(getRequest());
                    log.debug("auto register result：{}", result.getMessage());
                } catch (Exception e) {
                    log.error("auto register error", e);
                } finally {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private RpcRegistry.RegistryRequest getRequest() {
        return Optional.ofNullable(request).orElseGet(() -> {
            request = RpcRegistry.RegistryRequest.newBuilder()
                    .setServiceName(registryClientProperties.getClient().getServiceName())
                    .setIp(registryClientProperties.getClient().getIp())
                    .setPort(registryClientProperties.getClient().getPort())
                    .build();
            return request;
        });
    }
}
