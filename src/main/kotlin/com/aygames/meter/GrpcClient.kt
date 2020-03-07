package com.aygames.meter

import io.grpc.ManagedChannelBuilder

object GrpcClient {
    @JvmStatic
    fun main(args: Array<String>) {
        val channel = ManagedChannelBuilder.forAddress("localhost", 6565)
            .usePlaintext()
            .build()
        val stub = HelloServiceGrpc.newBlockingStub(channel)
        val helloResponse = stub.hello(
            HelloServiceOuterClass.HelloRequest.newBuilder()
                .setFirstName("Meter")
                .setLastName("Big")
                .build()
        )
        channel.shutdown()

        println(helloResponse.greeting)
    }
}
