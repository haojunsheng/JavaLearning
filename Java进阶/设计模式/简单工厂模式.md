[TOC]

# 前言

[设计模式（一）——设计模式概述](http://www.hollischuang.com/archives/1368)中简单介绍了设计模式以及各种设计模式的基本概念，本文主要介绍简单工厂模式，包括其概念、用途、实现方式及存在的问题等。

# 1. 概念

简单工厂模式是属于创建型模式，又叫做静态工厂方法（Static Factory Method）模式。简单工厂模式是由一个工厂对象决定创建出哪一种产品类的实例。简单工厂模式是工厂模式家族中最简单实用的模式，可以理解为是不同工厂模式的一个特殊实现。

> 值得注意的是，简单工厂模式并不属于23种GOF设计模式之一。但是他是抽象工厂模式，工厂方法模式的基础，并且也有广泛的应用。

# 2. 用途

在介绍简单工厂模式之前，我们尝试解决以下问题：

现在我们要使用面向对象的形式定义计算器，为了实现各算法之间的解耦。主要的用到的类如下：

```java
// 计算类的基类
public abstract class Operation {

    private double value1 = 0;
    private double value2 = 0;

    public double getValue1() {
        return value1;
    }

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    protected abstract double getResule();
}

//加法
public class OperationAdd extends Operation {

    @Override
    protected double getResule() {
        return getValue1() + getValue2();
    }
}

//减法
public class OperationSub extends Operation {

    @Override
    protected double getResule() {
        return getValue1() - getValue2();
    }
}

//乘法
public class OperationMul extends Operation {

    @Override
    protected double getResule() {
        return getValue1() * getValue2();
    }
}

//除法
public class OperationDiv extends Operation {

    @Override
    protected double getResule() {
        if (getValue2() != 0) {
            return getValue1() / getValue2();
        }
        throw new IllegalArgumentException("除数不能为零");
    }
}
```

当我想要执行加法运算时，可以使用如下代码：

```java
public class Main {

    public static void main(String[] args) {
        OperationAdd operationAdd = new OperationAdd();
        operationAdd.setValue1(10);
        operationAdd.setValue2(5);
        System.out.println(operationAdd.getResule());
    }
}
```

当我需要执行减法运算时，我就要创建一个OperationSub类。也就是说，我想要使用不同的运算的时候就要创建不同的类，并且要明确知道该类的名字。那么这种重复的创建类的工作其实可以放到一个统一的工厂类中。

简单工厂模式有以下优点：

> 1、一个调用者想创建一个对象，只要知道其名称就可以了。
>
> 2、屏蔽产品的具体实现，调用者只关心产品的接口。

# 3. 实现方式

简单工厂模式其实和他的名字一样，很简单。先来看看它的组成:

> 1) 工厂类角色:这是本模式的核心,含有一定的商业逻辑和判断逻辑。在java中它往往由 一个具体类实现。（OperationFactory）
>
> 2) 抽象产品角色:它一般是具体产品继承的父类或者实现的接口。在java中由接口或者抽象类来实现。（Operation）
>
> 3) 具体产品角色:工厂类所创建的对象就是此角色的实例。在java中由一个具体类实现。 来用类图来清晰的表示下的它们之间的关系（OperationAdd\OperationSub等）

![QQ20160411-0](https://ws1.sinaimg.cn/large/006tKfTcly1g0aojpwwiyj30gw089td7.jpg)

在原有类的基础上，定义工厂类：

```java
//工厂类
public class OperationFactory {

    public static Operation createOperation(String operation) {
        Operation oper = null;
        switch (operation) {

            case "+":
                oper = new OperationAdd();
                break;

            case "-":
                oper = new OperationSub();
                break;

            case "*":
                oper = new OperationMul();
                break;

            case "/":
                oper = new OperationDiv();
                break;
            default:
                throw new UnsupportedOperationException("不支持该操作");
        }
        return oper;
    }
}
```

有了工厂类之后，可以使用工厂创建对象：

```java
Operation operationAdd = OperationFactory.createOperation("+");
operationAdd.setValue1(10);
operationAdd.setValue2(5);
System.out.println(operationAdd.getResule());
```

通过简单工厂模式，该计算器的使用者不需要关系实现加法逻辑的那个类的具体名字，他只要知道该类对应的参数”+”就可以了。

# 4. 简单工厂模式存在的问题

[在设计模式（一）——设计模式概述](http://www.hollischuang.com/archives/1368)中介绍了设计模式一般应该遵循的几个原则。

下面我们从开闭原则(对扩展开放;对修改封闭)上来分析下简单工厂模式。当我们需要增加一种计算时，例如开平方。这个时候我们需要先定义一个类继承`Operation`类，其中实现平方的代码。除此之外我们还要修改`OperationFactory`类的代码，增加一个case。这显然是违背开闭原则的。可想而知对于新产品的加入，工厂类是很被动的。

我们举的例子是最简单的情况。而在实际应用中，很可能产品是一个多层次的树状结构。 简单工厂可能就不太适用了。

# 5. 总结

工厂类是整个简单工厂模式的关键。包含了必要的逻辑判断，根据外界给定的信息，决定究竟应该创建哪个具体类的对象。通过使用工厂类，外界可以从直接创建具体产品对象的尴尬局面摆脱出来，仅仅需要负责“消费”对象就可以了。而不必管这些对象究竟如何创建及如何组织的。明确了各自的职责和权利，有利于整个软件体系结构的优化。

但是由于工厂类集中了所有实例的创建逻辑，违反了高内聚责任分配原则，将全部创建逻辑集中到了一个工厂类中；它所能创建的类只能是事先考虑到的，如果需要添加新的类，则就需要改变工厂类了。

当系统中的具体产品类不断增多时候，可能会出现要求工厂类根据不同条件创建不同实例的需求．这种对条件的判断和对具体产品类型的判断交错在一起，很难避免模块功能的蔓延，对系统的维护和扩展非常不利；

这些缺点在工厂方法模式中得到了一定的解决。

文中所有代码见[GitHub](https://github.com/hollischuang/DesignPattern)

## 参考资料

[大话设计模式](http://s.click.taobao.com/t?e=m=2&s=R5B/xd29JVMcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67jN2wQzI0ZBVHBMajAjK1gBpS4hLH/P02ckKYNRBWOBBey11vvWwHXSniyi5vWXIZkKWZZq7zWpCC8X3k5aQlui0qVGgqDL2o8YMXU3NNCg/&pvid=10_42.120.73.203_224_1460382841310)

