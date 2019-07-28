package com.erzhiqianyi.reactor;

import com.erzhiqianyi.reactor.domain.User;
import com.erzhiqianyi.reactor.domain.VipUser;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;

public class Part02Transform {

    /**
     * 使用 {@link Mono#map(Function)}  将字母转为大写
     */
    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(user -> new User(user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(),
                user.getLastname().toUpperCase()
        ));
    }


    /**
     * 使用 {@link Flux#map(Function)}  将字母转为大写
     */
    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(user -> new User(user.getUsername().toUpperCase(),
                user.getFirstname().toUpperCase(),
                user.getLastname().toUpperCase()
        ));
    }

    /**
     * 使用 {@link Mono#cast(Class)}  类型转换
     */
    Mono<User> castOne(Mono<VipUser> mono) {
        return mono.cast(User.class);
    }

    /**
     * 使用 {@link Mono#cast(Class)}  类型转换
     */
    Flux<User> castMany(Flux<VipUser> flux) {
        return flux.cast(User.class);
    }

    /**
     * 使用 {@link Flux#index()}  获取每个元素的序号
     */
    <T> Flux<Tuple2<Long, T>> index(Flux<T> flux) {
        return flux.index();
    }

    /**
     * 使用 {@link Mono#flatMap(Function)}  转换成另一个Mono
     */
    Mono<Integer> monoFlatMap(Mono<String> mono) {
        return mono.flatMap(item -> Mono.just(item.length()));
    }

    /**
     * 使用 {@link Mono#flatMap(Function)}  转换成另一个Mono
     */
    Flux<Integer> fluxFlatMap(Flux<String> flux) {
        return flux.flatMap(item -> Flux.just(item.length()));
    }

    /**
     * 将字符串转为一串字符
     */
    Flux<String> flatMapString(String str) {
        return Flux.just(str).flatMap(item -> Flux.just(item.split("")));
    }

    /**
     * 异步处理
     */
    Flux<String> flatMapHandle(Flux<String> flux) {
        return flux.flatMap(item -> Mono.fromCallable(
                () -> {
                    System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
                    return ("result:" + item);
                }));
    }

    /**
     * 过滤元素
     */
    Flux<String> flatMapEmpty(Flux<String> flux) {
        return flux.flatMap(item -> {
            if (item.length() > 3) {
                return Mono.just(item);
            } else {
                return Mono.empty();
            }
        });
    }

    /**
     * 使用 {@link Flux#flatMapSequential(Function)} 保留原来的序列顺序,对每个元素的异步任务会立即执行，但会将结果按照原序列顺序排序）
     */
    Flux<String> flatMapSequential(Flux<String> flux) {
        Executor executor = Executors.newFixedThreadPool(Math.min(1, 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        return flux.doOnNext(item -> System.out.println(" current " + item + " start " + System.currentTimeMillis()))
                .flatMapSequential(item -> withDelay(Mono.fromFuture(
                        () -> {
                            System.out.println(" current " + item + " running " + System.currentTimeMillis());
                            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> item
                                    , executor);
                            return future;
                        }), item.length()))
                .doOnNext(item -> System.out.println(" current " + item + " end " + System.currentTimeMillis()));
    }

    /**
     * 使用 {@link Mono#flatMapMany(Function)} 异步任务返回多个元素的序列
     */
    Flux<String> flatMapMany(Mono<String> mono) {
        return mono.flatMapMany(item -> Flux.just(item.split("")));
    }

    /**
     * 使用 {@link Flux#startWith(Object[])} 在开头添加
     */
    Flux<String> startWith(Flux<String> flux, String start) {
        return flux.startWith(start);
    }

    /**
     * 使用 {@link Flux#concatWith(Publisher)}  在后面添加
     */
    Flux<String> concatWith(Flux<String> flux, Flux<String> other) {
        return flux.concatWith(other);
    }

    /**
     * 使用 {@link Flux#collectList()}   转为list
     */
    Mono<List<String>> collectList(Flux<String> flux) {
        return flux.collectList();
    }

    /**
     * 使用 {@link Flux#collectSortedList()} 转为排序list
     */
    Mono<List<String>> collectSortedList(Flux<String> flux, Comparator comparator) {
        return flux.collectSortedList(comparator);
    }

    /**
     * 使用 {@link Flux#collectMap(Function)} 转为Map
     */
    Mono<Map<Integer, String>> collectMap(Flux<String> flux) {
        return flux.collectMap(String::length, item -> item);
    }

    /**
     * 使用 {@link Flux#collectMultimap(Function)}  转为Map
     */
    Mono<Map<Integer, Collection<String>>> collectMultimap(Flux<String> flux) {
        return flux.collectMultimap(String::length);
    }

    /**
     * 使用 {@link Flux#count()} 计数
     */
    Mono<Long> count(Flux<String> flux) {
        return flux.count();
    }

    /**
     * 使用 {@link Flux#all(Predicate)} 判断所有元素都满足条件
     */
    Mono<Boolean> all(Flux<String> flux, Predicate<String> predicate) {
        return flux.all(predicate);
    }


    /**
     * 使用 {@link Flux#any(Predicate)}  判断至少有一个元素满足条件
     */
    Mono<Boolean> any(Flux<String> flux, Predicate<String> predicate) {
        return flux.any(predicate);
    }

    /**
     * 使用 {@link Flux#hasElements()} 判断流是否有数据
     */
    Mono<Boolean> hasElements(Flux<String> flux) {
        return flux.hasElements();
    }

    /**
     * 使用 {@link Flux#hasElement(Object)}  判断流中至少有一个元素满足条件
     */
    Mono<Boolean> hasElement(Flux<String> flux, String value) {
        return flux.hasElement(value);
    }

    /**
     * 使用 {@link Flux#concat(Iterable)} 连接其他元素
     */
    Flux<String> concat(Flux<String> flux, List<String> values) {
        return Flux.concat(flux, Flux.fromIterable(values));
    }

    /**
     * 使用 {@link Flux#concatDelayError(Publisher[])} 连接元素，如果发生错误，等待所有的 发布者 连接完成
     */
    Flux<String> concatDelayError(Flux<String> flux, List<String> values) {
        return Flux.concatDelayError(flux,Flux.error(new IllegalStateException()), Flux.fromIterable(values));
    }


    private Mono<String> withDelay(Mono<String> userMono, Integer duration) {
        return Mono
                .delay(Duration.ofSeconds(duration))
                .flatMap(c -> userMono)
                .doOnNext(str -> System.out.println(" current " + str + " delay running " + System.currentTimeMillis()));
    }

}
