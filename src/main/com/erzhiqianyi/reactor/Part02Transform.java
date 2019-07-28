package com.erzhiqianyi.reactor;

import com.erzhiqianyi.reactor.domain.User;
import com.erzhiqianyi.reactor.domain.VipUser;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.*;
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
        return Flux.concatDelayError(flux, Flux.error(new IllegalStateException()), Flux.fromIterable(values));
    }

    /**
     * 使用 {@link Flux#mergeSequential(Publisher[])} 按订阅顺序merge
     */
    Flux<String> mergeSequential(String... values) {
        Flux<String> flux = Flux.fromArray(values);
        return Flux.mergeSequential(Flux.just(flux.flatMap(item -> Flux.just(item))));
    }

    /**
     * 使用 {@link Flux#merge(Publisher)}  按到元素到达的顺序merge
     */
    Flux<String> merge(String... values) {
        Flux<String> flux = Flux.fromArray(values);
        return Flux.merge(Flux.just(flux.flatMap(item -> withDelay(Mono.just(item), item.length()))));
    }

    /**
     * 使用 {@link Flux#mergeWith(Publisher)}  按到元素到达的顺序merge
     */
    Flux<String> mergeWith(Flux<String> flux, String... values) {
        Flux<String> anotherFlux = Flux.fromArray(values)
                .flatMap(item -> withDelay(Mono.just(item), item.length()));
        return flux.mergeWith(anotherFlux);
    }

    /**
     * 使用 {@link Flux#zip(Publisher, Publisher)}
     * 将两个数据源合并到一起，一边取一个，直到其中一个数据源结束
     */
    Flux<Tuple2<Integer, String>> zip(Flux<Integer> flux, Flux<String> anotherFlux) {
        return Flux.zip(flux, anotherFlux);
    }

    /**
     * 使用 {@link Flux#zipWith(Publisher)}
     * 将两个数据源合并到一起，一边取一个，直到其中一个数据源结束
     */
    Flux<Tuple2<Integer, String>> zipWith(Flux<Integer> flux, Flux<String> another) {
        return flux.zipWith(another);
    }

    /**
     * 使用 {@link Mono#zip(Mono, Mono)} (Publisher)}
     * 将两个Mono 合并到一起
     */
    Mono<Tuple2<Integer, String>> monoZip(Mono<Integer> one, Mono<String> another) {
        return Mono.zip(one, another);
    }

    /**
     * 使用 {@link Mono#zipWith(Mono)}
     * 将两个Mono合并到一起
     */
    Mono<Tuple2<Integer, String>> monoZipWith(Mono<Integer> one, Mono<String> another) {
        return one.zipWith(another);
    }


    /**
     * 使用 {@link Mono#and(Publisher)}
     * 在 Mono 终止时转换为一个 Mono<Void>
     */
    Mono<Void> and(Mono<Integer> one, Mono<String> another) {
        return one.log().and(another);
    }

    /**
     * 使用 {@link Mono#when(Publisher[])}
     * 所有 Mono 终止时转换为一个 Mono<Void>
     */
    Mono<Void> when(Mono<String> one, Mono<String> another) {
        return Mono.when(withDelay(one, 2), withDelay(another, 3));
    }

    /**
     * 使用 {@link Flux#combineLatest(Function, Publisher[])}
     * 合并最近发出的元素
     */
    Flux<String> combineLatest(Flux<String> one, Flux<String> another) {
        Function<Object[], String> combinator = objects -> Arrays.toString(objects);
        return Flux.combineLatest(combinator, one, another).log();
    }

    /**
     * 使用 {@link Flux#first(Publisher[])}
     * 挑选出第一个发布者，由其提供事件。能有效避免多个源的冲突。
     */
    Flux<String> firstFlux(Flux<String>... sources) {
        return Flux.first(sources);
    }

    /**
     * 使用 {@link Mono#first(Mono[])}
     * 挑选出第一个发布者，由其提供事件。能有效避免多个源的冲突。
     */
    Mono<String> firstMono(Mono<String>... sources) {
        return Mono.first(sources);
    }

    /**
     * 使用 {@link Flux#or(Publisher)}
     * 挑选出第一个发布者，由其提供事件。能有效避免多个源的冲突。
     */
    Flux<String> orFlux(Flux<String> one, Flux<String> another) {
        return one.or(another);
    }

    /**
     * 使用 {@link Mono#or(Mono)}
     * 挑选出第一个发布者，由其提供事件。能有效避免多个源的冲突。
     */
    Mono<String> orMono(Mono<String> one, Mono<String> another) {
        return one.or(another);
    }

    /**
     * 使用 {@link Flux#switchMap(Function)} 由一个序列触发，
     */
    Flux<String> switchMap(Flux<String> flux) {
        return flux.log().switchMap(item -> {
            if (item.length() > 3) {
                return Flux.just(item).log();
            } else {
                return Flux.just(item).flatMap(sub -> Flux.just(sub.split(""))).log();
            }
        });
    }
    /**
     * 使用 {@link Flux#switchOnNext(Publisher)} 由一个序列触发，
     */
    Flux<String> switchMap(Flux<String> flux) {
        return flux.log().switchMap(item -> {
            if (item.length() > 3) {
                return Flux.just(item).log();
            } else {
                return Flux.just(item).flatMap(sub -> Flux.just(sub.split(""))).log();
            }
        });
    }


    private Mono<String> withDelay(Mono<String> userMono, Integer duration) {
        return Mono
                .delay(Duration.ofSeconds(duration))
                .flatMap(c -> userMono)
                .doOnNext(str -> System.out.println(" current " + str + " delay running " + System.currentTimeMillis()));
    }

}
