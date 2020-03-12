package com.aygames.meter.service

import com.aygames.meter.UserServiceGrpc
import com.aygames.meter.UserServiceOuterClass
import com.aygames.meter.converter.UserRequestToUserConverter
import com.aygames.meter.converter.UserToResponseConverter
import com.aygames.meter.repository.UserRepository
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.slf4j.LoggerFactory

@GRpcService
class UserService(
    private val userRepository: UserRepository,
    private val requestToUserConverter: UserRequestToUserConverter,
    private val userToResponseConverter: UserToResponseConverter
) : UserServiceGrpc.UserServiceImplBase() {

    private val log = LoggerFactory.getLogger(UserService::class.java)

    override fun start(
        request: UserServiceOuterClass.UserRequest,
        responseObserver: StreamObserver<UserServiceOuterClass.UserResponse>
    ) {
        userRepository.validateUser(requestToUserConverter.convert(request))
            .subscribe {
                responseObserver.onNext(userToResponseConverter.convert(it))
                responseObserver.onCompleted()
            }
    }
}
