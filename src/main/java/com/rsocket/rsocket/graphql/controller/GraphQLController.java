package com.rsocket.rsocket.graphql.controller;

import com.rsocket.rsocket.graphql.model.Greeting;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Controller
public class GraphQLController {
    @QueryMapping
    public Greeting greeting() {
        return new Greeting("Hello Welcome to the world");
    }

    @SubscriptionMapping
    Flux<Greeting> greetings() {
        return Flux.fromStream(Stream.generate(new Supplier<Greeting>() {
            @Override
            public Greeting get() {
                return new Greeting("Changing tim,e stamp" + LocalDateTime.now());
            }
        })).delayElements(Duration.ofSeconds(3)).take(10);
    }

    @SubscriptionMapping
    Flux<Greeting> greetingsId(@Argument String id) {
        return Flux.fromStream(Stream.generate(() -> new Greeting("Changing tim,e stamp [ " + id + " ]" + LocalDateTime.now()))

        ).delayElements(Duration.ofSeconds(3)).take(10);
    }
}
