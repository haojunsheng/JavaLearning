[TOC]

# 前言

所有Java类的父类——`java.lang.Object`中定义了两个重要的方法：

```java
public boolean equals(Object obj)
public int hashCode()
```

本文首先会给出一个错误使用这两个方法的例子，然后再解释equals和hashcode是如何协同工作的。

# 一个常犯的错误

先看以下代码：

```java
import java.util.HashMap;

public class Apple {
    private String color;

    public Apple(String color) {
        this.color = color;
    }

    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (!(obj instanceof Apple))
            return false;   
        if (obj == this)
            return true;
        return this.color.equals(((Apple) obj).color);
    }

    public static void main(String[] args) {
        Apple a1 = new Apple("green");
        Apple a2 = new Apple("red");

        //hashMap stores apple type and its quantity
        HashMap<Apple, Integer> m = new HashMap<Apple, Integer>();
        m.put(a1, 10);
        m.put(a2, 20);
        System.out.println(m.get(new Apple("green")));
    }
}
```

上面的代码执行过程中，先是创建个两个Apple，一个green apple和一个red apple，然后将这来两个apple存储在map中，存储之后再试图通过map的get方法获取到其中green apple的实例。读者可以试着执行以上代码，数据结果为null。也就是说刚刚通过put方法放到map中的green apple并没有通过get方法获取到。你可能怀疑是不是green apple并没有被成功的保存到map中，但是，通过debug工具可以看到，它已经被保存成功了。

# hashcode()惹的祸

造成以上问题的原因其实比较简单，是因为代码中并没有重写`hashcode`方法。`hashcode`和`equals`的约定关系如下：

> 1、如果两个对象相等，那么他们一定有相同的哈希值（hash code）。
>
> 2、如果两个对象的哈希值相等，那么这两个对象有可能相等也有可能不相等。（需要再通过equals来判断）

如果你了解Map的工作原理，那么你一定知道，它是通过把key值进行hash来定位对象的，这样可以提供比线性存储更好的性能。实际上，Map的底层数据结构就是一个数组的数组（准确的说其实是一个链表+数组）。第一个数组的索引值是key的哈希码。通过这个索引可以定位到第二个数组，第二个数组通过使用equals方法进行线性搜索的方式来查找对象。([HashMap完全解读](http://www.hollischuang.com/archives/82))

![image](https://ws3.sinaimg.cn/large/006tNc79gy1fzq0nxam2tj30sg0hamyg.jpg)

其实，一个哈希码可以映射到一个桶（bucket）中，`hashcode`的作用就是先确定对象是属于哪个桶的。如果多个对象有相同的哈希值，那么他们可以放在同一个桶中。如果有不同的哈希值，则需要放在不同的桶中。至于同一个桶中的各个对象之前如何区分就需要使用`equals`方法了。

hashcode方法的默认实现会为每个对象返回一个不同的int类型的值。所以，上面的代码中，第二个apple被创建出来时他将具有不同的哈希值。可以通过重写hashCode方法来解决。

```java
public int hashCode(){
    return this.color.hashCode();   
}
```

# 总结

在判断两个对象是否相等时，不要只使用equals方法判断。还要考虑其哈希码是否相等。尤其是和hashMap等与hash相关的数据结构一起使用时。