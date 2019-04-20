## 需求分析

**总体目标：**设计一套**自适应/简洁/美观/易于文章管理发布**的一个属于我个人的博客，最后一页能展示我个人的简历，因为大三快结束了马上就该去找工作了...哦忘了，最重要的还是要支持**Markdown**才行，因为已经习惯了...

#### 前端需求分析

首先，前端的**页面要求**是：

-  **①简洁/美观**——个人很喜欢像Mac那样的简洁风，越简单越好，当然也得好看；
-  **②最好是单页面**——单页面的目的一方面是为了简洁，另一方面也是为了实现起来比较简单；
-  **③自适应**——至少能适配常见的手机分辨率吧，我可不希望自己的博客存在显示差异性的问题；

然后，思考了一下**可能出现的页面**：
 

![image-20190316123111986](https://ws2.sinaimg.cn/large/006tKfTcly1g14iyoi0l3j313u05wgp3.jpg)





> 1）首页：

-  **最新的文章**——我可以搞一个轮播之类的东西用来显示最新的几篇博文；
-  **顶部导航**——导航栏可以要有，而且可以提出来搞成通用的；
-  **联系方式**——首页最好再有能一眼找到我的联系方式，比如简书/博客园/微信公众号之类的；
-  **时间**——摁，时间；

> 2）文章页：

-  **分类栏**——左侧应该有文章的分类，记得要有一个全部文章；
-  **文章列表**——分类栏的右边就应该是该分类下的所有文章；
-  **分页栏**——考虑到现在我的Java Web分栏下的文章已经有那么多了，还是有必要搞个分页；

> 3）简历页：

这是预留的页面，到时候用来显示个人的简历；

> 4）关于页：

用来介绍项目的搭建编写过程，还有使用的技术栈啊之类的，然后留下个人的联系方式，Nice；

> 5）留言页：

因为是个人的博客，所以我并不想要限制看官们留言的权利，我希望他们能自己能定义用于显示的用户名，但是需要填写一个Email，不然搞得我不能回复，那搞个啥...当然也可以不留Email，也就是不希望得到回复呗（那可能有些留言会让我难受死吧..思考...）...

#### 后台需求分析：

最初的思考是这样的：



![img](https:////upload-images.jianshu.io/upload_images/7896890-4179c8999d11b748.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



后来一想，**文章置顶**这个都给忘了...然后发现其实有一个很关键的问题就是Markdown的文章应该怎样保存？一开始还是想要保存为.md文件保存在服务器的硬盘上的，但想想直接保存在数据库里也不错，省的麻烦，而且我很明确一点的是：**我并不会直接在博客上写Markdown**，因为有许许多多成熟的产品能让我写的舒心的多，我没必要去搞这么麻烦复杂繁琐，而且不一定好，所以我只需要用博客来**展示我写的Markdown格式的博文**就好了，Nice啊...又成功骗自己少写了好多代码hhhhh（没有啦..需求就这样的嘛...）

顺着这样的思路，我通常写文都是先在简书上写好的，并且简书有一个特点是所有的图片，不管是已经发布的文章还是没有发布的私人文章，都能通过地址取得，可以利用这一点让简书当个图床，诶又少弄了一部分代码，然后分析分析着就把需求搞成下面这样了：



![img](https:////upload-images.jianshu.io/upload_images/7896890-47540922422aa24e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



> 1）博文管理：

这个比较常规，就不说了；

> 2）网站数据统计：

作为网站的拥有者和设计者，我当然希望能希望知道这些数据了，然后单独作为拥有者来说，最好再分为日访问量/月访问量/总访问量这样子显示出来，再搞搞样式，简直不要太爽；

> 3）缓存管理：

图片就没缓存了，因为保存文章内容我需要保存md源码，所以可能需要在Redis里缓存最近常访问的文章的md转HTML后渲染好的HTML源码；

> 4）系统设置：

网站标题可以改呀，然后导航栏的名字也可以弄弄呀，其实这个也可以不用去搞，只是以防有时候心情不好给一整捣鼓可能心情就好了，hhhhh....；

> 5）留言管理：

有一些流氓留言可以删掉，最近学习到的比较好的方法是让该条数据的状态为置为0，而不是直接删除该条数据，这个设计数据库的时候就需要多设计一个字段，也可以通过用户留下的Email地址进行回复，最好搞一个自动通知，完美；

------

## 表结构设计

通过需求分析，然后严格按照《阿里巴巴Java开发手册》（下面所说的规范均来自于此）反复分析了很多遍，最终确定了如下的几张表：

![image-20190316123432770](https://ws3.sinaimg.cn/large/006tKfTcly1g14j26wlkwj30u00u5qsy.jpg)

然后来具体说一下各个表：

服务器：凌云智链，mysql用户：hjs，密码hjs457723，数据库名：blog

> 1）日志表（sys_log）：

```sql
CREATE TABLE `sys_log` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(20) NOT NULL DEFAULT '' COMMENT '操作地址的IP',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '操作内容',
  `operate_url` varchar(50) NOT NULL DEFAULT '' COMMENT '操作的访问地址',
  `operate_by` varchar(20) DEFAULT '' COMMENT '操作的浏览器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这张表就是拿来保存日志，用来记录每一个用户访问了哪些地址，使用了什么样的浏览器，操作内容可以作为一个保留字段，如果以后想要监控用户行为，那也是可以的~

这里首先遵守的规范是（下面雷同则不再重复赘述）：

-  **第五章第一节第2条（强制）**——**表名、字段名必须使用小写字母或数字**，禁止出现数字开头，禁止两个下划线中间只出现数字。数据库字段名的修改代价很大，因为无法进行预发布，所以字段名称需要慎重考虑；
-  **第五章第一节第3条（强制）**——表名不使用复数名词；
-  **第五章第一节第10条（推荐）**——表的命名最好加上“业务名称_表的作用”

想要拿出来跟大家讨论的一则规范是：

-  **第五章第9条（强制）**——表必备三个字段：id（unsigned bigint自增），gmt_create（date_time），gmt_modified（date_time）

像如上设计的日志表，它插入进去了就不会再更新了，而且对于我这个系统也很大概率不会有趣操作这个表的可能，那么**对于这样不会更新和操作的表，gmt_modified这个字段还有必要存在吗？**

emmm..事实上我问了**孤尽**大大本人，他回答的简洁有力：“要的，以备不时之需；”然而原谅我还是没有听话，hhhhh，另外一点我想说的是，我忘了是在哪里看到的了，但是像gmt_create这样的字段最好设计成create_by这样，字段本身就是很好的注释，摁，就喜欢这样满满的细节...

> 2）浏览量表（sys_view）：

```
CREATE TABLE `sys_view` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `ip` varchar(20) NOT NULL COMMENT '访问IP',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这张表用于保存每一次访问主页的记录，我想的是**每访问主页就记录增加一条数据**，简单同时也增加访问量嘛，hhhhh，也是不会更新的一张表，所以没modifield_by字段；

> 3）留言/评论表（tbl_message）

```
CREATE TABLE `tbl_comment` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(200) NOT NULL DEFAULT '' COMMENT '留言/评论内容',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `email` varchar(20) NOT NULL DEFAULT '' COMMENT '邮箱，用于回复消息',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户自己定义的名称',
  `ip` varchar(20) NOT NULL DEFAULT '' COMMENT '留言/评论IP',
  `is_effective` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，默认为1为有效，0为无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='因为message分为两种，一种是留言，一种是评论，这里搞成一张表是因为它们几乎是拥有相同的字段，我觉得没必要分成两张表来进行维护';
```

这是评论/留言表，因为考虑到留言和评论有几乎相同的字段，所以给弄成了一张表，这张表同样的不需要更新没有modifield_by字段，这里遵守的规范是：

-  **第五章第一节第1条（强制）**——表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned tinyint（1表示是，0表示否）
-  **第五章第一节第15条（参考）**——设置合适的字段存储长度，不但可以节约数据库表控件和索引存储，更重要的事能够提升检索速度；

> 4）分类信息表（tbl_category_info）：

```
CREATE TABLE `tbl_category_info` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `number` tinyint(10) NOT NULL DEFAULT '0' COMMENT '该分类下的文章数量',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这张表是文章的分类，一开始都忘记设计了....

> 5）文章信息表（tbl_article_info）：

```
CREATE TABLE `tbl_article_info` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) NOT NULL DEFAULT '' COMMENT '文章标题',
  `summary` varchar(300) NOT NULL DEFAULT '' COMMENT '文章简介，默认100个汉字以内',
  `is_top` tinyint(1) NOT NULL DEFAULT '0' COMMENT '文章是否置顶，0为否，1为是',
  `traffic` int(10) NOT NULL DEFAULT '0' COMMENT '文章访问量',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这是文章信息表，都是一些基础常用的字段就不再多做解释了

> 6）文章内容表（tbl_article_content）：

```
CREATE TABLE `tbl_article_content` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `article_id` bigint(40) NOT NULL COMMENT '对应文章ID',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这是文章内容表，我们并没有直接把内容字段设计在文章信息表里，而是单独建了一个表用来保存文章的内容，然后使用主键来关联，我们这里遵守的规范是：

-  **第五章第一节第8条（强制）**——varchar是可变长字符串，不预先分配存储空间，长度不要超过5000个字符。如果存储长度大于此值，则应定义字段类型为text，独立出来一张表，用主键来对应，避免影响其他字段的索引效率；
-  **第五章第三节第6条（强制）**——不得使用外键与级联，一切外键概念必须在应用层解决；

说明:以学生和成绩的关系为例，学生表中的 student_id 是主键，那么成绩表中的 student_id 则为外键。如果更新学生表中的 student_id，同时触发成绩表中的 student_id 更新，即为 级联更新。外键与级联更新适用于单机低并发，不适合分布式、高并发集群;级联更新是强阻 塞，存在数据库更新风暴的风险;外键影响数据库的插入速度。 

我试过我现在最长的一篇文章长度大概能存储8W长度的varchar，所以我就给单独建一个表分离出来了，使用text类型来保存文章的md源码

> 7）文章评论表（tbl_article_message）：

```
CREATE TABLE `tbl_article_comment` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(40) NOT NULL COMMENT '文章ID',
  `comment_id` bigint(40) NOT NULL COMMENT '对应的留言ID',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_effective` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效，默认为1有效，置0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

这其实是一个关联表，关联了文章和tbl_message表，用于专门存储某个文章下的评论信息

> 8）文章分类表（tbl_article_sort）：

```
CREATE TABLE `tbl_article_category` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `category_id` bigint(40) NOT NULL COMMENT '分类id',
  `article_id` bigint(40) NOT NULL COMMENT '文章id',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

同样是一张关联表，连接了文章和分类，并且同一篇文章能属于多个分类；

> 9）文章题图表（tbl_article_picture）：

```
CREATE TABLE `tbl_article_picture` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(40) NOT NULL COMMENT '对应文章id',
  `picture_url` varchar(100) NOT NULL DEFAULT '' COMMENT '图片url',
  `create_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_by` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='这张表用来保存题图url，每一篇文章都应该有题图';
```

这个是保存每一篇文章的题图，每一篇文章都因该有题图；

------

## 原型设计

事实上，我是直接先去找的原型，去参考了一下大概我需要做成什么样子...

#### 前端原型参考

在这里先给大家推荐一个设计网站吧，找素材啊之类的还挺方便的：

> 站酷：<http://www.zcool.com.cn/>

所以我在里面找到了我想要的前端原型，大概就像这个样子：

**1）首页：**



![img](https:////upload-images.jianshu.io/upload_images/7896890-8f7f25a8328d2372.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



**2）博客页：**



![img](https:////upload-images.jianshu.io/upload_images/7896890-84e9118bb6ef8aee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/940/format/webp)



**3）博文详情页：**



![img](https:////upload-images.jianshu.io/upload_images/7896890-9f378b79ea6c70fb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



**4）博文列表页：**



![img](https:////upload-images.jianshu.io/upload_images/7896890-8949658901ee238e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



不能再酷了..

#### 后端原型参考



![img](https:////upload-images.jianshu.io/upload_images/7896890-fec2bad0f9159957.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



emmmm...大概就像这样了吧，具体的样式可以到时候再调...

**总体是酷的就行！**

------

## 项目搭建

先来介绍一下这次想要使用的一些技术：

- SpringBoot / Spring 来编写后台
- Vue 来写页面，准备抛弃一下JSP，虽然现在Vue还啥都不懂，学呗
- MyBatis 用于ORM，喜欢这玩意儿的逆向工程
- RESTful API / JSON 交互
- ~~Redis 可能还会使用这个来缓存一下md转换之后的html源码~~

#### SpringBoot 工程搭建

SpringBoot 项目搭建过程就不再赘述了，不熟悉的童鞋戳这边：<https://www.jianshu.com/p/70963ab49f8c>，这里就简单给一下配置信息：



![img](https:////upload-images.jianshu.io/upload_images/7896890-444964414de6b61d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/971/format/webp)





![img](https:////upload-images.jianshu.io/upload_images/7896890-2f554c138801018e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/971/format/webp)



后台肯定是需要加安全验证的，要简单点我可以搞一个拦截器来简单弄弄，也可以用现有的安全框架，这里暂时就不加入这方面的东西了，把基本的弄进来就OK，然后它默认加入的东西不能够支持我们的业务，所以还需要手动添加进一些包：

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>cn.wmyskxz</groupId>
    <artifactId>blog</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>blog</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--MyBatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
        <!--MyBatis逆向工程-->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.6</version>
        </dependency>

        <!--SpringBoot测试支持-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--MySQL-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!--SpringBoot热部署-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional> <!-- 这个需要为 true 热部署才有效 -->
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>


</project>
```

热部署还是要的呀，然后再在【resrouces】下新建一个【banner.txt】文件，修改一下SpringBoot启动的提示信息：

```
 __      __                                 __                        
/\ \  __/\ \                               /\ \                       
\ \ \/\ \ \ \    ___ ___    __  __     ____\ \ \/'\    __  _  ____    
 \ \ \ \ \ \ \ /' __` __`\ /\ \/\ \   /',__\\ \ , <   /\ \/'\/\_ ,`\  
  \ \ \_/ \_\ \/\ \/\ \/\ \\ \ \_\ \ /\__, `\\ \ \\`\ \/>  </\/_/  /_ 
   \ `\___x___/\ \_\ \_\ \_\\/`____ \\/\____/ \ \_\ \_\/\_/\_\ /\____\
    '\/__//__/  \/_/\/_/\/_/ `/___/> \\/___/   \/_/\/_/\//\/_/ \/____/
                                /\___/                                
                                \/__/                                 
```

弄弄结构，最后整个项目的目录看起来大概是这个样子：



![img](https:////upload-images.jianshu.io/upload_images/7896890-84b958c7e9581369.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/382/format/webp)



下面对这些目录进行一些简要的说明：

- controller：控制器
- dao：实际上这个包可以改名叫mapper，因为里面放的应该是MyBatis逆向工程自动生成之后的mapper类，还是叫dao吧，传统...
- entity：实体类，还会有一些MyBatis生成的example
- generator：MyBatis逆向工程生成类
- interceptor：SpringBoot 拦截器
- service：Service层，里面还有一层impl目录
- util：一些工具类可以放在里面
- mapper：用于存放MyBatis逆向工程生成的.xml映射文件
- static：这个目录存放一些静态文件，简单了解了一下Vue的前后端分离，前台文件以后也需要放在这个目录下面

然后我使用application.yml文件代替了application.properties，这个东西结构清晰一点儿，反正用哪个都无所谓，配置好就OK了：

```
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/blog?characterEncoding=UTF-8
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    #Druid连接池配置相关
    druid:
      # 初始大小，最大，最小
      initial-size: 5
      min-idle: 5
      max-active: 20
      # 配置获取连接等待超时的时间
      max-wait: 60000
      # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
      time-between-eviction-runs-millis: 60000
      # 配置一个连接在池中最小生存的时间，单位是毫秒
      min-evictable-idle-time-millis: 300000
```

不需要检测数据库，不要整这么复杂，不过倒是需要给数据库密码加个密，明文的配置实在不安全，但是现在先不搞了；

#### MyBatis 逆向工程

使用过MyBatis逆向工程的朋友都应该知道，这东西有个BUG，就是重复生成的时候它并不会覆盖掉原来的内容（特指xml映射文件），而是会在后面重新生成一遍，这有点儿头疼，所以首先需要解决这个问题：

首先在【util】包下新建一个【OverIsMergeablePlugin】工具类：

```
package cn.wmyskxz.blog.util;

import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 避免MyBatiis重复生成的工具类
 *
 * @author:wmyskxz
 * @create:2018-06-14-上午 9:50
 */
public class OverIsMergeablePlugin extends PluginAdapter {
    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
```

然后在【generatorConfig.xml】中配置上该工具类：

```
<plugin type="cn.wmyskxz.blog.util.OverIsMergeablePlugin"/>
```

好的这样就搞定了，我们就正式开始我们的逆向工程：

**1）编写generatorConfig.xml逆向工程配置文件：**

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <context id="DB2Tables" targetRuntime="MyBatis3">
        <!--避免生成重复代码的插件-->
        <plugin type="cn.wmyskxz.blog.util.OverIsMergeablePlugin"/>

        <!--是否在代码中显示注释-->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!--数据库链接地址账号密码-->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/blog?characterEncoding=UTF-8" userId="root"
                        password="123456">
        </jdbcConnection>

        <!--生成pojo类存放位置-->
        <javaModelGenerator targetPackage="cn.wmyskxz.blog.entity" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <!--生成xml映射文件存放位置-->
        <sqlMapGenerator targetPackage="mapper" targetProject="src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!--生成mapper类存放位置-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="cn.wmyskxz.blog.dao" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <!--生成对应表及类名-->
        <table tableName="sys_log" domainObjectName="SysLog" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <!--使用自增长键-->
            <property name="my.isgen.usekeys" value="true"/>
            <!--使用数据库中实际的字段名作为生成的实体类的属性-->
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="sys_view" domainObjectName="SysView" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_article_content" domainObjectName="ArticleContent" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_article_info" domainObjectName="ArticleInfo" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_article_message" domainObjectName="ArticleMessage" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_message" domainObjectName="Message" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_sort_info" domainObjectName="SortInfo" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>
        <table tableName="tbl_article_sort" domainObjectName="ArticleSort" enableCountByExample="false"
               enableUpdateByExample="false" enableDeleteByExample="false" enableSelectByExample="true"
               selectByExampleQueryId="false">
            <property name="my.isgen.usekeys" value="true"/>
            <property name="useActualColumnNames" value="true"/>
            <generatedKey column="id" sqlStatement="JDBC"/>
        </table>

    </context>
</generatorConfiguration>
```

注意表名/生成目标目录之类的有没有写错，表名最好就直接去复制数据库中的名称；

**2）编写逆向工程生成类：**

```
package cn.wmyskxz.blog.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MyBatis逆向工程生成类
 *
 * @author:wmyskxz
 * @create:2018-06-14-上午 10:10
 */
public class MybatisGenerator {

    public static void main(String[] args) throws Exception {
        String today = "2018-6-14";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = sdf.parse(today);
        Date d = new Date();

        if (d.getTime() > now.getTime() + 1000 * 60 * 60 * 24) {
            System.err.println("——————未成成功运行——————");
            System.err.println("——————未成成功运行——————");
            System.err.println("本程序具有破坏作用，应该只运行一次，如果必须要再运行，需要修改today变量为今天，如:" + sdf.format(new Date()));
            return;
        }

        if (false)
            return;
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream is = MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        System.out.println("生成代码成功，只能执行一次，以后执行会覆盖掉mapper,pojo,xml 等文件上做的修改");
    }
}
```

这个是参考自[how2j.cn](http://how2j.cn)的逆向工程，这个可以说是很成熟的模块了，写的很棒，考虑了安全方面的一些东西，链接在这里：<http://how2j.cn/k/tmall_ssm/tmall_ssm-1547/1547.html>

**3）点击运行：**

控制台看到成功的信息之后，就能看到项目中自动多了一堆文件了：



![img](https:////upload-images.jianshu.io/upload_images/7896890-ef97cdf127b3bd41.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/693/format/webp)





![img](https:////upload-images.jianshu.io/upload_images/7896890-bc52c7d5ea0a5a69.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/306/format/webp)



#### RESTful API 设计

为了实现前后端分离，好的RESTful API是离不开的，正好前一段时间学习了这方面的知识，所以决定先来设计一套RESTful API，之前学习的文章链接在这里：<https://www.jianshu.com/p/91600da4df95>

**1）引入Swagger2来构造RESTful API：**

既然想弄一下前后端分离，那就彻底一点儿，写后台完全不管前台，前后台的交互靠一套RESTful API和JSON数据来弄，所以需要一个文档来瞅瞅，首先在pox.xml添加相关依赖：

```
<!--Swagger2支持-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.2.2</version>
</dependency>
```

**2）创建Swagger2配置类：**

在SpringBoot启动类的同级目录下创建Swagger2的配置类【Swagger2】：

```
/**
 * Swagger2 配置类
 *
 * @author:wmyskxz
 * @create:2018-06-14-上午 10:40
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.wmyskxz.blog"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wmyskxz个人博客RESTful APIs")
                .description("原文地址链接：http://blog.didispace.com/springbootswagger2/")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("@我没有三颗心脏")
                .version("1.0")
                .build();
    }

}
```

这样，就可以在我们启动项目之后，访问`http://localhost:8080/swagger-ui.html`地址来查看当前项目中的RESTful风格的API：



![img](https:////upload-images.jianshu.io/upload_images/7896890-c208961e4bd95e19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



**3）设计RESTful API：**

好的，捣鼓了半天，终于有了一些雏形：



![img](https:////upload-images.jianshu.io/upload_images/7896890-9e498dd965dca821.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



但是这也只是设计了API，具体都还没有实现，这些就在写后台的时候来完善了，具体的这些内容怎么显示出来的，我给一个【SortController】的参考类：

```
/**
 * 分类信息控制器
 *
 * @author:wmyskxz
 * @create:2018-06-14-下午 13:25
 */
@RestController
@RequestMapping("/api/sort")
public class SortController {

    /**
     * 获取所有分类信息
     *
     * @return
     */
    @ApiOperation("获取所有分类信息")
    @GetMapping("/list")
    public List<SortInfo> listAllSortInfo() {
        return null;
    }

    /**
     * 通过id获取一条分类信息
     *
     * @param id
     * @return
     */
    @ApiOperation("获取某一条分类信息")
    @ApiImplicitParam(name = "id", value = "分类ID", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public SortInfo getSortInfoById(@PathVariable Long id) {
        return null;
    }

    /**
     * 增加一条分类信息数据
     *
     * @return
     */
    @ApiOperation("增加分类信息")
    @ApiImplicitParam(name = "name", value = "分类名称", required = true, dataType = "String")
    @PostMapping("")
    public String addSortInfo() {
        return null;
    }

    /**
     * 更新/编辑一条数据
     *
     * @param id
     * @return
     */
    @ApiOperation("更新/编辑分类信息")
    @ApiImplicitParam(name = "id", value = "分类ID", required = true, dataType = "Long")
    @PutMapping("/{id}")
    public String updateSortInfo(@PathVariable Long id) {
        return null;
    }

    /**
     * 根据ID删除分类信息
     *
     * @param id
     * @return
     */
    @ApiOperation("删除分类信息")
    @ApiImplicitParam(name = "id", value = "分类ID", required = true, dataType = "Long")
    @DeleteMapping("/{id}")
    public String deleteSortInfo(@PathVariable Long id) {
        return null;
    }
}
```

简单介绍一下这些Swagger2的注解吧：

- @ApiOperation：用于给API设置提示信息，就上图中右边显示的那些，默认不写的情况下是value属性，还可以多写一个notes属性，用于详细的描述API，这里就不需要了，都还比较简单；
- @ApiImplicaitParam：用于说明API的参数信息，加了s的注解同理，写了这个之后呢，我们就可以利用Swagger2给我们的信息页面进行测试了，当然这里没有具体实现，也可以来看一下（下图）；



![img](https:////upload-images.jianshu.io/upload_images/7896890-e320c37bb2974333.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



这里没有具体实现所以就不足以完成测试，等到后台编写的时候再进行测试吧...

------

#### 总结

至此呢，我们项目所需要的准备就差不多完成了，想要去做一个东西必须要清楚的知道要的是一个什么东西，这样才能更加好的完成我们的产品，这也是我喜欢和坚信的事情：**方向永远比努力重要！**（强行有联系..hhhh）

**另外一个问题：** 我在想文章信息和内容分成了两个表的问题，这样的设计我觉得是没有问题的，但是作为前端并不关心这些数据库的设计，他只要能拿到对象就可以了，在设计 API 的时候，就发现获得一篇文章，需要从三个表（文章信息/文章内容/评论）去获取信息并封装返回前端，这就需要自己在后台另外写一个实体类去封装这些信息，这无疑增加了我们的代码工作量，有没有什么好的方法解决呢？

作者：我没有三颗心脏

链接：https://www.jianshu.com/p/0293368fe750

来源：简书

简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。