[TOC]

# 前言

java的常用命令十分的重要。

# [Java命令学习系列（零）——常见命令及Java Dump介绍](https://www.hollischuang.com/archives/308)

## 一、常用命令：

在JDK的bin目彔下,包含了java命令及其他实用工具。

> [jps](http://www.hollischuang.com/archives/105):查看本机的Java中进程信息。
>
> [jstack](http://www.hollischuang.com/archives/110):打印线程的栈信息,制作线程Dump。
>
> [jmap](http://www.hollischuang.com/archives/303):打印内存映射,制作堆Dump。
>
> [jstat](http://www.hollischuang.com/archives/481):性能监控工具。
>
> [jhat](http://www.hollischuang.com/archives/1047):内存分析工具。
>
> jconsole:简易的可视化控制台。
>
> jvisualvm:功能强大的控制台。

## 二、认识Java Dump：

#### 什么是Java Dump？

> Java虚拟机的运行时快照。将Java虚拟机运行时的状态和信息保存到文件。
>
> **线程Dump**,包含所有线程的运行状态。纯文本格式。
>
> **堆Dump**,包含线程Dump,幵包含所有堆对象的状态。二进制格式。

#### Java Dump有什么用？

> 补足传统Bug分析手段的不足: 可在任何Java环境使用;信息量充足。 针对非功能正确性的Bug,主要为:多线程幵发、内存泄漏。

## 三.制作Java Dump

**使用Java虚拟机制作Dump**

指示虚拟机在发生内存不足错误时,自动生成堆Dump

```shell
-XX:+HeapDumpOnOutOfMemoryError
```

**使用图形化工具制作Dump**

使用JDK(1.6)自带的工具:Java VisualVM。

**使用命令行制作Dump**

`jstack`:打印线程的栈信息,制作线程Dump。

`jmap`:打印内存映射,制作堆Dump。

**步骤：**

1. 检查虚拟机版本（java -version）
2. [找出目标Java应用的进程ID](http://www.hollischuang.com/archives/105)（jps）
3. [使用jstack命令制作线程Dump](http://www.hollischuang.com/archives/110) • Linux环境下使用kill命令制作线程Dump
4. 使用[jmap](http://www.hollischuang.com/archives/303)命令制作堆Dump



------

# 1.[Java命令学习系列（一）——Jps](https://www.hollischuang.com/archives/105)

> jps位于jdk的bin目录下，其作用是显示当前系统的java进程情况，及其id号。 jps相当于Solaris进程工具ps。不象”gg”或”ps -ef grep java”，jps并不使用应用程序名来查找JVM实例。因此，**它查找所有的Java应用程序**，包括即使没有使用java执行体的那种（例如，定制的启动 器）。另外，**jps仅查找当前用户的Java进程**，而不是当前系统中的所有进程。

## 1.1位置

我们知道，很多Java命令都在jdk的JAVA_HOME/bin/目录下面，jps也不例外，他就在bin目录下，所以，他是java自带的一个命令。我的位置：

```
/Library/Java/JavaVirtualMachines/jdk1.8.0_181.jdk/Contents/Home/bin
```

## 1.2 功能

**回答了是什么的问题**？

jps(Java Virtual Machine Process Status Tool)是JDK 1.5提供的一个显示当前所有java进程pid的命令，简单实用，非常适合在linux/unix平台上简单察看当前java进程的一些简单情况。

## 1.3 原理

jdk中的jps命令可以显示当前运行的java进程以及相关参数，它的实现机制如下：

**回答了怎么实现了的问题**？

java程序在启动以后，会在`java.io.tmpdir`指定的目录下，就是临时文件夹里，生成一个类似于`hsperfdata_User`的文件夹，这个文件夹里（在Linux中为/tmp/hsperfdata_{userName}/），有几个文件，名字就是java进程的pid，因此列出当前运行的java进程，只是把这个目录里的文件名列一下而已。 至于系统的参数什么，就可以解析这几个文件获得。

在我的电脑上，java.io.tmpdir被重定向到`/var/folders/st/cz2czw493kq_sp37hy2pc6780000gn/T/`目录下。具体查找的方法是使用`System.out.println(System.getProperty("java.io.tmpdir"));`

```shell
hollis@hos:/tmp/hsperfdata_hollis$ pwd
/tmp/hsperfdata_hollis
#相当于ls -l
hollis@hos:/tmp/hsperfdata_hollis$ ll
total 48
drwxr-xr-x 2 hollis hollis  4096  4月 16 10:54 ./
drwxrwxrwt 7 root   root   12288  4月 16 10:56 ../
-rw------- 1 hollis hollis 32768  4月 16 10:57 2679
hollis@hos:/tmp/hsperfdata_hollis$ 
```

上面的内容就是我机器中/tmp/hsperfdata_hollis目录下的内容，其中2679就是我机器上当前运行中的java的进程的pid，我们执行jps验证一下：

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps
2679 org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar
4445 Jps
```

执行了jps命令之后，我们发现有两个java进程，一个是pid为2679的eclipse运行的进程，另外一个是pid为4445的jps使用的进程（他也是java命令，也要开一个进程）

## 1.4 使用

想要学习一个命令，先来看看帮助，使用`jps -help`查看帮助：

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps -help
usage: jps [-help]
       jps [-q] [-mlvV] [<hostid>]

Definitions:
    <hostid>:      <hostname>[:<port>]
```

接下来，为了详细介绍这些参数，我们编写几个类，在main方法里写一个while(true)的循环，查看java进程情况。代码如下：

```java
package com.JavaCommand;
/**
 * @author hollis
 */
public class JpsDemo {
    public static void main(String[] args) {
        while(true){
            System.out.println(1);
        }
    }
}
```

**-q 只显示pid，不显示class名称,jar文件名和传递给main 方法的参数**

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps -q
2679
11421
```

**-m 输出传递给main 方法的参数，在嵌入式jvm上可能是null，** 在这里，在启动main方法的时候，我给String[] args传递两个参数。hollis,chuang,执行`jsp -m`:

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps -m
12062 JpsDemo hollis,chuang
```

**-l 输出应用程序main class的完整package名 或者 应用程序的jar文件完整路径名**

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps -l
12356 sun.tools.jps.Jps
2679 /home/hollis/tools/eclipse//plugins/org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar
12329 com.JavaCommand.JpsDemo
```

**-v 输出传递给JVM的参数** 在这里，在启动main方法的时候，我给jvm传递一个参数：-Dfile.encoding=UTF-8,执行`jps -v`：

```shell
hollis@hos:/tmp/hsperfdata_hollis$ jps -v
2679 org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar -Djava.library.path=/usr/lib/jni:/usr/lib/x86_64-linux-gnu/jni -Dosgi.requiredJavaVersion=1.6 -XX:MaxPermSize=256m -Xms40m -Xmx512m
13157 Jps -Denv.class.path=/home/hollis/tools/java/jdk1.7.0_71/lib:/home/hollis/tools/java/jdk1.7.0_71/jre/lib: -Dapplication.home=/home/hollis/tools/java/jdk1.7.0_71 -Xms8m
13083 JpsDemo -Dfile.encoding=UTF-8
```

PS:jps命令有个地方很不好，似乎只能显示当前用户的java进程，要显示其他用户的还是只能用unix/linux的ps命令。

> **jps是我最常用的java命令。使用jps可以查看当前有哪些Java进程处于运行状态。如果我运行了一个web应用（使用tomcat、jboss、jetty等启动）的时候，我就可以使用jps查看启动情况。有的时候我想知道这个应用的日志会输出到哪里，或者启动的时候使用了哪些javaagent，那么我可以使用jps -v 查看进程的jvm参数情况。**

## 1.5 JPS失效处理

**现象：** 用ps -ef|grep java能看到启动的java进程，但是用jps查看却不存在该进程的id。待会儿解释过之后就能知道在该情况下，jconsole、jvisualvm可能无法监控该进程，其他java自带工具也可能无法使用

**分析：** jps、jconsole、jvisualvm等工具的数据来源就是这个文件（/tmp/hsperfdata_userName/pid)。所以当该文件不存在或是无法读取时就会出现jps无法查看该进程号，jconsole无法监控等问题

**原因：**

（1）、磁盘读写、目录权限问题 若该用户没有权限写/tmp目录或是磁盘已满，则无法创建/tmp/hsperfdata_userName/pid文件。或该文件已经生成，但用户没有读权限

（2）、临时文件丢失，被删除或是定期清理 对于linux机器，一般都会存在定时任务对临时文件夹进行清理，导致/tmp目录被清空。这也是我第一次碰到该现象的原因。常用的可能定时删除临时目录的工具为crontab、redhat的tmpwatch、ubuntu的tmpreaper等等

这个导致的现象可能会是这样，用jconsole监控进程，发现在某一时段后进程仍然存在，但是却没有监控信息了。

（3）、java进程信息文件存储地址被设置，不在/tmp目录下 上面我们在介绍时说默认会在/tmp/hsperfdata_userName目录保存进程信息，但由于以上1、2所述原因，可能导致该文件无法生成或是丢失，所以java启动时提供了参数(-Djava.io.tmpdir)，可以对这个文件的位置进行设置，而jps、jconsole都只会从/tmp目录读取，而无法从设置后的目录读物信息，这是我第二次碰到该现象的原因

## 1.6 附：

1.如何给main传递参数 在eclipse中，鼠标右键->Run As->Run COnfiguations->Arguments->在Program arguments中写下要传的参数值

1.如何给JVM传递参数 在eclipse中，鼠标右键->Run As->Run COnfiguations->Arguments->在VM arguments中写下要传的参数值（一般以-D开头）

**也可以直接使用命令行进行传递。**

------



# 2. [Java命令学习系列（二）——Jstack](https://www.hollischuang.com/archives/110)

> jstack是java虚拟机自带的一种堆栈跟踪工具。

## 2.1 功能

jstack用于生成java虚拟机当前时刻的线程快照。

**线程快照是什么**？

线程快照是当前java虚拟机内每一条线程正在执行的方法堆栈的集合。

**生成线程快照的主要目的：**

生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等。 

线程出现停顿的时候通过jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做什么事情，或者等待什么资源。 如果java程序崩溃生成core文件，jstack工具可以用来获得core文件的java stack和native stack的信息，从而可以轻松地知道java程序是如何崩溃和在程序何处发生问题。另外，jstack工具还可以附属到正在运行的java程序中，看到当时运行的java程序的java stack和native stack的信息, 如果现在运行的java程序呈现hung的状态，jstack是非常有用的。



> So,**jstack命令主要用来查看Java线程的调用堆栈的，可以用来分析线程问题（如死锁）。**

## 2.2 线程状态

想要通过jstack命令来分析线程的情况的话，首先要知道线程都有哪些状态，下面这些状态是我们使用jstack命令查看线程堆栈信息时可能会看到的**线程的几种状态**：

> NEW,未启动的。不会出现在Dump中。
>
> RUNNABLE,在虚拟机内执行的。
>
> BLOCKED,受阻塞并等待监视器锁。
>
> WATING,无限期等待另一个线程执行特定操作。
>
> TIMED_WATING,有时限的等待另一个线程的特定操作。
>
> TERMINATED,已退出的。

## 2.2 Monitor

在多线程的 JAVA程序中，实现线程之间的同步，就要说说 Monitor。 **Monitor是 Java中用以实现线程之间的互斥与协作的主要手段**，它可以看成是对象或者 Class的锁。每一个对象都有，也仅有一个 monitor。下 面这个图，描述了线程和 Monitor之间关系，以 及线程的状态转换图：

![thread](https://ws2.sinaimg.cn/large/006tNc79ly1fz4pspq2efj30dw0abaap.jpg)

**进入区(Entrt Set)**:表示线程通过synchronized要求获取对象的锁。如果对象未被锁住,则进入拥有者;否则则在进入区等待。一旦对象锁被其他线程释放,立即参与竞争。

**拥有者(The Owner)**:表示某一线程成功竞争到对象锁。

**等待区(Wait Set)**:表示线程通过对象的wait方法,释放对象的锁,并在等待区等待被唤醒。

从图中可以看出，一个 Monitor在某个时刻，只能被一个线程拥有，该线程就是 `“Active Thread”`，而其它线程都是 `“Waiting Thread”`，分别在两个队列 `“ Entry Set”`和 `“Wait Set”`里面等候。在 `“Entry Set”`中等待的线程状态是 `“Waiting for monitor entry”`，而在 `“Wait Set”`中等待的线程状态是 `“in Object.wait()”`。 先看 “Entry Set”里面的线程。我们称被 synchronized保护起来的代码段为临界区。当一个线程申请进入临界区时，它就进入了 “Entry Set”队列。对应的 code就像：

```java
synchronized(obj) {
.........

}
```

## 2.3 调用修饰

表示线程在方法调用时,额外的重要的操作。线程Dump分析的重要信息。修饰上方的方法调用。

> locked <地址> 目标：使用synchronized申请对象锁成功,监视器的拥有者。
>
> waiting to lock <地址> 目标：使用synchronized申请对象锁未成功,在进入区等待。
>
> waiting on <地址> 目标：使用synchronized申请对象锁成功后,释放锁并在等待区等待。
>
> parking to wait for <地址> 目标

**locked**

```java 
at oracle.jdbc.driver.PhysicalConnection.prepareStatement
- locked <0x00002aab63bf7f58> (a oracle.jdbc.driver.T4CConnection)
at oracle.jdbc.driver.PhysicalConnection.prepareStatement
- locked <0x00002aab63bf7f58> (a oracle.jdbc.driver.T4CConnection)
at com.jiuqi.dna.core.internal.db.datasource.PooledConnection.prepareStatement
```

通过synchronized关键字,成功获取到了对象的锁,成为监视器的拥有者,在临界区内操作。对象锁是可以线程重入的。

**waiting to lock**

```java
at com.jiuqi.dna.core.impl.CacheHolder.isVisibleIn(CacheHolder.java:165)
- waiting to lock <0x0000000097ba9aa8> (a CacheHolder)
at com.jiuqi.dna.core.impl.CacheGroup$Index.findHolder
at com.jiuqi.dna.core.impl.ContextImpl.find
at com.jiuqi.dna.bap.basedata.common.util.BaseDataCenter.findInfo
```

通过synchronized关键字,没有获取到了对象的锁,线程在监视器的进入区等待。在调用栈顶出现,线程状态为Blocked。

**waiting on**

```java
at java.lang.Object.wait(Native Method)
- waiting on <0x00000000da2defb0> (a WorkingThread)
at com.jiuqi.dna.core.impl.WorkingManager.getWorkToDo
- locked <0x00000000da2defb0> (a WorkingThread)
at com.jiuqi.dna.core.impl.WorkingThread.run
```

通过synchronized关键字,成功获取到了对象的锁后,调用了wait方法,进入对象的等待区等待。在调用栈顶出现,线程状态为WAITING或TIMED_WATING。

**parking to wait for**

park是基本的线程阻塞原语,不通过监视器在对象上阻塞。随concurrent包会出现的新的机制,不synchronized体系不同。

## 2.4 线程动作

线程状态产生的原因

> runnable:状态一般为RUNNABLE。
>
> in Object.wait():等待区等待,状态为WAITING或TIMED_WAITING。
>
> waiting for monitor entry:进入区等待,状态为BLOCKED。
>
> waiting on condition:等待区等待、被park。
>
> sleeping:休眠的线程,调用了Thread.sleep()。

**Wait on condition** 该状态出现在线程等待某个条件的发生。具体是什么原因，可以结合 stacktrace来分析。 

最常见的情况就是线程处于sleep状态，等待被唤醒。 常见的情况还有等待网络IO：在java引入nio之前，对于每个网络连接，都有一个对应的线程来处理网络的读写操作，即使没有可读写的数据，线程仍然阻塞在读写操作上，这样有可能造成资源浪费，而且给操作系统的线程调度也带来压力。在 NewIO里采用了新的机制，编写的服务器程序的性能和可扩展性都得到提高。 正等待网络读写，这可能是一个网络瓶颈的征兆。因为网络阻塞导致线程无法执行。一种情况是网络非常忙，几 乎消耗了所有的带宽，仍然有大量数据等待网络读 写；另一种情况也可能是网络空闲，但由于路由等问题，导致包无法正常的到达。所以要结合系统的一些性能观察工具来综合分析，比如 netstat统计单位时间的发送包的数目，如果很明显超过了所在网络带宽的限制 ; 观察 cpu的利用率，如果系统态的 CPU时间，相对于用户态的 CPU时间比例较高；如果程序运行在 Solaris 10平台上，可以用 dtrace工具看系统调用的情况，如果观察到 read/write的系统调用的次数或者运行时间遥遥领先；这些都指向由于网络带宽所限导致的网络瓶颈。（来自<http://www.blogjava.net/jzone/articles/303979.html>）

## 2.5 线程Dump的分析

### 2.5.1 原则

> 结合代码阅读的,推理。需要线程Dump和源码的相互推导和印证。
>
> 造成Bug的根源往往会在调用栈上直接体现,一定格外注意线程当前调用之前的所有调用。

### 2.5.2 入手点

**进入区等待**

```java
"d&a-3588" daemon waiting for monitor entry [0x000000006e5d5000]
java.lang.Thread.State: BLOCKED (on object monitor)
at com.jiuqi.dna.bap.authority.service.UserService$LoginHandler.handle()
- waiting to lock <0x0000000602f38e90> (a java.lang.Object)
at com.jiuqi.dna.bap.authority.service.UserService$LoginHandler.handle()
```

线程状态BLOCKED,线程动作wait on monitor entry,调用修饰waiting to lock总是一起出现。表示在代码级别已经存在冲突的调用。必然有问题的代码,需要尽可能减少其发生。

**同步块阻塞**

一个线程锁住某对象,大量其他线程在该对象上等待。

```java
"blocker" runnable
java.lang.Thread.State: RUNNABLE
at com.jiuqi.hcl.javadump.Blocker$1.run(Blocker.java:23)
- locked <0x00000000eb8eff68> (a java.lang.Object)
"blockee-11" waiting for monitor entry
java.lang.Thread.State: BLOCKED (on object monitor)
at com.jiuqi.hcl.javadump.Blocker$2.run(Blocker.java:41)
- waiting to lock <0x00000000eb8eff68> (a java.lang.Object)
"blockee-86" waiting for monitor entry
java.lang.Thread.State: BLOCKED (on object monitor)
at com.jiuqi.hcl.javadump.Blocker$2.run(Blocker.java:41)
- waiting to lock <0x00000000eb8eff68> (a java.lang.Object)
```

**持续运行的IO** IO操作是可以以RUNNABLE状态达成阻塞。例如:数据库死锁、网络读写。 格外注意对IO线程的真实状态的分析。 一般来说,被捕捉到RUNNABLE的IO调用,都是有问题的。

以下堆栈显示： 线程状态为RUNNABLE。 调用栈在SocketInputStream或SocketImpl上,socketRead0等方法。 调用栈包含了jdbc相关的包。很可能发生了数据库死锁

```java
"d&a-614" daemon prio=6 tid=0x0000000022f1f000 nid=0x37c8 runnable
[0x0000000027cbd000]
java.lang.Thread.State: RUNNABLE
at java.net.SocketInputStream.socketRead0(Native Method)
at java.net.SocketInputStream.read(Unknown Source)
at oracle.net.ns.Packet.receive(Packet.java:240)
at oracle.net.ns.DataPacket.receive(DataPacket.java:92)
at oracle.net.ns.NetInputStream.getNextPacket(NetInputStream.java:172)
at oracle.net.ns.NetInputStream.read(NetInputStream.java:117)
at oracle.jdbc.driver.T4CMAREngine.unmarshalUB1(T4CMAREngine.java:1034)
at oracle.jdbc.driver.T4C8Oall.receive(T4C8Oall.java:588)
```

**分线程调度的休眠**

正常的线程池等待

```java
"d&a-131" in Object.wait()
java.lang.Thread.State: TIMED_WAITING (on object monitor)
at java.lang.Object.wait(Native Method)
at com.jiuqi.dna.core.impl.WorkingManager.getWorkToDo(WorkingManager.java:322)
- locked <0x0000000313f656f8> (a com.jiuqi.dna.core.impl.WorkingThread)
at com.jiuqi.dna.core.impl.WorkingThread.run(WorkingThread.java:40)
```

可疑的线程等待

```java
"d&a-121" in Object.wait()
java.lang.Thread.State: WAITING (on object monitor)
at java.lang.Object.wait(Native Method)
at java.lang.Object.wait(Object.java:485)
at com.jiuqi.dna.core.impl.AcquirableAccessor.exclusive()
- locked <0x00000003011678d8> (a com.jiuqi.dna.core.impl.CacheGroup)
at com.jiuqi.dna.core.impl.Transaction.lock()
```

## 2.6 入手点总结

**wait on monitor entry：** 被阻塞的,肯定有问题

**runnable** ： 注意IO线程

**in Object.wait()**： 注意非线程池等待

## 2.7 使用

想要学习一个命令，先来看看帮助，使用jstack -help查看帮助：

```shell
hollis@hos:~$ jstack -help
Usage:
    jstack [-l] <pid>
        (to connect to running process)
    jstack -F [-m] [-l] <pid>
        (to connect to a hung process)
    jstack [-m] [-l] <executable> <core>
        (to connect to a core file)
    jstack [-m] [-l] [server_id@]<remote server IP or hostname>
        (to connect to a remote debug server)

Options:
    -F  to force a thread dump. Use when jstack <pid> does not respond (process is hung)
    -m  to print both java and native frames (mixed mode)
    -l  long listing. Prints additional information about locks
    -h or -help to print this help message
```

`-F`当’jstack [-l] pid’没有相应的时候强制打印栈信息 `-l`长列表. 打印关于锁的附加信息,例如属于java.util.concurrent的ownable synchronizers列表. `-m`打印java和native c/c++框架的所有栈信息. `-h` | -help打印帮助信息 `pid` 需要被打印配置信息的java进程id,可以用jps查询.

首先，我们分析这么一段程序的线程情况：

```java
/**
 * @author hollis
 */
public class JStackDemo1 {
    public static void main(String[] args) {
        while (true) {
            //Do Nothing
        }
    }
}
```

先是有jps查看进程号：

```shell
hollis@hos:~$ jps
29788 JStackDemo1
29834 Jps
22385 org.eclipse.equinox.launcher_1.3.0.v20130327-1440.jar
```

然后使用jstack 查看堆栈信息：

```shell
hollis@hos:~$ jstack 29788
2015-04-17 23:47:31
...此处省略若干内容...
"main" prio=10 tid=0x00007f197800a000 nid=0x7462 runnable [0x00007f197f7e1000]
   java.lang.Thread.State: RUNNABLE
    at javaCommand.JStackDemo1.main(JStackDemo1.java:7)
```

我们可以从这段堆栈信息中看出什么来呢？我们可以看到，当前一共有一条用户级别线程,线程处于runnable状态，执行到JStackDemo1.java的第七行。 看下面代码：

```java
/**
 * @author hollis
 */
public class JStackDemo1 {
    public static void main(String[] args) {
        Thread thread = new Thread(new Thread1());
        thread.start();
    }
}
class Thread1 implements Runnable{
    @Override
    public void run() {
        while(true){
            System.out.println(1);
        }
    }
}
```

线程堆栈信息如下：

```shell
"Reference Handler" daemon prio=10 tid=0x00007fbbcc06e000 nid=0x286c in Object.wait() [0x00007fbbc8dfc000]
   java.lang.Thread.State: WAITING (on object monitor)
    at java.lang.Object.wait(Native Method)
    - waiting on <0x0000000783e066e0> (a java.lang.ref.Reference$Lock)
    at java.lang.Object.wait(Object.java:503)
    at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:133)
    - locked <0x0000000783e066e0> (a java.lang.ref.Reference$Lock)
```

我们能看到：

> 线程的状态： WAITING 线程的调用栈 线程的当前锁住的资源： <0x0000000783e066e0> 线程当前等待的资源：<0x0000000783e066e0>

为什么同时锁住的等待同一个资源：

> 线程的执行中，先获得了这个对象的 Monitor（对应于 locked <0x0000000783e066e0>）。当执行到 obj.wait(), 线程即放弃了 Monitor的所有权，进入 “wait set”队列（对应于 waiting on <0x0000000783e066e0> ）。

## 2.8 死锁分析

学会了怎么使用jstack命令之后，我们就可以看看，如何使用jstack分析死锁了，这也是我们一定要掌握的内容。 **啥叫死锁？** 所谓[死锁](http://zh.wikipedia.org/wiki/%E6%AD%BB%E9%94%81)： 是指两个或两个以上的进程在执行过程中，由于竞争资源或者由于彼此通信而造成的一种阻塞的现象，若无外力作用，它们都将无法推进下去。此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。 

说白了，我现在想吃鸡蛋灌饼，桌子上放着鸡蛋和饼，但是我和我的朋友同时分别拿起了鸡蛋和病，我手里拿着鸡蛋，但是我需要他手里的饼。他手里拿着饼，但是他想要我手里的鸡蛋。就这样，如果不能同时拿到鸡蛋和饼，那我们就不能继续做后面的工作（做鸡蛋灌饼）。所以，这就造成了死锁。 **看一段死锁的程序：**

```java
package javaCommand;
/**
 * @author hollis
 */
public class JStackDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockclass(true));//建立一个线程
        Thread t2 = new Thread(new DeadLockclass(false));//建立另一个线程
        t1.start();//启动一个线程
        t2.start();//启动另一个线程
    }
}
class DeadLockclass implements Runnable {
    public boolean falg;// 控制线程
    DeadLockclass(boolean falg) {
        this.falg = falg;
    }
    public void run() {
        /**
         * 如果falg的值为true则调用t1线程
         */
        if (falg) {
            while (true) {
                synchronized (Suo.o1) {
                    System.out.println("o1 " + Thread.currentThread().getName());
                    synchronized (Suo.o2) {
                        System.out.println("o2 " + Thread.currentThread().getName());
                    }
                }
            }
        }
        /**
         * 如果falg的值为false则调用t2线程
         */
        else {
            while (true) {
                synchronized (Suo.o2) {
                    System.out.println("o2 " + Thread.currentThread().getName());
                    synchronized (Suo.o1) {
                        System.out.println("o1 " + Thread.currentThread().getName());
                    }
                }
            }
        }
    }
}

class Suo {
    static Object o1 = new Object();
    static Object o2 = new Object();
}
```

当我启动该程序时，我们看一下控制台：

[![thread_meitu_1](http://www.hollischuang.com/wp-content/uploads/2016/01/QQ20160118-0.png)](http://qyu1325060001.my3w.com/wp-content/uploads/2015/04/thread_meitu_1.jpg)

我们发现，程序只输出了两行内容，然后程序就不再打印其它的东西了，但是程序并没有停止。这样就产生了死锁。 当线程1使用`synchronized`锁住了o1的同时，线程2也是用`synchronized`锁住了o2。当两个线程都执行完第一个打印任务的时候，线程1想锁住o2，线程2想锁住o1。但是，线程1当前锁着o1，线程2锁着o2。所以两个想成都无法继续执行下去，就造成了死锁。

然后，我们使用**jstack来看一下线程堆栈信息**：

```
Found one Java-level deadlock:
=============================
"Thread-1":
  waiting to lock monitor 0x00007f0134003ae8 (object 0x00000007d6aa2c98, a java.lang.Object),
  which is held by "Thread-0"
"Thread-0":
  waiting to lock monitor 0x00007f0134006168 (object 0x00000007d6aa2ca8, a java.lang.Object),
  which is held by "Thread-1"

Java stack information for the threads listed above:
===================================================
"Thread-1":
    at javaCommand.DeadLockclass.run(JStackDemo.java:40)
    - waiting to lock <0x00000007d6aa2c98> (a java.lang.Object)
    - locked <0x00000007d6aa2ca8> (a java.lang.Object)
    at java.lang.Thread.run(Thread.java:745)
"Thread-0":
    at javaCommand.DeadLockclass.run(JStackDemo.java:27)
    - waiting to lock <0x00000007d6aa2ca8> (a java.lang.Object)
    - locked <0x00000007d6aa2c98> (a java.lang.Object)
    at java.lang.Thread.run(Thread.java:745)

Found 1 deadlock.
```

哈哈，堆栈写的很明显，它告诉我们 `Found one Java-level deadlock`，然后指出造成死锁的两个线程的内容。然后，又通过 `Java stack information for the threads listed above`来显示更详细的死锁的信息。 他说

> Thread-1在想要执行第40行的时候，当前锁住了资源`<0x00000007d6aa2ca8>`,但是他在等待资源`<0x00000007d6aa2c98>` Thread-0在想要执行第27行的时候，当前锁住了资源`<0x00000007d6aa2c98>`,但是他在等待资源`<0x00000007d6aa2ca8>` 由于这两个线程都持有资源，并且都需要对方的资源，所以造成了死锁。 原因我们找到了，就可以具体问题具体分析，解决这个死锁了。

## 2.9 其他

**虚拟机执行Full GC时,会阻塞所有的用户线程。因此,即时获取到同步锁的线程也有可能被阻塞。** 在查看线程Dump时,首先查看内存使用情况。



------

# 3. [Java命令学习系列（三）——Jmap](https://www.hollischuang.com/archives/303)

> jmap是JDK自带的工具软件，主要用于打印指定Java进程(或核心文件、远程调试服务器)的共享对象内存映射或堆内存细节。可以使用jmap生成Heap Dump。在[Java命令学习系列（零）——常见命令及Java Dump介绍](http://www.hollischuang.com/archives/308)和[Java命令学习系列（二）——Jstack](http://www.hollischuang.com/archives/110)中分别有关于Java Dump以及线程 Dump的介绍。 **这篇文章主要介绍Java的堆Dump以及jamp命令**

## 3.1 什么是堆Dump

堆Dump是反应Java堆使用情况的内存镜像，其中主要包括**系统信息**、**虚拟机属性**、**完整的线程Dump**、**所有类和对象的状态**等。 一般，在内存不足、GC异常等情况下，我们就会怀疑有[内存泄露](http://zh.wikipedia.org/zh-cn/%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F)。这个时候我们就可以制作堆Dump来查看具体情况。分析原因。

## 3.2 基础知识

[Java虚拟机的内存组成以及堆内存介绍](http://www.hollischuang.com/archives/80) [Java GC工作原理](http://www.hollischuang.com/archives/76) 常见内存错误：

> outOfMemoryError **年老代内存不足。**
> outOfMemoryError:PermGen Space **永久代内存不足。**
> outOfMemoryError:GC overhead limit exceed **垃圾回收时间占用系统运行时间的98%或以上。**

## 3.3 jmap

**用法摘要**

```shell
Usage:
    jmap [option] <pid>
        (to connect to running process)
    jmap [option] <executable <core>
        (to connect to a core file)
    jmap [option] [server_id@]<remote server IP or hostname>
        (to connect to remote debug server)

where <option> is one of:
    <none>               to print same info as Solaris pmap
    -heap                to print java heap summary
    -histo[:live]        to print histogram of java object heap; if the "live"
                         suboption is specified, only count live objects
    -permstat            to print permanent generation statistics
    -finalizerinfo       to print information on objects awaiting finalization
    -dump:<dump-options> to dump java heap in hprof binary format
                         dump-options:
                           live         dump only live objects; if not specified,
                                        all objects in the heap are dumped.
                           format=b     binary format
                           file=<file>  dump heap to <file>
                         Example: jmap -dump:live,format=b,file=heap.bin <pid>
    -F                   force. Use with -dump:<dump-options> <pid> or -histo
                         to force a heap dump or histogram when <pid> does not
                         respond. The "live" suboption is not supported
                         in this mode.
    -h | -help           to print this help message
    -J<flag>             to pass <flag> directly to the runtime system
```

> **指定进程号(pid)的进程** jmap [ option ] **指定核心文件** jmap [ option ] **指定远程调试服务器**jmap [ option ] [server-id@]

------

> **参数：**
>
> > **option** 选项参数是互斥的(不可同时使用)。想要使用选项参数，直接跟在命令名称后即可。
> > **pid** 需要打印配置信息的进程ID。该进程必须是一个Java进程。想要获取运行的Java进程列表，你可以使用jps。
> > **executable** 产生核心dump的Java可执行文件。
> > **core** 需要打印配置信息的核心文件。
> > **remote-hostname-or-IP** 远程调试服务器的(请查看jsadebugd)主机名或IP地址。
> > **server-id** 可选的唯一id，如果相同的远程主机上运行了多台调试服务器，用此选项参数标识服务器。
>
> **选项:**
>
> > `<no option>` 如果使用不带选项参数的jmap打印共享对象映射，将会打印目标虚拟机中加载的每个共享对象的起始地址、映射大小以及共享对象文件的路径全称。这与Solaris的pmap工具比较相似。
> > `-dump:[live,]format=b,file=<filename>` 以hprof二进制格式转储Java堆到指定`filename`的文件中。live子选项是可选的。如果指定了live子选项，堆中只有活动的对象会被转储。想要浏览heap dump，你可以使用jhat(Java堆分析工具)读取生成的文件。
> > `-finalizerinfo` 打印等待终结的对象信息。
> > `-heap` 打印一个堆的摘要信息，包括使用的GC算法、堆配置信息和generation wise heap usage。
> > `-histo[:live]` 打印堆的柱状图。其中包括每个Java类、对象数量、内存大小(单位：字节)、完全限定的类名。打印的虚拟机内部的类名称将会带有一个’*’前缀。如果指定了live子选项，则只计算活动的对象。
> > `-permstat` 打印Java堆内存的永久保存区域的类加载器的智能统计信息。对于每个类加载器而言，它的名称、活跃度、地址、父类加载器、它所加载的类的数量和大小都会被打印。此外，包含的字符串数量和大小也会被打印。
> > `-F` 强制模式。如果指定的pid没有响应，请使用jmap -dump或jmap -histo选项。此模式下，不支持live子选项。
> > `-h` 打印帮助信息。
> > `-help` 打印帮助信息。
> > `-J<flag>` 指定传递给运行jmap的JVM的参数。

## 3.4 举例

**查看java 堆（heap）使用情况,**执行命令： `hollis@hos:~/workspace/design_apaas/apaasweb/control/bin$ jmap -heap 31846`

```shell
Attaching to process ID 31846, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 24.71-b01

using thread-local object allocation.
Parallel GC with 4 thread(s)//GC 方式

Heap Configuration: //堆内存初始化配置
   MinHeapFreeRatio = 0 //对应jvm启动参数-XX:MinHeapFreeRatio设置JVM堆最小空闲比率(default 40)
   MaxHeapFreeRatio = 100 //对应jvm启动参数 -XX:MaxHeapFreeRatio设置JVM堆最大空闲比率(default 70)
   MaxHeapSize      = 2082471936 (1986.0MB) //对应jvm启动参数-XX:MaxHeapSize=设置JVM堆的最大大小
   NewSize          = 1310720 (1.25MB)//对应jvm启动参数-XX:NewSize=设置JVM堆的‘新生代’的默认大小
   MaxNewSize       = 17592186044415 MB//对应jvm启动参数-XX:MaxNewSize=设置JVM堆的‘新生代’的最大大小
   OldSize          = 5439488 (5.1875MB)//对应jvm启动参数-XX:OldSize=<value>:设置JVM堆的‘老生代’的大小
   NewRatio         = 2 //对应jvm启动参数-XX:NewRatio=:‘新生代’和‘老生代’的大小比率
   SurvivorRatio    = 8 //对应jvm启动参数-XX:SurvivorRatio=设置年轻代中Eden区与Survivor区的大小比值 
   PermSize         = 21757952 (20.75MB)  //对应jvm启动参数-XX:PermSize=<value>:设置JVM堆的‘永生代’的初始大小
   MaxPermSize      = 85983232 (82.0MB)//对应jvm启动参数-XX:MaxPermSize=<value>:设置JVM堆的‘永生代’的最大大小
   G1HeapRegionSize = 0 (0.0MB)

Heap Usage://堆内存使用情况
PS Young Generation
Eden Space://Eden区内存分布
   capacity = 33030144 (31.5MB)//Eden区总容量
   used     = 1524040 (1.4534378051757812MB)  //Eden区已使用
   free     = 31506104 (30.04656219482422MB)  //Eden区剩余容量
   4.614088270399305% used //Eden区使用比率
From Space:  //其中一个Survivor区的内存分布
   capacity = 5242880 (5.0MB)
   used     = 0 (0.0MB)
   free     = 5242880 (5.0MB)
   0.0% used
To Space:  //另一个Survivor区的内存分布
   capacity = 5242880 (5.0MB)
   used     = 0 (0.0MB)
   free     = 5242880 (5.0MB)
   0.0% used
PS Old Generation //当前的Old区内存分布
   capacity = 86507520 (82.5MB)
   used     = 0 (0.0MB)
   free     = 86507520 (82.5MB)
   0.0% used
PS Perm Generation//当前的 “永生代” 内存分布
   capacity = 22020096 (21.0MB)
   used     = 2496528 (2.3808746337890625MB)
   free     = 19523568 (18.619125366210938MB)
   11.337498256138392% used

670 interned Strings occupying 43720 bytes.
```

------

**查看堆内存(histogram)中的对象数量及大小**。执行命令： `hollis@hos:~/workspace/design_apaas/apaasweb/control/bin$ jmap -histo 3331`

```shell
num     #instances         #bytes  class name
编号     个数                字节     类名
----------------------------------------------
   1:             7        1322080  [I
   2:          5603         722368  <methodKlass>
   3:          5603         641944  <constMethodKlass>
   4:         34022         544352  java.lang.Integer
   5:           371         437208  <constantPoolKlass>
   6:           336         270624  <constantPoolCacheKlass>
   7:           371         253816  <instanceKlassKlass>
```

> **jmap -histo:live 这个命令执行，JVM会先触发gc，然后再统计信息。**

**将内存使用的详细情况输出到文件**，执行命令： `hollis@hos:~/workspace/design_apaas/apaasweb/control/bin$ jmap -dump:format=b,file=heapDump 6900`  

然后用`jhat`命令可以参看 `jhat -port 5000 heapDump` 在浏览器中访问：`http://localhost:5000/` 查看详细信息

> **这个命令执行，JVM会将整个heap的信息dump写入到一个文件，heap如果比较大的话，就会导致这个过程比较耗时，并且执行的过程中为了保证dump的信息是可靠的，所以会暂停应用。**

## 3.5 总结

1.如果程序内存不足或者频繁GC，很有可能存在内存泄露情况，这时候就要借助Java堆Dump查看对象的情况。
2.要制作堆Dump可以直接使用jvm自带的jmap命令
3.可以先使用`jmap -heap`命令查看堆的使用情况，看一下各个堆空间的占用情况。
4.使用`jmap -histo:[live]`查看堆内存中的对象的情况。如果有大量对象在持续被引用，并没有被释放掉，那就产生了内存泄露，就要结合代码，把不用的对象释放掉。
5.也可以使用 `jmap -dump:form   at=b,file=<fileName>`命令将堆信息保存到一个文件中，再借助jhat命令查看详细内容
6.在内存出现泄露、溢出或者其它前提条件下，建议多dump几次内存，把内存文件进行编号归档，便于后续内存整理分析。

## 3.6 错误总结

Error attaching to process: sun.jvm.hotspot.debugger.DebuggerException: Can’t attach to the process

在ubuntu中第一次使用jmap会报错：`Error attaching to process: sun.jvm.hotspot.debugger.DebuggerException: Can't attach to the process`，这是oracla文档中提到的一个bug:<http://bugs.java.com/bugdatabase/view_bug.do?bug_id=7050524>,解决方式如下：

> 1. echo 0 | sudo tee /proc/sys/kernel/yama/ptrace_scope 该方法在下次重启前有效。
> 2. 永久有效方法 sudo vi /etc/sysctl.d/10-ptrace.conf 编辑下面这行: kernel.yama.ptrace_scope = 1 修改为: kernel.yama.ptrace_scope = 0 重启系统，使修改生效。

# 4. [Java命令学习系列（四）——jstat](https://www.hollischuang.com/archives/481)

> jstat(JVM Statistics Monitoring Tool)是用于监控虚拟机各种运行状态信息的命令行工具。他可以显示本地或远程虚拟机进程中的类装载、内存、垃圾收集、JIT编译等运行数据，在没有GUI图形的服务器上，它是运行期定位虚拟机性能问题的首选工具。



jstat位于java的bin目录下，主要利用JVM内建的指令对Java应用程序的资源和性能进行实时的命令j行的监控，包括了对Heap size和垃圾回收状况的监控。可见，Jstat是轻量级的、专门针对JVM的工具，非常适用。

## 4.1 jstat 命令格式

```shell
Usage: jstat -help|-options
       jstat -<option> [-t] [-h<lines>] <vmid> [<interval> [<count>]]

Definitions:
  <option>      An option reported by the -options option
  <vmid>        Virtual Machine Identifier. A vmid takes the following form:
                     <lvmid>[@<hostname>[:<port>]]
                Where <lvmid> is the local vm identifier for the target
                Java virtual machine, typically a process id; <hostname> is
                the name of the host running the target Java virtual machine;
                and <port> is the port number for the rmiregistry on the
                target host. See the jvmstat documentation for a more complete
                description of the Virtual Machine Identifier.
  <lines>       Number of samples between header lines.
  <interval>    Sampling interval. The following forms are allowed:
                    <n>["ms"|"s"]
                Where <n> is an integer and the suffix specifies the units as 
                milliseconds("ms") or seconds("s"). The default units are "ms".
  <count>       Number of samples to take before terminating.
  -J<flag>      Pass <flag> directly to the runtime system.
```

## 4.2 参数解释：

Option — 选项，我们一般使用 -gcutil 查看gc情况

vmid — VM的进程号，即当前运行的java进程号

interval– 间隔时间，单位为秒或者毫秒

count — 打印次数，如果缺省则打印无数次

参数interval和count代表查询间隔和次数，如果省略这两个参数，说明只查询一次。假设需要每250毫秒查询一次进程5828垃圾收集状况，一共查询5次，那命令行如下：

```
jstat -gc 5828 250 5
```

> 对于命令格式中的`VMID`与`LVMID`需要特别说明下：如果是本地虚拟机进程，`VMID`(Virtual Machine IDentifier,虚机标识符)和`LVMID`(Local Virtual Machine IDentifier,虚机标识符)是一致的，如果是远程虚拟机进程，那VMID的格式应当是：`[protocol:][//] lvmid [@hostname[:port]/servername]`

## 4.3 option

选项option代表这用户希望查询的虚拟机信息，主要分为3类：类装载、垃圾收集和运行期编译状况，具体选项及作用如下：

> –`class` 监视类装载、卸载数量、总空间及类装载所耗费的时间 –`gc` 监视Java堆状况，包括Eden区、2个Survivor区、老年代、永久代等的容量 –`gccapacity` 监视内容与-gc基本相同，但输出主要关注Java堆各个区域使用到的最大和最小空间 –`gcutil` 监视内容与-gc基本相同，但输出主要关注已使用空间占总空间的百分比 –`gccause` 与-gcutil功能一样，但是会额外输出导致上一次GC产生的原因 –`gcnew` 监视新生代GC的状况 –`gcnewcapacity` 监视内容与-gcnew基本相同，输出主要关注使用到的最大和最小空间 –`gcold` 监视老年代GC的状况 –`gcoldcapacity` 监视内容与——gcold基本相同，输出主要关注使用到的最大和最小空间 –`gcpermcapacity` 输出永久代使用到的最大和最小空间 –`compiler` 输出JIT编译器编译过的方法、耗时等信息 –`printcompilation` 输出已经被JIT编译的方法

## 4.4 常见术语

1、`jstat –class<pid> :` 显示加载class的数量，及所占空间等信息。

> `Loaded` 装载的类的数量 `Bytes` 装载类所占用的字节数 `Unloaded` 卸载类的数量 `Bytes` 卸载类的字节数 `Time` 装载和卸载类所花费的时间

2、`jstat -compiler <pid>`显示VM实时编译的数量等信息。

> `Compiled` 编译任务执行数量 `Failed` 编译任务执行失败数量 `Invalid` 编译任务执行失效数量 `Time` 编译任务消耗时间 `FailedType` 最后一个编译失败任务的类型 `FailedMethod` 最后一个编译失败任务所在的类及方法

3、`jstat -gc <pid>`: 可以显示gc的信息，查看gc的次数，及时间。

> `S0C` 年轻代中第一个survivor（幸存区）的容量 (字节) `S1C` 年轻代中第二个survivor（幸存区）的容量 (字节) `S0U` 年轻代中第一个survivor（幸存区）目前已使用空间 (字节) `S1U` 年轻代中第二个survivor（幸存区）目前已使用空间 (字节) `EC` 年轻代中Eden（伊甸园）的容量 (字节) `EU` 年轻代中Eden（伊甸园）目前已使用空间 (字节) `OC` Old代的容量 (字节) `OU` Old代目前已使用空间 (字节) `PC` Perm(持久代)的容量 (字节) `PU` Perm(持久代)目前已使用空间 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `YGCT` 从应用程序启动到采样时年轻代中gc所用时间(s) `FGC` 从应用程序启动到采样时old代(全gc)gc次数 `FGCT` 从应用程序启动到采样时old代(全gc)gc所用时间(s) `GCT` 从应用程序启动到采样时gc用的总时间(s)

4、`jstat -gccapacity <pid>:`可以显示，VM内存中三代（young,old,perm）对象的使用和占用大小

> `NGCMN` 年轻代(young)中初始化(最小)的大小(字节) `NGCMX` 年轻代(young)的最大容量 (字节) `NGC` 年轻代(young)中当前的容量 (字节) `S0C` 年轻代中第一个survivor（幸存区）的容量 (字节) `S1C` 年轻代中第二个survivor（幸存区）的容量 (字节) `EC` 年轻代中Eden（伊甸园）的容量 (字节) `OGCMN` old代中初始化(最小)的大小 (字节) `OGCMX` old代的最大容量(字节) `OGC` old代当前新生成的容量 (字节) `OC` Old代的容量 (字节) `PGCMN` perm代中初始化(最小)的大小 (字节) `PGCMX` perm代的最大容量 (字节)
> `PGC` perm代当前新生成的容量 (字节) `PC` Perm(持久代)的容量 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `FGC` 从应用程序启动到采样时old代(全gc)gc次数

5、`jstat -gcutil <pid>`:统计gc信息

> `S0` 年轻代中第一个survivor（幸存区）已使用的占当前容量百分比 `S1` 年轻代中第二个survivor（幸存区）已使用的占当前容量百分比 `E` 年轻代中Eden（伊甸园）已使用的占当前容量百分比 `O` old代已使用的占当前容量百分比 `P` perm代已使用的占当前容量百分比 `YGC` 从应用程序启动到采样时年轻代中gc次数 `YGCT` 从应用程序启动到采样时年轻代中gc所用时间(s) `FGC` 从应用程序启动到采样时old代(全gc)gc次数 `FGCT` 从应用程序启动到采样时old代(全gc)gc所用时间(s) `GCT` 从应用程序启动到采样时gc用的总时间(s)

6、`jstat -gcnew <pid>`:年轻代对象的信息。

> `S0C` 年轻代中第一个survivor（幸存区）的容量 (字节) `S1C` 年轻代中第二个survivor（幸存区）的容量 (字节) `S0U` 年轻代中第一个survivor（幸存区）目前已使用空间 (字节) `S1U` 年轻代中第二个survivor（幸存区）目前已使用空间 (字节) `TT` 持有次数限制 `MTT` 最大持有次数限制 `EC` 年轻代中Eden（伊甸园）的容量 (字节) `EU` 年轻代中Eden（伊甸园）目前已使用空间 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `YGCT` 从应用程序启动到采样时年轻代中gc所用时间(s)

7、`jstat -gcnewcapacity<pid>`: 年轻代对象的信息及其占用量。

> `NGCMN` 年轻代(young)中初始化(最小)的大小(字节) `NGCMX` 年轻代(young)的最大容量 (字节) `NGC` 年轻代(young)中当前的容量 (字节) `S0CMX` 年轻代中第一个survivor（幸存区）的最大容量 (字节) `S0C` 年轻代中第一个survivor（幸存区）的容量 (字节) `S1CMX` 年轻代中第二个survivor（幸存区）的最大容量 (字节) `S1C` 年轻代中第二个survivor（幸存区）的容量 (字节) `ECMX` 年轻代中Eden（伊甸园）的最大容量 (字节) `EC` 年轻代中Eden（伊甸园）的容量 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `FGC` 从应用程序启动到采样时old代(全gc)gc次数

8、`jstat -gcold <pid>：`old代对象的信息。

> `PC` Perm(持久代)的容量 (字节) `PU` Perm(持久代)目前已使用空间 (字节) `OC` Old代的容量 (字节) `OU` Old代目前已使用空间 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `FGC` 从应用程序启动到采样时old代(全gc)gc次数 `FGCT` 从应用程序启动到采样时old代(全gc)gc所用时间(s) `GCT` 从应用程序启动到采样时gc用的总时间(s)

9、`stat -gcoldcapacity <pid>`: old代对象的信息及其占用量。

> `OGCMN` old代中初始化(最小)的大小 (字节) `OGCMX` old代的最大容量(字节) `OGC` old代当前新生成的容量 (字节) `OC` Old代的容量 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `FGC`从应用程序启动到采样时old代(全gc)gc次数 `FGCT` 从应用程序启动到采样时old代(全gc)gc所用时间(s) `GCT` 从应用程序启动到采样时gc用的总时间(s)

10、`jstat -gcpermcapacity<pid>`: perm对象的信息及其占用量。

> `PGCMN` perm代中初始化(最小)的大小 (字节) `PGCMX` perm代的最大容量 (字节)
> `PGC` perm代当前新生成的容量 (字节) `PC` Perm(持久代)的容量 (字节) `YGC` 从应用程序启动到采样时年轻代中gc次数 `FGC` 从应用程序启动到采样时old代(全gc)gc次数 `FGCT` 从应用程序启动到采样时old代(全gc)gc所用时间(s) `GCT` 从应用程序启动到采样时gc用的总时间(s)

11、`jstat -printcompilation <pid>`：当前VM执行的信息。

> `Compiled` 编译任务的数目 `Size` 方法生成的字节码的大小 `Type` 编译类型 `Method` 类名和方法名用来标识编译的方法。类名使用/做为一个命名空间分隔符。方法名是给定类中的方法。上述格式是由-XX:+PrintComplation选项进行设置的

------

# 5. [Java命令学习系列（五）——jhat](https://www.hollischuang.com/archives/1047)

> jhat(Java Heap Analysis Tool),是一个用来分析java的堆情况的命令。之前的文章讲到过，使用[jmap](http://www.hollischuang.com/archives/303)可以生成Java堆的Dump文件。生成dump文件之后就可以用jhat命令，将dump文件转成html的形式，然后通过http访问可以查看堆情况。

jhat命令解析会Java堆dump并启动一个web服务器，然后就可以在浏览器中查看堆的dump文件了。

## 5.1 实例

### 5.1.1 一、导出dump文件

关于dump文件的生成可以看[jmap](http://www.hollischuang.com/archives/303)命令的详细介绍.

**1、运行java程序**

```java
/**
 * Created by hollis on 16/1/21.
 */
public class JhatTest {

    public static void main(String[] args) {
        while(true) {
            String string = new String("hollis");
            System.out.println(string);
        }
    }
}
```

**2、查看该进程的ID**

```shell
HollisMacBook-Air:apaas hollis$ jps -l
68680 org.jetbrains.jps.cmdline.Launcher
62247 com.intellij.rt.execution.application.AppMain
69038 sun.tools.jps.Jps
```

使用[jps](http://www.hollischuang.com/archives/105)命令查看发现有三个java进程在运行，一个是我的IDEA使用的进程68680，一个是JPS命令使用的进程69038，另外一个就是上面那段代码运行的进程62247。

**3、生成dump文件**

```shell
HollisMacBook-Air:test hollis$ jmap -dump:format=b,file=heapDump 62247
Dumping heap to /Users/hollis/workspace/test/heapDump ...
Heap dump file created
```

以上命令可以将进程6900的堆dump文件导出到heapDump文件中。
查看当前目录就能看到heapDump文件。

除了使用jmap命令，还可以通过以下方式：

> 1、使用 jconsole 选项通过 HotSpotDiagnosticMXBean 从运行时获得堆转储（生成dump文件）、
>
> 2、虚拟机启动时如果指定了 -XX:+HeapDumpOnOutOfMemoryError 选项, 则在抛出 OutOfMemoryError 时, 会自动执行堆转储。
>
> 3、使用 hprof 命令

### 5.1.2 二、解析Java堆转储文件,并启动一个 web server

```shell
HollisMacBook-Air:apaas hollis$ jhat heapDump
Reading from heapDump...
Dump file created Thu Jan 21 18:59:51 CST 2016
Snapshot read, resolving...
Resolving 341297 objects...
Chasing references, expect 68 dots....................................................................
Eliminating duplicate references....................................................................
Snapshot resolved.
Started HTTP server on port 7000
Server is ready.
```

使用jhat命令，就启动了一个http服务，端口是7000

然后在访问http://localhost:7000/

页面如下：

![QQ20160121-1](https://ws2.sinaimg.cn/large/006tNc79ly1fz526gq2h6j30ql0a976b.jpg)

### 5.1.3  三、分析

在浏览器里面看到dump文件之后就可以进行分析了。这个页面会列出当前进程中的所有对像情况。

该页面提供了几个查询功能可供使用：

```
All classes including platform//
Show all members of the rootset
Show instance counts for all classes (including platform)
Show instance counts for all classes (excluding platform)
Show heap histogram
Show finalizer summary
Execute Object Query Language (OQL) query
```

一般查看堆异常情况主要看这个两个部分：

**Show instance counts for all classes (excluding platform)**，平台外的所有对象信息。如下图：

![QQ20160121-3](https://ws2.sinaimg.cn/large/006tNc79ly1fz52bjrotaj30sg06iwfk.jpg)

**Show heap histogram** 以树状图形式展示堆情况。如下图：

![QQ20160121-2](https://ws2.sinaimg.cn/large/006tNc79ly1fz52be6hlfj30q90c8426.jpg)

具体排查时需要结合代码，观察是否大量应该被回收的对象在一直被引用或者是否有占用内存特别大的对象无法被回收。

## 5.2 用法摘要

这一部分放在后面介绍的原因是一般不太使用。

```shell
HollisMacBook-Air:~ hollis$ jhat -help
Usage:  jhat [-stack <bool>] [-refs <bool>] [-port <port>] [-baseline <file>] [-debug <int>] [-version] [-h|-help] <file>

    -J<flag>          Pass <flag> directly to the runtime system. For
              example, -J-mx512m to use a maximum heap size of 512MB
    -stack false:     Turn off tracking object allocation call stack.
    -refs false:      Turn off tracking of references to objects
    -port <port>:     Set the port for the HTTP server.  Defaults to 7000
    -exclude <file>:  Specify a file that lists data members that should
              be excluded from the reachableFrom query.
    -baseline <file>: Specify a baseline object dump.  Objects in
              both heap dumps with the same ID and same class will
              be marked as not being "new".
    -debug <int>:     Set debug level.
                0:  No debug output
                1:  Debug hprof file parsing
                2:  Debug hprof file parsing, no server
    -version          Report version number
    -h|-help          Print this help and exit
    <file>            The file to read
```

> -stack false|true
>
> 关闭对象分配调用栈跟踪(tracking object allocation call stack)。 如果分配位置信息在堆转储中不可用. 则必须将此标志设置为 false. 默认值为 true.
>
> -refs false|true
>
> 关闭对象引用跟踪(tracking of references to objects)。 默认值为 true. 默认情况下, 返回的指针是指向其他特定对象的对象,如反向链接或输入引用(referrers or incoming references), 会统计/计算堆中的所有对象。
>
> **-port port-number**
>
> 设置 jhat HTTP server 的端口号. 默认值 7000.
>
> -exclude exclude-file
>
> 指定对象查询时需要排除的数据成员列表文件(a file that lists data members that should be excluded from the reachable objects query)。 例如, 如果文件列列出了 java.lang.String.value , 那么当从某个特定对象 Object o 计算可达的对象列表时, 引用路径涉及 java.lang.String.value 的都会被排除。
>
> -baseline exclude-file
>
> 指定一个基准堆转储(baseline heap dump)。 在两个 heap dumps 中有相同 object ID 的对象会被标记为不是新的(marked as not being new). 其他对象被标记为新的(new). 在比较两个不同的堆转储时很有用.
>
> -debug int
>
> 设置 debug 级别. 0 表示不输出调试信息。 值越大则表示输出更详细的 debug 信息.
>
> -version
>
> 启动后只显示版本信息就退出
>
> -J< flag >
>
> 因为 jhat 命令实际上会启动一个JVM来执行, 通过 -J 可以在启动JVM时传入一些启动参数. 例如, -J-Xmx512m 则指定运行 jhat 的Java虚拟机使用的最大堆内存为 512 MB. 如果需要使用多个JVM启动参数,则传入多个 -Jxxxxxx.

## 5.3 OQL

jhat还提供了一种对象查询语言(Object Query Language)，OQL有点类似SQL,可以用来查询。

OQL语句的执行页面: http://localhost:7000/oql/

OQL帮助信息页面为: http://localhost:7000/oqlhelp/

OQL的预发可以在帮助页面查看，这里就不详细讲解了。

# 6. [Java命令学习系列（六）——jinfo](https://www.hollischuang.com/archives/1094)

jinfo可以输出java进程、core文件或远程debug服务器的配置信息。这些配置信息包括JAVA系统参数及命令行参数,如果进程运行在64位虚拟机上，需要指明`-J-d64`参数，如：`jinfo -J-d64 -sysprops pid`

另外，Java7的官方文档指出，这一命令在后续的版本中可能不再使用。笔者使用的版本(jdk8)中已经不支持该命令(笔者翻阅了[java8中该命令的文档](http://docs.oracle.com/javase/8/docs/technotes/tools/unix/jinfo.html)，其中已经明确说明不再支持)。提示如下：

```shell
HollisMacBook-Air:test-workspace hollis$ jinfo 92520
Attaching to process ID 92520, please wait...
^@

Exception in thread "main" java.lang.reflect.InvocationTargetException
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke(Method.java:606)
    at sun.tools.jinfo.JInfo.runTool(JInfo.java:97)
    at sun.tools.jinfo.JInfo.main(JInfo.java:71)
Caused by: sun.jvm.hotspot.runtime.VMVersionMismatchException: Supported versions are 24.79-b02. Target VM is 25.40-b25
    at sun.jvm.hotspot.runtime.VM.checkVMVersion(VM.java:234)
    at sun.jvm.hotspot.runtime.VM.<init>(VM.java:297)
    at sun.jvm.hotspot.runtime.VM.initialize(VM.java:368)
    at sun.jvm.hotspot.bugspot.BugSpotAgent.setupVM(BugSpotAgent.java:598)
    at sun.jvm.hotspot.bugspot.BugSpotAgent.go(BugSpotAgent.java:493)
    at sun.jvm.hotspot.bugspot.BugSpotAgent.attach(BugSpotAgent.java:331)
    at sun.jvm.hotspot.tools.Tool.start(Tool.java:163)
    at sun.jvm.hotspot.tools.JInfo.main(JInfo.java:128)
    ... 6 more
```

由于打印jvm常用信息可以使用[Jps](http://www.hollischuang.com/archives/105)命令，并且在后续的java版本中可能不再支持，所以这个命令笔者就不详细介绍了。下面给出help信息，读者可自行阅读使用。（这就好像上高中，老师讲到一些难点的时候说，不明白也不要紧，知道有这么一回事就可以了！）

## 6.1 用法摘要

以键值对的形式打印出JAVA系统参数及命令行参数的名称和内容。

```
-flag name
prints the name and value of the given command line flag.
-flag [+|-]name
enables or disables the given boolean command line flag.
-flag name=value
sets the given command line flag to the specified value.
-flags
prints command line flags passed to the JVM. pairs.
-sysprops
prints Java System properties as name, value pairs.
-h
prints a help message
-help
prints a help message
```

## 6.2 参考资料

[jinfo](http://docs.oracle.com/javase/7/docs/technotes/tools/share/jinfo.html)

# 7. [Java命令学习系列（七）——javap](https://www.hollischuang.com/archives/1107)

> javap是jdk自带的一个工具，可以对代码[反编译](http://www.hollischuang.com/archives/58)，也可以查看java编译器生成的字节码。

一般情况下，很少有人使用javap对class文件进行反编译，因为有很多成熟的反编译工具可以使用，比如jad。但是，javap还可以查看java编译器为我们生成的字节码。通过它，可以对照源代码和字节码，从而了解很多编译器内部的工作。

## 7.1 实例

javap命令分解一个class文件，它根据options来决定到底输出什么。如果没有使用options,那么javap将会输出包，类里的protected和public域以及类里的所有方法。`javap`将会把它们输出在标准输出上。来看这个例子，先编译(`javac`)下面这个类。

```java
import java.awt.*;
import java.applet.*;

public class DocFooter extends Applet {
        String date;
        String email;

        public void init() {
                resize(500,100);
                date = getParameter("LAST_UPDATED");
                email = getParameter("EMAIL");
        }

        public void paint(Graphics g) {
                g.drawString(date + " by ",100, 15);
                g.drawString(email,290,15);
        }
}
```

在命令行上键入javap DocFooter后，输出结果如下

```java
Compiled from "DocFooter.java"
public class DocFooter extends java.applet.Applet {
  java.lang.String date;
  java.lang.String email;
  public DocFooter();
  public void init();
  public void paint(java.awt.Graphics);
}
```

如果加入了-c，即javap -c DocFooter，那么输出结果如下

```java
Compiled from "DocFooter.java"
public class DocFooter extends java.applet.Applet {
  java.lang.String date;

  java.lang.String email;

  public DocFooter();
    Code:
       0: aload_0       
       1: invokespecial #1                  // Method java/applet/Applet."<init>":()V
       4: return        

  public void init();
    Code:
       0: aload_0       
       1: sipush        500
       4: bipush        100
       6: invokevirtual #2                  // Method resize:(II)V
       9: aload_0       
      10: aload_0       
      11: ldc           #3                  // String LAST_UPDATED
      13: invokevirtual #4                  // Method getParameter:(Ljava/lang/String;)Ljava/lang/String;
      16: putfield      #5                  // Field date:Ljava/lang/String;
      19: aload_0       
      20: aload_0       
      21: ldc           #6                  // String EMAIL
      23: invokevirtual #4                  // Method getParameter:(Ljava/lang/String;)Ljava/lang/String;
      26: putfield      #7                  // Field email:Ljava/lang/String;
      29: return        

  public void paint(java.awt.Graphics);
    Code:
       0: aload_1       
       1: new           #8                  // class java/lang/StringBuilder
       4: dup           
       5: invokespecial #9                  // Method java/lang/StringBuilder."<init>":()V
       8: aload_0       
       9: getfield      #5                  // Field date:Ljava/lang/String;
      12: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      15: ldc           #11                 // String  by 
      17: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      20: invokevirtual #12                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      23: bipush        100
      25: bipush        15
      27: invokevirtual #13                 // Method java/awt/Graphics.drawString:(Ljava/lang/String;II)V
      30: aload_1       
      31: aload_0       
      32: getfield      #7                  // Field email:Ljava/lang/String;
      35: sipush        290
      38: bipush        15
      40: invokevirtual #13                 // Method java/awt/Graphics.drawString:(Ljava/lang/String;II)V
      43: return        
}
```

上面输出的内容就是字节码。

## 7.2 用法摘要

```shell
-help 帮助
-l 输出行和变量的表
-public 只输出public方法和域
-protected 只输出public和protected类和成员
-package 只输出包，public和protected类和成员，这是默认的
-p -private 输出所有类和成员
-s 输出内部类型签名
-c 输出分解后的代码，例如，类中每一个方法内，包含java字节码的指令，
-verbose 输出栈大小，方法参数的个数
-constants 输出静态final常量
```

## 7.3 总结

javap可以用于反编译和查看编译器编译后的字节码**。平时一般用`javap -c`比较多**，该命令用于列出每个方法所执行的JVM指令，并显示每个方法的字节码的实际作用。可以通过字节码和源代码的对比，深入分析java的编译原理，了解和解决各种Java原理级别的问题。

# 8. [Java开发必须掌握的线上问题排查命令](https://www.hollischuang.com/archives/1561)

作为一个合格的开发人员，不仅要能写得一手还代码，还有一项很重要的技能就是排查问题。这里提到的排查问题不仅仅是在coding的过程中debug等，还包括的就是线上问题的排查。由于在生产环境中，一般没办法debug（其实有些问题，debug也白扯。。。）,所以我们需要借助一些常用命令来查看运行时的具体情况，这些运行时信息包括但不限于运行日志、异常堆栈、堆使用情况、GC情况、JVM参数情况、线程情况等。

给一个系统定位问题的时候，知识、经验是关键，数据是依据，工具是运用知识处理数据的手段。为了便于我们排查和解决问题，Sun公司为我们提供了一些常用命令。这些命令一般都是jdk/lib/tools.jar中类库的一层薄包装。随着JVM的安装一起被安装到机器中，在bin目录中。下面就来认识一下这些命令以及具体使用方式。文中涉及到的所有命令的详细信息可以参考 [Java命令学习系列文章](http://www.hollischuang.com/archives/tag/java%E5%91%BD%E4%BB%A4%E5%AD%A6%E4%B9%A0%E7%B3%BB%E5%88%97)

## 8.1 jps

**功能**

显示当前所有java进程pid的命令。

**常用指令**

`jps`：显示当前用户的所有java进程的PID

`jps -v 3331`：显示虚拟机参数

`jps -m 3331`：显示传递给main()函数的参数

`jps -l 3331`：显示主类的全路径

详细介绍](http://www.hollischuang.com/archives/105)

## 8.2 jinfo

**功能**

实时查看和调整虚拟机参数，可以显示未被显示指定的参数的默认值（`jps -v 则不能`）。

> jdk8中已经不支持该命令。

**常用指令**

`jinfo -flag CMSIniniatingOccupancyFration 1444`：查询CMSIniniatingOccupancyFration参数值



详细介绍](http://www.hollischuang.com/archives/1094)

## 8.3 jstat

**功能**

显示进程中的类装载、内存、垃圾收集、JIT编译等运行数据。

**常用指令**

`jstat -gc 3331 250 20` ：查询进程2764的垃圾收集情况，每250毫秒查询一次，一共查询20次。

`jstat -gccause`：额外输出上次GC原因

`jstat -calss`：件事类装载、类卸载、总空间以及所消耗的时间



[详细介绍](http://www.hollischuang.com/archives/481)

## 8.4 jmap

**功能**

生成堆转储快照（heapdump）

**常用指令**

`jmap -heap 3331`：查看java 堆（heap）使用情况

`jmap -histo 3331`：查看堆内存(histogram)中的对象数量及大小

`jmap -histo:live 3331`：JVM会先触发gc，然后再统计信息

`jmap -dump:format=b,file=heapDump 3331`：将内存使用的详细情况输出到文件，之后一般使用其他工具进行分析。



[详细介绍](http://www.hollischuang.com/archives/303)

## 8.5 jhat

**功能**

一般与jmap搭配使用，用来分析jmap生成的堆转储文件。

> 由于有很多可视化工具（Eclipse Memory Analyzer 、IBM HeapAnalyzer）可以替代，所以很少用。不过在没有可视化工具的机器上也是可用的。

**常用指令**

`jmap -dump:format=b,file=heapDump 3331` + `jhat heapDump`：解析Java堆转储文件,并启动一个 web server



[详细介绍](http://www.hollischuang.com/archives/1047)

## 8.6 jstack

**功能**

生成当前时刻的线程快照。

**常用指令**

`jstack 3331`：查看线程情况

`jstack -F 3331`：正常输出不被响应时，使用该指令

`jstack -l 3331`：除堆栈外，显示关于锁的附件信息

[详细介绍](http://www.hollischuang.com/archives/110)



## 8.7 常见问题定位过程

**频繁GC问题或内存溢出问题**

一、使用`jps`查看线程ID

二、使用`jstat -gc 3331 250 20` 查看gc情况，一般比较关注PERM区的情况，查看GC的增长情况。

三、使用`jstat -gccause`：额外输出上次GC原因

四、使用`jmap -dump:format=b,file=heapDump 3331`生成堆转储文件

五、使用jhat或者可视化工具（Eclipse Memory Analyzer 、IBM HeapAnalyzer）分析堆情况。

六、结合代码解决内存溢出或泄露问题。



**死锁问题**

一、使用`jps`查看线程ID

二、使用`jstack 3331`：查看线程情况



## 8.8 结语

经常使用适当的虚拟机监控和分析工具可以加快我们分析数据、定位解决问题的速度，但也要知道，工具永远都是知识技能的一层包装，没有什么工具是包治百病的。



































































