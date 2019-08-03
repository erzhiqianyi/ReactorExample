package com.erzhiqianyi.reactor;

import reactor.core.publisher.Flux;

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
        return flux.doOnNext(item -> System.out.println("do on next print value " + item));
    }





}
