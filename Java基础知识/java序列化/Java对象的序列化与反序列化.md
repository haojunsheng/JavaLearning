[TOC]

# 前言

# 序列化与反序列化

序列化 (Serialization)是将**对象的状态信息**转换为可以**存储或传输**的形式的过程。一般将一个对象存储至一个储存媒介，例如档案或是记亿体缓冲等。在网络传输过程中，可以是字节或是XML等格式。而字节的或XML编码格式可以还原完全相等的对象。这个相反的过程又称为反序列化。

# Java对象的序列化与反序列化

在Java中，我们可以通过多种方式来创建对象，并且只要对象没有被回收我们都可以复用该对象。但是，我们创建出来的这些Java对象都是存在于JVM的堆内存中的。只有JVM处于运行状态的时候，这些对象才可能存在。一旦JVM停止运行，这些对象的状态也就随之而丢失了。

但是在真实的应用场景中，我们需要将这些对象持久化下来，并且能够在需要的时候把对象重新读取出来。Java的对象序列化可以帮助我们实现该功能。

对象序列化机制（object serialization）是Java语言内建的一种对象持久化方式，**通过对象序列化，可以把对象的状态保存为字节数组，并且可以在有需要的时候将这个字节数组通过反序列化的方式再转换成对象。**对象序列化可以很容易的在JVM中的活动对象和字节数组（流）之间进行转换。

在Java中，对象的序列化与反序列化被广泛应用到RMI(远程方法调用)及网络传输中。

# 相关接口及类

Java为了方便开发人员将Java对象进行序列化及反序列化提供了一套方便的API来支持。其中包括以下接口和类：

> java.io.Serializable
>
> java.io.Externalizable
>
> ObjectOutput
>
> ObjectInput
>
> ObjectOutputStream
>
> ObjectInputStream

## Serializable 接口

类通过实现 `java.io.Serializable` 接口以启用其序列化功能。未实现此接口的类将无法使其任何状态序列化或反序列化。可序列化类的所有子类型本身都是可序列化的。**序列化接口没有方法或字段，仅用于标识可序列化的语义。**([该接口并没有方法和字段，为什么只有实现了该接口的类的对象才能被序列化呢？](http://www.hollischuang.com/archives/1140#What%20Serializable%20Did))

当试图对一个对象进行序列化的时候，如果遇到不支持 Serializable 接口的对象。在此情况下，将抛出 `NotSerializableException`。

如果要序列化的类有父类，要想同时将在父类中定义过的变量持久化下来，那么父类也应该集成`java.io.Serializable`接口。

下面是一个实现了`java.io.Serializable`接口的类

```java
package com.hollischaung.serialization.SerializableDemos;
import java.io.Serializable;
/**
 * Created by hollis on 16/2/17.
 * 实现Serializable接口
 */
public class User1 implements Serializable {

    private String name;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

通过下面的代码进行序列化及反序列化

```java
package com.hollischaung.serialization.SerializableDemos;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
/**
 * Created by hollis on 16/2/17.
 * SerializableDemo1 结合SerializableDemo2说明 一个类要想被序列化必须实现Serializable接口
 */
public class SerializableDemo1 {

    public static void main(String[] args) {
        //Initializes The Object
        User1 user = new User1();
        user.setName("hollis");
        user.setAge(23);
        System.out.println(user);

        //Write Obj to File
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
        }

        //Read Obj from File
        File file = new File("tempFile");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            User1 newUser = (User1) ois.readObject();
            System.out.println(newUser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

//OutPut:
//User{name='hollis', age=23}
//User{name='hollis', age=23}
```

更多关于Serializable的使用，请参考[代码实例](https://github.com/hollischuang/java-demo/tree/master/src/main/java/com/hollischaung/serialization/SerializableDemos)

## Externalizable接口

除了Serializable 之外，java中还提供了另一个序列化接口`Externalizable`

为了了解Externalizable接口和Serializable接口的区别，先来看代码，我们把上面的代码改成使用Externalizable的形式。

```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by hollis on 16/2/17.
 * 实现Externalizable接口
 */
public class User1 implements Externalizable {

    private String name;
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

    public void writeExternal(ObjectOutput out) throws IOException {

    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

 

```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.*;

/**
 * Created by hollis on 16/2/17.
 */
public class ExternalizableDemo1 {

    //为了便于理解和节省篇幅，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //IOException直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        User1 user = new User1();
        user.setName("hollis");
        user.setAge(23);
        oos.writeObject(user);
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        User1 newInstance = (User1) ois.readObject();
        //output
        System.out.println(newInstance);
    }
}
//OutPut:
//User{name='null', age=0}
```

通过上面的实例可以发现，对User1类进行序列化及反序列化之后得到的对象的所有属性的值都变成了默认值。也就是说，之前的那个对象的状态并没有被持久化下来。这就是Externalizable接口和Serializable接口的区别：

Externalizable继承了Serializable，该接口中定义了两个抽象方法：`writeExternal()`与`readExternal()`。当使用Externalizable接口来进行序列化与反序列化的时候**需要开发人员重写`writeExternal()`与`readExternal()`方法**。由于上面的代码中，并没有在这两个方法中定义序列化实现细节，所以输出的内容为空。还有一点值得注意：在使用Externalizable进行序列化的时候，在读取对象时，会调用被序列化类的无参构造器去创建一个新的对象，然后再将被保存对象的字段的值分别填充到新对象中。所以，**实现Externalizable接口的类必须要提供一个public的无参的构造器。**

按照要求修改之后代码如下：

```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by hollis on 16/2/17.
 * 实现Externalizable接口,并实现writeExternal和readExternal方法
 */
public class User2 implements Externalizable {

    private String name;
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

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
```

 

```java
package com.hollischaung.serialization.ExternalizableDemos;

import java.io.*;

/**
 * Created by hollis on 16/2/17.
 */
public class ExternalizableDemo2 {

    //为了便于理解和节省篇幅，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //IOException直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        User2 user = new User2();
        user.setName("hollis");
        user.setAge(23);
        oos.writeObject(user);
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        User2 newInstance = (User2) ois.readObject();
        //output
        System.out.println(newInstance);
    }
}
//OutPut:
//User{name='hollis', age=23}
```

这次，就可以把之前的对象状态持久化下来了。

> 如果User类中没有无参数的构造函数，在运行时会抛出异常：`java.io.InvalidClassException`

更多Externalizable接口使用实例请参考[代码实例](https://github.com/hollischuang/java-demo/tree/master/src/main/java/com/hollischaung/serialization/ExternalizableDemos)

## ObjectOutput和ObjectInput 接口

ObjectInput接口扩展自 DataInput 接口以包含对象的读操作。

> DataInput 接口用于从二进制流中读取字节，并根据所有 Java 基本类型数据进行重构。同时还提供根据 UTF-8 修改版格式的数据重构 String 的工具。
>
> 对于此接口中的所有数据读取例程来说，如果在读取所需字节数之前已经到达文件末尾 (end of file)，则将抛出 EOFException（IOException 的一种）。如果因为到达文件末尾以外的其他原因无法读取字节，则将抛出 IOException 而不是 EOFException。尤其是，在输入流已关闭的情况下，将抛出 IOException。

ObjectOutput 扩展 DataOutput 接口以包含对象的写入操作。

> DataOutput 接口用于将数据从任意 Java 基本类型转换为一系列字节，并将这些字节写入二进制流。同时还提供了一个将 String 转换成 UTF-8 修改版格式并写入所得到的系列字节的工具。
>
> 对于此接口中写入字节的所有方法，如果由于某种原因无法写入某个字节，则抛出 IOException。

## ObjectOutputStream类和ObjectInputStream类

通过前面的代码片段中我们也能知道，我们一般使用ObjectOutputStream的`writeObject`方法把一个对象进行持久化。再使用ObjectInputStream的`readObject`从持久化存储中把对象读取出来。

更多关于ObjectInputStream和ObjectOutputStream的相关知识欢迎阅读我的另外两篇博文：[深入分析Java的序列化与反序列化](http://www.hollischuang.com/archives/1140)、[单例与序列化的那些事儿](http://www.hollischuang.com/archives/1144)

# Transient 关键字

Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。关于Transient 关键字的拓展知识欢迎阅读[深入分析Java的序列化与反序列化](http://www.hollischuang.com/archives/1140)

# 序列化ID

虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致（就是 `private static final long serialVersionUID`)

**情境**：两个客户端 A 和 B 试图通过网络传递对象数据，A 端将对象 C 序列化为二进制数据再传给 B，B 反序列化得到 C。

**问题**：C 对象的全类路径假设为 com.inout.Test，在 A 和 B 端都有这么一个类文件，功能代码完全一致。也都实现了 Serializable 接口，但是反序列化时总是提示不成功。

**解决**：**虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致（就是 private static final long serialVersionUID = 1L）**。清单 1 中，虽然两个类的功能代码完全一致，但是序列化 ID 不同，他们无法相互序列化和反序列化。

清单 1. 相同功能代码不同序列化 ID 的类对比

```java
`package com.inout; ` `import java.io.Serializable; ` `public class A implements Serializable { ` `    ``private static final long serialVersionUID = 1L; ` `    ``private String name; ``   ` `    ``public String getName() ``    ``{ ``        ``return name; ``    ``} ``   ` `    ``public void setName(String name) ``    ``{ ``        ``this.name = name; ``    ``} ``} ` `package com.inout; ` `import java.io.Serializable; ` `public class A implements Serializable { ` `    ``private static final long serialVersionUID = 2L; ``   ` `    ``private String name; ``   ` `    ``public String getName() ``    ``{ ``        ``return name; ``    ``} ``   ` `    ``public void setName(String name) ``    ``{ ``        ``this.name = name; ``    ``} ``}`
```

序列化 ID 在 Eclipse 下提供了两种生成策略，一个是固定的 1L，一个是随机生成一个不重复的 long 类型数据（实际上是使用 JDK 工具生成），在这里有一个建议，如果没有特殊需求，就是用默认的 1L 就可以，这样可以确保代码一致时反序列化成功。那么随机生成的序列化 ID 有什么作用呢，有些时候，通过改变序列化 ID 可以用来限制某些用户的使用。

**特性使用案例**

读者应该听过 Façade 模式，它是为应用程序提供统一的访问接口，案例程序中的 Client 客户端使用了该模式，案例程序结构图如图 1 所示。

![](https://ws2.sinaimg.cn/large/006tNc79ly1g02u1awoimj30av069wet.jpg)

Client 端通过 Façade Object 才可以与业务逻辑对象进行交互。而客户端的 Façade Object 不能直接由 Client 生成，而是需要 Server 端生成，然后序列化后通过网络将二进制对象数据传给 Client，Client 负责反序列化得到 Façade 对象。该模式可以使得 Client 端程序的使用需要服务器端的许可，同时 Client 端和服务器端的 Façade Object 类需要保持一致。当服务器端想要进行版本更新时，只要将服务器端的 Façade Object 类的序列化 ID 再次生成，当 Client 端反序列化 Façade Object 就会失败，也就是强制 Client 端从服务器端获取最新程序。

# 静态变量序列化

**情境**：查看清单 2 的代码。

```java
import java.util.*;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Test implements Serializable {
 private static final long serialVersionUID = 1L;
 
    public static int staticVar = 5;
 
    public static void main(String[] args) {
        try {
            //初始时staticVar为5
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("result.obj"));
            out.writeObject(new Test());
            out.close();
 
            //序列化后修改为10
            Test.staticVar = 10;
 
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
                    "result.obj"));
            Test t = (Test) oin.readObject();
            oin.close();
             
            //再读取，通过t.staticVar打印新的值
            System.out.println(t.staticVar);
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

```

清单 2 中的 main 方法，将对象序列化后，修改静态变量的数值，再将序列化对象读取出来，然后通过读取出来的对象获得静态变量的数值并打印出来。依照清单 2，这个 System.out.println(t.staticVar) 语句输出的是 10 还是 5 呢？

最后的输出是 10，对于无法理解的读者认为，打印的 staticVar 是从读取的对象里获得的，应该是保存时的状态才对。之所以打印 10 的原因在于序列化时，并不保存静态变量，这其实比较容易理解，序列化保存的是对象的状态，静态变量属于类的状态，因此 **序列化并不保存静态变量**。

# 对敏感字段加密

**情境**：服务器端给客户端发送序列化对象数据，对象中有一些数据是敏感的，比如密码字符串等，希望对该密码字段在序列化时，进行加密，而客户端如果拥有解密的密钥，只有在客户端进行反序列化时，才可以对密码进行读取，这样可以一定程度保证序列化对象的数据安全。

**解决**：在序列化过程中，虚拟机会试图调用对象类里的 writeObject 和 readObject 方法，进行用户自定义的序列化和反序列化，如果没有这样的方法，则默认调用是 ObjectOutputStream 的 defaultWriteObject 方法以及 ObjectInputStream 的 defaultReadObject 方法。用户自定义的 writeObject 和 readObject 方法可以允许用户控制序列化的过程，比如可以在序列化的过程中动态改变序列化的数值。基于这个原理，可以在实际应用中得到使用，用于敏感字段的加密工作，清单 3 展示了这个过程。

```java
private static final long serialVersionUID = 1L;

private String password = "pass";
 
   public String getPassword() {
       return password;
   }
 
   public void setPassword(String password) {
       this.password = password;
   }
 
   private void writeObject(ObjectOutputStream out) {
       try {
           PutField putFields = out.putFields();
           System.out.println("原密码:" + password);
           password = "encryption";//模拟加密
           putFields.put("password", password);
           System.out.println("加密后的密码" + password);
           out.writeFields();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
 
   private void readObject(ObjectInputStream in) {
       try {
           GetField readFields = in.readFields();
           Object object = readFields.get("password", "");
           System.out.println("要解密的字符串:" + object.toString());
           password = "pass";//模拟解密,需要获得本地的密钥
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
 
   }
 
   public static void main(String[] args) {
       try {
           ObjectOutputStream out = new ObjectOutputStream(
                   new FileOutputStream("result.obj"));
           out.writeObject(new Test());
           out.close();
 
           ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
                   "result.obj"));
           Test t = (Test) oin.readObject();
           System.out.println("解密后的字符串:" + t.getPassword());
           oin.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
   }
```

在清单 3 的 writeObject 方法中，对密码进行了加密，在 readObject 中则对 password 进行解密，只有拥有密钥的客户端，才可以正确的解析出密码，确保了数据的安全。执行清单 3 后控制台输出如图 3 所示。

**特性使用案例**

RMI 技术是完全基于 Java 序列化技术的，服务器端接口调用所需要的参数对象来至于客户端，它们通过网络相互传输。这就涉及 RMI 的安全传输的问题。一些敏感的字段，如用户名密码（用户登录时需要对密码进行传输），我们希望对其进行加密，这时，就可以采用本节介绍的方法在客户端对密码进行加密，服务器端进行解密，确保数据传输的安全性。

# 序列化存储规则

情境：问题代码如清单 4 所示。

```java
ObjectOutputStream out = new ObjectOutputStream(
             new FileOutputStream("result.obj"));
   Test test = new Test();
   //试图将对象两次写入文件
   out.writeObject(test);
   out.flush();
   System.out.println(new File("result.obj").length());//31
   out.writeObject(test);
   out.close();
   System.out.println(new File("result.obj").length());//36
 
   ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
           "result.obj"));
   //从文件依次读出两个文件
   Test t1 = (Test) oin.readObject();
   Test t2 = (Test) oin.readObject();
   oin.close();
            
   //判断两个引用是否指向同一个对象
   System.out.println(t1 == t2);//true

```

清单 3 中对同一对象两次写入文件，打印出写入一次对象后的存储大小和写入两次后的存储大小，然后从文件中反序列化出两个对象，比较这两个对象是否为同一对象。一般的思维是，两次写入对象，文件大小会变为两倍的大小，反序列化时，由于从文件读取，生成了两个对象，判断相等时应该是输入 false 才对，但是最后结果输出如图 4 所示。

我们看到，第二次写入对象时文件只增加了 5 字节，并且两个对象是相等的，这是为什么呢？

**解答**：Java 序列化机制为了节省磁盘空间，具有特定的存储规则，当写入文件的为同一对象时，并不会再将对象的内容进行存储，而只是再次存储一份引用，上面增加的 5 字节的存储空间就是新增引用和一些控制信息的空间。反序列化时，恢复引用关系，使得清单 3 中的 t1 和 t2 指向唯一的对象，二者相等，输出 true。该存储规则极大的节省了存储空间。

**特性案例分析**

查看清单 5 的代码。

```java
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("result.obj"));
Test test = new Test();
test.i = 1;
out.writeObject(test);
out.flush();
test.i = 2;
out.writeObject(test);
out.close();
ObjectInputStream oin = new ObjectInputStream(new FileInputStream(
                    "result.obj"));
Test t1 = (Test) oin.readObject();
Test t2 = (Test) oin.readObject();
System.out.println(t1.i);
System.out.println(t2.i);
```

清单 5 的目的是希望将 test 对象两次保存到 result.obj 文件中，写入一次以后修改对象属性值再次保存第二次，然后从 result.obj 中再依次读出两个对象，输出这两个对象的 i 属性值。案例代码的目的原本是希望一次性传输对象修改前后的状态。

结果两个输出的都是 1， 原因就是第一次写入对象以后，第二次再试图写的时候，虚拟机根据引用关系知道已经有一个相同对象已经写入文件，因此只保存第二次写的引用，所以读取时，都是第一次保存的对象。读者在使用一个文件多次 writeObject 需要特别注意这个问题。

# 参考资料

[维基百科](https://zh.wikipedia.org/wiki/%E5%BA%8F%E5%88%97%E5%8C%96)

[理解Java对象序列化](http://www.blogjava.net/jiangshachina/archive/2012/02/13/369898.html)

[Java 序列化的高级认识](https://www.ibm.com/developerworks/cn/java/j-lo-serial/)

# 推荐阅读

[深入分析Java的序列化与反序列化](http://www.hollischuang.com/archives/1140)

[单例与序列化的那些事儿](http://www.hollischuang.com/archives/1144)