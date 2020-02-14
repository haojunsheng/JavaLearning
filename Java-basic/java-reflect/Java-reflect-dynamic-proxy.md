[TOC]

# 前言

与[javax.lang.model](http://download.oracle.com/javase/6/docs/api/javax/lang/model/package-summary.html)不同的是，通过反射 API 可以获取程序在运行时刻的内部结构。反射 API 中提供的动态代理也是非常强大的功能，可以原生实现[AOP](http://en.wikipedia.org/wiki/Aspect-oriented_programming)中 的方法拦截功能。正如英文单词 reflection 的含义一样，使用反射 API 的时候就好像在看一个 Java 类在水中的倒影一样。知道了 Java 类的内部 结构之后，就可以与它进行交互，包括创建新的对象和调用对象中的方法等。这种交互方式与直接在源代码中使用的效果是相同的，但是又额外提供了运行时刻的灵活性。使用反射的一个最大的弊端是[性能比较差](http://stackoverflow.com/questions/435553/java-reflection-performance)。相同的操作，用反射 API 所需的时间大概比直接的使用要慢一两个数量级。不过现在的 JVM 实现中，反射操作的性能已经有了[很大的提升](http://java.sun.com/j2se/1.4.2/performance.guide.html)。在灵活性与性能之间，总是需要进行权衡的。应用可以在适当的时机来使用反射 API。

# 1.基本用法

Java 反射 API 的**第一个主要作用是获取程序在运行时刻的内部结构**。这对于程序的检查工具和调试器来说，是非常实用的功能。只需要短短的十几行代码，就可以遍历出来一个 Java 类的内部结构，包括其中的构造方法、声明的域和定义的方法等。这不得不说是一个很强大的能力。只要有了 java.lang.Class 类 的对象，就可以通过其中的方法来获取到该类中的构造方法、域和方法。对应的方法分别是[getConstructor](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getConstructor%28java.lang.Class...%29)、[getField](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getField%28java.lang.String%29)和[getMethod](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getMethod%28java.lang.String,%20java.lang.Class...%29)。这三个方法还有相应的 getDeclaredXXX 版本，区别在**于 getDeclaredXXX 版本的方法只会获取该类自身所声明的元素，而不会考虑继承下来的。**[Constructor](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Constructor.html)、[Field](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Field.html)和[Method](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Method.html)这三个类分别表示类中的构造方法、域和方法。这些类中的方法可以获取到所对应结构的元数据。

**反射 API 的另外一个作用是在运行时刻对一个 Java 对象进行操作**。 **这些操作包括动态创建一个 Java 类的对象，获取某个域的值以及调用某个方法。**在 Java 源代码中编写的对类和对象的操作，都可以在运行时刻通过反射 API 来实现。考虑下面一个简单的 Java 类。

```java
class MyClass {
    public int count;
    public MyClass(int start) {
        count = start;
    }
    public void increase(int step) {
        count = count + step;
    }
} 
```

使用一般做法和反射 API 都非常简单。

```java
MyClass myClass = new MyClass(0); // 一般做法 
myClass.increase(2);
System.out.println("Normal -> " + myClass.count);
try {
    Constructor constructor = MyClass.class.getConstructor(int.class); // 获取构造方法

    MyClass myClassReflect = constructor.newInstance(10); // 创建对象

    Method method = MyClass.class.getMethod("increase", int.class);  // 获取方法

    method.invoke(myClassReflect, 5); // 调用方法

    Field field = MyClass.class.getField("count"); // 获取域

    System.out.println("Reflect -> " + field.getInt(myClassReflect)); // 获取域的值

} catch (Exception e) { 

    e.printStackTrace();

} 
```

由于数组的特殊性，[Array](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Array.html)类提供了一系列的静态方法用来创建数组和对数组中的元素进行访问和操作。

```java
Object array = Array.newInstance(String.class, 10); // 等价于 new String[10]
Array.set(array, 0, "Hello");  // 等价于 array[0] = "Hello"
Array.set(array, 1, "World");  // 等价于 array[1] = "World"
System.out.println(Array.get(array, 0));  // 等价于 array[0]
```

使用 Java 反射 API 的时候可以绕过 Java 默认的访问控制检查，比如可以直接获取到对象的私有域的值或是调用私有方法。只需要在获取到 Constructor、Field 和 Method 类的对象之后，调用[setAccessible](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible%28boolean%29)方法并设为 true 即可。有了这种机制，就可以很方便的在运行时刻获取到程序的内部状态。

# 2.处理泛型

Java 5 中引入了泛型的概念之后，Java 反射 API 也做了相应的修改，以提供对泛型的支持。由于类型擦除机制的存在，泛型类中的类型参数等信息，在运行时刻是不存在的。JVM 看到的都是原始类型。对此，Java 5 对 Java 类文件的格式做了[修订](http://java.sun.com/docs/books/jvms/second_edition/ClassFileFormat-Java5.pdf)，添加了 Signature 属性，用来包含不在 JVM 类型系统中的类型信息。比如以 java.util.List 接口为例，在其类文件中的 Signature 属性的声明是 <E:Ljava/lang/Object;>Ljava/lang/Object;Ljava/util/Collection<TE;>;; ，这就说明 List 接口有一个类型参数 E。在运行时刻，JVM 会读取 Signature 属性的内容并提供给反射 API 来使用。

比如在代码中声明了一个域是 List<String> 类型的，虽然在运行时刻其类型会变成原始类型 List，但是仍然可以通过反射来获取到所用的实际的类型参数。

```java
Field field = Pair.class.getDeclaredField("myList"); //myList 的类型是 List 
Type type = field.getGenericType(); 
if (type instanceof ParameterizedType) {     
    ParameterizedType paramType = (ParameterizedType) type;     
    Type[] actualTypes = paramType.getActualTypeArguments();     
    for (Type aType : actualTypes) {         
        if (aType instanceof Class) {         
            Class clz = (Class) aType;             
            System.out.println(clz.getName()); // 输出 java.lang.String         
        }     
    } 
}  
```

# 3.动态代理

熟悉设计模式的人对于[代理模式](http://sourcemaking.com/design_patterns/proxy)可 能都不陌生。 代理对象和被代理对象一般实现相同的接口，调用者与代理对象进行交互。代理的存在对于调用者来说是透明的，调用者看到的只是接口。代理对象则可以封装一些内部的处理逻辑，如访问控制、远程通信、日志、缓存等。比如一个对象访问代理就可以在普通的访问机制之上添加缓存的支持。这种模式在[RMI](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136424.html)和[EJB](http://www.oracle.com/technetwork/java/javaee/ejb/index.html)中都得到了广泛的使用。传统的代理模式的实现，需要在源代码中添加一些附加的类。这些类一般是手写或是通过工具来自动生成。JDK 5 引入的动态代理机制，允许开发人员在运行时刻动态的创建出代理类及其对象。在运行时刻，可以动态创建出一个实现了多个接口的代理类。每个代理类的对象都会关联一个表示内部处理逻辑的[InvocationHandler](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/InvocationHandler.html)接 口的实现。当使用者调用了代理对象所代理的接口中的方法的时候，这个调用的信息会被传递给 InvocationHandler 的 invoke 方法。在 invoke 方法的参数中可以获取到代理对象、方法对应的 Method 对象和调用的实际参数。invoke 方法的返回值被返回给使用者。这种做法实际上相 当于对方法调用进行了拦截。熟悉 AOP 的人对这种使用模式应该不陌生。但是这种方式不需要依赖[AspectJ](http://www.eclipse.org/aspectj/)等 AOP 框架。

下面的代码用来代理一个实现了 List 接口的对象。所实现的功能也非常简单，那就是禁止使用 List 接口中的 add 方法。如果在 getList 中传入一个实现 List 接口的对象，那么返回的实际就是一个代理对象，尝试在该对象上调用 add 方法就会抛出来异常。

```java
public List getList(final List list) {
    return (List) Proxy.newProxyInstance(DummyProxy.class.getClassLoader(), new Class[] { List.class },
        new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("add".equals(method.getName())) {
                    throw new UnsupportedOperationException();
                }
                else {
                    return method.invoke(list, args);
                }
            }
        });
 } 
```

这里的实际流程是，当代理对象的 add 方法被调用的时候，InvocationHandler 中的 invoke 方法会被调用。参数 method 就包含了调用的基本信息。因为方法名称是 add，所以会抛出相关的异常。如果调用的是其它方法的话，则执行原来的逻辑。

# 4. 使用案例

Java 反射 API 的存在，为 Java 语言添加了一定程度上的动态性，可以实现某些动态语言中的功能。比如在 JavaScript 的代码中，可以通过 obj["set" + propName]() 来根据变量 propName 的值找到对应的方法进行调用。虽然在 Java 源代码中不能这么写，但是通过反射 API 同样可以实现类似 的功能。这对于处理某些遗留代码来说是有帮助的。比如所需要使用的类有多个版本，每个版本所提供的方法名称和参数不尽相同。而调用代码又必须与这些不同的版本都能协同工作，就可以通过反射 API 来依次检查实际的类中是否包含某个方法来选择性的调用。

Java 反射 API 实际上定义了一种相对于编译时刻而言更加松散的契约。如果被调用的 Java 对象中并不包含某个方法，而在调用者代码中进行引用的话，在编译时刻就会出现错误。而反射 API 则可以把这样的检查推迟到运行时刻来完成。通过把 Java 中的字节代码增强、类加载器和反射 API 结合起来，可以处理一些对灵 活性要求很高的场景。

在 有些情况下，可能会需要从远端加载一个 Java 类来执行。比如一个客户端 Java 程序可以通过网络从服务器端下载 Java 类来执行，从而可以实现自动更新 的机制。当代码逻辑需要更新的时候，只需要部署一个新的 Java 类到服务器端即可。一般的做法是通过自定义类加载器下载了类字节代码之后，定义出 Class 类的对象，再通过 newInstance 方法就可以创建出实例了。不过这种做法要求客户端和服务器端都具有某个接口的定义，从服务器端下载的是 这个接口的实现。这样的话才能在客户端进行所需的类型转换，并通过接口来使用这个对象实例。如果希望客户端和服务器端采用更加松散的契约的话，使用反射 API 就可以了。两者之间的契约只需要在方法的名称和参数这个级别就足够了。服务器端 Java 类并不需要实现特定的接口，可以是一般的 Java 类。

动态代理的使用场景就更加广泛了。需要使用 AOP 中的方法拦截功能的地方都可以用到动态代理。Spring 框架的[AOP 实现](http://static.springsource.org/spring/docs/2.5.x/reference/aop.html)默认也使用动态代理。不过 JDK 中的动态代理只支持对接口的代理，不能对一个普通的 Java 类提供代理。不过这种实现在大部分的时候已经够用了。

# 5. 参考资料

- [Classworking toolkit: Reflecting  generics](http://www.ibm.com/developerworks/library/j-cwt11085.html)
- [D?ecorating with dynamic proxies](http://www.ibm.com/developerworks/java/library/j-jtp08305.html)