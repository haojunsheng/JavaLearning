<!--ts-->
   * [SQL简介](#sql简介)
   * [SQL基本操作](#sql基本操作)
      * [创建表](#创建表)
      * [检索数据](#检索数据)
         * [DISTINCT去重](#distinct去重)
         * [ORDER BY排序](#order-by排序)
         * [LIMIT约束返回结果的数量](#limit约束返回结果的数量)
         * [WHERE 过滤](#where-过滤)
         * [常见函数](#常见函数)
         * [聚集函数](#聚集函数)
         * [GROUP BY过滤分组](#group-by过滤分组)
         * [HAVING过滤分组](#having过滤分组)
         * [子查询](#子查询)
         * [连接](#连接)
      * [视图](#视图)
      * [存储过程](#存储过程)
      * [事务](#事务)
   * [Mysql调优](#mysql调优)
      * [数据库范式](#数据库范式)
      * [索引](#索引)
      * [定位sql为什么执行慢](#定位sql为什么执行慢)

<!-- Added by: anapodoton, at: 2020年11月 9日 星期一 21时20分54秒 CST -->

<!--te-->

# SQL简介

- DDL,Data Definition Language，也就是数据定义语言，用来定义我们的数据库对象，包括数据库、数据表和列。通过使用 DDL，我们可以创建，删除和修改数据库和表结构。
- DML，Data Manipulation Language，数据操作语言，我们用它操作和数据库相关的记录，比如增加、删除、修改数据表中的记录。
- DCL，英文叫做 Data Control Language，数据控制语言，我们用它来定义访问权限和 安全级别。
- DQL，英文叫做 Data Query Language，数据查询语言，我们用它查询想要的记录，它 是 SQL 语言的重中之重。

SQL大小写的问题：

- 表名、表别名、字段名、字段别名等都小写;
- SQL 保留字、函数名、绑定变量等都大写。

分析资源使用情况：

```mysql
mysql> select @@profiling;
+-------------+
| @@profiling |
+-------------+
|           0 |
+-------------+
1 row in set, 1 warning (0.00 sec)
mysql> set profiling=1;
mysql> show profiles;
+----------+------------+---------------------+
| Query_ID | Duration   | Query               |
+----------+------------+---------------------+
|        1 | 0.15606300 | show databases      |
|        2 | 0.00195600 | SELECT DATABASE()   |
|        3 | 0.00085400 | show databases      |
|        4 | 0.01973200 | show tables         |
|        5 | 0.00009400 | show database       |
|        6 | 0.00224900 | show databases      |
|        7 | 0.00167200 | select * from hero  |
|        8 | 0.00423300 | show tables         |
|        9 | 0.00416700 | select * from heros |
|       10 | 0.00020200 | set profiling=1     |
|       11 | 0.00103700 | select * from heros |
+----------+------------+---------------------+
11 rows in set, 1 warning (0.01 sec)
# 查询上一次执行的详细信息
mysql> show profile;
+--------------------------------+----------+
| Status                         | Duration |
+--------------------------------+----------+
| starting                       | 0.000093 |
| Executing hook on transaction  | 0.000009 |
| starting                       | 0.000012 |
| checking permissions           | 0.000009 |
| Opening tables                 | 0.000084 |
| init                           | 0.000011 |
| System lock                    | 0.000014 |
| optimizing                     | 0.000008 |
| statistics                     | 0.000023 |
| preparing                      | 0.000026 |
| executing                      | 0.000645 |
| end                            | 0.000015 |
| query end                      | 0.000006 |
| waiting for handler commit     | 0.000010 |
| closing tables                 | 0.000012 |
| freeing items                  | 0.000042 |
| cleaning up                    | 0.000018 |
+--------------------------------+----------+
17 rows in set, 1 warning (0.01 sec)

mysql> show profile for query 3;
+----------------------------+----------+
| Status                     | Duration |
+----------------------------+----------+
| starting                   | 0.000060 |
| checking permissions       | 0.000010 |
| Opening tables             | 0.000173 |
| checking permissions       | 0.000007 |
| checking permissions       | 0.000003 |
| checking permissions       | 0.000004 |
| checking permissions       | 0.000004 |
| checking permissions       | 0.000063 |
| init                       | 0.000069 |
| checking permissions       | 0.000005 |
| checking permissions       | 0.000015 |
| checking permissions       | 0.000003 |
| checking permissions       | 0.000014 |
| System lock                | 0.000009 |
| optimizing                 | 0.000015 |
| statistics                 | 0.000049 |
| preparing                  | 0.000031 |
| Creating tmp table         | 0.000097 |
| executing                  | 0.000115 |
| end                        | 0.000017 |
| query end                  | 0.000004 |
| waiting for handler commit | 0.000016 |
| removing tmp table         | 0.000005 |
| waiting for handler commit | 0.000004 |
| closing tables             | 0.000010 |
| freeing items              | 0.000020 |
| cleaning up                | 0.000032 |
+----------------------------+----------+
27 rows in set, 1 warning (0.00 sec)
```

# SQL基本操作

## 创建表

[sql数据](https://github.com/cystanford/sql_heros_data)

```mysql
# 创建数据库
CREATE DATABASE [if not exists] db_name；
# 修改数据库
ALTER DATABASE db_name;
# 删除数据库
DROP DATABASE [if exitsts] db_name;
CREATE TABLE `player`  (
  `player_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '球员ID',
  `team_id` int(11) NOT NULL COMMENT '球队ID',
  `player_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '球员姓名',
  `height` float(3, 2) NULL DEFAULT NULL COMMENT '球员身高',
  PRIMARY KEY (`player_id`) USING BTREE,
  UNIQUE INDEX `player_name`(`player_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
# 修改表
# 添加字段
ALTER TABLE player ADD (age int(11));
# 修改字段名
ALTER TABLE player RENAME COLUMN age to player_age;
# 修改字段的数据类型
ALTER TABLE player MODIFY (player_age float(3,1));
# 删除字段
ALTER TABLE player DROP COLUMN player_age;
```

常见约束：

1. 主键约束；作用是唯一标识一条记录，不能重复，不能为空， 即 UNIQUE+NOT NULL。一个数据表的主键只能有一个。 主键可以是一个字段，也可以由多个字段复合组成；
2. 外键约束：外键确保了表与表之间引用的完整性。一个表中的外键对应另一张表的主键。外键可以是重复的，也可以为空；
3. 唯一性约束：唯一性约束UNIQUE表明了字段在表中的数值是唯一的；
4. NOT NULL 约束。对字段定义了 NOT NULL，即表明该字 段不应为空，必须有取值；
5. DEFAULT，表明了字段的默认值。如果在插入数据的时 候，这个字段没有取值，就设置为默认值；
6. CHECK 约束，用来检查特定字段取值范围的有效性，CHECK 约束的结果不能为 FALSE，CHECK(height>=0 AND height<3)；

## 检索数据

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

1. 首先先通过 CROSS JOIN 求笛卡尔积，相当于得到虚拟表 vt(virtual table)1-1;
2. 通过 ON 进行筛选，在虚拟表 vt1-1 的基础上进行筛 选，得到虚拟表 vt1-2；
3. 添加外部行。如果我们使用的是左连接、右链接或者全连 接，就会涉及到外部行，也就是在虚拟表 vt1-2 的基础上 增加外部行，得到虚拟表 vt1-3。

```mysql
# 查询，书写顺序SELECT ... FROM ... WHERE ... GROUP BY ... HAVING ... ORDER BY ...
# 执行顺序，FROM > WHERE > GROUP BY > HAVING > SELECT的字段 > DISTINCT > ORDER BY > LIMIT
# 取别名
select name as n from heros;
# 查询常数
mysql> SELECT '王者荣耀' as platform, name FROM heros;
```

当我们拿到了查询数据表的原始数据，也就是最终的虚拟表 vt1，就可以在此基础上再进行 WHERE 阶段。在这个阶段中，会根据 vt1 表的结果进行筛选过滤，得到虚拟表 vt2。然后进入第三步和第四步，也就是 GROUP 和 HAVING 阶段。在这个阶段中，实际上是在虚拟表 vt2 的基础上进行分组和分组过滤，得到中间的虚拟表 vt3 和 vt4。当我们完成了条件筛选部分之后，就可以筛选表中提取的字段，也就是进入到 SELECT 和 DISTINCT 阶段。首先在 SELECT 阶段会提取想要的字段，然后在 DISTINCT 阶段过滤掉重复的行，分别得到中间的虚拟表 vt5-1 和 vt5-2。当我们提取了想要的字段数据之后，就可以按照指定的字段进行排序，也就是 ORDER BY 阶段，得到虚拟表 vt6。最后在 vt6 的基础上，取出指定行的记录，也就是 LIMIT 阶段，得到最终的结果，对应的是虚拟表 vt7。

### DISTINCT去重

```mysql
# DISTINCT去除重复行,需要放到所有列名的前面,
SELECT DISTINCT attack_range FROM heros;
+--------------+
| attack_range |
+--------------+
| 近战         |
| 远程         |
+--------------+
2 rows in set (0.01 sec)
```

小结：

1. DISTINCT 需要放到所有列名的前面，如果写成SELECT name, DISTINCT attack_range FROM heros会报错。

```mysql
mysql> SELECT attack_range,DISTINCT name FROM heros;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'DISTINCT name FROM heros' at line 1
```

2. DISTINCT 其实是对后面所有列名的组合进行去重，你能看到最后的结果是 69 条，因为这 69 个英雄名称不同。

```mysql
SELECT DISTINCT attack_range, name FROM heros;
| 近战         | 花木兰       |
| 近战         | 赵云         |
| 近战         | 橘石京       |
+--------------+--------------+
69 rows in set (0.00 sec)
```

### ORDER BY排序

```mysql
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
```

小结：

1. 排序的列名，如果是多列，则先排序第一个，然后第二个；
2. ASC 代表递增排序，DESC 代表递减排序；默认升序；
3. 非选择列排序:ORDER BY 可以使用非选择列进行排序，所以即使在 SELECT 后面没有这个列名，你同样可以放到 ORDER BY后面进行排序。
4. ORDER BY 的位置:ORDER BY 通常位于 SELECT 语句的最后一条子句，否则会报错。

```mysql
# 显示英雄名称及最大生命值，按照最大生命值从高到低的方式进行排序
mysql> SELECT name, hp_max FROM heros ORDER BY hp_max DESC;
+--------------+--------+
| name         | hp_max |
+--------------+--------+
| 廉颇         |   9328 |
| 白起         |   8638 |
| 武则天       |   5037 |
+--------------+--------+
69 rows in set (0.00 sec)
# 显示英雄名称及最大生命值，按照第一排序最大法力从低到高，当最大法力值相等的时候则按照第二排序进行，即最大生命值从高到低的方式进行排序
mysql> SELECT name, hp_max FROM heros ORDER BY mp_max, hp_max DESC;
+--------------+--------+
| name         | hp_max |
+--------------+--------+
| 程咬金       |   8611 |
| 亚瑟         |   8050 |
| 曹操         |   7473 |
| 吕布         |   7344 |
| 橘石京       |   7000 |
| 妲己         |   5824 |
+--------------+--------+
69 rows in set (0.00 sec)
```

### LIMIT约束返回结果的数量

```mysql
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
```

### WHERE 过滤

<img src="https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201109153530.png" alt="img" style="zoom:50%;" />

```mysql
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
## 查询所有最大生命值在 5399 到 6811 之间的英雄：
mysql> SELECT name, hp_max FROM heros WHERE hp_max BETWEEN 5399 AND 6811;
+--------------+--------+
| name         | hp_max |
+--------------+--------+
| 芈月         |   6164 |
| 雅典娜       |   6264 |
| 后羿         |   5986 |
+--------------+--------+
41 rows in set (0.00 sec)
## 空值检查
mysql> SELECT name, hp_max FROM heros WHERE hp_max IS NULL;
Empty set (0.00 sec)
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
## 通配符过滤LIKE, _匹配一个字符，%匹配一个或者多个字符
mysql> SELECT name FROM heros WHERE name LIKE '%太%';
+--------------+
| name         |
+--------------+
| 东皇太一     |
| 太乙真人     |
+--------------+
2 rows in set (0.00 sec)
```

注意：LIKE可能会造成索引失效，LIKE '%太%'或LIKE '%太'的时候就会对全表进行扫描。如果使用LIKE '太%'，索引不会失效。

### 常见函数

```mysql
# 算术函数
## 绝对值
SELECT ABS(-2);
## 取余
SELECT MOD(101,3)
## 四舍五入
SELECT ROUND(37.25,1)
# 字符串函数
## 拼接
SELECT CONCAT('abc', 123)
## 长度
SELECT LENGTH('你好')
## 汉字，字母都算一个字符
SELECT CHAR_LENGTH('你好')
## 转小写
SELECT LOWER('ABC')
## 转大写
SELECT UPPER('abc')
## 替换，运行结果为 f123d
SELECT REPLACE('fabcd', 'abc', 123)
## 截取字符串
SELECT SUBSTRING('fabcd', 1,3)
# 日期函数
SELECT CURRENT_DATE()，运行结果为 2019-04-03
SELECT CURRENT_TIME()，运行结果为 21:26:34
SELECT CURRENT_TIMESTAMP()，运行结果为 2019-04-03 21:26:34
SELECT EXTRACT(YEAR FROM '2019-04-03')，运行结果为 2019
SELECT DATE('2019-04-01 12:00:05')，运行结果为 2019-04-01
# 转换函数
SELECT CAST(123.123 AS DECIMAL(8,2))，运行结果为 123.12。
SELECT COALESCE(null,1,2)，运行结果为 1。
```

### 聚集函数

```mysql
# 聚集函数,COUNT总行数，MAX最大值，MIN最小值，SUM求和，AVG平均值
## COUNT
### 查询最大生命值大于 6000 的英雄数量
mysql> SELECT COUNT(*) FROM heros WHERE hp_max > 6000;
+----------+
| COUNT(*) |
+----------+
|       41 |
+----------+
1 row in set (0.01 sec)
### 查询最大生命值大于 6000，且有次要定位的英雄数量
#### 注意，COUNT(role_assist)会忽略值为 NULL 的数据行，而 COUNT(*) 不管某个字段是否为NULL
mysql> SELECT COUNT(role_assist) FROM heros WHERE hp_max > 6000;
+--------------------+
| COUNT(role_assist) |
+--------------------+
|                 23 |
+--------------------+
1 row in set (0.01 sec)
## MAX
### 查询射手（主要定位或者次要定位是射手）的最大生命值的最大值是多少
mysql> SELECT MAX(hp_max) FROM heros WHERE role_main = '射手' or role_assist = '射手';
+-------------+
| MAX(hp_max) |
+-------------+
|        6014 |
+-------------+
1 row in set (0.00 sec)
mysql> SELECT COUNT(*), AVG(hp_max), MAX(mp_max), MIN(attack_max), SUM(defense_max) FROM heros WHERE role_main = '射手' or role_assist = '射手';
+----------+-------------+-------------+-----------------+------------------+
| COUNT(*) | AVG(hp_max) | MAX(mp_max) | MIN(attack_max) | SUM(defense_max) |
+----------+-------------+-------------+-----------------+------------------+
|       10 |      5798.5 |        1784 |             362 |             3333 |
+----------+-------------+-------------+-----------------+------------------+
1 row in set (0.00 sec)
```

### GROUP BY过滤分组

```mysql
# 按照英雄的主要定位进行分组，并统计每组的英雄数量
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
# 对英雄按照次要定位进行分组，并统计每组英雄的数量,NULL也被算作一个分组
mysql> SELECT COUNT(*), role_assist FROM heros GROUP BY role_assist;
+----------+-------------+
| COUNT(*) | role_assist |
+----------+-------------+
|        6 | 战士        |
|       10 | 坦克        |
|        5 | 辅助        |
|       40 | NULL        |
|        2 | 法师        |
|        6 | 刺客        |
+----------+-------------+
6 rows in set (0.00 sec)
# 按照英雄的主要定位、次要定位进行分组，查看这些英雄的数量，并按照这些分组的英雄数量从高到低进行排序。
mysql> SELECT COUNT(*) as num, role_main, role_assist FROM heros GROUP BY role_main, role_assist ORDER BY num DESC;
+-----+-----------+-------------+
| num | role_main | role_assist |
+-----+-----------+-------------+
|  12 | 法师      | NULL        |
|   9 | 射手      | NULL        |
|   8 | 战士      | NULL        |
+-----+-----------+-------------+
19 rows in set (0.01 sec)
```

### HAVING过滤分组

```mysql
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
```

### 子查询

子查询从数据表中查询了数据结果，如果这个数据结果只执行一次，然后这个数据结果作为主查询的条件进行执行，那么这样的子查询叫做非关联子查询。

如果子查询需要执行多次，即采用循环的方式，先从外部查询开始，每次都传入子查询进行查询，然后再将结果反馈给外部，这种嵌套的执行方式就称为关联子查询。

**在非关联子查询中，从句的计算结果是固定的。**

```mysql
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
#集合比较子查询
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
## ANY子查询
### 查询球员表中，比印第安纳步行者（对应的 team_id 为 1002）中任意一个球员身高高的球员信息，并且输出他们的球员 ID、球员姓名和球员身高
mysql> SELECT player_id, player_name, height FROM player WHERE height > ANY (SELECT height FROM player WHERE team_id = 1002);
+-----------+------------------------------------+--------+
| player_id | player_name                        | height |
+-----------+------------------------------------+--------+
|     10001 | 韦恩-艾灵顿                        |   1.93 |
|     10002 | 雷吉-杰克逊                        |   1.91 |
|     10003 | 安德烈-德拉蒙德                    |   2.11 |
+-----------+------------------------------------+--------+
35 rows in set (0.01 sec)
### 比印第安纳步行者（对应的 team_id 为 1002）中所有球员身高都高的球员的信息，并且输出球员 ID、球员姓名和球员身高
mysql> SELECT player_id, player_name, height FROM player WHERE height > ALL (SELECT height FROM player WHERE team_id = 1002);
+-----------+---------------+--------+
| player_id | player_name   | height |
+-----------+---------------+--------+
|     10004 | 索恩-马克     |   2.16 |
+-----------+---------------+--------+
1 row in set (0.01 sec)

```

IN vs EXISTS,IN是外表和内表进行hash连接，是先执行子查询，EXISTS是对外表进行循环，然后在内表进行查询。因此如果**外表数据量大，则用IN，如果外表数据量小，也用EXISTS**。IN有一个缺陷是不能判断NULL，因此如果字段存在NULL值，则会出现返回，因为最好使用NOT EXISTS。

SELECT * FROM A WHERE cc IN (SELECT cc FROM B)

SELECT * FROM A WHERE EXIST (SELECT cc FROM B WHERE B.cc=A.cc)

### 连接

``` mysql
## 连接
## 笛卡尔积,交叉连接，英文是 CROSS JOIN
### 笛卡尔积，X 和 Y 的笛卡尔积就是 X 和 Y 的所有可能组合
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
### SQL99中：SELECT * FROM player CROSS JOIN team;
## 等值连接（自然连接）,用两张表中都存在的列进行连接
mysql> SELECT player_id, player.team_id, player_name, height, team_name FROM player, team WHERE player.team_id = team.team_id;
+-----------+---------+------------------------------------+--------+-----------------------+
| player_id | team_id | player_name                        | height | team_name             |
+-----------+---------+------------------------------------+--------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 | 底特律活塞            |
|     10003 |    1001 | 安德烈-德拉蒙德                    |   2.11 | 底特律活塞            |
+-----------+---------+------------------------------------+--------+-----------------------+
37 rows in set (0.01 sec)
### SQL99中：SELECT player_id, team_id, player_name, height, team_name FROM player NATURAL JOIN team
## 非等值连接
## 外连接
### 左外连接，左表是主表
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
###等价于：SELECT * FROM player LEFT JOIN team ON player.team_id = team.team_id
### 右外连接，右表是主表
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
### 等价于：SELECT * FROM player RIGHT JOIN team ON player.team_id = team.team_id
## 全外连接
SELECT * FROM player FULL JOIN team ON player.team_id = team.team_id;
### 自连接,查询条件使用了当前表的字段
#### 查看比布雷克·格里芬高的球员都有谁，以及他们的对应身高
mysql> SELECT b.player_name, b.height FROM player as a , player as b WHERE a.player_name = '布雷克-格里芬' and a.height < b.height;
+---------------------------+--------+
| player_name               | height |
+---------------------------+--------+
| 安德烈-德拉蒙德           |   2.11 |
| 索恩-马克                 |   2.16 |
| 扎扎-帕楚里亚             |   2.11 |
| 亨利-埃伦森               |   2.11 |
| 多曼塔斯-萨博尼斯         |   2.11 |
| 迈尔斯-特纳               |   2.11 |
+---------------------------+--------+
6 rows in set (0.01 sec)
#### 如果我们不使用自连接,需要两次查询
mysql> SELECT height FROM player WHERE player_name = '布雷克-格里芬';
+--------+
| height |
+--------+
|   2.08 |
+--------+
1 row in set (0.01 sec)
mysql> SELECT player_name, height FROM player WHERE height > 2.08;
+---------------------------+--------+
| player_name               | height |
+---------------------------+--------+
| 安德烈-德拉蒙德           |   2.11 |
| 索恩-马克                 |   2.16 |
| 扎扎-帕楚里亚             |   2.11 |
| 亨利-埃伦森               |   2.11 |
| 多曼塔斯-萨博尼斯         |   2.11 |
| 迈尔斯-特纳               |   2.11 |
+---------------------------+--------+
6 rows in set (0.00 sec)

## ON连接
mysql> SELECT player_id, player.team_id, player_name, height, team_name FROM player JOIN team ON player.team_id = team.team_id;
+-----------+---------+------------------------------------+--------+-----------------------+
| player_id | team_id | player_name                        | height | team_name             |
+-----------+---------+------------------------------------+--------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 | 底特律活塞            |
+-----------+---------+------------------------------------+--------+-----------------------+
37 rows in set (0.01 sec)
## USING 连接
mysql> SELECT player_id, team_id, player_name, height, team_name FROM player JOIN team USING(team_id);
+-----------+---------+------------------------------------+--------+-----------------------+
| player_id | team_id | player_name                        | height | team_name             |
+-----------+---------+------------------------------------+--------+-----------------------+
|     10001 |    1001 | 韦恩-艾灵顿                        |   1.93 | 底特律活塞            |
|     10002 |    1001 | 雷吉-杰克逊                        |   1.91 | 底特律活塞            |
|     10003 |    1001 | 安德烈-德拉蒙德                    |   2.11 | 底特律活塞            |
|     10036 |    1002 | 阿利兹-约翰逊                      |   2.06 | 印第安纳步行者        |
|     10037 |    1002 | 伊凯·阿尼博古                      |   2.08 | 印第安纳步行者        |
+-----------+---------+------------------------------------+--------+-----------------------+
37 rows in set (0.00 sec)

```

## 视图

TODO

## 存储过程

TODO

## 事务

事务特性：ACID。

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

# Mysql调优

- 子查询优化：小表驱动大表的原则（EXISTS和 IN子查询）;
- 在 WHERE 子句中会尽量避免对字段进行函数运算，它们会让字段的索引失效;
- 如果数据重复度高，就不需要创建索引。通常在重复度超过 10% 的情况下，可以不创建这个字段的索引。比如性别这个字段（取值为男和女）;
- 要注意索引列的位置对索引使用的影响。比如我们在 WHERE 子句中对索引字段进行了表达式的计算，会造成这个字段的索引失效;
- 要注意联合索引对索引使用的影响。我们在创建联合索引的时候会对多个字段创建索引，这时索引的顺序就很重要了。比如我们对字段 x, y, z 创建了索引，那么顺序是 (x,y,z) 还是 (z,y,x)，在执行的时候就会存在差别；
- 要注意多个索引对索引使用的影响。索引不是越多越好，因为每个索引都需要存储空间，索引多也就意味着需要更多的存储空间。此外，过多的索引也会导致优化器在进行评估的时候增加了筛选出索引的计算时间，影响评估的效率；

## 数据库范式

- 超键：能唯一标识元组的属性集叫做超键。
- 候选键：如果超键不包括多余的属性，那么这个超键就是候选键。
- 主键：用户可以从候选键中选择一个作为主键。
- 外键：如果数据表 R1 中的某属性集不是 R1 的主键，而是另一个数据表 R2 的主键，那么这个属性集就是数据表 R1 的外键。
- 主属性：包含在任一候选键中的属性称为主属性。
- 非主属性：与主属性相对，指的是不包含在任何一个候选键中的属性。

我们之前用过 NBA 的球员表（player）和球队表（team）。这里我可以把球员表定义为包含球员编号、姓名、身份证号、年龄和球队编号；球队表包含球队编号、主教练和球队所在地。对于球员表来说，超键就是包括球员编号或者身份证号的任意组合，比如（球员编号）（球员编号，姓名）（身份证号，年龄）等。候选键就是最小的超键，对于球员表来说，候选键就是（球员编号）或者（身份证号）。主键是我们自己选定，也就是从候选键中选择一个，比如（球员编号）。外键就是球员表中的球队编号。在 player 表中，主属性是（球员编号）（身份证号），其他的属性（姓名）（年龄）（球队编号）都是非主属性。

1. 列不可分；
2. 保证表中的非主属性与候选键完全依赖；
3. 保证表中的非主属性与候选键不存在传递依赖；

## 索引

**索引分类**：

功能逻辑上划分：

1. 普通索引：没有任何约束，主要用于提高查询效率；
2. 唯一索引：增加了数据唯一性的约束，在一张数据表里可以有多个唯一索引；
3. 主键索引：在唯一索引的基础上增加了不为空的约束，也就是 NOT NULL+UNIQUE，一张表里最多只有一个主键索引；
4. 全文索引：MySQL只支持英文；

物理实现上划分：

1. 聚集索引：数据和索引存放在一块；每个表只可以有一个；
2. 非聚集索引（二级索引或者辅助索引）：数据和索引分开；

聚集索引的叶子节点存储的就是我们的数据记录，非聚集索引的叶子节点存储的是数据位置。非聚集索引不会影响数据表的物理存储顺序。一个表只能有一个聚集索引，因为只能有一种排序存储的方式，但可以有多个非聚集索引，也就是多个索引目录提供数据检索。使用聚集索引的时候，数据的查询效率高，但如果对数据进行插入，删除，更新等操作，效率会比非聚集索引低。



**创建索引时机**：

1. 字段的数值有唯一性的限制，比如用户名，可以直接创建唯一性索引，或者主键索引；
2. 频繁作为 WHERE 查询条件的字段，尤其在数据表大的情况下；
3. 需要经常 GROUP BY 和 ORDER BY 的列；
4. UPDATE、DELETE 的 WHERE 条件列，一般也需要创建索引；
5. DISTINCT 字段需要创建索引；

**不需要索引**：

1. WHERE 条件（包括 GROUP BY、ORDER BY）里用不到的字段不需要创建索引；
2. 小表不需要，如小于1000个；
3. 大量重复数据，如性别；
4. 频繁更新的；



**索引失效**，参考实验4

1. 索引进行了表达式计算，则会失效；

```mysql
mysql> EXPLAIN SELECT comment_id, user_id, comment_text FROM product_comment WHERE comment_id+1 = 900001;
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra       |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 996424 |   100.00 | Using where |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
1 row in set, 1 warning (0.01 sec)

mysql> 
mysql> SELECT comment_id, user_id, comment_text FROM product_comment WHERE comment_id = 900000;
+------------+---------+----------------------+
| comment_id | user_id | comment_text         |
+------------+---------+----------------------+
|     900000 |  290349 | 4404fe9783f97a475ed1 |
+------------+---------+----------------------+
1 row in set (0.00 sec)
```

2. 如果对索引使用函数，也会造成失效；
3. 在 WHERE 子句中，如果在 OR 前的条件列进行了索引，而在 OR 后的条件列没有进行索引，那么索引会失效;

```mysql
mysql> EXPLAIN SELECT comment_id, user_id, comment_text FROM product_comment WHERE comment_id = 900001 OR comment_text = '462eed7ac6e791292a79';
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra       |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | PRIMARY       | NULL | NULL    | NULL | 996424 |    10.00 | Using where |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
1 row in set, 1 warning (0.00 sec)
# 创建索引后
+----+-------------+-----------------+------------+-------------+----------------------+----------------------+---------+------+------+----------+------------------------------------------------+
| id | select_type | table           | partitions | type        | possible_keys        | key                  | key_len | ref  | rows | filtered | Extra                                          |
+----+-------------+-----------------+------------+-------------+----------------------+----------------------+---------+------+------+----------+------------------------------------------------+
|  1 | SIMPLE      | product_comment | NULL       | index_merge | PRIMARY,comment_text | PRIMARY,comment_text | 4,767   | NULL |    2 |   100.00 | Using union(PRIMARY,comment_text); Using where |
+----+-------------+-----------------+------------+-------------+----------------------+----------------------+---------+------+------+----------+------------------------------------------------+
```

4. 当我们使用 LIKE 进行模糊查询的时候，前面不能是 %;

```mysql
mysql> EXPLAIN SELECT comment_id, user_id, comment_text FROM product_comment WHERE comment_text LIKE '%abc';
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra       |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 996424 |    11.11 | Using where |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
1 row in set, 1 warning (0.00 sec)
```

5. 索引列尽量设置为 NOT NULL 约束;

6. 我们在使用联合索引的时候要注意最左原则;

优化：

1. 如果数据重复度高，就不需要创建索引。通常在重复度超过 10% 的情况下，可以不创建这个字段的索引。
2. 要注意索引列的位置对索引使用的影响。比如我们在 WHERE 子句中对索引字段进行了表达式的计算，会造成这个字段的索引失效。
3. 联合索引对索引使用的影响。我们在创建联合索引的时候会对多个字段创建索引，这时索引的顺序就很重要了。比如我们对字段 x, y, z 创建了索引，那么顺序是 (x,y,z) 还是 (z,y,x)，在执行的时候就会存在差别。
4. 多个索引对索引使用的影响。



[所需数据](https://pan.baidu.com/s/1X47UAx6EWasYLLU91RYHKQ#list/path=%2Fsharelink4110802606-216507779167973%2F%E7%B4%A2%E5%BC%95%E7%9A%84%E6%95%B0%E6%8D%AE%E5%AE%9E%E9%AA%8C&parentPath=%2Fsharelink4110802606-216507779167973)：

实验1：数据量小

```mysql
# 没有索引
mysql> SELECT id, name, hp_max, mp_max FROM heros_without_index WHERE name = '刘禅'; 
+-------+--------+--------+--------+
| id    | name   | hp_max | mp_max |
+-------+--------+--------+--------+
| 10015 | 刘禅   |   8581 |   1694 |
+-------+--------+--------+--------+
1 row in set (0.00 sec)
# 对name字段建立索引
mysql> SELECT id, name, hp_max, mp_max FROM heros_with_index WHERE name = '刘禅';   
+-------+--------+--------+--------+
| id    | name   | hp_max | mp_max |
+-------+--------+--------+--------+
| 10015 | 刘禅   |   8581 |   1694 |
+-------+--------+--------+--------+
1 row in set (0.00 sec)
```

结论：基本没有区别。

实验2：对比分析聚集索引和非聚集索引：

```mysql
# user_id为主键
mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_id = 900001;
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.00 sec)
# user_name未建立索引，可以看到没有索引，查询效率变慢
mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_name = 'student_890001';
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.23 sec)
# 对user_name创建普通索引,可以看到查询的时间大大缩短
mysql> CREATE INDEX user_name ON user_gender(user_name);
Query OK, 0 rows affected (2.19 sec)
Records: 0  Duplicates: 0  Warnings: 0

mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_name = 'student_890001';
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.00 sec)
```

实验3：最左前缀匹配原则：

```mysql
# 删除之前的索引
mysql> DROP INDEX user_name ON user_gender;
Query OK, 0 rows affected (0.01 sec)
Records: 0  Duplicates: 0  Warnings: 0
# 建立(user_id,user_name)联合索引
mysql> CREATE INDEX user_name ON user_gender(user_id,user_name);
Query OK, 0 rows affected (0.84 sec)
Records: 0  Duplicates: 0  Warnings: 0
# 查询user_id,user_name
mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_id = 900001 AND user_name = 'student_890001';
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.01 sec)
# 查询user_id
mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_id = 900001;
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.00 sec)

# 查询user_name，索引失效
mysql> SELECT user_id, user_name, user_gender FROM user_gender WHERE user_name = 'student_890001';
+---------+----------------+-------------+
| user_id | user_name      | user_gender |
+---------+----------------+-------------+
|  900001 | student_890001 |           0 |
+---------+----------------+-------------+
1 row in set (0.23 sec)
```

实验4：索引失效

```mysql
# 创建普通索引
mysql> CREATE INDEX player_id ON player(player_id);
Query OK, 0 rows affected (0.04 sec)
Records: 0  Duplicates: 0  Warnings: 0
# 显示执行计划，索引表达式进行了计算，从而会失效
mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE player_id+1 = 10001;
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | player | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   37 |   100.00 | Using where |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.01 sec)
# 避免索引失效
mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE player_id = 10000;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+--------------------------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra                          |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+--------------------------------+
|  1 | SIMPLE      | NULL  | NULL       | NULL | NULL          | NULL | NULL    | NULL | NULL |     NULL | no matching row in const table |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+--------------------------------+
1 row in set, 1 warning (0.00 sec)
# 对索引使用函数，从而失效
mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE SUBSTRING(player_id, 3,4)='11';
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | player | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   37 |   100.00 | Using where |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.01 sec)

mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE player_id LIKE '%11';
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | player | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   37 |    11.11 | Using where |
+----+-------------+--------+------------+------+---------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)

# 在 WHERE 子句中，如果在 OR 前的条件列进行了索引，而在 OR 后的条件列没有进行索引，那么索引会失效。
mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE player_id = 10001 OR player_name = '韦恩-艾灵顿'; 
+----+-------------+--------+------------+------+-------------------+------+---------+------+------+----------+-------------+
| id | select_type | table  | partitions | type | possible_keys     | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+--------+------------+------+-------------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | player | NULL       | ALL  | PRIMARY,player_id | NULL | NULL    | NULL |   37 |    12.43 | Using where |
+----+-------------+--------+------------+------+-------------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)

mysql> EXPLAIN SELECT player_id, team_id, player_name FROM player WHERE player_id = 10001 OR player_name = '韦恩-艾灵顿'; 
+----+-------------+--------+------------+-------------+---------------------+---------------------+---------+------+------+----------+-----------------------------------------------+
| id | select_type | table  | partitions | type        | possible_keys       | key                 | key_len | ref  | rows | filtered | Extra                                         |
+----+-------------+--------+------------+-------------+---------------------+---------------------+---------+------+------+----------+-----------------------------------------------+
|  1 | SIMPLE      | player | NULL       | index_merge | PRIMARY,player_name | PRIMARY,player_name | 4,767   | NULL |    2 |   100.00 | Using union(PRIMARY,player_name); Using where |
+----+-------------+--------+------------+-------------+---------------------+---------------------+---------+------+------+----------+-----------------------------------------------+
1 row in set, 1 warning (0.00 sec)

# 当我们使用 LIKE 进行模糊查询的时候，前面不能是 %
mysql> EXPLAIN SELECT comment_id, user_id, comment_text FROM product_comment WHERE comment_text LIKE '%abc';
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra       |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 996424 |    11.11 | Using where |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
1 row in set, 1 warning (0.00 sec)
```



## 定位sql为什么执行慢

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201015153415.png" alt="img" style="zoom:33%;" />

1. 慢查询定位

```mysql
mysql> show variables like '%slow_query_log';
+----------------+-------+
| Variable_name  | Value |
+----------------+-------+
| slow_query_log | OFF   |
+----------------+-------+
1 row in set (0.03 sec)
# 开启慢查询日志
mysql> set global slow_query_log='ON';
Query OK, 0 rows affected (0.03 sec)

mysql> show variables like '%slow_query%';
+---------------------+----------------------------------------------+
| Variable_name       | Value                                        |
+---------------------+----------------------------------------------+
| slow_query_log      | ON                                           |
| slow_query_log_file | /usr/local/mysql/data/MacBook-Pro-2-slow.log |
+---------------------+----------------------------------------------+
2 rows in set (0.01 sec)
# 看慢查询的时间阈值设置
mysql> show variables like '%long_query_time%';
+-----------------+-----------+
| Variable_name   | Value     |
+-----------------+-----------+
| long_query_time | 10.000000 |
+-----------------+-----------+
1 row in set (0.00 sec)
# 修改慢查询时间阈值
set global long_query_time = 3;
# 使用mysql自带的mysqldumpslow统计慢查询日志
# mysqldumpslow: -s：采用 order 排序的方式，排序方式可以有以下几种。分别是 c（访问次数）、t（查询时间）、l（锁定时间）、r（返回记录）、ac（平均查询次数）、al（平均锁定时间）、ar（平均返回记录数）和 at（平均查询时间）。其中 at 为默认排序方式。-t：返回前 N 条数据 。
sudo mysqldumpslow -s t -t 2 "/usr/local/mysql/data/MacBook-Pro-2-slow.log"
Password:

Reading mysql slow query log from /usr/local/mysql/data/MacBook-Pro-2-slow.log
Count: 1  Time=0.00s (0s)  Lock=0.00s (0s)  Rows=0.0 (0), 0users@0hosts
  
Died at /usr/local/bin/mysqldumpslow line 162, <> chunk 1.
```

2. 使用 EXPLAIN 查看执行计划

EXPLAIN 可以帮助我们了解数据表的读取顺序、SELECT 子句的类型、数据表的访问类型、可使用的索引、实际使用的索引、使用的索引长度、上一个表的连接匹配条件、被优化器查询的行的数量以及额外的信息（比如是否使用了外部排序，是否使用了临时表等）等。

数据表的访问类型所对应的 type 列是我们比较关注的信息。type 可能有以下几种情况：

<img src="https://static001.geekbang.org/resource/image/22/92/223e8c7b863bd15c83f25e3d93958692.png" alt="img" style="zoom:50%;" />

```mysql
mysql> EXPLAIN SELECT comment_id, product_id, comment_text, product_comment.user_id, user_name FROM product_comment JOIN user on product_comment.user_id = user.user_id;
+----+-------------+-----------------+------------+--------+---------------+---------+---------+------------------------------+--------+----------+-------+
| id | select_type | table           | partitions | type   | possible_keys | key     | key_len | ref                          | rows   | filtered | Extra |
+----+-------------+-----------------+------------+--------+---------------+---------+---------+------------------------------+--------+----------+-------+
|  1 | SIMPLE      | product_comment | NULL       | ALL    | NULL          | NULL    | NULL    | NULL                         | 996424 |   100.00 | NULL  |
|  1 | SIMPLE      | user            | NULL       | eq_ref | PRIMARY       | PRIMARY | 4       | hero.product_comment.user_id |      1 |   100.00 | NULL  |
+----+-------------+-----------------+------------+--------+---------------+---------+---------+------------------------------+--------+----------+-------+
2 rows in set, 1 warning (0.01 sec)
# 对 product_comment 数据表进行查询，设计了联合索引composite_index (user_id, comment_text)，然后对数据表中的comment_id、comment_text、user_id这三个字段进行查询，最后用 EXPLAIN 看下执行计划
# 访问方式采用了 index 的方式，key 列采用了联合索引，进行扫描。Extral 列为 Using index，告诉我们索引可以覆盖 SELECT 中的字段，也就不需要回表查询了。
mysql> EXPLAIN SELECT comment_id, comment_text, user_id FROM product_comment;
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | NULL          | NULL | NULL    | NULL | 996424 |   100.00 | NULL  |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------+
1 row in set, 1 warning (0.00 sec)
# index_merge 说明查询同时使用了两个或以上的索引，最后取了交集或者并集。比如想要对comment_id=500000 或者user_id=500000的数据进行查询，数据表中 comment_id 为主键，user_id 是普通索引，我们可以查看下执行计划：
# 看到这里同时使用到了两个索引，分别是主键和 user_id，采用的数据表访问类型是 index_merge，通过 union 的方式对两个索引检索的数据进行合并。
mysql> EXPLAIN SELECT comment_id, product_id, comment_text, user_id FROM product_comment WHERE comment_id = 500000 OR user_id = 500000;
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
| id | select_type | table           | partitions | type | possible_keys | key  | key_len | ref  | rows   | filtered | Extra       |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
|  1 | SIMPLE      | product_comment | NULL       | ALL  | PRIMARY       | NULL | NULL    | NULL | 996424 |    10.00 | Using where |
+----+-------------+-----------------+------------+------+---------------+------+---------+------+--------+----------+-------------+
1 row in set, 1 warning (0.00 sec)
```

3. 使用SHOW PROFILE 查看 SQL 的具体执行成本

SHOW PROFILE 相比 EXPLAIN 能看到更进一步的执行解析，包括 SQL 都做了什么、所花费的时间等。默认情况下，profiling 是关闭的，我们可以在会话级别开启这个功能。

```mysql
mysql> show variables like 'profiling';
+---------------+-------+
| Variable_name | Value |
+---------------+-------+
| profiling     | OFF   |
+---------------+-------+
1 row in set (0.01 sec)

mysql> set profiling = 'ON';
Query OK, 0 rows affected, 1 warning (0.01 sec)
# 
mysql> show profiles;
Empty set, 1 warning (0.01 sec)
```

