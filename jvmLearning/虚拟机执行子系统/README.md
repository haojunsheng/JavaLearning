[TOC]

# 前言

了解虚拟机是究竟如何执行程序的。

https://blog.csdn.net/u010349169/column/info/jvm-principle

> **需要学习CLass文件的具体是怎么组成的。**

 [CLass文件结构.md](CLass文件结构.md) 

讲解了Class文件是如何进行组织的。比较重要的有常量池。

我们这里研究的是class文件中的常量池。注意和运行时常量池，字符串常量池进行区分。参考 [好好说说Java中的常量池.md](../好好说说Java中的常量池.md) 

**常量池包含什么？**

字面量（基本类型数据和字符串的值）和符号引用（类或接口的名字或描述符，字段的名字或描述符，方法的名字或描述符）

![](https://ws4.sinaimg.cn/large/006tNc79gy1fzlf1xelkrj30gh0b8jsk.jpg)

**Classs常量池有什么用？**

由于此时class文件没有进行连接，在加载的时候才进行动态链接，为了进行动态链接，把符号引用和字面量放入常量池，进而确定其真正在内存中的地址。

**Class常量池是怎么实现的？**

- int和float（CONSTANT_Integer_info, CONSTANT_Float_info）
- long和double（CONSTANT_Long_info、CONSTANT_Double_info）
- String（CONSTANT_String_info、CONSTANT_Utf8_info）
- 类名（CONSTANT_Class_info）
- 字段名（CONSTANT_Fieldref_info, CONSTANT_Name_Type_info）
  - **只有在类中的其他地方引用到了，才会将他放到常量池中**。
- method方法（CONSTANT_Methodref_info, CONSTANT_Name_Type_info）



接下来是描述类或者接口的：访问标志，类索引，父类索引，接口索引集合。

再接下来是字段的表示：字段表集合（其中字段表中的属性表是为了对静态字段进行赋值）。

在接下来是方法的表示：方法表集合（其中的属性表存放机器指令，异常信息等）



> JVM内存结构的划分

 [JVM运行时数据区.md](JVM运行时数据区.md) 



> jvm机器指令集

 [JVM机器指令集.md](JVM机器指令集.md) 

讲解的是方法是如何进行调用的。

**每个线程拥有一个虚拟机栈，每个方法拥有一个栈帧。**

栈帧的组成：局部变量表，操作数栈，动态链接，方法出口和其他信息。

>  JVM类加载器机制与类加载过程

 [JVM类加载器机制与类加载过程.md](JVM类加载器机制与类加载过程.md) 

 [类加载机制.md](../类加载机制.md) 













