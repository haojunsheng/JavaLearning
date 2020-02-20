[TOC]

# 前言

java虚拟机是如何执行class文件的。

# 1. Java虚拟机对运行时虚拟机栈（JVM Stack）的组织

​    Java虚拟机在运行时会为每一个线程在内存中分配了一个虚拟机栈，来表示线程的运行状态和信息，虚拟机栈中的元素称之为栈帧（JVM stack frame）,每一个栈帧表示这对一个方法的调用信息。如下所示：

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzk9n6i5m9j30j10j4acx.jpg)

上述的描述可能会有点抽象，为了给读者一个直观的感受，我们定义一个简单的Java类，然后执行这个运行这个类，逐步分析整个Java虚拟机的运行时信息的组织的。

# 2.  方法调用过程在JVM中是如何表示的

```java
/**
- JVM 原理简单用例

- @author louis
  *
   */
  public class Bootstrap {

  public static void main(String[] args) {
  	String name = "Louis";
  	greeting(name);
  }

  public static void greeting(String name)
  {
  	System.out.println("Hello,"+name);
  }

}
```

当我们将Bootstrap.java 编译成Bootstrap.class 并运行这段程序的时候，在JVM复杂的运行逻辑中，会有以下几步：

1. 首先JVM会先将这个Bootstrap.class 信息加载到 内存中的方法区(Method Area)中。

    Bootstrap.class 中包含了常量池信息，方法的定义 以及编译后的方法实现的二进制形式的机器指令，所有的线程共享一个方法区，从中读取方法定义和方法的指令集。

2. 接着，JVM会在Heap堆上为Bootstrap.class 创建一个Class<Bootstrap>实例用来表示Bootstrap.class 的 类实例。

3. JVM开始执行main方法，这时会为main方法创建一个栈帧，以表示main方法的整个执行过程（我会在后面章节中详细展开这个过程）；

4. main方法在执行的过程之中，调用了greeting静态方法，则JVM会为greeting方法创建一个栈帧，推到虚拟机栈顶（我会在后面章节中详细展开这个过程）。

 5.当greeting方法运行完成后，则greeting方法出栈，main方法继续运行；

![](https://ws4.sinaimg.cn/large/006tNc79gy1fzka707nc0j30ky0gsdhl.jpg)



JVM方法调用的过程是通过栈帧来实现的，那么，方法的指令是如何运行的呢？弄清楚这个之前，我们要先了解对于JVM而言，方法的结构是什么样的。

我们知道，class 文件时 JVM能够识别的二进制文件，其中通过特定的结构描述了每个方法的定义。

JVM在编译Bootstrap.java 的过程中，在将源代码编译成二进制机器码的同时，会判断其中的每一个方法的三个信息：

 1 ).  在运行时会使用到的局部变量的数量（作用是：当JVM为方法创建栈帧的时候，在栈帧中为该方法创建一个局部变量表，来存储方法指令在运算时的局部变量值）

  2 ).  其机器指令执行时所需要的最大的操作数栈的大小（当JVM为方法创建栈帧的时候，在栈帧中为方法创建一个操作数栈，保证方法内指令可以完成工作）

  3 ).  方法的参数的数量

经过编译之后，我们可以得到main方法和greeting方法的信息如下

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzkayabt4qj30kj09pt9w.jpg)

根据编译出来的结果，greeting的操作数栈大小应该是2。

![](https://ws1.sinaimg.cn/large/006tNc79gy1fzkb8y4vn2j31720c2wgj.jpg) 

**关于操作数栈：**

虚拟机把操作数栈作为它的工作区——大多数指令都要从这里弹出数据，执行运算，然后把结果压回操作数栈。比如，iadd指令就要从操作数栈中弹出两个整数，执行加法运算，其结果又压回到操作数栈中，看看下面的示例，它演示了虚拟机是如何把两个int类型的局部变量相加，再把结果保存到第三个局部变量的：

1. ```java
   1. begin  
   2. iload_0    // push the int in local variable 0 onto the stack  
   3. iload_1    // push the int in local variable 1 onto the stack  
   4. iadd       // pop two ints, add them, push result  
   5. istore_2   // pop int, store into local variable 2  
   6. end  
   ```

​        在这个字节码序列里，前两个指令iload_0和iload_1将存储在局部变量中索引为0和1的整数压入操作数栈中，其后iadd指令从操作数栈中弹出那两个整数相加，再将结果压入操作数栈。第四条指令istore_2则从操作数栈中弹出结果，并把它存储到局部变量区索引为2的位置。下图详细表述了这个过程中局部变量和操作数栈的状态变化，图中没有使用的局部变量区和操作数栈区域以空白表示。

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzkbesqzo3j30k00cit9c.jpg)

**JVM运行main方法的过程：**

## 2.1 **为main方法创建栈帧：**

   JVM解析main方法，发现其 局部变量的数量为 2，操作数栈的数量为1， 则会为main方法创建一个栈帧（VM Stack），并将其加入虚拟机栈中：

![](https://ws4.sinaimg.cn/large/006tNc79gy1fzkb1e2nqnj30la0n8q4z.jpg)

## 2.2  **完成栈帧初始化：**

main栈帧创建完成后，会将栈帧push 到虚拟机栈中，现在有两步重要的事情要做：

a). 计算PC值。PC 是指令计数器，其内部的值决定了JVM虚拟机下一步应该执行哪一个机器指令，而机器指令存放在方法区，我们需要让PC的值指向方法区的main方法上；

初始化 PC = main方法在方法区指令的地址+0；

b). 局部变量的初始化。main方法有个入参(String[] args) ，JVM已经在main所在的栈帧的局部变量表中为其空出来了一个slot ，我们需要将 args 的引用值初始化到局部点亮表中;

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzkbudt2apj30ap0750sz.jpg)

JVM指令通用形式如下:

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzm44fj8zjj30bs096glu.jpg)

JVM常用指令解析：

**invokespecial**

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzm45975tzj30bm07awep.jpg)

说明：invokespecial 用于调用实例方法，专门用来处理调用超类方法、私有方法和实例初始化方法。

indexByte1 和indexByte2 用于组成常量池中的索引（(indexbyte1 << 8)|indexbyte2）。所指向的常量项必须是 MethodRef Info 类型。同时该条指令还会创建一个函数栈帧，然后从当前的操作数栈中出栈被调用的方法的参数，并且将其放到被调用方法的函数栈帧的本地变量表中。

**aload_n**

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzm480w9dnj30c20aywex.jpg)

说明：aload_n 从局部变量表加载一个 reference 类型值到操作数栈中，至于从当前函数栈帧的本地变量表中加载哪个变量是有N的值决定的。

**astore_n**

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzm49gm9mvj30cc0as3yz.jpg)

说明：将一个 reference 类型数据保存到局部变量表中，至于保存在局部变量表的哪个位置就由 N 的值决定。



1. 接着JVM开始读取PC指向的机器指令。如上图所示，main方法的指令序列：`**12 10 4c 2b b8 20 12 b1**` ，通过JVM虚拟机指令集规范，可以将这个指令序列解析成以下Java汇编语言:

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzkc1ygy92j31ew0rqaiu.jpg)

![image-20190126220054847](/Users/anapodoton/Library/Application Support/typora-user-images/image-20190126220054847.png)

当main方法调用greeting()时， JVM会为greeting方法创建一个栈帧，用以表示对greeting方法的调用，具体栈帧信息如下：

![](https://ws1.sinaimg.cn/large/006tNc79gy1fzkcjbdehtj30kd0lidhy.jpg)

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzkckrnjn9j313x0u0qde.jpg)



# 3.  JVM对一个方法执行的基本策略

一般地，对于java方法的执行，在JVM在其某一特定线程的虚拟机栈(JVM Stack) 中会为方法分配一个 局部变量表，一个操作数栈，用以存储方法的运行过程中的中间值存储。

由于JVM的指令是基于栈的，即大部分的指令的执行，都伴随着操作数的出栈和入栈。所以在学习JVM的机器指令的时候，一定要铭记一点：

每个机器指令的执行，对操作数栈和局部变量的影响，充分地了解了这个机制，你就可以非常顺畅地读懂class文件中的二进制机器指令了。

如下是栈帧信息的简化图，在分析JVM指令时，脑海中对栈帧有个清晰的认识：

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzkcrmy5ihj30ga0l5dh8.jpg)

# 4.  机器指令的格式

所谓的机器指令，就是只有机器才能够认识的二进制代码。一个机器指令分为两部分组成：

![](https://ws1.sinaimg.cn/large/006tNc79gy1fzkcwbdfs1j30eb0gb75f.jpg)

# 5.  机器指令的执行模式---基于操作数栈的模式

对于传统的物理机而言，大部分的机器指令的设计都是寄存器的，物理机内设置若干个寄存器，用以存储机器指令运行过程中的值，寄存器的数量和支持的指令的个数决定了这个机器的处理能力。

但是Java虚拟机的设计的机制并不是这样的，Java虚拟机使用操作数栈 来存储机器指令的运算过程中的值。所有的操作数的操作，都要遵循出栈和入栈的规则，所以在《Java虚拟机规范》中，你会发现有很多机器指令都是关于出栈入栈的操作。

























