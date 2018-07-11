package com.example.reactive_learn;

import com.example.reactive_learn.entity.MyEvent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableMongoRepositories
public class ReactiveLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveLearnApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(MongoOperations mongoOperations) {

        return (String... args)->{
            mongoOperations.dropCollection(MyEvent.class);
            mongoOperations.createCollection(MyEvent.class, CollectionOptions.empty().maxDocuments(200).size(100000).capped());
        };
    }
}
