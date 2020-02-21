<!--ts-->
   * [1. 初识synchronized](#1-初识synchronized)
         * [什么是synchronized](#什么是synchronized)
         * [synchronized 的用法](#synchronized-的用法)
         * [synchronized 的实现原理](#synchronized-的实现原理)
            * [同步方法实现原理](#同步方法实现原理)
            * [同步代码块实现原理](#同步代码块实现原理)
            * [synchronized 原理总结](#synchronized-原理总结)
         * [Monitor 的实现原理](#monitor-的实现原理)
            * [什么是 Monitor](#什么是-monitor)
            * [Monitor 的代码实现](#monitor-的代码实现)
         * [Monitor 原理总结](#monitor-原理总结)
         * [总结](#总结)
   * [2. 进阶synchronized——如何保证线程安全？](#2-进阶synchronized如何保证线程安全)
         * [synchronized与原子性](#synchronized与原子性)
         * [synchronized与有序性](#synchronized与有序性)
         * [synchronized与可见性](#synchronized与可见性)
         * [总结](#总结-1)
   * [3. 拓展synchronized——锁优化](#3-拓展synchronized锁优化)
         * [自旋锁](#自旋锁)
         * [锁消除](#锁消除)
         * [锁粗化](#锁粗化)
         * [总结](#总结-2)

<!-- Added by: anapodoton, at: Fri Feb 21 23:34:47 CST 2020 -->

<!--te-->

# 1. 初识synchronized

在前面10个章节我们从计算机硬件开始，介绍了并发编程及内存模型等相关的知识。由于处理器的分时优化、多级缓存，编译器的指令重排等会导致在并发编程中存在原子性、可见性以及有序性等问题。而在并发编程的过程中，程序员之所以可以不需要关心这么多的底层原理及技术，是因为有内存模型可以帮我们屏蔽这些问题。

在计算机内存模型的基础上，Java 语言又提供了 Java 内存模型来帮助 Java 开发者可以更好的处理并发编程问题。Java 内存模型，除了定义了一套规范外，还提供了一系列原语，封装了底层实现后，供开发者直接使用。这些原语就是 Java 中的很多关键字和很多并发处理的类。本章节就来介绍 Java 并发处理中最最常用的关键字——`synchronized`。

### 什么是synchronized

`synchronized` 是 Java 中的一个很重要的关键字，主要用来加锁，`synchronized` 所添加的锁有以下几个特点。

- 互斥性
  - 同一时间点，只有一个线程可以获得锁，获得锁的线程才可以处理被 synchronized 修饰的代码片段。
- 阻塞性
  - 只有获得锁的线程才可以执行被 synchronized 修饰的代码片段，未获得锁的线程只能阻塞，等待锁释放。
- 可重入性
  - 如果一个线程已经获得锁，在锁未释放之前，再次请求锁的时候，是必然可以获得锁的。

### synchronized 的用法

synchronized 的使用方法比较简单，主要可以用来修饰方法和代码块。根据其锁定的对象不同，可以用来定义同步方法和同步代码块。

**同步方法**

```
//同步方法，对象锁  
public synchronized void doSth(){
    System.out.println("Hello World");
}

//同步方法，类锁  
public synchronized static void doSth(){
    System.out.println("Hello World");
}
```

以上代码，在方法的作用域（public）后面增加 `Synchronized`，即可声明一个同步方法。

**同步代码块**

```
//同步代码块，类锁
public void doSth1(){
    synchronized (Demo.class){
        System.out.println("Hello World");
    }
}

//同步代码块，对象锁
public void doSth1(){
    synchronized (this){
        System.out.println("Hello World");
    }
}
```

以上代码，在代码块前面增加 `synchronized`，即可声明一个同步代码块。

在上面的同步方法和同步代码块的例子中，均提供了两个代码 demo，分别是两种类型的锁，即类锁和对象锁。区分方式按照其锁定的内容进行划分。对象锁锁定的内容是对象，类锁锁定的内容是类。用法如下：

> 对象锁
>
> > synchronized(object) {}
> >
> > 修饰非静态方法
>
> 类锁
>
> > synchronized(Class) {}
> >
> > 修饰静态方法

PS：其实，类锁也是通过对象锁实现的，因为在 Java 中，万物皆对象。

### synchronized 的实现原理

了解了 `synchronized` 的用法之后，接下来，我们来介绍一下 `synchronized` 关键字的实现原理，看一看 `synchronized` 到底是如何加锁的。

由于我们想要了解其原理，我们需要将一段 Java 中的和 `synchronized` 相关的代码先编译成 class 文件，再将其反编译查看其字节码。

源代码：

```
public class SynchronizedTest {

    public synchronized void doSth(){
        System.out.println("Hello World");
    }

    public void doSth1(){
        synchronized (SynchronizedTest.class){
            System.out.println("Hello World");
        }
    }
}
```

使用 javap 进行反编译后得到的字节码如下（部分无用信息过滤掉了）：

```
public synchronized void doSth();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello World
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return

  public void doSth1();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=3, args_size=1
         0: ldc           #5                  // class com/hollis/SynchronizedTest
         2: dup
         3: astore_1
         4: monitorenter
         5: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         8: ldc           #3                  // String Hello World
        10: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        13: aload_1
        14: monitorexit
        15: goto          23
        18: astore_2
        19: aload_1
        20: monitorexit
        21: aload_2
        22: athrow
        23: return
```

反编译后，我们可以看到 Java 编译器为我们生成的字节码。在对于 doSth 和 doSth1 的处理上稍有不同。也就是说。JVM 对于同步方法和同步代码块的处理方式不同。

对于同步方法，JVM 采用 `ACC_SYNCHRONIZED` 标记符来实现同步。 对于同步代码块。JVM 采用 `monitorenter`、`monitorexit` 两个指令来实现同步。

#### 同步方法实现原理

[The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.11.10) 中有关于方法级同步的介绍：

> Method-level synchronization is performed implicitly, as part of method invocation and return. A synchronized method is distinguished in the run-time constant pool’s method_info structure by the ACC_SYNCHRONIZED flag, which is checked by the method invocation instructions. When invoking a method for which ACC_SYNCHRONIZED is set, the executing thread enters a monitor, invokes the method itself, and exits the monitor whether the method invocation completes normally or abruptly. During the time the executing thread owns the monitor, no other thread may enter it. If an exception is thrown during invocation of the synchronized method and the synchronized method does not handle the exception, the monitor for the method is automatically exited before the exception is rethrown out of the synchronized method.

主要说的是： 方法级的同步是隐式的。同步方法的常量池中会有一个 `ACC_SYNCHRONIZED` 标志。当某个线程要访问某个方法的时候，会检查是否有 `ACC_SYNCHRONIZED`，如果有设置，则需要先获得监视器锁，然后开始执行方法，方法执行之后再释放监视器锁。这时如果其他线程来请求执行方法，会因为无法获得监视器锁而被阻断住。值得注意的是，如果在方法执行过程中，发生了异常，并且方法内部并没有处理该异常，那么在异常被抛到方法外面之前监视器锁会被自动释放。

#### 同步代码块实现原理

同步代码块使用 `monitorenter` 和 `monitorexit` 两个指令实现。 [The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html) 中有关于这两个指令的介绍：

**monitorenter**

> Each object is associated with a monitor. A monitor is locked if and only if it has an owner. The thread that executes monitorenter attempts to gain ownership of the monitor associated with objectref, as follows:
>
> > If the entry count of the monitor associated with objectref is zero, the thread enters the monitor and sets its entry count to one. The thread is then the owner of the monitor.
> >
> > If the thread already owns the monitor associated with objectref, it reenters the monitor, incrementing its entry count.
> >
> > If another thread already owns the monitor associated with objectref, the thread blocks until the monitor’s entry count is zero, then tries again to gain ownership.

**monitorexit**

> The thread that executes monitorexit must be the owner of the monitor associated with the instance referenced by objectref.
>
> The thread decrements the entry count of the monitor associated with objectref. If as a result the value of the entry count is zero, the thread exits the monitor and is no longer its owner. Other threads that are blocking to enter the monitor are allowed to attempt to do so.

大致内容如下： 可以把执行 `monitorenter` 指令理解为加锁，执行 `monitorexit` 理解为释放锁。 每个对象维护着一个记录着被锁次数的计数器。未被锁定的对象的该计数器为 0，当一个线程获得锁（执行 `monitorenter` ）后，该计数器自增变为 1 ，当同一个线程再次获得该对象的锁的时候，计数器再次自增。当同一个线程释放锁（执行 `monitorexit` 指令）的时候，计数器再自减。当计数器为 0 的时候。锁将被释放，其他线程便可以获得锁。

#### synchronized 原理总结

同步方法通过 `ACC_SYNCHRONIZED` 关键字隐式的对方法进行加锁。当线程要执行的方法被标注上 `ACC_SYNCHRONIZED` 时，需要先获得锁才能执行该方法。

同步代码块通过 `monitorenter` 和 `monitorexit` 执行来进行加锁。当线程执行到 `monitorenter` 的时候要先获得所锁，才能执行后面的方法。当线程执行到 `monitorexit` 的时候则要释放锁。

每个对象自身维护这一个被加锁次数的计数器，当计数器数字为 0 时表示可以被任意线程获得锁。当计数器不为 0 时，只有获得锁的线程才能再次获得锁。即可重入锁。

前面我们提到了无论是同步方法还是同步代码块，其实现其实都要依赖对象的监视器（Monitor），那么到底什么是 Monitor，Monitor 又是如何进行加锁和解锁的呢？

### Monitor 的实现原理

为了解决这类线程安全的问题，Java 提供了同步机制、互斥锁机制，这个机制保证了在同一时刻只有一个线程能访问共享资源。

这个机制的保障来源于监视锁 Monitor，每个对象都拥有自己的监视锁 Monitor。当我们尝试获得对象的锁的时候，其实是对该对象拥有的 Monitor 进行操作。

#### 什么是 Monitor

先来举个例子，然后我们在上源码。我们可以把监视器理解为包含一个特殊的房间的建筑物，这个特殊房间同一时刻只能有一个客人（线程）。这个房间中包含了一些数据和代码。

![img](img/165dc67181a2c632-20200221204757433.jpg)

如果一个顾客想要进入这个特殊的房间，他首先需要在走廊（Entry Set）排队等待。调度器将基于某个标准（比如 FIFO）来选择排队的客户进入房间。如果，因为某些原因，该客户客户暂时因为其他事情无法脱身（线程被挂起），那么他将被送到另外一间专门用来等待的房间（Wait Set），这个房间的可以可以在稍后再次进入那件特殊的房间。如上面所说，这个建筑屋中一共有三个场所。

![img](img/165dc6718182c75f-20200221204758289.jpg)

总之，监视器是一个用来监视这些线程进入特殊的房间的。他的义务是保证（同一时间）只有一个线程可以访问被保护的数据和代码。

Monitor 其实是一种同步工具，也可以说是一种同步机制，它通常被描述为一个对象，主要特点是：

> 对象的所有方法都被“互斥”的执行。好比一个 Monitor 只有一个运行“许可”，任一个线程进入任何一个方法都需要获得这个“许可”，离开时把许可归还。
>
> 通常提供 singal 机制：允许正持有“许可”的线程暂时放弃“许可”，等待某个谓词成真（条件变量），而条件成立后，当前进程可以“通知”正在等待这个条件变量的线程，让他可以重新去获得运行许可。

#### Monitor 的代码实现

在 Java 虚拟机(HotSpot)中，Monitor 是基于 C++ 实现的，由 [ObjectMonitor](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/runtime/objectMonitor.cpp) 实现的，其主要数据结构如下：

```
  ObjectMonitor() {
    _header       = NULL;
    _count        = 0;
    _waiters      = 0,
    _recursions   = 0;
    _object       = NULL;
    _owner        = NULL;
    _WaitSet      = NULL;
    _WaitSetLock  = 0 ;
    _Responsible  = NULL ;
    _succ         = NULL ;
    _cxq          = NULL ;
    FreeNext      = NULL ;
    _EntryList    = NULL ;
    _SpinFreq     = 0 ;
    _SpinClock    = 0 ;
    OwnerIsThread = 0 ;
  }
```

源码地址：[objectMonitor.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/runtime/objectMonitor.hpp#L193)

ObjectMonitor 中有几个关键属性：

> _owner：指向持有 ObjectMonitor 对象的线程
>
> _WaitSet：存放处于 wait 状态的线程队列
>
> _EntryList：存放处于等待锁 block 状态的线程队列
>
> _recursions：锁的重入次数
>
> _count：用来记录该线程获取锁的次数

当多个线程同时访问一段同步代码时，首先会进入 `_EntryList` 队列中，当某个线程获取到对象的 monitor 后进入 `_Owner` 区域并把 monitor 中的 `_owner` 变量设置为当前线程，同时 monitor 中的计数器 `_count` 加1。即获得对象锁。

若持有 monitor 的线程调用 `wait()` 方法，将释放当前持有的 monitor，`_owner` 变量恢复为 `null`，`_count` 自减 1，同时该线程进入 `_WaitSet` 集合中等待被唤醒。若当前线程执行完毕也将释放 monitor(锁)并复位变量的值，以便其他线程进入获取 monitor(锁)。如下图所示

![img](img/165dc6718151f32f-20200221204757121.png)

下面是 ObjectMonitor 类中提供的几个方法，我在关键节点处都增加了注释，便于读者阅读，可以对照上面的例子以及后文的流程图进行理解。

**获得锁**

```
void ATTR ObjectMonitor::enter(TRAPS) {
  Thread * const Self = THREAD ;
  void * cur ;
  //通过CAS尝试把monitor的`_owner`字段设置为当前线程
  cur = Atomic::cmpxchg_ptr (Self, &_owner, NULL) ;
  //获取锁失败
  if (cur == NULL) {         assert (_recursions == 0   , "invariant") ;
     assert (_owner      == Self, "invariant") ;
     // CONSIDER: set or assert OwnerIsThread == 1
     return ;
  }
  // 如果旧值和当前线程一样，说明当前线程已经持有锁，此次为重入，_recursions自增，并获得锁。
  if (cur == Self) { 
     // TODO-FIXME: check for integer overflow!  BUGID 6557169.
     _recursions ++ ;
     return ;
  }

  // 如果当前线程是第一次进入该monitor，设置_recursions为1，_owner为当前线程
  if (Self->is_lock_owned ((address)cur)) { 
    assert (_recursions == 0, "internal state error");
    _recursions = 1 ;
    // Commute owner from a thread-specific on-stack BasicLockObject address to
    // a full-fledged "Thread *".
    _owner = Self ;
    OwnerIsThread = 1 ;
    return ;
  }

  // 省略部分代码。
  // 通过自旋执行ObjectMonitor::EnterI方法等待锁的释放
  for (;;) {
  jt->set_suspend_equivalent();
  // cleared by handle_special_suspend_equivalent_condition()
  // or java_suspend_self()

  EnterI (THREAD) ;

  if (!ExitSuspendEquivalent(jt)) break ;

  //
  // We have acquired the contended monitor, but while we were
  // waiting another thread suspended us. We don't want to enter
  // the monitor while suspended because that would surprise the
  // thread that suspended us.
  //
      _recursions = 0 ;
  _succ = NULL ;
  exit (Self) ;

  jt->java_suspend_self();
}
}
```

![img](https://www.hollischuang.com/wp-content/uploads/2019/11/165dc671817e245b.png)

释放锁

```
void ATTR ObjectMonitor::exit(TRAPS) {
   Thread * Self = THREAD ;
   //如果当前线程不是Monitor的所有者
   if (THREAD != _owner) { 
     if (THREAD->is_lock_owned((address) _owner)) { // 
       // Transmute _owner from a BasicLock pointer to a Thread address.
       // We don't need to hold _mutex for this transition.
       // Non-null to Non-null is safe as long as all readers can
       // tolerate either flavor.
       assert (_recursions == 0, "invariant") ;
       _owner = THREAD ;
       _recursions = 0 ;
       OwnerIsThread = 1 ;
     } else {
       // NOTE: we need to handle unbalanced monitor enter/exit
       // in native code by throwing an exception.
       // TODO: Throw an IllegalMonitorStateException ?
       TEVENT (Exit - Throw IMSX) ;
       assert(false, "Non-balanced monitor enter/exit!");
       if (false) {
          THROW(vmSymbols::java_lang_IllegalMonitorStateException());
       }
       return;
     }
   }
    // 如果_recursions次数不为0.自减
   if (_recursions != 0) {
     _recursions--;        // this is simple recursive enter
     TEVENT (Inflated exit - recursive) ;
     return ;
   }

   //省略部分代码，根据不同的策略（由QMode指定），从cxq或EntryList中获取头节点，通过ObjectMonitor::ExitEpilog方法唤醒该节点封装的线程，唤醒操作最终由unpark完成。
```

![img](img/165dc6718278c2dc-20200221204756285.png)

除了 enter 和 exit 方法以外，[objectMonitor.cpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/runtime/objectMonitor.cpp)中还有

```
void      wait(jlong millis, bool interruptable, TRAPS);
void      notify(TRAPS);
void      notifyAll(TRAPS);
```

等方法。

### Monitor 原理总结

上面介绍的就是 HotSpot 虚拟机中 Moniter 的的加锁以及解锁的原理。

通过这篇文章我们知道了 `synchronized` 对某个对象进行加锁的时候，会调用该对象拥有的 objectMonitor 的 `enter` 方法，解锁的时候会调用 `exit` 方法。

事实上，只有在 JDK1.6 之前，`synchronized` 的实现才会直接调用 ObjectMonitor 的 `enter` 和 `exit` ，这种锁被称之为重量级锁。为什么说这种方式操作锁很重呢？

- Java 的线程是映射到操作系统原生线程之上的，如果要阻塞或唤醒一个线程就需要操作系统的帮忙，这就要从用户态转换到核心态，因此状态转换需要花费很多的处理器时间，对于代码简单的同步块（如被 `Synchronized` 修饰的 `get` 或 `set` 方法）状态转换消耗的时间有可能比用户代码执行的时间还要长，所以说 `synchronized` 是 java 语言中一个重量级的操纵。

所以，在 JDK1.6 中出现对锁进行了很多的优化，进而出现轻量级锁，偏向锁，锁消除，适应性自旋锁，锁粗化(自旋锁在 1.4 就有 只不过默认的是关闭的，JDK1.6 是默认开启的)，这些操作都是为了在线程之间更高效的共享数据 ，解决竞争问题。后面的文章会继续介绍这几种锁优化机制以及他们之间的关系。

### 总结

本文介绍了synchronized关键字，主要包括其用法和基本原理。

在用法上，synchronized可以用于同步方法及同步代码块，而同步方法和同步代码块的实现原理也不尽相同。

同步方法通过 `ACC_SYNCHRONIZED` 关键字隐式的对方法进行加锁。当线程要执行的方法被标注上 `ACC_SYNCHRONIZED` 时，需要先获得锁才能执行该方法。

同步代码块通过 `monitorenter` 和 `monitorexit` 执行来进行加锁。当线程执行到 `monitorenter` 的时候要先获得所锁，才能执行后面的方法。当线程执行到 `monitorexit` 的时候则要释放锁。

前面我们提到了无论是同步方法还是同步代码块，其实现其实都要依赖对象的监视器（Monitor）。`synchronized` 对某个对象进行加锁的时候，会调用该对象拥有的 objectMonitor 的 `enter` 方法，解锁的时候会调用 `exit` 方法。

文中还提到JDK1.6 之前，`synchronized`的实现是重量级锁，那么到底什么是重量级锁？如何优化重量级锁呢？

还有，synchronized的原理我们介绍完了，那么，为什么使用synchronized就能保证并发编程的原子性、可见性和有序性了呢？

以上这两个问题，后续文章会继续介绍。

# 2. 进阶synchronized——如何保证线程安全？

本文是《深入理解Java并发编程》系列文字的第12篇，前面我们用了10篇文章，从计算机硬件开始，介绍了并发编程及内存模型等相关的知识，然后在第11篇我们讲解了第一个关键字synchronized，主要介绍了他的用法以及实现原理。

那么，这一篇，我们把前面11篇总结一下，看一下我们介绍过的synchronized是如何保证线程安全的。

在前面的文章中，我们说过并发编程最大的挑战解决如何解决多个线程之前的原子性、有序性以及可见性。而synchronized作为Java并发模型中必不可少的一个关键字，那么他是如何解决这三个问题的呢，本文就来逐一分析一下。

### synchronized与原子性

原子性是指一个操作是不可中断的，要全部执行完成，要不就都不执行。

我们知道，线程是CPU调度的基本单位。CPU有时间片的概念，会根据不同的调度算法进行线程调度。当一个线程获得时间片之后开始执行，在时间片耗尽之后，就会失去CPU使用权。所以在多线程场景下，由于时间片在线程间轮换，就会发生原子性问题。

比如线程1获得时间片执行，但是执行过程中，CPU时间片耗尽，他就需要让出CPU，这时线程2获得了时间片开始执行。但是对于线程1来说，他的操作并没有全部执行完成，也没有全都不执行，这就是原子性问题。

那么，synchronized如何保证的原子性呢？

我们在第11篇《[深入理解Java并发编程（十一）：初识synchronized](https://www.hollischuang.com/archives/4117)》介绍过，synchonized其实是通过 monitorenter 和 monitorexit 这两个字节码指令实现的。

当线程执行到 monitorenter 的时候要先获得锁，才能执行后面的方法。当线程执行到 monitorexit 的时候则要释放锁。

在未释放之前，其他线程是无法再次获得锁的，所以，通过monitorenter和monitorexit指令，可以保证被synchronized修饰的代码在同一时间只能被一个线程访问，在锁未释放之前，无法被其他线程访问到。因此，在Java中可以使用synchronized来保证方法和代码块内的操作是原子性的。

线程1在执行monitorenter指令的时候，会对Monitor进行加锁，加锁后其他线程无法获得锁，除非线程1主动解锁。即使在执行过程中，由于某种原因，比如CPU时间片用完，线程1放弃了CPU，但是，他并没有进行解锁。而由于synchronized的锁是可重入的，下一个时间片还是只能被他自己获取到，还是会继续执行代码。直到所有代码执行完。这就保证了原子性。

### synchronized与有序性

有序性即程序执行的顺序按照代码的先后顺序执行。

我们知道，计算机硬件层面做了很多优化，除了引入了时间片以外，由于处理器优化和指令重排等，CPU还可能对输入代码进行乱序执行，比如load->add->save 有可能被优化成load->save->add 。这就是可能存在有序性问题。

那么，想要彻底解决有序性问题，最好的办法就是直接禁止指令重排和处理器优化，但是，synchronized是做不到的，那么为什么我们还说synchronized也提供了有序性保证呢？

这里就需要把有序性的概念扩展一下了，Java程序中天然的有序性可以总结为一句话：如果在本线程内观察，所有操作都是天然有序的。如果在一个线程中观察另一个线程，所有操作都是无序的。

以上这句话也是《深入理解Java虚拟机》中的原句，但是怎么理解呢？周志明并没有详细的解释。这里我简单扩展一下，这其实和as-if-serial语义有关。

as-if-serial语义的意思指：不管怎么重排序，单线程程序的执行结果都不能被改变。编译器和处理器无论如何优化，都必须遵守as-if-serial语义。

这里不对as-if-serial语义详细展开了，简单说就是，as-if-serial语义保证了单线程中，指令重排是有一定的限制的，而只要编译器和处理器都遵守了这个语义，那么就可以认为单线程程序是按照顺序执行的。当然，实际上还是有重排的，只不过我们无须关心这种重排的干扰。

所以呢，由于synchronized修饰的代码，同一时间只能被同一线程访问。那么也就是单线程执行的。所以，可以保证其有序性。

### synchronized与可见性

可见性是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。

Java内存模型规定了所有的变量都存储在主内存中，每条线程还有自己的工作内存，线程的工作内存中保存了该线程中是用到的变量的主内存副本拷贝，线程对变量的所有操作都必须在工作内存中进行，而不能直接读写主内存。

不同的线程之间也无法直接访问对方工作内存中的变量，线程间变量的传递均需要自己的工作内存和主存之间进行数据同步进行。所以，就可能出现线程1改了某个变量的值，但是线程2不可见的情况。

前面我们介绍过，被synchronized修饰的代码，在开始执行时会加锁，执行完成后会进行解锁。而为了保证可见性，有一条规则是这样的：对一个变量解锁之前，必须先把此变量同步回主存中。这样解锁后，后续线程就可以访问到被修改后的值。

所以，synchronized关键字锁住的对象，其值是具有可见性的。

### 总结

本文介绍了synchronized是如何保证多线程场景下的原子性、有序性以及可见性的。

因为synchronized是一种锁，并且是一种可重入的排它锁，所以他可以保证被他修饰代码同一时间内只能被单一线程访问，并且在进入这段代码前加锁，离开这段代码前解锁。

在某个线程加锁之后，释放之前，其他线程无法获得锁，也就无法执行被锁定的代码，所以这就保证了即使CPU时间片耗尽，也没有其他线程可以执行到被锁定的代码，之后本线程再次获得时间片才行。这就保证了原子性。

另外，在对变量解锁之前，都会把本地内存中变量的值同步到主内存，在加锁之前再从主内存把变量的值读到本地内存。这就保证了，多个线程顺序执行过程中，变量的值是可见的。

另外，根据as-if-serial语义，单线程内是天然有序的，而synchronized通过加锁实现了单线程执行，所以也就具备了有序性。

# 3. 拓展synchronized——锁优化

本文是《深入理解Java并发编程》系列文字的第13篇，也是synchronized相关的第3篇，在这篇之后，还会有两篇介绍synchronized的。这一篇算是关于synchronized的一个深入和拓展。

在第11篇《[深入理解Java并发编程（十一）：初识synchronized](https://www.hollischuang.com/archives/4117)》中，我们留了一个问题：

事实上，只有在 JDK1.6 之前，synchronized 的实现才会直接调用 ObjectMonitor 的 enter 和 exit ，这种锁被称之为重量级锁。那么什么是重量级锁？JDK 1.6之后发生了什么？

本文，就来解答下这些问题。

高效并发是从JDK 1.5 到 JDK 1.6的一个重要改进，HotSpot虚拟机开发团队在这个版本中花费了很大的精力去对Java中的锁进行优化，如适应性自旋、锁消除、锁粗化、轻量级锁和偏向锁等。这些技术都是为了在线程之间更高效的共享数据，以及解决竞争问题。

本文，主要先来介绍一下自旋、锁消除以及锁粗化等技术。

这里简单说明一下，本文要介绍的这几个概念，以及后面要介绍的轻量级锁和偏向锁，其实对于使用他的开发者来说是屏蔽掉了的，也就是说，作为一个Java开发，你只需要知道你想在加锁的时候使用synchronized就可以了，具体的锁的优化是虚拟机根据竞争情况自行决定的。

### 自旋锁

我们介绍的`synchronized`的实现方式时，说过，它是使用`Monitor`进行加锁，这是一种互斥锁，为了表示他对性能的影响我们称之为重量级锁。

这种互斥锁在互斥同步上对性能的影响很大，Java的线程是映射到操作系统原生线程之上的，如果要阻塞或唤醒一个线程就需要操作系统的帮忙，这就要从用户态转换到内核态，因此状态转换需要花费很多的处理器时间。

就像去银行办业务的例子，当你来到银行，发现柜台前面都有人的时候，你需要取一个号，然后再去等待区等待，一直等待被叫号。这个过程是比较浪费时间的，那么有没有什么办法改进呢？

有一种比较好的设计，那就是银行提供自动取款机，当你去银行取款的时候，你不需要取号，不需要去休息区等待叫号，你只需要找到一台取款机，排在其他人后面等待取款就行了。

[![Pic2](img/Pic2-20200221204924812.png)](http://www.hollischuang.com/wp-content/uploads/2018/04/Pic2.png)

之所以能这样做，是因为取款的这个过程相比较之下是比较节省时间的。如果所有人去银行都只取款，或者办理业务的时间都很短的话，那也就可以不需要取号，不需要去单独的休息区，不需要听叫号，也不需要再跑到对应的柜台了。

而，在程序中，Java虚拟机的开发工程师们在分析过大量数据后发现：共享数据的锁定状态一般只会持续很短的一段时间，为了这段时间去挂起和恢复线程其实并不值得。

如果物理机上有多个处理器，可以让多个线程同时执行的话。我们就可以让后面来的线程“稍微等一下”，但是并不放弃处理器的执行时间，看看持有锁的线程会不会很快释放锁。这个“稍微等一下”的过程就是自旋。

自旋锁在JDK 1.4中已经引入，在JDK 1.6中默认开启。

很多人在对于自旋锁的概念不清楚的时候可能会有以下疑问：这么听上去，自旋锁好像和阻塞锁没啥区别，反正都是等着嘛。

- 对于去银行取钱的你来说，站在取款机面前等待和去休息区等待叫号有一个很大的区别：
  - 那就是如果你在休息区等待，这段时间你什么都不需要管，随意做自己的事情，等着被唤醒就行了。
  - 如果你在取款机面前等待，那么你需要时刻关注自己前面还有没有人，因为没人会唤醒你。
  - 很明显，这种直接去取款机前面排队取款的效率是比较高。

**所以呢，自旋锁和阻塞锁最大的区别就是，到底要不要放弃处理器的执行时间。对于阻塞锁和自旋锁来说，都是要等待获得共享资源。但是阻塞锁是放弃了CPU时间，进入了等待区，等待被唤醒。而自旋锁是一直“自旋”在那里，时刻的检查共享资源是否可以被访问。**

由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不停增加时，性能下降明显，因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，并且保持锁的时间短。适合使用自旋锁。

### 锁消除

除了自旋锁之后，JDK中还有一种锁的优化被称之为锁消除。还拿去银行取钱的例子说。

你去银行取钱，所有情况下都需要取号，并且等待吗？其实是不用的，当银行办理业务的人不多的时候，可能根本不需要取号，直接走到柜台前面办理业务就好了。

[![Pic3](img/Pic3-20200221204924076.png)](http://www.hollischuang.com/wp-content/uploads/2018/04/Pic3.png)

能这么做的前提是，没有人和你抢着办业务。

上面的这种例子，在锁优化中被称作“锁消除”，是JIT编译器对内部锁的具体实现所做的一种优化。

在动态编译同步块的时候，JIT编译器可以借助一种被称为逃逸分析（Escape Analysis）的技术来判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程。

如果同步块所使用的锁对象通过这种分析被证实只能够被一个线程访问，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。

如以下代码：

```
public void f() {
    Object hollis = new Object();
    synchronized(hollis) {
        System.out.println(hollis);
    }
}
```

代码中对`hollis`这个对象进行加锁，但是`hollis`对象的生命周期只在`f()`方法中，并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉。优化成：

```
public void f() {
    Object hollis = new Object();
    System.out.println(hollis);
}
```

> 这里，可能有读者会质疑了，代码是程序员自己写的，程序员难道没有能力判断要不要加锁吗？就像以上代码，完全没必要加锁，有经验的开发者一眼就能看的出来的。其实道理是这样，但是还是有可能有疏忽，比如我们经常在代码中使用`StringBuffer`作为局部变量，而`StringBuffer`中的`append`是线程安全的，有`synchronized`修饰的，这种情况开发者可能会忽略。这时候，JIT就可以帮忙优化，进行锁消除。

了解我的朋友都知道，一般到这个时候，我就会开始反编译，然后拿出反编译之后的代码来证明锁优化确实存在。

但是，之前很多例子之所以可以用反编译工具，是因为那些“优化”，如语法糖等，是在`javac编译`阶段发生的，并不是在`JIT编译`阶段发生的。而锁优化，是JIT编译器的功能，所以，无法使用现有的反编译工具查看具体的优化结果。（关于javac编译和JIT编译的关系和区别，我在我的知识星球中单独发了一篇文章介绍。）

> 但是，如果读者感兴趣，还是可以看的，只是会复杂一点，首先你要自己build一个fasttest版本的jdk，然后在使用java命令对`.class`文件进行执行的时候加上`-XX:+PrintEliminateLocks`参数。而且jdk的模式还必须是server模式。

总之，读者只需要知道，在使用`synchronized`的时候，如果JIT经过逃逸分析之后发现并无线程安全问题的话，就会做锁消除。

### 锁粗化

很多人都知道，在代码中，需要加锁的时候，我们提倡尽量减小锁的粒度，这样可以避免不必要的阻塞。

这也是很多人原因是用同步代码块来代替同步方法的原因，因为往往他的粒度会更小一些，这其实是很有道理的。

还是我们去银行柜台办业务，最高效的方式是你坐在柜台前面的时候，只办和银行相关的事情。如果这个时候，你拿出手机，接打几个电话，问朋友要往哪个账户里面打钱，这就很浪费时间了。最好的做法肯定是提前准备好相关资料，在办理业务时直接办理就好了。

[![Pic4](img/Pic4-20200221204924044.png)](http://www.hollischuang.com/wp-content/uploads/2018/04/Pic4.png)

加锁也一样，把无关的准备工作放到锁外面，锁内部只处理和并发相关的内容。这样有助于提高效率。

那么，这和锁粗化有什么关系呢？可以说，大部分情况下，减小锁的粒度是很正确的做法，只有一种特殊的情况下，会发生一种叫做锁粗化的优化。

就像你去银行办业务，你为了减少每次办理业务的时间，你把要办的五个业务分成五次去办理，这反而适得其反了。因为这平白的增加了很多你重新取号、排队、被唤醒的时间。

如果在一段代码中连续的对同一个对象反复加锁解锁，其实是相对耗费资源的，这种情况可以适当放宽加锁的范围，减少性能消耗。

当JIT发现一系列连续的操作都对同一个对象反复加锁和解锁，甚至加锁操作出现在循环体中的时候，会将加锁同步的范围扩散（粗化）到整个操作序列的外部。

如以下代码：

```
for(int i=0;i<100000;i++){  
    synchronized(this){  
        do();  
}  
```

会被粗化成：

```
synchronized(this){  
    for(int i=0;i<100000;i++){  
        do();  
}  
```

**这其实和我们要求的减小锁粒度并不冲突。减小锁粒度强调的是不要在银行柜台前做准备工作以及和办理业务无关的事情。而锁粗化建议的是，同一个人，要办理多个业务的时候，可以在同一个窗口一次性办完，而不是多次取号多次办理。**

### 总结

自Java 6/Java 7开始，Java虚拟机对内部锁的实现进行了一些优化。这些优化主要包括锁消除（Lock Elision）、锁粗化（Lock Coarsening）、偏向锁（Biased Locking）以及适应性自旋锁（Adaptive Locking）。这些优化仅在Java虚拟机server模式下起作用（即运行Java程序时我们可能需要在命令行中指定Java虚拟机参数“-server”以开启这些优化）。

本文主要介绍了自旋锁、锁粗化和锁消除的概念。在JIT编译过程中，虚拟机会根据情况使用这三种技术对锁进行优化，目的是减少锁的竞争，提升性能。

