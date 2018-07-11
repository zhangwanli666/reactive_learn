package com.example.reactive_learn.dao;

import com.example.reactive_learn.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,String> {

    Mono<User> findUserByUsername(String username);

    Mono<Long> deleteUserByUsername(String username);
}
