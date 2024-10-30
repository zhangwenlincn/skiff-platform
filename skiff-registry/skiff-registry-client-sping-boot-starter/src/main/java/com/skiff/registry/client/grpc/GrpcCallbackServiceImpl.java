package com.skiff.registry.client.grpc;

import com.google.protobuf.ByteString;
import com.skiff.common.core.code.BaseCodeEnum;
import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.CallbackServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GrpcCallbackServiceImpl extends CallbackServiceGrpc.CallbackServiceImplBase {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(GrpcCallbackServiceImpl.class);

    @Override
    public void callback(RpcCallback.CallbackRequest request, StreamObserver<RpcResult.Result> responseObserver) {
        log.debug("callback service name：{}, bean:{}, params:{}", request.getServiceName(), request.getBean(), request.getParams());
        RpcResult.Result result = RpcResult.Result.newBuilder().
                setCode(BaseCodeEnum.SUCCESS.getCode())
                .setMessage("success")
                .setData(ByteString.copyFromUtf8("register success"))
                .build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }
}
