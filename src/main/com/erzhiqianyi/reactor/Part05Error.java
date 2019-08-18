package com.erzhiqianyi.reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.function.Supplier;


public class Part05Error {

    /**
     * 使用 {@link Mono#error(Throwable)} 生成错误,产生 {@link IllegalStateException}
     */
    public Mono<String> monoError() {
        return Mono.error(new IllegalStateException());
    }


    /**
     * 使用 {@link Flux#error(Throwable)} 生成错误,产生 {@link IllegalStateException}
     */
    public Flux<String> fluxError() {
        return Flux.error(new IllegalStateException());
    }

    /**
     * 使用 {@link Mono#error(Supplier)} 和 生成错误,产生 {@link IllegalStateException}
     */
    public Mono<String> monoErrorSupplier() {
        return Mono.error(() -> new IllegalStateException());
    }

    /**
     * 使用 {@link Flux#error(Supplier)} 生成错误,产生 {@link IllegalStateException}
     */
    public Flux<String> fluxErrorSupplier() {
        return Flux.error(() -> new IllegalStateException());
    }

    /**
     * 使用 {@link Mono#onErrorReturn(Object)} 发生异常时返回回调值
     */
    public Mono<String> monoOnErrorReturn(Mono<String> mono) {
        return mono.onErrorReturn("error");
    }

    /**
     * 使用 {@link Flux#onErrorReturn(Object)}
     */
    public Flux<String> fluxOnErrorReturn(Flux<String> flux) {
        return flux.onErrorReturn("error");
    }

    /**
     * 使用 {@link Mono#onErrorResume(Function)}
     */
    public Mono<String> monoOnErrorResume(Mono<String> mono) {
        return mono.onErrorResume(throwable -> {
            System.err.println(throwable);
            return Mono.just("error");
        });
    }

    /**
     * 使用 {@link Flux#onErrorResume(Function)}
     */
    public Flux<String> fluxOnErrorResume(Flux<String> flux) {
       return flux.onErrorResume(throwable -> {
           System.err.println(throwable);
           return Mono.just("error");
       });
    }

    /**
     * 使用 {@link Mono#onErrorMap(Function)}
     */
    public Mono<String> monoOnErrorMap() {
        return Mono.error(() -> new IllegalStateException());
    }

    /**
     * 使用 {@link Flux#onErrorMap(Function)}
     */
    public Mono<String> fluxOnErrorMap() {
        return Mono.error(() -> new IllegalStateException());
    }


    /**
     * 使用 {@link Mono#timeout(Duration)} 超时未发出元素产生  {@link TimeoutException}
     */
    public Mono<String> monoTimeout(Mono<String> mono) {
        return mono.timeout(Duration.ofSeconds(5));
    }

    /**
     * 使用 {@link Flux#timeout(Duration)} 超时未发出元素产生  {@link TimeoutException}
     */
    public Flux<String> fluxTimeout(Flux<String> flux) {
        return flux.timeout(Duration.ofSeconds(5));
    }

    /**
     * 使用 {@link Mono#retry()}
     */
    public Mono<String> monoRetry(Mono<String> mono) {
        return mono.timeout(Duration.ofSeconds(5));
    }

    /**
     * 使用 {@link Flux#retry()}
     */
    public Mono<String> fluxRetry(Mono<String> mono) {
        return mono.timeout(Duration.ofSeconds(5));
    }

    /**
     * 使用 {@link Mono#retryWhen(Function)}
     */
    public Mono<String> monoRetryWhen(Mono<String> mono) {
        return mono.timeout(Duration.ofSeconds(5));
    }

    /**
     * 使用 {@link Flux#retryWhen(Function)}
     */
    public Mono<String> fluxRetryWhen(Mono<String> mono) {
        return mono.timeout(Duration.ofSeconds(5));
    }


    /**
     * 使用 {@link Flux#concat(Publisher[])} 和{@link Flux#error(Throwable)} ,将错误添加到序列中
     */
    public Flux<String> fluxConcatError(Flux<String> flux) {
        return Flux.concat(flux, Flux.error(new IllegalStateException()));
    }

    /**
     * 使用 {@link Mono#then(Mono)}  和{@link Mono#error(Throwable)} ,返回错误
     */
    public Mono<String> monoThenError(Mono<String> mono) {
        return mono.then(Mono.error(new IllegalStateException()));
    }


}
