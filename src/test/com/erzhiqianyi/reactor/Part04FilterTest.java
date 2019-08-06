package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Part04FilterTest {

    private Part04Filter part04Filter;

    @Before
    public void init() {
        part04Filter = new Part04Filter();
    }

    @Test
    public void monoFilter() {
        String foo = "foo";
        Mono<String> mono = Mono.just(foo);
        mono = part04Filter.monoFilter(mono.log());
        StepVerifier.create(mono)
                .expectNext(foo)
                .verifyComplete();
    }

    @Test
    public void fluxFilter() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.filter(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();

    }

    @Test
    public void monoFilterWhen() {
    }

    @Test
    public void fluxFilterWhen() {
    }

    @Test
    public void monoOfType() {
    }

    @Test
    public void fluxOfType() {
    }

    @Test
    public void monoIgnoreElement() {
    }

    @Test
    public void fluxIgnoreElements() {
    }

    @Test
    public void distinct() {
    }

    @Test
    public void distinctUntilChanged() {
    }

    @Test
    public void takeByIndex() {
    }

    @Test
    public void takeDuration() {
    }

    @Test
    public void next() {
    }

    @Test
    public void limitRequest() {
    }

    @Test
    public void takeLast() {
    }

    @Test
    public void takeUtil() {
    }

    @Test
    public void takeUntilOther() {
    }

    @Test
    public void elementAt() {
    }

    @Test
    public void last() {
    }

    @Test
    public void lastDefault() {
    }

    @Test
    public void skipIndex() {
    }

    @Test
    public void skipDuration() {
    }

    @Test
    public void skipLast() {
    }

    @Test
    public void skipUntil() {
    }

    @Test
    public void skipUntilOther() {
    }

    @Test
    public void skipWhile() {
    }

    @Test
    public void sampleDuration() {
    }

    @Test
    public void samplePublisher() {
    }

    @Test
    public void sampleFirst() {
    }

    @Test
    public void sampleTimeout() {
    }

    @Test
    public void single() {
    }

    @Test
    public void singleDefault() {
    }

    @Test
    public void singleOrEmpty() {
    }
}