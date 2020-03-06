<!--ts-->
   * [class对象](#class对象)
   * [反射](#反射)
      * [反射的作用](#反射的作用)
   * [反射与工厂模式](#反射与工厂模式)
   * [反射与动态代理](#反射与动态代理)
      * [基本用法](#基本用法)
      * [处理泛型](#处理泛型)
      * [动态代理](#动态代理)
      * [使用案例](#使用案例)
      * [<strong>参考资料</strong>](#参考资料)
   * [静态代理与动态代理](#静态代理与动态代理)
      * [1.<strong>代理概念</strong>](#1代理概念)
      * [2. 静态代理](#2-静态代理)
      * [3. 动态代理](#3-动态代理)
         * [Java实现动态代理的大致步骤](#java实现动态代理的大致步骤)
         * [Java 实现动态代理主要涉及哪几个类](#java-实现动态代理主要涉及哪几个类)
         * [动态代理实现](#动态代理实现)
            * [jdk动态代理](#jdk动态代理)
            * [cglib动态代理](#cglib动态代理)
      * [<strong>4、动态代理机制特点</strong>](#4动态代理机制特点)
      * [5、动态代理的优点和美中不足](#5动态代理的优点和美中不足)
      * [AOP](#aop)
   * [动态代理(码农翻身)](#动态代理码农翻身)
      * [前言](#前言)
      * [使用接口来实现](#使用接口来实现)
      * [GCLib实现动态代理](#gclib实现动态代理)

<!-- Added by: anapodoton, at: Thu Mar  5 11:55:52 CST 2020 -->

<!--te-->

# class对象

[深入理解Class对象](https://blog.csdn.net/javazejian/article/details/70768369)

Java的Class类是java反射机制的基础,通过Class类我们可以获得关于一个类的相关信息

Java.lang.Class是一个比较特殊的类，它用于封装被装入到JVM中的类（包括类和接口）的信息。当一个类或接口被装入的JVM时便会产生一个与之关联的java.lang.Class对象，可以通过这个Class对象对被装入类的详细信息进行访问。

虚拟机为每种类型管理一个独一无二的Class对象。也就是说，每个类（型）都有一个Class对象。运行程序时，Java虚拟机(JVM)首先检查是否所要加载的类对应的Class对象是否已经加载。如果没有加载，JVM就会根据类名查找.class文件，并将其Class对象载入。

# 反射

## 反射的作用

在运行时判断任意一个对象所属的类。

在运行时判断任意一个类所具有的成员变量和方法。

在运行时任意调用一个对象的方法

在运行时构造任意一个类的对象

# 反射与工厂模式

[反射与工厂](http://blog.chinaunix.net/uid-29068508-id-4076589.html)

**工厂模式定义**：

百度百科：实例化对象，用工厂方法代替new操作。工厂模式是我们最常用的模式了，著名的Jive论坛 ,就大量使用了工厂模式，工厂模式在Java程序系统可以说是随处可见。因为工厂模式就相当于创建实例对象的new，我们经常要根据类Class生成实例对象，如A a=new A() 工厂模式也是用来创建实例对象的，所以以后new时就要多个心眼，是否可以考虑使用工厂模式，虽然这样做，可能多做一些工作，但会给你系统带来更大的可扩展性和尽量少的修改量。

下面的demo源码在这里下载：http://download.csdn.net/detail/qiyijay/6446729

1.下面我们先写个使用了工厂模式的例子

FactoryDemo1.java

```java
interface Fruit{//我们有水果的这类产品  
       public void produce();//产品要一份有说明书  
   }  
     
   class Apple implements Fruit{//产品一：苹果  
       public Apple(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个苹果");//苹果的说明书  
       }  
   }  
     
   class Orange implements Fruit{//产品二：橘子  
       public Orange(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个橘子");//橘子的说明书  
       }  
   }    
     
   class Factory{//这是一个工厂  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           if(className.equals("apple")){  
               f=new Apple();  
           }else{  
               f=new Orange();  
           }  
           return f;  
       }  
   }  
     
   public class FactoryDemo1 {  
       public static void main(String[] args){  
           String s="apple";//客户说需要一个苹果  
           Fruit f=Factory.getInstance(s);//把客户需求放入工厂开始生产  
           f.produce();//打印说明书  
       }  
   } 
```

 

以上实现的就是一个简单的工厂模式，但是假如我现在的产品增多了是不是在工厂中每一次都要加入判断呢？工厂类会变成这样：

   

```java
class Factory{//这是一个工厂  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           if(className.equals("apple")){  
               f=new Apple();  
           }else if(className.equals("Orange"){  
               f=new Orange();  
           }else if(className.equals("梨子"){  
               ...  
           }  
           else if(className.equals("西瓜"){  
               ...  
           }  
           else if(className.equals("飞机大炮..."){  
               ...  
           }  
           return f;  
       }  
   }  
```

2.接下来我们就将工厂模式与反射机制相结合

复制FactoryDemo1.java命名为FactoryDemo2.java，我们只改Factory这个工厂类，改成如下：

```java
  class Factory{  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           try {  
               f=(Fruit)Class.forName(className).newInstance();  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
           return f;  
       }  
   }  
```

将这时需要将FactoryDemo2.java中main方法的字符串不再是s=“apple”。需要写成包名加类名，我这里包是com.demo2所以我的s="com.demo2.Apple"。

运行看看结果~~，这样就减少了很多判断。但是这种方法之后我们发现每次都要把包名和类名加上也比较麻烦。于是就想到了一个方法，比如用一个带键值对的容器去装它

键装的是它的小名，值装的是它的全名（如苹果：   键 ——“apple”；值——“com.demo2.Apple”）。

3.从上面的例子中引申出的问题。添加一个项目配置文件可以解决上面的问题，同时还能学习到配置文件的好处

1）不懂什么是配置文件？

先去百度一下，简单的理解配置文件就是程序生成的一些文件，里面有一些配置信息，每一次程序启动就会加载配置文件里面的信息，比如一个应用的配置文件里面记录下了它的桌面背景为某张图片，下一次这个应用启动背景就自动成为这张背景，就不必每一次去改桌面背景了。

2）使用Properties类创建和读取配置文件。Properties简单的使用方法

   

```java
Properties pro=new Properties();  
           pro.storeToXML(OutputStream, comment);//创建一个配置文件需要一个输出流对象和节点名称。  
           pro.loadFromXML(InputStream)//读取一个XML文件需要一个输入流对象  
           pro.setProperty(key, value)//创建一个键值对  
           String s=pro.getProperty(key)//取值  
```

3）复制FactoryDemo2.java命名为FactoryDemo3.java 代码修改如下

  

```java
 interface Fruit{  
       public void produce();  
   }  
     
   class Apple implements Fruit{  
       public Apple(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个苹果");  
       }  
   }  
     
   class Orange implements Fruit{  
       public Orange(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个橘子");  
       }  
   }    
     
   class Factory{  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           try {  
               f=(Fruit)Class.forName(className).newInstance();  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
           return f;  
       }  
   }  
     
   class PropertiesOperate{//创建一个配置文件操作类  
       private Properties pro=null;  
       File file=new File("D:"+File.separator+"fruit.properties");//创建一个File对象 地址为：D：/fruit.properties  
       public PropertiesOperate(){  
           this.pro=new Properties();  
           if(file.exists()){//文件如果存在  
               try {  
                   pro.loadFromXML(new FileInputStream(file));//加载配置文件  
               } catch (Exception e) {  
                   e.printStackTrace();  
               }  
           }else{//如果不存在  
               this.save();  
           }  
       }  
       public Properties getPro(){  
           return this.pro;  
       }  
       public void save(){//创建一个新的配置文件  
           this.pro.setProperty("apple", "com.demo3.Apple");//添加文件内容  
           this.pro.setProperty("orange", "com.demo3.Orange");//添加文件内容  
           try {  
               this.pro.storeToXML(new FileOutputStream(this.file), "Fruit");//把文件保存到指定目录，节点为Fruit  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
       }  
   }  
     
   public class FactoryDemo3 {  
       public static void main(String[] args){  
           Properties pro=new PropertiesOperate().getPro();//创建Properties对象  
           Fruit f=Factory.getInstance(pro.getProperty("apple"));//使用getProperty(key)方法获得apple全名  
           f.produce();  
       }  
   }  
```


看看运行结果，是不是一样呢？

4.执行完结果之后大家请看电脑的D盘是否多了一个fruit.properties文件呢?这个就是我们创建出来的配置文件，用记事本打开看看吧

```
   Fruit  
   com.demo3.Apple  
   com.demo3.Orange  
```

​    


相信里面的意义大家一目了然了吧，这其实就是一个xml文件。里面记录的信息就是我们所创建的信息。假如我们把它这个文件改一下，将其中一句

   com.demo3.Apple  
   改成：  
   com.demo3.Orange  

这时再一次执行程序试试。没错，执行结果为：

橘子生产了
这是一个橘子

我们现在没有修改任何的程序代码，只修改了它的配置文件就将其结果改变了，这时大家能体会到配置文件的强大及好处了吧。

5.知识点：1）熟悉工厂模式的原理，2）使用反射机制创建对象与工厂模式想结合，3）配置文件的创建与使用。

总结：上面的例子是用一个简单的程序去实验，也显得很繁琐，但是当项目越来越大的时候，这样的模式对程序的扩展和优化都起到非常大的作用。

# 反射与动态代理

在[上一篇文章](http://www.infoq.com/cn/articles/cf-java-annotation)中介绍Java 注解的时候，多次提到了Java 的反射API。与[ javax.lang.model ](http://download.oracle.com/javase/6/docs/api/javax/lang/model/package-summary.html)不同的是，通过反射 API 可以获取程序在运行时刻的内部结构。反射 API 中提供的动态代理也是非常强大的功能，可以原生实现[ AOP ](http://en.wikipedia.org/wiki/Aspect-oriented_programming)中 的方法拦截功能。正如英文单词 reflection 的含义一样，使用反射 API 的时候就好像在看一个 Java 类在水中的倒影一样。知道了 Java 类的内部 结构之后，就可以与它进行交互，包括创建新的对象和调用对象中的方法等。这种交互方式与直接在源代码中使用的效果是相同的，但是又额外提供了运行时刻的灵活性。使用反射的一个最大的弊端是[性能比较差](http://stackoverflow.com/questions/435553/java-reflection-performance)。相同的操作，用反射API 所需的时间大概比直接的使用要慢一两个数量级。不过现在的JVM 实现中，反射操作的性能已经有了[很大的提升](http://java.sun.com/j2se/1.4.2/performance.guide.html)。在灵活性与性能之间，总是需要进行权衡的。应用可以在适当的时机来使用反射API。

## 基本用法

Java 反射 API 的第一个主要作用是获取程序在运行时刻的内部结构。这对于程序的检查工具和调试器来说，是非常实用的功能。只需要短短的十几行代码，就可以遍历出来一个 Java 类的内部结构，包括其中的构造方法、声明的域和定义的方法等。这不得不说是一个很强大的能力。只要有了 java.lang.Class 类 的对象，就可以通过其中的方法来获取到该类中的构造方法、域和方法。对应的方法分别是[ getConstructor ](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getConstructor(java.lang.Class...))、[ getField ](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getField(java.lang.String))和[ getMethod ](http://download.oracle.com/javase/6/docs/api/java/lang/Class.html#getMethod(java.lang.String, java.lang.Class...))。这三个方法还有相应的 getDeclaredXXX 版本，区别在于 getDeclaredXXX 版本的方法只会获取该类自身所声明的元素，而不会考虑继承下来的。[ Constructor ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Constructor.html)、[ Field ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Field.html)和[ Method ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Method.html)这三个类分别表示类中的构造方法、域和方法。这些类中的方法可以获取到所对应结构的元数据。

反射 API 的另外一个作用是在运行时刻对一个 Java 对象进行操作。 这些操作包括动态创建一个 Java 类的对象，获取某个域的值以及调用某个方法。在 Java 源代码中编写的对类和对象的操作，都可以在运行时刻通过反射 API 来实现。考虑下面一个简单的 Java 类。

```
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

```
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

由于数组的特殊性，[ Array ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/Array.html)类提供了一系列的静态方法用来创建数组和对数组中的元素进行访问和操作。

```
Object array = Array.newInstance(String.class, 10); // 等价于 new String[10]
Array.set(array, 0, "Hello");  // 等价于 array[0] = "Hello"
Array.set(array, 1, "World");  // 等价于 array[1] = "World"
System.out.println(Array.get(array, 0));  // 等价于 array[0]
```

使用 Java 反射 API 的时候可以绕过 Java 默认的访问控制检查，比如可以直接获取到对象的私有域的值或是调用私有方法。只需要在获取到 Constructor、Field 和 Method 类的对象之后，调用[ setAccessible ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/AccessibleObject.html#setAccessible(boolean))方法并设为 true 即可。有了这种机制，就可以很方便的在运行时刻获取到程序的内部状态。

## 处理泛型

Java 5 中引入了泛型的概念之后，Java 反射 API 也做了相应的修改，以提供对泛型的支持。由于类型擦除机制的存在，泛型类中的类型参数等信息，在运行时刻是不存在的。JVM 看到的都是原始类型。对此，Java 5 对 Java 类文件的格式做了[修订](http://java.sun.com/docs/books/jvms/second_edition/ClassFileFormat-Java5.pdf)，添加了Signature 属性，用来包含不在JVM 类型系统中的类型信息。比如以java.util.List 接口为例，在其类文件中的Signature 属性的声明是<E:Ljava/lang/Object;>Ljava/lang/Object;Ljava/util/Collection<TE;>;; ，这就说明List 接口有一个类型参数E。在运行时刻，JVM 会读取Signature 属性的内容并提供给反射API 来使用。

比如在代码中声明了一个域是 List<String> 类型的，虽然在运行时刻其类型会变成原始类型 List，但是仍然可以通过反射来获取到所用的实际的类型参数。

```
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

## 动态代理

熟悉设计模式的人对于[代理模式](http://sourcemaking.com/design_patterns/proxy)可 能都不陌生。 代理对象和被代理对象一般实现相同的接口，调用者与代理对象进行交互。代理的存在对于调用者来说是透明的，调用者看到的只是接口。代理对象则可以封装一些内部的处理逻辑，如访问控制、远程通信、日志、缓存等。比如一个对象访问代理就可以在普通的访问机制之上添加缓存的支持。这种模式在[ RMI ](http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136424.html)和[ EJB ](http://www.oracle.com/technetwork/java/javaee/ejb/index.html)中都得到了广泛的使用。传统的代理模式的实现，需要在源代码中添加一些附加的类。这些类一般是手写或是通过工具来自动生成。JDK 5 引入的动态代理机制，允许开发人员在运行时刻动态的创建出代理类及其对象。在运行时刻，可以动态创建出一个实现了多个接口的代理类。每个代理类的对象都会关联一个表示内部处理逻辑的[ InvocationHandler ](http://download.oracle.com/javase/6/docs/api/java/lang/reflect/InvocationHandler.html)接 口的实现。当使用者调用了代理对象所代理的接口中的方法的时候，这个调用的信息会被传递给 InvocationHandler 的 invoke 方法。在 invoke 方法的参数中可以获取到代理对象、方法对应的 Method 对象和调用的实际参数。invoke 方法的返回值被返回给使用者。这种做法实际上相 当于对方法调用进行了拦截。熟悉 AOP 的人对这种使用模式应该不陌生。但是这种方式不需要依赖[ AspectJ ](http://www.eclipse.org/aspectj/)等 AOP 框架。

下面的代码用来代理一个实现了 List 接口的对象。所实现的功能也非常简单，那就是禁止使用 List 接口中的 add 方法。如果在 getList 中传入一个实现 List 接口的对象，那么返回的实际就是一个代理对象，尝试在该对象上调用 add 方法就会抛出来异常。

```
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

## 使用案例

Java 反射 API 的存在，为 Java 语言添加了一定程度上的动态性，可以实现某些动态语言中的功能。比如在 JavaScript 的代码中，可以通过 obj["set" + propName]() 来根据变量 propName 的值找到对应的方法进行调用。虽然在 Java 源代码中不能这么写，但是通过反射 API 同样可以实现类似 的功能。这对于处理某些遗留代码来说是有帮助的。比如所需要使用的类有多个版本，每个版本所提供的方法名称和参数不尽相同。而调用代码又必须与这些不同的版本都能协同工作，就可以通过反射 API 来依次检查实际的类中是否包含某个方法来选择性的调用。

Java 反射 API 实际上定义了一种相对于编译时刻而言更加松散的契约。如果被调用的 Java 对象中并不包含某个方法，而在调用者代码中进行引用的话，在编译时刻就会出现错误。而反射 API 则可以把这样的检查推迟到运行时刻来完成。通过把 Java 中的字节代码增强、类加载器和反射 API 结合起来，可以处理一些对灵 活性要求很高的场景。

在 有些情况下，可能会需要从远端加载一个 Java 类来执行。比如一个客户端 Java 程序可以通过网络从服务器端下载 Java 类来执行，从而可以实现自动更新 的机制。当代码逻辑需要更新的时候，只需要部署一个新的 Java 类到服务器端即可。一般的做法是通过自定义类加载器下载了类字节代码之后，定义出 Class 类的对象，再通过 newInstance 方法就可以创建出实例了。不过这种做法要求客户端和服务器端都具有某个接口的定义，从服务器端下载的是 这个接口的实现。这样的话才能在客户端进行所需的类型转换，并通过接口来使用这个对象实例。如果希望客户端和服务器端采用更加松散的契约的话，使用反射 API 就可以了。两者之间的契约只需要在方法的名称和参数这个级别就足够了。服务器端 Java 类并不需要实现特定的接口，可以是一般的 Java 类。

动态代理的使用场景就更加广泛了。需要使用 AOP 中的方法拦截功能的地方都可以用到动态代理。Spring 框架的[ AOP 实现](http://static.springsource.org/spring/docs/2.5.x/reference/aop.html)默认也使用动态代理。不过 JDK 中的动态代理只支持对接口的代理，不能对一个普通的 Java 类提供代理。不过这种实现在大部分的时候已经够用了。

## **参考资料**

- [Classworking toolkit: Reflecting generics](http://www.ibm.com/developerworks/library/j-cwt11085.html)
- [D?ecorating with dynamic proxies](http://www.ibm.com/developerworks/java/library/j-jtp08305.html)

# 静态代理与动态代理

## 1.**代理概念**

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

## 2. 静态代理

由程序员创建或工具生成代理类的源码，再编译代理类。所谓静态也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。所谓静态代理，就是代理类是由程序员自己编写的，在编译期就确定好了的。来看下下面的例子：

```
public interface HelloSerivice {
    public void say();
}

public class HelloSeriviceImpl implements HelloSerivice{

    @Override
    public void say() {
        System.out.println("hello world");
    }
}
```

上面的代码比较简单，定义了一个接口和其实现类。这就是代理模式中的目标对象和目标对象的接口。接下类定义代理对象。

```
public class HelloSeriviceProxy implements HelloSerivice{

    private HelloSerivice target;
    public HelloSeriviceProxy(HelloSerivice target) {
        this.target = target;
    }

    @Override
    public void say() {
        System.out.println("记录日志");
        target.say();
        System.out.println("清理数据");
    }
}
```

上面就是一个代理类，他也实现了目标对象的接口，并且扩展了say方法。下面是一个测试类：

```
public class Main {
    @Test
    public void testProxy(){
        //目标对象
        HelloSerivice target = new HelloSeriviceImpl();
        //代理对象
        HelloSeriviceProxy proxy = new HelloSeriviceProxy(target);
        proxy.say();
    }
}
```

// 记录日志 // hello world // 清理数据

这就是一个简单的静态的代理模式的实现。代理模式中的所有角色（代理对象、目标对象、目标对象的接口）等都是在编译期就确定好的。

静态代理的用途 控制真实对象的访问权限 通过代理对象控制对真实对象的使用权限。

避免创建大对象 通过使用一个代理小对象来代表一个真实的大对象，可以减少系统资源的消耗，对系统进行优化并提高运行速度。

增强真实对象的功能 这个比较简单，通过代理可以在调用真实对象的方法的前后增加额外功能。

**静态代理类优缺点**
优点：业务类只需要关注业务逻辑本身，保证了业务类的重用性。这是代理的共有优点。
缺点：
1）代理对象的一个接口只服务于一种类型的对象，如果要代理的方法很多，势必要为每一种方法都进行代理，静态代理在程序规模稍大时就无法胜任了。

2）如果接口增加一个方法，除了所有实现类需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。

另外，如果要按照上述的方法使用代理模式，那么真实角色(委托类)必须是事先已经存在的，并将其作为代理对象的内部属性。但是实际使用时，一个真实角色必须对应一个代理角色，如果大量使用会导致类的急剧膨胀；此外，如果事先并不知道真实角色（委托类），该如何使用代理呢？这个问题可以通过Java的动态代理类来解决。

## 3. 动态代理

动态代理类的源码是在程序运行期间由JVM根据反射等机制动态的生成，所以不存在代理类的字节码文件。代理类和委托类的关系是在程序运行时确定。

前面介绍了[静态代理](https://github.com/hollischuang/toBeTopJavaer/blob/master/basics/java-basic/static-proxy.md)，虽然静态代理模式很好用，但是静态代理还是存在一些局限性的，比如使用静态代理模式需要程序员手写很多代码，这个过程是比较浪费时间和精力的。一旦需要代理的类中方法比较多，或者需要同时代理多个对象的时候，这无疑会增加很大的复杂度。

有没有一种方法，可以不需要程序员自己手写代理类呢。这就是动态代理啦。

动态代理中的代理类并不要求在编译期就确定，而是可以在运行期动态生成，从而实现对目标对象的代理功能。

反射是动态代理的一种实现方式。

Java中，实现动态代理有两种方式：

1、JDK动态代理：java.lang.reflect 包中的Proxy类和InvocationHandler接口提供了生成动态代理类的能力。

2、Cglib动态代理：Cglib (Code Generation Library )是一个第三方代码生成类库，运行时在内存中动态生成一个子类对象从而实现对目标对象功能的扩展。

关于这两种动态代理的写法本文就不深入展开了，读者感兴趣的话，后面我再写文章单独介绍。本文主要来简单说一下这两种动态代理的区别和用途。

JDK动态代理和Cglib动态代理的区别 JDK的动态代理有一个限制，就是使用动态代理的对象必须实现一个或多个接口。如果想代理没有实现接口的类，就可以使用CGLIB实现。

Cglib是一个强大的高性能的代码生成包，它可以在运行期扩展Java类与实现Java接口。它广泛的被许多AOP的框架使用，例如Spring AOP和dynaop，为他们提供方法的interception（拦截）。

Cglib包的底层是通过使用一个小而快的字节码处理框架ASM，来转换字节码并生成新的类。不鼓励直接使用ASM，因为它需要你对JVM内部结构包括class文件的格式和指令集都很熟悉。

Cglib与动态代理最大的区别就是：

使用动态代理的对象必须实现一个或多个接口

使用cglib代理的对象则无需实现接口，达到代理类无侵入。

### Java实现动态代理的大致步骤

1、定义一个委托类和公共接口。

2、自己定义一个类（调用处理器类，即实现 InvocationHandler 接口），这个类的目的是指定运行时将生成的代理类需要完成的具体任务（包括Preprocess和Postprocess），即代理类调用任何方法都会经过这个调用处理器类（在本文最后一节对此进行解释）。

3、生成代理对象（当然也会生成代理类），需要为他指定(1)委托对象(2)实现的一系列接口(3)调用处理器类的实例。因此可以看出一个代理对象对应一个委托对象，对应一个调用处理器实例。

### Java 实现动态代理主要涉及哪几个类

java.lang.reflect.Proxy: 这是生成代理类的主类，通过 Proxy 类生成的代理类都继承了 Proxy 类，即 DynamicProxyClass extends Proxy。

java.lang.reflect.InvocationHandler: 这里称他为"调用处理器"，他是一个接口，我们动态生成的代理类需要完成的具体内容需要自己定义一个类，而这个类必须实现 InvocationHandler 接口。

### 动态代理实现

使用动态代理实现功能：不改变Test类的情况下，在方法target 之前打印一句话，之后打印一句话。

```
public class UserServiceImpl implements UserService {

    @Override
    public void add() {
        // TODO Auto-generated method stub
        System.out.println("--------------------add----------------------");
    }
}
```

#### jdk动态代理

```
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {

        super();
        this.target = target;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PerformanceMonior.begin(target.getClass().getName()+"."+method.getName());
        //System.out.println("-----------------begin "+method.getName()+"-----------------");
        Object result = method.invoke(target, args);
        //System.out.println("-----------------end "+method.getName()+"-----------------");
        PerformanceMonior.end();
        return result;
    }

    public Object getProxy(){

        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }

}

public static void main(String[] args) {

  UserService service = new UserServiceImpl();
  MyInvocationHandler handler = new MyInvocationHandler(service);
  UserService proxy = (UserService) handler.getProxy();
  proxy.add();
}
```

#### cglib动态代理

```
public class CglibProxy implements MethodInterceptor{  
 private Enhancer enhancer = new Enhancer();  
 public Object getProxy(Class clazz){  
  //设置需要创建子类的类  
  enhancer.setSuperclass(clazz);  
  enhancer.setCallback(this);  
  //通过字节码技术动态创建子类实例  
  return enhancer.create();  
 }  
 //实现MethodInterceptor接口方法  
 public Object intercept(Object obj, Method method, Object[] args,  
   MethodProxy proxy) throws Throwable {  
  System.out.println("前置代理");  
  //通过代理类调用父类中的方法  
  Object result = proxy.invokeSuper(obj, args);  
  System.out.println("后置代理");  
  return result;  
 }  
}  

public class DoCGLib {  
 public static void main(String[] args) {  
  CglibProxy proxy = new CglibProxy();  
  //通过生成子类的方式创建代理类  
  UserServiceImpl proxyImp = (UserServiceImpl)proxy.getProxy(UserServiceImpl.class);  
  proxyImp.add();  
 }  
}
```

## **4、动态代理机制特点**  

首先是动态生成的代理类本身的一些特点。1）包：如果所代理的接口都是 public 的，那么它将被定义在顶层包（即包路径为空），如果所代理的接口中有非 public 的接口（因为接口不能被定义为 protect 或 private，所以除 public 之外就是默认的 package 访问级别），那么它将被定义在该接口所在包（假设代理了 com.ibm.developerworks 包中的某非 public 接口 A，那么新生成的代理类所在的包就是 com.ibm.developerworks），这样设计的目的是为了最大程度的保证动态代理类不会因为包管理的问题而无法被成功定义并访问；2）类修饰符：该代理类具有 final 和 public 修饰符，意味着它可以被所有的类访问，但是不能被再度继承；3）类名：格式是“$ProxyN”，其中 N 是一个逐一递增的阿拉伯数字，代表 Proxy 类第 N 次生成的动态代理类，值得注意的一点是，并不是每次调用 Proxy 的静态方法创建动态代理类都会使得 N 值增加，原因是如果对同一组接口（包括接口排列的顺序相同）试图重复创建动态代理类，它会很聪明地返回先前已经创建好的代理类的类对象，而不会再尝试去创建一个全新的代理类，这样可以节省不必要的代码重复生成，提高了代理类的创建效率。4）类继承关系：该类的继承关系如图：

图2：动态代理类的继承关系
![img](img/775caa62-37c7-35b2-9155-28534991a63d.jpg)
由图可见，Proxy 类是它的父类，这个规则适用于所有由 Proxy 创建的动态代理类。而且该类还实现了其所代理的一组接口，这就是为什么它能够被安全地类型转换到其所代理的某接口的根本原因。
接下来让我们了解一下代理类实例的一些特点。每个实例都会关联一个调用处理器对象，可以通过 Proxy 提供的静态方法 getInvocationHandler 去获得代理类实例的调用处理器对象。在代理类实例上调用其代理的接口中所声明的方法时，这些方法最终都会由调用处理器的 invoke 方法执行，此外，值得注意的是，代理类的根类 java.lang.Object 中有三个方法也同样会被分派到调用处理器的 invoke 方法执行，它们是 hashCode，equals 和 toString，可能的原因有：一是因为这些方法为 public 且非 final 类型，能够被代理类覆盖；二是因为这些方法往往呈现出一个类的某种特征属性，具有一定的区分度，所以为了保证代理类与委托类对外的一致性，这三个方法也应该被分派到委托类执行。当代理的一组接口有重复声明的方法且该方法被调用时，代理类总是从排在最前面的接口中获取方法对象并分派给调用处理器，而无论代理类实例是否正在以该接口（或继承于该接口的某子接口）的形式被外部引用，因为在代理类内部无法区分其当前的被引用类型。
接着来了解一下被代理的一组接口有哪些特点。首先，要注意不能有重复的接口，以避免动态代理类代码生成时的编译错误。其次，这些接口对于类装载器必须可见，否则类装载器将无法链接它们，将会导致类定义失败。再次，需被代理的所有非 public 的接口必须在同一个包中，否则代理类生成也会失败。最后，接口的数目不能超过 65535，这是 JVM 设定的限制。
最后再来了解一下异常处理方面的特点。从调用处理器接口声明的方法中可以看到理论上它能够抛出任何类型的异常，因为所有的异常都继承于 Throwable 接口，但事实是否如此呢？答案是否定的，原因是我们必须遵守一个继承原则：即子类覆盖父类或实现父接口的方法时，抛出的异常必须在原方法支持的异常列表之内。所以虽然调用处理器理论上讲能够，但实际上往往受限制，除非父接口中的方法支持抛 Throwable 异常。那么如果在 invoke 方法中的确产生了接口方法声明中不支持的异常，那将如何呢？放心，Java 动态代理类已经为我们设计好了解决方法：它将会抛出 UndeclaredThrowableException 异常。这个异常是一个 RuntimeException 类型，所以不会引起编译错误。通过该异常的 getCause 方法，还可以获得原来那个不受支持的异常对象，以便于错误诊断。

## 5、动态代理的优点和美中不足

优点：
动态代理与静态代理相比较，最大的好处是接口中声明的所有方法都被转移到调用处理器一个集中的方法中处理（InvocationHandler.invoke）。这样，在接口方法数量比较多的时候，我们可以进行灵活处理，而不需要像静态代理那样每一个方法进行中转。在本示例中看不出来，因为invoke方法体内嵌入了具体的外围业务（记录任务处理前后时间并计算时间差），实际中可以类似Spring AOP那样配置外围业务。
美中不足：
诚然，Proxy 已经设计得非常优美，但是还是有一点点小小的遗憾之处，那就是它始终无法摆脱仅支持 interface 代理的桎梏，因为它的设计注定了这个遗憾。回想一下那些动态生成的代理类的继承关系图，它们已经注定有一个共同的父类叫 Proxy。Java 的继承机制注定了这些动态代理类们无法实现对 class 的动态代理，原因是多继承在 Java 中本质上就行不通。



有很多条理由，人们可以否定对 class 代理的必要性，但是同样有一些理由，相信支持 class 动态代理会更美好。接口和类的划分，本就不是很明显，只是到了 Java 中才变得如此的细化。如果只从方法的声明及是否被定义来考量，有一种两者的混合体，它的名字叫抽象类。实现对抽象类的动态代理，相信也有其内在的价值。此外，还有一些历史遗留的类，它们将因为没有实现任何接口而从此与动态代理永世无缘。如此种种，不得不说是一个小小的遗憾。

## AOP

Spring AOP中的动态代理主要有两种方式，JDK动态代理和CGLIB动态代理。

JDK动态代理通过反射来接收被代理的类，并且要求被代理的类必须实现一个接口。JDK动态代理的核心是InvocationHandler接口和Proxy类。

如果目标类没有实现接口，那么Spring AOP会选择使用CGLIB来动态代理目标类。

CGLIB（Code Generation Library），是一个代码生成的类库，可以在运行时动态的生成某个类的子类，注意，CGLIB是通过继承的方式做的动态代理，因此如果某个类被标记为final，那么它是无法使用CGLIB做动态代理的。

# 动态代理(码农翻身)

## 前言

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

## 使用接口来实现

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

## GCLib实现动态代理

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





