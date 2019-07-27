package com.erzhiqianyi.reactor;

import com.erzhiqianyi.reactor.domain.User;
import com.erzhiqianyi.reactor.domain.VipUser;
import com.erzhiqianyi.reactor.repository.ReactiveRepository;
import com.erzhiqianyi.reactor.repository.ReactiveUserRepository;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class Part02TransformTest {

    private Part02Transform part02Transform;
    private ReactiveRepository reactiveRepository;

    @Before

    public void init() {
        part02Transform = new Part02Transform();
        reactiveRepository = new ReactiveUserRepository();
    }

    @Test
    public void capitalizeOne() {
        Mono<User> userMono = reactiveRepository.findFirst();
        Mono<User> mono = part02Transform.capitalizeOne(userMono).log();
        StepVerifier.create(mono)
                .expectNext(new User("SWHITE", "SKYLER", "WHITE"))
                .verifyComplete();
    }

    @Test
    public void capitalizeMany() {
        Flux<User> flux = reactiveRepository.findAll();
        StepVerifier.create(part02Transform.capitalizeMany(flux).log())
                .expectNext(
                        new User("SWHITE", "SKYLER", "WHITE"),
                        new User("JPINKMAN", "JESSE", "PINKMAN"),
                        new User("WWHITE", "WALTER", "WHITE"),
                        new User("SGOODMAN", "SAUL", "GOODMAN"))
                .verifyComplete();
    }

    @Test
    public void castOne() {
        VipUser vipUser = new VipUser("SWHITE", "SKYLER", "WHITE", 1);
        Mono<VipUser> mono = Mono.just(vipUser).log();
        User user = vipUser;
        StepVerifier.create(part02Transform.castOne(mono))
                .expectNext(user)
                .verifyComplete();
    }


    @Test
    public void castMany() {
        VipUser vipUser = new VipUser("SWHITE", "SKYLER", "WHITE", 1);
        Flux<VipUser> flux = Flux.just(vipUser).log();
        User user = vipUser;
        StepVerifier.create(part02Transform.castMany(flux))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void index() {
        Flux<String> flux = Flux.just("one", "two", "three");
        Flux<Tuple2<Long, String>> tuple2Flux = part02Transform.index(flux).log();
        StepVerifier.create(tuple2Flux.map(Tuple2::getT1))
                .expectNext(0l, 1l, 2l)
                .verifyComplete();

        tuple2Flux.subscribe(item -> {
            System.out.println(item.getT1());
            System.out.println(item.getT2());
        });
    }

    @Test
    public void monoFlatMap() {
        String str = "one";
        Mono<String> mono = Mono.just(str);
        StepVerifier.create(part02Transform.monoFlatMap(mono))
                .expectNext(str.length())
                .verifyComplete();
    }

    @Test
    public void fluxFlatMap() {
        String str = "one";
        Flux<String> flux = Flux.just(str);
        StepVerifier.create(part02Transform.fluxFlatMap(flux).log())
                .expectNext(str.length())
                .verifyComplete();
    }

    @Test
    public void flatMapString() {
        String str = "one";
        Flux<String> flux = part02Transform.flatMapString(str).log();
        StepVerifier.create(flux)
                .expectNext("o", "n", "e")
                .verifyComplete();

    }

    @Test
    public void flatMapHandle() {
        String str = "one";
        Flux<String> flux = part02Transform.flatMapHandle(Flux.just(str)).log();
        StepVerifier.create(flux)
                .expectNext("result:" + str)
                .verifyComplete();
    }

    @Test
    public void flatMapEmpty() {
        Flux<String> flux = part02Transform.flatMapEmpty(
                Flux.just("one", "two", "three", "four")).log();
        StepVerifier.create(flux)
                .expectNext("three", "four")
                .verifyComplete();
    }

    @Test
    public void flatMapSequential() {
        Flux<String> flux = part02Transform.flatMapSequential(
                Flux.just("one_one", "two_four", "three", "four", "one", "two")).log();
        StepVerifier.create(flux)
                .expectNext("one_one", "two_four", "three", "four", "one", "two")
                .verifyComplete();
    }

    @Test
    public void flatMapMany() {
        Flux<String> flux = part02Transform.flatMapMany(Mono.just("one")).log();
        StepVerifier.create(flux)
                .expectNext("o", "n", "e")
                .verifyComplete();
    }

    @Test
    public void startWith() {
        Flux<String> flux = Flux.just("two", "three");
        Flux<String> startFlux = part02Transform.startWith(flux, "one").log();
        StepVerifier.create(startFlux)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }

    @Test
    public void concatWith() {
        Flux<String> flux = Flux.just("one", "two");
        Flux<String> other = Flux.just("three", "four");
        Flux<String> concat = part02Transform.concatWith(flux, other).log();
        StepVerifier.create(concat)
                .expectNext("one", "two", "three", "four")
                .verifyComplete();
    }

    @Test
    public void collectList() {
        Flux<String> flux = Flux.just("one", "two", "three", "four");
        Mono<List<String>> mono = part02Transform.collectList(flux).log();
        List<String> list = Arrays.asList("one", "two", "three", "four");
        StepVerifier.create(mono)
                .expectNext(list)
                .verifyComplete();
    }

    @Test
    public void collectSortedList() {
        Flux<String> flux = Flux.just("one", "two", "three", "four");
        Mono<List<String>> mono = part02Transform
                .collectSortedList(flux, Comparator.comparing(String::length))
                .log();
        List<String> list = Arrays.asList("one", "two", "four", "three");
        StepVerifier.create(mono)
                .expectNext(list)
                .verifyComplete();
    }

    @Test
    public void collectMap() {
        Flux<String> flux = Flux.just("one", "three", "four");
        Mono<Map<Integer, String>> mono = part02Transform.collectMap(flux).log();
        Map<Integer, String> map = Stream.of("one", "three", "four")
                .collect(Collectors.toMap(String::length, item -> item));
        StepVerifier.create(mono)
                .expectNext(map)
                .verifyComplete();
    }

    @Test
    public void collectMultimap() {
        List<String> list = Arrays.asList("one", "two", "three", "four", "five", "six", "seven");
        Mono<Map<Integer, List<String>>> mono = part02Transform.collectMultimap(Flux.fromIterable(list)).log();
        Map<Integer, List<String>> map = list.stream()
                .collect(Collectors.groupingBy(String::length));
        StepVerifier.create(mono)
                .expectNext(map)
                .verifyComplete();

    }
}