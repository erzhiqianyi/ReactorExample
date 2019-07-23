package com.erzhiqianyi.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Part01CreateInstance {
    private String getFoo() {
        System.out.println(" 获取 Foo ");
        return "foo";
    }

    /**
     * 使用 ${@link Mono#just(Object)} 发出一个T
     */
    Mono<String> fooMono() {
        return Mono.just(getFoo());
    }

    /**
     * 使用 ${@link Mono#justOrEmpty(Object)} }  基于一个${@link java.util.Optional}发出一个T
     */
    Mono<String> fooOptionalMono() {
        return Mono.justOrEmpty(Optional.of("foo"));
    }

    /**
     * 使用 ${@link Mono#justOrEmpty(Optional)} }  基于一个${@link java.util.Optional}发出一个T,数据为空
     */
    Mono<String> fooOptionalMonoEmpty() {
        return Mono.justOrEmpty(Optional.empty());
    }


    /**
     * 使用 ${@link Mono#justOrEmpty(Object)} }  基于一个${@link Object}发出一个可能为空的T
     */
    Mono<String> fooJustEmptyMono() {
        return Mono.justOrEmpty("foo");
    }

    /**
     * 使用 ${@link Mono#justOrEmpty(Object)} }  基于一个${@link Object}发出一个可能为空的T,数据为空
     */
    Mono<String> fooJustEmptyMonoEmpty() {
        return Mono.justOrEmpty(null);
    }

    /**
     * 使用 ${@link Mono#fromSupplier(Supplier) }  发出一个T ，懒创建
     */
    Mono<String> fooMonoSupplier() {
        return Mono.fromSupplier(() -> "foo");
    }


    /**
     * 使用 ${@link Flux#just(Object) }  发出多个T
     */
    Flux<String> justFlux() {
        return Flux.just("one", "two", "three");
    }

    /**
     * 使用 ${@link Flux#fromArray(Object[]) }  基于数组发出Flux
     */
    Flux<String> FluxFromArray() {
        String[] array = {"one", "two", "three"};
        return Flux.fromArray(array);
    }

    /**
     * 使用 ${@link Flux#fromIterable(Iterable) }  基于Iterable发出Flux
     */
    Flux<String> FluxFromIterable() {
        List<String> list = Arrays.asList(new String[]{"one", "two"});
        return Flux.fromIterable(list);
    }

    /**
     * 使用 ${@link Flux#range(int, int) }  发出指定范围的Flux
     */
    Flux<Integer> FluxRange() {
        return Flux.range(1, 10);
    }

    /**
     * 使用 ${@link Flux#fromStream(Stream) }  基于Stream发出Flux
     */
    Flux<String> FluxFromStream() {
        return Flux.fromStream(Stream.of("one", "two", "three"));
    }

    /**
     * 使用 ${@link Mono#fromCallable(Callable) }  基于任务发出Mono
     */
    Mono<String> MonoFromCallable() {
        Callable<String> task = () -> {
            System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
            return "foo";
        };
        return Mono.fromCallable(task);
    }

    /**
     * 使用 ${@link Mono#fromRunnable(Runnable) }  基于任务发出Mono
     */
    Mono<String> MonoFromRunnable() {
        return Mono.fromRunnable(() -> {
            System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
        });
    }

    /**
     * 使用 ${@link Mono#fromFuture(CompletableFuture)}  基于CompletableFuture发出Mono
     */
    Mono<String> MonoFromFuture() {
        Executor executor = Executors.newFixedThreadPool(Math.min(1, 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "foo", executor);
        return Mono.fromFuture(future);
    }

    /**
     * 使用 ${@link Mono#empty()} }  发送空的 Mono
     */
    Mono<String> emptyMono() {
        return Mono.empty();
    }

    /**
     * 使用 ${@link Flux#empty()} }  发送空的 Flux
     */
    Flux<String> emptyFlux() {
        return Flux.empty();
    }


    /**
     * 使用 ${@link Mono#error(Throwable) } 立即生成错误
     */
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }

    /**
     * 使用 ${@link Flux#error(Throwable) }  立即生成错误
     */
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

    /**
     * 使用 ${@link Mono#never() }  不产生任何信号
     */
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }

    /**
     * 使用 ${@link Flux#never() }  不产生任何信号
     */
    Flux<String> fluxWithNoSignal() {
        return Flux.never();
    }


    /**
     * 使用 ${@link Mono#defer(Supplier)}  }  发出一个T ，懒创建
     */
    Mono<String> fooMonoDefer() {
        return Mono.defer(() -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Flux#defer(Supplier)}  }  发出T ，懒创建
     */
    Flux<String> fooFluxDefer() {
        return Flux.defer(() -> Mono.just(getFoo()));
    }


}
