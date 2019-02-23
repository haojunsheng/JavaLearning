[TOC]

# 前言

[文档](https://help.anylogic.com/index.jsp?topic=%2Fcom.anylogic.help%2Fhtml%2Fcode%2FArrays_Collections.html)

Java offers two types of constructs where you can store multiple values or objects of the same type: arrays and collections (for System Dynamics models AnyLogic also offers HyperArray, also known as "subscripts", – a special type of collection for dynamic variables).

**Array or collection?** Arrays are simple constructs with linear storage of fixed size and therefore they can only store a given number of elements. Arrays are built into the core of Java language and the array-related Java syntax is very easy and straightforward, for example the nth element of the array can be obtained as array[n-1]. Collections are more sophisticated and flexible. First of all, they are resizable: you can add any number of elements to a collection. A collection will automatically handle deletion of an element from any position. There are several types of collections with different internal storage structure (linear, list, hash set, tree, etc.) and you can choose a collection type best matching your problem so that your most frequent operations will be convenient and efficient. Collections are Java classes and syntax for obtaining, e.g., the nth element of a collection of type ArrayList is collection.get(n).

![img](https://ws1.sinaimg.cn/large/006tKfTcly1g0gcickvh3j30c70bvglx.jpg)

上面的大概意思是说：我们可以使用数组或者容器来存储值或者对象。我们需要进行什么样的选择呢？

1. 数组是**将数字和对象联系起来**，它**保存明确的对象**，查询对象时候不需要对查询结果进行转换，它可以是多维的，可以保存基本类型的数据，**但是数组一旦生成，其容量不能改变**。所以数组是不可以直接删除和添加元素。
2. Collection 保存单一的元素，而 Map 保存相关联的值键对，有了 Java 泛型，可以指定容器存放对象类型，不会将错误类型的对象放在容器中，取元素时候也不需要转型。而且 Collection 和 Map 都可以自动调整其尺寸。**容器不可以持有基本类型**。



Arrays和Collections都提供了静态方法方便我们去操作。