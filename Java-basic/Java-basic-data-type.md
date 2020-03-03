<!--ts-->

<!--te-->

## Java中有几种基本数据类型，如何分类的？

Java有8种基本数据类型

- byte：8位有符号的以二进制补码表示的整数
- short：16 位有符号的以二进制补码表示的整数
- int：32位有符号的以二进制补码表示的整数
- long：64 位有符号的以二进制补码表示的整数
- float：单精度、32位、符合IEEE 754标准的浮点数
- double：双精度、64 位、符合IEEE 754标准的浮点数
- char：单个16位Unicode字符
- boolean：true|false

分为三大类：

- 字符型：char
- 布尔型：boolean
- 数值型
  - 整型：int、byte、short、long
  - 浮点型：float、double



## 整型中 byte、short、int、long 的取值范围

### 整型几种类型的取值范围

```java
// byte
byte byte_min = Byte.MIN_VALUE;
byte byte_max = Byte.MAX_VALUE;

// short
short short_min = Short.MIN_VALUE;
short short_max = Short.MAX_VALUE;

// int
int int_min = Integer.MIN_VALUE;
int int_max = Integer.MAX_VALUE;

// long
long long_min = Long.MIN_VALUE;
long long_max = Long.MAX_VALUE;
```

打印结果

```
---byte---
-128
127
---short---
-32768
32767
---int---
-2147483648
2147483647
---long---
-9223372036854775808
9223372036854775807
```



## 什么是浮点型。什么是单精度和双精度。为什么代码中不要用浮点数表示金额

详细定义可以参考wiki的 [浮点数](https://zh.wikipedia.org/wiki/%E6%B5%AE%E7%82%B9%E6%95%B0)

简单来说：

**浮点数就是带有小数的数值**

单精度和双精度实际上是说明浮点数的存储位数

- 单精度是32位
- 双精度是64位

另外因为浮点数在计算过程中会丢失精度，所以并不能使用浮点数来表示金额

在Java中用来表示金额可以使用BigDecimal或者Long（单位为分）。





