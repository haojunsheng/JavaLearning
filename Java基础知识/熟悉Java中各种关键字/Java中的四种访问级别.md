Java中有三个很常用的关键字：`public` `protected` 和 `private`。我们可以称呼他们为访问控制(级别)，也可称呼为作用域。怎么称呼都不重要，重要的是理解他们的作用及用法。

Java访问级别包含两个部分:1)对类的访问级别 2)对成员的访问级别。

在对类的访问进行限制的时候，关键字可以是`public`或者不明确指定类修饰符（`package-private`）。

在对类里面的成员做访问限制时，可以使用`public`，`protected`，`package-private`（不指明关键字），`private`

下面的表格总结了不同的关键字在修饰成员时候的访问级别。访问级别决定了类中的字段和方法的可访问性。

![image-20190130172434381](https://ws2.sinaimg.cn/large/006tNc79gy1fzoqk0rtokj316s0he0u6.jpg)

注意：外部类的访问修饰符只有public和包访问权限两种。内部类即私有。

