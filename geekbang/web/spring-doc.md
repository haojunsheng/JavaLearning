# 前言

[Spring官方文档。](https://docs.spring.io/spring-framework/docs/current/reference/html/)

# 核心技术

## 1. IoC容器

## 1.1 IoC容器和Beans的简介

IoC容器负责管理Beans，最核心的是[`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.3.8/javadoc-api/org/springframework/beans/factory/BeanFactory.html) 接口和[`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.8/javadoc-api/org/springframework/context/ApplicationContext.html)接口。其中，后者是前者的子接口，增加了更多的功能特性。

beans是被Spring IoC容器实例化，装配和管理的对象。

## 1.2. 容器简介

![container magic](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost@master/img/20210628211106.png)

### 1.2.1 配置元数据

配置元数据告诉Spring如何实例化，管理Beans对象。

我们一般使用xml或者Java注解。

xml的话，一般是：`<bean/>`，Java注解的话是@Bean注解方法，@Configuration注解类。

### 1.2.2 实例化容器

```
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");
```



### 1.2.3 使用容器

```java
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml", "daos.xml");

// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);

// use configured instance
List<String> userList = service.getUsernameList();
```

## 1.3 Bean概览

BeanDefinition 是 Spring Framework 中定义 Bean 的配置元信息接口。

- 包含包名的类名
- Bean 行为配置元素，如作用域、自动绑定的模式，生命周期回调等
- 其他 Bean 引用，又可称作合作者(collaborators)或者依赖(dependencies)
- 配置设置，比如 Bean 属性(Properties)

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210628231951.png" alt="image-20210628231950382" style="zoom:50%;" />

### 1.3.1 命名Bean

每个 Bean 拥有一个或多个标识符(identifiers)，这些标识符在 Bean 所在的容器必须是唯一 的。通常，一个 Bean 仅有一个标识符，如果需要额外的，可考虑使用别名(Alias)来扩充。

在基于 XML 的配置元信息中，开发人员可用 id 或者 name 属性来规定 Bean 的 标识符。通常 Bean 的 标识符由字母组成，允许出现特殊字符。如果要想引入 Bean 的别名的话，可在 name 属性使用半角逗号(“,”)或分号(“;”) 来间隔。

Bean 的 id 或 name 属性并非必须制定，如果留空的话，容器会为 Bean 自动生成一个唯一的 名称。

### 1.3.2 实例化Bean

- 常规方式

  - 通过构造器(配置元信息:XML、Java 注解和 Java API )
  - 通过静态工厂方法(配置元信息:XML 和 Java API )
  - 通过 Bean 工厂方法(配置元信息:XML和 Java API )
  - 通过 FactoryBean(配置元信息:XML、Java 注解和 Java API )

  ```
  <!-- the factory bean, which contains a method called createInstance() -->
  <bean id="serviceLocator" class="examples.DefaultServiceLocator">
      <!-- inject any dependencies required by this locator bean -->
  </bean>
  
  <!-- the bean to be created via the factory bean -->
  <bean id="clientService"
      factory-bean="serviceLocator"
      factory-method="createClientServiceInstance"/>
  ```

  ```
  public class DefaultServiceLocator {
  
      private static ClientService clientService = new ClientServiceImpl();
  
      public ClientService createClientServiceInstance() {
          return clientService;
      }
  }
  ```

  

## 1.4 Dependencies

用来把多个对象组装在一起进行工作。

### 1.4.1 依赖注入

#### 1.4.1.1 构造函数注入

假设有下面的代码:

```
package examples;

public class ExampleBean {

    // Number of years to calculate the Ultimate Answer
    private int years;

    // The Answer to Life, the Universe, and Everything
    private String ultimateAnswer;

    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

可以根据构造函数类型去匹配：

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg type="int" value="7500000"/>
    <constructor-arg type="java.lang.String" value="42"/>
</bean>
```

可以根据索引去匹配：

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg index="0" value="7500000"/>
    <constructor-arg index="1" value="42"/>
</bean>
```

可以根据名字去匹配：

```
<bean id="exampleBean" class="examples.ExampleBean">
    <constructor-arg name="years" value="7500000"/>
    <constructor-arg name="ultimateAnswer" value="42"/>
</bean>
```

我们也可以使用注解的方式来注入：

```
package examples;

public class ExampleBean {

    // Fields omitted

    @ConstructorProperties({"years", "ultimateAnswer"})
    public ExampleBean(int years, String ultimateAnswer) {
        this.years = years;
        this.ultimateAnswer = ultimateAnswer;
    }
}
```

#### 1.4.1.2 setter注入方式

```
public class SimpleMovieLister {

    // the SimpleMovieLister has a dependency on the MovieFinder
    private MovieFinder movieFinder;

    // a setter method so that the Spring container can inject a MovieFinder
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }

    // business logic that actually uses the injected MovieFinder is omitted...
}
```

### 1.4.2 依赖和配置的细节

#### Straight Values (Primitives, Strings, and so on)

```xml
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <!-- results in a setDriverClassName(String) call -->
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
    <property name="username" value="root"/>
    <property name="password" value="masterkaoli"/>
</bean>
```















