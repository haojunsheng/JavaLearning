<!--ts-->
   * [前情提要](#前情提要)
   * [本文概述](#本文概述)
   * [1. 继承Thread类创建线程](#1-继承thread类创建线程)
   * [2. 实现Runnable接口创建线程](#2-实现runnable接口创建线程)
   * [3. 通过Callable和FutureTask创建线程](#3-通过callable和futuretask创建线程)
   * [4. 通过线程池创建线程](#4-通过线程池创建线程)

<!-- Added by: anapodoton, at: Fri Feb 21 23:34:15 CST 2020 -->

<!--te-->

在前面的章节中，我们介绍了和线程安全有关的概念，我们知道，线程是执行的基本单元。同一个进程中可以包含多个线程。

# 前情提要

- 1、线程的特点
  - 在多线程操作系统中，通常是在一个进程中包括多个线程，每个线程都是作为利用CPU的基本单位，是花费最小开销的实体。是一个独立调度和分派的基本单位，可并发执行并且是共享进程资源的。
- 2、线程的实现
  - 主流的操作系统都提供了线程实现，实现线程主要有3种方式：使用内核线程实现、使用用户线程实现和使用用户线程加轻量级进程混合实现。
- 3、Java线程的实现
  - Java线程在JDK 1.2之前，是基于称为“绿色线程”（Green Threads）的用户线程实现的，而在JDK 1.2中，线程模型替换为基于操作系统原生线程模型来实现。
  - 在目前的JDK版本中，操作系统支持怎样的线程模型，在很大程度上决定了Java虚拟机的线程是怎样映射的，这点在不同的平台上没有办法达成一致，虚拟机规范中也并未限定Java线程需要使用哪种线程模型来实现。
- 4、线程状态
  - 线程是有状态的，并且这些状态之间也是可以互相流转的。Java中线程的状态分为6种：初始(NEW)、运行(RUNNABLE)、阻塞(BLOCKED)、等待(WAITING)、超时等待(TIMED_WAITING)和终止(TERMINATED)。
  - 其中运行(RUNNABLE)包含两种子状态，分别是就绪（READY）和运行中（RUNNING）
- 5、线程调度
  - 一个线程想要从就绪状态变成运行中状态，这个过程需要系统调度，即给线程分配CPU的使用权，获得CPU使用权的线程才会从就绪状态变成运行状态。给多个线程按照特定的机制分配CPU的使用权的过程就叫做线程调度。
  - 不同的操作系统，有不同的线程调度策略。Java程序都是运行在Java虚拟机上面的，而虚拟机帮我们屏蔽了操作系统的差异。在Java的多线程程序中，为保证所有线程的执行能按照一定的规则执行，JVM实现了一个线程调度器，它定义了线程调度模型，对于CPU运算的分配都进行了规定，按照这些特定的机制为多个线程分配CPU的使用权。
  - 主要有两种调度模型：协同式线程调度和抢占式调度模型。Java虚拟机采用抢占式调度模型。
- 6、线程优先级
  - 虽然Java线程调度是系统自动完成的，但是我们还是可以通过设置优先级来“建议”系统给某些线程多分配一点执行时间，另外的一些线程则可以少分配一点
  - Java语言一共设置了10个级别的线程优先级（Thread.MIN_PRIORITY至Thread.MAX_PRIORITY），可以使用Thread类的setPriority()方法为线程设置了新的优先级。getPriority()方法返回线程的当前优先级。
- 7、守护线程
  - 在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程) 。守护线程也就是“后台线程”，一般用来执行后台任务，守护线程最典型的应用就是GC(垃圾回收器)。
  - 可以通过使用setDaemon()方法通过传递true作为参数，可以使用isDaemon()方法来检查线程是否是守护线程。
  - Java虚拟机在所有“用户线程”都结束后就会退出。不会等待守护线程的执行。
- 8、ThreadLocal
  - ThreadLocal存放的值是线程内共享的，线程间互斥的，主要用于线程内共享一些数据，避免通过参数来传递，这样处理后，能够优雅的解决一些实际问题。
- 9、线程池
  - 线程过多会带来调度开销，进而影响缓存局部性和整体性能。而线程池维护着多个线程，等待着监督管理者分配可并发执行的任务。这避免了在处理短时间任务时创建与销毁线程的代价。

# 本文概述

在Java语言中，当我们运行一个main方法的时候，就会创建一个main线程。那么，如何在Java中实现多线程呢？或者说，除了main线程以外，开发者如何创建出更多的线程的？

前面区分开了进程和线程之后，我们也就知道了，线程是执行的基本单元。同一个进程中可以包含多个线程。

在Java语言中，当我们运行一个main方法的时候，就会创建一个main线程。在Java中，共有四种方式可以创建线程，分别是继承Thread类创建线程、实现Runnable接口创建线程、通过Callable和FutureTask创建线程以及通过线程池创建线程。

# 1. 继承Thread类创建线程

```java
public class MultiThreads {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        System.out.println("继承Thread类创建线程");
        SubClassThread subClassThread = new SubClassThread();
        subClassThread.start();  
    }
}

class SubClassThread extends Thread {

    @Override
    public void run() {
        System.out.println(getName());
    }
}
```

输出结果：

![image-20200222144504501](img/image-20200222144504501.png)

SubClassThread是一个继承了Thread类的子类，继承Thread类，并重写其中的run方法。然后new 一个SubClassThread的对象，并调用其start方法，即可启动一个线程。之后就会运行run中的代码。

每个线程都是通过某个特定Thread对象所对应的方法`run()`来完成其操作的，方法`run()`称为线程体。通过调用Thread类的`start()`方法来启动一个线程。

在主线程中，调用了子线程的`start()`方法后，主线程无需等待子线程的执行，即可执行后续的代码。而子线程便会开始执行其`run()`方法。

当然，`run()`方法也是一个公有方法，在main函数中也可以直接调用这个方法，但是直接调用`run()`的话，主线程就需要等待其执行完，这种情况下，`run()`就是一个普通方法。

如果读者感兴趣的话，查看一下前面介绍的Thread的源码，就可以发现，他继承了一个接口，那就是`java.lang.Runnable`，其实，开发者在代码中也可以直接通过这个接口创建一个新的线程。

# 2. 实现Runnable接口创建线程

```java
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());


        System.out.println("实现Runnable接口创建线程");
        RunnableThread runnableThread = new RunnableThread();
        new Thread(runnableThread).start();

      }
}

class RunnableThread implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
```

输出结果：

![image-20200222144717237](img/image-20200222144717237.png)

通过实现接口，同样覆盖`run()`就可以创建一个新的线程了。

我们都知道，Java是不支持多继承的，所以，使用Runnbale接口的形式，就可以避免要多继承 。比如有一个类A，已经继承了类B，就无法再继承Thread类了，这时候要想实现多线程，就需要使用Runnable接口了。

除此之外，两者之间几乎无差别。

但是，这两种创建线程的方式，其实是有一个缺点的，那就是：在执行完任务之后无法获取执行结果。

如果我们希望再主线程中得到子线程的执行结果的话，就需要用到Callable和FutureTask

# 3. 通过Callable和FutureTask创建线程

自从Java 1.5开始，提供了Callable和Future，通过它们可以在任务执行完毕之后得到任务执行结果。

```java
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException,ExecutionException {
        System.out.println(Thread.currentThread().getName());
        System.out.println("通过Callable和FutureTask创建线程");
        CallableThread callableThread = new CallableThread();
        FutureTask futureTask = new FutureTask<>(callableThread);
        new Thread(futureTask).start();
        System.out.println(futureTask.get());
    }
}

class CallableThread implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        return "Hollis";
    }
}
```

输出结果：

![image-20200222150556043](img/image-20200222150556043.png)

Callable位于java.util.concurrent包下，它也是一个接口，在它里面也只声明了一个方法，只不过这个方法call()，和Runnable接口中的run()方法不同的是，call()方法有返回值。

以上代码中，我们在CallableThread的call方法中返回字符串”Hollis”，在主线程是可以获取到的。

FutureTask可用于异步获取执行结果或取消执行任务的场景。通过传入Callable的任务给FutureTask，直接调用其run方法或者放入线程池执行，之后可以在外部通过FutureTask的get方法异步获取执行结果，因此，FutureTask非常适合用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。

另外，FutureTask还可以确保即使调用了多次run方法，它都只会执行一次Runnable或者Callable任务，或者通过cancel取消FutureTask的执行等。

值得注意的是，`futureTask.get()`会阻塞主线程，一直等子线程执行完并返回后才能继续执行主线程后面的代码。

一般，在Callable执行完之前的这段时间，主线程可以先去做一些其他的事情，事情都做完之后，再获取Callable的返回结果。可以通过`isDone()`来判断子线程是否执行完。

以上代码改造下就是如下内容：

```java
public class MultiThreads {
    public static void main(String[] args) throws InterruptedException {
        CallableThread callableThread = new CallableThread();
        FutureTask futureTask = new FutureTask<>(callableThread);
        new Thread(futureTask).start();

        System.out.println("主线程先做其他重要的事情");
        if(!futureTask.isDone()){
            // 继续做其他事儿
        }
        System.out.println(future.get()); // 可能会阻塞等待结果
}
```

一般，我们会把Callable放到线程池中，然后让线程池去执行Callable中的代码。关于线程池前面介绍过了，是一种避免重复创建线程的开销的技术手段，线程池也可以用来创建线程。

# 4. 通过线程池创建线程

Java中提供了对线程池的支持，有很多种方式。Jdk提供给外部的接口也很简单。直接调用ThreadPoolExecutor构造一个就可以了：

```java
import java.util.concurrent.*;

public class MultiThreads {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());
        System.out.println("通过线程池创建线程");
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(10));
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
    }
}
```

输出结果：

![image-20200222151218023](img/image-20200222151218023.png)

所谓线程池本质是一个hashSet。多余的任务会放在阻塞队列中。

线程池的创建方式其实也有很多，也可以通过Executors静态工厂构建，但一般不建议。建议使用线程池来创建线程，并且建议使用带有ThreadFactory参数的ThreadPoolExecutor（需要依赖guava）构造方法设置线程名字，具体原因我们在后面的章节中在详细介绍。
