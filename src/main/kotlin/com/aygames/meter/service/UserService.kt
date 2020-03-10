package com.aygames.meter.service

import com.aygames.meter.UserServiceGrpc
import com.aygames.meter.UserServiceOuterClass
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UserService : UserServiceGrpc.UserServiceImplBase() {

    override fun start(
        request: UserServiceOuterClass.UserRequest,
        responseObserver: StreamObserver<UserServiceOuterClass.UserResponse>
    ) {
        responseObserver.onNext(UserServiceOuterClass.UserResponse.getDefaultInstance())
    }
}
