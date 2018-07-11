package com.example.reactive_learn.service;

import com.example.reactive_learn.dao.UserRepository;
import com.example.reactive_learn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> save(User user) {
        return userRepository.save(user)
                .onErrorResume(e->userRepository.findUserByUsername(user.getUsername()).flatMap(originUser->{
                            user.setId(originUser.getId());
                            return userRepository.save(user);
                        }
                       ));

    }

    public Flux<User> streamAddUser(Flux<User> userFlux) {
        return userRepository.insert(userFlux)
                .onErrorReturn(User.builder().username("tom").build());
    }

    public Mono<Long> deleteByUsername(String username) {
        return userRepository.deleteUserByUsername(username);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Flux<User> findAll() {
        return userRepository.findAll().log("reactor.Flux.Map", Level.ALL,SignalType.ON_NEXT);
    }
}
