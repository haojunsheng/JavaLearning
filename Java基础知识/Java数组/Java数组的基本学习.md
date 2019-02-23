[TOC]

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

































