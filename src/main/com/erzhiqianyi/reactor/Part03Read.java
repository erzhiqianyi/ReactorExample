package com.erzhiqianyi.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

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
        return flux.doOnComplete( () -> System.out.println(" do on complete ") );
    }

    /**
     * 使用 {@link Mono#doOnSuccess(Consumer)}
     */
    Mono<String> doOnSuccess(Mono<String> mono) {
        return mono.doOnSuccess( item -> System.out.println(" do on success " + item));
    }

    /**
     * 使用 {@link Mono#doOnError(Consumer)} 发生错误时终止
     */
    Mono<String> monoDoOnError(Mono<String> mono) {
        return mono.doOnError( item -> System.out.println(" on error " + item.getMessage() ));
    }

    /**
     * 使用 {@link Flux#doOnError(Consumer)} 发生错误时终止
     */
    Flux<String> fluxDoOnError(Flux<String> flux) {
        return flux.doOnError( item -> System.out.println(" on error:  " + item.getMessage() ));
    }






}
