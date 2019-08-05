package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.test.StepVerifier;

public class Part03ReadTest {

    private Part03Read part03Read;

    @Before
    public void init() {
        part03Read = new Part03Read();
    }

    @Test
    public void doOnNext() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.doOnNext(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void doOnComplete() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.doOnComplete(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void doOnSuccess() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.doOnSuccess(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();
    }

    @Test
    public void monoDoOnError() {
        Mono<String> mono = Mono.just("one")
                .doOnNext(item -> item.substring(1, 100));
        mono = part03Read.monoDoOnError(mono).log();
        StepVerifier.create(mono)
                .expectError(StringIndexOutOfBoundsException.class)
                .verify();
    }

    @Test
    public void fluxDoOnError() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoOnError(Flux.fromArray(array)
                .doOnNext(item -> item.substring(1, 100)))
                .log();
        StepVerifier.create(flux)
                .expectError(StringIndexOutOfBoundsException.class)
                .verify();

    }

    @Test
    public void monoDoOnCancel() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoOnCancel(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();

    }


    @Test
    public void fluxDoOnCancel() {

    }


}