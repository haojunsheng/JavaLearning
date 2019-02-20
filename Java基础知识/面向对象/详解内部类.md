[TOC]

**可以将一个类的定义放在另一个类的定义内部，这就是内部类。**

内部类是一个非常有用的特性但又比较难理解使用的特性(鄙人到现在都没有怎么使用过内部类，对内部类也只是略知一二)。

## 第一次见面

内部类我们从外面看是非常容易理解的，无非就是在一个类的内部在定义一个类。

```
public class OuterClass {
    private String name ;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    class InnerClass{
        public InnerClass(){
            name = "chenssy";
            age = 23;
        }
    }
}
```

在这里InnerClass就是内部类，对于初学者来说内部类实在是使用的不多，鄙人菜鸟一个同样没有怎么使用过(貌似仅仅只在做swing 注册事件中使用过)，但是随着编程能力的提高，我们会领悟到它的魅力所在，它可以使用能够更加优雅的设计我们的程序结构。在使用内部类之间我们需要明白为什么要使用内部类，内部类能够为我们带来什么样的好处。

## 一、为什么要使用内部类

为什么要使用内部类？在《Think in java》中有这样一句话：使用内部类最吸引人的原因是：每个内部类都能独立地继承一个（接口的）实现，所以无论外围类是否已经继承了某个（接口的）实现，对于内部类都没有影响。

在我们程序设计中有时候会存在一些使用接口很难解决的问题，这个时候我们可以利用内部类提供的、可以继承多个具体的或者抽象的类的能力来解决这些程序设计问题。可以这样说，接口只是解决了部分问题，而内部类使得多重继承的解决方案变得更加完整。

```
public interface Father {

}

public interface Mother {

}

public class Son implements Father, Mother {

}

public class Daughter implements Father{

    class Mother_ implements Mother{

    }
}
```

其实对于这个实例我们确实是看不出来使用内部类存在何种优点，但是如果Father、Mother不是接口，而是抽象类或者具体类呢？这个时候我们就只能使用内部类才能实现多重继承了。

其实使用内部类最大的优点就在于它能够非常好的解决多重继承的问题，但是如果我们不需要解决多重继承问题，那么我们自然可以使用其他的编码方式，但是使用内部类还能够为我们带来如下特性（摘自《Think in java》）：

- 1、内部类可以用多个实例，每个实例都有自己的状态信息，并且与其他外围对象的信息相互独立。
- 2、在单个外围类中，可以让多个内部类以不同的方式实现同一个接口，或者继承同一个类。
- 3、创建内部类对象的时刻并不依赖于外围类对象的创建。
- 4、内部类并没有令人迷惑的“is-a”关系，他就是一个独立的实体。
- 5、内部类提供了更好的封装，除了该外围类，其他类都不能访问。

## 二、内部类基础

在这个部分主要介绍内部类如何使用外部类的属性和方法，以及使用.this与.new。

当我们在创建一个内部类的时候，它无形中就与外围类有了一种联系，依赖于这种联系，它可以无限制地访问外围类的元素。

```
public class OuterClass {
    private String name ;
    private int age;

    /**省略getter和setter方法**/

    public class InnerClass{
        public InnerClass(){
            name = "chenssy";
            age = 23;
        }

        public void display(){
            System.out.println("name：" + getName() +"   ;age：" + getAge());
        }
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        innerClass.display();
    }
}
--------------
Output：
name：chenssy   ;age：23
```

在这个应用程序中，我们可以看到内部了InnerClass可以对外围类OuterClass的属性进行无缝的访问，尽管它是private修饰的。这是因为当我们在创建某个外围类的内部类对象时，此时内部类对象必定会捕获一个指向那个外围类对象的引用，只要我们在访问外围类的成员时，就会用这个引用来选择外围类的成员。

其实在这个应用程序中我们还看到了如何来引用内部类：引用内部类我们需要指明这个对象的类型：OuterClasName.InnerClassName。同时如果我们需要创建某个内部类对象，必须要利用外部类的对象通过.new来创建内部类： OuterClass.InnerClass innerClass = outerClass.new InnerClass();。

同时如果我们需要生成对外部类对象的引用，可以使用OuterClassName.this，这样就能够产生一个正确引用外部类的引用了。当然这点实在编译期就知晓了，没有任何运行时的成本。

```
public class OuterClass {
    public void display(){
        System.out.println("OuterClass...");
    }

    public class InnerClass{
        public OuterClass getOuterClass(){
            return OuterClass.this;
        }
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass = outerClass.new InnerClass();
        innerClass.getOuterClass().display();
    }
}
-------------
Output:
OuterClass...
```

到这里了我们需要明确一点，内部类是个编译时的概念，一旦编译成功后，它就与外围类属于两个完全不同的类（当然他们之间还是有联系的）。对于一个名为OuterClass的外围类和一个名为InnerClass的内部类，在编译成功后，会出现这样两个class文件：OuterClass.class和OuterClass$InnerClass.class。

在Java中内部类主要分为成员内部类、局部内部类、匿名内部类、静态内部类。

## 三、成员内部类

成员内部类也是最普通的内部类，它是外围类的一个成员，所以他是可以无限制的访问外围类的所有 成员属性和方法，尽管是private的，但是外围类要访问内部类的成员属性和方法则需要通过内部类实例来访问。

在成员内部类中要注意两点:

- 第一：成员内部类中不能存在任何static的变量和方法；
- 第二：成员内部类是依附于外围类的，所以只有先创建了外围类才能够创建内部类。

```
public class OuterClass {
    private String str;

    public void outerDisplay(){
        System.out.println("outerClass...");
    }

    public class InnerClass{
        public void innerDisplay(){
            //使用外围内的属性
            str = "chenssy...";
            System.out.println(str);
            //使用外围内的方法
            outerDisplay();
        }
    }

    /*推荐使用getxxx()来获取成员内部类，尤其是该内部类的构造函数无参数时 */
    public InnerClass getInnerClass(){
        return new InnerClass();
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        OuterClass.InnerClass inner = outer.getInnerClass();
        inner.innerDisplay();
    }
}
--------------------
chenssy...
outerClass...
```

**推荐使用getxxx()来获取成员内部类，尤其是该内部类的构造函数无参数时 。**

## 四、局部内部类

有这样一种内部类，它是嵌套在方法和作用于内的，对于这个类的使用主要是应用与解决比较复杂的问题，想创建一个类来辅助我们的解决方案，到那时又不希望这个类是公共可用的，所以就产生了局部内部类，局部内部类和成员内部类一样被编译，只是它的作用域发生了改变，它只能在该方法和属性中被使用，出了该方法和属性就会失效。

对于局部内部类实在是想不出什么好例子，所以就引用《Think in java》中的经典例子了。

定义在方法里：

```
public class Parcel5 {
    public Destionation destionation(String str){
        class PDestionation implements Destionation{
            private String label;
            private PDestionation(String whereTo){
                label = whereTo;
            }
            public String readLabel(){
                return label;
            }
        }
        return new PDestionation(str);
    }

    public static void main(String[] args) {
        Parcel5 parcel5 = new Parcel5();
        Destionation d = parcel5.destionation("chenssy");
    }
}
```

定义在作用域内:

```
public class Parcel6 {
    private void internalTracking(boolean b){
        if(b){
            class TrackingSlip{
                private String id;
                TrackingSlip(String s) {
                    id = s;
                }
                String getSlip(){
                    return id;
                }
            }
            TrackingSlip ts = new TrackingSlip("chenssy");
            String string = ts.getSlip();
        }
    }

    public void track(){
        internalTracking(true);
    }

    public static void main(String[] args) {
        Parcel6 parcel6 = new Parcel6();
        parcel6.track();
    }
}
```

## 五、匿名内部类

在做Swing编程中，我们经常使用这种方式来绑定事件

```
button2.addActionListener(  
                new ActionListener(){  
                    public void actionPerformed(ActionEvent e) {  
                        System.out.println("你按了按钮二");  
                    }  
                });
```

我们咋一看可能觉得非常奇怪，因为这个内部类是没有名字的，在看如下这个例子：

```
public class OuterClass {
    public InnerClass getInnerClass(final int num,String str2){
        return new InnerClass(){
            int number = num + 3;
            public int getNumber(){
                return number;
            }
        };        /* 注意：分号不能省 */
    }

    public static void main(String[] args) {
        OuterClass out = new OuterClass();
        InnerClass inner = out.getInnerClass(2, "chenssy");
        System.out.println(inner.getNumber());
    }
}

interface InnerClass {
    int getNumber();
}

----------------
Output:
5
```

这里我们就需要看清几个地方

- 1、** 匿名内部类是没有访问修饰符的。
- 2、** new 匿名内部类，这个类首先是要存在的。如果我们将那个InnerClass接口注释掉，就会出现编译出错。
- 3、** 注意getInnerClass()方法的形参，第一个形参是用final修饰的，而第二个却没有。同时我们也发现第二个形参在匿名内部类中没有使用过，所以当所在方法的形参需要被匿名内部类使用，那么这个形参就必须为final。
- 4、** 匿名内部类是没有构造方法的。因为它连名字都没有何来构造方法。

PS：由于篇幅有限，对匿名内部类就介绍到这里，有关更多关于匿名内部类的知识，我就会在下篇博客（java提高篇—–详解匿名内部类）做详细的介绍，包括为何形参要定义成final，怎么对匿名内部类进行初始化等等，敬请期待……

## 六、静态内部类

在java提高篇—–关键字static中提到Static可以修饰成员变量、方法、代码块，其他它还可以修饰内部类，使用static修饰的内部类我们称之为静态内部类，不过我们更喜欢称之为嵌套内部类。静态内部类与非静态内部类之间存在一个最大的区别，我们知道非静态内部类在编译完成之后会隐含地保存着一个引用，该引用是指向创建它的外围内，但是静态内部类却没有。没有这个引用就意味着：

- 1、** 它的创建是不需要依赖于外围类的。
- 2、** 它不能使用任何外围类的非static成员变量和方法。

```
public class OuterClass {
    private String sex;
    public static String name = "chenssy";

    /**
     *静态内部类
     */
    static class InnerClass1{
        /* 在静态内部类中可以存在静态成员 */
        public static String _name1 = "chenssy_static";

        public void display(){
            /*
             * 静态内部类只能访问外围类的静态成员变量和方法
             * 不能访问外围类的非静态成员变量和方法
             */
            System.out.println("OutClass name :" + name);
        }
    }

    /**
     * 非静态内部类
     */
    class InnerClass2{
        /* 非静态内部类中不能存在静态成员 */
        public String _name2 = "chenssy_inner";
        /* 非静态内部类中可以调用外围类的任何成员,不管是静态的还是非静态的 */
        public void display(){
            System.out.println("OuterClass name：" + name);
        }
    }

    /**
     * @desc 外围类方法
     * @author chenssy
     * @data 2013-10-25
     * @return void
     */
    public void display(){
        /* 外围类访问静态内部类：内部类. */
        System.out.println(InnerClass1._name1);
        /* 静态内部类 可以直接创建实例不需要依赖于外围类 */
        new InnerClass1().display();

        /* 非静态内部的创建需要依赖于外围类 */
        OuterClass.InnerClass2 inner2 = new OuterClass().new InnerClass2();
        /* 方位非静态内部类的成员需要使用非静态内部类的实例 */
        System.out.println(inner2._name2);
        inner2.display();
    }

    public static void main(String[] args) {
        OuterClass outer = new OuterClass();
        outer.display();
    }
}
----------------
Output:
chenssy_static
OutClass name :chenssy
chenssy_inner
OuterClass name：chenssy
```

上面这个例子充分展现了静态内部类和非静态内部类的区别。



在这篇博客中你可以了解到匿名内部类的使用、匿名内部类要注意的事项、如何初始化匿名内部类、匿名内部类使用的形参为何要为final。

## 一、使用匿名内部类内部类

匿名内部类由于没有名字，所以它的创建方式有点儿奇怪。创建格式如下：

```
new 父类构造器（参数列表）|实现接口（）  
   {  
    //匿名内部类的类体部分  
   }
```

在这里我们看到使用匿名内部类我们必须要继承一个父类或者实现一个接口，当然也仅能只继承一个父类或者实现一个接口。同时它也是没有class关键字，这是因为匿名内部类是直接使用new来生成一个对象的引用。当然这个引用是隐式的。

```
public abstract class Bird {
   private String name;

   public String getName() {
       return name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public abstract int fly();
}

public class Test {

   public void test(Bird bird){
       System.out.println(bird.getName() + "能够飞 " + bird.fly() + "米");
   }

   public static void main(String[] args) {
       Test test = new Test();
       test.test(new Bird() {

           public int fly() {
               return 10000;
           }

           public String getName() {
               return "大雁";
           }
       });
   }
}
------------------
Output：
大雁能够飞 10000米
```

在Test类中，test()方法接受一个Bird类型的参数，同时我们知道一个抽象类是没有办法直接new的，我们必须要先有实现类才能new出来它的实现类实例。所以在mian方法中直接使用匿名内部类来创建一个Bird实例。

由于匿名内部类不能是抽象类，所以它必须要实现它的抽象父类或者接口里面所有的抽象方法。

对于这段匿名内部类代码其实是可以拆分为如下形式：

```
public class WildGoose extends Bird{
   public int fly() {
       return 10000;
   }

   public String getName() {
       return "大雁";
   }
}

WildGoose wildGoose = new WildGoose();
test.test(wildGoose);
```

在这里系统会创建一个继承自Bird类的匿名类的对象，该对象转型为对Bird类型的引用。

对于匿名内部类的使用它是存在一个缺陷的，就是它仅能被使用一次，创建匿名内部类时它会立即创建一个该类的实例，该类的定义会立即消失，所以匿名内部类是不能够被重复使用。对于上面的实例，如果我们需要对test()方法里面内部类进行多次使用，建议重新定义类，而不是使用匿名内部类。

## 二、注意事项

在使用匿名内部类的过程中，我们需要注意如下几点：

- 1、使用匿名内部类时，我们必须是继承一个类或者实现一个接口，但是两者不可兼得，同时也只能继承一个类或者实现一个接口。
- 2、匿名内部类中是不能定义构造函数的。
- 3、匿名内部类中不能存在任何的静态成员变量和静态方法。
- 4、匿名内部类为局部内部类，所以局部内部类的所有限制同样对匿名内部类生效。
- 5、匿名内部类不能是抽象的，它必须要实现继承的类或者实现的接口的所有抽象方法。

## 三、使用的形参为何要为final

参考文件：<http://android.blog.51cto.com/268543/384844>

 我们给匿名内部类传递参数的时候，若该形参在内部类中需要被使用，那么该形参必须要为final。也就是说：**当所在的方法的形参需要被内部类里面使用时，该形参必须为final。**

为什么必须要为final呢？

首先我们知道在内部类编译成功后，它会产生一个class文件，该class文件与外部类并不是同一class文件，仅仅只保留对外部类的引用。当外部类传入的参数需要被内部类调用时，从java程序的角度来看是直接被调用：

```
public class OuterClass {
   public void display(final String name,String age){
       class InnerClass{
           void display(){
               System.out.println(name);
           }
       }
   }
}
```

从上面代码中看好像name参数应该是被内部类直接调用？其实不然，在java编译之后实际的操作如下：

```
public class OuterClass$InnerClass {
   public InnerClass(String name,String age){
       this.InnerClass$name = name;
       this.InnerClass$age = age;
   }

   public void display(){
       System.out.println(this.InnerClass$name + "----" + this.InnerClass$age );
   }
}
```

所以从上面代码来看，内部类并不是直接调用方法传递的参数，而是利用自身的构造器对传入的参数进行备份，自己内部方法调用的实际上时自己的属性而不是外部方法传递进来的参数。

直到这里还没有解释为什么是final？在内部类中的属性和外部方法的参数两者从外表上看是同一个东西，但实际上却不是，所以他们两者是可以任意变化的，也就是说在内部类中我对属性的改变并不会影响到外部的形参，而然这从程序员的角度来看这是不可行的，毕竟站在程序的角度来看这两个根本就是同一个，如果内部类该变了，而外部方法的形参却没有改变这是难以理解和不可接受的，所以为了保持参数的一致性，就规定使用final来避免形参的不改变。

**简单理解就是，拷贝引用，为了避免引用值发生改变，例如被外部类的方法修改等，而导致内部类得到的值不一致，于是用final来让该引用不可改变。**

**故如果定义了一个匿名内部类，并且希望它使用一个其外部定义的参数，那么编译器会要求该参数引用是final的。**

## 四、匿名内部类初始化

我们一般都是利用构造器来完成某个实例的初始化工作的，但是匿名内部类是没有构造器的！那怎么来初始化匿名内部类呢？使用构造代码块！利用构造代码块能够达到为匿名内部类创建一个构造器的效果。

```
public class OutClass {
   public InnerClass getInnerClass(final int age,final String name){
       return new InnerClass() {
           int age_ ;
           String name_;
           //构造代码块完成初始化工作
           {
               if(0 < age && age < 200){
                   age_ = age;
                   name_ = name;
               }
           }
           public String getName() {
               return name_;
           }

           public int getAge() {
               return age_;
           }
       };
   }

   public static void main(String[] args) {
       OutClass out = new OutClass();

       InnerClass inner_1 = out.getInnerClass(201, "chenssy");
       System.out.println(inner_1.getName());

       InnerClass inner_2 = out.getInnerClass(23, "chenssy");
       System.out.println(inner_2.getName());
   }
}
```











