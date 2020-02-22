<!--ts-->

   * [1. 线程的特点](#1-线程的特点)
      * [1.1 轻型实体](#11-轻型实体)
      * [1.2 独立调度和分派的基本单位](#12-独立调度和分派的基本单位)
      * [1.3 可并发执行](#13-可并发执行)
      * [1.4 共享进程资源](#14-共享进程资源)
   * [2. 线程的实现](#2-线程的实现)
      * [2.1 使用内核线程实现](#21-使用内核线程实现)
      * [2.2 使用用户线程实现](#22-使用用户线程实现)
      * [2.3 使用用户线程加轻量级进程混合实现](#23-使用用户线程加轻量级进程混合实现)
      * [2.4 Java线程的实现](#24-java线程的实现)
   * [3. 线程的状态](#3-线程的状态)
      * [3.1 <strong>wait(), notify(), notifyAll()等方法介绍</strong>](#31-wait-notify-notifyall等方法介绍)
      * [3.2 为什么notify(), wait()等函数定义在Object中，而不是Thread中](#32-为什么notify-wait等函数定义在object中而不是thread中)
      * [3.3 <strong>yield()介绍</strong>](#33-yield介绍)
      * [3.4 yield() 与 wait()的比较](#34-yield-与-wait的比较)
      * [3.5 sleep()介绍](#35-sleep介绍)
      * [3.6 <strong>sleep() 与 wait()的比较</strong>](#36-sleep-与-wait的比较)
   * [4. 线程的调度](#4-线程的调度)
      * [4.1 Linux线程调度](#41-linux线程调度)
      * [4.2 Windows线程调度](#42-windows线程调度)
      * [4.3 Java线程调度](#43-java线程调度)
         * [4.3.1 协同式线程调度](#431-协同式线程调度)
         * [4.3.2 抢占式调度模型](#432-抢占式调度模型)
      * [4.4 小结](#44-小结)
   * [5. 线程优先级](#5-线程优先级)
   * [6. 守护线程](#6-守护线程)
   * [7. ThreadLocal](#7-threadlocal)
   * [8. 池化技术](#8-池化技术)
   * [9. 线程池](#9-线程池)

<!-- Added by: anapodoton, at: Fri Feb 21 23:44:18 CST 2020 -->

<!--te-->

# 1. 线程的特点

在多线程操作系统中，通常是在一个进程中包括多个线程，每个线程都是作为利用CPU的基本单位，是花费最小开销的实体。线程具有以下属性。

## 1.1 轻型实体

线程中的实体基本上不拥有系统资源，只是有一点必不可少的、能保证独立运行的资源。 线程的实体包括程序、数据和TCB。线程是动态概念，它的动态特性由线程控制块TCB（Thread Control Block）描述。TCB包括以下信息： （1）线程状态。 （2）当线程不运行时，被保存的现场资源。 （3）一组执行堆栈。 （4）存放每个线程的局部变量主存区。 （5）访问同一个进程中的主存和其它资源。 用于指示被执行指令序列的程序计数器、保留局部变量、少数状态参数和返回地址等的一组寄存器和堆栈。

## 1.2 独立调度和分派的基本单位

在多线程操作系统中，线程是能独立运行的基本单位，因而也是独立调度和分派的基本单位。由于线程很“轻”，故线程的切换非常迅速且开销小（在同一进程中的）。

## 1.3 可并发执行

在一个进程中的多个线程之间，可以并发执行，甚至允许在一个进程中所有线程都能并发执行；同样，不同进程中的线程也能并发执行，充分利用和发挥了处理机与外围设备并行工作的能力。

## 1.4 共享进程资源

在同一进程中的各个线程，都可以共享该进程所拥有的资源，这首先表现在：所有线程都具有相同的地址空间（进程的地址空间），这意味着，线程可以访问该地址空间的每一个虚地址；此外，还可以访问进程所拥有的已打开文件、定时器、信号量机构等。由于同一个进程内的线程共享内存和文件，所以线程之间互相通信不必调用内核。

# 2. 线程的实现

主流的操作系统都提供了线程实现，实现线程主要有3种方式：**使用内核线程实现、使用用户线程实现和使用用户线程加轻量级进程混合实现。**

## 2.1 使用内核线程实现

内核线程（Kernel-Level Thread,KLT）就是直接由操作系统内核（Kernel，下称内核）支持的线程，这种线程由内核来完成线程切换，内核通过操纵调度器（Scheduler）对线程进行调度，并负责将线程的任务映射到各个处理器上。每个内核线程可以视为内核的一个分身，这样操作系统就有能力同时处理多件事情，支持多线程的内核就叫做多线程内核（Multi-Threads Kernel）。

　　程序一般不会直接去使用内核线程，而是去使用内核线程的一种高级接口——轻量级进程（Light Weight Process,LWP），轻量级进程就是我们通常意义上所讲的线程，由于每个轻量级进程都由一个内核线程支持，因此只有先支持内核线程，才能有轻量级进程。这种**轻量级进程与内核线程之间1:1的关系称为一对一的线程模型**，如图所示。

![img](img/15442554190788.jpg)￼

　　由于内核线程的支持，每个轻量级进程都成为一个独立的调度单元，即使有一个轻量级进程在系统调用中阻塞了，也不会影响整个进程继续工作，但是轻量级进程具有它的局限性：首先，由于是基于内核线程实现的，所以各种线程操作，如创建、析构及同步，都需要进行系统调用。而系统调用的代价相对较高，需要在用户态（User Mode）和内核态（Kernel Mode）中来回切换。其次，每个轻量级进程都需要有一个内核线程的支持，因此轻量级进程要消耗一定的内核资源（如内核线程的栈空间），因此一个系统支持轻量级进程的数量是有限的。

## 2.2 使用用户线程实现

　　从广义上来讲，一个线程只要不是内核线程，就可以认为是用户线程（User Thread,UT），因此，从这个定义上来讲，轻量级进程也属于用户线程，但轻量级进程的实现始终是建立在内核之上的，许多操作都要进行系统调用，效率会受到限制。

　　而狭义上的用户线程指的是完全建立在用户空间的线程库上，系统内核不能感知线程存在的实现。用户线程的建立、同步、销毁和调度完全在用户态中完成，不需要内核的帮助。如果程序实现得当，这种线程不需要切换到内核态，因此操作可以是非常快速且低消耗的，也可以支持规模更大的线程数量，部分高性能数据库中的多线程就是由用户线程实现的。这种进程与用户线程之间1：N的关系称为一对多的线程模型，如图所示。

![img](img/15442554407298.jpg)￼

　　使用用户线程的优势在于不需要系统内核支援，**劣势也在于没有系统内核的支援，所有的线程操作都需要用户程序自己处理。线程的创建、切换和调度都是需要考虑的问题，而且由于操作系统只把处理器资源分配到进程，那诸如“阻塞如何处理”、“多处理器系统中如何将线程映射到其他处理器上”这类问题解决起来将会异常困难，甚至不可能完成。**因而使用用户线程实现的程序一般都比较复杂 ，除了以前在不支持多线程的操作系统中（如DOS）的多线程程序与少数有特殊需求的程序外，现在使用用户线程的程序越来越少了，Java、Ruby等语言都曾经使用过用户线程，最终又都放弃使用它。

## 2.3 使用用户线程加轻量级进程混合实现

线程除了依赖内核线程实现和完全由用户程序自己实现之外，还有一种将内核线程与用户线程一起使用的实现方式。在这种混合实现下，既存在用户线程，也存在轻量级进程。**用户线程还是完全建立在用户空间中，因此用户线程的创建、切换、析构等操作依然廉价，并且可以支持大规模的用户线程并发。**而操作系统提供支持的轻量级进程则作为用户线程和内核线程之间的桥梁，这样可以使用内核提供的线程调度功能及处理器映射，并且用户线程的系统调用要通过轻量级线程来完成，大大降低了整个进程被完全阻塞的风险。在这种混合模式中，用户线程与轻量级进程的数量比是不定的，即为N：M的关系，如图12-5所示，这种就是多对多的线程模型。

许多UNIX系列的操作系统，如Solaris、HP-UX等都提供了N：M的线程模型实现。

这个玩意在Go里面叫做协程。

![img](img/15442554705166.jpg)￼

## 2.4 Java线程的实现

　　 Java线程在JDK 1.2之前，是基于称为“绿色线程”（Green Threads）的用户线程实现的，而在JDK 1.2中，**线程模型替换为基于操作系统原生线程模型来实现。**因此，在目前的JDK版本中，操作系统支持怎样的线程模型，在很大程度上决定了Java虚拟机的线程是怎样映射的，这点在不同的平台上没有办法达成一致，虚拟机规范中也并未限定Java线程需要使用哪种线程模型来实现。线程模型只对线程的并发规模和操作成本产生影响，对Java程序的编码和运行过程来说，这些差异都是透明的。

　　对于Sun JDK来说，它的Windows版与Linux版都是**使用一对一的线程模型实现的，一条Java线程就映射到一条轻量级进程之中，因为Windows和Linux系统提供的线程模型就是一对一的。**

　　而在Solaris平台中，由于操作系统的线程特性可以同时支持一对一（通过Bound Threads或Alternate Libthread实现）及多对多（通过LWP/Thread Based Synchronization实现）的线程模型，因此在Solaris版的JDK中也对应提供了两个平台专有的虚拟机参数：-XX：+UseLWPSynchronization（默认值）和-XX：+UseBoundThreads来明确指定虚拟机使用哪种线程模型。 　　 Java语言则提供了在不同硬件和操作系统平台下对线程操作的统一处理，每个已经执行start（）且还未结束的java.lang.Thread类的实例就代表了一个线程。我们注意到Thread类与大部分的Java API有显著的差别，它的所有关键方法都是声明为Native的。在Java API中，一个Native方法往往意味着这个方法没有使用或无法使用平台无关的手段来实现（当然也可能是为了执行效率而使用Native方法，不过，通常最高效率的手段也就是平台相关的手段）。

(参考：深入理解Java虚拟机）

# 3. 线程的状态

线程是有状态的，并且这些状态之间也是可以互相流转的。Java中线程的状态分为6种：

1. 初始(NEW)：新创建了一个线程对象，但还没有调用start()方法。
2. 运行(RUNNABLE)：Java线程中将就绪（READY）和运行中（RUNNING）两种状态笼统的称为“运行”。
   1. 就绪（READY）:线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中并分配cpu使用权 。
   2. 运行中（RUNNING）：就绪(READY)的线程获得了cpu 时间片，开始执行程序代码。
3. 阻塞(BLOCKED)：表示线程阻塞于锁（关于锁，在后面章节会介绍）。
4. 等待(WAITING)：进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）。
5. 超时等待(TIMED_WAITING)：该状态不同于WAITING，它可以在指定的时间后自行返回。
6. 终止(TERMINATED)：表示该线程已经执行完毕。

下图是一张线程状态的流转图：

![image-20200218221423128](img/image-20200218221423128.png)

可以看到，图中的各个状态之间的流转路径上都有标注对应的Java中的方法。这些就是Java中进行线程调度的一些api。

## 3.1 **wait(), notify(), notifyAll()等方法介绍**

在Object.java中，定义了wait(), notify()和notifyAll()等接口。wait()的作用是让当前线程进入等待状态，同时，wait()也会让当前线程释放它所持有的锁。而notify()和notifyAll()的作用，则是唤醒当前对象上的等待线程；notify()是唤醒单个线程，而notifyAll()是唤醒所有的线程。

Object类中关于等待/唤醒的API详细信息如下：
**notify()**        -- 唤醒在此对象监视器上等待的单个线程。
**notifyAll()**   -- 唤醒在此对象监视器上等待的所有线程。
**wait()**                                         -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法”，当前线程被唤醒(进入“就绪状态”)。
**wait(long timeout)**                    -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量”，当前线程被唤醒(进入“就绪状态”)。
**wait(long timeout, int nanos)**  -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量”，当前线程被唤醒(进入“就绪状态”)。

## 3.2 为什么notify(), wait()等函数定义在Object中，而不是Thread中

Object中的wait(), notify()等函数，和synchronized一样，会对“对象的同步锁”进行操作。

wait()会使“当前线程”等待，因为线程进入等待状态，所以线程应该释放它锁持有的“同步锁”，否则其它线程获取不到该“同步锁”而无法运行！
OK，线程调用wait()之后，会释放它锁持有的“同步锁”；而且，根据前面的介绍，我们知道：等待线程可以被notify()或notifyAll()唤醒。现在，请思考一个问题：notify()是依据什么唤醒等待线程的？或者说，wait()等待线程和notify()之间是通过什么关联起来的？答案是：依据“对象的同步锁”。

负责唤醒等待线程的那个线程(我们称为“**唤醒线程**”)，它只有在获取“该对象的同步锁”(**这里的同步锁必须和等待线程的同步锁是同一个**)，并且调用notify()或notifyAll()方法之后，才能唤醒等待线程。虽然，等待线程被唤醒；但是，它不能立刻执行，因为唤醒线程还持有“该对象的同步锁”。必须等到唤醒线程释放了“对象的同步锁”之后，等待线程才能获取到“对象的同步锁”进而继续运行。

总之，notify(), wait()依赖于“同步锁”，而“同步锁”是对象锁持有，并且每个对象有且仅有一个！这就是为什么notify(), wait()等函数定义在Object类，而不是Thread类中的原因。

## 3.3 **yield()介绍**

**yield()的作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同优先级的等待线程获取执行权；但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！**

## 3.4 yield() 与 wait()的比较

**我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。而yield()的作用是让步，它也会让当前线程离开“运行状态”。它们的区别是：(01) wait()是让线程由“运行状态”进入到“等待(阻塞)状态”，而不yield()是让线程由“运行状态”进入到“就绪状态”。(02) wait()是会线程释放它所持有对象的同步锁，而yield()方法不会释放锁。**

```java
// YieldLockTest.java 的源码
public class YieldLockTest{ 

    private static Object obj = new Object();

    public static void main(String[] args){ 
        ThreadA t1 = new ThreadA("t1"); 
        ThreadA t2 = new ThreadA("t2"); 
        t1.start(); 
        t2.start();
    } 

    static class ThreadA extends Thread{
        public ThreadA(String name){ 
            super(name); 
        } 
        public void run(){ 
            // 获取obj对象的同步锁
            synchronized (obj) {
                for(int i=0; i <10; i++){ 
                    System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i); 
                    // i整除4时，调用yield
                    if (i%4 == 0)
                        Thread.yield();
                }
            }
        } 
    } 
}
```

**(某一次)运行结果**：

```
t1 [5]:0
t1 [5]:1
t1 [5]:2
t1 [5]:3
t1 [5]:4
t1 [5]:5
t1 [5]:6
t1 [5]:7
t1 [5]:8
t1 [5]:9
t2 [5]:0
t2 [5]:1
t2 [5]:2
t2 [5]:3
t2 [5]:4
t2 [5]:5
t2 [5]:6
t2 [5]:7
t2 [5]:8
t2 [5]:9
```

**结果说明**：
主线程main中启动了两个线程t1和t2。t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。在t1运行过程中，虽然它会调用Thread.yield()；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！

## 3.5 sleep()介绍

sleep() 定义在Thread.java中。
sleep() 的作用是让当前线程休眠，即当前线程会从“[运行状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”进入到“[休眠(阻塞)状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”。sleep()会指定休眠时间，线程休眠的时间会大于/等于该休眠时间；在线程重新被唤醒时，它会由“[阻塞状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”变成“[就绪状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”，从而等待cpu的调度执行。

## 3.6 **sleep() 与 wait()的比较**

**我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。而sleep()的作用是也是让当前线程由“运行状态”进入到“休眠(阻塞)状态”。但是，wait()会释放对象的同步锁，而sleep()则不会释放锁。下面通过示例演示sleep()是不会释放锁的。**

```java
// SleepLockTest.java的源码
public class SleepLockTest{ 

    private static Object obj = new Object();

    public static void main(String[] args){ 
        ThreadA t1 = new ThreadA("t1"); 
        ThreadA t2 = new ThreadA("t2"); 
        t1.start(); 
        t2.start();
    } 

    static class ThreadA extends Thread{
        public ThreadA(String name){ 
            super(name); 
        } 
        public void run(){ 
            // 获取obj对象的同步锁
            synchronized (obj) {
                try {
                    for(int i=0; i <10; i++){ 
                        System.out.printf("%s: %d\n", this.getName(), i); 
                        // i能被4整除时，休眠100毫秒
                        if (i%4 == 0)
                            Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } 
    } 
}
```

主线程main中启动了两个线程t1和t2。t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。在t1运行过程中，虽然它会调用Thread.sleep(100)；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！
注意，若我们注释掉synchronized (obj)后再次执行该程序，t1和t2是可以相互切换的。

# 4. 线程的调度

在关于线程安全的文章中，我们提到过，对于单CPU的计算机来说，在任意时刻只能执行一条机器指令，每个线程只有获得CPU的使用权才能执行指令。

所谓多线程的并发运行，其实是指从宏观上看，各个线程轮流获得CPU的使用权，分别执行各自的任务。

前面关于线程状态的介绍中，我们知道，线程的运行状态中包含两种子状态，即就绪（READY）和运行中(RUNNING)。

而一个线程想要从就绪状态变成运行中状态，这个过程需要系统调度，即给线程分配CPU的使用权，获得CPU使用权的线程才会从就绪状态变成运行状态。

**给多个线程按照特定的机制分配CPU的使用权的过程就叫做线程调度。**

还记得在介绍进程和线程的区别的时候，我们提到过的一句话吗：进程是分配资源的基本单元，线程是CPU调度的基本单元。这里所说的调度指的就是给其分配CPU时间片，让其执行任务。

## 4.1 Linux线程调度

在Linux中，线程是由进程来实现，线程就是轻量级进程（ lightweight process ），因此在Linux中，线程的调度是按照进程的调度方式来进行调度的，也就是说线程是调度单元。

Linux这样实现的线程的好处的之一是：线程调度直接使用进程调度就可以了，没必要再搞一个进程内的线程调度器。在Linux中，调度器是基于线程的调度策略（scheduling policy）和静态调度优先级（static scheduling priority）来决定那个线程来运行。

在Linux中，主要有三种调度策略。分别是：

- SCHED_OTHER 分时调度策略，（默认的）
- SCHED_FIFO 实时调度策略，先到先服务
- SCHED_RR 实时调度策略，时间片轮转

## 4.2 Windows线程调度

Windows 采用基于优先级的、抢占调度算法来调度线程。

用于处理调度的 Windows 内核部分称为调度程序，Windows 调度程序确保具有最高优先级的线程总是在运行的。由于调度程序选择运行的线程会一直运行，直到被更高优先级的线程所抢占，或终止，或时间片已到，或调用阻塞系统调用（如 I/O）。如果在低优先级线程运行时，更高优先级的实时线程变成就绪，那么低优先级线程就被抢占。这种抢占使得实时线程在需要使用 CPU 时优先得到使用。

## 4.3 Java线程调度

可以看到，不同的操作系统，有不同的线程调度策略。但是，作为一个Java开发人员来说，我们日常开发过程中一般很少关注操作系统层面的东西。

主要是因为Java程序都是运行在Java虚拟机上面的，而虚拟机帮我们屏蔽了操作系统的差异，所以我们说Java是一个跨平台语言。

**在操作系统中，一个Java程序其实就是一个进程。所以，我们说Java是单进程、多线程的！**

前面关于线程的实现也介绍过，Thread类与大部分的Java API有显著的差别，它的所有关键方法都是声明为Native的，也就是说，他需要根据不同的操作系统有不同的实现。

在Java的多线程程序中，为保证所有线程的执行能按照一定的规则执行，JVM实现了一个线程调度器，它定义了线程调度模型，对于CPU运算的分配都进行了规定，按照这些特定的机制为多个线程分配CPU的使用权。

主要有两种调度模型：**协同式线程调度**和**抢占式调度模型**。

### 4.3.1 协同式线程调度

协同式调度的多线程系统，线程的执行时间由线程本身来控制，线程把自己的工作执行完了之后，要主动通知系统切换到另外一个线程上。协同式多线程的最大好处是实现简单，而且由于线程要把自己的事情干完后才会进行线程切换，切换操作对线程自己是可知的，所以没有什么线程同步的问题。

### 4.3.2 抢占式调度模型

抢占式调度的多线程系统，那么每个线程将由系统来分配执行时间，线程的切换不由线程本身来决定。在这种实现线程调度的方式下，线程的执行时间是系统可控的，也不会有一个线程导致整个进程阻塞的问题。

系统会让可运行池中优先级高的线程占用CPU，如果可运行池中的线程优先级相同，那么就随机选择一个线程，使其占用CPU。处于运行状态的线程会一直运行，直至它不得不放弃CPU。

**Java虚拟机采用抢占式调度模型。**

虽然Java线程调度是系统自动完成的，但是我们还是可以“建议”系统给某些线程多分配一点执行时间，另外的一些线程则可以少分配一点——这项操作可以通过设置线程优先级来完成。Java语言一共设置了10个级别的线程优先级（Thread.MIN_PRIORITY至Thread.MAX_PRIORITY），在两个线程同时处于Ready状态时，优先级越高的线程越容易被系统选择执行。

不过，线程优先级并不是太靠谱，原因是Java的线程是通过映射到系统的原生线程上来实现的，所以线程调度最终还是取决于操作系统，虽然现在很多操作系统都提供线程优先级的概念，但是并不见得能与Java线程的优先级一一对应。

## 4.4 小结

- 线程的特点
  - 在多线程操作系统中，通常是在一个进程中包括多个线程，每个线程都是作为利用CPU的基本单位，是花费最小开销的实体。是一个独立调度和分派的基本单位，可并发执行并且是共享进程资源的。
- 线程的实现
  - 主流的操作系统都提供了线程实现，实现线程主要有3种方式：使用内核线程实现、使用用户线程实现和使用用户线程加轻量级进程混合实现。
- Java线程的实现
  - Java线程在JDK 1.2之前，是基于称为“绿色线程”（Green Threads）的用户线程实现的，而在JDK 1.2中，线程模型替换为基于操作系统原生线程模型来实现。
  - 在目前的JDK版本中，操作系统支持怎样的线程模型，在很大程度上决定了Java虚拟机的线程是怎样映射的，这点在不同的平台上没有办法达成一致，虚拟机规范中也并未限定Java线程需要使用哪种线程模型来实现。
- 线程状态
  - 线程是有状态的，并且这些状态之间也是可以互相流转的。Java中线程的状态分为6种：初始(NEW)、运行(RUNNABLE)、阻塞(BLOCKED)、等待(WAITING)、超时等待(TIMED_WAITING)和终止(TERMINATED)。
  - 其中运行(RUNNABLE)包含两种子状态，分别是就绪（READY）和运行中（RUNNING）
- 线程调度
  - 一个线程想要从就绪状态变成运行中状态，这个过程需要系统调度，即给线程分配CPU的使用权，获得CPU使用权的线程才会从就绪状态变成运行状态。给多个线程按照特定的机制分配CPU的使用权的过程就叫做线程调度。
  - 不同的操作系统，有不同的线程调度策略。Java程序都是运行在Java虚拟机上面的，而虚拟机帮我们屏蔽了操作系统的差异。在Java的多线程程序中，为保证所有线程的执行能按照一定的规则执行，JVM实现了一个线程调度器，它定义了线程调度模型，对于CPU运算的分配都进行了规定，按照这些特定的机制为多个线程分配CPU的使用权。
  - 主要有两种调度模型：协同式线程调度和抢占式调度模型。Java虚拟机采用抢占式调度模型。

下面主要包括线程优先级、守护线程、ThreadLoacal以及线程池等。

# 5. 线程优先级

我们学习过，Java虚拟机采用抢占式调度模型。也就是说他会给优先级更高的线程优先分配CPU。

虽然Java线程调度是系统自动完成的，但是我们还是可以“建议”系统给某些线程多分配一点执行时间，另外的一些线程则可以少分配一点——这项操作可以通过设置线程优先级来完成。

Java语言一共设置了10个级别的线程优先级（Thread.MIN_PRIORITY至Thread.MAX_PRIORITY），在两个线程同时处于Ready状态时，优先级越高的线程越容易被系统选择执行。

Java 线程优先级使用 1 ~ 10 的整数表示。默认的优先级是5。

```
最低优先级 1：Thread.MIN_PRIORITY

最高优先级 10：Thread.MAX_PRIORITY

普通优先级 5：Thread.NORM_PRIORITY
```

在Java中，可以使用Thread类的`setPriority()`方法为线程设置了新的优先级。`getPriority()`方法返回线程的当前优先级。当创建一个线程时，其默认优先级是创建该线程的线程的优先级。

以下代码演示如何设置和获取线程的优先：

```java
/**
 * @author Hollis
 */
public class Main {

    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("Main Thread  Priority:" + t.getPriority());

        Thread t1 = new Thread();
        System.out.println("Thread(t1) Priority:" + t1.getPriority());
        t1.setPriority(Thread.MAX_PRIORITY - 1);
        System.out.println("Thread(t1) Priority:" + t1.getPriority());

        t.setPriority(Thread.NORM_PRIORITY);
        System.out.println("Main Thread  Priority:" + t.getPriority());

        Thread t2 = new Thread();
        System.out.println("Thread(t2) Priority:" + t2.getPriority());

        // Change thread t2 priority to minimum
        t2.setPriority(Thread.MIN_PRIORITY);
        System.out.println("Thread(t2) Priority:" + t2.getPriority());
    }

}
```

输出结果为：

```
Main Thread  Priority:5
Thread(t1) Priority:5
Thread(t1) Priority:9
Main Thread  Priority:5
Thread(t2) Priority:5
Thread(t2) Priority:1
```

在上面的代码中，Java虚拟机启动时，就会通过main方法启动一个线程，JVM就会一直运行下去，直到以下任意一个条件发生：

- 调用了exit()方法，并且exit()有权限被正常执行。
- 所有的“非守护线程”都死了(即JVM中仅仅只有“守护线程”)。

关于exit，我们在后面的文章中再进行介绍，这里我们先来看看什么是守护线程。

# 6. 守护线程

在Java中有两类线程：User Thread(用户线程)、Daemon Thread(守护线程) 。用户线程一般用户执行用户级任务，而守护线程也就是“后台线程”，一般用来执行后台任务，守护线程最典型的应用就是GC(垃圾回收器)。

这两种线程其实是没有什么区别的，唯一的区别就是Java虚拟机在所有“用户线程”都结束后就会退出。

我们可以通过使用`setDaemon()`方法通过传递true作为参数，使线程成为一个守护线程。我们必须在启动线程之前调用一个线程的`setDaemon()`方法。否则，就会抛出一个`java.lang.IllegalThreadStateException`。

可以使用`isDaemon()`方法来检查线程是否是守护线程。

```java
public class Main {
  
    public static void main(String[] args) {
      
        Thread t1 = new Thread();
        System.out.println(t1.isDaemon());
        t1.setDaemon(true);
        System.out.println(t1.isDaemon());
        t1.start();
        t1.setDaemon(false);
    }
}
```

以上代码输出结果：

![image-20200222142621413](img/image-20200222142621413.png)

我们提到，当JVM中只剩下守护线程的时候，JVM就会退出，那么写一段代码测试下：

```java
public class Main {
    public static void main(String[] args) {

        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("I'm child thread..");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        childThread.start();
        System.out.println("I'm main thread...");
    }
}
```

以上代码中，我们在Main线程中开启了一个子线程，在并没有显示将其设置为守护线程的情况下，他是一个用户线程，代码比较好理解，就是子线程处于一个while(true)循环中，每隔一秒打印一次`I'm child thread..`

输出结果为：

```
I'm main thread...
I'm child thread..
I'm child thread..
.....
I'm child thread..
I'm child thread..
```

我们再把子线程设置成守护线程，重新运行以上代码。

```java
/**
 * @author Hollis
 */
public class Main {
    public static void main(String[] args) {

        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("I'm child thread..");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        childThread.setDaemon(true);
        childThread.start();
        System.out.println("I'm main thread...");
    }
}
```

以上代码，我们通过`childThread.setDaemon(true);`把子线程设置成守护线程，然后运行，得到以下结果：

![image-20200222143004453](img/image-20200222143004453.png)

子线程只打印了一次，也就是，在main线程执行结束后，由于子线程是一个守护线程，JVM就会直接退出了。

**值得注意的是，在Daemon线程中产生的新线程也是Daemon的。**

提到线程，有一个很重要的东西我们需要介绍一下，那就是ThreadLocal。

# 7. ThreadLocal

ThreadLocal是java.lang下面的一个类，是用来解决java多线程程序中并发问题的一种途径；**通过为每一个线程创建一份共享变量的副本**来保证各个线程之间的变量的访问和修改互相不影响；

ThreadLocal存放的值是**线程内共享**的，**线程间互斥**的，主要用于线程内共享一些数据，避免通过参数来传递，这样处理后，能够优雅的解决一些实际问题。

比如一次用户的页面操作请求，我们可以在最开始的filter中，把用户的信息保存在ThreadLocal中，在同一次请求中，在使用到用户信息，就可以直接到ThreadLocal中获取就可以了。

还有一个典型的应用就是保存数据库连接，我们可以在第一次初始化Connection的时候，把他保存在ThreadLocal中。

ThreadLocal有四个方法，分别为：

- initialValue
  - 返回此线程局部变量的初始值
- get
  - 返回此线程局部变量的当前线程副本中的值。如果这是线程第一次调用该方法，则创建并初始化此副本。
- set
  - 将此线程局部变量的当前线程副本中的值设置为指定值。许多应用程序不需要这项功能，它们只依赖于 initialValue() 方法来设置线程局部变量的值。
- remove
  - 移除此线程局部变量的值。

Hibernate中的OpenSessionInView，就是使用ThreadLocal保存Session对象，还有我们经常用ThreadLocal存放Connection，代码如：

```java
/** 
* 数据库连接管理类 
*/  
public class ConnectionManager {  

  /** 线程内共享Connection，ThreadLocal通常是全局的，支持泛型 */  
  private static ThreadLocal threadLocal = new ThreadLocal();  

  public static Connection getCurrConnection() {  
      // 获取当前线程内共享的Connection  
      Connection conn = threadLocal.get();  
      try {  
          // 判断连接是否可用  
          if(conn == null || conn.isClosed()) {  
              // 创建新的Connection赋值给conn(略)  
              // 保存Connection  
              threadLocal.set(conn);  
          }  
      } catch (SQLException e) {  
          // 异常处理  
      }  
      return conn;  
  }  

  /** 
   * 关闭当前数据库连接 
   */  
  public static void close() {  
      // 获取当前线程内共享的Connection  
      Connection conn = threadLocal.get();  
      try {  
          // 判断是否已经关闭  
          if(conn != null && !conn.isClosed()) {  
              // 关闭资源  
              conn.close();  
              // 移除Connection  
              threadLocal.remove();  
              conn = null;  
          }  
      } catch (SQLException e) {  
          // 异常处理  
      }  
  }  
}  
```

本来想介绍一下ThreadLocal的原理，不过我在网上找到一篇不错的文章：http://www.jasongj.com/java/threadlocal/ ，大家可以去看下。

前面介绍过了很多关于线程的基本知识，线程是我们在Java开发中经常遇到的，但是，线程虽然比进程更加轻量级，但是频繁的创建和销毁还是会有很多开销的。为了解决这样的问题，有一种技术就诞生了，那就是——池化技术。

## 7.1 ThreadLocal解决什么问题

由于 ThreadLocal 支持范型，如 ThreadLocal< StringBuilder >，为表述方便，后文用 **变量** 代表 ThreadLocal 本身，而用 **实例**代表具体类型（如 StringBuidler ）的实例。

### 7.1.1 不恰当的理解

写这篇文章的一个原因在于，网上很多博客关于 ThreadLocal 的适用场景以及解决的问题，描述的并不清楚，甚至是错的。下面是常见的对于 ThreadLocal的介绍

> ThreadLocal为解决多线程程序的并发问题提供了一种新的思路
> ThreadLocal的目的是为了解决多线程访问资源时的共享问题

还有很多文章在对比 ThreadLocal 与 synchronize 的异同。既然是作比较，那应该是认为这两者解决相同或类似的问题。

上面的描述，问题在于，ThreadLocal 并不解决多线程 **共享** 变量的问题。既然变量不共享，那就更谈不上同步的问题。

### 7.1.2 合理的理解

ThreadLoal 变量，它的基本原理是，同一个 ThreadLocal 所包含的对象（对ThreadLocal< String >而言即为 String 类型变量），在不同的 Thread 中有不同的副本（实际是不同的实例，后文会详细阐述）。这里有几点需要注意

- 因为每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来
- 既然每个 Thread 有自己的实例副本，且其它 Thread 不可访问，那就不存在多线程间共享的问题
- 既无共享，何来同步问题，又何来解决同步问题一说？

那 ThreadLocal 到底解决了什么问题，又适用于什么样的场景？

> This class provides thread-local variables. These variables differ from their normal counterparts in that each thread that accesses one (via its get or set method) has its own, independently initialized copy of the variable. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or Transaction ID).
> Each thread holds an implicit reference to its copy of a thread-local variable as long as the thread is alive and the ThreadLocal instance is accessible; after a thread goes away, all of its copies of thread-local instances are subject to garbage collection (unless other references to these copies exist).

核心意思是

> ThreadLocal 提供了线程本地的实例。它与普通变量的区别在于，每个使用该变量的线程都会初始化一个完全独立的实例副本。ThreadLocal 变量通常被`private static`修饰。当一个线程结束时，它所使用的所有 ThreadLocal 相对的实例副本都可被回收。

总的来说，**ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景。**后文会通过实例详细阐述该观点。另外，该场景下，并非必须使用 ThreadLocal ，其它方式完全可以实现同样的效果，只是 ThreadLocal 使得实现更简洁。

## 7.2 ThreadLocal用法

### 7.2.1 实例代码

下面通过如下代码说明 ThreadLocal 的使用方式

```java
public class ThreadLocalDemo {

  public static void main(String[] args) throws InterruptedException {

    int threads = 3;
    CountDownLatch countDownLatch = new CountDownLatch(threads);
    InnerClass innerClass = new InnerClass();
    for(int i = 1; i <= threads; i++) {
      new Thread(() -> {
        for(int j = 0; j < 4; j++) {
          innerClass.add(String.valueOf(j));
          innerClass.print();
        }
        innerClass.set("hello world");
        countDownLatch.countDown();
      }, "thread - " + i).start();
    }
    countDownLatch.await();

  }

  private static class InnerClass {

    public void add(String newStr) {
      StringBuilder str = Counter.counter.get();
      Counter.counter.set(str.append(newStr));
    }

    public void print() {
      System.out.printf("Thread name:%s , ThreadLocal hashcode:%s, Instance hashcode:%s, Value:%s\n",
      Thread.currentThread().getName(),
      Counter.counter.hashCode(),
      Counter.counter.get().hashCode(),
      Counter.counter.get().toString());
    }

    public void set(String words) {
      Counter.counter.set(new StringBuilder(words));
      System.out.printf("Set, Thread name:%s , ThreadLocal hashcode:%s,  Instance hashcode:%s, Value:%s\n",
      Thread.currentThread().getName(),
      Counter.counter.hashCode(),
      Counter.counter.get().hashCode(),
      Counter.counter.get().toString());
    }
  }

  private static class Counter {

    private static ThreadLocal<StringBuilder> counter = new ThreadLocal<StringBuilder>() {
      @Override
      protected StringBuilder initialValue() {
        return new StringBuilder();
      }
    };

  }

}
```

### 7.2.2 实例分析

ThreadLocal本身支持范型。该例使用了 StringBuilder 类型的 ThreadLocal 变量。可通过 ThreadLocal 的 get() 方法读取 StringBuidler 实例，也可通过 set(T t) 方法设置 StringBuilder。

上述代码执行结果如下

```
Thread name:thread - 1 , ThreadLocal hashcode:372282300, Instance hashcode:418873098, Value:0
Thread name:thread - 3 , ThreadLocal hashcode:372282300, Instance hashcode:1609588821, Value:0
Thread name:thread - 2 , ThreadLocal hashcode:372282300, Instance hashcode:1780437710, Value:0
Thread name:thread - 3 , ThreadLocal hashcode:372282300, Instance hashcode:1609588821, Value:01
Thread name:thread - 1 , ThreadLocal hashcode:372282300, Instance hashcode:418873098, Value:01
Thread name:thread - 3 , ThreadLocal hashcode:372282300, Instance hashcode:1609588821, Value:012
Thread name:thread - 3 , ThreadLocal hashcode:372282300, Instance hashcode:1609588821, Value:0123
Set, Thread name:thread - 3 , ThreadLocal hashcode:372282300,  Instance hashcode:1362597339, Value:hello world
Thread name:thread - 2 , ThreadLocal hashcode:372282300, Instance hashcode:1780437710, Value:01
Thread name:thread - 1 , ThreadLocal hashcode:372282300, Instance hashcode:418873098, Value:012
Thread name:thread - 2 , ThreadLocal hashcode:372282300, Instance hashcode:1780437710, Value:012
Thread name:thread - 1 , ThreadLocal hashcode:372282300, Instance hashcode:418873098, Value:0123
Thread name:thread - 2 , ThreadLocal hashcode:372282300, Instance hashcode:1780437710, Value:0123
Set, Thread name:thread - 1 , ThreadLocal hashcode:372282300,  Instance hashcode:482932940, Value:hello world
Set, Thread name:thread - 2 , ThreadLocal hashcode:372282300,  Instance hashcode:1691922941, Value:hello world
```



从上面的输出可看出

- 从第1-3行输出可见，每个线程通过 ThreadLocal 的 get() 方法拿到的是不同的 StringBuilder 实例
- 第1-3行输出表明，每个线程所访问到的是同一个 ThreadLocal 变量
- 从7、12、13行输出以及第30行代码可见，虽然从代码上都是对 Counter 类的静态 counter 字段进行 get() 得到 StringBuilder 实例并追加字符串，但是这并不会将所有线程追加的字符串都放进同一个 StringBuilder 中，而是每个线程将字符串追加进各自的 StringBuidler 实例内
- 对比第1行与第15行输出并结合第38行代码可知，使用 set(T t) 方法后，ThreadLocal 变量所指向的 StringBuilder 实例被替换

## 7.3 ThreadLocal原理

### 7.3.1ThreadLocal维护线程与实例的映射

既然每个访问 ThreadLocal 变量的线程都有自己的一个“本地”实例副本。一个可能的方案是 ThreadLocal 维护一个 Map，键是 Thread，值是它在该 Thread 内的实例。线程通过该 ThreadLocal 的 get() 方案获取实例时，只需要以线程为键，从 Map 中找出对应的实例即可。该方案如下图所示


[![ThreadLocal side Map](img/VarMap.png)](http://www.jasongj.com/img/java/threadlocal/VarMap.png)

该方案可满足上文提到的每个线程内一个独立备份的要求。每个新线程访问该 ThreadLocal 时，需要向 Map 中添加一个映射，而每个线程结束时，应该清除该映射。这里就有两个问题：

- 增加线程与减少线程均需要写 Map，故需保证该 Map 线程安全。虽然[从ConcurrentHashMap的演进看Java多线程核心技术](http://www.jasongj.com/java/concurrenthashmap/)一文介绍了几种实现线程安全 Map 的方式，但它或多或少都需要锁来保证线程的安全性
- 线程结束时，需要保证它所访问的所有 ThreadLocal 中对应的映射均删除，否则可能会引起内存泄漏。（后文会介绍避免内存泄漏的方法）

其中锁的问题，是 JDK 未采用该方案的一个原因。

### 7.3.2 Thread维护ThreadLocal与实例的映射

上述方案中，出现锁的问题，原因在于多线程访问同一个 Map。如果该 Map 由 Thread 维护，从而使得每个 Thread 只访问自己的 Map，那就不存在多线程写的问题，也就不需要锁。该方案如下图所示。


[![ThreadLocal side Map](img/ThreadMap.png)](http://www.jasongj.com/img/java/threadlocal/ThreadMap.png)

该方案虽然没有锁的问题，但是由于每个线程访问某 ThreadLocal 变量后，都会在自己的 Map 内维护该 ThreadLocal 变量与具体实例的映射，如果不删除这些引用（映射），则这些 ThreadLocal 不能被回收，可能会造成内存泄漏。后文会介绍 JDK 如何解决该问题。

## 7.4 ThreadLocal 在 JDK 8 中的实现

### 7.4.1 ThreadLocalMap与内存泄漏

该方案中，Map 由 ThreadLocal 类的静态内部类 ThreadLocalMap 提供。该类的实例维护某个 ThreadLocal 与具体实例的映射。与 HashMap 不同的是，ThreadLocalMap 的每个 Entry 都是一个对 ***键\*** 的弱引用，这一点从`super(k)`可看出。另外，每个 Entry 都包含了一个对 ***值\*** 的强引用。

```
static class Entry extends WeakReference<ThreadLocal<?>> {
  /** The value associated with this ThreadLocal. */
  Object value;

  Entry(ThreadLocal<?> k, Object v) {
    super(k);
    value = v;
  }
}
```



使用弱引用的原因在于，当没有强引用指向 ThreadLocal 变量时，它可被回收，从而避免上文所述 ThreadLocal 不能被回收而造成的内存泄漏的问题。

但是，这里又可能出现另外一种内存泄漏的问题。ThreadLocalMap 维护 ThreadLocal 变量与具体实例的映射，当 ThreadLocal 变量被回收后，该映射的键变为 null，该 Entry 无法被移除。从而使得实例被该 Entry 引用而无法被回收造成内存泄漏。

***注：\***Entry虽然是弱引用，但它是 ThreadLocal 类型的弱引用（也即上文所述它是对 ***键\*** 的弱引用），而非具体实例的的弱引用，所以无法避免具体实例相关的内存泄漏。

### 7.4.2 读取实例

读取实例方法如下所示

```
public T get() {
  Thread t = Thread.currentThread();
  ThreadLocalMap map = getMap(t);
  if (map != null) {
    ThreadLocalMap.Entry e = map.getEntry(this);
    if (e != null) {
      @SuppressWarnings("unchecked")
      T result = (T)e.value;
      return result;
    }
  }
  return setInitialValue();
}
```



读取实例时，线程首先通过`getMap(t)`方法获取自身的 ThreadLocalMap。从如下该方法的定义可见，该 ThreadLocalMap 的实例是 Thread 类的一个字段，即由 Thread 维护 ThreadLocal 对象与具体实例的映射，这一点与上文分析一致。

```
ThreadLocalMap getMap(Thread t) {
  return t.threadLocals;
}
```



获取到 ThreadLocalMap 后，通过`map.getEntry(this)`方法获取该 ThreadLocal 在当前线程的 ThreadLocalMap 中对应的 Entry。该方法中的 this 即当前访问的 ThreadLocal 对象。

如果获取到的 Entry 不为 null，从 Entry 中取出值即为所需访问的本线程对应的实例。如果获取到的 Entry 为 null，则通过`setInitialValue()`方法设置该 ThreadLocal 变量在该线程中对应的具体实例的初始值。

### 7.4.3 设置初始值

设置初始值方法如下

```
private T setInitialValue() {
  T value = initialValue();
  Thread t = Thread.currentThread();
  ThreadLocalMap map = getMap(t);
  if (map != null)
    map.set(this, value);
  else
    createMap(t, value);
  return value;
}
```



该方法为 private 方法，无法被重载。

首先，通过`initialValue()`方法获取初始值。该方法为 public 方法，且默认返回 null。所以典型用法中常常重载该方法。上例中即在内部匿名类中将其重载。

然后拿到该线程对应的 ThreadLocalMap 对象，若该对象不为 null，则直接将该 ThreadLocal 对象与对应实例初始值的映射添加进该线程的 ThreadLocalMap中。若为 null，则先创建该 ThreadLocalMap 对象再将映射添加其中。

这里并不需要考虑 ThreadLocalMap 的线程安全问题。因为每个线程有且只有一个 ThreadLocalMap 对象，并且只有该线程自己可以访问它，其它线程不会访问该 ThreadLocalMap，也即该对象不会在多个线程中共享，也就不存在线程安全的问题。

### 7.4.4 设置实例

除了通过`initialValue()`方法设置实例的初始值，还可通过 set 方法设置线程内实例的值，如下所示。

```
public void set(T value) {
  Thread t = Thread.currentThread();
  ThreadLocalMap map = getMap(t);
  if (map != null)
    map.set(this, value);
  else
    createMap(t, value);
}
```



该方法先获取该线程的 ThreadLocalMap 对象，然后直接将 ThreadLocal 对象（即代码中的 this）与目标实例的映射添加进 ThreadLocalMap 中。当然，如果映射已经存在，就直接覆盖。另外，如果获取到的 ThreadLocalMap 为 null，则先创建该 ThreadLocalMap 对象。

### 7.4.5 防止内存泄漏

对于已经不再被使用且已被回收的 ThreadLocal 对象，它在每个线程内对应的实例由于被线程的 ThreadLocalMap 的 Entry 强引用，无法被回收，可能会造成内存泄漏。

针对该问题，ThreadLocalMap 的 set 方法中，通过 replaceStaleEntry 方法将所有键为 null 的 Entry 的值设置为 null，从而使得该值可被回收。另外，会在 rehash 方法中通过 expungeStaleEntry 方法将键和值为 null 的 Entry 设置为 null 从而使得该 Entry 可被回收。通过这种方式，ThreadLocal 可防止内存泄漏。

```
private void set(ThreadLocal<?> key, Object value) {
  Entry[] tab = table;
  int len = tab.length;
  int i = key.threadLocalHashCode & (len-1);

  for (Entry e = tab[i]; e != null; e = tab[i = nextIndex(i, len)]) {
    ThreadLocal<?> k = e.get();
    if (k == key) {
      e.value = value;
      return;
    }
    if (k == null) {
      replaceStaleEntry(key, value, i);
      return;
    }
  }
  tab[i] = new Entry(key, value);
  int sz = ++size;
  if (!cleanSomeSlots(i, sz) && sz >= threshold)
    rehash();
}
```

## 7.5 适用场景

如上文所述，ThreadLocal 适用于如下两种场景

- 每个线程需要有自己单独的实例
- 实例需要在多个方法中共享，但不希望被多线程共享

对于第一点，每个线程拥有自己实例，实现它的方式很多。例如可以在线程内部构建一个单独的实例。ThreadLocal 可以以非常方便的形式满足该需求。

对于第二点，可以在满足第一点（每个线程有自己的实例）的条件下，通过方法间引用传递的形式实现。ThreadLocal 使得代码耦合度更低，且实现更优雅。

## 7.6 案例

对于 Java Web 应用而言，Session 保存了很多信息。很多时候需要通过 Session 获取信息，有些时候又需要修改 Session 的信息。一方面，需要保证每个线程有自己单独的 Session 实例。另一方面，由于很多地方都需要操作 Session，存在多方法共享 Session 的需求。如果不使用 ThreadLocal，可以在每个线程内构建一个 Session实例，并将该实例在多个方法间传递，如下所示。

```
public class SessionHandler {

  @Data
  public static class Session {
    private String id;
    private String user;
    private String status;
  }

  public Session createSession() {
    return new Session();
  }

  public String getUser(Session session) {
    return session.getUser();
  }

  public String getStatus(Session session) {
    return session.getStatus();
  }

  public void setStatus(Session session, String status) {
    session.setStatus(status);
  }

  public static void main(String[] args) {
    new Thread(() -> {
      SessionHandler handler = new SessionHandler();
      Session session = handler.createSession();
      handler.getStatus(session);
      handler.getUser(session);
      handler.setStatus(session, "close");
      handler.getStatus(session);
    }).start();
  }
}
```



该方法是可以实现需求的。但是每个需要使用 Session 的地方，都需要显式传递 Session 对象，方法间耦合度较高。

这里使用 ThreadLocal 重新实现该功能如下所示。

```
public class SessionHandler {

  public static ThreadLocal<Session> session = ThreadLocal.<Session>withInitial(() -> new Session());

  @Data
  public static class Session {
    private String id;
    private String user;
    private String status;
  }

  public String getUser() {
    return session.get().getUser();
  }

  public String getStatus() {
    return session.get().getStatus();
  }

  public void setStatus(String status) {
    session.get().setStatus(status);
  }

  public static void main(String[] args) {
    new Thread(() -> {
      SessionHandler handler = new SessionHandler();
      handler.getStatus();
      handler.getUser();
      handler.setStatus("close");
      handler.getStatus();
    }).start();
  }
}
```



使用 ThreadLocal 改造后的代码，不再需要在各个方法间传递 Session 对象，并且也非常轻松的保证了每个线程拥有自己独立的实例。

如果单看其中某一点，替代方法很多。比如可通过在线程内创建局部变量可实现每个线程有自己的实例，使用静态变量可实现变量在方法间的共享。但如果要同时满足变量在线程间的隔离与方法间的共享，ThreadLocal再合适不过。

## 7.7 总结

- ThreadLocal 并不解决线程间共享数据的问题
- ThreadLocal 通过隐式的在不同线程内创建独立实例副本避免了实例线程安全的问题
- 每个线程持有一个 Map 并维护了 ThreadLocal 对象与具体实例的映射，该 Map 由于只被持有它的线程访问，故不存在线程安全以及锁的问题
- ThreadLocalMap 的 Entry 对 ThreadLocal 的引用为弱引用，避免了 ThreadLocal 对象无法被回收的问题
- ThreadLocalMap 的 set 方法通过调用 replaceStaleEntry 方法回收键为 null 的 Entry 对象的值（即为具体实例）以及 Entry 对象本身从而防止内存泄漏
- ThreadLocal 适用于变量在线程间隔离且在方法间共享的场景

# 8. 池化技术

前面提到一个名词——池化技术，那么到底什么是池化技术呢？

池化技术简单点来说，就是提前保存大量的资源，以备不时之需。在机器资源有限的情况下，使用池化技术可以大大的提高资源的利用率，提升性能等。

在编程领域，比较典型的池化技术有：

线程池、连接池、内存池、对象池等。

# 9. 线程池

一种线程使用模式。线程过多会带来调度开销，进而影响缓存局部性和整体性能。而线程池维护着多个线程，等待着监督管理者分配可并发执行的任务。这避免了在处理短时间任务时创建与销毁线程的代价。

线程池不仅能够保证内核的充分利用，还能防止过分调度。可用线程数量应该取决于可用的并发处理器、处理器内核、内存、网络sockets等的数量。 例如，线程数一般取cpu数量+2比较合适，线程数过多会导致额外的线程切换开销。

关于线程池部分，后面还会有章节单独介绍其原理和正确的使用姿势。目前大家只要记住，使用线程池可以大大的节省开销就好了。
