# ReactorExample
## Reactor 反应式编程基础
本部分介绍Reactor基础，为后续业务实现打基础。涉及的内容有 
- [反应式编程介绍](#反应式编程介绍)
- [Reactor介绍](#Reactor介绍)
- [创建Flux](#创建Flux)
- [创建Mono](#创建Mono)
- [使用StepVerifier测试](#使用StepVerifier测试)
- [调试](#调试)
    - [启用调试模式](#启用调试模式)
    - [使用检查点](#使用检查点)
    - [日志记录](#日志记录)
- [操作符](#操作符)
    - [创建序列](#创建序列)
		- [just](#just)
		- [justOrEmpty](#justOrEmpty)
		- [fromArray](#fromArray)
		- [fromSupplier](#fromSupplier)
		- [range](#range)
		- [fromStream](#fromStream)
		- [fromCallable](#fromCallable)
		- [fromRunnable](#fromRunnable)
		- [fromFuture](#fromFuture)
		- [empty](#empty)
		- [error](#error)
		- [defer](#defer)
		- [using](#using)
		- [generate](#generate)
		- [create](#create)
    - [转换序列](#转换序列)
		- [map](#map)
		- [cast](#cast)
		- [index](#index)
		- [flatMap](#flatMap)
		- [flatMapSequential](#flatMapSequential)
		- [flatMapMany](#flatMapMany)
		- [startWith](#startWith)
		- [concatWith](#concatWith)
		- [collectList](#collectList)
		- [collectSortedList](#collectSortedList)
		- [collectMap](#collectMap)
		- [collectMultiMap](#collectMultiMap)
		- [count](#count)
		- [reduce](#reduce)
		- [scan](#scan)
		- [all](#all)
		- [hasElement](#hasElement)
		- [hasElement](#hasElement)
		- [concat](#concat)
		- [concatWith](#concatWith)
		- [concatDelayError](#concatDelayError)
-
		- [](#)
    - [只读序列](#只读序列)
    - [过虑序列](#过虑序列)
    - [错误处理](#错误处理)
    - [基于时间的操作](#基于时间的操作)
    - [拆分Flux](#拆分Flux)
    - [回到同步的世界](#回到同步的世界)
- [调度器](#调度器)
- [消息处理](#消息处理)
- [“懒”和“急”](#“懒”和“急”)
- [参考内容](#参考内容)
### 反应式编程介绍
```Webflux``` 使用 [Reactor](https://projectreactor.io/docs) 进行反应式编程，java的反应式编程库还有 [Rxjava](https://github.com/ReactiveX/RxJava) 和 [Rxjava2](https://github.com/ReactiveX/RxJava/wiki/What's-different-in-2.0) 。

反应式编程采用声明性代码来构建异步处理链，使用订阅者-发布者模式，发布者产生新数据时，会推送给订阅者，只有订阅者进行订阅操作后，整个处理链才会执行。即在订阅之前，什么都不会操作。和 ```Stream``` 流类似，执行终止操作前，数据流不会执行任何操作。
### Reactor介绍
```Reactor```则是基于反应式流规范设计和实现的库，使用起来比较简单直观。Spring 5 中反应式编程库用的也是 ```Reactor```。

```Flux```和 ```Mono``` 是 ```Reactor``` 中的两个基本概念。```Flux ```表示的是包含 0 到 N 个元素的异步序列。在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。当消息通知产生时，订阅者中对应的方法 ```onNext()```,```onComplete()```和 ```onError()```会被调用。

```Mono``` 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 ```Flux``` 相同的三种类型的消息通知。

```Flux``` 和 ```Mono``` 之间可以进行转换。

对一个 ```Flux``` 序列进行计数操作，得到的结果是一个 ```Mono<Long>```对象。把两个 ```Mono``` 序列合并在一起，得到的是一个 ```Flux``` 对象。

### 创建Flux 

![Flux](https://github.com/erzhiqianyi/yitian-blog/blob/master/image/flux.png?raw=true)
```Flux```  是反应式流发布者(Publisher),可以执行各种流式操作，比如生成、转换、过滤等。大部分 ```Stream流``` 中的操作，都可以在Flux中找到对应的操作。
使用静态方法生成Flux
- just()
    可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
- fromArray()，fromIterable()和 fromStream()
    可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
- empty()：
    创建一个不包含任何元素，只发布结束消息的序列。
- error(Throwable error)
    创建一个只包含错误消息的序列。
- never() 
    创建一个不包含任何消息通知的序列。
- range(int start, int count)
    创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
- interval(Duration period)和interval(Duration delay, Duration period)
    创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
- intervalMillis(long period)和 intervalMillis(long delay, long period)
    与 interval()方法的作用相同，只不过该方法通过毫秒数来指定时间间隔和延迟时间。

```Part01Flux.java```
```java
public class Part01Flux {

    //  Return an empty Flux
    Flux<String> emptyFlux() {
        return Flux.empty();
    }


    //  Return a Flux that contains 2 values "foo" and "bar" without using an array or a collection
    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }


    // Create a Flux from a List that contains 2 values "foo" and "bar"
    Flux<String> fooBarFluxFromList() {
        return Flux.fromIterable(Arrays.asList(new String[]{"foo", "bar"}));
    }

    //  Create a Flux that emits an IllegalStateException
    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

    //  Create a Flux that emits increasing values from 0 to 9 each 100ms
    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100)).take(10);
    }
    Flux<Integer> range(){
        return Flux.range(1,5);
    }
}
```
测试代码```Part01FluxTest.java```
```java
public class Part01FluxTest {
    Part01Flux part01Flux;

    @Before
    public void init() {
        part01Flux = new Part01Flux();
    }

    @Test
    public void emptyFlux() {
        StepVerifier.create(part01Flux.emptyFlux())
                .expectComplete()
                .verify();
    }

    @Test
    public void fooBarFluxFromValues() {
        StepVerifier.create(part01Flux.fooBarFluxFromValues())
                .expectNext("foo", "bar")
                .expectComplete()
                .verify();
    }

    @Test
    public void fooBarFluxFromList() {
        StepVerifier.create(part01Flux.fooBarFluxFromList())
                .expectNext("foo", "bar")
                .expectComplete()
                .verify();
    }

    @Test
    public void errorFlux() {
        StepVerifier.create(part01Flux.errorFlux())
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    public void counter() {
        StepVerifier.create(part01Flux.counter())
                .expectNext(0l, 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l)
                .expectComplete()
                .verify();
    }

    @Test
    public void range() {
        StepVerifier.create(part01Flux.range())
                .expectNext( 1, 2, 3, 4, 5)
                .expectComplete()
                .verify();

    }
}
```

### 创建Mono
![Mono](https://github.com/erzhiqianyi/yitian-blog/blob/master/image/mono.png?raw=true)

[Mono](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html)
使用静态方法生成Mono
- just()
    创建一个元素后立即结束   
- empty()：
    创建一个不包含任何元素，只发布结束消息的Mono。
- error(Throwable error)
    创建一个只包含错误消息的Mono。
- never() 
    创建一个不包含任何消息通知的Mono。
- justOrEmpty(Optional<? extends T> data)和 justOrEmpty(T data)
    从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。 
- fromCallable()
  从 Callable 创建
- fromCompletionStage()
  从 CompletionStage 创建
- fromFuture()
  从 CompletableFuture 创建
- fromRunnable()
  从 Runnable 创建
- fromSupplier ()
  从 Supplier创建
- delay(Duration duration)和 delayMillis(long duration)
  创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。 

```Part02Mono.java```
```java
public class Part02Mono {
    //  Return an empty Mono
    Mono<String> emptyMono() {
        return Mono.empty();
    }


    //  Return a Mono that never emits any signal
    Mono<String> monoWithNoSignal() {
        return Mono.never();
    }


    //  Return a Mono that contains a "foo" value
    Mono<String> fooMono() {
        return Mono.just("foo");
    }


    //  Create a Mono that emits an IllegalStateException
    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }

    //  Create a Mono from supplier
    Mono<String> monoFromSupplier(){
       return Mono.fromSupplier(() -> "foo") ;
    }

    //  Create a Mono delay
    Mono<Long> delayMono(){
       return Mono.delay(Duration.ofMillis(100)) ;
    }

    Mono<String> justOrEmptyMono(){
       return Mono.justOrEmpty(Optional.empty()) ;
    }

}
```
测试代码 ```Part02MonoTest.java```
```java
public class Part02MonoTest {

    private Part02Mono part02Mono;

    @Before
    public void init() {
        part02Mono = new Part02Mono();
    }

    @Test
    public void emptyMono() {
        StepVerifier.create(part02Mono.emptyMono())
                .expectComplete()
                .verify();

    }

    @Test
    public void monoWithNoSignal() {
        StepVerifier.create(part02Mono.monoWithNoSignal())
                .expectComplete()
                .verify();
    }

    @Test
    public void fooMono() {
        StepVerifier.create(part02Mono.fooMono())
                .expectNext("foo")
                .expectComplete()
                .verify();

    }

    @Test
    public void errorMono() {
        StepVerifier.create(part02Mono.errorMono())
                .expectError(IllegalStateException.class)
                .verify();

    }

    @Test
    public void monoFromSupplier() {
        StepVerifier.create(part02Mono.monoFromSupplier())
                .expectNext("foo")
                .expectComplete()
                .verify();

    }

    @Test
    public void delayMono() {
        StepVerifier.create(part02Mono.delayMono())
                .expectNext(0l)
                .expectComplete()
                .verify();

    }

    @Test
    public void justOrEmptyMono() {
        StepVerifier.create(part02Mono.justOrEmptyMono())
                .expectComplete()
                .verify();

    }
}
```
### 使用StepVerifier测试
Reactor 使用 StepVerifier 进行测试。
典型测试方法如下, ```create()```  创建测试```StepVerifier```,然后可以调用```StepVerifier```的操作方法执行测试，比如检查元素序列是否符合预期，检查是否有指定错误，测试操作时间等。最后执行 ```verify()```操作，整个测试才会执行，不执行```verify()```,不会进行任何操作
```java
StepVerifier.create(T<Publisher>).{expectations...}.verify()
```

### 调试
一般如果程序出现问题，通常会去看 ```stack trace``` ,看是哪一部分代码出现了问题，是什么问题。 
比如下面这段代码
```java
    public void error(){
        int i = 1/0 ;
    }
```
执行后，立即可以定位到错误所在
```java
java.lang.ArithmeticException: / by zero

	at com.erzhiqianyi.blog.dao.reactor.basic.Part03Debug.error(Part03Debug.java:26)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.error(Part03DebugTest.java:28)
```
在 ```Steam流```中出现问题时，也可以快速定位，如下面这段代码
```java
    public List<String> getStreamWithException(){
        return Stream.of("one","two","on")
                .map(item -> item.substring(0,3))
                .collect(Collectors.toList());
    }

```
异常信息如下
```java
java.lang.StringIndexOutOfBoundsException: begin 0, end 3, length 2

	at java.base/java.lang.String.checkBoundsBeginEnd(String.java:3107)
	at java.base/java.lang.String.substring(String.java:1873)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03Debug.lambda$getStreamWithException$2(Part03Debug.java:21)
	at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
	at java.base/java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
	at java.base/java.util.stream.AbstractPipeline.copyInto
```
在反应式编程中，效果就不一样了。比如下面这段代码
```java
 public Mono<Integer> getMonoWithException() {
        return Flux.<Integer>range(1, 5)
                .map(i -> i * i)
                .filter(i -> (i % 2) == 0)
                .single();
    }
```
运行后结果如下
```java
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IndexOutOfBoundsException: Source emitted more than one item

Caused by: java.lang.IndexOutOfBoundsException: Source emitted more than one item
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onNext(MonoSingle.java:129)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.tryOnNext(FluxFilterFuseable.java:143)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.tryOnNext(FluxMapFuseable.java:303)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.fastPath(FluxRange.java:278)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.request(FluxRange.java:256)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.request(FluxMapFuseable.java:346)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:185)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.request(MonoSingle.java:94)
	at reactor.core.publisher.LambdaMonoSubscriber.onSubscribe(LambdaMonoSubscriber.java:87)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onSubscribe(MonoSingle.java:114)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:82)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onSubscribe(FluxMapFuseable.java:255)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:65)
	at reactor.core.publisher.FluxMapFuseable.subscribe(FluxMapFuseable.java:60)
	at reactor.core.publisher.FluxFilterFuseable.subscribe(FluxFilterFuseable.java:52)
	at reactor.core.publisher.MonoSingle.subscribe(MonoSingle.java:58)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3848)
	at reactor.core.publisher.Mono.subscribeWith(Mono.java:3954)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3733)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.getMonoWithException(Part03DebugTest.java:18)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
```
错误信息不是很明显，即使使用开发工具进行断点，也难以定位到问题所在。为了更好帮助开发者进行调试，```Reactor``` 提供了相应的辅助功能。
#### 启用调试模式
Reactor提供了开启调试模式的方法。
```
Hooks.onOperatorDebug();
```
这个方法能够开启调试模式，从而在抛出异常时打印出一些有用的信息。下面加上开启调试功能
```java
    @Test
    public void getMonoWithException() {
        Hooks.onOperatorDebug();
        part03Debug.getMonoWithException()
                .subscribe();
    }
```
错误信息如下
```java
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IndexOutOfBoundsException: Source emitted more than one item

Caused by: java.lang.IndexOutOfBoundsException: Source emitted more than one item
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onNext(MonoSingle.java:129)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:345)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.tryOnNext(FluxFilterFuseable.java:143)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblyConditionalSubscriber.tryOnNext(FluxOnAssembly.java:476)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.tryOnNext(FluxMapFuseable.java:303)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblyConditionalSubscriber.tryOnNext(FluxOnAssembly.java:476)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.fastPath(FluxRange.java:278)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.request(FluxRange.java:256)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:442)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.request(FluxMapFuseable.java:346)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:442)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:185)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:442)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.request(MonoSingle.java:94)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:442)
	at reactor.core.publisher.LambdaMonoSubscriber.onSubscribe(LambdaMonoSubscriber.java:87)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:426)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onSubscribe(MonoSingle.java:114)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:426)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:82)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:426)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onSubscribe(FluxMapFuseable.java:255)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:426)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:65)
	at reactor.core.publisher.FluxOnAssembly.subscribe(FluxOnAssembly.java:117)
	at reactor.core.publisher.FluxMapFuseable.subscribe(FluxMapFuseable.java:60)
	at reactor.core.publisher.FluxOnAssembly.subscribe(FluxOnAssembly.java:117)
	at reactor.core.publisher.FluxFilterFuseable.subscribe(FluxFilterFuseable.java:52)
	at reactor.core.publisher.FluxOnAssembly.subscribe(FluxOnAssembly.java:122)
	at reactor.core.publisher.MonoSingle.subscribe(MonoSingle.java:58)
	at reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:61)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3848)
	at reactor.core.publisher.Mono.subscribeWith(Mono.java:3954)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3733)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.getMonoWithException(Part03DebugTest.java:19)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
Assembly trace from producer [reactor.core.publisher.MonoSingle] :
	reactor.core.publisher.Flux.single(Flux.java:7507)
	com.erzhiqianyi.blog.dao.reactor.basic.Part03Debug.getMonoWithException(Part03Debug.java:16)
Error has been observed by the following operator(s):
	|_	Flux.single ⇢ com.erzhiqianyi.blog.dao.reactor.basic.Part03Debug.getMonoWithException(Part03Debug.java:16)
```
可以快速定位到com.erzhiqianyi.blog.dao.reactor.basic.Part03Debug.getMonoWithException(Part03Debug.java:16) 第16行的single()只能接收一个参数，这里右边多个结果，导致报错。
Hooks.onOperatorDebug()的实现原理在于在组装期包装各个操作符的构造方法，加入一些监测功能，所以这个 hook 应该在早于声明的时候被激活，最保险的方式就是在你程序的最开始就激活它。
当然开启调试会影响程序性能，最好在出现问题后再开启调试模式。

#### 使用检查点
还可以使用 ```checkpoint()``` 单独开启调试模式，初步定位到问题链后，在问题链上添加 ```checkpoint()``` ,即可对该链开启调试模式，局部调试，而非全局调试。

```java
    @Test
    public  void checkPoint(){
        part03Debug.getMonoWithException()
                .checkpoint("test")
                .subscribe();
    }
```
错误信息如下 
```java
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IndexOutOfBoundsException: Source emitted more than one item

Caused by: java.lang.IndexOutOfBoundsException: Source emitted more than one item
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onNext(MonoSingle.java:129)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.tryOnNext(FluxFilterFuseable.java:143)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.tryOnNext(FluxMapFuseable.java:303)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.fastPath(FluxRange.java:278)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.request(FluxRange.java:256)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.request(FluxMapFuseable.java:346)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:185)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.request(MonoSingle.java:94)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:442)
	at reactor.core.publisher.LambdaMonoSubscriber.onSubscribe(LambdaMonoSubscriber.java:87)
	at reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:426)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onSubscribe(MonoSingle.java:114)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:82)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onSubscribe(FluxMapFuseable.java:255)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:65)
	at reactor.core.publisher.FluxMapFuseable.subscribe(FluxMapFuseable.java:60)
	at reactor.core.publisher.FluxFilterFuseable.subscribe(FluxFilterFuseable.java:52)
	at reactor.core.publisher.MonoSingle.subscribe(MonoSingle.java:58)
	at reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:61)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3848)
	at reactor.core.publisher.Mono.subscribeWith(Mono.java:3954)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3733)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.checkPoint(Part03DebugTest.java:36)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
Assembly trace from producer [reactor.core.publisher.MonoSingle] :
	reactor.core.publisher.Mono.checkpoint(Mono.java:1681)
	com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.checkPoint(Part03DebugTest.java:35)
Error has been observed by the following operator(s):
	|_	Mono.checkpoint ⇢ com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.checkPoint(Part03DebugTest.java:35)
```
#### 日志记录
系统日志也是重要的调试工具，在 ```Reactor``` 可以用 ```log()``` 操作符来记录各种事件。

```log```操作符可以通过 ```SLF4J``` 使用类似 ```Log4J``` 和 ```Logback``` 这样的公共的日志工具来记录日志，如果SLF4J不存在的话，则直接将日志输出到控制台。
下面这段测试代码启用日志记录 
```java
    @Test
    public void log() {
        part03Debug.getMonoWithException()
                .log("GET")
                .subscribe();
    }
```
日志记录如下
```
11:04:48.857 [main] INFO GET - | onSubscribe([Fuseable] MonoSingle.SingleSubscriber)
11:04:48.860 [main] INFO GET - | request(unbounded)
11:04:48.864 [main] ERROR GET - | onError(java.lang.IndexOutOfBoundsException: Source emitted more than one item)
11:04:48.868 [main] ERROR GET - 
java.lang.IndexOutOfBoundsException: Source emitted more than one item
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onNext(MonoSingle.java:129)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.tryOnNext(FluxFilterFuseable.java:143)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.tryOnNext(FluxMapFuseable.java:303)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.fastPath(FluxRange.java:278)
	at reactor.core.publisher.FluxRange$RangeSubscriptionConditional.request(FluxRange.java:256)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.request(FluxMapFuseable.java:346)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.request(FluxFilterFuseable.java:185)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.request(MonoSingle.java:94)
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableSubscriber.request(FluxPeekFuseable.java:138)
	at reactor.core.publisher.LambdaMonoSubscriber.onSubscribe(LambdaMonoSubscriber.java:87)
	at reactor.core.publisher.FluxPeekFuseable$PeekFuseableSubscriber.onSubscribe(FluxPeekFuseable.java:172)
	at reactor.core.publisher.MonoSingle$SingleSubscriber.onSubscribe(MonoSingle.java:114)
	at reactor.core.publisher.FluxFilterFuseable$FilterFuseableSubscriber.onSubscribe(FluxFilterFuseable.java:82)
	at reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onSubscribe(FluxMapFuseable.java:255)
	at reactor.core.publisher.FluxRange.subscribe(FluxRange.java:65)
	at reactor.core.publisher.FluxMapFuseable.subscribe(FluxMapFuseable.java:60)
	at reactor.core.publisher.FluxFilterFuseable.subscribe(FluxFilterFuseable.java:52)
	at reactor.core.publisher.MonoSingle.subscribe(MonoSingle.java:58)
	at reactor.core.publisher.MonoLogFuseable.subscribe(MonoLogFuseable.java:53)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3848)
	at reactor.core.publisher.Mono.subscribeWith(Mono.java:3954)
	at reactor.core.publisher.Mono.subscribe(Mono.java:3733)
	at com.erzhiqianyi.blog.dao.reactor.basic.Part03DebugTest.log(Part03DebugTest.java:44)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
```
### 操作符
```Reactor Steam```作为一种，和 java8 中的 ```Stream``` 类似，提供了很多操作符，方便对流进行操作。
操作符实践，可以参考官方提供的 [lite-rx-api-hands-on](https://github.com/reactor/lite-rx-api-hands-on/tree/master/src/test/java/io/pivotal/literx), 完成里面的todo ,然会执行test。
#### 创建序列

#####  just
- 发出一个 T，已经有数据
```java 
	public static <T> Mono<T> just(T data) {
		return onAssembly(new MonoJust<>(data));
	}
```
![](svg/just.svg)

- 发出许多 T，可以明确列举出来
 ```java
 	public static <T> Flux<T> just(T... data) {
		return fromArray(data);
	}
 ```
 ![](svg/justMultiple.svg)


##### justOrEmpty 
基于一个 Optional<T>
```java
  	public static <T> Mono<T> justOrEmpty(@Nullable Optional<? extends T> data) {
		return data != null && data.isPresent() ? just(data.get()) : empty();
	}
```
![](svg/justOrEmpty.svg)

基于一个可能为 null 的 T
 ```java
	public static <T> Mono<T> justOrEmpty(@Nullable Optional<? extends T> data) {
		return data != null && data.isPresent() ? just(data.get()) : empty();
	}
 ```
![](svg/justOrEmpty.svg)
#####  fromSupplier
- 发出一个 T，且还是由 just 方法返回, 但是“懒”创建的 
 ```java
	public static <T> Mono<T> fromSupplier(Supplier<? extends T> supplier) {
		return onAssembly(new MonoSupplier<>(supplier));
	}

 ```
![](svg/fromSupplier.svg)

##### fromArray
- 基于一个数组发出T
 ```java
 	public static <T> Flux<T> fromArray(T[] array) {
		if (array.length == 0) {
			return empty();
		}
		if (array.length == 1) {
			return just(array[0]);
		}
		return onAssembly(new FluxArray<>(array));
	}

 ```
 ![](svg/fromArray.svg)

##### fromIterable
-  基于一个集合或 iterable 发出 T 
 ```java
 	public static <T> Flux<T> fromIterable(Iterable<? extends T> it) {
			return onAssembly(new FluxIterable<>(it));
		}
 ```
 ![](svg/fromIterable.svg)

##### range
- 一个 Integer 的 range,发出指定范围内的数字 
```java
	public static Flux<Integer> range(int start, int count) {
		if (count == 1) {
			return just(start);
		}
		if (count == 0) {
			return empty();
		}
		return onAssembly(new FluxRange(start, count));
	}
```
![](svg/range.svg)

##### fromStream
- 一个 Stream 提供给每一个订阅
```java
	public static <T> Flux<T> fromStream(Stream<? extends T> s) {
		Objects.requireNonNull(s, "Stream s must be provided");
		return onAssembly(new FluxStream<>(() -> s));
	}

```
![](svg/fromStream.svg)

##### fromCallable
- 基于任务结果发出T
```java
	public static <T> Mono<T> fromCallable(Callable<? extends T> supplier) {
		return onAssembly(new MonoCallable<>(supplier));
	}


```
![](svg/fromCallable.svg)
##### fromRunnable 
- 执行任务，没有返回结果 
```java
	public static <T> Mono<T> fromRunnable(Runnable runnable) {
		return onAssembly(new MonoRunnable<>(runnable));
	}
```
![](svg/fromRunnable.svg)

##### fromFuture
- 一个 CompletableFuture<T>
```java
	public static <T> Mono<T> fromFuture(CompletableFuture<? extends T> future) {
		return onAssembly(new MonoCompletionStage<>(future));
	}
```
![](svg/fromFuture.svg)

##### empty
- 直接完成
```java
	public static <T> Mono<T> empty() {
		return MonoEmpty.instance();
	}

	public static <T> Flux<T> empty() {
		return FluxEmpty.instance();
	}
```
![](svg/empty.svg)

##### error
- 立即生成错误
```java
    public static <T> Mono<T> error(Throwable error) {
		return onAssembly(new MonoError<>(error));
	}

	public static <T> Flux<T> error(Throwable error) {
		return error(error, false);
	}
```
![](svg/error.svg)

- 什么都不做：never
```java
	public static <T> Mono<T> never() {
		return MonoNever.instance();
	}

	public static <T> Flux<T> never() {
		return FluxNever.instance();
	}

```
![](svg/never.svg)

##### defer
- 订阅时才计算

```java
	public static <T> Mono<T> defer(Supplier<? extends Mono<? extends T>> supplier) {
		return onAssembly(new MonoDefer<>(supplier));
	}

  	public static <T> Flux<T> defer(Supplier<? extends Publisher<T>> supplier) {
		return onAssembly(new FluxDefer<>(supplier));
	}

```

![](svg/deferForMono.svg)
![](svg/deferForFlux.svg)
##### using
- 依赖一个可回收的资源,先获取资源，再用资源生成发布者，然后清理资源
```java
	public static <T, D> Mono<T> using(Callable<? extends D> resourceSupplier,
			Function<? super D, ? extends Mono<? extends T>> sourceSupplier,
			Consumer<? super D> resourceCleanup,
			boolean eager) {
		return onAssembly(new MonoUsing<>(resourceSupplier, sourceSupplier,
				resourceCleanup, eager));
	}

    public static <T, D> Flux<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends
			Publisher<? extends T>> sourceSupplier, Consumer<? super D> resourceCleanup) {
		return using(resourceSupplier, sourceSupplier, resourceCleanup, true);
	}

```
![](svg/usingForMono.svg)
![](svg/usingForFlux.svg)

##### generate
- 可编程地生成事件（可以使用状态）: 
```java
	public static <T> Flux<T> generate(Consumer<SynchronousSink<T>> generator) {
		Objects.requireNonNull(generator, "generator");
		return onAssembly(new FluxGenerate<>(generator));
	}

```
![](/svg/generateStateless.svg)

##### create
- 异步（也可同步）的，每次尽可能多发出元素：
```java
	public static <T> Mono<T> create(Consumer<MonoSink<T>> callback) {
	    return onAssembly(new MonoCreate<>(callback));
	}

	public static <T> Flux<T> create(Consumer<? super FluxSink<T>> emitter) {
	    return create(emitter, OverflowStrategy.BUFFER);
    }
```
![](svg/createForFlux.svg)

#### 转换序列
##### map
- 1对1地转化（比如字符串转化为它的长度）
```java
    public final <R> Mono<R> map(Function<? super T, ? extends R> mapper) {
		if (this instanceof Fuseable) {
			return onAssembly(new MonoMapFuseable<>(this, mapper));
		}
		return onAssembly(new MonoMap<>(this, mapper));
	}
```
![](svg/mapForMono.svg)

```java
    public final <V> Flux<V> map(Function<? super T, ? extends V> mapper) {
		if (this instanceof Fuseable) {
			return onAssembly(new FluxMapFuseable<>(this, mapper));
		}
		return onAssembly(new FluxMap<>(this, mapper));
	}
```

![](svg/mapForFlux.svg)
##### cast
- 类型转化
```java
    public final <E> Mono<E> cast(Class<E> clazz) {
		Objects.requireNonNull(clazz, "clazz");
		return map(clazz::cast);
	}
```
![](svg/castForMono.svg)

```java
    public final <E> Flux<E> cast(Class<E> clazz) {
		Objects.requireNonNull(clazz, "clazz");
		return map(clazz::cast);
	}
```
![](svg/castForFlux.svg)

##### index
- 获取每个元素的序号
```java
	public final Flux<Tuple2<Long, T>> index() {
		//noinspection unchecked
		return index(TUPLE2_BIFUNCTION);
	}
```
![](svg/indexWithMapper.svg)

##### flatMap
```java
	public final <R> Mono<R> flatMap(Function<? super T, ? extends Mono<? extends R>>
			transformer) {
		return onAssembly(new MonoFlatMap<>(this, transformer));
	}
```
![](svg/flatMapForMono.svg)

```java
	public final <R> Flux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper) {
		return flatMap(mapper, Queues.SMALL_BUFFER_SIZE, Queues
				.XS_BUFFER_SIZE);
	}

```
![](svg/flatMapForFlux.svg)

- 1对n地转化
```
flatMap + 使用一个工厂方法
```
比如将字符串转为一串字符
```java
 return Flux.just(str).flatMap(item -> Flux.just(item.split("")));
```

- 1对n地转化可自定义转化方法和/或状态：handle
	对每一个元素执行一个异步操作（如对 url 执行 http 请求）：flatMap + 一个异步的返回类型为 Publisher 的方法
```java
Flux.just("url").flatMap(item -> Mono.fromCallable(
                () -> {
                    System.out.println("flux:callable task executor: " + Thread.currentThread().getName());
                    return ("result:" + item);
                }));
```
- 忽略一些数据
	在 flatMap lambda 中根据条件返回一个 Mono.empty()
```java
  return Flux.just("one","two","three","four").flatMap(item -> {
            if (item.length() > 3) {
                return Mono.just(item);
            } else {
                return Mono.empty();
            }
        });
```
##### flatMapSequential 
- 保留原来的序列顺序,对每个元素的异步任务会立即执行，但会将结果按照原序列顺序排序）
```java
	public final <R> Flux<R> flatMapSequential(Function<? super T, ? extends
			Publisher<? extends R>> mapper) {
		return flatMapSequential(mapper, Queues.SMALL_BUFFER_SIZE);
	}
```
![](svg/flatMapSequential.svg)

##### flatMapMany 
-  Mono 元素的异步任务返回多个元素的序列
```java
	public final <R> Flux<R> flatMapMany(Function<? super T, ? extends Publisher<? extends R>> mapper) {
		return Flux.onAssembly(new MonoFlatMapMany<>(this, mapper));
	}

```
![](svg/flatMapMany.svg)

##### startWith 
- 在开头添加
```java
	public final Flux<T> startWith(T... values) {
		return startWith(just(values));
	}
```
![](svg/startWithIterable.svg)

##### concatWith
- 在最后添加
```java
	public final Flux<T> concatWith(Publisher<? extends T> other) {
		if (this instanceof FluxConcatArray) {
			@SuppressWarnings({ "unchecked" })
			FluxConcatArray<T> fluxConcatArray = (FluxConcatArray<T>) this;

			return fluxConcatArray.concatAdditionalSourceLast(other);
		}
		return concat(this, other);
	}

```
![](svg/concatWithForFlux.svg)

##### collectList 
- 转为list
```java
	public final Mono<List<T>> collectList() {
		if (this instanceof Callable) {
			if (this instanceof Fuseable.ScalarCallable) {
				@SuppressWarnings("unchecked")
				Fuseable.ScalarCallable<T> scalarCallable = (Fuseable.ScalarCallable<T>) this;

				T v;
				try {
					v = scalarCallable.call();
				}
				catch (Exception e) {
					return Mono.error(e);
				}
				if (v == null) {
					return Mono.onAssembly(new MonoSupplier<>(listSupplier()));
				}
				return Mono.just(v).map(u -> {
					List<T> list = Flux.<T>listSupplier().get();
					list.add(u);
					return list;
				});

			}
			@SuppressWarnings("unchecked")
			Callable<T> thiz = (Callable<T>)this;
			return Mono.onAssembly(new MonoCallable<>(thiz).map(u -> {
				List<T> list = Flux.<T>listSupplier().get();
				list.add(u);
				return list;
			}));
		}
		return Mono.onAssembly(new MonoCollectList<>(this));
	}
```
![](svg/collectList.svg)

##### collectSortedList 
```java
	public final Mono<List<T>> collectSortedList(@Nullable Comparator<? super T> comparator) {
		return collectList().map(list -> {
			// Note: this assumes the list emitted by buffer() is mutable
			if (comparator != null) {
				list.sort(comparator);
			} else {

				List<Comparable> l = (List<Comparable>)list;
				Collections.sort(l);
			}
			return list;
		});
	}
```
![](svg/collectSortedList.svg)

##### collectMap 
- 转为map,和 ```Stream``` 中的 
```java
    public static <T, K, U>
    Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper,
                                    Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), HashMap::new);
    }

```

```java
	public final <K, V> Mono<Map<K, V>> collectMap(Function<? super T, ? extends K> keyExtractor,
			Function<? super T, ? extends V> valueExtractor) {
		return collectMap(keyExtractor, valueExtractor, () -> new HashMap<>());
	}

```
![](svg/collectMapWithKeyExtractor.svg)

##### collectMultiMap 
- map分组,相当于 ```Stream``` 中的
```java
 public static <T, K> Collector<T, ?, Map<K, List<T>>>
    groupingBy(Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, toList());
    }
```

```java
	public final <K> Mono<Map<K, Collection<T>>> collectMultimap(Function<? super T, ? extends K> keyExtractor) {
		return collectMultimap(keyExtractor, identityFunction());
	}

```
![](svg/collectMultiMapWithKeyExtractor.svg)

##### count
##### reduce 
##### scan 
##### all
##### hasElements
##### hasElement
##### concat  
##### concatWith
##### concatDelayError
- 转化为 List：collectList，collectSortedList
- 转化为 Map：collectMap，collectMultiMap
- 转化为自定义集合：collect
- 计数：count
- reduce 算法（将上个元素的reduce结果与当前元素值作为输入执行reduce方法，如sum） reduce
- 每次 reduce 的结果立即发出：scan
转化为一个 boolean 值：
- 对所有元素判断都为true：all
- 对至少一个元素判断为true：any
- 判断序列是否有元素（不为空）：hasElements
- 判断序列中是否有匹配的元素：hasElement

- 按序连接：Flux#concat 或 .concatWith(other)
- 即使有错误，也会等所有的 publishers 连接完成：Flux#concatDelayError
- 按订阅顺序连接（这里的合并仍然可以理解成序列的连接）：Flux#mergeSequential
- 按元素发出的顺序合并（无论哪个序列的，元素先到先合并）：Flux#merge / .mergeWith(other)
- 元素类型会发生变化：Flux#zip / Flux#zipWith
- 2个 Monos 组成1个 Tuple2：Mono#zipWith
n个 Monos 的元素都发出来后组成一个 Tuple：Mono#zip
- 在终止信号出现时“采取行动”：
在 Mono 终止时转换为一个 Mono<Void>：Mono#and
- 当 n 个 Mono 都终止时返回 Mono<Void>：Mono#when
返回一个存放组合数据的类型，对于被合并的多个序列： 
每个序列都发出一个元素时：Flux#zip
任何一个序列发出元素时：Flux#combineLatest
只取各个序列的第一个元素：Flux#first，Mono#first，mono.or 
(otherMono).or(thirdMono)，`flux.or(otherFlux).or(thirdFlux)
由一个序列触发（类似于 flatMap，不过“喜新厌旧”）：switchMap
由每个新序列开始时触发（也是“喜新厌旧”风格）：switchOnNext
我想重复一个序列：repeat

…但是以一定的间隔重复：Flux.interval(duration).flatMap(tick -> myExistingPublisher)
我有一个空序列，但是…

我想要一个缺省值来代替：defaultIfEmpty
我想要一个缺省的序列来代替：switchIfEmpty
我有一个序列，但是我对序列的元素值不感兴趣：ignoreElements

…并且我希望用 Mono 来表示序列已经结束：then
…并且我想在序列结束后等待另一个任务完成：thenEmpty
…并且我想在序列结束之后返回一个 Mono：Mono#then(mono)
…并且我想在序列结束之后返回一个值：Mono#thenReturn(T)
…并且我想在序列结束之后返回一个 Flux：thenMany
我有一个 Mono 但我想延迟完成…

…当有1个或N个其他 publishers 都发出（或结束）时才完成：Mono#delayUntilOther
…使用一个函数式来定义如何获取“其他 publisher”：Mono#delayUntil(Function)
我想基于一个递归的生成序列的规则扩展每一个元素，然后合并为一个序列发出：

…广度优先：expand(Function)
…深度优先：expandDeep(Function)
--------------------- 
#### 只读序列
#### 过虑序列
#### 错误处理
#### 基于时间的操作
#### 拆分 Flux
#### 回到同步的世界
### 调度器
### 消息处理
### “懒”和“急”
```Reactor sources``` 有懒和急的区别。```just``` 是急，```defer``` 是懒。像 ```Http``` 请求，应该要懒处理。

比如调用 ```Mono.just(System.currentTimeMillis())``` 会立即调用 ```System.currentTimeMillis())```,并且立即
计算结果，结果已经计算完毕，多次订阅，最终得到的结果是一样的。
```java
    @Test
    public void just() throws InterruptedException {
        Mono<Long> clock = Mono.just(System.currentTimeMillis());
        clock.subscribe(System.out::println);
        //time == t0

        Thread.sleep(1_000);
        //time == t10
        clock.subscribe(System.out::println);
        clock.block(); //we use block for demonstration purposes, returns t0

        clock.subscribe(System.out::println);
        Thread.sleep(7_000);
        //time == t17
        clock.subscribe(System.out::println);

        clock.block(); //we re-subscribe to clock, still returns t0
        clock.subscribe(System.out::println);

    }

```

```defer``` 则不同，不会立即计算，有订阅者时才会计算，每增加一个新的订阅者，都会重新计算一次。
```java
    @Test
    public void defer() throws InterruptedException {
        Mono<Long> clock = Mono.defer(() -> Mono.just(System.currentTimeMillis()));
        //time == t0

        clock.subscribe(System.out::println);
        Thread.sleep(1_000);
        //time == t10
        clock.block(); //invoked currentTimeMillis() here and returns t10
        clock.subscribe(System.out::println);

        Thread.sleep(7_000);
        //time == t17
        clock.block(); //invoke currentTimeMillis() once again here and returns t17
        clock.subscribe(System.out::println);
    }
```
### 参考文章
本文参考了以下内容
- [Reactor文档](https://projectreactor.io/learn)
- [使用 Reactor 进行反应式编程](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)
- [响应式Spring的道法术器（Spring WebFlux 教程）](https://blog.csdn.net/get_set/article/details/79466657)
- [Introduction to Reactive Programming](https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Intro)
- [what does Mono.defer() do?](https://stackoverflow.com/questions/55955567/what-does-mono-defer-do)
- [Reactor Core ](https://projectreactor.io/docs/core/release/api/overview-summary.html)