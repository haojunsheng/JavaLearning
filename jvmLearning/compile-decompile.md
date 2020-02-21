

# 前言

# 1. Java代码的编译与反编译那些事儿

 [Java代码的编译与反编译那些事儿](https://www.hollischuang.com/archives/58)



# 2. 深入分析Java的编译原理

[深入分析Java的编译原理](https://www.hollischuang.com/archives/2322)

[Github地址](https://github.com/haojunsheng/JavaLearning/blob/master/jvmLearning/HotSpot.md#32-%E6%B7%B1%E5%85%A5%E5%88%86%E6%9E%90java%E7%9A%84%E7%BC%96%E8%AF%91%E5%8E%9F%E7%90%86)

# 3. 我反编译了Java 10的本地变量类型推断

[我反编译了Java 10的本地变量类型推断](https://www.hollischuang.com/archives/2187)

北京时间 3 月 21 日，Oracle 官方宣布 Java 10 正式发布。这是 Java 大版本周期变化后的第一个正式发布版本。关于Java 10 ，最值得程序员关注的一个新特性恐怕就是本地变量类型推断（local-variable type inference）了。

Java 10 推出之后，很多文章也随之出来了，告诉我们有哪些特性，告诉我们本地变量类型推断怎么用。但是，知其然，要知其所以然。

Java 10发布之后，我第一时间下载了这个版本的Jdk并安装到我的电脑中，然后写了一段代码，真正的感受一下本地变量推断到底如何。这篇文章简单来谈一下我的感受。

关于本地变量类型推断的用法，我的《[Java 10将于本月发布，它会改变你写代码的方式](http://www.hollischuang.com/archives/2064)》中有介绍过。主要可以用在以下几个场景中：

```java
public class VarDemo {

    public static void main(String[] args) {
        //初始化局部变量  
        var string = "hollis";
        //初始化局部变量  
        var stringList = new ArrayList<String>();
        stringList.add("hollis");
        stringList.add("chuang");
        stringList.add("weChat:hollis");
        stringList.add("blog:http://www.hollischuang.com");
        //增强for循环的索引
        for (var s : stringList){
            System.out.println(s);
        }
        //传统for循环的局部变量定义
        for (var i = 0; i < stringList.size(); i++){
            System.out.println(stringList.get(i));
        }
    }
}
```

然后，使用java 10的javac命令进行编译：

```
/Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents/Home/bin/javac VarDemo.java
```

生成VarDemo.class文件，我们对VarDemo.class进行[反编译](http://www.hollischuang.com/archives/58)。用jad进行反编译得到以下代码：

```java
public class VarDemo
{
    public static void main(String args[])
    {
        String s = "hollis";
        ArrayList arraylist = new ArrayList();
        arraylist.add("hollis");
        arraylist.add("chuang");
        arraylist.add("weChat:hollis");
        arraylist.add("blog:http://www.hollischuang.com");
        String s1;
        for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); System.out.println(s1))
            s1 = (String)iterator.next();

        for(int i = 0; i < arraylist.size(); i++)
            System.out.println((String)arraylist.get(i));

    }
}
```

这段代码我们就很熟悉了，就是在Java 10之前，没有本地变量类型推断的时候写的代码。代码的对应关系如下：

|            本地变量类型推断写法             |                  正常写法                   |
| :-----------------------------------------: | :-----------------------------------------: |
|           var string = “hollis”;            |          String string = “hollis”;          |
|      var stringList = new ArrayList();      |   ArrayList stringList = new ArrayList();   |
|          for (var s : stringList)           |         for (String s : stringList)         |
| for (var i = 0; i < stringList.size(); i++) | for (int i = 0; i < stringList.size(); i++) |

> `ArrayList arraylist = new ArrayList();` 其实是`ArrayList stringList = new ArrayList();` 解糖后，类型擦除后的写法。
>
> `for(Iterator iterator = arraylist.iterator(); iterator.hasNext(); System.out.println(s1))` 其实是 `for (String s : stringList)` 这种for循环解糖后的写法。

所以，本地变量类型推断，也是Java 10提供给开发者的语法糖。虽然我们在代码中使用var进行了定义，但是对于虚拟机来说他是不认识这个var的，在java文件编译成class文件的过程中，会进行解糖，使用变量真正的类型来替代var（如使用`String string` 来替换 `var string`）。对于虚拟机来说，完全不需要对var做任何兼容性改变，因为他的生命周期在编译阶段就结束了。唯一变化的是编译器在编译过程中需要多增加一个关于var的解糖操作。

感兴趣的同学可以写两段代码，一段使用var，一段不使用var，然后对比下编译后的字节码。你会发现真的是完全一样的。下图是我用diff工具对比的结果。

[![diff](img/diff.png)](http://www.hollischuang.com/wp-content/uploads/2018/03/diff.png)

> 语法糖（Syntactic Sugar），也称糖衣语法，是由英国计算机学家 Peter.J.Landin 发明的一个术语，指在计算机语言中添加的某种语法，这种语法对语言的功能并没有影响，但是更方便程序员使用。简而言之，语法糖让程序更加简洁，有更高的可读性。

### 和JavaScript有啥区别

很多人都知道，在JavaScript中，变量的定义就是使用var来声明的。所以，Java 10的本地变量类型推断刚刚一出来，就有人说了，这不就是抄袭JavaScript的吗？这和JS里面的var不是一样吗？

其实，还真的不一样。

首先，JavaScript 是一种弱类型（或称动态类型）语言，即变量的类型是不确定的。你可以在JavaScript中，使用“4”-3这样的语法，他的的结果是数字1，这里字符串和数字做运算了。不信的话，你打开你浏览器的控制台，试一下：

[![console](img/console.png)](http://www.hollischuang.com/wp-content/uploads/2018/03/console.png)

但是，Java中虽然可以使用var来声明变量，但是它还是一种强类型的语言。通过上面反编译的代码，我们已经知道，var只是Java给开发者提供的语法糖，最终在编译之后还是要将var定义的对象类型定义成编译器推断出来的类型的。

### 到底会不会影响可读性

本地变量类型推断最让人诟病的恐怕就是其可读性了，因为在之前，我们定义变量时候要明确指定他的类型，所以在阅读代码的时候只要看其声明的类型就可以知道他的类型了，但是全都使用var之后，那就惨了。毫无疑问，这会损失一部分可读性的。但是，在代码中使用var声明对象同样也带来了很多的好处，如代码更加简洁等。

一个新东西刚刚出来之前，总会有各种不习惯。现在大家就会觉得这东西太影响我阅读代码的效率。就像淘宝商城刚刚改名叫天猫的时候，大家都觉得，这是个什么鬼名字。现在听习惯了，是不是觉得还挺好的。

如果大家都使用了var来声明变量以后，那么变量的名字就更加重要了。那时候大家就会更注重变量起名的可读性。而且，相信不久，各大IDE就会推出智能显示变量的推断类型功能。所以，从各个方面，都能弥补一些不足。

总之，对于本地变量类型推断这一特性，我是比较积极的拥抱的。

最后，再提出一个问题，供大家思考，本地变量类型推断看上去还是挺好用的，而且，既然Java已经决定在新版本中推出他，那么为什么要限制他的用法呢。现在已知的可以使用var声明变量的几个场景就是初始化局部变量、增强for循环的索引和传统for循环的局部变量定义，还有几个场景是不支持这种用法的，如：

方法的参数 构造函数的参数 方法的返回值类型 对象的成员变量 只是定义定义而不初始化

那么，我的问题是，Java为什么做这些限制，考虑是什么？

# 4. javap命令

[javap命令](https://github.com/haojunsheng/JavaLearning/blob/master/jvmLearning/java-command.md#71-实例)

# 5. Java中的Switch对整型、字符型、字符串型的具体实现细节

Java 7中，switch的参数可以是String类型了，这对我们来说是一个很方便的改进。到目前为止switch支持这样几种数据类型：`byte` `short` `int` `char` `String` 。但是，作为一个程序员我们不仅要知道他有多么好用，还要知道它是如何实现的，witch对整型的支持是怎么实现的呢？对字符型是怎么实现的呢？String类型呢？有一点Java开发经验的人这个时候都会猜测switch对String的支持是使用equals()方法和hashcode()方法。那么到底是不是这两个方法呢？接下来我们就看一下，switch到底是如何实现的。

## 5.1 switch对整型支持的实现

下面是一段很简单的Java代码，定义一个int型变量a，然后使用switch语句进行判断。执行这段代码输出内容为5，那么我们将下面这段代码反编译，看看他到底是怎么实现的。

```java
public class switchDemoInt {
    public static void main(String[] args) {
        int a = 5;
        switch (a) {
        case 1:
            System.out.println(1);
            break;
        case 5:
            System.out.println(5);
            break;
        default:
            break;
        }
    }
}
//output 5
```

反编译后的代码如下：

```java
public class switchDemoInt
{
    public switchDemoInt()
    {
    }
    public static void main(String args[])
    {
        int a = 5;
        switch(a)
        {
        case 1: // '\001'
            System.out.println(1);
            break;

        case 5: // '\005'
            System.out.println(5);
            break;
        }
    }
}
```

我们发现，反编译后的代码和之前的代码比较除了多了两行注释以外没有任何区别，那么我们就知道，**switch对int的判断是直接比较整数的值**。

## 5.2 switch对字符型支持的实现

直接上代码：

```
public class switchDemoInt {
    public static void main(String[] args) {
        char a = 'b';
        switch (a) {
        case 'a':
            System.out.println('a');
            break;
        case 'b':
            System.out.println('b');
            break;
        default:
            break;
        }
    }
}
```

编译后的代码如下： `public class switchDemoChar

```
public class switchDemoChar
{
    public switchDemoChar()
    {
    }
    public static void main(String args[])
    {
        char a = 'b';
        switch(a)
        {
        case 97: // 'a'
            System.out.println('a');
            break;
        case 98: // 'b'
            System.out.println('b');
            break;
        }
  }
}
```

通过以上的代码作比较我们发现：对char类型进行比较的时候，实际上比较的是ascii码，编译器会把char型变量转换成对应的int型变量

## 5.3 switch对字符串支持的实现

还是先上代码：

```
public class switchDemoString {
    public static void main(String[] args) {
        String str = "world";
        switch (str) {
        case "hello":
            System.out.println("hello");
            break;
        case "world":
            System.out.println("world");
            break;
        default:
            break;
        }
    }
}
```

对代码进行反编译：

```
public class switchDemoString
{
    public switchDemoString()
    {
    }
    public static void main(String args[])
    {
        String str = "world";
        String s;
        switch((s = str).hashCode())
        {
        default:
            break;
        case 99162322:
            if(s.equals("hello"))
                System.out.println("hello");
            break;
        case 113318802:
            if(s.equals("world"))
                System.out.println("world");
            break;
        }
    }
}
```

看到这个代码，你知道原来字符串的switch是通过`equals()`和`hashCode()`方法来实现的。**记住，switch中只能使用整型**，比如`byte`。`short`，`char`(ackii码是整型)以及`int`。还好`hashCode()`方法返回的是`int`，而不是`long`。通过这个很容易记住`hashCode`返回的是`int`这个事实。仔细看下可以发现，进行`switch`的实际是哈希值，然后通过使用equals方法比较进行安全检查，这个检查是必要的，因为哈希可能会发生碰撞。因此它的性能是不如使用枚举进行switch或者使用纯整数常量，但这也不是很差。因为Java编译器只增加了一个`equals`方法，如果你比较的是字符串字面量的话会非常快，比如”abc” ==”abc”。如果你把`hashCode()`方法的调用也考虑进来了，那么还会再多一次的调用开销，因为字符串一旦创建了，它就会把哈希值缓存起来。因此如果这个`siwtch`语句是用在一个循环里的，比如逐项处理某个值，或者游戏引擎循环地渲染屏幕，这里`hashCode()`方法的调用开销其实不会很大。

好，以上就是关于switch对整型、字符型、和字符串型的支持的实现方式，总结一下我们可以发现，**其实swich只支持一种数据类型，那就是整型，其他数据类型都是转换成整型之后在使用switch的。**