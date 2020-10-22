# 前言

管程作为一种解决并发问题的模型，是继信号量模型之后的一项重大创新，它与信号量在逻 辑上是等价的(可以用管程实现信号量，也可以用信号量实现管程)，但是相比之下管程更易用。

synchronized、wait()、notify() 是操作系统领域里管程模型的一种实现， Java SDK 并发包里的条件变量 Condition 也是管程里的概念。

**并发编程可以总结为三个核心问题:分工、同步、互斥。**

**分工**指的是如何高效地拆解任务并分配给线程，而**同步**指的是线程之间如何协作，**互斥** 则是保证同一时刻只允许一个线程访问共享资源。Java SDK 并发包很大部分内容都是按照这三个维度组织的，例如  Executor，Fork/Join，Future 框架就是一种分工模式，CountDownLatch，CyclicBarrier、Phaser、Exchanger 就是一种典型的同步方式，而可重入锁则是一种互斥手段。

分工、同步主要强调的是性能，但并发程序里还有一部分是关于正确性的，用专业术语叫“**线程安全**”。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021204234.png" alt="image-20201021204234871" style="zoom: 50%;" />

> 首先，得理解并发的重要性，为什么需要并发？对于这个问题，只需要放在潜意识里面，只需要两个字：性能！其它的细节，再去慢慢拓展。
>    然后，既然并发很重要，而并发处理的是任务，接下就是：对任务的抽象、拆解、分工执行。而线程模型，只是其中的一种模型，还有多进程、协程。Java使用的是多线程模型，对应到具体的代码就是：Thread, Runnable, Task，执行任务有：Exectors。 引出了线程，有势必存在着线程安全性的问题，因为多线程访问，数据存在着不一致的问题。
>    再然后，大的任务被拆解多个小的子任务，小的子任务被各自执行，不难想象，子任务之间肯定存在着依赖关系，所以需要协调，那如何协调呢？也不难想到，锁是非常直接的方式(Monitor原理)，但是只用锁，协调的费力度太高，在并发的世界里面，又有了一些其它的更抽象的工具：闭锁、屏障、队列以及其它的一些并发容器等；好了，协调的工作不难处理了。可是协调也会有出错的时候，这就有了死锁、活锁等问题，大师围绕着这个问题继续优化协调工具，尽量让使用者不容易出现这些活跃性问题；
>    到此，「并发」的历史还在演化：如果一遇到并发问题，就直接上锁，倒也没有什么大问题，可是追求性能是人类的天性。计算机大师就在思考，能不不加锁也能实现并发，还不容易出错，于是就有了：CAS、copy-on-write等技术思想，这就是实现了「无锁」并发；
>    可是，事情到此还没有完。如果以上这些个东西，都需要每个程序员自己去弄，然后自己保证正确性，那程序员真累死了，哪还有时间、精力创造这么多美好的应用！于是，计算机大师又开始思考，能不能抽象出统一「模型」，可能这就有了类似于「Java内存模型」这样的东西。

# 1. 并发理论基础

## 1. 可见性，有序性和原子性

核心矛盾： CPU、内存、I/O 设备速度不匹配。

1. CPU 增加了缓存，以均衡与内存的速度差异;
2. 操作系统增加了进程、线程，以分时复用 CPU，进而均衡 CPU 与 I/O 设备的速度差异;

3. 编译程序优化指令执行次序，使得缓存能够得到更加合理地利用。

问题1，**缓存导致的可见性问题**：

多核时代，线程A对变量 V 的操作对于线程 B 而言就不具备可见性。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021205407.png" alt="image-20201021205407236" style="zoom:25%;" />

```java
public class Test_01_1 {
    private static long count = 0;

    private  void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        // 创建两个线程，执行add()操作
        Test_01_1 test = new Test_01_1();
        Thread th1 = new Thread(() -> {
            test.add10K();
        });
        Thread th2 = new Thread(() -> {
            test.add10K();
        }); // 启动两个线程
        th1.start();
        th2.start(); // 等待两个线程执行结束
        th1.join();
        th2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Test_01_1.calc());
    }
}
```

![image-20201021210940115](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021210940.png)

我们发现，总共循环了20000次，结果却是10126次。

**问题2：线程切换带来的原子性问题**

count += 1至少需要三条CPU指令，指令 1:首先，需要把变量 count 从内存加载到 CPU 的寄存器;指令 2:之后，在寄存器中执行 +1 操作指令 3:最后，将结果写入内存(缓存机制导致可能写入的是 CPU 缓存而不是内存)。操作系统做任务切换，可以发生在任何一条**CPU 指令**执行完。

![image-20201022001253240](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022001253.png)

**问题3：编译优化带来的有序性问题**

利用双重检查创建单例对象。

```java
public class Singleton {
  static Singleton instance;
  static Singleton getInstance(){
    if (instance == null) {
      synchronized(Singleton.class) {
        if (instance == null)
          instance = new Singleton();
        }
    }
    return instance;
  }
}
```

实际执行的步骤：

1. 分配一块内存 M;
2. 将 M 的地址赋值给 instance 变量;
3. 最后在内存 M 上初始化 Singleton 对象。

如下所示，出现空指针异常的问题。

![image-20201022001541013](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022001647.png)

## 2. java内存模型解决可见性和有序性问题

为了解决可见性和有序性问题，按需禁用缓存和编译优化即可。Java 内存模型规范了 JVM 如何提供按需禁用缓存和编译优化的方 法。具体来说，这些方法包括 **volatile**、**synchronized** 和 **final** 三个关键字，以及六项 **Happens-Before 规则**。

**Happens-Before** **规则**：前面一个操作的结果对后续的操作是可见的，本质上是一种可见性。

```java
public class VolatileExample {

    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            // 这里 x 会是多少呢?
            System.out.println(x);
        }
    }
}
```

1. 顺序性：第7行一定Happens-Before 于第8行；
2. **volatile** 变量规则：对一个 volatile 变量的写操作， Happens-Before 于后续对这个 volatile 变量的读操作。
3. 传递性：如果 A Happens-Before B，且 B Happens-Before C，那么 A Happens-Before C。

![image-20201022104816050](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022104816.png)

从图中，我们可以看到:

1） “x=42” Happens-Before 写变量 “v=true” ，这是规则 1 的内容;

2） 写变量“v=true” Happens-Before 读变量 “v=true”，这是规则 2 的内容 。

再根据这个传递性规则，我们得到结果:“x=42” Happens-Before 读变 量“v=true”。

4. 管程中锁的规则：对一个锁的解锁 Happens-Before 于后续对这个锁的加锁。
5. 线程start原则：主线程 A 启动子线程 B 后，子线程 B 能够看到主线程在启动子线程 B 前的操作。

![image-20201022105206120](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022105206.png)

6. 线程join原则：如果在线程 A 中，调用线程 B 的 join() 并成功返回，那么线程 B 中的任意 操作 Happens-Before 于该 join() 操作的返回。

![image-20201022105310834](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022105310.png)

## 3. 互斥锁：解决原子性问题

> **解决原子性问题，是要保证中间状态对外不可见**。

原子性问题的源头是**线程切换**，禁止 CPU 发生中断就能够禁止线 程切换。在早期单核 CPU 时代，这个方案的确是可行的。

![image-20201022105850438](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022105850.png)

但是在多核场景下，同一时刻，有可能有两个线程同时在执行，一个线程执行在 CPU-1 上，一个线程执行在 CPU-2 上，此时禁止 CPU 中断，只能保证 CPU 上的线程连续执行， 并不能保证同一时刻只有一个线程执行，如果这两个线程同时写 long 型变量高 32 位的 话，那就有可能出现我们开头提及的诡异 Bug 了。

“**同一时刻只有一个线程执行**”这个条件非常重要，我们称之为**互斥**。如果我们能够保证对 共享变量的修改是互斥的，那么，无论是单核 CPU 还是多核 CPU，就都能保证原子性了。

**简单锁模型**：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022110020.png" alt="image-20201022110020646" style="zoom:33%;" />



改进后的锁模型：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022110139.png" alt="image-20201022110139581" style="zoom:33%;" />

首先，我们要把临界区要保护的资源标注出来，如图中临界区里增加了一个元素:受保护的资源 R;其次，我们要保护资源 R 就得为它创建一把锁 LR;最后，针对这把锁 LR，我们 还需在进出临界区时添上加锁操作和解锁操作。另外，在锁 LR 和受保护资源之间，我特地用一条线做了关联，这个关联关系非常重要。

**Java** **语言提供的锁技术:**synchronized

```java
public class SynchronizedExample_03_1 {
    // 修饰非静态方法,锁定当前类的 Class 对象
    synchronized void foo() {
        // 临界区
    }
    // 修饰静态方法，锁定当前实例对象 this
    synchronized static void bar() {
        // 临界区
    }

    // 修饰代码块
    Object obj = new Object();

    void baz() {
        synchronized (obj) {
            // 临界区
        }
    }
}
```

实例：**用** **synchronized** **解决** **count+=1** **问题**

```java
public class SafeCalc_03_2 {
    long value = 0L;

    synchronized long get() {
        return value;
    }

    synchronized void addOne() {
        value += 1;
    }
}
```

原子性是显而易见的，下面分析下可见性，根据happens-before原则：前一个线程在临界区修改的共享变量(该操作在解锁之前)，对后续进入临界区(该操作在加锁之后)的线程是可见的。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022111126.png" alt="image-20201022111126623" style="zoom:33%;" />

**锁和受保护资源的关系**

**受保护资源和锁之间的关联关系是 N:1 的关系**，下面看一个两把锁保护同一个资源出现异常的例子：

```java
public class SafeCalc_03_3 {
    static long value = 0L;

    synchronized long get() {
        return value;
    }

    synchronized static void addOne() {
        value += 1;
    }
}
```

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022111623.png" alt="image-20201022111623891" style="zoom:33%;" />

## 4. 一把锁保护多个资源

**保护没有关联关系的多个资源**，这个很简单。

**保护有关联关系的多个资源**：

错误1：

```java
public class Account_04_1 {
    private int balance;

    //转账
    synchronized void transfer(Account_04_1 target, int amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }
}
```

this 这把锁可以保护自己的余额 this.balance，却保护不了别 人的余额 target.balance。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022112350.png" alt="image-20201022112350781" style="zoom:33%;" />

假设有 A、B、C 三个账户，余额都是 200 元，我们用两个线程分 别执行两个转账操作:账户 A 转给账户 B 100 元，账户 B 转给账户 C 100 元，最后我们 期望的结果应该是账户 A 的余额是 100 元，账户 B 的余额是 200 元， 账户 C 的余额是 300 元。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022112520.png" alt="image-20201022112520899" style="zoom:50%;" />

优化：

```java
    void transfer(Account_04_2 target, int amt) {
        synchronized (Account_04_2.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
```

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022112843.png" alt="image-20201022112843791" style="zoom:33%;" />

## 5. 死锁怎么办

上面的分析中，我们锁定了Account.class，这会导致所有的转账操作变为串行化，是不可以被接受的。所以需要两把锁。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022113559.png" alt="image-20201022113559512" style="zoom:33%;" />

```java
//转账
    void transfer(Account_05_1 target, int amt) {
        // 锁定转出账户 
        synchronized (this) {
            // 锁定转入账户 
            synchronized (target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
```

但是这样做会导致死锁。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022113902.png" alt="image-20201022113902572" style="zoom:33%;" />

**死锁预防**：

死锁发生的四个条件：

1. 互斥，共享资源 X 和 Y 只能被一个线程占用;

2. 占有且等待，线程 T1 已经取得共享资源 X，在等待共享资源 Y 的时候，不释放共享资

   源 X;

3. 不可抢占，其他线程不能强行抢占线程 T1 占有的资源;

4. 循环等待，线程 T1 等待线程 T2 占有的资源，线程 T2 等待线程 T1 占有的资源，就是

   循环等待。

解决：

1. 破坏**占用且等待条件**

一次性申请所有资源。

```java
public class Account_05_2 {
    // actr 应该为单例
    private Allocator actr;
    private int balance;
    //转账
    void transfer(Account_05_2 target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
       // 注意，这里只锁定了相关的2个账户，Account.class则锁定了Account的所有实例
      // 为了优化，我们这里可以加一个超时
        while (!actr.apply(this, target)) {
            ;
        }
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
class Allocator {
    private List<Object> als =
            new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) ||
                als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }
    // 归还资源
    synchronized void free(
            Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}
}
```

2. 破坏不可强占条件

synchronized无法做到，java.util.concurrent 这个包下面提供的 Lock 可以做到。

3. **破坏循环等待条件**

破坏这个条件，需要对资源进行排序，然后按序申请资源。

## 6. 等待通知优化循环等待

上面我们使用了while(!actr.apply(this, target))来进行死循环等待，但是这样太消耗cpu了，我们可以使用**等待 - 通知机制**：**线程 首先获取互斥锁，当线程要求的条件不满足时，释放互斥锁，进入等待状态;当要求的条件 满足时，通知等待的线程，重新获取互斥锁**。

**用** **synchronized** **实现等待** **-** 通知机制

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022144208.png" alt="image-20201022144208582" style="zoom:33%;" />

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022144304.png" alt="image-20201022144304619" style="zoom:33%;" />



```java
public class Account_06_1 {
    // actr 应该为单例
    private Allocator actr;
    private int balance;

    //转账
    void transfer(Account_06_1 target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target)) {
            ;
        }
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}
class Allocator1 {
    private List<Object> als = new ArrayList<>();
    // 一次性申请所有资源
    synchronized boolean apply(Object from, Object to) {
        while (als.contains(from) || als.contains(to)) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        als.add(from);
        als.add(to);
        return true;
    }
    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        //notify() 是会随机地通知等待队列中的一个线程，而 notifyAll() 会通知等 待队列中的所有线程
        notifyAll();
    }
}
```

## 7. **安全性、活跃性以及性能问题**

1. 吞吐量:指的是单位时间内能处理的请求数量。吞吐量越高，说明性能越好。 
2. 延迟:指的是从发出请求到收到响应的时间。延迟越小，说明性能越好。

3. 并发量:指的是能同时处理的请求数量，一般来说随着并发量的增加、延迟也会增加。 所以延迟这个指标，一般都会是基于并发量来说的。例如并发量是 1000 的时候，延迟 是 50 毫秒。

## 8. **管程:并发编程的万能钥匙**

> **管程，指的是管理共享变量以及对共享变量的操作过程，让他们支持并发**

操作系统原理课程告诉我，用信号量能解决所有并发问题。**管程和信号量是 等价的，所谓等价指的是用管程能够实现信号量，也能用信号量实现管程。**

**MESA** **模型**

在并发编程领域，有两大核心问题:一个是**互斥**，即同一时刻只允许一个线程访问共享资 源;另一个是**同步**，即线程之间如何通信、协作。这两大问题，管程都是能够解决的。

管程解决互斥问题的思路很简单，就是将共享变量及其对共享变量的操作统一封装起来。在 下图中，管程 X 将共享变量 queue 这个队列和相关的操作入队 enq()、出队 deq() 都封装 起来了;线程 A 和线程 B 如果想访问共享变量 queue，只能通过调用管程提供的 enq()、 deq() 方法来实现;enq()、deq() 保证互斥性，只允许一个线程进入管程。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022154232.png" alt="image-20201022154231969" style="zoom:33%;" />

在管程模型里，共享变量和对共享变量的操作是被封装起来的，图中最外层的框就代表封装 的意思。框的上面只有一个入口，并且在入口旁边还有一个入口等待队列。当多个线程同时 试图进入管程内部时，只允许一个线程进入，其他线程则在入口等待队列中等待。

管程里还引入了条件变量的概念，而且**每个条件变量都对应有一个等待队列**，如下图，条件 变量 A 和条件变量 B 分别都有自己的等待队列。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022154302.png" alt="image-20201022154302757" style="zoom:33%;" />

假设有个线程 T1 执行出队操作，不过需要注意的是执行出队操作，有个前提条件，就是队 列不能是空的，而队列不空这个前提条件就是管程里的条件变量。 如果线程 T1 进入管程 后恰好发现队列是空的，那怎么办呢?等待啊，去哪里等呢?就去条件变量对应的等待队列 里面等。此时线程 T1 就去“队列不空”这个条件变量的等待队列中等待。线程 T1 进入条件变量的等待队列后，是允许其他线程进入管程的。

再假设之后另外一个线程 T2 执行入队操作，入队操作执行成功之后，“队列不空”这个条 件对于线程 T1 来说已经满足了，此时线程 T2 要通知 T1，告诉它需要的条件已经满足了。 当线程 T1 得到通知后，会从等待队列里面出来，但是出来之后不是马上执行，而是重新进 入到入口等待队列里面。

MESA 模型中，条件变量可以有多个，Java 语言内置的管程里只有一个条件变量。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022154744.png" alt="image-20201022154744882" style="zoom:33%;" />

## 9 java线程的生命周期

通用线程模型：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022155134.png" alt="image-20201022155134208" style="zoom:33%;" />

java线程模型：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022155024.png" alt="image-20201022155024249" style="zoom:33%;" />

1. NEW(初始化状态)
2. RUNNABLE(可运行 / 运行状态)
3. BLOCKED(阻塞状态)
4. WAITING(无时限等待)
5. TIMED_WAITING(有时限等待)
6. TERMINATED(终止状态)

合并了运行/可运行，BLOCKED、WAITING、TIMED_WAITING都属于休眠。

1. **RUNNABLE** **与** **BLOCKED** 

线程等待 synchronized。

此外，线程调用阻塞式 API 时，是 否会转换到 BLOCKED 状态呢?在操作系统层面，线程是会转换到休眠状态的，但是在 JVM 层面，Java 线程的状态不会发生变化，也就是说 Java 线程的状态会依然保持 RUNNABLE 状态。**JVM 层面并不关心操作系统调度相关的状态**，因为在 JVM 看来，等待 CPU 使用权(操作系统层面此时处于可执行状态)与等待 I/O(操作系统层面此时处于休 眠状态)没有区别，都是在等待某个资源，所以都归入了 RUNNABLE 状态。

2. **RUNNABLE** **与** **WAITING**

1) 调用无参数的 Object.wait() 方法；

2）调用无参数的 Thread.join() 方法；有一个线程对象 thread A，当调用 A.join() 的时候，执行这条语句的线程会等待 thread A 执行完，而等待中的这个线程，其状态会从 RUNNABLE 转换到 WAITING。当线程 thread A 执行完，原来等待它的线程又会从 WAITING 状态转换到 RUNNABLE。

3）调用 LockSupport.park() 方法；

3. **RUNNABLE** **与** **TIMED_WAITING** 

1）调用**带超时参数**的 Thread.sleep(long millis) 方法;

2）调用**带超时参数**的 Object.wait(long timeout) 方法;

3）调用**带超时参数**的 Thread.join(long millis) 方法;

4）调用**带超时参数**的 LockSupport.parkNanos(Object blocker, long deadline) 方法;

5）调用**带超时参数**的 LockSupport.parkUntil(long deadline) 方法。

4. **NEW** **到** **RUNNABLE**

NEW 状态的线程，不会被操作系统调度，因此不会执行。Java 线程要执行，就必须转换到 RUNNABLE 状态。从 NEW 状态转换到 RUNNABLE 状态很简单，只要调用线程对象的 start() 方法就可以了。

5. **RUNNABLE** **到** **TERMINATED**

线程执行完 run() 方法后，会自动转换到 TERMINATED 状态，当然如果执行 run() 方法的 时候异常抛出，也会导致线程终止。。有时候我们需要强制中断 run() 方法的执行，调用 interrupt() 方法。

补充： **stop() 和 interrupt() 方法的主要区别是什么呢？**

stop() 方法会真的杀死线程，不给线程喘息的机会，如果线程持有 ReentrantLock 锁，被 stop() 的线程并不会自动调用 ReentrantLock 的 unlock() 去释放锁，那其他线程就再也没 机会获得 ReentrantLock 锁。

而 interrupt() 方法就温柔多了，interrupt() 方法仅仅是通知线程，线程有机会执行一些后 续操作，同时也可以无视这个通知。被 interrupt 的线程，是怎么收到通知的呢?一种是异 常，另一种是主动检测。

当线程 A 处于 WAITING、TIMED_WAITING 状态时，如果其他线程调用线程 A 的 interrupt() 方法，会使线程 A 返回到 RUNNABLE 状态，同时线程 A 的代码会触发 InterruptedException 异常。上面我们提到转换到 WAITING、TIMED_WAITING 状态的 触发条件，都是调用了类似 wait()、join()、sleep() 这样的方法，我们看这些方法的签名， 发现都会 throws InterruptedException 这个异常。这个异常的触发条件就是:其他线程 调用了该线程的 interrupt() 方法。

当线程 A 处于 RUNNABLE 状态时，并且阻塞在 java.nio.channels.InterruptibleChannel 上时，如果其他线程调用线程 A 的 interrupt() 方法，线程 A 会触发 java.nio.channels.ClosedByInterruptException 这个异常;而阻塞在 java.nio.channels.Selector 上时，如果其他线程调用线程 A 的 interrupt() 方法，线程 A 的 java.nio.channels.Selector 会立即返回。

上面这两种情况属于被中断的线程通过异常的方式获得了通知。还有一种是主动检测，如果 线程处于 RUNNABLE 状态，并且没有阻塞在某个 I/O 操作上，例如中断计算圆周率的线程 A，这时就得依赖线程 A 主动检测中断状态了。如果其他线程调用线程 A 的 interrupt() 方法，那么线程 A 可以通过 isInterrupted() 方法，检测是不是自己被中断了。

## 10 **创建多少线程才是合适的**

**为什么使用多线程？**

度量性能的指标最核心的是延迟和吞吐量。**延迟**指的是发出请求到收到响应这个过程的时间;延迟越短，意味着程序执行得越快，性能也就越好。 **吞吐量**指的是在单位时间内能处理请求数量;吞吐量越大，意味着程序能处理的请求越多，性能也就越好。这两个指标内部有一定的联系(同等条件下，延迟越短，吞吐量越大)，但是由于它们隶属不同的维度(一个是时间维度，一个是空间维度)，并不能互相转换。

**最佳线程数量**：

CPU密集型：**程的数量 =CPU 核数+1**，为了避免因为偶尔的内存页失效或其他原因导致阻塞时，这个额外的线程可以顶上。

IO密集型：最佳线程数 ==CPU 核数 * [ 1 +(I/O 耗时 / CPU 耗时)]

## 12 如何用面向对象思想写好并发程序

TODO

## 13 小结

起源是一个硬件的核心矛盾:CPU 与内存、I/O 的速度差异，系统软件(操作系统、编译 器)在解决这个核心矛盾的同时，引入了可见性、原子性和有序性问题，这三个问题就是很多并发程序的 Bug 之源。这，就是01的内容。

那如何解决这三个问题呢?Java 语言自然有招儿，它提供了 Java 内存模型和互斥锁方案。 所以，在02我们介绍了 Java 内存模型，以应对可见性和有序性问题;那另一个原子性问题 该如何解决?多方考量用好互斥锁才是关键，这就是03和04的内容。

虽说互斥锁是解决并发问题的核心工具，但它也可能会带来死锁问题，所以05就介绍了死 锁的产生原因以及解决方案;同时还引出一个线程间协作的问题，这也就引出了06这篇文 章的内容，介绍线程间的协作机制:等待 - 通知。

你应该也看出来了，前六篇文章，我们更多地是站在微观的角度看待并发问题。而07则是 换一个角度，站在宏观的角度重新审视并发编程相关的概念和理论，同时也是对前六篇文章 的查漏补缺。

08介绍的管程，是 Java 并发编程技术的基础，是解决并发问题的万能钥匙。并发编程里两 大核心问题——互斥和同步，都是可以由管程来解决的。所以，学好管程，就相当于掌握 了一把并发编程的万能钥匙。

至此，并发编程相关的问题，理论上你都应该能找到问题所在，并能给出理论上的解决方案了。

而后在09、10和11我们又介绍了线程相关的知识，毕竟 Java 并发编程是要靠多线程来实 现的，所以有针对性地学习这部分知识也是很有必要的，包括线程的生命周期、如何计算合 适的线程数以及线程内部是如何执行的。

最后，在12我们还介绍了如何用面向对象思想写好并发程序，因为在 Java 语言里，面向对 象思想能够让并发编程变得更简单。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022162604.png" alt="image-20201022162604068" style="zoom:33%;" />

# 2. 并发工具类

## 14. **Lock和Condition(上):隐藏在并发包中的管程**

**Java SDK 并发包通过 Lock 和 Condition 两个接口来实现管程，其 中 Lock 用于解决互斥问题，Condition 用于解决同步问题**。

死锁问题，提出了一个**破坏不可抢占条件**方案， synchronized 没有办法解决。原因是 synchronized 申请资源的时候，如果申请不 到，线程直接进入阻塞状态了，而线程进入阻塞状态，啥都干不了，也释放不了线程已经占 有的资源。但我们希望的是:对于“不可抢占”这个条件，占用部分资源的线程进一步申请其他资源时， 如果申请不到，可以主动释放它占有的资源，这样不可抢占这个条件就破坏掉了。

如果我们重新设计一把互斥锁去解决这个问题，那该怎么设计呢?我觉得有三种方案：

1. **能够响应中断**。synchronized 的问题是，持有锁 A 后，如果尝试获取锁 B 失败，那么 线程就进入阻塞状态，一旦发生死锁，就没有任何机会来唤醒阻塞的线程。但如果阻塞 状态的线程能够响应中断信号，也就是说当我们给阻塞的线程发送中断信号的时候，能 够唤醒它，那它就有机会释放曾经持有的锁 A。这样就破坏了不可抢占条件了。

2. **支持超时**。如果线程在一段时间之内没有获取到锁，不是进入阻塞状态，而是返回一个错误，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。

3. **非阻塞地获取锁**。如果尝试获取锁失败，并不进入阻塞状态，而是直接返回，那这个线程也有机会释放曾经持有的锁。这样也能破坏不可抢占条件。

对应 Lock接口的三个方法：

```java
// 支持中断的 API
void lockInterruptibly() throws InterruptedException;
// 支持超时的 API
boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
// 支持非阻塞获取锁的 API
boolean tryLock();
```

**保证可见性**：

我们运行下面的程序发现线程2可以获得我们想要的结果。

```java
public class Lock_14_1 {
    private final Lock rtl = new ReentrantLock();
    int value=0;

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value += 1;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public static void main(String[] args) {
        Lock_14_1 lock141 = new Lock_14_1();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock141.addOne();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(lock141.value);
            }
        });
        thread1.start();
        thread2.start();
    }
}
```

1. **顺序性规则**:对于线程 T1，value+=1 Happens-Before 释放锁的操作 unlock(); 
2.  **volatile 变量规则**:由于 state = 1 会先读取 state，所以线程 T1 的 unlock() 操作Happens-Before 线程 T2 的 lock() 操作;
3. **传递性规则**:线程 T1 的 value+=1 Happens-Before 线程 T2 的 lock() 操作。



**什么是可重入锁**：**线程可以重复获取同一把锁**。

**公平锁与非公平锁**：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022173504.png" alt="image-20201022173504381" style="zoom:33%;" />

**最佳实践**：

1. 永远只在更新对象的成员变量时加锁 
2. 永远只在访问可变的成员变量时加锁 
3. 永远不在调用其他对象的方法时加锁

## 15. **Dubbo**如何用管程实现异步转同步?

**Condition 实现了管程模型里面的条件变量**，Lock&Condition 实现的管程是支持多个条件变量的。

**如何利用两个条件变量快速实现阻塞队列**？

一个阻塞队列，需要两个条件变量，一个是队列不空(空队列不允许出队)，另一个是队列不满(队列已满不允许入队)。

```java
public class BlockedQueue_15_1<T> {
    final Lock lock = new ReentrantLock();
    // 条件变量:队列不满
    final Condition notFull = lock.newCondition();
    // 条件变量:队列不空
    final Condition notEmpty = lock.newCondition();

    //入队
    void enq(T x) {
        lock.lock();
        try {
            while (队列已满) {
                // 等待队列不满
                notFull.await();
            }
            // 省略入队操作...
            // 入队后, 通知可出队
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //出队
    void deq() {
        lock.lock();
        try {
            while (队列已空) {
                // 等待队列不空
                notEmpty.await();
            }
            // 省略出队操作...
            // 出队后，通知可入队
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

**同步与异步**：**调用方是否需要等待结果，如果需要等待结果，就是同步;如 果不需要等待结果，就是异步**。

## 16. **Semaphore**:如何快速实现一个限流器?

**信号量模型**：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022190315.png" alt="image-20201022190315682" style="zoom:33%;" />

- init():设置计数器的初始值。

- down():计数器的值减 1;如果此时计数器的值小于 0，则当前线程将被阻塞，否则当前线程可以继续执行。
- up():计数器的值加 1;如果此时计数器的值小于或者等于 0，则唤醒等待队列中的一个 线程，并将其从等待队列中移除。

```java
public class Semaphore_16_1 {
    //计数器
    int count;
    // 等待队列
    Queue queue;

    // 初始化操作
    Semaphore_16_1(int c) {
        this.count = c;
    }

    //也被称为PV原语，semWait() 和 semSignal() 
    void acquire() {
        this.count--;
        if (this.count < 0) {
            // 将当前线程插入等待队列
            // 阻塞当前线程
        }
    }

    void release() {
        this.count++;
        if (this.count <= 0) {
            // 移除等待队列中的某个线程 T
            // 唤醒线程 T
        }
    }
}
```

**信号量的使用**-互斥锁

```java
static int count;
    // 初始化信号量
    static final Semaphore s = new Semaphore(1);

    // 用信号量保证互斥 
    static void addOne() {
        s.acquire();
        try {
            count += 1;
        } finally {
            s.release();
        }
    }
```

**信号量实现限流器**：不允许多于 N 个线程同时进入临界区

> **Semaphore 可以允许多个线程访问一个临界区**。

```java
public class ObjPool_16_2<T, R> {
    final List<T> pool;
    // 用信号量实现限流器
    final Semaphore_16_1 sem;

    // 构造函数
    ObjPool_16_2(int size, T t) {
        pool = new Vector<T>() {
        };
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore_16_1(size);
    }

    // 利用对象池的对象，调用 func
    R exec(Function<T, R> func) {
        T t = null;
        sem.acquire();
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }
    }

    public static void main(String[] args) {
        // 创建对象池
        ObjPool_16_2<Integer, String> pool =
                new ObjPool_16_2<Integer, String>(10, 2);
        // 通过对象池获取 t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
```

我们用一个 List来保存对象实例，用 Semaphore 实现限流器。关键的代码是 ObjPool 里 面的 exec() 方法，这个方法里面实现了限流的功能。在这个方法里面，我们首先调用 acquire() 方法(与之匹配的是在 finally 里面调用 release() 方法)，假设对象池的大小是 10，信号量的计数器初始化为 10，那么前 10 个线程调用 acquire() 方法，都能继续执 行，相当于通过了信号灯，而其他线程则会阻塞在 acquire() 方法上。对于通过信号灯的线 程，我们为每个线程分配了一个对象 t(这个分配工作是通过 pool.remove(0) 实现的)， 分配完之后会执行一个回调函数 func，而函数的参数正是前面分配的对象 t ;执行完回调 函数之后，它们就会释放对象(这个释放工作是通过 pool.add(t) 实现的)，同时调用 release() 方法来更新信号量的计数器。如果此时信号量里计数器的值小于等于 0，那么说 明有线程在等待，此时会自动唤醒等待的线程。

## 17. **ReadWriteLock**:如何快速实现一个完备的缓存

> 读多写少这种并发场景。

1. 允许多个线程同时读共享变量;
2. 只允许一个线程写共享变量;
3. 如果一个写线程正在执行写操作，此时禁止读线程读共享变量。

读写锁与互斥锁的一个重要区别就是**读写锁允许多个线程同时读共享变量**，而互斥锁是不允 许的，这是读写锁在读多写少场景下性能优于互斥锁的关键。

```java
public class Cache_17_1<K, V> {
    final Map<K, V> m = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    //读锁
    final Lock r = rwl.readLock();
    //写锁
    final Lock w = rwl.writeLock();

    // 读缓存
    V get(K key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    // 写缓存
    V put(K key, V v) {
        w.lock();
        try {
            return m.put(key, v);
        } finally {
            w.unlock();
        }
    }
}
```

## 19. **CountDownLatch**和CyclicBarrier:如何让多线程步调一致?

业务场景：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022194919.png" alt="image-20201022194918969" style="zoom:33%;" />

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022194944.png" alt="image-20201022194944761" style="zoom:50%;" />

目前的对账系统，由于订单量和派送单量巨大，所以查询未对账订单 getPOrders() 和查询 派送单 getDOrders() 相对较慢，那有没有办法快速优化一下呢?目前对账系统是单线程执行的，图形化后是下图这个样子。对于串行化的系统，优化性能首先想到的是能否**利用多线程并行处理**。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022195100.png" alt="image-20201022195100676" style="zoom:33%;" />

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022195151.png" alt="image-20201022195151754" style="zoom:33%;" />

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022195311.png" alt="image-20201022195311361" style="zoom:33%;" />

存在问题：while循环里面每次都会创建新的线程，创建线程可是个耗时的操作。所以最好创建出来的线程能够循环利用，即线程池。

但是这样的话，我们没有办法知道什么时候getPOrders() 和 getDOrders() 执行完，因为两个线程不会退出。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022195709.png" alt="image-20201022195709618" style="zoom:33%;" />

### **用** **CountDownLatch** **实现线程等待**

> 解决一个线程等待多个线程的场景

```java
public class CountDownLatch {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(2);
        while (存在未对账订单) {
            // 计数器初始化为2
            CountDownLatch latch = new CountDownLatch(2);
            // 查询未对账订单
            executor.execute(() -> {
                pos = getPOrders();
                latch.countDown();
            });
            // 查询派送单
            executor.execute(() -> {
                dos = getDOrders();
                latch.countDown();
            });
            // 等待两个查询操作结束
            latch.await();
            // 执行对账操作
            diff = check(pos, dos);
            // 差异写入差异库
            save(diff);
        }
    }
}
```

继续优化：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022200223.png" alt="image-20201022200208507" style="zoom:33%;" />

两次查询操作能够和对账操作并行，对账操 作还依赖查询操作的结果，这明显有点生产者 - 消费者的意思，两次查询操作是生产者， 对账操作是消费者。既然是生产者 - 消费者模型，那就需要有个队列，来保存生产者生产 的数据，而消费者则从这个队列消费数据。

不过针对对账这个项目，我设计了两个队列，并且两个队列的元素之间还有对应关系。具体 如下图所示，订单查询操作将订单查询结果插入订单队列，派送单查询操作将派送单插入派 送单队列，这两个队列的元素之间是有一一对应的关系的。两个队列的好处是，对账操作可 以每次从订单队列出一个元素，从派送单队列出一个元素，然后对这两个元素执行对账操 作，这样数据一定不会乱掉。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022200525.png" alt="image-20201022200524905" style="zoom:33%;" />

线程 T1 和线程 T2 只有都生产完 1 条数据的时候， 才能一起向下执行，也就是说，线程 T1 和线程 T2 要互相等待，步调要一致;同时当线程 T1 和 T2 都生产完一条数据的时候，还要能够通知线程 T3 执行对账操作。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022200626.png" alt="image-20201022200626490" style="zoom:33%;" />

### **用** **CyclicBarrier** **实现线程同步**

> CyclicBarrier 是一组线程之间互相等待，CountDownLatch 的计数器是不能循环利用的，也就是说一旦计数器减到 0，再有线程调用 await()，该线程会直接通过。但**CyclicBarrier 的计数器是可以循环利用的**，而且 具备自动重置的功能，一旦计数器减到 0 会自动重置到你设置的初始值。除此之外， CyclicBarrier 还可以设置回调函数。

```java
    public static void main(String[] args) {
        // 订单队列
        Vector<P> pos;
        // 派送单队列
        Vector<D> dos;
        // 执行回调的线程池
        Executor executor = Executors.newFixedThreadPool(1);
        final CyclicBarrier barrier = new CyclicBarrier(2, () -> executor.execute(() -> check())                });
    }
    void check() {
        P p = pos.remove(0);
        D d = dos.remove(0);
        // 执行对账操作
        diff = check(p, d);
        // 差异写入差异库
        save(diff);
    }
    void checkAll() {
        // 循环查询订单库
        Thread T1 = new Thread(() -> {
            while (存在未对账订单) {
                // 查询订单库
                pos.add(getPOrders());
                // 等待
                barrier.await();
            }
        });
        T1.start();
        // 循环查询运单库
        Thread T2 = new Thread(() -> {
            while (存在未对账订单) {
                // 查询运单库
                dos.add(getDOrders());
                // 等待
                barrier.await();
            }
        });
        T2.start();
    }
```

## 20. 并发容器

不安全容器变安全：

```java
public class SafeArrayList_20_1<T> {
    // 封装 ArrayList
    List<T> c = new ArrayList<>();

    // 控制访问路径
    synchronized T get(int idx) {
        return c.get(idx);
    }
    synchronized void add(int idx, T t) {
        c.add(idx, t);
    }
    synchronized boolean addIfNotExist(T t) {
        if (!c.contains(t)) {
            c.add(t);
            return true;
        }
        return false;
    }
}
```

举一反三：

```java
List list = Collections. synchronizedList(new ArrayList());
Set set = Collections. synchronizedSet(new HashSet());
Map map = Collections. synchronizedMap(new HashMap());
```

**组合操作需要注意竞态条件问题**，例如上面提到的 addIfNotExist() 方 法就包含组合操作。组合操作往往隐藏着竞态条件问题，即便每个操作都能保证原子性，也 并不能保证组合操作的原子性，这个一定要注意。

在容器领域**一个容易被忽视的“坑”是用迭代器遍历容器**，例如在下面的代码中，通过迭代 器遍历容器 list，对每个元素调用 foo() 方法，这就存在并发问题，这些组合的操作不具备原子性。

```
List list = Collections. synchronizedList(new ArrayList());
Iterator i = list.iterator(); 
while (i.hasNext())
	foo(i.next());
```

正确：

```
List list = Collections. synchronizedList(new ArrayList());
Iterator i = list.iterator(); 
synchronized (list) {
while (i.hasNext())
	foo(i.next());
}
```

![image-20201022202729941](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022202730.png)

1. List

List 里面只有一个实现类就是**CopyOnWriteArrayList**。CopyOnWrite，顾名思义就是写的时候会将共享变量新复制一份出来，这样做的好处是读操作完全无锁。

CopyOnWriteArrayList 内部维护了一个数组，成员变量 array 就指向这个内部数组，所有 的读操作都是基于 array 进行的，如下图所示，迭代器 Iterator 遍历的就是 array 数组。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022203201.png" alt="image-20201022203201538" style="zoom:33%;" />

如果在遍历 array 的同时，还有一个写操作，例如增加元素，CopyOnWriteArrayList 是如 何处理的呢?CopyOnWriteArrayList 会将 array 复制一份，然后在新复制处理的数组上执 行增加元素的操作，执行完之后再将 array 指向这个新的数组。通过下图你可以看到，读写 是可以并行的，遍历操作一直都是基于原 array 执行，而写操作则是基于新 array 进行。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022203252.png" alt="image-20201022203251902" style="zoom:33%;" />

使用 CopyOnWriteArrayList 需要注意的“坑”主要有两个方面。一个是应用场景， CopyOnWriteArrayList 仅适用于写操作非常少的场景，而且能够容忍读写的短暂不一致。 例如上面的例子中，写入的新元素并不能立刻被遍历到。另一个需要注意的是， CopyOnWriteArrayList 迭代器是只读的，不支持增删改。因为迭代器遍历的仅仅是一个快 照，而对快照进行增删改是没有意义的。

2. Map

Map 接口的两个实现是 ConcurrentHashMap 和 ConcurrentSkipListMap，它们从应用 的角度来看，主要区别在于**ConcurrentHashMap 的 key 是无序的，而 ConcurrentSkipListMap 的 key 是有序的**。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022203442.png" alt="image-20201022203441905" style="zoom:33%;" />

ConcurrentSkipListMap 里面的 SkipList 本身就是一种数据结构，中文一般都翻译为“跳 表”。跳表插入、删除、查询操作平均的时间复杂度是 O(log n)，理论上和并发线程数没 有关系，所以在并发度非常高的情况下，若你对 ConcurrentHashMap 的性能还不满意， 可以尝试一下 ConcurrentSkipListMap。

3. Set

Set 接口的两个实现是 CopyOnWriteArraySet 和 ConcurrentSkipListSet，使用场景可以 参考前面讲述的 CopyOnWriteArrayList 和 ConcurrentSkipListMap

4. Queue

Java 并发包里面 Queue 这类并发容器是最复杂的，你可以从以下两个维度来分类。一个 维度是**阻塞与非阻塞**，所谓阻塞指的是当队列已满时，入队操作阻塞;当队列已空时，出队 操作阻塞。另一个维度是**单端与双端**，单端指的是只能队尾入队，队首出队;而双端指的是 队首队尾皆可入队出队。Java 并发包里**阻塞队列都用 Blocking 关键字标识，单端队列使 用 Queue 标识，双端队列使用 Deque 标识**。

1) **单端阻塞队列**:其实现有 ArrayBlockingQueue、LinkedBlockingQueue、 SynchronousQueue、LinkedTransferQueue、PriorityBlockingQueue 和 DelayQueue。内部一般会持有一个队列，这个队列可以是数组(其实现是 ArrayBlockingQueue)也可以是链表(其实现是 LinkedBlockingQueue);甚至还可以 不持有队列(其实现是 SynchronousQueue)，此时生产者线程的入队操作必须等待消费 者线程的出队操作。而 LinkedTransferQueue 融合 LinkedBlockingQueue 和 SynchronousQueue 的功能，性能比 LinkedBlockingQueue 更好; PriorityBlockingQueue 支持按照优先级出队;DelayQueue 支持延时出队。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022203724.png" alt="image-20201022203724479" style="zoom:33%;" />

2) **双端阻塞队列**:其实现是 LinkedBlockingDeque。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201022203755.png" alt="image-20201022203755458" style="zoom:33%;" />

3）**单端非阻塞队列**:其实现是 ConcurrentLinkedQueue

4）**双端非阻塞队列**:其实现是 ConcurrentLinkedDeque

只有 ArrayBlockingQueue 和 LinkedBlockingQueue 是支持有界的，所以**在使用其他无界队列时，一定要充分考虑是否 存在导致 OOM 的隐患**。

## 21 **原子类:无锁工具类的典范**

> 所有原子类的方法都是针对一个共享变量的，如果你需要解决多个变量的原子性问题，建议还是使用互斥锁方案。

对于简单的原子性问题，还有一种**无锁方案**。

```java
AtomicLong count = new AtomicLong(0);

    void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count.getAndIncrement();
        }
    }
```

**无锁方案的实现原理**

CAS，全称是 Compare And Swap，即“比较并交换”。**作为一条 CPU 指令， CAS 指令本身是能够保证原子性的**。

```java
public class SimulatedCAS_21_2 {
    int count = 0;
    public static void main(String[] args) {
        SimulatedCAS_21_2 simulatedCAS_21_2 = new SimulatedCAS_21_2();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(simulatedCAS_21_2.cas(0, 1));
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(simulatedCAS_21_2.cas(1, 2));
            }
        });
        thread1.start();
        thread2.start();
    }
    synchronized int cas(int expect, int newValue) {
        // 读目前 count 的值
        int curValue = count;
        // 比较目前 count 值是否 == 期望值
        if (curValue == expect) {
            // 如果是，则更新 count 的值
            count = newValue;
        }
        // 返回写入前的值
        return curValue;
    }
}
```

<img src="../../../../../Library/Application Support/typora-user-images/image-20201022205334197.png" alt="image-20201022205334197" style="zoom:33%;" />

注意ABA问题。

**Java** **如何实现原子化的** **count += 1**

```java
public final long getAndIncrement() {
        return unsafe.getAndAddLong(this, valueOffset, 1L);
    }
```

unsafe.getAndAddLong() 方法的源码如下，该方法首先会在内存中读取共享变量的值， 之后循环调用 compareAndSwapLong() 方法来尝试设置共享变量的值，直到成功为止。 compareAndSwapLong() 是一个 native 方法，只有当内存中共享变量的值等于 expected 时，才会将共享变量的值更新为 x，并且返回 true;否则返回 fasle。 compareAndSwapLong 的语义和 CAS 指令的语义的差别仅仅是返回值不同而已。

```java
public final long getAndAddLong(Object o, long offset, long delta) {
        long v;
        do {
          // 读取内存中的值
            v = getLongVolatile(o, offset);
        } while(!this.compareAndSwapLong(o, offset, v, v + delta));

        return v;
    }
// 原子性地将变量更新为 x
// 条件是内存中的值等于 expected
// 更新成功则返回 true
public final native boolean compareAndSwapLong(Object var1, long var2, long var4, long var6);
```

![image-20201023000337009](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201023000558.png)

原子类概览：

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201023003144.png" alt="image-20201023003144054" style="zoom:33%;" />

1. **原子化的基本数据类型**

![image-20201023003736939](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201023003737.png)

2. **原子化的对象引用类型**

## 22. **Executor**与线程池

线程池的设计：**生产者** **-** **消费者模式**

先来看一般意义上池化资源的设计方法：

```java
// 采用一般意义上池化资源的设计方法
public class ThreadPool_22_1 {
    // 获取空闲线程
    void acquire() {
    }
    // 释放线程
    void release(Thread t) {
    }

    public static void main(String[] args) {
        // 期望的使用
        ThreadPool_22_1 pool;
        Thread T1 = pool.acquire();
        // 传入 Runnable 对象
        T1.execute(() -> {
            // 具体业务逻辑
        });
    }
}
```

目前业界线程池的设计，普遍采用的都是**生产者 - 消费者模式**。线程池的使用方是生产者，线程池本身是消费者。

```java
// 简化的线程池，仅用来说明工作原理
public class MyThreadPool_22_2 {
    // 利用阻塞队列实现生产者 - 消费者模式
    BlockingQueue<Runnable> workQueue;
    // 保存内部工作线程
    List<WorkerThread> threads = new ArrayList<>();

    // 构造方法
    MyThreadPool_22_2(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        // 创建工作线程
        for (int idx = 0; idx < poolSize; idx++) {
            WorkerThread work = new WorkerThread();
            work.start();
            threads.add(work);
        }
    }

    // 提交任务
    void execute(Runnable command) throws InterruptedException {
        workQueue.put(command);
    }

    // 工作线程负责消费任务，并执行任务
    class WorkerThread extends Thread {
        @Override
        public void run() { // 循环取任务并执行
            while (true) {
                Runnable task = null;
                try {
                    task = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
            }

        }
    }

    public static void main(String[] args) {
        /** 下面是使用示例 **/
        // 创建有界阻塞队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);
// 创建线程池
        MyThreadPool_22_2 pool = new MyThreadPool_22_2(10, workQueue);
        // 提交任务
        try {
            pool.execute(() -> {
                System.out.println("hello");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```



# 3. 并发设计模式 



# 4. 案例分析



# 5. 其他并发模型





















