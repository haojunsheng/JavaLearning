[TOC]

# 1. 线程的特点



# 2. 线程的状态

![img](https://ws4.sinaimg.cn/large/006tKfTcly1g0ju5j86e1j31a00f8adp.jpg)

![img](https://ws1.sinaimg.cn/large/006tKfTcly1g0ju5ot1a4j30gt0bnmz6.jpg)

说明：
线程共包括以下5种状态。
1. 新建状态(New)         : 线程对象被创建后，就进入了新建状态。例如，Thread thread = new Thread()。
2. 就绪状态(Runnable): 也被称为“可执行状态”。线程对象被创建后，其它线程调用了该对象的start()方法，从而来启动该线程。例如，thread.start()。处于就绪状态的线程，随时可能被CPU调度执行。
3. 运行状态(Running) : 线程获取CPU权限进行执行。需要注意的是，线程只能从就绪状态进入到运行状态。
4. 阻塞状态(Blocked)  : 阻塞状态是线程因为某种原因放弃CPU使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。阻塞的情况分三种：
    (01) 等待阻塞 -- 通过调用线程的wait()方法，让线程等待某工作的完成。
    (02) 同步阻塞 -- 线程在获取synchronized同步锁失败(因为锁被其它线程所占用)，它会进入同步阻塞状态。
    (03) 其他阻塞 -- 通过调用线程的sleep()或join()或发出了I/O请求时，线程会进入到阻塞状态。当sleep()状态超时、join()等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。
5. 死亡状态(Dead)    : 线程执行完了或者因异常退出了run()方法，该线程结束生命周期。

## 2.1 **wait(), notify(), notifyAll()等方法介绍**

在Object.java中，定义了wait(), notify()和notifyAll()等接口。wait()的作用是让当前线程进入等待状态，同时，wait()也会让当前线程释放它所持有的锁。而notify()和notifyAll()的作用，则是唤醒当前对象上的等待线程；notify()是唤醒单个线程，而notifyAll()是唤醒所有的线程。

Object类中关于等待/唤醒的API详细信息如下：
**notify()**        -- 唤醒在此对象监视器上等待的单个线程。
**notifyAll()**   -- 唤醒在此对象监视器上等待的所有线程。
**wait()**                                         -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法”，当前线程被唤醒(进入“就绪状态”)。
**wait(long timeout)**                    -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量”，当前线程被唤醒(进入“就绪状态”)。
**wait(long timeout, int nanos)**  -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量”，当前线程被唤醒(进入“就绪状态”)。

## 2.2 为什么notify(), wait()等函数定义在Object中，而不是Thread中

Object中的wait(), notify()等函数，和synchronized一样，会对“对象的同步锁”进行操作。

wait()会使“当前线程”等待，因为线程进入等待状态，所以线程应该释放它锁持有的“同步锁”，否则其它线程获取不到该“同步锁”而无法运行！
OK，线程调用wait()之后，会释放它锁持有的“同步锁”；而且，根据前面的介绍，我们知道：等待线程可以被notify()或notifyAll()唤醒。现在，请思考一个问题：notify()是依据什么唤醒等待线程的？或者说，wait()等待线程和notify()之间是通过什么关联起来的？答案是：依据“对象的同步锁”。

负责唤醒等待线程的那个线程(我们称为“**唤醒线程**”)，它只有在获取“该对象的同步锁”(**这里的同步锁必须和等待线程的同步锁是同一个**)，并且调用notify()或notifyAll()方法之后，才能唤醒等待线程。虽然，等待线程被唤醒；但是，它不能立刻执行，因为唤醒线程还持有“该对象的同步锁”。必须等到唤醒线程释放了“对象的同步锁”之后，等待线程才能获取到“对象的同步锁”进而继续运行。

总之，notify(), wait()依赖于“同步锁”，而“同步锁”是对象锁持有，并且每个对象有且仅有一个！这就是为什么notify(), wait()等函数定义在Object类，而不是Thread类中的原因。

## 2.3 **yield()介绍**

**yield()的作用是让步。它能让当前线程由“运行状态”进入到“就绪状态”，从而让其它具有相同优先级的等待线程获取执行权；但是，并不能保证在当前线程调用yield()之后，其它具有相同优先级的线程就一定能获得执行权；也有可能是当前线程又进入到“运行状态”继续运行！**

## 2.4 yield() 与 wait()的比较

**我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。而yield()的作用是让步，它也会让当前线程离开“运行状态”。它们的区别是：(01) wait()是让线程由“运行状态”进入到“等待(阻塞)状态”，而不yield()是让线程由“运行状态”进入到“就绪状态”。(02) wait()是会线程释放它所持有对象的同步锁，而yield()方法不会释放锁。**

```java
// YieldLockTest.java 的源码
public class YieldLockTest{ 

    private static Object obj = new Object();

    public static void main(String[] args){ 
        ThreadA t1 = new ThreadA("t1"); 
        ThreadA t2 = new ThreadA("t2"); 
        t1.start(); 
        t2.start();
    } 

    static class ThreadA extends Thread{
        public ThreadA(String name){ 
            super(name); 
        } 
        public void run(){ 
            // 获取obj对象的同步锁
            synchronized (obj) {
                for(int i=0; i <10; i++){ 
                    System.out.printf("%s [%d]:%d\n", this.getName(), this.getPriority(), i); 
                    // i整除4时，调用yield
                    if (i%4 == 0)
                        Thread.yield();
                }
            }
        } 
    } 
}
```

**(某一次)运行结果**：

```
t1 [5]:0
t1 [5]:1
t1 [5]:2
t1 [5]:3
t1 [5]:4
t1 [5]:5
t1 [5]:6
t1 [5]:7
t1 [5]:8
t1 [5]:9
t2 [5]:0
t2 [5]:1
t2 [5]:2
t2 [5]:3
t2 [5]:4
t2 [5]:5
t2 [5]:6
t2 [5]:7
t2 [5]:8
t2 [5]:9
```

**结果说明**：
主线程main中启动了两个线程t1和t2。t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。在t1运行过程中，虽然它会调用Thread.yield()；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！

## 2.5 sleep()介绍

sleep() 定义在Thread.java中。
sleep() 的作用是让当前线程休眠，即当前线程会从“[运行状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”进入到“[休眠(阻塞)状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”。sleep()会指定休眠时间，线程休眠的时间会大于/等于该休眠时间；在线程重新被唤醒时，它会由“[阻塞状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”变成“[就绪状态](http://www.cnblogs.com/skywang12345/p/3479024.html)”，从而等待cpu的调度执行。

## 2.6 **6. sleep() 与 wait()的比较**

**我们知道，wait()的作用是让当前线程由“运行状态”进入“等待(阻塞)状态”的同时，也会释放同步锁。而sleep()的作用是也是让当前线程由“运行状态”进入到“休眠(阻塞)状态”。但是，wait()会释放对象的同步锁，而sleep()则不会释放锁。下面通过示例演示sleep()是不会释放锁的。**

```java
// SleepLockTest.java的源码
public class SleepLockTest{ 

    private static Object obj = new Object();

    public static void main(String[] args){ 
        ThreadA t1 = new ThreadA("t1"); 
        ThreadA t2 = new ThreadA("t2"); 
        t1.start(); 
        t2.start();
    } 

    static class ThreadA extends Thread{
        public ThreadA(String name){ 
            super(name); 
        } 
        public void run(){ 
            // 获取obj对象的同步锁
            synchronized (obj) {
                try {
                    for(int i=0; i <10; i++){ 
                        System.out.printf("%s: %d\n", this.getName(), i); 
                        // i能被4整除时，休眠100毫秒
                        if (i%4 == 0)
                            Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } 
    } 
}
```

主线程main中启动了两个线程t1和t2。t1和t2在run()会引用同一个对象的同步锁，即synchronized(obj)。在t1运行过程中，虽然它会调用Thread.sleep(100)；但是，t2是不会获取cpu执行权的。因为，t1并没有释放“obj所持有的同步锁”！
注意，若我们注释掉synchronized (obj)后再次执行该程序，t1和t2是可以相互切换的。



# 3. 线程的调度

一般线程调度模式分为两种——**抢占式调度和协同式调度**。抢占式调度指的是每条线程执行的时间、线程的切换都由系统控制，系统控制指的是在系统某种运行机制下，可能每条线程都分同样的执行时间片，也可能是某些线程执行的时间片较长，甚至某些线程得不到执行的时间片。在这种机制下，一个线程的堵塞不会导致整个进程堵塞。协同式调度指某一线程执行完后主动通知系统切换到另一线程上执行，这种模式就像接力赛一样，一个人跑完自己的路程就把接力棒交接给下一个人，下个人继续往下跑。线程的执行时间由线程本身控制，线程切换可以预知，不存在多线程同步问题，但它有一个致命弱点：如果一个线程编写有问题，运行到一半就一直堵塞，那么可能导致整个系统崩溃。

![](https://ws3.sinaimg.cn/large/006tKfTcly1g0jv2wlrhlj30et0a8755.jpg)

左边为抢占式线程调度，假如三条线程需要运行，处理器运行的路径是在线程一运行一个时间片后强制切换到线程二运行一个时间片，然后切到线程三，再回到线程一，如此循环直至三条线程都执行完。而协同式线程调度则不这样走，它会先将线程一执行完，线程一再通知线程二执行，线程二再通知线程三，直到线程三执行完。



Java使用的是哪种线程调度模式？此问题涉及到JVM的实现，JVM规范中规定每个线程都有优先级，且优先级越高越优先执行，但优先级高并不代表能独自占用执行时间片，可能是优先级高得到越多的执行时间片，反之，优先级低的分到的执行时间少但不会分配不到执行时间。JVM的规范没有严格地给调度策略定义，**一般Java使用的线程调度是抢占式调度**，在JVM中体现为让可运行池中优先级高的线程拥有CPU使用权，如果可运行池中线程优先级一样则随机选择线程，但要注意的是实际上一个绝对时间点只有一个线程在运行（这里是相对于一个CPU来说），直到此线程进入非可运行状态或另一个具有更高优先级的线程进入可运行线程池，才会使之让出CPU的使用权。



# 4. 线程间通信

## 4.1 介绍

本文总结我对于JAVA多线程中线程之间的通信方式的理解，主要以代码结合文字的方式来讨论线程间的通信，故摘抄了书中的一些示例代码。

## 4.2 **线程间的通信方式**

**共享内存机制和消息通信机制。**

### 4.2.1 **同步**

这里讲的同步是指多个线程通过synchronized关键字这种方式来实现线程间的通信。

参考示例：

```java
public class MyObject {

    synchronized public void methodA() {
        //do something....
    }

    synchronized public void methodB() {
        //do some other thing
    }
}

public class ThreadA extends Thread {

    private MyObject object;
//省略构造方法
    @Override
    public void run() {
        super.run();
        object.methodA();
    }
}

public class ThreadB extends Thread {

    private MyObject object;
//省略构造方法
    @Override
    public void run() {
        super.run();
        object.methodB();
    }
}

public class Run {
    public static void main(String[] args) {
        MyObject object = new MyObject();

        //线程A与线程B 持有的是同一个对象:object
        ThreadA a = new ThreadA(object);
        ThreadB b = new ThreadB(object);
        a.start();
        b.start();
    }
}
```

由于线程A和线程B持有同一个MyObject类的对象object，尽管这两个线程需要调用不同的方法，但是它们是同步执行的，比如：**线程B需要等待线程A执行完了methodA()方法之后，它才能执行methodB()方法。这样，线程A和线程B就实现了 通信。**

**这种方式，本质上就是“共享内存”式的通信。多个线程需要访问同一个共享变量，谁拿到了锁（获得了访问权限），谁就可以执行。**

### 4.2.2 **while轮询的方式**

代码如下：

```java
 1 import java.util.ArrayList;
 2 import java.util.List;
 3 
 4 public class MyList {
 5 
 6     private List<String> list = new ArrayList<String>();
 7     public void add() {
 8         list.add("elements");
 9     }
10     public int size() {
11         return list.size();
12     }
13 }
14 
15 import mylist.MyList;
16 
17 public class ThreadA extends Thread {
18 
19     private MyList list;
20 
21     public ThreadA(MyList list) {
22         super();
23         this.list = list;
24     }
25 
26     @Override
27     public void run() {
28         try {
29             for (int i = 0; i < 10; i++) {
30                 list.add();
31                 System.out.println("添加了" + (i + 1) + "个元素");
32                 Thread.sleep(1000);
33             }
34         } catch (InterruptedException e) {
35             e.printStackTrace();
36         }
37     }
38 }
39 
40 import mylist.MyList;
41 
42 public class ThreadB extends Thread {
43 
44     private MyList list;
45 
46     public ThreadB(MyList list) {
47         super();
48         this.list = list;
49     }
50 
51     @Override
52     public void run() {
53         try {
54             while (true) {
55                 if (list.size() == 5) {
56                     System.out.println("==5, 线程b准备退出了");
57                     throw new InterruptedException();
58                 }
59             }
60         } catch (InterruptedException e) {
61             e.printStackTrace();
62         }
63     }
64 }
65 
66 import mylist.MyList;
67 import extthread.ThreadA;
68 import extthread.ThreadB;
69 
70 public class Test {
71 
72     public static void main(String[] args) {
73         MyList service = new MyList();
74 
75         ThreadA a = new ThreadA(service);
76         a.setName("A");
77         a.start();
78 
79         ThreadB b = new ThreadB(service);
80         b.setName("B");
81         b.start();
82     }
83 }
```

在这种方式下，线程A不断地改变条件，线程ThreadB不停地通过while语句检测这个条件(list.size()==5)是否成立 ，从而实现了线程间的通信。但是**这种方式会浪费CPU资源**。之所以说它浪费资源，是因为JVM调度器将CPU交给线程B执行时，它没做啥“有用”的工作，只是在不断地测试 某个条件是否成立。*就类似于现实生活中，某个人一直看着手机屏幕是否有电话来了，而不是： 在干别的事情，当有电话来时，响铃通知TA电话来了。*关于线程的轮询的影响，[可参考：](http://www.cnblogs.com/hapjin/p/5467984.html)[JAVA多线程之当一个线程在执行死循环时会影响另外一个线程吗？](http://www.cnblogs.com/hapjin/p/5467984.html)

这种方式还存在另外一个问题：

轮询的条件的可见性问题，关于内存可见性问题，可参考：[JAVA多线程之volatile 与 synchronized 的比较](http://www.cnblogs.com/hapjin/p/5492880.html)中的第一点“**一，volatile关键字的可见性**”

线程都是先把变量读取到本地线程栈空间，然后再去再去修改的本地变量。因此，如果线程B每次都在取本地的 条件变量，那么尽管另外一个线程已经改变了轮询的条件，它也察觉不到，这样也会造成死循环。 

### 4.2.3 **wait/notify机制**

代码如下：

```java
 1 import java.util.ArrayList;
 2 import java.util.List;
 3 
 4 public class MyList {
 5 
 6     private static List<String> list = new ArrayList<String>();
 7 
 8     public static void add() {
 9         list.add("anyString");
10     }
11 
12     public static int size() {
13         return list.size();
14     }
15 }
16 
17 
18 public class ThreadA extends Thread {
19 
20     private Object lock;
21 
22     public ThreadA(Object lock) {
23         super();
24         this.lock = lock;
25     }
26 
27     @Override
28     public void run() {
29         try {
30             synchronized (lock) {
31                 if (MyList.size() != 5) {
32                     System.out.println("wait begin "
33                             + System.currentTimeMillis());
34                     lock.wait();
35                     System.out.println("wait end  "
36                             + System.currentTimeMillis());
37                 }
38             }
39         } catch (InterruptedException e) {
40             e.printStackTrace();
41         }
42     }
43 }
44 
45 
46 public class ThreadB extends Thread {
47     private Object lock;
48 
49     public ThreadB(Object lock) {
50         super();
51         this.lock = lock;
52     }
53 
54     @Override
55     public void run() {
56         try {
57             synchronized (lock) {
58                 for (int i = 0; i < 10; i++) {
59                     MyList.add();
60                     if (MyList.size() == 5) {
61                         lock.notify();
62                         System.out.println("已经发出了通知");
63                     }
64                     System.out.println("添加了" + (i + 1) + "个元素!");
65                     Thread.sleep(1000);
66                 }
67             }
68         } catch (InterruptedException e) {
69             e.printStackTrace();
70         }
71     }
72 }
73 
74 public class Run {
75 
76     public static void main(String[] args) {
77 
78         try {
79             Object lock = new Object();
80 
81             ThreadA a = new ThreadA(lock);
82             a.start();
83 
84             Thread.sleep(50);
85 
86             ThreadB b = new ThreadB(lock);
87             b.start();
88         } catch (InterruptedException e) {
89             e.printStackTrace();
90         }
91     }
92 }
```

线程A要等待某个条件满足时(list.size()==5)，才执行操作。线程B则向list中添加元素，改变list 的size。

A,B之间如何通信的呢？也就是说，线程A如何知道 list.size() 已经为5了呢？

这里用到了Object类的 wait() 和 notify() 方法。

当条件未满足时(list.size() !=5)，线程A调用wait() 放弃CPU，并进入阻塞状态。---不像②while轮询那样占用CPU

当条件满足时，线程B调用 notify()通知 线程A，所谓通知线程A，就是唤醒线程A，并让它进入可运行状态。

这种方式的一个好处就是CPU的利用率提高了。

但是也有一些缺点：比如，线程B先执行，一下子添加了5个元素并调用了notify()发送了通知，而此时线程A还执行；当线程A执行并调用wait()时，那它永远就不可能被唤醒了。因为，线程B已经发了通知了，以后不再发通知了。这说明：**通知过早，会打乱程序的执行逻辑。**

### 4.2.4 **管道通信**

就是使用java.io.PipedInputStream 和 java.io.PipedOutputStream进行通信

具体就不介绍了。分布式系统中说的两种通信机制：**共享内存机制和消息通信机制**。感觉前面的①中的synchronized关键字和②中的while轮询 “属于” 共享内存机制，由于是轮询的条件使用了volatile关键字修饰时，这就表示它们通过判断这个“共享的条件变量“是否改变了，来实现进程间的交流。

而管道通信，更像消息传递机制，也就是说：通过管道，将一个线程中的消息发送给另一个。



# 4. 守护线程

Java的线程分为两种：User Thread(用户线程)、DaemonThread(守护线程)。

只要当前JVM实例中尚存任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束是，守护线程随着JVM一同结束工作，Daemon作用是为其他线程提供便利服务，守护线程最典型的应用就是GC(垃圾回收器)，他就是一个很称职的守护者。

User和Daemon两者几乎没有区别，唯一的不同之处就在于虚拟机的离开：如果 User Thread已经全部退出运行了，只剩下Daemon Thread存在了，虚拟机也就退出了。 因为没有了被守护者，Daemon也就没有工作可做了，也就没有继续运行程序的必要了。

首先看一个例子，主线程中建立一个守护线程，当主线程结束时，守护线程也跟着结束。

```
`package` `com.daemon;  ``  ` `import` `java.util.concurrent.TimeUnit;  ``  ` `public` `class` `DaemonThreadTest  ``{  ``    ``public` `static` `void` `main(String[] args)  ``    ``{  ``        ``Thread mainThread = ``new` `Thread(``new` `Runnable(){  ``            ``@Override` `            ``public` `void` `run()  ``            ``{  ``                ``Thread childThread = ``new` `Thread(``new` `ClildThread());  ``                ``childThread.setDaemon(``true``);  ``                ``childThread.start();  ``                ``System.out.println(``"I'm main thread..."``);  ``            ``}  ``        ``});  ``        ``mainThread.start();  ``    ``}  ``}  ``  ` `class` `ClildThread ``implements` `Runnable  ``{  ``    ``@Override` `    ``public` `void` `run()  ``    ``{  ``        ``while``(``true``)  ``        ``{  ``            ``System.out.println(``"I'm child thread.."``);  ``            ``try` `            ``{  ``                ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``            ``}  ``            ``catch` `(InterruptedException e)  ``            ``{  ``                ``e.printStackTrace();  ``            ``}  ``        ``}  ``    ``}  ``}`
```

运行结果：

```
`I'm child thread..  ``I'm main thread...`
```

如果不何止childThread为守护线程，当主线程结束时，childThread还在继续运行，如下：

```
`package` `com.daemon;  ``  ` `import` `java.util.concurrent.TimeUnit;  ``  ` `public` `class` `DaemonThreadTest  ``{  ``    ``public` `static` `void` `main(String[] args)  ``    ``{  ``        ``Thread mainThread = ``new` `Thread(``new` `Runnable(){  ``            ``@Override` `            ``public` `void` `run()  ``            ``{  ``                ``Thread childThread = ``new` `Thread(``new` `ClildThread());  ``                ``childThread.setDaemon(``false``);  ``                ``childThread.start();  ``                ``System.out.println(``"I'm main thread..."``);  ``            ``}  ``        ``});  ``        ``mainThread.start();  ``    ``}  ``}  ``  ` `class` `ClildThread ``implements` `Runnable  ``{  ``    ``@Override` `    ``public` `void` `run()  ``    ``{  ``        ``while``(``true``)  ``        ``{  ``            ``System.out.println(``"I'm child thread.."``);  ``            ``try` `            ``{  ``                ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``            ``}  ``            ``catch` `(InterruptedException e)  ``            ``{  ``                ``e.printStackTrace();  ``            ``}  ``        ``}  ``    ``}  ``}`
```

运行结果：

```
`I'm main thread...  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..（无限输出）`
```

可以看到，当主线程结束时，childThread是非守护线程，就会无限的执行。

守护线程有一个应用场景，就是当主线程结束时，结束其余的子线程（守护线程）自动关闭，就免去了还要继续关闭子线程的麻烦。不过博主推荐，如果真有这种场景，还是用中断的方式实现比较合理。

还有补充一点，不是说当子线程是守护线程，主线程结束，子线程就跟着结束，这里的前提条件是：当前jvm应用实例中没有用户线程继续执行，如果有其他用户线程继续执行，那么后台线程不会中断，如下：

```
`package` `com.daemon;  ``  ` `import` `java.util.concurrent.TimeUnit;  ``  ` `public` `class` `DaemonThreadTest  ``{  ``    ``public` `static` `void` `main(String[] args)  ``    ``{  ``        ``Thread mainThread = ``new` `Thread(``new` `Runnable(){  ``            ``@Override` `            ``public` `void` `run()  ``            ``{  ``                ``Thread childThread = ``new` `Thread(``new` `ClildThread());  ``                ``childThread.setDaemon(``true``);  ``                ``childThread.start();  ``                ``System.out.println(``"I'm main thread..."``);  ``            ``}  ``        ``});  ``        ``mainThread.start();  ``          ` `        ``Thread otherThread = ``new` `Thread(``new` `Runnable(){  ``            ``@Override` `            ``public` `void` `run()  ``            ``{  ``                ``while``(``true``)  ``                ``{  ``                    ``System.out.println(``"I'm other user thread..."``);  ``                    ``try` `                    ``{  ``                        ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``                    ``}  ``                    ``catch` `(InterruptedException e)  ``                    ``{  ``                        ``e.printStackTrace();  ``                    ``}  ``                ``}  ``            ``}  ``        ``});  ``        ``otherThread.start();  ``    ``}  ``}  ``  ` `class` `ClildThread ``implements` `Runnable  ``{  ``    ``@Override` `    ``public` `void` `run()  ``    ``{  ``        ``while``(``true``)  ``        ``{  ``            ``System.out.println(``"I'm child thread.."``);  ``            ``try` `            ``{  ``                ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``            ``}  ``            ``catch` `(InterruptedException e)  ``            ``{  ``                ``e.printStackTrace();  ``            ``}  ``        ``}  ``    ``}  ``}`
```

运行结果：

```
`I'm other user thread...  ``I'm child thread..  ``I'm main thread...  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm child thread..  ``I'm other user thread...  ``I'm other user thread...  ``I'm child thread..  ``I'm child thread..  ``I'm other user thread...  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..  ``I'm other user thread...  ``I'm child thread..（无限输出）`
```

如果需要在主线程结束时，将子线程结束掉，可以采用如下的中断方式：

```
`package` `com.self;  ``  ` `import` `java.util.concurrent.ExecutorService;  ``import` `java.util.concurrent.Executors;  ``import` `java.util.concurrent.TimeUnit;  ``  ` `public` `class` `ThreadTest  ``{  ``  ` `    ``public` `static` `void` `main(String[] args)  ``    ``{  ``        ``Thread mainThread = ``new` `Thread(``new` `Runnable(){  ``            ``public` `void` `run()  ``            ``{  ``                ``System.out.println(``"主线程开始..."``);  ``                ``Thread sonThread = ``new` `Thread(``new` `Thread1(Thread.currentThread()));  ``                ``sonThread.setDaemon(``false``);  ``                ``sonThread.start();  ``                  ` `                ``try` `                ``{  ``                    ``TimeUnit.MILLISECONDS.sleep(``10000``);  ``                ``}  ``                ``catch` `(InterruptedException e)  ``                ``{  ``                    ``e.printStackTrace();  ``                ``}  ``                ``System.out.println(``"主线程结束"``);  ``            ``}  ``        ``});  ``        ``mainThread.start();  ``    ``}  ``      ` `}  ``  ` `class` `Thread1 ``implements` `Runnable  ``{  ``    ``private` `Thread mainThread;  ``      ` `    ``public` `Thread1(Thread mainThread)  ``    ``{  ``        ``this``.mainThread = mainThread;  ``    ``}  ``      ` `    ``@Override` `    ``public` `void` `run()  ``    ``{  ``        ``while``(mainThread.isAlive())  ``        ``{  ``            ``System.out.println(``"子线程运行中...."``);  ``            ``try` `            ``{  ``                ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``            ``}  ``            ``catch` `(InterruptedException e)  ``            ``{  ``                ``e.printStackTrace();  ``            ``}  ``        ``}  ``    ``}  ``      ` `}`
```

运行结果：

```
`主线程开始...  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``子线程运行中....  ``主线程结束`
```

主线程结束

回归正题，这里有几点需要注意：
(1) thread.setDaemon(true)必须在thread.start()之前设置，否则会跑出一个IllegalThreadStateException异常。你不能把正在运行的常规线程设置为守护线程。
(2) 在Daemon线程中产生的新线程也是Daemon的。
(3) 不要认为所有的应用都可以分配给Daemon来进行服务，比如读写操作或者计算逻辑。
写java多线程程序时，一般比较喜欢用java自带的多线程框架，比如ExecutorService，但是java的线程池会将守护线程转换为用户线程，所以如果要使用后台线程就不能用java的线程池。
如下，线程池中将daemon线程转换为用户线程的程序片段：

```
`static` `class` `DefaultThreadFactory ``implements` `ThreadFactory {  ``    ``private` `static` `final` `AtomicInteger poolNumber = ``new` `AtomicInteger(``1``);  ``    ``private` `final` `ThreadGroup group;  ``    ``private` `final` `AtomicInteger threadNumber = ``new` `AtomicInteger(``1``);  ``    ``private` `final` `String namePrefix;  ``  ` `    ``DefaultThreadFactory() {  ``        ``SecurityManager s = System.getSecurityManager();  ``        ``group = (s != ``null``) ? s.getThreadGroup() :  ``                              ``Thread.currentThread().getThreadGroup();  ``        ``namePrefix = ``"pool-"` `+  ``                      ``poolNumber.getAndIncrement() +  ``                     ``"-thread-"``;  ``    ``}  ``  ` `    ``public` `Thread newThread(Runnable r) {  ``        ``Thread t = ``new` `Thread(group, r,  ``                              ``namePrefix + threadNumber.getAndIncrement(),  ``                              ``0``);  ``        ``if` `(t.isDaemon())  ``            ``t.setDaemon(``false``);  ``        ``if` `(t.getPriority() != Thread.NORM_PRIORITY)  ``            ``t.setPriority(Thread.NORM_PRIORITY);  ``        ``return` `t;  ``    ``}  ``}`
```

注意到，这里不仅会将守护线程转变为用户线程，而且会把优先级转变为Thread.NORM_PRIORITY。
如下所示，将守护线程采用线程池的方式开启：

```
`package` `com.daemon;  ``  ` `import` `java.util.concurrent.ExecutorService;  ``import` `java.util.concurrent.Executors;  ``import` `java.util.concurrent.TimeUnit;  ``  ` `public` `class` `DaemonThreadTest  ``{  ``    ``public` `static` `void` `main(String[] args)  ``    ``{  ``        ``Thread mainThread = ``new` `Thread(``new` `Runnable(){  ``            ``@Override` `            ``public` `void` `run()  ``            ``{  ``                ``ExecutorService exec = Executors.newCachedThreadPool();  ``                ``Thread childThread = ``new` `Thread(``new` `ClildThread());  ``                ``childThread.setDaemon(``true``);  ``                ``exec.execute(childThread);  ``                ``exec.shutdown();  ``                ``System.out.println(``"I'm main thread..."``);  ``            ``}  ``        ``});  ``        ``mainThread.start();  ``    ``}  ``}  ``  ` `class` `ClildThread ``implements` `Runnable  ``{  ``    ``@Override` `    ``public` `void` `run()  ``    ``{  ``        ``while``(``true``)  ``        ``{  ``            ``System.out.println(``"I'm child thread.."``);  ``            ``try` `            ``{  ``                ``TimeUnit.MILLISECONDS.sleep(``1000``);  ``            ``}  ``            ``catch` `(InterruptedException e)  ``            ``{  ``                ``e.printStackTrace();  ``            ``}  ``        ``}  ``    ``}  ``}`
```

运行结果：

```
`I'm main thread...  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..  ``I'm child thread..（无限输出）`
```

上面代码证实了线程池会将守护线程转变为用户线程。



# 5. 幽灵线程

# 6. 线程优先级

## 6.1 优先级取值范围
Java 线程优先级使用 1 ~ 10 的整数表示：

最低优先级 1：Thread.MIN_PRIORITY

最高优先级 10：Thread.MAX_PRIORITY

普通优先级 5：Thread.NORM_PRIORITY

## 6.2 获取线程优先级
public static void main(String[] args) {
    System.out.println(Thread.currentThread().getPriority());
}

运行结果： 

![](https://ws4.sinaimg.cn/large/006tKfTcly1g0lzytni8gj30k202xt9d.jpg)

## 6.3 设置优先级
Java 使用 setPriority 方法设置线程优先级，方法签名

public final void setPriority(int newPriority)
示例：

public static void main(String[] args) {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    System.out.println(Thread.currentThread().getPriority());
    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    System.out.println(Thread.currentThread().getPriority());
    Thread.currentThread().setPriority(8);
    System.out.println(Thread.currentThread().getPriority());
}
运行结果：

![](https://ws2.sinaimg.cn/large/006tKfTcly1g0lzzfbrn2j30k2041aar.jpg)

newPriority 设置范围在 1-10，否则抛出 java.lang.IllegalArgumentException 异常

public static void main(String[] args) {
    Thread.currentThread().setPriority(0);
    System.out.println(Thread.currentThread().getPriority());
}
运行结果：

![](https://ws3.sinaimg.cn/large/006tKfTcly1g0lzzpsiv1j30k203l3zo.jpg)

## 6.4  默认线程优先级
Java 默认的线程优先级是父线程的优先级，而非普通优先级Thread.NORM_PRIORITY，因为主线程默认优先级是普通优先级Thread.NORM_PRIORITY，所以如果不主动设置线程优先级，则新创建的线程的优先级就是普通优先级Thread.NORM_PRIORITY

class CustomThread extends Thread {
    @Override
    public void run() {
        System.out.println("父线程优先级：" + this.getPriority());
        Thread t = new Thread(new CustomRunnable());
        System.out.println("子线程优先级：" + t.getPriority());
    }
}

class CustomRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("CustomRunnable : " + i);
        }
    }
}

public static void main(String[] args) {
    Thread t = new CustomThread();
    t.setPriority(3);
    t.start();
}
运行结果：

![](https://ws1.sinaimg.cn/large/006tKfTcly1g0m007erlkj30k203edgl.jpg)

## 6.5 线程调度
高优先级的线程比低优先级的线程有更高的几率得到执行，实际上这和操作系统及虚拟机版本相关，有可能即使设置了线程的优先级也不会产生任何作用。

class CustomThread extends Thread {
    public CustomThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
        }
    }
}

public static void main(String[] args) {
    Thread t1 = new CustomThread("A");
    Thread t2 = new CustomThread("B");
    t1.setPriority(Thread.MIN_PRIORITY);
    t2.setPriority(Thread.MAX_PRIORITY);
    t1.start();
    t2.start();
}
运行结果：

![](https://ws2.sinaimg.cn/large/006tKfTcly1g0m00luh9oj30k20cxab9.jpg)

## 6.6 线程组的最大优先级
我们可以设定线程组的最大优先级，当创建属于该线程组的线程时，新线程的优先级不能超过这个最大优先级

系统线程组的最大优先级默认为 Thread.MAX_PRIORITY

创建线程组时最大优先级默认为父线程组（如果未指定父线程组，则其父线程组默认为当前线程所属线程组）的最大优先级

可以通过 setPriority 更改最大优先级，但无法超过父线程组的最大优先级

## 6.7 setPriority 注意事项
该方法只能更改本线程组及其子线程组（递归）的最大优先级，但不能影响已经创建的直接或间接属于该线程组的线程的优先级，也就是说，即使目前有一个子线程的优先级比新设定的线程组优先级大，也不会更改该子线程的优先级。只有当试图改变子线程的优先级或者创建新的子线程的时候，线程组的最大优先级才起作用

## 6.8 线程优先级的问题
Thread.setPriority() 是否起作用和操作系统及虚拟机版本相关

线程优先级对于不同的线程调度器可能有不同的含义，可能并不是你直观的推测。特别地，优先级并不一定是指CPU的分享。在UNIX系统，优先级或多或少可以认为是CPU的分配，但Windows不是这样

线程的优先级通常是全局的和局部的优先级设定的组合。Java的 setPriority() 方法只应用于局部的优先级。换句话说，你不能在整个可能的范围内设定优先级，这通常是一种保护的方式，你大概不希望鼠标指针的线程或者处理音频数据的线程被其它随机的用户线程所抢占

不同的系统有不同的线程优先级的取值范围，但是Java定义了10个级别（1-10）。这样就有可能出现几个线程在一个操作系统里有不同的优先级，在另外一个操作系统里却有相同的优先级，并因此可能有意想不到的行为

操作系统可能（并通常这么做）根据线程的优先级给线程添加一些专有的行为，如 only give a quantum boost if the priority is below X，这里再重复一次，优先级的定义有部分在不同系统间有差别

大多数操作系统的线程调度器实际上执行的是在战略的角度上对线程的优先级做临时操作，例如当一个线程接收到它所等待的一个事件或者 I/O，通常操作系统知道最多，试图手工控制优先级可能只会干扰这个系统

应用程序通常不知道有哪些其它运行的线程，所以对于整个系统来说，变更一个线程的优先级所带来的影响是难于预测的，例如有一个预期为偶尔在后台运行的低优先级的线程几乎没有运行，原因是一个病毒监控程序在一个稍微高一点的优先级（但仍然低于普通的优先级）上运行，并且无法预计你程序的性能，它会根据你的客户使用的防病毒程序不同而不同。



# 7. ThreadLocal

 [ThreadLocal.md](ThreadLocal.md) 

# 8. 线程池

 [Java线程池的实现原理.md](Java线程池的实现原理.md) 































