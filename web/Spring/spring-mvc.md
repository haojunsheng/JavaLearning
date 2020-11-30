# 前言

SpringMVC是Spring家族的一员，Spring是将现在开发中流行的组件进行组合而成的一个框架!它用在 基于**MVC**的表现层开发，类似于**struts2**框架。

为什么要有Spring MVC？Struts2过于拉胯。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020141724.png" alt="image-20201020141724803" style="zoom:33%;" />

# 1. 快速入门

1. 新建项目

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020142226.png)

![image-20201020142510641](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020142510.png)

2. 修改web.xml

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020142649.png)

3. #### dispatcher-servlet.xml

```xml
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

![image-20201020231236871](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020231237.png)

# 2. Spring MVC工作流程

![image-20201020144403767](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020231458.png)



## 2.1 DispatcherServlet

DispatcherServlet 会拦截所有的请求，并且将这些请求发送给 Spring MVC 控制器。

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

## 2.2 处理器映射（HandlerMapping）

DispatcherServlet 会查询一个或多个处理器映射来确定请求的下一站在哪里，处理器映射会**根据请求所携带的 URL 信息来进行决策**，例如上面的例子中，我们通过配置 simpleUrlHandlerMapping 来将 /hello 地址交给 helloController 处理：

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

## 2.3 控制器

```
public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
    // 处理逻辑
    ....
}
```

## 2.4 返回 DispatcherServlet

当控制器在完成逻辑处理后，通常会产生一些信息，这些信息就是需要返回给用户并在浏览器上显示的信息，它们被称为**模型（Model）**。仅仅返回原始的信息时不够的——这些信息需要以用户友好的方式进行格式化，一般会是 HTML，所以，信息需要发送给一个**视图（view）**，通常会是 JSP。

控制器所做的最后一件事就是将模型数据打包，并且表示出用于渲染输出的视图名**（逻辑视图名）。它接下来会将请求连同模型和视图名发送回 DispatcherServlet。**

```java
COPYpublic ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
    // 处理逻辑
    ....
    // 返回给 DispatcherServlet
    return mav;
}
```

## 2.5 视图解析器

这样以来，控制器就不会和特定的视图相耦合，传递给 DispatcherServlet 的视图名并不直接表示某个特定的 JSP。（实际上，它甚至不能确定视图就是 JSP）相反，**它传递的仅仅是一个逻辑名称，这个名称将会用来查找产生结果的真正视图。**

DispatcherServlet 将会使用视图解析器（view resolver）来将逻辑视图名匹配为一个特定的视图实现，它可能是也可能不是 JSP。

## 2.6 视图

既然 DispatcherServlet 已经知道由哪个视图渲染结果了，那请求的任务基本上也就完成了。它的最后一站是视图的实现，在这里它交付模型数据，请求的任务也就完成了。视图使用模型数据渲染出结果，这个输出结果会通过响应对象传递给客户端。

```
COPY<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<h1>${message}</h1>
```

# 3. 使用注解配置Spring MVC

1. 修改Controller

```java
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

2. 取消之前的 XML 注释

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

3. 重新运行即可。

# 4. 视图解析器

有些数据需要保护，不希望用户可以直接访问。

![image-20201020233604587](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020233604.png)

1. 我们在 dispatcher-servlet.xml 文件中做如下配置：

```
<bean id="viewResolver"
      class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/page/" />
    <property name="suffix" value=".jsp" />
</bean>
```

这里配置了一个 Spring MVC 内置的一个视图解析器，该解析器是遵循着一种约定：会**在视图名上添加前缀和后缀，进而确定一个 Web 应用中视图资源的物理路径的。**让我们实际来看看效果：

2. 修改controller

```
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("message", "Hello Spring MVC");
        return mav;
    }
}
```

3. 重启

![image-20201020233909183](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020233909.png)

4. 原理

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020233944.png)

我们传入的逻辑视图名为 index ，再加上 “`/WEB-INF/page/`” 前缀和 “`.jsp`” 后缀，就能确定物理视图的路径了，这样我们以后就可以将所有的视图放入【page】文件夹下了！

 # 5. 控制器接收请求数据

1. 修改jsp

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

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020234353.png)

2. 使用 Servlet 原生 API接收参数

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

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020234539.png)

3. 使用同名匹配规则

我们可以把方法定义的形参名字设置成和前台传入参数名一样的方法，来获取到数据（同名匹配规则）：

```java
@RequestMapping("/param")
public ModelAndView getParam(String userName,
                             String password) {
    System.out.println(userName);
    System.out.println(password);
    return null;
}
```

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020234742.png)

4. 和前端松耦合

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020234935.png)



- **`@RequestParam` 注解细节：**该注解有三个变量：`value`、`required`、`defaultvalue`
- `value` ：指定 `name` 属性的名称是什么，`value` 属性都可以默认不写
- `required` ：是否必须要有该参数，可以设置为【true】或者【false】
- `defaultvalue` ：设置默认值

5. 使用模型传参，**前台参数名字必须和模型中的字段名一样**

![image-20201020235226822](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201020235408.png)

6. 解决中文乱码问题

只对 POST 方法有效。在web.xml中新加：

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

# 6. 回显数据

1. 使用Servlet 原生 API 来实现

test2.jsp:

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

Controller:

```
@RequestMapping("/value")
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response) {
        request.setAttribute("message","成功！");
        return new ModelAndView("test2");
    }
```

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021000232.png)

2. 使用 Spring MVC 所提供的 ModelAndView 对象

```
ModelAndView modelAndView=new ModelAndView("test2");
        modelAndView.addObject("message","成功！");
        return modelAndView;
```

3. 使用Model对象

```
@RequestMapping("/value")
    public String handleRequest(Model model) {
        model.addAttribute("message","成功！");
        return "test2";
    }
```

4. **使用 `@ModelAttribute` 注解**

```
@ModelAttribute
public void model(Model model) {
    model.addAttribute("message", "注解成功");
}

@RequestMapping("/value")
public String handleRequest() {
    return "test2";
}
```

# 7. 客户端跳转

前面不管是地址 `/hello` 跳转到 index.jsp 还是 `/test` 跳转到 test.jsp，这些都是服务端的跳转，也就是 `request.getRequestDispatcher("地址").forward(request, response);`

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

我们使用 `redirect:/hello` 就表示我们要跳转到 `/hello` 这个路径，我们重启服务器，在地址栏中输入：`localhost/jump` ，会自动跳转到 `/hello` 路径下。

等价于：

```
@RequestMapping("/jump")
public String jump() {
    return "redirect: ./hello";
}
```

# 8. 文件上传

1. 导入jar包：commons-io-1.3.2.jar和commons-fileupload-1.2.1.jar
2. 配置上传解析器

在 dispatcher-servlet.xml 中新增一句：

```
<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
```

开启对上传功能的支持

3. 编写upload.jsp

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

4. 编写UploadController控制器

```
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

5. 测试

![](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201021112019.png)

# 9. 校验器**Validation**





