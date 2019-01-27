[TOC]

# 前言

# 1. Class文件的基本结构

废话不说，先看图。

![](https://ws1.sinaimg.cn/large/006tNc79ly1fz9s3ykx5hj30nl0rlado.jpg)

## 1.1 魔数(magic)

所有的由Java编译器编译而成的class文件的前4个字节都是“0xCAFEBABE”  

它的作用在于：当JVM在尝试加载某个文件到内存中来的时候，会首先判断此class文件有没有JVM认为可以接受的“签名”，即JVM会首先读取文件的前4个字节，判断该4个字节是否是“0xCAFEBABE”，如果是，则JVM会认为可以将此文件当作class文件来加载并使用。

## 1.2 版本号

(minor_version,major_version)

随着Java本身的发展，Java语言特性和JVM虚拟机也会有相应的更新和增强。目前我们能够用到的JDK版本如：1.5，1.6，1.7，还有现如今最新的1.8。发布新版本的目的在于：在原有的版本上增加新特性和相应的JVM虚拟机的优化。而随着主版本发布的次版本，则是修改相应主版本上出现的bug。我们平时只需要关注主版本就可以了。

主版本号和次版本号在class文件中各占两个字节，副版本号占用第5、6两个字节，而主版本号则占用第7，8两个字节。JDK1.0的主版本号为45，以后的每个新主版本都会在原先版本的基础上加1。若现在使用的是JDK1.7编译出来的class文件，则相应的主版本号应该是51,对应的7，8个字节的十六进制的值应该是 0x33。

  一个 JVM实例只能支持特定范围内的主版本号 （Mi 至Mj） 和 0 至特定范围内 （0 至 m） 的副版本号。假设一个 Class 文件的格式版本号为 V， 仅当Mi.0 ≤ v ≤ Mj.m成立时，这个 Class 文件才可以被此 Java 虚拟机支持。不同版本的 Java 虚拟机实现支持的版本号也不同，高版本号的 Java 虚拟机实现可以支持低版本号的 Class 文件，反之则不成立。

 JVM在加载class文件的时候，会读取出主版本号，然后比较这个class文件的主版本号和JVM本身的版本号，如果JVM本身的版本号 < class文件的版本号，JVM会认为加载不了这个class文件，会抛出我们经常见到的"java.lang.UnsupportedClassVersionError: Bad version number in .class file " Error 错误；反之，JVM会认为可以加载此class文件，继续加载此class文件。

1. 有时候我们在运行程序时会抛出这个Error 错误："java.lang.UnsupportedClassVersionError: Bad version number in .class file"。上面已经揭示了出现这个问题的原因，就是在于当前尝试加载class文件的JVM虚拟机的版本 低于class文件的版本。解决方法：1.重新使用当前jvm编译源代码，然后再运行代码；2.将当前JVM虚拟机更新到class文件的版本。

2. 怎样查看class文件的版本号？

 可以借助于文本编辑工具，直接查看该文件的7，8个字节的值，确定class文件是什么版本的。

当然快捷的方式使用JDK自带的javap工具，如当前有Programmer.class 文件，进入此文件所在的目录，然后执行 ”javap -v Programmer“,结果会类似如下所示：

![](https://ws4.sinaimg.cn/large/006tNc79ly1fz9s8kg22pj30j20b10v3.jpg)



## 1.3 常量池计数器

(constant_pool_count)

常量池是class文件中非常重要的结构，它描述着整个class文件的字面量信息。 常量池是由一组constant_pool结构体数组组成的，而数组的大小则由常量池计数器指定。常量池计数器constant_pool_count 的值 =constant_pool表中的成员数+ 1。constant_pool表的索引值只有在大于 0 且小于constant_pool_count时才会被认为是有效的。



## 1.4 常量池数据区

(constant_pool[contstant_pool_count-1])

常量池，constant_pool是一种表结构,它包含 Class 文件结构及其子结构中引用的所有字符串常量、 类或接口名、字段名和其它常量。 常量池中的每一项都具备相同的格式特征——第一个字节作为类型标记用于识别该项是哪种类型的常量，称为 “tag byte” 。常量池的索引范围是 1 至constant_pool_count−1。

 [好好说说Java中的常量池.md](../好好说说Java中的常量池.md) 

## 1.5 访问标志(access_flags)

 访问标志，access_flags 是一种掩码标志，用于表示某个类或者接口的访问权限及基础属性。

![](https://ws1.sinaimg.cn/large/006tNc79ly1fz9serwl0bj30jf0hhwja.jpg)

​    **访问标志（access_flags）**紧接着**常量池**后，占有两个字节，总共16位，如下图所示：

![](https://ws4.sinaimg.cn/large/006tNc79ly1fzasce0vquj30o50if75x.jpg)

当**JVM**在编译某个类或者接口的源代码时，**JVM**会解析出这个类或者接口的访问标志信息，然后，将这些标志设置到**访问标志（access_flags）**这16个位上。**JVM**会考虑如下设置如下访问表示信息：

1. 我们知道，每个定义的类或者接口都会生成**class**文件（这里也包括内部类，在某个类中定义的静态内部类也会单独生成一个class文件。

   对于定义的类，JVM在将其编译成class文件时，会将class文件的访问标志的第11位设置为1 。第11位叫做ACC_SUPER标志位；

   对于定义的接口，JVM在将其编译成class文件时，会将class文件的访问标志的第8位 设置为 1 。第8位叫做ACC_INTERFACE标志位；

2. **class**文件表示的类或者接口的访问权限有**public**类型的和包**package**类型的。      

   如果类或者接口被声明为**public**类型的，那么，**JVM**将其编译成**class**文件时，会将**class**文件的访问标志的第**16**位设置为**1** 。第**16**位叫做**ACC_PUBLIC标志符****；

3. 类是否为抽象类型的，即我们定义的类有没有被**abstract**关键字修饰，即我们定义的类是否为抽象类。

4. 该类是否被声明了**final**类型,即表示该类不能被继承。

5. 如果我们这个**class**文件不是**JVM**通过java源代码文件编译而成的，而是用户自己通过**class**文件的组织规则生成的，那么，一般会对**class**文件的**访问标志**第**4**位设置为 **1** 。通过**JVM**编译源代码产生的**class**文件此标志位为 **0**，第4位叫做**ACC_SYNTHETIC标志位**；

6. 枚举类，对于定义的枚举类如：public enum EnumTest{....}，**JVM**也会对此枚举类编译成**class**文件，这时，对于这样的**class**文件，**JVM**会对**访问标志**第**2**位设置为 **1** ，以表示它是枚举类。第**2**位叫做**ACC_ENUM标志位**；

7. 注解类，对于定义的注解类如：public @interface{.....},**JVM**会对此注解类编译成**class**文件，对于这样的**class**文件，**JVM**会将**访问标志**第**3**位设置为1，以表示这是个注解类，第**3**位叫做**ACC_ANNOTATION标志位**。

![](https://ws4.sinaimg.cn/large/006tNc79ly1fzat29eh7hj30mm0ihq5t.jpg)



## 1.6 类索引(this_class)

 类索引，this_class的值必须是对constant_pool表中项目的一个有效索引值。constant_pool表在这个索引处的项必须为CONSTANT_Class_info 类型常量，表示这个 Class 文件所定义的类或接口。

我们知道一般情况下一个Java类源文件经过JVM编译会生成一个class文件，也有可能一个Java类源文件中定义了其他类或者内部类，这样编译出来的class文件就不止一个，但每一个class文件表示某一个类，至于这个class表示哪一个类，便可以通过 类索引 这个数据项来确定。JVM通过类的完全限定名确定是某一个类。

**类索引的作用，就是为了指出class文件所描述的这个类叫什么名字。**

类索引紧接着访问标志的后面，占有两个字节，在这两个字节中存储的值是一个指向常量池的一个索引，该索引指向的是CONSTANT_Class_info常量池项，

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzautuez8qj30lz0bfmyr.jpg)



![](https://ws1.sinaimg.cn/large/006tNc79ly1fzauvsian7j30kg0din1r.jpg)

​           由上可知，它的类索引值为0x0001,那么，它指向了常量池中的第一个常量池项，那我们再看一下常量池中的信息。使用javap -v Simple,常量池中有以下信息：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzauw0adolj30ix04owf6.jpg)

可以看到常量池中的第一项是CONSTANT_Class_info项，它表示一个"com/louis/jvm/Simple"的类名。即类索引是告诉我们这个class文件所表示的是哪一个类。

![](https://ws4.sinaimg.cn/large/006tNc79ly1fzauwmg4c5j30mc0eugn0.jpg)



## 1.7 父类索引(super_class)

父类索引，对于类来说，super_class 的值必须为 0 或者是对constant_pool 表中项目的一个有效索引值。如果它的值不为 0，那 constant_pool 表在这个索引处的项必须为CONSTANT_Class_info 类型常量，表示这个 Class 文件所定义的类的直接父类。当前类的直接父类，以及它所有间接父类的access_flag 中都不能带有ACC_FINAL 标记。对于接口来说，它的Class文件的super_class项的值必须是对constant_pool表中项目的一个有效索引值。constant_pool表在这个索引处的项必须为代表 java.lang.Object 的 CONSTANT_Class_info 类型常量 。如果 Class 文件的 super_class的值为 0，那这个Class文件只可能是定义的是java.lang.Object类，只有它是唯一没有父类的类。

Java支持单继承模式，除了java.lang.Object 类除外，每一个类都会有且只有一个父类。class文件中紧接着类索引(this_class)之后的两个字节区域表示父类索引，跟类索引一样，父类索引这两个字节中的值指向了常量池中的某个常量池项CONSTANT_Class_info，表示该class表示的类是继承自哪一个类。

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzauzyc326j30ki0hbwix.jpg)



## 1.8 接口计数器(interfaces_count)

单继承，多个接口。

接口计数器，interfaces_count的值表示当前类或接口的直接父接口数量。



## 1.9 接口信息数据区

(interfaces[interfaces_count])

接口表，interfaces[]数组中的每个成员的值必须是一个对constant_pool表中项目的一个有效索引值， 它的长度为 interfaces_count。每个成员 interfaces[i]  必须为 CONSTANT_Class_info类型常量，其中 0 ≤ i <interfaces_count。在interfaces[]数组中，成员所表示的接口顺序和对应的源代码中给定的接口顺序（从左至右）一样，即interfaces[0]对应的是源代码中最左边的接口。



​      

一个类可以不实现任何接口，也可以实现很多个接口，为了表示当前类实现的接口信息，class文件使用了如下结构体描述某个类的接口实现信息:

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzaw6pmip5j30n207rab8.jpg)

   由于类实现的接口数目不确定，所以接口索引集合的描述的前部分叫做接口计数器（interfaces_count），接口计数器占用两个字节，其中的值表示着这个类实现了多少个接口，紧跟着接口计数器的部分就是接口索引部分了，每一个接口索引占有两个字节，接口计数器的值代表着后面跟着的接口索引的个数。接口索引和类索引和父类索引一样，其内的值存储的是指向了常量池中的常量池项的索引，表示着这个接口的完全限定名。

```java
/**

- Worker 接口类

- @author luan louis
  */
  public interface Worker{

  public void work();

}
 
public class Programmer implements Worker {
 
	@Override
	public void work() {
		System.out.println("I'm Programmer,Just coding....");
	}
}

```

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzaw8l34ayj30ii0sa7ch.jpg)



## 1.10 字段计数器(fields_count)

 字段计数器，fields_count的值表示当前 Class 文件 fields[]数组的成员个数。 fields[]数组中每一项都是一个field_info结构的数据项，它用于表示该**类或接口声明的类字段或者实例字段**（不包括局部变量）。

## 1.11  字段信息数据区

(fields[fields_count])

字段表，fields[]数组中的每个成员都必须是一个fields_info结构的数据项，用于表示当前类或接口中某个字段的完整描述。 fields[]数组描述当前类或接口声明的所有字段，但不包括从父类或父接口继承的部分。

**类中定义的field字段是如何在class文件中组织的**

**不同的数据类型在class文件中是如何表示的**

**static final类型的field字段的初始化赋值问题**

### 1.11.1 概述

**字段表集合**是指由若干个**字段表（field_info）**组成的集合。对于在类中定义的若干个字段，经过**JVM**编译成**class**文件后，会将相应的字段信息组织到一个叫做字段表集合的结构中，字段表集合是一个类数组结构，如下图所示：

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzgqzb07llj30f00990tu.jpg)

注意：这里所讲的字段是指在类中定义的静态或者非静态的变量，而不是在类中的方法内定义的变量。请注意区别。

比如，如果某个类中定义了5个字段，那么，**JVM**在编译此类的时候，会生成5个**字段表（field_info）**信息,然后将字段表集合中的字段计数器的值设置成5，将5个字段表信息依次放置到字段计数器的后面。

### 1.11.2 字段表集合在class文件中的位置

**字段表集合**紧跟在**class**文件的**接口索引集合**结构的后面。

### 1.11.3 Java中的一个Field字段应该包含那些信息？

**字段表field_info结构体的定义。**

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzgr613g0yj30ke06jwf7.jpg)

​    针对上述的字段表示，**JVM**虚拟机规范规定了**field_info**结构体来描述字段，其表示信息如下：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzgr8up61bj30kt0g30up.jpg)

![](https://ws4.sinaimg.cn/large/006tNc79ly1fzgrbouhlaj30iy07ygmc.jpg)

下面我将一一讲解**FIeld_info**的组成元素：**访问标志（access_flags）**、**名称索引（name_index）**、**描述索引（descriptor_index）**、**属性表集合**

### 1.11.4  field字段的访问标志

 如上图所示定义的**field_info**结构体，**field**字段的**访问标志(access_flags)**占有两个字节，它能够表述的信息如下所示：

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzgrnerwjgj30kz0gimz6.jpg)

举例：如果我们在某个类中有定义**field**域：private static String str;，那么在访问标志上，第**15**位**ACC_PRIVATE**和第**13**位**ACC_STATIC**标志位都应该为1。**field**域**str**的访问标志信息应该是如下所示：

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzgrsj971oj30fi042gly.jpg)

如上图所示，**str**字段的**访问标志**的值为**0x000A**，它由两个修饰符**ACC_PRIVATE**和**ACC_STATIC**组成。

根据给定的**访问标志（access_flags）**，我们可以通过以下运算来得到这个域有哪些修饰符：

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzgrxugq4yj30hn0a7dgw.jpg)

上面列举的str字段的访问标志的值为**000A**，那么分别域上述的标志符的特征值取&，结果为**1**的只有**ACC_PRIVATE**和**ACC_STATIC**，所以该字段的标志符只有有**ACC_PRIVATE**和**ACC_STATIC**。

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzgsiwq4y9j30c907z751.jpg)

### 1.11.5 字段的数据类型表示和字段名称表示

**class**文件对数据类型的表示如下图所示：

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzgsmzqu6bj30jn0h5go4.jpg)

**field**字段名称，我们定义了一个形如**private static String str**的**field**字段，其中"**str**"就是这个字段的名称。

**class文件将字段名称和field字段的数据类型表示作为字符串存储在常量池中。**

在field_info结构体中，紧接着访问标志的，就是字段名称索引和字段描述符索引，它们分别占有两个字节，其内部存储的是指向了常量池中的某个常量池项的索引，对应的常量池项中存储的字符串，分别表示该字段的名称和字段描述符。



### 1.11.6 属性表集合-----静态field字段的初始化

在定义**field**字段的过程中，我们有时候会很自然地对**field**字段直接赋值，如下所示：

```java
public static final int MAX=100;
public  int count=0;
```

对于虚拟机而言，上述的两个**field**字段赋值的时机是不同的：

- 对于非静态（即无**static**修饰）的**field**字段的赋值将会出现在实例构造方法**<init>()**中
-  对于静态的**field**字段，有两个选择：1、在静态构造方法**<cinit>()**中进行；2 、使用**ConstantValue**属性进行赋值

目前的Sun javac编译器的选择是：如果使用final和static同时修饰一个field字段，并且这个字段是基本类型或者String类型的，那么编译器在编译这个字段的时候，会在对应的field_info结构体中增加一个ConstantValue类型的结构体，在赋值的时候使用这个ConstantValue进行赋值；如果该field字段并没有被final修饰，或者不是基本类型或者String类型，那么将在类构造方法<cinit>()中赋值。

 对于上述的**public static final init MAX=100;**   javac编译器在编译此**field**字段构建**field_info**结构体时，除了访问标志、名称索引、描述符索引外，会增加一个**ConstantValue**类型的属性表。

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzgt59podcj30lm0j7ac1.jpg)

1. 字段计数器中的值为0x0001,表示这个类就定义了一个field字段
2. 字段的访问标志是0x009A,二进制是00000000 10011010，即第9、12、13、15位标志位为1，这个字段的标志符有：ACC_TRANSIENT、ACC_FINAL、ACC_STATIC、ACC_PRIVATE;

3. 名称索引中的值为0x0005,指向了常量池中的第5项，为“str”,表明这个field字段的名称是str；

4. 描述索引中的值为0x0006,指向了常量池中的第6项，为"Ljava/lang/String;"，表明这个field字段的数据类型是java.lang.String类型；

5.属性表计数器中的值为0x0001,表明field_info还有一个属性表；

6.属性表名称索引中的值为0x0007,指向常量池中的第7项，为“ConstantValue”,表明这个属性表的名称是ConstantValue，即属性表的类型是ConstantValue类型的；

7.属性长度中的值为0x0002，因为此属性表是ConstantValue类型，它的值固定为2；

8.常量值索引 中的值为0x0008,指向了常量池中的第8项，为CONSTANT_String_info类型的项，表示“This is a test” 的常量。在对此field赋值时，会使用此常量对field赋值。

### 1.11.7 实例解析

定义如下一个简单的**Simple**类，然后通过查看**Simple.class**文件内容并结合**javap -v Simple** 生成的常量池内容，分析**str field**字段的结构：

```java
public class Simple {
 
	private  transient static final String str ="This is a test";
}
```

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzgu2w60a9j30mh0uiafz.jpg)

1. 字段计数器中的值为0x0001,表示这个类就定义了一个field字段

2. 字段的访问标志是0x009A,二进制是00000000 10011010，即第9、12、13、15位标志位为1，这个字段的标志符有：ACC_TRANSIENT、ACC_FINAL、ACC_STATIC、ACC_PRIVATE;

3. 名称索引中的值为0x0005,指向了常量池中的第5项，为“str”,表明这个field字段的名称是str；

4. 描述索引中的值为0x0006,指向了常量池中的第6项，为"Ljava/lang/String;"，表明这个field字段的数据类型是java.lang.String类型；

5.属性表计数器中的值为0x0001,表明field_info还有一个属性表；

6.属性表名称索引中的值为0x0007,指向常量池中的第7项，为“ConstantValue”,表明这个属性表的名称是ConstantValue，即属性表的类型是ConstantValue类型的；

7.属性长度中的值为0x0002，因为此属性表是ConstantValue类型，它的值固定为2；

8.常量值索引 中的值为0x0008,指向了常量池中的第8项，为CONSTANT_String_info类型的项，表示“This is a test” 的常量。在对此field赋值时，会使用此常量对field赋值。

## 1.12 方法计数器(methods_count)

方法计数器， methods_count的值表示当前Class 文件 methods[]数组的成员个数。Methods[]数组中每一项都是一个 method_info 结构的数据项。

## 1.13 方法信息数据区

(methods[methods_count])

方法表，methods[] 数组中的每个成员都必须是一个 method_info 结构的数据项，用于表示当前类或接口中某个方法的完整描述。如果某个method_info 结构的access_flags 项既没有设置 ACC_NATIVE 标志也没有设置ACC_ABSTRACT 标志，那么它所对应的方法体就应当可以被 Java 虚拟机直接从当前类加载，而不需要引用其它类。 method_info结构可以表示类和接口中定义的所有方法，包括实例方法、类方法、实例初始化方法方法和类或接口初始化方法方法 。methods[]数组只描述当前类或接口中声明的方法，不包括从父类或父接口继承的方法。



**1、类中定义的method方法是如何在class文件中组织的**

**2、method方法的表示-方法表集合在class文件的什么位置**

**3、类中的method方法的实现代码---即机器码指令存放到哪了，并初步了解机器指令**

4. **为什么没有在类中定义自己的构造函数，却可以使用new ClassName()构造函数创建对象**

5. **IDE代码提示功能的基本原理**



### 1.13.1 概述

​      **方法表集合**是指由若干个**方法表（method_info）**组成的集合。对于在类中定义的若干个，经过**JVM**编译成**class**文件后，会将相应的**method**方法信息组织到一个叫做**方法表集合**的结构中，**字段表集合**是一个类数组结构，如下图所示：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzhdz7f3v8j30js0daaca.jpg) 

### 1.13.2 method方法的描述

方法表集合在class文件中的位置

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzhe2fxq1nj30nm0c9mzr.jpg)



### 1.13.3 一个类中的method方法应该包含哪些信息？

method_info结构体的定义

接下来让我们看看**Method_info** 结构体是怎么组织**method**方法信息的:

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzhe866mh4j30md0d9abj.jpg)



​      实际上**JVM**还会对**method**方法的描述添加其他信息，我们将在后面详细讨论。

​    方法表的结构体由：**访问标志(access_flags)、名称索引(name_index)、描述索引(descriptor_index)、属性表(attribute_info)集合**组成。

- 访问标志(access_flags)：

method_info结构体最前面的两个字节表示的访问标志（access_flags），记录这这个方法的作用域、静态or非静态、可变性、是否可同步、是否本地方法、是否抽象等信息，实际上不止这些信息，我们后面会详细介绍访问标志这两个字节的每一位具体表示什么意思。

- 名称索引(name_index)：

紧跟在访问标志（access_flags）后面的两个字节称为名称索引，这两个字节中的值指向了常量池中的某一个常量池项，这个方法的名称以UTF-8格式的字符串存储在这个常量池项中。如public void methodName(),很显然，“methodName”则表示着这个方法的名称，那么在常量池中会有一个CONSTANT_Utf8_info格式的常量池项，里面存储着“methodName”字符串，而mehodName()方法的方法表中的名称索引则指向了这个常量池项。

- 描述索引(descriptor_index)：

描述索引表示的是这个方法的特征或者说是签名，**一个方法会有若干个参数和返回值**，而若干个参数的数据类型和返回值的数据类型构成了这个方法的描述，其基本格式为：     (参数数据类型描述列表)返回值数据类型   。我们将在后面继续讨论。

- 属性表(attribute_info)集合：

这个属性表集合非常重要，方法的实现被JVM编译成JVM的机器码指令，机器码指令就存放在一个Code类型的属性表中；如果方法声明要抛出异常，那么异常信息会在一个Exceptions类型的属性表中予以展现。Code类型的属性表可以说是非常复杂的内容，也是本文最难的地方。

### 1.13.4 访问标志(access_flags)

记录着method方法的访问信息

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzhej6nhm6j30mi0eo76h.jpg)

```java
public static synchronized final void greeting(){
}
```
greeting()方法的修饰符有：public、static、synchronized、final 这几个修饰符修饰，那么相对应地，greeting()方法的访问标志中的ACC_PUBLIC、ACC_STATIC、ACC_SYNCHRONIZED、ACC_FINAL标志位都应该是1，即：

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzhemg55haj30jz08ddgq.jpg)

从上图中可以看出**访问标志**的值应该是二进制**00000000 00111001,即十六进制0x0039**。

### 1.13.5 名称索引和描述符索引

一个方法的签名

   紧接着访问标志（access_flags）后面的两个字节，叫做名称索引(name_index)，这两个字节中的值是指向了常量池中某个常量池项的索引，该常量池项表示这这个方法名称的字符串。

方法描述符索引(descrptor_index)是紧跟在名称索引后面的两个字节，这两个字节中的值跟名称索引中的值性质一样，都是指向了常量池中的某个常量池项。这两个字节中的指向的常量池项，是表示了方法描述符的字符串。

所谓的方法描述符，实质上就是指用一个什么样的字符串来描述一个方法，方法描述符的组成如下图所示：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzheollr78j30le0a9jse.jpg)



如下图所示,method_info结构体的名称索引中存储了一个索引值x，指向了常量池中的第x项，第 x项表示的是字符串"greeting",即表示该方法名称是"greeting"；描述符索引中的y 值指向了常量池的第y项，该项表示字符串"()V"，即表示该方法没有参数，返回值是void类型。

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzheqrw1iyj30ki0k3jth.jpg)

## 1.14 属性计数器

(attributes_count)

局部变量。

 属性计数器，attributes_count的值表示当前 Class 文件attributes表的成员个数。attributes表中每一项都是一个attribute_info 结构的数据项。

## 1.15 属性信息数据区

(attributes[attributes_count])



属性表，attributes 表的每个项的值必须是attribute_info结构。

在Java 7 规范里，Class文件结构中的attributes表的项包括下列定义的属性： InnerClasses  、 EnclosingMethod 、 Synthetic  、Signature、SourceFile，SourceDebugExtension 、Deprecated、RuntimeVisibleAnnotations 、RuntimeInvisibleAnnotations以及BootstrapMethods属性。

  对于支持 Class 文件格式版本号为 49.0 或更高的 Java 虚拟机实现，必须正确识别并读取attributes表中的Signature、RuntimeVisibleAnnotations和RuntimeInvisibleAnnotations属性。对于支持Class文件格式版本号为 51.0 或更高的 Java 虚拟机实现，必须正确识别并读取 attributes表中的BootstrapMethods属性。Java 7 规范 要求任一 Java 虚拟机实现可以自动忽略 Class 文件的 attributes表中的若干 （甚至全部） 它不可识别的属性项。任何本规范未定义的属性不能影响Class文件的语义，只能提供附加的描述信息 。



 **属性表集合：记录方法的机器指令和抛出异常等信息。**

- 这个方法的代码实现，即**方法的可执行的机器指令**
- 这个方法声明的**要抛出的异常信息**
- 这个方法是否**被@deprecated注解表示**
- 这个方法是否是**编译器自动生成的**



**属性表（attribute_info）**结构体的一般结构如下所示：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzhexprqqpj30k80k8gok.jpg)

### 1.15.1  Code类型的属性表

method方法中的机器指令的信息。



**局部变量的信息存放在code属性中。**



 **Code**类型的**属性表(attribute_info)**可以说是**class**文件中最为重要的部分，因为它包含的是**JVM**可以运行的机器码指令，**JVM**能够运行这个类，就是从这个属性中取出机器码的。除了要执行的机器码，它还包含了一些其他信息，如下所示：

Code属性表的组成部分：

- 机器指令----code：

目前的JVM使用一个字节表示机器操作码，即对JVM底层而言，它能表示的机器操作码不多于2的 8 次方，即 256个。class文件中的机器指令部分是class文件中最重要的部分，并且非常复杂，本文的重点不止介绍它，我将专门在一片博文中讨论它，敬请期待。

- 异常处理跳转信息---exception_table：

如果代码中出现了try{}catch{}块，那么try{}块内的机器指令的地址范围记录下来，并且记录对应的catch{}块中的起始机器指令地址，当运行时在try块中有异常抛出的话，JVM会将catch{}块对应懂得其实机器指令地址传递给PC寄存器，从而实现指令跳转；

- Java源码行号和机器指令的对应关系---LineNumberTable属性表：

编译器在将java源码编译成class文件时，会将源码中的语句行号跟编译好的机器指令关联起来，这样的class文件加载到内存中并运行时，如果抛出异常，JVM可以根据这个对应关系，抛出异常信息，告诉我们我们的源码的多少行有问题，方便我们定位问题。这个信息不是运行时必不可少的信息，但是默认情况下，编译器会生成这一项信息，如果你项取消这一信息，你可以使用-g:none 或-g:lines来取消或者要求设置这一项信息。如果使用了-g:none来生成class文件，class文件中将不会有LineNumberTable属性表，造成的影响就是 将来如果代码报错，将无法定位错误信息报错的行，并且如果项调试代码，将不能在此类中打断点（因为没有指定行号。）

- 局部变量表描述信息----LocalVariableTable属性表：

局部变量表信息会记录栈帧局部变量表中的变量和java源码中定义的变量之间的关系，这个信息不是运行时必须的属性，默认情况下不会生成到class文件中。你可以根据javac指令的-g:none或者-g:vars选项来取消或者设置这一项信息。

它有什么作用呢？  当我们使用IDE进行开发时，最喜欢的莫过于它们的代码提示功能了。如果在项目中引用到了第三方的jar包，而第三方的包中的class文件中有无LocalVariableTable属性表的区别如下所示：

![](https://ws4.sinaimg.cn/large/006tNc79ly1fzhfmtvyrjj30jv0e9dht.jpg)

Code属性表结构体的解释：
1.attribute_name_index,属性名称索引，占有2个字节，其内的值指向了常量池中的某一项，该项表示字符串“Code”;
2. attribute_length,属性长度，占有 4个字节，其内的值表示后面有多少个字节是属于此Code属性表的；
3. max_stack,操作数栈深度的最大值，占有 2 个字节，在方法执行的任意时刻，操作数栈都不应该超过这个值，虚拟机的运行的时候，会根据这个值来设置该方法对应的栈帧(Stack Frame)中的操作数栈的深度；
4. max_locals,最大局部变量数目，占有 2个字节，其内的值表示局部变量表所需要的存储空间大小；
5. code_length,机器指令长度，占有 4 个字节，表示跟在其后的多少个字节表示的是机器指令；
6. code,机器指令区域，该区域占有的字节数目由 code_length中的值决定。JVM最底层的要执行的机器指令就存储在这里；
7. exception_table_length,显式异常表长度，占有2个字节，如果在方法代码中出现了try{} catch()形式的结构，该值不会为空，紧跟其后会跟着若干个exception_table结构体，以表示异常捕获情况；
8. exception_table，显式异常表，占有8 个字节，start_pc,end_pc,handler_pc中的值都表示的是PC计数器中的指令地址。exception_table表示的意思是：如果字节码从第start_pc行到第end_pc行之间出现了catch_type所描述的异常类型，那么将跳转到handler_pc行继续处理。
9. attribute_count,属性计数器，占有 2 个字节，表示Code属性表的其他属性的数目
10. attribute_info,表示Code属性表具有的属性表，它主要分为两个类型的属性表：“LineNumberTable”类型和“LocalVariableTable”类型。
  “LineNumberTable”类型的属性表记录着Java源码和机器指令之间的对应关系
  “LocalVariableTable”类型的属性表记录着局部变量描述



```java
public class Simple {
public static synchronized final void greeting(){
	int a = 10;
}
}
```


***Simple.class文件组织信息如下所示：***

![](https://ws1.sinaimg.cn/large/006tNc79ly1fzhg3389d0j30jf0asq5q.jpg)

如上所示，方法表集合使用了蓝色线段圈了起来。

请注意：方法表集合的头两个字节，即方法表计数器（method_count）的值是0x0002，它表示该类中有2 个方法。细心的读者会注意到，我们的Simple.java中就定义了一个greeting()方法，为什么class文件中会显示有两个方法呢？？

在Simple.classz中出现了两个方法表，分别代表构造方法<init>()和 greeting()方法，现在让我们分别来讨论这两个方法：



***Simple.class 中的<init>() 方法:***

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzhg6f2y89j30mz0rtn26.jpg)

解释：

  1. 方法访问标志(access_flags)： 占有 2个字节，值为0x0001,即标志位的第 16 位为 1，所以该<init>()方法的修饰符是：ACC_PUBLIC;

  2. 名称索引(name_index)： 占有 2 个字节，值为 0x0004，指向常量池的第 4项，该项表示字符串“<init>”，即该方法的名称是“<init>”;

 3.描述符索引(descriptor_index): 占有 2 个字节，值为0x0005,指向常量池的第 5 项，该项表示字符串“()V”，即表示该方法不带参数，并且无返回值（构造函数确实也没有返回值）；

4. 属性计数器（attribute_count): 占有 2 个字节，值为0x0001,表示该方法表中含有一个属性表，后面会紧跟着一个属性表；

5. 属性表的名称索引(attribute_name_index)：占有 2 个字节，值为0x0006,指向常量池中的第6 项，该项表示字符串“Code”，表示这个属性表是Code类型的属性表；

6. 属性长度（attribute_length）：占有4个字节，值为0x0000 0011，即十进制的 17，表明后续的 17 个字节可以表示这个Code属性表的属性信息；

7. 操作数栈的最大深度（max_stack）：占有2个字节，值为0x0001,表示栈帧中操作数栈的最大深度是1；

8. 局部变量表的最大容量（max_variable）：占有2个字节，值为0x0001, JVM在调用该方法时，根据这个值设置栈帧中的局部变量表的大小；

9. 机器指令数目(code_length)：占有4个字节，值为0x0000 0005,表示后续的5 个字节 0x2A 、0xB7、 0x00、0x01、0xB1表示机器指令;

10. 机器指令集(code[code_length])：这里共有  5个字节，值为0x2A 、0xB7、 0x00、0x01、0xB1；

11. 显式异常表集合（exception_table_count）： 占有2 个字节，值为0x0000,表示方法中没有需要处理的异常信息；

12. Code属性表的属性表集合（attribute_count）： 占有2 个字节，值为0x0000，表示它没有其他的属性表集合，因为我们使用了-g:none 禁止编译器生成Code属性表的 LineNumberTable 和LocalVariableTable;





***Simple.class 中的greeting() 方法:***

![](https://ws4.sinaimg.cn/large/006tNc79ly1fziyfvkrc9j30lo0r4gql.jpg)

1. 方法访问标志(access_flags)： 占有 2个字节，值为 0x0039 ,即二进制的00000000 00111001,即标志位的第11、12、13、16位为1，根据上面讲的方法标志位的表示，可以得到该greeting()方法的修饰符有：ACC_SYNCHRONIZED、ACC_FINAL、ACC_STATIC、ACC_PUBLIC;

 2. 名称索引(name_index)： 占有 2 个字节，值为 0x0007，指向常量池的第 7 项，该项表示字符串“greeting”，即该方法的名称是“greeting”;

 3. 描述符索引(descriptor_index): 占有 2 个字节，值为0x0005,指向常量池的第 5 项，该项表示字符串“()V”，即表示该方法不带参数，并且无返回值；

4. 属性计数器（attribute_count): 占有 2 个字节，值为0x0001,表示该方法表中含有一个属性表，后面会紧跟着一个属性表；

5.属性表的名称索引(attribute_name_index)：占有 2 个字节，值为0x0006,指向常量池中的第6 项，该项表示字符串“Code”，表示这个属性表是Code类型的属性表；

6. 属性长度（attribute_length）：占有4个字节，值为0x0000 0010，即十进制的16，表明后续的16个字节可以表示这个Code属性表的属性信息；

7. 操作数栈的最大深度（max_stack）：占有2个字节，值为0x0001,表示栈帧中操作数栈的最大深度是1；

8. 局部变量表的最大容量（max_variable）：占有2个字节，值为0x0001, JVM在调用该方法时，根据这个值设置栈帧中的局部变量表的大小；

9. 机器指令数目(code_length)：占有4 个字节，值为0x0000 0004,表示后续的4个字节0x10、 0x0A、 0x3B、0xB1的是表示机器指令;

10.机器指令集(code[code_length])：这里共有4 个字节，值为0x10、 0x0A、 0x3B、0xB1 ；

11. 显式异常表集合（exception_table_count）： 占有2 个字节，值为0x0000,表示方法中没有需要处理的异常信息；

12. Code属性表的属性表集合（attribute_count）： 占有2 个字节，值为0x0000，表示它没有其他的属性表集合，因为我们使用了-g:none 禁止编译器生成Code属性表的 LineNumberTable 和LocalVariableTable;

### 1.15.2 Exceptions类型的属性表

method方法声明的要抛出的异常信息

有些方法在定义的时候，会声明该方法会抛出什么类型的异常，如下定义一个**Interface**接口，它声明了sayHello()方法，抛出Exception异常：

```java
public interface Interface {
 
	public  void sayHello() throws Exception;
}
```

现在让我们看一下**Exceptions**类型的**属性表(attribute_info)**结构体是怎样组织的：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzj1c2s555j30mh0mkq5o.jpg)

如上图所示，Exceptions类型的属性表(attribute_info)结构体由一下元素组成：

属性名称索引(attribute_name_index)：占有 2个字节，其中的值指向了常量池中的表示"Exceptions"字符串的常量池项；

属性长度(attribute_length)：它比较特殊，占有4个字节，它的值表示跟在其后面多少个字节表示异常信息；

异常数量(number_of_exceptions)：占有2 个字节，它的值表示方法声明抛出了多少个异常，即表示跟在其后有多少个异常名称索引；

异常名称索引(exceptions_index_table)：占有2个字节，它的值指向了常量池中的某一项，该项是一个CONSTANT_Class_info类型的项，表示这个异常的完全限定名称；

Exceptions类型的属性表的长度计算
如果某个方法定义中，没有声明抛出异常，那么，表示该方法的方法表(method_info)结构体中的属性表集合中不会有Exceptions类型的属性表；换句话说，如果方法声明了要抛出的异常，方法表(method_info)结构体中的属性表集合中必然会有Exceptions类型的属性表，并且该属性表中的异常数量不小于1。

我们假设异常数量中的值为 N，那么后面的异常名称索引的数量就为N，它们总共占有的字节数为N*2，而异常数量占有2个字节，那么将有下面的这个关系式：

属性长度(attribute_length)中的值= 2  + 2*异常数量(number_of_exceptions)中的值
Exceptions类型的属性表（attribute_info）的长度=2+4+属性长度(attribute_length)中的值

举例：

将上面定义的Interface接口类编译成class文件，然后我们查看Interface.class文件，找出方法表集合所在位置和相应的数据，并辅助javap -v  Inerface 查看常量池信息，如

![](https://ws2.sinaimg.cn/large/006tNc79ly1fzj1jneqebj30ko0petdy.jpg)

  由于sayHello()方法是在的Interface接口类中声明的，它没有被实现，所以它对应的方法表(method_info)结构体中的属性表集合中没有Code类型的属性表。

注：

1. 方法计数器（methods_count）中的值为0x0001，表明其后的方法表(method_info)就一个,即我们就定义了一个方法，其后会紧跟着一个方法表(method_info)结构体；

2. 方法的访问标志（access_flags）的值是0x0401，二进制是00000100 00000001,第6位和第16位是1，对应上面的标志位信息，可以得出它的访问标志符有：ACC_ABSTRACT、ACC_PUBLIC。细心的读者可能会发现，在上面声明的sayHello()方法中并没有声明为abstract类型啊。确实如此，这是因为编译器对于接口内声明的方法自动加上ACC_ABSTRACT标志。

3. 名称索引（name_index）中的值为0x0005，0x0005指向了常量池的第5项，第五项表示的字符串为“sayHello”，即表示的方法名称是sayHello

4. 描述符索引(descriptor_index)中的值为0x0006,0x0006指向了常量池中的第6项，第6项表示的字符串为“()V” 表示这个方法的无入参，返回值为void类型

5. 属性表计数器(attribute_count)中的值为0x0001,表示后面的属性表的个数就1个，后面紧跟着一个attribute_info结构体；

6. 属性表（attribute_info）中的属性名称索引(attribute_name_index)中的值为0x0007，0x0007指向了常量池中的第7 项，第 7项指向字符串“Exceptions”，即表示该属性表表示的异常信息；

7. 属性长度（attribute_length）中的值为：0x00000004,即后续的4个字节将会被解析成属性值；

8. 异常数量（number_of_exceptions）中的值为0x0001,表示这个方法声明抛出的异常个数是1个；

9.异常名称索引(exception_index_table)中的值为0x0008,指向了常量池中的第8项，第8项表示的是CONSTANT_Class_info类型的常量池项，表示“java/lang/Exception”，即表示此方法抛出了java.lang.Exception异常。

### 1.15.3  IDE代码提示功能实现的基本原理 

​       每个IDE都提供了代码提示功能，它们实现的基本原理其实就是IDE针对它们项目下的包中所有的class文件进行建模，解析出它们的方法信息，当我们一定的条件时，IDE会自动地将合适条件的方法列表展示给开发者，供开发者使用。

![](https://ws3.sinaimg.cn/large/006tNc79ly1fzj1xtyvunj30jv0e9dht.jpg)

## 1.16 总结

根据上述的叙述，我们可以将class的文件组织结构概括成以下面这个结构体：

![](https://ws2.sinaimg.cn/large/006tNc79ly1fz9t02eetrj30fk0fpgmz.jpg)

























