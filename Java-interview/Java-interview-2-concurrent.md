## （一）高并发编程基础知识

> 这里涉及到一些基础的概念，我重新捧起了一下《实战 Java 高并发程序设计》这一本书，感觉到心潮澎湃，这或许就是笔者叙述功底扎实的魅力吧，喜欢。对于并发的基础可以参照一下我之前写过的一篇博文：[Java学习笔记（4）——并发基础](https://www.jianshu.com/p/cd9d0927be35)

#### 1）多线程和单线程的区别和联系？

答：

1. 在单核 CPU 中，将 CPU 分为很小的时间片，在每一时刻只能有一个线程在执行，是一种微观上轮流占用 CPU 的机制。
2. 多线程会存在线程上下文切换，会导致程序执行速度变慢，即采用一个拥有两个线程的进程执行所需要的时间比一个线程的进程执行两次所需要的时间要多一些。

结论：即采用多线程不会提高程序的执行速度，反而会降低速度，但是对于用户来说，可以减少用户的响应时间。

**面试官：那使用多线程有什么优势？**

解析：尽管面临很多挑战，多线程有一些优点仍然使得它一直被使用，而这些优点我们应该了解。

答：

（1）资源利用率更好

想象一下，一个应用程序需要从本地文件系统中读取和处理文件的情景。比方说，从磁盘读取一个文件需要5秒，处理一个文件需要2秒。处理两个文件则需要：

```
1| 5秒读取文件A
2| 2秒处理文件A
3| 5秒读取文件B
4| 2秒处理文件B
5| ---------------------
6| 总共需要14秒
```

从磁盘中读取文件的时候，大部分的CPU时间用于等待磁盘去读取数据。在这段时间里，CPU非常的空闲。它可以做一些别的事情。通过改变操作的顺序，就能够更好的使用CPU资源。看下面的顺序：

```
1| 5秒读取文件A
2| 5秒读取文件B + 2秒处理文件A
3| 2秒处理文件B
4| ---------------------
5| 总共需要12秒
```

CPU等待第一个文件被读取完。然后开始读取第二个文件。当第二文件在被读取的时候，CPU会去处理第一个文件。记住，在等待磁盘读取文件的时候，CPU大部分时间是空闲的。

总的说来，CPU能够在等待IO的时候做一些其他的事情。这个不一定就是磁盘IO。它也可以是网络的IO，或者用户输入。通常情况下，网络和磁盘的IO比CPU和内存的IO慢的多。

（2）程序设计在某些情况下更简单

在单线程应用程序中，如果你想编写程序手动处理上面所提到的读取和处理的顺序，你必须记录每个文件读取和处理的状态。相反，你可以启动两个线程，每个线程处理一个文件的读取和操作。线程会在等待磁盘读取文件的过程中被阻塞。在等待的时候，其他的线程能够使用CPU去处理已经读取完的文件。其结果就是，磁盘总是在繁忙地读取不同的文件到内存中。这会带来磁盘和CPU利用率的提升。而且每个线程只需要记录一个文件，因此这种方式也很容易编程实现。

（3）程序响应更快

有时我们会编写一些较为复杂的代码（这里的复杂不是说复杂的算法，而是复杂的业务逻辑），例如，一笔订单的创建，它包括插入订单数据、生成订单赶快找、发送邮件通知卖家和记录货品销售数量等。用户从单击“订购”按钮开始，就要等待这些操作全部完成才能看到订购成功的结果。但是这么多业务操作，如何能够让其更快地完成呢？

在上面的场景中，可以使用多线程技术，即将数据一致性不强的操作派发给其他线程处理（也可以使用消息队列），如生成订单快照、发送邮件等。这样做的好处是响应用户请求的线程能够尽可能快地处理完成，缩短了响应时间，提升了用户体验。

> **多线程还有一些优势也显而易见：**
> ① 进程之前不能共享内存，而线程之间共享内存(堆内存)则很简单。
> ② 系统创建进程时需要为该进程重新分配系统资源,创建线程则代价小很多,因此实现多任务并发时,多线程效率更高.
> ③ Java语言本身内置多线程功能的支持,而不是单纯第作为底层系统的调度方式,从而简化了多线程编程.

#### 2）多线程一定快吗？

答：不一定。

比如，我们尝试使用并行和串行来分别执行累加的操作观察是否并行执行一定比串行执行更快：

![img](https://upload-images.jianshu.io/upload_images/7896890-a698017d4c0dbf79.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

以下是我测试的结果，可以看出，当不超过1百万的时候，并行是明显比串行要慢的，为什么并发执行的速度会比串行慢呢？这是因为线程有创建和上下文切换的开销。

![img](https://upload-images.jianshu.io/upload_images/7896890-e515dc406651240b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 3）什么是同步？什么又是异步？

解析：这是对多线程基础知识的考察

答：同步和异步通常用来形容一次方法调用。

同步方法调用一旦开始，调用者必须等到方法返回后，才能继续后续的行为。这就好像是我们去商城买一台空调，你看中了一台空调，于是就跟售货员下了单，然后售货员就去仓库帮你调配物品，这天你热的实在不行，就催着商家赶紧发货，于是你就在商店里等着，知道商家把你和空调都送回家，一次愉快的购物才结束，这就是同步调用。

而异步方法更像是一个消息传递，一旦开始，方法调用就会立即返回，调用者就可以继续后续的操作。回到刚才买空调的例子，我们可以坐在里打开电脑，在网上订购一台空调。当你完成网上支付的时候，对你来说购物过程已经结束了。虽然空调还没有送到家，但是你的任务都已经完成了。商家接到你的订单后，就会加紧安排送货，当然这一切已经跟你无关了，你已经支付完成，想什么就能去干什么了，出去溜达几圈都不成问题。等送货上门的时候，接到商家电话，回家一趟签收即可。这就是异步调用。

![img](https://upload-images.jianshu.io/upload_images/7896890-a8d7703b4874703a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**面试官：那并发（Concurrency）和并行（Parallelism）的区别呢？**

解析：并行性和并发性是既相似又有区别的两个概念。

**答：并行性是指两个或多个事件在同一时刻发生。而并发性是指连个或多个事件在同一时间间隔内发生。**

![img](https://upload-images.jianshu.io/upload_images/7896890-672dfca5db6de845.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在多道程序环境下，并发性是指在一段时间内宏观上有多个程序在同时运行，但在单处理机环境下（一个处理器），每一时刻却仅能有一道程序执行，故微观上这些程序只能是分时地交替执行。例如，在1秒钟时间内，0-15ms程序A运行；15-30ms程序B运行；30-45ms程序C运行；45-60ms程序D运行，因此可以说，在1秒钟时间间隔内，宏观上有四道程序在同时运行，但微观上，程序A、B、C、D是分时地交替执行的。

如果在计算机系统中有多个处理机，这些可以并发执行的程序就可以被分配到多个处理机上，实现并发执行，即利用每个处理机处理一个可并发执行的程序。这样，多个程序便可以同时执行。以此就能提高系统中的资源利用率，增加系统的吞吐量。

#### 4）线程和进程的区别：（必考）

答：

1. 进程是一个 “执行中的程序”，是系统进行资源分配和调度的一个独立单位；
2. 线程是进程的一个实体，一个进程中拥有多个线程，线程之间共享地址空间和其它资源（所以通信和同步等操作线程比进程更加容易）；
3. 线程上下文的切换比进程上下文切换要快很多。
   - （1）进程切换时，涉及到当前进程的 CPU 环境的保存和新被调度运行进程的 CPU 环境的设置。
   - （2）线程切换仅需要保存和设置少量的寄存器内容，不涉及存储管理方面的操作。

**面试官：进程间如何通讯？线程间如何通讯？**

答：进程间通讯依靠 IPC 资源，例如管道（pipes）、套接字（sockets）等；

线程间通讯依靠 JVM 提供的 API，例如 wait()、notify()、notifyAll() 等方法，线程间还可以通过共享的主内存来进行值的传递。

> 关于线程和进程有一篇写得非常不错的文章，不过是英文的，我进行了翻译，相信阅读之后会对进程和线程有不一样的理解：[线程和进程基础——翻译文](https://www.jianshu.com/p/11e6cb1c3d38)

#### 5）什么是阻塞（Blocking）和非阻塞（Non-Blocking）？

答：阻塞和非阻塞通常用来形容多线程间的相互影响。比如一个线程占用了临界区资源，那么其他所有需要这个而资源的线程就必须在这个临界区中进行等待。等待会导致线程挂起，这种情况就是阻塞。此时，如果占用资源的线程一直不愿意释放资源，那么其他所有阻塞在这个临界区上的线程都不能工作。

非阻塞的意思与之相反，它强调没有一个线程可以妨碍其他线程执行。所有的线程都会尝试不断前向执行。

**面试官：临界区是什么？**

答：临界区用来表示一种公共资源或者说是共享资源，可以被多个线程使用。但是每一次，只能有一个线程使用它，一旦临界区资源被占用，其他线程要想使用这个资源，就必须等待。

比如，在一个办公室里有一台打印机，打印机一次只能执行一个任务。如果小王和小明同时需要打印文件，很显然，如果小王先下发了打印任务，打印机就开始打印小王的文件了，小明的任务就只能等待小王打印结束后才能打印，这里的打印机就是一个临界区的例子。

在并行程序中，临界区资源是保护的对象，如果意外出现打印机同时执行两个打印任务，那么最可能的结果就是打印出来的文件就会是损坏的文件，它既不是小王想要的，也不是小明想要的。

#### 6）什么是死锁（Deadlock）、饥饿（Starvation）和活锁（Livelock）？

答：死锁、饥饿和活锁都属于多线程的活跃性问题，如果发现上述几种情况，那么相关线程可能就不再活跃，也就说它可能很难再继续往下执行了。

![img](https://upload-images.jianshu.io/upload_images/7896890-a6041256aed650a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 死锁应该是最糟糕的一种情况了，它表示两个或者两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。
2. 饥饿是指某一个或者多个线程因为种种原因无法获得所需要的资源，导致一直无法执行。比如：
   1）它的线程优先级可能太低，而高优先级的线程不断抢占它需要的资源，导致低优先级的线程无法工作。在自然界中，母鸡喂食雏鸟时，很容易出现这种情况，由于雏鸟很多，食物有限，雏鸟之间的食物竞争可能非常厉害，小雏鸟因为经常抢不到食物，有可能会被饿死。线程的饥饿也非常类似这种情况。
   2）另外一种可能是，某一个线程一直占着关键资源不放，导致其他需要这个资源的线程无法正常执行，这种情况也是饥饿的一种。
   与死锁相比，饥饿还是有可能在未来一段时间内解决的（比如高优先级的线程已经完成任务，不再疯狂的执行）
3. 活锁是一种非常有趣的情况。不知道大家是不是有遇到过这样一种情况，当你要坐电梯下楼，电梯到了，门开了，这时你正准备出去，但不巧的是，门外一个人挡着你的去路，他想进来。于是你很绅士的靠左走，避让对方，但同时对方也很绅士，但他靠右走希望避让你。结果，你们又撞上了。于是乎，你们都意识到了问题，希望尽快避让对方，你立即向右走，他也立即向左走，结果又撞上了！不过介于人类的只能，我相信这个动作重复 2、 3 次后，你应该可以顺利解决这个问题，因为这个时候，大家都会本能的对视，进行交流，保证这种情况不再发生。
   但如果这种情况发生在两个线程间可能就不会那么幸运了，如果线程的智力不够，且都秉承着 “谦让” 的原则，主动将资源释放给他人使用，那么就会出现资源不断在两个线程中跳动，而没有一个线程可以同时拿到所有的资源而正常执行。这种情况就是活锁。

#### 7）多线程产生死锁的 4 个必要条件？

答：

1. 互斥条件：一个资源每次只能被一个线程使用；
2. 请求与保持条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放；
3. 不剥夺条件：进程已经获得的资源，在未使用完之前，不能强行剥夺；
4. 循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系。

**面试官：如何避免死锁？（经常接着问这个问题哦~）**

答：指定获取锁的顺序，举例如下：

1. 比如某个线程只有获得 A 锁和 B 锁才能对某资源进行操作，在多线程条件下，如何避免死锁？
2. 获得锁的顺序是一定的，比如规定，只有获得 A 锁的线程才有资格获取 B 锁，按顺序获取锁就可以避免死锁！！！

#### 8）如何指定多个线程的执行顺序？

解析：面试官会给你举个例子，如何让 10 个线程按照顺序打印 0123456789？（写代码实现）

答：

1. 设定一个 orderNum，每个线程执行结束之后，更新 orderNum，指明下一个要执行的线程。并且唤醒所有的等待线程。
2. 在每一个线程的开始，要 while 判断 orderNum 是否等于自己的要求值！！不是，则 wait，是则执行本线程。

#### 9）Java 中线程有几种状态？

答：六种（查看 Java 源码也可以看到是 6 种），并且某个时刻 Java 线程只能处于其中的一个状态。

![img](https://upload-images.jianshu.io/upload_images/7896890-7b69db5925631d29.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

1. 新建（NEW）状态：表示新创建了一个线程对象，而此时线程并没有开始执行。
2. 可运行（RUNNABLE）状态：线程对象创建后，其它线程（比如 main 线程）调用了该对象的 start() 方法，才表示线程开始执行。当线程执行时，处于 RUNNBALE 状态，表示线程所需的一切资源都已经准备好了。该状态的线程位于可运行线程池中，等待被线程调度选中，获取 cpu 的使用权。
3. 阻塞（BLOCKED）状态：如果线程在执行过程终于到了 synchronized 同步块，就会进入 BLOCKED 阻塞状态，这时线程就会暂停执行，直到获得请求的锁。
4. 等待（WAITING）状态：当线程等待另一个线程通知调度器一个条件时，它自己进入等待状态。在调用Object.wait方法或Thread.join方法，或者是等待java.util.concurrent库中的Lock或Condition时，就会出现这种情况；
5. 计时等待（TIMED_WAITING）状态：Object.wait、Thread.join、Lock.tryLock和Condition.await 等方法有超时参数，还有 Thread.sleep 方法、LockSupport.parkNanos 方法和 LockSupport.parkUntil 方法，这些方法会导致线程进入计时等待状态，如果超时或者出现通知，都会切换会可运行状态；
6. 终止（TERMINATED）状态：当线程执行完毕，则进入该状态，表示结束。

注意：从 NEW 状态出发后，线程不能再回到 NEW 状态，同理，处于 TERMINATED 状态的线程也不能再回到 RUNNABLE 状态。

------

## （二）高并发编程-JUC 包

> 在 Java 5.0 提供了 java.util.concurrent（简称 JUC ）包，在此包中增加了在并发编程中很常用的实用工具类，用于定义类似于线程的自定义子系统，包括线程池、异步 IO 和轻量级任务框架。

#### 1）sleep( ) 和 wait( n)、wait( ) 的区别：

答：

1. sleep 方法：是 Thread 类的静态方法，当前线程将睡眠 n 毫秒，线程进入阻塞状态。当睡眠时间到了，会解除阻塞，进行可运行状态，等待 CPU 的到来。睡眠不释放锁（如果有的话）；
2. wait 方法：是 Object 的方法，必须与 synchronized 关键字一起使用，线程进入阻塞状态，当 notify 或者 notifyall 被调用后，会解除阻塞。但是，只有重新占用互斥锁之后才会进入可运行状态。睡眠时，释放互斥锁。

#### 2）synchronized 关键字：

答：底层实现：

1. 进入时，执行 monitorenter，将计数器 +1，释放锁 monitorexit 时，计数器-1；
2. 当一个线程判断到计数器为 0 时，则当前锁空闲，可以占用；反之，当前线程进入等待状态。

含义：（monitor 机制）

Synchronized 是在加锁，加对象锁。对象锁是一种重量锁（monitor），synchronized 的锁机制会根据线程竞争情况在运行时会有偏向锁（单一线程）、轻量锁（多个线程访问 synchronized 区域）、对象锁（重量锁，多个线程存在竞争的情况）、自旋锁等。

该关键字是一个几种锁的封装。

#### 3）volatile 关键字：

答：该关键字可以保证可见性不保证原子性。

功能：

1. 主内存和工作内存，直接与主内存产生交互，进行读写操作，保证可见性；
2. 禁止 JVM 进行的指令重排序。

解析：关于指令重排序的问题，可以查阅 DCL 双检锁失效相关资料。

#### 4）volatile 能使得一个非原子操作变成原子操作吗？

答：能。

一个典型的例子是在类中有一个 long 类型的成员变量。如果你知道该成员变量会被多个线程访问，如计数器、价格等，你最好是将其设置为 volatile。为什么？因为 Java 中读取 long 类型变量不是原子的，需要分成两步，如果一个线程正在修改该 long 变量的值，另一个线程可能只能看到该值的一半（前 32 位）。但是对一个 volatile 型的 long 或 double 变量的读写是原子。

**面试官：volatile 修饰符的有过什么实践？**

答：

1. 一种实践是用 volatile 修饰 long 和 double 变量，使其能按原子类型来读写。double 和 long 都是64位宽，因此对这两种类型的读是分为两部分的，第一次读取第一个 32 位，然后再读剩下的 32 位，这个过程不是原子的，但 Java 中 volatile 型的 long 或 double 变量的读写是原子的。
2. volatile 修复符的另一个作用是提供内存屏障（memory barrier），例如在分布式框架中的应用。简单的说，就是当你写一个 volatile 变量之前，Java 内存模型会插入一个写屏障（write barrier），读一个 volatile 变量之前，会插入一个读屏障（read barrier）。意思就是说，在你写一个 volatile 域时，能保证任何线程都能看到你写的值，同时，在写之前，也能保证任何数值的更新对所有线程是可见的，因为内存屏障会将其他所有写的值更新到缓存。

#### 5）ThreadLocal（线程局部变量）关键字：

答：当使用 ThreadLocal 维护变量时，其为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立的改变自己的副本，而不会影响其他线程对应的副本。

ThreadLocal 内部实现机制：

1. 每个线程内部都会维护一个类似 HashMap 的对象，称为 ThreadLocalMap，里边会包含若干了 Entry（K-V 键值对），相应的线程被称为这些 Entry 的属主线程；
2. Entry 的 Key 是一个 ThreadLocal 实例，Value 是一个线程特有对象。Entry 的作用即是：为其属主线程建立起一个 ThreadLocal 实例与一个线程特有对象之间的对应关系；
3. Entry 对 Key 的引用是弱引用；Entry 对 Value 的引用是强引用。

![img](https://upload-images.jianshu.io/upload_images/7896890-219b164cba63247a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 6）线程池有了解吗？（必考）

答：java.util.concurrent.ThreadPoolExecutor 类就是一个线程池。客户端调用 ThreadPoolExecutor.submit(Runnable task) 提交任务，线程池内部维护的工作者线程的数量就是该线程池的线程池大小，有 3 种形态：

> - 当前线程池大小 ：表示线程池中实际工作者线程的数量；
> - 最大线程池大小 （maxinumPoolSize）：表示线程池中允许存在的工作者线程的数量上限；
> - 核心线程大小 （corePoolSize ）：表示一个不大于最大线程池大小的工作者线程数量上限。

1. 如果运行的线程少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队；
2. 如果运行的线程等于或者多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不是添加新线程；
3. 如果无法将请求加入队列，即队列已经满了，则创建新的线程，除非创建此线程超出 maxinumPoolSize， 在这种情况下，任务将被拒绝。

**面试官：我们为什么要使用线程池？**

答：

1. 减少创建和销毁线程的次数，每个工作线程都可以被重复利用，可执行多个任务。
2. 可以根据系统的承受能力，调整线程池中工作线程的数目，放置因为消耗过多的内存，而把服务器累趴下（每个线程大约需要 1 MB 内存，线程开的越多，消耗的内存也就越大，最后死机）

**面试官：核心线程池内部实现了解吗？**

答：对于核心的几个线程池，无论是 newFixedThreadPool() 方法，newSingleThreadExecutor() 还是 newCachedThreadPool() 方法，虽然看起来创建的线程有着完全不同的功能特点，但其实内部实现均使用了 ThreadPoolExecutor 实现，其实都只是 ThreadPoolExecutor 类的封装。

为何 ThreadPoolExecutor 有如此强大的功能呢？我们可以来看一下 ThreadPoolExecutor 最重要的构造函数：

```
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)
```

函数的参数含义如下：

- corePoolSize：指定了线程池中的线程数量
- maximumPoolSize：指定了线程池中的最大线程数量
- keepAliveTime：当线程池线程数量超过 corePoolSize 时，多余的空闲线程的存活时间。即，超过了 corePoolSize 的空闲线程，在多长时间内，会被销毁。
- unit: keepAliveTime 的单位。
- workQueue：任务队列，被提交但尚未被执行的任务。
- threadFactory：线程工厂，用于创建线程，一般用默认的即可。
- handler：拒绝策略。当任务太多来不及处理，如何拒绝任务。

#### 7）Atomic关键字：

**答：可以使基本数据类型以原子的方式实现自增自减等操作。参考博客：concurrent.atomic包下的类AtomicInteger的使用**

#### 8）创建线程有哪几种方式？

答：有两种创建线程的方法：一是实现Runnable接口，然后将它传递给Thread的构造函数，创建一个Thread对象;二是直接继承Thread类。

**面试官：两种方式有什么区别呢？**

1. 继承方式:
   - （1）Java中类是单继承的,如果继承了Thread了,该类就不能再有其他的直接父类了.
   - （2）从操作上分析,继承方式更简单,获取线程名字也简单.(操作上,更简单)
   - （3）从多线程共享同一个资源上分析,继承方式不能做到.
2. 实现方式:
   - （1）Java中类可以多实现接口,此时该类还可以继承其他类,并且还可以实现其他接口(设计上,更优雅).
   - （2）从操作上分析,实现方式稍微复杂点,获取线程名字也比较复杂,得使用Thread.currentThread()来获取当前线程的引用.
   - （3）从多线程共享同一个资源上分析,实现方式可以做到(是否共享同一个资源).

#### 9）run() 方法和 start() 方法有什么区别？

答：start() 方法会新建一个线程并让这个线程执行 run() 方法；而直接调用 run() 方法知识作为一个普通的方法调用而已，它只会在当前线程中，串行执行 run() 中的代码。

#### 10）你怎么理解线程优先级？

答：Java 中的线程可以有自己的优先级。优先极高的线程在竞争资源时会更有优势，更可能抢占资源，当然，这只是一个概率问题。如果运行不好，高优先级线程可能也会抢占失败。

由于线程的优先级调度和底层操作系统有密切的关系，在各个平台上表现不一，并且这种优先级产生的后果也可能不容易预测，无法精准控制，比如一个低优先级的线程可能一直抢占不到资源，从而始终无法运行，而产生饥饿（虽然优先级低，但是也不能饿死它啊）。因此，在要求严格的场合，还是需要自己在应用层解决线程调度的问题。

在 Java 中，使用 1 到 10 表示线程优先级，一般可以使用内置的三个静态标量表示：

```
public final static int MIN_PRIORITY = 1;
public final static int NORM_PRIORITY = 5;
public final static int MAX_PRIORITY = 10;
```

数字越大则优先级越高，但有效范围在 1 到 10 之间，默认的优先级为 5 。

#### 11）在 Java 中如何停止一个线程？

答：Java 提供了很丰富的 API 但没有为停止线程提供 API 。

JDK 1.0 本来有一些像 stop()，suspend() 和 resume() 的控制方法但是由于潜在的死锁威胁因此在后续的 JDK 版本中他们被弃用了，之后 Java API 的设计者就没有提供一个兼容且线程安全的方法来停止任何一个线程。

当 run() 或者 call() 方法执行完的时候线程会自动结束，如果要手动结束一个线程，你可以用 volatile 布尔变量来退出 run() 方法的循环或者是取消任务来中断线程。

#### 12）多线程中的忙循环是什么？

答：忙循环就是程序员用循环让一个线程等待，不像传统方法 wait(),sleep() 或yield() 它们都放弃了 CPU 控制权，而忙循环不会放弃 CPU，它就是在运行一个空循环。这么做的目的是为了保留 CPU 缓存。

在多核系统中，一个等待线程醒来的时候可能会在另一个内核运行，这样会重建缓存，为了避免重建缓存和减少等待重建的时间就可以使用它了。

#### 13）10 个线程和 2 个线程的同步代码，哪个更容易写？

答：从写代码的角度来说，两者的复杂度是相同的，因为同步代码与线程数量是相互独立的。但是同步策略的选择依赖于线程的数量，因为越多的线程意味着更大的竞争，所以你需要利用同步技术，如锁分离，这要求更复杂的代码和专业知识。

#### 14）你是如何调用 wait（）方法的？使用 if 块还是循环？为什么？

答：wait() 方法应该在循环调用，因为当线程获取到 CPU 开始执行的时候，其他条件可能还没有满足，所以在处理前，循环检测条件是否满足会更好。下面是一段标准的使用 wait 和 notify 方法的代码：

```
// The standard idiom for using the wait method
synchronized (obj) {
while (condition does not hold)
obj.wait(); // (Releases lock, and reacquires on wakeup)
... // Perform action appropriate to condition
}
```

参见 Effective Java 第 69 条，获取更多关于为什么应该在循环中来调用 wait 方法的内容。

#### 15）什么是多线程环境下的伪共享（false sharing）？

答：伪共享是多线程系统（每个处理器有自己的局部缓存）中一个众所周知的性能问题。伪共享发生在不同处理器的上的线程对变量的修改依赖于相同的缓存行，如下图所示：

![img](https://upload-images.jianshu.io/upload_images/7896890-3cbee585a5f68f02.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

伪共享问题很难被发现，因为线程可能访问完全不同的全局变量，内存中却碰巧在很相近的位置上。如其他诸多的并发问题，避免伪共享的最基本方式是仔细审查代码，根据缓存行来调整你的数据结构。

#### 16）用 wait-notify 写一段代码来解决生产者-消费者问题？

解析：这是常考的基础类型的题，只要记住在同步块中调用 wait() 和 notify()方法，如果阻塞，通过循环来测试等待条件。

答：

```
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java program to solve Producer Consumer problem using wait and notify
 * method in Java. Producer Consumer is also a popular concurrency design pattern.
 *
 * @author Javin Paul
 */
public class ProducerConsumerSolution {

    public static void main(String args[]) {
        Vector sharedQueue = new Vector();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
        prodThread.start();
        consThread.start();
    }
}

class Producer implements Runnable {

    private final Vector sharedQueue;
    private final int SIZE;

    public Producer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        for (int i = 0; i < 7; i++) {
            System.out.println("Produced: " + i);
            try {
                produce(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void produce(int i) throws InterruptedException {

        // wait if queue is full
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                                    + " is waiting , size: " + sharedQueue.size());

                sharedQueue.wait();
            }
        }

        // producing element and notify consumers
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            sharedQueue.notifyAll();
        }
    }
}

class Consumer implements Runnable {

    private final Vector sharedQueue;
    private final int SIZE;

    public Consumer(Vector sharedQueue, int size) {
        this.sharedQueue = sharedQueue;
        this.SIZE = size;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumed: " + consume());
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private int consume() throws InterruptedException {
        // wait if queue is empty
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                                    + " is waiting , size: " + sharedQueue.size());

                sharedQueue.wait();
            }
        }

        // Otherwise consume element and notify waiting producer
        synchronized (sharedQueue) {
            sharedQueue.notifyAll();
            return (Integer) sharedQueue.remove(0);
        }
    }
}

Output:
Produced: 0
Queue is empty Consumer is waiting , size: 0
Produced: 1
Consumed: 0
Produced: 2
Produced: 3
Produced: 4
Produced: 5
Queue is full Producer is waiting , size: 4
Consumed: 1
Produced: 6
Queue is full Producer is waiting , size: 4
Consumed: 2
Consumed: 3
Consumed: 4
Consumed: 5
Consumed: 6
Queue is empty Consumer is waiting , size: 0
```

#### 17）用 Java 写一个线程安全的单例模式（Singleton）？

解析：有多种方法，但重点掌握的是双重校验锁。

答：

1.饿汉式单例

饿汉式单例是指在方法调用前，实例就已经创建好了。下面是实现代码：

```
public class Singleton {

    private static Singleton instance = new Singleton();

    private Singleton (){}

    public static Singleton getInstance() {
        return instance;
    }
}
```

2.加入 synchronized 的懒汉式单例

所谓懒汉式单例模式就是在调用的时候才去创建这个实例，我们在对外的创建实例方法上加如 synchronized 关键字保证其在多线程中很好的工作：

```
public class Singleton {    

    private static Singleton instance;    

    private Singleton (){}    

    public static synchronized Singleton getInstance() {    
        if (instance == null) {    
            instance = new Singleton();    
    }    
    return instance;    
    }    
}  
```

3.使用静态内部类的方式创建单例

这种方式利用了 classloder 的机制来保证初始化 instance 时只有一个线程，它跟饿汉式的区别是：饿汉式只要 Singleton 类被加载了，那么 instance 就会被实例化（没有达到 lazy loading 的效果），而这种方式是 Singleton 类被加载了，instance 不一定被初始化。只有显式通过调用 getInstance() 方法时才会显式装载 SingletonHoder 类，从而实例化 singleton

```
public class Singleton {

    private Singleton() {
    }

    private static class SingletonHolder {// 静态内部类  
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHolder.singleton;
    }
}
```

4.双重校验锁

为了达到线程安全，又能提高代码执行效率，我们这里可以采用DCL的双检查锁机制来完成，代码实现如下：

```
public class Singleton {  
  
    private static Singleton singleton;  

    private Singleton() {  
    }  

    public static Singleton getInstance(){  
        if (singleton == null) {  
            synchronized (Singleton.class) {  
                if (singleton == null) {  
                    singleton = new Singleton();  
                }  
            }  
        }  
        return singleton;  
    }  
} 
```

这种是用双重判断来创建一个单例的方法，那么我们为什么要使用两个if判断这个对象当前是不是空的呢 ？因为当有多个线程同时要创建对象的时候，多个线程有可能都停止在第一个if判断的地方，等待锁的释放，然后多个线程就都创建了对象，这样就不是单例模式了，所以我们要用两个if来进行这个对象是否存在的判断。

5.使用 static 代码块实现单例

静态代码块中的代码在使用类的时候就已经执行了，所以可以应用静态代码块的这个特性的实现单例设计模式。

```
public class Singleton{  
       
    private static Singleton instance = null;  
       
    private Singleton(){}  
  
    static{  
        instance = new Singleton();  
    }  
      
    public static Singleton getInstance() {   
        return instance;  
    }   
}  
```

6.使用枚举数据类型实现单例模式

枚举enum和静态代码块的特性相似，在使用枚举时，构造方法会被自动调用，利用这一特性也可以实现单例：

```
public class ClassFactory{   
      
    private enum MyEnumSingleton{  
        singletonFactory;  
          
        private MySingleton instance;  
          
        private MyEnumSingleton(){//枚举类的构造方法在类加载是被实例化  
            instance = new MySingleton();  
        }  
   
        public MySingleton getInstance(){  
            return instance;  
        }  
    }   
   
    public static MySingleton getInstance(){  
        return MyEnumSingleton.singletonFactory.getInstance();  
    }  
}  
```

> 小结：关于 Java 中多线程编程，线程安全等知识一直都是面试中的重点和难点，还需要熟练掌握。

------

#### 参考资料：

[① 知名互联网公司校招 Java 开发岗面试知识点解析](https://mp.weixin.qq.com/s?__biz=MjM5MjAwODM4MA==&mid=2650692240&idx=1&sn=dc39f07097656042344e7fee8a17259e&chksm=bea62b4389d1a255619238b869b0fc33c1c8fda9172f0da2b62da71524b4ea1b32989df6bc76&mpshare=1&scene=23&srcid=1225zdAW9iFdJ86OiBQSr8mP#rd)
[② 最近5年133个Java面试问题列表](https://zhuanlan.zhihu.com/p/23533393)
③ 《实战 Java 高并发程序设计 —— 葛一鸣 郭超 编著》