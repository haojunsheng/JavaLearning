

# 前言

**java的类被装载后不能改变，所以只能动态的生成一个新类。**

写完了代码以后有这样的需求：

- 在某些函数调用前后加上日志记录

- 给某些函数加上事务的支持

- 给某些函数加上权限控制.



这些需求挺通用的，如果在每个函数中都实现一遍，那重复代码就太多了。 更要命的是有时候代码是别人写的，你只有class 文件，怎么修改？ 怎么加上这些功能？



所以“刁民”们就想了一个损招，他们想在XML文件或者什么地方声明一下， 比如对于添加日志的需求吧， **声明**的大意如下：



*对于com.coderising这个package下所有以add开头的方法，在执行之前都要调用Logger.startLog()方法， 在执行之后都要调用Logger.endLog()方法。*



对于增加事务支持的需求，**声明**的大意如下：



*对于所有以DAO结尾的类，所有的方法执行之前都要调用TransactionManager.begin()，执行之后都要调用TransactionManager.commit(), 如果抛出异常的话调用TransactionManager.rollback()。*



他们已经充分发挥了自己的那点儿小聪明，号称是开发了一个叫AOP的东西，**能够读取这个XML中的声明， 并且能够找到那些需要插入日志。**

# 使用接口来实现

虽然不能修改现有的类，**但是可以在运行时动态的创建新的类啊**，比如有个类HelloWorld:

![image-20190302130031778](https://ws1.sinaimg.cn/large/006tKfTcly1g0od4u0kahj30uk0cogu4.jpg)



“这么简单的类，怎么还得实现一个接口呢？ ” 国王问道



“臣想给这些刁民们增加一点点障碍， 你不是想让我动态地创建新的类吗？你必须得有接口才行啊”　IO大臣又得意又阴险地笑了。



国王脸上也露出了一丝不易觉察的微笑。



“现在他们的问题是要在sayHello()方法中调用Logger.startLog(), Logger.endLog()添加上日志， 但是这个sayHello()方法又不能修改了！”

![image-20190302130101158](https://ws3.sinaimg.cn/large/006tKfTcly1g0od5cgumxj30pm0em40z.jpg)



“所以臣想了想， 可以动态地生成一个**新类**，让这个类作为HelloWorld的**代理**去做事情（加上日志功能）， 陛下请看，这个HelloWorld代理也实现了IHelloWorld接口。 所以在调用方看来，都是IHelloWorld接口， 并不会意识到其实底层其实已经沧海沧田了。”

![image-20190302130113225](https://ws1.sinaimg.cn/large/006tKfTcly1g0od5j1guqj30nu0io787.jpg)

“朕能明白你这个绿色的HelloWorld代理，但是你这个类怎么可能知道把Logger的方法加到什么地方呢？”　国王一下子看出了关键。　



“陛下天资聪慧，臣拜服，‘刁民’们需要写一个类来告诉我们具体把Logger的代码加到什么地方， 这个类必须实现帝国定义的**InvocationHandler**接口，该接口中有个叫做**invoke**的方法就是他们写扩展代码的地方。  比如这个LoggerHandler： ”



![image-20190302130127744](https://ws3.sinaimg.cn/large/006tKfTcly1g0od5s06sbj30yy0oq4h3.jpg)

“ 看起来有些让朕不舒服，不过朕大概明白了， 无非就是在调用真正的方法之前先调用Logger.startLog(), 在调用之后在调用Logger.end()， 这就是对方法进行拦截了，对不对？”



“正是如此！ 其实这个LoggerHandler 充当了一个中间层， 我们自动化生成的类$HelloWorld100会调用它，把sayHello这样的方法调用传递给他 （上图中的method变量），于是sayHello()方法就被添加上了Logger的startLog()和endLog()方法”



![image-20190302130145301](https://ws1.sinaimg.cn/large/006tKfTcly1g0od63vo24j30rc0oc107.jpg)

“此外，臣想提醒陛下的是，这个Handler不仅仅能作用于IHelloWorld 这个接口和 HelloWorld这个类，陛下请看，那个target 是个Object, 这就意味着任何类的实例都可以， 当然我们会要求这些类必须得实现接口。  臣民们使用LoggerHandler的时候是这样的：”

![image-20190302130159314](https://ws2.sinaimg.cn/large/006tKfTcly1g0od6d1d61j30z80euk51.jpg)



输出：

Start Logging

Hello World

End Logging



“如果想对另外一个接口ICalculator和类Calcualtor做代理， 也可以复用这个LoggerHandler的类：”

![image-20190302130212890](https://ws3.sinaimg.cn/large/006tKfTcly1g0od6kr6z1j31020ek15w.jpg)



“折腾了变天，**原来魔法是在Proxy.newProxyInstance(....)  这里，就是动态地生成了一个类嘛， 这个类对臣民们来说是动态生成的， 也是看不到源码的。**”



“圣明无过陛下，我就是在运行时，在内存中生成了一个新的类，这个类在调用sayHello() 或者add()方法的时候， 其实调用的是LoggerHanlder的invoke 方法， 而那个invoke就会拦截真正的方法调用，添加日志功能了！ ”



“爱卿辛苦了，虽然有点绕，但是理解了还是挺简单的。 朕明天就颁发圣旨， 全国推行，对了你打算叫它什么名字？ ”



“既然是在运行时动态的生成类，并且作为一个真实对象的代理来做事情， 那就叫**动态代理**吧！”



动态代理技术发布了，臣民们得到了暂时的安抚，但是这个动态代理的缺陷就是必须有接口才能工作，帝国的臣民能忍受得了吗？

# GCLib实现动态代理

![image-20190302134102067](https://ws2.sinaimg.cn/large/006tKfTcly1g0oeaz32z9j30o80lggr5.jpg)

“孺子可教，你这个名称起得也不错，Interceptor，意味着拦截的意思。这样一来LogInterceptor就可以被其他类给复用了。”



“师傅， 回到我最初的问题， 怎么在运行时动态地生成Java Class啊？ 总不能让我直接写Java字节码吧？”



“这个你不用担心，有个叫做ASM的家伙，他已经对底层的Java字节码操作做了封装，你直接调用它就行了”



（老刘提示: 请移步 《[ASM： 一个低调成功者的自述](http://mp.weixin.qq.com/s?__biz=MzAxOTc0NzExNg==&mid=2665513528&idx=1&sn=da8b99016aeb4ede2e3c078682be0b46&chksm=80d67a7bb7a1f36dbbc3fc9b3a08ca4b9fae63dbcbd298562b9372da739d5fa4b049dec7ed33&scene=21#wechat_redirect)》）



“好， 让我去看看，这个玩意儿到底怎么样。”



ASM确实挺难的， 虽然对字节码操作做了封装，但是非得理解JVM指令才行，张大胖不得不去学习一下JVM字节码的知识，两个月后，他终于能够使用ASM动态的在内存中创建类了。 



又花了两个月，张大胖终于把整个系统开发完成，现在的使用非常简单：

![image-20190302134209969](https://ws2.sinaimg.cn/large/006tKfTcly1g0oec4urafj30ra0fiwrg.jpg)



张大胖把这个东西命名为**动态代理**， 因为所做的所有事情无非就是在运行时为原有的类建立一个代理，增加功能而已。 



过了两天， 师傅急匆匆地来找张大胖：“大胖， 我刚刚听说， Java帝国的IO大臣在JDK中加入了一个重要功能，叫做**Java 动态代理**，你赶紧研究下，看看和咱们做的有什么不同。”



大胖不敢怠慢，赶紧查看帝国发布的公告文书， 看完以后就放心了：“师傅， 这官方的动态代理有个重大的缺陷，就是必须有接口才能使用，而我们做的动态代理只要有个类就可以了， 我可以动态地生成一个子类。当然如果一个类被标记为final , 无法被继承，那就不行了。”



![image-20190302134224088](https://ws2.sinaimg.cn/large/006tKfTcly1g0oecfpj3cj30ww0len4j.jpg)

“嗯，有点意思” 师傅说道 “这官方动态生成的HelloWorldProxy是HelloWorld的**兄弟**， 而我们动态生成的HelloWorldProxy是HelloWorld的**孩子**啊！”



“哈哈，果然是这样。 师傅，你还没给我说这玩意儿到底有啥用处呢”



“你没看官方的公告吗？”



“官方大话套话连篇，看不懂啊！”



“还拿之前的例子来说吧，你现在有很多类，例如Person, Student, Employee,Teacher ... ， 每个类都有很多方法， 现在你想给这些方法加上日志输出，该怎么办呢？”



张大胖说：“我可以去改动代码， 嗯，这样改动量非常大，并且如果拿不到源码的话，就没办法了。”



“对啊，这时候动态代理不就派上用场了？ 动态生成代理类PersonProxy, StudentProxy，EmployProxy... 等等， 让它们去继承Person, Student, Employee,   这样代理类就可以增加日志输出代码了。 你甚至可以把要添加日志功能的类和方法写到一个XML文件中去， 然后再写个工具去读取这个XML文件，自动地生成所有代理类，多方便啊。”



“奥，原来如此 ，不仅仅是日志，还有事务了， 权限检查了，都可以用这种办法，对吧 ” 张大胖一点就通。



“是这样的， 这就是AOP编程了。  对了，既然官方已经把动态代理这个名称给占了， 我们就得改名了，不能叫做动态代理了”



“师傅，这个我已经想好了，叫做**Code Generation Library**， 简称**CGLib**, 体现了技术的本质，就是一个代码生成的工具。”



**后记**：CGLIb为了提高性能，还用了一种叫做FastClass的方式来直接调用一个对象的方法，而不是通过反射。   由于涉及的代码太多，本文不再展示，一个具体的调用过程参见下图：



![image-20190302134240758](https://ws2.sinaimg.cn/large/006tKfTcly1g0oeco5mx9j310g0suag2.jpg)





