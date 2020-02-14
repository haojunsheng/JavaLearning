[TOC]

# 前言

什么是快速失败（fail-fast）和安全失败（fail-safe）？它们又和什么内容有关系。以上两点就是这篇文章的内容，废话不多话，正文请慢用。

我们都接触 HashMap、ArrayList 这些集合类，这些在 java.util 包的集合类就都是快速失败的；而  java.util.concurrent 包下的类都是安全失败，比如：ConcurrentHashMap。

# 1. 快速失败

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

# 2. 安全失败

明白了什么是快速失败之后，安全失败也是非常好理解的。



采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。



由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，故不会抛 ConcurrentModificationException 异常



我们上代码看下是不是这样

```
ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
concurrentHashMap.put("不只Java-1", 1);
concurrentHashMap.put("不只Java-2", 2);
concurrentHashMap.put("不只Java-3", 3);

Set set = concurrentHashMap.entrySet();
Iterator iterator = set.iterator();

while (iterator.hasNext()) {
    System.out.println(iterator.next());
    concurrentHashMap.put("下次循环正常执行", 4);
}
System.out.println("程序结束");
```





运行效果如下，的确不会抛异常，程序正常执行。

![](https://ws4.sinaimg.cn/large/006tKfTcly1g0gd47y2utj306a03jglk.jpg)

最后说明一下，快速失败和安全失败是对迭代器而言的。并发环境下建议使用 java.util.concurrent 包下的容器类，除非没有修改操作。