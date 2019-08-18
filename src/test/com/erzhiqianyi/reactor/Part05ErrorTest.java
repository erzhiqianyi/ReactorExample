package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
    }

    @Test
    public void fluxOnErrorMap() {
    }

    @Test
    public void monoTimeout() {
    }

    @Test
    public void fluxTimeout() {
    }

    @Test
    public void monoRetry() {
    }

    @Test
    public void fluxRetry() {
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