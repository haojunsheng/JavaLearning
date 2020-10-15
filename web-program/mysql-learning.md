# 数据库基本原理

数据库是怎么执行的：

```sql
# 0表示关闭，1表示开启
select @@profiling;
set profiling=1;
```

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201004204126.png" alt="image-20201004203947601" style="zoom:33%;" />

[sql数据](https://github.com/cystanford/sql_heros_data)

# 数据库基本操作

```mysql
# 创建数据库
create database [if not exists] db_name；
# 修改数据库
alter database db_name;
# 删除数据库
drop database [if exitsts] db_name;
create table <表名>
( 
 列名1 数据类型[列级别约束条件][默认值],
 列名2 数据类型[列级别约束条件][默认值],
 ...
 [表级别约束条件]
);
eg:
CREATE TABLE `player`  (
  `player_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '球员ID',
  `team_id` int(11) NOT NULL COMMENT '球队ID',
  `player_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '球员姓名',
  `height` float(3, 2) NULL DEFAULT NULL COMMENT '球员身高',
  PRIMARY KEY (`player_id`) USING BTREE,
  UNIQUE INDEX `player_name`(`player_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
# 修改表
ALTER TABLE player ADD (age int(11));
ALTER TABLE player RENAME COLUMN age to player_age;
ALTER TABLE player MODIFY (player_age float(3,1));
ALTER TABLE player DROP COLUMN player_age;
# 查询，书写顺序SELECT ... FROM ... WHERE ... GROUP BY ... HAVING ... ORDER BY ...
# 执行顺序，FROM > WHERE > GROUP BY > HAVING > SELECT的字段 > DISTINCT > ORDER BY > LIMIT
# 取别名
select name as n from heros;
# DISTINCT去除重复行,需要放到所有列名的前面,
SELECT DISTINCT attack_range FROM heros;
+--------------+
| attack_range |
+--------------+
| 近战         |
| 远程         |
+--------------+
2 rows in set (0.01 sec)

SELECT DISTINCT attack_range, name FROM heros;
| 近战         | 花木兰       |
| 近战         | 赵云         |
| 近战         | 橘石京       |
+--------------+--------------+
69 rows in set (0.00 sec)

# ORDER BY排序,ASC升序，DESC降序。显示英雄名称及最大生命值，按照最大生命值排序;
mysql> SELECT name, hp_max FROM heros ORDER BY hp_max;
+--------------+--------+
| name         | hp_max |
+--------------+--------+
| 武则天       |   5037 |
| 花木兰       |   5397 |
| 姜子牙       |   5399 |
| 大乔         |   5399 |
| 王昭君       |   5429 |
| 嬴政         |   5471 |
| 李白         |   5483 |
| 周瑜         |   5513 |
| 干将莫邪     |   5583 |
| 甄姬         |   5584 |

# LIMIT约束
mysql> SELECT name, hp_max FROM heros ORDER BY hp_max LIMIT 5;
+-----------+--------+
| name      | hp_max |
+-----------+--------+
| 武则天    |   5037 |
| 花木兰    |   5397 |
| 姜子牙    |   5399 |
| 大乔      |   5399 |
| 王昭君    |   5429 |
+-----------+--------+
5 rows in set (0.00 sec)

# 数据过滤,主要是where子句
## 比较运算符，=, <>/!= , <, >,!< ,BETWEEN,ISNULL(为空)
### 筛选最大生命值大于 6000
SELECT name, hp_max FROM heros WHERE hp_max > 6000;
mysql> SELECT name, hp_max FROM heros WHERE hp_max > 6000;
+--------------+--------+
| name         | hp_max |
+--------------+--------+
| 夏侯惇       |   7350 |
| 钟无艳       |   7000 |
| 张飞         |   8341 |
| 牛魔         |   8476 |
| 吕布         |   7344 |
| 亚瑟         |   8050 |
| 芈月         |   6164 |
41 rows in set (0.00 sec)
## 逻辑运算符,AND,OR,IN,NOT
### 注意：当 WHERE子句中同时出现AND和OR操作符的时候，优先执行AND
### 筛选最大生命值大于 6000，最大法力大于 1700 的英雄，然后按照最大生命值和最大法力值之和从高到低进行排序。
mysql> SELECT name, hp_max,mp_max FROM heros WHERE hp_max > 6000 AND mp_max>1700 ORDER BY (hp_max+mp_max) DESC;
+--------------+--------+--------+
| name         | hp_max | mp_max |
+--------------+--------+--------+
| 廉颇         |   9328 |   1708 |
| 牛魔         |   8476 |   1926 |
| 刘邦         |   8073 |   1940 |
| 东皇太一     |   7669 |   1926 |
| 典韦         |   7516 |   1774 |
| 夏侯惇       |   7350 |   1746 |
+--------------+--------+--------+
23 rows in set (0.01 sec)
## 通配符过滤，LIKE, _匹配一个字符，%匹配一个或者多个字符
mysql> SELECT name FROM heros WHERE name LIKE '%太%';
+--------------+
| name         |
+--------------+
| 东皇太一     |
| 太乙真人     |
+--------------+
2 rows in set (0.00 sec)
# 聚集函数,COUNT总行数，MAX最大值，MIN最小值，SUM求和，AVG平均值
# DISTINCT求不同的值
# 分组，GROUP BY
mysql> SELECT COUNT(*), role_main FROM heros GROUP BY role_main;
+----------+-----------+
| COUNT(*) | role_main |
+----------+-----------+
|       10 | 坦克      |
|       18 | 战士      |
|       19 | 法师      |
|        6 | 辅助      |
|       10 | 射手      |
|        6 | 刺客      |
+----------+-----------+
6 rows in set (0.00 sec)
# HAVING过滤分组，WHERE过滤行
## 按照英雄的主要定位、次要定位进行分组，并且筛选分组中英雄数量大于5的组，最后按照分组中的英雄数量从高到低进行排序
mysql> SELECT COUNT(*) as num, role_main, role_assist FROM heros GROUP BY role_main,role_assist HAVING num > 5 ORDER BY num DESC;
+-----+-----------+-------------+
| num | role_main | role_assist |
+-----+-----------+-------------+
|  12 | 法师      | NULL        |
|   9 | 射手      | NULL        |
|   8 | 战士      | NULL        |
|   6 | 战士      | 坦克        |
+-----+-----------+-------------+
4 rows in set (0.01 sec)
# 子查询
## 关联子查询，非关联子查询
### 非关联子查询，哪个球员的身高最高，最高身高是多少
mysql> SELECT player_name, height FROM player WHERE height = (SELECT max(height) FROM player) ;
+---------------+--------+
| player_name   | height |
+---------------+--------+
| 索恩-马克     |   2.16 |
+---------------+--------+
1 row in set (0.00 sec)
### 关联子查询,查找每个球队中大于平均身高的球员有哪些，并显示他们的球员姓名、身高以及所在球队ID。
### 将 player 表复制成了表 a 和表 b，每次计算的时候，需要将表 a 中的 team_id 传入从句，作为已知值。因为每次表 a 中的 team_id 可能是不同的，所以是关联子查询。
mysql> SELECT player_name, height, team_id FROM player AS a WHERE height > (SELECT avg(height) FROM player AS b WHERE a.team_id = b.team_id);
+------------------------------------+--------+---------+
| player_name                        | height | team_id |
+------------------------------------+--------+---------+
| 安德烈-德拉蒙德                    |   2.11 |    1001 |
| 索恩-马克                          |   2.16 |    1001 |
| 扎扎-帕楚里亚                      |   2.11 |    1001 |
| 乔恩-洛伊尔                        |   2.08 |    1001 |
| 布雷克-格里芬                      |   2.08 |    1001 |
+------------------------------------+--------+---------+
18 rows in set (0.01 sec)
## EXISTS子查询，用来判断条件是否满足
### 看出场过的球员都有哪些，并且显示他们的姓名、球员ID和球队ID
mysql> SELECT player_id, team_id, player_name FROM player WHERE EXISTS (SELECT player_id FROM player_score WHERE player.player_id = player_score.player_id);
+-----------+---------+---------------------------+
| player_id | team_id | player_name               |
+-----------+---------+---------------------------+
|     10001 |    1001 | 韦恩-艾灵顿               |
|     10002 |    1001 | 雷吉-杰克逊               |
|     10003 |    1001 | 安德烈-德拉蒙德           |
+-----------+---------+---------------------------+
19 rows in set (0.00 sec)
## IN子查询
### 出场过的球员都有哪些
mysql> SELECT player_id, team_id, player_name FROM player WHERE player_id IN (SELECT player_id FROM player_score WHERE player.player_id = player_score.player_id);
+-----------+---------+---------------------------+
| player_id | team_id | player_name               |
+-----------+---------+---------------------------+
|     10001 |    1001 | 韦恩-艾灵顿               |
|     10002 |    1001 | 雷吉-杰克逊               |
|     10003 |    1001 | 安德烈-德拉蒙德           |                  |
+-----------+---------+---------------------------+
19 rows in set (0.00 sec)
### IN vs EXISTS,IN是外表和内表进行hash连接，是先执行子查询，EXISTS是对外表进行循环，然后在内表进行查询。因此如果外表数据量大，则用IN，如果外表数据量小，也用EXISTS。IN有一个缺陷是不能判断NULL，因此如果字段存在NULL值，则会出现返回，因为最好使用NOT EXISTS。
### SELECT * FROM A WHERE cc IN (SELECT cc FROM B)
### SELECT * FROM A WHERE EXIST (SELECT cc FROM B WHERE B.cc=A.cc)

# 连接
## 笛卡尔积,交叉连接，英文是 CROSS JOIN
mysql> SELECT * FROM player, team;
+-----------+---------+------------------------------------+--------+---------+-----------------------+
| player_id | team_id | player_name                        | height | team_id | team_name             |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 |    1001 | 底特律活塞            |
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 |    1002 | 印第安纳步行者        |
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 |    1003 | 亚特兰大老鹰          |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 |    1001 | 底特律活塞            |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
111 rows in set (0.01 sec)
## 等值连接,用两张表中都存在的列进行连接
mysql> SELECT player_id, player.team_id, player_name, height, team_name FROM player, team WHERE player.team_id = team.team_id;
+-----------+---------+------------------------------------+--------+-----------------------+
| player_id | team_id | player_name                        | height | team_name             |
+-----------+---------+------------------------------------+--------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 | 底特律活塞            |
|     10003 |    1001 | 安德烈-德拉蒙德                    |   2.11 | 底特律活塞            |
+-----------+---------+------------------------------------+--------+-----------------------+
37 rows in set (0.01 sec)
## 非等值连接
## 左外连接
mysql> SELECT * FROM player LEFT JOIN team on player.team_id = team.team_id;
+-----------+---------+------------------------------------+--------+---------+-----------------------+
| player_id | team_id | player_name                        | height | team_id | team_name             |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 |    1001 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 |    1001 | 底特律活塞            |
|     10003 |    1001 | 安德烈-德拉蒙德                    |   2.11 |    1001 | 底特律活塞            |
|     10004 |    1001 | 索恩-马克                          |   2.16 |    1001 | 底特律活塞            |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
37 rows in set (0.00 sec)
## 右外连接
mysql> SELECT * FROM player RIGHT JOIN team on player.team_id = team.team_id;
+-----------+---------+------------------------------------+--------+---------+-----------------------+
| player_id | team_id | player_name                        | height | team_id | team_name             |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 |    1001 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 |    1001 | 底特律活塞            |
|     10003 |    1001 | 安德烈-德拉蒙德                    |   2.11 |    1001 | 底特律活塞            |
|      NULL |    NULL | NULL                               |   NULL |    1003 | 亚特兰大老鹰          |
+-----------+---------+------------------------------------+--------+---------+-----------------------+
38 rows in set (0.00 sec)
```

常见约束：

1. 主键约束；作用是唯一标识一条记录，不能重复，不能为空， 即 UNIQUE+NOT NULL。一个数据表的主键只能有一个。 主键可以是一个字段，也可以由多个字段复合组成；
2. 外键约束：外键确保了表与表之间引用的完整性。一个表中的外键对应 另一张表的主键。外键可以是重复的，也可以为空；
3. 唯一性约束：唯一性约束表明了字段在表中的数值是唯一的；
4. NOT NULL 约束。对字段定义了 NOT NULL，即表明该字 段不应为空，必须有取值；
5. DEFAULT，表明了字段的默认值。如果在插入数据的时 候，这个字段没有取值，就设置为默认值；
6. CHECK 约束，用来检查特定字段取值范围的有效性；



SELECT 语句的执行顺序

```mysql
# 书写顺序
SELECT ... FROM ... WHERE ... GROUP BY ... HAVING ... ORDER BY ...
# 执行顺序
FROM子句组装数据(包括通过ON进行连接) > WHERE子句进行条件筛选 > GROUP BY分组 > 使用聚集函数进行计算 > HAVING筛选分组 > 计算所有的表达式 > SELECT的字段 > DISTINCT > ORDER BY排序 > LIMIT筛选

SELECT DISTINCT player_id, player_name, count(*) as num #顺序5
FROM player JOIN team ON player.team_id = team.team_id #顺序1
WHERE height > 1.80 #顺序2
GROUP BY player.team_id #顺序3
HAVING num > 2 #顺序4
ORDER BY num DESC #顺序6
LIMIT 2 #顺序7
注意，count(*)是在HAVING之前计算的
```

如果是多表查询，则：

1. 首先先通过 CROSS JOIN 求笛卡尔积，相当于得到虚拟 表 vt(virtual table)1-1;
2. 通过 ON 进行筛选，在虚拟表 vt1-1 的基础上进行筛 选，得到虚拟表 vt1-2；
3. 添加外部行。如果我们使用的是左连接、右链接或者全连 接，就会涉及到外部行，也就是在虚拟表 vt1-2 的基础上 增加外部行，得到虚拟表 vt1-3。



# SQL中重要概念

## SQL函数

算术函数：ABS取绝对值，MOD取余；ROUND四舍五入，如ROUND(37.25,1)=37.3；

字符串函数：CONCAT字符串拼接，LENGTH长度（一个汉字长度为3），CHAR_LENGTH长度（汉字也算一个长度），LOWER转小写，UPPER转大写，REPLACE替换，SUBSTRING截取；

日期函数：CURRENT_DATE当前日期，如2019-04-03；CURRENT_TIME当前时间，如21:26:34；CURRENT_TIMESTAMP，如2019-04-03 21:26:34；EXTRACT(YEAR FROM '2019-04-03')，运行结果为 2019；DATE('2019-04-01 12:00:05')，运行结果为 2019-04-01；

转换函数：CAST数据类型转换。

## 事务

```mysql
# 查看支持引擎
mysql> SHOW ENGINES;
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| Engine             | Support | Comment                                                        | Transactions | XA   | Savepoints |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
| ARCHIVE            | YES     | Archive storage engine                                         | NO           | NO   | NO         |
| BLACKHOLE          | YES     | /dev/null storage engine (anything you write to it disappears) | NO           | NO   | NO         |
| MRG_MYISAM         | YES     | Collection of identical MyISAM tables                          | NO           | NO   | NO         |
| FEDERATED          | NO      | Federated MySQL storage engine                                 | NULL         | NULL | NULL       |
| MyISAM             | YES     | MyISAM storage engine                                          | NO           | NO   | NO         |
| PERFORMANCE_SCHEMA | YES     | Performance Schema                                             | NO           | NO   | NO         |
| InnoDB             | DEFAULT | Supports transactions, row-level locking, and foreign keys     | YES          | YES  | YES        |
| MEMORY             | YES     | Hash based, stored in memory, useful for temporary tables      | NO           | NO   | NO         |
| CSV                | YES     | CSV storage engine                                             | NO           | NO   | NO         |
+--------------------+---------+----------------------------------------------------------------+--------------+------+------------+
9 rows in set (0.00 sec)
# mysql的事务默认隐式的提交
# START TRANSACTION 或者 BEGIN，作用是显式开启一个事务。
# COMMIT：提交事务。当提交事务后，对数据库的修改是永久性的。
# ROLLBACK 或者 ROLLBACK TO [SAVEPOINT]，意为回滚事务。意思是撤销正在进行的所有没有提交的修改，或者将事务回滚到某个保存点。
# SAVEPOINT：在事务中创建保存点，方便后续针对保存点进行回滚。一个事务中可以存在多个保存点。
# RELEASE SAVEPOINT：删除某个保存点。
# SET TRANSACTION，设置事务的隔离级别。
# set autocommit =0; //关闭自动提交
# set autocommit =1; //开启自动提交

# 事务的隔离级别
## 查看默认隔离级别
mysql> SHOW VARIABLES LIKE 'transaction_isolation';
+-----------------------+-----------------+
| Variable_name         | Value           |
+-----------------------+-----------------+
| transaction_isolation | REPEATABLE-READ |
+-----------------------+-----------------+
1 row in set (0.03 sec)
## 修改默认级别为读未提交
mysql> SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
Query OK, 0 rows affected (0.00 sec)
## 关闭默认提交
mysql> SET autocommit = 0;
Query OK, 0 rows affected (0.00 sec)

## 准备数据
mysql> select * from heros_temp;
+----+--------+
| id | name   |
+----+--------+
|  1 | 张飞   |
|  2 | 关羽   |
|  3 | 刘备   |
+----+--------+
3 rows in set (0.00 sec)
## 模拟脏读
### A用户
mysql> BEGIN;
Query OK, 0 rows affected (0.00 sec)

mysql> INSERT INTO heros_temp values(4, '吕布');
Query OK, 1 row affected (0.00 sec)
### B用户，读到了A还没提交的数据
mysql> SELECT * FROM heros_temp;
+----+--------+
| id | name   |
+----+--------+
|  1 | 张飞   |
|  2 | 关羽   |
|  3 | 刘备   |
|  4 | 吕布   |
+----+--------+
4 rows in set (0.01 sec)
mysql> BEGIN;
Query OK, 0 rows affected (0.01 sec)
## 模拟不可重复读
### A用户
mysql> SELECT name FROM heros_temp WHERE id = 1;
+--------+
| name   |
+--------+
| 张飞   |
+--------+
1 row in set (0.00 sec)
### B用户
mysql> BEGIN;
Query OK, 0 rows affected (0.00 sec)
mysql> UPDATE heros_temp SET name = '张翼德' WHERE id = 1;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql> SELECT name FROM heros_temp WHERE id = 1;
+-----------+
| name      |
+-----------+
| 张翼德    |
+-----------+
1 row in set (0.00 sec)
## 模拟幻读
```

# 实战

## Python操作Mysql

![img](https://static001.geekbang.org/resource/image/5d/7f/5d8113fc1637d1fe951e985b22e0287f.png)

```python
# -*- coding: UTF-8 -*-
import mysql.connector
# 打开数据库连接
db = mysql.connector.connect(
       host="localhost",
       user="root",
       passwd="XXX", # 写上你的数据库密码
       database='wucai', 
       auth_plugin='mysql_native_password'
)
# 获取操作游标 
cursor = db.cursor()
# 执行SQL语句
cursor.execute("SELECT VERSION()")
# 获取一条数据
data = cursor.fetchone()
print("MySQL版本: %s " % data)
# 关闭游标&数据库连接
cursor.close()
db.close()
```

Connection 就是对数据库的当前连接进行管理，我们可以通过它来进行以下操作：

通过指定 host、user、passwd 和 port 等参数来创建数据库连接，这些参数分别对应着数据库 IP 地址、用户名、密码和端口号；

使用 db.close() 关闭数据库连接；使用 db.cursor() 创建游标，操作数据库中的数据；

使用 db.begin() 开启事务；

使用 db.commit() 和 db.rollback()，对事务进行提交以及回滚。

当我们通过cursor = db.cursor()创建游标后，就可以通过面向过程的编程方式对数据库中的数据进行操作：

使用cursor.execute(query_sql)，执行数据库查询；

使用cursor.fetchone()，读取数据集中的一条数据；

使用cursor.fetchall()，取出数据集中的所有行，返回一个元组 tuples 类型；

使用cursor.fetchmany(n)，取出数据集中的多条数据，同样返回一个元组 tuples；

使用cursor.rowcount，返回查询结果集中的行数。如果没有查询到数据或者还没有查询，则结果为 -1，否则会返回查询得到的数据行数；

使用cursor.close()，关闭游标。

```Python
# 增加数据
## 插入新球员，不论插入的数值为整数类型，还是浮点类型，都需要统一用（%s）进行占位。
sql = "INSERT INTO player (team_id, player_name, height) VALUES (%s, %s, %s)"
val = (1003, "约翰-科林斯", 2.08)
cursor.execute(sql, val)
db.commit()
print(cursor.rowcount, "记录插入成功。")

# 查询
## 查询身高大于等于2.08的球员
sql = 'SELECT player_id, player_name, height FROM player WHERE height>=2.08'
cursor.execute(sql)
data = cursor.fetchall()
for each_player in data:
  print(each_player)

# 修改
## 修改球员约翰-科林斯
sql = 'UPDATE player SET height = %s WHERE player_name = %s'
val = (2.09, "约翰-科林斯")
cursor.execute(sql, val)
db.commit()
print(cursor.rowcount, "记录被修改。")

# 删除
## 删除约翰·科林斯这个球员的数据
sql = 'DELETE FROM player WHERE player_name = %s'
val = ("约翰-科林斯")
cursor.execute(sql, val)
db.commit()
print(cursor.rowcount, "记录删除成功。")

# 关闭连接
cursor.close()
db.close()

# 异常捕获
import traceback
try:
  sql = "INSERT INTO player (team_id, player_name, height) VALUES (%s, %s, %s)"
  val = (1003, "约翰-科林斯", 2.08)
  cursor.execute(sql, val)
  db.commit()
  print(cursor.rowcount, "记录插入成功。")
except Exception as e:
  # 打印异常信息
  traceback.print_exc()
  # 回滚  
  db.rollback()
finally:
  # 关闭数据库连接
  db.close()
```

### Python ORM框架SQLAlchemy

```python
# pip install sqlalchemy
# 初始化数据库连接
from sqlalchemy import create_engine
# 初始化数据库连接，修改为你的数据库用户名和密码
engine = create_engine('mysql+mysqlconnector://root:password@localhost:3306/wucai')

# 创建模型

# 定义Player对象:
class Player(Base):
    # 表的名字:
    __tablename__ = 'player'
 
    # 表的结构:
    player_id = Column(Integer, primary_key=True, autoincrement=True)
    team_id = Column(Integer)
    player_name = Column(String(255))
    height = Column(Float(3,2))

# 增加数据
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)
# 创建session对象:
session = DBSession()
# 创建Player对象:
new_player = Player(team_id = 1003, player_name = "约翰-科林斯", height = 2.08)
# 添加到session:
session.add(new_player)
# 提交即保存到数据库:
session.commit()
# 关闭session:
session.close()

# 查询数据
#增加to_dict()方法到Base类中
def to_dict(self):
    return {c.name: getattr(self, c.name, None)
            for c in self.__table__.columns}
#将对象可以转化为dict类型
Base.to_dict = to_dict
# 查询身高>=2.08的球员有哪些
rows = session.query(Player).filter(Player.height >= 2.08).all()
print([row.to_dict() for row in rows])

```

![img](https://static001.geekbang.org/resource/image/d6/42/d6f02460647f34fba692e8a61b80a042.png)

![img](https://static001.geekbang.org/resource/image/45/dd/458d77c980f2ac7b9e8e34dd75eac8dd.png)