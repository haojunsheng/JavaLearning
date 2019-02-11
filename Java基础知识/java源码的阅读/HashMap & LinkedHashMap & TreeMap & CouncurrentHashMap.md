[TOC]

# 前言



# 1. [HashMap完全解读](https://www.hollischuang.com/archives/82)

## 1.1 什么是HashMap

> **基于哈希表的 Map 接口的实现**。此实现提供所有可选的映射操作，**并允许使用 null 值和 null 键。**（除了非同步和允许使用 null 之外，HashMap 类与 Hashtable 大致相同。）**此类不保证映射的顺序，特别是它不保证该顺序恒久不变。** 此实现假定哈希函数将元素适当地分布在各桶之间，可为基本操作（get 和 put）提供稳定的性能。迭代 collection 视图所需的时间与 HashMap 实例的“容量”（桶的数量）及其大小（键-值映射关系数）成比例。所以，如果迭代性能很重要，则不要将初始容量设置得太高（或将加载因子设置得太低）。

## 1.2 HashMap和HashTable的区别

> 1.**HashTable的方法是同步的**，在方法的前面都有synchronized来同步，**HashMap未经同步**，所以在多线程场合要手动同步
> 2.**HashTable不允许null值**(key和value都不可以) ,**HashMap允许null值**(key和value都可以)。
> 3.HashTable有一个contains(Object value)功能和containsValue(Object value)功能一样。
> 4.HashTable使用Enumeration进行遍历，HashMap使用Iterator进行遍历。
> 5.HashTable中hash数组默认大小是11，增加的方式是 old*2+1。HashMap中hash数组的默认大小是16，而且一定是2的指数。
> 6.哈希值的使用不同，HashTable直接使用对象的hashCode，代码是这样的：

```java
int hash = key.hashCode();
int index = (hash & 0x7FFFFFFF) % tab.length;
```

> 而HashMap重新计算hash值，而且用与代替求模：

```java
int hash = hash(k);
int i = indexFor(hash, table.length);
static int hash(Object x) {
h ^= (h >>> 20) ^ (h >>> 12);
     return h ^ (h >>> 7) ^ (h >>> 4);
}
static int indexFor(int h, int length) {
return h & (length-1);
}
```

## 1.3 HashMap与HashSet的关系

> 1、HashSet底层是采用HashMap实现的：
>
> ```java
> public HashSet() {
> map = new HashMap<E,Object>();
> }
> ```
>
> 2、调用HashSet的add方法时，实际上是向HashMap中增加了一行(key-value对)，该行的key就是向HashSet增加的那个对象，该行的value就是一个Object类型的常量。
>
> ```java
> private static final Object PRESENT = new Object(); 
> public boolean add(E e) { 
>     return map.put(e, PRESENT)==null; 
> } 
> public boolean remove(Object o) { 
>     return map.remove(o)==PRESENT; 
> }
> ```

## 1.4 HashMap 和 ConcurrentHashMap 的关系

> 关于这部分内容建议自己去翻翻源码，`ConcurrentHashMap` 也是一种线程安全的集合类，他和`HashTable`也是有区别的，主要区别就是加锁的粒度以及如何加锁，`ConcurrentHashMap`的加锁粒度要比`HashTable`更细一点。将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。

## 1.5 HashMap实现原理分析

### 1.5.1 **HashMap的数据结构** 

数据结构中有`数组`和`链表`来实现对数据的存储，但这两者基本上是两个极端。

> **数组**:数组必须事先定义固定的长度（元素个数），不能适应数据动态地增减的情况。当数据增加时，可能超出原先定义的元素个数；当数据减少时，造成内存浪费。
>
> > 数组是静态分配内存，并且在内存中连续。
> > 数组利用下标定位，时间复杂度为O(1)
> > 数组插入或删除元素的时间复杂度O(n)
> > 数组的特点是：*寻址容易，插入和删除困难*；
>
> **链表**:链表存储区间离散，占用内存比较宽松。
>
> > 链表是动态分配内存，并不连续。
> > 链表定位元素时间复杂度O(n)
> > 链表插入或删除元素的时间复杂度O(1)
> > 链表的特点是：*寻址困难，插入和删除容易。*

### 1.5.2 **哈希表**

那么我们能不能综合两者的特性，做出一种寻址容易，插入删除也容易的数据结构？答案是肯定的，这就是我们要提起的哈希表。`哈希表（(Hash table）`既满足了数据的查找方便，同时不占用太多的内容空间，使用也十分方便。

　　哈希表有多种不同的实现方法，我接下来解释的是最常用的一种方法—— 拉链法，我们可以理解为“链表的数组” ，如图：

![image](https://ws2.sinaimg.cn/large/006tNc79gy1fzqolu10lhj30sg0hamyg.jpg)

　从上图我们可以发现哈希表是由数组+链表组成的，一个长度为16的数组中，每个元素存储的是一个链表的头结点。那么这些元素是按照什么样的规则存储到数组中呢。一般情况是通过hash(key)%len获得，也就是元素的key的哈希值对数组长度取模得到。比如上述哈希表中，12%16=12,28%16=12,108%16=12,140%16=12。所以12、28、108以及140都存储在数组下标为12的位置。 　　HashMap其实也是一个线性的数组实现的,所以可以理解为其存储数据的容器就是一个线性数组。这可能让我们很不解，一个线性的数组怎么实现按键值对来存取数据呢？这里HashMap有做一些处理。 　　首先HashMap里面实现一个静态内部类Entry，其重要的属性有 key , value, next，从属性key,value我们就能很明显的看出来Entry就是HashMap键值对实现的一个基础bean，我们上面说到HashMap的基础就是一个线性数组，这个数组就是Entry[]，Map里面的内容都保存在Entry[]里面。

## 1.6 HashMap的存取实现

既然是线性数组，为什么能随机存取？这里HashMap用了一个小算法，大致是这样实现：

```java
// 存储时:
int hash = key.hashCode(); // 这个hashCode方法这里不详述,只要理解每个key的hash是一个固定的int值
int index = hash % Entry[].length;
Entry[index] = value;

// 取值时:
int hash = key.hashCode();
int index = hash % Entry[].length;
return Entry[index];
```

**1）put**

疑问：如果两个key通过hash%Entry[].length得到的index相同，会不会有覆盖的危险？ 　　

这里HashMap里面用到链式数据结构的一个概念。上面我们提到过Entry类里面有一个`next`属性，作用是指向下一个Entry。
打个比方， 第一个键值对A进来，通过计算其key的hash得到的index=0，记做:Entry[0] = A。一会后又进来一个键值对B，通过计算其index也等于0，现在怎么办？HashMap会这样做:B.next = A,Entry[0] = B,如果又进来C,index也等于0,那么C.next = B,Entry[0] = C；这样我们发现index=0的地方其实存取了A,B,C三个键值对,他们通过next这个属性链接在一起。所以疑问不用担心。也就是说数组中存储的是最后插入的元素。到这里为止，HashMap的大致实现，我们应该已经清楚了。

```java
public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value); //null总是放在数组的第一个链表中
        int hash = hash(key.hashCode());
        int i = indexFor(hash, table.length);
        //遍历链表
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            //如果key在链表中已存在，则替换为新value
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(hash, key, value, i);
        return null;
}

void addEntry(int hash, K key, V value, int bucketIndex) {
    Entry<K,V> e = table[bucketIndex];
    table[bucketIndex] = new Entry<K,V>(hash, key, value, e); //参数e, 是Entry.next
    //如果size超过threshold，则扩充table大小。再散列
    if (size++ >= threshold)
            resize(2 * table.length);
}
```

> 当然HashMap里面也包含一些优化方面的实现，这里也说一下。比如：Entry[]的长度一定后，随着map里面数据的越来越长，这样同一个index的链就会很长，会不会影响性能？HashMap里面设置一个因子，随着map的size越来越大，Entry[]会以一定的规则加长长度。

**2）get**

```java
public V get(Object key) {
        if (key == null)
            return getForNullKey();
        int hash = hash(key.hashCode());
        //先定位到数组元素，再遍历该元素处的链表
        for (Entry<K,V> e = table[indexFor(hash, table.length)];
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
}
```

**3）null key的存取**

null key总是存放在Entry[]数组的第一个元素。

```java
   private V putForNullKey(V value) {
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        addEntry(0, null, value, 0);
        return null;
    }


private V getForNullKey() {
    for (Entry<K,V> e = table[0]; e != null; e = e.next) {
        if (e.key == null)
            return e.value;
    }
    return null;
}
```

**4）确定数组index：hashcode % table.length取模**

HashMap存取时，都需要计算当前key应该对应Entry[]数组哪个元素，即计算数组下标；算法如下：

```java
 /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
    }
```

按位取并，作用上相当于取模mod或者取余%。 **这意味着数组下标相同，并不表示hashCode相同。**

**5）table初始大小**

```java
public HashMap(int initialCapacity, float loadFactor) {
        .....
        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;
        this.loadFactor = loadFactor;
        threshold = (int)(capacity * loadFactor);
        table = new Entry[capacity];
        init();
    }
```

## 1.7 解决hash冲突的办法

> 开放定址法（线性探测再散列，二次探测再散列，伪随机探测再散列） 再哈希法 链地址法 建立一个公共溢出区 **Java中hashmap的解决办法就是采用的链地址法。**

## 1.8 再散列rehash过程

> 当哈希表的容量超过默认容量时，必须调整table的大小。当容量已经达到最大可能值时，那么该方法就将容量调整到Integer.MAX_VALUE返回，这时，需要创建一张新表，将原表的映射到新表中。

```java
 /**
     * Rehashes the contents of this map into a new array with a
     * larger capacity.  This method is called automatically when the
     * number of keys in this map reaches its threshold.
     *
     * If current capacity is MAXIMUM_CAPACITY, this method does not
     * resize the map, but sets threshold to Integer.MAX_VALUE.
     * This has the effect of preventing future calls.
     *
     * @param newCapacity the new capacity, MUST be a power of two;
     *        must be greater than current capacity unless current
     *        capacity is MAXIMUM_CAPACITY (in which case value
     *        is irrelevant).
     */
    void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }



/**
 * Transfers all entries from current table to newTable.
 */
void transfer(Entry[] newTable) {
    Entry[] src = table;
    int newCapacity = newTable.length;
    for (int j = 0; j < src.length; j++) {
        Entry<K,V> e = src[j];
        if (e != null) {
            src[j] = null;
            do {
                Entry<K,V> next = e.next;
                //重新计算index
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            } while (e != null);
        }
    }
}
```

