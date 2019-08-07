package com.erzhiqianyi.reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.function.Predicate;

public class Part04Filter {

    /**
     * 使用 {@link Mono#filter(Predicate)}  对元素进行过滤,返回元素为 "foo" 的元素
     */
    Mono<String> monoFilter(Mono<String> mono) {
        return mono.filter(item -> item.equals("foo"));
    }

    /**
     * 使用 {@link Flux#filter(Predicate)}  对元素进行过滤,返回长度大于等于4 的元素
     */
    Flux<String> filter(Flux<String> flux) {
        return flux.filter(item -> item.length() >= 4);
    }

    /**
     * 使用 {@link Mono#filterWhen(Function)}   对元素进行异步过滤,返回元素为 "foo" 的元素
     */
    Mono<String> monoFilterWhen(Mono<String> mono) {
        return mono.filterWhen(s -> Mono.just(s.equals("foo")));
    }

    /**
     * 使用 {@link Flux#filterWhen(Function)}   对元素进行异步过滤,返回长度大于等于4的元素
     */
    Flux<String> fluxFilterWhen(Flux<String> flux) {
        return flux.filterWhen(s -> Flux.just(s.length() >= 4));
    }

    /**
     * 使用 {@link Mono#ofType(Class)} 判断指定类型对象, 判断元素是否为 String 类型
     */
    Mono<String> monoOfType(Mono<Object> mono) {
        return mono.ofType(String.class);
    }


    /**
     * 使用 {@link Flux#ofType(Class)} 判断指定类型对象 判断元素是否为 String 类型
     */
    Flux<String> fluxOfType(Flux<Object> flux) {
        return flux.ofType(String.class);
    }

    /**
     * 使用 {@link Mono#ignoreElement()} 忽略所有元素
     */
    Mono<String> monoIgnoreElement(Mono<String> mono) {
        return mono.ignoreElement();
    }


    /**
     * 使用 {@link Flux#ignoreElements()} 忽略所有元素
     */
    Mono<String> fluxIgnoreElements(Flux<String> flux) {
        return flux.ignoreElements();
    }


    /**
     * 使用 {@link Flux#distinct()}  去重,去除重复元素
     */
    Flux<String> distinct(Flux<String> flux) {
        return flux.distinct();
    }

    /**
     * 使用 {@link Flux#distinctUntilChanged()}   去除连续重复元素
     */
    Flux<String> distinctUntilChanged(Flux<String> flux) {
        return flux.distinctUntilChanged();
    }

    /**
     * 使用 {@link Flux#take(long)} 从序列第一个元素开始取，取出前3个元素
     */
    Flux<String> takeByIndex(Flux<String> flux) {
        return flux.take(3);
    }

    /**
     * 使用 {@link Flux#take(Duration)} 取一段时间发出的元素
     */
    Flux<String> takeDuration(Flux<String> flux) {
        return flux.take(Duration.of(100, ChronoUnit.MILLIS));
    }

    /**
     * 使用 {@link Flux#next()} 取一个元素放到 Mono 中返回
     */
    Mono<String> next(Flux<String> flux) {
        return flux.next();
    }

    /**
     * 使用 {@link Flux#limitRequest(long)}
     */
    Flux<String> limitRequest(Flux<String> flux) {
        return flux.limitRequest(5);
    }

    /**
     * 使用 {@link Flux#takeLast(int)} 从序列的最后一个元素倒数,取出后5个元素
     */
    Flux<String> takeLast(Flux<String> flux) {
        return flux.takeLast(5);
    }

    /**
     * 使用 {@link Flux#takeUntil(Predicate)}  直到满足条件时才取出之前元素,然会返回,基于判断条件,
     * 取出 "foo" 元素之前的元素
     */
    Flux<String> takeUtil(Flux<String> flux) {
        return flux.takeUntil(item -> item.equalsIgnoreCase("foo"));
    }

    /**
     * 使用 {@link Flux#takeUntilOther(Publisher)} 直到满足条件时才取元素,基于对 publisher 比较
     */
    Flux<String> takeUntilOther(Flux<String> flux) {
        return flux.takeUntilOther(Mono.delay(Duration.of(5, ChronoUnit.SECONDS)));
    }

    /**
     * 使用 {@link Flux#elementAt(int)}  取给定序号元素,取出序号为3的 元素,第一个元素为0
     */
    Mono<String> elementAt(Flux<String> flux) {
        return flux.elementAt(3);
    }

    /**
     * 使用 {@link Flux#last()}  获取最后一个元素， 如果序列为空则发出错误信号
     */
    Mono<String> last(Flux<String> flux) {
        return flux.last();
    }

    /**
     * 使用 {@link Flux#last(Object)} 如果序列为空则返回默认值
     */
    Mono<String> lastDefault(Flux<String> flux) {
        return flux.last("default");
    }


    /**
     * 使用 {@link Flux#skip(long)} 从序列的第一个元素开始跳过, 跳过前三个元素
     */
    Flux<String> skipIndex(Flux<String> flux) {
        return flux.skip(2);
    }

    /**
     * 使用 {@link Flux#skip(Duration)} 跳过一段时间内发出的元素
     */
    Flux<String> skipDuration(Flux<String> flux) {
        return flux.skip(Duration.of(5,ChronoUnit.SECONDS));
    }

    /**
     * 使用 {@link Flux#skipLast(int)} 跳过最后的 n 个元素
     */
    Flux<String> skipLast(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }


    /**
     * 使用 {@link Flux#skipUntil(Predicate)} 直到满足某个条件才跳过(包含 ),基于判断条件
     */
    Flux<String> skipUntil(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#skipUntilOther(Publisher)}  直到满足某个条件才跳过(包含),基于对 publisher 的比较
     */
    Flux<String> skipUntilOther(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#skipWhile(Predicate)} 直到满足某个条件（不包含）才跳过
     */
    Flux<String> skipWhile(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#sample(Duration)}  给定采样周期进行采样
     */
    Flux<String> sampleDuration(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#sample(Publisher)} 基于另一个 publisher 采样
     */
    Flux<String> samplePublisher(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#sampleFirst(Duration)}  采样周期里的第一个元素
     */
    Flux<String> sampleFirst(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }


    /**
     * 使用 {@link Flux#sampleTimeout(Function)}  基于 publisher 超时
     */
    Flux<String> sampleTimeout(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#single()} 只要一个元素，为空则发出错误信息，多个返回错误
     */
    Flux<String> single(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#single(Object)}  只要一个元素，为空则发出默认值，多个返回错误
     */
    Flux<String> singleDefault(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#singleOrEmpty()}  只要一个元素，为空返回空序列,多个返回错误
     */
    Flux<String> singleOrEmpty(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }


}
