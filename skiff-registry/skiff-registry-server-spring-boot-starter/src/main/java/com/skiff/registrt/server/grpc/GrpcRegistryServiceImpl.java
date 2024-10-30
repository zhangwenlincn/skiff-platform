package com.skiff.registrt.server.grpc;

import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.RegistryServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrpcRegistryServiceImpl extends RegistryServiceGrpc.RegistryServiceImplBase {

    private final static Logger log = LoggerFactory.getLogger(GrpcRegistryServiceImpl.class);

    @Override
    public void register(RpcRegistry.RegistryRequest request,
                         StreamObserver<RpcResult.Result> responseObserver) {
        log.debug("register service name：{}， ip: {}，port：{}", request.getServiceName(), request.getIp(), request.getPort());
    }
}
