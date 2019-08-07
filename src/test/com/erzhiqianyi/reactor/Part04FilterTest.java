package com.erzhiqianyi.reactor;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

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
                .expectNext("three", "four", "five")
                .verifyComplete();
    }

    @Test
    public void monoFilterWhen() {
        String foo = "foo";
        Mono<String> mono = Mono.just(foo);
        mono = part04Filter.monoFilterWhen(mono.log());
        StepVerifier.create(mono)
                .expectNext(foo)
                .verifyComplete();
    }

    @Test
    public void fluxFilterWhen() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.fluxFilterWhen(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("three", "four", "five")
                .verifyComplete();

    }

    @Test
    public void monoOfType() {
        Object foo = "foo";
        Mono<Object> mono = Mono.just(foo);
        Mono<String> typeMono = part04Filter.monoOfType(mono).log();
        StepVerifier.create(typeMono)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void fluxOfType() {
        String[] array = {"one", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.fluxOfType(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();

    }

    @Test
    public void monoIgnoreElement() {
        String foo = "foo";
        Mono<String> mono = Mono.just(foo);
        mono = part04Filter.monoIgnoreElement(mono).log();
        StepVerifier.create(mono)
                .verifyComplete();

    }


    @Test
    public void fluxIgnoreElements() {
        String[] array = {"one", "two", "three", "four", "five"};
        Mono<String> mono = part04Filter.fluxIgnoreElements(Flux.fromArray(array)).log();
        StepVerifier.create(mono)
                .verifyComplete();
    }

    @Test
    public void distinct() {
        String[] array = {"one", "two", "three", "four", "five", "one", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.distinct(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three", "four", "five")
                .verifyComplete();
    }

    @Test
    public void distinctUntilChanged() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.distinctUntilChanged(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "two", "three", "four", "five")
                .verifyComplete();
    }

    @Test
    public void takeByIndex() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.takeByIndex(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "one", "two")
                .verifyComplete();

    }

    @Test
    public void takeDuration() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.takeDuration(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "one", "two", "two", "three", "four", "five")
                .verifyComplete();

    }

    @Test
    public void next() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Mono<String> mono = part04Filter.next(Flux.fromArray(array)).log();
        StepVerifier.create(mono)
                .expectNext("one")
                .verifyComplete();
    }

    @Test
    public void limitRequest() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.limitRequest(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "one", "two", "two", "three")
                .verifyComplete();
    }

    @Test
    public void takeLast() {
        String[] array = {"one", "one", "two", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.takeLast(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("two", "two", "three", "four", "five")
                .verifyComplete();
    }

    @Test
    public void takeUtil() {
        String[] array = {"one", "one", "two", "foo", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.takeUtil(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("one", "one", "two", "foo")
                .verifyComplete();
    }

    @Test
    public void takeUntilOther() {
        String[] array = {"one", "one", "two", "foo", "two", "three", "four", "five"};
        Flux<String> flux = part04Filter.takeUntilOther(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext(array)
                .verifyComplete();
    }

    @Test
    public void elementAt() {
        String[] array = {"one", "one", "two", "foo", "two", "three", "four", "five"};
        Mono<String> flux = part04Filter.elementAt(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("foo")
                .verifyComplete();
    }

    @Test
    public void last() {
        String[] array = {"one", "one", "two", "foo", "two", "three", "four", "five"};
        Mono<String> flux = part04Filter.last(Flux.fromArray(array)).log();
        StepVerifier.create(flux)
                .expectNext("five")
                .verifyComplete();

    }

    @Test
    public void lastDefault() {
        Mono<String> flux = part04Filter.lastDefault(Flux.empty()).log();
        StepVerifier.create(flux)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void skipIndex() {

    }

    @Test
    public void skipDuration() {
        Flux<String> flux = Flux.interval(Duration.ofSeconds(1)).map(item -> String.valueOf(item));
        flux = part04Filter.skipDuration(flux).take(10).log();
        StepVerifier.create(flux)
                .expectNext("4","5","6","7","8","9","10","11","12","13")
                .verifyComplete();

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