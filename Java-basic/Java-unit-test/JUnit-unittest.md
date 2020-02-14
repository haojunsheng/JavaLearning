[TOC]

# 前言

[上一弹](http://www.hollischuang.com/archives/1755)中介绍了单元测试以及单元测试框架，这一弹主要来介绍一下JUnit这个目前比较流行的单测框架。

JUnit是由 Erich Gamma 和 Kent Beck 编写的一个回归测试框架（regression testing framework）。Junit测试是程序员测试，即所谓白盒测试，因为程序员知道被测试的软件如何（How）完成功能和完成什么样（What）的功能。

现在很多IDE中都已经集成了JUnit，当我们在创建`maven`项目的时候，一般在`pom`文件中也会自动增加junit的依赖。

```java
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
    <version>4.4</version>
</dependency>
```

注意上面的maven的依赖中的`scope`，因为junit只在测试代码中会被用到，这里`scope`指定未`test`即可。我们直接使用和介绍JUnit4。

## 上手JUnit

要测试，要先有被测代码（当然，基于测试编程可以先有测试代码）。先来看我们想要测试的代码：

```
public class CaculateService {
    public float divide(float divisor, float dividend) {
        return divisor / dividend;
    }
}
```

我们想要测试这个类，那么如何使用Junit进行测试呢？先来写一个测试类。如果你使用的是`IntelliJ`+`Mac`，那么可以在类名上使用快捷键`option`+`enter`直接生成测试类，这样IDE会帮忙生成一个对应的测试类。（其他操作系统和IDE也有同样的功能）

![generate_test](http://www.hollischuang.com/wp-content/uploads/2017/01/test.png)

生成后的测试代码和被测代码所处路径如下：

![test_package](http://www.hollischuang.com/wp-content/uploads/2017/01/test_package.png)

可以看到，一般的maven项目中，会在`src/main`下面有两个目录，`java`和`test`，`java`目录中放的是源码，`test`目录中放的是测试代码。测试代码和测试代码的包名保持一致即可。

测试代码如下：

```
public class CaculateServiceTest {
    CaculateService caculateService = new CaculateService();
    @Test
    public void testDivide() throws Exception {
       Assert.assertEquals(caculateService.divide(2, 1), 2.0);
    }

}
```

然后执行该方法就可以了，先不管`Assert.assertEquals`的用法及结果，这里总结下使用JUnit写测试代码的简单步骤：

> 创建一个名为 `CaculateServiceTest.java` 的测试类。
>
> 向测试类中添加名为 `testDivide()` 的方法。
>
> 向方法中添加 Annotaion `@Test`。
>
> 执行测试条件并且应用 Junit 的 assertEquals API 来检查。

## JUnit中的Assert

```
public class Assert extends java.lang.Object
```

这个类提供了一系列的编写测试的有用的声明方法。只有失败的声明方法才会被记录。

- ```
  void assertEquals(boolean expected, boolean actual)
  ```

  - 检查两个变量或者等式是否平衡

- ```
  void assertFalse(boolean condition)
  ```

  - 检查条件是假的

- ```
  void assertNotNull(Object object)
  ```

  - 检查对象不是空的

- ```
  void assertNull(Object object)
  ```

  - 检查对象是空的

- ```
  void assertTrue(boolean condition)
  ```

  - 检查条件为真

- ```
  void fail()
  ```

  - 在没有报告的情况下使测试不通过

这些方法我就不一一介绍了，相信我的读者们都能看懂并在平时开发中用的到，还是比较容易理解的。

Assert可以用来判断方法的真是结果和预期结果是否一样。是我们在写单元测试中用到最多的一个api。

## JUnit中的注解

- `@BeforeClass`：针对所有测试，只执行一次，且必须为static void
- `@Before`：初始化方法
- `@Test`：测试方法，在这里可以测试期望异常和超时时间
- `@After`：释放资源
- `@AfterClass`：针对所有测试，只执行一次，且必须为static void
- `@Ignore`：忽略的测试方法

一个单元测试类执行顺序为：

```
@BeforeClass` –> `@Before` –> `@Test` –> `@After` –> `@AfterClass
```

每一个测试方法的调用顺序为：

```
@Before` –> `@Test` –> `@After
```

### 时间测试

如果一个测试用例比起指定的毫秒数花费了更多的时间，那么 Junit 将自动将它标记为失败。timeout 参数和 `@Test`注释一起使用。现在让我们看看活动中的 @test(timeout)。

```
@Test(timeout = 1000)
public void testTimeoutSuccess() {
    // do nothing
}
```

### 异常测试

你可以测试代码是否它抛出了想要得到的异常。expected 参数和 @Test 注释一起使用。现在让我们看看活动中的 @Test(expected)。

```
@Test(expected = NullPointerException.class)
public void testException() {
    throw new NullPointerException();
}
```

## 所有测试代码

[代码地址](https://github.com/hollischuang/EffectiveUT/blob/master/src/test/test/com/hollischuang/effective/unitest/service/JUnitTest.java)

```java
package com.hollischuang.effective.unitest.service;

import org.junit.*;

/**
 * @author Hollis 17/1/7.
 */
public class JUnitTest {

    /**
     * 只执行一次,在整个类执行之前执行
     */
    @BeforeClass
    public static void beforeClass() {
        System.out.println("in before class");
    }

    /**
     * 只执行一次,在整个类执行之后执行
     */
    @AfterClass
    public static void afterClass() {
        System.out.println("in after class");
    }

    /**
     * 每个测试方法被执行前都被执行一次
     */
    @Before
    public void before() {
        System.out.println("in before");
    }

    /**
     * 每个测试方法被执行后都被执行一次
     */
    @After
    public void after() {
        System.out.println("in after");
    }

    // test case 1
    @Test
    public void testCase1() {
        System.out.println("in test case 1");
    }

    // test case 2
    @Test
    public void testCase2() {
        System.out.println("in test case 2");
    }

    /**
     * 测试assertEquals
     */
    @Test
    public void testEquals() {
        Assert.assertEquals(1 + 2, 3);
    }

    /**
     * 测试assertTrue
     */
    @Test
    public void testTrue() {
        Assert.assertTrue(1 + 2 == 3);
    }

    /**
     * 测试assertFalse
     */
    @Test
    public void testFals() {
        Assert.assertFalse(1 + 2 == 4);
    }

    /**
     * 测试assertNotNull
     */
    @Test
    public void assertNotNull() {
        Assert.assertNotNull("not null");
    }

    /**
     * 测试assertNull
     */
    @Test
    public void assertNull() {
        Assert.assertNull(null);
    }

    /**
     * 测试fail和Ignore
     */
    @Test
    @Ignore
    public void assertFail() {
        Assert.fail();
    }

    /**
     * 测试异常
     */
    @Test(expected = NullPointerException.class)
    public void testException() {
        throw new NullPointerException();
    }

    /**
     * 测试时间
     */
    @Test(timeout = 1000)
    public void testTimeoutSuccess() {
        // do nothing
    }

    /**
     * 测试时间
     */
    @Test(timeout = 1000)
    public void testTimeoutFailed() {
        while (true) {

        }
    }
}
```

## 总结

本文主要介绍了JUnit的常见用法，后面会专门写一篇文章介绍如何将JUnit和Spring集合到一起。