# 前言

# 什么是可变参数

可变参数(variable arguments)是在Java 1.5中引入的一个特性。它允许一个方法把任意数量的值作为参数。

```java
public static void main(String[] args) {
    print("a");
    print("a", "b");
    print("a", "b", "c");
}

public static void print(String ... s){
    for(String a: s)
        System.out.println(a);
}
```

#可变参数的工作原理

是用数组实现的。

可变参数在被使用的时候，他首先会创建一个数组，数组的长度就是调用该方法是传递的实参的个数，然后再把参数值全部放到这个数组当中，然后再把这个数组作为参数传递到被调用的方法中。

# 可变参数的使用场景

通过其定义我们知道，当一个方法可能要接收任意数量的参数的时候使用可变参数是非常有用的。Java SDK中有个很好的例子：`String.format(String format, Object... args)`。一个字符串可以中可以包含任意个待格式化参数，所以使用可变参数。

```java
String.format("An integer: %d", i);
String.format("An integer: %d and a string: %s", i, s);
```