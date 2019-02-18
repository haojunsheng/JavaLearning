Servlet不是线程安全的。

要解释为什么Servlet为什么不是线程安全的，需要了解Servlet容器（即Tomcat）使如何响应HTTP请求的。

当Tomcat接收到Client的HTTP请求时，Tomcat从线程池中取出一个线程，之后找到该请求对应的Servlet对象并进行初始化，之后调用service()方法。要注意的是每一个Servlet对象再Tomcat容器中只有一个实例对象，即是单例模式。如果多个HTTP请求请求的是同一个Servlet，那么着两个HTTP请求对应的线程将并发调用Servlet的service()方法。

![](https://ws4.sinaimg.cn/large/006tKfTcly1g0ahd5zwnsj30vo0fvdh2.jpg)

上图中的Thread1和Thread2调用了同一个Servlet1，所以**此时如果Servlet1中定义了实例变量或静态变量，那么可能会发生线程安全问题**（因为所有的线程都可能使用这些变量）。

 i. 如果service()方法没有访问Servlet的成员变量也没有访问全局的资源比如静态变量、文件、数据库连接等，而是只使用了当前线程自己的资源，比如非指向全局资源的临时变量、request和response对象等。该方法本身就是线程安全的，不必进行任何的同步控制。

​      ii. 如果service()方法访问了Servlet的成员变量，但是对该变量的操作是只读操作，该方法本身就是线程安全的，不必进行任何的同步控制。

​      iii. 如果service()方法访问了Servlet的成员变量，并且对该变量的操作既有读又有写，通常需要加上同步控制语句。

​      iv. 如果service()方法访问了全局的静态变量，如果同一时刻系统中也可能有其它线程访问该静态变量，如果既有读也有写的操作，通常需要加上同步控制语句。

​      v. 如果service()方法访问了全局的资源，比如文件、数据库连接等，通常需要加上同步控制语句。