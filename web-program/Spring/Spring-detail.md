

## 1. 认识 Spring 框架

Spring 框架是 Java 应用最广的框架，它的**成功来源于理念，而不是技术本身**，它的理念包括 **IoC (Inversion of Control，控制反转)** 和 **AOP(Aspect Oriented Programming，面向切面编程)**。

[Spring框架的发展历史](https://mp.weixin.qq.com/s/wW6Xpq2X5OB0SWg5bU_tug)

### 什么是 Spring：

1. Spring 是一个**轻量级的 DI / IoC 和 AOP 容器的开源框架**，来源于 Rod Johnson 在其著作**《Expert one on one J2EE design and development》**中阐述的部分理念和原型衍生而来。
2. Spring 提倡以**“最少侵入”**的方式来管理应用中的代码，这意味着我们可以随时安装或者卸载 Spring

- **适用范围：任何 Java 应用**
- **Spring 的根本使命：简化 Java 开发**

### Spring 中常用术语：

- **框架：**是能**完成一定功能**的**半成品**。
  框架能够帮助我们完成的是：**项目的整体框架、一些基础功能、规定了类和对象如何创建，如何协作等**，当我们开发一个项目时，框架帮助我们完成了一部分功能，我们自己再完成一部分，那这个项目就完成了。
- **非侵入式设计：**
  从框架的角度可以理解为：**无需继承框架提供的任何类**。对于E JB、Struts2等一些传统的框架，通常是要实现特定的接口，继承特定的类才能增强功能。
- **轻量级和重量级：**
  轻量级是相对于重量级而言的，**轻量级一般就是非入侵性的、所依赖的东西非常少、资源占用非常少、部署简单等等**，其实就是**比较容易使用**，而**重量级正好相反**。
- **JavaBean：**
  即**符合 JavaBean 规范**的 Java 类
- **POJO：**即 **Plain Old Java Objects，简单老式 Java 对象**
  它可以包含业务逻辑或持久化逻辑，但**不担当任何特殊角色**且**不继承或不实现任何其它Java框架的类或接口。**

*注意：bean 的各种名称——虽然 Spring 用 bean 或者 JavaBean 来表示应用组件，但并不意味着 Spring 组件必须遵循 JavaBean 规范，一个 Spring 组件可以是任意形式的 POJO。*

- **容器：**
  在日常生活中容器就是一种盛放东西的器具，从程序设计角度看就是**装对象的的对象**，因为存在**放入、拿出等**操作，所以容器还要**管理对象的生命周期**。

### Spring 的优势

- **低侵入 / 低耦合** （降低组件之间的耦合度，实现软件各层之间的解耦）

前面我们在写程序的时候，都是面向接口编程，通过**DaoFactroy**等方法来实现松耦合。DAO层和Service层通过DaoFactory来实现松耦合，如果Serivce层直接 new DaoBook() ，那么DAO和 Service就紧耦合了【Service层依赖紧紧依赖于Dao】。

```java
private CategoryDao categoryDao = DaoFactory.getInstance().createDao("zhongfucheng.dao.impl.CategoryDAOImpl", CategoryDao.class);
private BookDao bookDao = DaoFactory.getInstance().createDao("zhongfucheng.dao.impl.BookDaoImpl", BookDao.class);
```

- **声明式事务管理**（基于切面和惯例）
- **方便集成其他框架**（如MyBatis、Hibernate）
- **降低 Java 开发难度**
- Spring 框架中包括了 J2EE 三层的每一层的解决方案（一站式）

### Spring 能帮我们做什么

**①.Spring** 能帮我们根据配置文件**创建及组装对象之间的依赖关系**。
**②.Spring 面向切面编程**能帮助我们**无耦合的实现日志记录，性能统计，安全控制。**
**③.Spring** 能**非常简单的帮我们管理数据库事务**。
**④.Spring** 还**提供了与第三方数据访问框架（如Hibernate、JPA）无缝集成**，而且自己也提供了一套**JDBC访问模板**来方便数据库访问。
**⑤.Spring** 还提供与**第三方Web（如Struts1/2、JSF）框架无缝集成**，而且自己也提供了一套**Spring MVC**框架，来方便web层搭建。
**⑥.Spring** 能**方便的与Java EE（如Java Mail、任务调度）整合**，与**更多技术整合（比如缓存框架）**。

### Spring 的框架结构

<img src="img/image-20200606160159364.png" alt="image-20200606160159364" style="zoom:50%;" />

- **Data Access/Integration层**包含有JDBC、ORM、OXM、JMS和Transaction模块。
- **Web层**包含了Web、Web-Servlet、WebSocket、Web-Porlet模块。
- **AOP模块**提供了一个符合AOP联盟标准的面向切面编程的实现。
- **Core Container(核心容器)：**包含有Beans、Core、Context和SpEL模块。
- **Test模块**支持使用JUnit和TestNG对Spring组件进行测试。

###  Bean的生命周期

Spring容器 从XML 文件中读取bean的定义，并实例化bean。 Spring根据bean的定义填充所有的属性。
 如果bean实现了BeanNameAware 接口，Spring 传递bean 的ID 到 setBeanName方法。 如果Bean 实现了 BeanFactoryAware 接口， Spring传递beanfactory 给setBeanFactory 方法。 如果有任何与bean相关联的BeanPostProcessors，Spring会在 postProcesserBeforeInitialization()方法内调用它们。 如果bean实现IntializingBean了，调用它的afterPropertySet方法，如果bean声明了初始化方 法，调用此初始化方法。

如果有BeanPostProcessors 和bean 关联，这些bean的postProcessAfterInitialization() 方法将 被调用。
 如果bean实现了 DisposableBean，它将调用destroy()方法。

## 2. Spring IoC

IoC：Inverse of Control（控制反转）

- 读作**“反转控制”**，更好理解，不是什么技术，而是一种**设计思想**，就是**将原本在程序中手动创建对象的控制权，交由Spring框架来管理。**控制指的是:当前对象对内部成员的控制权。 反转指的是:这种控制权不由当前对象管理了，由其他(类,第三方容器)来管理。
- **正控：**若要使用某个对象，需要**自己去负责对象的创建**
- **反控：**若要使用某个对象，只需要**从 Spring 容器中获取需要使用的对象，不关心对象的创建过程**，也就是把**创建对象的控制权反转给了Spring框架**
- **好莱坞法则：**Don’t call me ,I’ll call you

### 获取IOC容器

1. 导入jar包

![image-20201021143638345](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021143638.png)

2. 编写配置文件applicationContext.xml

```
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
</beans>
```

3. 获取IOC容器

1） 通过**Resource**获取BeanFactory

```
//加载Spring的资源文件
Resource resource = new ClassPathResource("applicationContext.xml");
//创建IOC容器对象【IOC容器=工厂类+applicationContext.xml】
BeanFactory beanFactory = new XmlBeanFactory(resource);
System.out.println(beanFactory);

org.springframework.beans.factory.xml.XmlBeanFactory@13deb50e: defining beans []; root of factory hierarchy
```

2）获取**ApplicationContext**

```
ApplicationContext context = new ClassPathXmlApplicationContext(
new String[]{"applicationContext.xml"}
);
System.out.println(context);

org.springframework.context.support.ClassPathXmlApplicationContext@dc24521, started on Wed Oct 21 14:38:49 CST 2020
```

### 装配Bean

#### xml方式

1）装配简单值

```
<bean id="c" class="pojo.Category">
    <property name="name" value="测试" />
</bean>
```

- `id` 属性是 Spring 能找到当前 Bean 的一个依赖的编号，**遵守 XML 语法的 ID 唯一性约束。必须以字母开头，**可以使用*字母、数字、连字符、下划线、句号、冒号*，**不能以 `/` 开头**。
  不过 `id` 属性**不是一个必需的属性**，`name` 属性也可以定义 bean 元素的名称，能以逗号或空格隔开**起多个别名**，并且可以**使用很多的特殊字符**，比如在 Spring 和 Spring MVC 的整合中，就得使用 `name` 属性来定义 bean 的名称，并且使用 `/` 开头。
- 如果 `id` 和 `name` 属性都没有声明的话，那么 Spring 将会采用 **“全限定名#{number}”** 的格式生成编号。 例如这里，如果没有声明 “`id="c"`” 的话，那么 Spring 为其生成的编号就是 “`pojo.Category#0`”，当它第二次声明没有 `id` 属性的 Bean 时，编号就是 “`pojo.Category#1`”，以此类推。
- `class` 属性显然就是一个类的全限定名
- `property` 元素是定义类的属性，其中的 `name` 属性定义的是属性的名称，而 `value` 是它的值。
- **注入对象：**使用 `ref` 属性

2）装配集合

```xml
<bean id="complexAssembly" class="pojo.ComplexAssembly">
    <!-- 装配Long类型的id -->
    <property name="id" value="1"/>

    <!-- 装配List类型的list -->
    <property name="list">
        <list>
            <value>value-list-1</value>
            <value>value-list-2</value>
            <value>value-list-3</value>
        </list>
    </property>

    <!-- 装配Map类型的map -->
    <property name="map">
        <map>
            <entry key="key1" value="value-key-1"/>
            <entry key="key2" value="value-key-2"/>
            <entry key="key3" value="value-key-2"/>
        </map>
    </property>

    <!-- 装配Properties类型的properties -->
    <property name="properties">
        <props>
            <prop key="prop1">value-prop-1</prop>
            <prop key="prop2">value-prop-2</prop>
            <prop key="prop3">value-prop-3</prop>
        </props>
    </property>

    <!-- 装配Set类型的set -->
    <property name="set">
        <set>
            <value>value-set-1</value>
            <value>value-set-2</value>
            <value>value-set-3</value>
        </set>
    </property>

    <!-- 装配String[]类型的array -->
    <property name="array">
        <array>
            <value>value-array-1</value>
            <value>value-array-2</value>
            <value>value-array-3</value>
        </array>
    </property>
</bean>
```

- List 属性为对应的 `<list>` 元素进行装配，然后通过多个 `<value>` 元素设值
- Map 属性为对应的 `<map>` 元素进行装配，然后通过多个 `<entry>` 元素设值，只是 `entry` 包含一个键值对(key-value)的设置
- Properties 属性为对应的 `<properties>` 元素进行装配，通过多个 `<property>` 元素设值，只是 `properties` 元素有一个必填属性 `key` ，然后可以设置值
- Set 属性为对应的 `<set>` 元素进行装配，然后通过多个 `<value>` 元素设值
- 对于数组而言，可以使用 `<array>` 设置值，然后通过多个 `<value>` 元素设值。

3） 更加复杂的

- List 属性使用 `<list>` 元素定义注入，使用多个 `<ref>` 元素的 Bean 属性去引用之前定义好的 Bean

```xml
COPY<property name="list">
    <list>
        <ref bean="bean1"/>
        <ref bean="bean2"/>
    </list>
</property>
```

- Map 属性使用 `<map>` 元素定义注入，使用多个 `<entry>` 元素的 `key-ref` 属性去引用之前定义好的 Bean 作为键，而用 `value-ref` 属性引用之前定义好的 Bean 作为值

```xml
COPY<property name="map">
    <map>
        <entry key-ref="keyBean" value-ref="valueBean"/>
    </map>
</property>
```

- Set 属性使用 `<set>` 元素定义注入，使用多个 `<ref>` 元素的 `bean` 去引用之前定义好的 Bean

```xml
COPY<property name="set">
    <set>
        <ref bean="bean"/>
    </set>
</property>
```

具体的demo：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
<bean name="source" class="pojo.Source">
<property name="fruit" value="橙子"/>
<property name="sugar" value="多糖"/>
<property name="size" value="超大杯"/>
</bean>
</beans>

ApplicationContext context = new ClassPathXmlApplicationContext(
"applicationContext.xml");
Source source = (Source) context.getBean("source");
System.out.println(source.getFruit());
System.out.println(source.getSugar());
System.out.println(source.getSize());

```

 补充：

新增JuiceMaker

```
package pojo;

public class JuiceMaker {

    // 唯一关联了一个 Source 对象
    private Source source = null;

    /* setter and getter */

    public String makeJuice(){
        String juice = "xxx用户点了一杯" + source.getFruit() + source.getSugar() + source.getSize();
        return juice;
    }
}

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean name="source" class="pojo.Source">
        <property name="fruit" value="橙子"/>
        <property name="sugar" value="多糖"/>
        <property name="size" value="超大杯"/>
    </bean>
    <bean name="juickMaker" class="pojo.JuiceMaker">
        <property name="source" ref="source" />
    </bean>
</beans>

JuiceMaker juiceMaker = (JuiceMaker) context.getBean("juickMaker");
System.out.println(juiceMaker.makeJuice());
```

补充2：带参数的构造函数

```
 <bean name="source" class="pojo.Source">
        <constructor-arg index="0" name="fruit" type="java.lang.String" value="橙子"/>
        <constructor-arg index="1" name="sugar" type="java.lang.String" value="多糖"/>
        <constructor-arg index="2" name="size" type="java.lang.String" value="超大杯"/>
    </bean>
# 在constructor上如果构造函数的值是一个对象，而不是一个普通类型的值，我们就需要用到ref属性 了，而不是value属性
```

#### 注解

1) 指定扫描包

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="hjs"></context:component-scan>
</beans>
```

2）测试代码

```java
@Repository
public class UserDao {
    public void save() {
        System.out.println("DB:保存用户");
    }
}

//把UserService对象添加到IOC容器中,首字母会小写
@Service
public class UserService {
    //如果@Resource不指定值，那么就根据类型来找--->UserDao....当然了，IOC容器不能有两个 UserDao类型的对象
//@Resource
//如果指定了值，那么Spring就在IOC容器找有没有id为userDao的对象。
    @Resource(name = "userDao")
    private UserDao userDao;

    public void save() {
        userDao.save();
    }
}

//把对象添加到IOC容器中,首字母会小写
@Controller
public class UserAction {
    @Resource(name = "userService")
    private UserService userService;

    public String execute() {
        userService.save();
        return null;
    }
}

public class App {
    public static void main(String[] args) {
        // 创建容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("hjs/applicationContext.xml");
        UserAction userAction = (UserAction) ac.getBean("userAction");
        userAction.execute();
    }
}
```

![image-20201021150846547](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021150846.png)

相关注解：

@ComponentScan扫描器
 @Configuration表明该类是配置类
 @Component 指定把一个对象加入IOC容器--->@Name也可以实现相同的效果【一般少用】 @Repository 作用同@Component; 在持久层使用
 @Service 作用同@Component; 在业务逻辑层使用
 @Controller 作用同@Component; 在控制层使用
 @Resource 依赖关系：如果@Resource不指定值，那么就根据类型来找，相同的类型在IOC容器中不能有两个 ；如果@Resource指定了值，那么就根据名字来找。



###  bean的定义和初始化

1. 定义

   1）Resource 定位

Spring IoC 容器先根据开发者的配置，进行资源的定位，在 Spring 的开发中，通过 XML 或者注解都是十分常见的方式，定位的内容是由开发者提供的。

​      2）BeanDefinition 的载入

这个时候只是将 Resource 定位到的信息，保存到 Bean 定义（BeanDefinition）中，此时并不会创建 Bean 的实例。

   3) BeanDefinition 的注册

这个过程就是将 BeanDefinition 的信息发布到 Spring IoC 容器中。

2. 初始化和依赖注入

会自动初始化bean，如果**lazy-init**设置为true，则会在getBean的时候进行初始化。



Spring 提供了 5 种作用域，它会根据情况来决定是否生成新的对象：

| 作用域类别              | 描述                                                         |
| ----------------------- | ------------------------------------------------------------ |
| singleton(单例)         | 在Spring IoC容器中仅存在一个Bean实例 （默认的scope）         |
| prototype(多例)         | 每次从容器中调用Bean时，都返回一个新的实例，即每次调用getBean()时 ，相当于执行new XxxBean()：不会在容器启动时创建对象 |
| request(请求)           | 用于web开发，将Bean放入request范围 ，request.setAttribute(“xxx”) ， 在同一个request 获得同一个Bean |
| session(会话)           | 用于web开发，将Bean 放入Session范围，在同一个Session 获得同一个Bean |
| globalSession(全局会话) | 一般用于 Porlet 应用环境 , 分布式系统存在全局 session 概念（单点登录），如果不是 porlet 环境，globalSession 等同于 Session |

在开发中主要使用 `scope="singleton"`、`scope="prototype"`，**对于MVC中的Action使用prototype类型，其他使用singleton**，Spring容器会管理 Action 对象的创建,此时把 Action 的作用域设置为 prototype.



<img src="img/image-20200606170334265.png" alt="image-20200606170334265" style="zoom:50%;" />

<img src="img/image-20200606170357285.png" alt="image-20200606170357285" style="zoom: 50%;" />

<img src="img/image-20200606170509404.png" alt="image-20200606170509404" style="zoom:50%;" />

<img src="img/image-20200606170542971.png" alt="image-20200606170542971" style="zoom:50%;" />

<img src="img/image-20200606170924902.png" alt="image-20200606170924902" style="zoom:50%;" />

- BeanDefinitionReader读取**Resource**所指向的配置文件资源，然后解析配置文件。配置文件中每 一个 <bean> 解析成一个**BeanDefinition**对象，并保存到BeanDefinitionRegistry中; 

- **容器扫描BeanDefinitionRegistry中的BeanDefinition;调用InstantiationStrategy进行**Bean**实例 化的工作;使用**BeanWrapper**完成**Bean属性的设置工作;

- 单例Bean缓存池:Spring 在DefaultSingletonBeanRegistry类中提供了一个用于缓存单实例 Bean 的缓存器，它是一个用HashMap实现的缓存器，单实例的Bean以**beanName**为键保存在这 个**HashMap**中。

## 3. Spring AOP 简介

如果说 IoC 是 Spring 的核心，那么面向切面编程就是 Spring 最为重要的功能之一了，在数据库事务中切面编程被广泛使用。

AOP 即 Aspect Oriented Program 面向切面编程，首先，在面向切面编程的思想里面，把功能分为核心业务功能，和周边功能。

- **所谓的核心业务**，比如登陆，增加数据，删除数据都叫核心业务
- **所谓的周边功能**，比如性能统计，日志，事务管理等等

周边功能在 Spring 的面向切面编程AOP思想里，即被定义为切面

**在面向切面编程AOP的思想里面，核心业务功能和切面功能分别独立进行开发，然后把切面功能和核心业务功能 "编织" 在一起，这就叫AOP**

AOP能够将那些与业务无关，**却为业务模块所共同调用的逻辑或责任（例如事务处理、日志管理、权限控制等）封装起来**，便于**减少系统的重复代码**，**降低模块间的耦合度**，并**有利于未来的可拓展性和可维护性**。

### AOP 当中的概念：

- 切入点（Pointcut）：在哪些类，哪些方法上切入（**where**）
- 通知（Advice）：在方法执行的什么实际（**when:**方法前/方法后/方法前后）做什么（**what:**增强的功能）
- 切面（Aspect）：切面 = 切入点 + 通知，通俗点就是：**在什么时机，什么地方，做什么增强！**
- 织入（Weaving）：把切面加入到对象，并创建出代理对象的过程。（由 Spring 来完成）

AOP实例：

```
package service;
public class ProductService {
    public void doSomeService(){
        System.out.println("doSomeService");
    }
}
// xml中装配
<bean name="productService" class="service.ProductService" />
// 测试
ProductService productService = (ProductService) context.getBean("productService");
productService.doSomeService();
```

开始AOP：

```
package aspect;

import org.aspectj.lang.ProceedingJoinPoint;

public class LoggerAspect {

    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log:" + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log:" + joinPoint.getSignature().getName());
        return object;
    }
}

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
   http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/aop
   http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <bean name="productService" class="service.ProductService" />
    <bean id="loggerAspect" class="aspect.LoggerAspect"/>

    <!-- 配置AOP -->
    <aop:config>
        <!-- where：在哪些地方（包.类.方法）做增加 -->
        <aop:pointcut id="loggerCutpoint"
                      expression="execution(* service.ProductService.*(..)) "/>

        <!-- what:做什么增强 -->
        <aop:aspect id="logAspect" ref="loggerAspect">
            <!-- when:在什么时机（方法前/后/前后） -->
            <aop:around pointcut-ref="loggerCutpoint" method="log"/>
        </aop:aspect>
    </aop:config>
</beans>
```

再次运行：

![image-20201021153005665](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021153005.png)



# 4. Spring连接数据库

