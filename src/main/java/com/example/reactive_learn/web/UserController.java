package com.example.reactive_learn.web;

import com.example.reactive_learn.entity.User;
import com.example.reactive_learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Mono<User> addUser(User user) {
        return userService.save(user);
    }

    /**
     * 流式增加用户
     * @param userFlux
     * @return
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> streamAddUser(@RequestBody Flux<User> userFlux) {
        return userService.streamAddUser(userFlux).then();
    }

    @DeleteMapping(value = "/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return userService.deleteByUsername(username);
    }

    @GetMapping(value = "/{username}")
    public Mono<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> getAllUsers() {
        return userService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> getAllUser() {
        return userService.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<User> findAllUser() {
        return userService.findAll();
    }


}
