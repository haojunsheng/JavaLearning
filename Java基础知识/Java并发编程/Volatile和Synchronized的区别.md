Volatile和Synchronized的区别：

二者都是为了线程安全进行设计的，为了保证变量的原子性（Volatile无法保证），可见性，有序性，但是他们的实现机制是不同的。



其中Synchronized对原子性的保证是通过（monitorenter和monitorexit）来实现的，对可见性是通过(加锁来实现的），对有序性是通过（as-if-serial）语义来实现的。

Volatile对可见性是通过（强制刷新内存，强制从内存读进行实现的），对有序性是通过禁止指令重排实现的。但是不能保证原子性，因为并没有加锁。



Volatile只能修饰变量，不能修饰方法和代码块，Synchronized则都可以。

