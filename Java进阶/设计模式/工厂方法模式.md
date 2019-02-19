[TOC]

# 前言

在[设计模式（四）——简单工厂模式](http://www.hollischuang.com/archives/1391)文章中介绍了简单工厂模式，通过一个例子讲述了如何使用简单工厂模式。同时也留下了一个问题，那就是简单工厂模式破坏了[开放-封闭原则](http://www.hollischuang.com/archives/220)。那么本文将介绍另外一种设计模式——工厂方法模式。主要介绍其概念、用途、实现方式、以及优缺点等。

# 概念

工厂方法模式(Factory Method Pattern)又称为工厂模式，也叫虚拟构造器(Virtual Constructor)模式或者多态工厂(Polymorphic Factory)模式，它属于类创建型模式。

工厂方法模式是一种实现了“工厂”概念的面向对象设计模式。就像其他创建型模式一样，它也是处理在不指定对象具体类型的情况下创建对象的问题。

> 工厂方法模式的实质是“定义一个创建对象的接口，但让实现这个接口的类来决定实例化哪个类。工厂方法让类的实例化推迟到子类中进行。”

# 2. 用途

工厂方法模式和[简单工厂模式](http://www.hollischuang.com/archives/1391)虽然都是通过工厂来创建对象，他们之间最大的不同是——**工厂方法模式在设计上完全完全符合“开闭原则”。**

在以下情况下可以使用工厂方法模式：

> 一个类不知道它所需要的对象的类：在工厂方法模式中，客户端不需要知道具体产品类的类名，只需要知道所对应的工厂即可，具体的产品对象由具体工厂类创建；客户端需要知道创建具体产品的工厂类。
>
> 一个类通过其子类来指定创建哪个对象：在工厂方法模式中，对于抽象工厂类只需要提供一个创建产品的接口，而由其子类来确定具体要创建的对象，利用面向对象的多态性和[里氏代换原则](http://www.hollischuang.com/archives/220)，在程序运行时，子类对象将覆盖父类对象，从而使得系统更容易扩展。
>
> 将创建对象的任务委托给多个工厂子类中的某一个，客户端在使用时可以无须关心是哪一个工厂子类创建产品子类，需要时再动态指定，可将具体工厂类的类名存储在配置文件或数据库中。

# 3. 实现方式

工厂方法模式包含如下角色：

> Product：抽象产品（`Operation`）
>
> ConcreteProduct：具体产品(`OperationAdd`)
>
> Factory：抽象工厂(`IFactory`)
>
> ConcreteFactory：具体工厂(`AddFactory`)

![QQ20160412-0](https://ws2.sinaimg.cn/large/006tKfTcly1g0aoymyjjzj30m60eeakk.jpg)

这里还用计算器的例子。在保持`Operation`，`OperationAdd`，`OperationDiv`，`OperationSub`，`OperationMul`等几个方法不变的情况下，修改简单工厂模式中的工厂类（`OperationFactory`）。替代原有的那个”万能”的大工厂类，这里使用工厂方法来代替：

```java
//工厂接口
public interface IFactory {
    Operation CreateOption();
}

//加法类工厂
public class AddFactory implements IFactory {

    public Operation CreateOption() {
        return new OperationAdd();
    }
}

//除法类工厂
public class DivFactory implements IFactory {

    public Operation CreateOption() {
        return new OperationDiv();
    }
}

//除法类工厂
public class MulFactory implements IFactory {

    public Operation CreateOption() {
        return new OperationMul();
    }
}

//减法类工厂
public class SubFactory implements IFactory {

    public Operation CreateOption() {
        return new OperationSub();
    }
}
```

这样，在客户端中想要执行加法运算时，需要以下方式：

```java
public class Main {

    public static void main(String[] args) {
        IFactory factory = new AddFactory();
        Operation operationAdd =  factory.CreateOption();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResult());
    }
}
```

到这里，一个工厂方法模式就已经写好了。

------

从代码量上看，这种工厂方法模式比简单工厂方法模式更加复杂。针对不同的操作（Operation）类都有对应的工厂。很多人会有以下疑问：

> 貌似工厂方法模式比简单工厂模式要复杂的多？
>
> 工厂方法模式和我自己创建对象没什么区别？为什么要多搞出一些工厂来？

下面就针对以上两个问题来深入理解一下工厂方法模式。

# 4. 工厂方法模式的利与弊

## 4.1 为什么要使用工厂来创建对象？

> 封装对象的创建过程

在工厂方法模式中，工厂方法用来创建客户所需要的产品，同时还向客户**隐藏了哪种具体产品类将被实例化这一细节，用户只需要关心所需产品对应的工厂，无须关心创建细节，甚至无须知道具体产品类的类名。**

基于工厂角色和产品角色的多态性设计是工厂方法模式的关键。**它能够使工厂可以自主确定创建何种产品对象，而如何创建这个对象的细节则完全封装在具体工厂内部。**工厂方法模式之所以又被称为多态工厂模式，是因为所有的具体工厂类都具有同一抽象父类。

## 4.2 为什么每种对象要单独有一个工厂？

> 符合『[开放-封闭原则](http://www.hollischuang.com/archives/220http://)』

主要目的是为了解耦。在系统中加入新产品时，无须修改抽象工厂和抽象产品提供的接口，无须修改客户端，也无须修改其他的具体工厂和具体产品，而只要添加一个具体工厂和具体产品就可以了。这样，系统的可扩展性也就变得非常好，完全符合“[开闭原则](http://www.hollischuang.com/archives/220)”。

以上就是工厂方法模式的优点。但是，工厂模式也有一些不尽如人意的地方：

> 在添加新产品时，需要编写新的具体产品类，而且还要提供与之对应的具体工厂类，**系统中类的个数将成对增加**，在一定程度上增加了系统的复杂度，有更多的类需要编译和运行，会给系统带来一些额外的开销。
>
> 由于考虑到系统的可扩展性，需要引入抽象层，在客户端代码中均使用抽象层进行定义，**增加了系统的抽象性和理解难度**，且在实现时可能需要用到DOM、反射等技术，增加了系统的实现难度。

## 4.3 工厂方法与简单工厂的区别

工厂模式克服了简单工厂模式违背[开放-封闭原则](http://www.hollischuang.com/archives/220)的缺点，又保持了封装对象创建过程的优点。

他们都是集中封装了对象的创建，使得要更换对象时，不需要做大的改动就可实现，降低了客户端与产品对象的耦合。

# 5. 总结

工厂方法模式是简单工厂模式的进一步抽象和推广。

由于使用了面向对象的多态性，工厂方法模式保持了简单工厂模式的优点，而且克服了它的缺点。

在工厂方法模式中，核心的工厂类不再负责所有产品的创建，而是将具体创建工作交给子类去做。这个核心类仅仅负责给出具体工厂必须实现的接口，而不负责产品类被实例化这种细节，这使得工厂方法模式可以允许系统在不修改工厂角色的情况下引进新产品。

工厂方法模式的主要优点是增加新的产品类时无须修改现有系统，并封装了产品对象的创建细节，系统具有良好的灵活性和可扩展性；其缺点在于增加新产品的同时需要增加新的工厂，导致系统类的个数成对增加，在一定程度上增加了系统的复杂性。

文中所有代码见[GitHub](https://github.com/hollischuang/DesignPattern)

# 6. 参考资料

[大话设计模式](http://s.click.taobao.com/t?e=m=2&s=R5B/xd29JVMcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67jN2wQzI0ZBVHBMajAjK1gBpS4hLH/P02ckKYNRBWOBBey11vvWwHXSniyi5vWXIZkKWZZq7zWpCC8X3k5aQlui0qVGgqDL2o8YMXU3NNCg/&pvid=10_42.120.73.203_224_1460382841310)

[深入浅出设计模式](http://s.click.taobao.com/t?e=m%3D2%26s%3DObpq8Qxse2EcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67utJaEGcptl2kfkm8XrrgBtpS4hLH%2FP02ckKYNRBWOBBey11vvWwHXTpkOAWGyim%2Bw2PNKvM2u52N5aP5%2Bgx7zgh4LxdBQDQSXEqY%2Bakgpmw&pvid=10_121.0.29.199_322_1460465025379)

[工厂方法模式(Factory Method Pattern)](http://design-patterns.readthedocs.org/zh_CN/latest/creational_patterns/factory_method.html#id11)



# 7. [JDK中的那些工厂方法](https://www.hollischuang.com/archives/1408)

在[设计模式（五）——工厂方法模式](http://www.hollischuang.com/archives/1401)中介绍了工厂方法模式。本文通过介绍JDK源码中用到的工厂方法，在上篇文章的基础上深入理解一下工厂方法。

## 7.1 再谈工厂方法

在[设计模式（五）——工厂方法模式](http://www.hollischuang.com/archives/1401)中用整篇介绍了工厂方法模式。为什么要再谈呢？因为很多人走进了一个误区。认为工厂方法模式就是要严格包含抽象产品、具体产品、抽象工厂和具体工厂等角色。其实并不是这样的。

> 有时候也会创建不使用多态性创建对象的工厂方法，以得到使用工厂方法的其他好处。

如果抛开设计模式的范畴，“工厂方法”这个词也可以指**作为“工厂”的方法**，这个方法的主要目的就是创建对象，而这个方法不一定在单独的工厂类中。这些方法通常作为静态方法，定义在方法所实例化的类中。

> 每个工厂方法都有特定的名称。在许多面向对象的编程语言中，构造方法必须和它所在的类具有相同的名称，这样的话，如果有多种创建对象的方式（重载）就可能导致歧义。工厂方法没有这种限制，所以可以具有描述性的名称。举例来说，根据两个实数创建一个复数，而这两个实数表示直角坐标或极坐标，如果使用工厂方法，方法的含义就非常清晰了。当工厂方法起到这种消除歧义的作用时，构造方法常常被设置为私有方法，从而强制客户端代码使用工厂方法创建对象。

```java
class Complex {
     public static Complex fromCartesianFactory(double real, double imaginary) {
         return new Complex(real, imaginary);
     }
     public static Complex fromPolarFactory(double modulus, double angle) {
         return new Complex(modulus * cos(angle), modulus * sin(angle));
     }
     private Complex(double a, double b) {
         //...
     }
}

Complex product = Complex.fromPolarFactory(1, pi);
```

上面的代码也可以叫做使用了工厂方法模式。同样是定义了一个工厂方法，方法的作用就是生成一个新的对象。

## 7.2 JDK中的工厂方法模式

看过前面的介绍和上一篇文章的介绍，相信读者对工厂方法有了一定的认识，那么能不能试着回答这样一个问题：什么情况下应该使用工厂方法模式？

其实这个问题很简单。很多时候我们在自己的代码中会不自觉的会使用到工厂方法模式。考虑以下情况：

> 创建对象需要大量重复的代码。
>
> 创建对象需要访问某些信息，而这些信息不应该包含在复合类中。
>
> 创建对象的生命周期必须集中管理，以保证在整个程序中具有一致的行为。

遇到上面的情况时，你是不是会”很自觉”的把创建对象的代码封装到一个方法中呢？这个方法就是工厂方法。其实这就是工厂方法的思想。

在JDK中，也有很多使用工厂方法模式的代码。下面就介绍几个典型的用法。

## 7.3 Collection中的iterator方法

`java.util.Collection`接口中定义了一个抽象的`iterator()`方法，该方法就是一个工厂方法。

对于`iterator()`方法来说`Collection`就是一个根抽象工厂，下面还有`List`等接口作为抽象工厂，再往下有`ArrayList`等具体工厂。

`java.util.Iterator`接口是根抽象产品，下面有`ListIterator`等抽象产品，还有`ArrayListIterator`等作为具体产品。

使用不同的具体工厂类中的`iterator`方法能得到不同的具体产品的实例。

![collection-iterator-uml](https://ws4.sinaimg.cn/large/006tKfTcly1g0ary1hom2j30bj0cyjs8.jpg)

## 7.4 JDBC数据库开发

在使用`JDBC`进行数据库开发时，如果数据库由MySQL改为Oracle或其他，则只需要改一下数据库驱动名称就可以，其他都不用修改（前提是使用的都是标准SQL语句）。或者在Hibernate框架中，更换数据库方言也是类似道理。

## 7.5 连接邮件服务器框架

如果需要设计一个连接邮件服务器的框架，那么就要考虑到连接邮件服务器有几种方式：`POP3`、`SMTP`、`HTTP`。就可以定义一个连接邮件服务器接口，在此接口中定义一些对邮件操作的接口方法，把这三种连接方式封装成产品类，实现接口中定义的抽象方法。再定义抽象工厂和具体工厂，当选择不同的工厂时，对应到产生相应的连接邮件产品对象。采用这种工厂方法模式的设计，就可以做到良好的扩展性。比如某些邮件服务器提供了WebService接口，只需要增加一个产品类和工厂类就可以了，而不需要修改原来代码。

## 7.6 其他工厂方法

除上面介绍的典型的使用工厂方法模式的用法外，在JDK中还有几处使用了工厂方法来创建对象。其中包括：

```java
java.lang.Proxy#newProxyInstance()
java.lang.Object#toString()
java.lang.Class#newInstance()
java.lang.reflect.Array#newInstance()
java.lang.reflect.Constructor#newInstance()
java.lang.Boolean#valueOf(String)
java.lang.Class#forName()
```

读者感兴趣可以找出来看一看，这些方法都是用于返回具体对象。上面大部分方法都和反射有关，因为反射创建对象的过程比较复杂，封装成工厂方法更易于使用。

## 7.7 参考资料

[工厂方法模式](http://tianweili.github.io/blog/2015/03/09/factory-method-pattern/#h39)































