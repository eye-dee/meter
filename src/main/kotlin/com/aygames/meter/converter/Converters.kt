package com.aygames.meter.converter

import com.aygames.meter.UserServiceOuterClass
import com.aygames.meter.model.User
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class UserRequestToUserConverter : Converter<UserServiceOuterClass.UserRequest, User> {

    override fun convert(request: UserServiceOuterClass.UserRequest): User =
        User(
            email = request.email,
            name = request.name
        )

}

@Component
class UserToResponseConverter : Converter<User, UserServiceOuterClass.UserResponse> {

    override fun convert(source: User): UserServiceOuterClass.UserResponse =
        UserServiceOuterClass.UserResponse.newBuilder()
            .setStatus("Success")
            .build()

}
