# 前言



# java基础

## 1. 谈谈你对Java平台的理解

Java本身是一种面向对象的语言，最显著的特性有两个方面，一是所谓的“书写一次，到处运行”(Write once, run anywhere)，能够非常容易地获得跨平台能力;另外就是垃圾收 集(GC, Garbage Collection)，Java通过垃圾收集器(Garbage Collector)回收分配内存，大部分情况下，程序员不需要自己操心内存的分配和回收。

我们日常会接触到JRE(Java Runtime Environment)或者JDK(Java Development Kit)。 JRE，也就是Java运行环境，包含了JVM和Java类库，以及一些模块等。 而JDK可以看作是JRE的一个超集，提供了更多工具，比如编译器、各种诊断工具等。

对于“Java是解释执行”这句话，这个说法不太准确。我们开发的Java的源代码，首先通过Javac编译成为字节码(bytecode)，然后，在运行时，通过 Java虚拟机(JVM)内嵌的 解释器将字节码转换成为最终的机器码。但是常见的JVM，比如我们大多数情况使用的Oracle JDK提供的Hotspot JVM，都提供了JIT(Just-In-Time)编译器，也就是通常所说的 动态编译器，JIT能够在运行时将热点代码编译成机器码，这种情况下部分热点代码就属于编译执行，而不是解释执行了。



# 参考

