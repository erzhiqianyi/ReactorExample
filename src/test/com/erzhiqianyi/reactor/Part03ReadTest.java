package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Part03ReadTest {

    private Part03Read part03Read;

    @Before
    public void init(){
        part03Read = new Part03Read() ;
    }

    @Test
    public void doOnNext() {
        String[] array = {"one","two","three","four","five"};
        Flux<String> flux = part03Read.doOnNext(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }


}