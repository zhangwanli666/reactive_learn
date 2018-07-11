package com.example.reactive_learn.support;

import com.example.reactive_learn.entity.MyEvent;
import com.example.reactive_learn.entity.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class WebFluxClient {

    @Test
    public void webFluxClientTest1() throws InterruptedException {

        Flux<String> userFlux = WebClient.create("http://localhost:8080")
                .get().uri("/hello").retrieve().bodyToFlux(String.class);
        userFlux.subscribe(user -> System.out.println(user));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test2() {
        User user = WebClient.create("http://localhost:8080")
                .get().uri("/user")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(User.class))
                .doOnNext(System.out::println)
                .blockLast();
        System.out.println(user);
    }

    @Test
    public void test3() {
        WebClient.create("http://localhost:8080")
                .get().uri("/times")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(String.class))
                .doOnNext(System.out::println)
//                .log()
                .take(10)
                .blockLast();
    }

    @Test
    public void test4() {
        //10个元素的用户流
        Flux<User> userFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> User.builder().username("john" + new Random().nextInt(100))
                        .email("1571699158@qq.com").phone("1576897768").build()).take(10);
        WebClient.create("http://localhost:8080")
                .post().uri("/user")
                .contentType(MediaType.APPLICATION_STREAM_JSON) //注意媒体类型
                .body(userFlux,User.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

    /**
     * 流式json
     */
    @Test
    public void webClientTest4() {
        Flux<MyEvent> eventFlux = Flux.interval(Duration.ofSeconds(1))
                .map(l -> new MyEvent(System.currentTimeMillis(), "message-" + l)).take(5); // 1
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient
                .post().uri("/events")
                .contentType(MediaType.APPLICATION_STREAM_JSON) // 2
                .body(eventFlux, MyEvent.class) // 3
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * 客户端接收SSE事件
     */
    @Test
    public void test5() {
        WebClient.create("http://localhost:8080")
                .get().uri("/times")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .log()
                .take(10)
                .blockLast();

    }
}
