## MVC 设计概述

在早期 Java Web 的开发中，统一把显示层、控制层、数据层的操作全部交给 JSP 或者 JavaBean 来进行处理，我们称之为 **Model1：**

![image-20190305151404188](https://ws3.sinaimg.cn/large/006tKfTcly1g0rxuo4gu1j31280hgdj8.jpg)

- **出现的弊端：**
- JSP 和 Java Bean 之间严重耦合，Java 代码和 HTML 代码也耦合在了一起
- 要求开发者不仅要掌握 Java ，还要有高超的前端水平
- 前端和后端相互依赖，前端需要等待后端完成，后端也依赖前端完成，才能进行有效的测试
- 代码难以复用

正因为上面的种种弊端，所以很快这种方式就被 Servlet + JSP + Java Bean 所替代了，早期的 MVC 模型**（Model2）**就像下图这样：



![image-20190305151432283](https://ws3.sinaimg.cn/large/006tKfTcly1g0rxv5xjtbj310y0ga447.jpg)

首先用户的请求会到达 Servlet，然后根据请求调用相应的 Java Bean，并把所有的显示结果交给 JSP 去完成，这样的模式我们就称为 MVC 模式。

- **M 代表 模型（Model）**
  模型是什么呢？ 模型就是数据，就是 dao,bean
- **V 代表 视图（View）**
  视图是什么呢？ 就是网页, JSP，用来展示模型中的数据
- **C 代表 控制器（controller)**
  控制器是什么？ 控制器的作用就是把不同的数据(Model)，显示在不同的视图(View)上，Servlet 扮演的就是这样的角色。

> 扩展阅读：[Web开发模式](https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247483775&idx=1&sn=c9d7ead744c6e0c3ab2fe55c09bbe61f&chksm=ebd7407edca0c9688f3870d895b760836101271b912899821fb35c5704fe215da2fc5daff2f9#rd)

#### Spring MVC 的架构

为解决持久层中一直未处理好的数据库事务的编程，又为了迎合 NoSQL 的强势崛起，Spring MVC 给出了方案：

![image-20190305151650471](https://ws2.sinaimg.cn/large/006tKfTcly1g0rxxk45f7j312w0im44i.jpg)

**传统的模型层被拆分为了业务层(Service)和数据访问层（DAO,Data Access Object）。** 在 Service 下可以通过 Spring 的声明式事务操作数据访问层，而在业务层上还允许我们访问 NoSQL ，这样就能够满足异军突起的 NoSQL 的使用了，它可以大大提高互联网系统的性能。

- **特点：**
  结构松散，几乎可以在 Spring MVC 中使用各类视图
  松耦合，各个模块分离
  与 Spring 无缝集成

------

## Hello Spring MVC

让我们来写一下我们的第一个 Spring MVC 程序：

#### 第一步：在 IDEA 中新建 Spring MVC 项目

![image-20190305151909634](https://ws2.sinaimg.cn/large/006tKfTcly1g0rxzz7mumj310n0u0k5t.jpg)并且取名为 【HelloSpringMVC】，点击【Finish】：



![image-20190316113747734](https://ws2.sinaimg.cn/large/006tKfTcly1g14hf61fjuj30lg0emjw1.jpg)

IDEA 会自动帮我们下载好必要的 jar 包，并且为我们创建好一些默认的目录和文件，创建好以后项目结构如下：



#### 第二步：修改 web.xml

我们打开 web.xml ，按照下图完成修改：



![image-20190316113838268](https://ws2.sinaimg.cn/large/006tKfTcly1g14hfzos8pj313c0f24d6.jpg)

把`<url-pattern>`元素的值改为 / ，表示要拦截所有的请求，并交由Spring MVC的后台控制器来处理，改完之后：

```
<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

#### 第三步：编辑 dispatcher-servlet.xml

这个文件名的开头 dispatcher 与上面 web.xml 中的 `<servlet-name>` 元素配置的 dispatcher 对应，这是 Spring MVC 的映射配置文件（xxx-servlet.xml），我们编辑如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="simpleUrlHandlerMapping"
          class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
        <property name="mappings">
            <props>
                <!-- /hello 路径的请求交给 id 为 helloController 的控制器处理-->
                <prop key="/hello">helloController</prop>
            </props>
        </property>
    </bean>
    <bean id="helloController" class="controller.HelloController"></bean>
</beans>
```

#### 第四步：编写 HelloController

在 Package【controller】下创建 【HelloController】类，并实现 org.springframework.web.servlet.mvc.Controller 接口：

```
package controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller{
    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }
}
```

- **出现了问题：** javax.servlet 包找不到
- **解决：** 将本地 Tomcat 服务器的目录下【lib】文件夹下的 servlet-api.jar 包拷贝到工程【lib】文件夹下，添加依赖

Spring MVC 通过 ModelAndView 对象把模型和视图结合在一起

```
ModelAndView mav = new ModelAndView("index.jsp");
mav.addObject("message", "Hello Spring MVC");
```

这里表示视图的是index.jsp
模型数据的是 message，内容是 “Hello Spring MVC”

```
package controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloController implements Controller {

    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index.jsp");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}
```

#### 第五步：准备 index.jsp

将 index.jsp 的内容修改为：

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
 
<h1>${message}</h1>
```

内容很简单，用El表达式显示 message 的内容。

#### 第六步：部署 Tomcat 及相关环境

在【Run】菜单项下找到【Edit Configurations】





配置 Tomcat 环境：





选择好本地的 Tomcat 服务器，并改好名字：





在 Deployment 标签页下完成如下操作：



点击 OK 就好了，我们点击右上角的三角形将 Tomcat 服务器运行起来。

- **出现的问题：** Tomcat 服务器无法正常启动
- **原因：** Tomcat 服务器找不到相关的 jar 包
- **解决方法：** 将【lib】文件夹整个剪贴到【WEB-INF】下，并重新建立依赖：

![image-20190316115013908](https://ws3.sinaimg.cn/large/006tKfTcly1g14hs27pnij30lq0wc7lu.jpg)

#### 第七步：重启服务器

重启服务器，输入地址：localhost/hello

![image-20190316115108699](https://ws4.sinaimg.cn/large/006tKfTcly1g14ht0usylj314i0c6wie.jpg)



> 参考资料：[Spring MVC 教程(how2j.cn)](http://how2j.cn/k/springmvc/springmvc-springmvc/615.html#step1891)

------

## 跟踪 Spring MVC 的请求

每当用户在 Web 浏览器中点击链接或者提交表单的时候，请求就开始工作了，像是邮递员一样，从离开浏览器开始到获取响应返回，它会经历很多站点，在每一个站点都会留下一些信息同时也会带上其他信息，下图为 Spring MVC 的请求流程：



![image-20190316121547430](https://ws4.sinaimg.cn/large/006tKfTcly1g14iinhzr1j311i0lejym.jpg)

#### 第一站：DispatcherServlet

从请求离开浏览器以后，第一站到达的就是 DispatcherServlet，看名字这是一个 Servlet，通过 J2EE 的学习，我们知道 Servlet 可以拦截并处理 HTTP 请求，DispatcherServlet 会拦截所有的请求，并且将这些请求发送给 Spring MVC 控制器。

```
<servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <!-- 拦截所有的请求 -->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

- **DispatcherServlet 的任务就是拦截请求发送给 Spring MVC 控制器。**

#### 第二站：处理器映射（HandlerMapping）

- **问题：** 典型的应用程序中可能会有多个控制器，这些请求到底应该发给哪一个控制器呢？

所以 DispatcherServlet 会查询一个或多个处理器映射来确定请求的下一站在哪里，处理器映射会**根据请求所携带的 URL 信息来进行决策**，例如上面的例子中，我们通过配置 simpleUrlHandlerMapping 来将 /hello 地址交给 helloController 处理：

```
<bean id="simpleUrlHandlerMapping"
      class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
    <property name="mappings">
        <props>
            <!-- /hello 路径的请求交给 id 为 helloController 的控制器处理-->
            <prop key="/hello">helloController</prop>
        </props>
    </property>
</bean>
<bean id="helloController" class="controller.HelloController"></bean>
```

#### 第三站：控制器

一旦选择了合适的控制器， DispatcherServlet 会将请求发送给选中的控制器，到了控制器，请求会卸下其负载（用户提交的请求）等待控制器处理完这些信息：

```
public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
    // 处理逻辑
    ....
}
```

#### 第四站：返回 DispatcherServlet

当控制器在完成逻辑处理后，通常会产生一些信息，这些信息就是需要返回给用户并在浏览器上显示的信息，它们被称为**模型（Model）**。仅仅返回原始的信息时不够的——这些信息需要以用户友好的方式进行格式化，一般会是 HTML，所以，信息需要发送给一个**视图（view）**，通常会是 JSP。

控制器所做的最后一件事就是将模型数据打包，并且表示出用于渲染输出的视图名**（逻辑视图名）。它接下来会将请求连同模型和视图名发送回 DispatcherServlet。**

```
public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
    // 处理逻辑
    ....
    // 返回给 DispatcherServlet
    return mav;
}
```

#### 第五站：视图解析器

这样以来，控制器就不会和特定的视图相耦合，传递给 DispatcherServlet 的视图名并不直接表示某个特定的 JSP。（实际上，它甚至不能确定视图就是 JSP）相反，**它传递的仅仅是一个逻辑名称，这个名称将会用来查找产生结果的真正视图。**

DispatcherServlet 将会使用视图解析器（view resolver）来将逻辑视图名匹配为一个特定的视图实现，它可能是也可能不是 JSP

> 上面的例子是直接绑定到了 index.jsp 视图

#### 第六站：视图

既然 DispatcherServlet 已经知道由哪个视图渲染结果了，那请求的任务基本上也就完成了。

它的最后一站是视图的实现，在这里它交付模型数据，请求的任务也就完成了。视图使用模型数据渲染出结果，这个输出结果会通过响应对象传递给客户端。

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<h1>${message}</h1>
```

------

## 使用注解配置 Spring MVC

上面我们已经对 Spring MVC 有了一定的了解，并且通过 XML 配置的方式创建了第一个 Spring MVC 程序，我们来看看基于注解应该怎么完成上述程序的配置：

#### 第一步：为 HelloController 添加注解

```
package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController{

    @RequestMapping("/hello")
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index.jsp");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}
```

把实现的接口也给去掉。

- **简单解释一下：**
- `@Controller` 注解：
  很明显，这个注解是用来声明控制器的，但实际上这个注解对 Spring MVC 本身的影响并不大。（Spring 实战说它仅仅是辅助实现组件扫描，可以用 `@Component` 注解代替，但我自己尝试了一下并不行，因为上述例子没有配置 JSP 视图解析器我还自己配了一个仍没有成功...）
- `@RequestMapping` 注解：
  很显然，这就表示路径 `/hello` 会映射到该方法上

#### 第二步：取消之前的 XML 注释

在 dispatcher-servlet.xml 文件中，注释掉之前的配置，然后增加一句组件扫描：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--<bean id="simpleUrlHandlerMapping"-->
                                        <!--class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">-->
    <!--<property name="mappings">-->
            <!--<props>-->
                <!--&lt;!&ndash; /hello 路径的请求交给 id 为 helloController 的控制器处理&ndash;&gt;-->
                <!--<prop key="/hello">helloController</prop>-->
            <!--</props>-->
        <!--</property>-->
    <!--</bean>-->
    <!--<bean id="helloController" class="controller.HelloController"></bean>-->

    <!-- 扫描controller下的组件 -->
    <context:component-scan base-package="controller"/>
</beans>
```

#### 第三步：重启服务器

当配置完成，重新启动服务器，输入 `localhost/hello` 地址仍然能看到效果：



![img](https://upload-images.jianshu.io/upload_images/7896890-390fb571e9f6ff03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/947/format/webp)

#### @RequestMapping 注解细节

如果 `@RequestMapping` 作用在类上，那么就相当于是给该类所有配置的映射地址前加上了一个地址，例如：

```
@Controller
@RequestMapping("/wmyskxz")
public class HelloController {
    @RequestMapping("/hello")
    public ModelAndView handleRequest(....) throws Exception {
        ....
    }
}
```

- 则访问地址： `localhost/wmyskxz/hello`

------

## 配置视图解析器

还记得我们 Spring MVC 的请求流程吗，视图解析器负责定位视图，它接受一个由 DispaterServlet 传递过来的逻辑视图名来匹配一个特定的视图。

- **需求：** 有一些页面我们不希望用户用户直接访问到，例如有重要数据的页面，例如有模型数据支撑的页面。
- **造成的问题：**
  我们可以在【web】根目录下放置一个【test.jsp】模拟一个重要数据的页面，我们什么都不用做，重新启动服务器，网页中输入 `localhost/test.jsp` 就能够直接访问到了，这会造成**数据泄露**...
  另外我们可以直接输入 `localhost/index.jsp` 试试，根据我们上面的程序，这会是一个空白的页面，因为并没有获取到 `${message}` 参数就直接访问了，这会**影响用户体验**

#### 解决方案

我们将我们的 JSP 文件配置在【WEB-INF】文件夹中的【page】文件夹下，【WEB-INF】是 Java Web 中默认的安全目录，是不允许用户直接访问的*（也就是你说你通过 localhost/WEB-INF/ 这样的方式是永远访问不到的）*

但是我们需要将这告诉给视图解析器，我们在 dispatcher-servlet.xml 文件中做如下配置：

```
<bean id="viewResolver"
      class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/page/" />
    <property name="suffix" value=".jsp" />
</bean>
```

这里配置了一个 Spring MVC 内置的一个视图解析器，该解析器是遵循着一种约定：会**在视图名上添加前缀和后缀，进而确定一个 Web 应用中视图资源的物理路径的。**让我们实际来看看效果：

#### 第一步：修改 HelloController

我们将代码修改一下：



![img](https://upload-images.jianshu.io/upload_images/7896890-2ce49e171bd6d547.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/765/format/webp)

#### 第二步：配置视图解析器：

按照上述的配置，完成：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!--<bean id="simpleUrlHandlerMapping"-->
                                        <!--class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">-->
    <!--<property name="mappings">-->
            <!--<props>-->
                <!--&lt;!&ndash; /hello 路径的请求交给 id 为 helloController 的控制器处理&ndash;&gt;-->
                <!--<prop key="/hello">helloController</prop>-->
            <!--</props>-->
        <!--</property>-->
    <!--</bean>-->
    <!--<bean id="helloController" class="controller.HelloController"></bean>-->

    <!-- 扫描controller下的组件 -->
    <context:component-scan base-package="controller"/>
    <bean id="viewResolver"
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/page/" />
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

#### 第三步：剪贴 index.jsp 文件

在【WEB-INF】文件夹下新建一个【page】文件夹，并将【index.jsp】文件剪贴到里面：



![img](https://upload-images.jianshu.io/upload_images/7896890-88995fd05ccd0f80.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/362/format/webp)

#### 第四步：更新资源重启服务器

访问 `localhost/hello` 路径，看到正确效果：



![img](https://upload-images.jianshu.io/upload_images/7896890-390fb571e9f6ff03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/947/format/webp)



- **原理：**



![img](https://upload-images.jianshu.io/upload_images/7896890-a716a3ac8f7e541d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/468/format/webp)

我们传入的逻辑视图名为 index ，再加上 “`/WEB-INF/page/`” 前缀和 “`.jsp`” 后缀，就能确定物理视图的路径了，这样我们以后就可以将所有的视图放入【page】文件夹下了！

- **注意：**此时的配置仅是 dispatcher-servlet.xml 下的

------

## 控制器接收请求数据

使用控制器接收参数往往是 Spring MVC 开发业务逻辑的第一步，为探索 Spring MVC 的传参方式，为此我们先来创建一个简单的表单用于提交数据：

```
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Spring MVC 传参方式</title>
</head>
<body>
<form action="/param" role="form">
    用户名：<input type="text" name="userName"><br/>
    密码：<input type="text" name="password"><br/>
    <input type="submit" value="提  交">
</form>
</body>
</html>
```

丑就丑点儿吧，我们就是来测试一下：



![img](https://upload-images.jianshu.io/upload_images/7896890-b50a42db8debde97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/456/format/webp)

#### 使用 Servlet 原生 API 实现：

我们很容易知道，表单会提交到 `/param` 这个目录，我们先来使用 Servlet 原生的 API 来看看能不能获取到数据：

```
@RequestMapping("/param")
public ModelAndView getParam(HttpServletRequest request,
                         HttpServletResponse response) {
    String userName = request.getParameter("userName");
    String password = request.getParameter("password");

    System.out.println(userName);
    System.out.println(password);
    return null;
}
```

测试成功：



![img](https://upload-images.jianshu.io/upload_images/7896890-df21058b7ef71924.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/706/format/webp)

#### 使用同名匹配规则

我们可以把方法定义的形参名字设置成和前台传入参数名一样的方法，来获取到数据（同名匹配规则）：

```
@RequestMapping("/param")
public ModelAndView getParam(String userName,
                             String password) {
    System.out.println(userName);
    System.out.println(password);
    return null;
}
```

测试成功：



![img](https://upload-images.jianshu.io/upload_images/7896890-55a1c296c778e506.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/595/format/webp)

- **问题：** 这样又会和前台产生很强的耦合，这是我们不希望的
- **解决：** 使用 `@RequestParam("前台参数名")` 来注入：



![img](https://upload-images.jianshu.io/upload_images/7896890-a649ad50866a01c5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/760/format/webp)

- **@RequestParam 注解细节：**
  该注解有三个变量：`value`、`required`、`defaultvalue`
- `value` ：指定 `name` 属性的名称是什么，`value` 属性都可以默认不写
- `required` ：是否必须要有该参数，可以设置为【true】或者【false】
- `defaultvalue` ：设置默认值

#### 使用模型传参

- **要求： 前台参数名字必须和模型中的字段名一样**

让我们先来为我们的表单创建一个 User 模型：

```
package pojo;

public class User {
    
    String userName;
    String password;

    /* getter and setter */
}
```

然后测试仍然成功：



![img](https://upload-images.jianshu.io/upload_images/7896890-471d26bcb335aee6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/438/format/webp)

#### 中文乱码问题

- **注意：** 跟 Servlet 中的一样，该方法只对 POST 方法有效（因为是直接处理的 request）

我们可以通过配置 Spring MVC 字符编码过滤器来完成，在 web.xml 中添加：

```
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <!-- 设置编码格式 -->
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

------

## 控制器回显数据

通过上面，我们知道了怎么接受请求数据，并能解决 POST 乱码的问题，那么我们怎么回显数据呢？为此我们在【page】下创建一个【test2.jsp】：

```
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false" %>
<html>
<head>
    <title>Spring MVC 数据回显</title>
</head>
<body>
<h1>回显数据：${message}</h1>
</body>
</html>
```

#### 使用 Servlet 原生 API 来实现

我们先来测试一下 Servlet 原生的 API 是否能完成这个任务：

```
@RequestMapping("/value")
public ModelAndView handleRequest(HttpServletRequest request,
                                  HttpServletResponse response) {
    request.setAttribute("message","成功！");
    return new ModelAndView("test1");
}
```

在浏览器地址栏中输入：`localhost/value` 测试



![img](https://upload-images.jianshu.io/upload_images/7896890-66d2f24a876306e6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/562/format/webp)

#### 使用 Spring MVC 所提供的 ModelAndView 对象



![img](https://upload-images.jianshu.io/upload_images/7896890-360ce67947be817d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/703/format/webp)

#### 使用 Model 对象

在 Spring MVC 中，我们通常都是使用这样的方式来绑定数据，



![img](https://upload-images.jianshu.io/upload_images/7896890-685dd384904ad28f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/536/format/webp)

- **使用 @ModelAttribute 注解：**

```
@ModelAttribute
public void model(Model model) {
    model.addAttribute("message", "注解成功");
}

@RequestMapping("/value")
public String handleRequest() {
    return "test1";
}
```

这样写就会在访问控制器方法 handleRequest() 时，会首先调用 model() 方法将 `message` 添加进页面参数中去，在视图中可以直接调用，但是这样写会导致该控制器所有的方法都会首先调用 model() 方法，但同样的也很方便，因为可以加入各种各样的数据。

------

## 客户端跳转

前面不管是地址 `/hello` 跳转到 index.jsp 还是 `/test` 跳转到 test.jsp，这些都是服务端的跳转，也就是 `request.getRequestDispatcher("地址").forward(request, response);`

那我们如何进行客户端跳转呢？我们继续在 HelloController 中编写：

```
@RequestMapping("/hello")
public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
    ModelAndView mav = new ModelAndView("index");
    mav.addObject("message", "Hello Spring MVC");
    return mav;
}

@RequestMapping("/jump")
public ModelAndView jump() {
    ModelAndView mav = new ModelAndView("redirect:/hello");
    return mav;
}
```

我们使用 `redirect:/hello` 就表示我们要跳转到 `/hello` 这个路径，我们重启服务器，在地址栏中输入：`localhost/jump` ，会自动跳转到 `/hello` 路径下：



![img](https://upload-images.jianshu.io/upload_images/7896890-390fb571e9f6ff03.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/947/format/webp)

也可以这样用：

```
@RequestMapping("/jump")
public String jump() {
    return "redirect: ./hello";
}
```

------

## 文件上传

我们先来回顾一下传统的文件上传和下载：[这里](https://www.jianshu.com/p/e7837435bf4c)

我们再来看一下在 Spring MVC 中如何实现文件的上传和下载

- **注意：** 需要先导入 `commons-io-1.3.2.jar` 和 `commons-fileupload-1.2.1.jar` 两个包

#### 第一步：配置上传解析器

在 dispatcher-servlet.xml 中新增一句：

```
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
```

开启对上传功能的支持

#### 第二步：编写 JSP

文件名为 upload.jsp，仍创建在【page】下：

```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试文件上传</title>
</head>
<body>
<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="picture">
    <input type="submit" value="上 传">
</form>
</body>
</html>
```

#### 第三步：编写控制器

在 Package【controller】下新建【UploadController】类：

```
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {

    @RequestMapping("/upload")
    public void upload(@RequestParam("picture") MultipartFile picture) throws Exception {
        System.out.println(picture.getOriginalFilename());
    }

    @RequestMapping("/test2")
    public ModelAndView upload() {
        return new ModelAndView("upload");
    }

}
```

#### 第四步：测试

在浏览器地址栏中输入：`localhost/test2` ，选择文件点击上传，测试成功：



![img](https://upload-images.jianshu.io/upload_images/7896890-531c47b14dbc71e5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/440/format/webp)

------

#### 参考资料：

- 《Java EE 互联网轻量级框架整合开发》
- 《Spring 实战》
- [How2j Spring MVC 系列教程](http://how2j.cn/k/springmvc/springmvc-springmvc/615.html)
- 全能的百度和万能的大脑

------

> 欢迎转载，转载请注明出处！
> 简书ID：[@我没有三颗心脏](https://www.jianshu.com/u/a40d61a49221) 
> github：[wmyskxz](https://github.com/wmyskxz/) 
> 欢迎关注公众微信号：wmyskxz
> 分享自己的学习 & 学习资料 & 生活
> 想要交流的朋友也可以加qq群：3382693

小礼物走一走，来简书关注我

赞赏支持



 Java Web

© 著作权归作者所有

举报文章

![96](https://upload.jianshu.io/users/upload_avatars/7896890/25a228c7-84e0-4c3e-a2c5-65efa1d0c258.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96)

关注

我没有三颗心脏

 



写了 308044 字，被 1306 人关注，获得了 1904 个喜欢

摩羯it男爱音乐爱文艺。 欢迎关注公众微信号：wmyskxz获取最新精选JavaWeb学习资料！ 也欢迎加入QQ群：3382693来一同交流！

喜欢

 

86

   [更多分享](javascript:void(0);)

![Web note ad 1](https://cdn2.jianshu.io/assets/web/web-note-ad-1-c2e1746859dbf03abe49248893c9bea4.png)

![img](https://cdn2.jianshu.io/assets/default_avatar/avatar_default-78d4d1f68984cd6d4379508dd94b4210.png)

[登录](https://www.jianshu.com/sign_in?utm_source=desktop&utm_medium=not-signed-in-comment-form) 后发表评论

7条评论

 

只看作者

按时间倒序按时间正序

[![img](https://cdn2.jianshu.io/assets/default_avatar/15-a7ac401939dd4df837e3bbf82abaa2a8.jpg)](https://www.jianshu.com/u/859d7a63e00a)

 

luckywind

7楼 · 2019.02.24 22:10

神书推荐： sping-MVC学习指南 <https://pan.baidu.com/s/1_pdDFhH7g9tTnP0wkmz07A>

赞  回复

[![img](https://upload.jianshu.io/users/upload_avatars/8251081/4c4a58eb-23b3-443b-af05-942e64eac4c2.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114/format/webp)](https://www.jianshu.com/u/eae37e4abf6b)

 

YuTengjing

6楼 · 2018.12.20 02:01

对我一个刚刚学习sprng的新手帮助很大，感谢楼主

赞  回复

[![img](https://upload.jianshu.io/users/upload_avatars/13195097/b3307517-da00-48b0-b9e7-3fa9af6f96e1?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114/format/webp)](https://www.jianshu.com/u/d96ec5ec6223)

 

嘉斯顿特杨

5楼 · 2018.11.13 18:13

没有看到你关于 applicationContext.xml 的编辑。 是不是因为 dispatcher 和各种其他web包对 bean 的管理已经内部做好实现了（例如能自动通过模型传参，只要写好有 getter setter 的 pojo 就好了，甚至都不用写怎么从表单提交的数据喂给pojo去实例化）？所以Spring 的 Application context = ... （读入xml配置，实例化）在web上面没啥用？

赞  回复

[![img](https://upload.jianshu.io/users/upload_avatars/13568395/8768e1e0-bf60-47d8-aca7-73d53bf7dac5?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114/format/webp)](https://www.jianshu.com/u/4efbaed52ad5)

 

正经龙

4楼 · 2018.10.12 00:25

作为一个一直不懂mvc的萌新，看完这篇文章恍然大悟，真的很详细，希望博主写的越来越棒，关注啦(๑•ั็ω•็ั๑)😃

赞  回复

[![img](https://upload.jianshu.io/users/upload_avatars/7178192/dc79528e-4d15-4c3f-925b-260e2f099552?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114/format/webp)](https://www.jianshu.com/u/391d40dcbd6b)

 

I_Wright

3楼 · 2018.07.08 12:34

鲜花送上

赞  回复



[我没有三颗心脏](https://www.jianshu.com/u/a40d61a49221)：

 

[@I_Wright](https://www.jianshu.com/u/391d40dcbd6b) 谢谢



2018.07.08 14:02  回复

 添加新评论

[![img](https://upload.jianshu.io/users/upload_avatars/1498391/fd12e37a3c76?imageMogr2/auto-orient/strip|imageView2/1/w/114/h/114/format/webp)](https://www.jianshu.com/u/8b78939b162d)

 

Sunny玄子

2楼 · 2018.05.20 11:07

写的很详细，很入门👍👍👍👍

赞  回复



- 
- 

被以下专题收入，发现更多相似内容



ssh



Java 杂谈



我爱编程



Java学习笔记



服务器学习



我的Sprin...



Java技术升华

展开更多 

推荐阅读[更多精彩内容](https://www.jianshu.com/)



你不是情商低，而是三观太正

01 昨天，朋友小夕跟我吐槽，自己遇到了一个情商特别低的人，差点耽误了她的工作。 原来，不久后，小夕要出国办公谈业务，需要办理签证，而办签证需要提供加盖公司公章的收入证明。 于是，小夕做好了收入证明，准备拿给掌管公章的同事盖章。 结果，当天领导不在，小夕无法把盖章事由给领导签字，而那位掌管公章的同事又特别不好说话，不敢在权限范围外盖章，又不愿先打电话知会领导，盖完章再找领导签字。 小夕解释，自己这是为了办签证，由于出差的时间比较近，拿到签证还需要一段时间，如果不能及时盖章可能会耽误工作，希望能够通融一下。 可这位同事认死理，只能先签字，再盖章，小夕只好悻悻地回去，等领导在的时候再跑一趟。...



 

精读君





汪涵：一个人开始变好的3种迹象

01 最近看了一期汪涵的综艺《火星情报局》。 有人在节目上问他： “涵哥，为什么你的主持功力那么深，临场反应能力那么强？真羡慕你有这种天赋。” 汪涵笑着回应说： “很多人以为我是凭空变成这样的，其实我背地里是一个非常努力的人。 我大概从十几二十年前，就从来没有进过练歌房，我也从来没有去泡过吧。 天天躲在家里不断地看书，而且经常是从夜里九点看到早上七点。 我还会把我认为最好的句子，或者是最感动我的一些思想，不断地在自己内心咀嚼。 有的时候我甚至还对着镜子去重复练习，就是为了自己在说的时候更加自然。” 看汪涵主持的节目，常常会被他的学识和智慧吸引到。 林青霞曾经赞美汪涵：“每次听你说话，都像...



 

精读君



为什么有人为了权欲，宁愿过把瘾就死？

在十八路关东联军讨伐董卓的时候，孙坚在洛阳一口井里捡到了宝贝：皇帝的玉玺。 但孙坚并没有把这个传国玉玺上交盟主，后来死的时候，传给了儿子孙策。 孙策一直在袁术手下做事，但看到了袁术的另一面，就想到江东独立发展，于是去向袁术借兵。 为了证明自己守信用，把传国玉玺作为抵押。借了兵三千、马五百匹。在江东发展不错。 袁术意外得到了传国玉玺。心中升腾起了对皇帝的渴望，恨不得马上要做皇帝。 当时的时势是：南边的袁术，势力最大；中部西边的曹操，势力次之；中部东边的吕布，势力最弱。 如果袁术和吕布联合，对曹操就会构成巨大威胁。 曹操于是利用皇帝优势为吕布加官进爵，并写信赞美说，曹操对吕布的景仰之情如何如...



 

异趣盎然



我打赌这片三天后一票难求

今天除夕。 相信回到家的同学们，已经该见的见了，该吃的吃了...... 最开始的兴奋劲已经快速消耗成了“葛优瘫”的养猪模式。 年还没过，已经腻了？ 大菜还没上桌呢—— 眼瞅着史上强春节档，Sir就无比兴奋。 今天，先上一盘硬菜。 它出终极预告的时候，Sir就掩饰不住激动写过一篇。许多毒饭还在犹豫：是不是期待得太早了？ 看过片后，Sir更肯定，它绝对要炸。 请扶稳坐好—— 《流浪地球》 正式聊它，Sir必须先收一收震撼、兴奋、感动。 不如，就先看看这四个字的片名。 暂时收回对原著设定的默认，把片名一刀分开——地球、流浪。 相互陌生的两个词。 一个听起来很大，是人类生存300万年的家园。 一...



 

Sir电影



《流浪地球》情怀上完爆好莱坞大片

先科普一下红巨星和氦闪。 我在小的时候，爸爸曾经给我和哥哥买过一套《十万个为什么》，天文篇是我最喜欢看的。 在这本书里，我读到了太阳的演化。 太阳主要是有氢元素和氦元素组成的，氢元素在极高的温度下不断聚变成氦，氦会沉积在太阳中心，形成一个致密的核。当氢元素消耗到一定程度，太阳失去能量的稳定性调节机制，体积开始膨胀，变得更红，这个阶段的恒星，称为红巨星。 而当处于红巨星末期的时候，太阳内部的温度越来越高，内核的氦元素也会开始聚变，失控的氦元素会在短暂的时间内释放出极为巨大的能量，这个现象叫做氦闪。 氦闪几乎每数万年就会发生一次，而这个过程会持续数亿年的时间。直到太阳进一步收缩，最终会变成白...



 

青色百合99

Spring boot参考指南

Spring Boot 参考指南 介绍 转载自:https://www.gitbook.com/book/qbgbook/spring-boot-reference-guide-zh/details带目录浏览地址:http://www.maoyupeng.com/sprin...

![48](https://upload.jianshu.io/users/upload_avatars/1687958/9b5b9b6de8b7.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

毛宇鹏

![240](https://upload-images.jianshu.io/upload_images/7328262-54f7992145380c10.png?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240)

Spring Cloud

Spring Cloud为开发人员提供了快速构建分布式系统中一些常见模式的工具（例如配置管理，服务发现，断路器，智能路由，微代理，控制总线）。分布式系统的协调导致了样板模式, 使用Spring Cloud开发人员可以快速地支持实现这些模式的服务和应用程序。他们将在任何分布式...

![48](https://cdn2.jianshu.io/assets/default_avatar/2-9636b13945b9ccf345bc98d0d81074eb.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

卡卡罗2017

![240](https://upload-images.jianshu.io/upload_images/7882361-ca42d26e82026386.png?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240)

【Spring实战】构建Spring Web应用程序

本章内容： 映射请求到Spring控制器 透明地绑定表单参数 校验表单提交 状态管理、工作流以及验证都是Web 开发需要解决的重要特性。HTTP协议的无状态性决定了这些问题都不那么容易解决。 Spring的Web框架就是为了帮助解决这些关注点而设计的。Spring MVC基...

![48](https://upload.jianshu.io/users/upload_avatars/7882361/bc8830d2-d828-4ddb-a5ab-2e2cf55cd3f9.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

谢随安

Java面试宝典Beta5.0

pdf下载地址：Java面试宝典 第一章内容介绍	20 第二章JavaSE基础	21 一、Java面向对象	21 1. 面向对象都有哪些特性以及你对这些特性的理解	21 2. 访问权限修饰符public、private、protected, 以及不写（默认）时的区别(201...

![48](https://upload.jianshu.io/users/upload_avatars/6480773/e7eb1b5f-9375-4d65-a47f-f46719569b93?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

王震阳

![240](https://upload-images.jianshu.io/upload_images/3676154-a2d7d8331eb32050.png?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240)

Spring MVC

Spring MVC一、什么是 Spring MVCSpring MVC 属于 SpringFrameWork 的后续产品，已经融合在 Spring Web Flow 里面，是一个强大灵活的 Web 框架。Spring MVC 提供了一个 DispatcherServlet...

![48](https://upload.jianshu.io/users/upload_avatars/3676154/2dc7612ca172.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

任任任任师艳

我的眼里没有你

《我的眼里没有你》 文/白传英 也许是天意 我不能和你在一起 或许是老天的有意安排 在你我之间增加了距离 你看不到我 我看不到你 我们只能用心 彼此感受着对方的心理 也许只有在泪水流下的时候 你才能发现我的存在 还有一个我 泪水流下的也是那么多 我是多么想和你在一...

![48](https://upload.jianshu.io/users/upload_avatars/3946136/981a45abb56a.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

白清风

![240](https://upload-images.jianshu.io/upload_images/4853363-c6feb0f51398adc2.png?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240)

Mysql笔记

MySQL的启动和关闭 启动MySQL服务 net start mysql 关闭MySQL服务 net stop mysql MySQL登录/退出 登录 mysql 参数 mysql -uroot -p -P3306 -h127.0.0.1参数： -u --us...

![48](https://upload.jianshu.io/users/upload_avatars/4853363/ab054368-cf2b-4944-bf48-0e9fe37cef41.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

不二很纯洁

第四维度

当你遇到一个另类， 不用世俗的去面对。 当你遇到一个纠结， 不用复杂的去想象。 当你遇到一个执着， 不用刻意的去判断。 当你遇到一个向往， 不用犹豫的去追求。 当你遇到的都错过， 不用感慨的去后悔。 因为那时已经晚了， 时间从未为谁停留。 你只能追随的时间， 毫无感情甚至冰冷！

![48](https://upload.jianshu.io/users/upload_avatars/1261891/970b8501592c.jpeg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

得哩

HTML的 <form> 标签

标签用于为用户输入创建 HTML 表单。 表单能够包含 input 元素，比如文本字段、复选框、单选框、提交按钮等等。表单还可以包含 menus、textarea、fieldset、legend 和 label 元素。表单用于向服务器传输数据。 的属性有： enctype ...

![48](https://cdn2.jianshu.io/assets/default_avatar/5-33d2da32c552b8be9a0548c7a4576607.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

SingleDiego

![240](https://upload-images.jianshu.io/upload_images/2527154-d23bff6e5abec83f.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/300/h/240)

社会浮躁，读书的意义何在？

作者/Angela 图片/网络 01 带孩子去图书馆的路上，有一家小小的书店。我们从图书馆回来时，常常去那里淘书看。 书店位于街角，大约十几个平方的面积，在这一条繁华的商业街上，宛如一片净土，安静地存在着。 店主是一位50多岁的大叔。听他说，这家书店传自他的母亲，已经经营...

![48](https://upload.jianshu.io/users/upload_avatars/2527154/28caf039-033c-4e3a-a560-001cff2a330c.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/48/h/48)

 

Angela在悉尼