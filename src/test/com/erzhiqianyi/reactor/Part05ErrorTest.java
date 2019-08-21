package com.erzhiqianyi.reactor;

import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class Part05ErrorTest {

    private Part05Error part05Error;

    @Before
    public void init() {
        part05Error = new Part05Error();
    }

    @Test
    public void monoError() {
        Mono<String> mono = part05Error.monoError().log();
        StepVerifier.create(mono)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void fluxError() {
        Flux<String> flux = part05Error.fluxError().log();
        StepVerifier.create(flux)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void monoErrorSupplier() {
        Mono<String> mono = part05Error.monoErrorSupplier().log();
        StepVerifier.create(mono)
                .expectError(IllegalStateException.class)
                .verify();

    }

    @Test
    public void fluxErrorSupplier() {
        Flux<String> flux = part05Error.fluxErrorSupplier().log();
        StepVerifier.create(flux)
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void monoOnErrorReturn() {
        Mono<String> mono = part05Error.monoErrorSupplier().log();
        StepVerifier.create(part05Error.monoOnErrorReturn(mono))
                .expectNext("error")
                .verifyComplete();
    }

    @Test
    public void fluxOnErrorReturn() {
        Flux<String> flux = part05Error.fluxErrorSupplier().log();
        StepVerifier.create(part05Error.fluxOnErrorReturn(flux))
                .expectNext("error")
                .verifyComplete();

    }

    @Test
    public void monoOnErrorResume() {
        Mono<String> mono = part05Error.monoErrorSupplier().log();
        StepVerifier.create(part05Error.monoOnErrorResume(mono))
                .expectNext("error")
                .verifyComplete();

    }

    @Test
    public void fluxOnErrorResume() {
        Flux<String> flux = part05Error.fluxErrorSupplier().log();
        StepVerifier.create(part05Error.fluxOnErrorResume(flux))
                .expectNext("error")
                .verifyComplete();

    }

    @Test
    public void monoOnErrorMap() {
        Mono<String> mono = Mono.just("one").map(item -> item.substring(0, 10));
        StepVerifier.create(part05Error.monoOnErrorMap(mono))
                .expectError(Exception.class)
                .verify();

    }

    @Test
    public void fluxOnErrorMap() {
        Flux<String> flux = Flux.just("one", "two", "three");
        flux = part05Error.fluxOnErrorMap(flux).map(item -> item.substring(0, 10));
        StepVerifier.create(flux)
                .expectError(Exception.class)
                .verify();
    }

    @Test
    public void monoTimeout() {
        Mono<String> mono = Mono.delay(Duration.ofSeconds(10)).flatMap(item -> Mono.just("foo"));
        mono = part05Error.monoTimeout(mono).log();
        StepVerifier.create(mono)
                .expectError(TimeoutException.class)
                .verify();

    }

    @Test
    public void fluxTimeout() {
        Flux<String> flux = Flux.just("one", "two", "three", "three-three")
                .flatMap(item -> Mono.delay(Duration.ofSeconds(item.length()))
                        .flatMap(sub -> Mono.just("foo")));
        flux = part05Error.fluxTimeout(flux).log();
        StepVerifier.create(flux)
                .expectNext("foo", "foo", "foo")
                .expectError(TimeoutException.class)
                .verify();

    }

    @Test
    public void monoDoFinally() {
        Mono<String> mono = Mono.just("foo");
        mono = part05Error.monoDoFinally(mono).log();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void fluxDoFinally() {
        Flux<String> flux = Flux.just("one", "two");
        flux = part05Error.fluxDoFinally(flux).log();
        StepVerifier.create(flux)
                .expectNext("one", "two")
                .verifyComplete();
    }


    @Test
    public void monoRetry() {
        Mono<String> mono  = Mono.just("one");
        mono = part05Error.monoRetry(mono);
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();

    }

    @Test
    public void fluxRetry() {
        Flux<String> flux = Flux.just("one", "two");
        flux = part05Error.fluxRetry(flux).log();
        StepVerifier.create(flux)
                .expectNext("one", "two")
                .verifyComplete();
    }

    @Test
    public void monoRetryWhen() {
    }

    @Test
    public void fluxRetryWhen() {
    }

    @Test
    public void fluxConcatError() {
    }

    @Test
    public void monoThenError() {
    }
}