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

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

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
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoOnCancel(
                Flux.fromArray(array)
                        .log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void monoDoOnSubscribe() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoOnSubscribe(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();

    }

    @Test
    public void fluxDoOnSubscribe() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoOnSubscribe(
                Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }


    @Test
    public void monoOnRequest() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoOnRequest(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();

    }

    @Test
    public void fluxOnRequest() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxOnRequest(
                Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void monoDoOnTerminate() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoOnTerminate(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();
    }

    @Test
    public void fluxDoOnTerminate() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoOnTerminate(Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }


    @Test
    public void monoDoAfterTerminate() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoAfterTerminate(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();
    }

    @Test
    public void fluxDoAfterTerminate() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoAfterTerminate(Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void monoDoOnEach() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoOnEach(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();

    }

    @Test
    public void fluxDoOnEach() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoOnEach(Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }


    @Test
    public void monoDoFinally() {
        Mono<String> mono = Mono.just("one");
        mono = part03Read.monoDoFinally(mono).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();
    }

    @Test
    public void fluxDoFinally() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part03Read.fluxDoFinally(Flux.fromArray(array).log());
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();

    }


}