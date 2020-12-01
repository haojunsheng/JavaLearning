<!--ts-->

<!--te-->

# 必备基础

## 1. **Web**容器学习路径

### **Web容器是什么?**

早期的 Web 应用主要用于浏览新闻等静态页面，HTTP 服务器(比如 Apache、Nginx) 向浏览器返回静态 HTML，浏览器负责解析 HTML，将结果呈现给用户。随着互联网的发展，我们已经不满足于仅仅浏览静态页面，还希望通过一些交互操作，来获 取动态结果，因此也就需要一些扩展机制能够让 HTTP 服务器调用服务端程序。于是 Sun 公司推出了 Servlet 技术。你可以把 Servlet 简单理解为运行在服务端的 Java 小程序，但是 Servlet 没有 main 方法，不能独立运行，因此必须把它部署到 Servlet 容器 中，由容器来实例化并调用 Servlet。

### Web 容器该怎么学？

**操作系统基础**

Java 语言其实是对操作系统 API 的封装，上层应用包括 Web 容器都是通过操作系统来工作的，因此掌握相关的操作系统原理是我们深刻理解 Web 容器的基础。对于 Web 容器来说，操作系统方面你应该掌握它的工作原理，比如什么是进程、什么是内核、什么是内核空间和用户空间、进程间通信的方式、进程和线程的区别、线程同步的方式、什么是虚拟内存、内存分配的过程、什么是 I/O、什么是 I/O 模型、阻塞与非阻塞的区别、同步与异步的区别、网络通信的原理、OSI 七层网络模型以及 TCP/IP、UDP 和 HTTP 协议。总之一句话，基础扎实了，你学什么都快。关于操作系统的学习，我推荐你读一读《UNIX 环境高级编程》这本经典书籍。

**Java 语言基础**

Java 的基础知识包括 Java 基本语法、面向对象设计的概念（封装、继承、多态、接口、抽象类等）、Java 集合的使用、Java I/O 体系、异常处理、基本的多线程并发编程（包括线程同步、原子类、线程池、并发容器的使用和原理）、Java 网络编程（I/O 模型 BIO、NIO、AIO 的原理和相应的 Java API）、Java 注解以及 Java 反射的原理等。此外你还需要了解一些 JVM 的基本知识，比如 JVM 的类加载机制、JVM 内存模型、JVM 内存空间分布、JVM 内存和本地内存的区别以及 JVM GC 的原理等。这方面我推荐的经典书籍有《Java 核心技术》、《Java 编程思想》、《Java 并发编程实战》和《深入理解 Java 虚拟机：JVM 高级特性与最佳实践》等。

**Java Web** 

开发基础具备了一定的操作系统和 Java 基础，接下来就可以开始学习 Java Web 开发，你可以开始学习一些通用的设计原则和设计模式。这个阶段的核心任务就是了解 Web 的工作原理，同时提高你的设计能力，注重代码的质量。我的建议是可以从学习 Servlet 和 Servlet 容器开始。我见过不少同学跳过这个阶段直接学 Web 框架，这样做的话结果会事倍功半。

为什么这么说呢？Web 框架的本质是，开发者在使用某种语言编写 Web 应用时，总结出的一些经验和设计思路。很多 Web 框架都是从实际的 Web 项目抽取出来的，其目的是用于简化 Web 应用程序开发。我以 Spring 框架为例，给你讲讲 Web框架是怎么产生的。Web 应用程序的开发主要是完成两方面的工作。

- 设计并实现类，包括定义类与类之间的关系，以及实现类的方法，方法对数据的操作就是具体的业务逻辑。
- 类设计好之后，需要创建这些类的实例并根据类与类的关系把它们组装在一起，这样类的实例才能一起协作完成业务功能。

Spring 又是用容器来完成这个工作的的，容器负责创建、组装和销毁这些类的实例，而应用只需要通过配置文件或者注解来告诉 Spring 类与类之间的关系。但是容器的概念不是 Spring 发明的，最开始来源于 Servlet 容器，并且 Servlet 容器也是通过配置文件来加载 Servlet 的。你会发现它们的“元神”是相似的，在 Web 应用的开发中，有一些本质的东西是不变的，而很多“元神”就藏在“老祖宗”那里，藏在 Servlet 容器的设计里。

Spring 框架就是对 Servlet 的封装，Spring 应用本身就是一个 Servlet，而 Servlet 容器是管理和运行 Servlet 的，因此我们需要先理解 Servlet 和 Servlet 容器是怎样工作的，才能更好地理解 Spring。

## 2. **HTTP**协议必知必会

HTTP 协议和其他应用层协议一样，本质上是一种通信格式。由于 HTTP 是无状态的协议，为了识别请求是哪个用户发过来的，出现了 Cookie 和 Session 技术。Cookie 本质上就是一份存储在用户本地的文件，里面包含了每次请求中都需要传递的信息;Session 可以理解为服务器端开辟的存储空间，里面保存的信息用于保持状态。作为 Web 容器，Tomcat 负责创建和管理 Session，并提供了多种持久化方案来存储 Session。

TODO

## 3. S**ervlet**规范和Servlet容器

通过专栏上一期的学习我们知道，浏览器发给服务端的是一个 HTTP 格式的请求，HTTP 服务器收到这个请求后，需要调用服务端程序来处理，所谓的服务端程序就是你写的 Java 类，一般来说不同的请求需要由不同的 Java 类来处理。

那么问题来了，HTTP 服务器怎么知道要调用哪个 Java 类的哪个方法呢。最直接的做法是在 HTTP 服务器代码里写一大堆 if else 逻辑判断：如果是 A 请求就调 X 类的 M1 方法，如果是 B 请求就调 Y 类的 M2 方法。但这样做明显有问题，因为 HTTP 服务器的代码跟业务逻辑耦合在一起了，如果新加一个业务方法还要改 HTTP 服务器的代码。

那该怎么解决这个问题呢？我们知道，面向接口编程是解决耦合问题的法宝，于是有一伙人就定义了一个接口，各种业务类都必须实现这个接口，这个接口就叫 Servlet 接口，有时我们也把实现了 Servlet 接口的业务类叫作 Servlet。

但是这里还有一个问题，对于特定的请求，HTTP 服务器如何知道由哪个 Servlet 来处理呢？Servlet 又是由谁来实例化呢？显然 HTTP 服务器不适合做这个工作，否则又和业务类耦合了。

Servlet 容器用来加载和管理业务类。HTTP 服务器不直接跟业务类打交道，而是把请求交给 Servlet 容器去处理，Servlet 容器会将请求 转发到具体的 Servlet，如果这个 Servlet 还没创建，就加载并实例化这个 Servlet，然后调 用这个 Servlet 的接口方法。因此 Servlet 接口其实是**Servlet 容器跟具体业务类之间的接口**。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201130193518.png" alt="image-20201030112553158" style="zoom:50%;" />

图的左边表示 HTTP 服务器直接调用具体业务类，它们是紧耦合的。再看图的右边，HTTP 服务器不直接调用业务类，而是把请求交给容器来处理，容器通过 Servlet 接口调用业务类。因此 Servlet 接口和 Servlet 容器的出现，达到了 HTTP 服务器与业务类解耦的目的。

而 Servlet 接口和 Servlet 容器这一整套规范叫作 Servlet 规范。Tomcat 和 Jetty 都按照 Servlet 规范的要求实现了 Servlet 容器，同时它们也具有 HTTP 服务器的功能。作为 Java 程序员，如果我们要实现新的业务功能，只需要实现一个 Servlet，并把它注册到 Tomcat（Servlet 容器）中，剩下的事情就由 Tomcat 帮我们处理了。

### Servlet 接口

```java
public interface Servlet {
  void init(ServletConfig config) throws ServletException;

  ServletConfig getServletConfig();

  void service(ServletRequest req, ServletResponse res) throws ServletException, IOException;

  String getServletInfo();

  void destroy();
}
```

其中最重要是的 service 方法，具体业务类在这个方法里实现处理逻辑。这个方法有两个参数：ServletRequest 和 ServletResponse。ServletRequest 用来封装请求信息，ServletResponse 用来封装响应信息，因此本质上这两个类是对通信协议的封装。

比如 HTTP 协议中的请求和响应就是对应了 HttpServletRequest 和 HttpServletResponse 这两个类。你可以通过 HttpServletRequest 来获取所有请求相关的信息，包括请求路径、Cookie、HTTP 头、请求参数等。此外，我在专栏上一期提到过，我们还可以通过 HttpServletRequest 来创建和获取 Session。而 HttpServletResponse 是用来封装 HTTP 响应的。

你可以看到接口中还有两个跟生命周期有关的方法 init 和 destroy，这是一个比较贴心的设计，Servlet 容器在加载 Servlet 类的时候会调用 init 方法，在卸载的时候会调用 destroy 方法。我们可能会在 init 方法里初始化一些资源，并在 destroy 方法里释放这些资源，比如 Spring MVC 中的 DispatcherServlet，就是在 init 方法里创建了自己的 Spring 容器。

你还会注意到 ServletConfig 这个类，ServletConfig 的作用就是封装 Servlet 的初始化参数。你可以在web.xml给 Servlet 配置参数，并在程序里通过 getServletConfig 方法拿到这些参数。

我们知道，有接口一般就有抽象类，抽象类用来实现接口和封装通用的逻辑，因此 Servlet 规范提供了 GenericServlet 抽象类，我们可以通过扩展它来实现 Servlet。虽然 Servlet 规范并不在乎通信协议是什么，但是大多数的 Servlet 都是在 HTTP 环境中处理的，因此 Servet 规范还提供了 HttpServlet 来继承 GenericServlet，并且加入了 HTTP 特性。这样我们通过继承 HttpServlet 类来实现自己的 Servlet，只需要重写两个方法：doGet 和 doPost。

### **Servlet** **容器**

我在前面提到，为了解耦，HTTP 服务器不直接调用 Servlet，而是把请求交给 Servlet 容器来处理，那 Servlet 容器又是怎么工作的呢？接下来我会介绍 Servlet 容器大体的工作流程，一起来聊聊我们非常关心的两个话题：Web 应用的目录格式是什么样的，以及我该怎样扩展和定制化 Servlet 容器的功能。

**工作流程**

当客户请求某个资源时，HTTP 服务器会用一个 ServletRequest 对象把客户的请求信息封 装起来，然后调用 Servlet 容器的 service 方法，Servlet 容器拿到请求后，根据请求的 URL 和 Servlet 的映射关系，找到相应的 Servlet，如果 Servlet 还没有被加载，就用反射 机制创建这个 Servlet，并调用 Servlet 的 init 方法来完成初始化，接着调用 Servlet 的 service 方法来处理请求，把 ServletResponse 对象返回给 HTTP 服务器，HTTP 服务器会 把响应发送给客户端。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201103246.png)

**web应用**

Servlet 容器会实例化和调用 Servlet，那 Servlet 是怎么注册到 Servlet 容器中的呢？一般来说，我们是以 Web 应用程序的方式来部署 Servlet 的，而根据 Servlet 规范，Web 应用程序有一定的目录结构，在这个目录下分别放置了 Servlet 的类文件、配置文件以及静态资源，Servlet 容器通过读取配置文件，就能找到并加载 Servlet。Web 应用的目录结构大概是下面这样的：

```
| -  MyWebApp
      | -  WEB-INF/web.xml        -- 配置文件，用来配置Servlet等
      | -  WEB-INF/lib/           -- 存放Web应用所需各种JAR包
      | -  WEB-INF/classes/       -- 存放你的应用类，比如Servlet类
      | -  META-INF/              -- 目录存放工程的一些信息
```

Servlet 规范里定义了 ServletContext 这个接口来对应一个 Web 应用。Web 应用部署好后，Servlet 容器在启动时会加载 Web 应用，并为每个 Web 应用创建唯一的 ServletContext 对象。你可以把 ServletContext 看成是一个全局对象，一个 Web 应用可能有多个 Servlet，这些 Servlet 可以通过全局的 ServletContext 来共享数据，这些数据包括 Web 应用的初始化参数、Web 应用目录下的文件资源等。由于 ServletContext 持有所有 Servlet 实例，你还可以通过它来实现 Servlet 请求的转发。

**扩展机制**

不知道你有没有发现，引入了 Servlet 规范后，你不需要关心 Socket 网络通信、不需要关心 HTTP 协议，也不需要关心你的业务类是如何被实例化和调用的，因为这些都被 Servlet 规范标准化了，你只要关心怎么实现的你的业务逻辑。这对于程序员来说是件好事，但也有不方便的一面。所谓规范就是说大家都要遵守，就会千篇一律，但是如果这个规范不能满足你的业务的个性化需求，就有问题了，因此设计一个规范或者一个中间件，要充分考虑到可扩展性。Servlet 规范提供了两种扩展机制：Filter 和 Listener。

**Filter**是过滤器，这个接口允许你对请求和响应做一些统一的定制化处理，比如你可以根据 请求的频率来限制访问，或者根据国家地区的不同来修改响应内容。过滤器的工作原理是这 样的:Web 应用部署完成后，Servlet 容器需要实例化 Filter 并把 Filter 链接成一个 FilterChain。当请求进来时，获取第一个 Filter 并调用 doFilter 方法，doFilter 方法负责 调用这个 FilterChain 中的下一个 Filter。

**Listener**是监听器，这是另一种扩展机制。当 Web 应用在 Servlet 容器中运行时，Servlet 容器内部会不断的发生各种事件，如 Web 应用的启动和停止、用户请求到达等。 Servlet容器提供了一些默认的监听器来监听这些事件，当事件发生时，Servlet 容器会负责调用监 听器的方法。当然，你可以定义自己的监听器去监听你感兴趣的事件，将监听器配置在 web.xml 中。比如 Spring 就实现了自己的监听器，来监听 ServletContext 的启动事件， 目的是当 Servlet 容器启动时，创建并初始化全局的 Spring 容器。

**Filter 是干预过程的**，它是过程的一部分，是基于过程行为的。

**Listener 是基于状态的**，任何行为改变同一个状态，触发的事件是一致的。

## **4. 实战:纯手工打造和运行一个**Servlet



##  5. **Tomcat**系统架构(上): 连接器是如何设计的?

### Tomcat 总体架构

Tomcat 实现 2 个核心功能:

- 处理 Socket 连接，负责网络字节流与Request和Response对象的转化。

- 加载和管理 Servlet，以及具体处理 Request 请求。

**因此 Tomcat 设计了两个核心组件连接器(Connector)和容器(Container)来分别做 这两件事情。连接器负责对外交流，容器负责内部处理。**

在开始讲连接器前，我先铺垫一下 Tomcat 支持的多种 I/O 模型和应用层协议。

Tomcat 支持的 I/O 模型有：

- NIO：非阻塞 I/O，采用 Java NIO 类库实现。
- NIO.2：异步 I/O，采用 JDK 7 最新的 NIO.2 类库实现。
- APR：采用 Apache 可移植运行库实现，是 C/C++ 编写的本地库。

Tomcat 支持的应用层协议有：

- HTTP/1.1：这是大部分 Web 应用采用的访问协议。
- AJP：用于和 Web 服务器集成（如 Apache）。
- HTTP/2：HTTP 2.0 大幅度的提升了 Web 性能。

Tomcat 为了实现支持多种 I/O 模型和应用层协议，一个容器可能对接多个连接器，就好比一个房间有多个门。但是单独的连接器或者容器都不能对外提供服务，需要把它们组装起来才能工作，组装后这个整体叫作 Service 组件。这里请你注意，Service 本身没有做什么重要的事情，只是在连接器和容器外面多包了一层，把它们组装在一起。Tomcat 内可能有多个 Service，这样的设计也是出于灵活性的考虑。通过在 Tomcat 中配置多个 Service，可以实现通过不同的端口号来访问同一台机器上部署的不同应用。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201001634.jpg" alt="img" style="zoom:33%;" />

从图上你可以看到，最顶层是 Server，这里的 Server 指的就是一个 Tomcat 实例。一个 Server 中有一个或者多个 Service，一个 Service 中有多个连接器和一个容器。连接器与容器之间通过标准的 ServletRequest 和 ServletResponse 通信。

### 连接器

连接器对 Servlet 容器屏蔽了协议及 I/O 模型等的区别，无论是 HTTP 还是 AJP，在容器中获取到的都是一个标准的 ServletRequest 对象。

我们可以把连接器的功能需求进一步细化，比如:

- 监听网络端口；
- 接受网络连接请求；
- 读取请求网络字节流；
- 根据具体应用层协议(HTTP/AJP)解析字节流，生成统一的 Tomcat Request 对象；
- 将 Tomcat Request 对象转成标准的 ServletRequest；
- 调用 Servlet 容器，得到 ServletResponse；
- 将 ServletResponse 转成 Tomcat Response 对象；
- 将 Tomcat Response 转成网络字节流；
- 将响应字节流写回给浏览器。

需求列清楚后，我们要考虑的下一个问题是，连接器应该有哪些子模块？优秀的模块化设计应该考虑高内聚、低耦合。

- 高内聚是指相关度比较高的功能要尽可能集中，不要分散。
- 低耦合是指两个相关的模块要尽可能减少依赖的部分和降低依赖的程度，不要让两个模块产生强依赖。

通过分析连接器的详细功能列表，我们发现连接器需要完成 3 个高内聚的功能：

- 网络通信。
- 应用层协议解析。
- Tomcat Request/Response 与 ServletRequest/ServletResponse 的转化。

因此 Tomcat 的设计者设计了 3 个组件来实现这 3 个功能，分别是 Endpoint、Processor 和 Adapter。

组件之间通过抽象接口交互。这样做还有一个好处是封装变化。这是面向对象设计的精髓，将系统中经常变化的部分和稳定的部分隔离，有助于增加复用性，并降低系统耦合度。

网络通信的 I/O 模型是变化的，可能是非阻塞 I/O、异步 I/O 或者 APR。应用层协议也是变化的，可能是 HTTP、HTTPS、AJP。浏览器端发送的请求信息也是变化的。

但是整体的处理逻辑是不变的，Endpoint 负责提供字节流给 Processor，Processor 负责提供 Tomcat Request 对象给 Adapter，Adapter 负责提供 ServletRequest 对象给容器。

如果要支持新的 I/O 方案、新的应用层协议，只需要实现相关的具体子类，上层通用的处理逻辑是不变的。

由于 I/O 模型和应用层协议可以自由组合，比如 NIO + HTTP 或者 NIO.2 + AJP。Tomcat 的设计者将网络通信和应用层协议解析放在一起考虑，设计了一个叫 ProtocolHandler 的接口来封装这两种变化点。各种协议和通信模型的组合有相应的具体实现类。比如：Http11NioProtocol 和 AjpNioProtocol。

除了这些变化点，系统也存在一些相对稳定的部分，因此 Tomcat 设计了一系列抽象基类来封装这些稳定的部分，抽象基类 AbstractProtocol 实现了 ProtocolHandler 接口。每一种应用层协议有自己的抽象基类，比如 AbstractAjpProtocol 和 AbstractHttp11Protocol，具体协议的实现类扩展了协议层抽象基类。下面我整理一下它们的继承关系。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201114733.jpg" alt="img" style="zoom:50%;" />

通过上面的图，你可以清晰地看到它们的继承和层次关系，这样设计的目的是尽量将稳定的部分放到抽象基类，同时每一种 I/O 模型和协议的组合都有相应的具体实现类，我们在使用时可以自由选择。

小结一下，连接器模块用三个核心组件：Endpoint、Processor 和 Adapter 来分别做三件事情，其中 Endpoint 和 Processor 放在一起抽象成了 ProtocolHandler 组件，它们的关系如下图所示。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201114938.jpg)

### ProtocolHandler 组件

由上文我们知道，连接器用 ProtocolHandler 来处理网络连接和应用层协议，包含了 2 个重要部件：Endpoint 和 Processor，下面我来详细介绍它们的工作原理。

- Endpoint

Endpoint 是通信端点，即通信监听的接口，是具体的 Socket 接收和发送处理器，是对传输层的抽象，因此 Endpoint 是用来实现 TCP/IP 协议的。

Endpoint 是一个接口，对应的抽象实现类是 AbstractEndpoint，而 AbstractEndpoint 的具体子类，比如在 NioEndpoint 和 Nio2Endpoint 中，有两个重要的子组件：Acceptor 和 SocketProcessor。

其中 Acceptor 用于监听 Socket 连接请求。SocketProcessor 用于处理接收到的 Socket 请求，它实现 Runnable 接口，在 run 方法里调用协议处理组件 Processor 进行处理。为了提高处理能力，SocketProcessor 被提交到线程池来执行。而这个线程池叫作执行器（Executor)，我在后面的专栏会详细介绍 Tomcat 如何扩展原生的 Java 线程池。

- Processor

如果说 Endpoint 是用来实现 TCP/IP 协议的，那么 Processor 用来实现 HTTP 协议，Processor 接收来自 Endpoint 的 Socket，读取字节流解析成 Tomcat Request 和 Response 对象，并通过 Adapter 将其提交到容器处理，Processor 是对应用层协议的抽象。

Processor 是一个接口，定义了请求的处理等方法。它的抽象实现类 AbstractProcessor 对一些协议共有的属性进行封装，没有对方法进行实现。具体的实现有 AjpProcessor、Http11Processor 等，这些具体实现类实现了特定协议的解析方法和请求处理方式。

我们再来看看连接器的组件图：

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201115342.jpg" alt="img" style="zoom:33%;" />

从图中我们看到，Endpoint 接收到 Socket 连接后，生成一个 SocketProcessor 任务提交到线程池去处理，SocketProcessor 的 run 方法会调用 Processor 组件去解析应用层协议，Processor 通过解析生成 Request 对象后，会调用 Adapter 的 Service 方法。

到这里我们学习了 ProtocolHandler 的总体架构和工作原理，关于 Endpoint 的详细设计，后面我还会专门介绍 Endpoint 是如何最大限度地利用 Java NIO 的非阻塞以及 NIO.2 的异步特性，来实现高并发。

### Adapter 组件

我在前面说过，由于协议不同，客户端发过来的请求信息也不尽相同，Tomcat 定义了自己的 Request 类来“存放”这些请求信息。ProtocolHandler 接口负责解析请求并生成 Tomcat Request 类。但是这个 Request 对象不是标准的 ServletRequest，也就意味着，不能用 Tomcat Request 作为参数来调用容器。Tomcat 设计者的解决方案是引入 CoyoteAdapter，这是适配器模式的经典运用，连接器调用 CoyoteAdapter 的 sevice 方法，传入的是 Tomcat Request 对象，CoyoteAdapter 负责将 Tomcat Request 转成 ServletRequest，再调用容器的 service 方法。

### 本期精华

Tomcat 的整体架构包含了两个核心组件连接器和容器。连接器负责对外交流，容器负责内部处理。连接器用 ProtocolHandler 接口来封装通信协议和 I/O 模型的差异，ProtocolHandler 内部又分为 Endpoint 和 Processor 模块，Endpoint 负责底层 Socket 通信，Processor 负责应用层协议解析。连接器通过适配器 Adapter 调用容器。

## 6. **Tomcat**系统架构(下):聊聊多层容器的设计

专栏上一期我们学完了连接器的设计，今天我们一起来看一下 Tomcat 的容器设计。先复习一下，上期我讲到了 Tomcat 有两个核心组件：连接器和容器，其中连接器负责外部交流，容器负责内部处理。具体来说就是，连接器处理 Socket 通信和应用层协议的解析，得到 Servlet 请求；而容器则负责处理 Servlet 请求。我们通过下面这张图来回忆一下。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201120320.jpg" alt="img" style="zoom:50%;" />

### 容器的层次结构

Tomcat 设计了 4 种容器，分别是 Engine、Host、Context 和 Wrapper。这 4 种容器不是平行关系，而是父子关系。下面我画了一张图帮你理解它们的关系。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201132856.jpg" alt="img" style="zoom:33%;" />

你可能会问，为什么要设计成这么多层次的容器，这不是增加了复杂度吗？其实这背后的考虑是，Tomcat 通过一种分层的架构，使得 Servlet 容器具有很好的灵活性。

Context 表示一个 Web 应用程序；Wrapper 表示一个 Servlet，一个 Web 应用程序中可能会有多个 Servlet；Host 代表的是一个虚拟主机，或者说一个站点，可以给 Tomcat 配置多个虚拟主机地址，而一个虚拟主机下可以部署多个 Web 应用程序；Engine 表示引擎，用来管理多个虚拟站点，一个 Service 最多只能有一个 Engine。

你可以再通过 Tomcat 的server.xml配置文件来加深对 Tomcat 容器的理解。Tomcat 采用了组件化的设计，它的构成组件都是可配置的，其中最外层的是 Server，其他组件按照一定的格式要求配置在这个顶层容器中。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201133244.jpg" alt="img" style="zoom: 50%;" />

那么，Tomcat 是怎么管理这些容器的呢？你会发现这些容器具有父子关系，形成一个树形结构，你可能马上就想到了设计模式中的组合模式。没错，Tomcat 就是用组合模式来管理这些容器的。具体实现方法是，所有容器组件都实现了 Container 接口，因此组合模式可以使得用户对单容器对象和组合容器对象的使用具有一致性。这里单容器对象指的是最底层的 Wrapper，组合容器对象指的是上面的 Context、Host 或者 Engine。Container 接口定义如下：

```java
public interface Container extends Lifecycle {
    public void setName(String name);
    public Container getParent();
    public void setParent(Container container);
    public void addChild(Container child);
    public void removeChild(Container child);
    public Container findChild(String name);
}
```

正如我们期望的那样，我们在上面的接口看到了 getParent、setParent、addChild 和 removeChild 等方法。你可能还注意到 Container 接口扩展了 Lifecycle 接口，Lifecycle 接口用来统一管理各组件的生命周期。

### 请求定位 Servlet 的过程

你可能好奇，设计了这么多层次的容器，Tomcat 是怎么确定请求是由哪个 Wrapper 容器里的 Servlet 来处理的呢？答案是，Tomcat 是用 Mapper 组件来完成这个任务的。

Mapper 组件的功能就是将用户请求的 URL 定位到一个 Servlet，它的工作原理是：Mapper 组件里保存了 Web 应用的配置信息，其实就是容器组件与访问路径的映射关系，比如 Host 容器里配置的域名、Context 容器里的 Web 应用路径，以及 Wrapper 容器里 Servlet 映射的路径，你可以想象这些配置信息就是一个多层次的 Map。

当一个请求到来时，Mapper 组件通过解析请求 URL 里的域名和路径，再到自己保存的 Map 里去查找，就能定位到一个 Servlet。请你注意，一个请求 URL 最后只会定位到一个 Wrapper 容器，也就是一个 Servlet。

读到这里你可能感到有些抽象，接下来我通过一个例子来解释这个定位的过程。

假如有一个网购系统，有面向网站管理人员的后台管理系统，还有面向终端客户的在线购物系统。这两个系统跑在同一个 Tomcat 上，为了隔离它们的访问域名，配置了两个虚拟域名：manage.shopping.com和user.shopping.com，网站管理人员通过manage.shopping.com域名访问 Tomcat 去管理用户和商品，而用户管理和商品管理是两个单独的 Web 应用。终端客户通过user.shopping.com域名去搜索商品和下订单，搜索功能和订单管理也是两个独立的 Web 应用。

针对这样的部署，Tomcat 会创建一个 Service 组件和一个 Engine 容器组件，在 Engine 容器下创建两个 Host 子容器，在每个 Host 容器下创建两个 Context 子容器。由于一个 Web 应用通常有多个 Servlet，Tomcat 还会在每个 Context 容器里创建多个 Wrapper 子容器。每个容器都有对应的访问路径，你可以通过下面这张图来帮助你理解。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201141610.jpg" alt="img" style="zoom:33%;" />

假如有用户访问一个 URL，比如图中的http://user.shopping.com:8080/order/buy，Tomcat 如何将这个 URL 定位到一个 Servlet 呢？

- 首先，根据协议和端口号选定 Service 和 Engine。

我们知道 Tomcat 的每个连接器都监听不同的端口，比如 Tomcat 默认的 HTTP 连接器监听 8080 端口、默认的 AJP 连接器监听 8009 端口。上面例子中的 URL 访问的是 8080 端口，因此这个请求会被 HTTP 连接器接收，而一个连接器是属于一个 Service 组件的，这样 Service 组件就确定了。我们还知道一个 Service 组件里除了有多个连接器，还有一个容器组件，具体来说就是一个 Engine 容器，因此 Service 确定了也就意味着 Engine 也确定了。

- 然后，根据域名选定 Host。

Service 和 Engine 确定后，Mapper 组件通过 URL 中的域名去查找相应的 Host 容器，比如例子中的 URL 访问的域名是user.shopping.com，因此 Mapper 会找到 Host2 这个容器。

- 之后，根据 URL 路径找到 Context 组件。

Host 确定以后，Mapper 根据 URL 的路径来匹配相应的 Web 应用的路径，比如例子中访问的是/order，因此找到了 Context4 这个 Context 容器。

- 最后，根据 URL 路径找到 Wrapper（Servlet）。

Context 确定后，Mapper 再根据web.xml中配置的 Servlet 映射路径来找到具体的 Wrapper 和 Servlet。

看到这里，我想你应该已经了解了什么是容器，以及 Tomcat 如何通过一层一层的父子容器找到某个 Servlet 来处理请求。需要注意的是，并不是说只有 Servlet 才会去处理请求，实际上这个查找路径上的父子容器都会对请求做一些处理。我在上一期说过，连接器中的 Adapter 会调用容器的 Service 方法来执行 Servlet，最先拿到请求的是 Engine 容器，Engine 容器对请求做一些处理后，会把请求传给自己子容器 Host 继续处理，依次类推，最后这个请求会传给 Wrapper 容器，Wrapper 会调用最终的 Servlet 来处理。那么这个调用过程具体是怎么实现的呢？答案是使用 Pipeline-Valve 管道。

Pipeline-Valve 是责任链模式，责任链模式是指在一个请求处理的过程中有很多处理者依次对请求进行处理，每个处理者负责做自己相应的处理，处理完之后将再调用下一个处理者继续处理。

Valve 表示一个处理点，比如权限认证和记录日志。如果你还不太理解的话，可以来看看 Valve 和 Pipeline 接口中的关键方法。

```java
public interface Valve {
  public Valve getNext();
  public void setNext(Valve valve);
  public void invoke(Request request, Response response)
}
```

由于 Valve 是一个处理点，因此 invoke 方法就是来处理请求的。注意到 Valve 中有 getNext 和 setNext 方法，因此我们大概可以猜到有一个链表将 Valve 链起来了。请你继续看 Pipeline 接口：

```java
public interface Pipeline extends Contained {
  public void addValve(Valve valve);
  public Valve getBasic();
  public void setBasic(Valve valve);
  public Valve getFirst();
}
```

没错，Pipeline 中有 addValve 方法。Pipeline 中维护了 Valve 链表，Valve 可以插入到 Pipeline 中，对请求做某些处理。我们还发现 Pipeline 中没有 invoke 方法，因为整个调用链的触发是 Valve 来完成的，Valve 完成自己的处理后，调用getNext.invoke来触发下一个 Valve 调用。

每一个容器都有一个 Pipeline 对象，只要触发这个 Pipeline 的第一个 Valve，这个容器里 Pipeline 中的 Valve 就都会被调用到。但是，不同容器的 Pipeline 是怎么链式触发的呢，比如 Engine 中 Pipeline 需要调用下层容器 Host 中的 Pipeline。

这是因为 Pipeline 中还有个 getBasic 方法。这个 BasicValve 处于 Valve 链表的末端，它是 Pipeline 中必不可少的一个 Valve，负责调用下层容器的 Pipeline 里的第一个 Valve。我还是通过一张图来解释。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201143105.jpg" alt="img" style="zoom:50%;" />

整个调用过程由连接器中的 Adapter 触发的，它会调用 Engine 的第一个 Valve：

```
// Calling the container
connector.getService().getContainer().getPipeline().getFirst().invoke(request, response);
```

Wrapper 容器的最后一个 Valve 会创建一个 Filter 链，并调用 doFilter 方法，最终会调到 Servlet 的 service 方法。

你可能会问，前面我们不是讲到了 Filter，似乎也有相似的功能，那 Valve 和 Filter 有什么区别吗？它们的区别是：

- Valve 是 Tomcat 的私有机制，与 Tomcat 的基础架构 /API 是紧耦合的。Servlet API 是公有的标准，所有的 Web 容器包括 Jetty 都支持 Filter 机制。
- 另一个重要的区别是 Valve 工作在 Web 容器级别，拦截所有应用的请求；而 Servlet Filter 工作在应用级别，只能拦截某个 Web 应用的所有请求。如果想做整个 Web 容器的拦截器，必须通过 Valve 来实现。

### 精华

今天我们学习了 Tomcat 容器的层次结构、根据请求定位 Servlet 的过程，以及请求在容器中的调用过程。Tomcat 设计了多层容器是为了灵活性的考虑，灵活性具体体现在一个 Tomcat 实例（Server）可以有多个 Service，每个 Service 通过多个连接器监听不同的端口，而一个 Service 又可以支持多个虚拟主机。一个 URL 网址可以用不同的主机名、不同的端口和不同的路径来访问特定的 Servlet 实例。

请求的链式调用是基于 Pipeline-Valve 责任链来完成的，这样的设计使得系统具有良好的可扩展性，如果需要扩展容器本身的功能，只需要增加相应的 Valve 即可。

## 07 | Tomcat如何实现一键式启停？

通过前面的学习，相信你对 Tomcat 的架构已经有所了解，知道了 Tomcat 都有哪些组件，组件之间是什么样的关系，以及 Tomcat 是怎么处理一个 HTTP 请求的。下面我们通过一张简化的类图来回顾一下，从图上你可以看到各种组件的层次关系，图中的虚线表示一个请求在 Tomcat 中流转的过程。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201143801.png" alt="img" style="zoom:50%;" />

上面这张图描述了组件之间的静态关系，如果想让一个系统能够对外提供服务，我们需要创建、组装并启动这些组件；在服务停止的时候，我们还需要释放资源，销毁这些组件，因此这是一个动态的过程。也就是说，Tomcat 需要动态地管理这些组件的生命周期。

在我们实际的工作中，如果你需要设计一个比较大的系统或者框架时，你同样也需要考虑这几个问题：如何统一管理组件的创建、初始化、启动、停止和销毁？如何做到代码逻辑清晰？如何方便地添加或者删除组件？如何做到组件启动和停止不遗漏、不重复？

今天我们就来解决上面的问题，在这之前，先来看看组件之间的关系。如果你仔细分析过这些组件，可以发现它们具有两层关系。

- 第一层关系是组件有大有小，大组件管理小组件，比如 Server 管理 Service，Service 又管理连接器和容器。
- 第二层关系是组件有外有内，外层组件控制内层组件，比如连接器是外层组件，负责对外交流，外层组件调用内层组件完成业务功能。也就是说，请求的处理过程是由外层组件来驱动的。

这两层关系决定了系统在创建组件时应该遵循一定的顺序。

- 第一个原则是先创建子组件，再创建父组件，子组件需要被“注入”到父组件中。
- 第二个原则是先创建内层组件，再创建外层组件，内层组件需要被“注入”到外层组件。

因此，最直观的做法就是将图上所有的组件按照先小后大、先内后外的顺序创建出来，然后组装在一起。不知道你注意到没有，这个思路其实很有问题！因为这样不仅会造成代码逻辑混乱和组件遗漏，而且也不利于后期的功能扩展。为了解决这个问题，我们希望找到一种通用的、统一的方法来管理组件的生命周期，就像汽车“一键启动”那样的效果。

### 一键式启停：Lifecycle 接口

我在前面说到过，设计就是要找到系统的变化点和不变点。这里的不变点就是每个组件都要经历创建、初始化、启动这几个过程，这些状态以及状态的转化是不变的。而变化点是每个具体组件的初始化方法，也就是启动方法是不一样的。

因此，我们把不变点抽象出来成为一个接口，这个接口跟生命周期有关，叫作 Lifecycle。Lifecycle 接口里应该定义这么几个方法：init、start、stop 和 destroy，每个具体的组件去实现这些方法。

理所当然，在父组件的 init 方法里需要创建子组件并调用子组件的 init 方法。同样，在父组件的 start 方法里也需要调用子组件的 start 方法，因此调用者可以无差别的调用各组件的 init 方法和 start 方法，这就是组合模式的使用，并且只要调用最顶层组件，也就是 Server 组件的 init 和 start 方法，整个 Tomcat 就被启动起来了。下面是 Lifecycle 接口的定义。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201144406.png)

### 可扩展性：Lifecycle 事件

我们再来考虑另一个问题，那就是系统的可扩展性。因为各个组件 init 和 start 方法的具体实现是复杂多变的，比如在 Host 容器的启动方法里需要扫描 webapps 目录下的 Web 应用，创建相应的 Context 容器，如果将来需要增加新的逻辑，直接修改 start 方法？这样会违反开闭原则，那如何解决这个问题呢？开闭原则说的是为了扩展系统的功能，你不能直接修改系统中已有的类，但是你可以定义新的类。

我们注意到，组件的 init 和 start 调用是由它的父组件的状态变化触发的，上层组件的初始化会触发子组件的初始化，上层组件的启动会触发子组件的启动，因此我们把组件的生命周期定义成一个个状态，把状态的转变看作是一个事件。而事件是有监听器的，在监听器里可以实现一些逻辑，并且监听器也可以方便的添加和删除，这就是典型的观察者模式。

具体来说就是在 Lifecycle 接口里加入两个方法：添加监听器和删除监听器。除此之外，我们还需要定义一个 Enum 来表示组件有哪些状态，以及处在什么状态会触发什么样的事件。因此 Lifecycle 接口和 LifecycleState 就定义成了下面这样。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201144929.png)

从图上你可以看到，组件的生命周期有 NEW、INITIALIZING、INITIALIZED、STARTING_PREP、STARTING、STARTED 等，而一旦组件到达相应的状态就触发相应的事件，比如 NEW 状态表示组件刚刚被实例化；而当 init 方法被调用时，状态就变成 INITIALIZING 状态，这个时候，就会触发 BEFORE_INIT_EVENT 事件，如果有监听器在监听这个事件，它的方法就会被调用。

### 重用性：LifecycleBase 抽象基类

有了接口，我们就要用类去实现接口。一般来说实现类不止一个，不同的类在实现接口时往往会有一些相同的逻辑，如果让各个子类都去实现一遍，就会有重复代码。那子类如何重用这部分逻辑呢？其实就是定义一个基类来实现共同的逻辑，然后让各个子类去继承它，就达到了重用的目的。

而基类中往往会定义一些抽象方法，所谓的抽象方法就是说基类不会去实现这些方法，而是调用这些方法来实现骨架逻辑。抽象方法是留给各个子类去实现的，并且子类必须实现，否则无法实例化。

比如宝马和荣威的底盘和骨架其实是一样的，只是发动机和内饰等配套是不一样的。底盘和骨架就是基类，宝马和荣威就是子类。仅仅有底盘和骨架还不是一辆真正意义上的车，只能算是半成品，因此在底盘和骨架上会留出一些安装接口，比如安装发动机的接口、安装座椅的接口，这些就是抽象方法。宝马或者荣威上安装的发动机和座椅是不一样的，也就是具体子类对抽象方法有不同的实现。

回到 Lifecycle 接口，Tomcat 定义一个基类 LifecycleBase 来实现 Lifecycle 接口，把一些公共的逻辑放到基类中去，比如生命状态的转变与维护、生命事件的触发以及监听器的添加和删除等，而子类就负责实现自己的初始化、启动和停止等方法。为了避免跟基类中的方法同名，我们把具体子类的实现方法改个名字，在后面加上 Internal，叫 initInternal、startInternal 等。我们再来看引入了基类 LifecycleBase 后的类图：

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201150031.png)

从图上可以看到，LifecycleBase 实现了 Lifecycle 接口中所有的方法，还定义了相应的抽象方法交给具体子类去实现，这是典型的模板设计模式。

我们还是看一看代码，可以帮你加深理解，下面是 LifecycleBase 的 init 方法实现。

```
@Override
public final synchronized void init() throws LifecycleException {
    //1. 状态检查
    if (!state.equals(LifecycleState.NEW)) {
        invalidTransition(Lifecycle.BEFORE_INIT_EVENT);
    }

    try {
        //2.触发INITIALIZING事件的监听器
        setStateInternal(LifecycleState.INITIALIZING, null, false);
        
        //3.调用具体子类的初始化方法
        initInternal();
        
        //4. 触发INITIALIZED事件的监听器
        setStateInternal(LifecycleState.INITIALIZED, null, false);
    } catch (Throwable t) {
      ...
    }
}
```

这个方法逻辑比较清楚，主要完成了四步：

第一步，检查状态的合法性，比如当前状态必须是 NEW 然后才能进行初始化。

第二步，触发 INITIALIZING 事件的监听器：

setStateInternal(LifecycleState.INITIALIZING, null, false);

在这个 setStateInternal 方法里，会调用监听器的业务方法。

第三步，调用具体子类实现的抽象方法 initInternal 方法。我在前面提到过，为了实现一键式启动，具体组件在实现 initInternal 方法时，又会调用它的子组件的 init 方法。

第四步，子组件初始化后，触发 INITIALIZED 事件的监听器，相应监听器的业务方法就会被调用。

setStateInternal(LifecycleState.INITIALIZED, null, false);

总之，LifecycleBase 调用了抽象方法来实现骨架逻辑。讲到这里， 你可能好奇，LifecycleBase 负责触发事件，并调用监听器的方法，那是什么时候、谁把监听器注册进来的呢？

分为两种情况：

- Tomcat 自定义了一些监听器，这些监听器是父组件在创建子组件的过程中注册到子组件的。比如 MemoryLeakTrackingListener 监听器，用来检测 Context 容器中的内存泄漏，这个监听器是 Host 容器在创建 Context 容器时注册到 Context 中的。
- 我们还可以在server.xml中定义自己的监听器，Tomcat 在启动时会解析server.xml，创建监听器并注册到容器组件。

### 生周期管理总体类图

通过上面的学习，我相信你对 Tomcat 组件的生命周期的管理有了深入的理解，我们再来看一张总体类图继续加深印象。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201201150300.png)

这里请你注意，图中的 StandardServer、StandardService 等是 Server 和 Service 组件的具体实现类，它们都继承了 LifecycleBase。

StandardEngine、StandardHost、StandardContext 和 StandardWrapper 是相应容器组件的具体实现类，因为它们都是容器，所以继承了 ContainerBase 抽象基类，而 ContainerBase 实现了 Container 接口，也继承了 LifecycleBase 类，它们的生命周期管理接口和功能接口是分开的，这也符合设计中接口分离的原则。

### 精华

Tomcat 为了实现一键式启停以及优雅的生命周期管理，并考虑到了可扩展性和可重用性，将面向对象思想和设计模式发挥到了极致，分别运用了组合模式、观察者模式、骨架抽象类和模板方法。

如果你需要维护一堆具有父子关系的实体，可以考虑使用组合模式。

观察者模式听起来“高大上”，其实就是当一个事件发生后，需要执行一连串更新操作。传统的实现方式是在事件响应代码里直接加更新逻辑，当更新逻辑加多了之后，代码会变得臃肿，并且这种方式是紧耦合的、侵入式的。而观察者模式实现了低耦合、非侵入式的通知与更新机制。

而模板方法在抽象基类中经常用到，用来实现通用逻辑。

## 08 | Tomcat的“高层们”都负责做什么？











# 参考

1. [tomcat源码](https://github.com/apache/tomcat)
2. 

















