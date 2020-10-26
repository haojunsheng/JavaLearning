# Java虚拟机基本原理

## 1. **Java**代码是怎么运行的

```shell
➜  java git:(master) ✗ java com.hjs.jvm.basic_1.Foo_01_1         
Hello, Java!
Hello, JVM!
➜  java git:(master) ✗ java -cp com/hjs/jvm/basic_1/asmtools.jar org.openjdk.asmtools.jdis.Main com/hjs/jvm/basic_1/Foo_01_1.class > Foo.jasm.1 
➜  java git:(master) ✗ awk 'NR==1,/iconst_1/{sub(/iconst_1/, "iconst_2")} 1' Foo.jasm.1 > Foo.jasm
➜  java git:(master) ✗ java -cp com/hjs/jvm/basic_1/asmtools.jar org.openjdk.asmtools.jasm.Main Foo.jasm
➜  java git:(master) ✗ java com.hjs.jvm.basic_1.Foo_01_1 
Hello, Java!
Hello, JVM!
```

## 2. **Java**虚拟机是如何加载Java类的?

java的数据类型：基本类型和引用类型：类、接口、数组类（数组类是由 Java 虚拟机直接生成的，其他两种则有对应的字节流）。

- 加载：双亲委派模型，



# 参考

[深入拆解Java虚拟机](https://time.geekbang.org/column/article/11074)

![img](https://static001.geekbang.org/resource/image/41/77/414248014bf825dd610c3095eed75377.jpg)