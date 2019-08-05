package com.erzhiqianyi.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.LongConsumer;

public class Part03Read {

    /**
     * 使用 {@link Flux#doOnNext(Consumer)} 发出元素，可以执行一些操作，建议不要修改元素状态
     */
    Flux<String> doOnNext(Flux<String> flux) {
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }

    /**
     * 使用 {@link Flux#doOnComplete(Runnable)}
     */
    Flux<String> doOnComplete(Flux<String> flux) {
        return flux.doOnComplete(() -> System.out.println(" do on complete "));
    }

    /**
     * 使用 {@link Mono#doOnSuccess(Consumer)}
     */
    Mono<String> doOnSuccess(Mono<String> mono) {
        return mono.doOnSuccess(item -> System.out.println(" do on success " + item));
    }

    /**
     * 使用 {@link Mono#doOnError(Consumer)} 发生错误时终止
     */
    Mono<String> monoDoOnError(Mono<String> mono) {
        return mono.doOnError(item -> System.out.println(" on error " + item.getMessage()));
    }

    /**
     * 使用 {@link Flux#doOnError(Consumer)} 发生错误时终止
     */
    Flux<String> fluxDoOnError(Flux<String> flux) {
        return flux.doOnError(item -> System.out.println(" on error:  " + item.getMessage()));
    }


    /**
     * 使用 {@link Mono#doOnCancel(Runnable)} 在取消时执行操作
     */
    Mono<String> monoDoOnCancel(Mono<String> mono) {
        return mono.doOnCancel(() -> System.out.println("cancel"));
    }


    /**
     * 使用 {@link Flux#doOnCancel(Runnable)}  在取消时执行操作
     */
    Flux<String> fluxDoOnCancel(Flux<String> flux) {
        return flux.doOnCancel(() -> System.out.println("cancel"));
    }

    /**
     * 使用 {@link Mono#doOnSubscribe(Consumer)} 订阅时执行操作
     */
    Mono<String> monoDoOnSubscribe(Mono<String> mono) {
        return mono.doOnSubscribe(sub -> System.out.println(" do on subscribe "));
    }

    /**
     * 使用 {@link Flux#doOnSubscribe(Consumer)}  订阅时执行操作
     */
    Flux<String> fluxDoOnSubscribe(Flux<String> flux) {
        return flux.doOnSubscribe(sub -> System.out.println(" do on subscribe "));
    }

    /**
     * 使用 {@link Mono#doOnRequest(LongConsumer)} 请求时执行操作
     */
    Mono<String> monoOnRequest(Mono<String> mono) {
        return mono.doOnRequest(consumer -> System.out.println(" do on request " + consumer));
    }

    /**
     * 使用 {@link Flux#doOnRequest(LongConsumer)} 请求时执行操作
     */
    Flux<String> fluxOnRequest(Flux<String> flux) {
        return flux.doOnRequest(consumer -> System.out.println(" do on request " + consumer));
    }

    /**
     * 使用 {@link Mono#doOnTerminate(Runnable)} 完成或错误终止：（Mono的方法可能包含有结果）
     */
    Mono<String> monoDoOnTerminate(Mono<String> mono) {
        return mono.doOnTerminate(() -> System.out.println(" do on terminate "));
    }

    /**
     * 使用 {@link Flux#doOnTerminate(Runnable)} 完成或错误终止：（Mono的方法可能包含有结果）
     */
    Flux<String> fluxDoOnTerminate(Flux<String> flux) {
        return flux.doOnTerminate(() -> System.out.println(" do on terminate "));
    }

    /**
     * 使用 {@link Mono#doAfterTerminate(Runnable)} 在终止信号向下游传递
     */
    Mono<String> monoDoAfterTerminate(Mono<String> mono) {
        return mono.doAfterTerminate(() -> System.out.println(" do after terminate "));
    }

    /**
     * 使用 {@link Flux#doAfterTerminate(Runnable)} 在终止信号向下游传递
     */
    Flux<String> fluxDoAfterTerminate(Flux<String> flux) {
        return flux.doAfterTerminate(() -> System.out.println(" do after terminate "));
    }

    /**
     * 使用 {@link Mono#doOnEach(Consumer)} 对所有类型的信号执行操作
     */
    Mono<String> monoDoOnEach(Mono<String> mono) {
        return mono.doOnEach(signalConsumer -> System.out.println(" do on each " + signalConsumer.get()));
    }

    /**
     * 使用 {@link Flux#doOnEach(Consumer)} 对所有类型的信号执行操作
     */
    Flux<String> fluxDoOnEach(Flux<String> flux) {
        return flux.doOnEach(signal -> System.out.println(" do on each " + signal));
    }

    /**
     * 使用 {@link Mono#doFinally(Consumer)} 所有结束的情况（完成complete、错误error、取消cancel）执行操作
     */
    Mono<String> monoDoFinally(Mono<String> mono) {
        return mono.doFinally(signalConsumer -> System.out.println(" do on finally " + signalConsumer));
    }

    /**
     * 使用 {@link Flux#doFinally(Consumer)} 所有结束的情况（完成complete、错误error、取消cancel）执行操作
     */
    Flux<String> fluxDoFinally(Flux<String> flux) {
        return flux.doFinally(signal -> System.out.println(" do on finally " + signal));
    }


}
