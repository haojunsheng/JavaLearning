<!--ts-->
   * [前言](#前言)
   * [1. Comparable](#1-comparable)
   * [2. Comparator](#2-comparator)
   * [3. 如何选择](#3-如何选择)
      * [3.1 使用Comparator创建TreeSet](#31-使用comparator创建treeset)
      * [3.2 使用Comparable创建TreeSet](#32-使用comparable创建treeset)

<!-- Added by: anapodoton, at: Sat Feb 15 21:42:42 CST 2020 -->

<!--te-->

# 前言

`Comparable` 和 `Comparator`是Java核心API提供的两个接口，从它们的名字中，我们大致可以猜到它们用来做对象之间的比较的。但它们到底怎么用，它们之间有又哪些差别呢？下面有两个例子可以很好的回答这个问题。下面的例子用来比较HDTV的大小。看完下面的代码，相信对于如何使用`Comparable`和`Comparator`会有一个更加清晰的认识。

# 1. Comparable

一个实现了`Comparable`接口的类，可以让其自身的对象和其他对象进行比较。也就是说，同一个类的两个对象之间要想比较，对应的类就要实现`Comparable`接口，并实现`compareTo()`方法，代码如下：

```java
class HDTV implements Comparable<HDTV> {
    private int size;
    private String brand;

    public HDTV(int size, String brand) {
        this.size = size;
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public int compareTo(HDTV tv) {

        if (this.getSize() > tv.getSize())
            return 1;
        else if (this.getSize() < tv.getSize())
            return -1;
        else
            return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        HDTV tv1 = new HDTV(55, "Samsung");
        HDTV tv2 = new HDTV(60, "Sony");

        if (tv1.compareTo(tv2) > 0) {
            System.out.println(tv1.getBrand() + " is better.");     
        } else {
            System.out.println(tv2.getBrand() + " is better.");
        }
    }
}
```

输出结果：

```
Sony is better.
```

# 2. Comparator

在一些情况下，你不希望修改一个原有的类，但是你还想让他可以比较，`Comparator`接口可以实现这样的功能。通过使用`Comparator`接口，你可以针对其中特定的属性/字段来进行比较。比如，当我们要比较两个人的时候，我可能通过年龄比较、也可能通过身高比较。这种情况使用`Comparable`就无法实现（因为要实现`Comparable`接口，其中的`compareTo`方法只能有一个，无法实现多种比较）。

通过实现`Comparator`接口同样要重写一个方法：`compare()`。接下来的例子就通过这种方式来比较HDTV的大小。其实`Comparator`通常用于排序。Java中的`Collections`和`Arrays`中都包含排序的`sort`方法，该方法可以接收一个`Comparator`的实例（比较器）来进行排序。

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class HDTV {
    private int size;
    private String brand;

    public HDTV(int size, String brand) {
        this.size = size;
        this.brand = brand;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

class SizeComparator implements Comparator<HDTV> {
    @Override
    public int compare(HDTV tv1, HDTV tv2) {
        int tv1Size = tv1.getSize();
        int tv2Size = tv2.getSize();

        if (tv1Size > tv2Size) {
            return 1;
        } else if (tv1Size < tv2Size) {
            return -1;
        } else {
            return 0;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        HDTV tv1 = new HDTV(55, "Samsung");
        HDTV tv2 = new HDTV(60, "Sony");
        HDTV tv3 = new HDTV(42, "Panasonic");

        ArrayList<HDTV> al = new ArrayList<HDTV>();
        al.add(tv1);
        al.add(tv2);
        al.add(tv3);

        Collections.sort(al, new SizeComparator());
        for (HDTV a : al) {
            System.out.println(a.getBrand());
        }
    }
}
```

输出结果：

```
Panasonic
Samsung
Sony
```

以上代码就实现了通过自定义一个比较器（`Comparator`）来实现对一个列表进行排序。

我们也经常会使用`Collections.reverseOrder()`来获取一个倒序的`Comparator`。例如：

```java
ArrayList<Integer> al = new ArrayList<Integer>();
al.add(3);
al.add(1);
al.add(2);
System.out.println(al);
Collections.sort(al);
System.out.println(al);

Comparator<Integer> comparator = Collections.reverseOrder();
Collections.sort(al,comparator);
System.out.println(al);
```

输出结果：

```
[3,1,2]
[1,2,3]
[3,2,1]
```

# 3. 如何选择

简单来说，一个类如果实现`Comparable`接口，那么他就具有了可比较性，意思就是说它的实例之间相互直接可以进行比较。

通常在两种情况下会定义一个实现`Comparator`类。

> 1、如上面的例子一样，可以把一个`Comparator`的子类传递给`Collections.sort()`、`Arrays.sort()`等方法，用于自定义排序规则。
>
> 2、用于初始化特定的数据结构。常见的有可排序的Set（`TreeSet`）和可排序的Map（`TreeMap`）

下面通过这两种方式分别创建`TreeSet`。

## 3.1 使用`Comparator`创建`TreeSet`

```java
class Dog {
    int size;

    Dog(int s) {
        size = s;
    }
}

class SizeComparator implements Comparator<Dog> {
    @Override
    public int compare(Dog d1, Dog d2) {
        return d1.size - d2.size;
    }
}

public class ImpComparable {
    public static void main(String[] args) {
        TreeSet<Dog> d = new TreeSet<Dog>(new SizeComparator()); // pass comparator
        d.add(new Dog(1));
        d.add(new Dog(2));
        d.add(new Dog(1));
    }
}
```

> 这里使用的就是`Comparator`的第二种用法，定义一个`Comparator`的子类，重写`compare`方法。然后在定义`HashSet`的时候，把这个类的实例传递给其构造函数。这样，再使用`add`方法向`HashSet`中增加元素的时候，就会按照刚刚定义的那个比较器的逻辑进行排序。

## 3.2 使用`Comparable`创建`TreeSet`

```java
class Dog implements Comparable<Dog>{
    int size;

    Dog(int s) {
        size = s;
    }

    @Override
    public int compareTo(Dog o) {
        return o.size - this.size;
    }
}

public class ImpComparable {
    public static void main(String[] args) {
        TreeSet<Dog> d = new TreeSet<Dog>();
        d.add(new Dog(1));
        d.add(new Dog(2));
        d.add(new Dog(1));
    }
}
```

> 这里，定义`TreeSet`的时候并没有传入一个比较器。但是使用`add`方法向`HashSet`中增加的对象是一个实现了`Comparable`的类的实例。所以，也能实现排序功能。
