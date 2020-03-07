package com.aygames.meter.service

import com.aygames.meter.HelloServiceGrpc
import com.aygames.meter.HelloServiceOuterClass
import io.grpc.stub.StreamObserver

class HelloServiceImpl : HelloServiceGrpc.HelloServiceImplBase() {
    override fun hello(
        request: HelloServiceOuterClass.HelloRequest,
        responseObserver: StreamObserver<HelloServiceOuterClass.HelloResponse?>
    ) {
        val greeting: String = StringBuilder()
            .append("Hello, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString()
        val response: HelloServiceOuterClass.HelloResponse = HelloServiceOuterClass.HelloResponse.newBuilder()
            .setGreeting(greeting)
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}
