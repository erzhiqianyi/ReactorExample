package com.erzhiqianyi.reactor;

import com.erzhiqianyi.reactor.domain.ResourceLoader;
import com.erzhiqianyi.reactor.domain.User;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Part01CreateInstanceTest {
    Part01CreateInstance part01CreateInstance;

    @Before
    public void init() {
        Hooks.onOperatorDebug();
        part01CreateInstance = new Part01CreateInstance();
    }

    @Test
    public void fooMono() {
        Mono<String> mono = part01CreateInstance.justMono().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void justFlux() {
        Flux<String> flux = part01CreateInstance.justFlux().log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();

    }


    @Test
    public void fooOptionalMono() {
        Mono<String> mono = part01CreateInstance.fooOptionalMono().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void fooOptionalMonoEmpty() {
        Mono<String> mono = part01CreateInstance.fooOptionalMonoEmpty().log();
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void fooJustEmptyMono() {
        Mono<String> mono = part01CreateInstance.fooJustEmptyMono().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void fooJustEmptyMonoEmpty() {
        Mono<String> mono = part01CreateInstance.fooJustEmptyMonoEmpty().log();
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void fooMonoSupplier() {
        Mono<String> mono = part01CreateInstance.fooMonoSupplier().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }


    @Test
    public void fluxFromArray() {
        Flux<String> flux = part01CreateInstance.fluxFromArray().log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();

    }

    @Test
    public void fluxFromIterable() {
        Flux<String> flux = part01CreateInstance.fluxFromIterable().log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();

    }

    @Test
    public void fluxRange() {
        Flux<Integer> flux = part01CreateInstance.fluxRange().log();
        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .verifyComplete();
    }

    @Test
    public void fluxFromStream() {
        Flux<String> flux = part01CreateInstance.fluxFromStream().log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    public void monoFromCallable() {
        Mono<String> mono = part01CreateInstance.monoFromCallable().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void monoFromRunnable() {
        Mono<String> mono = part01CreateInstance.monoFromRunnable().log();
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void monoFromFuture() {
        Mono<String> mono = part01CreateInstance.monoFromFuture().log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();

    }

    @Test
    public void emptyMono() {
        Mono<String> mono = part01CreateInstance.emptyMono().log();
        StepVerifier.create(mono)
                .verifyComplete();

    }

    @Test
    public void emptyFlux() {
        Flux<String> flux = part01CreateInstance.emptyFlux().log();
        StepVerifier.create(flux)
                .verifyComplete();
    }

    @Test
    public void errorMono() {
        Mono<String> mono = part01CreateInstance.errorMono().log();
        StepVerifier.create(mono)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void errorFlux() {
        Flux<String> flux = part01CreateInstance.errorFlux().log();
        StepVerifier.create(flux)
                .expectError(IllegalStateException.class)
                .verify();

    }

    @Test
    public void monoWithNoSignal() {
        Mono<String> mono = part01CreateInstance.monoWithNoSignal().log();
        StepVerifier
                .create(mono)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

    @Test
    public void fluxWithNoSignal() {
        Flux<String> flux = part01CreateInstance.fluxWithNoSignal().log();
        StepVerifier
                .create(flux)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(1))
                .thenCancel()
                .verify();
    }

    @Test
    public void fooMonoDefer() {
        Mono<String> mono = part01CreateInstance.fooMonoDefer().log();
        StepVerifier
                .create(mono)
                .expectNext("foo")
                .verifyComplete();

    }

    @Test
    public void fooFluxDefer() {
        Flux<String> flux = part01CreateInstance.fooFluxDefer().log();
        StepVerifier
                .create(flux)
                .expectNext("one", "two", "three")
                .verifyComplete();

    }

    @Test
    public void fooMonoUsing() {
        ResourceLoader resourceLoader = new ResourceLoader();
        Mono<User> userMono = part01CreateInstance.fooMonoUsing().log();
        StepVerifier
                .create(userMono)
                .expectNext(resourceLoader.getFirst())
                .verifyComplete();
    }

    @Test
    public void fooFluxUsing() {
        Flux<User> userMono = part01CreateInstance.fooFluxUsing().log();
        StepVerifier
                .create(userMono)
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .verifyComplete();

    }

    @Test
    public void fooFluxGenerate() {
        Flux<Integer> flux = part01CreateInstance.generate().log();
        flux.subscribe();

    }

    @Test
    public void fooMonoCreate() {
        Mono<String> mono = part01CreateInstance.fooMonoCreate().log();
        StepVerifier
                .create(mono)
                .expectNext("foo")
                .verifyComplete();

    }

    @Test
    public void fooFluxCreate() {
        Flux<String> flux = part01CreateInstance.fooFluxCreate().log();
        StepVerifier
                .create(flux)
                .expectNext("one","two","three")
                .verifyComplete();

    }

    @Test
    public void just() throws InterruptedException {
        Mono<Long> clock = Mono.just(System.currentTimeMillis());
        clock.log().subscribe(System.out::println);
        //time == t0

        Thread.sleep(1_000);
        //time == t10
        clock.log().subscribe(System.out::println);
        clock.block(); //we use block for demonstration purposes, returns t0

        clock.subscribe(System.out::println);
        Thread.sleep(2_000);
        //time == t17
        clock.log().subscribe(System.out::println);

        clock.block(); //we re-subscribe to clock, still returns t0
        clock.log().subscribe(System.out::println);

    }

    @Test
    public void defer() throws InterruptedException {
        Mono<Long> clock = Mono.defer(() -> Mono.just(System.currentTimeMillis()));
        //time == t0

        clock.subscribe(System.out::println);
        Thread.sleep(1_000);
        //time == t10
        clock.block(); //invoked currentTimeMillis() here and returns t10
        clock.log().subscribe(System.out::println);

        Thread.sleep(2_000);
        //time == t17
        clock.block(); //invoke currentTimeMillis() once again here and returns t17
        clock.log().subscribe(System.out::println);
    }
}