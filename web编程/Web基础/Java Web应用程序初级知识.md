[TOC]

# 前言

Web应用程序是用于创建动态网站。Java提供的Web应用程序通过servlets和JSP的支持。我们可以创建一个静态HTML页面的网站，但当我们想要的信息是动态的(和网站有交互),需要的Web应用程序。 本文的目的是提供Web应用程序的不同组件基本资料以及我们如何使用Servlet和JSP来创建我们的Java Web应用。

# Web服务器和客户端

Web服务器是一个软件，可以处理客户端的请求和发送响应给客户端。例如，Apache是一种使用最广泛的Web服务器。Web服务器运行在物理机器上监控特定端口的客户端请求。

客户端是一个与服务器通信的软件,一些广泛使用的Web客户端是Firefox，Safari等浏览器，当我们要从服务器（通过URL）请求东西的时候，Web客户端负责创建一个请求并发送给服务器，然后解析服务器响应展示给用户。

#  HTML 和 HTTP

Web服务器和Web客户端是两个独立的软件，所以应该有一种共同的语言来进行沟通。HTML是客户端和服务器之间的共同使用的超文本标记语言。

Web服务器和客户端需要一个共同的通信协议，HTTP（超文本传输协议）是服务器和客户端之间的通信协议。HTTP运行在TCP / IP通信协议。

HTTP请求的一些重要部分：

> **HTTP Method**(请求方法) –要执行的动作,主要有 GET, POST, PUT 等 **URL** 想要访问的页面 **Form Parameters** (表单参数)–类似于java程序里面的参数,比如用户在登陆页面输入的账号和密码

例如:

> GET /FirstServletProject/jsps/hello.jsp HTTP/1.1 Host: localhost:8080 Cache-Control: no-cache

HTTP响应的一些重要部分：

> **Status Code** (状态码)– 用于表示请求是否成功的一个整数. 比较常见的状态码有:200表示成功,404表示找不到,403表示请求被拒绝
>
>  **Content Type** – text, html, image, pdf 等. 也叫做 MIME type 
>
> **Content** – 在客户端上展示给用户的实际数据

例如:

> 200 OK Date: Wed, 07 Aug 2013 19:55:50 GMT Server: Apache-Coyote/1.1 Content-Length: 309 Content-Type: text/html;charset=US-ASCII `<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html><head><meta http-equiv="Content-Type" content="text/html; charset=US-ASCII"><title>Hello</title></head><body><h2>Hi There!</h2><br><h3>Date=Wed Aug 07 12:57:55 PDT 2013</h3></body></html>`

MIME类型或内容类型：如果你看到上面的示例HTTP响应头，它包含标签“Content-Type”。它也被称为MIME类型,服务器告诉客户端发送的是哪种类型的数据。它可以帮助客户端为用户呈现数据。一些常用的MIME类型是`text/html`, `text/xml`, `application/xml` 等。

![image-20190215161701441](https://ws4.sinaimg.cn/large/006tKfTcly1g076imrqo3j30tk0ziqd4.jpg)

# 理解URL

URL是统一资源定位器的缩写，它是用来定位服务器和资源。网络上的每个资源都有它自己独特的地址。让我们看一个例子:`http://localhost:8080/FirstServletProject/jsps/hello.jsp` **http://** – 这是ＵＲＬ的第一个部分，他指明了服务器和客户端通信所使用的协议 **localhost** – 服务器的特定地址, 大多数情况下他是一个映射到某一个IP的地址的域名. 有些时候,多个域名会指向同一个IP地址,WEB服务器虚拟机负责将请求分发到特定的服务器. **8080** – 这是服务器监听的端口,他是可选的,如果没有指定会使用协议的默认端口. 端口号`0`到`1023`是众所周知的服务保留端口,比如 `80` 是 `HTTP` 的端口, `443` 是 `HTTPS`的端口 , `21` 是 `FTP` 的端口等. **FirstServletProject/jsps/hello.jsp** – 从服务器请求的资源,可以是静态HTML., pdf, JSP, servlets, PHP 等

# 我们为什么需要Servlet和JSP

Web服务器对HTML网页有很好的支持,但是他不知道如何生成动态的内容或者怎么将数据保存到数据库中,所以我们需要另外一种工具来生成动态的内容,有很多程序可以生成动态的内容,比如PHP, Python, Ruby on Rails, Java Servlets 和 JSPs. **Java Servlet 和 JSP 是通过支持动态响应和数据持久化来扩展Web服务器能力的服务器端技术**

Java Server Pages

> Model-View-Controller。View,，就是指表现层，Model，是用来承载数据的抽象结构，而Controller则是View和Model的桥梁。
>
> 把业务逻辑（比如操作数据库等）从Servlet中抽出来，把它放入一个模型中（模型是指一个可重用的普通Java类，是业务数据（比如购物车的状态）和和处理该数据方法的集合）。



- 控制器：获取用户输入，并明确输入对模型的影响。更新模型，并且让View层可以得到新的模型状态。
- 模型：具体的业务逻辑和状态。只有这部分才能和数据库进行通信。
- View层：从控制器得到模型状态；获取用户输入，并且交给控制器。

MVC：Model是Java类，View是JSP，Control是Servlet。

JSP负责把数据和模板进行装载来显示。

# 使用Servlet和JSP创建第一个Web应用程序

我们使用Eclipse来创建第一个servlet应用,因为servlet是服务器端技术,所以我们需要一个web容器来支持.这里我们使用 Apache Tomcat,这东西安装很简单,自己搞下就好了 为了便于开发,我们把tomcat添加到eclipse中,他帮助我们快速的部署并运行程序

> Preference–>select Server Runtime Environments—>选择你对应的tomcat版本

![enter image description here](https://ws3.sinaimg.cn/large/006tKfTcly1g07597s0kbj30vw0et76u.jpg)

填写tomcat的路径和jre环境,然后取server视图中像下图一样创建一个新的server

![enter image description here](https://ws2.sinaimg.cn/large/006tKfTcly1g05tuljjdrj30m406474i.jpg)

> 提示: 如果server标签不可见,选择 Window > Show View > Servers. 试着启动一下,看看是否配置成功,如果你已经通过终端启动了服务,那么你需要从终端先停止服务,然后再从eclipse中启动服务

接下来终于特么可以创建第一个程序了(老外写东西太墨迹了~~) 选择 File > New > Dynamic Web Project 一直按下一步,直到最后一步的时候添加servlet模本3.0 !

![enter image description here](https://ws3.sinaimg.cn/large/006tKfTcly1g07599mk4xj30ci0agmyr.jpg)

你可以直接点击完成或者下一步查看其他的选项设置 现在,选择 File > New > Servlet 创建一个servlet,此处点啥就不翻译了 !

![enter image description here](http://www.journaldev.com/wp-content/uploads/2013/08/first-servlet-450x367.png)

如果我们点完成,他会生成servlet的框架代码,所以我们有一些重要的方法不需要我们自己写,这很节省时间 接下来,我们在doGet()方法中添加一些html代码.当我们使用get方法请求时会调用这个doGet方法.我们的程序如下:

```
package com.journaldev.first;

import java.io.IOException; import java.io.PrintWriter; import java.util.Date;

import javax.servlet.ServletException; import javax.servlet.annotation.WebInitParam; import javax.servlet.annotation.WebServlet; import javax.servlet.http.HttpServlet; import javax.servlet.http.HttpServletRequest; import javax.servlet.http.HttpServletResponse;

/** * Servlet implementation class FirstServlet */ @WebServlet(description = "My First Servlet", urlPatterns = { "/FirstServlet" , "/FirstServlet.do"}, initParams = {@WebInitParam(name="id",value="1"),@WebInitParam(name="name",value="pankaj")}) public class FirstServlet extends HttpServlet { private static final long serialVersionUID = 1L; public static final String HTML_START=""; public static final String HTML_END="";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Date date = new Date();
        out.println(HTML_START + "<h2>Hi There!</h2><br/><h3>Date="+date +"</h3>"+HTML_END);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }


} 
```

在Servlet 3以前的版本中.我们需要在应用部署描述信息中提供url表达式信息,但是Servlet 3.0使用java注解的方式,这样即容易理解也可以减少错误 然后选择`Run > Run on Server` 如下图:![enter image description here](https://ws1.sinaimg.cn/large/006tKfTcly1g0794djvfmj30a40ci75l.jpg)

![enter image description here](http://www.journaldev.com/wp-content/uploads/2013/08/servlet-eclipse-server-webapps-450x401.png)

点击完成,然后使用浏览器打开页面

![enter image description here](http://www.journaldev.com/wp-content/uploads/2013/08/first-servlet-run-450x138.png)

通过刷新页面,你会发现数据是在不断变化的,eclipse有内部浏览器,你愿意用就用,不愿意用爱用啥用啥.

> 小结:Servlet用于生成动态页面并把它通过响应信息发送给客户端,如果你查看 `doGet()`方法的实现.我们实际上创建了一个`PrintWriter`对象,我们使用他来写动态内容

这是一个好的开始,但是,但是如果有很多的动态数据需要展示,那么我们要在servlet中码很多代码,这样即不好阅读也容易出错,这个时候Jsp就派上用场了 jsp也是服务端技术,他向html一样提供了很多额外功能让我们方便的创建动态内容.因为他很像HTML,所以他很容易展示. 下面这段代码就是jsp代码:

```java
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Hello</title>
</head>
<body>
<h2>Hi There!</h2>
<br>
<h3>Date=<%= new Date() %>
</h3>
</body>
</html>
```

运行程序看到如下结果:

![enter image description here](http://www.journaldev.com/wp-content/uploads/2013/08/first-jsp-run.png)

后续文章会讲述很多关于SERVLET和jsp的内部细节,在详细介绍之前我们还要多学一些Web应用方面的知识.

# Web容器

Tomcat 是一个Web容器（Apache是服务器）, 当一个请求从客户端发送到服务端时,请求会被web容器接收,web容器会找到请求的正确资源,然后把这些资源封装成响应提供给服务器,然后服务器再把响应发送给客户端 当web容器接收到请求的时候,如果请求是servlet类型的请求,容器会创建两个对象`HTTPServletRequest` 和 `HTTPServletResponse`然后他会从url中找到瑶要接收请求的servlet并且给请求单独创建一个线程.然后调用service方法,service方法再调doGet() 或者 doPost() 方法,Servlet方法生成动态页面并且将他写入response(响应)中,一旦servlet线程结束,容器将response转成HTTP respons并发送给客户端 web容器做的一些主要工作:

> **通信支持** –容器给web服务器和servlet(jsp)之间提供一种简单的通信方式,因为有了容器,我们不需要创建server socket来监听从web服务器传过来的请求,也不需要解析请求和生成响应,这些重要而且复杂的任务全部由容器来完成,我们只需要关注我们应用的业务逻辑就好.
>
> **生命周期和资源关联** – 容器会管理servlet的整个声明周期,容器负责把servlet加载到内存中,初始化servlet,调用servlet方法并且销毁他们,容器也提供了像JNDI一样对资源的共享和管理工具
>
> **多线程支持** – 容器为每一个servlet请求创建一个单独线程,所以servlet并不为每一个请求单独创建资源来节省时间和内存
>
> **对JSP的支持** – jsp看起来并不像标准的java程序但是容器却可以支持,应用中的每一个jsp都被容器解析并转换成servlet并像管理servlet一样来管理他们
>
> **复杂的任务** – Web容器管理资源池,做内存优化,执行垃圾回收,提供安全配置,支持多个应用,热部署和后台执行使生活变得美好

# Web应用程序目录结构

Java Web应用程序被打成war包（有固定的结构），你可以把上面的程序解压出来看看目录结构，内容应该像下面的图片一样：

![enter image description here](http://www.journaldev.com/wp-content/uploads/2013/08/WAR-directory-structure.png)

# 部署文件

web.xml是web应用程序的部署文件，包括对servlet的映射，欢迎页面，安全性配置，session失效时间等

这些就是关于java web应用的初级介绍，接下来我们会介绍更多的关于 Servlets and JSP　的东西

------

[wpdm_package id=’1216′]