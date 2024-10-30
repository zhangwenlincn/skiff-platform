~~~shell
 ../bin/protoc.exe  --java_out=../src/main/java/     Result.proto
 ../bin/protoc.exe  --java_out=../src/main/java/    Registry.proto
 ../bin/protoc.exe  --java_out=../src/main/java/    Callback.proto
 ../bin/protoc.exe  --java_out=../src/main/java/  --grpc-java_out=../src/main/java/  --plugin=protoc-gen-grpc-java=../bin/protoc-grpc-java-1.68.0.exe    RegistryGrpc.proto
 ../bin/protoc.exe  --java_out=../src/main/java/  --grpc-java_out=../src/main/java/  --plugin=protoc-gen-grpc-java=../bin/protoc-grpc-java-1.68.0.exe    CallbackGrpc.proto
~~~