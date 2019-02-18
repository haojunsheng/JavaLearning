Servlet运行原理 

![](https://ws1.sinaimg.cn/large/006tKfTcly1g0agen8tsrj30ai0640sw.jpg)



生命周期

![](https://ws1.sinaimg.cn/large/006tKfTcly1g0agg5902yj30dw05sglx.jpg)

- **加载和实例化Servlet**

　　Servlet容器负责加载和实例化Servlet。当Servlet容器启动时，或者在容器检测到需要这个Servlet来响应第一个请求时，创建Servlet实例。当Servlet容器启动后，它必须要知道所需的Servlet类在什么位置，Servlet容器可以从本地文件系统、远程文件系统或者其他的网络服务中通过类加载器加载Servlet类，成功加载后，容器创建Servlet的实例。因为容器是通过Java的反射API来创建Servlet实例，调用的是Servlet的默认构造方法（即不带参数的构造方法），所以我们在编写Servlet类的时候，不应该提供带参数的构造方法。

- **初始化**

在Servlet实例化之后，容器将调用Servlet的init()方法初始化这个对象。初始化的目的是为了让Servlet对象在处理客户端请求前完成一些初始化的工作，如建立数据库的连接，获取配置信息等。对于每一个Servlet实例，init()方法只被调用一次。在初始化期间，Servlet实例可以使用容器为它准备的ServletConfig对象从Web应用程序的配置信息（在web.xml中配置）中获取初始化的参数信息。在初始化期间，如果发生错误，Servlet实例可以抛出ServletException异常或者UnavailableException异常来通知容器。ServletException异常用于指明一般的初始化失败，例如没有找到初始化参数；而UnavailableException异常用于通知容器该Servlet实例不可用。例如，数据库服务器没有启动，数据库连接无法建立，Servlet就可以抛出UnavailableException异常向容器指出它暂时或永久不可用。

注意：当Server Thread线程执行Servlet实例的init()方法时，所有的Client Service Thread线程都不能执行该实例的service()方法，更没有线程能够执行该实例的destroy()方法，因此Servlet的init()方法是工作在单线程的环境下，开发者不必考虑任何线程安全的问题。



- **请求处理**

Servlet容器调用Servlet的service()方法对请求进行处理。要注意的是，在service()方法调用之前，init()方法必须成功执行。在service()方法中，

Servlet实例通过ServletRequest对象得到客户端的相关信息和请求信息，在对请求进行处理后，调用ServletResponse对象的方法设置响应信息。在service

()方法执行期间，如果发生错误，Servlet实例可以抛出ServletException异常或者UnavailableException异常。如果UnavailableException异常指示了该实

例永久不可用，Servlet容器将调用实例的destroy()方法，释放该实例。此后对该实例的任何请求，都将收到容器发送的HTTP 404（请求的资源不可用）响应

。如果UnavailableException异常指示了该实例暂时不可用，那么在暂时不可用的时间段内，对该实例的任何请求，都将收到容器发送的HTTP 503（服务器暂

时忙，不能处理请求）响应。

**I. service()方法的职责**

​     service()方法为Servlet的核心方法，客户端的业务逻辑应该在该方法内执行，典型的服务方法的开发流程为：

​    解析客户端请求-〉执行业务逻辑-〉输出响应页面到客户端

**II.service()方法与线程**

​     为了提高效率，Servlet规范要求一个Servlet实例必须能够同时服务于多个客户端请求，即service()方法运行在多线程的环境下，Servlet开发者必须保证该方法的线程安全性。

**III.service()方法与异常**

​     service()方法在执行的过程中可以抛出ServletException和IOException。其中ServletException可以在处理客户端请求的过程中抛出，比如请求的资源不可用、[数据库](http://lib.csdn.net/base/mysql)不可用等。一旦该异常抛出，容器必须回收请求对象，并报告客户端该异常信息。IOException表示输入输出的错误，编程者不必关心该异常，直接由容器报告给客户端即可。



注意:当服务器接收到来自客户端的多个请求时，服务器会在单独的Client Service Thread线程中执行Servlet实例的service()方法服务于每个客户端。此时会有多个线程同时执行同一个Servlet实例的service()方法，因此必须考虑线程安全的问题。

- **服务终止**

当容器检测到一个Servlet实例应该从服务中被移除的时候，容器就会调用实例的destroy()方法，以便让该实例可以释放它所使用的资源，保存数据到持久存

储设备中。当需要释放内存或者容器关闭时，容器就会调用Servlet实例的destroy()方法。在destroy()方法调用之后，容器会释放这个Servlet实例，该实例

随后会被Java的垃圾收集器所回收。如果再次需要这个Servlet处理请求，Servlet容器会创建一个新的Servlet实例。

　　在整个Servlet的生命周期过程中，创建Servlet实例、**调用实例的init()和destroy()方法都只进行一次**，当初始化完成后，Servlet容器会将该实例保存在内存中，通过调用它的service()方法，为接收到的请求服务。

























