<!--ts-->
   * [前言](#前言)
   * [1. 类型擦除](#1-类型擦除)
   * [2. 实例分析](#2-实例分析)
   * [3. 通配符与上下界](#3-通配符与上下界)
   * [4. 类型系统](#4-类型系统)
   * [5. 开发自己的泛型类](#5-开发自己的泛型类)
   * [6. 泛型的使用](#6-泛型的使用)
         * [普通泛型](#普通泛型)
         * [通配符](#通配符)
         * [受限泛型](#受限泛型)
         * [Java泛型无法向上转型](#java泛型无法向上转型)
         * [Java泛型接口](#java泛型接口)
         * [Java泛型方法](#java泛型方法)
         * [通过泛型方法返回泛型类型实例](#通过泛型方法返回泛型类型实例)
         * [使用泛型统一传入的参数类型](#使用泛型统一传入的参数类型)
         * [Java泛型数组](#java泛型数组)
         * [Java泛型的嵌套设置](#java泛型的嵌套设置)
   * [7. 泛型擦除](#7-泛型擦除)
      * [前言](#前言-1)
      * [7.1 各种语言中的编译器是如何处理泛型的](#71-各种语言中的编译器是如何处理泛型的)
      * [7.2 什么是类型擦除](#72-什么是类型擦除)
      * [7.3 Java编译器处理泛型的过程](#73-java编译器处理泛型的过程)
      * [7.4 泛型带来的问题](#74-泛型带来的问题)
         * [7.4.1 <strong>当泛型遇到重载：</strong>](#741-当泛型遇到重载)
         * [7.4.2 <strong>当泛型遇到catch:</strong>](#742-当泛型遇到catch)
         * [7.4.3 当泛型内包含静态变量](#743-当泛型内包含静态变量)
      * [7.5 总结](#75-总结)
      * [7.6 总结](#76-总结)

<!-- Added by: anapodoton, at: Tue Mar  3 14:41:11 CST 2020 -->

<!--te-->

# 前言

[Java泛型（`generics`）](http://docs.oracle.com/javase/1.5.0/docs/guide/language/generics.html) 是JDK 5中引入的一个新特性，允许在定义类和接口的时候使用类型参数（type parameter）。声明的类型参数在使用时用具体的类型来替换。泛型最主要的应用是在JDK 5中的新集合类框架中。对于泛型概念的引入，开发社区的观点是褒贬不一。

从好的方面来说，泛型的引入**可以解决之前的集合类框架在使用过程中通常会出现的运行时刻类型错误**，因为编译器可以在编译时刻就发现很多明显的错误。

而从不好的地方来说，为了保证与旧有版本的兼容性，Java泛型的实现上存在着一些不够优雅的地方。当然这也是任何有历史的编程语言所需要承担的历史包袱。后续的版本更新会为早期的设计缺陷所累。
开发人员在使用泛型的时候，很容易根据自己的直觉而犯一些错误。比如一个方法如果接收`List<Object>`作为形式参数，那么如果尝试将一个`List<String>`的对象作为实际参数传进去，却发现无法通过编译。虽然从直觉上来说，Object是String的父类，这种类型转换应该是合理的。但是实际上这会产生隐含的类型转换问题，因此编译器直接就禁止这样的行为。本文试图对Java泛型做一个概括性的说明。

# 1. 类型擦除

正确理解泛型概念的首要前提是理解**类型擦除**（`type erasure`）。

Java中的泛型基本上都是在编译器这个层次来实现的。在生成的Java字节代码中是不包含泛型中的类型信息的。使用泛型的时候加上的类型参数，会被编译器在编译的时候去掉。这个过程就称为类型擦除。如在代码中定义的`List<Object>` 和`List<String>`等类型，在编译之后都会变成List。JVM看到的只是List，而由泛型附加的类型信息对JVM来说是不可见的。Java编译器会在编译时尽可能的发现可能出错的地方，但是仍然无法避免在运行时刻出现类型转换异常的情况。类型擦除也是Java的泛型实现方式与C++模板机制实现方式之间的重要区别。 很多泛型的奇怪特性都与这个类型擦除的存在有关，包括：

> 泛型类并没有自己独有的Class类对象。比如并不存在`List<String>.class`或是`List<Integer>.class`，而只有`List.class`。 静态变量是被泛型类的所有实例所共享的。对于声明为`MyClass<T>`的类，访问其中的静态变量的方法仍然是 MyClass.myStaticVar。不管是通过`new MyClass<String>;`还是`new MyClass<Integer>`创建的对象，都是共享一个静态变量。 泛型的类型参数不能用在Java异常处理的catch语句中。因为异常处理是由JVM在运行时刻来进行的。由于类型信息被擦除，JVM是无法区分两个异常类型`MyException<String>;`和`MyException<Integer>`的。对于JVM来说，它们都是 MyException类型的。也就无法执行与异常对应的catch语句。

**类型擦除**的基本过程也比较简单: 首先是找到用来替换类型参数的具体类。这个具体类一般是Object。如果指定了类型参数的上界的话，则使用这个上界。把代码中的类型参数都替换成具体的类。同时去掉出现的类型声明，即去掉`<>`的内容。比如: `T get()`方法声明就变成了`Object get()`； `List<String>`就变成了`List`。 接下来就可能需要生成一些**桥接方法**（bridge method）。这是由于擦除了类型之后的类可能缺少某些必须的方法。比如考虑下面的代码：

```
class MyString implements Comparable<String> {
    public int compareTo(String str) {        
        return 0;    
    }
} 
```

当类型信息被擦除之后，上述类的声明变成了class MyString implements Comparable。但是这样的话，类MyString就会有编译错误，因为没有实现接口Comparable声明的int compareTo(Object)方法。这个时候就由编译器来动态生成这个方法。

# 2. 实例分析

了解了类型擦除机制之后，就会明白编译器承担了全部的类型检查工作。编译器禁止某些泛型的使用方式，正是为了确保类型的安全性。以上面提到的`List<Object>`和`List<String>`为例来具体分析：

```java
public void inspect(List<Object> list) {    
    for (Object obj : list) {        
        System.out.println(obj);    
    }    
    list.add(1); //这个操作在当前方法的上下文是合法的。 
}
public void test() {    
    List<String> strs = new ArrayList<String>();    
    inspect(strs); //编译错误 
} 
```

这段代码中，`inspect`方法接受`List<Object>`作为参数，当在`test`方法中试图传入`List<String>`的时候，会出现**编译错误**。假设这样的做法是允许的，那么在`inspect`方法就可以通过`list.add(1)`来向集合中添加一个数字。这样在`test`方法看来，其声明为`List<String>`的集合中却被添加了一个`Integer`类型的对象。这显然是违反类型安全的原则的，在某个时候肯定会抛出`ClassCastException`。因此，编译器禁止这样的行为。编译器会尽可能的检查可能存在的类型安全问题。对于确定是违反相关原则的地方，会给出编译错误。当编译器无法判断类型的使用是否正确的时候，会给出警告信息。

# 3. 通配符与上下界 

（可参考 [Java泛型中extends和super的理解](http://www.hollischuang.com/archives/255) 和 [Java泛型中K T V E ？ object等的含义](http://www.hollischuang.com/archives/252)）

在使用泛型类的时候，既可以指定一个具体的类型，如`List<String>`就声明了具体的类型是`String`；也可以用通配符`?`来表示未知类型，如`List<?>`就声明了List中包含的元素类型是未知的。 通配符所代表的其实是一组类型，但具体的类型是未知的。`List<?>`所声明的就是所有类型都是可以的。但是`List<?>`并不等同于`List<Object>`。`List<Object>`实际上确定了`List`中包含的是`Object`及其子类，在使用的时候都可以通过`Object`来进行引用。而`List<?>`则其中所包含的元素类型是不确定。其中可能包含的是`String`，也可能是 `Integer`。如果它包含了`String`的话，往里面添加`Integer`类型的元素就是错误的。正因为类型未知，就不能通过`new ArrayList<?>()`的方法来创建一个新的`ArrayList`对象。因为编译器无法知道具体的类型是什么。但是对于 `List<?>`中的元素确总是可以用`Object`来引用的，因为虽然类型未知，但肯定是`Object`及其子类。考虑下面的代码：

```
public void wildcard(List<?> list) {
    list.add(1);//编译错误 
}  
```

**如上所示，试图对一个带通配符的泛型类进行操作的时候，总是会出现编译错误。其原因在于通配符所表示的类型是未知的。**

因为对于`List<?>`中的元素只能用`Object`来引用，在有些情况下不是很方便。在这些情况下，可以使用上下界来限制未知类型的范围。 如`List<? extends Number>`说明List中可能包含的元素类型是`Number`及其子类。而`List<? super Number>`则说明`List`中包含的是`Number`及其父类。当引入了上界之后，在使用类型的时候就可以使用上界类中定义的方法。比如访问 `List<? extends Number>`的时候，就可以使用`Number`类的`intValue`等方法。

**List<Object> 和原始类型 List 之间的区别?**
原始类型和带参数类型<Object>之间的主要区别是，在编译时编译器不会对原始类型进行类型安全检查，却会对带参数的类型进行检查。



# 4. 类型系统

在Java中，大家比较熟悉的是通过继承机制而产生的类型体系结构。比如`String`继承自`Object`。根据Liskov替换原则，子类是可以替换父类的。当需要Object类的引用的时候，如果传入一个`String`对象是没有任何问题的。但是反过来的话，即用父类的引用替换子类引用的时候，就需要进行强制类型转换。编译器并不能保证运行时刻这种转换一定是合法的。这种自动的子类替换父类的类型转换机制，对于数组也是适用的。 `String[]`可以替换`Object[]`。但是泛型的引入，对于这个类型系统产生了一定的影响。正如前面提到的List是不能替换掉`List<Object>`的。

引入泛型之后的类型系统增加了两个维度：一个是类型参数自身的继承体系结构，另外一个是泛型类或接口自身的继承体系结构。第一个指的是对于 `List<String>`和`List<Object>`这样的情况，类型参数`String`是继承自`Object`的。而第二种指的是 `List`接口继承自`Collection`接口。对于这个类型系统，有如下的一些规则：

> 相同类型参数的泛型类的关系取决于泛型类自身的继承体系结构。即`List<String>`是`Collection<String>` 的子类型，`List<String>`可以替换`Collection<String>`。这种情况也适用于带有上下界的类型声明。 当泛型类的类型声明中使用了通配符的时候， 其子类型可以在两个维度上分别展开。如对`Collection<? extends Number>`来说，其子类型可以在Collection这个维度上展开，即`List<? extends Number>`和`Set<? extends Number>`等；也可以在Number这个层次上展开，即`Collection<Double>`和 `Collection<Integer>`等。如此循环下去，`ArrayList<Long>`和 `HashSet<Double>`等也都算是`Collection<? extends Number>`的子类型。 如果泛型类中包含多个类型参数，则对于每个类型参数分别应用上面的规则。

理解了上面的规则之后，就可以很容易的修正实例分析中给出的代码了。只需要把`List<Object>`改成`List<?>`即可。`List<String>`是`List<?>`的子类型，因此传递参数时不会发生错误。

# 5. 开发自己的泛型类

泛型类与一般的`Java`类基本相同，只是在类和接口定义上多出来了用`<>`声明的类型参数。一个类可以有多个类型参数，如 `MyClass<X, Y, Z>`。 每个类型参数在声明的时候可以指定上界。所声明的类型参数在Java类中可以像一般的类型一样作为方法的参数和返回值，或是作为域和局部变量的类型。但是由于类型擦除机制，类型参数并不能用来创建对象或是作为静态变量的类型。考虑下面的泛型类中的正确和错误的用法。

```java
class ClassTest<X extends Number, Y, Z> {    
    private X x;    
    private static Y y; //编译错误，不能用在静态变量中    
    public X getFirst() {
        //正确用法        
        return x;    
    }    
    public void wrong() {        
        Z z = new Z(); //编译错误，不能创建对象    
    }
}  
```

# 6. 泛型的使用

> 写在前面：泛型。很重要～



### 普通泛型

```java
class Point< T>{  // 此处可以随便写标识符号，T是type的简称  
 private T var ; // var的类型由T指定，即：由外部指定  
 public T getVar(){ // 返回值的类型由外部决定  
  return var ;  
 }  
 public void setVar(T var){ // 设置的类型也由外部决定  
  this.var = var ;  
 }  
};  
public class GenericsDemo06{  
 public static void main(String args[]){  
  Point< String> p = new Point< String>() ; // 里面的var类型为String类型  
  p.setVar("it") ;  // 设置字符串  
  System.out.println(p.getVar().length()) ; // 取得字符串的长度  
 }  
};  
```

------

```java
class Notepad< K,V>{  // 此处指定了两个泛型类型  
 private K key ;  // 此变量的类型由外部决定  
 private V value ; // 此变量的类型由外部决定  
 public K getKey(){  
  return this.key ;  
 }  
 public V getValue(){  
  return this.value ;  
 }  
 public void setKey(K key){  
  this.key = key ;  
 }  
 public void setValue(V value){  
  this.value = value ;  
 }  
};  
public class GenericsDemo09{  
 public static void main(String args[]){  
  Notepad< String,Integer> t = null ;  // 定义两个泛型类型的对象  
  t = new Notepad< String,Integer>() ;  // 里面的key为String，value为Integer  
  t.setKey("汤姆") ;  // 设置第一个内容  
  t.setValue(20) ;   // 设置第二个内容  
  System.out.print("姓名；" + t.getKey()) ;  // 取得信息  
  System.out.print("，年龄；" + t.getValue()) ;  // 取得信息  

 }  
};  
```

### 通配符

```java
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo14{  
 public static void main(String args[]){  
  Info< String> i = new Info< String>() ;  // 使用String为泛型类型  
  i.setVar("it") ;       // 设置内容  
  fun(i) ;  
 }  
 public static void fun(Info< ?> temp){  // 可以接收任意的泛型对象  
  System.out.println("内容：" + temp) ;  
 }  
};  
```

### 受限泛型

```java
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo17{  
 public static void main(String args[]){  
  Info< Integer> i1 = new Info< Integer>() ;  // 声明Integer的泛型对象  
  Info< Float> i2 = new Info< Float>() ;   // 声明Float的泛型对象  
  i1.setVar(30) ;         // 设置整数，自动装箱  
  i2.setVar(30.1f) ;        // 设置小数，自动装箱  
  fun(i1) ;  
  fun(i2) ;  
 }  
 public static void fun(Info< ? extends Number> temp){ // 只能接收Number及其Number的子类  
  System.out.print(temp + "、") ;  
 }  
};  
```

------

```java
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo21{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  // 声明String的泛型对象  
  Info< Object> i2 = new Info< Object>() ;  // 声明Object的泛型对象  
  i1.setVar("hello") ;  
  i2.setVar(new Object()) ;  
  fun(i1) ;  
  fun(i2) ;  
 }  
 public static void fun(Info< ? super String> temp){ // 只能接收String或Object类型的泛型  
  System.out.print(temp + "、") ;  
 }  
};  
```

### Java泛型无法向上转型

```java
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo23{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  // 泛型类型为String  
  Info< Object> i2 = null ;  
  i2 = i1 ;        //这句会出错 incompatible types  
 }  
};  
```

### Java泛型接口

```java
interface Info< T>{  // 在接口上定义泛型  
 public T getVar() ; // 定义抽象方法，抽象方法的返回值就是泛型类型  
}  
class InfoImpl< T> implements Info< T>{ // 定义泛型接口的子类  
 private T var ;    // 定义属性  
 public InfoImpl(T var){  // 通过构造方法设置属性内容  
  this.setVar(var) ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
};  
public class GenericsDemo24{  
 public static void main(String arsg[]){  
  Info< String> i = null;  // 声明接口对象  
  i = new InfoImpl< String>("汤姆") ; // 通过子类实例化对象  
  System.out.println("内容：" + i.getVar()) ;  
 }  
};  
```

------

```java
interface Info< T>{  // 在接口上定义泛型  
 public T getVar() ; // 定义抽象方法，抽象方法的返回值就是泛型类型  
}  
class InfoImpl implements Info< String>{ // 定义泛型接口的子类  
 private String var ;    // 定义属性  
 public InfoImpl(String var){  // 通过构造方法设置属性内容  
  this.setVar(var) ;   
 }  
 public void setVar(String var){  
  this.var = var ;  
 }  
 public String getVar(){  
  return this.var ;  
 }  
};  
public class GenericsDemo25{  
 public static void main(String arsg[]){  
  Info i = null;  // 声明接口对象  
  i = new InfoImpl("汤姆") ; // 通过子类实例化对象  
  System.out.println("内容：" + i.getVar()) ;  
 }  
};  
```

### Java泛型方法

```java
class Demo{  
 public < T> T fun(T t){   // 可以接收任意类型的数据  
  return t ;     // 直接把参数返回  
 }  
};  
public class GenericsDemo26{  
 public static void main(String args[]){  
  Demo d = new Demo() ; // 实例化Demo对象  
  String str = d.fun("汤姆") ; // 传递字符串  
  int i = d.fun(30) ;  // 传递数字，自动装箱  
  System.out.println(str) ; // 输出内容  
  System.out.println(i) ;  // 输出内容  
 }  
};  
```

### 通过泛型方法返回泛型类型实例

```java
class Info< T extends Number>{ // 指定上限，只能是数字类型  
 private T var ;  // 此类型由外部决定  
 public T getVar(){  
  return this.var ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public String toString(){  // 覆写Object类中的toString()方法  
  return this.var.toString() ;   
 }  
};  
public class GenericsDemo27{  
 public static void main(String args[]){  
  Info< Integer> i = fun(30) ;  
  System.out.println(i.getVar()) ;  
 }  
 public static < T extends Number> Info< T> fun(T param){//方法中传入或返回的泛型类型由调用方法时所设置的参数类型决定  
  Info< T> temp = new Info< T>() ;  // 根据传入的数据类型实例化Info  
  temp.setVar(param) ;  // 将传递的内容设置到Info对象的var属性之中  
  return temp ; // 返回实例化对象  
 }  
};  
```

### 使用泛型统一传入的参数类型

```java
class Info< T>{ // 指定上限，只能是数字类型  
 private T var ;  // 此类型由外部决定  
 public T getVar(){  
  return this.var ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public String toString(){  // 覆写Object类中的toString()方法  
  return this.var.toString() ;   
 }  
};  
public class GenericsDemo28{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  
  Info< String> i2 = new Info< String>() ;  
  i1.setVar("HELLO") ;  // 设置内容  
  i2.setVar("汤姆") ;  // 设置内容  
  add(i1,i2) ;  
 }  
 public static < T> void add(Info< T> i1,Info< T> i2){  
  System.out.println(i1.getVar() + " " + i2.getVar()) ;  
 }  
};  
```

### Java泛型数组

```java
public class GenericsDemo30{  
 public static void main(String args[]){  
  Integer i[] = fun1(1,2,3,4,5,6) ; // 返回泛型数组  
  fun2(i) ;  
 }  
 public static < T> T[] fun1(T...arg){ // 接收可变参数  
  return arg ;   // 返回泛型数组  
 }  
 public static < T> void fun2(T param[]){ // 输出  
  System.out.print("接收泛型数组：") ;  
  for(T t:param){  
   System.out.print(t + "、") ;  
  }  
 }  
};  
```

### Java泛型的嵌套设置

```java
class Info< T,V>{  // 接收两个泛型类型  
 private T var ;  
 private V value ;  
 public Info(T var,V value){  
  this.setVar(var) ;  
  this.setValue(value) ;  
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public void setValue(V value){  
  this.value = value ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public V getValue(){  
  return this.value ;  
 }  
};  
class Demo< S>{  
 private S info ;  
 public Demo(S info){  
  this.setInfo(info) ;  
 }  
 public void setInfo(S info){  
  this.info = info ;  
 }  
 public S getInfo(){  
  return this.info ;  
 }  
};  
public class GenericsDemo31{  
 public static void main(String args[]){  
  Demo< Info< String,Integer>> d = null ;  // 将Info作为Demo的泛型类型  
  Info< String,Integer> i = null ; // Info指定两个泛型类型  
  i = new Info< String,Integer>("汤姆",30) ;  // 实例化Info对象  
  d = new Demo< Info< String,Integer>>(i) ; // 在Demo类中设置Info类的对象  
  System.out.println("内容一：" + d.getInfo().getVar()) ;  
  System.out.println("内容二：" + d.getInfo().getValue()) ;  
 }  
};  
```

# 7. 泛型擦除

## 前言

> 写在前面:最近在看泛型,研究泛型的过程中,发现了一个比较令我意外的情况,Java中的泛型基本上都是在编译器这个层次来实现的。在**生成的Java字节代码中是不包含泛型中的类型信息**的。使用泛型的时候加上的类型参数，会被编译器在编译的时候去掉。 其实编译器通过Code sharing方式为每个泛型类型创建唯一的字节码表示，并且将该泛型类型的实例都映射到这个唯一的字节码表示上。将多种泛型类形实例映射到唯一的字节码表示是通过**类型擦除**（`type erasue`）实现的。



类型擦除,嘿嘿,第一次听说的东西,很好奇,于是上网查了查,把官方解释贴在下面,应该可以看得懂[JavaDoc](http://docs.oracle.com/javase/tutorial/java/generics/erasure.html)

> **Type Erasure** Generics were introduced to the Java language to provide tighter type checks at compile time and to support generic programming. To implement generics, the Java compiler applies type erasure to: Replace all type parameters in generic types with their bounds or Object if the type parameters are unbounded. The produced bytecode, therefore, contains only ordinary classes, interfaces, and methods. Insert type casts if necessary to preserve type safety. Generate bridge methods to preserve polymorphism in extended generic types. Type erasure ensures that no new classes are created for parameterized types; consequently, generics incur no runtime overhead.

## 7.1 各种语言中的编译器是如何处理泛型的

通常情况下，一个编译器处理泛型有两种方式：

1.`Code specialization`。在实例化一个泛型类或泛型方法时都产生一份新的目标代码（字节码or二进制代码）。例如，针对一个泛型`list`，可能需要 针对`string`，`integer`，`float`产生三份目标代码。

2.`Code sharing`。对每个泛型类只生成唯一的一份目标代码；该泛型类的所有实例都映射到这份目标代码上，在需要的时候执行类型检查和类型转换。

**C++**中的模板（`template`）是典型的`Code specialization`实现。**C++**编译器会为每一个泛型类实例生成一份执行代码。执行代码中`integer list`和`string list`是两种不同的类型。这样会导致**代码膨胀（code bloat）**。 **C#**里面泛型无论在程序源码中、编译后的`IL`中（Intermediate Language，中间语言，这时候泛型是一个占位符）或是运行期的CLR中都是切实存在的，`List<int>`与`List<String>`就是两个不同的类型，它们在系统运行期生成，有自己的虚方法表和类型数据，这种实现称为类型膨胀，基于这种方法实现的泛型被称为`真实泛型`。 **Java**语言中的泛型则不一样，它只在程序源码中存在，在编译后的字节码文件中，就已经被替换为原来的原生类型（Raw Type，也称为裸类型）了，并且在相应的地方插入了强制转型代码，因此对于运行期的Java语言来说，`ArrayList<int>`与`ArrayList<String>`就是同一个类。所以说泛型技术实际上是Java语言的一颗语法糖，Java语言中的泛型实现方法称为**类型擦除**，基于这种方法实现的泛型被称为`伪泛型`。

`C++`和`C#`是使用`Code specialization`的处理机制，前面提到，他有一个缺点，那就是**会导致代码膨胀**。另外一个弊端是在引用类型系统中，浪费空间，因为引用类型集合中元素本质上都是一个指针。没必要为每个类型都产生一份执行代码。而这也是Java编译器中采用`Code sharing`方式处理泛型的主要原因。

`Java`编译器通过`Code sharing`方式为每个泛型类型创建唯一的字节码表示，并且将该泛型类型的实例都映射到这个唯一的字节码表示上。将多种泛型类形实例映射到唯一的字节码表示是通过**类型擦除**（`type erasue`）实现的。

## 7.2 什么是类型擦除

前面我们多次提到这个词：**类型擦除**（`type erasue`） ，那么到底什么是类型擦除呢？

> 类型擦除指的是通过类型参数合并，将泛型类型实例关联到同一份字节码上。编译器只为泛型类型生成一份字节码，并将其实例关联到这份字节码上。类型擦除的关键在于从泛型类型中清除类型参数的相关信息，并且再必要的时候添加类型检查和类型转换的方法。 
>
> 类型擦除可以简单的理解为将泛型java代码转换为普通java代码，只不过编译器更直接点，将泛型java代码直接转换成普通java字节码。 类型擦除的主要过程如下： 1.将所有的泛型参数用其最左边界（最顶级的父类型）类型替换。（这部分内容可以看：[Java泛型中extends和super的理解](http://www.hollischuang.com/archives/255)） 2.移除所有的类型参数。

## 7.3 Java编译器处理泛型的过程

**code 1:**

```java
public static void main(String[] args) {  
    Map<String, String> map = new HashMap<String, String>();  
    map.put("name", "hollis");  
    map.put("age", "22");  
    System.out.println(map.get("name"));  
    System.out.println(map.get("age"));  
}  
```

**反编译后的code 1:**

```java
public static void main(String[] args) {  
    Map map = new HashMap();  
    map.put("name", "hollis");  
    map.put("age", "22"); 
    System.out.println((String) map.get("name"));  
    System.out.println((String) map.get("age"));  
}  
```

我们发现泛型都不见了，程序又变回了Java泛型出现之前的写法，泛型类型都变回了原生类型，

------

**code 2:**

```java
interface Comparable<A> {
    public int compareTo(A that);
}

public final class NumericValue implements Comparable<NumericValue> {
    private byte value;

    public NumericValue(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public int compareTo(NumericValue that) {
        return this.value - that.value;
    }
}
```

**反编译后的code 2:**

```java
 interface Comparable {
  public int compareTo( Object that);
} 

public final class NumericValue
    implements Comparable
{
    public NumericValue(byte value)
    {
        this.value = value;
    }
    public byte getValue()
    {
        return value;
    }
    public int compareTo(NumericValue that)
    {
        return value - that.value;
    }
    public volatile int compareTo(Object obj)
    {
        return compareTo((NumericValue)obj);
    }
    private byte value;
}
```

------

**code 3:**

```java
public class Collections {
    public static <A extends Comparable<A>> A max(Collection<A> xs) {
        Iterator<A> xi = xs.iterator();
        A w = xi.next();
        while (xi.hasNext()) {
            A x = xi.next();
            if (w.compareTo(x) < 0)
                w = x;
        }
        return w;
    }
}
```

**反编译后的code 3:**

```java
public class Collections
{
    public Collections()
    {
    }
    public static Comparable max(Collection xs)
    {
        Iterator xi = xs.iterator();
        Comparable w = (Comparable)xi.next();
        while(xi.hasNext())
        {
            Comparable x = (Comparable)xi.next();
            if(w.compareTo(x) < 0)
                w = x;
        }
        return w;
    }
}
```

第2个泛型类`Comparable <A>`擦除后 A被替换为最左边界`Object`。`Comparable<NumericValue>`的类型参数`NumericValue`被擦除掉，但是这直 接导致`NumericValue`没有实现接口`Comparable的compareTo(Object that)`方法，于是编译器充当好人，添加了一个**桥接方法**。 第3个示例中限定了类型参数的边界`<A extends Comparable<A>>A`，A必须为`Comparable<A>`的子类，按照类型擦除的过程，先讲所有的类型参数 ti换为最左边界`Comparable<A>`，然后去掉参数类型`A`，得到最终的擦除后结果。

## 7.4 泛型带来的问题

### 7.4.1 **当泛型遇到重载：**

```java
public class GenericTypes {  

    public static void method(List<String> list) {  
        System.out.println("invoke method(List<String> list)");  
    }  

    public static void method(List<Integer> list) {  
        System.out.println("invoke method(List<Integer> list)");  
    }  
}  
```

上面这段代码，有两个重载的函数，因为他们的参数类型不同，一个是`List<String>`另一个是`List<Integer>` ，但是，这段代码是编译通不过的。因为我们前面讲过，参数`List<Integer>`和`List<String>`编译之后都被擦除了，变成了一样的原生类型List，擦除动作导致这两个方法的特征签名变得一模一样。

### 7.4.2 **当泛型遇到catch:**

如果我们自定义了一个泛型异常类GenericException，那么，不要尝试用多个catch取匹配不同的异常类型，例如你想要分别捕获GenericException、GenericException，这也是有问题的。

### 7.4.3 当泛型内包含静态变量

```java
public class StaticTest{
    public static void main(String[] args){
        GT<Integer> gti = new GT<Integer>();
        gti.var=1;
        GT<String> gts = new GT<String>();
        gts.var=2;
        System.out.println(gti.var);
    }
}
class GT<T>{
    public static int var=0;
    public void nothing(T x){}
}
```

答案是——2！由于经过类型擦除，所有的泛型类实例都关联到同一份字节码上，泛型类的所有静态变量是共享的。

## 7.5 总结

1.虚拟机中没有泛型，只有普通类和普通方法,所有泛型类的类型参数在编译时都会被擦除,泛型类并没有自己独有的Class类对象。比如并不存在`List<String>`.class或是`List<Integer>.class`，而只有`List.class`。 

2.创建泛型对象时请指明类型，让编译器尽早的做参数检查（**Effective Java，第23条：请不要在新代码中使用原生态类型**） 

3.不要忽略编译器的警告信息，那意味着潜在的`ClassCastException`等着你。 

4.静态变量是被泛型类的所有实例所共享的。对于声明为`MyClass<T>`的类，访问其中的静态变量的方法仍然是 `MyClass.myStaticVar`。不管是通过`new MyClass<String>`还是`new MyClass<Integer>`创建的对象，都是共享一个静态变量。

 5.泛型的类型参数不能用在`Java`异常处理的`catch`语句中。因为异常处理是由JVM在运行时刻来进行的。由于类型信息被擦除，`JVM`是无法区分两个异常类型`MyException<String>`和`MyException<Integer>`的。对于`JVM`来说，它们都是 `MyException`类型的。也就无法执行与异常对应的`catch`语句。

## 7.6 总结

在使用泛型的时候可以遵循一些基本的原则，从而避免一些常见的问题。

> **在代码中避免泛型类和原始类型的混用(Effective Java中建议不要在代码中使用原始类型)**。比如List和List<String>不应该共同使用。这样会产生一些编译器警告和潜在的运行时异常。当需要利用JDK 5之前开发的遗留代码，而不得不这么做时，也尽可能的隔离相关的代码。 在使用带通配符的泛型类的时候，需要明确通配符所代表的一组类型的概念。由于具体的类型是未知的，很多操作是不允许的。 泛型类最好不要同数组一块使用。你只能创建new List<?>[10]这样的数组，无法创建new List[10]这样的。这限制了数组的使用能力，而且会带来很多费解的问题。因此，当需要类似数组的功能时候，使用集合类即可。 不要忽视编译器给出的警告信息。

参考资料：

[Java深度历险（五）——Java泛型](
