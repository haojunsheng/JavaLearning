[TOC]

# 前言

[上一篇](http://www.hollischuang.com/archives/1691)中介绍了一种行为型设计模式——策略模式，这篇文章中介绍另外一个比较简单的行为型设计模式——迭代器模式。

## 概念

一提到迭代器模式很多人可能感觉很陌生，但是实际上，迭代器模式是所有设计模式中最简单也是最常用的设计模式，正是因为他太常用了，所以很多人忽略了他的存在。

> 迭代器模式提供一种方法访问一个容器中各个元素，而又不需要暴露该对象的内部细节。

那么，这里提到的容器是什么呢？其实就是可以包含一组对象的数据结构，如Java中的`Collection`和`Set`。

## 用途

从迭代器模式的概念中我们也看的出来，迭代器模式的重要用途就是帮助我们遍历容器。拿List来举例。如果我们想要遍历他的话，通常有以下几种方式：

```
    for (int i = 0; i < list.size(); i++) {
        System.out.print(list.get(i) + ",");
    }

    Iterator iterator = list.iterator();
    while (iterator.hasNext()) {
        System.out.print(iterator.next() + ",");
    }

    for (Integer i : list) {
        System.out.print(i + ",");
    }
```

其实，第二种和[第三种](http://www.hollischuang.com/archives/1776)都是基于迭代器模式实现的。本文重点是介绍迭代器模式，那么先暂不介绍Java中内置的迭代器，我们尝试自己实现一下迭代器模式，这样更有助于我们彻底理解迭代器模式。

## 实现方式

迭代器模式包含如下角色：

> Iterator 抽象迭代器
>
> ConcreteIterator 具体迭代器
>
> Aggregate 抽象容器
>
> Concrete Aggregate 具体容器

[![iterator](http://www.hollischuang.com/wp-content/uploads/2017/02/iterator.jpg)](http://www.hollischuang.com/wp-content/uploads/2017/02/iterator.jpg)

这里我们举一个菜单的例子，我们有一个菜单，我们想要展示出菜单中所有菜品的名字和报价信息等。

先定义抽象迭代器：

```
public interface Iterator<E> {

    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
```

这里的迭代器提供了三个方法，分别包括hasNext方法、next方法和remove方法。

> hasNext 返回该迭代器中是否还有未遍历过的元素
>
> next 返回迭代器中的下一个元素

在定义一个具体的迭代器：

```
public class MenuIterator implements Iterator {

    String[] foods;
    int      position = 0;

    public MenuIterator(String[] foods){
        this.foods = foods;
    }

    @Override
    public boolean hasNext() {

        return position != foods.length;
    }

    @Override
    public Object next() {
        String food = foods[position];
        position += 1;
        return food;
    }
}
```

这个具体的类实现了Iterator接口，并实现了其中的方法。具体实现就不详细写了，相信都能看得懂（请忽略线程安全问题）。

接下来定义一个抽象容器：

```
/**
 * Created by hollis on 17/2/18.
 * /
public interface Menu {

    void add(String name);

    Iterator getIterator();
}
```

这里定义一个菜单接口，只提供两个方法，一个add方法和一个getIterator方法，用于返回一个迭代器。

然后定义一个具体的容器，用于实现Menu接口：

```
public class ChineseFoodMenu implements Menu {

    private String[] foods    = new String[4];
    private int      position = 0;

    @Override
    public void add(String name) {
        foods[position] = name;
        position += 1;
    }

    @Override
    public Iterator getIterator() {
        return new MenuIterator(this.foods);
    }
}
```

该类的实现也相对简单。至此，我们已经具备了一个迭代器模式需要的所有角色。接下来写一个测试类看看具体使用：

```
public class Main {

    public static void main(String[] args) {
        ChineseFoodMenu chineseFoodMenu = new ChineseFoodMenu();
        chineseFoodMenu.add("宫保鸡丁");
        chineseFoodMenu.add("孜然羊肉");
        chineseFoodMenu.add("水煮鱼");
        chineseFoodMenu.add("北京烤鸭");

        Iterator iterator = chineseFoodMenu.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
//output:
//宫保鸡丁
//孜然羊肉
//水煮鱼
//北京烤鸭
```

我们通过迭代器的方式实现了对一个容器（Menu）的遍历。迭代器的好处就是我们在Main类中使用Menu的时候根本不知道他底层的实现，只需要通过迭代器来遍历就可以了。

## 总结

迭代器的使用现在非常广泛，因为Java中提供了java.util.Iterator。而且Java中的很多容器（Collection、Set）也都提供了对迭代器的支持。

迭代器甚至可以从23种设计模式中移除，因为他已经普遍的可以称之为工具了。

最后最后，迭代器模式很好用，本文中介绍了如何写迭代器模式，但是，如果你要做Java开发，请直接用Java提供的Iterator。

文中所有代码见[GitHub](https://github.com/hollischuang/DesignPattern)

# [JDK中的迭代器模式](https://www.hollischuang.com/archives/1771)

上一篇介绍了[迭代器模式](http://www.hollischuang.com/archives/1765)，而且我们也提到，迭代器模式在JAVA的很多集合类中应用广泛，本文就来看看JAVA源码中是如何使用迭代器模式的。

以下这段代码是JAVA中比较常见的，使用迭代器来遍历List：

```
List<String> list = new ArrayList<>();
        Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
```

在上一篇文章中，我们介绍过迭代器模式中包括的几个角色：

> Iterator 抽象迭代器
>
> ConcreteIterator 具体迭代器
>
> Aggregate 抽象容器
>
> Concrete Aggregate 具体容器

我们看看`ArrayList`在使用迭代器的时候是否也包含这些角色。

首先，具体的容器就不用说了，`ArrayList`类本身就是这个具体的容器类。我们看得到，`ArrayList`中包含一个`iterator`方法，该方法用来返回一个具体的迭代器。

在介绍这个具体的迭代器的实现之前，我们先来找找这个迭代器模式的实现中是否包含抽象迭代器和抽象容器。

抽象容器比较好找，只要我们找到这个`iterator`方法在哪个接口中定义的就可以了。这里直接给出答案：`java.util.Iterable`。

抽象迭代器就是`java.util.Iterator`

当我们在使用JAVA开发的时候，想使用迭代器模式的话，只要让我们自己定义的容器类实现`java.util.Iterable`并实现其中的iterator方法使其返回一个`java.util.Iterator`的实现类就可以了。

我们接下来看一下`ArrayList`中的`iterator`方法是如何实现的，直接贴源码(jdk1.8.0_73)：

```
public Iterator<E> iterator() {
    return new Itr();
}

private class Itr implements Iterator<E> {
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such
    int expectedModCount = modCount;

    public boolean hasNext() {
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        checkForComodification();
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int size = ArrayList.this.size;
        int i = cursor;
        if (i >= size) {
            return;
        }
        final Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length) {
            throw new ConcurrentModificationException();
        }
        while (i != size && modCount == expectedModCount) {
            consumer.accept((E) elementData[i++]);
        }
        // update once at end of iteration to reduce heap write traffic
        cursor = i;
        lastRet = i - 1;
        checkForComodification();
    }

    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

上面的代码比较简单，应该都可以看得懂，这里的实现很容易理解，就是`iterator`方法中`new`了一个`Iterator`的实现类并反回了。`Itr`是一个内部类，他实现了`Iterator`接口并实现了其中的方法。

至此，看完了JAVA中`ArrayList`对迭代器模式的实现。通过迭代器，可以不必关心其内部实现方式。

当我们在使用JAVA开发的时候，想使用迭代器模式的话，只要让我们自己定义的容器类实现`java.util.Iterable`并实现其中的iterator方法使其返回一个`java.util.Iterator`的实现类就可以了。































