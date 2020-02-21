<!--ts-->
   * [前言](#前言)
      * [Java虚拟机是如何执行线程同步的](#java虚拟机是如何执行线程同步的)
         * [线程和共享数据](#线程和共享数据)
         * [对象和类的锁](#对象和类的锁)
         * [监视器（Monitors）](#监视器monitors)
         * [多次加锁](#多次加锁)
         * [同步](#同步)
   * [1.Synchronized的实现原理](#1synchronized的实现原理)
      * [1.1 反编译](#11-反编译)
      * [1.2 同步方法](#12-同步方法)
      * [1.3 同步代码块](#13-同步代码块)
         * [1.3.1 monitorenter](#131-monitorenter)
         * [1.3.2 monitorexit](#132-monitorexit)
      * [1.4 总结](#14-总结)
   * [2. Java的对象模型](#2-java的对象模型)
      * [2.1 Java的对象模型](#21-java的对象模型)
         * [2.1.1 oop-klass model](#211-oop-klass-model)
         * [2.1.2 oop-klass结构](#212-oop-klass结构)
            * [_mark](#_mark)
            * [_metadata](#_metadata)
         * [2.1.3 instanceKlass](#213-instanceklass)
      * [2.2 内存存储](#22-内存存储)
      * [2.3 总结](#23-总结)
      * [2.4 参考资料](#24-参考资料)
   * [3. Java的对象头](#3-java的对象头)
      * [3.1 Java对象模型回顾与勘误](#31-java对象模型回顾与勘误)
   * [4. Moniter的实现原理](#4-moniter的实现原理)
      * [4.1 操作系统中的管程](#41-操作系统中的管程)
      * [4.2 Java线程同步相关的Moniter](#42-java线程同步相关的moniter)
      * [4.3 监视器的实现](#43-监视器的实现)
      * [4.4 总结](#44-总结)
   * [5. Java虚拟机的锁优化技术](#5-java虚拟机的锁优化技术)
      * [5.1 前情提要](#51-前情提要)
      * [5.2 线程状态](#52-线程状态)
      * [5.3 自旋锁](#53-自旋锁)
      * [5.4 锁消除](#54-锁消除)
      * [5.5 锁粗化](#55-锁粗化)
      * [5.6 总结](#56-总结)

<!-- Added by: anapodoton, at: Fri Feb 21 17:34:46 CST 2020 -->

<!--te-->

# 前言

我们将在这里深入学习多线程的知识。首先我们需要学习下java虚拟机是如何执行线程同步的。

##  Java虚拟机是如何执行线程同步的

[Java虚拟机是如何执行线程同步的](https://www.hollischuang.com/archives/1876)

想介绍下synchronized的原理，但是又不知道从何下手，在网上看到一篇老外的文章，介绍了和线程同步相关的几个基础知识点。所以想把它翻译一下给大家看看。相信看过这些基础知识之后再看我后面要写的synchronized的原理就会好理解一点了。

------

原文地址：[How the Java virtual machine performs thread synchronization](https://www.javaworld.com/article/2076971/java-concurrency/how-the-java-virtual-machine-performs-thread-synchronization.html)

了解Java语言的人都知道，Java代码要想被JVM执行，需要被转换成由字节码组成的class文件。本文主要来分析下Java虚拟机是如何在字节码层面上执行线程同步的。

### 线程和共享数据

Java编程语言的优点之一是它在语言层面上对多线程的支持。这种支持大部分集中在协调多个线程对共享数据的访问上。JVM的内存结构主要包含以下几个重要的区域：栈、堆、方法区等。

在Java虚拟中，每个线程独享一块栈内存，其中包括局部变量、线程调用的每个方法的参数和返回值。其他线程无法读取到该栈内存块中的数据。栈中的数据仅限于基本类型和对象引用。所以，在JVM中，栈上是无法保存真实的对象的，只能保存对象的引用。真正的对象要保存在堆中。

在JVM中，堆内存是所有线程共享的。**堆中只包含对象，没有其他东西。所以，堆上也无法保存基本类型和对象引用。堆和栈分工明确。**但是，对象的引用其实也是对象的一部分。这里值得一提的是，数组是保存在堆上面的，即使是基本类型的数据，也是保存在堆中的。因为在Java中，数组是对象。

除了栈和堆，还有一部分数据可能保存在JVM中的方法区中，比如类的静态变量。**方法区和栈类似，其中只包含基本类型和对象应用。**和栈不同的是，方法区中的静态变量可以被所有线程访问到。

### 对象和类的锁

如前文提到，JVM中有两块内存区域可以被所有线程共享：

> 堆，上面存放着所有对象
>
> 方法区，上面存放着静态变量

那么，如果有多个线程想要同时访问同一个对象或者静态变量，就需要被管控，否则可能出现不可预期的结果。

为了协调多个线程之间的共享数据访问，虚拟机给每个对象和类都分配了一个锁。这个锁就像一个特权，在同一时刻，只有一个线程可以“拥有”这个类或者对象。如果一个线程想要获得某个类或者对象的锁，需要询问虚拟机。当一个线程向虚拟机申请某个类或者对象的锁之后，也许很快或者也许很慢虚拟机可以把锁分配给这个线程，同时这个线程也许永远也无法获得锁。当线程不再需要锁的时候，他再把锁还给虚拟机。这时虚拟机就可以再把锁分配给其他申请锁的线程。

类锁其实通过对象锁实现的。因为当虚拟机加载一个类的时候，会会为这个类实例化一个 `java.lang.Class` 对象，当你锁住一个类的时候，其实锁住的是其对应的Class 对象。

### 监视器（Monitors）

监视器和锁同时被JVM使用（我理解作者的意思应该是想说锁其实是通过监视器实现的。），监视器主要功能是监控一段代码，确保在同一时间只有一个线程在执行。

每个监视器都与一个对象相关联。当线程执行到监视器监视下的代码块中的第一条指令时，线程必须获取对被引用对象的锁定。在线程获取锁之前，他是无法执行这段代码的，一旦获得锁，线程便可以进入“被保护”的代码开始执行。

当线程离开代码块的时候，无论如何离开，都会释放所关联对象的锁。

### 多次加锁

同一个线程可以对同一个对象进行多次加锁。每个对象维护着一个记录着被锁次数的计数器。未被锁定的对象的该计数器为0，当一个线程获得锁后，该计数器自增变为 1 ，当同一个线程再次获得该对象的锁的时候，计数器再次自增。当同一个线程释放锁的时候，计数器再自减。当计数器为0的时候。锁将被释放，其他线程便可以获得锁。

### 同步

在Java中，当有多个线程都必须要对同一个共享数据进行访问时，有一种协调方式叫做同步。Java语言提供了两种内置方式来使线程同步的访问数据：同步代码块和同步方法。

这篇文章中后面还介绍了同步代码块和同步方法，以及简单的介绍了下实现方式。这里就不做翻译了，因为我觉得他介绍的太简单了。我后面专门写篇文章详细介绍。

# 1.Synchronized的实现原理

`synchronized`，是Java中用于解决并发情况下数据同步访问的一个很重要的关键字。当我们想要保证一个共享资源在同一时间只会被一个线程访问到时，我们可以在代码中使用`synchronized`关键字对类或者对象加锁。那么，本文来介绍一下`synchronized`关键字的实现原理是什么。在阅读本文之间，建议先看下[Java虚拟机是如何执行线程同步的](http://www.hollischuang.com/archives/1876) 。

## 1.1 反编译

众所周知，在Java中，`synchronized`有两种使用形式，同步方法和同步代码块。代码如下：

```java
/**
 * @author Hollis 17/11/9.
 */
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

我们先来使用[Javap](http://www.hollischuang.com/archives/1107)来反编译以上代码，结果如下（部分无用信息过滤掉了）：

 [java常用命令.md](java常用命令.md) 

```java
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

[反编译](http://www.hollischuang.com/archives/58)后，我们可以看到Java编译器为我们生成的字节码。在对于`doSth`和`doSth1`的处理上稍有不同。也就是说。JVM对于同步方法和同步代码块的处理方式不同。

**对于同步方法，JVM采用`ACC_SYNCHRONIZED`标记符来实现同步。 对于同步代码块。JVM采用`monitorenter`、`monitorexit`两个指令来实现同步。**

关于这部分内容，在[JVM规范](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-3.html#jvms-3.14)中也可以找到相关的描述。

## 1.2 同步方法

[The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.11.10)中有关于方法级同步的介绍：

> Method-level synchronization is performed implicitly, as part of method invocation and return. A synchronized method is distinguished in the run-time constant pool’s method_info structure by the ACC_SYNCHRONIZED flag, which is checked by the method invocation instructions. When invoking a method for which ACC_SYNCHRONIZED is set, the executing thread enters a monitor, invokes the method itself, and exits the monitor whether the method invocation completes normally or abruptly. During the time the executing thread owns the monitor, no other thread may enter it. If an exception is thrown during invocation of the synchronized method and the synchronized method does not handle the exception, the monitor for the method is automatically exited before the exception is rethrown out of the synchronized method.

主要说的是： 方法级的同步是隐式的。同步方法的常量池中会有一个`ACC_SYNCHRONIZED`标志。当某个线程要访问某个方法的时候，会检查是否有`ACC_SYNCHRONIZED`，如果有设置，则需要先获得监视器锁，然后开始执行方法，方法执行之后再释放监视器锁。这时如果其他线程来请求执行方法，会因为无法获得监视器锁而被阻断住。值得注意的是，如果在方法执行过程中，发生了异常，并且方法内部并没有处理该异常，那么在异常被抛到方法外面之前监视器锁会被自动释放。

## 1.3 同步代码块

同步代码块使用`monitorenter`和`monitorexit`两个指令实现。 [The Java® Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html) 中有关于这两个指令的介绍：

### 1.3.1 monitorenter

> Each object is associated with a monitor. A monitor is locked if and only if it has an owner. The thread that executes monitorenter attempts to gain ownership of the monitor associated with objectref, as follows:
>
> > If the entry count of the monitor associated with objectref is zero, the thread enters the monitor and sets its entry count to one. The thread is then the owner of the monitor.
> >
> > If the thread already owns the monitor associated with objectref, it reenters the monitor, incrementing its entry count.
> >
> > If another thread already owns the monitor associated with objectref, the thread blocks until the monitor’s entry count is zero, then tries again to gain ownership.

### 1.3.2 monitorexit

> The thread that executes monitorexit must be the owner of the monitor associated with the instance referenced by objectref.
>
> The thread decrements the entry count of the monitor associated with objectref. If as a result the value of the entry count is zero, the thread exits the monitor and is no longer its owner. Other threads that are blocking to enter the monitor are allowed to attempt to do so.

大致内容如下： 可以把执行`monitorenter`指令理解为加锁，执行`monitorexit`理解为释放锁。 每个对象维护着一个记录着被锁次数的计数器。未被锁定的对象的该计数器为0，当一个线程获得锁（执行`monitorenter`）后，该计数器自增变为 1 ，当同一个线程再次获得该对象的锁的时候，计数器再次自增。当同一个线程释放锁（执行`monitorexit`指令）的时候，计数器再自减。当计数器为0的时候。锁将被释放，其他线程便可以获得锁。

## 1.4 总结

> 同步方法通过`ACC_SYNCHRONIZED`关键字隐式的对方法进行加锁。当线程要执行的方法被标注上`ACC_SYNCHRONIZED`时，需要先获得锁才能执行该方法。
>
> 同步代码块通过`monitorenter`和`monitorexit`执行来进行加锁。当线程执行到`monitorenter`的时候要先获得所锁，才能执行后面的方法。当线程执行到`monitorexit`的时候则要释放锁。
>
> 每个对象自身维护这一个被加锁次数的计数器，当计数器数字为0时表示可以被任意线程获得锁。当计数器不为0时，只有获得锁的线程才能再次获得锁。即可重入锁。

至此，我们大致了解了Synchronized的原理。但是还有几个问题并没有介绍清楚，比如，Monitor到底是什么？对象的锁的状态保存在哪里？ 别急，后面会再介绍。

------

# 2. Java的对象模型

 [Java的对象模型](https://www.hollischuang.com/archives/1910)

[上一篇](http://www.hollischuang.com/archives/1883)文章中简单介绍过`synchronized`关键字的方式，其中，同步代码块使用`monitorenter`和`monitorexit`两个指令实现，同步方法使用`ACC_SYNCHRONIZED`标记符实现。后面几篇文章会从JVM源码的角度更加深入，层层剥开`synchronized`的面纱。

在进入正题之前，肯定有些基础知识需要铺垫，那么先来看一下一个容易被忽略的但是又很重要的知识点 —— Java对象模型 。

大家都知道的是，Java对象保存在堆内存中。在内存中，一个Java对象包含三部分：**对象头、实例数据和对齐填充**。其中对象头是一个很关键的部分，因为**对象头中包含锁状态标志、线程持有的锁等标志**。这篇文章就主要从Java对象模型入手，找一找我们关系的对象头以及对象头中和锁相关的运行时数据在JVM中是如何表示的。

## 2.1 Java的对象模型

任何一个接触过Java的人都知道，Java是一种面向对象语言。在学习Java的过程中你一定对下面两句话不陌生：

- 1、在面向对象的软件中，对象（Object）是某一个类（Class）的实例。 [维基百科](https://zh.wikipedia.org/wiki/%E5%AF%B9%E8%B1%A1_(%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%A7%91%E5%AD%A6))
- 2、一切皆对象 [Thinking In Java](https://book.douban.com/subject/1474824/)

我们还知道，在JVM的内存结构中，对象保存在堆内存中，而我们在对对象进行操作时，其实操作的是对象的引用。那么对象本身在JVM中的结构是什么样的呢？本文的所有分析均基于[HotSpot](https://github.com/openjdk-mirror/jdk7u-hotspot)虚拟机。

### 2.1.1 oop-klass model

HotSpot是基于c++实现，而c++是一门面向对象的语言，本身是具备面向对象基本特征的，所以Java中的对象表示，最简单的做法是为每个Java类生成一个c++类与之对应。但HotSpot JVM并没有这么做，而是设计了一个`OOP-Klass Model`。**OOP（`Ordinary Object Pointer`）指的是普通对象指针，而`Klass`用来描述对象实例的具体类型**。

为什么HotSpot要设计一套`oop-klass model`呢？答案是：HotSopt JVM的设计者不想让每个对象中都含有一个`vtable`（虚函数表）

这个解释似乎可以说得通。众所周知，C++和Java都是面向对象的语言，面向对象语言有一个很重要的特性就是多态。关于多态的实现，C++和Java有着本质的区别。

> 多态是面向对象的最主要的特性之一，是一种方法的动态绑定，实现运行时的类型决定对象的行为。多态的表现形式是父类指针或引用指向子类对象，在这个指针上调用的方法使用子类的实现版本。多态是IOC、模板模式实现的关键。
>
> > 在C++中通过虚函数表的方式实现多态，每个包含虚函数的类都具有一个虚函数表（virtual table），在这个类对象的地址空间的最靠前的位置存有指向虚函数表的指针。在虚函数表中，按照声明顺序依次排列所有的虚函数。由于C++在运行时并不维护类型信息，所以在编译时直接在子类的虚函数表中将被子类重写的方法替换掉。
> >
> > 在Java中，在运行时会维持类型信息以及类的继承体系。每一个类会在方法区中对应一个数据结构用于存放类的信息，可以通过Class对象访问这个数据结构。其中，类型信息具有superclass属性指示了其超类，以及这个类对应的方法表（其中只包含这个类定义的方法，不包括从超类继承来的）。而每一个在堆上创建的对象，都具有一个指向方法区类型信息数据结构的指针，通过这个指针可以确定对象的类型。

上面这段是我从网上摘取过来的，说的有一定道理，但是也不全对。至于为啥，我会在后文介绍到Klass的时候细说。

关于opp-klass模型的整体定义，在HotSpot的[源码](https://github.com/openjdk-mirror/jdk7u-hotspot)中可以找到。

oops模块可以分成两个相对独立的部分：OOP框架和Klass框架。

在[oopsHierarchy.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/oopsHierarchy.hpp)里定义了oop和klass各自的体系。

### 2.1.2 oop-klass结构

![oops](img/oops.png)

oop体系：

```c++
//定义了oops共同基类
typedef class   oopDesc*                            oop;
//表示一个Java类型实例
typedef class   instanceOopDesc*            instanceOop;
//表示一个Java方法
typedef class   methodOopDesc*                    methodOop;
//表示一个Java方法中的不变信息
typedef class   constMethodOopDesc*            constMethodOop;
//记录性能信息的数据结构
typedef class   methodDataOopDesc*            methodDataOop;
//定义了数组OOPS的抽象基类
typedef class   arrayOopDesc*                    arrayOop;
//表示持有一个OOPS数组
typedef class   objArrayOopDesc*            objArrayOop;
//表示容纳基本类型的数组
typedef class   typeArrayOopDesc*            typeArrayOop;
//表示在Class文件中描述的常量池
typedef class   constantPoolOopDesc*            constantPoolOop;
//常量池告诉缓存
typedef class   constantPoolCacheOopDesc*   constantPoolCacheOop;
//描述一个与Java类对等的C++类
typedef class   klassOopDesc*                    klassOop;
//表示对象头
typedef class   markOopDesc*                    markOop;
```

**上面列出的是整个Oops模块的组成结构，其中包含多个子模块。每一个子模块对应一个类型，每一个类型的OOP都代表一个在JVM内部使用的特定对象的类型。**

从上面的代码中可以看到，有一个变量oop的类型是`oopDesc` ，OOPS类的共同基类型为`oopDesc`。

**在Java程序运行过程中，每创建一个新的对象，在JVM内部就会相应地创建一个对应类型的OOP对象。**在HotSpot中，根据JVM内部使用的对象业务类型， 具有多种`oopDesc`的子类。除了`oppDesc`类型外，opp体系中还有很多`instanceOopDesc`、`arrayOopDesc` 等类型的实例，他们都是`oopDesc`的子类。

![OOP结构](img/OOP结构.png)这些OOPS在JVM内部有着不同的用途，例如**，instanceOopDesc表示类实例，arrayOopDesc表示数组。**也就是说，**当我们使用new创建一个Java对象实例的时候，JVM会创建一个instanceOopDesc对象来表示这个Java对象。同理，当我们使用new创建一个Java数组实例的时候，JVM会创建一个arrayOopDesc对象来表示这个数组对象。**

在HotSpot中，oopDesc类定义在[oop.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/oop.hpp)中，instanceOopDesc定义在[instanceOop.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/instanceOop.hpp)中，arrayOopDesc定义在[arrayOop.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/arrayOop.hpp)中。

简单看一下相关定义：

```c++
class oopDesc {
  friend class VMStructs;
  private:
      volatile markOop  _mark;
      union _metadata {
        wideKlassOop    _klass;
        narrowOop       _compressed_klass;
      } _metadata;

  private:
      // field addresses in oop
      void*     field_base(int offset)        const;

      jbyte*    byte_field_addr(int offset)   const;
      jchar*    char_field_addr(int offset)   const;
      jboolean* bool_field_addr(int offset)   const;
      jint*     int_field_addr(int offset)    const;
      jshort*   short_field_addr(int offset)  const;
      jlong*    long_field_addr(int offset)   const;
      jfloat*   float_field_addr(int offset)  const;
      jdouble*  double_field_addr(int offset) const;
      address*  address_field_addr(int offset) const;
}


class instanceOopDesc : public oopDesc {
}

class arrayOopDesc : public oopDesc {
}
```

通过上面的源码可以看到，`instanceOopDesc`实际上就是继承了`oopDesc`，并没有增加其他的数据结构，也就是说`instanceOopDesc`中主要包含以下几部分数据：`markOop _mark`和`union _metadata` 以及一些不同类型的 `field`。

HotSpot虚拟机中，**对象在内存中存储的布局可以分为三块区域：对象头、实例数据和对齐填充**。在虚拟机内部，一个Java对象对应一个`instanceOopDesc`的对象。**其中对象头包含了两部分内容：`_mark`和`_metadata`，而实例数据则保存在oopDesc中定义的各种field中。**

#### _mark

文章开头我们就说过，之所以我们要写这篇文章，是因为对象头中有和锁相关的运行时数据，这些运行时数据是`synchronized`以及其他类型的锁实现的重要基础，而**关于锁标记、GC分代等信息均保存在`_mark`中**。因为本文主要介绍的`oop-klass`模型，在这里暂时不对对象头做展开，下一篇文章介绍。

#### _metadata

前面介绍到的`_metadata`是一个共用体，其中`_klass`是普通指针，`_compressed_klass`是压缩类指针。在深入介绍之前，就要来到`oop-Klass`中的另外一个主角`klass`了。

####klass

klass体系

```c++
//klassOop的一部分，用来描述语言层的类型
class  Klass;
//在虚拟机层面描述一个Java类
class   instanceKlass;
//专有instantKlass，表示java.lang.Class的Klass
class     instanceMirrorKlass;
//专有instantKlass，表示java.lang.ref.Reference的子类的Klass
class     instanceRefKlass;
//表示methodOop的Klass
class   methodKlass;
//表示constMethodOop的Klass
class   constMethodKlass;
//表示methodDataOop的Klass
class   methodDataKlass;
//最为klass链的端点，klassKlass的Klass就是它自身
class   klassKlass;
//表示instanceKlass的Klass
class     instanceKlassKlass;
//表示arrayKlass的Klass
class     arrayKlassKlass;
//表示objArrayKlass的Klass
class       objArrayKlassKlass;
//表示typeArrayKlass的Klass
class       typeArrayKlassKlass;
//表示array类型的抽象基类
class   arrayKlass;
//表示objArrayOop的Klass
class     objArrayKlass;
//表示typeArrayOop的Klass
class     typeArrayKlass;
//表示constantPoolOop的Klass
class   constantPoolKlass;
//表示constantPoolCacheOop的Klass
class   constantPoolCacheKlass;
```

和`oopDesc`是其他oop类型的父类一样，Klass类是其他klass类型的父类。

![klass](img/klass.png)

Klass向JVM提供两个功能：

- 实现语言层面的Java类（在Klass基类中已经实现）
- 实现Java对象的分发功能（由Klass的子类提供虚函数实现）

文章开头的时候说过：之所以设计`oop-klass`模型，是因为HotSopt JVM的设计者不想让每个对象中都含有一个虚函数表。

**HotSpot JVM的设计者把对象一拆为二，分为`klass`和`oop`，其中`oop`的职能主要在于表示对象的实例数据，所以其中不含有任何虚函数。**而klass为了实现虚函数多态，所以提供了虚函数表。所以，关于Java的多态，其实也有虚函数的影子在。

`_metadata`是一个共用体，其中`_klass`是普通指针，`_compressed_klass`是压缩类指针。这两个指针都指向`instanceKlass`对象，它用来描述对象的具体类型。

### 2.1.3 instanceKlass

JVM在运行时，需要一种用来标识Java内部类型的机制。在HotSpot中的解决方案是：为每一个已加载的Java类创建一个`instanceKlass`对象，用来在JVM层表示Java类。

来看下[instanceKlass](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/instanceKlass.hpp)的内部结构：

```c++
  //类拥有的方法列表
  objArrayOop     _methods;
  //描述方法顺序
  typeArrayOop    _method_ordering;
  //实现的接口
  objArrayOop     _local_interfaces;
  //继承的接口
  objArrayOop     _transitive_interfaces;
  //域
  typeArrayOop    _fields;
  //常量
  constantPoolOop _constants;
  //类加载器
  oop             _class_loader;
  //protected域
  oop             _protection_domain;
      ....
```

可以看到，一个类该具有的东西，这里面基本都包含了。

这里还有个点需要简单介绍一下。

在JVM中，对象在内存中的基本存在形式就是oop。那么，对象所属的类，在JVM中也是一种对象，因此它们实际上也会被组织成一种oop，即klassOop。同样的，对于klassOop，也有对应的一个klass来描述，它就是klassKlass，也是klass的一个子类。klassKlass作为oop的klass链的端点。关于对象和数组的klass链大致如下图：

![400_ac3_932](img/400_ac3_932.png)

在这种设计下，JVM对内存的分配和回收，都可以采用统一的方式来管理。oop-klass-klassKlass关系如图：

![2579123-5b117a7c06e83d84](img/2579123-5b117a7c06e83d84.png)

## 2.2 内存存储

关于一个Java对象，他的存储是怎样的，一般很多人会回答：对象存储在堆上。稍微好一点的人会回答：对象存储在堆上，对象的引用存储在栈上。今天，再给你一个更加显得牛逼的回答：

> 对象的实例（instantOopDesc)保存在堆上，对象的元数据（instantKlass）保存在方法区，对象的引用保存在栈上。

**对象的元数据不是指Class对象，使用Class对象来访问元数据。**

其实如果细追究的话，上面这句话有点故意卖弄的意思。因为我们都知道。**方法区用于存储虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。** 所谓加载的类信息，其实不就是给每一个被加载的类都创建了一个 instantKlass对象么。

talk is cheap ,show me the code ：

```java
class Model
{
    public static int a = 1;
    public int b;

    public Model(int b) {
        this.b = b;
    }
}

public static void main(String[] args) {
    int c = 10;
    Model modelA = new Model(2);
    Model modelB = new Model(3);
}
```

存储结构如下：

![20170615230126453](img/20170615230126453-20200219215106002.jpeg)

从上图中可以看到，在方法区的instantKlass中有一个`int a=1`的数据存储。在堆内存中的两个对象的oop中，分别维护着`int b=3`,`int b=2`的实例数据。和oopDesc一样，instantKlass也维护着一些`fields`，用来保存类中定义的类数据，比如`int a=1`。

## 2.3 总结

每一个Java类，在被JVM加载的时候，JVM会给这个类创建一个`instanceKlass`，保存在方法区，用来在JVM层表示该Java类。当我们在Java代码中，使用new创建一个对象的时候，JVM会创建一个`instanceOopDesc`对象，这个对象中包含了两部分信息，对象头以及实例数据。元数据其实维护的是指针，指向的是对象所属的类的`instanceKlass`。

## 2.4 参考资料

[【理解HotSpot虚拟机】对象在jvm中的表示：OOP-Klass模型](http://blog.csdn.net/linxdcn/article/details/73287490)

[Java反射: 从JDK到JVM全链路详解](http://www.jianshu.com/p/b6cb4c694951)

[HotSpotVM 对象机制实现浅析#1](http://www.voidcn.com/article/p-pzznrtkc-ez.html)

[HotSpot实战](https://book.douban.com/subject/25847620/)

------

# 3. Java的对象头

[Java的对象头](https://www.hollischuang.com/archives/1953)

[上一篇](http://www.hollischuang.com/archives/1910)文章中我们从HotSpot的源码入手，介绍了Java的对象模型。这一篇文章在上一篇文章的基础上再来介绍一下Java的对象头。主要介绍一下对象头的作用，结构以及他和锁的关系。

## 3.1 Java对象模型回顾与勘误

在上一篇文章中，关于对象头的部分描述有误，我已经在我博客的文章中就行修正 。这里再重新表述一下。

每一个Java类，在被JVM加载的时候，JVM会给这个类创建一个`instanceKlass`，保存在方法区，用来在JVM层表示该Java类。当我们在Java代码中，使用new创建一个对象的时候，JVM会创建一个`instanceOopDesc`对象，这个对象中包含了对象头以及实例数据。

这里提到的对象头到底是什么呢？

```c++
class oopDesc {
  friend class VMStructs;
 private:
  volatile markOop  _mark;
  union _metadata {
    wideKlassOop    _klass;
    narrowOop       _compressed_klass;
  } _metadata;
}
```

上面代码中的`_mark`和`_metadata`其实就是对象头的定义。关于`_metadata`之前就介绍过，这里不再赘述。由于这个专题主要想介绍和JAVA并发相关的知识，所以本文展开介绍一下`_mark` ，即mark word。

什么是对象头？

**对象头信息是与对象自身定义的数据无关的额外存储成本**。

考虑到虚拟机的空间效率，Mark Word被设计成一个非固定的数据结构以便在极小的空间内存储尽量多的信息，它会根据对象的状态复用自己的存储空间。

对markword的设计方式上，非常像网络协议报文头：将mark word划分为多个比特位区间，并在不同的对象状态下赋予比特位不同的含义。下图描述了在32位虚拟机上，在对象不同状态时 mark word各个比特位区间的含义。

![ObjectHead](img/ObjectHead-1024x329.png)

同样，在HotSpot的源码中我们可以找到关于对象头对象的定义，会一一印证上图的描述。对应与[markOop.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/markOop.hpp)类。

```c++
enum { age_bits                 = 4,
      lock_bits                = 2,
      biased_lock_bits         = 1,
      max_hash_bits            = BitsPerWord - age_bits - lock_bits - biased_lock_bits,
      hash_bits                = max_hash_bits > 31 ? 31 : max_hash_bits,
      cms_bits                 = LP64_ONLY(1) NOT_LP64(0),
      epoch_bits               = 2
};
```

从上面的枚举定义中可以看出，**对象头中主要包含了GC分代年龄、锁状态标记、哈希码、epoch等信息**。

从上图中可以看出，对象的状态一共有五种，分别是无锁态、轻量级锁、重量级锁、GC标记和偏向锁。在32位的虚拟机中有两个Bits是用来存储锁的标记为的，但是我们都知道，两个bits最多只能表示四种状态：00、01、10、11，那么第五种状态如何表示呢 ，就要额外依赖1Bit的空间，使用0和1来区分。

> 在32位的HotSpot虚拟机 中对象未被锁定的状态下，Mark Word的32个Bits空间中的25Bits用于存储对象哈希码(HashCode)，4Bits用于存储对象分代年龄，2Bits用于存储锁标志位，1Bit固定为0，表示非偏向锁。

[markOop.hpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/oops/markOop.hpp)类中有关于对象状态的定义：

```
  enum { locked_value             = 0,
         unlocked_value           = 1,
         monitor_value            = 2,
         marked_value             = 3,
         biased_lock_pattern      = 5
  };
```

简单翻译一下：

> locked_value(00) = 0
>
> unlocked_value(01) = 1
>
> monitor_value(10) = 2
>
> marked_value(11) = 3
>
> biased_lock_pattern(101) = 5

关于为什么要定义这么多状态，上面提到的轻量级锁、重量级锁、偏向锁以及他们之前的关系，会在下一篇文章中重点阐述，敬请期待。

------

# 4. Moniter的实现原理

[Moniter的实现原理](https://www.hollischuang.com/archives/2030)

在[深入理解多线程（一）——Synchronized的实现原理](http://www.hollischuang.com/archives/1883)中介绍过关于`Synchronize`的实现原理，无论是同步方法还是同步代码块，无论是`ACC_SYNCHRONIZED`还是`monitorenter`、`monitorexit`都是基于`Monitor`实现的，那么这篇来介绍下什么是**Monitor**。

## 4.1 操作系统中的管程

如果你在大学学习过操作系统，你可能还记得管程（monitors）在操作系统中是很重要的概念。同样Monitor在java同步机制中也有使用。

> 管程 (英语：Monitors，也称为监视器) 是一种程序结构，结构内的多个子程序（对象或模块）形成的多个工作线程互斥访问共享资源。这些共享资源一般是硬件设备或一群变量。管程实现了在一个时间点，最多只有一个线程在执行管程的某个子程序。与那些通过修改数据结构实现互斥访问的并发程序设计相比，管程实现很大程度上简化了程序设计。 管程提供了一种机制，线程可以临时放弃互斥访问，等待某些条件得到满足后，重新获得执行权恢复它的互斥访问。

## 4.2 Java线程同步相关的Moniter

在多线程访问共享资源的时候，经常会带来可见性和原子性的安全问题。为了解决这类线程安全的问题，Java提供了同步机制、互斥锁机制，这个机制保证了在同一时刻只有一个线程能访问共享资源。这个机制的保障来源于监视锁Monitor，每个对象都拥有自己的监视锁Monitor。

先来举个例子，然后我们在上源码。我们可以把监视器理解为包含一个特殊的房间的建筑物，这个特殊房间同一时刻只能有一个客人（线程）。这个房间中包含了一些数据和代码。

![Java-Monitor](img/Java-Monitor.jpg)

如果一个顾客想要进入这个特殊的房间，他首先需要在走廊（Entry Set）排队等待。调度器将基于某个标准（比如 FIFO）来选择排队的客户进入房间。如果，因为某些原因，该客户客户暂时因为其他事情无法脱身（线程被挂起），那么他将被送到另外一间专门用来等待的房间（Wait Set），这个房间的可以可以在稍后再次进入那件特殊的房间。如上面所说，这个建筑屋中一共有三个场所。

![java-monitor-associate-with-object](img/java-monitor-associate-with-object.jpg)

总之，监视器是一个用来监视这些线程进入特殊的房间的。他的义务是保证（同一时间）只有一个线程可以访问被保护的数据和代码。

Monitor其实是一种同步工具，也可以说是一种同步机制，它通常被描述为一个对象，主要特点是：

> 对象的所有方法都被“互斥”的执行。好比一个Monitor只有一个运行“许可”，任一个线程进入任何一个方法都需要获得这个“许可”，离开时把许可归还。
>
> 通常提供singal机制：允许正持有“许可”的线程暂时放弃“许可”，等待某个谓词成真（条件变量），而条件成立后，当前进程可以“通知”正在等待这个条件变量的线程，让他可以重新去获得运行许可。

## 4.3 监视器的实现

在Java虚拟机(HotSpot)中，Monitor是基于C++实现的，由[ObjectMonitor](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/runtime/objectMonitor.cpp)实现的，其主要数据结构如下：

```c++
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

ObjectMonitor中有几个关键属性：

> _owner：指向持有ObjectMonitor对象的线程
>
> _WaitSet：存放处于wait状态的线程队列
>
> _EntryList：存放处于等待锁block状态的线程队列
>
> _recursions：锁的重入次数
>
> _count：用来记录该线程获取锁的次数

当多个线程同时访问一段同步代码时，首先会进入`_EntryList`队列中，当某个线程获取到对象的monitor后进入`_Owner`区域并把monitor中的`_owner`变量设置为当前线程，同时monitor中的计数器`_count`加1。即获得对象锁。

若持有monitor的线程调用`wait()`方法，将释放当前持有的monitor，`_owner`变量恢复为`null`，`_count`自减1，同时该线程进入`_WaitSet`集合中等待被唤醒。若当前线程执行完毕也将释放monitor(锁)并复位变量的值，以便其他线程进入获取monitor(锁)。如下图所示

![monitor](img/monitor.png)

ObjectMonitor类中提供了几个方法：

**获得锁**

```java
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

![lockenter](img/lockenter.png)

**释放锁**

```java
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

![lockexit](img/lockexit.png)

除了enter和exit方法以外，[objectMonitor.cpp](https://github.com/openjdk-mirror/jdk7u-hotspot/blob/50bdefc3afe944ca74c3093e7448d6b889cd20d1/src/share/vm/runtime/objectMonitor.cpp)中还有

```
void      wait(jlong millis, bool interruptable, TRAPS);
void      notify(TRAPS);
void      notifyAll(TRAPS);
```

等方法。

## 4.4 总结

上面介绍的就是HotSpot虚拟机中Moniter的的加锁以及解锁的原理。

通过这篇文章我们知道了`sychronized`加锁的时候，会调用objectMonitor的`enter`方法，解锁的时候会调用`exit`方法。事实上，只有在JDK1.6之前，`synchronized`的实现才会直接调用ObjectMonitor的`enter`和`exit`，这种锁被称之为重量级锁。为什么说这种方式操作锁很重呢？

- Java的线程是映射到操作系统原生线程之上的，如果要阻塞或唤醒一个线程就需要操作系统的帮忙，这就要从用户态转换到核心态，因此状态转换需要花费很多的处理器时间，对于代码简单的同步块（如被`synchronized`修饰的`get` 或`set`方法）状态转换消耗的时间有可能比用户代码执行的时间还要长，所以说`synchronized`是java语言中一个重量级的操纵。

所以，在JDK1.6中出现对锁进行了很多的优化，进而出现轻量级锁，偏向锁，锁消除，适应性自旋锁，锁粗化(自旋锁在1.4就有 只不过默认的是关闭的，jdk1.6是默认开启的)，这些操作都是为了在线程之间更高效的共享数据 ，解决竞争问题。后面的文章会继续介绍这几种锁以及他们之间的关系。

[Java Synchronized实现原理](http://bigdatadecode.club/JavaSynchronizedTheory.html)

[JVM源码分析之Object.wait/notify实现](http://www.jianshu.com/p/f4454164c017)

[Linux Kernel CMPXCHG函数分析](http://blog.csdn.net/penngrove/article/details/44175387)

[从jvm源码看synchronized](https://www.cnblogs.com/kundeg/p/8422557.html)



------

# 5. Java虚拟机的锁优化技术

[Java虚拟机的锁优化技术](https://www.hollischuang.com/archives/2344)

本文是《[深入理解多线程](http://www.hollischuang.com/archives/tag/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3%E5%A4%9A%E7%BA%BF%E7%A8%8B)》的第五篇文章，前面几篇文章中我们从synchronized的实现原理开始，一直介绍到了Monitor的实现原理。

## 5.1 前情提要

通过前面几篇文章，我们已经知道：

1、同步方法通过`ACC_SYNCHRONIZED`关键字隐式的对方法进行加锁。当线程要执行的方法被标注上`ACC_SYNCHRONIZED`时，需要先获得锁才能执行该方法。《[深入理解多线程（一）——Synchronized的实现原理](http://www.hollischuang.com/archives/1883)》

2、同步代码块通过`monitorenter`和`monitorexit`执行来进行加锁。当线程执行到`monitorenter`的时候要先获得所锁，才能执行后面的方法。当线程执行到`monitorexit`的时候则要释放锁。《[深入理解多线程（四）—— Moniter的实现原理](http://www.hollischuang.com/archives/2030)》

3、在HotSpot虚拟机中，使用**oop-klass模型**来表示对象。每一个Java类，在被JVM加载的时候，JVM会给这个类创建一个`instanceKlass`，保存在方法区，用来在JVM层表示该Java类。当我们在Java代码中，使用new创建一个对象的时候，JVM会创建一个`instanceOopDesc`对象，这个对象中包含了对象头以及实例数据。《[深入理解多线程（二）—— Java的对象模型](http://www.hollischuang.com/archives/1910)》

4、对象头中主要包含了GC分代年龄、锁状态标记、哈希码、epoch等信息。对象的状态一共有五种，分别是无锁态、轻量级锁、重量级锁、GC标记和偏向锁。《[深入理解多线程（三）—— Java的对象头](http://www.hollischuang.com/archives/1953)》

**在上一篇文章的最后，我们说过，事实上，只有在JDK1.6之前，synchronized的实现才会直接调用ObjectMonitor的enter和exit，这种锁被称之为重量级锁。**

**高效并发是从JDK 1.5 到 JDK 1.6的一个重要改进，HotSpot虚拟机开发团队在这个版本中花费了很大的精力去对Java中的锁进行优化，如适应性自旋、锁消除、锁粗化、轻量级锁和偏向锁等。这些技术都是为了在线程之间更高效的共享数据，以及解决竞争问题。**

本文，主要先来介绍一下自旋、锁消除以及锁粗化等技术。

这里简单说明一下，本文要介绍的这几个概念，以及后面要介绍的轻量级锁和偏向锁，其实对于使用他的开发者来说是屏蔽掉了的，也就是说，**作为一个Java开发，你只需要知道你想在加锁的时候使用synchronized就可以了，具体的锁的优化是虚拟机根据竞争情况自行决定的。**

也就是说，在JDK 1.5 以后，我们即将介绍的这些概念，都被封装在synchronized中了。

## 5.2 线程状态

要想把锁说清楚，一个重要的概念不得不提，那就是线程和线程的状态。锁和线程的关系是怎样的呢，举个简单的例子你就明白了。

比如，你今天要去银行办业务，你到了银行之后，要先取一个号，然后你坐在休息区等待叫号，过段时间，广播叫到你的号码之后，会告诉你去哪个柜台办理业务，这时，你拿着你手里的号码，去到对应的柜台，找相应的柜员开始办理业务。当你办理业务的时候，这个柜台和柜台后面的柜员只能为你自己服务。当你办完业务离开之后，广播再喊其他的顾客前来办理业务。

![Pic1](img/Pic1.png)

> 这个例子中，每个顾客是一个**线程**。 柜台前面的那把椅子，就是**锁**。 柜台后面的柜员，就是**共享资源**。 你发现无法直接办理业务，要取号等待的过程叫做**阻塞**。 当你听到叫你的号码的时候，你起身去办业务，这就是**唤醒**。 当你坐在椅子上开始办理业务的时候，你就**获得锁**。 当你办完业务离开的时候，你就**释放锁**。

对于线程来说，一共有五种状态，分别为：初始状态(New) 、就绪状态(Runnable) 、运行状态(Running) 、阻塞状态(Blocked) 和死亡状态(Dead) 。

![thread](img/thread.png)

## 5.3 自旋锁

在[前一篇](http://www.hollischuang.com/archives/2030)文章中，我们介绍的`synchronized`的实现方式中使用`Monitor`进行加锁，这是一种互斥锁，为了表示他对性能的影响我们称之为重量级锁。

这种互斥锁在互斥同步上对性能的影响很大，Java的线程是映射到操作系统原生线程之上的，如果要阻塞或唤醒一个线程就需要操作系统的帮忙，这就要从用户态转换到内核态，因此状态转换需要花费很多的处理器时间。

就像去银行办业务的例子，当你来到银行，发现柜台前面都有人的时候，你需要取一个号，然后再去等待区等待，一直等待被叫号。这个过程是比较浪费时间的，那么有没有什么办法改进呢？

有一种比较好的设计，那就是银行提供自动取款机，当你去银行取款的时候，你不需要取号，不需要去休息区等待叫号，你只需要找到一台取款机，排在其他人后面等待取款就行了。

![Pic2](img/Pic2.png)

之所以能这样做，是因为取款的这个过程相比较之下是比较节省时间的。如果所有人去银行都只取款，或者办理业务的时间都很短的话，那也就可以不需要取号，不需要去单独的休息区，不需要听叫号，也不需要再跑到对应的柜台了。

而在程序中，Java虚拟机的开发工程师们在分析过大量数据后发现：**共享数据的锁定状态一般只会持续很短的一段时间，为了这段时间去挂起和恢复线程其实并不值得。**

如果物理机上有多个处理器，可以让多个线程同时执行的话。我们就可以让后面来的线程“稍微等一下”，但是并不放弃处理器的执行时间，看看持有锁的线程会不会很快释放锁。这个“稍微等一下”的过程就是自旋。

自旋锁在JDK 1.4中已经引入，在JDK 1.6中默认开启。

很多人在对于自旋锁的概念不清楚的时候可能会有以下疑问：这么听上去，自旋锁好像和阻塞锁没啥区别，反正都是等着嘛。

- 对于去银行取钱的你来说，站在取款机面前等待和去休息区等待叫号有一个很大的区别：
  - 那就是如果你在休息区等待，这段时间你什么都不需要管，随意做自己的事情，等着被唤醒就行了。
  - 如果你在取款机面前等待，那么你需要时刻关注自己前面还有没有人，因为没人会唤醒你。
  - 很明显，这种直接去取款机前面排队取款的效率是比较高。

**所以呢，自旋锁和阻塞锁最大的区别就是，到底要不要放弃处理器的执行时间。对于阻塞锁和自旋锁来说，都是要等待获得共享资源。但是阻塞锁是放弃了CPU时间，进入了等待区，等待被唤醒。而自旋锁是一直“自旋”在那里，时刻的检查共享资源是否可以被访问。**

由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不停增加时，性能下降明显，因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。

## 5.4 锁消除

除了自旋锁之后，JDK中还有一种锁的优化被称之为锁消除。还拿去银行取钱的例子说。

你去银行取钱，所有情况下都需要取号，并且等待吗？其实是不用的，当银行办理业务的人不多的时候，可能根本不需要取号，直接走到柜台前面办理业务就好了。能这么做的前提是，没有人和你抢着办业务。

![Pic3](img/Pic3.png)

上面的这种例子，在锁优化中被称作“锁消除”，是JIT编译器对内部锁的具体实现所做的一种优化。

在动态编译同步块的时候，JIT编译器可以借助一种被称为逃逸分析（Escape Analysis）的技术来判断同步块所使用的锁对象是否只能够被一个线程访问而没有被发布到其他线程。

如果同步块所使用的锁对象通过这种分析被证实只能够被一个线程访问，那么JIT编译器在编译这个同步块的时候就会取消对这部分代码的同步。

如以下代码：

```java
public void f() {
    Object hollis = new Object();
    synchronized(hollis) {
        System.out.println(hollis);
    }
}
```

代码中对`hollis`这个对象进行加锁，但是`hollis`对象的生命周期只在`f()`方法中，并不会被其他线程所访问到，所以在JIT编译阶段就会被优化掉。优化成：

```java
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

## 5.5 锁粗化

很多人都知道，在代码中，需要加锁的时候，我们提倡尽量减小锁的粒度，这样可以避免不必要的阻塞。

这也是很多人原因是用同步代码块来代替同步方法的原因，因为往往他的粒度会更小一些，这其实是很有道理的。

还是我们去银行柜台办业务，最高效的方式是你坐在柜台前面的时候，只办和银行相关的事情。如果这个时候，你拿出手机，接打几个电话，问朋友要往哪个账户里面打钱，这就很浪费时间了。最好的做法肯定是提前准备好相关资料，在办理业务时直接办理就好了。

![Pic4](img/Pic4.png)

加锁也一样，把无关的准备工作放到锁外面，锁内部只处理和并发相关的内容。这样有助于提高效率。

那么，这和锁粗化有什么关系呢？可以说，大部分情况下，减小锁的粒度是很正确的做法，只有一种特殊的情况下，会发生一种叫做锁粗化的优化。

就像你去银行办业务，你为了减少每次办理业务的时间，你把要办的五个业务分成五次去办理，这反而适得其反了。因为这平白的增加了很多你重新取号、排队、被唤醒的时间。

如果在一段代码中连续的对同一个对象反复加锁解锁，其实是相对耗费资源的，这种情况可以适当放宽加锁的范围，减少性能消耗。

当JIT发现一系列连续的操作都对同一个对象反复加锁和解锁，甚至加锁操作出现在循环体中的时候，会将加锁同步的范围扩散（粗化）到整个操作序列的外部。

如以下代码：

```shell
for(int i=0;i<100000;i++){  
    synchronized(this){  
        do();  
}  
```

会被粗化成：

```java
synchronized(this){  
    for(int i=0;i<100000;i++){  
        do();  
}  
```

**这其实和我们要求的减小锁粒度并不冲突。减小锁粒度强调的是不要在银行柜台前做准备工作以及和办理业务无关的事情。而锁粗化建议的是，同一个人，要办理多个业务的时候，可以在同一个窗口一次性办完，而不是多次取号多次办理。**

## 5.6 总结

自Java 6/Java 7开始，Java虚拟机对内部锁的实现进行了一些优化。这些优化主要包括锁消除（Lock Elision）、锁粗化（Lock Coarsening）、偏向锁（Biased Locking）以及适应性自旋锁（Adaptive Locking）。这些优化仅在Java虚拟机server模式下起作用（即运行Java程序时我们可能需要在命令行中指定Java虚拟机参数“-server”以开启这些优化）。

本文主要介绍了自旋锁、锁粗化和锁消除的概念。在JIT编译过程中，虚拟机会根据情况使用这三种技术对锁进行优化，目的是减少锁的竞争，提升性能。
