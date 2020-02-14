[TOC]

# 前言

本文主要介绍创建型模式的最后一种————建造者模式。

# 1. 概念

建造者模式（英：Builder Pattern）是一种创建型设计模式，又名：**生成器模式**。GOF 给建造者模式的定义为：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。这句话说的比较抽象，其实解释一下就是：**将建造复杂对象的过程和组成对象的部件解耦**。

# 2. 用途

假设现在我们是一家网游设计公司，现在我们要”抄袭”梦幻西游这款游戏，你是该公司的游戏角色设计人员。你怎么设计出该游戏中的各种角色呢？ 在梦幻西游来中包括人、仙、魔等种族的角色，而每种不同的种族的角色中又包含龙太子、逍遥生等具体的角色。

作为一个出色的开发人员，我们设计的角色生成系统应该包含以下功能和特性：

> 为了保证游戏平衡，所有角色的基本属性应该一致
>
> 因为角色的创建过程可能很复杂，所以角色的生成细节不应该对外暴露
>
> 随时可以新增角色
>
> 对某个具体角色的修改应该不影响其他角色

其实，对于角色的设计，我们可以使用抽象工厂模式，将同一种族的角色看成是一个产品族。但是，这样做可能存在一个问题，那就是我们可能要在每个角色的创建过程中都要从头到尾的构建一遍该角色。比如一个角色包含头部、身体。其中头部又包括脸部、和其他部位。其中脸部又包含眉毛、嘴巴、鼻子等部位。整个角色的创建过程是极其复杂的。很容易遗漏其中的某个步骤。

那么，我们可以将这些**具体部位的创建工作和对象的创建进行解耦**。这就是建造者模式。

# 3. 实现方式

建造者模式包含如下角色：

> Builder：抽象建造者(`Builder`)
>
> ConcreteBuilder：具体建造者(`CommonBuilder`、`SuperBuilder`)
>
> Director：指挥者(`Director`)
>
> Product：产品角色(`Role`)

![Builder](https://ws3.sinaimg.cn/large/006tKfTcly1g0awryf2r3j30jt09ewf6.jpg)

这里采用设计角色的例子，为了便于理解，我们只创建两个角色，分别是普通角色和超级角色。他们都有设置头部、脸部、身体、气血值、魔法值、能量值等方法。值得注意的是设置脸部是依赖于设置头部的，要有先后顺序。

产品角色：Role

```java
public class Role {

    private String head; //头部
    private String face; //脸部（脸部依赖于头部）
    private String body; //身体
    private Double hp;   //生命值
    private Double sp;   //能量值
    private Double mp;   //魔法值

    //setter and getter 
     // toString 
}
```

抽象建造者：Builder

```java
public abstract class Builder {

    protected Role role = new Role();

    public abstract void buildHead();

    public abstract void buildFace();

    public abstract void buildBody();

    public abstract void buildHp();

    public abstract void buildSp();

    public abstract void buildMp();

    public Role getResult() {
        return role;
    }
}
```

具体建造者：

```java
public class CommonRoleBuilder extends Builder {

    private Role role = new Role();

    @Override
    public void buildHead() {
        role.setBody("common head");
    }

    @Override
    public void buildFace() {
        role.setFace("common face");
    }

    @Override
    public void buildBody() {
        role.setBody("common body");
    }

    @Override
    public void buildHp() {
        role.setHp(100d);
    }

    @Override
    public void buildSp() {
        role.setSp(100d);
    }

    @Override
    public void buildMp() {
        role.setMp(100d);
    }

    @Override
    public Role getResult() {
        return role;
    }
}

public class SuperRoleBuilder extends Builder {

    private Role role = new Role();

    @Override
    public void buildHead() {
        role.setBody("suoer head");
    }

    @Override
    public void buildFace() {
        role.setFace("super face");
    }

    @Override
    public void buildBody() {
        role.setBody("super body");
    }

    @Override
    public void buildHp() {
        role.setHp(120d);
    }

    @Override
    public void buildSp() {
        role.setSp(120d);
    }

    @Override
    public void buildMp() {
        role.setMp(120d);
    }

    @Override
    public Role getResult() {
        return role;
    }
}
```

指挥者：

```java
public class Director {

    public void construct(Builder builder){
        builder.buildBody();
        builder.buildHead();
        builder.buildFace();
        builder.buildHp();
        builder.buildMp();
        builder.buildSp();
    }
}
```

测试类：

```java
public class Main {

    public static void main(String[] args) {

        Director director = new Director();
        Builder commonBuilder = new CommonRoleBuilder();

        director.construct(commonBuilder);
        Role commonRole = commonBuilder.getResult();
        System.out.println(commonRole);

    }
}
```

到这里，一个建造者模式已经完成了，是不是很简单？

------

再回到之前的需求，看看我们是否都满足？

由于建造角色的过程比较复杂，其中还有相互依赖关系（如脸部依赖于头部），所以我们使用建造者模式将将建造复杂对象的过程和组成对象的部件解耦。这样既保证了基本属性全都一致（这里的一致指的是该包含的应该全都包含）也封装了其中的具体实现细节。

同时，在修改某个具体角色的时候我们只需要修改对应的具体角色就可以了，不会影响到其他角色。

如果需要新增角色，只要再增加一个具体建造者，并在该建造者中写好具体细节的建造部分代码就OK了。

# 4. 建造者模式的优缺点

## 4.1 优点

建造者模式的**封装性很好。使用建造者模式可以有效的封装变化**，在使用建造者模式的场景中，一般产品类和建造者类是比较稳定的，因此，将主要的业务逻辑封装在导演类中对整体而言可以取得比较好的稳定性。

在建造者模式中，**客户端不必知道产品内部组成的细节**，将产品本身与产品的创建过程解耦，使得相同的创建过程可以创建不同的产品对象。

**可以更加精细地控制产品的创建过程** 。将复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰，也更方便使用程序来控制创建过程。

其次，**建造者模式很容易进行扩展**。如果有新的需求，通过实现一个新的建造者类就可以完成，基本上不用修改之前已经测试通过的代码，因此也就不会对原有功能引入风险。符合开闭原则。

## 4.2 缺点

建造者模式所创建的产品一般具有较多的共同点，其组成部分相似，如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。

如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。

# 5. 适用环境

在以下情况下可以使用建造者模式：

> 需要生成的产品对象有复杂的内部结构，这些产品对象通常包含多个成员属性。
>
> 需要生成的产品对象的属性相互依赖，需要指定其生成顺序。
>
> 对象的创建过程独立于创建该对象的类。在建造者模式中引入了指挥者类，将创建过程封装在指挥者类中，而不在建造者类中。
>
> 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。

# 6. 建造者模式与工厂模式的区别

我们可以看到，建造者模式与工厂模式是极为相似的，总体上，建造者模式仅仅只比工厂模式多了一个”指挥者”的角色。在建造者模式的类图中，假如把这个导演类看做是最终调用的客户端，那么图中剩余的部分就可以看作是一个简单的工厂模式了。

与工厂模式相比，建造者模式一般用来创建更为复杂的对象，因为对象的创建过程更为复杂，因此将对象的创建过程独立出来组成一个新的类——导演类。

也就是说，工厂模式是将对象的全部创建过程封装在工厂类中，由工厂类向客户端提供最终的产品；而建造者模式中，建造者类一般只提供产品类中各个组件的建造，而将具体建造过程交付给导演类。由导演类负责将各个组件按照特定的规则组建为产品，然后将组建好的产品交付给客户端。

建造者模式与工厂模式类似，适用的场景也很相似。一般来说，如果产品的建造很复杂，那么请用工厂模式；如果产品的建造更复杂，那么请用建造者模式。哈哈哈。。。

# 7. 总结

建造者模式将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。

在建造者模式的结构中引入了一个指挥者类，该类的作用主要有两个：一方面它隔离了客户与生产过程；另一方面它负责控制产品的生成过程。指挥者针对抽象建造者编程，客户端只需要知道具体建造者的类型，即可通过指挥者类调用建造者的相关方法，返回一个完整的产品对象。

# 8. 参考资料

[大话设计模式](http://s.click.taobao.com/t?e=m=2&s=R5B/xd29JVMcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67jN2wQzI0ZBVHBMajAjK1gBpS4hLH/P02ckKYNRBWOBBey11vvWwHXSniyi5vWXIZkKWZZq7zWpCC8X3k5aQlui0qVGgqDL2o8YMXU3NNCg/&pvid=10_42.120.73.203_224_1460382841310)

[深入浅出设计模式](http://s.click.taobao.com/t?e=m=2&s=Obpq8Qxse2EcQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67utJaEGcptl2kfkm8XrrgBtpS4hLH/P02ckKYNRBWOBBey11vvWwHXTpkOAWGyim%2bw2PNKvM2u52N5aP5%2bgx7zgh4LxdBQDQSXEqY%2bakgpmw&pvid=10_121.0.29.199_322_1460465025379)

[建造者模式](http://design-patterns.readthedocs.io/zh_CN/latest/creational_patterns/builder.html)



# 9. [建造者模式的实践](https://www.hollischuang.com/archives/1533)

我不打算深入介绍设计模式的细节内容，因为有很多这方面的[文章](http://www.hollischuang.com/archives/1477)和书籍可供参考。本文主要关注于告诉你为什么以及在什么情况下你应该考虑使用建造者模式。然而，值得一提的是本文中的模式和GOF中的提出的有点不一样。那种原生的模式主要侧重于抽象构造的过程以达到通过修改builder的实现来得到不同的结果的目的。本文中主要介绍的这种模式并没有那么复杂，因为我删除了不必要的多个构造函数、多个可选参数以及大量的`setter`/`getter`方法。

假设你有一个类，其中包含大量属性。就像下面的User类一样。假设你想让这个类是不可变的。

```java
public class User {
    private final String firstName;    //required
    private final String lastName;    //required
    private final int age;    //optional
    private final String phone;    //optional
    private final String address;    //optional
    ...
}
```

在这样的类中，有一些属性是必须的（`required`）而另外一些是可选的（`optional`）。如果你想要构造这个类的实例，你会怎么做？把所有属性都设置成final类型，然后使用构造函数初始化他们嘛？但是，如果你想让这个类的调用者可以从众多的可选参数中选择自己想要的进行设置怎么办？

第一个可想到的方案可能是重载多个构造函数，其中有一个只初始化必要的参数，还有一个会在初始化必要的参数同时初始化所有的可选参数，还有一些其他的构造函数介于两者之间，就是一次多初始化一个可选参数。就像下面的代码：

```java
public User(String firstName, String lastName) {
    this(firstName, lastName, 0);
}

public User(String firstName, String lastName, int age) {
    this(firstName, lastName, age, "");
}

public User(String firstName, String lastName, int age, String phone) {
    this(firstName, lastName, age, phone, "");
}

public User(String firstName, String lastName, int age, String phone, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.phone = phone;
    this.address = address;
}
```

首先可以肯定的是，这样做是可以满足要求的。当然，这种方式的缺点也是很明显的。当一个类中只有几个参数的时候还好，如果一旦类中的参数逐渐增大，那么这个类就会变得很难阅读和维护。更重要的是，这样的一个类，调用者会很难使用。我到底应该使用哪个构造方法？是包含两个参数的还是包含三个参数的？如果我没有传递值的话那些属性的默认值是什么？如果我只想对`address`赋值而不对`age`和`phone`赋值怎么办？遇到这种情况可能我只能调用那个参数最全的构造函数，然后对于我不想要的参数值传递一个默认值。此外，如果多个参数的类型都相同那就很容易让人困惑，第一个`String`类型的参数到底是`number`还是`address`呢？

还有没有其他方案可选择呢？我们可以遵循`JaveBean`规范，定义一个只包含无参数的构造方法和`getter`、`setter`方法的`JavaBean`。

```java
public class User {
    private String firstName; // required
    private String lastName; // required
    private int age; // optional
    private String phone; // optional
    private String address;  //optional

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
```

这种方式看上去很容易阅读和维护。对于调用者来说，我只需要创建一个空的对象，然后对于我想设置的参数调用`setter`方法设置就好了。这难道还有什么问题吗？其实存在两个问题。第一个问题是该类的实例状态不固定。如果你想创建一个`User`对象，该对象的5个属性都要赋值，那么直到所有的`setXX`方法都被调用之前，该对象都没有一个完整的状态。这意味着在该对象状态还不完整的时候，一部分客户端程序可能看见这个对象并且以为该对象已经构造完成。这种方法的第二个不足是`User`类是易变的（因为没有属性是`final`的）。你将会失去不可变对象带来的所有优点。

幸运的是应对这种场景我们有第三种选择，建造者模式。解决方案类似如下所示：

```java
public class User {
    private final String firstName; // required
    private final String lastName; // required
    private final int age; // optional
    private final String phone; // optional
    private final String address; // optional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
```

值得注意的几个要点:

> `User`类的构造函数是私有的，这意味着你不能在外面直接创建这个类的对象。
>
> 该类是不可变的。所有属性都是`final`类型的，在构造方法里面被赋值。另外，我们只为它们提供了`getter`方法。
>
> builder类使用[流式接口](http://martinfowler.com/bliki/FluentInterface.html)风格，让客户端代码阅读起来更容易（我们马上就会看到一个它的例子）
>
> builder的构造方法只接收必要的参数，为了确保这些属性在构造方法里赋值，只有这些属性被定义成`final`类型。

使用建造者模式有在本文开始时提到的两种方法的所有优点，并且没有它们的缺点。客户端代码写起来更简单，更重要的是，更易读。我听过的关于该模式的唯一批判是你必须在builder类里面复制类的属性。然而，考虑到这个事实，builder类通常是需要建造的类的一个静态类成员，它们一起扩展起来相当容易。（**译者表示没明白为设定为静态成员扩展起来就容易了。设为静态成员我认为有一个好处就是可以避免出现is not an enclosing class的编译问题，创建对象时候更加方便**）

现在，试图创建一个新的`User`对象的客户端代码看起来如何那？让我们来看一下：

```java
public User getUser() {
    return new
            User.UserBuilder("Jhon", "Doe")
            .age(30)
            .phone("1234567")
            .address("Fake address 1234")
            .build();
}
```

> 译者注：如果`UserBuilder`没有设置为`static`的，以上代码会有编译错误。错误提示：`User is not an enclosing class`

以上代码看上去相当整洁。我们可以只通过一行代码就可以创建一个`User`对象，并且这行代码也很容易读懂。除此之外，这样还能确保无论何时你想获取该类的对象都不会是不完整的（**译者注：因为创建对象的过程是一气呵成的，一旦对象创建完成之后就不可修改了**）。

这种模式非常灵活，一个单独的builder类可以通过在调用`build`方法之前改变builder的属性来创建多个对象。builder类甚至可以在每次调用之间自动补全一些生成的字段，例如一个id或者序列号。

值得注意的是，像构造函数一样，builder可以对参数的合法性进行检查，一旦发现参数不合法可以抛出`IllegalStateException`异常。

但是，很重要的一点是，如果要检查参数的合法性，一定要先把参数传递给对象，然后在检查对象中的参数是否合法。其原因是因为builder并不是线程安全的。如果我们在创建真正的对象之前验证参数，参数值可能被另一个线程在参数验证完和参数被拷贝完成之间的时间修改。这段时间周期被称作“脆弱之窗”。我们的例子中情况如下：

```java
public User build() {
    User user = new user(this);
    if (user.getAge() > 120) {
        throw new IllegalStateException(“Age out of range”); // thread-safe
    }
    return user;
}
```

上一个代码版本是线程安全的因为我们首先创建user对象，然后在不可变对象上验证条件约束。下面的代码在功能上看起来一样但是它不是线程安全的，你应该避免这么做：

```
public User build() {
    if (age > 120) {
        throw new IllegalStateException(“Age out of range”); // bad, not thread-safe
    }
    // This is the window of opportunity for a second thread to modify the value of age
    return new User(this);
}   
```

建造者模式最后的一个优点是builder可以作为参数传递给一个方法，让该方法有为客户端创建一个或者多个对象的能力，而不需要知道创建对象的任何细节。为了这么做你可能通常需要一个如下所示的简单接口：

```
public interface Builder {
    T build();
}
```

借用之前的User例子，UserBuilder类可以实现Builder。如此，我们可以有如下的代码：

```
UserCollection buildUserCollection(Builder userBuilder){...}
```

> 译者注：关于这这最后一个优点的部分内容并没太看懂，希望有理解的人能过不吝赐教。

好吧，这确实是一篇很长的文章。总而言之，建造者模式在多于几个参数（虽然不是很科学准确，但是我通常把四个参数作为使用建造者模式的一个很好的指示器），特别是当大部分参数都是可选的时候。你可以让客户端代码在阅读，写和维护方面更容易。另外，你的类可以保持不可变特性，让你的代码更安全。

UPDATE：如果你使用eclipse开发，你有很多插件来避免编写建造者模式大部分的重复代码。已知的有下面三个：

http://code.google.com/p/bpep/
http://code.google.com/a/eclipselabs.org/p/bob-the-builder/
http://code.google.com/p/fluent-builders-generator-eclipse-plugin/

这几个插件我都没有使用过，所以关于哪个更好，我无法给出好的建议。我估计其他IDEs也会存在类型的插件。

