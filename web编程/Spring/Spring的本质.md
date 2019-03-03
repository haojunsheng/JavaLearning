首先，一个java bean 其实就是一个普通的java 类， 但我们对这个类有些要求： 

1. 这个类需要是public 的， 然后需要有个无参数的构造函数
2. 这个类的属性应该是private 的， 通过setXXX()和getXXX()来访问
3.  这个类需要能支持“事件”， 例如addXXXXListener(XXXEvent e),  事件可以是Click事件，Keyboard事件等等， 当然咱们也支持自定义的事件。 
4. 我们得提供一个所谓的自省/反射机制， 这样能在运行时查看java bean 的各种信息
5. 这个类应该是可以序列化的， 即可以把bean的状态保存的硬盘上， 以便以后来恢复。 



JSP + Servlet+Java Bean

用java bean 来封装业务逻辑，保存数据到数据库， 像这样：

![image-20190301192257058](https://ws4.sinaimg.cn/large/006tKfTcly1g0nikejfzyj31360fogqb.jpg)





# 1. 对象的创建

面向对象的编程语言是用类(Class)来对现实世界进行抽象， 在运行时这些类会生成对象(Object)。 

当然，单独的一个或几个对象根本没办法完成复杂的业务， 实际的系统是由千千万万个对象组成的， 这些对象需要互相协作才能干活，例如对象A调用对象B的方法，那必然会提出一个问题：对象A怎么才能获得对象B的引用呢？

最简单的办法无非是： 当对象A需要使用对象B的时候， **把它给new 出来** ，这也是最常用的办法， java 不就是这么做的？例如：  

Apple a = new Apple();

后来业务变复杂了， 抽象出了一个水果(Fruit)的类， 创建对象会变成这个样子：

Fruit f1 = new Apple();

Fruit f2 = new Banana();

Fruit f3 = ......

很自然的，类似下面的代码就会出现：

![image-20190302100839518](https://ws1.sinaimg.cn/large/006tKfTcly1g0o85zdhj2j312i0ou7gi.jpg)

这样的代码如果散落在各处，维护起来将会痛苦不堪， 例如你新加一个水果的类型Orange, 那得找到系统中所有的这些创建Fruit的地方，进行修改， 这绝对是一场噩梦。

 

解决办法也很简单， 前辈们早就总结好了：工厂模式 

![image-20190302100858973](https://ws3.sinaimg.cn/large/006tKfTcly1g0o86bcqbhj30s60h4tfo.jpg)

工厂模式，以及其他模式像抽象工厂， Builder模式提供的都是创建对象的方法。

这背后体现的都是“**封装变化**”的思想。

这些模式只是一些最佳实践而已： 起了一个名称、描述一下解决的问题、使用的范围和场景，码农们在项目中还得自己去编码实现他们。



# 2. 解除依赖

我们再来看一个稍微复杂一点， 更加贴近实际项目的例子：



一个订单处理类，它会被定时调用：  查询数据库中订单的处理情况， 必要时给下订单的用户发信。

![image-20190302100927547](https://ws1.sinaimg.cn/large/006tKfTcly1g0o86tamcqj30v40u01dv.jpg)

看起来也没什么难度， 需要注意的是很多类一起协作了， 尤其是OrderProcessor , 它依赖于

OrderService 和 EmailService这两个服务，它获取依赖的方式就是通过**单例方法**。



如果你想对这个process方法进行单元测试--**这也是很多优秀的团队要求的**-- 麻烦就来了。 



首先OrderService 确实会从真正的数据库中取得Order信息，你需要确保数据库中有数据， 数据库连接没问题，实际上如果数据库连接Container（例如Tomcat）管理的， 你没有Tomcat很难建立数据库连接。



其次这个EmailService 真的会对外发邮件， 你可不想对真正的用户发测试邮件，当然你可以修改数据库，把邮件地址改成假的，但那样很麻烦， 并且EmailService 会抛出一堆错误来，很不爽。



所有的这些障碍， 最终会导致脆弱的单元测试： **速度慢， 不可重复，需要手工干预，不能独立运行。**



想克服这些障碍， 一个可行的办法就是不在方法中直接调用OrderService和EmailService的getInstance()方法， 而是把他们通过setter方法传进来。

![image-20190302100955028](https://ws3.sinaimg.cn/large/006tKfTcly1g0o87aezqoj313y0aaaja.jpg)

通过这种方式，你的单元测试就可以构造一个假的OrderService 和假的EmailService 了。



例如OrderService 的冒牌货可以是MockOrderService , 它可以返回你想要的任何Order 对象， 而不是从数据库取。



MockEmailService 也不会真的发邮件， 而是把代码中试图发的邮件保存下来， 测试程序可以检查是否正确。



你的测试代码可能是这样的：

![image-20190302101010342](https://ws1.sinaimg.cn/large/006tKfTcly1g0o87kf2hej30vi0hen5b.jpg)

当然， 有经验的你马上就会意识到： 需要把OrderService 和 EmailService 变成 接口或者抽象类， 这样才可以把Mock对象传进来。 



这其实也遵循了面向对象编程的另外一个要求： **对接口编程， 而不是对实现编程。**



# 3. Spring 依赖注入

啰啰嗦嗦说了这么多， 快要和Spring扯上关系了。

 

上面的代码其实就是实现了一个依赖的注入，把两个冒牌货注入到业务类中(通过set方法)， 这个注入的过程是在一个测试类中通过代码完成的。



既然能把冒牌货注入进去，  那毫无疑问，肯定也能把一个正经的类安插进去， 因为setter 方法接受的是接口，而不是具体类。

![image-20190302101036896](https://ws3.sinaimg.cn/large/006tKfTcly1g0o880wl8uj30q60aewje.jpg)



用这种方式来处理对象之间的依赖， 会强迫你对接口编程， 好处显而易见。 



随着系统复杂度的增长， 这样的代码会越来越多， 最后也会变得难于维护。 



能不能把各个类之间的依赖关系统一维护呢？

能不能把系统做的更加灵活一点，用声明的方式而不是用代码的方式来描述依赖关系呢？



肯定可以， 在Java 世界里，如果想描述各种逻辑关系， XML是不二之选：

![image-20190302101054049](https://ws4.sinaimg.cn/large/006tKfTcly1g0o88b9kruj311q0e0wr1.jpg)



 [JavaBean的来龙去脉.md](../../Java基础知识/JavaBean的来龙去脉.md) 

这个xml 挺容易理解的， 但是仅仅有它还不够， 还缺一个解析器（假设叫做XmlAppContext）来解析，处理这个文件，基本过程是：

\0. 解析xml, 获取各种元素

\1. 通过**Java反射**把各个bean 的实例创建起来： com.coderising.OrderProcessor   , OrderServiceImpl, EmailServiceImpl. 



\2. 还是通过**Java反射**调用OrderProcessor的两个方法：setOrderService(....)  和 setEmailService(...) 把orderService , emailService 实例 注入进去。



应用程序使用起来就简单了：



XmlAppContext ctx = new XmlAppContext("c:\\bean.xml");



OrderProcessor op = (OrderProcessor) ctx.getBean("order-processor");



op.process();



其实Spring的处理方式和上面说的非常类似， 当然Spring 处理了更多的细节，例如不仅仅是setter方法注入， 还可以构造函数注入，init 方法， destroy方法等等， 基本思想是一致的。

![image-20190302101146341](https://ws3.sinaimg.cn/large/006tKfTcly1g0o897trfaj30xq0s40zm.jpg)

**既然对象的创建过程和装配过程都是Spring做的， 那Spring 在这个过程中就可以玩很多把戏了， 比如对你的业务类做点字节码级别的增强， 搞点AOP什么的， 这都不在话下了。** 



# 4. IoC vs DI

“不要给我们打电话，我们会打给你的(don‘t call us, we‘ll call you)”这是著名的好莱坞原则。



在好莱坞，把简历递交给演艺公司后就只有回家等待。由演艺公司对整个娱乐项目完全控制，演员只能被动式的接受公司的差使,在需要的环节中，完成自己的演出。



这和软件开发有一定的相似性， 演员们就像一个个Java Object, 最早的时候自己去创建自己所依赖的对象，   有了演艺公司（Spring容器）的介入，所有的依赖关系都是演艺公司搞定的， 于是控制就翻转了 

Inversion of Control, 简称IoC。 



但是IoC这个词不能让人更加直观和清晰的理解背后所代表的含义， 于是Martin Flower先生就创造了一个新词 : 依赖注入 (Dependency Injection，简称DI),  是不是更加贴切一点？



# 5. AOP

## 5.1 问题来源

我们在做系统设计的时候，一个非常重要的工作就是把一个大系统做分解， 按业务功能分解成一个个低耦合、高内聚的模块，就像这样：

![image-20190302101830684](https://ws3.sinaimg.cn/large/006tKfTcly1g0o8g8sp9ej30j00ekwfw.jpg)

但是分解以后就会发现有些很有趣的东西， 这些东西是通用的，或者是跨越多个模块的：

**日志**： 对特定的操作输出日志来记录

**安全**：在执行操作之前进行操作检查

**性能**：要统计每个方法的执行时间

**事务**：方法开始之前要开始事务， 结束后要提交或者回滚事务

等等....



这些可以称为是非功能需求， 但他们是多个业务模块都需要的， 是跨越模块的， 把他们放到什么地方呢？



最简单的办法就是把这些通用模块的接口写好， 让程序员在实现业务模块的时候去调用就可以了，码农嘛，辛苦一下也没什么。

![image-20190302101859474](https://ws4.sinaimg.cn/large/006tKfTcly1g0o8gseu5jj30hg0ka0wj.jpg)

这样做看起来没问题， 只是会产生类似这样的代码：

![image-20190302101918017](https://ws4.sinaimg.cn/large/006tKfTcly1g0o8h26midj30t80t04eh.jpg)

这样的代码也实现了功能，但是看起来非常的不爽， 那就是日志，性能，事务 相关的代码几乎要把真正的业务代码给淹没了。



不仅仅这一个类需要这么干， 其他类都得这么干， 重复代码会非常的多。



有经验的程序员还好， 新手忘记写这样的非业务代码简直是必然的。

## 5.2  设计模式：模板方法

用设计模式在某些情况下可以部分解决上面的问题，例如著名的**模板方法**：

![image-20190302101950211](https://ws3.sinaimg.cn/large/006tKfTcly1g0o8hlwfofj30rc18w7to.jpg)

在**父类（BaseCommand）中已经把那些“乱七八糟“的非功能代码都写好了， 只是留了一个口子（抽象方法doBusiness()）让子类去实现。**



子类变的清爽， 只需要关注业务逻辑就可以了。

调用也很简单，例如：

BaseCommand  cmd = ...  获得PlaceOrderCommand的实例...

cmd.execute();



但是这样方式的巨大缺陷就是父类会定义一切： **要执行哪些非功能代码， 以什么顺序执行等等**

**子类只能无条件接受，完全没有反抗余地。**



如果有个子类， 根本不需要事务， 但是它也没有办法把事务代码去掉。



## 5.3 设计模式：装饰者

如果利用装饰者模式， 针对上面的问题，可以带来更大的灵活性：

![image-20190302102049199](https://ws1.sinaimg.cn/large/006tKfTcly1g0o8in6x9ij30u010x1i2.jpg)

![image-20190302102109398](https://ws2.sinaimg.cn/large/006tKfTcly1g0o8iyzdcrj30q80dwgsz.jpg)

现在让这个PlaceOrderCommand 能够打印日志，进行性能统计

Command cmd = new **LoggerDecorator**(

​    new **PerformanceDecorator**(

​        new **PlaceOrderCommand**()));

cmd.execute();



如果PaymentCommand 只需要打印日志，装饰一次就可以了：

Command cmd = new **LoggerDecorator**(

​    new **PaymentCommand**());

cmd.execute();



可以使用任意数量装饰器，还可以以任意次序执行（严格意义上来说是不行的）， 是不是很灵活？ 

## 5.4 AOP

如果仔细思考一下就会发现装饰者模式的不爽之处:

(1)  一个处理日志/性能/事务 的类为什么要实现 业务接口（Command）呢?

(2) 如果别的业务模块，没有实现Command接口，但是也想利用日志/性能/事务等功能，该怎么办呢？



**最好把日志/安全/事务这样的代码和业务代码完全隔离开来，因为他们的关注点和业务代码的关注点完全不同** ，他们之间应该是正交的，他们之间的关系

应该是这样的：

![image-20190302102140530](https://ws4.sinaimg.cn/large/006tKfTcly1g0o8jincxtj30q80goq6x.jpg)

如果把这个业务功能看成一层层面包的话， 这些日志/安全/事务 像不像一个个“切面”(Aspect) ？



如果我们能让这些“切面“能和业务独立，  并且能够非常灵活的“织入”到业务方法中， 那就实现了面向切面编程(AOP)！



## 5.5 实现AOP

现在我们来实现AOP吧， 首先我们得有一个所谓的**“切面“类(Aspect)** ， 这应该是一个普通的java 类 ， 不用实现什么“乱七八糟”的接口。

以一个事务类为例：

![image-20190302102204436](https://ws4.sinaimg.cn/large/006tKfTcly1g0o8jxk017j30hu0bggq8.jpg)我们想达到的目的只这样的： 对于com.coderising这个包中所有类的execute方法， 在方法调用之前，需要执行Transaction.beginTx()方法， 在调用之后， 需要执行Transaction.commitTx()方法。



暂时停下脚步分析一下。



“对于com.coderising这个包中所有类的execute方法” ， 用一个时髦的词来描述就是**切入点（PointCut）** , 它可以是一个方法或一组方法（可以通过通配符来支持，你懂的）



”在方法调用之前/之后 ， 需要执行xxx“ , 用另外一个时髦的词来描述就是**通知（Advice）**



码农翻身认为，PointCut,Advice 这些词实在是不直观， 其实Spring的作者们也是这么想的 :  These terms are not Spring-specific… unfortunately, AOP terminology is not particularly intuitive; however, it would be even more confusing if Spring used its own terminology.



当然，想描述这些规则， xml依然是不二之选：

![image-20190302103855145](https://ws2.sinaimg.cn/large/006tKfTcly1g0o91iqytpj30vm0f8qc5.jpg)

注意：现在**Transaction这个类和业务类在源代码层次上没有一点关系，完全隔离了。**



隔离是一件好事情， 但是马上给我们带来了大麻烦 。

 

Java 是一门静态的强类型语言， 代码一旦写好， 编译成java class 以后 ，可以在运行时通过反射（Reflection）来查看类的信息， 但是想对类进行修改非常困难。 



而AOP要求的恰恰就是在不改变业务类的源代码（其实大部分情况下你也拿不到）的情况下， 修改业务类的方法, 进行功能的增强，就像上面给所有的业务类增加事务支持。

 [Java动态代理（码农翻身）.md](../../Java基础知识/java反射/Java动态代理（码农翻身）.md) 

为了突破这个限制，大家可以说是费尽心机， 现在基本是有这么几种技术：



(1) 在编译的时候， 根据AOP的配置信息，悄悄的把日志，安全，事务等“切面”代码 和业务类编译到一起去。



(2) 在运行期，业务类加载以后， 通过Java动态代理技术为业务类生产一个代理类， 把“切面”代码放到代理类中，  Java 动态代理要求业务类需要**实现接口**才行。



(3) 在运行期， 业务类加载以后， 动态的使用字节码构建一个业务类的子类，将“切面”逻辑加入到子类当中去, **CGLIB**就是这么做的。

Spring采用的就是(2) +(3) 的方式，限于篇幅，这里不再展开各种技术了， 不管使用哪一种方式， 在运行时，真正干活的“业务类”其实已经不是原来单纯的业务类了， 它们被AOP了 ！





# 6. 总结

在大型的项目中，我们不可能管理很多类的声明周期，为此，我们引入了IOC（也成DI）和AOP的概念，把对象的生命周期（创建，运行，销毁）交由统一的容器进行管理。

但是还是十分的复杂，对象之间的关系。我们可以使用xml来描述java bean [JavaBean的来龙去脉.md](../../Java基础知识/JavaBean的来龙去脉.md) 之间的关系。但是xml的描述还是十分的复杂（也不易阅读和修改），我们又引入了注解 [Java注解（码农翻身）.md](../../Java基础知识/java注解/Java注解（码农翻身）.md)  来帮助我们。

事实上，java注解的优点和缺点都是很明显的，优点是类的定义和注解是放在一起的，配置靠近代码，易于阅读和修改。但是，当文件复杂的时候，注解散落在各个java文件中，不易于进行统一的管理。当修改配置的时候，还得重新编译java源文件，十分的不方便。

所以，二者都有符合的特定的场景。对于一些需要**集中配置**的场合，例如数据源的配置， 自然是用XML。 另外一方面对于@Controller, @RequestMapping, @Transactional 这样的注解 ， 大家更喜欢和Java方法写在一起，显得简单而直观。







你们没看到我的名字中有个BySpring吗？ 我就是从Spring容器来的，容器负责我们的**生命周期**，管理我们的生老病死。在Sping容器中，我们这些Java对象被称为Bean。 对了，你知道吗？ 除了Spring之外，还有其他‘容器’，比如Tomcat, Jetty等可以叫做Servlet的容器，管理Servlet的一生， Weblogic，Websphere等就是EJB的容器，当然现在EJB不怎么用了。”  这家伙知道的东西倒是不少。



“你说的生命周期是什么意思？ Java 对象不就是new出来，然后使用，最后被垃圾回收吗？” 我问道。



“容器给我们规定了详细的生命周期，每个Spring的Bean都得严格执行生命周期的各个步骤。大致包括实例化，初始化，运行，销毁这么几个阶段。”  他说着给我们画了一张图。



![image-20190303142427791](https://ws2.sinaimg.cn/large/006tKfTcly1g0pl6fzkcgj30iy0oqjy3.jpg)



“你们这些容器管理的'Bean'这么复杂啊？ 我还以为new出来以后，就可以直接运行干活了。” 



“是啊， 其实生命周期在我们计算机软件界是很常见的事情，例如Servlet有生命周期，init->service->destroy。  React 组件也有生命周期 创建->更新->卸载,   Android的Activity也有生命周期，什么create, start, running, pause, stop ，destory等等， 大同小异。 这是非常**细腻**的管理，我们都很享受。”



我心里想，这些被容器管理的对象就是不一样，把短暂的一生分得这么细致。 



“可是，这么做的意义是什么？”  



“一个很重要的原因就是方便扩展啊，拿我们Spring容器来说，在生命周期的每个阶段开始和结束的时候，设置了一些钩子(Hook)函数，让大家随意扩展。”



![image-20190303142450326](https://ws4.sinaimg.cn/large/006tKfTcly1g0pl6u4o6rj310a0u0am5.jpg)

（注：为了更容易读，我修改了方法名，和Spring源码不一致）



“钩子函数？”  我从来没有听说过。



“对啊，看到那些beforeXXX, afterXXX了吗？ 这些都是钩子函数，如果你想在Bean的实例化或者初始化前后做点事情，就可以写一个类，当然得实现特定的接口(如InstantiationAwareBeanPostProcessor, BeanPostProcessor等)，把这个类告诉我们的Spring容器，Spring就会在特定的时刻去调用了。”



“有这个必要吗？” 



“怎么没有必要？看到图中那个processPropertyValues()方法了吗，对，就是红色那个，在这个方法中，就可以实现Spring的@Autowired注解，把一个Bean依赖的其他对象给注入进来。 再比如那个afterInitialization()方法， 在里边创建一个对象的代理出来。这个代理在调用真正对象的方法之前，可以执行一些事务，安全，日志等操作，嗯， 人类把这种行为称为AOP。”



“代理 ？ 怪不得你的名称和我们的不一样， 你的类名其实叫做Account$$BySpringCGLIB$$caa5f28f，你根本不是Account类，对不对？”  



我的这句话引起来大家的注意，其他Account对象纷纷围了过来。 要看看这个混入我们Accoun类家族的“奸细”。 



“嘿嘿，大家别紧张。 没错，我确实是从被增强了的一个新的Java Class创建的 ，不过我们也是一个家族啊，Account$$BySpringCGLIB$$caa5f28f 是运行时通过CGLib生成的， 是Account类的子类啊！ 你们Account类拥有的方法， 我也都有，感谢伟大的多态， 在客户端看来，我就是个普通的Accout对象，客户端根本意识不到Spring容器在背后所做的小动作。”



![image-20190303142521437](https://ws4.sinaimg.cn/large/006tKfTcly1g0pl7df7wej30xe0ouqbz.jpg)

```
//这里获得的account对象其实已经是被增强过了的类
//Account$$BySpringCGLIB$$caa5f28f
Account account = ctx.getBean("account");
//客户端还以为在调用原有的withdraw方法，但是由于多态的存在
//其实是增强类的withdraw方法，其中被添加了事务的功能。
account.withdraw();
```

（友情提示：可左右滑动）



“你这一招瞒天过海玩得可真溜啊。”  有个Account对象说道。 “你有这么强大的功能， 在我们这里不是抢我们的生意吗？ 赶紧走吧！”



“走？ 你让我去哪儿， 我也是堂堂正正的Java对象，也在Java Heap中生活！我还是个Singleton , 誓与容器一起共存亡！”



几个Account对象上来推搡他。



可是奇怪的事情发生了，那些个Account莫名其妙地消失了。



这个从Spring容器出来的Bean哈哈大笑：“瞧瞧，估计你们都是在一个for循环中生成的临时对象，生命短暂，很快被垃圾回收了！ ”























