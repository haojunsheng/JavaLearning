<!--ts-->
   * [前言](#前言)
   * [为什么数组有length属性？](#为什么数组有length属性)
   * [Java中为什么没有定义一个类似String一样Array类](#java中为什么没有定义一个类似string一样array类)
   * [为什么String有length()方法？](#为什么string有length方法)

<!-- Added by: anapodoton, at: Sat Feb 15 21:13:02 CST 2020 -->

<!--te-->

# 前言

在开始正文之前，请你快速回答如下问题：

> 在不使用任何带有自动补全功能IDE的情况下，如何获取一个数组的长度？以及，如何获取一个字符串的长度？

这个问题我问过不同水平的程序员，包括初级和中级水平的。他们都不能准确而自信地回答这个问题（如果你能很准确很自信的回答这个问题，那么证明针对这一知识点你比大多数中级程序员掌握的好）。由于现在很多IDE都有代码补全功能，这使得开发人员在很多问题上都理解的很肤浅。本文将介绍几个关于Java数组的关键概念。

上面问题的正确回答姿势应该是这样的：

```java
int[] arr = new int[3];
System.out.println(arr.length);//使用length获取数组的程度

String str = "abc";
System.out.println(str.length());//使用length()获取字符串的长度
```

那么问题来了，为什么数组有`length`属性，而字符串没有？或者，为什么字符串有`length()`方法，而数组没有？

# 为什么数组有length属性？

首先，数组是一个容器对象（[Java中的数组是对象吗？](http://blog.csdn.net/zhangjg_blog/article/details/16116613#t1) ）， [Java中的数组是对象吗?.md](../Java中的数组是对象吗?.md) 其中包含固定数量的同一类型的值。一旦数组被创建，他的长度就是固定的了。数组的长度可以作为`final`实例变量的长度。因此，长度可以被视为一个数组的属性。

有两种创建数组的方法：1、通过数组表达式创建数组。2、通过初始化值创建数组。无论使用哪种方式，一旦数组被创建，其大小就固定了。

使用表达式创建数组方式如下，该方式指明了元素类型、数组的维度、以及至少一个维度的数组的长度。

该声明方式是符合要求的，因为他指定了一个维度的长度（该数组的类型为int，维度为2，第一维度的长度为3）

```java
int[][] arr = new int[3][];
```

使用数组初始化的方式创建数组时需要提供所有的初始值。形式是使用`{`和`}`将所有初始值括在一起并用`,`隔开。

```java
int[] arr = {1,2,3};
```

注：

这里可能会有一个疑问，既然数组大小是初始化时就规定好的，那么`int[][] arr = new int[3][];`定义的数组并没有给出数组的第二维的大小，那么这个`arr`的长度到底是如何“规定好”的呢？

其实，`arr`的长度就是3。其实Java中所有的数组，无论几维，其实都是一维数组。例如arr，分配了3个空间，每个空间存放一个一维数组的地址，这样就成了“二维”数组。但是对于arr来说，他的长度就是3。

```java
int[][] a=new int[3][];
System.out.println(a.length);//3
int[][] b=new int[3][5];
System.out.println(b.length);//3
```

# Java中为什么没有定义一个类似String一样Array类

因为数组也是对象，所以下面的代码也是合法的：

```java
Object obj = new int[10];
```

数组包含所有从Object继承下来方法（[Java中数组的继承关系](http://blog.csdn.net/zhangjg_blog/article/details/16116613#t4)），除clone()之外。为什么没有一个array类呢？在Java中没有`Array.java`文件。一个简单的解释是它被隐藏起来了（注：Java中的数组有点类似于基本数据类型，是一个内建类型，并没有实际的类与他对应）。你可以思考这样一个问题——如果有一个Array类，那它会像什么样？它会仍然需要一个数组来存放所有的数组元素，对吗？因此，定义出一个Array类不是一个好的主意。（译者注：这里可能有点绕，道理有点类似于：鸡生蛋蛋生鸡问题，可能比喻也不是很恰当，请读者自行理解）

事实上我们可以获得数组的类定义，通过下面的代码：

```java
int[] arr = new int[3];
System.out.println(arr.getClass());
```

输出：

```
class [I
```

“class [I”代表着”成员类型是int的数组”的class对象运行时类型的签名

# 为什么String有length()方法？

String背后的数据结构是一个char数组,所以没有必要来定义一个不必要的属性（因为该属性在char数值中已经提供了）。和C不同的是，Java中char的数组并不等于字符串，虽然String的内部机制是char数组实现的。（注：C语言中，并没有String类，定义字符串通常使用`char string[6] = "hollis";`的形式）

注：要想把char[]转成字符串有以下方式：

```java
char []s = {'a','b','c'};
String string2 = new String(s);
String string3 = String.valueOf(s);
```
