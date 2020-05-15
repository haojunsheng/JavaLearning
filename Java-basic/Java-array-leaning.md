<!--ts-->
   * [1.什么是数组](#1什么是数组)
   * [2. 数组的使用方法](#2-数组的使用方法)
   * [3. 性能？请优先考虑数组](#3-性能请优先考虑数组)
   * [4. 变长数组？](#4-变长数组)
   * [5. 数组复制问题](#5-数组复制问题)
   * [6. 数组转换为List注意地方](#6-数组转换为list注意地方)
   * [7. java中的数组是对象吗？](#7-java中的数组是对象吗)
      * [7.1 前言](#71-前言)
      * [7.2 Java中数组的类型](#72-java中数组的类型)
      * [7.3 Java中数组的继承关系](#73-java中数组的继承关系)
      * [7.4 Java中数组的另一种“继承”关系](#74-java中数组的另一种继承关系)

<!-- Added by: anapodoton, at: Tue Mar  3 13:20:31 CST 2020 -->

<!--te-->

# 1.什么是数组

数组？什么是数组？在我印象中的数组是应该这样的：通过new关键字创建并组装他们，通过使用整形索引值访问它的元素，并且它的尺寸是不可变的！

但是这只是数组的最表面的东西！深一点？就是这样：数组是一个简单的复合数据类型，它是一系列有序数据的集合，它当中的每一个数据都具有相同的数据类型，我们通过数组名加上一个不会越界下标值来唯一确定数组中的元素。

还有更深的，那就是数组是一个特殊的对象！！

不管在其他语言中数组是什么，在java中它就是对象。一个比较特殊的对象。

```java
public class Test {
    public static void main(String[] args) {
        int[] array = new int[10];
        System.out.println("array的父类是：" + array.getClass().getSuperclass());
        System.out.println("array的类名是：" + array.getClass().getName());
    }
}
-------Output:
array的父类是：class java.lang.Object
array的类名是：[I
```

从上面示例可以看出,数组的是Object的直接子类,它属于“第一类对象”，但是它又与普通的java对象存在很大的不同，从它的类名就可以看出：[I，这是什么东东？？在JDK中我就没有找到这个类，话说这个”[I”都不是一个合法标识符。怎么定义成类啊？所以我认为SUN那帮天才肯定对数组的底层肯定做了特殊的处理。

我们再看如下示例：

```java
public class Test {
    public static void main(String[] args) {
        int[] array_00 = new int[10];
        System.out.println("一维数组：" + array_00.getClass().getName());
        int[][] array_01 = new int[10][10];
        System.out.println("二维数组：" + array_01.getClass().getName());

        int[][][] array_02 = new int[10][10][10];
        System.out.println("三维数组：" + array_02.getClass().getName());
    }
}
-----------------Output:
一维数组：[I
二维数组：[[I
三维数组：[[[I
```

通过这个实例我们知道：[代表了数组的维度，一个[表示一维，两个[表示二维。可以简单的说数组的类名由若干个'[‘和数组元素类型的内部名称组成。不清楚我们再看：

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("Object[]:" + Object[].class);
        System.out.println("Object[][]:" + Object[][].class);
        System.err.println("Object[][][]:" + Object[][][].class);
        System.out.println("Object:" + Object.class);
    }
}
---------Output:
Object[]:class [Ljava.lang.Object;
Object[][]:class [[Ljava.lang.Object;
Object[][][]:class [[[Ljava.lang.Object;
Object:class java.lang.Object
```

从这个实例我们可以看出数组的“庐山真面目”。同时也可以看出数组和普通的Java类是不同的，普通的java类是以全限定路径名+类名来作为自己的唯一标示的，而数组则是以若干个[+L+数组元素类全限定路径+类来最为唯一标示的。这个不同也许在某种程度上说明了数组也普通java类在实现上存在很大的区别，也许可以利用这个区别来使得JVM在处理数组和普通java类时作出区分。

我们暂且不论这个[I是什么东东，是由谁来声明的，怎么声明的（这些我现在也不知道！但是有一点可以确认：这个是在运行时确定的）。先看如下：

```
public class Test {
    public static void main(String[] args) {
        int[] array = new int[10];
        Class clazz = array.getClass();   
        System.out.println(clazz.getDeclaredFields().length);   
        System.out.println(clazz.getDeclaredMethods().length);   
        System.out.println(clazz.getDeclaredConstructors().length);   
        System.out.println(clazz.getDeclaredAnnotations().length);   
        System.out.println(clazz.getDeclaredClasses().length);   
    }
}
----------------Output：
0
0
0
0
0
```

从这个运行结果可以看出，我们亲爱的[I没有生命任何成员变量、成员方法、构造函数、Annotation甚至连length成员变量这个都没有，它就是一个彻彻底底的空类。没有声明length，那么我们array.length时，编译器怎么不会报错呢？确实，数组的length是一个非常特殊的成员变量。我们知道数组的是Object的直接之类，但是Object是没有length这个成员变量的，那么length应该是数组的成员变量，但是从上面的示例中，我们发现数组根本就没有任何成员变量，这两者不是相互矛盾么？

```
public class Main {
    public static void main(String[] args) {
        int a[] = new int[2];
        int i = a.length;
    }
}
```

打开class文件，得到main方法的字节码：

```
0 iconst_2                   //将int型常量2压入操作数栈  
    1 newarray 10 (int)          //将2弹出操作数栈，作为长度，创建一个元素类型为int, 维度为1的数组，并将数组的引用压入操作数栈  
    3 astore_1                   //将数组的引用从操作数栈中弹出，保存在索引为1的局部变量(即a)中  
    4 aload_1                    //将索引为1的局部变量(即a)压入操作数栈  
    5 arraylength                //从操作数栈弹出数组引用(即a)，并获取其长度(JVM负责实现如何获取)，并将长度压入操作数栈  
    6 istore_2                   //将数组长度从操作数栈弹出，保存在索引为2的局部变量(即i)中  
    7 return                     //main方法返回
```

在这个字节码中我们还是没有看到length这个成员变量，但是看到了这个:arraylength ,这条指令是用来获取数组的长度的，所以说JVM对数组的长度做了特殊的处理，它是通过arraylength这条指令来实现的。

# 2. 数组的使用方法

通过上面算是对数组是什么有了一个初步的认识，下面将简单介绍数组的使用方法。

数组的使用方法无非就是四个步骤：声明数组、分配空间、赋值、处理。

声明数组：就是告诉计算机数组的类型是什么。有两种形式：int[] array、int array[]。

分配空间：告诉计算机需要给该数组分配多少连续的空间，记住是连续的。array = new int[10];

赋值：赋值就是在已经分配的空间里面放入数据。array[0] = 1 、array[1] = 2……其实分配空间和赋值是一起进行的，也就是完成数组的初始化。有如下三种形式：

```
int a[] = new int[2];    //默认为0,如果是引用数据类型就为null
int b[] = new int[] {1,2,3,4,5};    
int c[] = {1,2,3,4,5};
```

处理：就是对数组元素进行操作。通过数组名+有效的下标来确认数据。

# 3. 性能？请优先考虑数组

在java中有很多方式来存储一系列数据，而且在操作上面比数组方便的多？但为什么我们还需要使用数组，而不是替代它呢？数组与其他种类的容器之间的区别有三个方面：**效率、类型和保存基本类型**的能力。在java中，数组是一种效率最高的存储和随机访问对象引用序列的方式。

在项目设计中数组使用的越来越少了，而且它确实是没有List、Set这些集合使用方便，但是在某些方面数组还是存在一些优势的，例如：速度，而且集合类的底层也都是通过数组来实现的。

```java
--------这是ArrayList的add()------
    public boolean add(E e) {
    ensureCapacity(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
    }
```

下面利用数组和list来做一些操作比较。

一、求和

```java
Long time1 = System.currentTimeMillis();
        for(int i = 0 ; i < 100000000 ;i++){
            sum += arrays[i%10];
        }
        Long time2 = System.currentTimeMillis();
        System.out.println("数组求和所花费时间：" + (time2 - time1) + "毫秒");
        Long time3 = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            sum  += list.get(i%10);
        }
        Long time4 = System.currentTimeMillis();
        System.out.println("List求和所花费时间：" + (time4 - time3) + "毫秒");
--------------Output:
数组求和所花费时间：696毫秒
List求和所花费时间：3498毫秒
```

从上面的时间消耗上面来说数组对于基本类型的求和计算的速度是集合的5倍左右。其实在list集合中，求和当中有一个致命的动作：list.get(i)。这个动作是进行拆箱动作，Integer对象通过intValue方法自动转换成一个int基本类型，在这里就产生了不必要的性能消耗。

**所以在性能要求较高的场景中请优先考虑数组。**

# 4. 变长数组？

数组是定长的，一旦初始化声明后是不可改变长度的。这对我们在实际开发中是非常不方便的，聪明的我们肯定是可以找到方法来实现的。就如java不能实现多重继承一样，我们一样可以利用内部类和接口来实现(请参考：[java提高篇(九)—–实现多重继承](http://www.cnblogs.com/chenssy/p/3389027.html))。

那么如何来实现变长数组呢？我们可以利用List集合add方法里面的扩容思路来模拟实现。下面是ArrayList的扩容方法:

```java
public void ensureCapacity(int minCapacity) {
        modCount++;  
        int oldCapacity = elementData.length;
        /**
         * 若当前需要的长度超过数组长度时进行扩容处理
         */
        if (minCapacity > oldCapacity) {
            Object oldData[] = elementData;    
            int newCapacity = (oldCapacity * 3) / 2 + 1;    //扩容
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            //拷贝数组，生成新的数组
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
```

这段代码对我们有用的地方就在于if语句后面。它的思路是将原始数组拷贝到新数组中，新数组是原始数组长度的1.5倍。所以模拟的数组扩容代码如下：

```java
public class ArrayUtils {
    /**
     * @desc 对数组进行扩容
     * @author chenssy
     * @data 2013-12-8
     * @param <T>
     * @param datas 原始数组
     * @param newLen 扩容大小
     * @return T[]
     */
    public static <T> T[] expandCapacity(T[] datas,int newLen){
        newLen = newLen < 0 ? datas.length :datas.length + newLen;   
        //生成一个新的数组
        return Arrays.copyOf(datas, newLen);
    }

    /**
     * @desc 对数组进行扩容处理，1.5倍
     * @author chenssy
     * @data 2013-12-8
     * @param <T>
     * @param datas  原始数组
     * @return T[]
     */
    public static <T> T[] expandCapacity(T[] datas){
        int newLen = (datas.length * 3) / 2;      //扩容原始数组的1.5倍
        //生成一个新的数组
        return Arrays.copyOf(datas, newLen);
    }

    /**
     * @desc 对数组进行扩容处理，
     * @author chenssy
     * @data 2013-12-8
     * @param <T>
     * @param datas 原始数组
     * @param mulitiple 扩容的倍数
     * @return T[]
     */
    public static <T> T[] expandCapacityMul(T[] datas,int mulitiple){
        mulitiple = mulitiple < 0 ? 1 : mulitiple;
        int newLen = datas.length * mulitiple;
        return Arrays.copyOf(datas,newLen );
    }
}
```

通过这种迂回的方式我们可以实现数组的扩容。因此在项目中如果确实需要变长的数据集，数组也是在考虑范围之内的，我们不能因为他是固定长度而排斥他！

# 5. 数组复制问题

以前在做集合拷贝的时候由于集合没有拷贝的方法，所以一个一个的复制是非常麻烦的，所以我就干脆使用List.toArray()方法转换成数组然后再通过Arrays.copyOf拷贝，在转换成集合，个人觉得非常方便，殊不知我已经陷入了其中的陷进！我们知道若数组元素为对象，则数组里面数据是对象引用

```java
public class Test {
    public static void main(String[] args) {
        Person person_01 = new Person("chenssy_01");

        Person[] persons1 = new Person[]{person_01};
        Person[] persons2 = Arrays.copyOf(persons1,persons1.length);

        System.out.println("数组persons1:");
        display(persons1);
        System.out.println("---------------------");
        System.out.println("数组persons2:");
        display(persons2);
        //改变其值
        persons2[0].setName("chessy_02");
        System.out.println("------------改变其值后------------");
        System.out.println("数组persons1:");
        display(persons1);
        System.out.println("---------------------");
        System.out.println("数组persons2:");
        display(persons2);
    }
    public static void display(Person[] persons){
        for(Person person : persons){
            System.out.println(person.toString());
        }
    }
}
-------------Output:
数组persons1:
姓名是：chenssy_01
---------------------
数组persons2:
姓名是：chenssy_01
------------改变其值后------------
数组persons1:
姓名是：chessy_02
---------------------
数组persons2:
姓名是：chessy_02
```

从结果中发现,persons1中的值也发生了改变，这是典型的浅拷贝问题。所以通过Arrays.copyOf()方法产生的数组是一个浅拷贝。同时数组的clone()方法也是，集合的clone()方法也是，所以我们在使用拷贝方法的同时一定要注意浅拷贝这问题。

有关于深浅拷贝的博文，参考：

- 渐析java的浅拷贝和深拷贝：<http://www.cnblogs.com/chenssy/p/3308489.html>。
- 使用序列化实现对象的拷贝：<http://www.cnblogs.com/chenssy/p/3382979.html>。

# 6. 数组转换为List注意地方

我们经常需要使用到Arrays这个工具的asList()方法将其转换成列表。方便是方便，但是有时候会出现莫名其妙的问题。如下：

```java
public static void main(String[] args) {
        int[] datas = new int[]{1,2,3,4,5};
        List list = Arrays.asList(datas);
        System.out.println(list.size());
    }
------------Output:
1
```

结果是1,是的你没有看错, 结果就是1。但是为什么会是1而不是5呢？先看asList()的源码

```
 public static <T> List<T> asList(T... a) {
        return new ArrayList<T>(a);
    }
```

注意这个参数:T…a，这个参数是一个泛型的变长参数，我们知道基本数据类型是不可能泛型化的，也是就说8个基本数据类型是不可作为泛型参数的，但是为什么编译器没有报错呢？这是因为在java中，数组会当做一个对象来处理，它是可以泛型的，所以我们的程序是把一个int型的数组作为了T的类型，所以在转换之后List中就只会存在一个类型为int数组的元素了。所以我们这样的程序System.out.println(datas.equals(list.get(0)));输出结果肯定是true。当然如果将int改为Integer，则长度就会变成5了。

我们在看下面程序：

```java
enum Week{Sum,Mon,Tue,Web,Thu,Fri,Sat}
    public static void main(String[] args) {
        Week[] weeks = {Week.Sum,Week.Mon,Week.Tue,Week.Web,Week.Thu,Week.Fri};
        List<Week> list = Arrays.asList(weeks);
        list.add(Week.Sat);
    }
```

这个程序非常简单，就是讲一个数组转换成list，然后改变集合中值，但是运行呢？

```
 Exception in thread "main" java.lang.UnsupportedOperationException
    at java.util.AbstractList.add(AbstractList.java:131)
    at java.util.AbstractList.add(AbstractList.java:91)
    at com.array.Test.main(Test.java:18)
```

编译没错，但是运行竟然出现了异常错误！UnsupportedOperationException ，当不支持请求的操作时，就会抛出该异常。从某种程度上来说就是不支持add方法，我们知道这是不可能的！什么原因引起这个异常呢？先看asList()的源代码：

```
 public static <T> List<T> asList(T... a) {
        return new ArrayList<T>(a);
    }
```

这里是直接返回一个ArrayList对象返回，但是注意这个ArrayList并不是java.util.ArrayList,而是Arrays工具类的一个内之类：

```
private static class ArrayList<E> extends AbstractList<E>
    implements RandomAccess, java.io.Serializable{
        private static final long serialVersionUID = -2764017481108945198L;
        private final E[] a;
        ArrayList(E[] array) {
            if (array==null)
                throw new NullPointerException();
        a = array;
    }
       /** 省略方法 **/
    }
```

但是这个内部类并没有提供add()方法，那么查看父类：

```
public boolean add(E e) {
    add(size(), e);
    return true;
    }
    public void add(int index, E element) {
    throw new UnsupportedOperationException();
    }
```

这里父类仅仅只是提供了方法，方法的具体实现却没有，所以具体的实现需要子类自己来提供，但是非常遗憾

这个内部类ArrayList并没有提高add的实现方法。在ArrayList中，它主要提供了如下几个方法：

- 1、size：元素数量
- 2、toArray：转换为数组，实现了数组的浅拷贝。
- 3、get：获得指定元素。
- 4、contains：是否包含某元素。

所以综上所述，asList返回的是一个长度不可变的列表。数组是多长，转换成的列表是多长，我们是无法通过add、remove来增加或者减少其长度的。

# 7. java中的数组是对象吗？

## 7.1 前言

Java和C++都是面向对象的语言。在使用这些语言的时候，我们可以直接使用标准的类库，也可以使用组合和继承等面向对象的特性构建自己的类，并且根据自己构建的类创建对象。那么，我们是不是应该考虑这样一个问题：在面向对象的语言中，数组是对象吗？



要判断数组是不是对象，那么首先明确什么是对象，也就是对象的定义。在较高的层面上，对象是根据某个类创建出来的一个实例，表示某类事物中一个具体的个体。对象具有各种属性，并且具有一些特定的行为。而在较低的层面上，站在计算机的角度，对象就是内存中的一个内存块，在这个内存块封装了一些数据，也就是类中定义的各个属性，所以，对象是用来封装数据的。以下为一个Person对象在内存中的表示：

![](https://ws3.sinaimg.cn/large/006tNc79gy1fzowl7wwwgj30fc0893zc.jpg)

注意：

1）小的红色矩形表示一个引用（地址）或一个基本类型的数据，大的红色矩形表示一个对象，多个小的红色矩形组合在一块，可组成一个对象。

2）name在对象中只表示一个引用， 也就是一个地址值，它指向一个真实存在的字符串对象。在这里严格区分了引用和对象。



那么在Java中，数组满足以上的条件吗？在较高的层面上，数组不是某类事物中的一个具体的个体，而是多个个体的集合。那么它应该不是对象。而在计算机的角度，数组也是一个内存块，也封装了一些数据，这样的话也可以称之为对象。以下是一个数组在内存中的表示：

![](https://ws4.sinaimg.cn/large/006tNc79gy1fzowm1asc0j30cz09jwf5.jpg)

这样的话， 数组既可以是对象， 也可以不是对象。至于到底是不是把数组当做对象，全凭Java的设计者决定。数组到底是不是对象， 通过代码验证：


```java
	int[] a = new int[4];
	//a.length;  //对属性的引用不能当成语句
	int len = a.length;  //数组中保存一个字段, 表示数组的长度
	
	//以下方法说明数组可以调用方法,java中的数组是对象.这些方法是Object中的方法,所以可以肯定,数组的最顶层父类也是Object
	a.clone();
	a.toString();
```

在数组a上， 可以访问他的属性，也可以调用一些方法。这基本上可以认定，java中的数组也是对象，它具有java中其他对象的一些基本特点：封装了一些数据，可以访问属性，也可以调用方法。所以，数组是对象。



而在C++中，数组虽然封装了数据，但数组名只是一个指针，指向数组中的首个元素，既没有属性，也没有方法可以调用。如下代码所示：

```java
int main(){
	int a[] = {1, 2, 3, 4};
	int* pa = a;
	//无法访问属性，也不能调用方法。
	return 0;
}
```

所以C++中的数组不是对象，只是一个数据的集合，而不能当做对象来使用。

## 7.2 Java中数组的类型

Java是一种强类型的语言。既然是对象， 那么就必须属于一个类型，比如根据Person类创建一个对象，这个对象的类型就是Person。那么数组的类型是什么呢？看下面的代码：

```java
	int[] a1 = {1, 2, 3, 4};
		System.out.println(a1.getClass().getName());
		//打印出的数组类的名字为[I
	String[] s = new String[2];
	System.out.println(s.getClass().getName());
	//打印出的数组类的名字为  [Ljava.lang.String;
	
	String[][] ss = new String[2][3];
	System.out.println(ss.getClass().getName());
	//打印出的数组类的名字为    [[Ljava.lang.String;
```

打印出a1的类型为[ I ，s 的类型是[Ljava.lang.String;  ,  ss的类型是[[Ljava.lang.String;  

所以，数组也是有类型的。只是这个类型显得比较奇怪。你可以说a1的类型是int[]，这也无可厚非。但是我们没有自己创建这个类，也没有在Java的标准库中找到这个类。也就是说不管是我们自己的代码，还是在JDK中，都没有如下定义：	

```java
	public class int[] {
	// ...
	
	// ...
	
	// ...
}
```

这只能有一个解释，那就是虚拟机自动创建了数组类型，可以把数组类型和8种基本数据类型一样， 当做java的内建类型。这种类型的命名规则是这样的：

* 每一维度用一个[表示；开头两个[，就代表是二维数组。
* [后面是数组中元素的类型(包括基本数据类型和引用数据类型)

在java语言层面上,s是数组,也是一个对象,那么他的类型应该是String[]，这样说是合理的。但是在JVM中，他的类型为[java.lang.String。顺便说一句普通的类在JVM里的类型为 包名+类名，也就是全限定名。同一个类型在java语言中和在虚拟机中的表示可能是不一样的。

## 7.3 Java中数组的继承关系

上面已经验证了，数组是对象，也就是说可以以操作对象的方式来操作数组。并且数组在虚拟机中有它特别的类型。既然是对象，遵循Java语言中的规则 -- Object是上帝， 也就是说所有类的顶层父类都是Object。数组的顶层父类也必须是Object，这就说明数组对象可以向上直接转型到Object，也可以向下强制类型转换，也可以使用instanceof关键字做类型判定。 这一切都和普通对象一样。如下代码所示：


```java
	//1		在test1()中已经测试得到以下结论: 数组也是对象, 数组的顶层父类是Object, 所以可以向上转型
	int[] a = new int[8];
	Object obj = a ; //数组的父类也是Object,可以将a向上转型到Object
	
	//2		那么能向下转型吗?
	int[] b = (int[])obj;  //可以进行向下转型
	
	//3		能使用instanceof关键字判定吗?
	if(obj instanceof int[]){  //可以用instanceof关键字进行类型判定
		System.out.println("obj的真实类型是int[]");
	}
```

## 7.4 Java中数组的另一种“继承”关系

如下代码是正确的，却很容易让我们疑惑：
	

```java
	String[] s = new String[5];
		Object[] obja = s;   //成立,说明可以用Object[]的引用来接收String[]的对象
```

Object[]类型的引用可以指向String[]类型的数组对象？ 由上文的验证可以得知数组类型的顶层父类一定是Object，那么上面代码中s的直接父类是谁呢？难道说String[]继承自Object[]，而Object[]又继承自Object? 让我们通过反射的方式来验证这个问题：
		

```java
//5		那么String[] 的直接父类是Object[] 还是 Object?
		System.out.println(s.getClass().getSuperclass().getName());
		//打印结果为java.lang.Object,说明String[] 的直接父类是 Object而不是Object[]
```

由代码可知，String[]的直接父类就是Object而不是Object[]。可是Object[]的引用明明可以指向String[]类型的对象。那么他们的继承关系有点像这样：

![](https://ws2.sinaimg.cn/large/006tNc79gy1fzox5ll461j30ak097dg0.jpg)

这样的话就违背了Java单继承的原则。String[]不可能即继承Object，又继承Object[]。上面的类图肯定是错误的。那么只能这样解释：数组类直接继承了Object，关于Object[]类型的引用能够指向String[]类型的对象，这种情况只能是Java语法之中的一个特例，并不是严格意义上的继承。也就是说，String[]不继承自Object[]，但是我可以允许你向上转型到Object[]，这种特性是赋予你的一项特权。

其实这种关系可以这样表述：如果有两个类A和B，如果B继承（extends）了A，那么A[]类型的引用就可以指向B[]类型的对象。如下代码所示：

```java
public static class Father {
}

public static class Son extends Father {
 
}
	//6	  下面成立吗?  Father是Son的直接父类
	Son[] sons = new Son[3];
	Father[] fa = sons;  //成立
	
	//7		那么Son[] 的直接父类是Father[] 还是  Object[] 或者是Object?
	System.out.println(sons.getClass().getSuperclass().getName());
	//打印结果为java.lang.Object,说明Son[]的直接父类是Object
```



**数组的这种用法不能作用于基本类型数据：**
	

```java
	int[] aa = new int[4];
		//Object[] objaa = aa;  //错误的，不能通过编译
```

这是错误的, 因为int不是引用类型，Object不是int的父类，在这里自动装箱不起作用。但是这种方式是可以的：
Object[] objss = {"aaa", 1, 2.5};

这种情况下自动装箱可以工作，也就是说，Object数组中可以存放任何值，包括基本数据类型。


Java为什么会为数组提供这样一种语法特性呢？也就是说这种语法有什么作用？编写过Android中Sqlite数据库操作程序的同学可能发现过这种现象，用一个Object[]引用接收所有的数组对象，在编译SQL语句时，为SQL语句中的占位符提供对应的值。
db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.name, person.age}); 

所以这种特性主要是用于方法中参数的传递。如果不传递数组，而是依次传递各个值，会使方法参数列表变得冗长。如果使用具体的数组类型，如String[]，那么就限定了类型，失去了灵活性。所以传递数组类型是一种比较好的方式。但是如果没有上面的数组特性（如果有两个类A和B，如果B继承（extends）了A，那么A[]类型的引用就可以指向B[]类型的对象），那么数组类型就只能通过Object类型接收，这样就无法在方法内部访问或遍历数组中的各个元素。如下代码：
	

```java
private static void test3() {
		String[] a = new String[3];
		doArray(a);
	}
	
private static void doArray(Object[] objs){
	
}

private static void doArray1(Object obj){
	//不能用Object接收数组，因为这样无法对数组的元素进行访问
	// obj[1]  //错误
	
	//如果在方法内部对obj转型到数组，存在类型转换异常的风险
	// Object[] objs = (Object[]) obj;
}

private static void doArray2(String[] strs){
	//如果适用特定类型的数组，就限制了类型，失去灵活性和通用性
}

private static void doArray3(String name, int age, String id, float account){
	//如果不适用数组而是依次传递参数，会使参数列表变得冗长，难以阅读
}
```

到此为止，数组的特性就总结完了。上文中加粗的部分为重要结论。下面贴出整个源码：

```java
public class ArrayTest {
/**
 * @param args
 */
public static void main(String[] args) {
	test1();
	test2();
	test3();
}
 
/**
 * 数组具有这种特性：
 * 如果有两个类A和B，如果B继承（extends）了A，那么A[]类型的引用就可以指向B[]类型的对象
 * 测试数组的特殊特性对参数传递的便利性
 */
private static void test3() {
	String[] a = new String[3];
	doArray(a);
}
 
private static void doArray(Object[] objs){
	
}

private static void doArray1(Object obj){
	//不能用Object接收数组，因为这样无法对数组的元素进行访问
	// obj[1]  //错误
	
	//如果在方法内部对obj转型到数组，存在类型转换异常的风险
	// Object[] objs = (Object[]) obj;
}

private static void doArray2(String[] strs){
	//如果适用特定类型的数组，就限制了类型，失去灵活性和通用性
}

private static void doArray3(String name, int age, String id, float account){
	//如果不适用数组而是依次传递参数，会使参数列表变得冗长，难以阅读
}
/**
 * 测试数组的集成关系, 并且他的继承关系是否和数组中元素的类型有关
 */
private static void test2() {
	
	//1		在test1()中已经测试得到以下结论: 数组也是对象, 数组的顶层父类是Object, 所以可以向上转型
	int[] a = new int[8];
	Object obj = a ; //数组的父类也是Object,可以将a向上转型到Object
	
	//2		那么能向下转型吗?
	int[] b = (int[])obj;  //可以进行向下转型
	
	//3		能使用instanceof关键字判定吗?
	if(obj instanceof int[]){  //可以用instanceof关键字进行类型判定
		System.out.println("obj的真实类型是int[]");
	}
	
	//4  	下面代码成立吗?
	String[] s = new String[5];
	Object[] obja = s;   //成立,说明可以用Object[]的引用来接收String[]的对象
	
	//5		那么String[] 的直接父类是Object[] 还是 Object?
	System.out.println(s.getClass().getSuperclass().getName());
	//打印结果为java.lang.Object,说明String[] 的直接父类是 Object而不是Object[]
	
	//6	  下面成立吗?  Father是Son的直接父类
	Son[] sons = new Son[3];
	Father[] fa = sons;  //成立
	
	//7		那么Son[] 的直接父类是Father[] 还是  Object[] 或者是Object?
	System.out.println(sons.getClass().getSuperclass().getName());
	//打印结果为java.lang.Object,说明Son[]的直接父类是Object
	
	/**
	 * 做一下总结, 如果A是B的父类, 那么A[] 类型的引用可以指向 B[]类型的变量
	 * 但是B[]的直接父类是Object, 所有数组的父类都是Object
	 */
	
	//8		上面的结论可以扩展到二维数组
	Son[][] sonss = new Son[2][4];
	Father[][] fathers = sonss;
	//将Father[][]数组看成是一维数组, 这是个数组中的元素为Father[]
	//将Son[][]数组看成是一维数组, 这是个数组中的元素为Son[]
	//因为Father[]类型的引用可以指向Son[]类型的对象
	//所以,根据上面的结论,Father[][]的引用可以指向Son[][]类型的对象
	
	/**
	 * 扩展结论:
	 * 因为Object是所有引用类型的父类
	 * 所以Object[]的引用可以指向任何引用数据类型的数组的对象. 如:
	 * Object[] objs = new String[1];
	 * Object[] objs = new Son[1];
	 *
	 */
	
	//9		下面的代码成立吗?
	int[] aa = new int[4];
	//Object[] objaa = aa;  //错误的，不能通过编译
	//这是错误的, 因为Object不是int的父类,在这里自动装箱不起作用
	
	//10 	这样可以吗？
	Object[] objss = {"aaa", 1, 2.5};//成立
}
 
/**
 * 测试在java语言中,数组是不是对象
 * 如果是对象, 那么他的类型是什么?
 */
private static void test1() {
	int[] a = new int[4];
	//a.length;  //对属性的引用不能当成语句
	int len = a.length;  //数组中保存一个字段, 表示数组的长度
	
	//以下方法说明数组可以调用方法,java中的数组是对象.这些方法是Object中的方法,所以可以肯定,数组的最顶层父类也是Object
	a.clone();
	a.toString();
    /**
	 * java是强类型的语言,一个对象总会有一个特定的类型,例如 Person p = new Person();
	 * 对象p(确切的说是引用)的类型是Person类, 这个Person类是我们自己编写的
	 * 那么数组的类型是什么呢? 下面使用反射的方式进行验证
	 */
	int[] a1 = {1, 2, 3, 4};
	System.out.println(a1.getClass().getName());
	//打印出的数组类的名字为[I
	
	String[] s = new String[2];
	System.out.println(s.getClass().getName());
	//打印出的数组类的名字为  [Ljava.lang.String;
	
	String[][] ss = new String[2][3];
	System.out.println(ss.getClass().getName());
	//打印出的数组类的名字为    [[Ljava.lang.String;
	
	/**
	 * 所以,数组也是有类型的,只不过这个类型不是有程序员自己定义的类, 也不是jdk里面
	 * 的类, 而是虚拟机在运行时专门创建的类
	 * 类型的命名规则是:
	 * 		每一维度用一个[表示;
	 * 		[后面是数组中元素的类型(包括基本数据类型和引用数据类型)
	 * 
	 * 在java语言层面上,s是数组,也是一个对象,那么他的类型应该是String[],
	 * 但是在JVM中,他的类型为[java.lang.String
	 * 
	 * 顺便说一句普通的类在JVM里的类型为 包名+类名, 也就是全限定名
	 */
}

public static class Father {
 
}

public static class Son extends Father {
 
}
}	
```
