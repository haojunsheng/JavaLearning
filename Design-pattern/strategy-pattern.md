[TOC]

# 前言

前几篇文章主要介绍了几种创建型模式，本文开始介绍行为型模式。首先介绍一个比较简单的设计模式——策略模式。

# 1. 概念

学习过设计模式的人大概都知道[Head First设计模式](http://s.click.taobao.com/DxA2xSx)这本书，这本书中介绍的第一个模式就是策略模式。把策略模式放在第一个，笔者认为主要有两个原因：1、这的确是一个比较简单的模式。2、这个模式可以充分的体现面向对象设计原则中的`封装变化`、`多用组合，少用继承`、`针对接口编程，不针对实现编程`等原则。

> 策略模式(Strategy Pattern)：定义一系列算法，将每一个算法封装起来，并让它们可以相互替换。策略模式让算法独立于使用它的客户而变化，也称为政策模式(Policy)。

# 2 用途

结合策略模式的概念，我们找一个实际的场景来理解一下。

假设我们是一家新开的书店，为了招揽顾客，我们推出会员服务，我们把店里的会员分为三种，分别是初级会员、中级会员和高级会员。针对不同级别的会员我们给予不同的优惠。初级会员买书我们不打折、中级会员买书我们打九折、高级会员买书我们打八折。

我们希望用户在付款的时候，只要刷一下书的条形码，会员再刷一下他的会员卡，收银台的工组人员就能直接知道应该向顾客收取多少钱。

在不使用模式的情况下，我们可以在结算的方法中使用`if/else`语句来区别出不同的会员来计算价格。

但是，如果我们有一天想要把初级会员的折扣改成9.8折怎么办？有一天我要推出超级会员怎么办？有一天我要针对中级会员可打折的书的数量做限制怎么办？

使用`if\else`设计出来的系统，所有的算法都写在了一起，只要有改动我就要修改整个类。我们都知道，只要是修改代码就有可能引入问题。为了避免这个问题，我们可以使用策略模式。。。

> 对于收银台系统，计算应收款的时候，一个客户只可能是初级、中级、高级会员中的一种。不同的会员使用不同的算法来计算价格。收银台系统其实不关心具体的会员类型和折扣之间的关系。也不希望会员和折扣之间的任何改动会影响到收银台系统。

在介绍策略模式的具体实现方式之前，再来巩固一下几个面向对象设计原则：`封装变化`、`多用组合，少用继承`、`针对接口编程，不针对实现编程`。想一想如何运用到策略模式中，并且有什么好处。

# 3. 实现方式

策略模式包含如下角色：

> Context: 环境类
>
> Strategy: 抽象策略类
>
> ConcreteStrategy: 具体策略类

[![strategy](http://www.hollischuang.com/wp-content/uploads/2016/09/Strategy.jpg)](http://www.hollischuang.com/wp-content/uploads/2016/09/Strategy.jpg)

我们运用策略模式来实现一下书店的收银台系统。我们可以把会员抽象成一个策略类，不同的会员类型是具体的策略类。不同策略类里面实现了计算价格这一算法。然后通过组合的方式把会员集成到收银台中。

先定义一个接口，这个接口就是抽象策略类，该接口定义了计算价格方法，具体实现方式由具体的策略类来定义。

```java
/**
 * Created by hollis on 16/9/19. 会员接口
 */
public interface Member {

    /**
     * 计算应付价格
     * @param bookPrice 书籍原价(针对金额,建议使用BigDecimal,double会损失精度)
     * @return 应付金额
     */
    public double calPrice(double bookPrice);
}
```

针对不同的会员，定义三种具体的策略类，每个类中都分别实现计算价格方法。

```java
/**
 * Created by hollis on 16/9/19. 初级会员
 */
public class PrimaryMember implements Member {

    @Override
    public double calPrice(double bookPrice) {
        System.out.println("对于初级会员的没有折扣");
        return bookPrice;
    }
}


/**
 * Created by hollis on 16/9/19. 中级会员,买书打九折
 */
public class IntermediateMember implements Member {

    @Override
    public double calPrice(double bookPrice) {
        System.out.println("对于中级会员的折扣为10%");
        return bookPrice * 0.9;
    }
}


/**
 * Created by hollis on 16/9/19. 高级会员,买书打八折
 */
public class AdvancedMember implements Member {

    @Override
    public double calPrice(double bookPrice) {
        System.out.println("对于中级会员的折扣为20%");
        return bookPrice * 0.8;
    }
}
```

上面几个类的定义体现了`封装变化`的设计原则，不同会员的具体折扣方式改变不会影响到其他的会员。

定义好了抽象策略类和具体策略类之后，我们再来定义环境类，所谓环境类，就是集成算法的类。这个例子中就是收银台系统。采用组合的方式把会员集成进来。

```
/**
 * Created by hollis on 16/9/19. 书籍价格类
 */
public class Cashier {

    /**
     * 会员,策略对象
     */
    private Member member;

    public Cashier(Member member){
        this.member = member;
    }

    /**
     * 计算应付价格
     * @param booksPrice
     * @return
     */
    public double quote(double booksPrice) {
        return this.member.calPrice(booksPrice);
    }
}
```

这个Cashier类就是一个环境类，该类的定义体现了`多用组合，少用继承`、`针对接口编程，不针对实现编程`两个设计原则。由于这里采用了组合+接口的方式，后面我们在推出超级会员的时候无须修改Cashier类。只要再定义一个`SuperMember implements Member` 就可以了。

下面定义一个客户端来测试一下：

```
/**
 * Created by hollis on 16/9/19.
 */
public class BookStore {

    public static void main(String[] args) {

        //选择并创建需要使用的策略对象
        Member strategy = new AdvancedMember();
        //创建环境
        Cashier cashier = new Cashier(strategy);
        //计算价格
        double quote = cashier.quote(300);
        System.out.println("高级会员图书的最终价格为：" + quote);

        strategy = new IntermediateMember();
        cashier = new Cashier(strategy);
        quote = cashier.quote(300);
        System.out.println("中级会员图书的最终价格为：" + quote);
    }
}

//对于中级会员的折扣为20%
//高级会员图书的最终价格为：240.0
//对于中级会员的折扣为10%
//中级会员图书的最终价格为：270.0
```

从上面的示例可以看出，策略模式仅仅封装算法，提供新的算法插入到已有系统中，策略模式并不决定在何时使用何种算法。在什么情况下使用什么算法是由客户端决定的。

- 策略模式的重心
  - 策略模式的重心不是如何实现算法，而是如何组织、调用这些算法，从而让程序结构更灵活，具有更好的维护性和扩展性。
- 算法的平等性
  - 策略模式一个很大的特点就是各个策略算法的平等性。对于一系列具体的策略算法，大家的地位是完全一样的，正因为这个平等性，才能实现算法之间可以相互替换。所有的策略算法在实现上也是相互独立的，相互之间是没有依赖的。
  - 所以可以这样描述这一系列策略算法：策略算法是相同行为的不同实现。
- 运行时策略的唯一性
  - 运行期间，策略模式在每一个时刻只能使用一个具体的策略实现对象，虽然可以动态地在不同的策略实现中切换，但是同时只能使用一个。
- 公有的行为
  - 经常见到的是，所有的具体策略类都有一些公有的行为。这时候，就应当把这些公有的行为放到共同的抽象策略角色Strategy类里面。当然这时候抽象策略角色必须要用Java抽象类实现，而不能使用接口。（[《JAVA与模式》之策略模式](http://www.cnblogs.com/java-my-life/archive/2012/05/10/2491891.html)）

# 4. 策略模式的优缺点

## 4.1 优点

- 策略模式提供了对“开闭原则”的完美支持，用户可以在不修改原有系统的基础上选择算法或行为，也可以灵活地增加新的算法或行为。
- 策略模式提供了管理相关的算法族的办法。策略类的等级结构定义了一个算法或行为族。恰当使用继承可以把公共的代码移到父类里面，从而避免代码重复。
- 使用策略模式可以避免使用多重条件(if-else)语句。多重条件语句不易维护，它把采取哪一种算法或采取哪一种行为的逻辑与算法或行为的逻辑混合在一起，统统列在一个多重条件语句里面，比使用继承的办法还要原始和落后。

## 4.2 缺点

- 客户端必须知道所有的策略类，并自行决定使用哪一个策略类。这就意味着客户端必须理解这些算法的区别，以便适时选择恰当的算法类。
- 由于策略模式把每个具体的策略实现都单独封装成为类，如果备选的策略很多的话，那么对象的数目就会很可观。可以通过使用享元模式在一定程度上减少对象的数量。

文中所有代码见[GitHub](https://github.com/hollischuang/DesignPattern)

# 5. 参考资料

[《JAVA与模式》之策略模式](http://www.cnblogs.com/java-my-life/archive/2012/05/10/2491891.html)

# 6. 郭霖策略模式

今天你的leader兴致冲冲地找到你，希望你可以帮他一个小忙，他现在急着要去开会。要帮什么忙呢？你很好奇。



他对你说，当前你们项目的数据库中有一张用户信息表，里面存放了很用户的数据，现在需要完成一个选择性查询用户信息的功能。他说会传递给你一个包含许多用户名的数组，你需要根据这些用户名把他们相应的数据都给查出来。



这个功能很简单的嘛，你爽快地答应了。由于你们项目使用的是MySQL数据库，你很快地写出了如下代码：

public class QueryUtil {

	public void findUserInfo(String[] usernames) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",
				"123456");
		Statement stat = conn.createStatement();
		StringBuilder sql = new StringBuilder("select * from user_info where ");
		for (String user : usernames) {
			sql.append("username = '");
			sql.append(user);
			sql.append("' or ");
		}
		System.out.println(sql);
		ResultSet resultSet = stat.executeQuery(sql.toString());
		while (resultSet.next()) {
			// 处理从数据库读出来的数据
		}
		// 后面应将读到的数据组装成对象返回，这里略去。
	}
}
这里根据传入的用户名数组拼装了SQL语句，然后去数据库中查找相应的行。为了方面调试，你还将拼装好的SQL语句打印了出来。


然后，你写了如下代码来测试这个方法：

public class Test {

	public static void main(String[] args) throws Exception {
		QueryUtil query = new QueryUtil();
		query.findUserInfo(new String[] { "Tom", "Jim", "Anna" });
	}

}
现在运行一下测试代码，你发现程序出错了。于是你立刻去检查了一下打印的SQL语句，果然发现了问题。
select * from user_info where username = 'Tom' or username = 'Jim' or username = 'Anna' or 
拼装出来的SQL语句在最后多加了一个 or 关键字！因为for循环执行到最后一条数据时不应该再加上or，可是代码很笨地给最后一条数据也加了or关键字，导致SQL语句语法出错了。


这可怎么办呢？



有了！你灵光一闪，想出了一个解决办法。等SQL语句拼装完成后，把最后一个or删除掉不就好了么。于是你将代码改成如下所示：

public class QueryUtil {

	public void findUserInfo(String[] usernames) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",
				"123456");
		Statement stat = conn.createStatement();
		StringBuilder sql = new StringBuilder("select * from user_info where ");
		for (String user : usernames) {
			sql.append("username = '");
			sql.append(user);
			sql.append("' or ");
		}
		sql.delete(sql.length() - " or ".length(), sql.length());
		System.out.println(sql);
		ResultSet resultSet = stat.executeQuery(sql.toString());
		while (resultSet.next()) {
			// 处理从数据库读出来的数据
		}
		// 后面应将读到的数据组装成对象返回，这里略去。
	}
}
使用StringBuilder的delete方法，把最后多余的一个or删除掉了，这样再运行测试代码，一切就正常了，打印的SQL语句如下所示：

select * from user_info where username = 'Tom' or username = 'Jim' or username = 'Anna'
好了，完工！你自信满满。



你的leader开完会后，过来看了下你的成果。总体来说，他还挺满意，但对于你使用的SQL语句拼装算法，他总是感觉有些不对劲，可是又说不上哪里不好。于是他告诉了你另一种拼装SQL语句的算法，让你加入到代码中，但是之前的那种算法也不要删除，先保留着再说，然后他又很忙似的跑开了。于是，你把他刚刚教你的算法加了进去，代码如下所示：

public class QueryUtil {

	public void findUserInfo(String[] usernames, int strategy) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",
				"123456");
		Statement stat = conn.createStatement();
		StringBuilder sql = new StringBuilder("select * from user_info where ");
		if (strategy == 1) {
			for (String user : usernames) {
				sql.append("username = '");
				sql.append(user);
				sql.append("' or ");
			}
			sql.delete(sql.length() - " or ".length(), sql.length());
		} else if (strategy == 2) {
			boolean needOr = false;
			for (String user : usernames) {
				if (needOr) {
					sql.append(" or ");
				}
				sql.append("username = '");
				sql.append(user);
				sql.append("'");
				needOr = true;
			}
		}
		System.out.println(sql);
		ResultSet resultSet = stat.executeQuery(sql.toString());
		while (resultSet.next()) {
			// 处理从数据库读出来的数据
		}
		// 后面应将读到的数据组装成对象返回，这里略去。
	}
}
可以看到，你leader教你的拼装算法，使用了一个布尔变量来控制是否需要加个or这个关键字，第一次执行for循环的时候因为该布尔值为false，所以不会加上or，在循环的最后将布尔值赋值为true，这样以后循环每次都会在头部加上一个or关键字，由于使用了头部添加or的方法，所以不用再担心SQL语句的尾部会多出一个or来。然后你为了将两个算法都保留，在findUserInfo方法上加了一个参数，strategy值为1表示使用第一种算法，strategy值为2表示使用第二种算法。


这样测试代码也需要改成如下方式：

public class Test {

	public static void main(String[] args) throws Exception {
		QueryUtil query = new QueryUtil();
		query.findUserInfo(new String[] { "Tom", "Jim", "Anna" }, 2);
	}

}
这里你通过参数指明了使用第二种算法来拼装SQL语句，打印的结果和使用第一种算法是完全相同的。



你立刻把你的leader从百忙之中拖了过来，让他检验一下你当前的成果，可是他还是一如既往的挑剔。


“你这样写的话，findUserInfo这个方法的逻辑就太复杂了，非常不利于阅读，也不利于将来的扩展，如果我还有第三第四种算法想加进去，这个方法还能看吗？”  你的leader指点你，遇到这种情况，就要使用策略模式来解决，策略模式的核心思想就是把算法提取出来放到一个独立的对象中。



为了指点你，他不顾自己的百忙，开始教你如何使用策略模式进行优化。



首先定义一个策略接口：

public interface Strategy {

	String getSQL(String[] usernames);

}
然后定义两个子类都实现了上述接口，并将两种拼装SQL语句的算法分别加入两个子类中：
public class Strategy1 implements Strategy {

	@Override
	public String getSQL(String[] usernames) {
		StringBuilder sql = new StringBuilder("select * from user_info where ");
		for (String user : usernames) {
			sql.append("username = '");
			sql.append(user);
			sql.append("' or ");
		}
		sql.delete(sql.length() - " or ".length(), sql.length());
		return sql.toString();
	}

}
public class Strategy2 implements Strategy {

	@Override
	public String getSQL(String[] usernames) {
		StringBuilder sql = new StringBuilder("select * from user_info where ");
		boolean needOr = false;
		for (String user : usernames) {
			if (needOr) {
				sql.append(" or ");
			}
			sql.append("username = '");
			sql.append(user);
			sql.append("'");
			needOr = true;
		}
		return sql.toString();
	}

}
然后把QueryUtil中findUserInfo方法的第二个参数改成Strategy对象，这样只需要调用Strategy的getSQL方法就可以获得拼装好的SQL语句，代码如下所示：
public class QueryUtil {

	public void findUserInfo(String[] usernames, Strategy strategy) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root",
				"123456");
		Statement stat = conn.createStatement();
		String sql = strategy.getSQL(usernames);
		System.out.println(sql);
		ResultSet resultSet = stat.executeQuery(sql);
		while (resultSet.next()) {
			// 处理从数据库读出来的数据
		}
		// 后面应将读到的数据组装成对象返回，这里略去。
	}
}
最后，测试代码在调用findUserInfo方法时，只需要显示地指明需要使用哪一个策略对象就可以了：
public class Test {

	public static void main(String[] args) throws Exception {
		QueryUtil query = new QueryUtil();
		query.findUserInfo(new String[] { "Tom", "Jim", "Anna" }, new Strategy1());
		query.findUserInfo(new String[] { "Jac", "Joe", "Rose" }, new Strategy2());
	}

}
打印出的SQL语句丝毫不出预料，如下所示：
select * from user_info where username = 'Tom' or username = 'Jim' or username = 'Anna'
select * from user_info where username = 'Jac' or username = 'Joe' or username = 'Rose'
使用策略模式修改之后，代码的可读性和扩展性都有了很大的提高，即使以后还需要添加新的算法，你也是手到擒来了！

策略：它定义了算法家庭，分别封装起来。让它们之间可以互相替换，此模式让算法的变化，不会影响到使用算法的客户。 

