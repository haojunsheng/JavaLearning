# 必备基础

## 1. **Web**容器学习路径

**Web容器是什么?**

早期的 Web 应用主要用于浏览新闻等静态页面，HTTP 服务器(比如 Apache、Nginx) 向浏览器返回静态 HTML，浏览器负责解析 HTML，将结果呈现给用户。随着互联网的发展，我们已经不满足于仅仅浏览静态页面，还希望通过一些交互操作，来获 取动态结果，因此也就需要一些扩展机制能够让 HTTP 服务器调用服务端程序。于是 Sun 公司推出了 Servlet 技术。你可以把 Servlet 简单理解为运行在服务端的 Java 小程序，但是 Servlet 没有 main 方法，不能独立运行，因此必须把它部署到 Servlet 容器 中，由容器来实例化并调用 Servlet。

## 2. **HTTP**协议必知必会

HTTP 协议和其他应用层协议一样，本质上是一种通信格式。由于 HTTP 是无状态的协议，为了识别请求是哪个用户发过来的，出现了 Cookie 和 Session 技术。Cookie 本质上就是一份存储在用户本地的文件，里面包含了每次请求中都需要传递的信息;Session 可以理解为服务器端开辟的存储空间，里面保存的信息用于保持状态。作为 Web 容器，Tomcat 负责创建和管理 Session，并提供了多种持久化方案来存储 Session。

## 3. S**ervlet**规范和Servlet容器

Servlet 容器用来加载和管理业务类。HTTP 服务器不直接跟业务类打交道，而是把请求交给 Servlet 容器去处理，Servlet 容器会将请求 转发到具体的 Servlet，如果这个 Servlet 还没创建，就加载并实例化这个 Servlet，然后调 用这个 Servlet 的接口方法。因此 Servlet 接口其实是**Servlet 容器跟具体业务类之间的接口**。

![image-20201030112553158](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201030112748.png)

HTTP 服务器不直接调用业务类，而是把请求交给容器来处理，容器通过 Servlet 接口调用业务 类。因此 Servlet 接口和 Servlet 容器的出现，达到了 HTTP 服务器与业务类解耦的目的。

```java
public interface Servlet {
  void init(ServletConfig config) throws ServletException;

  ServletConfig getServletConfig();

  void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

  String getServletInfo();

  void destroy();
}
```

ServletRequest 用来封装请求信息， ServletResponse 用来封装响应信息，因此**本质上这两个类是对通信协议的封装。**

你可以看到接口中还有两个跟生命周期有关的方法 init 和 destroy，这是一个比较贴心的设 计，Servlet 容器在加载 Servlet 类的时候会调用 init 方法，在卸载的时候会调用 destroy 方法。我们可能会在 init 方法里初始化一些资源，并在 destroy 方法里释放这些资源，比 如 Spring MVC 中的 DispatcherServlet，就是在 init 方法里创建了自己的 Spring 容器。

你还会注意到 ServletConfig 这个类，ServletConfig 的作用就是封装 Servlet 的初始化参 数。你可以在 web.xml 给 Servlet 配置参数，并在程序里通过 getServletConfig 方法拿到这些参数。

**Servlet** **容器**

当客户请求某个资源时，HTTP 服务器会用一个 ServletRequest 对象把客户的请求信息封 装起来，然后调用 Servlet 容器的 service 方法，Servlet 容器拿到请求后，根据请求的 URL 和 Servlet 的映射关系，找到相应的 Servlet，如果 Servlet 还没有被加载，就用反射 机制创建这个 Servlet，并调用 Servlet 的 init 方法来完成初始化，接着调用 Servlet 的 service 方法来处理请求，把 ServletResponse 对象返回给 HTTP 服务器，HTTP 服务器会 把响应发送给客户端。

![image-20201030182822079](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201031221814.png)

![image-20201030183031338](../../../../Library/Application Support/typora-user-images/image-20201030183031338.png)

Servlet 规范里定义了**ServletContext**这个接口来对应一个 Web 应用。Web 应用部署好 后，Servlet 容器在启动时会加载 Web 应用，并为每个 Web 应用创建唯一的 ServletContext 对象。你可以把 ServletContext 看成是一个全局对象，一个 Web 应用可 能有多个 Servlet，这些 Servlet 可以通过全局的 ServletContext 来共享数据，这些数据包 括 Web 应用的初始化参数、Web 应用目录下的文件资源等。由于 ServletContext 持有所 有 Servlet 实例，你还可以通过它来实现 Servlet 请求的转发。

**Filter**是过滤器，这个接口允许你对请求和响应做一些统一的定制化处理，比如你可以根据 请求的频率来限制访问，或者根据国家地区的不同来修改响应内容。过滤器的工作原理是这 样的:Web 应用部署完成后，Servlet 容器需要实例化 Filter 并把 Filter 链接成一个 FilterChain。当请求进来时，获取第一个 Filter 并调用 doFilter 方法，doFilter 方法负责 调用这个 FilterChain 中的下一个 Filter。

**Listener**是监听器，这是另一种扩展机制。当 Web 应用在 Servlet 容器中运行时，Servlet 容器内部会不断的发生各种事件，如 Web 应用的启动和停止、用户请求到达等。 Servlet容器提供了一些默认的监听器来监听这些事件，当事件发生时，Servlet 容器会负责调用监 听器的方法。当然，你可以定义自己的监听器去监听你感兴趣的事件，将监听器配置在 web.xml 中。比如 Spring 就实现了自己的监听器，来监听 ServletContext 的启动事件， 目的是当 Servlet 容器启动时，创建并初始化全局的 Spring 容器。

**Filter 是干预过程的**，它是过程的一部分，是基于过程行为的。

**Listener 是基于状态的**，任何行为改变同一个状态，触发的事件是一致的。

## **4. 实战:纯手工打造和运行一个**Servlet



##  5. **Tomcat**系统架构(上): 连接器是如何设计的?

Tomcat 实现 2 个核心功能:

- 处理 Socket 连接，负责网络字节流与Request和Response对象的转化。

- 加载和管理 Servlet，以及具体处理 Request 请求。

**因此 Tomcat 设计了两个核心组件连接器(Connector)和容器(Container)来分别做 这两件事情。连接器负责对外交流，容器负责内部处理。**

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201031222756.png" alt="image-20201031222750668" style="zoom:50%;" />

连接器对 Servlet 容器屏蔽了协议及 I/O 模型等的区别，无论是 HTTP 还是 AJP，在容器 中获取到的都是一个标准的 ServletRequest 对象。

我们可以把连接器的功能需求进一步细化，比如:

监听网络端口；接受网络连接请求；读取请求网络字节流；根据具体应用层协议(HTTP/AJP)解析字节流，生成统一的 Tomcat Request 对象；将 Tomcat Request 对象转成标准的 ServletRequest；调用 Servlet 容器，得到 ServletResponse；将 ServletResponse 转成 Tomcat Response 对象；将 Tomcat Response 转成网络字节流；将响应字节流写回给浏览器。



EndPoint 负责提供字节流给 Processor，Processor 负责 提供 Tomcat Request 对象给 Adapter，Adapter 负责提供 ServletRequest 对象给容器。

ProtocolHandler将网络通信和应用层协议解析放在一起。

![image-20201101111438112](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201101111438.png)

连接器模块用三个核心组件:Endpoint、Processor 和 Adapter 来分别做三件 事情，其中 Endpoint 和 Processor 放在一起抽象成了 ProtocolHandler 组件，它们的关系如下图所示。

![image-20201031230156023](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201031230156.png)

连接器用 ProtocolHandler 来处理网络连接和应用层协议，包含了 2 个 重要部件:EndPoint 和 Processor。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201101225612.png" alt="image-20201101225612534" style="zoom:50%;" />

- EndPoint：EndPoint 是通信端点，即通信监听的接口，是具体的 Socket 接收和发送处理器，是对传 输层的抽象，因此 EndPoint 是用来实现 TCP/IP 协议的。EndPoint 是一个接口，对应的抽象实现类是 AbstractEndpoint，而 AbstractEndpoint 的具体子类，比如在 NioEndpoint 和 Nio2Endpoint 中，有两个重要的子组件: Acceptor 和 SocketProcessor。其中 Acceptor 用于监听 Socket 连接请求。SocketProcessor 用于处理接收到的 Socket 请求，它实现 Runnable 接口，在 Run 方法里调用协议处理组件 Processor 进行处理。为 了提高处理能力，SocketProcessor 被提交到线程池来执行。而这个线程池叫作执行器 (Executor)。

Tomcat 的整体架构包含了两个核心组件连接器和容器。连接器负责对外交流，容器负责内 部处理。连接器用 ProtocolHandler 接口来封装通信协议和 I/O 模型的差异， ProtocolHandler 内部又分为 EndPoint 和 Processor 模块，EndPoint 负责底层 Socket 通信，Proccesor 负责应用层协议解析。连接器通过适配器 Adapter 调用容器。

## 6. **Tomcat**系统架构(下):聊聊多层容器的设计





# 参考

1. [tomcat源码](https://github.com/apache/tomcat)
2. 

















