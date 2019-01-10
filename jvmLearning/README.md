[TOC]

jvm的学习笔记

[jvm思维导图](https://mubu.com/doc/2gXImpU6uw)

# 三个大问题

- 什么是java虚拟机？

  `Java Virtual Mechinal`(JAVA虚拟机)。JVM是JRE的一部分，它是一个虚构出来的计算机，是通过在实际的计算机上仿真模拟各种计算机功能来实现的。

- java虚拟机有什么用处？

  JVM 的主要工作是解释自己的指令集（即字节码）并映射到本地的 CPU 的指令集或 OS 的系统调用。

- java虚拟机是怎么实现的？

  JVM有自己完善的硬件架构，如处理器、堆栈、寄存器等，还具有相应的指令系统。Java语言是跨平台运行的，其实就是不同的操作系统，使用不同的JVM映射规则，让其与操作系统无关，完成了跨平台性。JVM 对上层的 Java 源文件是不关心的，它关注的只是由源文件生成的类文件（ class file ）。类文件的组成包括 JVM 指令集，符号表以及一些补助信息。

#  学完jvm应该可以回答的问题？

## 1.JVM内存结构

[jvm内存结构](jvm内存结构.md)

堆、栈、方法区、直接内存、堆和栈区别

## 2.java内存模型

 [Java内存模型.md](Java内存模型.md) 

内存可见性、重排序、顺序一致性、volatile、锁、final

## 3. 垃圾回收

 [垃圾回收.md](垃圾回收.md) 

内存分配策略、垃圾收集器（G1）、GC算法、GC参数、对象存活的判定 

## 4.JVM参数及调优

 [jvm参数调优.md](jvm参数调优.md) 

## 5.Java对象模型

 [Java对象模型.md](Java对象模型.md) 

oop-klass、对象头

 [JVM内存结构 VS Java内存模型 VS Java对象模型.md](JVM内存结构 VS Java内存模型 VS Java对象模型.md) 

## 6.HotSpot

 [HotSpot.md](HotSpot.md) 

即时编译器、编译优化

## 7.类加载机制

 [类加载机制.md](类加载机制.md) 

classLoader、类加载过程、双亲委派（破坏双亲委派）、模块化（jboss modules、osgi、jigsaw）

## 8.虚拟机性能监控与故障处理工具

 [虚拟机性能监控与故障处理工具.md](虚拟机性能监控与故障处理工具.md) 

jps, jstack, jmap、jstat, jconsole, jinfo, jhat, javap, btrace、TProfiler













