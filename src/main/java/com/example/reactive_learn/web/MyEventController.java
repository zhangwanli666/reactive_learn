package com.example.reactive_learn.web;

import com.example.reactive_learn.dao.MyEventRepository;
import com.example.reactive_learn.entity.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/events")
public class MyEventController {

    @Autowired
    private MyEventRepository eventRepository;

    /**
     * 接收流式json
     * @param eventFlux
     * @return
     */
    @PostMapping(path = "",consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> addEvents(@RequestBody Flux<MyEvent> eventFlux) {

        return eventRepository.insert(eventFlux).then();
    }

    /**
     * 产生流式json
     * @return
     */
    @GetMapping(path = "", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MyEvent> getEvents() {
        return eventRepository.findBy();
    }




}
