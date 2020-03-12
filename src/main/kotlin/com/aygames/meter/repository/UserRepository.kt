package com.aygames.meter.repository

import com.aygames.meter.model.User
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class UserRepository(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {

    private val log = LoggerFactory.getLogger(UserRepository::class.java)

    fun validateUser(user: User): Mono<User> =
        reactiveMongoTemplate.findAndModify(
                Query.query(Criteria.where("email").isEqualTo(user.email)),
                Update()
                    .set("email", user.email)
                    .set("name", user.name),
                FindAndModifyOptions.options().upsert(true),
                User::class.java
            )
            .doOnError { log.warn("error during validating user", it) }
}
