package com.erzhiqianyi.reactor;

import javafx.util.Callback;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Part01CreateInstance {
    private String getFoo() {
        Logger logger = Logger.getLogger("Foo");
        logger.info("flux:callable task executor: " + Thread.currentThread().getName() + " | foo" );
        return "foo";
    }

    private String[]  getArray(){
        Logger logger = Logger.getLogger("Array");
        String[] array = {"one", "two", "three"};
        logger.info(Arrays.toString(array));
        return array;
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
        return Mono.justOrEmpty(Optional.of(getFoo()));
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
        return Mono.justOrEmpty(getFoo());
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
        return Mono.fromSupplier(() -> getFoo());
    }


    /**
     * 使用 ${@link Flux#just(Object) }  发出多个T
     */
    Flux<String> justFlux() {
        return Flux.just(getArray());
    }

    /**
     * 使用 ${@link Flux#fromArray(Object[]) }  基于数组发出Flux
     */
    Flux<String> fluxFromArray() {
        return Flux.fromArray(getArray());
    }

    /**
     * 使用 ${@link Flux#fromIterable(Iterable) }  基于Iterable发出Flux
     */
    Flux<String> fluxFromIterable() {
        return Flux.fromIterable(Arrays.asList(getArray()));
    }

    /**
     * 使用 ${@link Flux#range(int, int) }  发出指定范围的Flux
     */
    Flux<Integer> fluxRange() {
        return Flux.range(1, 10);
    }

    /**
     * 使用 ${@link Flux#fromStream(Stream) }  基于Stream发出Flux
     */
    Flux<String> fluxFromStream() {
        return Flux.fromStream(Stream.of(getArray()));
    }

    /**
     * 使用 ${@link Mono#fromCallable(Callable) }  基于任务发出Mono
     */
    Mono<String> monoFromCallable() {
        Callable<String> task = () -> {
            System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
            return getFoo();
        };
        return Mono.fromCallable(task);
    }

    /**
     * 使用 ${@link Mono#fromRunnable(Runnable) }  基于任务发出Mono
     */
    Mono<String> monoFromRunnable() {
        return Mono.fromRunnable(() -> {
            System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
        });
    }

    /**
     * 使用 ${@link Mono#fromFuture(CompletableFuture)}  基于CompletableFuture发出Mono
     */
    Mono<String> monoFromFuture() {
        Executor executor = Executors.newFixedThreadPool(Math.min(1, 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> getFoo(), executor);
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
        return Mono.defer( () -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Flux#defer(Supplier)}  }  发出T ，懒创建
     */
    Flux<String> fooFluxDefer() {
        return Flux.defer(() -> Flux.just(getArray()));
    }

    /**
     * 使用 ${@link Mono#using(Callable, Function, Consumer) }  基于可回收资源发出T ，
     */
    Flux<String> fooMonoUsing() {
        return Flux.defer(() -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Flux#using(Callable, Function, Consumer) }  基于可回收资源发出T ，
     */
    Flux<String> fooFluxUsing() {
        return Flux.defer(() -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Flux#generate(Consumer)} 可编程的生成事件，一个接一个 ，
     */
    Flux<String> fooFluxGeneerate() {
        return Flux.defer(() -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Mono#create(Consumer)} 异步发出T ，
     */
    Flux<String> fooMonoCreate() {
        
        return Flux.defer(() -> Mono.just(getFoo()));
    }

    /**
     * 使用 ${@link Flux#create(Consumer)} 异步发出T ，
     */
    Flux<String> fooFluxCreate() {
        return Flux.defer(() -> Mono.just(getFoo()));
    }




}
