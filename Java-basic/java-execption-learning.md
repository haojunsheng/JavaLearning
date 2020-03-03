<!--ts-->
   * [前言](#前言)
   * [java异常机制](#java异常机制)
      * [1. 为什么要使用异常](#1-为什么要使用异常)
      * [2. 基本定义](#2-基本定义)
      * [3. 异常体系](#3-异常体系)
      * [4. 异常使用](#4-异常使用)
      * [5. 自定义异常](#5-自定义异常)
      * [6. 异常链](#6-异常链)
      * [7. <strong>异常的使用误区</strong>](#7-异常的使用误区)
   * [Java异常处理的陋习展播](#java异常处理的陋习展播)
      * [反例之一：丢弃异常](#反例之一丢弃异常)
      * [反例之二：不指定具体的异常](#反例之二不指定具体的异常)
      * [反例之三：占用资源不释放](#反例之三占用资源不释放)
      * [反例之四：不说明异常的详细信息](#反例之四不说明异常的详细信息)
      * [反例之五：过于庞大的try块](#反例之五过于庞大的try块)
      * [反例之六：输出数据不完整](#反例之六输出数据不完整)
      * [改写后的代码](#改写后的代码)
   * [异常处理的 15 个处理原则](#异常处理的-15-个处理原则)

<!-- Added by: anapodoton, at: Tue Mar  3 14:34:56 CST 2020 -->

<!--te-->

# 前言

# java异常机制

> Java的基本理念是“结构不佳的代码不能运行”！！！！！

大成若缺，其用不弊。

大盈若冲，其用不穷。

在这个世界不可能存在完美的东西，不管完美的思维有多么缜密，细心，我们都不可能考虑所有的因素，这就是所谓的智者千虑必有一失。同样的道理，计算机的世界也是不完美的，异常情况随时都会发生，我们所需要做的就是避免那些能够避免的异常，处理那些不能避免的异常。这里我将记录如何利用异常还程序一个“完美世界”。

## 1. 为什么要使用异常

首先我们可以明确一点就是异常的处理机制可以确保我们程序的健壮性，提高系统可用率。虽然我们不是特别喜欢看到它，但是我们不能不承认它的地位，作用。有异常就说明程序存在问题，有助于我们及时改正。在我们的程序设计当中，任何时候任何地方因为任何原因都有可能会出现异常，在没有异常机制的时候我们是这样处理的：通过函数的返回值来判断是否发生了异常（这个返回值通常是已经约定好了的），调用该函数的程序负责检查并且分析返回值。虽然可以解决异常问题，但是这样做存在几个缺陷：

- 1、容易混淆。如果约定返回值为-11111时表示出现异常，那么当程序最后的计算结果真的为-1111呢？
- 2、代码可读性差。将异常处理代码和程序代码混淆在一起将会降低代码的可读性。
- 3、由调用函数来分析异常，这要求程序员对库函数有很深的了解。

在OO中提供的异常处理机制是提供代码健壮的强有力的方式。使用异常机制它能够降低错误处理代码的复杂度，如果不使用异常，那么就必须检查特定的错误，并在程序中的许多地方去处理它，而如果使用异常，那就不必在方法调用处进行检查，因为异常机制将保证能够捕获这个错误，并且，只需在一个地方处理错误，即所谓的异常处理程序中。这种方式不仅节约代码，而且把“概述在正常执行过程中做什么事”的代码和“出了问题怎么办”的代码相分离。总之，与以前的错误处理方法相比，异常机制使代码的阅读、编写和调试工作更加井井有条。（摘自《Think in java 》）。

在初学时，总是听老师说把有可能出错的地方记得加异常处理，刚刚开始还不明白，有时候还觉得只是多此一举，现在随着自己的不断深入，代码编写多了，渐渐明白了异常是非常重要的。

## 2. 基本定义

在《Think in java》中是这样定义异常的：异常情形是指阻止当前方法或者作用域继续执行的问题。在这里一定要明确一点：异常代码某种程度的错误，尽管Java有异常处理机制，但是我们不能以“正常”的眼光来看待异常，异常处理机制的原因就是告诉你：这里可能会或者已经产生了错误，您的程序出现了不正常的情况，可能会导致程序失败！

**那么什么时候才会出现异常呢？只有在你当前的环境下程序无法正常运行下去，也就是说程序已经无法来正确解决问题了，这时它所就会从当前环境中跳出，并抛出异常。抛出异常后，它首先会做几件事。首先，它会使用new创建一个异常对象，然后在产生异常的位置终止程序，并且从当前环境中弹出对异常对象的引用，这时。异常处理机制就会接管程序，并开始寻找一个恰当的地方来继续执行程序，这个恰当的地方就是异常处理程序，它的任务就是将程序从错误状态恢复，以使程序要么换一种方法执行，要么继续执行下去。**

​     总的来说异常处理机制就是当程序发生异常时，它强制终止程序运行，记录异常信息并将这些信息反馈给我们，由我们来确定是否处理异常。

## 3. 异常体系

java为我们提供了非常完美的异常处理机制，使得我们可以更加专心于我们的程序，在使用异常之前我们需要了解它的体系结构：如下

![1354439580_6933](https://ws3.sinaimg.cn/large/006tKfTcly1g0d4e9rzkpj30gk0j20t9.jpg)

从上面这幅图可以看出，Throwable是java语言中所有错误和异常的超类（万物即可抛）。它有两个子类：Error、Exception。

其中Error为错误，是程序无法处理的，如OutOfMemoryError、ThreadDeath等，出现这种情况你唯一能做的就是听之任之，交由JVM来处理，不过JVM在大多数情况下会选择终止线程。

而Exception是程序可以处理的异常。它又分为两种CheckedException（受捡异常），一种是UncheckedException（不受检异常）。其中CheckException发生在编译阶段，必须要使用try…catch（或者throws）否则编译不通过。而UncheckedException发生在运行期，具有不确定性，主要是由于程序的逻辑问题所引起的，难以排查，我们一般都需要纵观全局才能够发现这类的异常错误，所以在程序设计中我们需要认真考虑，好好写代码，尽量处理异常，即使产生了异常，也能尽量保证程序朝着有利方向发展。

所以：对于可恢复的条件使用被检查的异常（CheckedException），对于程序错误（言外之意不可恢复，大错已经酿成）使用运行时异常（RuntimeException）。

**java的异常类实在是太多了，产生的原因也千变万化，所以下篇博文我将会整理，统计java中经常出现的异常，望各位关注！！**

## 4. 异常使用

在网上看了这样一个搞笑的话：世界上最真情的相依，是你在try我在catch。无论你发神马脾气，我都默默承受，静静处理。对于初学者来说异常就是try…catch，（鄙人刚刚接触时也是这么认为的，碰到异常就是try…catch）。个人感觉try…catch确实是用的最多也是最实用的。

在异常中try快包含着可能出现异常的代码块，catch块捕获异常后对异常进行处理。先看如下实例：

```java
public class ExceptionTest {
    public static void main(String[] args) {
        String file = "D:\\exceptionTest.txt";
        FileReader reader;
        try {
            reader = new FileReader(file);
            Scanner in = new Scanner(reader);  
            String string = in.next();  
            System.out.println(string + "不知道我有幸能够执行到不.....");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("对不起,你执行不到...");
        }  
        finally{
            System.out.println("finally 在执行...");
        }
    }
}
```

这是段非常简单的程序，用于读取D盘目录下的exceptionText.txt文件，同时读取其中的内容、输出。首先D盘没有该文件，运行程序结果如下：

```
java.io.FileNotFoundException: D:\exceptionTest.txt (系统找不到指定的文件。)
    at java.io.FileInputStream.open(Native Method)
    at java.io.FileInputStream.<init>(FileInputStream.java:106)
    at java.io.FileInputStream.<init>(FileInputStream.java:66)
    at java.io.FileReader.<init>(FileReader.java:41)
    at com.test9.ExceptionTest.main(ExceptionTest.java:19)
对不起,你执行不到...
finally 在执行...
```

从这个结果我们可以看出这些：

- 1、当程序遇到异常时会终止程序的运行（即后面的代码不在执行），控制权交由异常处理机制处理。
- 2、catch捕捉异常后，执行里面的函数。

当我们在D盘目录下新建一个exceptionTest.txt文件后，运行程序结果如下：

```
1111不知道我有幸能够执行到不.....
finally 在执行...
```

11111是该文件中的内容。从这个运行结果可以得出这个结果：**不论程序是否发生异常，finally代码块总是会执行。所以finally一般用来关闭资源。**

在这里我们在看如下程序：

```java
public class ExceptionTest {
    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        System.out.println(a[4]);
        System.out.println("我执行了吗???");
    }
}
```

程序运行结果：

```
 Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 4
    at com.test9.ExceptionTest.main(ExceptionTest.java:14)

       各位请注意这个异常信息和上面的异常信息错误，为了看得更加清楚，我将他们列在一起：

 java.io.FileNotFoundException: D:\exceptionTest.txt (系统找不到指定的文件。)
        Exception in thread "main" **java.lang.ArrayIndexOutOfBoundsException:** 4
```

在这里我们发现两个异常之间存在如下区别：第二个异常信息多了Exception in thread “main”，这显示了出现异常信息的位置。在这里可以得到如下结论：

**若程序中显示的声明了某个异常，则抛出异常时不会显示出处，若程序中没有显示的声明某个异常，当抛出异常时，系统会显示异常的出处。**

## 5. 自定义异常

Java确实给我们提供了非常多的异常，但是异常体系是不可能预见所有的希望加以报告的错误，所以Java允许我们自定义异常来表现程序中可能会遇到的特定问题，总之就是一句话：我们不必拘泥于Java中已有的异常类型。

Java自定义异常的使用要经历如下四个步骤：

- 1、定义一个类继承Throwable或其子类。
- 2、添加构造方法(当然也可以不用添加，使用默认构造方法)。
- 3、在某个方法类抛出该异常。
- 4、捕捉该异常。

```java
/** 自定义异常 继承Exception类 **/
public class MyException extends Exception{
    public MyException(){

    }

    public MyException(String message){
        super(message);
    }
}

public class Test {
    public void display(int i) throws MyException{
        if(i == 0){
            throw new MyException("该值不能为0.......");
        }
        else{
            System.out.println( i / 2);
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        try {
            test.display(0);
            System.out.println("---------------------");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果：

![1111](https://ws3.sinaimg.cn/large/006tKfTcly1g0d4scs5ayj30bg0280sn.jpg)

## 6. 异常链

在设计模式中有一个叫做责任链模式，该模式是将多个对象链接成一条链，客户端的请求沿着这条链传递直到被接收、处理。同样Java异常机制也提供了这样一条链：异常链。

我们知道每遇到一个异常信息，我们都需要进行try…catch,一个还好，如果出现多个异常呢？分类处理肯定会比较麻烦，那就一个Exception解决所有的异常吧。这样确实是可以，但是这样处理势必会导致后面的维护难度增加。最好的办法就是将这些异常信息封装，然后捕获我们的封装类即可。

诚然在应用程序中，我们有时候不仅仅只需要封装异常，更需要传递。怎么传递？throws!！binge，正确！！但是如果仅仅只用throws抛出异常，那么你的封装类，怎么办？？

我们有两种方式处理异常，一是throws抛出交给上级处理，二是try…catch做具体处理。但是这个与上面有什么关联呢？try…catch的catch块我们可以不需要做任何处理，仅仅只用throw这个关键字将我们封装异常信息主动抛出来。然后在通过关键字throws继续抛出该方法异常。它的上层也可以做这样的处理，以此类推就会产生一条由异常构成的异常链。

**通过使用异常链，我们可以提高代码的可理解性、系统的可维护性和友好性。**

同理，我们有时候在捕获一个异常后抛出另一个异常信息，并且希望将原始的异常信息也保持起来，这个时候也需要使用异常链。

在异常链的使用中，throw抛出的是一个新的异常信息，这样势必会导致原有的异常信息丢失，如何保持？在Throwable及其子类中的构造器中都可以接受一个cause参数，该参数保存了原有的异常信息，通过getCause()就可以获取该原始异常信息。

语法：

```java
public void test() throws XxxException{
        try {
            //do something:可能抛出异常信息的代码块
        } catch (Exception e) {
            throw new XxxException(e);
        }
    }
```

示例：

```java
public class Test {
    public void f() throws MyException{
         try {
            FileReader reader = new FileReader("G:\\myfile\\struts.txt");  
             Scanner in = new Scanner(reader);  
             System.out.println(in.next());
        } catch (FileNotFoundException e) {
            //e 保存异常信息
            throw new MyException("文件没有找到--01",e);
        }  
    }

    public void g() throws MyException{
        try {
            f();
        } catch (MyException e) {
            //e 保存异常信息
            throw new MyException("文件没有找到--02",e);
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        try {
            t.g();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果:

```
com.test9.MyException: 文件没有找到--02
    at com.test9.Test.g(Test.java:31)
    at com.test9.Test.main(Test.java:38)
Caused by: com.test9.MyException: 文件没有找到--01
    at com.test9.Test.f(Test.java:22)
    at com.test9.Test.g(Test.java:28)
    ... 1 more
Caused by: java.io.FileNotFoundException: G:\myfile\struts.txt (系统找不到指定的路径。)
    at java.io.FileInputStream.open(Native Method)
    at java.io.FileInputStream.<init>(FileInputStream.java:106)
    at java.io.FileInputStream.<init>(FileInputStream.java:66)
    at java.io.FileReader.<init>(FileReader.java:41)
    at com.test9.Test.f(Test.java:17)
    ... 2 more
```

如果在程序中,去掉e，也就是：throw new MyException(“文件没有找到–02”);

那么异常信息就保存不了，运行结果如下：

```
 com.test9.MyException: 文件没有找到--02
    at com.test9.Test.g(Test.java:31)
    at com.test9.Test.main(Test.java:38)
```

**PS:其实对于异常链鄙人使用的也不是很多，理解的不是很清楚，望各位指正！！！！_**

## 7. **异常的使用误区**

首先我们先看如下示例：该实例能够反映java异常的不正确使用（其实这也是我刚刚学Java时写的代码）！！

```java
OutputStreamWriter out = null;
        java.sql.Connection conn = null;
        try {            //   ---------1
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select *from user");
            while (rs.next()){
                out.println("name:" + rs.getString("name") + "sex:"
                        + rs.getString("sex"));
            }
            conn.close();         //------2
            out.close();
        }
        catch (Exception ex){    //------3
            ex.printStackTrace();    //------4
        }
```

对于这个try…catch块，我想他的真正目的是捕获SQL的异常，但是这个try块是不是包含了太多的信息了。这是我们为了偷懒而养成的代码坏习惯。有些人喜欢将一大块的代码全部包含在一个try块里面，因为这样省事，反正有异常它就会抛出，而不愿意花时间来分析这个大代码块有那几块会产生异常，产生什么类型的异常，反正就是一篓子全部搞定。这就想我们出去旅游将所有的东西全部装进一个箱子里面，而不是分类来装，虽不知装进去容易，找出来难啊！！！所有对于一个异常块，我们应该仔细分清楚每块的抛出异常，因为一个大代码块有太多的地方会出现异常了。

**结论一：尽可能的减小try块！！！**

在这里你发现了什么？异常改变了运行流程！！不错就是异常改变了程序运行流程。如果该程序发生了异常那么conn.close(); out.close();是不可能执行得到的，这样势必会导致资源不能释放掉。所以如果程序用到了文件、Socket、JDBC连接之类的资源，即使遇到了异常，我们也要确保能够正确释放占用的资源。这里finally就有用武之地了：不管是否出现了异常，finally总是有机会运行的，所以finally用于释放资源是再适合不过了。

**结论二：保证所有资源都被正确释放。充分运用finally关键词。** 

对于这个代码我想大部分人都是这样处理的，使用这样代码的人都有这样一个心理，一个catch解决所有异常，这样是可以，但是不推荐！为什么！首先我们需要明白catch块所表示是它预期会出现何种异常，并且需要做何种处理，而使用Exception就表示他要处理所有的异常信息，但是这样做有什么意义呢？

这里我们再来看看上面的程序实例，很显然它可能需要抛出两个异常信息，SQLException和IOException。所以一个catch处理两个截然不同的Exception明显的不合适。如果用两个catch，一个处理SQLException、一个处理IOException就好多了。所以：

**结论三：catch语句应当尽量指定具体的异常类型，而不应该指定涵盖范围太广的Exception类。 不要一个Exception试图处理所有可能出现的异常。** 

这个就问题多多了，我敢保证几乎所有的人都这么使用过。这里涉及到了两个问题，一是，捕获了异常不做处理，二是异常信息不够明确。

4.1、捕获异常不做处理，就是我们所谓的丢弃异常。我们都知道异常意味着程序出现了不可预期的问题，程序它希望我们能够做出处理来拯救它，但是你呢？一句ex.printStackTrace()搞定，这是多么的不负责任对程序的异常情况不理不顾。虽然这样在调试可能会有一定的帮助，但是调试阶段结束后呢？不是一句ex.printStackTrace()就可以搞定所有的事情的！

那么怎么改进呢？有四种选择：

- 1、处理异常。对所发生的的异常进行一番处理，如修正错误、提醒。再次申明ex.printStackTrace()算不上已经“处理好了异常”.
- 2、重新抛出异常。既然你认为你没有能力处理该异常，那么你就尽情向上抛吧！！！
- 3、封装异常。这是LZ认为最好的处理方法，对异常信息进行分类，然后进行封装处理。
- 4、不要捕获异常。

4.2、异常信息不明确。我想对于这样的：java.io.FileNotFoundException: ………信息除了我们IT人没有几个人看得懂和想看吧！所以在出现异常后，我们最好能够提供一些文字信息，例如当前正在执行的类、方法和其他状态信息，包括以一种更适合阅读的方式整理和组织printStackTrace提供的信息。起码我公司是需要将异常信息所在的类、方法、何种异常都需要记录在日志文件中的。

所以：

- **结论四：既然捕获了异常，就要对它进行适当的处理。不要捕获异常之后又把它丢弃，不予理睬。 不要做一个不负责的人。**
- **结论五：在异常处理模块中提供适量的错误原因信息，组织错误信息使其易于理解和阅读。**

对于异常还有以下几个注意地方：

**六、不要在finally块中处理返回值。**

**七、不要在构造函数中抛出异常。**

八、**try…catch、throw、throws**

在这里主要是区分throw和throws。

throws是方法抛出异常。在方法声明中，如果添加了throws子句，表示该方法即将抛出异常，异常的处理交由它的调用者，至于调用者任何处理则不是它的责任范围内的了。所以如果一个方法会有异常发生时，但是又不想处理或者没有能力处理，就使用throws吧！

而throw是语句抛出异常。它不可以单独使用，要么与try…catch配套使用，要么与throws配套使用。

```
//使用throws抛出异常
    public void f() throws MyException{
         try {
            FileReader reader = new FileReader("G:\\myfile\\struts.txt");  
             Scanner in = new Scanner(reader);  
             System.out.println(in.next());
        } catch (FileNotFoundException e) {
            throw new MyException("文件没有找到", e);    //throw
        }  

    }
```

8. 总结

其实对于异常使用的优缺点现在确实存在很多的讨论。例如：<http://www.cnblogs.com/mailingfeng/archive/2012/11/14/2769974.html>。这篇博文对于是否需要使用异常进行了比较深刻的讨论。LZ实乃菜鸟一枚，不能理解异常深奥之处。但是有一点LZ可以肯定，那就是异常必定会影响系统的性能。

异常使用指南（摘自：Think in java）

应该在下列情况下使用异常。

- 1、在恰当的级别处理问题（在知道该如何处理异常的情况下才捕获异常）。
- 2、解决问题并且重新调用产生异常的方法。
- 3、进行少许修补，然后绕过异常发生的地方继续执行。
- 4、用别的数据进行计算，以代替方法预计会返回的值。
- 5、把当前运行环境下能做的事情尽量做完。然后把相同（不同）的异常重新抛到更高层。
- 6、终止程序。
- 7、进行简化。
- 8、让类库和程序更加安全。（这既是在为调试做短期投资，也是在为程序的健壮做长期投资）

# Java异常处理的陋习展播

你觉得自己是一个Java专家吗？是否肯定自己已经全面掌握了Java的异常处理机制？在下面这段代码中，你能够迅速找出异常处理的六个问题吗？

```
OutputStreamWriter out = ... 
java.sql.Connection conn = ... 
try { // ⑸ 
 　Statement stat = conn.createStatement(); 
 　ResultSet rs = stat.executeQuery( 
 　　"select uid, name from user"); 
 　while (rs.next()) 
 　{ 
 　　out.println("ID：" + rs.getString("uid") // ⑹ 
 　　　"，姓名：" + rs.getString("name")); 
 　} 
 　conn.close(); // ⑶ 
 　out.close(); 
 } 
 catch(Exception ex) // ⑵ 
 { 
 　ex.printStackTrace(); //⑴,⑷ 
 }
```

**作为一个Java程序员，你至少应该能够找出两个问题。但是，如果你不能找出全部六个问题，请继续阅读本文。**

本文讨论的不是Java异常处理的一般性原则，因为这些原则已经被大多数人熟知。我们要做的是分析各种可称为“反例”（anti-pattern）的违背优秀编码规范的常见坏习惯，帮助读者熟悉这些典型的反面例子，从而能够在实际工作中敏锐地察觉和避免这些问题。

## 反例之一：丢弃异常

代码：15行-18行。

这段代码捕获了异常却不作任何处理，可以算得上Java编程中的杀手。从问题出现的频繁程度和祸害程度来看，它也许可以和C/C++程序的一个恶名远播的问题相提并论??不检查缓冲区是否已满。如果你看到了这种丢弃（而不是抛出）异常的情况，可以百分之九十九地肯定代码存在问题（在极少数情况下，这段代码有存在的理由，但最好加上完整的注释，以免引起别人误解）。

这段代码的错误在于，异常（几乎）总是意味着某些事情不对劲了，或者说至少发生了某些不寻常的事情，我们不应该对程序发出的求救信号保持沉默和无动于衷。调用一下printStackTrace算不上“处理异常”。不错，调用printStackTrace对调试程序有帮助，但程序调试阶段结束之后，printStackTrace就不应再在异常处理模块中担负主要责任了。

丢弃异常的情形非常普遍。打开JDK的ThreadDeath类的文档，可以看到下面这段说明：“特别地，虽然出现ThreadDeath是一种‘正常的情形’，但ThreadDeath类是Error而不是Exception的子类，因为许多应用会捕获所有的Exception然后丢弃它不再理睬。”这段话的意思是，虽然ThreadDeath代表的是一种普通的问题，但鉴于许多应用会试图捕获所有异常然后不予以适当的处理，所以JDK把ThreadDeath定义成了Error的子类，因为Error类代表的是一般的应用不应该去捕获的严重问题。可见，丢弃异常这一坏习惯是如此常见，它甚至已经影响到了Java本身的设计。

那么，应该怎样改正呢？主要有四个选择：

> 1、处理异常。针对该异常采取一些行动，例如修正问题、提醒某个人或进行其他一些处理，要根据具体的情形确定应该采取的动作。再次说明，调用printStackTrace算不上已经“处理好了异常”。 2、重新抛出异常。处理异常的代码在分析异常之后，认为自己不能处理它，重新抛出异常也不失为一种选择。 3、把该异常转换成另一种异常。大多数情况下，这是指把一个低级的异常转换成应用级的异常（其含义更容易被用户了解的异常）。 4、不要捕获异常。

**结论一：既然捕获了异常，就要对它进行适当的处理。不要捕获异常之后又把它丢弃，不予理睬。**

## 反例之二：不指定具体的异常

代码：15行。

许多时候人们会被这样一种“美妙的”想法吸引：用一个catch语句捕获所有的异常。最常见的情形就是使用catch(Exception ex)语句。但实际上，在绝大多数情况下，这种做法不值得提倡。为什么呢？

要理解其原因，我们必须回顾一下catch语句的用途。catch语句表示我们预期会出现某种异常，而且希望能够处理该异常。异常类的作用就是告诉Java编译器我们想要处理的是哪一种异常。由于绝大多数异常都直接或间接从java.lang.Exception派生，catch(Exception ex)就相当于说我们想要处理几乎所有的异常。

再来看看前面的代码例子。我们真正想要捕获的异常是什么呢？最明显的一个是SQLException，这是JDBC操作中常见的异常。另一个可能的异常是IOException，因为它要操作OutputStreamWriter。显然，在同一个catch块中处理这两种截然不同的异常是不合适的。如果用两个catch块分别捕获SQLException和IOException就要好多了。这就是说，catch语句应当尽量指定具体的异常类型，而不应该指定涵盖范围太广的Exception类。

另一方面，除了这两个特定的异常，还有其他许多异常也可能出现。例如，如果由于某种原因，executeQuery返回了null，该怎么办？答案是让它们继续抛出，即不必捕获也不必处理。实际上，我们不能也不应该去捕获可能出现的所有异常，程序的其他地方还有捕获异常的机会??直至最后由JVM处理。

**结论二：在catch语句中尽可能指定具体的异常类型，必要时使用多个catch。不要试图处理所有可能出现的异常。**

## 反例之三：占用资源不释放

代码：3行-14行。

异常改变了程序正常的执行流程。这个道理虽然简单，却常常被人们忽视。如果程序用到了文件、Socket、JDBC连接之类的资源，即使遇到了异常，也要正确释放占用的资源。为此，Java提供了一个简化这类操作的关键词finally。

finally是样好东西：不管是否出现了异常，Finally保证在try/catch/finally块结束之前，执行清理任务的代码总是有机会执行。遗憾的是有些人却不习惯使用finally。

当然，编写finally块应当多加小心，特别是要注意在finally块之内抛出的异常??这是执行清理任务的最后机会，尽量不要再有难以处理的错误。

**结论三：保证所有资源都被正确释放。充分运用finally关键词。**

## 反例之四：不说明异常的详细信息

代码：3行-18行。

仔细观察这段代码：如果循环内部出现了异常，会发生什么事情？我们可以得到足够的信息判断循环内部出错的原因吗？不能。我们只能知道当前正在处理的类发生了某种错误，但却不能获得任何信息判断导致当前错误的原因。

printStackTrace的堆栈跟踪功能显示出程序运行到当前类的执行流程，但只提供了一些最基本的信息，未能说明实际导致错误的原因，同时也不易解读。

因此，在出现异常时，最好能够提供一些文字信息，例如当前正在执行的类、方法和其他状态信息，包括以一种更适合阅读的方式整理和组织printStackTrace提供的信息。

**结论四：在异常处理模块中提供适量的错误原因信息，组织错误信息使其易于理解和阅读。**

## 反例之五：过于庞大的try块

代码：3行-14行。

经常可以看到有人把大量的代码放入单个try块，实际上这不是好习惯。这种现象之所以常见，原因就在于有些人图省事，不愿花时间分析一大块代码中哪几行代码会抛出异常、异常的具体类型是什么。把大量的语句装入单个巨大的try块就象是出门旅游时把所有日常用品塞入一个大箱子，虽然东西是带上了，但要找出来可不容易。

一些新手常常把大量的代码放入单个try块，然后再在catch语句中声明Exception，而不是分离各个可能出现异常的段落并分别捕获其异常。这种做法为分析程序抛出异常的原因带来了困难，因为一大段代码中有太多的地方可能抛出Exception。

**结论五：尽量减小try块的体积。**

## 反例之六：输出数据不完整

代码：7行-11行。

不完整的数据是Java程序的隐形杀手。仔细观察这段代码，考虑一下如果循环的中间抛出了异常，会发生什么事情。循环的执行当然是要被打断的，其次，catch块会执行??就这些，再也没有其他动作了。已经输出的数据怎么办？使用这些数据的人或设备将收到一份不完整的（因而也是错误的）数据，却得不到任何有关这份数据是否完整的提示。对于有些系统来说，数据不完整可能比系统停止运行带来更大的损失。

较为理想的处置办法是向输出设备写一些信息，声明数据的不完整性；另一种可能有效的办法是，先缓冲要输出的数据，准备好全部数据之后再一次性输出。

**结论六：全面考虑可能出现的异常以及这些异常对执行流程的影响。**

## 改写后的代码

根据上面的讨论，下面给出改写后的代码。也许有人会说它稍微有点啰嗦，但是它有了比较完备的异常处理机制。

```
OutputStreamWriter out = ... 
java.sql.Connection conn = ... 
try { 
　Statement stat = conn.createStatement(); 
　ResultSet rs = stat.executeQuery( 
　　"select uid, name from user"); 
　while (rs.next()) 
　{ 
　　out.println("ID：" + rs.getString("uid") + "，姓名: " + rs.getString("name")); 
　} 
} 
catch(SQLException sqlex) 
{ 
　out.println("警告：数据不完整"); 
　throw new ApplicationException("读取数据时出现SQL错误", sqlex); 
} 
catch(IOException ioex) 
{ 
　throw new ApplicationException("写入数据时出现IO错误", ioex); 
} 
finally
{ 
　if (conn != null) { 
　　try { 
　　　conn.close(); 
　　} 
　　catch(SQLException sqlex2) 
　　{ 
　　　System.err(this.getClass().getName() + ".mymethod - 不能关闭数据库连接: " + sqlex2.toString()); 
　　} 
　} 

　if (out != null) { 
　　try { 
　　　out.close(); 
　　} 
　　catch(IOException ioex2) 
　　{ 
　　　System.err(this.getClass().getName() + ".mymethod - 不能关闭输出文件" + ioex2.toString()); 
　　} 
　} 
}
```

**异常设计的几个原则：1.如果方法遭遇了一个无法处理的意外情况，那么抛出一个异常。2.避免使用异常来指出可以视为方法的常用功能的情况。3.如果发现客户违反了契约（例如，传入非法输入参数），那么抛出非检查型异常。4.如果方法无法履型契约，那么抛出检查型异常，也可以抛出非检查型异常。5.如果你认为客户程序员需要有意识地采取措施，那么抛出检查型异常。6.异常类应该给客户提供丰富的信息，异常类跟其它类一样，允许定义自己的属性和方法。7.异常类名和方法遵循JAVA类名规范和方法名规范8.跟JAVA其它类一样，不要定义多余的方法和变量。(不会使用的变量，就不要定义,spring的BadSqlGrammarException.getSql() 就是多余的)**

# 异常处理的 15 个处理原则

1、不用使用异常来管理业务逻辑，应该使用条件语句。如果一个控制逻辑可通过 if-else 语句来简单完成的，那就不用使用异常，因为异常会降低代码的可读性和性能，例如一些 null 的判断逻辑、除0的控制等等；

2、异常的名字必须清晰而且有具体的意思，表示异常发生的问题，例如 FileNotFoundException 就很清晰直观

3、当方法判断出错该返回时应该抛出异常，而不是返回一些错误值，因为错误值难以理解而且不够直观，例如抛出 FileNotFoundException 异常，而不是返回 -1 或者 -2 之类的错误值。

4、应该捕获指定的异常，而不是 catch(Exception e) 了事，这对性能、代码的可读性以及诸多方面都有好处

5、Null 的判断逻辑并不是一成不变的，当方法允许返回 null 的时候使用 if-else 控制逻辑，否则就抛出 NullPointerException

6、尽量不要二次抛出异常，如果非得这么做的话，抛出同一个异常示例，而不是重新构建一个异常对象，这对性能是有帮助的，而且外层调用者可获取真实的异常信息

7、定义你自己的异常类层次，例如 UserException 和 SystemException 分别代表用户级别的异常信息和系统级别的异常信息，而其他的异常在这两个基类上进行扩展

8、明确的使用不同的异常类型：

```
Fatal: System crash states. 
Error: Lack of requirement. 
Warn: Not an error but error probability. 
Info: Info for user. 
Debug: Info for developer. 
```

9、不要仅仅捕获异常而不做任何处理，不便于将来维护

10、不要多次重复记录同一个异常，这可以让我们清晰的了解异常发生的位置

11、请使用 finally 来释放一些打开的资源，例如打开的文件、数据库连接等等

12、大部分情况下不建议在循环中进行异常处理，应该在循环外对异常进行捕获处理

13、异常的粒度很重要，应该为一个基本操作定义一个 try-catch 块，不要为了简便，将几百行代码放到一个 try-catch 块中

14、为你的异常生成足够的文档说明，至少是 JavaDoc

15、为每个异常消息定义一个数值，这对好的文档来说是非常重要的。
