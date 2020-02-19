<!--ts-->
   * [前言](#前言)
   * [1. 定义](#1-定义)
      * [1.1 抽象类](#11-抽象类)
      * [1.2 实现Comparable和Serializable接口](#12-实现comparable和serializable接口)
      * [1.3 泛型：&lt;E extends Enum&lt;E&gt;&gt;](#13-泛型e-extends-enume)
   * [2. 成员变量](#2-成员变量)
   * [3. 构造函数](#3-构造函数)
   * [4. 其他方法](#4-其他方法)
      * [参考资料：](#参考资料)

<!-- Added by: anapodoton, at: Mon Feb 17 10:08:17 CST 2020 -->

<!--te-->

# 前言

> Enum类是java.lang包中一个类，他是Java语言中所有枚举类型的公共基类。

# 1. 定义

```java
public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable
```

## 1.1 抽象类

首先，**抽象类不能被实例化**，所以我们**在java程序中不能使用new关键字来声明一个Enum**，如果想要定义可以使用这样的语法：

```java
enum enumName{
    value1,value2
    method1(){}
    method2(){}
}
```

其次，看到抽象类，第一印象是肯定有类继承他。至少我们应该是可以继承他的，所以：

```java
/**
 * @author hollis
 */
public class testEnum extends Enum{
}
public class testEnum extends Enum<Enum<E>>{
}
public class testEnum<E> extends Enum<Enum<E>>{
}
```

尝试了以上三种方式之后，得出以下结论：**Enum类无法被继承**。

为什么一个抽象类不让继承？enum定义的枚举是怎么来的？难道不是对Enum的一种继承吗？带着这些疑问我们来反编译以下代码：

```java
  enum Color {RED, BLUE, GREEN}
```

编译器将会把他转成如下内容：

```java
/**
 * @author hollis
 */
public final class Color extends Enum<Color> {
  public static final Color[] values() { return (Color[])$VALUES.clone(); }
  public static Color valueOf(String name) { ... }

  private Color(String s, int i) { super(s, i); }

  public static final Color RED;
  public static final Color BLUE;
  public static final Color GREEN;

  private static final Color $VALUES[];

  static {
    RED = new Color("RED", 0);
    BLUE = new Color("BLUE", 1);
    GREEN = new Color("GREEN", 2);
    $VALUES = (new Color[] { RED, BLUE, GREEN });
  }
} 
```

短短的一行代码，被编译器处理过之后竟然变得这么多，看来，enmu关键字是java提供给我们的一个语法糖啊。。。从反编译之后的代码中，我们发现，编译器不让我们继承Enum，但是当我们使用enum关键字定义一个枚举的时候，他会帮我们在编译后默认继承java.lang.Enum类，而不像其他的类一样默认继承Object类。且采用enum声明后，该类会被编译器加上final声明，故该类是无法继承的。 PS：由于JVM类初始化是线程安全的，所以可以采用枚举类实现一个线程安全的单例模式。

## 1.2 实现`Comparable`和`Serializable`接口

Enum实现了Serializable接口，可以序列化。 Enum实现了Comparable接口，可以进行比较，默认情况下，只有同类型的enum才进行比较（原因见后文），要实现不同类型的enum之间的比较，只能复写compareTo方法。##

## 1.3 泛型：`<E extends Enum<E>>`

**怎么理解<E extends Enum<E>>？**

> 首先，这样写只是为了让Java的API更有弹性，他主要是限定形态参数实例化的对象，故要求只能是Enum，这样才能对 compareTo 之类的方法所传入的参数进行形态检查。所以，**我们完全可以不必去关心他为什么这么设计。**

这里倒是可以关注一下[泛型中extends的用法](http://www.hollischuang.com/archives/255)，以及[`K` `V` `O` `T` `E` `？` `object`这几个符号之间的区别](http://www.hollischuang.com/archives/252)。

**好啦，我们回到这个令人实在是无法理解的<E extends Enum<E>>**

**首先我们先来“翻译”一下这个Enum<E extends Enum<E>>到底什么意思，**然后再来解释为什么Java要这么用。 我们先看一个比较常见的泛型：`List<String>`。这个泛型的意思是，List中存的都是String类型，告诉编译器要接受String类型，并且从List中取出内容的时候也自动帮我们转成String类型。 所以`Enum<E extends Enum<E>>`可以暂时理解为Enum里面的内容都是`E extends Enum<E>`类型。 这里的`E`我们就理解为枚举，extends表示上界，比如： `List<? extends Object>`，List中的内容可以是Object或者扩展自Object的类。这就是extends的含义。 所以，`E extends Enum<E>`表示为一个继承了`Enum<E>`类型的枚举类型。 那么，`Enum<E extends Enum<E>>`就不难理解了，就是一个Enum只接受一个Enum或者他的子类作为参数。相当于把一个子类或者自己当成参数，传入到自身，引起一些特别的语法效果。

**为什么Java要这样定义Enum**

首先我们来科普一下enum，

```java
/**
 * @author hollis
 */
enum Color{
    RED,GREEN,YELLOW
}
enum Season{
    SPRING,SUMMER,WINTER
}
public class EnumTest{
    public static void main(String[] args) {
        System.out.println(Color.RED.ordinal());
        System.out.println(Season.SPRING.ordinal());
    }
}
```

代码中两处输出内容都是 0 ，因为枚举类型的默认的序号都是从零开始的。

要理解这个问题，首先我们来看一个Enum类中的方法（暂时忽略其他成员变量和方法）：

```java
/**
 * @author hollis
 */
public abstract class Enum<E extends Enum<E>> implements Comparable<E>, Serializable {
        private final int ordinal;

        public final int compareTo(E o) {
        Enum other = (Enum)o;
        Enum self = this;
        if (self.getClass() != other.getClass() && // optimization
            self.getDeclaringClass() != other.getDeclaringClass())
            throw new ClassCastException();
        return self.ordinal - other.ordinal;
    }
}
```

首先我们认为Enum的定义中没有使用`Enum<E extends Enum<E>>`，那么compareTo方法就要这样定义（因为没有使用泛型，所以就要使用Object，这也是Java中很多方法常用的方式）：

```
public final int compareTo(Object o) 
```

当我们调用compareTo方法的时候依然传入两个枚举类型，在compareTo方法的实现中，比较两个枚举的过程是先将参数转化成Enum类型，然后再比较他们的序号是否相等。那么我们这样比较：

```java
Color.RED.compareTo(Color.RED);
Color.RED.compareTo(Season.SPRING);
```

如果在`compareTo`方法中不做任何处理的话，那么以上这段代码返回内容将都是true（因为Season.SPRING的序号和Color.RED的序号都是 0 ）。但是，很明显， `Color.RED`和`Season.SPRING`并不相等。

但是Java使用`Enum<E extends Enum<E>>`声明Enum，并且在`compareTo`的中使用`E`作为参数来避免了这种问题。 以上两个条件限制Color.RED只能和Color定义出来的枚举进行比较，当我们试图使用`Color.RED.compareTo(Season.SPRING);`这样的代码是，会报出这样的错误：

```
The method compareTo(Color) in the type Enum<Color> is not applicable for the arguments (Season)
```

他说明，compareTo方法只接受`Enum<Color>`类型。

Java为了限定形态参数实例化的对象，故要求只能是Enum，这样才能对 compareTo之类的方法所传入的参数进行形态检查。 因为“红色”只有和“绿色”比较才有意义，用“红色”和“春天”比较毫无意义，所以，Java用这种方式一劳永逸的保证像compareTo这样的方法可以正常的使用而不用考虑类型。

> PS：在Java中，其实也可以实现“红色”和“春天”比较，因为Enum实现了`Comparable`接口，可以重写compareTo方法来实现不同的enum之间的比较。

# 2. 成员变量

在Enum中，有两个成员变量，一个是名字(name)，一个是序号(ordinal)。 序号是一个枚举常量，表示在枚举中的位置，从0开始，依次递增。

```java
/**
 * @author hollis
 */
private final String name；
public final String name() {
    return name;
}
private final int ordinal;
public final int ordinal() {
    return ordinal;
}
```

# 3. 构造函数

前面我们说过，Enum是一个抽象类，不能被实例化，但是他也有构造函数，从前面我们反编译出来的代码中，我们也发现了Enum的构造函数，在Enum中只有一个保护类型的构造函数：

```java
protected Enum(String name, int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
}
```

文章开头反编译的代码中`private Color(String s, int i) { super(s, i); }`中的`super(s, i);`就是调用Enum中的这个保护类型的构造函数来初始化name和ordinal。

# 4. 其他方法

Enum当中有以下这么几个常用方法，调用方式就是使用`Color.RED.methodName（params...）`的方式调用

```java
public String toString() {
    return name;
}

public final boolean equals(Object other) {
    return this==other;
}

public final int hashCode() {
    return super.hashCode();
}

public final int compareTo(E o) {
    Enum other = (Enum)o;
    Enum self = this;
    if (self.getClass() != other.getClass() && // optimization
        self.getDeclaringClass() != other.getDeclaringClass())
        throw new ClassCastException();
    return self.ordinal - other.ordinal;
}

public final Class<E> getDeclaringClass() {
    Class clazz = getClass();
    Class zuper = clazz.getSuperclass();
    return (zuper == Enum.class) ? clazz : zuper;
}

public static <T extends Enum<T>> T valueOf(Class<T> enumType,String name) {
    T result = enumType.enumConstantDirectory().get(name);
    if (result != null)
        return result;
    if (name == null)
        throw new NullPointerException("Name is null");
    throw new IllegalArgumentException(
        "No enum constant " + enumType.getCanonicalName() + "." + name);
}
```

方法内容都比较简单，平时能使用的就会也不是很多，这里就不详细介绍了。

## 参考资料：

[java.lang.Enum](http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/7u40-b43/java/lang/Enum.java#Enum)

[Java Generics FAQs](http://www.angelikalanger.com/GenericsFAQ/FAQSections/TypeParameters.html)
