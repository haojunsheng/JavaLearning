<!--ts-->

<!--te-->

## 1 [线程池的基础架构](http://cmsblogs.com/?p=2444)

### 1.1 Executor

我们先看线程池的基础架构图：

![img](https://ws2.sinaimg.cn/large/006tKfTcly1g0m2gslq95j30cs0b4mxf.jpg)

**Executor**

Executor，任务的执行者，线程池框架中几乎所有类都直接或者间接实现Executor接口，它是线程池框架的基础。Executor提供了一种将“任务提交”与“任务执行”分离开来的机制，它仅提供了一个Execute()方法用来执行已经提交的Runnable任务。

```java
public interface Executor {
    void execute(Runnable command);
}
```

**ExcutorService**

继承Executor，它是“执行者服务”接口，它是为”执行者接口Executor”服务而存在的。准确的地说，ExecutorService提供了”将任务提交给执行者的接口(submit方法)”，”让执行者执行任务(invokeAll, invokeAny方法)”的接口等等。

```java
public interface ExecutorService extends Executor {

    /**
     * 启动一次顺序关闭，执行以前提交的任务，但不接受新任务
     */
    void shutdown();

    /**
     * 试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表
     */
    List<Runnable> shutdownNow();

    /**
     * 如果此执行程序已关闭，则返回 true。
     */
    boolean isShutdown();

    /**
     * 如果关闭后所有任务都已完成，则返回 true
     */
    boolean isTerminated();

    /**
     * 请求关闭、发生超时或者当前线程中断，无论哪一个首先发生之后，都将导致阻塞，直到所有任务完成执行
     */
    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 提交一个返回值的任务用于执行，返回一个表示任务的未决结果的 Future
     */
    <T> Future<T> submit(Callable<T> task);

    /**
     * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
     */
    <T> Future<T> submit(Runnable task, T result);

    /**
     * 提交一个 Runnable 任务用于执行，并返回一个表示该任务的 Future
     */
    Future<?> submit(Runnable task);

    /**
     * 执行给定的任务，当所有任务完成时，返回保持任务状态和结果的 Future 列表
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

    /**
     * 执行给定的任务，当所有任务完成或超时期满时（无论哪个首先发生），返回保持任务状态和结果的 Future 列表
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * 执行给定的任务，如果某个任务已成功完成（也就是未抛出异常），则返回其结果
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;

    /**
     * 执行给定的任务，如果在给定的超时期满前某个任务已成功完成（也就是未抛出异常），则返回其结果
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

**AbstractExecutorService**

抽象类，实现ExecutorService接口，为其提供默认实现。AbstractExecutorService除了实现ExecutorService接口外，还提供了newTaskFor()方法返回一个RunnableFuture，在运行的时候，它将调用底层可调用任务，作为 Future 任务，它将生成可调用的结果作为其结果，并为底层任务提供取消操作。

**ScheduledExecutorService**

继承ExcutorService，为一个“延迟”和“定期执行”的ExecutorService。他提供了一些如下几个方法安排任务在给定的延时执行或者周期性执行。

```java
// 创建并执行在给定延迟后启用的 ScheduledFuture。
<V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit)

// 创建并执行在给定延迟后启用的一次性操作。
ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)

// 创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；
//也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。
ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)

// 创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。
ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
```

**ThreadPoolExecutor**

大名鼎鼎的“线程池”，后续做详细介绍。

**ScheduledThreadPoolExecutor**

ScheduledThreadPoolExecutor继承ThreadPoolExecutor并且实现ScheduledExecutorService接口，是两者的集大成者，相当于提供了“延迟”和“周期执行”功能的ThreadPoolExecutor。

**Executors**

静态工厂类，提供了Executor、ExecutorService、ScheduledExecutorService、ThreadFactory 、Callable 等类的静态工厂方法，通过这些工厂方法我们可以得到相对应的对象。

1. 创建并返回设置有常用配置字符串的 ExecutorService 的方法。
2. 创建并返回设置有常用配置字符串的 ScheduledExecutorService 的方法。
3. 创建并返回“包装的”ExecutorService 方法，它通过使特定于实现的方法不可访问来禁用重新配置。
4. 创建并返回 ThreadFactory 的方法，它可将新创建的线程设置为已知的状态。
5. 创建并返回非闭包形式的 Callable 的方法，这样可将其用于需要 Callable 的执行方法中。

### 1.2 Future

Future接口和实现Future接口的FutureTask代表了线程池的异步计算结果。

AbstractExecutorService提供了newTaskFor()方法返回一个RunnableFuture，除此之外当我们把一个Runnable或者Callable提交给（submit()）ThreadPoolExecutor或者ScheduledThreadPoolExecutor时，他们则会向我们返回一个FutureTask对象。如下：

```java
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new FutureTask<T>(runnable, value);
    }

        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
    }

    <T> Future<T> submit(Callable<T> task)
    <T> Future<T> submit(Runnable task, T result)
    Future<> submit(Runnable task)
```

![img](https://ws4.sinaimg.cn/large/006tKfTcly1g0m39v528vj306g06k3yh.jpg)

**Future**

作为异步计算的顶层接口，Future对具体的Runnable或者Callable任务提供了三种操作：执行任务的取消、查询任务是否完成、获取任务的执行结果。其接口定义如下：

```java
public interface Future<V> {

    /**
     * 试图取消对此任务的执行
     * 如果任务已完成、或已取消，或者由于某些其他原因而无法取消，则此尝试将失败。
     * 当调用 cancel 时，如果调用成功，而此任务尚未启动，则此任务将永不运行。
     * 如果任务已经启动，则 mayInterruptIfRunning 参数确定是否应该以试图停止任务的方式来中断执行此任务的线程
     */
    boolean cancel(boolean mayInterruptIfRunning);

    /**
     * 如果在任务正常完成前将其取消，则返回 true
     */
    boolean isCancelled();

    /**
     * 如果任务已完成，则返回 true
     */
    boolean isDone();

    /**
     *   如有必要，等待计算完成，然后获取其结果
     */
    V get() throws InterruptedException, ExecutionException;

    /**
     * 如有必要，最多等待为使计算完成所给定的时间之后，获取其结果（如果结果可用）
     */
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
```

**RunnableFuture**

继承Future、Runnable两个接口，为两者的合体，即所谓的Runnable的Future。提供了一个run()方法可以完成Future并允许访问其结果。

```java
public interface RunnableFuture<V> extends Runnable, Future<V> {
    //在未被取消的情况下，将此 Future 设置为计算的结果
    void run();
}
```

**FutureTask**

实现RunnableFuture接口，既可以作为Runnable被执行，也可以作为Future得到Callable的返回值。

## 2 [ThreadPoolExecutor](http://cmsblogs.com/?p=2448)

作为Executor框架中最核心的类，ThreadPoolExecutor代表着鼎鼎大名的线程池，它给了我们足够的理由来弄清楚它。

下面我们就通过源码来一步一步弄清楚它。

### 2.1 内部状态

线程有五种状态：新建，就绪，运行，阻塞，死亡，线程池同样有五种状态：Running, SHUTDOWN, STOP, TIDYING, TERMINATED。

```java
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
```

变量**ctl**定义为AtomicInteger ，其功能非常强大，记录了“线程池中的任务数量”和“线程池的状态”两个信息。共32位，其中高3位表示”线程池状态”，低29位表示”线程池中的任务数量”。

```java
RUNNING            -- 对应的高3位值是111。
SHUTDOWN       -- 对应的高3位值是000。
STOP                   -- 对应的高3位值是001。
TIDYING              -- 对应的高3位值是010。
TERMINATED     -- 对应的高3位值是011。
```

**RUNNING**：处于RUNNING状态的线程池能够接受新任务，以及对新添加的任务进行处理。

**SHUTDOWN**：处于SHUTDOWN状态的线程池不可以接受新任务，但是可以对已添加的任务进行处理。

**STOP**：处于STOP状态的线程池不接收新任务，不处理已添加的任务，并且会中断正在处理的任务。

**TIDYING**：当所有的任务已终止，ctl记录的”任务数量”为0，线程池会变为TIDYING状态。当线程池变为TIDYING状态时，会执行钩子函数terminated()。terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；可以通过重载terminated()函数来实现。

**TERMINATED**：线程池彻底终止的状态。

各个状态的转换如下：

![img](https://ws2.sinaimg.cn/large/006tKfTcly1g0m3ig34m9j30pq09kmxx.jpg)

### 2.2 创建线程池

我们可以通过ThreadPoolExecutor构造函数来创建一个线程池：

```java
    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
            maximumPoolSize <= 0 ||
            maximumPoolSize < corePoolSize ||
            keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }
```

共有七个参数，每个参数含义如下：

**corePoolSize**

线程池中核心线程的数量。当提交一个任务时，线程池会新建一个线程来执行任务，直到当前线程数等于corePoolSize。如果调用了线程池的prestartAllCoreThreads()方法，线程池会提前创建并启动所有基本线程。

**maximumPoolSize**

线程池中允许的最大线程数。线程池的阻塞队列满了之后，如果还有任务提交，如果当前的线程数小于maximumPoolSize，则会新建线程来执行任务。注意，如果使用的是无界队列，该参数也就没有什么效果了。

**keepAliveTime**

线程空闲的时间。线程的创建和销毁是需要代价的。线程执行完任务后不会立即销毁，而是继续存活一段时间：keepAliveTime。默认情况下，该参数只有在线程数大于corePoolSize时才会生效。

**unit**

keepAliveTime的单位。TimeUnit

**workQueue**

用来保存等待执行的任务的阻塞队列，等待的任务必须实现Runnable接口。我们可以选择如下几种：

- ArrayBlockingQueue：基于数组结构的有界阻塞队列，FIFO。[【死磕Java并发】—-J.U.C之阻塞队列：ArrayBlockingQueue](http://cmsblogs.com/?p=2381)
- LinkedBlockingQueue：基于链表结构的有界阻塞队列，FIFO。
- SynchronousQueue：不存储元素的阻塞队列，每个插入操作都必须等待一个移出操作，反之亦然。[【死磕Java并发】—-J.U.C之阻塞队列：SynchronousQueue](http://cmsblogs.com/?p=2418)
- PriorityBlockingQueue：具有优先界别的阻塞队列。[【死磕Java并发】—-J.U.C之阻塞队列：PriorityBlockingQueue](http://cmsblogs.com/?p=2407)

**threadFactory**

用于设置创建线程的工厂。该对象可以通过Executors.defaultThreadFactory()，如下：

```java
    public static ThreadFactory defaultThreadFactory() {
        return new DefaultThreadFactory();
    }
```

返回的是DefaultThreadFactory对象，源码如下：

```java
    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
```

ThreadFactory的左右就是提供创建线程的功能的线程工厂。他是通过newThread()方法提供创建线程的功能，newThread()方法创建的线程都是“非守护线程”而且“线程优先级都是Thread.NORM_PRIORITY”。

**handler**

RejectedExecutionHandler，线程池的拒绝策略。所谓拒绝策略，是指将任务添加到线程池中时，线程池拒绝该任务所采取的相应策略。当向线程池中提交任务时，如果此时线程池中的线程已经饱和了，而且阻塞队列也已经满了，则线程池会选择一种拒绝策略来处理该任务。

线程池提供了四种拒绝策略：

1. AbortPolicy：直接抛出异常，默认策略；
2. CallerRunsPolicy：用调用者所在的线程来执行任务；
3. DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
4. DiscardPolicy：直接丢弃任务；

当然我们也可以实现自己的拒绝策略，例如记录日志等等，实现RejectedExecutionHandler接口即可。



####**为什么不允许使用 Executors 创建线程池**

![img](https://ws2.sinaimg.cn/large/006tKfTcly1g0m4wjlng7j31te0iu0xu.jpg)

### 2.3 线程池

Executor框架提供了三种线程池，他们都可以通过工具类Executors来创建。

**FixedThreadPool**

FixedThreadPool，可重用固定线程数的线程池，其定义如下：

```java
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
```

corePoolSize 和 maximumPoolSize都设置为创建FixedThreadPool时指定的参数nThreads，意味着当线程池满时且阻塞队列也已经满时，如果继续提交任务，则会直接走拒绝策略，该线程池不会再新建线程来执行任务，而是直接走拒绝策略。FixedThreadPool使用的是默认的拒绝策略，即AbortPolicy，则直接抛出异常。

keepAliveTime设置为0L，表示空闲的线程会立刻终止。

workQueue则是使用LinkedBlockingQueue，但是没有设置范围，那么则是最大值（Integer.MAX_VALUE），这基本就相当于一个无界队列了。使用该“无界队列”则会带来哪些影响呢？当线程池中的线程数量等于corePoolSize 时，如果继续提交任务，该任务会被添加到阻塞队列workQueue中，当阻塞队列也满了之后，则线程池会新建线程执行任务直到maximumPoolSize。由于FixedThreadPool使用的是“无界队列”LinkedBlockingQueue，那么maximumPoolSize参数无效，同时指定的拒绝策略AbortPolicy也将无效。而且该线程池也不会拒绝提交的任务，如果客户端提交任务的速度快于任务的执行，那么keepAliveTime也是一个无效参数。

其运行图如下（参考《Java并发编程的艺术》）：

![img](https://ws4.sinaimg.cn/large/006tKfTcly1g0m3wpuknyj30ps0e2gm0.jpg)

**SingleThreadExecutor**

SingleThreadExecutor是使用单个worker线程的Executor，定义如下：

```java
    public static ExecutorService newSingleThreadExecutor() {
        return new FinalizableDelegatedExecutorService
            (new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>()));
    }
```

作为单一worker线程的线程池，SingleThreadExecutor把corePool和maximumPoolSize均被设置为1，和FixedThreadPool一样使用的是无界队列LinkedBlockingQueue,所以带来的影响和FixedThreadPool一样。

![img](https://ws2.sinaimg.cn/large/006tKfTcly1g0m3x6o007j30of09d3yo.jpg)

**CachedThreadPool**

CachedThreadPool是一个会根据需要创建新线程的线程池 ，他定义如下：

```java
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
    }
```

CachedThreadPool的corePool为0，maximumPoolSize为Integer.MAX_VALUE，这就意味着所有的任务一提交就会加入到阻塞队列中。keepAliveTime这是为60L，unit设置为TimeUnit.SECONDS，意味着空闲线程等待新任务的最长时间为60秒，空闲线程超过60秒后将会被终止。阻塞队列采用的SynchronousQueue，而我们在[【死磕Java并发】—-J.U.C之阻塞队列：SynchronousQueue](http://cmsblogs.com/?p=2418)中了解到SynchronousQueue是一个没有元素的阻塞队列，加上corePool = 0 ，maximumPoolSize = Integer.MAX_VALUE，这样就会存在一个问题，如果主线程提交任务的速度远远大于CachedThreadPool的处理速度，则CachedThreadPool会不断地创建新线程来执行任务，这样有可能会导致系统耗尽CPU和内存资源，所以在**使用该线程池是，一定要注意控制并发的任务数，否则创建大量的线程可能导致严重的性能问题**。

[![img](https://gitee.com/chenssy/blog-home/raw/master/image/sijava/2018120835007.png)](https://gitee.com/chenssy/blog-home/raw/master/image/sijava/2018120835007.png)

### 2.4 任务提交

线程池根据业务不同的需求提供了两种方式提交任务：Executor.execute()、ExecutorService.submit()。其中ExecutorService.submit()可以获取该任务执行的Future。
我们以Executor.execute()为例，来看看线程池的任务提交经历了那些过程。

定义：

```java
public interface Executor {

    void execute(Runnable command);
}
```

ThreadPoolExecutor提供实现：

```java
    public void execute(Runnable command) {
        if (command == null)
            throw new NullPointerException();
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {
            if (addWorker(command, true))
                return;
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (! isRunning(recheck) && remove(command))
                reject(command);
            else if (workerCountOf(recheck) == 0)
                addWorker(null, false);
        }
        else if (!addWorker(command, false))
            reject(command);
    }
```

执行流程如下：

1. 如果线程池当前线程数小于corePoolSize，则调用addWorker创建新线程执行任务，成功返回true，失败执行步骤2。
2. 如果线程池处于RUNNING状态，则尝试加入阻塞队列，如果加入阻塞队列成功，则尝试进行Double Check，如果加入失败，则执行步骤3。
3. 如果线程池不是RUNNING状态或者加入阻塞队列失败，则尝试创建新线程直到maxPoolSize，如果失败，则调用reject()方法运行相应的拒绝策略。

在步骤2中如果加入阻塞队列成功了，则会进行一个Double Check的过程。Double Check过程的主要目的是判断加入到阻塞队里中的线程是否可以被执行。如果线程池不是RUNNING状态，则调用remove()方法从阻塞队列中删除该任务，然后调用reject()方法处理任务。否则需要确保还有线程执行。

**addWorker**
当线程中的当前线程数小于corePoolSize，则调用addWorker()创建新线程执行任务，当前线程数则是根据**ctl**变量来获取的，调用workerCountOf(ctl)获取低29位即可：

```java
    private static int workerCountOf(int c)  { return c & CAPACITY; }
```

addWorker(Runnable firstTask, boolean core)方法用于创建线程执行任务，源码如下：

```java
    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (;;) {
            int c = ctl.get();

            // 获取当前线程状态
            int rs = runStateOf(c);


            if (rs >= SHUTDOWN &&
                    ! (rs == SHUTDOWN &&
                            firstTask == null &&
                            ! workQueue.isEmpty()))
                return false;

            // 内层循环，worker + 1
            for (;;) {
                // 线程数量
                int wc = workerCountOf(c);
                // 如果当前线程数大于线程最大上限CAPACITY  return false
                // 若core == true，则与corePoolSize 比较，否则与maximumPoolSize ，大于 return false
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                // worker + 1,成功跳出retry循环
                if (compareAndIncrementWorkerCount(c))
                    break retry;

                // CAS add worker 失败，再次读取ctl
                c = ctl.get();

                // 如果状态不等于之前获取的state，跳出内层循环，继续去外层循环判断
                if (runStateOf(c) != rs)
                    continue retry;
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = null;
        try {

            // 新建线程：Worker
            w = new Worker(firstTask);
            // 当前线程
            final Thread t = w.thread;
            if (t != null) {
                // 获取主锁：mainLock
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {

                    // 线程状态
                    int rs = runStateOf(ctl.get());

                    // rs < SHUTDOWN ==> 线程处于RUNNING状态
                    // 或者线程处于SHUTDOWN状态，且firstTask == null（可能是workQueue中仍有未执行完成的任务，创建没有初始任务的worker线程执行）
                    if (rs < SHUTDOWN ||
                            (rs == SHUTDOWN && firstTask == null)) {

                        // 当前线程已经启动，抛出异常
                        if (t.isAlive()) // precheck that t is startable
                            throw new IllegalThreadStateException();

                        // workers是一个HashSet<Worker>
                        workers.add(w);

                        // 设置最大的池大小largestPoolSize，workerAdded设置为true
                        int s = workers.size();
                        if (s > largestPoolSize)
                            largestPoolSize = s;
                        workerAdded = true;
                    }
                } finally {
                    // 释放锁
                    mainLock.unlock();
                }
                // 启动线程
                if (workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        } finally {

            // 线程启动失败
            if (! workerStarted)
                addWorkerFailed(w);
        }
        return workerStarted;
    }
```

1. 判断当前线程是否可以添加任务，如果可以则进行下一步，否则return false；
   1. rs >= SHUTDOWN ，表示当前线程处于SHUTDOWN ，STOP、TIDYING、TERMINATED状态
   2. rs == SHUTDOWN , firstTask != null时不允许添加线程，因为线程处于SHUTDOWN 状态，不允许添加任务
   3. rs == SHUTDOWN , firstTask == null，但workQueue.isEmpty() == true，不允许添加线程，因为firstTask == null是为了添加一个没有任务的线程然后再从workQueue中获取任务的，如果workQueue == null，则说明添加的任务没有任何意义。
2. 内嵌循环，通过CAS worker + 1
3. 获取主锁mailLock，如果线程池处于RUNNING状态获取处于SHUTDOWN状态且 firstTask == null，则将任务添加到workers Queue中，然后释放主锁mainLock，然后启动线程，然后return true，如果中途失败导致workerStarted= false，则调用addWorkerFailed()方法进行处理。

在这里需要好好理论addWorker中的参数，在execute()方法中，有三处调用了该方法：

- 第一次：`workerCountOf(c) < corePoolSize ==> addWorker(command, true)`，这个很好理解，当然线程池的线程数量小于 corePoolSize ，则新建线程执行任务即可，在执行过程core == true，内部与corePoolSize比较即可。
- 第二次：加入阻塞队列进行Double Check时，`else if (workerCountOf(recheck) == 0) ==>addWorker(null, false)`。如果线程池中的线程==0，按照道理应该该任务应该新建线程执行任务，但是由于已经该任务已经添加到了阻塞队列，那么就在线程池中新建一个空线程，然后从阻塞队列中取线程即可。
- 第三次：线程池不是RUNNING状态或者加入阻塞队列失败：`else if (!addWorker(command, false))`，这里core == fase，则意味着是与maximumPoolSize比较。

在新建线程执行任务时，将讲Runnable包装成一个Worker，Woker为ThreadPoolExecutor的内部类

**Woker内部类**

Woker的源码如下：

```java
    private final class Worker extends AbstractQueuedSynchronizer
            implements Runnable {
        private static final long serialVersionUID = 6138294804551838833L;

        // task 的thread
        final Thread thread;

        // 运行的任务task
        Runnable firstTask;

        volatile long completedTasks;

        Worker(Runnable firstTask) {

            //设置AQS的同步状态private volatile int state，是一个计数器，大于0代表锁已经被获取
            setState(-1);
            this.firstTask = firstTask;

            // 利用ThreadFactory和 Worker这个Runnable创建的线程对象
            this.thread = getThreadFactory().newThread(this);
        }

        // 任务执行
        public void run() {
            runWorker(this);
        }

    }
```

从Worker的源码中我们可以看到Woker继承AQS，实现Runnable接口，所以可以认为Worker既是一个可以执行的任务，也可以达到获取锁释放锁的效果。这里继承AQS主要是为了方便线程的中断处理。这里注意两个地方：构造函数、run()。构造函数主要是做三件事：1.设置同步状态state为-1，同步状态大于0表示就已经获取了锁，2.设置将当前任务task设置为firstTask，3.利用Worker本身对象this和ThreadFactory创建线程对象。

当线程thread启动（调用start()方法）时，其实就是执行Worker的run()方法，内部调用runWorker()。

**runWorker**

```java
    final void runWorker(Worker w) {

        // 当前线程
        Thread wt = Thread.currentThread();

        // 要执行的任务
        Runnable task = w.firstTask;

        w.firstTask = null;

        // 释放锁，运行中断
        w.unlock(); // allow interrupts
        boolean completedAbruptly = true;
        try {
            while (task != null || (task = getTask()) != null) {
                // worker 获取锁
                w.lock();

                // 确保只有当线程是stoping时，才会被设置为中断，否则清楚中断标示
                // 如果线程池状态 >= STOP ,且当前线程没有设置中断状态，则wt.interrupt()
                // 如果线程池状态 < STOP，但是线程已经中断了，再次判断线程池是否 >= STOP，如果是 wt.interrupt()
                if ((runStateAtLeast(ctl.get(), STOP) ||
                        (Thread.interrupted() &&
                                runStateAtLeast(ctl.get(), STOP))) &&
                        !wt.isInterrupted())
                    wt.interrupt();
                try {
                    // 自定义方法
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try {
                        // 执行任务
                        task.run();
                    } catch (RuntimeException x) {
                        thrown = x; throw x;
                    } catch (Error x) {
                        thrown = x; throw x;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    } finally {
                        afterExecute(task, thrown);
                    }
                } finally {
                    task = null;
                    // 完成任务数 + 1
                    w.completedTasks++;
                    // 释放锁
                    w.unlock();
                }
            }
            completedAbruptly = false;
        } finally {
            processWorkerExit(w, completedAbruptly);
        }
    }
```

运行流程

1. 根据worker获取要执行的任务task，然后调用unlock()方法释放锁，这里释放锁的主要目的在于中断，因为在new Worker时，设置的state为-1，调用unlock()方法可以将state设置为0，这里主要原因就在于interruptWorkers()方法只有在state >= 0时才会执行；
2. 通过getTask()获取执行的任务，调用task.run()执行，当然在执行之前会调用worker.lock()上锁，执行之后调用worker.unlock()放锁；
3. 在任务执行前后，可以根据业务场景自定义beforeExecute() 和 afterExecute()方法，则两个方法在ThreadPoolExecutor中是空实现；
4. 如果线程执行完成，则会调用getTask()方法从阻塞队列中获取新任务，如果阻塞队列为空，则根据是否超时来判断是否需要阻塞；
5. task == null或者抛出异常（beforeExecute()、task.run()、afterExecute()均有可能）导致worker线程终止，则调用processWorkerExit()方法处理worker退出流程。

**getTask()**

```java
    private Runnable getTask() {
        boolean timedOut = false; // Did the last poll() time out?

        for (;;) {

            // 线程池状态
            int c = ctl.get();
            int rs = runStateOf(c);

            // 线程池中状态 >= STOP 或者 线程池状态 == SHUTDOWN且阻塞队列为空，则worker - 1，return null
            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);

            // 判断是否需要超时控制
            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

            if ((wc > maximumPoolSize || (timed && timedOut)) && (wc > 1 || workQueue.isEmpty())) {
                if (compareAndDecrementWorkerCount(c))
                    return null;
                continue;
            }

            try {

                // 从阻塞队列中获取task
                // 如果需要超时控制，则调用poll()，否则调用take()
                Runnable r = timed ?
                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
                        workQueue.take();
                if (r != null)
                    return r;
                timedOut = true;
            } catch (InterruptedException retry) {
                timedOut = false;
            }
        }
    }
```

timed == true，调用poll()方法，如果在keepAliveTime时间内还没有获取task的话，则返回null，继续循环。timed == false，则调用take()方法，该方法为一个阻塞方法，没有任务时会一直阻塞挂起，直到有任务加入时对该线程唤醒，返回任务。

在runWorker()方法中，无论最终结果如何，都会执行processWorkerExit()方法对worker进行退出处理。

**processWorkerExit()**

```java
    private void processWorkerExit(Worker w, boolean completedAbruptly) {

        // true：用户线程运行异常,需要扣减
        // false：getTask方法中扣减线程数量
        if (completedAbruptly)
            decrementWorkerCount();

        // 获取主锁
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            completedTaskCount += w.completedTasks;
            // 从HashSet中移出worker
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }

        // 有worker线程移除，可能是最后一个线程退出需要尝试终止线程池
        tryTerminate();

        int c = ctl.get();
        // 如果线程为running或shutdown状态，即tryTerminate()没有成功终止线程池，则判断是否有必要一个worker
        if (runStateLessThan(c, STOP)) {
            // 正常退出，计算min：需要维护的最小线程数量
            if (!completedAbruptly) {
                // allowCoreThreadTimeOut 默认false：是否需要维持核心线程的数量
                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
                // 如果min ==0 或者workerQueue为空，min = 1
                if (min == 0 && ! workQueue.isEmpty())
                    min = 1;

                // 如果线程数量大于最少数量min，直接返回，不需要新增线程
                if (workerCountOf(c) >= min)
                    return; // replacement not needed
            }
            // 添加一个没有firstTask的worker
            addWorker(null, false);
        }
    }
```

首先completedAbruptly的值来判断是否需要对线程数-1处理，如果completedAbruptly == true，说明在任务运行过程中出现了异常，那么需要进行减1处理，否则不需要，因为减1处理在getTask()方法中处理了。然后从HashSet中移出该worker，过程需要获取mainlock。然后调用tryTerminate()方法处理，该方法是对最后一个线程退出做终止线程池动作。如果线程池没有终止，那么线程池需要保持一定数量的线程，则通过addWorker(null,false)新增一个空的线程。

**addWorkerFailed()**

在addWorker()方法中，如果线程t==null，或者在add过程出现异常，会导致workerStarted == false，那么在最后会调用addWorkerFailed()方法：

```java
    private void addWorkerFailed(Worker w) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            // 从HashSet中移除该worker
            if (w != null)
                workers.remove(w);

            // 线程数 - 1
            decrementWorkerCount();
            // 尝试终止线程
            tryTerminate();
        } finally {
            mainLock.unlock();
        }
    }
```

整个逻辑显得比较简单。

**tryTerminate()**

当线程池涉及到要移除worker时候都会调用tryTerminate()，该方法主要用于判断线程池中的线程是否已经全部移除了，如果是的话则关闭线程池。

```java
    final void tryTerminate() {
        for (;;) {
            int c = ctl.get();
            // 线程池处于Running状态
            // 线程池已经终止了
            // 线程池处于ShutDown状态，但是阻塞队列不为空
            if (isRunning(c) ||
                    runStateAtLeast(c, TIDYING) ||
                    (runStateOf(c) == SHUTDOWN && ! workQueue.isEmpty()))
                return;

            // 执行到这里，就意味着线程池要么处于STOP状态，要么处于SHUTDOWN且阻塞队列为空
            // 这时如果线程池中还存在线程，则会尝试中断线程
            if (workerCountOf(c) != 0) {
                // /线程池还有线程，但是队列没有任务了，需要中断唤醒等待任务的线程
                // （runwoker的时候首先就通过w.unlock设置线程可中断，getTask最后面的catch处理中断）
                interruptIdleWorkers(ONLY_ONE);
                return;
            }

            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                // 尝试终止线程池
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try {
                        terminated();
                    } finally {
                        // 线程池状态转为TERMINATED
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                    return;
                }
            } finally {
                mainLock.unlock();
            }
        }
    }
```

在关闭线程池的过程中，如果线程池处于STOP状态或者处于SHUDOWN状态且阻塞队列为null，则线程池会调用interruptIdleWorkers()方法中断所有线程，注意ONLY_ONE== true，表示仅中断一个线程。

**interruptIdleWorkers**

```java
    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne)
                    break;
            }
        } finally {
            mainLock.unlock();
        }
    }
```

onlyOne==true仅终止一个线程，否则终止所有线程。

### 2.5 线程终止

线程池ThreadPoolExecutor提供了shutdown()和shutDownNow()用于关闭线程池。

shutdown()：按过去执行已提交任务的顺序发起一个有序的关闭，但是不接受新任务。

shutdownNow() :尝试停止所有的活动执行任务、暂停等待任务的处理，并返回等待执行的任务列表。

**shutdown**

```java
    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            // 推进线程状态
            advanceRunState(SHUTDOWN);
            // 中断空闲的线程
            interruptIdleWorkers();
            // 交给子类实现
            onShutdown();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }
```

**shutdownNow**

```java
    public List<Runnable> shutdownNow() {
        List<Runnable> tasks;
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            checkShutdownAccess();
            advanceRunState(STOP);
            // 中断所有线程
            interruptWorkers();
            // 返回等待执行的任务列表
            tasks = drainQueue();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
        return tasks;
    }
```

与shutdown不同，shutdownNow会调用interruptWorkers()方法中断所有线程。

```java
    private void interruptWorkers() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers)
                w.interruptIfStarted();
        } finally {
            mainLock.unlock();
        }
    }
```

同时会调用drainQueue()方法返回等待执行到任务列表。

```java
    private List<Runnable> drainQueue() {
        BlockingQueue<Runnable> q = workQueue;
        ArrayList<Runnable> taskList = new ArrayList<Runnable>();
        q.drainTo(taskList);
        if (!q.isEmpty()) {
            for (Runnable r : q.toArray(new Runnable[0])) {
                if (q.remove(r))
                    taskList.add(r);
            }
        }
        return taskList;
    }
```



## 3 [ScheduledThreadPoolExecutor](http://cmsblogs.com/?p=2451)

在上篇博客[【死磕Java并发】—–J.U.C之线程池：ThreadPoolExecutor](http://cmsblogs.com/?p=2448)已经介绍了线程池中最核心的类**ThreadPoolExecutor**，这篇就来看看另一个核心类**ScheduledThreadPoolExecutor**的实现。

我们知道Timer与TimerTask虽然可以实现线程的周期和延迟调度，但是Timer与TimerTask存在一些缺陷，所以对于这种定期、周期执行任务的调度策略，我们一般都是推荐ScheduledThreadPoolExecutor来实现。下面就深入分析ScheduledThreadPoolExecutor是如何来实现线程的周期、延迟调度的。

ScheduledThreadPoolExecutor，继承ThreadPoolExecutor且实现了ScheduledExecutorService接口，它就相当于提供了“延迟”和“周期执行”功能的ThreadPoolExecutor。在JDK API中是这样定义它的：ThreadPoolExecutor，它可另行安排在给定的延迟后运行命令，或者定期执行命令。需要多个辅助线程时，或者要求 ThreadPoolExecutor 具有额外的灵活性或功能时，此类要优于 Timer。 一旦启用已延迟的任务就执行它，但是有关何时启用，启用后何时执行则没有任何实时保证。按照提交的先进先出 (FIFO) 顺序来启用那些被安排在同一执行时间的任务。

它提供了四个构造方法：

```java
    public ScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue());
    }

    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       ThreadFactory threadFactory) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), threadFactory);
    }

    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       RejectedExecutionHandler handler) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), handler);
    }


    public ScheduledThreadPoolExecutor(int corePoolSize,
                                       ThreadFactory threadFactory,
                                       RejectedExecutionHandler handler) {
        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
                new DelayedWorkQueue(), threadFactory, handler);
    }
```

当然我们一般都不会直接通过其构造函数来生成一个ScheduledThreadPoolExecutor对象（例如new ScheduledThreadPoolExecutor(10)之类的），而是通过Executors类（例如Executors.newScheduledThreadPool(int);）

在ScheduledThreadPoolExecutor的构造函数中，我们发现它都是利用ThreadLocalExecutor来构造的，唯一变动的地方就在于它所使用的阻塞队列变成了DelayedWorkQueue，而不是ThreadLocalhExecutor的LinkedBlockingQueue（通过Executors产生ThreadLocalhExecutor对象）。DelayedWorkQueue为ScheduledThreadPoolExecutor中的内部类，它其实和阻塞队列DelayQueue有点儿类似。DelayQueue是可以提供延迟的阻塞队列，它只有在延迟期满时才能从中提取元素，其列头是延迟期满后保存时间最长的Delayed元素。如果延迟都还没有期满，则队列没有头部，并且 poll 将返回 null。有关于DelayQueue的更多介绍请参考这篇博客[【死磕Java并发】—–J.U.C之阻塞队列：DelayQueue](http://cmsblogs.com/?p=2413)。所以DelayedWorkQueue中的任务必然是按照延迟时间从短到长来进行排序的。下面我们再深入分析DelayedWorkQueue，这里留一个引子。

ScheduledThreadPoolExecutor提供了如下四个方法，也就是四个调度器：

1. schedule(Callable callable, long delay, TimeUnit unit) :创建并执行在给定延迟后启用的 ScheduledFuture。
2. schedule(Runnable command, long delay, TimeUnit unit) :创建并执行在给定延迟后启用的一次性操作。
3. scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) :创建并执行一个在给定初始延迟后首次启用的定期操作，后续操作具有给定的周期；也就是将在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。
4. scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) :创建并执行一个在给定初始延迟后首次启用的定期操作，随后，在每一次执行终止和下一次执行开始之间都存在给定的延迟。

第一、二个方法差不多，都是一次性操作，只不过参数一个是Callable，一个是Runnable。稍微分析下第三（scheduleAtFixedRate）、四个（scheduleWithFixedDelay）方法，加入initialDelay = 5，period/delay = 3，unit为秒。如果每个线程都是都运行非常良好不存在延迟的问题，那么这两个方法线程运行周期是5、8、11、14、17…….，但是如果存在延迟呢？比如第三个线程用了5秒钟，那么这两个方法的处理策略是怎样的？第三个方法（scheduleAtFixedRate）是周期固定，也就说它是不会受到这个延迟的影响的，每个线程的调度周期在初始化时就已经绝对了，是什么时候调度就是什么时候调度，它不会因为上一个线程的调度失效延迟而受到影响。但是第四个方法（scheduleWithFixedDelay），则不一样，它是每个线程的调度间隔固定，也就是说第一个线程与第二线程之间间隔delay，第二个与第三个间隔delay，以此类推。如果第二线程推迟了那么后面所有的线程调度都会推迟，例如，上面第二线程推迟了2秒，那么第三个就不再是11秒执行了，而是13秒执行。

查看着四个方法的源码，会发现其实他们的处理逻辑都差不多，所以我们就挑scheduleWithFixedDelay方法来分析，如下：

```java
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit) {
        if (command == null || unit == null)
            throw new NullPointerException();
        if (delay <= 0)
            throw new IllegalArgumentException();
        ScheduledFutureTask<Void> sft =
            new ScheduledFutureTask<Void>(command,
                                          null,
                                          triggerTime(initialDelay, unit),
                                          unit.toNanos(-delay));
        RunnableScheduledFuture<Void> t = decorateTask(command, sft);
        sft.outerTask = t;
        delayedExecute(t);
        return t;
    }
```

scheduleWithFixedDelay方法处理的逻辑如下：

1. 校验，如果参数不合法则抛出异常
2. 构造一个task，该task为ScheduledFutureTask
3. 调用delayedExecute()方法做后续相关处理

这段代码涉及两个类ScheduledFutureTask和RunnableScheduledFuture，其中RunnableScheduledFuture不用多说，他继承RunnableFuture和ScheduledFuture两个接口，除了具备RunnableFuture和ScheduledFuture两类特性外，它还定义了一个方法isPeriodic() ，该方法用于判断执行的任务是否为定期任务，如果是则返回true。而ScheduledFutureTask作为ScheduledThreadPoolExecutor的内部类，它扮演着极其重要的作用，因为它的作用则是负责ScheduledThreadPoolExecutor中任务的调度。

ScheduledFutureTask内部继承FutureTask，实现RunnableScheduledFuture接口，它内部定义了三个比较重要的变量

```java
        /** 任务被添加到ScheduledThreadPoolExecutor中的序号 */
        private final long sequenceNumber;

        /** 任务要执行的具体时间 */
        private long time;

        /**  任务的间隔周期 */
        private final long period;
```

这三个变量与任务的执行有着非常密切的关系，什么关系？先看ScheduledFutureTask的几个构造函数和核心方法：

```java
        ScheduledFutureTask(Runnable r, V result, long ns) {
            super(r, result);
            this.time = ns;
            this.period = 0;
            this.sequenceNumber = sequencer.getAndIncrement();
        }

        ScheduledFutureTask(Runnable r, V result, long ns, long period) {
            super(r, result);
            this.time = ns;
            this.period = period;
            this.sequenceNumber = sequencer.getAndIncrement();
        }

        ScheduledFutureTask(Callable<V> callable, long ns) {
            super(callable);
            this.time = ns;
            this.period = 0;
            this.sequenceNumber = sequencer.getAndIncrement();
        }

        ScheduledFutureTask(Callable<V> callable, long ns) {
            super(callable);
            this.time = ns;
            this.period = 0;
            this.sequenceNumber = sequencer.getAndIncrement();
        }
```

ScheduledFutureTask 提供了四个构造方法，这些构造方法与上面三个参数是不是一一对应了？这些参数有什么用，如何用，则要看ScheduledFutureTask在那些方法使用了该方法，在ScheduledFutureTask中有一个compareTo()方法：

```java
    public int compareTo(Delayed other) {
        if (other == this) // compare zero if same object
            return 0;
        if (other instanceof ScheduledFutureTask) {
            ScheduledFutureTask<?> x = (ScheduledFutureTask<?>)other;
            long diff = time - x.time;
            if (diff < 0)
                return -1;
            else if (diff > 0)
                return 1;
            else if (sequenceNumber < x.sequenceNumber)
                return -1;
            else
                return 1;
        }
        long diff = getDelay(NANOSECONDS) - other.getDelay(NANOSECONDS);
        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }
```

相信各位都知道该方法是干嘛用的，提供一个排序算法，该算法规则是：首先按照time排序，time小的排在前面，大的排在后面，如果time相同，则使用sequenceNumber排序，小的排在前面，大的排在后面。那么为什么在这个类里面提供compareTo()方法呢？在前面就介绍过ScheduledThreadPoolExecutor在构造方法中提供的是DelayedWorkQueue()队列中，也就是说ScheduledThreadPoolExecutor是把任务添加到DelayedWorkQueue中的，而DelayedWorkQueue则是类似于DelayQueue，内部维护着一个以时间为先后顺序的队列，所以compareTo()方法使用与DelayedWorkQueue队列对其元素ScheduledThreadPoolExecutor task进行排序的算法。

排序已经解决了，那么ScheduledThreadPoolExecutor 是如何对task任务进行调度和延迟的呢？任何线程的执行，都是通过run()方法执行，ScheduledThreadPoolExecutor 的run()方法如下：

```java
        public void run() {
            boolean periodic = isPeriodic();
            if (!canRunInCurrentRunState(periodic))
                cancel(false);
            else if (!periodic)
                ScheduledFutureTask.super.run();
            else if (ScheduledFutureTask.super.runAndReset()) {
                setNextRunTime();
                reExecutePeriodic(outerTask);
            }
        }
```

1. 调用isPeriodic()获取该线程是否为周期性任务标志，然后调用canRunInCurrentRunState()方法判断该线程是否可以执行，如果不可以执行则调用cancel()取消任务。
2. 如果当线程已经到达了执行点，则调用run()方法执行task，该run()方法是在FutureTask中定义的。
3. 否则调用runAndReset()方法运行并充值，调用setNextRunTime()方法计算任务下次的执行时间，重新把任务添加到队列中，让该任务可以重复执行。

**isPeriodic()**

该方法用于判断指定的任务是否为定期任务。

```java
        public boolean isPeriodic() {
            return period != 0;
        }
```

canRunInCurrentRunState()判断任务是否可以取消，cancel()取消任务，这两个方法比较简单，而run()执行任务，runAndReset()运行并重置状态，牵涉比较广，我们放在FutureTask后面介绍。所以重点介绍setNextRunTime()和reExecutePeriodic()这两个涉及到延迟的方法。

**setNextRunTime()**

setNextRunTime()方法用于重新计算任务的下次执行时间。如下：

```java
        private void setNextRunTime() {
            long p = period;
            if (p > 0)
                time += p;
            else
                time = triggerTime(-p);
        }
```

该方法定义很简单，p > 0 ,time += p ，否则调用triggerTime()方法重新计算time：

```java
    long triggerTime(long delay) {
        return now() +
            ((delay < (Long.MAX_VALUE >> 1)) ? delay : overflowFree(delay));
    }
```

**reExecutePeriodic**

```java
    void reExecutePeriodic(RunnableScheduledFuture<?> task) {
        if (canRunInCurrentRunState(true)) {
            super.getQueue().add(task);
            if (!canRunInCurrentRunState(true) && remove(task))
                task.cancel(false);
            else
                ensurePrestart();
        }
    }
```

reExecutePeriodic重要的是调用super.getQueue().add(task);将任务task加入的队列DelayedWorkQueue中。ensurePrestart()在[【死磕Java并发】—–J.U.C之线程池：ThreadPoolExecutor](http://cmsblogs.com/?p=2448)已经做了详细介绍。

到这里ScheduledFutureTask已经介绍完了，ScheduledFutureTask在ScheduledThreadPoolExecutor扮演作用的重要性不言而喻。其实ScheduledThreadPoolExecutor的实现不是很复杂，因为有FutureTask和ThreadPoolExecutor的支撑，其实现就显得不是那么难了。