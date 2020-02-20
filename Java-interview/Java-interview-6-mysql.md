> 前排引用说明及好文推荐：[面试/笔试第三弹 —— 数据库面试问题集锦](https://blog.csdn.net/justloveyou_/article/details/78308460)、[数据库常见面试题(开发者篇)](https://segmentfault.com/a/1190000013517914)

### 1）什么是存储过程？有哪些优缺点？

**存储过程就像是编程语言中的函数一样，封装了我们的代码（PLSQL，T-SQL）**

例如：

```java
-------------创建名为GetUserAccount的存储过程----------------
create Procedure GetUserAccount
as
select * from UserAccount
go

-------------执行上面的存储过程----------------
exec GetUserAccount
```

存储过程的优点：

- **能够将代码封装起来**
- **保存在数据库之中**
- **让编程语言进行调用**
- **存储过程是一个预编译的代码块，执行效率比较高**
- **一个存储过程替代大量T_SQL语句 ，可以降低网络通信量，提高通信速率**

存储过程的缺点：

- **每个数据库的存储过程语法几乎都不一样，十分难以维护（不通用）**
- **业务逻辑放在数据库上，难以迭代**

------

### 2）三大范式

> - 思考这样的一个例子：

我们现在需要建立一个描述学校教务的数据库，该数据库涉及的对象包括学生的学号（Sno）、所在系（Sdept）、系主任姓名（Mname）、课程号（Cno）和成绩（Grade），假设我们使用单一的关系模式 Student 来表示，那么根据现实世界已知的信息，会描述成以下这个样子：

![img](https://ws3.sinaimg.cn/large/006tKfTcly1g0ngdidr6dj30sx081aaf.jpg)

但是，这个关系模式存在以下问题：

**（1） 数据冗余**
比如，每一个系的系主任姓名重复出现，重复次数与该系所有学生的所有课程成绩出现次数相同，这将浪费大量的存储空间。
**（2）更新异常（update anomalies）**
由于数据冗余，当更新数据库中的数据时，系统要付出很大的代价来维护数据库的完整性，否则会面临数据不一致的危险。比如，某系更换系主任后，必须修改与该系学生有关的每一个元组。
**（3）插入异常（insertion anomalies）**
如果一个系刚成立，尚无学生，则无法把这个系及其系主任的信息存入数据库。
**（4）删除异常（deletion anomalies）**
如果某个系的学生全部毕业了，则在删除该系学生信息的同时，这个系及其系主任的信息也丢失了。

- **总结：** 所以，我们在设计数据库的时候，就需要满足一定的规范要求，而满足不同程度要求的就是不同的范式。

> - **第一范式：** 列不可分

1NF（第一范式）是对属性具有**原子性**的要求，不可再分，例如：

![img](https://ws4.sinaimg.cn/large/006tKfTcly1g0ngfjchxgj30dm023741.jpg)

如果认为最后一列还可以再分成出生年，出生月，出生日，则它就不满足第一范式的要求。

> - **第二范式：** 消除非主属性对码的部分函数依赖

2NF（第二范式）是对记录有**唯一性**的要求，即实体的唯一性，不存在部分依赖，每一列与主键都相关，例如：

![img](https://ws3.sinaimg.cn/large/006tKfTcly1g0nggecu5sj30dl026mwz.jpg)

该表明显说明了两个事物：学生信息和课程信息；正常的依赖应该是：学分依赖课程号，姓名依赖学号，但这里存在非主键字段对码的部分依赖，即与主键不相关，不满足第二范式的要求。

**可能存在的问题：**

- **数据冗余**：每条记录都含有相同信息；
- **删除异常**：删除所有学生成绩，就把课程信息全删除了；
- **插入异常**：学生未选课，无法记录进数据库；
- **更新异常**：调整课程学分，所有行都调整。

**正确的做法：**

![img](https://upload-images.jianshu.io/upload_images/7896890-fba36ca283ffd5fc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

> - **第三范式：** 消除非主属性对码的传递函数依赖

3NF（第三范式）对字段有**冗余性**的要求，任何字段不能由其他字段派生出来，它要求字段没有冗余，即不存在依赖传递，例如：

![img](https://upload-images.jianshu.io/upload_images/7896890-8d7548eb839bc8db.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

很明显，学院电话是一个冗余字段，因为存在依赖传递：（学号）→（学生）→（学院）→（学院电话）

**可能会存在的问题：**

- **数据冗余**：有重复值；
- **更新异常**：有重复的冗余信息，修改时需要同时修改多条记录，否则会出现数据不一致的情况 。

**正确的做法：**

![img](https://upload-images.jianshu.io/upload_images/7896890-83c0288ea9150976.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

------

### 3）数据库索引

> - 什么是索引？

**索引是对数据库表中一个或多个列的值进行排序的数据结构，以协助快速查询、更新数据库表中数据。**

你也可以这样理解：索引就是加快检索表中数据的方法。数据库的索引类似于书籍的索引。在书籍中，索引允许用户不必翻阅完整个书就能迅速地找到所需要的信息。在数据库中，索引也允许数据库程序迅速地找到表中的数据，而不必扫描整个数据库。

> - 底层数据结构是什么，为什么使用这种数据结构？

**（1）底层数据结构是B+树：**
在数据结构中，我们最为常见的搜索结构就是二叉搜索树和AVL树(高度平衡的二叉搜索树，为了提高二叉搜索树的效率，减少树的平均搜索长度)了。然而，无论二叉搜索树还是AVL树，当数据量比较大时，都会由于树的深度过大而造成I/O读写过于频繁，进而导致查询效率低下，因此对于索引而言，多叉树结构成为不二选择。特别地，B-Tree的各种操作能使B树保持较低的高度，从而保证高效的查找效率。

**（2）使用B+树的原因：**
查找速度快、效率高，在查找的过程中，每次都能抛弃掉一部分节点，减少遍历个数。（此时，你应该在白纸上画出什么是B+树）

> - 索引的分类？

- **唯一索引**：唯一索引不允许两行具有相同的索引值
- **主键索引**：为表定义一个主键将自动创建主键索引，主键索引是唯一索引的特殊类型。主键索引要求主键中的每个值是唯一的，并且不能为空
- **聚集索引(Clustered)**：表中各行的物理顺序与键值的逻辑（索引）顺序相同，每个表只能有一个
- **非聚集索引(Non-clustered)**：非聚集索引指定表的逻辑顺序。数据存储在一个位置，索引存储在另一个位置，索引中包含指向数据存储位置的指针。可以有多个，小于249个

> - 索引的优缺点？

**（1）优点：**

- **大大加快数据的检索速度**，这也是创建索引的最主要的原因；
- 加速表和表之间的连接；
- 在使用分组和排序子句进行数据检索时，同样可以显著减少查询中分组和排序的时间；
- 通过创建唯一性索引，可以保证数据库表中每一行数据的唯一性；

**（2）缺点：**

- 时间方面：创建索引和维护索引要耗费时间，具体地，当对表中的数据进行增加、删除和修改的时候，索引也要动态的维护，这样就降低了数据的维护速度；
- 空间方面：索引需要占物理空间。

> - 什么样的字段适合创建索引？

- 经常作查询选择的字段
- 经常作表连接的字段
- 经常出现在order by, group by, distinct 后面的字段

> - 创建索引时需要注意什么？

- **非空字段**：应该指定列为NOT NULL，除非你想存储NULL。在mysql中，含有空值的列很难进行查询优化，因为它们使得索引、索引的统计信息以及比较运算更加复杂。你应该用0、一个特殊的值或者一个空串代替空值；
- **取值离散大的字段**：（变量各个取值之间的差异程度）的列放到联合索引的前面，可以通过count()函数查看字段的差异值，返回值越大说明字段的唯一值越多字段的离散程度高；
- **索引字段越小越好**：数据库的数据存储以页为单位一页存储的数据越多一次IO操作获取的数据越大效率越高。

------

### 4）听说过事务吗？（必考）

**事务简单来说：一个 Session 中所进行所有的操作，要么同时成功，要么同时失败**；作为单个逻辑工作单元执行的一系列操作，满足四大特性：

1. 原子性（Atomicity）：事务作为一个整体被执行 ，要么全部执行，要么全部不执行
2. 一致性（Consistency）：保证数据库状态从一个一致状态转变为另一个一致状态
3. 隔离性（Isolation）：多个事务并发执行时，一个事务的执行不应影响其他事务的执行
4. 持久性（Durability）：一个事务一旦提交，对数据库的修改应该永久保存

> - 实例说明：

```
/*
 * 我们来模拟A向B账号转账的场景
 *   A和B账户都有1000块，现在我让A账户向B账号转500块钱
 *
 **/
//JDBC默认的情况下是关闭事务的，下面我们看看关闭事务去操作转账操作有什么问题

//A账户减去500块
String sql = "UPDATE a SET money=money-500 ";
preparedStatement = connection.prepareStatement(sql);
preparedStatement.executeUpdate();

//B账户多了500块
String sql2 = "UPDATE b SET money=money+500";
preparedStatement = connection.prepareStatement(sql2);
preparedStatement.executeUpdate();
```

从上面看，我们的确可以发现A向B转账，成功了。可是如果A向B转账的过程中出现了问题呢？下面模拟一下

```
// A账户减去500块
String sql = "UPDATE a SET money=money-500 ";
preparedStatement = connection.prepareStatement(sql);
preparedStatement.executeUpdate();

// 这里模拟出现问题
int a = 3 / 0;

String sql2 = "UPDATE b SET money=money+500";
preparedStatement = connection.prepareStatement(sql2);
preparedStatement.executeUpdate();
```

显然，上面代码是会抛出异常的，我们再来查询一下数据。A账户少了500块钱，B账户的钱没有增加。这明显是不合理的。

我们可以通过事务来解决上面出现的问题：

```
    // 开启事务,对数据的操作就不会立即生效。
    connection.setAutoCommit(false);

    // A账户减去500块
    String sql = "UPDATE a SET money=money-500 ";
    preparedStatement = connection.prepareStatement(sql);
    preparedStatement.executeUpdate();

    // 在转账过程中出现问题
    int a = 3 / 0;

    // B账户多500块
    String sql2 = "UPDATE b SET money=money+500";
    preparedStatement = connection.prepareStatement(sql2);
    preparedStatement.executeUpdate();

    // 如果程序能执行到这里，没有抛出异常，我们就提交数据
    connection.commit();

    // 关闭事务【自动提交】
    connection.setAutoCommit(true);

} catch(SQLException e) {
    try {
        // 如果出现了异常，就会进到这里来，我们就把事务回滚【将数据变成原来那样】
        connection.rollback();

        // 关闭事务【自动提交】
        connection.setAutoCommit(true);
    } catch (SQLException e1) {
        e1.printStackTrace();
    }
}
```

上面的程序也一样抛出了异常，A账户钱没有减少，B账户的钱也没有增加。

- **注意**：当Connection遇到一个未处理的SQLException时，系统会非正常退出，事务也会自动回滚，但如果程序捕获到了异常，是需要在catch中显式回滚事务的。

------

### 5）事务的并发问题有哪几种？

1. 丢失更新：一个事务的更新覆盖了另一个事务的更新；
2. 脏读：一个事务读取了另一个事务未提交的数据；
3. 不可重复读：不可重复读的重点是修改，同样条件下两次读取结果不同，也就是说，被读取的数据可以被其它事务修改；
4. 幻读：幻读的重点在于新增或者删除，同样条件下两次读出来的记录数不一样。

------

### 6）事务的隔离级别有哪几种？

隔离级别决定了一个session中的事务可能对另一个session中的事务的影响。ANSI标准定义了4个隔离级别，MySQL的InnoDB都支持，分别是：

1. 读未提交（READ UNCOMMITTED）：最低级别的隔离，通常又称为dirty read，它允许一个事务读取另一个事务还没 commit 的数据，这样可能会提高性能，但是会导致脏读问题；
2. 读已提交（READ COMMITTED）：在一个事务中只允许对其它事务已经 commit 的记录可见，该隔离级别不能避免不可重复读问题；
3. 可重复读（REPEATABLE READ）：在一个事务开始后，其他事务对数据库的修改在本事务中不可见，直到本事务 commit 或 rollback。但是，其他事务的 insert/delete 操作对该事务是可见的，也就是说，该隔离级别并不能避免幻读问题。在一个事务中重复 select 的结果一样，除非本事务中 update 数据库。
4. 序列化（SERIALIZABLE）：最高级别的隔离，只允许事务串行执行。

**MySQL默认的隔离级别是可重复读（REPEATABLE READ）**

> - MySql 的事务支持

MySQL的事务支持不是绑定在MySQL服务器本身，而是与存储引擎相关：

- MyISAM：不支持事务，用于只读程序提高性能；
- InnoDB：支持ACID事务、行级锁、并发；
- Berkeley DB：支持事务。

------

### 7）什么是视图？以及视图的使用场景有哪些？

视图是一种虚拟的表，具有和物理表相同的功能。可以对视图进行增，改，查，操作，试图通常是有一个表或者多个表的行或列的子集。对视图的修改不影响基本表。它使得我们获取数据更容易，相比多表查询。

如下两种场景一般会使用到视图：

1. 不希望访问者获取整个表的信息，只暴露部分字段给访问者，所以就建一个虚表，就是视图。
2. 查询的数据来源于不同的表，而查询者希望以统一的方式查询，这样也可以建立一个视图，把多个表查询结果联合起来，查询者只需要直接从视图中获取数据，不必考虑数据来源于不同表所带来的差异。

**注意**：这个视图是在数据库中创建的 而不是用代码创建的。

------

### 8）drop,delete与truncate的区别？

drop 直接删除表；truncate 删除表中数据，再插入时自增长id又从1开始 ；delete 删除表中数据，可以加where字句。

> - **drop table：**

- 属于DDL（Data Definition Language，数据库定义语言）
- 不可回滚
- 不可带 where
- 表内容和结构删除
- 删除速度快

> - **truncate table：**

- 属于DDL（Data Definition Language，数据库定义语言）
- 不可回滚
- 不可带 where
- 表内容删除
- 删除速度快

> - **delete from：**

- 属于DML
- 可回滚
- 可带where
- 表结构在，表内容要看where执行的情况
- 删除速度慢,需要逐行删除

> - **使用简要说明：**

- 不再需要一张表的时候，用drop
- 想删除部分数据行时候，用delete，并且带上where子句
- 保留表而删除所有数据的时候用truncate

------

### 9）触发器的作用？

触发器是与表相关的数据库对象，在满足定义条件时触发，并执行触发器中定义的语句集合。触发器的这种特性可以协助应用在数据库端确保数据库的完整性。

------

### 10）数据库的乐观锁和悲观锁是什么？

数据库管理系统（DBMS）中的并发控制的任务是确保在多个事务同时存取数据库中同一数据时不破坏事务的隔离性和统一性以及数据库的统一性。

乐观并发控制(乐观锁)和悲观并发控制（悲观锁）是并发控制主要采用的技术手段。

> - 悲观锁：假定会发生并发冲突，屏蔽一切可能违反数据完整性的操作

悲观锁是一种利用数据库内部机制提供的锁的方式，也就是对更新的数据加锁，这样在并发期间一旦有一个事务持有了数据库记录的锁，其他的线程将不能再对数据进行更新了，这就是悲观锁的实现方式。

**MySQL InnoDB中使用悲观锁：**

要使用悲观锁，我们必须关闭mysql数据库的自动提交属性，因为MySQL默认使用autocommit模式，也就是说，当你执行一个更新操作后，MySQL会立刻将结果进行提交。 set autocommit=0;

```
//0.开始事务
begin;/begin work;/start transaction; (三者选一就可以)
//1.查询出商品信息
select status from t_goods where id=1 for update;
//2.根据商品信息生成订单
insert into t_orders (id,goods_id) values (null,1);
//3.修改商品status为2
update t_goods set status=2;
//4.提交事务
commit;/commit work;
```

上面的查询语句中，我们使用了 `select…for update` 的方式，这样就通过开启排他锁的方式实现了悲观锁。此时在t_goods表中，id为1的 那条数据就被我们锁定了，其它的事务必须等本次事务提交之后才能执行。这样我们可以保证当前的数据不会被其它事务修改。

上面我们提到，使用 `select…for update` 会把数据给锁住，不过我们需要注意一些锁的级别，MySQL InnoDB默认行级锁。行级锁都是基于索引的，如果一条SQL语句用不到索引是不会使用行级锁的，会使用表级锁把整张表锁住，这点需要注意。

**优点与不足：**

悲观并发控制实际上是“先取锁再访问”的保守策略，为数据处理的安全提供了保证。但是在效率方面，处理加锁的机制会让数据库产生额外的开销，还有增加产生死锁的机会；另外，在只读型事务处理中由于不会产生冲突，也没必要使用锁，这样做只能增加系统负载；还有会降低了并行性，一个事务如果锁定了某行数据，其他事务就必须等待该事务处理完才可以处理那行数

> - 乐观锁：假设不会发生并发冲突，只在提交操作时检查是否违反数据完整性。

乐观锁是一种不会阻塞其他线程并发的控制，它不会使用数据库的锁进行实现，它的设计里面由于不阻塞其他线程，所以并不会引起线程频繁挂起和恢复，这样便能够提高并发能力，所以也有人把它称为非阻塞锁。一般的实现乐观锁的方式就是记录数据版本。

数据版本,为数据增加的一个版本标识。当读取数据时，将版本标识的值一同读出，数据每更新一次，同时对版本标识进行更新。当我们提交更新的时候，判断数据库表对应记录的当前版本信息与第一次取出来的版本标识进行比对，如果数据库表当前版本号与第一次取出来的版本标识值相等，则予以更新，否则认为是过期数据。

实现数据版本有两种方式，第一种是使用版本号，第二种是使用时间戳。

**使用版本号实现乐观锁：**

使用版本号时，可以在数据初始化时指定一个版本号，每次对数据的更新操作都对版本号执行+1操作。并判断当前版本号是不是该数据的最新的版本号。

```
1.查询出商品信息
select (status,status,version) from t_goods where id=#{id}
2.根据商品信息生成订单
3.修改商品status为2
update t_goods 
set status=2,version=version+1
where id=#{id} and version=#{version};
```

**优点与不足：**

乐观并发控制相信事务之间的数据竞争(data race)的概率是比较小的，因此尽可能直接做下去，直到提交的时候才去锁定，所以不会产生任何锁和死锁。但如果直接简单这么做，还是有可能会遇到不可预期的结果，例如两个事务都读取了数据库的某一行，经过修改以后写回数据库，这时就遇到了问题。

> 参考文章：[深入理解乐观锁与悲观锁](http://www.open-open.com/lib/view/open1452046967245.html)

------

### 11）超键、候选键、主键、外键分别是什么？

- 超键：在关系中能唯一标识元组的属性集称为关系模式的超键。一个属性可以为作为一个超键，多个属性组合在一起也可以作为一个超键。超键包含候选键和主键。
- 候选键（候选码）：是最小超键，即没有冗余元素的超键。
- 主键（主码）：数据库表中对储存数据对象予以唯一和完整标识的数据列或属性的组合。一个数据列只能有一个主键，且主键的取值不能缺失，即不能为空值（Null）。
- 外键：在一个表中存在的另一个表的主键称此表的外键。

**候选码和主码：**

例子：邮寄地址（城市名，街道名，邮政编码，单位名，收件人）

- **它有两个候选键:{城市名，街道名} 和 {街道名，邮政编码}**
- **如果我选取{城市名，街道名}作为唯一标识实体的属性，那么{城市名，街道名} 就是主码(主键)**

------

### 12）SQL 约束有哪几种？

- NOT NULL: 用于控制字段的内容一定不能为空（NULL）。
- UNIQUE: 控件字段内容不能重复，一个表允许有多个 Unique 约束。
- PRIMARY KEY: 也是用于控件字段内容不能重复，但它在一个表只允许出现一个。
- FOREIGN KEY: 用于预防破坏表之间连接的动作，也能防止非法数据插入外键列，因为它必须是它指向的那个表中的值之一。
- CHECK: 用于控制字段的值范围。

------

### 13）MySQL存储引擎中的MyISAM和InnoDB区别详解

在MySQL 5.5之前，MyISAM是mysql的默认数据库引擎，其由早期的ISAM（Indexed Sequential Access Method：有索引的顺序访问方法）所改良。虽然MyISAM性能极佳，但却有一个显著的缺点： **不支持事务处理**。不过，MySQL也导入了另一种数据库引擎InnoDB，以强化参考完整性与并发违规处理机制，后来就逐渐取代MyISAM。

InnoDB是MySQL的数据库引擎之一，其由Innobase oy公司所开发，2006年五月由甲骨文公司并购。与传统的ISAM、MyISAM相比，**InnoDB的最大特色就是支持ACID兼容的事务功能**，类似于PostgreSQL。目前InnoDB采用双轨制授权，一是GPL授权，另一是专有软件授权。具体地，MyISAM与InnoDB作为MySQL的两大存储引擎的差异主要包括：

- **存储结构**：每个MyISAM在磁盘上存储成三个文件：第一个文件的名字以表的名字开始，扩展名指出文件类型。.frm文件存储表定义，数据文件的扩展名为.MYD (MYData)，索引文件的扩展名是.MYI (MYIndex)。InnoDB所有的表都保存在同一个数据文件中（也可能是多个文件，或者是独立的表空间文件），InnoDB表的大小只受限于操作系统文件的大小，一般为2GB。
- **存储空间**：MyISAM可被压缩，占据的存储空间较小，支持静态表、动态表、压缩表三种不同的存储格式。InnoDB需要更多的内存和存储，它会在主内存中建立其专用的缓冲池用于高速缓冲数据和索引。
- **可移植性、备份及恢复**：MyISAM的数据是以文件的形式存储，所以在跨平台的数据转移中会很方便，同时在备份和恢复时也可单独针对某个表进行操作。InnoDB免费的方案可以是拷贝数据文件、备份 binlog，或者用 mysqldump，在数据量达到几十G的时候就相对痛苦了。
- **事务支持**：MyISAM强调的是性能，每次查询具有原子性，其执行数度比InnoDB类型更快，但是不提供事务支持。InnoDB提供事务、外键等高级数据库功能，具有事务提交、回滚和崩溃修复能力。
- **AUTO_INCREMENT**：在MyISAM中，可以和其他字段一起建立联合索引。引擎的自动增长列必须是索引，如果是组合索引，自动增长可以不是第一列，它可以根据前面几列进行排序后递增。InnoDB中必须包含只有该字段的索引，并且引擎的自动增长列必须是索引，如果是组合索引也必须是组合索引的第一列。
- **表锁差异**：MyISAM只支持表级锁，用户在操作MyISAM表时，select、update、delete和insert语句都会给表自动加锁，如果加锁以后的表满足insert并发的情况下，可以在表的尾部插入新的数据。InnoDB支持事务和行级锁。行锁大幅度提高了多用户并发操作的新能，但是InnoDB的行锁，只是在WHERE的主键是有效的，非主键的WHERE都会锁全表的。
- **全文索引**：MyISAM支持 FULLTEXT类型的全文索引；InnoDB不支持FULLTEXT类型的全文索引，但是innodb可以使用sphinx插件支持全文索引，并且效果更好。
- **表主键**：MyISAM允许没有任何索引和主键的表存在，索引都是保存行的地址。对于InnoDB，如果没有设定主键或者非空唯一索引，就会自动生成一个6字节的主键(用户不可见)，数据是主索引的一部分，附加索引保存的是主索引的值。
- **表的具体行数**：MyISAM保存表的总行数，select count() from table;会直接取出出该值；而InnoDB没有保存表的总行数，如果使用select count() from table；就会遍历整个表，消耗相当大，但是在加了wehre条件后，myisam和innodb处理的方式都一样。
- **CURD操作**：在MyISAM中，如果执行大量的SELECT，MyISAM是更好的选择。对于InnoDB，如果你的数据执行大量的INSERT或UPDATE，出于性能方面的考虑，应该使用InnoDB表。DELETE从性能上InnoDB更优，但DELETE FROM table时，InnoDB不会重新建立表，而是一行一行的删除，在innodb上如果要清空保存有大量数据的表，最好使用truncate table这个命令。
- **外键**：MyISAM不支持外键，而InnoDB支持外键。

通过上述的分析，基本上可以考虑使用InnoDB来替代MyISAM引擎了，原因是InnoDB自身很多良好的特点，比如事务支持、存储过程、视图、行级锁、外键等等。尤其在并发很多的情况下，相信InnoDB的表现肯定要比MyISAM强很多。另外，必须需要注意的是，任何一种表都不是万能的，合适的才是最好的，才能最大的发挥MySQL的性能优势。如果是不复杂的、非关键的Web应用，还是可以继续考虑MyISAM的，这个具体情况具体考虑。

------

### 14）MyIASM和Innodb两种引擎所使用的索引的数据结构是什么？

答案:都是B+树!

MyIASM引擎，B+树的数据结构中存储的内容实际上是实际数据的地址值。也就是说它的索引和实际数据是分开的，**只不过使用索引指向了实际数据。这种索引的模式被称为非聚集索引。**

Innodb引擎的索引的数据结构也是B+树，**只不过数据结构中存储的都是实际的数据，这种索引有被称为聚集索引。**

------

### 15）varchar和char的区别

char是一种固定长度的类型，varchar是一种可变长度的类型，例如：

定义一个char[10]和varchar[10]，如果存进去的是 'test'，那么char所占的长度依然为10，除了字符 'test' 外，后面跟六个空格，varchar就立马把长度变为4了，取数据的时候，char类型的要用trim()去掉多余的空格，而varchar是不需要的

char的存取速度还是要比varchar要快得多，因为其长度固定，方便程序的存储于查找

char也为此付出的是空间的代价，因为其长度固定，所以难免会有多余的空格占位符占据空间，可谓是以空间换取时间效率。

varchar是以空间效率为首位。

char的存储方式是：对英文字符（ASCII）占用1个字节，对一个汉字占用两个字节。

varchar的存储方式是：对每个英文字符占用2个字节，汉字也占用2个字节。
两者的存储数据都非unicode的字符数据。

------

### 16）主键、自增主键、主键索引与唯一索引概念区别

1. 主键：指字段 **唯一、不为空值** 的列；
2. 主键索引：指的就是主键，主键是索引的一种，是唯一索引的特殊类型。创建主键的时候，数据库默认会为主键创建一个唯一索引；
3. 自增主键：字段类型为数字、自增、并且是主键；
4. 唯一索引：索引列的值必须唯一，但允许有空值。**主键是唯一索引，这样说没错；但反过来说，唯一索引也是主键就错误了，因为唯一索引允许空值，主键不允许有空值，所以不能说唯一索引也是主键。**

------

### 17）主键就是聚集索引吗？主键和索引有什么区别？

**主键是一种特殊的唯一性索引，其可以是聚集索引，也可以是非聚集索引。**在SQLServer中，主键的创建必须依赖于索引，默认创建的是聚集索引，但也可以显式指定为非聚集索引。InnoDB作为MySQL存储引擎时，默认按照主键进行聚集，如果没有定义主键，InnoDB会试着使用唯一的非空索引来代替。如果没有这种索引，InnoDB就会定义隐藏的主键然后在上面进行聚集。所以，对于聚集索引来说，你创建主键的时候，自动就创建了主键的聚集索引。

------

### 18）实践中如何优化MySQL

实践中，MySQL的优化主要涉及SQL语句及索引的优化、数据表结构的优化、系统配置的优化和硬件的优化四个方面，如下图所示：

![img](https://upload-images.jianshu.io/upload_images/7896890-7a6deca3ebd226e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### ⑴ SQL语句优化：

SQL语句的优化主要包括三个问题，即如何发现有问题的SQL、如何分析SQL的执行计划以及如何优化SQL，下面将逐一解释。

**① 怎么发现有问题的SQL?（通过MySQL慢查询日志对有效率问题的SQL进行监控）**

MySQL的慢查询日志是MySQL提供的一种日志记录，它用来记录在MySQL中响应时间超过阀值的语句，具体指运行时间超过long_query_time值的SQL，则会被记录到慢查询日志中。long_query_time的默认值为10，意思是运行10s以上的语句。慢查询日志的相关参数如下所示：

![img](https://upload-images.jianshu.io/upload_images/7896890-79123c126bb94a31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

通过MySQL的慢查询日志，我们可以查询出执行的次数多占用的时间长的SQL、可以通过pt_query_disgest(一种mysql慢日志分析工具)分析Rows examine(MySQL执行器需要检查的行数)项去找出IO大的SQL以及发现未命中索引的SQL，对于这些SQL，都是我们优化的对象。

**② 通过explain查询和分析SQL的执行计划：**

使用 EXPLAIN 关键字可以知道MySQL是如何处理你的SQL语句的，以便分析查询语句或是表结构的性能瓶颈。通过explain命令可以得到表的读取顺序、数据读取操作的操作类型、哪些索引可以使用、哪些索引被实际使用、表之间的引用以及每张表有多少行被优化器查询等问题。当扩展列extra出现Using filesort和Using temporay，则往往表示SQL需要优化了。

**③ SQL语句的优化：**

**⒈优化insert语句：一次插入多值；**

**⒉应尽量避免在 where 子句中使用!=或<>操作符，否则将引擎放弃使用索引而进行全表扫描；**

**⒊应尽量避免在 where 子句中对字段进行null值判断，否则将导致引擎放弃使用索引而进行全表扫描；**

**⒋优化嵌套查询：子查询可以被更有效率的连接(Join)替代；**

**⒌很多时候用 exists 代替 in 是一个好的选择。**

**⒍选择最有效率的表名顺序：数据库的解析器按照从右到左的顺序处理FROM子句中的表名，FROM子句中写在最后的表将被最先处理**

在FROM子句中包含多个表的情况下：

- 如果三个表是完全无关系的话，将记录和列名最少的表，写在最后，然后依次类推
- 也就是说：选择记录条数最少的表放在最后

如果有3个以上的表连接查询：

- 如果三个表是有关系的话，将引用最多的表，放在最后，然后依次类推。
- 也就是说：被其他表所引用的表放在最后

**⒎用IN代替OR：**

```
select * from emp where sal = 1500 or sal = 3000 or sal = 800;
select * from emp where sal in (1500,3000,800);
```

**⒏SELECT子句中避免使用\*号：**

我们最开始接触 SQL 的时候，“`*`” 号是可以获取表中全部的字段数据的，**但是它要通过查询数据字典完成，这意味着将消耗更多的时间**，而且使用 “`*`” 号写出来的 SQL 语句也不够直观。

------

#### ⑵ 索引优化：

建议在经常作查询选择的字段、经常作表连接的字段以及经常出现在 order by、group by、distinct 后面的字段中建立索引。但必须注意以下几种可能会引起索引失效的情形：

- 以 “%(表示任意0个或多个字符)” 开头的 LIKE 语句，模糊匹配；
- OR语句前后没有同时使用索引；
- 数据类型出现隐式转化（如varchar不加单引号的话可能会自动转换为int型）；
- 对于多列索引，必须满足最左匹配原则(eg,多列索引col1、col2和col3，则 索引生效的情形包括col1或col1，col2或col1，col2，col3)。

------

#### ⑶ 数据库表结构的优化：

**① 选择合适数据类型：**

- 使用较小的数据类型解决问题；
- 使用简单的数据类型(mysql处理int要比varchar容易)；
- 尽可能的使用not null 定义字段；
- 尽量避免使用text类型，非用不可时最好考虑分表；

**② 表的范式的优化：**

一般情况下，表的设计应该遵循三大范式。

**③ 表的垂直拆分：**

把含有多个列的表拆分成多个表，解决表宽度问题，具体包括以下几种拆分手段：

- 把不常用的字段单独放在同一个表中；
- 把大字段独立放入一个表中；
- 把经常使用的字段放在一起；

这样做的好处是非常明显的，具体包括：拆分后业务清晰，拆分规则明确、系统之间整合或扩展容易、数据维护简单

**④ 表的水平拆分：**

表的水平拆分用于解决数据表中数据过大的问题，水平拆分每一个表的结构都是完全一致的。一般地，将数据平分到N张表中的常用方法包括以下两种：

- 对ID进行hash运算，如果要拆分成5个表，mod(id,5)取出0~4个值；
- 针对不同的hashID将数据存入不同的表中；

表的水平拆分会带来一些问题和挑战，包括跨分区表的数据查询、统计及后台报表的操作等问题，但也带来了一些切实的好处：

- 表分割后可以降低在查询时需要读的数据和索引的页数，同时也降低了索引的层数，提高查询速度；
- 表中的数据本来就有独立性，例如表中分别记录各个地区的数据或不同时期的数据，特别是有些数据常用，而另外一些数据不常用。
- 需要把数据存放到多个数据库中，提高系统的总体可用性(分库，鸡蛋不能放在同一个篮子里)。

------

#### ⑷ 系统配置的优化：

- 操作系统配置的优化：增加TCP支持的队列数
- mysql配置文件优化：Innodb缓存池设置(innodb_buffer_pool_size，推荐总内存的75%)和缓存池的个数（innodb_buffer_pool_instances）

------

#### ⑸ 硬件的优化：

- CPU：核心数多并且主频高的
- 内存：增大内存
- 磁盘配置和选择：磁盘性能



### 19）**MySQL 和 MongoDB 的区别有哪些？如何选择？**

### 20）**MongoDB 的优缺点有哪些？**

### 21） **数据库中 Where、group by、having 关键字：**

答：  **关键字作用：**

1. where 子句用来筛选 from 子句中指定的操作所产生的的行；
2. group by 子句用来分组 where 子句的输出；
3. having 子句用来从分组的结果中筛选行；

**having 和 where 的区别：**

1. 语法类似，where 搜索条件在进行分组操作之前应用；having 搜索条件在进行分组操作之后应用；
2. having 可以包含聚合函数 sum、avg、max 等；
3. having 子句限制的是组，而不是行。

当同时含有 where 子句、group by 子句 、having 子句及聚集函数时，执行顺序如下：

1. 执行 where 子句查找符合条件的数据；
2. 使用 group by 子句对数据进行分组；对 group by 子句形成的组运行聚集函数计算每一组的值；最后用 having 子句去掉不符合条件的组。