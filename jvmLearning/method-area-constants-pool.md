<!--ts-->
   * [1. 方法区&amp;常量池综述](#1-方法区常量池综述)
      * [1.1、相关特征](#11相关特征)
         * [1.1.1 方法区特征](#111-方法区特征)
         * [1.1.2 运行时常量池的特征](#112-运行时常量池的特征)
      * [1.2、HotSpot 方法区变迁](#12hotspot-方法区变迁)
         * [1.2.1 JDK1.2 ~ JDK6](#121-jdk12--jdk6)
         * [1.2.2 JDK7](#122-jdk7)
         * [1.2.3 JDK8](#123-jdk8)
      * [1.3 永久代变迁产生的影响](#13-永久代变迁产生的影响)
   * [2. 常量池](#2-常量池)
      * [2.1 <a href="https://www.hollischuang.com/archives/2855" rel="nofollow">Class常量池</a>](#21-class常量池)
         * [2.1.1  什么是Class文件](#211--什么是class文件)
         * [2.1.2 Class常量池](#212-class常量池)
         * [2.1.3 常量池中有什么](#213-常量池中有什么)
            * [字面量](#字面量)
            * [符号引用](#符号引用)
         * [2.1.4 Class常量池有什么用](#214-class常量池有什么用)
         * [2.1.6 <strong>常量池的里面是怎么组织的？</strong>](#216-常量池的里面是怎么组织的)
         * [2.1.7 <strong>常量池项 (cp_info) 的结构是什么</strong>](#217-常量池项-cp_info-的结构是什么)
         * [2.1.8 <strong>常量池能够表示那些信息</strong>](#218-常量池能够表示那些信息)
         * [2.1.9 <strong>int和float数据类型的常量</strong>在常量池中是怎样表示和存储的](#219-int和float数据类型的常量在常量池中是怎样表示和存储的)
         * [2.1.10 <strong>long和 double数据类型的常量</strong>在常量池中是怎样表示和存储的？](#2110-long和-double数据类型的常量在常量池中是怎样表示和存储的)
         * [2.1.11  <strong>String类型的字符串常量</strong>在常量池中是怎样表示和存储的？](#2111--string类型的字符串常量在常量池中是怎样表示和存储的)
         * [2.1.12 <strong>类文件中定义的类名和类中使用到的类</strong>在常量池中是怎样被组织和存储的？](#2112-类文件中定义的类名和类中使用到的类在常量池中是怎样被组织和存储的)
         * [2.1.13 <strong>类中引用到的field字段在常量池中是怎样描述的？</strong>](#2113-类中引用到的field字段在常量池中是怎样描述的)
         * [2.1.14 <strong>类中引用到的method方法在常量池中是怎样描述的？</strong>](#2114-类中引用到的method方法在常量池中是怎样描述的)
         * [2.1.15 类中引用到某个接口中定义的method方法在常量池中是怎样描述的？](#2115-类中引用到某个接口中定义的method方法在常量池中是怎样描述的)
      * [2.2 运行时常量池](#22-运行时常量池)
      * [2.3 字符串常量池](#23-字符串常量池)

<!-- Added by: anapodoton, at: Wed Feb 19 18:35:54 CST 2020 -->

<!--te-->

# 1. 方法区&常量池综述

java为什么叫方法区：「码农翻身认为」所谓的面向对象，到了底层，都“退化”成了面向过程，就是一个个的方法，所以我猜这是方法区的由来。

我的理解是：主要保存的是方法。保存类的信息和编译器可以确定的值（指类的，方法的局部变量不用）。

## 1.1、相关特征

### 1.1.1 方法区特征

- 同 Java 堆一样，方法区也是全局共享的一块内存区域
- 方法区的作用是存储 Java 类的结构信息，当我们创建对象实例后，**对象的类型信息存储在方法之中，实例数据存放在堆中；实例数据指的是在 Java 中创建的各种实例对象以及它们的值，类型信息指的是定义在 Java 代码中的常量、静态变量、以及在类中声明的各种方法、方法字段等等；同时可能包括即时编译器编译后产生的代码数据。**

- JVMS 不要求该区域实现自动的内存管理，但是商用 JVM 一般都已实现该区域的自动内存管理。
- 方法区分配内存可以不连续，可以动态扩展。
- 该区域并非像 JMM 规范描述的那样数据一旦放进去就属于 “永久代”；**在该区域进行内存回收的主要目的是对常量池的回收和对内存数据的卸载；一般来说这个区域的内存回收效率比起 Java 堆要低得多。**
- 当方法区无法满足内存需求时，将抛出 OutOfMemoryError 异常。

### 1.1.2 运行时常量池的特征

- **运行时常量池是方法区的一部分，**所以也是全局共享的。
- **其作用是存储 Java 类文件常量池中的符号信息。**
- **class 文件中存在常量池(非运行时常量池)，其在编译阶段就已经确定；JVM 规范对 class 文件结构有着严格的规范，必须符合此规范的 class 文件才会被 JVM 认可和装载。**
- **运行时常量池** 中保存着一些 class 文件中描述的符号引用，同时还会将这些符号引用所翻译出来的直接引用存储在 **运行时常量池** 中。
- **运行时常量池相对于 class 常量池一大特征就是其具有动态性，Java 规范并不要求常量只能在运行时才产生，也就是说运行时常量池中的内容并不全部来自 class 常量池，class 常量池并非运行时常量池的唯一数据输入口；在运行时可以通过代码生成常量并将其放入运行时常量池中。**
- 同方法区一样，当运行时常量池无法申请到新的内存时，将抛出 OutOfMemoryError 异常。

## 1.2、HotSpot 方法区变迁

### 1.2.1 JDK1.2 ~ JDK6

在 JDK1.2 ~ JDK6 的实现中，HotSpot 使用永久代实现方法区；HotSpot 使用 GC 分代实现方法区带来了很大便利；

### 1.2.2 JDK7

由于 GC 分代技术的影响，使之许多优秀的内存调试工具无法在 Oracle HotSpot之上运行，必须单独处理；并且 Oracle 同时收购了 BEA 和 Sun 公司，同时拥有 JRockit 和 HotSpot，在将 JRockit 许多优秀特性移植到 HotSpot 时由于 GC 分代技术遇到了种种困难，**所以从 JDK7 开始 Oracle HotSpot 开始移除永久代。**

**JDK7中符号表被移动到 Native Heap中，字符串常量和类引用被移动到 Java Heap中。**

### 1.2.3 JDK8

**在 JDK8 中，永久代已完全被元空间(Meatspace)所取代。**

## 1.3 永久代变迁产生的影响

1.3.1 测试代码1

```java
public class Test1 {
    public static void main(String[] args) {

		 String s1 = new StringBuilder("漠").append("然").toString();
		 System.out.println(s1.intern() == s1);

		 String s2 = new StringBuilder("漠").append("然").toString();
		 System.out.println(s2.intern() == s2);

	}

}
```

以上代码，在 JDK6 下执行结果为 false、false，在 JDK7 以上执行结果为 true、false。

**首先明确两点：** 1、在 Java 中直接使用双引号展示的字符串将会在常量池中直接创建。 2、String 的 intern 方法首先将尝试在常量池中查找该对象，如果找到则直接返回该对象在常量池中的地址；找不到则将该对象放入常量池后再返回其地址。**JDK6 常量池在方法区，频繁调用该方法可能造成 OutOfMemoryError。**

**产生两种结果的原因：**

在 JDK6 下 s1、s2 指向的是新创建的对象，**该对象将在 Java Heap 中创建，所以 s1、s2 指向的是 Java Heap 中的内存地址；**调用 intern 方法后将尝试在常量池中查找该对象，没找到后将其放入常量池并返回，**所以此时 s1/s2.intern() 指向的是常量池中的地址，JDK6常量池在方法区，与堆隔离，；所以 s1.intern()==s1 返回false。**

1.3.2 测试代码2

```java
public class Test2 {
	public static void main(String[] args) {
		/**
		 * 首先设置 持久代最大和最小内存占用(限定为10M)
		 * VM args: -XX:PermSize=10M -XX:MaxPremSize=10M
		 */

		List<String> list  = new ArrayList<String>();

		// 无限循环 使用 list 对其引用保证 不被GC  intern 方法保证其加入到常量池中
		int i = 0;
		while (true) {
		    // 此处永久执行，最多就是将整个 int 范围转化成字符串并放入常量池
			list.add(String.valueOf(i++).intern());
		}
	}
}
```

以上代码在 JDK6 下会出现 Perm 内存溢出，JDK7 or high 则没问题。

**原因分析：**

**JDK6 常量池存在方法区，设置了持久代大小后，不断while循环必将撑满 Perm 导致内存溢出；JDK7 常量池被移动到 Native Heap(Java Heap)，所以即使设置了持久代大小，也不会对常量池产生影响；不断while循环在当前的代码中，所有int的字符串相加还不至于撑满 Heap 区，所以不会出现异常。**

# 2. 常量池

主要区分下在jvm中的各种常量池。

**为什么需要常量池**

jvm 在栈帧(frame) 中进行操作数和方法的动态链接(link)，为了便于链接，jvm 使用常量池来保存跟踪当前类中引用的其他类及其成员变量和成员方法。

每个栈帧(frame)都包含一个运行常量池的引用，这个引用指向当前栈帧需要执行的方法，jvm使用这个引用来进行动态链接。

在 c/c++ 中，编译器将多个编译期编译的文件链接成一个可执行文件或者dll文件，在链接阶段，符号引用被解析为实际地址。java 中这种链接是在程序运行时动态进行的。

在Java中，常量池的概念想必很多人都听说过。这也是面试中比较常考的题目之一。在Java有关的面试题中，一般习惯通过String的有关问题来考察面试者对于常量池的知识的理解，几道简单的String面试题难倒了无数的开发者。所以说，常量池是Java体系中一个非常重要的概念。

谈到常量池，在Java体系中，共用三种常量池。分别是**字符串常量池**、**Class常量池**和**运行时常量池**。

## 2.1 Class常量池

### 2.1.1  什么是Class文件

在[Java代码的编译与反编译那些事儿](http://www.hollischuang.com/archives/58)中我们介绍过Java的编译和反编译的概念。我们知道，计算机只认识0和1，所以程序员写的代码都需要经过编译成0和1构成的二进制格式才能够让计算机运行。

我们在《[深入分析Java的编译原理](https://www.hollischuang.com/archives/2322)》中提到过，为了让Java语言具有良好的跨平台能力，Java独具匠心的提供了一种可以在所有平台上都能使用的一种中间代码——字节码（ByteCode）。

有了字节码，无论是哪种平台（如Windows、Linux等），只要安装了虚拟机，都可以直接运行字节码。

同样，有了字节码，也解除了Java虚拟机和Java语言之间的耦合。这话可能很多人不理解，Java虚拟机不就是运行Java语言的么？这种解耦指的是什么？

其实，目前Java虚拟机已经可以支持很多除Java语言以外的语言了，如Groovy、JRuby、Jython、Scala等。之所以可以支持，就是因为这些语言也可以被编译成字节码。而虚拟机并不关心字节码是有哪种语言编译而来的。

Java语言中负责编译出字节码的编译器是一个命令是`javac`。

> javac是收录于JDK中的Java语言编译器。该工具可以将后缀名为.java的源文件编译为后缀名为.class的可以运行于Java虚拟机的字节码。

如，我们有以下简单的`HelloWorld.java`代码：

```java
public class HelloWorld {
    public static void main(String[] args) {
        String s = "Hollis";
    }
}
```

通过javac命令生成class文件：

```
javac HelloWorld.java
```

生成`HelloWorld.class`文件:

![img](img/15401179593014-20200219171657508.jpg)￼

> 如何使用16进制打开class文件：使用 `vim test.class` ，然后在交互模式下，输入`:%!xxd`即可。

可以看到，上面的文件就是Class文件，Class文件中包含了Java虚拟机指令集和符号表以及若干其他辅助信息。

要想能够读懂上面的字节码，需要了解Class类文件的结构，由于这不是本文的重点，这里就不展开说明了。

> 读者可以看到，`HelloWorld.class`文件中的前八个字母是`cafe babe`，这就是Class文件的魔数（[Java中的”魔数”](https://www.hollischuang.com/archives/491)）

我们需要知道的是，在Class文件的4个字节的魔数后面的分别是4个字节的Class文件的版本号（第5、6个字节是次版本号，第7、8个字节是主版本号，我生成的Class文件的版本号是52，这时Java 8对应的版本。也就是说，这个版本的字节码，在JDK 1.8以下的版本中无法运行）在版本号后面的，就是Class常量池入口了。

### 2.1.2 Class常量池

Class常量池可以理解为是Class文件中的资源仓库。 Class文件中除了包含类的版本、字段、方法、接口等描述信息外，还有一项信息就是常量池(constant pool table)，用于存放编译器生成的各种字面量(Literal)和符号引用(Symbolic References)。

由于不同的Class文件中包含的常量的个数是不固定的，所以在Class文件的常量池入口处会设置两个字节的常量池容量计数器，记录了常量池中常量的个数。

![-w697](img/15401192359009.jpg)￼

当然，还有一种比较简单的查看Class文件中常量池的方法，那就是通过`javap`命令。对于以上的`HelloWorld.class`，可以通过

```
javap -v  HelloWorld.class
```

查看常量池内容如下:

![img](img/15401195127619.jpg)￼

> 从上图中可以看到，反编译后的class文件常量池中共有16个常量。而Class文件中常量计数器的数值是0011，将该16进制数字转换成10进制的结果是17。
>
> 原因是与Java的语言习惯不同，常量池计数器是从1开始而不是从0开始的，常量池的个数是10进制的17，这就代表了其中有16个常量，索引值范围为1-16。

### 2.1.3 常量池中有什么

介绍完了什么是Class常量池以及如何查看常量池，那么接下来我们就要深入分析一下，Class常量池中都有哪些内容。

常量池中主要存放两大类常量：字面量（literal）和符号引用（symbolic references）。

#### 字面量

前面说过，运行时常量池中主要保存的是字面量和符号引用，那么到底什么字面量？

> 在计算机科学中，字面量（literal）是用于表达源代码中一个固定值的表示法（notation）。几乎所有计算机编程语言都具有对基本值的字面量表示，诸如：整数、浮点数以及字符串；而有很多也对布尔类型和字符类型的值也支持字面量表示；还有一些甚至对枚举类型的元素以及像数组、记录和对象等复合类型的值也支持字面量表示法。

以上是关于计算机科学中关于字面量的解释，并不是很容易理解。说简单点，**字面量就是指由字母、数字等构成的字符串或者数值（基本类型的数据的值）。**

字面量只可以右值出现，所谓右值是指等号右边的值，如：int a=123这里的a为左值，123为右值。在这个例子中123就是字面量。

```java
int a = 123;
String s = "hollis";
```

上面的代码示例中，123和hollis都是字面量，a和s是符号引用。**符号引用主要是用来重定位的**。

#### 符号引用

常量池中，除了字面量以外，还有符号引用，那么到底什么是符号引用呢。

符号引用是编译原理中的概念，是相对于直接引用来说的。主要包括了以下三类常量： 

**类和接口的全限定名  **

**字段的名称和描述符 **

**方法的名称和描述符**

这也就可以印证前面的常量池中还包含一些`com/hollis/HelloWorld`、`main`、`([Ljava/lang/String;)V`等常量的原因了。**符号引用主要是用来重定位的**。

### 2.1.4 Class常量池有什么用

前面介绍了这么多，关于Class常量池是什么，怎么查看Class常量池以及Class常量池中保存了哪些东西。有一个关键的问题没有讲，那就是Class常量池到底有什么用。

首先，可以明确的是，Class常量池是Class文件中的资源仓库，其中保存了各种常量。而这些常量都是开发者定义出来，需要在程序的运行期使用的。

在《深入理解Java虚拟》中有这样的表述：

Java代码在进行`Javac`编译的时候，并不像C和C++那样有“连接”这一步骤，而是在虚拟机加载Class文件的时候进行动态连接。也就是说，在Class文件中不会保存各个方法、字段的最终内存布局信息，因此这些字段、方法的符号引用不经过运行期转换的话无法得到真正的内存入口地址，也就无法直接被虚拟机使用。当虚拟机运行时，需要从常量池获得对应的符号引用，再在类创建时或运行时解析、翻译到具体的内存地址之中。关于类的创建和动态连接的内容，在虚拟机类加载过程时再进行详细讲解。

前面这段话，看起来很绕，不是很容易理解。其实他的意思就是： **Class是用来保存常量的一个媒介场所，并且是一个中间场所。在JVM真的运行时，需要把常量池中的常量加载到内存中。**

另外，关于常量池中常量的存储形式，以及数据类型的表示方法本文中并未涉及，并不是说这部分知识点不重要，只是Class字节码的分析本就枯燥，作者不想在一篇文章中给读者灌输太多的理论上的内容。感兴趣的读者可以自行Google学习，如果真的有必要，我也可以单独写一篇文章再深入介绍。

### 2.1.6 **常量池的里面是怎么组织的？**

常量池的组织很简单，前端的两个字节占有的位置叫做常量池计数器(constant_pool_count)，它记录着常量池的组成元素  常量池项(cp_info) 的个数。紧接着会排列着constant_pool_count-1个常量池项(cp_info)。如下图所示：

![img](img/20141010174452984.png)

---------------------

### 2.1.7 **常量池项 (cp_info) 的结构是什么**

   每个**常量池项(cp_info)** 都会对应记录着class文件中的某中类型的字面量。让我们先来了解一下**常量池项(cp_info)**的结构吧：

![img](img/20141010142137086.png)

​     JVM虚拟机规定了不同的tag值和不同类型的字面量对应关系如下：

![img](img/20141010142338127.png)

​           所以根据cp_info中的tag 不同的值，可以将cp_info 更细化为以下结构体：

![img](img/20141011105500122.png)

​     现在让我们看一下细化了的常量池的结构会是类似下图所示的样子：

![img](img/20141011125642078.png)

### 2.1.8 **常量池能够表示那些信息**

![img](img/20141011111433728.png)

### 2.1.9 **int和float数据类型的常量**在常量池中是怎样表示和存储的

(CONSTANT_Integer_info, CONSTANT_Float_info)

Java语言规范规定了 int类型和Float 类型的数据类型占用 4 个字节的空间。那么存在于class字节码文件中的该类型的常量是如何存储的呢？相应地，在常量池中，将 int和Float类型的常量分别使用CONSTANT_Integer_info和 Constant_float_info表示，他们的结构如下所示：

![img](img/20141011132721395.png)

举例：建下面的类 IntAndFloatTest.java，在这个类中，我们声明了五个变量，但是取值就两种int类型的**10** 和Float类型的**11f**。	

```java
public class IntAndFloatTest {
private final int a = 10;
private final int b = 10;
private float c = 11f;
private float d = 11f;
private float e = 11f;
}
```

然后用编译器编译成IntAndFloatTest.class字节码文件，我们通过**javap -v IntAndFloatTest** 指令来看一下其常量池中的信息，可以看到虽然我们在代码中写了两次**10** 和三次**11f**，但是常量池中，就只有一个常量**10** 和一个常量**11f**,如下图所示:

![img](img/20141011140144109.png)

从结果上可以看到常量池第#8 个常量池项(cp_info) 就是CONSTANT_Integer_info,值为10；第#23个常量池项(cp_info) 就是CONSTANT_Float_info,值为11f。(常量池中其他的东西先别纠结啦，我们会面会一一讲解的哦)。

 代码中所有用到 int 类型 10 的地方，会使用指向常量池的指针值#8 定位到第#8 个常量池项(cp_info)，即值为 10的结构体 CONSTANT_Integer_info，而用到float类型的11f时，也会指向常量池的指针值#23来定位到第#23个常量池项(cp_info) 即值为11f的结构体CONSTANT_Float_info。如下图所示：

![img](img/20141011134943437.png)

### 2.1.10 **long和 double数据类型的常量**在常量池中是怎样表示和存储的？

(CONSTANT_Long_info、CONSTANT_Double_info )

Java语言规范规定了 long 类型和 double类型的数据类型占用8 个字节的空间。那么存在于class 字节码文件中的该类型的常量是如何存储的呢？相应地，在常量池中，将long和double类型的常量分别使用CONSTANT_Long_info和Constant_Double_info表示，他们的结构如下所示：

![img](img/20141011142454265.png)

​     举例：建下面的类 LongAndDoubleTest.java，在这个类中，我们声明了六个变量，但是取值就两种**Long** 类型的**-6076574518398440533L** 和**Double** 类型的**10.1234567890D**。

```java
package com.louis.jvm;
public class LongAndDoubleTest {
private long a = -6076574518398440533L;
private long b = -6076574518398440533L;
private long c = -6076574518398440533L;
private double d = 10.1234567890D;
private double e = 10.1234567890D;
private double f = 10.1234567890D;
}
```

![img](img/20141011150905498.png)

从结果上可以看到常量池第 **#18** 个**常量池项(cp_info)** 就是**CONSTANT_Long_info**,值为**-6076574518398440533L** ；第 **#26**个**常量池项(cp_info)** 就是**CONSTANT_Double_info**,值为**10.1234567890D**。

代码中所有用到 long 类型-6076574518398440533L 的地方，会使用指向常量池的指针值#18 定位到第 #18 个常量池项(cp_info)，即值为-6076574518398440533L 的结构体CONSTANT_Long_info，而用到double类型的10.1234567890D时，也会指向常量池的指针值#26 来定位到第 #26 个常量池项(cp_info) 即值为10.1234567890D的结构体CONSTANT_Double_info。如下图所示：

![img](img/20141011150130390.png)

### 2.1.11  **String类型的字符串常量**在常量池中是怎样表示和存储的？

（CONSTANT_String_info、CONSTANT_Utf8_info）

对于字符串而言，JVM会将字符串类型的字面量以UTF-8 编码格式存储到在class字节码文件中。这么说可能有点摸不着北，我们先从直观的Java源码中中出现的用双引号"" 括起来的字符串来看，在编译器编译的时候，都会将这些字符串转换成CONSTANT_String_info结构体，然后放置于常量池中。其结构如下所示：

![img](img/20141011160934657.png)

​     如上图所示的结构体，**CONSTANT_String_info**结构体中的string_index的值指向了CONSTANT_Utf8_info结构体，而字符串的utf-8编码数据就在这个结构体之中。如下图所示：

![img](img/20141011164935736.png)

请看一例，定义一个简单的StringTest.java类，然后在这个类里加一个"JVM原理" 字符串，然后，我们来看看它在class文件中是怎样组织的。

```java
public class StringTest {
	private String s1 = "JVM原理";
	private String s2 = "JVM原理";
	private String s3 = "JVM原理";
	private String s4 = "JVM原理";
}
```

![img](img/20141011175415291.png)

在面的图中，我们可以看到CONSTANT_String_info结构体位于常量池的第#15个索引位置。而存放"Java虚拟机原理" 字符串的 UTF-8编码格式的字节数组被放到CONSTANT_Utf8_info结构体中，该结构体位于常量池的第#16个索引位置。上面的图只是看了个轮廓，让我们再深入地看一下它们的组织吧。请看下图：

![img](img/20141011173951923.png)

### 2.1.12 **类文件中定义的类名和类中使用到的类**在常量池中是怎样被组织和存储的？

(CONSTANT_Class_info)

 JVM会将某个Java 类中所有使用到了的**类的完全限定名** 以**二进制形式的完全限定名** 封成**CONSTANT_Class_info**结构体中，然后将其放置到常量池里。**CONSTANT_Class_info** 的tag值为 **7 。**其结构如下：

![img](img/20141013102407578.png)

**类的完全限定名和二进制形式的完全限定名**

 在某个Java源码中，我们会使用很多个类，比如我们定义了一个 ClassTest的类，并把它放到com.louis.jvm 包下，则 ClassTest类的完全限定名为com.louis.jvm.ClassTest，将JVM编译器将类编译成class文件后，此完全限定名在class文件中，是以二进制形式的完全限定名存储的，即它会把完全限定符的"."换成"/" ，即在class文件中存储的 ClassTest类的完全限定名称是"com/louis/jvm/ClassTest"。因为这种形式的完全限定名是放在了class二进制形式的字节码文件中，所以就称之为 二进制形式的完全限定名。

举例，我们定义一个很简单的**ClassTest**类，来看一下常量池是怎么对类的完全限定名进行存储的。

```java
package com.jvm;
import  java.util.Date;
public class ClassTest {
	private Date date =new Date();
}
```

将Java源码编译成**ClassTest.class**文件后，在此文件的目录下执行 javap -v ClassTest 命令，会看到如下的常量池信息的轮廓：

![img](img/20141013125853447.png)如上图所示，在ClassTest.class文件的常量池中，共有 3 个CONSTANT_Class_info结构体，分别表示ClassTest 中用到的Class信息。 我们就看其中一个表示com/jvm.ClassTest的CONSTANT_Class_info 结构体。它在常量池中的位置是#1，它的name_index值为#2，它指向了常量池的第2 个常量池项，如下所示:

![img](img/20141013113134911.png)

注意，只有真正使用的，才会在常量池中：	

```java
import java.util.Date;
public  class Other{
	private Date date;
public Other()
{
	Date da;
}
}
```

  上述的Other的类，在JDK将其编译成class文件时，常量池中并没有java.util.Date对应的CONSTANT_Class_info常量池项，为什么呢?

 在Other类中虽然定义了Date类型的两个变量date、da，但是JDK编译的时候，认为你只是声明了“Ljava/util/Date”类型的变量，并没有实际使用到Ljava/util/Date类。**将类信息放置到常量池中的目的，是为了在后续的代码中有可能会反复用到它。**很显然，JDK在编译Other类的时候，会解析到Date类有没有用到，发现该类在代码中就没有用到过，所以就认为没有必要将它的信息放置到常量池中了。

如果改为new Date(),则：

![img](img/20141127102330502.png)

**总结**：

1.对于某个类或接口而言，其自身、父类和继承或实现的接口的信息会被直接组装成CONSTANT_Class_info常量池项放置到常量池中；  

2.类中或接口中使用到了其他的类，只有在类中实际使用到了该类时，该类的信息才会在常量池中有对应的CONSTANT_Class_info常量池项；

3.类中或接口中仅仅定义某种类型的变量，JDK只会将变量的类型描述信息以UTF-8字符串组成CONSTANT_Utf8_info常量池项放置到常量池中，上面在类中的private Date date;JDK编译器只会将表示date的数据类型的“Ljava/util/Date”字符串放置到常量池中。

### 2.1.13 **类中引用到的field字段在常量池中是怎样描述的？**

(CONSTANT_Fieldref_info, CONSTANT_Name_Type_info)

 一般而言，我们在定义类的过程中会定义一些 field 字段，然后会在这个类的其他地方（如方法中）使用到它。有可能我们在类的方法中只使用field字段一次，也有可能我们会在类定义的方法中使用它很多很多次。

 举一个简单的例子，我们定一个叫Person的简单java bean，它有name和age两个field字段，如下所示：

```java
public class Person {
private String name;
private int age;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}
}
```

在上面定义的类中，我们在Person类中的一系列方法里，多次引用到namefield字段 和agefield字段，对于JVM编译器而言，name和age只是一个符号而已，并且它在由于它可能会在此类中重复出现多次，所以JVM把它当作常量来看待，将name和age以field字段常量的形式保存到常量池中。

将它name和age封装成 CONSTANT_Fieldref_info 常量池项，放到常量池中，在类中引用到它的地方，直接放置一个指向field字段所在常量池的索引。

上面的Person类，使用javap -v Person指令，查看class文件的信息，你会看到，在Person类中引用到age和namefield字段的地方，都是指向了常量池中age和namefield字段对应的常量池项中。表示field字段的常量池项叫做CONSTANT_Fieldref_info。

![img](img/20141020172136250.png)

怎样描述某一个field字段的引用?

![img](img/20141021093513953.png)

![img](img/20141021093957765.png)

![img](img/20141021094937961.png)

![img](img/20141021100345281.png)

  实例解析： 现在，让我们来看一下**Person类中定义的namefield字段在常量池中的表示。通过使用**javap -v Person会查看到如下的常量池信息：

![img](img/20141021102039512.png)

![img](img/20141021141312817.png)

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzaqg13h4fj30oo0qv0uy.jpg)

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzaqi88zvsj30kh0cht9o.jpg)

   请读者看上图中namefield字段的数据类型，它在#6个常量池项，以UTF-8编码格式的字符串“Ljava/lang/String;” 表示，这表示着这个field 字段是java.lang.String 类型的。关于field字段的数据类型，class文件中存储的方式和我们在源码中声明的有些不一样。请看下图的对应关系：

![img](img/20141022140710727.png)

**只有在类中的其他地方引用到了，才会将他放到常量池中**。如果我们在类中定义了field 字段，但是没有在类中的其他地方用到这些字段，它是不会被编译器放到常量池中的。

### 2.1.14 **类中引用到的method方法在常量池中是怎样描述的？**

(CONSTANT_Methodref_info, CONSTANT_Name_Type_info)

```java
public class Person {
private String name;
private int age;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}
public int getAge() {
	return age;
}

public void setAge(int age) {
	this.age = age;
}
    //只有被别的函数调用的才会被加入常量池
	public String getInfo()
	{
	return getName()+"\t"+getAge();
}

}
```

![img](img/20141022155455316.png)

   这里的方法调用的方式牵涉到Java非常重要的一个术语和机制，叫动态绑定。

**怎样表示一个方法引用？**

![img](img/20141110150930996.png)

![img](img/20141111152543921.png)

![img](img/20141111130431203.png)

**方法描述符的组成**

![img](img/20141111125637625.png)

![img](img/20141111125836812.png)



![img](img/20141111132041264.png)

### 2.1.15 类中引用到某个接口中定义的method方法在常量池中是怎样描述的？

(CONSTANT_InterfaceMethodref_info, CONSTANT_Name_Type_info)

当我们在某个类中使用到了某个接口中的方法，JVM会将用到的接口中的方法信息方知道这个类的常量池中。

比如我们定义了一个**Worker**接口，和一个**Boss**类，在**Boss**类中调用了**Worker**接口中的方法，这时候在**Boss**类的常量池中会有**Worker**接口的方法的引用表示。

```java
 /**
 * Worker 接口类
 * @author luan louis
 */
public interface Worker{
	
	public void work();
 
}
```



```java
/**

- Boss 类，makeMoney()方法 调用Worker 接口的work

- @author louluan
  */
  public class Boss {

  public void makeMoney(Worker worker)
  {
  	worker.work();
  }

}
```

![img](img/20141111141834454.png)

如上图所示，在Boss类的makeMoney()方法中调用了Worker接口的work()方法，机器指令是通过invokeinterface指令完成的，invokeinterface指令后面的操作数，是指向了Boss常量池中Worker接口的work()方法描述，表示的意思就是：“我要调用Worker接口的work()方法”。

Worker接口的work()方法引用信息，JVM会使用CONSTANT_InterfaceMethodref_info结构体来描述，CONSTANT_InterfaceMethodref_info定义如下：

![img](img/20141111142916832.png)

  CONSTANT_InterfaceMethodref_info结构体和上面介绍的CONSTANT_Methodref_info 结构体很基本上相同，它们的不同点只有：

   1.CONSTANT_InterfaceMethodref_info 的tag 值为11，而CONSTANT_Methodref_info的tag值为10；

   2. CONSTANT_InterfaceMethodref_info 描述的是接口中定义的方法，而CONSTANT_Methodref_info描述的是实例类中的方法；  

小试牛刀
关于方法的描述,完全相同CONSTANT_InterfaceMethodref_info和上述的CONSTANT_Methodref_info 结构体完全一致，这里就不单独为CONSTANT_InterfaceMethodref_info绘制结构图了，请读者依照CONSTANT_Methodref_info的描述，结合本例子关于Worker接口和Boss类的关系，使用javap -v Boss,查看常量池信息，然后根据常量池信息，自己动手绘制work() 方法在常量池中的结构。

## 2.2 运行时常量池

运行时常量池是方法区的一部分，是一块内存区域。Class 文件常量池将在类加载后进入方法区的运行时常量池中存放。

**一个类加载到 JVM 中后对应一个运行时常量池**，运行时常量池相对于 Class 文件常量池来说具备动态性，Class 文件常量只是一个静态存储结构，里面的引用都是符号引用。而运行时常量池可以在运行期间将符号引用解析为直接引用。

可以说运行时常量池就是用来索引和查找字段和方法名称和描述符的。给定任意一个方法或字段的索引，通过这个索引最终可得到该方法或字段所属的类型信息和名称及描述符信息，这涉及到方法的调用和字段获取。

## 2.3 字符串常量池

- 在 jdk1.6（含）之前也是方法区的一部分，并且其中存放的是字符串的实例
- 在 jdk1.7（含）之后，是在堆内存之中，存储的是字符串对象的引用，字符串实例是在堆中
- jdk1.8 已移除永久代，字符串常量池是在本地内存当中，存储的也只是引用

**字符串常量池是全局**的，JVM 中独此一份，因此也称为全局字符串常量池。

运行时常量池中的字符串字面量若是成员的，则在类的加载初始化阶段就使用到了字符串常量池；若是本地的，则在使用到的时候（执行此代码时）才会使用到字符串常量池。

其实，“使用常量池”对应的字节码是一个 `ldc` 指令，在给 String 类型的引用赋值的时候会先执行这个指令，看常量池中是否存在这个字符串对象的引用，若有就直接返回这个引用，若没有，就在堆里创建这个字符串对象并在字符串常量池中记录下这个引用（jdk1.7)。String 类的 `intern()` 方法还可在运行期间把字符串放到字符串常量池中。

JVM 中除了字符串常量池，8种基本数据类型中除了两种浮点类型剩余的6种基本数据类型的包装类，都使用了缓冲池技术，但是 Byte、Short、Integer、Long、Character 这5种整型的包装类也只是在对应值在 [-128,127] 时才会使用缓冲池，超出此范围仍然会去创建新的对象。
