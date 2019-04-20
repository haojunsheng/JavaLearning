## MyBatis 简介

MyBatis 本是apache的一个开源项目iBatis, 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis，是一个基于Java的持久层框架。

-  **持久层：** 可以将业务数据**存储到磁盘，具备长期存储能力**，只要磁盘不损坏，在断电或者其他情况下，重新开启系统仍然可以读取到这些数据。
-  **优点：** 可以**使用巨大的磁盘空间**存储相当量的数据，并且很**廉价** 
-  **缺点：慢**（相对于内存而言）

#### 为什么使用 MyBatis

在我们**传统的 JDBC 中**，我们除了需要自己提供 SQL 外，还必须操作 Connection、Statment、ResultSet，不仅如此，为了访问不同的表，不同字段的数据，我们需要些很多雷同模板化的代码，闲的**繁琐又枯燥**。

而我们在使用了 **MyBatis** 之后，**只需要提供 SQL 语句就好了**，其余的诸如：建立连接、操作 Statment、ResultSet，处理 JDBC 相关异常等等都可以交给 MyBatis 去处理，我们的**关注点于是可以就此集中在 SQL 语句上**，关注在增删改查这些操作层面上。

并且 MyBatis 支持使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

------

## 搭建 MyBatis 环境

首先，我们需要先下载和搭建 MyBatis 的开发环境。

#### 下载 MyBatis 工程包

打开链接 <http://github.com/mybatis/mybatis-3/releases> 下载 MyBatis 所需要的包和源码，当前最新版本为 3.4.6，官方还提供了文档： [戳这里](http://www.mybatis.org/mybatis-3/zh/index.html)，虽然感觉写得一般，但还是有一些参考价值...唉，别当教程看，当字典看！



![img](https:////upload-images.jianshu.io/upload_images/7896890-ee64e4e10d1417c4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



下载好 MyBatis 的包解压后，可以得到以下的文件目录：



![img](https:////upload-images.jianshu.io/upload_images/7896890-4e53628814f8a285.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/623/format/webp)



其中 mybatis-3.4.6.jar 包就是 MyBatis 的项目工程包，【lib】文件夹下就是 MyBatis 项目需要依赖的第三方包，pdf 文件是它英文版的说明，不要英文也可以戳上面的链接。

#### 为 IDEA 配置 MyBatis 环境

IDEA 默认是不支持 MyBatis 开发的，需要自己下载第三方插件来支持，可惜的是功能强大的【MyBatis Plugin】是收费的，需要我们自己破解！

#### 第一步：在 IDEA 中下载 MyBatis Plugin

在【File】菜单下找到【Settings】，然后再【Plugins】下点击【Browse repositories..】：



![img](https:////upload-images.jianshu.io/upload_images/7896890-ef2c926134056cc3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



在搜索栏中输入【MyBatis Plugin】，然后点击【Install】（我这里是安装好了所以没有这个按钮）：



![img](https:////upload-images.jianshu.io/upload_images/7896890-893e957096a5219b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/826/format/webp)



#### 第二步：破解

有幸找到最新的[破解方法](https://shawnho.me/2017/12/20/ideaagent/)，最新支持破解的版本号为：v3.58 crack，下载链接：[戳这里](https://github.com/mrshawnho/ideaagent/releases)



![img](https:////upload-images.jianshu.io/upload_images/7896890-307aedb0c5f98f56.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



把它下载到 【D:\Download\】目录下，打开 idea.vmoptions （【Help】`->` 【Eidt Custom VM Options...】）：
 在下方插入 `-javaagent:D:/Download/ideaagent-1.2.jar`



![img](https:////upload-images.jianshu.io/upload_images/7896890-2b61569cdd41ed6d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/728/format/webp)



重启 IDEA，首次启动需要信任本地服务器 ssl 证书，点击接受后如未激活，再次重启即可：



![img](https:////upload-images.jianshu.io/upload_images/7896890-b03ccfdd09445118.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp)



至此，我们就为 IDEA 配置好了 MyBatis 的开发环境，可以检验一下是否安装成功：



![img](https:////upload-images.jianshu.io/upload_images/7896890-9be9d1f19a7428b4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/761/format/webp)



------

## 第一个 MyBatis 程序

我们来实际开发一个 MyBatis 程序，感受一下。

#### 第一步：准备数据库

首先我们创建一个数据库【mybatis】，编码方式设置为 UTF-8，然后再创建一个名为【student】的表，插入几行数据：

```
DROP DATABASE IF EXISTS mybatis;
CREATE DATABASE mybatis DEFAULT CHARACTER SET utf8;

use mybatis;
CREATE TABLE student(
  id int(11) NOT NULL AUTO_INCREMENT,
  studentID int(11) NOT NULL UNIQUE,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO student VALUES(1,1,'我没有三颗心脏');
INSERT INTO student VALUES(2,2,'我没有三颗心脏');
INSERT INTO student VALUES(3,3,'我没有三颗心脏');
```

#### 第二步：创建工程

在 IDEA 中新建一个 Java 工程，并命名为【HelloMybatis】，然后导入必要的 jar 包：

- mybatis-3.4.6.jar
- mysql-connector-java-5.1.21-bin.jar



![img](https:////upload-images.jianshu.io/upload_images/7896890-d84c564a30836343.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/372/format/webp)



#### 第三步：创建实体类

在 Package【pojo】下新建实体类【Student】，用于映射表 student：

```
package pojo;

public class Student {

    int id;
    int studentID;
    String name;

    /* getter and setter */
}
```

#### 第四步：配置文件 mybatis-config.xml

在【src】目录下创建 MyBaits 的主配置文件 `mybatis-config.xml` ，其主要作用是提供连接数据库用的驱动，数据名称，编码方式，账号密码等，我们在后面说明：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!-- 别名 -->
    <typeAliases>
        <package name="pojo"/>
    </typeAliases>
    <!-- 数据库环境 -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8"/>
                <property name="username" value="root"/>
                <property name="password" value="root"/>
            </dataSource>
        </environment>
    </environments>
    <!-- 映射文件 -->
    <mappers>
        <mapper resource="pojo/Student.xml"/>
    </mappers>

</configuration>
```

#### 第五步：配置文件 Student.xml

在 Package【pojo】下新建一个【Student.xml】文件：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="pojo">
    <select id="listStudent" resultType="Student">
        select * from  student
    </select>
</mapper>
```

- 由于上面配置了 `<typeAliases>` 别名，所以在这里的 `resultType` 可以直接写 Student，而不用写类的全限定名 pojo.Student
-  `namespace` 属性其实就是对 SQL 进行分类管理，实现不同业务的 SQL 隔离
- SQL 语句的增删改查对应的标签有：

#### 第六步：编写测试类

在 Package【test】小创建测试类【TestMyBatis】：

```
package test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    public static void main(String[] args) throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();
        // 最后通过 session 的 selectList() 方法调用 sql 语句 listStudent
        List<Student> listStudent = session.selectList("listStudent");
        for (Student student : listStudent) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }

    }
}
```

运行测试类：

![image-20190318221732460](https://ws3.sinaimg.cn/large/006tKfTcly1g17b5dacapj30sc098jw0.jpg)



#### 基本原理

- 应用程序找 MyBatis 要数据
- MyBatis 从数据库中找来数据
   1.通过 mybatis-config.xml 定位哪个数据库
   2.通过 Student.xml 执行对应的 sql 语句
   3.基于 Student.xml 把返回的数据库封装在 Student 对象中
   4.把多个 Student 对象装载一个 Student 集合中
- 返回一个 Student 集合



![img](https:////upload-images.jianshu.io/upload_images/7896890-805df95a65b023e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/772/format/webp)



> 参考资料：[How2j.cn-MyBatis 相关教程](http://how2j.cn/k/mybatis/mybatis-tutorial/1087.html#nowhere)

------

## CRUD 操作

我们来看看常规的一套增删改查应该怎么实现：

#### 第一步：配置 Student.xml

首先，我们在 SQL 映射文件中新增语句，用来支撑 CRUD 的系列操作

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="pojo">
    <select id="listStudent" resultType="Student">
        select * from  student
    </select>

    <insert id="addStudent" parameterType="Student">
        insert into student (id, studentID, name) values (#{id},#{studentID},#{name})
    </insert>

    <delete id="deleteStudent" parameterType="Student">
        delete from student where id = #{id}
    </delete>

    <select id="getStudent" parameterType="_int" resultType="Student">
        select * from student where id= #{id}
    </select>

    <update id="updateStudent" parameterType="Student">
        update student set name=#{name} where id=#{id}
    </update>
</mapper>
```

- parameterType：要求输入参数的类型
- resultType：输出的类型

#### 第二步：实现增删改查

```
package test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pojo.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMyBatis {

    public static void main(String[] args) throws IOException {
        // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 然后根据 sqlSessionFactory 得到 session
        SqlSession session = sqlSessionFactory.openSession();

        // 增加学生
        Student student1 = new Student();
        student1.setId(4);
        student1.setStudentID(4);
        student1.setName("新增加的学生");
        session.insert("addStudent", student1);

        // 删除学生
        Student student2 = new Student();
        student2.setId(1);
        session.delete("deleteStudent", student2);

        // 获取学生
        Student student3 = session.selectOne("getStudent", 2);

        // 修改学生
        student3.setName("修改的学生");
        session.update("updateStudent", student3);

        // 最后通过 session 的 selectList() 方法调用 sql 语句 listStudent
        List<Student> listStudent = session.selectList("listStudent");
        for (Student student : listStudent) {
            System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
        }

        // 提交修改
        session.commit();
        // 关闭 session
        session.close();
    }
}
```

上述的程序中：

- 通过 `session.insert("addStudent", student1);` 增加了一个 ID 和 studentID 都为 4，名字为“新增加的学生” 的学生
- 通过 `session.delete("deleteStudent", student2);` 删除了 ID = 1 的学生
- 通过 `Student student3 = session.selectOne("getStudent", 2);` 获取了 ID = 2的学生
- 通过 `session.update("updateStudent", student3);` 将 ID = 2 的学生的名字修改为 “修改的学生”
- 通过 `session.commit()` 来提交事务，也可以简单理解为更新到数据库

运行获得正确结果：



![img](https:////upload-images.jianshu.io/upload_images/7896890-255365369cb37257.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/584/format/webp)



#### 模糊查询

如果要对数据库中的 student 表进行模糊查询，需要通过匹配名字中的某个字来查询该用户。

我们首先在 Student.xml 配置文件中配置 SQL 映射：

```
<select id="findStudentByName" parameterMap="java.lang.String" resultType="Student">
    SELECT * FROM student WHERE name LIKE '%${value}%' 
</select>
```

-  **注意：** `<select>` 标签对中 SQL 语句的 “${}” 符号，表示拼接 SQL 串，将接受的参数内容**不加任何修饰地拼接在 SQL 中，在 “${}” 中只能使用 value 来代表其中的参数。** 

因为是模糊查询，所以得到的查询结果可能不止一个，所以我们使用 SqlSession 的 selectList() 方法，写一个测试方法：

```
@Test
public void test() throws IOException {

    // 根据 mybatis-config.xml 配置的信息得到 sqlSessionFactory
    String resource = "mybatis-config.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    // 然后根据 sqlSessionFactory 得到 session
    SqlSession session = sqlSessionFactory.openSession();

    // 模糊查询
    List<Student> students = session.selectList("findStudentByName", "三颗心脏");
    for (Student student : students) {
        System.out.println("ID:" + student.getId() + ",NAME:" + student.getName());
    }
}
```

测试结果：



![img](https:////upload-images.jianshu.io/upload_images/7896890-82018fbe86b9da7c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/587/format/webp)



#### 总结一下

-  **关于 parameterType：** 就是用来在 SQL 映射文件中指定输入参数类型的，可以指定为基本数据类型（如 int、float 等）、包装数据类型（如 String、Interger 等）以及用户自己编写的 JavaBean 封装类
-  **关于 resultType：** 在加载 SQL 配置，并绑定指定输入参数和运行 SQL 之后，会得到数据库返回的响应结果，此时使用 resultType 就是用来指定数据库返回的信息对应的 Java 的数据类型。
-  **关于 “#{}” ：** 在传统的 JDBC 的编程中，占位符用 “?” 来表示，然后再加载 SQL 之前按照 “?” 的位置设置参数。而 “#{}” 在 MyBatis 中也代表一种占位符，该符号接受输入参数，在大括号中编写参数名称来接受对应参数。当 “#{}” 接受简单类型时可以用 `value` 或者其他任意名称来获取。
-  **关于 “${}” ：** 在 SQL 配置中，有时候需要拼接 SQL 语句（例如模糊查询时），用 “#{}” 是无法达到目的的。在 MyBatis 中，“${}” 代表一个 “拼接符号” ，可以在原有 SQL 语句上拼接新的符合 SQL 语法的语句。使用 “${}” 拼接符号拼接 SQL ，会引起 SQL 注入，所以一般不建议使用 “${}”。
-  **MyBatis 使用场景：** 通过上面的入门程序，不难看出在进行 MyBatis 开发时，我们的大部分精力都放在了 SQL 映射文件上。 **MyBatis 的特点就是以 SQL 语句为核心的不完全的 ORM（关系型映射）框架。**与 Hibernate 相比，Hibernate 的学习成本比较高，而 SQL 语句并不需要开发人员完成，只需要调用相关 API 即可。这对于开发效率是一个优势，但是缺点是没办法对 SQL 语句进行优化和修改。而 MyBatis 虽然需要开发人员自己配置 SQL 语句，MyBatis 来实现映射关系，但是这样的项目可以适应经常变化的项目需求。**所以使用 MyBatis 的场景是：对 SQL 优化要求比较高，或是项目需求或业务经常变动。**

作者：我没有三颗心脏

链接：https://www.jianshu.com/p/c77e3691867d

来源：简书

简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。