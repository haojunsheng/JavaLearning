# Servlet的概念

[Servlet 工作原理解析](https://www.ibm.com/developerworks/cn/java/j-lo-servlet/index.html)

Servlet的存在是为了客户服务的。

获取用户的请求,交给相应的模型，即Java实体类，然后通知视图层进行更新。

# 初始化 Servlet

![img](https://ws4.sinaimg.cn/large/006tKfTcly1g08ji9nv5wj30nu0pgq4s.jpg)

# http request能获得的参数

## get和post的区别

- get的数据量小，post大
- get不安全
- get允许用户建立书签
- get是幂等的，post是非幂等的。

## ServletRequest 接口

ServletRequest接口是用来向servlet提供客户端请求信息。每一个客户端请求到达Servlet容器的时候，他都会创建一个ServletRequest对象，并将其传递对应的servlet的service()方法。

ServletRequest接口中的主要方法：

**Object getAttribute(String name)** – 返回指定的参数名对应的属性值。如果对应的参数不存在则返回null。我们可以使用`getAttributeNames()`方法来获取请求中的所有属性名称的枚举。接口中同样提供了设置值和删除值的方法。

**String getParameter(String name)** – 以字符串的形式返回请求参数值。我们可以使用getParameterNames()方法来获取请求参数名称的枚举。

**String getServerName()** – 返回服务器的主机名

**int getServerPort()** – 返回服务器监听的端口号。

ServletRequest的子接口HttpServletRequest中还包含了一些和session、cookies等相关的方法。

> 译者注：该接口中提供了getAttribute和getParameter两个方法，都是用于获取参数（属性）值的，那么这两个方法有什么区别呢？或者说Attribute和Parameter的区别是什么呢？
>
> 答：
>
> 来源不同
>
> > 参数（parameter）是从客户端（浏览器）中由用户提供的，若是GET方法是从URL中 提供的，若是POST方法是从请求体（request body）中提供的；
> >
> > 属性（attribute）是服务器端的组件（JSP或者Servlet）利用requst.setAttribute（）设置的.
>
> 操作不同
>
> > 参数（parameter）的值只能读取不能修改，读取可以使用request.getParameter()读取；
> >
> > 属性（attribute）的值既可以读取亦可以修改，读取可以使用request.setAttribute(),设置可使用request.getAttribute()
>
> 数据类型不同
>
> > 参数（parameter）不管前台传来的值语义是什么，在服务器获取时都以String类型看待，并且客户端的参数值只能是简单类型的值，不能是复杂类型，比如一个对象。
> >
> > 属性（attribute）的值可以是任意一个Object类型。

```java
//客户端平台和浏览器信息
request.getHeader("User-Agent");
request.getCookies();
request.getSession();
//请求的Http方法
request.getMethod();
//请求的输入流
request.getInputStream();
```





























