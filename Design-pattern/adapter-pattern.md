[TOC]

# 前言

# 1. 结构型设计模式

结构型模式(Structural Pattern)描述如何将类或者对象结合在一起形成更大的结构，就像搭积木，可以通过简单积木的组合形成复杂的、功能更为强大的结构。

结构型模式可以分为类结构型模式和对象结构型模式：

> 类结构型模式关心类的组合，由多个类可以组合成一个更大的 系统，在类结构型模式中一般只存在继承关系和实现关系。
>
> 对象结构型模式关心类与对象的组合，通过关联关系使得在一 个类中定义另一个类的实例对象，然后通过该对象调用其方法。 根据“合成复用原则”，在系统中尽量使用关联关系来替代继承关系，因此大部分结构型模式都是对象结构型模式。

# 2. 概念

GOF是这样给适配器模式(`Adapter`)定义的：将一个类的接口转化成用户需要的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

GOF中将适配器模式分为类适配器模式和对象适配器模式。区别仅在于适配器角色对于被适配角色的适配是通过继承还是组合来实现的。由于在Java 中不支持多重继承，而且有破坏封装之嫌。而且我们也提倡[多用组合少用继承](http://www.hollischuang.com/archives/1319)。所以本文主要介绍对象适配器。

# 3. 用途

相信大家都有这样的生活常识：就是目前我们使用的电子设备充电器的型号是不一样的。现在主流的手机充电器口主要包含Mini Usb、Micro Usb和Lightning三种。其中Mini Usb广泛出现在读卡器、MP3、数码相机以及移动硬盘上。由于Micro Usb比Mini Usb更薄，所有广泛应用于手机上，常见于安卓手机。还有一个比较常见的充电器口就是苹果手机常用的Lightning。

当然，特定型号的手机只能使用特定型号的充电器充电。比如Iphone6手机只能使用Lightning接口的充电器进行充电。但是，如果我们身边只有一条安卓的Micro Usb充电器线的话，我们能不能为苹果手机充电呢？答案是肯定的，只要有一个适配器就可以了。

![adapter](https://ws3.sinaimg.cn/large/006tKfTcly1g0bkpm1gm9j308c08c3yf.jpg)

适配器，在我们日常生活中随处可见。适配器模式也正是解决了类似的问题。

在程序设计过程中我们可能也遇到类似的场景：

> 1、系统需要使用现有的类，而此类的接口不符合系统的需要。
>
> 2、想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类，包括一些可能在将来引进的类一起工作，这些源类不一定有一致的接口。
>
> 3、通过接口转换，将一个类插入另一个类系中。（比如老虎和飞禽，现在多了一个飞虎，在不增加实体的需求下，增加一个适配器，在里面包容一个虎对象，实现飞的接口。）

以上场景都适合使用适配器模式。

# 4. 实现方式

适配器模式包含如下角色：

> Target：目标抽象类
>
> Adapter：适配器类
>
> Adaptee：适配者类
>
> Client：客户类

![Adapter-pattern](https://ws1.sinaimg.cn/large/006tKfTcly1g0bkr2m9r9j30k408pglz.jpg)

这里采用文章开头介绍的手机充电口的例子，我们定义一个适配器，该适配器的功能就是使用安卓充电器给苹果设备充电。

先定义接口：

```java
/**
 * MicroUsb充电器接口
 */
public interface MicroUsbInterface {
    public void chargeWithMicroUsb();
}

/**
 * Lightning充电器接口
 */
public interface LightningInterface {
    public void chargeWithLightning();
}
```

定义具体的实现类

```java
/**
 * 安卓设备的充电器
 */
public class AndroidCharger implements MicroUsbInterface {
    @Override
    public void chargeWithMicroUsb() {
        System.out.println("使用MicroUsb型号的充电器充电...");
    }
}

/**
 * 苹果设备的充电器
 */
public class AppleCharger implements LightningInterface {
    @Override
    public void chargeWithLightning() {
        System.out.println("使用Lightning型号的充电器充电...");
    }
}
```

> 因为我们要使用适配器模式将MicroUsb转成Lightning，所以这里的AppleCharger是本来不需要定义的。因为我们使用适配器的目的就是代替新建一个他。这里定义出来是为了使例子更加完整。

定义两个手机

```java
public class Iphone6Plus {

    private LightningInterface lightningInterface;

    public Iphone6Plus() {
    }

    public Iphone6Plus(LightningInterface lightningInterface) {
        this.lightningInterface = lightningInterface;
    }

    public void charge() {
        System.out.println("开始给我的Iphone6Plus手机充电...");
        lightningInterface.chargeWithLightning();
        System.out.println("结束给我的Iphone6Plus手机充电...");
    }

    public LightningInterface getLightningInterface() {
        return lightningInterface;
    }

    public void setLightningInterface(LightningInterface lightningInterface) {
        this.lightningInterface = lightningInterface;
    }
}

public class GalaxyS7 {

    private MicroUsbInterface microUsbInterface;

    public GalaxyS7() {
    }

    public GalaxyS7(MicroUsbInterface microUsbInterface) {
        this.microUsbInterface = microUsbInterface;
    }

    public void charge(){
        System.out.println("开始给我的GalaxyS7手机充电...");
        microUsbInterface.chargeWithMicroUsb();
        System.out.println("开始给我的GalaxyS7手机充电...");
    }

    public MicroUsbInterface getMicroUsbInterface() {
        return microUsbInterface;
    }

    public void setMicroUsbInterface(MicroUsbInterface microUsbInterface) {
        this.microUsbInterface = microUsbInterface;
    }
}
```

这里定义手机的作用是为了更方便的理解适配器模式，在该模式中他不扮演任何角色。

定义适配器

```java
/**
 * 适配器,将MicroUsb接口转成Lightning接口
 */
public class Adapter implements LightningInterface {
    private MicroUsbInterface microUsbInterface;

    public Adapter() {
    }

    public Adapter(MicroUsbInterface microUsbInterface) {
        this.microUsbInterface = microUsbInterface;
    }

    @Override
    public void chargeWithLightning() {
        microUsbInterface.chargeWithMicroUsb();
    }

    public MicroUsbInterface getMicroUsbInterface() {
        return microUsbInterface;
    }

    public void setMicroUsbInterface(MicroUsbInterface microUsbInterface) {
        this.microUsbInterface = microUsbInterface;
    }
}
```

该适配器的功能是把一个MicroUsb转换成Lightning。实现方式是实现目标类的接口（`LightningInterface`），然后使用组合的方式，在该适配器中定义microUsb。然后在重写的`chargeWithLightning（）`方法中，采用microUsb的方法来实现具体细节。

定义客户端

```java
public class Main {

    public static void main(String[] args) {
        Iphone6Plus iphone6Plus = new Iphone6Plus(new AppleCharger());
        iphone6Plus.charge();

        System.out.println("==============================");

        GalaxyS7 galaxyS7 = new GalaxyS7(new AndroidCharger());
        galaxyS7.charge();

        System.out.println("==============================");

        Adapter adapter  = new Adapter(new AndroidCharger());
        Iphone6Plus newIphone = new Iphone6Plus();
        newIphone.setLightningInterface(adapter);
        newIphone.charge();
    }
}
```

输出结果：

```
开始给我的Iphone6Plus手机充电...
使用Lightning型号的充电器充电...
结束给我的Iphone6Plus手机充电...
==============================
开始给我的GalaxyS7手机充电...
使用MicroUsb型号的充电器充电...
开始给我的GalaxyS7手机充电...
==============================
开始给我的Iphone6Plus手机充电...
使用MicroUsb型号的充电器充电...
结束给我的Iphone6Plus手机充电...
```

上面的例子通过适配器，把一个MicroUsb型号的充电器用来给Iphone充电。从代码层面，就是通过适配器复用了MicroUsb接口及其实现类。在很大程度上福永了已有的代码。

# 5. 优缺点

## 5.1 优点

将目标类和适配者类解耦，通过引入一个适配器类来重用现有的适配者类，而无须修改原有代码。

增加了类的透明性和复用性，将具体的实现封装在适配者类中，对于客户端类来说是透明的，而且提高了适配者的复用性。

灵活性和扩展性都非常好，通过使用配置文件，可以很方便地更换适配器，也可以在不修改原有代码的基础上增加新的适配器类，完全符合“开闭原则”。

## 5.2 缺点

过多地使用适配器，会让系统非常零乱，不易整体进行把握。比如，明明看到调用的是 A 接口，其实内部被适配成了 B 接口的实现，一个系统如果太多出现这种情况，无异于一场灾难。因此如果不是很有必要，可以不使用适配器，而是直接对系统进行重构。

对于类适配器而言，由于 JAVA 至多继承一个类，所以至多只能适配一个适配者类，而且目标类必须是抽象类

# 6. 总结

结构型模式描述如何将类或者对象结合在一起形成更大的结构。

适配器模式用于将一个接口转换成客户希望的另一个接口，适配器模式使接口不兼容的那些类可以一起工作，其别名为包装器。适配器模式既可以作为类结构型模式，也可以作为对象结构型模式。

适配器模式包含四个角色：

> 目标抽象类定义客户要用的特定领域的接口；
>
> 适配器类可以调用另一个接口，作为一个转换器，对适配者和抽象目标类进行适配，它是适配器模式的核心；
>
> 适配者类是被适配的角色，它定义了一个已经存在的接口，这个接口需要适配；
>
> 在客户类中针对目标抽象类进行编程，调用在目标抽象类中定义的业务方法。

在对象适配器模式中，适配器类继承了目标抽象类(或实现接口)并定义了一个适配者类的对象实例，在所继承的目标抽象类方法中调用适配者类的相应业务方法。

适配器模式的主要优点是将目标类和适配者类解耦，增加了类的透明性和复用性，同时系统的灵活性和扩展性都非常好，更换适配器或者增加新的适配器都非常方便，符合“[开闭原则](http://www.hollischuang.com/archives/220)”；类适配器模式的缺点是适配器类在很多编程语言中不能同时适配多个适配者类，对象适配器模式的缺点是很难置换适配者类的方法。

适配器模式适用情况包括：系统需要使用现有的类，而这些类的接口不符合系统的需要；想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类一起工作。

# 7. 参考资料

[适配器模式](http://www.runoob.com/design-pattern/adapter-pattern.html)

[适配器模式](http://design-patterns.readthedocs.io/zh_CN/latest/structural_patterns/adapter.html)

文中所有代码见[GitHub](https://github.com/hollischuang/DesignPattern)



# 8. 郭霖的讲解

今天一大早，你的leader就匆匆忙忙跑过来找到你：“快，快，紧急任务！最近ChinaJoy马上就要开始了，老板要求提供一种直观的方式，可以查看到我们新上线的游戏中每个服的在线人数。”



你看了看日期，不是吧！这哪里是马上要开始了，分明是已经开始了！这怎么可能来得及呢？



“没关系的。”你的leader安慰你道：“功能其实很简单的，接口都已经提供好了，你只需要调用一下就行了。”



好吧，你勉为其难地接受了，对于这种突如其来的新需求，你早已习惯。



你的leader向你具体描述了一下需求，你们的游戏目前有三个服，一服已经开放一段时间了，二服和三服都是新开的服。设计的接口非常轻便，你只需要调用Utility.getOnlinePlayerCount(int)，传入每个服对应的数值就可以获取到相应服在线玩家的数量了，如一服传入1，二服传入2，三服则传入3。如果你传入了一个不存在的服，则会返回-1。然后你只要将得到的数据拼装成XML就好，具体的显示功能由你的leader来完成。



好吧，听起来功能并不是很复杂，如果现在就开始动工好像还来得及，于是你马上敲起了代码。



首先定义一个用于统计在线人数的接口PlayerCount，代码如下：

public interface PlayerCount {

	String getServerName();
	 
	int getPlayerCount();

}
接着定义三个统计类实现了PlayerCount接口，分别对应了三个不同的服，如下所示：
public class ServerOne implements PlayerCount {

	@Override
	public String getServerName() {
		return "一服";
	}
	 
	@Override
	public int getPlayerCount() {
		return Utility.getOnlinePlayerCount(1);
	}

}
public class ServerTwo implements PlayerCount {

	@Override
	public String getServerName() {
		return "二服";
	}
	 
	@Override
	public int getPlayerCount() {
		return Utility.getOnlinePlayerCount(2);
	}

}
public class ServerThree implements PlayerCount {

	@Override
	public String getServerName() {
		return "三服";
	}
	 
	@Override
	public int getPlayerCount() {
		return Utility.getOnlinePlayerCount(3);
	}

}
然后定义一个XMLBuilder类，用于将各服的数据封装成XML格式，代码如下：
public class XMLBuilder {

	public static String buildXML(PlayerCount player) {
		StringBuilder builder = new StringBuilder();
		builder.append("<root>");
		builder.append("<server>").append(player.getServerName()).append("</server>");
		builder.append("<player_count").append(player.getPlayerCount()).append("</player_count>");
		builder.append("</root>");
		return builder.toString();
	}

}
这样的话，所有代码就完工了，如果你想查看一服在线玩家数只需要调用:
XMLBuilder.buildXML(new ServerOne());
查看二服在线玩家数只需要调用：
XMLBuilder.buildXML(new ServerTwo());
查看三服在线玩家数只需要调用：
XMLBuilder.buildXML(new ServerThree());
咦？你发现查看一服在线玩家数的时候，返回值永远是-1，查看二服和三服都很正常。



你只好把你的leader叫了过来：“我感觉我写的代码没有问题，但是查询一服在线玩家数总是返回-1，为什么会这样呢？”



“哎呀！”你的leader猛然想起，“这是我的问题，前面没跟你解释清楚。由于我们的一服已经开放一段时间了，查询在线玩家数量的功能早就有了，使用的是ServerFirst这个类。当时写Utility.getOnlinePlayerCount()这个方法主要是为了针对新开的二服和三服，就没把一服的查询功能再重复做一遍。”



听到你的leader这么说，你顿时松了一口气：“那你修改一下Utility.getOnlinePlayerCount()就好了，应该没我什么事了吧？”



“晤。。。本来应该是这样的。。。可是，Utility和ServerFirst这两个类都已经被打到Jar包里了，没法修改啊。。。”你的leader有些为难。



“什么？这不是坑爹吗，难道要我把接口给改了？”你已经泪流满面了。



“这倒不用，这种情况下可以使用适配器模式，这个模式就是为了解决接口之间不兼容的问题而出现的。”



其实适配器模式的使用非常简单，核心思想就是只要能让两个互不兼容的接口能正常对接就行了。上面的代码中，XMLBuilder中使用PlayerCount这个接口来拼装XML，而ServerFirst并没有实现PlayerCount这个接口，这个时候就需要一个适配器类来为XMLBuilder和ServerFirst之间搭起一座桥梁，毫无疑问，ServerOne就将充当适配器类的角色。修改ServerOne的代码，如下所示：

public class ServerOne implements PlayerCount {
	
	private ServerFirst mServerFirst;
	
	public ServerOne() {
		mServerFirst = new ServerFirst();
	}
	 
	@Override
	public String getServerName() {
		return "一服";
	}
	 
	@Override
	public int getPlayerCount() {
		return mServerFirst.getOnlinePlayerCount();
	}

}
这样通过ServerOne的适配，XMLBuilder和ServerFirst之间就成功完成对接了！使用的时候我们甚至无需知道有ServerFirst这个类，只需要正常创建ServerOne的实例就行了。


需要值得注意的一点是，适配器模式不并是那种会让架构变得更合理的模式，更多的时候它只是充当救火队员的角色，帮助解决由于前期架构设计不合理导致的接口不匹配的问题。更好的做法是在设计的时候就尽量把以后可能出现的情况多考虑一些，在这个问题上不要向你的leader学习。



适配器：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

