package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.*;

public class Part01CreateInstanceTest {
    Part01CreateInstance part01CreateInstance;

    @Before
    public void init() {
        part01CreateInstance = new Part01CreateInstance();
    }

    @Test
    public void fooMono() {
        Mono<String> mono = part01CreateInstance.fooMono();
        StepVerifier.create(mono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void fooOptionalMono() {
    }

    @Test
    public void fooOptionalMonoEmpty() {
    }

    @Test
    public void fooJustEmptyMono() {
    }

    @Test
    public void fooJustEmptyMonoEmpty() {
    }

    @Test
    public void fooMonoSupplier() {
    }

    @Test
    public void justFlux() {
    }

    @Test
    public void fluxFromArray() {
    }

    @Test
    public void fluxFromIterable() {
    }

    @Test
    public void fluxRange() {
    }

    @Test
    public void fluxFromStream() {
    }

    @Test
    public void monoFromCallable() {
    }

    @Test
    public void monoFromRunnable() {
    }

    @Test
    public void monoFromFuture() {
    }

    @Test
    public void emptyMono() {
    }

    @Test
    public void emptyFlux() {
    }

    @Test
    public void errorMono() {
    }

    @Test
    public void errorFlux() {
    }

    @Test
    public void monoWithNoSignal() {
    }

    @Test
    public void fluxWithNoSignal() {
    }

    @Test
    public void fooMonoDefer() {
    }

    @Test
    public void fooFluxDefer() {
    }
}