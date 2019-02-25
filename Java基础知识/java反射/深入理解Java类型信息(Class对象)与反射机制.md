[深入理解Class对象](https://blog.csdn.net/javazejian/article/details/70768369)

# 1. RRTI的概念以及Class对象作用
认识Class对象之前，先来了解一个概念，RTTI（Run-Time Type Identification）运行时类型识别，对于这个词一直是 C++ 中的概念，至于Java中出现RRTI的说法则是源于《Thinking in Java》一书，其作用是在运行时识别一个对象的类型和类的信息，这里分两种：传统的”RRTI”,它假定我们在编译期已知道了所有类型(在没有反射机制创建和使用类对象时，一般都是编译期已确定其类型，如new对象时该类必须已定义好)，另外一种是反射机制，它允许我们在运行时发现和使用类型的信息。在Java中用来表示运行时类型信息的对应类就是Class类，Class类也是一个实实在在的类，存在于JDK的java.lang包中，其部分源码如下：

```java
public final class Class<T> implements java.io.Serializable,GenericDeclaration,Type, AnnotatedElement {
    private static final int ANNOTATION= 0x00002000;
    private static final int ENUM      = 0x00004000;
    private static final int SYNTHETIC = 0x00001000;

    private static native void registerNatives();
    static {
        registerNatives();
    }

    /*
     * Private constructor. Only the Java Virtual Machine creates Class objects.（私有构造，只能由JVM创建该类）
     * This constructor is not used and prevents the default constructor being
     * generated.
     */
    private Class(ClassLoader loader) {
        // Initialize final field for classLoader.  The initialization value of non-null
        // prevents future JIT optimizations from assuming this final field is null.
        classLoader = loader;
    }
```
Class类被创建后的对象就是Class对象，注意，Class对象表示的是自己手动编写类的类型信息，比如创建一个Shapes类，那么，JVM就会创建一个Shapes对应Class类的Class对象，该Class对象保存了Shapes类相关的类型信息。实际上在Java中每个类都有一个Class对象，每当我们编写并且编译一个新创建的类就会产生一个对应Class对象并且这个Class对象会被保存在同名.class文件里(编译后的字节码文件保存的就是Class对象)，那为什么需要这样一个Class对象呢？是这样的，当我们new一个新对象或者引用静态成员变量时，Java虚拟机(JVM)中的类加载器子系统会将对应Class对象加载到JVM中，然后JVM再根据这个类型信息相关的Class对象创建我们需要实例对象或者提供静态变量的引用值。需要特别注意的是，手动编写的每个class类，无论创建多少个实例对象，在JVM中都只有一个Class对象，即在内存中每个类有且只有一个相对应的Class对象，挺拗口，通过下图理解（内存中的简易现象图）：![](https://ws3.sinaimg.cn/large/006tKfTcly1g0irnmxbovj30gl08p3zu.jpg)

到这我们也就可以得出以下几点信息：

Class类也是类的一种，与class关键字是不一样的。

手动编写的类被编译后会产生一个Class对象，其表示的是创建的类的类型信息，而且这个Class对象保存在同名.class的文件中(字节码文件)，比如创建一个Shapes类，编译Shapes类后就会创建其包含Shapes类相关类型信息的Class对象，并保存在Shapes.class字节码文件中。

每个通过关键字class标识的类，在内存中有且只有一个与之对应的Class对象来描述其类型信息，无论创建多少个实例对象，其依据的都是用一个Class对象。

Class类只存私有构造函数，因此对应Class对象只能有JVM创建和加载

Class类的对象作用是运行时提供或获得某个对象的类型信息，这点对于反射技术很重要(关于反射稍后分析)。

# 2. Class对象的加载及其获取方式
## 2.1 Class对象的加载
前面我们已提到过，Class对象是由JVM加载的，那么其加载时机是？实际上所有的类都是在对其第一次使用时动态加载到JVM中的，当程序创建第一个对类的静态成员引用时，就会加载这个被使用的类(实际上加载的就是这个类的字节码文件)，注意，使用new操作符创建类的新实例对象也会被当作对类的静态成员的引用(构造函数也是类的静态方法)，由此看来Java程序在它们开始运行之前并非被完全加载到内存的，其各个部分是按需加载，所以在使用该类时，类加载器首先会检查这个类的Class对象是否已被加载(类的实例对象创建时依据Class对象中类型信息完成的)，如果还没有加载，默认的类加载器就会先根据类名查找.class文件(编译后Class对象被保存在同名的.class文件中)，在这个类的字节码文件被加载时，它们必须接受相关验证，以确保其没有被破坏并且不包含不良Java代码(这是java的安全机制检测)，完全没有问题后就会被动态加载到内存中，此时相当于Class对象也就被载入内存了(毕竟.class字节码文件保存的就是Class对象)，同时也就可以被用来创建这个类的所有实例对象。下面通过一个简单例子来说明Class对象被加载的时机问题（例子引用自Thinking in Java）：

package com.zejian;

class Candy {
  static {   System.out.println("Loading Candy"); }
}

class Gum {
  static {   System.out.println("Loading Gum"); }
}

class Cookie {
  static {   System.out.println("Loading Cookie"); }
}

public class SweetShop {
  public static void print(Object obj) {
    System.out.println(obj);
  }
  public static void main(String[] args) {  
    print("inside main");
    new Candy();
    print("After creating Candy");
    try {
      Class.forName("com.zejian.Gum");
    } catch(ClassNotFoundException e) {
      print("Couldn't find Gum");
    }
    print("After Class.forName(\"com.zejian.Gum\")");
    new Cookie();
    print("After creating Cookie");
  }
}
在上述代码中,每个类Candy、Gum、Cookie都存在一个static语句，这个语句会在类第一次被加载时执行，这个语句的作用就是告诉我们该类在什么时候被加载，执行结果：

inside main
Loading Candy
After creating Candy
Loading Gum
After Class.forName("com.zejian.Gum")
Loading Cookie
After creating Cookie

Process finished with exit code 0

从结果来看，new一个Candy对象和Cookie对象，构造函数将被调用，属于静态方法的引用，Candy类的Class对象和Cookie的Class对象肯定会被加载，毕竟Candy实例对象的创建依据其Class对象。比较有意思的是

Class.forName("com.zejian.Gum");

其中forName方法是Class类的一个static成员方法，记住所有的Class对象都源于这个Class类，因此Class类中定义的方法将适应所有Class对象。这里通过forName方法，我们可以获取到Gum类对应的Class对象引用。从打印结果来看，调用forName方法将会导致Gum类被加载(前提是Gum类从来没有被加载过)。

## 2.2 Class.forName方法
通过上述的案例，我们也就知道Class.forName()方法的调用将会返回一个对应类的Class对象，因此如果我们想获取一个类的运行时类型信息并加以使用时，可以调用Class.forName()方法获取Class对象的引用，这样做的好处是无需通过持有该类的实例对象引用而去获取Class对象，如下的第2种方式是通过一个实例对象获取一个类的Class对象，其中的getClass()是从顶级类Object继承而来的，它将返回表示该对象的实际类型的Class对象引用。

public static void main(String[] args) {

    try{
      //通过Class.forName获取Gum类的Class对象
      Class clazz=Class.forName("com.zejian.Gum");
      System.out.println("forName=clazz:"+clazz.getName());
    }catch (ClassNotFoundException e){
      e.printStackTrace();
    }
    
    //通过实例对象获取Gum的Class对象
    Gum gum = new Gum();
    Class clazz2=gum.getClass();
    System.out.println("new=clazz2:"+clazz2.getName());
    注意调用forName方法时需要捕获一个名称为ClassNotFoundException的异常，因为forName方法在编译器是无法检测到其传递的字符串对应的类是否存在的，只能在程序运行时进行检查，如果不存在就会抛出ClassNotFoundException异常。

## 2.3 Class字面常量
在Java中存在另一种方式来生成Class对象的引用，它就是Class字面常量，如下：

//字面常量的方式获取Class对象
Class clazz = Gum.class;

这种方式相对前面两种方法更加简单，更安全。因为它在编译器就会受到编译器的检查同时由于无需调用forName方法效率也会更高，因为通过字面量的方法获取Class对象的引用不会自动初始化该类。更加有趣的是字面常量的获取Class对象引用方式不仅可以应用于普通的类，也可以应用用接口，数组以及基本数据类型，这点在反射技术应用传递参数时很有帮助，关于反射技术稍后会分析，由于基本数据类型还有对应的基本包装类型，其包装类型有一个标准字段TYPE，而这个TYPE就是一个引用，指向基本数据类型的Class对象，其等价转换如下，一般情况下更倾向使用.class的形式，这样可以保持与普通类的形式统一。

boolean.class = Boolean.TYPE;
char.class = Character.TYPE;
byte.class = Byte.TYPE;
short.class = Short.TYPE;
int.class = Integer.TYPE;
long.class = Long.TYPE;
float.class = Float.TYPE;
double.class = Double.TYPE;
void.class = Void.TYPE;

前面提到过，使用字面常量的方式获取Class对象的引用不会触发类的初始化，这里我们可能需要简单了解一下类加载的过程，如下：

![](https://ws1.sinaimg.cn/large/006tKfTcly1g0irv0pupuj30jg08tgn6.jpg)

加载：类加载过程的一个阶段：通过一个类的完全限定查找此类字节码文件，并利用字节码文件创建一个Class对象

链接：验证字节码的安全性和完整性，准备阶段正式为静态域分配存储空间，注意此时只是分配静态成员变量的存储空间，不包含实例成员变量，如果必要的话，解析这个类创建的对其他类的所有引用。

初始化：类加载最后阶段，若该类具有超类，则对其进行初始化，执行静态初始化器和静态初始化成员变量。

由此可知，我们获取字面常量的Class引用时，触发的应该是加载阶段，因为在这个阶段Class对象已创建完成，获取其引用并不困难，而无需触发类的最后阶段初始化。下面通过小例子来验证这个过程：

import java.util.*;

class Initable {
  //编译期静态常量
  static final int staticFinal = 47;
  //非编期静态常量
  static final int staticFinal2 =
    ClassInitialization.rand.nextInt(1000);
  static {
    System.out.println("Initializing Initable");
  }
}

class Initable2 {
  //静态成员变量
  static int staticNonFinal = 147;
  static {
    System.out.println("Initializing Initable2");
  }
}

class Initable3 {
  //静态成员变量
  static int staticNonFinal = 74;
  static {
    System.out.println("Initializing Initable3");
  }
}

public class ClassInitialization {
  public static Random rand = new Random(47);
  public static void main(String[] args) throws Exception {
    //字面常量获取方式获取Class对象
    Class initable = Initable.class;
    System.out.println("After creating Initable ref");
    //不触发类初始化
    System.out.println(Initable.staticFinal);
    //会触发类初始化
    System.out.println(Initable.staticFinal2);
    //会触发类初始化
    System.out.println(Initable2.staticNonFinal);
    //forName方法获取Class对象
    Class initable3 = Class.forName("Initable3");
    System.out.println("After creating Initable3 ref");
    System.out.println(Initable3.staticNonFinal);
  }
}

执行结果：

After creating Initable ref
47
Initializing Initable
258
Initializing Initable2
147
Initializing Initable3
After creating Initable3 ref
74

从输出结果来看，可以发现，通过字面常量获取方式获取Initable类的Class对象并没有触发Initable类的初始化，这点也验证了前面的分析，同时发现调用Initable.staticFinal变量时也没有触发初始化，这是因为staticFinal属于编译期静态常量，在编译阶段通过常量传播优化的方式将Initable类的常量staticFinal存储到了一个称为NotInitialization类的常量池中，在以后对Initable类常量staticFinal的引用实际都转化为对NotInitialization类对自身常量池的引用，所以在编译期后，对编译期常量的引用都将在NotInitialization类的常量池获取，这也就是引用编译期静态常量不会触发Initable类初始化的重要原因。但在之后调用了Initable.staticFinal2变量后就触发了Initable类的初始化，注意staticFinal2虽然被static和final修饰，但其值在编译期并不能确定，因此staticFinal2并不是编译期常量，使用该变量必须先初始化Initable类。Initable2和Initable3类中都是静态成员变量并非编译期常量，引用都会触发初始化。至于forName方法获取Class对象，肯定会触发初始化，这点在前面已分析过。到这几种获取Class对象的方式也都分析完，ok~,到此这里可以得出小结论：

获取Class对象引用的方式3种，通过继承自Object类的getClass方法，Class类的静态方法forName以及字面常量的方式”.class”。

其中实例类的getClass方法和Class类的静态方法forName都将会触发类的初始化阶段，而字面常量获取Class对象的方式则不会触发初始化。

初始化是类加载的最后一个阶段，也就是说完成这个阶段后类也就加载到内存中(Class对象在加载阶段已被创建)，此时可以对类进行各种必要的操作了（如new对象，调用静态成员等），注意在这个阶段，才真正开始执行类中定义的Java程序代码或者字节码。

关于类加载的初始化阶段，在虚拟机规范严格规定了有且只有5种场景必须对类进行初始化：

使用new关键字实例化对象时、读取或者设置一个类的静态字段(不包含编译期常量)以及调用静态方法的时候，必须触发类加载的初始化过程(类加载过程最终阶段)。

使用反射包(java.lang.reflect)的方法对类进行反射调用时，如果类还没有被初始化，则需先进行初始化，这点对反射很重要。

当初始化一个类的时候，如果其父类还没进行初始化则需先触发其父类的初始化。

当Java虚拟机启动时，用户需要指定一个要执行的主类(包含main方法的类)，虚拟机会先初始化这个主类

当使用JDK 1.7 的动态语言支持时，如果一个java.lang.invoke.MethodHandle 实例最后解析结果为REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄对应类没有初始化时，必须触发其初始化(这点看不懂就算了，这是1.7的新增的动态语言支持，其关键特征是它的类型检查的主体过程是在运行期而不是编译期进行的，这是一个比较大点的话题，这里暂且打住)

理解泛化的Class对象引用
由于Class的引用总数指向某个类的Class对象，利用Class对象可以创建实例类，这也就足以说明Class对象的引用指向的对象确切的类型。在Java SE5引入泛型后，使用我们可以利用泛型来表示Class对象更具体的类型，即使在运行期间会被擦除，但编译期足以确保我们使用正确的对象类型。如下：

/**
 * Created by zejian on 2017/4/30.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ClazzDemo {

    public static void main(String[] args){
        //没有泛型
        Class intClass = int.class;

        //带泛型的Class对象
        Class<Integer> integerClass = int.class;
        
        integerClass = Integer.class;
        
        //没有泛型的约束,可以随意赋值
        intClass= double.class;
        
        //编译期错误,无法编译通过
        //integerClass = double.class
    }
    }
    从代码可以看出，声明普通的Class对象，在编译器并不会检查Class对象的确切类型是否符合要求，如果存在错误只有在运行时才得以暴露出来。但是通过泛型声明指明类型的Class对象，编译器在编译期将对带泛型的类进行额外的类型检查，确保在编译期就能保证类型的正确性，实际上Integer.class就是一个Class<Integer>类的对象。面对下述语句，确实可能令人困惑，但该语句确实是无法编译通过的。

//编译无法通过
Class<Number> numberClass=Integer.class;
1
2
我们或许会想Integer不就是Number的子类吗？然而事实并非这般简单，毕竟Integer的Class对象并非Number的Class对象的子类，前面提到过，所有的Class对象都只来源于Class类，看来事实确实如此。当然我们可以利用通配符“?”来解决问题：

Class<?> intClass = int.class;
intClass = double.class;
1
2
这样的语句并没有什么问题，毕竟通配符指明所有类型都适用，那么为什么不直接使用Class还要使用Class<?>呢？这样做的好处是告诉编译器，我们是确实是采用任意类型的泛型，而非忘记使用泛型约束，因此Class<?>总是优于直接使用Class，至少前者在编译器检查时不会产生警告信息。当然我们还可以使用extends关键字告诉编译器接收某个类型的子类，如解决前面Number与Integer的问题：

//编译通过！
Class<? extends Number> clazz = Integer.class;
//赋予其他类型
clazz = double.class;
clazz = Number.class;
1
2
3
4
5
上述的代码是行得通的，extends关键字的作用是告诉编译器，只要是Number的子类都可以赋值。这点与前面直接使用Class<Number>是不一样的。实际上，应该时刻记住向Class引用添加泛型约束仅仅是为了提供编译期类型的检查从而避免将错误延续到运行时期。

关于类型转换的问题
在许多需要强制类型转换的场景，我们更多的做法是直接强制转换类型：

package com.zejian;

/**
 * Created by zejian on 2017/4/30.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ClassCast {

 public void cast(){

     Animal animal= new Dog();
     //强制转换
     Dog dog = (Dog) animal;
 }
}

interface Animal{ }

class Dog implements  Animal{ }

之所可以强制转换，这得归功于RRTI，要知道在Java中，所有类型转换都是在运行时进行正确性检查的，利用RRTI进行判断类型是否正确从而确保强制转换的完成，如果类型转换失败，将会抛出类型转换异常。除了强制转换外，在Java SE5中新增一种使用Class对象进行类型转换的方式，如下：

Animal animal= new Dog();
//这两句等同于Dog dog = (Dog) animal;
Class<Dog> dogType = Dog.class;
Dog dog = dogType.cast(animal)
1
2
3
4
利用Class对象的cast方法，其参数接收一个参数对象并将其转换为Class引用的类型。这种方式似乎比之前的强制转换更麻烦些，确实如此，而且当类型不能正确转换时，仍然会抛出ClassCastException异常。源码如下：

public T cast(Object obj) {
    if (obj != null && !isInstance(obj))
         throw new ClassCastException(cannotCastMsg(obj));
     return (T) obj;
  }
1
2
3
4
5
instanceof 关键字与isInstance方法
关于instanceof 关键字，它返回一个boolean类型的值，意在告诉我们对象是不是某个特定的类型实例。如下，在强制转换前利用instanceof检测obj是不是Animal类型的实例对象，如果返回true再进行类型转换，这样可以避免抛出类型转换的异常(ClassCastException)

public void cast2(Object obj){
    if(obj instanceof Animal){
          Animal animal= (Animal) obj;
      }
}
1
2
3
4
5
而isInstance方法则是Class类中的一个Native方法，也是用于判断对象类型的，看个简单例子：

public void cast2(Object obj){
        //instanceof关键字
        if(obj instanceof Animal){
            Animal animal= (Animal) obj;
        }

        //isInstance方法
        if(Animal.class.isInstance(obj)){
            Animal animal= (Animal) obj;
        }
  }
1
2
3
4
5
6
7
8
9
10
11
事实上instanceOf 与isInstance方法产生的结果是相同的。对于instanceOf是关键字只被用于对象引用变量，检查左边对象是不是右边类或接口的实例化。如果被测对象是null值，则测试结果总是false。一般形式：

//判断这个对象是不是这种类型
obj.instanceof(class)
1
2
而isInstance方法则是Class类的Native方法，其中obj是被测试的对象或者变量，如果obj是调用这个方法的class或接口的实例，则返回true。如果被检测的对象是null或者基本类型，那么返回值是false;一般形式如下：

//判断这个对象能不能被转化为这个类
class.inInstance(obj)
1
2
最后这里给出一个简单实例，验证isInstance方法与instanceof等价性：

class A {}

class B extends A {}

public class C {
  static void test(Object x) {
    print("Testing x of type " + x.getClass());
    print("x instanceof A " + (x instanceof A));
    print("x instanceof B "+ (x instanceof B));
    print("A.isInstance(x) "+ A.class.isInstance(x));
    print("B.isInstance(x) " +
      B.class.isInstance(x));
    print("x.getClass() == A.class " +
      (x.getClass() == A.class));
    print("x.getClass() == B.class " +
      (x.getClass() == B.class));
    print("x.getClass().equals(A.class)) "+
      (x.getClass().equals(A.class)));
    print("x.getClass().equals(B.class)) " +
      (x.getClass().equals(B.class)));
  }
  public static void main(String[] args) {
    test(new A());
    test(new B());
  } 
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
执行结果：

Testing x of type class com.zejian.A
x instanceof A true
x instanceof B false //父类不一定是子类的某个类型
A.isInstance(x) true
B.isInstance(x) false
x.getClass() == A.class true
x.getClass() == B.class false
x.getClass().equals(A.class)) true

x.getClass().equals(B.class)) false

Testing x of type class com.zejian.B
x instanceof A true
x instanceof B true
A.isInstance(x) true
B.isInstance(x) true
x.getClass() == A.class false
x.getClass() == B.class true
x.getClass().equals(A.class)) false
x.getClass().equals(B.class)) true
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
到此关于Class对象相关的知识点都分析完了，下面将结合Class对象的知识点分析反射技术。

理解反射技术
反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意一个方法和属性，这种动态获取的信息以及动态调用对象的方法的功能称为java语言的反射机制。一直以来反射技术都是Java中的闪亮点，这也是目前大部分框架(如Spring/Mybatis等)得以实现的支柱。在Java中，Class类与java.lang.reflect类库一起对反射技术进行了全力的支持。在反射包中，我们常用的类主要有Constructor类表示的是Class 对象所表示的类的构造方法，利用它可以在运行时动态创建对象、Field表示Class对象所表示的类的成员变量，通过它可以在运行时动态修改成员变量的属性值(包含private)、Method表示Class对象所表示的类的成员方法，通过它可以动态调用对象的方法(包含private)，下面将对这几个重要类进行分别说明。

Constructor类及其用法
Constructor类存在于反射包(java.lang.reflect)中，反映的是Class 对象所表示的类的构造方法。获取Constructor对象是通过Class类中的方法获取的，Class类与Constructor相关的主要方法如下：

方法返回值	方法名称	方法说明
static Class<?>	forName(String className)	返回与带有给定字符串名的类或接口相关联的 Class 对象。
Constructor<T>	getConstructor(Class<?>... parameterTypes)	返回指定参数类型、具有public访问权限的构造函数对象
Constructor<?>[]	getConstructors()	返回所有具有public访问权限的构造函数的Constructor对象数组
Constructor<T>	getDeclaredConstructor(Class<?>... parameterTypes)	返回指定参数类型、所有声明的（包括private）构造函数对象
Constructor<?>[]	getDeclaredConstructor()	返回所有声明的（包括private）构造函数对象
T	newInstance()	创建此 Class 对象所表示的类的一个新实例。
下面看一个简单例子来了解Constructor对象的使用：

package reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;

/**
 * Created by zejian on 2017/5/1.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ReflectDemo implements Serializable{
    public static void main(String[] args) throws Exception {

        Class<?> clazz = null;
        
        //获取Class对象的引用
        clazz = Class.forName("reflect.User");
        
        //第一种方法，实例化默认构造方法，User必须无参构造函数,否则将抛异常
        User user = (User) clazz.newInstance();
        user.setAge(20);
        user.setName("Rollen");
        System.out.println(user);
        
        System.out.println("--------------------------------------------");
        
        //获取带String参数的public构造函数
        Constructor cs1 =clazz.getConstructor(String.class);
        //创建User
        User user1= (User) cs1.newInstance("xiaolong");
        user1.setAge(22);
        System.out.println("user1:"+user1.toString());
        
        System.out.println("--------------------------------------------");
        
        //取得指定带int和String参数构造函数,该方法是私有构造private
        Constructor cs2=clazz.getDeclaredConstructor(int.class,String.class);
        //由于是private必须设置可访问
        cs2.setAccessible(true);
        //创建user对象
        User user2= (User) cs2.newInstance(25,"lidakang");
        System.out.println("user2:"+user2.toString());
        
        System.out.println("--------------------------------------------");
        
        //获取所有构造包含private
        Constructor<?> cons[] = clazz.getDeclaredConstructors();
        // 查看每个构造方法需要的参数
        for (int i = 0; i < cons.length; i++) {
            //获取构造函数参数类型
            Class<?> clazzs[] = cons[i].getParameterTypes();
            System.out.println("构造函数["+i+"]:"+cons[i].toString() );
            System.out.print("参数类型["+i+"]:(");
            for (int j = 0; j < clazzs.length; j++) {
                if (j == clazzs.length - 1)
                    System.out.print(clazzs[j].getName());
                else
                    System.out.print(clazzs[j].getName() + ",");
            }
            System.out.println(")");
        }
    }
    }


class User {
    private int age;
    private String name;
    public User() {
        super();
    }
    public User(String name) {
        super();
        this.name = name;
    }

    /**
     * 私有构造
     * @param age
     * @param name
     */
    private User(int age, String name) {
        super();
        this.age = age;
        this.name = name;
    }

  //..........省略set 和 get方法
}

运行结果：

User [age=20, name=Rollen]

user1:User [age=22, name=xiaolong]

user2:User [age=25, name=lidakang]

构造函数[0]:private reflect.User(int,java.lang.String)
参数类型[0]:(int,java.lang.String)
构造函数[1]:public reflect.User(java.lang.String)
参数类型[1]:(java.lang.String)
构造函数[2]:public reflect.User()
参数类型[2]:()

关于Constructor类本身一些常用方法如下(仅部分，其他可查API)，

方法返回值	方法名称	方法说明
Class<T>	getDeclaringClass()	返回 Class 对象，该对象表示声明由此 Constructor 对象表示的构造方法的类,其实就是返回真实类型（不包含参数）
Type[]	getGenericParameterTypes()	按照声明顺序返回一组 Type 对象，返回的就是 Constructor对象构造函数的形参类型。
String	getName()	以字符串形式返回此构造方法的名称。
Class<?>[]	getParameterTypes()	按照声明顺序返回一组 Class 对象，即返回Constructor 对象所表示构造方法的形参类型
T	newInstance(Object... initargs)	使用此 Constructor对象表示的构造函数来创建新实例
String	toGenericString()	返回描述此 Constructor 的字符串，其中包括类型参数。
代码演示如下：

Constructor cs3=clazz.getDeclaredConstructor(int.class,String.class);

System.out.println("-----getDeclaringClass-----");
Class uclazz=cs3.getDeclaringClass();
//Constructor对象表示的构造方法的类
System.out.println("构造方法的类:"+uclazz.getName());

System.out.println("-----getGenericParameterTypes-----");
//对象表示此 Constructor 对象所表示的方法的形参类型
Type[] tps=cs3.getGenericParameterTypes();
for (Type tp:tps) {
    System.out.println("参数名称tp:"+tp);
}
System.out.println("-----getParameterTypes-----");
//获取构造函数参数类型
Class<?> clazzs[] = cs3.getParameterTypes();
for (Class claz:clazzs) {
    System.out.println("参数名称:"+claz.getName());
}
System.out.println("-----getName-----");
//以字符串形式返回此构造方法的名称
System.out.println("getName:"+cs3.getName());

System.out.println("-----getoGenericString-----");
//返回描述此 Constructor 的字符串，其中包括类型参数。
System.out.println("getoGenericString():"+cs3.toGenericString());
/**
 输出结果:
 -----getDeclaringClass-----
 构造方法的类:reflect.User
 -----getGenericParameterTypes-----
 参数名称tp:int
 参数名称tp:class java.lang.String
 -----getParameterTypes-----
 参数名称:int
 参数名称:java.lang.String
 -----getName-----
 getName:reflect.User
 -----getoGenericString-----
 getoGenericString():private reflect.User(int,java.lang.String)
 */
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
其中关于Type类型这里简单说明一下，Type 是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。getGenericParameterTypes 与 getParameterTypes 都是获取构成函数的参数类型，前者返回的是Type类型，后者返回的是Class类型，由于Type顶级接口，Class也实现了该接口，因此Class类是Type的子类，Type 表示的全部类型而每个Class对象表示一个具体类型的实例，如String.class仅代表String类型。由此看来Type与 Class 表示类型几乎是相同的，只不过 Type表示的范围比Class要广得多而已。当然Type还有其他子类，如：

TypeVariable：表示类型参数，可以有上界，比如：T extends Number

ParameterizedType：表示参数化的类型，有原始类型和具体的类型参数，比如：List<String>

WildcardType：表示通配符类型，比如：?, ? extends Number, ? super Integer

通过以上的分析，对于Constructor类已有比较清晰的理解，利用好Class类和Constructor类，我们可以在运行时动态创建任意对象，从而突破必须在编译期知道确切类型的障碍。

Field类及其用法
Field 提供有关类或接口的单个字段的信息，以及对它的动态访问权限。反射的字段可能是一个类（静态）字段或实例字段。同样的道理，我们可以通过Class类的提供的方法来获取代表字段信息的Field对象，Class类与Field对象相关方法如下：

方法返回值	方法名称	方法说明
Field	getDeclaredField(String name)	获取指定name名称的(包含private修饰的)字段，不包括继承的字段
Field[]	getDeclaredField()	获取Class对象所表示的类或接口的所有(包含private修饰的)字段,不包括继承的字段
Field	getField(String name)	获取指定name名称、具有public修饰的字段，包含继承字段
Field[]	getField()	获取修饰符为public的字段，包含继承字段

下面的代码演示了上述方法的使用过程

/**
 * Created by zejian on 2017/5/1.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ReflectField {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class<?> clazz = Class.forName("reflect.Student");
        //获取指定字段名称的Field类,注意字段修饰符必须为public而且存在该字段,
        // 否则抛NoSuchFieldException
        Field field = clazz.getField("age");
        System.out.println("field:"+field);

        //获取所有修饰符为public的字段,包含父类字段,注意修饰符为public才会获取
        Field fields[] = clazz.getFields();
        for (Field f:fields) {
            System.out.println("f:"+f.getDeclaringClass());
        }
        
        System.out.println("================getDeclaredFields====================");
        //获取当前类所字段(包含private字段),注意不包含父类的字段
        Field fields2[] = clazz.getDeclaredFields();
        for (Field f:fields2) {
            System.out.println("f2:"+f.getDeclaringClass());
        }
        //获取指定字段名称的Field类,可以是任意修饰符的自动,注意不包含父类的字段
        Field field2 = clazz.getDeclaredField("desc");
        System.out.println("field2:"+field2);
    }
    /**
      输出结果: 
     field:public int reflect.Person.age
     f:public java.lang.String reflect.Student.desc
     f:public int reflect.Person.age
     f:public java.lang.String reflect.Person.name

     ================getDeclaredFields====================
     f2:public java.lang.String reflect.Student.desc
     f2:private int reflect.Student.score
     field2:public java.lang.String reflect.Student.desc
     */
    }

class Person{
    public int age;
    public String name;
    //省略set和get方法
}

class Student extends Person{
    public String desc;
    private int score;
    //省略set和get方法
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
上述方法需要注意的是，如果我们不期望获取其父类的字段，则需使用Class类的getDeclaredField/getDeclaredFields方法来获取字段即可，倘若需要连带获取到父类的字段，那么请使用Class类的getField/getFields，但是也只能获取到public修饰的的字段，无法获取父类的私有字段。下面将通过Field类本身的方法对指定类属性赋值，代码演示如下：

//获取Class对象引用
Class<?> clazz = Class.forName("reflect.Student");

Student st= (Student) clazz.newInstance();
//获取父类public字段并赋值
Field ageField = clazz.getField("age");
ageField.set(st,18);
Field nameField = clazz.getField("name");
nameField.set(st,"Lily");

//只获取当前类的字段,不获取父类的字段
Field descField = clazz.getDeclaredField("desc");
descField.set(st,"I am student");
Field scoreField = clazz.getDeclaredField("score");
//设置可访问，score是private的
scoreField.setAccessible(true);
scoreField.set(st,88);
System.out.println(st.toString());

//输出结果：Student{age=18, name='Lily ,desc='I am student', score=88} 

//获取字段值
System.out.println(scoreField.get(st));
// 88
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
其中的set(Object obj, Object value)方法是Field类本身的方法，用于设置字段的值，而get(Object obj)则是获取字段的值，当然关于Field类还有其他常用的方法如下：

方法返回值	方法名称	方法说明
void	set(Object obj, Object value)	将指定对象变量上此 Field 对象表示的字段设置为指定的新值。
Object	get(Object obj)	返回指定对象上此 Field 表示的字段的值
Class<?>	getType()	返回一个 Class 对象，它标识了此Field 对象所表示字段的声明类型。
boolean	isEnumConstant()	如果此字段表示枚举类型的元素则返回 true；否则返回 false
String	toGenericString()	返回一个描述此 Field（包括其一般类型）的字符串
String	getName()	返回此 Field 对象表示的字段的名称
Class<?>	getDeclaringClass()	返回表示类或接口的 Class 对象，该类或接口声明由此 Field 对象表示的字段
void	setAccessible(boolean flag)	将此对象的 accessible 标志设置为指示的布尔值,即设置其可访问性

上述方法可能是较为常用的，事实上在设置值的方法上，Field类还提供了专门针对基本数据类型的方法，如setInt()/getInt()、setBoolean()/getBoolean、setChar()/getChar()等等方法，这里就不全部列出了，需要时查API文档即可。需要特别注意的是被final关键字修饰的Field字段是安全的，在运行时可以接收任何修改，但最终其实际值是不会发生改变的。

Method类及其用法
Method 提供关于类或接口上单独某个方法（以及如何访问该方法）的信息，所反映的方法可能是类方法或实例方法（包括抽象方法）。下面是Class类获取Method对象相关的方法：

方法返回值	方法名称	方法说明
Method	getDeclaredMethod(String name, Class<?>... parameterTypes)	返回一个指定参数的Method对象，该对象反映此 Class 对象所表示的类或接口的指定已声明方法。
Method[]	getDeclaredMethod()	返回 Method 对象的一个数组，这些对象反映此 Class 对象表示的类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
Method	getMethod(String name, Class<?>... parameterTypes)	返回一个 Method 对象，它反映此 Class 对象所表示的类或接口的指定公共成员方法。
Method[]	getMethods()	返回一个包含某些 Method 对象的数组，这些对象反映此 Class 对象所表示的类或接口（包括那些由该类或接口声明的以及从超类和超接口继承的那些的类或接口）的公共 member 方法。
同样通过案例演示上述方法：

import java.lang.reflect.Method;

/**
 * Created by zejian on 2017/5/1.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ReflectMethod  {


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
    
        Class clazz = Class.forName("reflect.Circle");
    
        //根据参数获取public的Method,包含继承自父类的方法
        Method method = clazz.getMethod("draw",int.class,String.class);
    
        System.out.println("method:"+method);
    
        //获取所有public的方法:
        Method[] methods =clazz.getMethods();
        for (Method m:methods){
            System.out.println("m::"+m);
        }
    
        System.out.println("=========================================");
    
        //获取当前类的方法包含private,该方法无法获取继承自父类的method
        Method method1 = clazz.getDeclaredMethod("drawCircle");
        System.out.println("method1::"+method1);
        //获取当前类的所有方法包含private,该方法无法获取继承自父类的method
        Method[] methods1=clazz.getDeclaredMethods();
        for (Method m:methods1){
            System.out.println("m1::"+m);
        }
    }

/**
     输出结果:
     method:public void reflect.Shape.draw(int,java.lang.String)

     m::public int reflect.Circle.getAllCount()
     m::public void reflect.Shape.draw()
     m::public void reflect.Shape.draw(int,java.lang.String)
     m::public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
     m::public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
     m::public final void java.lang.Object.wait() throws java.lang.InterruptedException
     m::public boolean java.lang.Object.equals(java.lang.Object)
     m::public java.lang.String java.lang.Object.toString()
     m::public native int java.lang.Object.hashCode()
     m::public final native java.lang.Class java.lang.Object.getClass()
     m::public final native void java.lang.Object.notify()
     m::public final native void java.lang.Object.notifyAll()
    
     =========================================
     method1::private void reflect.Circle.drawCircle()
    
     m1::public int reflect.Circle.getAllCount()
     m1::private void reflect.Circle.drawCircle()
     */
}

class Shape {
    public void draw(){
        System.out.println("draw");
    }

    public void draw(int count , String name){
        System.out.println("draw "+ name +",count="+count);
    }

}
class Circle extends Shape{

    private void drawCircle(){
        System.out.println("drawCircle");
    }
    public int getAllCount(){
        return 100;
    }
}
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
在通过getMethods方法获取Method对象时，会把父类的方法也获取到，如上的输出结果，把Object类的方法都打印出来了。而getDeclaredMethod/getDeclaredMethods方法都只能获取当前类的方法。我们在使用时根据情况选择即可。下面将演示通过Method对象调用指定类的方法：

Class clazz = Class.forName("reflect.Circle");
//创建对象
Circle circle = (Circle) clazz.newInstance();

//获取指定参数的方法对象Method
Method method = clazz.getMethod("draw",int.class,String.class);

//通过Method对象的invoke(Object obj,Object... args)方法调用
method.invoke(circle,15,"圈圈");

//对私有无参方法的操作
Method method1 = clazz.getDeclaredMethod("drawCircle");
//修改私有方法的访问标识
method1.setAccessible(true);
method1.invoke(circle);

//对有返回值得方法操作
Method method2 =clazz.getDeclaredMethod("getAllCount");
Integer count = (Integer) method2.invoke(circle);
System.out.println("count:"+count);

/**
    输出结果:
    draw 圈圈,count=15
    drawCircle
    count:100
*/
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
在上述代码中调用方法，使用了Method类的invoke(Object obj,Object... args)第一个参数代表调用的对象，第二个参数传递的调用方法的参数。这样就完成了类方法的动态调用。

方法返回值	方法名称	方法说明
Object	invoke(Object obj, Object... args)	对带有指定参数的指定对象调用由此 Method 对象表示的底层方法。
Class<?>	getReturnType()	返回一个 Class 对象，该对象描述了此 Method 对象所表示的方法的正式返回类型,即方法的返回类型
Type	getGenericReturnType()	返回表示由此 Method 对象所表示方法的正式返回类型的 Type 对象，也是方法的返回类型。
Class<?>[]	getParameterTypes()	按照声明顺序返回 Class 对象的数组，这些对象描述了此 Method 对象所表示的方法的形参类型。即返回方法的参数类型组成的数组
Type[]	getGenericParameterTypes()	按照声明顺序返回 Type 对象的数组，这些对象描述了此 Method 对象所表示的方法的形参类型的，也是返回方法的参数类型
String	getName()	以 String 形式返回此 Method 对象表示的方法名称，即返回方法的名称
boolean	isVarArgs()	判断方法是否带可变参数，如果将此方法声明为带有可变数量的参数，则返回 true；否则，返回 false。
String	toGenericString()	返回描述此 Method 的字符串，包括类型参数。

getReturnType方法/getGenericReturnType方法都是获取Method对象表示的方法的返回类型，只不过前者返回的Class类型后者返回的Type(前面已分析过)，Type就是一个接口而已，在Java8中新增一个默认的方法实现，返回的就参数类型信息

public interface Type {
    //1.8新增
    default String getTypeName() {
        return toString();
    }
}
1
2
3
4
5
6
而getParameterTypes/getGenericParameterTypes也是同样的道理，都是获取Method对象所表示的方法的参数类型，其他方法与前面的Field和Constructor是类似的。

反射包中的Array类
在Java的java.lang.reflect包中存在着一个可以动态操作数组的类，Array，它提供了动态创建和访问 Java 数组的方法。Array 允许在执行 get 或 set 操作进行取值和赋值。在Class类中与数组关联的方法是：

方法返回值	方法名称	方法说明
Class<?>	getComponentType()	返回表示数组元素类型的 Class，即数组的类型
boolean	isArray()	判定此 Class 对象是否表示一个数组类。
java.lang.reflect.Array中的常用静态方法如下：

方法返回值	方法名称	方法说明
static Object	set(Object array, int index)	返回指定数组对象中索引组件的值。
static int	getLength(Object array)	以 int 形式返回指定数组对象的长度
static object	newInstance(Class<?> componentType, int... dimensions)	创建一个具有指定类型和维度的新数组。
static Object	newInstance(Class<?> componentType, int length)	创建一个具有指定的组件类型和长度的新数组。
static void	set(Object array, int index, Object value)	将指定数组对象中索引组件的值设置为指定的新值。
下面通过一个简单例子来演示这些方法

package reflect;

import java.lang.reflect.Array;

/**
 * Created by zejian on 2017/5/1.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
    */
    public class ReflectArray {

    public static void main(String[] args) throws ClassNotFoundException {
        int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        //获取数组类型的Class 即int.class
        Class<?> clazz = array.getClass().getComponentType();
        //创建一个具有指定的组件类型和长度的新数组。
        //第一个参数:数组的类型,第二个参数:数组的长度
        Object newArr = Array.newInstance(clazz, 15);
        //获取原数组的长度
        int co = Array.getLength(array);
        //赋值原数组到新数组
        System.arraycopy(array, 0, newArr, 0, co);
        for (int i:(int[]) newArr) {
            System.out.print(i+",");
        }

        //创建了一个长度为10 的字符串数组，
        //接着把索引位置为6 的元素设为"hello world!"，然后再读取索引位置为6 的元素的值
        Class clazz2 = Class.forName("java.lang.String");
        
        //创建一个长度为10的字符串数组，在Java中数组也可以作为Object对象
        Object array2 = Array.newInstance(clazz2, 10);
        
        //把字符串数组对象的索引位置为6的元素设置为"hello"
        Array.set(array2, 6, "hello world!");
        
        //获得字符串数组对象的索引位置为5的元素的值
        String str = (String)Array.get(array2, 6);
        System.out.println();
        System.out.println(str);//hello
    }
    /**
     输出结果:
     1,2,3,4,5,6,7,8,9,0,0,0,0,0,0,
     hello world!
     */
    }
    1
    2
    3
    4
    5
    6
    7
    8
    9
    10
    11
    12
    13
    14
    15
    16
    17
    18
    19
    20
    21
    22
    23
    24
    25
    26
    27
    28
    29
    30
    31
    32
    33
    34
    35
    36
    37
    38
    39
    40
    41
    42
    43
    44
    45
    46
    通过上述代码演示，确实可以利用Array类和反射相结合动态创建数组，也可以在运行时动态获取和设置数组中元素的值，其实除了上的set/get外Array还专门为8种基本数据类型提供特有的方法，如setInt/getInt、setBoolean/getBoolean，其他依次类推，需要使用是可以查看API文档即可。除了上述动态修改数组长度或者动态创建数组或动态获取值或设置值外，可以利用泛型动态创建泛型数组如下：

/**
  * 接收一个泛型数组，然后创建一个长度与接收的数组长度一样的泛型数组，
  * 并把接收的数组的元素复制到新创建的数组中，
  * 最后找出新数组中的最小元素，并打印出来
  * @param a
  * @param <T>
     */
      public  <T extends Comparable<T>> void min(T[] a) {
     //通过反射创建相同类型的数组
     T[] b = (T[]) Array.newInstance(a.getClass().getComponentType(), a.length);
     for (int i = 0; i < a.length; i++) {
         b[i] = a[i];
     }
     T min = null;
     boolean flag = true;
     for (int i = 0; i < b.length; i++) {
         if (flag) {
             min = b[i];
             flag = false;
         }
         if (b[i].compareTo(min) < 0) {
             min = b[i];
         }
     }
     System.out.println(min);
      }
     1
     2
     3
     4
     5
     6
     7
     8
     9
     10
     11
     12
     13
     14
     15
     16
     17
     18
     19
     20
     21
     22
     23
     24
     25
     26
     毕竟我们无法直接创建泛型数组，有了Array的动态创建数组的方式这个问题也就迎刃而解了。

//无效语句，编译不通
T[] a = new T[];
1
2
ok~，到这反射中几个重要并且常用的类我们都基本介绍完了，但更重要是，我们应该认识到反射机制并没有什么神奇之处。当通过反射与一个未知类型的对象打交道时，JVM只会简单地检查这个对象，判断该对象属于那种类型，同时也应该知道，在使用反射机制创建对象前，必须确保已加载了这个类的Class对象，当然这点完全不必由我们操作，毕竟只能JVM加载，但必须确保该类的”.class”文件已存在并且JVM能够正确找到。关于Class类的方法在前面我们只是分析了主要的一些方法，其实Class类的API方法挺多的，建议查看一下API文档，浏览一遍，有个印象也是不错的选择，这里仅列出前面没有介绍过又可能用到的API：

 /** 
  *    修饰符、父类、实现的接口、注解相关 
      */

//获取修饰符，返回值可通过Modifier类进行解读
public native int getModifiers();
//获取父类，如果为Object，父类为null
public native Class<? super T> getSuperclass();
//对于类，为自己声明实现的所有接口，对于接口，为直接扩展的接口，不包括通过父类间接继承来的
public native Class<?>[] getInterfaces();
//自己声明的注解
public Annotation[] getDeclaredAnnotations();
//所有的注解，包括继承得到的
public Annotation[] getAnnotations();
//获取或检查指定类型的注解，包括继承得到的
public <A extends Annotation> A getAnnotation(Class<A> annotationClass);
public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass);

/** 
  *   内部类相关
      */
      //获取所有的public的内部类和接口，包括从父类继承得到的
      public Class<?>[] getClasses();
      //获取自己声明的所有的内部类和接口
      public Class<?>[] getDeclaredClasses();
      //如果当前Class为内部类，获取声明该类的最外部的Class对象
      public Class<?> getDeclaringClass();
      //如果当前Class为内部类，获取直接包含该类的类
      public Class<?> getEnclosingClass();
      //如果当前Class为本地类或匿名内部类，返回包含它的方法
      public Method getEnclosingMethod();

/** 
  *    Class对象类型判断相关
      */
      //是否是数组
      public native boolean isArray();  
      //是否是基本类型
      public native boolean isPrimitive();
      //是否是接口
      public native boolean isInterface();
      //是否是枚举
      public boolean isEnum();
      //是否是注解
      public boolean isAnnotation();
      //是否是匿名内部类
      public boolean isAnonymousClass();
      //是否是成员类
      public boolean isMemberClass();
      //是否是本地类
      public boolean isLocalClass(); 