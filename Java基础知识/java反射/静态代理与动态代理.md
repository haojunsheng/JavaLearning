[TOC]

# 1.**代理概念**
为某个对象提供一个代理，以控制对这个对象的访问。 

为了提供额外的或不同的操作，而插入的用来代替实际对象的对象。

为什么要采用这种间接的形式来调用对象呢？一般是因为客户端**不想直接访问实际的对象，或者访问实际的对象存在困难**，因此通过一个代理对象来完成间接的访问。
在现实生活中，这种情形非常的常见，比如请一个律师代理来打官司。

代理类和委托类有共同的父类或父接口，这样在任何使用委托类对象的地方都可以用代理对象替代。代理类负责请求的预处理、过滤、将请求分派给委托类处理、以及委托类执行完请求后的后续处理。
![](https://ws3.sinaimg.cn/large/006tKfTcly1g0ipuyh4z2j30j60ayjsl.jpg)
从图中可以看出，代理接口（Subject）、代理类（ProxySubject）、委托类（RealSubject）形成一个“品”字结构。
根据代理类的生成时间不同可以将代理分为**静态代理和动态代理**两种。

下面以一个[模拟](https://www.baidu.com/s?wd=%E6%A8%A1%E6%8B%9F&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)需求说明静态代理和动态代理：委托类要处理一项耗时较长的任务，客户类需要打印出执行任务消耗的时间。解决这个问题需要记录任务执行前时间和任务执行后时间，两个时间差就是任务执行消耗的时间。

代理模式一般涉及到的角色有：
抽象角色：声明真实对象和代理对象的共同接口，对应代理接口（Subject）；
真实角色：代理角色所代表的真实对象，是我们最终要引用的对象，对应委托类（RealSubject）；
代理角色：代理对象角色内部含有对真实对象的引用，从而可以操作真实对象，同时代理对象提供与真实对象相同的接口以便在任何时刻都能代替真实对象。同时，代理对象可以在执行真实对象操作时，附加其他的操作，相当于对真实对象进行封装，对应代理类（ProxySubject）

# 2. 静态代理
由程序员创建或工具生成代理类的源码，再编译代理类。所谓静态也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。
***清单1：代理接口***

1. /**  
2.  \* 代理接口。处理给定名字的任务。 
3.  */  
4. **public** **interface** Subject {  
5.   /** 
6.    \* 执行给定名字的任务。 
7. ​    \* @param taskName 任务名 
8.    */  
9.    **public** **void** dealTask(String taskName);   
10. }  

***清单2：委托类，具体处理业务。***

1. /** 
2.  \* 真正执行任务的类，实现了代理接口。 
3.  */  
4. **public** **class** RealSubject **implements** Subject {  
5.   
6.  /** 
7.   \* 执行给定名字的任务。这里打印出任务名，并休眠500ms模拟任务执行了很长时间 
8.   \* @param taskName  
9.   */  
10.    @Override  
11.    **public** **void** dealTask(String taskName) {  
12. ​      System.out.println("正在执行任务："+taskName);  
13. ​      **try** {  
14. ​         Thread.sleep(500);  
15. ​      } **catch** (InterruptedException e) {  
16. ​         e.printStackTrace();  
17. ​      }  
18.    }  
19. }  

***清单3：静态代理类***

1. /** 
2.  *　代理类，实现了代理接口。 
3.  */  
4. **public** **class** ProxySubject **implements** Subject {  
5.  //代理类持有一个委托类的对象引用  
6.  **private** Subject delegate;  
7.    
8.  **public** ProxySubject(Subject delegate) {  
9.   **this**.delegate = delegate;  
10.  }  
11.   
12.  /** 
13.   \* 将请求分派给委托类执行，记录任务执行前后的时间，时间差即为任务的处理时间 
14.   \*  
15.   \* @param taskName 
16.   */  
17.  @Override  
18.  **public** **void** dealTask(String taskName) {  
19.   **long** stime = System.currentTimeMillis();   
20.   //将请求分派给委托类处理  
21.   delegate.dealTask(taskName);  
22.   **long** ftime = System.currentTimeMillis();   
23.   System.out.println("执行任务耗时"+(ftime - stime)+"毫秒");  
24. ​    
25.  }  
26. }  

***清单4：生成静态代理类工厂***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. **public** **class** SubjectStaticFactory {  
2.  //客户类调用此工厂方法获得代理对象。  
3.  //对客户类来说，其并不知道返回的是代理类对象还是委托类对象。  
4.  **public** **static** Subject getInstance(){   
5.   **return** **new** ProxySubject(**new** RealSubject());  
6.  }  
7. }  

***清单5：客户类***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. **public** **class** Client1 {  
2.   
3.  **public** **static** **void** main(String[] args) {  
4.   Subject proxy = SubjectStaticFactory.getInstance();  
5.   proxy.dealTask("DBQueryTask");  
6.  }   
7.   
8. }  

**静态代理类优缺点**
优点：业务类只需要关注业务逻辑本身，保证了业务类的重用性。这是代理的共有优点。
缺点：
1）代理对象的一个接口只服务于一种类型的对象，如果要代理的方法很多，势必要为每一种方法都进行代理，静态代理在程序规模稍大时就无法胜任了。



2）如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。

另外，如果要按照上述的方法使用代理模式，那么真实角色(委托类)必须是事先已经存在的，并将其作为代理对象的内部属性。但是实际使用时，一个真实角色必须对应一个代理角色，如果大量使用会导致类的急剧膨胀；此外，如果事先并不知道真实角色（委托类），该如何使用代理呢？这个问题可以通过Java的动态代理类来解决。

# 3. 动态代理
动态代理类的源码是在程序运行期间由JVM根据反射等机制动态的生成，所以不存在代理类的字节码文件。代理类和委托类的关系是在程序运行时确定。
1、先看看与动态代理紧密关联的Java API。
1）java.lang.reflect.Proxy
这是 Java 动态代理机制生成的所有动态代理类的父类，它提供了一组静态方法来为一组接口动态地生成代理类及其对象。
***清单6：Proxy类的静态方法***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. // 方法 1: 该方法用于获取指定代理对象所关联的调用处理器  
2. **static** InvocationHandler getInvocationHandler(Object proxy)   
3.   
4. // 方法 2：该方法用于获取关联于指定类装载器和一组接口的动态代理类的类对象  
5. **static** Class getProxyClass(ClassLoader loader, Class[] interfaces)   
6.   
7. // 方法 3：该方法用于判断指定类对象是否是一个动态代理类  
8. **static** **boolean** isProxyClass(Class cl)   
9.   
10. // 方法 4：该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例  
11. **static** Object newProxyInstance(ClassLoader loader, Class[] interfaces, InvocationHandler h)  

2）java.lang.reflect.InvocationHandler
这是调用处理器接口，它自定义了一个 invoke 方法，用于集中处理在动态代理类对象上的方法调用，通常在该方法中实现对委托类的代理访问。每次生成动态代理类对象时都要指定一个对应的调用处理器对象。
***清单7：InvocationHandler的核心方法***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. // 该方法负责集中处理动态代理类上的所有方法调用。第一个参数既是代理类实例，第二个参数是被调用的方法对象  
2. // 第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上反射执行  
3. Object invoke(Object proxy, Method method, Object[] args)  

3）java.lang.ClassLoader
这是类装载器类，负责将类的字节码装载到 Java 虚拟机（JVM）中并为其定义类对象，然后该类才能被使用。Proxy 静态方法生成动态代理类同样需要通过类装载器来进行装载才能使用，它与普通类的唯一区别就是其字节码是由 JVM 在运行时动态生成的而非预存在于任何一个 .class 文件中。
每次生成动态代理类对象时都需要指定一个类装载器对象
**2、动态代理实现步骤**
具体步骤是：
a. 实现InvocationHandler接口创建自己的调用处理器
b. 给Proxy类提供ClassLoader和代理接口类型数组创建动态代理类
c. 以调用处理器类型为参数，利用反射机制得到动态代理类的构造函数
d. 以调用处理器对象为参数，利用动态代理类的构造函数创建动态代理类对象
***清单8：分步骤实现动态代理***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. // InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发  
2. // 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用  
3. InvocationHandler handler = **new** InvocationHandlerImpl(..);   
4.   
5. // 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象  
6. Class clazz = Proxy.getProxyClass(classLoader, **new** Class[] { Interface.**class**, ... });   
7.   
8. // 通过反射从生成的类对象获得构造函数对象  
9. Constructor constructor = clazz.getConstructor(**new** Class[] { InvocationHandler.**class** });   
10.   
11. // 通过构造函数对象创建动态代理类实例  
12. Interface Proxy = (Interface)constructor.newInstance(**new** Object[] { handler });   

Proxy类的静态方法newProxyInstance对上面具体步骤的后三步做了封装，简化了动态代理对象的获取过程。
***清单9：简化后的动态代理实现***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. // InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发  
2. InvocationHandler handler = **new** InvocationHandlerImpl(..);   
3.   
4. // 通过 Proxy 直接创建动态代理类实例  
5. Interface proxy = (Interface)Proxy.newProxyInstance( classLoader,   
6. ​     **new** Class[] { Interface.**class** },  handler );   

**3、动态代理实现示例**
***清单10：创建自己的调用处理器***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. /** 
2.  \* 动态代理类对应的调用处理程序类 
3.  */  
4. **public** **class** SubjectInvocationHandler **implements** InvocationHandler {  
5.    
6.  //代理类持有一个委托类的对象引用  
7.  **private** Object delegate;  
8.    
9.  **public** SubjectInvocationHandler(Object delegate) {  
10.   **this**.delegate = delegate;  
11.  }  
12.    
13.  @Override  
14.  **public** Object invoke(Object proxy, Method method, Object[] args) **throws** Throwable {  
15.   **long** stime = System.currentTimeMillis();   
16.   //利用反射机制将请求分派给委托类处理。Method的invoke返回Object对象作为方法执行结果。  
17.   //因为示例程序没有返回值，所以这里忽略了返回值处理  
18.   method.invoke(delegate, args);  
19.   **long** ftime = System.currentTimeMillis();   
20.   System.out.println("执行任务耗时"+(ftime - stime)+"毫秒");  
21. ​    
22.   **return** **null**;  
23.  }  
24. }   

***清单11：生成动态代理对象的工厂，工厂方法列出了如何生成动态代理类对象的步骤。***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. /** 
2.  \* 生成动态代理对象的工厂. 
3.  */  
4. **public** **class** DynProxyFactory {  
5.  //客户类调用此工厂方法获得代理对象。  
6.  //对客户类来说，其并不知道返回的是代理类对象还是委托类对象。  
7.  **public** **static** Subject getInstance(){   
8.   Subject delegate = **new** RealSubject();  
9.   InvocationHandler handler = **new** SubjectInvocationHandler(delegate);  
10.   Subject proxy = **null**;  
11.   proxy = (Subject)Proxy.newProxyInstance(  
12. ​    delegate.getClass().getClassLoader(),   
13. ​    delegate.getClass().getInterfaces(),   
14. ​    handler);  
15.   **return** proxy;  
16.  }  
17. }  

***清单12：动态代理客户类***



Java代码  ![收藏代码](http://layznet.iteye.com/images/icon_star.png)

1. **public** **class** Client {  
2.   
3.  **public** **static** **void** main(String[] args) {  
4.   
5.   Subject proxy = DynProxyFactory.getInstance();  
6.   proxy.dealTask("DBQueryTask");  
7.  }   
8.   
9. }  

# **4、动态代理机制特点**  
首先是动态生成的代理类本身的一些特点。1）包：如果所代理的接口都是 public 的，那么它将被定义在顶层包（即包路径为空），如果所代理的接口中有非 public 的接口（因为接口不能被定义为 protect 或 private，所以除 public 之外就是默认的 package 访问级别），那么它将被定义在该接口所在包（假设代理了 com.ibm.developerworks 包中的某非 public 接口 A，那么新生成的代理类所在的包就是 com.ibm.developerworks），这样设计的目的是为了最大程度的保证动态代理类不会因为包管理的问题而无法被成功定义并访问；2）类修饰符：该代理类具有 final 和 public 修饰符，意味着它可以被所有的类访问，但是不能被再度继承；3）类名：格式是“$ProxyN”，其中 N 是一个逐一递增的阿拉伯数字，代表 Proxy 类第 N 次生成的动态代理类，值得注意的一点是，并不是每次调用 Proxy 的静态方法创建动态代理类都会使得 N 值增加，原因是如果对同一组接口（包括接口排列的顺序相同）试图重复创建动态代理类，它会很聪明地返回先前已经创建好的代理类的类对象，而不会再尝试去创建一个全新的代理类，这样可以节省不必要的代码重复生成，提高了代理类的创建效率。4）类继承关系：该类的继承关系如图：

图2：动态代理类的继承关系
![img](http://dl.iteye.com/upload/attachment/562228/775caa62-37c7-35b2-9155-28534991a63d.jpg)
由图可见，Proxy 类是它的父类，这个规则适用于所有由 Proxy 创建的动态代理类。而且该类还实现了其所代理的一组接口，这就是为什么它能够被安全地类型转换到其所代理的某接口的根本原因。
接下来让我们了解一下代理类实例的一些特点。每个实例都会关联一个调用处理器对象，可以通过 Proxy 提供的静态方法 getInvocationHandler 去获得代理类实例的调用处理器对象。在代理类实例上调用其代理的接口中所声明的方法时，这些方法最终都会由调用处理器的 invoke 方法执行，此外，值得注意的是，代理类的根类 java.lang.Object 中有三个方法也同样会被分派到调用处理器的 invoke 方法执行，它们是 hashCode，equals 和 toString，可能的原因有：一是因为这些方法为 public 且非 final 类型，能够被代理类覆盖；二是因为这些方法往往呈现出一个类的某种特征属性，具有一定的区分度，所以为了保证代理类与委托类对外的一致性，这三个方法也应该被分派到委托类执行。当代理的一组接口有重复声明的方法且该方法被调用时，代理类总是从排在最前面的接口中获取方法对象并分派给调用处理器，而无论代理类实例是否正在以该接口（或继承于该接口的某子接口）的形式被外部引用，因为在代理类内部无法区分其当前的被引用类型。
接着来了解一下被代理的一组接口有哪些特点。首先，要注意不能有重复的接口，以避免动态代理类代码生成时的编译错误。其次，这些接口对于类装载器必须可见，否则类装载器将无法链接它们，将会导致类定义失败。再次，需被代理的所有非 public 的接口必须在同一个包中，否则代理类生成也会失败。最后，接口的数目不能超过 65535，这是 JVM 设定的限制。
最后再来了解一下异常处理方面的特点。从调用处理器接口声明的方法中可以看到理论上它能够抛出任何类型的异常，因为所有的异常都继承于 Throwable 接口，但事实是否如此呢？答案是否定的，原因是我们必须遵守一个继承原则：即子类覆盖父类或实现父接口的方法时，抛出的异常必须在原方法支持的异常列表之内。所以虽然调用处理器理论上讲能够，但实际上往往受限制，除非父接口中的方法支持抛 Throwable 异常。那么如果在 invoke 方法中的确产生了接口方法声明中不支持的异常，那将如何呢？放心，Java 动态代理类已经为我们设计好了解决方法：它将会抛出 UndeclaredThrowableException 异常。这个异常是一个 RuntimeException 类型，所以不会引起编译错误。通过该异常的 getCause 方法，还可以获得原来那个不受支持的异常对象，以便于错误诊断。

# 5、动态代理的优点和美中不足
优点：
动态代理与静态代理相比较，最大的好处是接口中声明的所有方法都被转移到调用处理器一个集中的方法中处理（InvocationHandler.invoke）。这样，在接口方法数量比较多的时候，我们可以进行灵活处理，而不需要像静态代理那样每一个方法进行中转。在本示例中看不出来，因为invoke方法体内嵌入了具体的外围业务（记录任务处理前后时间并计算时间差），实际中可以类似Spring AOP那样配置外围业务。
美中不足：
诚然，Proxy 已经设计得非常优美，但是还是有一点点小小的遗憾之处，那就是它始终无法摆脱仅支持 interface 代理的桎梏，因为它的设计注定了这个遗憾。回想一下那些动态生成的代理类的继承关系图，它们已经注定有一个共同的父类叫 Proxy。Java 的继承机制注定了这些动态代理类们无法实现对 class 的动态代理，原因是多继承在 Java 中本质上就行不通。



有很多条理由，人们可以否定对 class 代理的必要性，但是同样有一些理由，相信支持 class 动态代理会更美好。接口和类的划分，本就不是很明显，只是到了 Java 中才变得如此的细化。如果只从方法的声明及是否被定义来考量，有一种两者的混合体，它的名字叫抽象类。实现对抽象类的动态代理，相信也有其内在的价值。此外，还有一些历史遗留的类，它们将因为没有实现任何接口而从此与动态代理永世无缘。如此种种，不得不说是一个小小的遗憾。