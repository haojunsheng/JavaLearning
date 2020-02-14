[TOC]

# 前言

[设计模式（一）——设计模式概述](http://www.hollischuang.com/archives/1368)中简单介绍了设计模式以及各种设计模式的基本概念，本文主要介绍**单例设计模式**。包括单例的概念、用途、实现方式、如何防止被序列化破坏等。

# 1. 概念

单例模式（`Singleton Pattern`）是 Java 中最简单的设计模式之一。这种类型的设计模式属于创建型模式。在 [GOF 书](http://s.click.taobao.com/t?e=m%3D2%26s%3DT5l23XuxzMIcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67sqCcISrC8hOF%2FSaKyaJTUZpS4hLH%2FP02ckKYNRBWOBBey11vvWwHXSniyi5vWXIZhtlrJbLMDAQihpQCXu2JnMU7C4KV%2Fo0CcYMXU3NNCg%2F&pvid=10_42.120.73.203_2589754_1459955095482)中给出的定义为：**保证一个类仅有一个实例，并提供一个访问它的全局访问点**。

单例模式一般体现在类声明中，单例的类负责创建自己的对象，同时确保只有单个对象被创建。这个类提供了一种访问其唯一的对象的方式，可以直接访问，不需要实例化该类的对象。

# 2. 用途

单例模式有以下两个优点：

> 在内存里只有一个实例，减少了内存的开销，尤其是频繁的创建和销毁实例（比如网站首页页面缓存）。
>
> 避免对资源的多重占用（比如写文件操作）。

有时候，我们在选择使用单例模式的时候，不仅仅考虑到其带来的优点，还有可能是有些场景就必须要单例。比如类似”一个党只能有一个主席”的情况。

# 3. 实现方式

我们知道，一个类的对象的产生是由类构造函数来完成的。如果一个类对外提供了`public`的构造方法，那么外界就可以任意创建该类的对象。所以，如果想限制对象的产生，一个办法就是将构造函数变为私有的(至少是受保护的)，使外面的类不能通过引用来产生对象。同时为了保证类的可用性，就必须提供一个自己的对象以及访问这个对象的静态方法。

![QQ20160406-0](https://ws2.sinaimg.cn/large/006tKfTcly1g0an9wlc3aj309106gmxj.jpg)

## 3.1 饿汉式

下面是一个简单的单例的实现：

```java
//code 1
public class Singleton {
    //在类内部实例化一个实例
    private static Singleton instance = new Singleton();
    //私有的构造函数,外部无法访问
    private Singleton() {
    }
    //对外提供获取实例的静态方法
    public static Singleton getInstance() {
        return instance;
    }
}
```

使用以下代码测试：

```java
//code2
public class SingletonClient {

    public static void main(String[] args) {
        SimpleSingleton simpleSingleton1 = SimpleSingleton.getInstance();
        SimpleSingleton simpleSingleton2 = SimpleSingleton.getInstance();
        System.out.println(simpleSingleton1==simpleSingleton2);
    }
}
```

输出结果：

```
true
```

code 1就是一个简单的单例的实现，这种实现方式我们称之为饿汉式。所谓饿汉。这是个比较形象的比喻。对于一个饿汉来说，他希望他想要用到这个实例的时候就能够立即拿到，而不需要任何等待时间。所以，通过`static`的静态初始化方式，在该类第一次被加载的时候，就有一个`SimpleSingleton`的实例被创建出来了。这样就保证在第一次想要使用该对象时，他已经被初始化好了。

同时，由于该实例在类被加载的时候就创建出来了，所以也避免了线程安全问题。（原因见：[在深度分析Java的ClassLoader机制（源码级别）](http://www.hollischuang.com/archives/197)、[Java类的加载、链接和初始化](http://www.hollischuang.com/archives/201)）

还有一种饿汉模式的变种：

```java
//code 3
public class Singleton2 {
    //在类内部定义
    private static Singleton2 instance;
    static {
        //实例化该实例
        instance = new Singleton2();
    }
    //私有的构造函数,外部无法访问
    private Singleton2() {
    }
    //对外提供获取实例的静态方法
    public static Singleton2 getInstance() {
        return instance;
    }
}
```

code 3和code 1其实是一样的，都是在类被加载的时候实例化一个对象。

**饿汉式单例，在类被加载的时候对象就会实例化。这也许会造成不必要的消耗，因为有可能这个实例根本就不会被用到。而且，如果这个类被多次加载的话也会造成多次实例化。其实解决这个问题的方式有很多，下面提供两种解决方式，第一种是使用静态内部类的形式。第二种是使用懒汉式。**

## 3.2 静态内部类式

先来看通过静态内部类的方式解决上面的问题：

```java
//code 4
public class StaticInnerClassSingleton {
    //在静态内部类中初始化实例对象
    private static class SingletonHolder {
        private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
    }
    //私有的构造方法
    private StaticInnerClassSingleton() {
    }
    //对外提供获取实例的静态方法
    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

这种方式同样利用了classloder的机制来保证初始化`instance`时只有一个线程，它跟饿汉式不同的是（很细微的差别）：饿汉式是只要`Singleton`类被装载了，那么`instance`就会被实例化（没有达到lazy loading效果），而这种方式是`Singleton`类被装载了，`instance`不一定被初始化。因为`SingletonHolder`类没有被主动使用，只有显示通过调用`getInstance`方法时，才会显示装载`SingletonHolder`类，从而实例化`instance`。想象一下，如果实例化`instance`很消耗资源，我想让他延迟加载，另外一方面，我不希望在`Singleton`类加载时就实例化，因为我不能确保`Singleton`类还可能在其他的地方被主动使用从而被加载，那么这个时候实例化`instance`显然是不合适的。这个时候，这种方式相比饿汉式更加合理。

## 3.3 懒汉式

下面看另外一种在该对象真正被使用的时候才会实例化的单例模式——懒汉模式。

```java
//code 5
public class Singleton {
    //定义实例
    private static Singleton instance;
    //私有构造方法
    private Singleton(){}
    //对外提供获取实例的静态方法
    public static Singleton getInstance() {
        //在对象被使用的时候才实例化
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

上面这种单例叫做懒汉式单例。懒汉，就是不会提前把实例创建出来，将类对自己的实例化延迟到第一次被引用的时候。`getInstance`方法的作用是希望该对象在第一次被使用的时候被`new`出来。

有没有发现，其实code 5这种懒汉式单例其实还存在一个问题，那就是**线程安全问题**。在多线程情况下，有可能两个线程同时进入`if`语句中，这样，在两个线程都从if中退出的时候就创建了两个不一样的对象。（这里就不详细讲解了，不理解的请恶补多线程知识）。

## 3.4 线程安全的懒汉式

针对线程不安全的懒汉式的单例，其实解决方式很简单，就是给创建对象的步骤加锁：

```java
//code 6
public class SynchronizedSingleton {
    //定义实例
    private static SynchronizedSingleton instance;
    //私有构造方法
    private SynchronizedSingleton(){}
    //对外提供获取实例的静态方法,对该方法加锁
    public static synchronized SynchronizedSingleton getInstance() {
        //在对象被使用的时候才实例化
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }
}
```

这种写法能够在多线程中很好的工作，而且看起来它也具备很好的延迟加载，但是，遗憾的是，他效率很低，因为99%情况下不需要同步。（因为上面的`synchronized`的加锁范围是整个方法，该方法的所有操作都是同步进行的，但是对于非第一次创建对象的情况，也就是没有进入`if`语句中的情况，根本不需要同步操作，可以直接返回`instance`。）

## 3.5 双重校验锁

针对上面code 6存在的问题，相信对并发编程了解的同学都知道如何解决。其实上面的代码存在的问题主要是锁的范围太大了。只要缩小锁的范围就可以了。那么如何缩小锁的范围呢？相比于同步方法，同步代码块的加锁范围更小。code 6可以改造成：

```java
//code 7
public class Singleton {

    private static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

code 7是对于code 6的一种改进写法，通过使用同步代码块的方式减小了锁的范围。这样可以大大提高效率。（对于已经存在`singleton`的情况，无须同步，直接return）。

但是，事情这的有这么容易吗？上面的代码看上去好像是没有任何问题。实现了惰性初始化，解决了同步问题，还减小了锁的范围，提高了效率。但是，该代码还存在隐患。隐患的原因主要和[Java内存模型（JMM](http://www.hollischuang.com/archives/1003)）有关。考虑下面的事件序列：

> 线程A发现变量没有被初始化, 然后它获取锁并开始变量的初始化。
>
> 由于某些编程语言的语义，编译器生成的代码允许在线程A执行完变量的初始化之前，更新变量并将其指向部分初始化的对象。
>
> 线程B发现共享变量已经被初始化，并返回变量。由于线程B确信变量已被初始化，它没有获取锁。如果在A完成初始化之前共享变量对B可见（这是由于A没有完成初始化或者因为一些初始化的值还没有穿过B使用的内存(缓存一致性)），程序很可能会崩溃。

（上面的例子不太能理解的同学，请恶补JAVA内存模型相关知识）

在[J2SE 1.4](https://zh.wikipedia.org/wiki/Java_SE)或更早的版本中使用双重检查锁有潜在的危险，有时会正常工作（区分正确实现和有小问题的实现是很困难的。取决于编译器，线程的调度和其他并发系统活动，不正确的实现双重检查锁导致的异常结果可能会间歇性出现。重现异常是十分困难的。） 在[J2SE 5.0](https://zh.wikipedia.org/wiki/Java_SE)中，这一问题被修正了。[volatile](https://zh.wikipedia.org/wiki/Volatile%E5%8F%98%E9%87%8F)关键字保证多个线程可以正确处理单件实例

所以，针对code 7 ，可以有code 8 和code 9两种替代方案：

### 3.5.1 使用`volatile`

```java
//code 8
public class VolatileSingleton {
    private static volatile VolatileSingleton singleton;

    private VolatileSingleton() {
    }

    public static VolatileSingleton getSingleton() {
        if (singleton == null) {
            synchronized (VolatileSingleton.class) {
                if (singleton == null) {
                    singleton = new VolatileSingleton();
                }
            }
        }
        return singleton;
    }
}
```

**上面这种双重校验锁的方式用的比较广泛，他解决了前面提到的所有问题。**

但是，即使是这种看上去完美无缺的方式也可能存在问题，那就是遇到序列化的时候。详细内容后文介绍。

### 3.5.2  使用`final`

```java
//code 9
class FinalWrapper<T> {
    public final T value;

    public FinalWrapper(T value) {
        this.value = value;
    }
}

public class FinalSingleton {
    private FinalWrapper<FinalSingleton> helperWrapper = null;

    public FinalSingleton getHelper() {
        FinalWrapper<FinalSingleton> wrapper = helperWrapper;

        if (wrapper == null) {
            synchronized (this) {
                if (helperWrapper == null) {
                    helperWrapper = new FinalWrapper<FinalSingleton>(new FinalSingleton());
                }
                wrapper = helperWrapper;
            }
        }
        return wrapper.value;
    }
}
```

## 3.6 枚举式

在1.5之前，实现单例一般只有以上几种办法，在1.5之后，还有另外一种实现单例的方式，那就是使用枚举：

```java
// code 10
public enum  Singleton {

    INSTANCE;
    Singleton() {
    }
}
```

这种方式是[Effective Java](http://s.click.taobao.com/t?e=m=2&s=ix/dAcrx42AcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67vbkYfk%2bHavVTHm2guh0YLtpS4hLH/P02ckKYNRBWOBBey11vvWwHXSniyi5vWXIZtVr9sOV2MxmP1RxEmSieVPs8Gq%2bZDw%2bWcYMXU3NNCg/&pvid=10_42.120.73.203_425_1459957079215)作者`Josh Bloch` 提倡的方式，它不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象（下面会介绍），可谓是很坚强的壁垒啊，在深度分析Java的枚举类型—-枚举的线程安全性及序列化问题中有详细介绍枚举的线程安全问题和序列化问题，不过，个人认为由于1.5中才加入`enum`特性，用这种方式写不免让人感觉生疏，在实际工作中，我也很少看见有人这么写过，但是不代表他不好。

# 4. 单例与序列化

在[单例与序列化的那些事儿](http://www.hollischuang.com/archives/1144)一文中，[Hollis](http://www.hollischuang.com/)就分析过单例和序列化之前的关系——序列化可以破坏单例。要想防止序列化对单例的破坏，只要在`Singleton`类中定义`readResolve`就可以解决该问题：

```java
//code 11
package com.hollis;
import java.io.Serializable;
/**
 * Created by hollis on 16/2/5.
 * 使用双重校验锁方式实现单例
 */
public class Singleton implements Serializable{
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    private Object readResolve() {
        return singleton;
    }
}
```

# 5. 总结

本文中介绍了几种实现单例的方法，主要包括饿汉、懒汉、使用静态内部类、双重校验锁、枚举等。还介绍了如何防止序列化破坏类的单例性。

从单例的实现中，我们可以发现，一个简单的单例模式就能涉及到这么多知识。在不断完善的过程中可以了解并运用到更多的知识。所谓学无止境。

**文中所有代码见GitHub**

# 6. 参考资料

[单例模式的七种写法](http://www.hollischuang.com/archives/205)

[双重检查锁定模式](https://zh.wikipedia.org/wiki/%E5%8F%8C%E9%87%8D%E6%A3%80%E6%9F%A5%E9%94%81%E5%AE%9A%E6%A8%A1%E5%BC%8F)

[深入浅出设计模式](http://s.click.taobao.com/t?e=m%3D2%26s%3Detkt7EP2O5scQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67nWAf3rZA5A%2FrumJQoe%2FxcNpS4hLH%2FP02ckKYNRBWOBBey11vvWwHXTpkOAWGyim%2Bw2PNKvM2u52N5aP5%2Bgx7zgh4LxdBQDQSXEqY%2Bakgpmw&pvid=10_42.120.73.203_1238_1459955035603)

[单例模式](http://www.runoob.com/design-pattern/singleton-pattern.html)

# 7. [JDK中的那些单例](https://www.hollischuang.com/archives/1383)

在[设计模式（二）——单例模式](http://www.hollischuang.com/archives/1373)中介绍了单例的概念、用途、实现方式、如何防止被序列化破坏等。单例模式在JDK源码中也有多处应用。本文通过JDK（java 8）中几个典型的单例的使用来复习一下单例模式，并且通过这种实际应用来深入理解一下单例的用法与实现方式。

## 7.1 java.lang.Runtime

`Runtime`类封装了Java运行时的环境。每一个java程序实际上都是启动了一个JVM进程，那么每个JVM进程都是对应这一个Runtime实例，此实例是由JVM为其实例化的。每个 Java 应用程序都有一个 Runtime 类实例，使应用程序能够与其运行的环境相连接。

由于Java是单进程的，所以，在一个JVM中，Runtime的实例应该只有一个。所以应该使用单例来实现。

```java
public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    public static Runtime getRuntime() {
        return currentRuntime;
    }

    private Runtime() {}
}
```

以上代码为JDK中`Runtime`类的部分实现，可以看到，这其实是饿汉式单例模式。在该类第一次被classloader加载的时候，这个实例就被创建出来了。

> 一般不能实例化一个`Runtime`对象，应用程序也不能创建自己的 Runtime 类实例，但可以通过 `getRuntime` 方法获取当前`Runtime`运行时对象的引用。

## 7.2 GUI中的单例

除了`Runtime`是典型的单例以外。JDK中还有几个类是单例的，他们都是GUI中的类。这几个单例的类和`Runtime`最大的区别就在于他们并不是饿汉模式，也就是他们都是惰性初始化的懒汉单例。如果分析其原因的话也比较简单：那就是他们并不需要事先创建好，只要在第一次真正用到的时候再创建就可以了。因为很多时候我们并不是用Java的GUI和其中的对象。如果使用饿汉单例的话会影响JVM的启动速度。

由于Java的强项并不是做GUI，所以这几个类其实并不会经常被用到。笔者也没用过。把代码贴到这里，从单例的实现的角度简单分析一下。

### 7.2.1 java.awt.Toolkit#getDefaultToolkit()

```java
public abstract class Toolkit {
    /**
     * The default toolkit.
     */
    private static Toolkit toolkit;

     public static synchronized Toolkit getDefaultToolkit() {
            if (toolkit == null) {
                java.security.AccessController.doPrivileged(
                        new java.security.PrivilegedAction<Void>() {
                    public Void run() {
                        Class<?> cls = null;
                        String nm = System.getProperty("awt.toolkit");
                        try {
                            cls = Class.forName(nm);
                        } catch (ClassNotFoundException e) {
                            ClassLoader cl = ClassLoader.getSystemClassLoader();
                            if (cl != null) {
                                try {
                                    cls = cl.loadClass(nm);
                                } catch (final ClassNotFoundException ignored) {
                                    throw new AWTError("Toolkit not found: " + nm);
                                }
                            }
                        }
                        try {
                            if (cls != null) {
                                toolkit = (Toolkit)cls.newInstance();
                                if (GraphicsEnvironment.isHeadless()) {
                                    toolkit = new HeadlessToolkit(toolkit);
                                }
                            }
                        } catch (final InstantiationException ignored) {
                            throw new AWTError("Could not instantiate Toolkit: " + nm);
                        } catch (final IllegalAccessException ignored) {
                            throw new AWTError("Could not access Toolkit: " + nm);
                        }
                        return null;
                    }
                });
                loadAssistiveTechnologies();
            }
            return toolkit;
        }
    }
```

上面的代码是`Toolkit`类的单例实现。这里类加载时只静态声明了私有`toolkit`并没有创建`Toolkit`实例对象，延迟加载加快了JVM启动速度。

> 单例模式作为一种创建模式，这里在依赖加载的时候应用了另一种创建对象的方式，不是`new`新的对象，因为`Toolkit`本身是个抽象类不能实例化对象，而是通过反射机制加载类并创建新的实例。

###7.2.2 java.awt.GraphicsEnvironment#getLocalGraphicsEnvironment()

```java
public abstract class GraphicsEnvironment {
    private static GraphicsEnvironment localEnv;
    public static synchronized GraphicsEnvironment getLocalGraphicsEnvironment() {
        if (localEnv == null) {
            localEnv = createGE();
        }

        return localEnv;
    }
}
```

这里类加载时只静态声明了私有`localEnv`并没有创建实例对象。在`GraphicsEnvironment`类被第一次调用时会创建该对象。这里没有贴出的`createGE()`方法也是通过[反射](http://www.hollischuang.com/archives/1163)的方式创建对象的。

### 7.2.3 java.awt.Desktop#getDesktop()

```java
public class Desktop {

    public static synchronized Desktop getDesktop(){
        if (GraphicsEnvironment.isHeadless()) throw new HeadlessException();
        if (!Desktop.isDesktopSupported()) {
            throw new UnsupportedOperationException("Desktop API is not " +
                                                    "supported on the current platform");
        }

        sun.awt.AppContext context = sun.awt.AppContext.getAppContext();
        Desktop desktop = (Desktop)context.get(Desktop.class);

        if (desktop == null) {
            desktop = new Desktop();
            context.put(Desktop.class, desktop);
        }

        return desktop;
    }
}
```

上面的代码看上去和单例不太一样。但是实际上也是线程安全的懒汉式单例。获取对象的时候先去环境容器中查找是否存在，不存在实例则创建一个实例。

> 以上三个类的获取实例的方法都通过同步方法的方式保证了线程安全。
>
> Runtime类是通过静态初始化的方式保证其线程安全的。

## 7.3 总结

文中介绍了四个单例的例子，其中有一个是饿汉式单例，三个是懒汉式单例。通过JDK中的实际应用我们可以得出以下结论：

> 当一个类的对象只需要或者只可能有一个时，应该考虑单例模式。
>
> 如果一个类的实例应该在JVM初始化时被创建出来，应该考虑使用饿汉式单例。
>
> 如果一个类的实例不需要预先被创建，也许这个类的实例并不一定能用得上，也许这个类的实例创建过程比较耗费时间，也许就是真的没必须提前创建。那么应该考虑懒汉式单例。
>
> 在使用懒汉式单例的时候，应该考虑到线程的安全性问题。

## 7.4 参考资料

[设计模式（二）——单例模式](http://www.hollischuang.com/archives/1373)

[openjdk 8u40-b25](http://grepcode.com/snapshot/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/)



















