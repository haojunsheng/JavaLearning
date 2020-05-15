<!--ts-->

   * [前言](#前言)
   * [1. ArrayList](#1-arraylist)
      * [1.1 ArrayList概述](#11-arraylist概述)
      * [1.2 ArrayList源码分析](#12-arraylist源码分析)
         * [1.2.1 底层使用数组](#121-底层使用数组)
         * [<strong>1.2.2 构造函数</strong>](#122-构造函数)
         * [<strong>1. 2.3 新增</strong>](#1-23-新增)
         * [<strong>1.2.4 删除</strong>](#124-删除)
         * [<strong>1.2.5 查找</strong>](#125-查找)
         * [<strong>1.2.6 扩容</strong>](#126-扩容)
         * [1.2.7 ArrayList是怎么实现序列化和反序列化的？](#127-arraylist是怎么实现序列化和反序列化的)
   * [2. LinkedList](#2-linkedlist)
      * [2.1 概述](#21-概述)
      * [2.2 源码分析](#22-源码分析)
         * [2.2.1 定义](#221-定义)
         * [2.2.2 属性](#222-属性)
         * [2.2.3 构造方法](#223-构造方法)
         * [2.2.4 增加方法](#224-增加方法)
         * [2.2.5 移除方法](#225-移除方法)
         * [2.2.6 查找方法](#226-查找方法)
   * [3. Vector](#3-vector)
      * [3.1 Vector简介](#31-vector简介)
      * [3.2 源码解析](#32-源码解析)
         * [3.2.1 增加：add(E e)](#321-增加adde-e)
         * [3.2.2 remove(Object o)](#322-removeobject-o)
      * [3.3 Vector遍历](#33-vector遍历)
      * [3.3.1 <strong>随机访问</strong>](#331-随机访问)
         * [3.3.2 迭代器](#332-迭代器)
         * [3.3.4 <strong>Enumeration循环</strong>](#334-enumeration循环)
      * [stack](#stack)
   * [4 总结](#4-总结)
      * [4.1 ArrayList vs LinkedList vs Vector](#41-arraylist-vs-linkedlist-vs-vector)
         * [4.1.1 ArrayList](#411-arraylist)
         * [4.1.2 LinkedList](#412-linkedlist)
         * [4.1.3 Vector](#413-vector)
         * [4.1.4 ArrayList 和 LinkedList的性能对比](#414-arraylist-和-linkedlist的性能对比)
      * [4.2 synchronizedList vs Vector?](#42-synchronizedlist-vs-vector)
         * [4.2.1 add方法](#421-add方法)
         * [4.2.2 remove方法](#422-remove方法)
         * [4.2.3 区别分析](#423-区别分析)
      * [4.3 <strong>通过Array.asList获得的List有何特点，使用时应该注意什么</strong>?](#43-通过arrayaslist获得的list有何特点使用时应该注意什么)
      * [4.4 fail-fast vs fail-safe](#44-fail-fast-vs-fail-safe)
         * [4.4.1 前言](#441-前言)
         * [4.4.2 快速失败](#442-快速失败)
         * [4.4.3 安全失败](#443-安全失败)
         * [4.4.4 Copy-On-Write](#444-copy-on-write)
      * [4.5 <strong>如何在遍历的同时删除ArrayList中的元素</strong>](#45-如何在遍历的同时删除arraylist中的元素)

<!-- Added by: anapodoton, at: Sun Mar  1 13:29:37 CST 2020 -->

<!--te-->

# 前言

List中的元素是有序的、可重复的，主要实现方式有动态数组和链表。

![qrcode](img/List.png)

- **Collection：**Collection 层次结构 中的根接口。它表示一组对象，这些对象也称为 collection 的元素。对于Collection而言，它不提供任何直接的实现，所有的实现全部由它的子类负责。
- **AbstractCollection：** 提供 Collection 接口的骨干实现，以最大限度地减少了实现此接口所需的工作。对于我们而言要实现一个不可修改的 collection，只需扩展此类，并提供 iterator 和 size 方法的实现。但要实现可修改的 collection，就必须另外重写此类的 add 方法（否则，会抛出 UnsupportedOperationException），iterator 方法返回的迭代器还必须另外实现其 remove 方法。
- **Iterator：** 迭代器。
- **ListIterator：** 系列表迭代器，允许程序员按任一方向遍历列表. 迭代期间修改列表，并获得迭代器在列表中的当前位置。
- **List：** 继承于Collection的接口。它代表着有序的队列。
- **AbstractList：** List 接口的骨干实现，以最大限度地减少实现“随机访问”数据存储（如数组）支持的该接口所需的工作。
- **Queue：** 队列。提供队列基本的插入. 获取. 检查操作。
- **Deque：** 一个线性 collection，支持在两端插入和移除元素。大多数 Deque 实现对于它们能够包含的元素数没有固定限制，但此接口既支持有容量限制的双端队列，也支持没有固定大小限制的双端队列。
- **AbstractSequentialList：** 提供了 List 接口的骨干实现，从而最大限度地减少了实现受“连续访问”数据存储（如链接列表）支持的此接口所需的工作。从某种意义上说，此类与在列表的列表迭代器上实现“随机访问”方法。
- **LinkedList：** List 接口的链接列表实现。它实现所有可选的列表操作。
- **ArrayList：** List 接口的大小可变数组的实现。它实现了所有可选列表操作，并允许包括 null 在内的所有元素。除了实现 List 接口外，此类还提供一些方法来操作内部用来存储列表的数组的大小。
- **Vector：** 实现可增长的对象数组。与数组一样，它包含可以使用整数索引进行访问的组件。
- **Stack：** 后进先出（LIFO）的对象堆栈。它通过五个操作对类 Vector 进行了扩展 ，允许将向量视为堆栈。
- **Enumeration：** 枚举，实现了该接口的对象，它生成一系列元素，一次生成一个。连续调用 nextElement 方法将返回一系列的连续元素。

![image-20200228222819038](img/image-20200228222819038.png)

# 1. ArrayList

## 1.1 ArrayList概述

ArrayList是实现List接口的动态数组，所谓动态就是它的大小是可变的。实现了所有可选列表操作，并允许包括 null 在内的所有元素。除了实现 List 接口外，此类还提供一些方法来**操作内部用来存储列表的数组的大小**。

每个ArrayList实例都有一个容量，该容量是指用来存储列表元素的数组的大小。**默认初始容量为10**。随着ArrayList中元素的增加，它的容量也会不断的自动增长。在每次添加新的元素时，ArrayList都会检查是否需要进行扩容操作，**扩容操作带来数据向新数组的重新拷贝**，所以如果我们知道具体业务数据量，在构造ArrayList时可以给ArrayList指定一个初始容量，这样就会减少扩容时数据的拷贝问题。当然在添加大量元素前，应用程序也可以使用ensureCapacity操作来增加ArrayList实例的容量，这可以减少递增式再分配的数量。

**注意，ArrayList实现不是同步的**。如果多个线程同时访问一个ArrayList实例，而其中至少一个线程从结构上修改了列表，那么它必须保持外部同步。所以为了保证同步，最好的办法是在创建时完成，以防止意外对列表进行不同步的访问：

```java
List list = Collections.synchronizedList(new ArrayList(...));
```

## 1.2 ArrayList源码分析    

ArrayList我们使用的实在是太多了，非常熟悉，所以在这里将不介绍它的使用方法。ArrayList是实现List接口的，底层采用数组实现，所以它的操作基本上都是基于对数组的操作。

### 1.2.1 底层使用数组

```java
 private transient Object[] elementData;
```

transient？为java关键字，为变量修饰符，如果用transient声明一个实例变量，当对象存储时，它的值不需要维持（**不需要序列化**）。Java的serialization提供了一种持久化对象实例的机制。当持久化对象时，可能有一个特殊的对象数据成员，我们不想用serialization机制来保存它。为了在一个特定对象的一个域上关闭serialization，可以在这个域前加上关键字transient。当一个对象被序列化的时候，transient型变量的值不包括在序列化的表示中，然而非transient型的变量是被包括进去的。

这里Object[] elementData，就是我们的ArrayList容器，下面介绍的基本操作都是基于该elementData变量来进行操作的。

### **1.2.2 构造函数**

ArrayList提供了三个构造函数：

- `ArrayList()`：默认构造函数，提供初始容量为10的空列表。
- `ArrayList(int initialCapacity)`：构造一个具有指定初始容量的空列表。
- `ArrayList(Collection<?> extends [E] c)`：构造一个包含指定 collection 的元素的列表，这些元素是按照该 collection 的迭代器返回它们的顺序排列的。

```java
    /**
     * 构造一个初始容量为 10 的空列表
     */
    public ArrayList() {
        this(10);
    }

    /**
     * 构造一个具有指定初始容量的空列表。
     */
    public ArrayList(int initialCapacity) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "
                    + initialCapacity);
        this.elementData = new Object[initialCapacity];
    }

    /**
     *  构造一个包含指定 collection 的元素的列表，这些元素是按照该 collection 的迭代器返回它们的顺序排列的。
     */
    public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        size = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, size, Object[].class);
    }
```

### **1. 2.3 新增**

ArrayList提供了`add(E e)`、`add(int index, E element)`、`addAll(Collection<? extends E> c)`、`addAll(int index, Collection<? extends E> c)`、`set(int index, E element)` 这个五个方法来实现ArrayList增加。

add(E e)：将指定的元素添加到此列表的尾部。

```java
 public boolean add(E e) {
    ensureCapacity(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
    }
```

这里ensureCapacity()方法是对ArrayList集合进行扩容操作，elementData(size++) = e，将列表末尾元素指向e。

add(int index, E element)：将指定的元素插入此列表中的指定位置。

```java
public void add(int index, E element) {
        //判断索引位置是否正确
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(
            "Index: "+index+", Size: "+size);
        //扩容检测
        ensureCapacity(size+1);  
        /*
         * 对源数组进行复制处理（位移），从index + 1到size-index。
         * 主要目的就是空出index位置供数据插入，
         * 即向右移动当前位于该位置的元素以及所有后续元素。
         */
        System.arraycopy(elementData, index, elementData, index + 1,
                 size - index);
        //在指定位置赋值
        elementData[index] = element;
        size++;
        }
```

在这个方法中最根本的方法就是System.arraycopy()方法，该方法的根本目的就是将index位置空出来以供新数据插入，这里需要进行数组数据的右移，这是非常麻烦和耗时的，所以如果指定的数据集合需要进行大量插入（中间插入）操作，推荐使用LinkedList。

`addAll(Collection<? extends E> c)`：按照指定 collection 的迭代器所返回的元素顺序，将该 collection 中的所有元素添加到此列表的尾部。

```java
public boolean addAll(Collection<? extends E> c) {
        // 将集合C转换成数组
        Object[] a = c.toArray();
        int numNew = a.length;
        // 扩容处理，大小为size + numNew
        ensureCapacity(size + numNew); // Increments modCount
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }
```

这个方法无非就是使用System.arraycopy()方法将C集合(先准换为数组)里面的数据复制到elementData数组中。这里就稍微介绍下System.arraycopy()，因为下面还将大量用到该方法。该方法的原型为：public static void **arraycopy**(Object src, int srcPos, Object dest, int destPos, int length)。它的根本目的就是进行数组元素的复制。即从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。将源数组src从srcPos位置开始复制到dest数组中，复制长度为length，数据从dest的destPos位置开始粘贴。

`addAll(int index, Collection<?> extends E> c)`：从指定的位置开始，将指定 collection 中的所有元素插入到此列表中。

```java
public boolean addAll(int index, Collection<? extends E> c) {
        //判断位置是否正确
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
                    + size);
        //转换成数组
        Object[] a = c.toArray();
        int numNew = a.length;
        //ArrayList容器扩容处理
        ensureCapacity(size + numNew); // Increments modCount
        //ArrayList容器数组向右移动的位置
        int numMoved = size - index;
        //如果移动位置大于0，则将ArrayList容器的数据向右移动numMoved个位置，确保增加的数据能够增加
        if (numMoved > 0)
            System.arraycopy(elementData, index, elementData, index + numNew,
                    numMoved);
        //添加数组
        System.arraycopy(a, 0, elementData, index, numNew);
        //容器容量变大
        size += numNew;   
        return numNew != 0;
    }
```

set(int index, E element)：用指定的元素替代此列表中指定位置上的元素。

```java
public E set(int index, E element) {
        //检测插入的位置是否越界
        RangeCheck(index);

        E oldValue = (E) elementData[index];
        //替代
        elementData[index] = element;
        return oldValue;
    }
```

### **1.2.4 删除**

ArrayList提供了remove(int index)、remove(Object o)、removeRange(int fromIndex, int toIndex)、removeAll()四个方法进行元素的删除。

remove(int index)：移除此列表中指定位置上的元素。

```java
public E remove(int index) {
        //位置验证
        RangeCheck(index);

        modCount++;
        //需要删除的元素
        E oldValue = (E) elementData[index];   
        //向左移的位数
        int numMoved = size - index - 1;
        //若需要移动，则想左移动numMoved位
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        //置空最后一个元素
        elementData[--size] = null; // Let gc do its work

        return oldValue;
    }
```

remove(Object o)：移除此列表中首次出现的指定元素（如果存在）。

```java
public boolean remove(Object o) {
        //因为ArrayList中允许存在null，所以需要进行null判断
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    //移除这个位置的元素
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
```

其中fastRemove()方法用于移除指定位置的元素。如下

```java
private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // Let gc do its work
    }
```

removeRange(int fromIndex, int toIndex)：移除列表中索引在 `fromIndex`（包括）和 `toIndex`（不包括）之间的所有元素。

```java
protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex,
                        numMoved);

        // Let gc do its work
        int newSize = size - (toIndex - fromIndex);
        while (size != newSize)
            elementData[--size] = null;
    }
```

removeAll()：是继承自AbstractCollection的方法，ArrayList本身并没有提供实现。

```java
public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        Iterator<?> e = iterator();
        while (e.hasNext()) {
            if (c.contains(e.next())) {
                e.remove();
                modified = true;
            }
        }
        return modified;
    }
```

### **1.2.5 查找**

ArrayList提供了get(int index)用读取ArrayList中的元素。由于ArrayList是动态数组，所以我们完全可以根据下标来获取ArrayList中的元素，而且速度还比较快，故ArrayList长于随机访问。

```java
 public E get(int index) {
        RangeCheck(index);

        return (E) elementData[index];
    }
```

### **1.2.6 扩容**

在上面的新增方法的源码中我们发现每个方法中都存在这个方法：ensureCapacity()，该方法就是ArrayList的扩容方法。在前面就提过ArrayList每次新增元素时都会需要进行容量检测判断，若新增元素后元素的个数会超过ArrayList的容量，就会进行扩容操作来满足新增元素的需求。所以当我们清楚知道业务数据量或者需要插入大量元素前，我可以使用ensureCapacity来手动增加ArrayList实例的容量，以减少递增式再分配的数量。

```java
public void ensureCapacity(int minCapacity) {
        //修改计数器，被修改的次数，如果该值失败，则会进入快速失败
        modCount++;
        //ArrayList容量大小
        int oldCapacity = elementData.length;
        /*
         * 若当前需要的长度大于当前数组的长度时，进行扩容操作
         */
        if (minCapacity > oldCapacity) {
            Object oldData[] = elementData;
            //计算新的容量大小，为当前容量的1.5倍
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            //数组拷贝，生成新的数组
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }
```

在这里有一个疑问，为什么每次扩容处理会是1.5倍，而不是2.5、3、4倍呢？通过google查找，发现1.5倍的扩容是最好的倍数。因为一次性扩容太大(例如2.5倍)可能会浪费更多的内存(1.5倍最多浪费33%，而2.5被最多会浪费60%，3.5倍则会浪费71%……)。但是一次性扩容太小，需要多次对数组重新分配内存，对性能消耗比较严重。所以1.5倍刚刚好，既能满足性能需求，也不会造成很大的内存消耗。

处理这个ensureCapacity()这个扩容数组外，ArrayList还给我们提供了将底层数组的容量调整为当前列表保存的实际元素的大小的功能。它可以通过trimToSize()方法来实现。该方法可以最小化ArrayList实例的存储量。

```java
public void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (size < oldCapacity) {
            elementData = Arrays.copyOf(elementData, size);
        }
    }
```

### 1.2.7 ArrayList是怎么实现序列化和反序列化的？

```java
/**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     */
    transient Object[] elementData; // non-private to simplify nested class access
```

我们知道elementData是真正存放元素的地方，但是加了transient字段是为了什么呢？

原来我们是自己实现了writeObject和readObject方法，来控制序列化。

```java
	/**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     *
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     */
private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
    // 防止序列化期间有修改
    int expectedModCount = modCount;
    // 写出非transient非static属性（会写出size属性）
    s.defaultWriteObject();

    // 写出元素个数
    s.writeInt(size);

    // 依次写出元素
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);
    }

    // 如果有修改，抛出异常
    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}

private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
    // 声明为空数组
    elementData = EMPTY_ELEMENTDATA;

    // 读入非transient非static属性（会读取size属性）
    s.defaultReadObject();

    // 读入元素个数，没什么用，只是因为写出的时候写了size属性，读的时候也要按顺序来读
    s.readInt();

    if (size > 0) {
        // 计算容量
        int capacity = calculateCapacity(elementData, size);
        SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
        // 检查是否需要扩容
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // 依次读取元素到数组中
        for (int i=0; i<size; i++) {
            a[i] = s.readObject();
        }
    }
}
```

查看writeObject()方法可知，先调用s.defaultWriteObject()方法，再把size写入到流中，再把元素一个一个的写入到流中。

一般地，只要实现了Serializable接口即可自动序列化，writeObject()和readObject()是为了自己控制序列化的方式，这两个方法必须声明为private，在java.io.ObjectStreamClass#getPrivateMethod()方法中通过反射获取到writeObject()这个方法。

在ArrayList的writeObject()方法中先调用了s.defaultWriteObject()方法，这个方法是写入非static非transient的属性，在ArrayList中也就是size属性。同样地，在readObject()方法中先调用了s.defaultReadObject()方法解析出了size属性。

elementData定义为transient的优势，**自己根据size序列化真实的元素，而不是根据数组的长度序列化元素，减少了空间占用。**

# 2. LinkedList

## 2.1 概述

LinkedList与ArrayList一样实现List接口，只是ArrayList是List接口的大小可变数组的实现，LinkedList是List接口链表的实现。基于链表实现的方式使得LinkedList在插入和删除时更优于ArrayList，而随机访问则比ArrayList逊色些。

LinkedList实现所有可选的列表操作，并允许所有的元素包括null。

除了实现 List 接口外，LinkedList 类还为在列表的开头及结尾 get、remove 和 insert 元素提供了统一的命名方法。**这些操作允许将链接列表用作堆栈、队列或双端队列**。 

此类实现 Deque 接口，为 add、poll 提供先进先出队列操作，以及其他堆栈和双端队列操作。

所有操作都是按照双重链接列表的需要执行的。在列表中编索引的操作将从开头或结尾遍历列表（从靠近指定索引的一端）。

同时，与ArrayList一样此实现不是同步的。

## 2.2 源码分析

### 2.2.1 定义

首先我们先看LinkedList的定义：

```java
 public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
```

从这段代码中我们可以清晰地看出LinkedList继承AbstractSequentialList，实现List、Deque、Cloneable、Serializable。其中AbstractSequentialList提供了 List 接口的骨干实现，从而最大限度地减少了实现受“连续访问”数据存储（如链接列表）支持的此接口所需的工作,从而以减少实现List接口的复杂度。Deque一个线性 collection，支持在两端插入和移除元素，定义了双端队列的操作。

### 2.2.2 属性

在LinkedList中提供了两个基本属性size、header。

private transient Entry header = new Entry(null, null, null);
private transient int size = 0;

 其中size表示的LinkedList的大小，header表示链表的表头，Entry为节点对象。

```java
private static class Entry<E> {
        E element;        //元素节点
        Entry<E> next;    //下一个元素
        Entry<E> previous;  //上一个元素

        Entry(E element, Entry<E> next, Entry<E> previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
```

上面为Entry对象的源代码，Entry为LinkedList的内部类，它定义了存储的元素。该元素的前一个元素、后一个元素，这是典型的双向链表定义方式。

### 2.2.3 构造方法

- LinkedList提高了两个构造方法：LinkedList()和LinkedList(Collection<? extends E> c)。

```java
    /**
     *  构造一个空列表。
     */
    public LinkedList() {
        header.next = header.previous = header;
    }

    /**
     *  构造一个包含指定 collection 中的元素的列表，这些元素按其 collection 的迭代器返回的顺序排列。
     */
    public LinkedList(Collection<? extends E> c) {
        this();
        addAll(c);
    }
```

LinkedList()构造一个空列表。里面没有任何元素，仅仅只是将header节点的前一个元素、后一个元素都指向自身。

LinkedList(Collection<? extends E> c)： 构造一个包含指定 collection 中的元素的列表，这些元素按其 collection 的迭代器返回的顺序排列。该构造函数首先会调用LinkedList()，构造一个空列表，然后调用了addAll()方法将Collection中的所有元素添加到列表中。以下是addAll()的源代码：

```java
    /**
     *  添加指定 collection 中的所有元素到此列表的结尾，顺序是指定 collection 的迭代器返回这些元素的顺序。
     */
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    /**
     * 将指定 collection 中的所有元素从指定位置开始插入此列表。其中index表示在其中插入指定collection中第一个元素的索引
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        //若插入的位置小于0或者大于链表长度，则抛出IndexOutOfBoundsException异常
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        Object[] a = c.toArray();
        int numNew = a.length;    //插入元素的个数
        //若插入的元素为空，则返回false
        if (numNew == 0)
            return false;
        //modCount:在AbstractList中定义的，表示从结构上修改列表的次数
        modCount++;
        //获取插入位置的节点，若插入的位置在size处，则是头节点，否则获取index位置处的节点
        Entry<E> successor = (index == size ? header : entry(index));
        //插入位置的前一个节点，在插入过程中需要修改该节点的next引用：指向插入的节点元素
        Entry<E> predecessor = successor.previous;
        //执行插入动作
        for (int i = 0; i < numNew; i++) {
            //构造一个节点e，这里已经执行了插入节点动作同时修改了相邻节点的指向引用
            //
            Entry<E> e = new Entry<E>((E) a[i], successor, predecessor);
            //将插入位置前一个节点的下一个元素引用指向当前元素
            predecessor.next = e;
            //修改插入位置的前一个节点，这样做的目的是将插入位置右移一位，保证后续的元素是插在该元素的后面，确保这些元素的顺序
            predecessor = e;
        }
        successor.previous = predecessor;
        //修改容量大小
        size += numNew;
        return true;
    }
```

在addAll()方法中，涉及到了两个方法，一个是entry(int index)，该方法为LinkedList的私有方法，主要是用来查找index位置的节点元素。

```java
    /**
     * 返回指定位置(若存在)的节点元素
     */
    private Entry<E> entry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
                    + size);
        //头部节点
        Entry<E> e = header;
        //判断遍历的方向
        if (index < (size >> 1)) {
            for (int i = 0; i <= index; i++)
                e = e.next;
        } else {
            for (int i = size; i > index; i--)
                e = e.previous;
        }
        return e;
    }
```

从该方法有两个遍历方向中我们也可以看出LinkedList是双向链表，这也是在构造方法中为什么需要将header的前、后节点均指向自己。

如果对数据结构有点了解，对上面所涉及的内容应该问题，我们只需要清楚一点：LinkedList是双向链表，其余都迎刃而解。

由于篇幅有限，下面将就LinkedList中几个常用的方法进行源码分析。

### 2.2.4 增加方法

add(E e): 将指定元素添加到此列表的结尾。

```java
 public boolean add(E e) {
    addBefore(e, header);
        return true;
    }
```

该方法调用addBefore方法，然后直接返回true，对于addBefore()而已，它为LinkedList的私有方法。

```java
private Entry<E> addBefore(E e, Entry<E> entry) {
        //利用Entry构造函数构建一个新节点 newEntry，
        Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
        //修改newEntry的前后节点的引用，确保其链表的引用关系是正确的
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
        //容量+1
        size++;
        //修改次数+1
        modCount++;
        return newEntry;
    }
```

在addBefore方法中无非就是做了这件事：构建一个新节点newEntry，然后修改其前后的引用。

LinkedList还提供了其他的增加方法：

- add(int index, E element)：在此列表中指定的位置插入指定的元素。
- addAll(Collection<? extends E> c)：添加指定 collection 中的所有元素到此列表的结尾，顺序是指定 collection 的迭代器返回这些元素的顺序。
- addAll(int index, Collection<? extends E> c)：将指定 collection 中的所有元素从指定位置开始插入此列表。
- AddFirst(E e): 将指定元素插入此列表的开头。
- addLast(E e): 将指定元素添加到此列表的结尾。

### 2.2.5 移除方法

remove(Object o)：从此列表中移除首次出现的指定元素（如果存在）。该方法的源代码如下：

```java
public boolean remove(Object o) {
        if (o==null) {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (e.element==null) {
                    remove(e);
                    return true;
                }
            }
        } else {
            for (Entry<E> e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }
        }
        return false;
    }
```

该方法首先会判断移除的元素是否为null，然后迭代这个链表找到该元素节点，最后调用remove(Entry e)，remove(Entry e)为私有方法，是LinkedList中所有移除方法的基础方法，如下：

```java
private E remove(Entry<E> e) {
        if (e == header)
            throw new NoSuchElementException();

        //保留被移除的元素：要返回
        E result = e.element;

        //将该节点的前一节点的next指向该节点后节点
        e.previous.next = e.next;
        //将该节点的后一节点的previous指向该节点的前节点
        //这两步就可以将该节点从链表从除去：在该链表中是无法遍历到该节点的
        e.next.previous = e.previous;
        //将该节点归空
        e.next = e.previous = null;
        e.element = null;
        size--;
        modCount++;
        return result;
    }
```

其他的移除方法：

- clear()： 从此列表中移除所有元素。
- remove()：获取并移除此列表的头（第一个元素）。
- remove(int index)：移除此列表中指定位置处的元素。
- remove(Objec o)：从此列表中移除首次出现的指定元素（如果存在）。
- removeFirst()：移除并返回此列表的第一个元素。
- removeFirstOccurrence(Object o)：从此列表中移除第一次出现的指定元素（从头部到尾部遍历列表时）。
- removeLast()：移除并返回此列表的最后一个元素。
- removeLastOccurrence(Object o)：从此列表中移除最后一次出现的指定元素（从头部到尾部遍历列表时）。

### 2.2.6 查找方法

对于查找方法的源码就没有什么好介绍了，无非就是迭代，比对，然后就是返回当前值。

- get(int index)：返回此列表中指定位置处的元素。
- getFirst()：返回此列表的第一个元素。
- getLast()：返回此列表的最后一个元素。
- indexOf(Object o)：返回此列表中首次出现的指定元素的索引，如果此列表中不包含该元素，则返回 -1。
- lastIndexOf(Object o)：返回此列表中最后出现的指定元素的索引，如果此列表中不包含该元素，则返回 -1。

# 3. Vector

## 3.1 Vector简介

Vector可以实现**可增长的对象数组**。与数组一样，它包含可以使用整数索引进行访问的组件。不过，Vector的大小是可以增加或者减小的，以便适应创建Vector后进行添加或者删除操作。

Vector实现List接口，继承AbstractList类，所以我们可以将其看做队列，支持相关的添加、删除、修改、遍历等功能。

Vector实现RandmoAccess接口，即提供了随机访问功能，提供提供快速访问功能。在Vector我们可以直接访问元素。

Vector 实现了Cloneable接口，支持clone()方法，可以被克隆。

```java
public class Vector<E>
   extends AbstractList<E>
   implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

Vector提供了四个构造函数：

```java
  /**
    * 构造一个空向量，使其内部数据数组的大小为 10，其标准容量增量为零。
    */
    public Vector() {
           this(10);
    }

   /**
    * 构造一个包含指定 collection 中的元素的向量，这些元素按其 collection 的迭代器返回元素的顺序排列。
    */
   public Vector(Collection<? extends E> c) {
       elementData = c.toArray();
       elementCount = elementData.length;
       // c.toArray might (incorrectly) not return Object[] (see 6260652)
       if (elementData.getClass() != Object[].class)
           elementData = Arrays.copyOf(elementData, elementCount,
                   Object[].class);
   }

   /**
    * 使用指定的初始容量和等于零的容量增量构造一个空向量。
    */
   public Vector(int initialCapacity) {
       this(initialCapacity, 0);
   }

   /**
    *  使用指定的初始容量和容量增量构造一个空的向量。
    */
   public Vector(int initialCapacity, int capacityIncrement) {
       super();
       if (initialCapacity < 0)
           throw new IllegalArgumentException("Illegal Capacity: "+
                                              initialCapacity);
       this.elementData = new Object[initialCapacity];
       this.capacityIncrement = capacityIncrement;
   }
```

在成员变量方面，Vector提供了elementData , elementCount， capacityIncrement三个成员变量。其中

- elementData ：”Object[]类型的数组”，它保存了Vector中的元素。按照Vector的设计elementData为一个动态数组，可以随着元素的增加而动态的增长，其具体的增加方式后面提到（ensureCapacity方法）。如果在初始化Vector时没有指定容器大小，则使用默认大小为10.
- elementCount：`Vector` 对象中的有效组件数。
- capacityIncrement：向量的大小大于其容量时，容量自动增加的量。如果在创建Vector时，指定了capacityIncrement的大小；则，每次当Vector中动态数组容量增加时>，增加的大小都是capacityIncrement。如果容量的增量小于等于零，则每次需要增大容量时，向量的容量将增大一倍。

同时Vector是线程安全的！

## 3.2 源码解析

对于源码的解析，LZ在这里只就增加（add）删除（remove）两个方法进行讲解。

### 3.2.1 增加：add(E e)

add(E e)：将指定元素添加到此向量的末尾。

```java
public synchronized boolean add(E e) {
       modCount++;     
       ensureCapacityHelper(elementCount + 1);    //确认容器大小，如果操作容量则扩容操作
       elementData[elementCount++] = e;   //将e元素添加至末尾
       return true;
   }
```

这个方法相对而言比较简单，具体过程就是先确认容器的大小，看是否需要进行扩容操作，然后将E元素添加到此向量的末尾。

```java
private void ensureCapacityHelper(int minCapacity) {
       //如果
       if (minCapacity - elementData.length > 0)
           grow(minCapacity);
   }

   /**
    * 进行扩容操作
    * 如果此向量的当前容量小于minCapacity，则通过将其内部数组替换为一个较大的数组俩增加其容量。
    * 新数据数组的大小姜维原来的大小 + capacityIncrement，
    * 除非 capacityIncrement 的值小于等于零，在后一种情况下，新的容量将为原来容量的两倍，不过，如果此大小仍然小于 minCapacity，则新容量将为 minCapacity。
    */
   private void grow(int minCapacity) {
       int oldCapacity = elementData.length;     //当前容器大小
       /*
        * 新容器大小
        * 若容量增量系数(capacityIncrement) > 0，则将容器大小增加到capacityIncrement
        * 否则将容量增加一倍
        */
       int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                        capacityIncrement : oldCapacity);

       if (newCapacity - minCapacity < 0)
           newCapacity = minCapacity;

       if (newCapacity - MAX_ARRAY_SIZE > 0)
           newCapacity = hugeCapacity(minCapacity);

       elementData = Arrays.copyOf(elementData, newCapacity);
   }

   /**
    * 判断是否超出最大范围
    * MAX_ARRAY_SIZE：private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    */
   private static int hugeCapacity(int minCapacity) {
       if (minCapacity < 0)
           throw new OutOfMemoryError();
       return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
   }
```

对于Vector整个的扩容过程，就是根据capacityIncrement确认扩容大小的，若capacityIncrement <= 0 则扩大一倍，否则扩大至capacityIncrement 。当然这个容量的最大范围为Integer.MAX_VALUE即，2^32 – 1，所以Vector并不是可以无限扩充的。

### 3.2.2 remove(Object o)

```java
  /**
    * 从Vector容器中移除指定元素E
    */
   public boolean remove(Object o) {
       return removeElement(o);
   }

   public synchronized boolean removeElement(Object obj) {
       modCount++;
       int i = indexOf(obj);   //计算obj在Vector容器中位置
       if (i >= 0) {
           removeElementAt(i);   //移除
           return true;
       }
       return false;
   }

   public synchronized void removeElementAt(int index) {
       modCount++;     //修改次数+1
       if (index >= elementCount) {   //删除位置大于容器有效大小
           throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
       }
       else if (index < 0) {    //位置小于 < 0
           throw new ArrayIndexOutOfBoundsException(index);
       }
       int j = elementCount - index - 1;
       if (j > 0) {   
           //从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
           //也就是数组元素从j位置往前移
           System.arraycopy(elementData, index + 1, elementData, index, j);
       }
       elementCount--;   //容器中有效组件个数 - 1
       elementData[elementCount] = null;    //将向量的末尾位置设置为null
   }
```

因为Vector底层是使用数组实现的，所以它的操作都是对数组进行操作，只不过其是可以随着元素的增加而动态的改变容量大小，其实现方法是是使用Arrays.copyOf方法将旧数据拷贝到一个新的大容量数组中。Vector的整个内部实现都比较简单，这里就不在重述了。

## 3.3 Vector遍历

Vector支持4种遍历方式。

## 3.3.1 **随机访问**

因为Vector实现了RandmoAccess接口，可以通过下标来进行随机访问。

```java
for(int i = 0 ; i < vec.size() ; i++){
       value = vec.get(i);
   }
```

### 3.3.2 迭代器

```java
Iterator it = vec.iterator();
   while(it.hasNext()){
       value = it.next();
       //do something
   }
```

###3.3.3 for循环

```java
for(Integer value:vec){
       //do something
   }
```

### 3.3.4 **Enumeration循环**

```java
Vector vec = new Vector<>();
   Enumeration enu = vec.elements();
   while (enu.hasMoreElements()) {
       value = (Integer)enu.nextElement();
   }
```

## stack

在Java中Stack类表示后进先出（LIFO）的对象堆栈。栈是一种非常常见的数据结构，它采用典型的先进后出的操作方式完成的。每一个栈都包含一个栈顶，每次出栈是将栈顶的数据取出，如下：

![2014070800001_thumb_thumb](img/091242265826653-2897266.jpg)

Stack通过五个操作对Vector进行扩展，允许将向量视为堆栈。这个五个操作如下：

| **操作**               | **说明**                                         |
| ---------------------- | ------------------------------------------------ |
| `**empty**()`          | 测试堆栈是否为空。                               |
| `**peek**()`           | 查看堆栈顶部的对象，但不从堆栈中移除它。         |
| `**pop**()`            | 移除堆栈顶部的对象，并作为此函数的值返回该对象。 |
| `**push**(E item)`     | 把项压入堆栈顶部。                               |
| `**search**(Object o)` | 返回对象在堆栈中的位置，以 1 为基数              |

Stack继承Vector，他对Vector进行了简单的扩展：

```java
 public class Stack<E> extends Vector<E>
```

Stack的实现非常简单，仅有一个构造方法，五个实现方法（从Vector继承而来的方法不算与其中），同时其实现的源码非常简单

```java
/**
     * 构造函数
     */
    public Stack() {
    }

    /**
     *  push函数：将元素存入栈顶
     */
    public E push(E item) {
        // 将元素存入栈顶。
        // addElement()的实现在Vector.java中
        addElement(item);

        return item;
    }

    /**
     * pop函数：返回栈顶元素，并将其从栈中删除
     */
    public synchronized E pop() {
        E    obj;
        int    len = size();

        obj = peek();
        // 删除栈顶元素，removeElementAt()的实现在Vector.java中
        removeElementAt(len - 1);

        return obj;
    }

    /**
     * peek函数：返回栈顶元素，不执行删除操作
     */
    public synchronized E peek() {
        int    len = size();

        if (len == 0)
            throw new EmptyStackException();
        // 返回栈顶元素，elementAt()具体实现在Vector.java中
        return elementAt(len - 1);
    }

    /**
     * 栈是否为空
     */
    public boolean empty() {
        return size() == 0;
    }

    /**
     *  查找“元素o”在栈中的位置：由栈底向栈顶方向数
     */
    public synchronized int search(Object o) {
        // 获取元素索引，elementAt()具体实现在Vector.java中
        int i = lastIndexOf(o);

        if (i >= 0) {
            return size() - i;
        }
        return -1;
    }
```

# 4 总结

## 4.1 ArrayList vs LinkedList vs Vector

问：Java中的List有几种实现，各有什么不同？ 解：List主要有ArrayList、LinkedList与Vector几种实现。这三者都实现了List 接口，使用方式也很相似,主要区别在于因为实现方式的不同,所以对不同的操作具有不同的效率。

- ArrayList 是一个可改变大小的数组.当更多的元素加入到ArrayList中时,其大小将会动态地增长.内部的元素可以直接通过get与set方法进行访问,因为ArrayList本质上就是一个数组. 

- LinkedList 是一个双链表,在添加和删除元素时具有比ArrayList更好的性能.但在get与set方面弱于ArrayList. 当然,这些对比都是指数据量很大或者操作很频繁的情况下的对比,如果数据和运算量很小,那么对比将失去意义. 
- Vector 和ArrayList类似,但属于强同步类。如果你的程序本身是线程安全的(thread-safe,没有在多个线程之间共享同一个集合/对象),那么使用ArrayList是更好的选择。 Vector和ArrayList在更多元素添加进来时会请求更大的空间。Vector每次请求其大小的双倍空间，而ArrayList每次对size增长50%. 
- 而 LinkedList 还实现了 Queue 接口,该接口比List提供了更多的方法,包括 offer(),peek(),poll()等. 注意: 默认情况下ArrayList的初始容量非常小,所以如果可以预估数据量的话,分配一个较大的初始值属于最佳实践,这样可以减少调整大小的开销。

|          | [ArrayList](https://github.com/haojunsheng/JavaLearning/blob/master/Java-basic/Java-collection/list-detail.md#1-arraylist) | [LinkedList](https://github.com/haojunsheng/JavaLearning/blob/master/Java-basic/Java-collection/list-detail.md#2-linkedlist) | [Vector](https://github.com/haojunsheng/JavaLearning/blob/master/Java-basic/Java-collection/list-detail.md#3-vector) | [Stack](https://github.com/haojunsheng/JavaLearning/blob/master/Java-basic/Java-collection/list-detail.md#stack) |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 实现方式 | 数组                                                         | 链表                                                         | 数组                                                         | 继承自Vector,实现一个后进先出的堆栈                          |
| 线程安全 | 否                                                           | 否                                                           | 是                                                           | 是                                                           |
| 默认容量 | 10                                                           |                                                              |                                                              |                                                              |
| 扩容     | 1.5倍                                                        |                                                              | 2倍                                                          |                                                              |
| 特性     |                                                              | size，表示大小；first,last；Node定义双向链表。               |                                                              |                                                              |
| 适用     | 查找                                                         | 插入，删除                                                   |                                                              |                                                              |

List 是一个接口，它继承于Collection的接口。它代表着有序的队列。当我们讨论List的时候，一般都和Set作比较。

> List中元素可以重复，并且是有序的（这里的有序指的是按照放入的顺序进行存储。如按照顺序把1，2，3存入List，那么，从List中遍历出来的顺序也是1，2，3）。
>
> Set中的元素不可以重复，并且是无序的（从set中遍历出来的数据和放入顺序没有关系）。

下面是Java中的集合类的关系图。从中可以大致了解集合类之间的关系

[![java-collection-hierarchy](img/java-collection-hierarchy-2900836.jpeg)](http://www.hollischuang.com/wp-content/uploads/2016/03/java-collection-hierarchy.jpeg)

从上图可以看出，ArrayList、 LinkedList 和 Vector都实现了List接口，是List的三种实现，所以在用法上非常相似。他们之间的主要区别体现在不同操作的性能上。后面会详细分析。

### 4.1.1 ArrayList

ArrayList底层是用数组实现的，可以认为ArrayList是一个可改变大小的数组。随着越来越多的元素被添加到ArrayList中，其规模是动态增加的。

### 4.1.2 LinkedList

LinkedList底层是通过双向链表实现的。所以，LinkedList和ArrayList之前的区别主要就是数组和链表的区别。

> 数组中查询和赋值比较快，因为可以直接通过数组下标访问指定位置。
>
> 链表中删除和增加比较快，因为可以直接通过修改链表的指针（Java中并无指针，这里可以简单理解为指针。其实是通过Node节点中的变量指定）进行元素的增删。

所以，LinkedList和ArrayList相比，增删的速度较快。但是查询和修改值的速度较慢。同时，LinkedList还实现了Queue接口，所以他还提供了offer(), peek(), poll()等方法。

### 4.1.3 Vector

Vector和ArrayList一样，都是通过数组实现的，但是Vector是线程安全的。和ArrayList相比，其中的很多方法都通过同步（synchronized）处理来保证线程安全。

如果你的程序不涉及到线程安全问题，那么使用ArrayList是更好的选择（因为Vector使用synchronized，必然会影响效率）。

二者之间还有一个区别，就是扩容策略不一样。在List被第一次创建的时候，会有一个初始大小，随着不断向List中增加元素，当List认为容量不够的时候就会进行扩容。Vector缺省情况下自动增长原来一倍的数组长度，ArrayList增长原来的50%。

### 4.1.4 ArrayList 和 LinkedList的性能对比

使用以下代码对ArrayList和LinkedList中的几种主要操作所用时间进行对比：

```java
ArrayList<Integer> arrayList = new ArrayList<Integer>();
LinkedList<Integer> linkedList = new LinkedList<Integer>();

// ArrayList add
long startTime = System.nanoTime();

for (int i = 0; i < 100000; i++) {
    arrayList.add(i);
}
long endTime = System.nanoTime();
long duration = endTime - startTime;
System.out.println("ArrayList add:  " + duration);

// LinkedList add
startTime = System.nanoTime();

for (int i = 0; i < 100000; i++) {
    linkedList.add(i);
}
endTime = System.nanoTime();
duration = endTime - startTime;
System.out.println("LinkedList add: " + duration);

// ArrayList get
startTime = System.nanoTime();

for (int i = 0; i < 10000; i++) {
    arrayList.get(i);
}
endTime = System.nanoTime();
duration = endTime - startTime;
System.out.println("ArrayList get:  " + duration);

// LinkedList get
startTime = System.nanoTime();

for (int i = 0; i < 10000; i++) {
    linkedList.get(i);
}
endTime = System.nanoTime();
duration = endTime - startTime;
System.out.println("LinkedList get: " + duration);



// ArrayList remove
startTime = System.nanoTime();

for (int i = 9999; i >=0; i--) {
    arrayList.remove(i);
}
endTime = System.nanoTime();
duration = endTime - startTime;
System.out.println("ArrayList remove:  " + duration);



// LinkedList remove
startTime = System.nanoTime();

for (int i = 9999; i >=0; i--) {
    linkedList.remove(i);
}
endTime = System.nanoTime();
duration = endTime - startTime;
System.out.println("LinkedList remove: " + duration);
```

结果：

```
ArrayList add:  13265642
LinkedList add: 9550057
ArrayList get:  1543352
LinkedList get: 85085551
ArrayList remove:  199961301
LinkedList remove: 85768810
```

[![arraylist-vs-linkedlist1](img/arraylist-vs-linkedlist1-2900836.png)](http://www.hollischuang.com/wp-content/uploads/2016/03/arraylist-vs-linkedlist1.png)

他们的表现的差异是显而易见的。在添加和删除操作上LinkedList更快,但在查询速度较慢。

**如何选择**

如果涉及到多线程，那么就选择Vector（当然，你也可以使用ArrayList并自己实现同步），如果不涉及到多线程就从LinkedList、ArrayList中选。 LinkedList更适合从中间插入或者删除（链表的特性）。 ArrayList更适合检索和在末尾插入或删除（数组的特性）。

**参考资料**

[ArrayList vs. LinkedList vs. Vector](http://www.programcreek.com/2013/03/arraylist-vs-linkedlist-vs-vector/)

## 4.2 synchronizedList vs Vector?

问：知道什么是synchronizedList吗？他和Vector有何区别？ 解： Vector是java.util包中的一个类。 SynchronizedList是java.util.Collections中的一个静态内部类。 在多线程的场景中可以直接使用Vector类，也可以使用Collections.synchronizedList(List list)方法来返回一个线程安全的List。

 1.如果使用add方法，那么他们的扩容机制不一样。 

2.SynchronizedList可以指定锁定的对象。即锁粒度是同步代码块。而Vector的锁粒度是同步方法。 3.SynchronizedList有很好的扩展和兼容功能。他可以将所有的List的子类转成线程安全的类。

 4.使用SynchronizedList的时候，进行遍历时要手动进行同步处理。 

5.SynchronizedList可以指定锁定的对象。

|                | SynchronizedList                 | Vector    |
| -------------- | -------------------------------- | --------- |
| 包             | java.util.Collections            | java.util |
| 线程安全       | 是                               | 是        |
| 扩容机制       | 1.5倍                            | 2倍       |
| 同步方式       | 同步代码块                       | 同步方法  |
| 扩展性和兼容性 | 将所有list的子类转成线程安全的类 |           |
| 遍历           | 需要手动同步                     |           |
| 锁定对象       | 可以指定锁定的对象(构造函数传入) | this对象  |

Vector是java.util包中的一个类。 SynchronizedList是java.util.Collections中的一个静态内部类。

在多线程的场景中可以直接使用Vector类，也可以使用Collections.synchronizedList(List list)方法来返回一个线程安全的List。

**那么，到底SynchronizedList和Vector有没有区别，为什么java api要提供这两种线程安全的List的实现方式呢？**

首先，我们知道Vector和Arraylist都是List的子类，他们底层的实现都是一样的。所以这里比较如下两个`list1`和`list2`的区别：

```java
List<String> list = new ArrayList<String>();
List list2 =  Collections.synchronizedList(list);
Vector<String> list1 = new Vector<String>();
```

### 4.2.1 add方法

**Vector的实现：**

```java
public void add(int index, E element) {
    insertElementAt(element, index);
}

public synchronized void insertElementAt(E obj, int index) {
    modCount++;
    if (index > elementCount) {
        throw new ArrayIndexOutOfBoundsException(index
                                                 + " > " + elementCount);
    }
    ensureCapacityHelper(elementCount + 1);
    System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
    elementData[index] = obj;
    elementCount++;
}

private void ensureCapacityHelper(int minCapacity) {
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}
```

**synchronizedList的实现：**

```java
public void add(int index, E element) {
   synchronized (mutex) {
       list.add(index, element);
   }
}
```

这里，使用同步代码块的方式调用ArrayList的add()方法。ArrayList的add方法内容如下：

```java
public void add(int index, E element) {
    rangeCheckForAdd(index);
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    System.arraycopy(elementData, index, elementData, index + 1,
                     size - index);
    elementData[index] = element;
    size++;
}
private void rangeCheckForAdd(int index) {
    if (index > size || index < 0)
        throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
}
private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    ensureExplicitCapacity(minCapacity);
}
```

从上面两段代码中发现有两处不同： **1.Vector使用同步方法实现，synchronizedList使用同步代码块实现。 2.两者的扩充数组容量方式不一样（两者的add方法在扩容方面的差别也就是ArrayList和Vector的差别。）**

### 4.2.2 remove方法

**synchronizedList的实现：**

```
public E remove(int index) {
    synchronized (mutex) {return list.remove(index);}
}
```

ArrayList类的remove方法内容如下：

```java
public E remove(int index) {
    rangeCheck(index);

    modCount++;
    E oldValue = elementData(index);

    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work

    return oldValue;
}
```

**Vector的实现：**

```java
public synchronized E remove(int index) {
        modCount++;
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        E oldValue = elementData(index);

        int numMoved = elementCount - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--elementCount] = null; // Let gc do its work

        return oldValue;
    }
```

**从remove方法中我们发现除了一个使用同步方法，一个使用同步代码块之外几乎无任何区别。**

> 通过比较其他方法，我们发现，SynchronizedList里面实现的方法几乎都是使用同步代码块包上List的方法。如果该List是ArrayList,那么，SynchronizedList和Vector的一个比较明显区别就是一个使用了同步代码块，一个使用了同步方法。

### 4.2.3 区别分析

**数据增长区别**

> 从内部实现机制来讲ArrayList和Vector都是使用数组(Array)来控制集合中的对象。当你向这两种类型中增加元素的时候，如果元素的数目超出了内部数组目前的长度它们都需要扩展内部数组的长度，Vector缺省情况下自动增长原来一倍的数组长度，ArrayList是原来的50%,所以最后你获得的这个集合所占的空间总是比你实际需要的要大。所以如果你要在集合中保存大量的数据那么使用Vector有一些优势，因为你可以通过设置集合的初始化大小来避免不必要的资源开销。

**同步代码块和同步方法的区别** 1.同步代码块在锁定的范围上可能比同步方法要小，一般来说锁的范围大小和性能是成反比的。 2.同步块可以更加精确的控制锁的作用域（锁的作用域就是从锁被获取到其被释放的时间），同步方法的锁的作用域就是整个方法。 3.静态代码块可以选择对哪个对象加锁，但是静态方法只能给this对象加锁。

> 因为SynchronizedList只是使用同步代码块包裹了ArrayList的方法，而ArrayList和Vector中同名方法的方法体内容并无太大差异，所以在锁定范围和锁的作用域上两者并无却别。 在锁定的对象区别上，SynchronizedList的同步代码块锁定的是mutex对象，Vector锁定的是this对象。那么mutex对象又是什么呢？ 其实SynchronizedList有一个构造函数可以传入一个Object,如果在调用的时候显示的传入一个对象，那么锁定的就是用户传入的对象。如果没有指定，那么锁定的也是this对象。

所以，SynchronizedList和Vector的区别目前为止有两点： 1.如果使用add方法，那么他们的扩容机制不一样。 2.SynchronizedList可以指定锁定的对象。

但是，凡事都有但是。 SynchronizedList中实现的类并没有都使用synchronized同步代码块。其中有listIterator和listIterator(int index)并没有做同步处理。但是Vector却对该方法加了方法锁。 所以说，在使用SynchronizedList进行遍历的时候要手动加锁。

但是，但是之后还有但是。

之前的比较都是基于我们将ArrayList转成SynchronizedList。那么如果我们想把LinkedList变成线程安全的，或者说我想要方便在中间插入和删除的同步的链表，那么我可以将已有的LinkedList直接转成 SynchronizedList，而不用改变他的底层数据结构。而这一点是Vector无法做到的，因为他的底层结构就是使用数组实现的，这个是无法更改的。

所以，最后，SynchronizedList和Vector最主要的区别： **1.SynchronizedList有很好的扩展和兼容功能。他可以将所有的List的子类转成线程安全的类。** **2.使用SynchronizedList的时候，进行遍历时要手动进行同步处理**。 **3.SynchronizedList可以指定锁定的对象。**

## 4.3 **通过Array.asList获得的List有何特点，使用时应该注意什么**?

**通过Array.asList获得的List有何特点，使用时应该注意什么**？

问：通过Array.asList获得的List有何特点，使用时应该注意什么？ 解：1. asList 得到的只是一个 Arrays 的内部类，一个原来数组的视图 List，因此如果对它进行增删操作会报错 2. 用 ArrayList 的构造器可以将其转变成真正的 ArrayList

## 4.4 fail-fast vs fail-safe

**什么是fail-fast，什么是fail-safe，有什么区别吗**？

问：什么是fail-fast，什么是fail-safe，有什么区别吗？ 解： 一：快速失败（fail—fast）      在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出ConcurrentModificationException。      如以下代码，会抛出ConcurrentModificationException：      for (Student stu : students) {      if (stu.getId() == 2)      students.remove(stu);      }      原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变modCount的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。    注意：这里异常的抛出条件是检测到 modCount=expectedmodCount 这个条件。如果集合发生变化时修改modCount值刚好又设置为了expectedmodCount值，则异常不会抛出。因此，不能依赖于这个异常是否抛出而进行并发操作的编程，这个异常只建议用于检测并发修改的bug。    场景：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）。 相关文章：[Java中的增强for循环（for each）的实现原理与坑-HollisChuang's Blog](http://www.hollischuang.com/archives/1776)   二：安全失败（fail—safe）    采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。    原理：由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发ConcurrentModificationException。    缺点：基于拷贝内容的优点是避免了ConcurrentModificationException，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。    场景：java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。

### 4.4.1 前言

什么是快速失败（fail-fast）和安全失败（fail-safe）？它们又和什么内容有关系。以上两点就是这篇文章的内容，废话不多话，正文请慢用。

我们都接触 HashMap、ArrayList 这些集合类，这些在 **java.util 包的集合类就都是快速失败**的；而  **java.util.concurrent 包下的类都是安全失败**，比如：ConcurrentHashMap。

### 4.4.2 快速失败

首先我们看下维基百科中关于fail-fast的解释：

> In systems design, a fail-fast system is one which immediately reports at its interface any condition that is likely to indicate a failure. Fail-fast systems are usually designed to stop normal operation rather than attempt to continue a possibly flawed process. Such designs often check the system’s state at several points in an operation, so any failures can be detected early. The responsibility of a fail-fast module is detecting errors, then letting the next-highest level of the system handle them.

大概意思是：在系统设计中，快速失效系统一种可以立即报告任何可能表明故障的情况的系统。快速失效系统通常设计用于停止正常操作，而不是试图继续可能存在缺陷的过程。这种设计通常会在操作中的多个点检查系统的状态，因此可以及早检测到任何故障。快速失败模块的职责是检测错误，然后让系统的下一个最高级别处理错误。

其实，这是一种理念，说白了就是**在做系统设计的时候先考虑异常情况，一旦发生异常，直接停止并上报**。

举一个最简单的fail-fast的例子：

```java
public int divide(int divisor,int dividend){
    if(dividend == 0){
        throw new RuntimeException("dividend can't be null");
    }
    return divisor/dividend;
}
```

上面的代码是一个对两个整数做除法的方法，在divide方法中，我们对被除数做了个简单的检查，如果其值为0，那么就直接抛出一个异常，并明确提示异常原因。这其实就是fail-fast理念的实际应用。

这样做的好处就是可以预先识别出一些错误情况，一方面可以避免执行复杂的其他代码，另外一方面，这种异常情况被识别之后也可以针对性的做一些单独处理。

怎么样，现在你知道fail-fast了吧，其实他并不神秘，你日常的代码中可能经常会在使用的。

既然，fail-fast是一种比较好的机制，为什么文章标题说fail-fast会有坑呢？

原因是Java的集合类中运用了fail-fast机制进行设计，一旦使用不当，触发fail-fast机制设计的代码，就会发生非预期情况。

**集合类中的fail-fast**

我们通常说的Java中的fail-fast机制，默认指的是Java集合的一种错误检测机制。当多个线程对部分集合进行结构上的改变的操作时，有可能会产生fail-fast机制，这个时候就会抛出ConcurrentModificationException（后文用CME代替）。

**CMException，当方法检测到对象的并发修改，但不允许这种修改时就抛出该异常。**

很多时候正是因为代码中抛出了CMException，很多程序员就会很困惑，明明自己的代码并没有在多线程环境中执行，为什么会抛出这种并发有关的异常呢？这种情况在什么情况下才会抛出呢？我们就来深入分析一下。

**异常复现**

在Java中， 如果在foreach 循环里对某些集合元素进行元素的 remove/add 操作的时候，就会触发fail-fast机制，进而抛出CMException。

如以下代码：

```java
List<String> userNames = new ArrayList<String>() {{
    add("Hollis");
    add("hollis");
    add("HollisChuang");
    add("H");
}};

for (String userName : userNames) {
    if (userName.equals("Hollis")) {
        userNames.remove(userName);
    }
}

System.out.println(userNames);
```

以上代码，使用增强for循环遍历元素，并尝试删除其中的Hollis字符串元素。运行以上代码，会抛出以下异常：

```
Exception in thread "main" java.util.ConcurrentModificationException
at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
at java.util.ArrayList$Itr.next(ArrayList.java:859)
at com.hollis.ForEach.main(ForEach.java:22)
```

同样的，读者可以尝试下在增强for循环中使用add方法添加元素，结果也会同样抛出该异常。

在深入原理之前，我们先尝试把foreach进行解语法糖，看一下foreach具体如何实现的。

我们使用[jad](https://www.hollischuang.com/archives/58)工具，对编译后的class进行反编译，得到以下代码：

```java
public static void main(String[] args) {
    // 使用ImmutableList初始化一个List
    List<String> userNames = new ArrayList<String>() {{
        add("Hollis");
        add("hollis");
        add("HollisChuang");
        add("H");
    }};

    Iterator iterator = userNames.iterator();
    do
    {
        if(!iterator.hasNext())
            break;
        String userName = (String)iterator.next();
        if(userName.equals("Hollis"))
            userNames.remove(userName);
    } while(true);
    System.out.println(userNames);
}
```

可以发现，foreach其实是依赖了while循环和Iterator实现的。

**异常原理**

通过以上代码的异常堆栈，我们可以跟踪到真正抛出异常的代码是：

```
java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
```

该方法是在iterator.next()方法中调用的。我们看下该方法的实现：

```java
final void checkForComodification() {
    if (modCount != expectedModCount)
        throw new ConcurrentModificationException();
}
```

如上，在该方法中对modCount和expectedModCount进行了比较，如果二者不想等，则抛出CMException。

那么，modCount和expectedModCount是什么？是什么原因导致他们的值不想等的呢？

```java
 /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the
     * list, or otherwise perturb it in such a fashion that iterations in
     * progress may yield incorrect results.
     *
     * <p>This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods.
     * If the value of this field changes unexpectedly, the iterator (or list
     * iterator) will throw a {@code ConcurrentModificationException} in
     * response to the {@code next}, {@code remove}, {@code previous},
     * {@code set} or {@code add} operations.  This provides
     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
     * the face of concurrent modification during iteration.
     *
     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
     * wishes to provide fail-fast iterators (and list iterators), then it
     * merely has to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides
     * that result in structural modifications to the list).  A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than
     * one to this field, or the iterators (and list iterators) will throw
     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
     * does not wish to provide fail-fast iterators, this field may be
     * ignored.
     */
    protected transient int modCount = 0;
```

modCount是ArrayList中的一个成员变量。它表示该集合实际被修改的次数。

```
List<String> userNames = new ArrayList<String>() {{
    add("Hollis");
    add("hollis");
    add("HollisChuang");
    add("H");
}};
```

当使用以上代码初始化集合之后该变量就有了。初始值为0。

expectedModCount 是 ArrayList中的一个内部类——Itr中的成员变量。

```
Iterator iterator = userNames.iterator();
```

以上代码，即可得到一个 Itr类，该类实现了Iterator接口。

expectedModCount表示这个迭代器预期该集合被修改的次数。其值随着Itr被创建而初始化。只有通过迭代器对集合进行操作，该值才会改变。

那么，接着我们看下userNames.remove(userName);方法里面做了什么事情，为什么会导致expectedModCount和modCount的值不一样。

通过翻阅代码，我们也可以发现，remove方法核心逻辑如下：

```
private void fastRemove(int index) {
    modCount++;
    int numMoved = size - index - 1;
    if (numMoved > 0)
        System.arraycopy(elementData, index+1, elementData, index,
                         numMoved);
    elementData[--size] = null; // clear to let GC do its work
}
```

可以看到，它只修改了modCount，并没有对expectedModCount做任何操作。

简单画一张图描述下以上场景：

<img src="img/15551448234429.jpg" alt="img" style="zoom:50%;" />￼

简单总结一下，**之所以会抛出CMException异常，是因为我们的代码中使用了增强for循环，而在增强for循环中，集合遍历是通过iterator进行的，但是元素的add/remove却是直接使用的集合类自己的方法。这就导致iterator在遍历的时候，会发现有一个元素在自己不知不觉的情况下就被删除/添加了，就会抛出一个异常，用来提示用户，可能发生了并发修改！**

所以，在使用Java的集合类的时候，如果发生CMException，优先考虑fail-fast有关的情况，实际上这里并没有真的发生并发，只是Iterator使用了fail-fast的保护机制，只要他发现有某一次修改是未经过自己进行的，那么就会抛出异常。

关于如何解决这种问题，我们在《为什么阿里巴巴禁止在 foreach 循环里进行元素的 remove/add 操作》中介绍过，这里不再赘述了。

**总结**：

遍历删除List中的元素有很多种方法，当运用不当的时候就会产生问题。下面主要看看以下几种遍历删除List中元素的形式：

1.通过增强的for循环删除符合条件的多个元素

2.通过增强的for循环删除符合条件的一个元素

3.通过普通的for删除删除符合条件的多个元素

4.通过Iterator进行遍历删除符合条件的多个元素

```java
/**  
 * 使用增强的for循环  
 * 在循环过程中从List中删除非基本数据类型以后，继续循环List时会报ConcurrentModificationException 
 */    
public void listRemove() {    
    List<Student> students = this.getStudents();    
    for (Student stu : students) {    
        if (stu.getId() == 2)     
            students.remove(stu);    
    }    
}    

/**  
 * 像这种使用增强的for循环对List进行遍历删除，但删除之后马上就跳出的也不会出现异常  
 */    
public void listRemoveBreak() {    
    List<Student> students = this.getStudents();    
    for (Student stu : students) {    
        if (stu.getId() == 2) {    
            students.remove(stu);    
            break;    
        }    
    }    
}    

/**  
 * 这种不使用增强的for循环的也可以正常删除和遍历,  
 * 这里所谓的正常是指它不会报异常，但是删除后得到的  
 * 数据不一定是正确的，这主要是因为删除元素后，被删除元素后  
 * 的元素索引发生了变化。假设被遍历list中共有10个元素，当  
 * 删除了第3个元素后，第4个元素就变成了第3个元素了，第5个就变成  
 * 了第4个了，但是程序下一步循环到的索引是第4个，  
 * 这时候取到的就是原本的第5个元素了。  
 */    
public void listRemove2() {    
    List<Student> students = this.getStudents();    
    for (int i=0; i<students.size(); i++) {    
        if (students.get(i).getId()%3 == 0) {    
            Student student = students.get(i);    
            students.remove(student);    
        }    
    }    
}    

/**  
 * 使用Iterator的方式可以顺利删除和遍历  
 */    
public void iteratorRemove() {    
    List<Student> students = this.getStudents();    
    System.out.println(students);    
    Iterator<Student> stuIter = students.iterator();    
    while (stuIter.hasNext()) {    
        Student student = stuIter.next();    
        if (student.getId() % 2 == 0)    
            stuIter.remove();//这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException    
    }    
    System.out.println(students);    
}    
```

那么看到以上的几段代码之后，我们来分析一下他的原因，分析原因之前我们先来认识一个词**fail-fast**

> fail-fast 机制是java集合(Collection)中的一种错误机制。当多个线程对同一个集合的内容进行操作时，就可能会产生fail-fast事件。
> 　　例如：当某一个线程A通过iterator去遍历某集合的过程中，若该集合的内容被其他线程所改变了；那么线程A访问集合时，就会抛出ConcurrentModificationException异常，产生fail-fast事件。
> 要了解fail-fast机制，我们首先要对ConcurrentModificationException 异常有所了解。当方法检测到对象的并发修改，但不允许这种修改时就抛出该异常。同时需要注意的是，该异常不会始终指出对象已经由不同线程并发修改，如果单线程违反了规则，同样也有可能会抛出改异常。
> 诚然，迭代器的快速失败行为无法得到保证，它不能保证一定会出现该错误，但是快速失败操作会尽最大努力抛出ConcurrentModificationException异常，所以因此，为提高此类操作的正确性而编写一个依赖于此异常的程序是错误的做法，正确做法是：ConcurrentModificationException 应该仅用于检测 bug。

当使用 `fail-fast iterator` 对 `Collection` 或 Map进行迭代操作过程中尝试直接修改 `Collection / Map` 的内容时，即使是在单线程下运行，`java.util.ConcurrentModificationException` 异常也将被抛出。

Iterator是工作在一个独立的线程中，并且拥有一个 mutex 锁。 Iterator被创建之后会建立一个指向原来对象的单链索引表，当原来的对象数量发生变化时，这个索引表的内容不会同步改变，所以当索引指针往后移动的时候就找不到要迭代的对象，所以按照 fail-fast 原则 Iterator 会马上抛出`java.util.ConcurrentModificationException` 异常。

所以 Iterator 在工作的时候是不允许被迭代的对象被改变的。但你可以使用 Iterator 本身的方法 remove() 来删除对象， Iterator.remove() 方法会在删除当前迭代对象的同时维护索引的一致性。

有意思的是如果你的 Collection / Map 对象实际只有一个元素的时候，`ConcurrentModificationException` 异常并不会被抛出。这也就是为什么在 javadoc 里面指出： `itwould be wrong to write a program that depended on this exception forits correctness: ConcurrentModificationException should be used only todetect bugs`.

附：来自ibm developerworks上对java.util.concurrent包的说明片段： java.util 包中的集合类都返回 fail-fast 迭代器，这意味着它们假设线程在集合内容中进行迭代时，集合不会更改它的内容。如果fail-fast 迭代器检测到在迭代过程中进行了更改操作，那么它会抛出 ConcurrentModificationException，这是不可控异常。 在迭代过程中不更改集合的要求通常会对许多并发应用程序造成不便。相反，比较好的是它允许并发修改并确保迭代器只要进行合理操作，就可以提供集合的一致视图，如 java.util.concurrent 集合类中的迭代器所做的那样。 java.util.concurrent 集合返回的迭代器称为弱一致的（weakly consistent）迭代器。对于这些类，如果元素自从迭代开始已经删除，且尚未由 next()方法返回，那么它将不返回到调用者。如果元素自迭代开始已经添加，那么它可能返回调用者，也可能不返回。在一次迭代中，无论如何更改底层集合，元素不会被返回两次。

### 4.4.3 安全失败

为了避免触发fail-fast机制，导致异常，我们可以使用Java中提供的一些采用了fail-safe机制的集合类。

这样的集合容器在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

java.util.concurrent包下的容器都是fail-safe的，可以在多线程下并发使用，并发修改。同时也可以在foreach中进行add/remove 。

我们拿CopyOnWriteArrayList这个fail-safe的集合类来简单分析一下。

```java
public static void main(String[] args) {
    List<String> userNames = new CopyOnWriteArrayList<String>() {{
        add("Hollis");
        add("hollis");
        add("HollisChuang");
        add("H");
    }};

    userNames.iterator();

    for (String userName : userNames) {
        if (userName.equals("Hollis")) {
            userNames.remove(userName);
        }
    }

    System.out.println(userNames);
}
```

以上代码，使用CopyOnWriteArrayList代替了ArrayList，就不会发生异常。

fail-safe集合的所有对集合的修改都是先拷贝一份副本，然后在副本集合上进行的，并不是直接对原集合进行修改。并且这些修改方法，如add/remove都是通过加锁来控制并发的。

所以，CopyOnWriteArrayList中的迭代器在迭代的过程中不需要做fail-fast的并发检测。（因为fail-fast的主要目的就是识别并发，然后通过异常的方式通知用户）

但是，虽然基于拷贝内容的优点是避免了ConcurrentModificationException，但同样地，迭代器并不能访问到修改后的内容。如以下代码：

```java
public static void main(String[] args) {
    List<String> userNames = new CopyOnWriteArrayList<String>() {{
        add("Hollis");
        add("hollis");
        add("HollisChuang");
        add("H");
    }};

    Iterator it = userNames.iterator();

    for (String userName : userNames) {
        if (userName.equals("Hollis")) {
            userNames.remove(userName);
        }
    }

    System.out.println(userNames);

    while(it.hasNext()){
        System.out.println(it.next());
    }
}
```

我们得到CopyOnWriteArrayList的Iterator之后，通过for循环直接删除原数组中的值，最后在结尾处输出Iterator，结果发现内容如下：

```
[hollis, HollisChuang, H]
Hollis
hollis
HollisChuang
H
```

迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。

### 4.4.4 Copy-On-Write

在了解了[CopyOnWriteArrayList](http://cmsblogs.com/?p=4729)之后，不知道大家会不会有这样的疑问：他的add/remove等方法都已经加锁了，还要copy一份再修改干嘛？多此一举？同样是线程安全的集合，这玩意和Vector有啥区别呢？

Copy-On-Write简称COW，是一种用于程序设计中的优化策略。**其基本思路是，从一开始大家都在共享同一个内容，当某个人想要修改这个内容的时候，才会真正把内容Copy出去形成一个新的内容然后再改，这是一种延时懒惰策略。**

CopyOnWrite容器即写**时复制的容器**。通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。

CopyOnWriteArrayList中add/remove等写方法是需要加锁的，目的是为了避免Copy出N个副本出来，导致并发写。

但是，CopyOnWriteArrayList中的读方法是没有加锁的。

```
public E get(int index) {
    return get(getArray(), index);
}
```

这样做的好处是我们可以对CopyOnWrite容器进行并发的读，当然，这里读到的数据可能不是最新的。因为写时复制的思想是通过延时更新的策略来实现数据的最终一致性的，并非强一致性。

**所以CopyOnWrite容器是一种读写分离的思想，读和写不同的容器。**而Vector在读写的时候使用同一个容器，读写互斥，同时只能做一件事儿。

