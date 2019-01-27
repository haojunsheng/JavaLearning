[TOC]

# 前言

我根据Hollis的行文习惯，尝试自己独立阅读下BigDecimal的源代码。

根据源码，我们知道，该类主要用于提供精确的计算。

# 1.定义

```java
public class BigDecimal extends Number implements Comparable<BigDecimal> {}
```

从该类的声明中我们可以看出BigDecimal是Number的子类。同时该类实现了接口： `Comparable<String>`，表明该类可以进行大小的比较。

# 2. 属性

