package com.skiff.registrt.client.grpc;

import com.skiff.protobuf.callback.RpcCallback;
import com.skiff.protobuf.core.RpcResult;
import com.skiff.protobuf.grpc.CallbackServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;

public class GrpcCallbackServiceImpl extends CallbackServiceGrpc.CallbackServiceImplBase {
    private final static Logger log = org.slf4j.LoggerFactory.getLogger(GrpcCallbackServiceImpl.class);

    @Override
    public void callback(RpcCallback.CallbackRequest request, StreamObserver<RpcResult.Result> responseObserver) {
        log.debug("callback service name：{}， bean：{}， params：{}", request.getServiceName(), request.getBean(), request.getParams());
    }
}
