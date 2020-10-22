# 1. Redis是什么

Redis is an open source (BSD licensed),**in-memory data structure store**, used as a database,**cache** and message broker.



高性能:假如用户第一次访问数据库中的某些数据。这个过程会比较慢，因为是从硬盘上读取的。将该用户访问的数据存在数缓存中，这样下一次再访问这些数据的时候就可以直接从缓存中获取了。

高并发:直接操作缓存能够承受的请求是远远大于直接访问数据库的。



- **异常快** - Redis 非常快，每秒可执行大约 110000 次的设置(SET)操作，每秒大约可执行 81000 次的读取/获取(GET)操作。
- **支持丰富的数据类型** - Redis 支持开发人员常用的大多数数据类型，例如列表，集合，排序集和散列等等。这使得 Redis 很容易被用来解决各种问题，因为我们知道哪些问题可以更好使用地哪些数据类型来处理解决。
- **操作具有原子性** - 所有 Redis 操作都是原子操作，这确保如果两个客户端并发访问，Redis 服务器能接收更新的值。
- **多实用工具** - Redis 是一个多实用工具，可用于多种用例，如：缓存，消息队列(Redis 本地支持发布/订阅)，应用程序中的任何短期数据，例如，web应用程序中的会话，网页命中计数等。

本地测试redis性能：redis-benchmark -n 100000 -q

![image-20201020112351999](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020112352.png)

## 1.1  redis为什么快

1、完全基于内存，绝大部分请求是纯粹的内存操作，非常快速。

2、**高效的数据结构，加上底层做了大量优化**：Redis 对于底层的数据结构和内存占用做了大量的优化，例如不同长度的字符串使用不同的结构体表示，HyperLogLog 的密集型存储结构等等..

3、采用单线程，避免了不必要的上下文切换和竞争条件，也不存在多进程或者多线程导致的切换而消耗 CPU，不用去考虑各种锁的问题，不存在加锁释放锁操作，没有因为可能出现死锁而导致的性能消耗；（因为 Redis 是基于内存的操作，**CPU 不是 Redis 的瓶颈**，Redis 的瓶颈最有可能是 **机器内存的大小** 或者 **网络带宽**。）

4、使用多路 I/O 复用模型，非阻塞 IO；

## 1.2  为什么要用 redis 而不用 map/guava 做缓存

缓存分为本地缓存和分布式缓存。以 Java 为例，使用自带的 map 或者 guava 实现的是本地缓存，最主要的特点是 轻量以及快速，生命周期随着 jvm 的销毁而结束，并且在多实例的情况下，每个实例都需要各自保存一份缓存，缓 存不具有一致性。使用 redis 或 memcached 之类的称为分布式缓存，在多实例的情况下，各实例共用一份缓存数据，缓存具有一致性。

# 2. Redis数据结构

## 2.1 string

简单动态字符串(Simple dynamic string,SDS)，Redis使用sdshdr结构来表示一个SDS值：

```c
struct sdshdr{

    // 字节数组，用于保存字符串
    char buf[];

    // 记录buf数组中已使用的字节数量，也是字符串的长度
    int len;

    // 记录buf数组未使用的字节数量
    int free;
}
```

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018215338.png)

1. sdshdr数据结构中用len属性记录了字符串的长度。那么**获取字符串的长度时，时间复杂度只需要O(1)**。
2. SDS不会发生溢出的问题，如果修改SDS时，空间不足。先会扩展空间，再进行修改！(**内部实现了动态扩展机制**)。
3. SDS可以**减少内存分配的次数**(空间预分配机制)。在扩展空间时，除了分配修改时所必要的空间，还会分配额外的空闲空间(free 属性)。
4. SDS是**二进制安全的**，不以\0为结束符，而是直接看长度。

```shell
> SET key value
OK
> GET key
"value"
> EXISTS key
(integer) 1
> DEL key
(integer) 1
> GET key
(nil)
> SET key1 value1
OK
> SET key2 value2
OK
# 批量设置
> MGET key1 key2 key3    # 返回一个列表
1) "value1"
2) "value2"
3) (nil)
> MSET key1 value1 key2 value2
> MGET key1 key2
1) "value1"
2) "value2"
# 设置过期时间
> SET key value1
> GET key
"value1"
> EXPIRE name 5    # 5s 后过期
...                # 等待 5s
> GET key
(nil)
# 等价于SET + EXPIRE 的 SETNX
> SETNX key value1
...                # 等待 5s 后获取
> GET key
(nil)

> SETNX key value1  # 如果 key 不存在则 SET 成功
(integer) 1
> SETNX key value1  # 如果 key 存在则 SET 失败
(integer) 0
> GET key
"value"             # 没有改变 
# 计数，使用 INCR 命令进行 原子性 的自增操作，这意味着及时多个客户端对同一个 key 进行操作，也决不会导致竞争的情况：
> SET counter 100
> INCR count
(interger) 101
> INCRBY counter 50
(integer) 151
```

## 2.2 链表

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020113253.png)

- 无环双向链表
- 获取表头指针，表尾指针，链表节点长度的时间复杂度均为O(1)
- 链表使用`void *`指针来保存节点值，可以保存各种不同类型的值

```shell
> rpush mylist A
(integer) 1
> rpush mylist B
(integer) 2
> lpush mylist first
(integer) 3
> lrange mylist 0 -1    # -1 表示倒数第一个元素, 这里表示从第一个元素到最后一个元素，即所有
1) "first"
2) "A"
3) "B"
```

list分为两种：原本是ziplist编码的，如果保存的数据长度太大或者元素数量过多，会转换成linkedlist编码的。

ziplist：字符串元素的长度都小于64个字节`&&`总数量少于512个

linkedlist：字符串元素的长度大于64个字节`||`总数量大于512个



### 2.2.1 压缩列表(ziplist)

压缩列表(ziplist)是list和hash的底层实现之一。如果list的每个都是小整数值，或者是比较短的字符串，压缩列表(ziplist)作为list的底层实现。压缩列表(ziplist)是Redis为了节约内存而开发的，是由一系列的**特殊编码的连续内存块**组成的**顺序性**数据结构。

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018221205)

## 2.3 hash

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018220302.jpeg)

从代码实现和示例图上我们可以发现，**Redis中有两个哈希表**：

- ht[0]：用于存放**真实**的`key-vlaue`数据
- ht[1]：用于**扩容(rehash)**，实现渐进式rehash

Redis中哈希算法和哈希冲突跟Java实现的差不多，它俩**差异**就是：

- Redis哈希冲突时：是将新节点添加在链表的**表头**。
- JDK1.8后，Java在哈希冲突时：是将新的节点添加到链表的**表尾**。

**Redis是专门使用一个哈希表来做rehash的**。Redis在rehash时采取渐进式的原因：**数据量如果过大的话，一次性rehash会有庞大的计算量，这很可能导致服务器一段时间内停止服务**。

- 在字典中维持一个索引计数器变量rehashidx，并将设置为0，表示rehash开始。
- 在rehash期间每次对字典进行增加、查询、删除和更新操作时，**除了执行指定命令外**；还会将ht[0]中rehashidx索引上的值**rehash到ht[1]**，操作完成后rehashidx+1。
- 字典操作不断执行，最终在某个时间点，所有的键值对完成rehash，这时**将rehashidx设置为-1，表示rehash完成**
- 在渐进式rehash过程中，字典会同时使用两个哈希表ht[0]和ht[1]，所有的更新、删除、查找操作也会在两个哈希表进行。例如要查找一个键的话，**服务器会优先查找ht[0]，如果不存在，再查找ht[1]**，诸如此类。此外当执行**新增操作**时，新的键值对**一律保存到ht[1]**，不再对ht[0]进行任何操作，以保证ht[0]的键值对数量只减不增，直至变为空表。

```shell
> HSET books java "think in java"    # 命令行的字符串如果包含空格则需要使用引号包裹
(integer) 1
> HSET books python "python cookbook"
(integer) 1
> HGETALL books    # key 和 value 间隔出现
1) "java"
2) "think in java"
3) "python"
4) "python cookbook"
> HGET books java
"think in java"
> HSET books java "head first java"  
(integer) 0        # 因为是更新操作，所以返回 0
> HMSET books java "effetive  java" python "learning python"    # 批量操作
OK
```

## 2.4 set

set类型有两种**编码格式**：

- intset：保存的元素全都是整数`&&`总数量小于512
- hashtable：保存的元素不是整数`||`总数量大于512

intset编码的集合结构：

![intset编码的集合结构](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018221429.jpeg)

hashtable编码的集合结构：

![hashtable编码的集合结构](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018221429.jpeg)

编码之间的**转换：**

- 原本是intset编码的，如果保存的数据不是整数值或者元素数量大于512，会转换成hashtable编码的。

整数集合(intset):

整数集合是set(集合)的底层数据结构之一。当一个set(集合)**只包含整数值元素**，并且**元素的数量不多**时，Redis就会采用整数集合(intset)作为set(集合)的底层实现。

整数集合(intset)保证了元素是**不会出现重复**的，并且是**有序**的(从小到大排序)。

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018221055.jpeg)

```shell
> SADD books java
(integer) 1
> SADD books java    # 重复
(integer) 0
> SADD books python golang
(integer) 2
> SMEMBERS books    # 注意顺序，set 是无序的 
1) "java"
2) "python"
3) "golang"
> SISMEMBER books java    # 查询某个 value 是否存在，相当于 contains
(integer) 1
> SCARD books    # 获取长度
(integer) 3
> SPOP books     # 弹出一个
"java"
```

## 2.5 sort set

跳跃表(shiplist)是实现sortset(**有序**集合)的底层数据结构之一。

Redis的跳跃表实现由zskiplist和zskiplistNode两个结构组成。其中**zskiplist保存跳跃表的信息**(表头，表尾节点，长度)，**zskiplistNode则表示跳跃表的节点**。

```c
typeof struct zskiplistNode {
        // 后退指针
        struct zskiplistNode *backward;
        // 分值
        double score;
        // 成员对象
        robj *obj;
        // 层
        struct zskiplistLevel {
                // 前进指针
                struct zskiplistNode *forward;
                // 跨度
                unsigned int span;
        } level[];
} zskiplistNode;
```

zskiplistNode的对象示例图(带有不同层高的节点)：

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018220944)

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018220959.jpeg)

zskiplist的结构如下：

```c
typeof struct zskiplist {
        // 表头节点，表尾节点
        struct skiplistNode *header,*tail;
        // 表中节点数量
        unsigned long length;
        // 表中最大层数
        int level;
} zskiplist;
```

最后我们整个跳跃表的示例图如下：

![跳跃表示例图](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018220959.jpeg)

```shell
> ZADD books 9.0 "think in java"
> ZADD books 8.9 "java concurrency"
> ZADD books 8.6 "java cookbook"

> ZRANGE books 0 -1     # 按 score 排序列出，参数区间为排名范围
1) "java cookbook"
2) "java concurrency"
3) "think in java"

> ZREVRANGE books 0 -1  # 按 score 逆序列出，参数区间为排名范围
1) "think in java"
2) "java concurrency"
3) "java cookbook"

> ZCARD books           # 相当于 count()
(integer) 3

> ZSCORE books "java concurrency"   # 获取指定 value 的 score
"8.9000000000000004"                # 内部 score 使用 double 类型进行存储，所以存在小数点精度问题

> ZRANK books "java concurrency"    # 排名
(integer) 1

> ZRANGEBYSCORE books 0 8.91        # 根据分值区间遍历 zset
1) "java cookbook"
2) "java concurrency"

> ZRANGEBYSCORE books -inf 8.91 withscores  # 根据分值区间 (-∞, 8.91] 遍历 zset，同时返回分值。inf 代表 infinite，无穷大的意思。
1) "java cookbook"
2) "8.5999999999999996"
3) "java concurrency"
4) "8.9000000000000004"

> ZREM books "java concurrency"             # 删除 value
(integer) 1
> ZRANGE books 0 -1
1) "java cookbook"
2) "think in java"
```

# 3. 过期和淘汰

## 3.1 键的过期时间

- 设置键的**生存**时间可以通过`EXPIRE`或者`PEXPIRE`命令。
- 设置键的**过期**时间可以通过`EXPIREAT`或者`PEXPIREAT`命令。

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018222448.jpeg)

## 3.2 过期策略

- 定时删除(对内存友好，对CPU不友好)

- - 到时间点上就把所有过期的键删除了。

- 惰性删除(对CPU极度友好，对内存极度不友好)

- - 每次从键空间取键的时候，判断一下该键是否过期了，如果过期了就删除。

- 定期删除(折中)

- - **每隔**一段时间去删除过期键，**限制**删除的执行时长和频率。

Redis采用的是**惰性删除+定期删除**两种策略。

## 3.3 内存淘汰机制

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018222651)

# 4. Redis持久化

Redis是基于内存的，如果不想办法将数据保存在硬盘上，一旦Redis重启(退出/故障)，内存的数据将会全部丢失。

- 我们肯定不想Redis里头的数据由于某些故障全部丢失(导致所有请求都走MySQL)，即便发生了故障也希望可以将Redis原有的数据恢复过来，这就是持久化的作用。

Redis提供了两种不同的持久化方法来讲数据存储到硬盘里边：

- RDB(基于快照)，将某一时刻的所有数据保存到一个RDB文件中。
- AOF(append-only-file)，当Redis服务器执行**写命令**的时候，将执行的**写命令**保存到AOF文件中。

## 4.1RDB(快照持久化)

RDB持久化可以**手动**执行，也可以根据服务器配置**定期**执行。RDB持久化所生成的RDB文件是一个经过**压缩**的二进制文件，Redis可以通过这个文件**还原**数据库的数据。

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223035)

有两个命令可以生成RDB文件：

- `SAVE`会**阻塞**Redis服务器进程，服务器不能接收任何请求，直到RDB文件创建完毕为止。
- `BGSAVE`创建出一个**子进程**，由子进程来负责创建RDB文件，服务器进程可以继续接收请求。

Redis服务器在启动的时候，如果发现有RDB文件，就会**自动**载入RDB文件(不需要人工干预)

- 服务器在载入RDB文件期间，会处于阻塞状态，直到载入工作完成。

除了手动调用`SAVE`或者`BGSAVE`命令生成RDB文件之外，我们可以使用配置的方式来**定期**执行：

在默认的配置下，如果以下的条件被触发，就会执行`BGSAVE`命令

```
    save 900 1              #在900秒(15分钟)之后，至少有1个key发生变化，
    save 300 10            #在300秒(5分钟)之后，至少有10个key发生变化
    save 60 10000        #在60秒(1分钟)之后，至少有10000个key发生变化
```

原理大概就是这样子的(结合上面的配置来看)：

```
struct redisServer{
    // 修改计数器
    long long dirty;

    // 上一次执行保存的时间
    time_t lastsave;

    // 参数的配置
    struct saveparam *saveparams;
};
```

遍历参数数组，判断修改次数和时间是否符合，如果符合则调用`besave()`来生成RDB文件

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223035)

总结：通过手动调用`SAVE`或者`BGSAVE`命令或者配置条件触发，将数据库**某一时刻**的数据快照，生成RDB文件实现持久化。

## 4.2 AOF(文件追加)

上面已经介绍了RDB持久化是通过将某一时刻数据库的数据“快照”来实现的，下面我们来看看AOF是怎么实现的。

- AOF是通过保存Redis服务器所执行的**写命令**来记录数据库的数据的。

![AOF原理图](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223045.png)AOF原理图

比如说我们对空白的数据库执行以下写命令：

```
redis> SET meg "hello"
OK

redis> SADD fruits "apple" "banana" "cherry"
(integer) 3

redis> RPUSH numbers 128 256 512
(integer) 3 
```

Redis会产生以下内容的AOF文件：

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223108)

这些都是以Redis的命令**请求协议格式**保存的。Redis协议规范(RESP)参考资料：

- https://www.cnblogs.com/tommy-huang/p/6051577.html

AOF持久化功能的实现可以分为3个步骤：

- 命令追加：命令写入aof_buf缓冲区
- 文件写入：调用flushAppendOnlyFile函数，考虑是否要将aof_buf缓冲区写入AOF文件中
- 文件同步：考虑是否将内存缓冲区的数据真正写入到硬盘

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223120)

flushAppendOnlyFile函数的行为由服务器配置的**appendfsyn选项**来决定的：

```
    appendfsync always     # 每次有数据修改发生时都会写入AOF文件。
    appendfsync everysec   # 每秒钟同步一次，该策略为AOF的默认策略。
    appendfsync no         # 从不同步。高效但是数据不会被持久化。
```

从字面上应该就更好理解了，这里我就不细说了…

下面来看一下AOF是如何载入与数据还原的：

- 创建一个**伪客户端**(本地)来执行AOF的命令，直到AOF命令被全部执行完毕。

![img](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib0LSf7wiaom7XfZ9RhpUWsW2c34wOB0qyXe5ibMgWuXWQvasIeG5PauicQVyKl9hJQicFuwMFSE8YR6icw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 4.2.1AOF重写

从前面的示例看出，我们写了三条命令，AOF文件就保存了三条命令。如果我们的命令是这样子的：

```
redis > RPUSH list "Java" "3y"
(integer)2

redis > RPUSH list "Java3y"
integer(3)

redis > RPUSH list "yyy"
integer(4)
```

同样地，AOF也会保存3条命令。我们会发现一个问题：上面的命令是可以**合并**起来成为1条命令的，并不需要3条。这样就可以**让AOF文件的体积变得更小**。

AOF重写由Redis自行触发(参数配置)，也可以用`BGREWRITEAOF`命令**手动触发**重写操作。

- 要值得说明的是：**AOF重写不需要对现有的AOF文件进行任何的读取、分析。AOF重写是通过读取服务器当前数据库的数据来实现的**！

比如说现在有一个Redis数据库的数据如下：

![img](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib0LSf7wiaom7XfZ9RhpUWsW2DVhNso3jZWicrLOso0UibN5p1xdzbQJWA0ibx3GzQ5zbmiaKYic2onS1cUw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

新的AOF文件的命令如下，**没有一条是多余的**！

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223527)

### 4.2.2AOF后台重写

Redis将AOF重写程序放到**子进程**里执行(`BGREWRITEAOF`命令)，像`BGSAVE`命令一样fork出一个子进程来完成重写AOF的操作，从而不会影响到主进程。

AOF后台重写是不会阻塞主进程接收请求的，新的写命令请求可能会导致**当前数据库和重写后的AOF文件的数据不一致**！

为了解决数据不一致的问题，Redis服务器设置了一个**AOF重写缓冲区**，这个缓存区会在服务器**创建出子进程之后使用**。

![img](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223443)

## 4.3RDB和AOF对过期键的策略

RDB持久化对过期键的策略：

- 执行`SAVE`或者`BGSAVE`命令创建出的RDB文件，程序会对数据库中的过期键检查，**已过期的键不会保存在RDB文件中**。
- 载入RDB文件时，程序同样会对RDB文件中的键进行检查，**过期的键会被忽略**。

RDB持久化对过期键的策略：

- 如果数据库的键已过期，但还没被惰性/定期删除，AOF文件不会因为这个过期键产生任何影响(也就说会保留)，当过期的键被删除了以后，会追加一条DEL命令来显示记录该键被删除了
- 重写AOF文件时，程序会对RDB文件中的键进行检查，**过期的键会被忽略**。

复制模式：

- **主服务器来控制**从服务器统一删除过期键(保证主从服务器数据的一致性)

## 4.4RDB和AOF用哪个？

RDB和AOF并不互斥，它俩可以**同时使用**。

- RDB的优点：载入时**恢复数据快**、文件体积小。
- RDB的缺点：会一定程度上**丢失数据**(因为系统一旦在定时持久化之前出现宕机现象，此前没有来得及写入磁盘的数据都将丢失。)
- AOF的优点：丢失数据少(默认配置只丢失一秒的数据)。
- AOF的缺点：恢复数据相对较慢，文件体积大

如果Redis服务器**同时开启**了RDB和AOF持久化，服务器会**优先使用AOF文件**来还原数据(因为AOF更新频率比RDB更新频率要高，还原的数据更完善)

可能涉及到RDB和AOF的配置：

```
redis持久化，两种方式
1、rdb快照方式
2、aof日志方式

----------rdb快照------------
save 900 1
save 300 10
save 60 10000

stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir /var/rdb/

-----------Aof的配置-----------
appendonly no # 是否打开 aof日志功能

appendfsync always #每一个命令都立即同步到aof，安全速度慢
appendfsync everysec
appendfsync no 写入工作交给操作系统，由操作系统判断缓冲区大小，统一写入到aof  同步频率低，速度快


no-appendfsync-on-rewrite yes 正在导出rdb快照的时候不要写aof
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb 


./bin/redis-benchmark -n 20000
```

官网文档：

- https://redis.io/topics/persistence#rdb-advantages

# 5. Redis I/O模型

## 5.1 Redis事件

Redis服务器是一个**事件驱动程序**，主要处理以下两类事件：

- 文件事件：文件事件其实就是**对Socket操作的抽象**，Redis服务器与Redis客户端的通信会产生文件事件，服务器通过监听并处理这些事件来完成一系列的网络操作
- 时间事件：时间事件其实就是对**定时操作的抽象**，前面我们已经讲了RDB、AOF、定时删除键这些操作都可以由服务端去定时或者周期去完成，底层就是通过触发时间事件来实现的！

### 5.1.1 文件事件

Redis开发了自己的网络事件处理器，这个处理器被称为**文件事件处理器**。

文件事件处理器由四部分组成：

![文件事件处理器组成](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223851)

> 文件事件处理器使用I/O多路复用程序来**同时监听多个Socket**。当被监听的Socket准备好执行连接应答(accept)、读取(read)等等操作时，与操作相对应的文件事件就会产生，根据文件事件来为Socket关联对应的事件处理器，从而实现功能。

要值得注意的是：Redis中的I/O多路复用程序会将所有**产生事件的Socket放到一个队列**里边，然后通过这个队列以有序、同步、每次一个Socket的方式向文件事件分派器传送套接字。也就是说：当上一个Socket处理完毕后，I/O多路复用程序才会向文件事件分派器传送下一个Socket。

首先，IO多路复用程序首先会监听着Socket的`AE_READABLE`事件，该事件对应着连接应答处理器

- 可以理解简单成`SocketServet.accpet()`

![监听着Socket的AE_READABLE事件](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223935)

此时，一个名字叫做3y的Socket要连接服务器啦。服务器会用**连接应答处理器**处理。创建出客户端的Socket，并将客户端的Socket与命令请求处理器进行关联，使得客户端可以向服务器发送命令请求。

- 相当于`Socket s = ss.accept();`，创建出客户端的Socket，然后将该Socket关联**命令请求处理器**
- 此时客户端就可以向主服务器发送命令请求了

![客户端请求连接，服务器创建出客户端Scoket，关联命令请求处理器](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223851)

假设现在客户端发送一个命令请求`set Java3y "关注、点赞、评论"`，客户端Socket将产生`AE_READABLE`事件，**引发命令请求处理器执行**。处理器读取客户端的命令内容，然后传给对应的程序去执行。

客户端发送完命令请求后，**服务端总得给客户端回应的**。此时服务端会将客户端的Scoket的`AE_WRITABLE`事件与命令回复处理器关联。

![客户端的Scoket的AE_WRITABLE事件与命令回复处理器关联](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223851)

最后客户端尝试读取命令回复时，客户端Socket**产生**AE_WRITABLE事件，触发命令回复处理器执行。当把所有的回复数据写入到Socket之后，服务器就会**解除**客户端Socket的AE_WRITABLE事件与命令回复处理器的关联。

最后以《Redis设计与实现》的一张图来概括：

![Redis事件交互过程](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223851)

### 5.1.2时间事件

持续运行的Redis服务器会**定期**对自身的资源和状态进行检查和调整，这些定期的操作由**serverCron**函数负责执行，它的主要工作包括：

- 更新服务器的统计信息(时间、内存占用、数据库占用)
- 清理数据库的过期键值对
- AOF、RDB持久化
- 如果是主从服务器，对从服务器进行定期同步
- 如果是集群模式，对进群进行定期同步和连接
- …

Redis服务器将时间事件放在一个**链表**中，当时间事件执行器运行时，会遍历整个链表。时间事件包括：

- **周期性事件**(Redis一般只执行serverCron时间事件，serverCron时间事件是周期性的)
- 定时事件

## 5.2 客户端与服务器

在《Redis设计与实现》中各用了一章节来写客户端与服务器，我看完觉得比较底层的东西，也很难记得住，所以我决定**总结**一下比较重要的知识。如果以后真的遇到了，再来补坑~

服务器使用clints**链表**连接多个客户端状态，新添加的客户端状态会被放到链表的末尾

![客户端--链表](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018223851)

- 一个服务器可以与多个客户端建立网络连接，每个**客户端可以向服务器发送命令请求**，而服务器则接收并处理客户端发送的命令请求，并向客户端返回命令回复。
- Redis服务器使用**单线程单进程**的方式处理命令请求。在数据库中保存**客户端执行命令所产生的数据**，并通过**资源管理来维持**服务器自身的运转。

### 5.2.1客户端

客户端章节中主要讲解了**Redis客户端的属性**(客户端状态、输入/输出缓冲区、命令参数、命令函数等等)

```
typedef struct redisClient{

    //客户端状态的输入缓冲区用于保存客户端发送的命令请求,最大1GB,否则服务器将关闭这个客户端
    sds querybuf;  


    //负责记录argv数组的长度。
    int argc;   

    // 命令的参数
    robj **argv;  

    // 客户端要执行命令的实现函数
    struct redisCommand *cmd, *lastcmd;  


    //记录了客户端的角色(role),以及客户端所处的状态。 (REDIS_SLAVE | REDIS_MONITOR | REDIS_MULTI) 
    int flags;             

    //记录客户端是否通过了身份验证
    int authenticated;     

    //时间相关的属性
    time_t ctime;           /* Client creation time */       
    time_t lastinteraction; /* time of the last interaction, used for timeout */
    time_t obuf_soft_limit_reached_time;


    //固定大小的缓冲区用于保存那些长度比较小的回复
    /* Response buffer */
    int bufpos;
    char buf[REDIS_REPLY_CHUNK_BYTES];

    //可变大小的缓冲区用于保存那些长度比较大的回复
    list *reply; //可变大小缓冲区由reply 链表和一个或多个字符串对象组成
    //...
}
```

### 5.2.2服务端

**服务器章节**中主要讲解了Redis服务器读取客户端发送过来的命令是如何解析，以及初始化的过程。

服务器从启动到能够处理客户端的命令请求需要执行以下的步骤：

- 初始化服务器状态
- 载入服务器配置
- 初始化服务器的数据结构
- 还原数据库状态
- 执行事件循环

总的来说是这样子的：

```
def main():

    init_server();

    while server_is_not_shutdown();
        aeProcessEvents()

    clean_server();
```

从客户端发送命令道完成主要包括的步骤：

- 客户端将命令请求发送给服务器
- 服务器读取命令请求，分析出**命令参数**
- 命令执行器根据参数**查找命令的实现函数**，执行实现函数并得出命令回复
- 服务器将命令回复返回给客户端

# 6. 主从架构

## 6.1为什么要主从架构

Redis也跟关系型数据(MySQL)一样，如果有过多请求还是撑不住的。

因为Redis如果只有一台服务器的话，那随着请求越来越多：

- **Redis的内存是有限的**，可能放不下那么多的数据
- 单台Redis**支持的并发量也是有限的**。
- **万一这台Redis挂了**，所有的请求全走关系数据库了，那就更炸了。

显然，出现的上述问题是因为一台Redis服务器不够，所以多搞几台Redis服务器就可以了。为了实现我们服务的**高可用性**，可以将这几台Redis服务器做成是**主从**来进行管理。

## 6.2主从架构的特点

下面我们来看看Redis的主从架构特点：

- **主**服务器负责接收**写**请求
- **从**服务器负责接收**读**请求
- 从服务器的数据由主服务器**复制**过去。主从服务器的数据是**一致**的

![主从架构特点](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018225147.jpeg)

主从架构的**好处**：

- 读写分离(主服务器负责写，从服务器负责读)
- 高可用(某一台从服务器挂了，其他从服务器还能继续接收请求，不影响服务)
- 处理更多的并发量(每台从服务器**都可以接收读请求**，读QPS就上去了)

主从架构除了上面的形式，也有下面这种的(只不过用得比较少)：

![从服务器又挂着从服务器](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018225151.jpeg)

## 6.3 复制功能

主从架构的特点之一：主服务器和从服务器的数据是**一致**的。

因为主服务器是能接收写请求的，主服务器处理完写请求，会做什么来保证主从数据的一致性呢？如果主从服务器断开了，过一阵子才重连，又会怎么处理呢？

> 在Redis中，用户可以通过执行SALVEOF命令或者设置salveof选项，让一个服务器去复制(replicate)另一个服务器，我们称呼被复制的服务器为主服务器(master)，而对主服务器进行复制的服务器则被称为从服务器(salve)

![复制](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib3ZO9Sp7eLYY6pHbicfkMlzYbRzTTaq01WK2cuA7AYyFGT8qrzibib8MibMIQ7I24yNEZEqv9qf4DCHBA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### 6.3.1复制功能的具体实现

复制功能分为两个操作：

- 同步(sync)

- - 将从服务器的数据库状态**更新至**主服务器的数据库状态

- 命令传播(command propagate)

- - 主服务器的数据库状态**被修改**，导致主从服务器的数据库状态**不一致**，让主从服务器的数据库状态**重新回到一致状态**。

![主从数据一致性](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib3ZO9Sp7eLYY6pHbicfkMlzYHVLR38XVOOG2lKfiaBFNHN4q3lVKuzlSDGFVSdNFgouHkrjmqiat2HkA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

从服务器对主服务器的**同步又可以分为两种情况**：

- 初次同步：从服务器**没有复制过任何**的主服务器，或者从服务器要复制的主服务器跟上次复制的主服务器**不一样**。
- 断线后同步：处于**命令传播阶段**的主从服务器因为**网络原因**中断了复制，从服务器通过**自动重连**重新连接主服务器，并继续复制主服务器

在Redis2.8以前，断线后复制这部分其实缺少的只是**部分的数据**，但是要让主从服务器**重新执行SYNC命令**，这样的做法是非常低效的。(因为执行SYNC命令是把**所有的数据**再次同步，而不是只同步丢失的数据)

接下来我们来详细看看Redis2.8以后复制功能是怎么实现的：



首先我们来看一下**前置的工作**：

- 从服务器设置主服务器的IP和端口
- 建立与主服务器的Socket连接
- 发送PING命令(检测Socket读写是否正常与主服务器的通信状况)
- 身份验证(看有没有设置对应的验证配置)
- 从服务器给主服务器发送端口的信息，主服务器记录监听的端口

![Redis复制的前置工作](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib3ZO9Sp7eLYY6pHbicfkMlzYBqRh72uVGvQktTCorTYWwKEDGIibCAGjsEleZnujDibT2BwTaadUz7yw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)Redis复制的前置工作

前面也提到了，Redis2.8之前，断线后同步会重新执行SYNC命令，这是非常**低效**的。下面我们来看一下Redis2.8之后是怎么进行同步的。

> Redis从2.8版本开始，使用PSYNC命令来**替代**SYNC命令执行复制时同步的操作。

PSYNC命令具有**完整**重同步和**部分**重同步两种模式(其实就跟上面所说的初次复制和断线后复制差不多个意思)。

### 6.3.2 完整重同步

下面先来看看**完整**重同步是怎么实现的：

- 从服务器向主服务器发送PSYNC命令
- 收到PSYNC命令的主服务器执行BGSAVE命令，在后台**生成一个RDB文件**。并用一个**缓冲区**来记录从现在开始执行的所有**写命令**。
- 当主服务器的BGSAVE命令执行完后，将生成的RDB文件发送给从服务器，**从服务器接收和载入RBD文件**。将自己的数据库状态更新至与主服务器执行BGSAVE命令时的状态。
- 主服务器将所有缓冲区的**写命令发送给从服务器**，从服务器执行这些写命令，达到数据最终一致性。

![完整重同步](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018225358)

### 6.3.3 部分重同步

接下来我们来看看**部分**重同步，部分重同步可以让我们断线后重连**只需要同步缺失的数据**(而不是Redis2.8之前的同步全部数据)，这是符合逻辑的！

部分重同步功能由以下部分组成：

- 主从服务器的**复制偏移量**
- 主服务器的**复制积压缓冲区**
- 服务器运行的ID(**run ID**)

首先我们来解释一下上面的名词：

复制偏移量：执行复制的双方都会**分别维护**一个复制偏移量

- 主服务器每次传播N个字节，就将自己的复制偏移量加上N
- 从服务器每次收到主服务器的N个字节，就将自己的复制偏移量加上N

通过**对比主从复制的偏移量**，就很容易知道主从服务器的数据是否处于一致性的状态！

![复制偏移量](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018225503)

那断线重连以后，从服务器向主服务器发送PSYNC命令，报告现在的偏移量是36，那么主服务器该对从服务器执行完整重同步还是部分重同步呢？？这就交由**复制积压缓冲区**来决定。

当主服务器进行命令传播时，不仅仅会将写命令发送给所有的从服务器，还会将写命令**入队到复制积压缓冲区**里面(这个大小可以调的)。如果复制积压缓冲区**存在**丢失的偏移量的数据，那就执行部分重同步，否则执行完整重同步。

服务器运行的ID(**run ID**)实际上就是用来比对ID是否相同。如果不相同，则说明从服务器断线之前复制的主服务器和当前连接的主服务器是两台服务器，这就会进行完整重同步。

所以流程大概如此：

![同步的流程](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018225503)

### 6.3.4 命令传播

> 当完成了同步之后，主从服务器就会进入命令传播阶段。这时主服务器只要将自己的写命令发送给从服务器，而从服务器接收并执行主服务器发送过来的写命令，就可以保证主从服务器一直保持数据一致了！

在命令传播阶段，从服务器默认会以每秒一次的频率，向服务器发送命令`REPLCONF ACK <replication_offset>` 其中replication_offset是从服务器当前的复制偏移量

发送这个命令主要有三个作用：

- **检测主从服务器的网络状态**
- 辅助实现min-slaves选项
- 检测命令丢失

# 7. 集群



如果从服务器挂了，没关系，我们一般会有多个从服务器，其他的请求可以交由没有挂的从服务器继续处理。如果主服务器挂了，怎么办？因为我们的写请求由主服务器处理，只有一台主服务器，那就无法处理写请求了？

Redis提供了**哨兵(Sentinel)机制**供我们解决上面的情况。如果主服务器挂了，我们可以将从服务器**升级**为主服务器，等到旧的主服务器(挂掉的那个)重连上来，会将它(挂掉的主服务器)变成从服务器。

- 这个过程叫做**主备切换**(故障转移)

在正常的情况下，主从加哨兵(Sentinel)机制是这样子的：

![正常情况下](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018231332.png)

主服务器挂了，主从复制操作就中止了，并且哨兵系统是可以察觉出主服务挂了。：

![Sentinel可以察觉主服务掉线，复制操作中止。](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib2yJD3Nqmn9a5r0Kzc5lgjgjxjVEHhM1GM9HrlKFrt0EoatODrAc4NIesAJ86QNowPoSE6pyibkBpg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

Redis提供哨兵机制可以将**选举**一台从服务器变成主服务器

![选举一台从服务器变成主服务器](https://mmbiz.qpic.cn/mmbiz_png/2BGWl1qPxib2yJD3Nqmn9a5r0Kzc5lgjgCDrlxd5J9nBbIuQeyiaiaHNYKbJlu265blDAzZqUhibf7Pe3eIBZzcmVg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

![旧的主服务器如果重连了，会变成从服务器](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201018231436)



# 8. 缓存雪崩

**如果我们的缓存挂掉了，这意味着我们的全部请求都跑去数据库了**。

如果缓存数据**设置的过期时间是相同**的，并且Redis恰好将这部分数据全部删光了。这就会导致在这段时间内，这些缓存**同时失效**，全部请求到数据库中。



解决办法：在缓存的时候给过期时间加上一个**随机值**，这样就会大幅度的**减少缓存在同一时间过期**。

- 事发前：实现Redis的**高可用**(主从架构+Sentinel 或者Redis Cluster)，尽量避免Redis挂掉这种情况发生。
- 事发中：万一Redis真的挂了，我们可以设置**本地缓存(ehcache)+限流(hystrix)**，尽量避免我们的数据库被干掉(起码能保证我们的服务还是能正常工作的)
- 事发后：redis持久化，重启后自动从磁盘上加载数据，**快速恢复缓存数据**。

# 9. 缓存穿透

> 缓存穿透是指查询一个一定**不存在的数据**。由于缓存不命中，并且出于容错考虑，如果从**数据库查不到数据则不写入缓存**，这将导致这个不存在的数据**每次请求都要到数据库去查询**，失去了缓存的意义。

- 由于请求的参数是不合法的(每次都请求不存在的参数)，于是我们可以使用布隆过滤器(BloomFilter)或者压缩filter**提前拦截**，不合法就不让这个请求到数据库层！

- 当我们从数据库找不到的时候，我们也将这个**空对象设置到缓存里边去**。下次再请求的时候，就可以从缓存里边获取了。

- - 这种情况我们一般会将空对象设置一个**较短的过期时间**。

## 10 缓存与数据库双写一致问题

读操作：

- 如果我们的数据在缓存里边有，那么就直接取缓存的。
- 如果缓存里没有我们想要的数据，我们会先去查询数据库，然后**将数据库查出来的数据写到缓存中**。
- 最后将数据返回给请求

因为可能存在更新操作，所以可能会造成数据不一致的问题，我们可以设置键的过期时间。

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020140417.png)

# 参考

[3y redis](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484386&idx=1&sn=323ddc84dc851a975530090fcd6e2326&chksm=ebd742e3dca0cbf52bc65d430447e639d81cc13e0ac34613edf464dae3950b10e2e1df74dcc5&token=620000779&lang=zh_CN&scene=21###wechat_redirect)

