<!--ts-->
   * [前言](#前言)
   * [1. 基本数据类型](#1-基本数据类型)
      * [1.1 基本数据类型有什么好处](#11-基本数据类型有什么好处)
      * [1.2 整型的取值范围](#12-整型的取值范围)
      * [1.3 超出范围怎么办](#13-超出范围怎么办)
   * [2. 包装类型](#2-包装类型)
      * [2.1 为什么需要包装类](#21-为什么需要包装类)
   * [3. 拆箱与装箱](#3-拆箱与装箱)
      * [3.1 自动拆箱与自动装箱](#31-自动拆箱与自动装箱)
      * [3.2 自动装箱与自动拆箱的实现原理](#32-自动装箱与自动拆箱的实现原理)
      * [3.3 哪些地方会自动拆装箱](#33-哪些地方会自动拆装箱)
         * [场景一、将基本数据类型放入集合类](#场景一将基本数据类型放入集合类)
         * [场景二、包装类型和基本类型的大小比较](#场景二包装类型和基本类型的大小比较)
         * [场景三、包装类型的运算](#场景三包装类型的运算)
         * [场景四、三目运算符的使用](#场景四三目运算符的使用)
         * [场景五、函数参数与返回值](#场景五函数参数与返回值)
      * [3.4 自动拆装箱与缓存](#34-自动拆装箱与缓存)
      * [3.5 自动拆装箱带来的问题](#35-自动拆装箱带来的问题)
      * [3.7 参考资料](#37-参考资料)
   * [4. 自动拆箱导致空指针异常](#4-自动拆箱导致空指针异常)
      * [4.1 三目运算符](#41-三目运算符)
      * [4.2 自动装箱与自动拆箱](#42-自动装箱与自动拆箱)
      * [4.3 问题回顾](#43-问题回顾)
      * [4.4 原理分析](#44-原理分析)
      * [4.5 问题解决](#45-问题解决)

<!-- Added by: anapodoton, at: Fri Feb 28 21:04:30 CST 2020 -->

<!--te-->

# 前言

本文主要介绍Java中的自动拆箱与自动装箱的有关知识。

请带着以下问题学习：

WHY:为什么需要自动拆装箱。

WHAT:什么是自动拆装箱。

HOW:自动拆装箱是如何实现的。

WHEN:什么时候会用到自动拆装箱

WHERE:什么地方可能会自动进行自动拆装箱，如三目运算符

OTHER:自动拆装箱可能会带来那些问题？

以上就是我学习新知识比较常用的方法：即 [WWW.WHO](http://www.who/)

# 1. 基本数据类型

基本类型，或者叫做内置类型，是Java中不同于类(Class)的特殊类型。它们是我们编程中使用最频繁的类型。

Java是一种强类型语言，第一次申明变量必须说明数据类型，第一次变量赋值称为变量的初始化。

Java基本类型共有八种，基本类型可以分为三类：

> 字符类型`char`
>
> 布尔类型`boolean`
>
> 数值类型`byte`、`short`、`int`、`long`、`float`、`double`。

数值类型又可以分为整数类型`byte`、`short`、`int`、`long`和浮点数类型`float`、`double`。

Java中的数值类型不存在无符号的，它们的取值范围是固定的，不会随着机器硬件环境或者操作系统的改变而改变。

实际上，Java中还存在另外一种基本类型`void`，它也有对应的包装类 `java.lang.Void`，不过我们无法直接对它们进行操作。

## 1.1 基本数据类型有什么好处

我们都知道在Java语言中，`new`一个对象是存储在堆里的，我们通过栈中的引用来使用这些对象；所以，对象本身来说是比较消耗资源的。

对于经常用到的类型，如int等，如果我们每次使用这种变量的时候都需要new一个Java对象的话，就会比较笨重。所以，和C++一样，Java提供了基本数据类型，这种数据的变量不需要使用new创建，他们不会在堆上创建，而是直接在栈内存中存储，因此会更加高效。

## 1.2 整型的取值范围

Java中的整型主要包含`byte`、`short`、`int`和`long`这四种，表示的数字范围也是从小到大的，之所以表示范围不同主要和他们存储数据时所占的字节数有关。

先来个简答的科普，1字节=8位（bit）。java中的整型属于有符号数。

先来看计算中8bit可以表示的数字：

```
最小值：10000000 （-128）(-2^7)
最大值：01111111（127）(2^7-1)
```

整型的这几个类型中，

- byte：byte用1个字节来存储，范围为-128(-2^7)到127(2^7-1)，在变量初始化的时候，byte类型的默认值为0。
- short：short用2个字节存储，范围为-32,768 (-2^15)到32,767 (2^15-1)，在变量初始化的时候，short类型的默认值为0，一般情况下，因为Java本身转型的原因，可以直接写为0。
- int：int用4个字节存储，范围为-2,147,483,648 (-2^31)到2,147,483,647 (2^31-1)，在变量初始化的时候，int类型的默认值为0。
- long：long用8个字节存储，范围为-9,223,372,036,854,775,808 (-2^63)到9,223,372,036, 854,775,807 (2^63-1)，在变量初始化的时候，long类型的默认值为0L或0l，也可直接写为0。

## 1.3 超出范围怎么办

上面说过了，整型中，每个类型都有一定的表示范围，但是，在程序中有些计算会导致超出表示范围，即溢出。如以下代码：

```java
    int i = Integer.MAX_VALUE;
    int j = Integer.MAX_VALUE;

    int k = i + j;
    System.out.println("i (" + i + ") + j (" + j + ") = k (" + k + ")");
```

输出结果：i (2147483647) + j (2147483647) = k (-2)

**这就是发生了溢出，溢出的时候并不会抛异常，也没有任何提示。**所以，在程序中，使用同类型的数据进行运算的时候，**一定要注意数据溢出的问题。**

# 2. 包装类型

Java语言是一个面向对象的语言，但是Java中的基本数据类型却不是面向对象的，这在实际使用时存在很多的不便，为了解决这个不足，在设计类时为每个基本数据类型设计了一个对应的类进行代表，这样八个和基本数据类型对应的类统称为包装类(Wrapper Class)。

包装类均位于java.lang包，包装类和基本数据类型的对应关系如下表所示

| 基本数据类型 | 包装类    |
| ------------ | --------- |
| byte         | Byte      |
| boolean      | Boolean   |
| short        | Short     |
| char         | Character |
| int          | Integer   |
| long         | Long      |
| float        | Float     |
| double       | Double    |

在这八个类名中，除了Integer和Character类以后，其它六个类的类名和基本数据类型一致，只是类名的第一个字母大写即可。

## 2.1 为什么需要包装类

很多人会有疑问，既然Java中为了提高效率，提供了八种基本数据类型，为什么还要提供包装类呢？

这个问题，其实前面已经有了答案，**因为Java是一种面向对象语言，很多地方都需要使用对象而不是基本数据类型。**比如，在集合类中，我们是无法将int 、double等类型放进去的。因为集合的容器要求元素是Object类型。

为了让基本类型也具有对象的特征，就出现了包装类型，它相当于将基本类型“包装起来”，使得它具有了对象的性质，并且为其添加了属性和方法，丰富了基本类型的操作。

# 3. 拆箱与装箱

那么，有了基本数据类型和包装类，肯定有些时候要在他们之间进行转换。比如把一个基本数据类型的int转换成一个包装类型的Integer对象。

我们认为包装类是对基本类型的包装，所以，把基本数据类型转换成包装类的过程就是打包装，英文对应于boxing，中文翻译为装箱。

反之，把包装类转换成基本数据类型的过程就是拆包装，英文对应于unboxing，中文翻译为拆箱。

在Java SE5之前，要进行装箱，可以通过以下代码：

```java
Integer i = new Integer(10);
```

## 3.1 自动拆箱与自动装箱

在Java SE5中，为了减少开发人员的工作，Java提供了自动拆箱与自动装箱功能。

自动装箱: 就是将基本数据类型自动转换成对应的包装类。

自动拆箱：就是将包装类自动转换成对应的基本数据类型。

```java
Integer i =10;  //自动装箱
int b= i;     //自动拆箱
```

`Integer i=10` 可以替代 `Integer i = new Integer(10);`，这就是因为Java帮我们提供了自动装箱的功能，不需要开发者手动去new一个Integer对象。

## 3.2 自动装箱与自动拆箱的实现原理

既然Java提供了自动拆装箱的能力，那么，我们就来看一下，到底是什么原理，Java是如何实现的自动拆装箱功能。

我们有以下自动拆装箱的代码：

```java
public static  void main(String[]args){
    Integer integer=1; //装箱
    int i=integer; //拆箱
}
```

对以上代码进行反编译后可以得到以下代码：

```java
public static  void main(String[]args){
    Integer integer=Integer.valueOf(1); 
    int i=integer.intValue(); 
}
```

从上面反编译后的代码可以看出，int的自动装箱都是通过`Integer.valueOf()`方法来实现的，Integer的自动拆箱都是通过`integer.intValue`来实现的。如果读者感兴趣，可以试着将八种类型都反编译一遍 ，你会发现以下规律：

> 自动装箱都是通过包装类的`valueOf()`方法来实现的.自动拆箱都是通过包装类对象的`xxxValue()`来实现的。

## 3.3 哪些地方会自动拆装箱

我们了解过原理之后，在来看一下，什么情况下，Java会帮我们进行自动拆装箱。前面提到的变量的初始化和赋值的场景就不介绍了，那是最简单的也最容易理解的。

我们主要来看一下，那些可能被忽略的场景。

### 场景一、将基本数据类型放入集合类

我们知道，Java中的集合类只能接收对象类型，那么以下代码为什么会不报错呢？

```java
List<Integer> li = new ArrayList<>();
for (int i = 1; i < 50; i ++){
    li.add(i);
}
```

将上面代码进行反编译，可以得到以下代码：

```java
List<Integer> li = new ArrayList<>();
for (int i = 1; i < 50; i += 2){
    li.add(Integer.valueOf(i));
}
```

以上，我们可以得出结论，当我们把基本数据类型放入集合类中的时候，会进行自动装箱。

### 场景二、包装类型和基本类型的大小比较

有没有人想过，当我们对Integer对象与基本类型进行大小比较的时候，实际上比较的是什么内容呢？看以下代码：

```java
    Integer a=1;
    System.out.println(a==1?"等于":"不等于");
    Boolean bool=false;
    System.out.println(bool?"真":"假");
```

对以上代码进行反编译，得到以下代码：

```java
    Integer a=1;
    System.out.println(a.intValue()==1?"等于":"不等于");
    Boolean bool=false;
    System.out.println(bool.booleanValue?"真":"假");
```

可以看到，包装类与基本数据类型进行比较运算，是**先将包装类进行拆箱成基本数据类型，然后进行比较的**。

### 场景三、包装类型的运算

有没有人想过，当我们对Integer对象进行四则运算的时候，是如何进行的呢？看以下代码：

```java
    Integer i = 10;
    Integer j = 20;

    System.out.println(i+j);
```

反编译后代码如下：

```java
    Integer i = Integer.valueOf(10);
    Integer j = Integer.valueOf(20);
    System.out.println(i.intValue() + j.intValue());
```

我们发现，两个包装类型之间的运算，会被**自动拆箱成基本类型进行**。

### 场景四、三目运算符的使用

这是很多人不知道的一个场景，作者也是一次线上的血淋淋的Bug发生后才了解到的一种案例。看一个简单的三目运算符的代码：

```java
boolean flag = true;
Integer i = 0;
int j = 1;
int k = flag ? i : j;
```

很多人不知道，其实在`int k = flag ? i : j;`这一行，会发生自动拆箱。反编译后代码如下：

```java
boolean flag = true;
Integer i = Integer.valueOf(0);
int j = 1;
int k = flag ? i.intValue() : j;
System.out.println(k);
```

这其实是三目运算符的语法规范。当第二，第三位操作数分别为基本类型和对象时，其中的对象就会拆箱为基本类型进行操作。

因为例子中，`flag ? i : j;`片段中，第二段的i是一个包装类型的对象，而第三段的j是一个基本类型，所以会对包装类进行自动拆箱。如果这个时候i的值为`null`，那么久会发生NPE。（[自动拆箱导致空指针异常](http://www.hollischuang.com/archives/435)）

### 场景五、函数参数与返回值

这个比较容易理解，直接上代码了：

```java
//自动拆箱
public int getNum1(Integer num) {
 return num;
}
//自动装箱
public Integer getNum2(int num) {
 return num;
}
```

## 3.4 自动拆装箱与缓存

-128到127之间存在缓存。

Java SE的自动拆装箱还提供了一个和缓存有关的功能，我们先来看以下代码，猜测一下输出结果：

```java
public static void main(String... strings) {

    Integer integer1 = 3;
    Integer integer2 = 3;

    if (integer1 == integer2)
        System.out.println("integer1 == integer2");
    else
        System.out.println("integer1 != integer2");

    Integer integer3 = 300;
    Integer integer4 = 300;

    if (integer3 == integer4)
        System.out.println("integer3 == integer4");
    else
        System.out.println("integer3 != integer4");

}
```

我们普遍认为上面的两个判断的结果都是false。虽然比较的值是相等的，但是由于比较的是对象，而对象的引用不一样，所以会认为两个if判断都是false的。在Java中，==比较的是对象应用，而equals比较的是值。所以，在这个例子中，不同的对象有不同的引用，所以在进行比较的时候都将返回false。奇怪的是，这里两个类似的if条件判断返回不同的布尔值。

上面这段代码真正的输出结果：

```
integer1 == integer2
integer3 != integer4
```

原因就和Integer中的缓存机制有关。在Java 5中，在Integer的操作上引入了一个新功能来节省内存和提高性能。整型对象通过使用相同的对象引用实现了缓存和重用。

> 适用于整数值区间-128 至 +127。
>
> 只适用于自动装箱。使用构造函数创建对象不适用。

具体的代码实现可以阅读[Java中整型的缓存机制](http://www.hollischuang.com/archives/1174)一文，这里不再阐述。

我们只需要知道，当需要进行自动装箱时，如果数字在-128至127之间时，会直接使用缓存中的对象，而不是重新创建一个对象。

其中的javadoc详细的说明了**缓存支持-128到127之间的自动装箱过程**。最大值127可以通过`-XX:AutoBoxCacheMax=size`修改。

实际上这个功能在Java 5中引入的时候,**范围是固定的-128 至 +127**。后来在Java 6中，可以通过`java.lang.Integer.IntegerCache.high`设置最大值。

这使我们可以根据应用程序的实际情况灵活地调整来提高性能。到底是什么原因选择这个-128到127范围呢？因为这个范围的数字是最被广泛使用的。 在程序中，第一次使用Integer的时候也需要一定的额外时间来初始化这个缓存。

在Boxing Conversion部分的Java语言规范(JLS)规定如下：

如果一个变量p的值是：

```java
-128至127之间的整数(§3.10.1)

true 和 false的布尔值 (§3.10.3)

‘\u0000’至 ‘\u007f’之间的字符(§3.10.4)
```

范围内的时，将p包装成a和b两个对象时，可以直接使用a==b判断a和b的值是否相等。

## 3.5 自动拆装箱带来的问题

当然，自动拆装箱是一个很好的功能，大大节省了开发人员的精力，不再需要关心到底什么时候需要拆装箱。但是，他也会引入一些问题。

> 包装对象的数值比较，不能简单的使用`==`，虽然-128到127之间的数字可以，但是这个范围之外还是需要使用`equals`比较。
>
> 前面提到，有些场景会进行自动拆装箱，同时也说过，由于自动拆箱，如果包装类对象为null，那么自动拆箱时就有可能抛出NPE。
>
> 如果一个增强for循环中有大量拆装箱操作，会浪费很多资源。

## 3.7 参考资料

[Java的自动拆装箱](https://www.jianshu.com/p/cc9312104876)

# 4. 自动拆箱导致空指针异常

[自动拆箱导致空指针异常](https://www.hollischuang.com/archives/435)

> 写在前面：三目运算符是我们经常在代码中使用的，`a= (b==null?0:1);`这样一行代码可以代替一个`if-else`,可以使代码变得清爽易读。但是，三目运算符也是有一定的语言规范的。在运用不恰当的时候会导致意想不到的问题。前段时间遇到（一个由于使用三目运算符导致的问题，其实是因为有三目运算符和自动拆箱同时使用（虽然自动拆箱不是我主动用的）。

## 4.1 三目运算符

对于条件表达式`b?x:y`，先计算条件b，然后进行判断。如果b的值为true，计算x的值，运算结果为x的值；否则，计算y的值，运算结果为y的值。一个条件表达式从不会既计算x，又计算y。条件运算符是右结合的，也就是说，从右向左分组计算。例如，a?b:c?d:e将按a?b:（c?d:e）执行。

## 4.2 自动装箱与自动拆箱

基本数据类型的**自动装箱**(`autoboxing`)、**拆箱**(`unboxing`)是自J2SE 5.0开始提供的功能。 一般我们要创建一个类的对象实例的时候，我们会这样： `Class a = new Class(parameters);` 当我们创建一个`Integer`对象时，却可以这样： `Integer i = 100;`(**注意：和 int i = 100;是有区别的** ) 实际上，执行上面那句代码的时候，系统为我们执行了： `Integer i = Integer.valueOf(100)`; 这里暂且不讨论这个原理是怎么实现的（何时拆箱、何时装箱），也略过普通数据类型和对象类型的区别。我们可以理解为，当我们自己写的代码符合装（拆）箱规范的时候，编译器就会自动帮我们拆（装）箱。那么，这种不被程序员控制的自动拆（装）箱会不会存在什么问题呢？

## 4.3 问题回顾

首先，通过你已有的经验看一下下面这段代码。如果你得到的结果和后文分析的结果一致（并且你知道原理），那么请忽略本文。如果不一致，请跟我探索下去。

```java
Map<String,Boolean> map =  new HashMap<String, Boolean>();
Boolean b = (map!=null ? map.get("test") : false);
```

以上这段代码，是我们在不注意的情况下有可能经常会写的一类代码（在很多时候我们都爱使用三目运算符）。当然，这段代码是存在问题的，执行该代码，会报NPE.

```
Exception in thread "main" java.lang.NullPointerException
```

首先可以明确的是，既然报了空指针，那么一定是有些地方调用了一个null的对象的某些方法。在这短短的两行代码中，看上去只有一处方法调用`map.get("test")`，但是我们也都是知道，map已经事先初始化过了，不会是Null，那么到底是哪里有空指针呢。我们接下来[反编译](http://www.hollischuang.com/archives/58)一下该代码。看看我们写的代码在经过编译器处理之后变成了什么样。

反编译后代码如下：

```java
HashMap hashmap = new HashMap();
Boolean boolean1 = Boolean.valueOf(hashmap == null ? false : ((Boolean)hashmap.get("test")).booleanValue());
```

看完这段反编译之后的代码之后，经过分析我们大概可以知道问题出在哪里。`((Boolean)hashmap.get("test")).booleanValue()`的执行过程及结果如下：

> hashmap.get(“test”)->null;
>
> (Boolean)null->null;
>
> null.booleanValue()->报错

好，问题终于定位到了。那么接下来看看如何解决该问题以及为什么会出现这种问题。

## 4.4 原理分析

通过查看反编译之后的代码，我们准确的定位到了问题，分析之后我们可以得出这样的结论：NPE的原因应该是三目运算符和自动拆箱导致了空指针异常。

那么，这段代码为什么会自动拆箱呢？这其实是三目运算符的语法规范。参见[jls-15.25](http://docs.oracle.com/javase/specs/jls/se7/html/jls-15.html#jls-15.25)，摘要如下：

```
If the second and third operands have the same type (which may be the null type), then that is the type of the conditional expression.

  If one of the second and third operands is of primitive type T, and the type of the other is the result of applying boxing conversion (§5.1.7) to T, then the type of the conditional expression is T.

 If one of the second and third operands is of the null type and the type of the other is a reference type, then the type of the conditional expression is that reference type.
```

简单的来说就是：当第二，第三位操作数分别为基本类型和对象时，其中的对象就会拆箱为基本类型进行操作。

所以，结果就是：由于使用了三目运算符，并且第二、第三位操作数分别是基本类型和对象。所以对对象进行拆箱操作，由于该对象为null，所以在拆箱过程中调用null.booleanValue()的时候就报了NPE。

## 4.5 问题解决

如果代码这么写，就不会报错：

```java
Map<String,Boolean> map =  new HashMap<String, Boolean>();
Boolean b = (map!=null ? map.get("test") : Boolean.FALSE);
```

就是保证了三目运算符的第二第三位操作数都为对象类型。

这和三目运算符有关。
