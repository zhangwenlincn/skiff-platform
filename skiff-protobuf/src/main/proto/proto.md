```shell
admin@DESKTOP-6TH5UF9 MINGW64 /d/JetBrains/grpc
$ ./protoc.exe  --java_out=./  --grpc-java_out=./ --plugin=protoc-gen-grpc-java=./protoc-grpc-java-1.68.0.exe    MessageService.proto

admin@DESKTOP-6TH5UF9 MINGW64 /d/JetBrains/grpc
$ ./protoc.exe  --java_out=./     Message.proto

admin@DESKTOP-6TH5UF9 MINGW64 /d/JetBrains/grpc
$ ./protoc.exe  --java_out=./     RpcResult.proto

admin@DESKTOP-6TH5UF9 MINGW64 /d/JetBrains/grpc

```