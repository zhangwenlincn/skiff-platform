```shell
 ./protoc.exe  --java_out=./  --grpc-java_out=./ --plugin=protoc-gen-grpc-java=./protoc-grpc-java-1.68.0.exe    MessageService.proto

 ./protoc.exe  --java_out=./     Message.proto

 ./protoc.exe  --java_out=./     RpcResult.proto
```