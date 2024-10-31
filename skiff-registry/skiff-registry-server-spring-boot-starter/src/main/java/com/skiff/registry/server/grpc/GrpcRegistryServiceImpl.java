package com.skiff.registry.server.grpc;

import com.google.protobuf.ByteString;
import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.RegistryServiceGrpc;
import com.skiff.protobuf.registry.RpcRegistry;
import com.skiff.registry.server.service.RegisteredService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GrpcRegistryServiceImpl extends RegistryServiceGrpc.RegistryServiceImplBase {

    private final static Logger log = LoggerFactory.getLogger(GrpcRegistryServiceImpl.class);

    private final RegisteredService registeredService;

    public GrpcRegistryServiceImpl(RegisteredService registeredService) {
        this.registeredService = registeredService;
    }


    @Override
    public void register(RpcRegistry.RegistryRequest request,
                         StreamObserver<RpcResult.Result> responseObserver) {
        log.debug("register service name:{}, ip:{}, port:{}", request.getServiceName(), request.getIp(), request.getPort());
        registeredService.registry(request);
        RpcResult.Result result = RpcResult.Result.newBuilder().
                setCode(BaseCodeEnum.SUCCESS.getCode())
                .setMessage("success")
                .setData(ByteString.copyFromUtf8("register success"))
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
