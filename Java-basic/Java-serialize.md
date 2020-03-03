<!--ts-->
   * [前言](#前言)
   * [1. java序列化和反序列化](#1-java序列化和反序列化)
      * [前言](#前言-1)
      * [Java对象的序列化](#java对象的序列化)
      * [如何对Java对象进行序列化与反序列化](#如何对java对象进行序列化与反序列化)
      * [序列化及反序列化相关知识](#序列化及反序列化相关知识)
      * [相关接口和类](#相关接口和类)
         * [Serializable 接口](#serializable-接口)
         * [Externalizable接口](#externalizable接口)
      * [ArrayList的序列化](#arraylist的序列化)
         * [writeObject和readObject方法](#writeobject和readobject方法)
         * [why transient](#why-transient)
         * [why writeObject and readObject](#why-writeobject-and-readobject)
         * [ObjectOutputStream](#objectoutputstream)
      * [Transient 关键字](#transient-关键字)
      * [序列化ID](#序列化id)
      * [总结](#总结)
   * [2. 单例与序列化](#2-单例与序列化)
      * [前言](#前言-2)
      * [序列化对单例的破坏](#序列化对单例的破坏)
      * [ObjectInputStream](#objectinputstream)
      * [防止序列化破坏单例模式](#防止序列化破坏单例模式)
      * [总结](#总结-1)
   * [3. Google Protocol Buffer序列化](#3-google-protocol-buffer序列化)
      * [简介](#简介)
      * [一个简单的例子](#一个简单的例子)
         * [安装 Google Protocol Buffer](#安装-google-protocol-buffer)
         * [关于简单例子的描述](#关于简单例子的描述)
         * [编写 writer 和 Reader](#编写-writer-和-reader)
         * [和其他类似技术的比较](#和其他类似技术的比较)
            * [Protobuf 的优点](#protobuf-的优点)
            * [Protobuf 的不足](#protobuf-的不足)
      * [高级应用话题](#高级应用话题)
         * [更复杂的 Message](#更复杂的-message)
         * [动态编译](#动态编译)
         * [编写新的 proto 编译器](#编写新的-proto-编译器)
      * [Protobuf 的更多细节](#protobuf-的更多细节)
         * [Google Protocol Buffer 的 Encoding](#google-protocol-buffer-的-encoding)
      * [封解包的速度](#封解包的速度)
   * [4. java序列化的5个问题](#4-java序列化的5个问题)
      * [前言](#前言-3)
      * [Java 序列化简介](#java-序列化简介)
      * [1. 序列化允许重构](#1-序列化允许重构)
         * [重构序列化类](#重构序列化类)
      * [2. 序列化并不安全](#2-序列化并不安全)
         * [模糊化序列化数据](#模糊化序列化数据)
      * [3. 序列化的数据可以被签名和密封](#3-序列化的数据可以被签名和密封)
      * [4. 序列化允许将代理放在流中](#4-序列化允许将代理放在流中)
         * [打包和解包代理](#打包和解包代理)
      * [5. 信任，但要验证](#5-信任但要验证)
      * [结束语](#结束语)
   * [5. 码农翻身版](#5-码农翻身版)
   * [6. 枚举与序列化](#6-枚举与序列化)
      * [枚举是如何保证线程安全的](#枚举是如何保证线程安全的)
      * [为什么用枚举实现的单例是最好的方式](#为什么用枚举实现的单例是最好的方式)

<!-- Added by: anapodoton, at: Tue Mar  3 17:09:07 CST 2020 -->

<!--te-->

# 前言

# 1. java序列化和反序列化

## 前言

序列化 (Serialization)是将对象的状态信息转换为可以存储或传输的形式的过程。一般将一个对象存储至一个储存媒介，例如档案或是记亿体缓冲等。在网络传输过程中，可以是字节或是XML等格式。而字节的或XML编码格式可以还原完全相等的对象。这个相反的过程又称为反序列化。

序列化是一种对象持久化的手段。普遍应用在网络传输、RMI等场景中。本文通过分析ArrayList的序列化来介绍Java序列化的相关内容。主要涉及到以下几个问题：

> 怎么实现Java的序列化
>
> 为什么实现了java.io.Serializable接口才能被序列化
>
> transient的作用是什么
>
> 怎么自定义序列化策略
>
> 自定义的序列化策略是如何被调用的
>
> ArrayList对序列化的实现有什么好处

## Java对象的序列化

Java平台允许我们在内存中创建可复用的Java对象，但一般情况下，只有当JVM处于运行时，这些对象才可能存在，即，这些对象的生命周期不会比JVM的生命周期更长。但在现实应用中，就可能要求在JVM停止运行之后能够保存(持久化)指定的对象，并在将来重新读取被保存的对象。Java对象序列化就能够帮助我们实现该功能。

使用Java对象序列化，在保存对象时，会把其状态保存为一组字节，在未来，再将这些字节组装成对象。必须注意地是，对象序列化保存的是对象的”状态”，即它的成员变量。由此可知，**对象序列化不会关注类中的静态变量**。

除了在持久化对象时会用到对象序列化之外，当使用RMI(远程方法调用)，或在网络中传递对象时，都会用到对象序列化。Java序列化API为处理对象序列化提供了一个标准机制，该API简单易用。

## 如何对Java对象进行序列化与反序列化

在Java中，只要一个类实现了`java.io.Serializable`接口，那么它就可以被序列化。这里先来一段代码：

code 1 创建一个User类，用于序列化及反序列化

```java
package com.hollis;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hollis on 16/2/2.
 */
public class User implements Serializable{
    private String name;
    private int age;
    private Date birthday;
    private transient String gender;
    private static final long serialVersionUID = -6849794470754667710L;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", birthday=" + birthday +
                '}';
    }
}
```

code 2 对User进行序列化及反序列化的Demo

```java
package com.hollis;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.util.Date;

/**
 * Created by hollis on 16/2/2.
 */
public class SerializableDemo {

    public static void main(String[] args) {
        //Initializes The Object
        User user = new User();
        user.setName("hollis");
        user.setGender("male");
        user.setAge(23);
        user.setBirthday(new Date());
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
            User newUser = (User) ois.readObject();
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
//output 
//User{name='hollis', age=23, gender=male, birthday=Tue Feb 02 17:37:38 CST 2016}
//User{name='hollis', age=23, gender=null, birthday=Tue Feb 02 17:37:38 CST 2016}
```

## 序列化及反序列化相关知识

1、在Java中，只要一个类实现了`java.io.Serializable`接口，那么它就可以被序列化。

2、通过`ObjectOutputStream`和`ObjectInputStream`对对象进行序列化及反序列化

3、虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致（就是 `private static final long serialVersionUID`）

4、序列化并不保存静态变量。

5、要想将父类对象也序列化，就需要让父类也实现`Serializable` 接口。

6、Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。

7、服务器端给客户端发送序列化对象数据，对象中有一些数据是敏感的，比如密码字符串等，希望对该密码字段在序列化时，进行加密，而客户端如果拥有解密的密钥，只有在客户端进行反序列化时，才可以对密码进行读取，这样可以一定程度保证序列化对象的数据安全。

## 相关接口和类

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

### Serializable 接口

类通过实现 `java.io.Serializable` 接口以启用其序列化功能。未实现此接口的类将无法使其任何状态序列化或反序列化。可序列化类的所有子类型本身都是可序列化的。**序列化接口没有方法或字段，仅用于标识可序列化的语义。** ([该接口并没有方法和字段，为什么只有实现了该接口的类的对象才能被序列化呢？](https://47.99.194.156/archives/1140#What Serializable Did))

当试图对一个对象进行序列化的时候，如果遇到不支持 Serializable 接口的对象。在此情况下，将抛出 `NotSerializableException`。

如果要序列化的类有父类，要想同时将在父类中定义过的变量持久化下来，那么父类也应该集成`java.io.Serializable`接口。

下面是一个实现了`java.io.Serializable`接口的类

```
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

```
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

### Externalizable接口

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

Externalizable继承了Serializable，该接口中定义了两个抽象方法：`writeExternal()`与`readExternal()`。当使用Externalizable接口来进行序列化与反序列化的时候需要开发人员重写`writeExternal()`与`readExternal()`方法。由于上面的代码中，并没有在这两个方法中定义序列化实现细节，所以输出的内容为空。还有一点值得注意：在使用Externalizable进行序列化的时候，在读取对象时，会调用被序列化类的无参构造器去创建一个新的对象，然后再将被保存对象的字段的值分别填充到新对象中。所以，实现Externalizable接口的类必须要提供一个public的无参的构造器。

按照要求修改之后代码如下：

```
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

 

```
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

## ArrayList的序列化

在介绍ArrayList序列化之前，先来考虑一个问题：

> **如何自定义的序列化和反序列化策略**

带着这个问题，我们来看`java.util.ArrayList`的源码

code 3

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
    private static final long serialVersionUID = 8683452581122892189L;
    transient Object[] elementData; // non-private to simplify nested class access
    private int size;
}
```

笔者省略了其他成员变量，从上面的代码中可以知道ArrayList实现了`java.io.Serializable`接口，那么我们就可以对它进行序列化及反序列化。因为elementData是`transient`的，所以我们认为这个成员变量不会被序列化而保留下来。我们写一个Demo，验证一下我们的想法：

code 4

```java
public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> stringList = new ArrayList<String>();
        stringList.add("hello");
        stringList.add("world");
        stringList.add("hollis");
        stringList.add("chuang");
        System.out.println("init StringList" + stringList);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("stringlist"));
        objectOutputStream.writeObject(stringList);

        IOUtils.close(objectOutputStream);
        File file = new File("stringlist");
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        List<String> newStringList = (List<String>)objectInputStream.readObject();
        IOUtils.close(objectInputStream);
        if(file.exists()){
            file.delete();
        }
        System.out.println("new StringList" + newStringList);
    }
//init StringList[hello, world, hollis, chuang]
//new StringList[hello, world, hollis, chuang]
```

了解ArrayList的人都知道，ArrayList底层是通过数组实现的。那么数组`elementData`其实就是用来保存列表中的元素的。通过该属性的声明方式我们知道，他是无法通过序列化持久化下来的。那么为什么code 4的结果却通过序列化和反序列化把List中的元素保留下来了呢？

### writeObject和readObject方法

在ArrayList中定义了来个方法： `writeObject`和`readObject`。

这里先给出结论:

> 在序列化过程中，如果被序列化的类中定义了writeObject 和 readObject 方法，虚拟机会试图调用对象类里的 writeObject 和 readObject 方法，进行用户自定义的序列化和反序列化。
>
> 如果没有这样的方法，则默认调用是 ObjectOutputStream 的 defaultWriteObject 方法以及 ObjectInputStream 的 defaultReadObject 方法。
>
> 用户自定义的 writeObject 和 readObject 方法可以允许用户控制序列化的过程，比如可以在序列化的过程中动态改变序列化的数值。

来看一下这两个方法的具体实现：

code 5

```java
private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }
```

code 6

```java
private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        // Write out element count, and any hidden stuff
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        s.writeInt(size);

        // Write out all elements in the proper order.
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
```

那么为什么ArrayList要用这种方式来实现序列化呢？

### why transient

ArrayList实际上是动态数组，每次在放满以后自动增长设定的长度值，如果数组自动增长长度设为100，而实际只放了一个元素，那就会序列化99个null元素。为了保证在序列化的时候不会将这么多null同时进行序列化，ArrayList把元素数组设置为transient。

### why writeObject and readObject

前面说过，为了防止一个包含大量空对象的数组被序列化，为了优化存储，所以，ArrayList使用`transient`来声明`elementData`。 但是，作为一个集合，在序列化过程中还必须保证其中的元素可以被持久化下来，所以，通过重写`writeObject` 和 `readObject`方法的方式把其中的元素保留下来。

`writeObject`方法把`elementData`数组中的元素遍历的保存到输出流（ObjectOutputStream）中。

`readObject`方法从输入流（ObjectInputStream）中读出对象并保存赋值到`elementData`数组中。

至此，我们先试着来回答刚刚提出的问题：

> 如何自定义的序列化和反序列化策略

答：可以通过在被序列化的类中增加writeObject 和 readObject方法。那么问题又来了：

> 虽然ArrayList中写了writeObject 和 readObject 方法，但是这两个方法并没有显示的被调用啊。
>
> **那么如果一个类中包含writeObject 和 readObject 方法，那么这两个方法是怎么被调用的呢?**

### ObjectOutputStream

从code 4中，我们可以看出，对象的序列化过程通过ObjectOutputStream和ObjectInputputStream来实现的，那么带着刚刚的问题，我们来分析一下ArrayList中的writeObject 和 readObject 方法到底是如何被调用的呢？

为了节省篇幅，这里给出ObjectOutputStream的writeObject的调用栈：

```
writeObject ---> writeObject0 --->writeOrdinaryObject--->writeSerialData--->invokeWriteObject
```

这里看一下invokeWriteObject：

```java
void invokeWriteObject(Object obj, ObjectOutputStream out)
        throws IOException, UnsupportedOperationException
    {
        if (writeObjectMethod != null) {
            try {
                writeObjectMethod.invoke(obj, new Object[]{ out });
            } catch (InvocationTargetException ex) {
                Throwable th = ex.getTargetException();
                if (th instanceof IOException) {
                    throw (IOException) th;
                } else {
                    throwMiscException(th);
                }
            } catch (IllegalAccessException ex) {
                // should not occur, as access checks have been suppressed
                throw new InternalError(ex);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
```

其中`writeObjectMethod.invoke(obj, new Object[]{ out });`是关键，通过反射的方式调用writeObjectMethod方法。官方是这么解释这个writeObjectMethod的：

> class-defined writeObject method, or null if none

在我们的例子中，这个方法就是我们在ArrayList中定义的writeObject方法。通过反射的方式被调用了。

至此，我们先试着来回答刚刚提出的问题：

> **如果一个类中包含writeObject 和 readObject 方法，那么这两个方法是怎么被调用的?**

答：在使用ObjectOutputStream的writeObject方法和ObjectInputStream的readObject方法时，会通过反射的方式调用。

------

至此，我们已经介绍完了ArrayList的序列化方式。那么，不知道有没有人提出这样的疑问：



> **Serializable明明就是一个空的接口，它是怎么保证只有实现了该接口的方法才能进行序列化与反序列化的呢？**

Serializable接口的定义：

```java
public interface Serializable {
}
```

读者可以尝试把code 1中的继承Serializable的代码去掉，再执行code 2，会抛出`java.io.NotSerializableException`。

其实这个问题也很好回答，我们再回到刚刚ObjectOutputStream的writeObject的调用栈：

```
writeObject ---> writeObject0 --->writeOrdinaryObject--->writeSerialData--->invokeWriteObject
```

writeObject0方法中有这么一段代码：

```java
if (obj instanceof String) {
                writeString((String) obj, unshared);
            } else if (cl.isArray()) {
                writeArray(obj, desc, unshared);
            } else if (obj instanceof Enum) {
                writeEnum((Enum<?>) obj, desc, unshared);
            } else if (obj instanceof Serializable) {
                writeOrdinaryObject(obj, desc, unshared);
            } else {
                if (extendedDebugInfo) {
                    throw new NotSerializableException(
                        cl.getName() + "\n" + debugInfoStack.toString());
                } else {
                    throw new NotSerializableException(cl.getName());
                }
            }
```

在进行序列化操作时，会判断要被序列化的类是否是Enum、Array和Serializable类型，如果不是则直接抛出`NotSerializableException`。

## Transient 关键字

Transient 关键字的作用是控制变量的序列化，在变量声明前加上该关键字，可以阻止该变量被序列化到文件中，在被反序列化后，transient 变量的值被设为初始值，如 int 型的是 0，对象型的是 null。关于Transient 关键字的拓展知识欢迎阅读[深入分析Java的序列化与反序列化](https://47.99.194.156/archives/1140)

## 序列化ID

虚拟机是否允许反序列化，不仅取决于类路径和功能代码是否一致，一个非常重要的一点是两个类的序列化 ID 是否一致（就是 `private static final long serialVersionUID`)

序列化 ID 在 Eclipse 下提供了两种生成策略，一个是固定的 1L，一个是随机生成一个不重复的 long 类型数据（实际上是使用 JDK 工具生成），在这里有一个建议，如果没有特殊需求，就是用默认的 1L 就可以，这样可以确保代码一致时反序列化成功。那么随机生成的序列化 ID 有什么作用呢，有些时候，通过改变序列化 ID 可以用来限制某些用户的使用。

## 总结

1、如果一个类想被序列化，需要实现Serializable接口。否则将抛出`NotSerializableException`异常，这是因为，在序列化操作过程中会对类型进行检查，要求被序列化的类必须属于Enum、Array和Serializable类型其中的任何一种。

2、在变量声明前加上该关键字，可以阻止该变量被序列化到文件中。

3、在类中增加writeObject 和 readObject 方法可以实现自定义序列化策略

# 2. 单例与序列化

## 前言

本文将通过实例+阅读Java源码的方式介绍序列化是如何破坏单例模式的，以及如何避免序列化对单例的破坏。

> 单例模式，是设计模式中最简单的一种。通过单例模式可以保证系统中一个类只有一个实例而且该实例易于外界访问，从而方便对实例个数的控制并节约系统资源。如果希望在系统中某个类的对象只能存在一个，单例模式是最好的解决方案。关于单例模式的使用方式，可以阅读[单例模式的七种写法](http://www.hollischuang.com/archives/205)

但是，单例模式真的能够实现实例的唯一性吗？

答案是否定的，很多人都知道使用反射可以破坏单例模式，除了反射以外，使用序列化与反序列化也同样会破坏单例。

## 序列化对单例的破坏

首先来写一个单例的类：

code 1

```java
package com.hollis;
import java.io.Serializable;
/**
 * Created by hollis on 16/2/5.
 * 使用双重校验锁方式实现单例
 */
public class Singleton implements Serializable{
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```

接下来是一个测试类：

code 2

```java
package com.hollis;
import java.io.*;
/**
 * Created by hollis on 16/2/5.
 */
public class SerializableDemo1 {
    //为了便于理解，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //Exception直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(Singleton.getSingleton());
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        Singleton newInstance = (Singleton) ois.readObject();
        //判断是否是同一个对象
        System.out.println(newInstance == Singleton.getSingleton());
    }
}
//false
```

输出结构为false，说明：

> 通过对Singleton的序列化与反序列化得到的对象是一个新的对象，这就破坏了Singleton的单例性。

这里，在介绍如何解决这个问题之前，我们先来深入分析一下，为什么会这样？在反序列化的过程中到底发生了什么。

## ObjectInputStream

对象的序列化过程通过ObjectOutputStream和ObjectInputputStream来实现的，那么带着刚刚的问题，分析一下ObjectInputputStream的`readObject`方法执行情况到底是怎样的。

为了节省篇幅，这里给出ObjectInputStream的`readObject`的调用栈：

这里看一下重点代码，`readOrdinaryObject`方法的代码片段： code 3

```java
private Object readOrdinaryObject(boolean unshared)
        throws IOException
    {
        //此处省略部分代码

        Object obj;
        try {
            obj = desc.isInstantiable() ? desc.newInstance() : null;
        } catch (Exception ex) {
            throw (IOException) new InvalidClassException(
                desc.forClass().getName(),
                "unable to create instance").initCause(ex);
        }

        //此处省略部分代码

        if (obj != null &&
            handles.lookupException(passHandle) == null &&
            desc.hasReadResolveMethod())
        {
            Object rep = desc.invokeReadResolve(obj);
            if (unshared && rep.getClass().isArray()) {
                rep = cloneArray(rep);
            }
            if (rep != obj) {
                handles.setObject(passHandle, obj = rep);
            }
        }

        return obj;
    }
```

code 3 中主要贴出两部分代码。先分析第一部分：

code 3.1

```java
Object obj;
try {
    obj = desc.isInstantiable() ? desc.newInstance() : null;
} catch (Exception ex) {
    throw (IOException) new InvalidClassException(desc.forClass().getName(),"unable to create instance").initCause(ex);
}
```

这里创建的这个obj对象，就是本方法要返回的对象，也可以暂时理解为是ObjectInputStream的`readObject`返回的对象。

> `isInstantiable`：如果一个serializable/externalizable的类可以在运行时被实例化，那么该方法就返回true。针对serializable和externalizable我会在其他文章中介绍。
>
> `desc.newInstance`：该方法通过反射的方式调用无参构造方法新建一个对象。

所以。到目前为止，也就可以解释，为什么序列化可以破坏单例了？

> 答：序列化会通过反射调用无参数的构造方法创建一个新的对象。

那么，接下来我们再看刚开始留下的问题，如何防止序列化/反序列化破坏单例模式。

## 防止序列化破坏单例模式

先给出解决方案，然后再具体分析原理：

只要在Singleton类中定义`readResolve`就可以解决该问题：

code 4

```java
package com.hollis;
import java.io.Serializable;
/**
 * Created by hollis on 16/2/5.
 * 使用双重校验锁方式实现单例
 */
public class Singleton implements Serializable{
    private volatile static Singleton singleton;
    private Singleton (){}
    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    private Object readResolve() {
        return singleton;
    }
}
```

还是运行以下测试类：

```java
package com.hollis;
import java.io.*;
/**
 * Created by hollis on 16/2/5.
 */
public class SerializableDemo1 {
    //为了便于理解，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //Exception直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(Singleton.getSingleton());
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        Singleton newInstance = (Singleton) ois.readObject();
        //判断是否是同一个对象
        System.out.println(newInstance == Singleton.getSingleton());
    }
}
//true
```

本次输出结果为true。具体原理，我们回过头继续分析code 3中的第二段代码:

code 3.2

```java
if (obj != null &&
            handles.lookupException(passHandle) == null &&
            desc.hasReadResolveMethod())
        {
            Object rep = desc.invokeReadResolve(obj);
            if (unshared && rep.getClass().isArray()) {
                rep = cloneArray(rep);
            }
            if (rep != obj) {
                handles.setObject(passHandle, obj = rep);
            }
        }
```

`hasReadResolveMethod`:如果实现了serializable或者externalizable接口的类中包含`readResolve`则返回true

`invokeReadResolve`:通过反射的方式调用要被反序列化的类的readResolve方法。

所以，原理也就清楚了，主要在Singleton中定义readResolve方法，并在该方法中指定要返回的对象的生成策略，就可以防止单例被破坏。

## 总结

在涉及到序列化的场景时，要格外注意他对单例的破坏。



# 3. Google Protocol Buffer序列化

## 简介

什么是 Google Protocol Buffer？ 假如您在网上搜索，应该会得到类似这样的文字介绍：

Google Protocol Buffer( 简称 Protobuf) 是 Google 公司内部的混合语言数据标准，目前已经正在使用的有超过 48,162 种报文格式定义和超过 12,183 个 .proto 文件。他们用于 RPC 系统和持续数据存储系统。

Protocol Buffers 是一种轻便高效的结构化数据存储格式，可以用于结构化数据串行化，或者说序列化。它很适合做数据存储或 RPC 数据交换格式。可用于通讯协议、数据存储等领域的语言无关、平台无关、可扩展的序列化结构数据格式。目前提供了 C++、Java、Python 三种语言的 API。

或许您和我一样，在第一次看完这些介绍后还是不明白 Protobuf 究竟是什么，那么我想一个简单的例子应该比较有助于理解它。

## 一个简单的例子

### 安装 Google Protocol Buffer

在网站 <http://code.google.com/p/protobuf/downloads/list>上可以下载 Protobuf 的源代码。然后解压编译安装便可以使用它了。

安装步骤如下所示：

```shell
tar -xzf protobuf-2.1.0.tar.gz 
cd protobuf-2.1.0 
./configure --prefix=$INSTALL_DIR 
make 
make check 
make install
```

### 关于简单例子的描述

我打算使用 Protobuf 和 C++ 开发一个十分简单的例子程序。

该程序由两部分组成。第一部分被称为 Writer，第二部分叫做 Reader。

Writer 负责将一些结构化的数据写入一个磁盘文件，Reader 则负责从该磁盘文件中读取结构化数据并打印到屏幕上。

准备用于演示的结构化数据是 HelloWorld，它包含两个基本数据：

- ID，为一个整数类型的数据
- Str，这是一个字符串

**书写 .proto 文件**

首先我们需要编写一个 proto 文件，定义我们程序中需要处理的结构化数据，在 protobuf 的术语中，结构化数据被称为 Message。proto 文件非常类似 java 或者 C 语言的数据定义。代码清单 1 显示了例子应用中的 proto 文件内容。

清单 1. proto 文件

```protobuf
package lm; 
message helloworld 
{ 
   required int32     id = 1;  // ID 
   required string    str = 2;  // str 
   optional int32     opt = 3;  //optional field 
}
```

一个比较好的习惯是认真对待 proto 文件的文件名。比如将命名规则定于如下：

```
packageName.MessageName.proto
```

在上例中，package 名字叫做 lm，定义了一个消息 helloworld，该消息有三个成员，类型为 int32 的 id，另一个为类型为 string 的成员 str。opt 是一个可选的成员，即消息中可以不包含该成员。

**编译 .proto 文件**

写好 proto 文件之后就可以用 Protobuf 编译器将该文件编译成目标语言了。本例中我们将使用 C++。

假设您的 proto 文件存放在 $SRC_DIR 下面，您也想把生成的文件放在同一个目录下，则可以使用如下命令：

```
protoc -I=$SRC_DIR --cpp_out=$DST_DIR $SRC_DIR/addressbook.proto
```

命令将生成两个文件：

lm.helloworld.pb.h ， 定义了 C++ 类的头文件

lm.helloworld.pb.cc ， C++ 类的实现文件

在生成的头文件中，定义了一个 C++ 类 helloworld，后面的 Writer 和 Reader 将使用这个类来对消息进行操作。诸如对消息的成员进行赋值，将消息序列化等等都有相应的方法。

### 编写 writer 和 Reader

如前所述，Writer 将把一个结构化数据写入磁盘，以便其他人来读取。假如我们不使用 Protobuf，其实也有许多的选择。一个可能的方法是将数据转换为字符串，然后将字符串写入磁盘。转换为字符串的方法可以使用 sprintf()，这非常简单。数字 123 可以变成字符串”123”。

这样做似乎没有什么不妥，但是仔细考虑一下就会发现，这样的做法对写 Reader 的那个人的要求比较高，Reader 的作者必须了 Writer 的细节。比如”123”可以是单个数字 123，但也可以是三个数字 1,2 和 3，等等。这么说来，我们还必须让 Writer 定义一种分隔符一样的字符，以便 Reader 可以正确读取。但分隔符也许还会引起其他的什么问题。最后我们发现一个简单的 Helloworld 也需要写许多处理消息格式的代码。

如果使用 Protobuf，那么这些细节就可以不需要应用程序来考虑了。

使用 Protobuf，Writer 的工作很简单，需要处理的结构化数据由 .proto 文件描述，经过上一节中的编译过程后，该数据化结构对应了一个 C++ 的类，并定义在 lm.helloworld.pb.h 中。对于本例，类名为 lm::helloworld。

Writer 需要 include 该头文件，然后便可以使用这个类了。

现在，在 Writer 代码中，将要存入磁盘的结构化数据由一个 lm::helloworld 类的对象表示，它提供了一系列的 get/set 函数用来修改和读取结构化数据中的数据成员，或者叫 field。

当我们需要将该结构化数据保存到磁盘上时，类 lm::helloworld 已经提供相应的方法来把一个复杂的数据变成一个字节序列，我们可以将这个字节序列写入磁盘。

对于想要读取这个数据的程序来说，也只需要使用类 lm::helloworld 的相应反序列化方法来将这个字节序列重新转换会结构化数据。这同我们开始时那个“123”的想法类似，不过 Protobuf 想的远远比我们那个粗糙的字符串转换要全面，因此，我们不如放心将这类事情交给 Protobuf 吧。

程序清单 2 演示了 Writer 的主要代码，您一定会觉得很简单吧？

清单 2. Writer 的主要代码

```c++
#include "lm.helloworld.pb.h"
…
 
 int main(void) 
 { 
   
  lm::helloworld msg1; 
  msg1.set_id(101); 
  msg1.set_str(“hello”); 
     
  // Write the new address book back to disk. 
  fstream output("./log", ios::out | ios::trunc | ios::binary); 
         
  if (!msg1.SerializeToOstream(&output)) { 
      cerr << "Failed to write msg." << endl; 
      return -1; 
  }         
  return 0; 
 }
```

Msg1 是一个 helloworld 类的对象，set_id() 用来设置 id 的值。SerializeToOstream 将对象序列化后写入一个 fstream 流。

代码清单 3 列出了 reader 的主要代码。

清单 3. Reader

```c++
#include "lm.helloworld.pb.h" 
…
 void ListMsg(const lm::helloworld & msg) { 
  cout << msg.id() << endl; 
  cout << msg.str() << endl; 
 } 
  
 int main(int argc, char* argv[]) { 
 
  lm::helloworld msg1; 
  
  { 
    fstream input("./log", ios::in | ios::binary); 
    if (!msg1.ParseFromIstream(&input)) { 
      cerr << "Failed to parse address book." << endl; 
      return -1; 
    } 
  } 
  
  ListMsg(msg1); 
  … 
 }
```

同样，Reader 声明类 helloworld 的对象 msg1，然后利用 ParseFromIstream 从一个 fstream 流中读取信息并反序列化。此后，ListMsg 中采用 get 方法读取消息的内部信息，并进行打印输出操作。

运行结果

运行 Writer 和 Reader 的结果如下：

```
>writer 
>reader 
101 
Hello
```

Reader 读取文件 log 中的序列化信息并打印到屏幕上。本文中所有的例子代码都可以在附件中下载。您可以亲身体验一下。

这个例子本身并无意义，但只要您稍加修改就可以将它变成更加有用的程序。比如将磁盘替换为网络 socket，那么就可以实现基于网络的数据交换任务。而存储和交换正是 Protobuf 最有效的应用领域。

### 和其他类似技术的比较

看完这个简单的例子之后，希望您已经能理解 Protobuf 能做什么了，那么您可能会说，世上还有很多其他的类似技术啊，比如 XML，JSON，Thrift 等等。和他们相比，Protobuf 有什么不同呢？

简单说来 Protobuf 的主要优点就是：简单，快。

这有测试为证，项目 thrift-protobuf-compare 比较了这些类似的技术，图 1 显示了该项目的一项测试结果，Total Time.

![图 1. 性能测试结果](img/image001.jpg)

​                                             图 1. 性能测试结果

Total Time 指一个对象操作的整个时间，包括创建对象，将对象序列化为内存中的字节序列，然后再反序列化的整个过程。从测试结果可以看到 Protobuf 的成绩很好，感兴趣的读者可以自行到网站 <http://code.google.com/p/thrift-protobuf-compare/wiki/Benchmarking>上了解更详细的测试结果。

#### Protobuf 的优点

Protobuf 有如 XML，不过它更小、更快、也更简单。你可以定义自己的数据结构，然后使用代码生成器生成的代码来读写这个数据结构。你甚至可以在无需重新部署程序的情况下更新数据结构。只需使用 Protobuf 对数据结构进行一次描述，即可利用各种不同语言或从各种不同数据流中对你的结构化数据轻松读写。

它有一个非常棒的特性，即“向后”兼容性好，人们不必破坏已部署的、依靠“老”数据格式的程序就可以对数据结构进行升级。这样您的程序就可以不必担心因为消息结构的改变而造成的大规模的代码重构或者迁移的问题。因为添加新的消息中的 field 并不会引起已经发布的程序的任何改变。

Protobuf 语义更清晰，无需类似 XML 解析器的东西（因为 Protobuf 编译器会将 .proto 文件编译生成对应的数据访问类以对 Protobuf 数据进行序列化、反序列化操作）。

使用 Protobuf 无需学习复杂的文档对象模型，Protobuf 的编程模式比较友好，简单易学，同时它拥有良好的文档和示例，对于喜欢简单事物的人们而言，Protobuf 比其他的技术更加有吸引力。

#### Protobuf 的不足

Protbuf 与 XML 相比也有不足之处。它功能简单，无法用来表示复杂的概念。

XML 已经成为多种行业标准的编写工具，Protobuf 只是 Google 公司内部使用的工具，在通用性上还差很多。

由于文本并不适合用来描述数据结构，所以 Protobuf 也不适合用来对基于文本的标记文档（如 HTML）建模。另外，由于 XML 具有某种程度上的自解释性，它可以被人直接读取编辑，在这一点上 Protobuf 不行，它以二进制的方式存储，除非你有 .proto 定义，否则你没法直接读出 Protobuf 的任何内容【 2 】。

## 高级应用话题

### 更复杂的 Message

到这里为止，我们只给出了一个简单的没有任何用处的例子。在实际应用中，人们往往需要定义更加复杂的 Message。我们用“复杂”这个词，不仅仅是指从个数上说有更多的 fields 或者更多类型的 fields，而是指更加复杂的数据结构：

**嵌套 Message**

嵌套是一个神奇的概念，一旦拥有嵌套能力，消息的表达能力就会非常强大。

代码清单 4 给出一个嵌套 Message 的例子。

清单 4. 嵌套 Message 的例子

```protobuf
message Person { 
 required string name = 1; 
 required int32 id = 2;        // Unique ID number for this person. 
 optional string email = 3; 
 
 enum PhoneType { 
   MOBILE = 0; 
   HOME = 1; 
   WORK = 2; 
 } 
 
 message PhoneNumber { 
   required string number = 1; 
   optional PhoneType type = 2 [default = HOME]; 
 } 
 repeated PhoneNumber phone = 4; 
}
```

在 Message Person 中，定义了嵌套消息 PhoneNumber，并用来定义 Person 消息中的 phone 域。这使得人们可以定义更加复杂的数据结构。

**Import Message**

在一个 .proto 文件中，还可以用 Import 关键字引入在其他 .proto 文件中定义的消息，这可以称做 Import Message，或者 Dependency Message。

比如下例：

清单 5. 代码

```
import common.header; 
 
message youMsg{ 
 required common.info_header header = 1; 
 required string youPrivateData = 2; 
}
```

Import Message 的用处主要在于提供了方便的代码管理机制，类似 C 语言中的头文件。您可以将一些公用的 Message 定义在一个 package 中，然后在别的 .proto 文件中引入该 package，进而使用其中的消息定义。

Google Protocol Buffer 可以很好地支持嵌套 Message 和引入 Message，从而让定义复杂的数据结构的工作变得非常轻松愉快。

### 动态编译

一般情况下，使用 Protobuf 的人们都会先写好 .proto 文件，再用 Protobuf 编译器生成目标语言所需要的源代码文件。将这些生成的代码和应用程序一起编译。

可是在某且情况下，人们无法预先知道 .proto 文件，他们需要动态处理一些未知的 .proto 文件。比如一个通用的消息转发中间件，它不可能预知需要处理怎样的消息。这需要动态编译 .proto 文件，并使用其中的 Message。



Protobuf 提供了 google::protobuf::compiler 包来完成动态编译的功能。主要的类叫做 importer，定义在 importer.h 中。使用 Importer 非常简单，下图展示了与 Import 和其它几个重要的类的关系。

图 2. Importer 类

 ![图 2. Importer 类](img/image002.gif)

Import 类对象中包含三个主要的对象，分别为处理错误的 MultiFileErrorCollector 类，定义 .proto 文件源目录的 SourceTree 类。

下面还是通过实例说明这些类的关系和使用吧。

对于给定的 proto 文件，比如 lm.helloworld.proto，在程序中动态编译它只需要很少的一些代码。如代码清单 6 所示。

清单 6. 代码

```
google::protobuf::compiler::MultiFileErrorCollector errorCollector；
google::protobuf::compiler::DiskSourceTree sourceTree; 
 
google::protobuf::compiler::Importer importer(&sourceTree, &errorCollector); 
sourceTree.MapPath("", protosrc); 
 
importer.import(“lm.helloworld.proto”);
```

首先构造一个 importer 对象。构造函数需要两个入口参数，一个是 source Tree 对象，该对象指定了存放 .proto 文件的源目录。第二个参数是一个 error collector 对象，该对象有一个 AddError 方法，用来处理解析 .proto 文件时遇到的语法错误。

之后，需要动态编译一个 .proto 文件时，只需调用 importer 对象的 import 方法。非常简单。

那么我们如何使用动态编译后的 Message 呢？我们需要首先了解几个其他的类

Package google::protobuf::compiler 中提供了以下几个类，用来表示一个 .proto 文件中定义的 message，以及 Message 中的 field，如图所示。

图 3. 各个 Compiler 类之间的关系

![图 3. 各个 Compiler 类之间的关系](img/image003.jpg)

类 FileDescriptor 表示一个编译后的 .proto 文件；类 Descriptor 对应该文件中的一个 Message；类 FieldDescriptor 描述一个 Message 中的一个具体 Field。

比如编译完 lm.helloworld.proto 之后，可以通过如下代码得到 lm.helloworld.id 的定义：

清单 7. 得到 lm.helloworld.id 的定义的代码

```
const protobuf::Descriptor *desc = 
   importer_.pool()->FindMessageTypeByName(“lm.helloworld”); 
const protobuf::FieldDescriptor* field = 
   desc->pool()->FindFileByName (“id”);
```

通过 Descriptor，FieldDescriptor 的各种方法和属性，应用程序可以获得各种关于 Message 定义的信息。比如通过 field->name() 得到 field 的名字。这样，您就可以使用一个动态定义的消息了。

### 编写新的 proto 编译器

随 Google Protocol Buffer 源代码一起发布的编译器 protoc 支持 3 种编程语言：C++，java 和 Python。但使用 Google Protocol Buffer 的 Compiler 包，您可以开发出支持其他语言的新的编译器。

类 CommandLineInterface 封装了 protoc 编译器的前端，包括命令行参数的解析，proto 文件的编译等功能。您所需要做的是实现类 CodeGenerator 的派生类，实现诸如代码生成等后端工作：

程序的大体框架如图所示：

图 4. XML 编译器框图

![图 4. XML 编译器框图](img/image004.jpg)

在 main() 函数内，生成 CommandLineInterface 的对象 cli，调用其 RegisterGenerator() 方法将新语言的后端代码生成器 yourG 对象注册给 cli 对象。然后调用 cli 的 Run() 方法即可。

这样生成的编译器和 protoc 的使用方法相同，接受同样的命令行参数，cli 将对用户输入的 .proto 进行词法语法等分析工作，最终生成一个语法树。该树的结构如图所示。

图 5. 语法树

![图 5. 语法树](img/image005.jpg)

其根节点为一个 FileDescriptor 对象（请参考“动态编译”一节），并作为输入参数被传入 yourG 的 Generator() 方法。在这个方法内，您可以遍历语法树，然后生成对应的您所需要的代码。简单说来，要想实现一个新的 compiler，您只需要写一个 main 函数，和一个实现了方法 Generator() 的派生类即可。

在本文的下载附件中，有一个参考例子，将 .proto 文件编译生成 XML 的 compiler，可以作为参考。

## Protobuf 的更多细节

人们一直在强调，同 XML 相比， Protobuf 的主要优点在于性能高。它以高效的二进制方式存储，比 XML 小 3 到 10 倍，快 20 到 100 倍。

对于这些 “小 3 到 10 倍”,“快 20 到 100 倍”的说法，严肃的程序员需要一个解释。因此在本文的最后，让我们稍微深入 Protobuf 的内部实现吧。

有两项技术保证了采用 Protobuf 的程序能获得相对于 XML 极大的性能提高。

第一点，我们可以考察 Protobuf 序列化后的信息内容。您可以看到 Protocol Buffer 信息的表示非常紧凑，这意味着消息的体积减少，自然需要更少的资源。比如网络上传输的字节数更少，需要的 IO 更少等，从而提高性能。

第二点我们需要理解 Protobuf 封解包的大致过程，从而理解为什么会比 XML 快很多。

### Google Protocol Buffer 的 Encoding

Protobuf 序列化后所生成的二进制消息非常紧凑，这得益于 Protobuf 采用的非常巧妙的 Encoding 方法。

考察消息结构之前，让我首先要介绍一个叫做 Varint 的术语。

Varint 是一种紧凑的表示数字的方法。它用一个或多个字节来表示一个数字，值越小的数字使用越少的字节数。这能减少用来表示数字的字节数。

比如对于 int32 类型的数字，一般需要 4 个 byte 来表示。但是采用 Varint，对于很小的 int32 类型的数字，则可以用 1 个 byte 来表示。当然凡事都有好的也有不好的一面，采用 Varint 表示法，大的数字则需要 5 个 byte 来表示。从统计的角度来说，一般不会所有的消息中的数字都是大数，因此大多数情况下，采用 Varint 后，可以用更少的字节数来表示数字信息。下面就详细介绍一下 Varint。

Varint 中的每个 byte 的最高位 bit 有特殊的含义，如果该位为 1，表示后续的 byte 也是该数字的一部分，如果该位为 0，则结束。其他的 7 个 bit 都用来表示数字。因此小于 128 的数字都可以用一个 byte 表示。大于 128 的数字，比如 300，会用两个字节来表示：1010 1100 0000 0010

下图演示了 Google Protocol Buffer 如何解析两个 bytes。注意到最终计算前将两个 byte 的位置相互交换过一次，这是因为 Google Protocol Buffer 字节序采用 little-endian 的方式。

图 6. Varint 编码

![图 6. Varint 编码](img/image006.jpg)

消息经过序列化后会成为一个二进制数据流，该流中的数据为一系列的 Key-Value 对。如下图所示：

图 7. Message Buffer

![图 7. Message Buffer](img/image007-20200303170128125.jpg)

采用这种 Key-Pair 结构无需使用分隔符来分割不同的 Field。对于可选的 Field，如果消息中不存在该 field，那么在最终的 Message Buffer 中就没有该 field，这些特性都有助于节约消息本身的大小。

以代码清单 1 中的消息为例。假设我们生成如下的一个消息 Test1:

```
Test1.id = 10; 
Test1.str = “hello”；
```

则最终的 Message Buffer 中有两个 Key-Value 对，一个对应消息中的 id；另一个对应 str。

Key 用来标识具体的 field，在解包的时候，Protocol Buffer 根据 Key 就可以知道相应的 Value 应该对应于消息中的哪一个 field。

Key 的定义如下：

```
(field_number << 3) | wire_type
```

可以看到 Key 由两部分组成。第一部分是 field_number，比如消息 lm.helloworld 中 field id 的 field_number 为 1。第二部分为 wire_type。表示 Value 的传输类型。

Wire Type 可能的类型如下表所示：

表 1. Wire Type

| **Type** | **Meaning**   | **Used For**                                             |
| -------- | ------------- | -------------------------------------------------------- |
| 0        | Varint        | int32, int64, uint32, uint64, sint32, sint64, bool, enum |
| 1        | 64-bit        | fixed64, sfixed64, double                                |
| 2        | Length-delimi | string, bytes, embedded messages, packed repeated fields |
| 3        | Start group   | Groups (deprecated)                                      |
| 4        | End group     | Groups (deprecated)                                      |
| 5        | 32-bit        | fixed32, sfixed32, float                                 |

在我们的例子当中，field id 所采用的数据类型为 int32，因此对应的 wire type 为 0。细心的读者或许会看到在 Type 0 所能表示的数据类型中有 int32 和 sint32 这两个非常类似的数据类型。Google Protocol Buffer 区别它们的主要意图也是为了减少 encoding 后的字节数。

在计算机内，一个负数一般会被表示为一个很大的整数，因为计算机定义负数的符号位为数字的最高位。如果采用 Varint 表示一个负数，那么一定需要 5 个 byte。为此 Google Protocol Buffer 定义了 sint32 这种类型，采用 zigzag 编码。

Zigzag 编码用无符号数来表示有符号数字，正数和负数交错，这就是 zigzag 这个词的含义了。

如图所示：

图 8. ZigZag 编码

![图 8. ZigZag 编码](img/image008.jpg)

使用 zigzag 编码，绝对值小的数字，无论正负都可以采用较少的 byte 来表示，充分利用了 Varint 这种技术。

其他的数据类型，比如字符串等则采用类似数据库中的 varchar 的表示方法，即用一个 varint 表示长度，然后将其余部分紧跟在这个长度部分之后即可。

通过以上对 protobuf Encoding 方法的介绍，想必您也已经发现 protobuf 消息的内容小，适于网络传输。假如您对那些有关技术细节的描述缺乏耐心和兴趣，那么下面这个简单而直观的比较应该能给您更加深刻的印象。

对于代码清单 1 中的消息，用 Protobuf 序列化后的字节序列为：

```
`08 65 12 06 48 65 6C 6C 6F 77`
```

而如果用 XML，则类似这样：

```
31 30 31 3C 2F 69 64 3E 3C 6E 61 6D 65 3E 68 65 
 6C 6C 6F 3C 2F 6E 61 6D 65 3E 3C 2F 68 65 6C 6C 
 6F 77 6F 72 6C 64 3E 
 
一共 55 个字节，这些奇怪的数字需要稍微解释一下，其含义用 ASCII 表示如下：
 <helloworld> 
    <id>101</id> 
    <name>hello</name> 
 </helloworld>
```

## 封解包的速度

首先我们来了解一下 XML 的封解包过程。XML 需要从文件中读取出字符串，再转换为 XML 文档对象结构模型。之后，再从 XML 文档对象结构模型中读取指定节点的字符串，最后再将这个字符串转换成指定类型的变量。这个过程非常复杂，其中将 XML 文件转换为文档对象结构模型的过程通常需要完成词法文法分析等大量消耗 CPU 的复杂计算。

反观 Protobuf，它只需要简单地将一个二进制序列，按照指定的格式读取到 C++ 对应的结构类型中就可以了。从上一节的描述可以看到消息的 decoding 过程也可以通过几个位移操作组成的表达式计算即可完成。速度非常快。

为了说明这并不是我拍脑袋随意想出来的说法，下面让我们简单分析一下 Protobuf 解包的代码流程吧。

以代码清单 3 中的 Reader 为例，该程序首先调用 msg1 的 ParseFromIstream 方法，这个方法解析从文件读入的二进制数据流，并将解析出来的数据赋予 helloworld 类的相应数据成员。

该过程可以用下图表示：

图 9. 解包流程图

![图 9. 解包流程图](img/image009-20200303170220187.jpg)

整个解析过程需要 Protobuf 本身的框架代码和由 Protobuf 编译器生成的代码共同完成。Protobuf 提供了基类 Message 以及 Message_lite 作为通用的 Framework，，CodedInputStream 类，WireFormatLite 类等提供了对二进制数据的 decode 功能，从 5.1 节的分析来看，Protobuf 的解码可以通过几个简单的数学运算完成，无需复杂的词法语法分析，因此 ReadTag() 等方法都非常快。 在这个调用路径上的其他类和方法都非常简单，感兴趣的读者可以自行阅读。 相对于 XML 的解析过程，以上的流程图实在是非常简单吧？这也就是 Protobuf 效率高的第二个原因了。

# 4. java序列化的5个问题

[参考](https://www.ibm.com/developerworks/cn/java/j-5things1/)

## 前言

序列化的数据是安全的？不见得吧。

数年前，当和一个软件团队一起用 Java 语言编写一个应用程序时，我体会到比一般程序员多知道一点关于 Java 对象序列化的知识所带来的好处。

大约一年前，一个负责管理应用程序所有用户设置的开发人员，决定将用户设置存储在一个 `Hashtable` 中，然后将这个 `Hashtable` 序列化到磁盘，以便持久化。当用户更改设置时，便重新将 `Hashtable` 写到磁盘。

这是一个优雅的、开放式的设置系统，但是，当团队决定从 `Hashtable`迁移到 Java Collections 库中的 `HashMap` 时，这个系统便面临崩溃。

`Hashtable` 和 `HashMap` 在磁盘上的格式是不相同、不兼容的。除非对每个持久化的用户设置运行某种类型的数据转换实用程序（极其庞大的任务），否则以后似乎只能一直用 `Hashtable` 作为应用程序的存储格式。

团队感到陷入僵局，但这只是因为他们不知道关于 Java 序列化的一个重要事实：Java 序列化允许随着时间的推移而改变类型。当我向他们展示如何自动进行序列化替换后，他们终于按计划完成了向 `HashMap`的转变。

本文是本系列的第一篇文章，这个系列专门揭示关于 Java 平台的一些有用的小知识 — 这些小知识不易理解，但对于解决 Java 编程挑战迟早有用。

将 Java 对象序列化 API 作为开端是一个不错的选择，因为它从一开始就存在于 JDK 1.1 中。本文介绍的关于序列化的 5 件事情将说服您重新审视那些标准 Java API。

## Java 序列化简介

Java 对象序列化是 JDK 1.1 中引入的一组开创性特性之一，用于作为一种将 Java 对象的状态转换为字节数组，以便存储或传输的机制，以后，仍可以将字节数组转换回 Java 对象原有的状态。

实际上，序列化的思想是 “冻结” 对象状态，传输对象状态（写到磁盘、通过网络传输等等），然后 “解冻” 状态，重新获得可用的 Java 对象。所有这些事情的发生有点像是魔术，这要归功于`ObjectInputStream`/`ObjectOutputStream` 类、完全保真的元数据以及程序员愿意用 `Serializable` 标识接口标记他们的类，从而 “参与” 这个过程。

清单 1 显示一个实现 `Serializable` 的 `Person` 类。

清单 1. Serializable Person

```java
`package com.tedneward;` `public class Person``    ``implements java.io.Serializable``{``    ``public Person(String fn, String ln, int a)``    ``{``        ``this.firstName = fn; this.lastName = ln; this.age = a;``    ``}` `    ``public String getFirstName() { return firstName; }``    ``public String getLastName() { return lastName; }``    ``public int getAge() { return age; }``    ``public Person getSpouse() { return spouse; }` `    ``public void setFirstName(String value) { firstName = value; }``    ``public void setLastName(String value) { lastName = value; }``    ``public void setAge(int value) { age = value; }``    ``public void setSpouse(Person value) { spouse = value; }` `    ``public String toString()``    ``{``        ``return "[Person: firstName=" + firstName + ``            ``" lastName=" + lastName +``            ``" age=" + age +``            ``" spouse=" + spouse.getFirstName() +``            ``"]";``    ``}    ` `    ``private String firstName;``    ``private String lastName;``    ``private int age;``    ``private Person spouse;` `}`
```

将 `Person` 序列化后，很容易将对象状态写到磁盘，然后重新读出它，下面的 JUnit 4 单元测试对此做了演示。

清单 2. 对 Person 进行反序列化

```java
`public class SerTest``{``    ``@Test public void serializeToDisk()``    ``{``        ``try``        ``{``            ``com.tedneward.Person ted = new com.tedneward.Person("Ted", "Neward", 39);``            ``com.tedneward.Person charl = new com.tedneward.Person("Charlotte",``                ``"Neward", 38);` `            ``ted.setSpouse(charl); charl.setSpouse(ted);` `            ``FileOutputStream fos = new FileOutputStream("tempdata.ser");``            ``ObjectOutputStream oos = new ObjectOutputStream(fos);``            ``oos.writeObject(ted);``            ``oos.close();``        ``}``        ``catch (Exception ex)``        ``{``            ``fail("Exception thrown during test: " + ex.toString());``        ``}``        ` `        ``try``        ``{``            ``FileInputStream fis = new FileInputStream("tempdata.ser");``            ``ObjectInputStream ois = new ObjectInputStream(fis);``            ``com.tedneward.Person ted = (com.tedneward.Person) ois.readObject();``            ``ois.close();``            ` `            ``assertEquals(ted.getFirstName(）， "Ted");``            ``assertEquals(ted.getSpouse().getFirstName(）， "Charlotte");` `            ``// Clean up the file``            ``new File("tempdata.ser").delete();``        ``}``        ``catch (Exception ex)``        ``{``            ``fail("Exception thrown during test: " + ex.toString());``        ``}``    ``}``}`
```

到现在为止，还没有看到什么新鲜的或令人兴奋的事情，但是这是一个很好的出发点。我们将使用 `Person` 来发现您可能*不* 知道的关于 *Java 对象序列化* 的 5 件事。

## 1. 序列化允许重构

序列化允许一定数量的类变种，甚至重构之后也是如此，`ObjectInputStream` 仍可以很好地将其读出来。

*Java Object Serialization* 规范可以自动管理的关键任务是：

- 将新字段添加到类中
- 将字段从 static 改为非 static
- 将字段从 transient 改为非 transient

取决于所需的向后兼容程度，转换字段形式（从非 static 转换为 static 或从非 transient 转换为 transient）或者删除字段需要额外的消息传递。

### 重构序列化类

既然已经知道序列化允许重构，我们来看看当把新字段添加到 `Person` 类中时，会发生什么事情。

如清单 3 所示，`PersonV2` 在原先 `Person` 类的基础上引入一个表示性别的新字段。

清单 3. 将新字段添加到序列化的 Person 中

```java
`enum Gender``{``    ``MALE, FEMALE``}` `public class Person``    ``implements java.io.Serializable``{``    ``public Person(String fn, String ln, int a, Gender g)``    ``{``        ``this.firstName = fn; this.lastName = ln; this.age = a; this.gender = g;``    ``}``  ` `    ``public String getFirstName() { return firstName; }``    ``public String getLastName() { return lastName; }``    ``public Gender getGender() { return gender; }``    ``public int getAge() { return age; }``    ``public Person getSpouse() { return spouse; }` `    ``public void setFirstName(String value) { firstName = value; }``    ``public void setLastName(String value) { lastName = value; }``    ``public void setGender(Gender value) { gender = value; }``    ``public void setAge(int value) { age = value; }``    ``public void setSpouse(Person value) { spouse = value; }` `    ``public String toString()``    ``{``        ``return "[Person: firstName=" + firstName + ``            ``" lastName=" + lastName +``            ``" gender=" + gender +``            ``" age=" + age +``            ``" spouse=" + spouse.getFirstName() +``            ``"]";``    ``}    ` `    ``private String firstName;``    ``private String lastName;``    ``private int age;``    ``private Person spouse;``    ``private Gender gender;``}`
```

序列化使用一个 hash，该 hash 是根据给定源文件中几乎所有东西 — 方法名称、字段名称、字段类型、访问修改方法等 — 计算出来的，序列化将该 hash 值与序列化流中的 hash 值相比较。

为了使 Java 运行时相信两种类型实际上是一样的，第二版和随后版本的 `Person` 必须与第一版有相同的序列化版本 hash（存储为 private static final `serialVersionUID` 字段）。因此，我们需要`serialVersionUID` 字段，它是通过对原始（或 V1）版本的 `Person` 类运行 JDK `serialver` 命令计算出的。

一旦有了 `Person` 的 `serialVersionUID`，不仅可以从原始对象 `Person` 的序列化数据创建 `PersonV2` 对象（当出现新字段时，新字段被设为缺省值，最常见的是“null”），还可以反过来做：即从 `PersonV2` 的数据通过反序列化得到 `Person`，这毫不奇怪。

## 2. 序列化并不安全

让 Java 开发人员诧异并感到不快的是，序列化二进制格式完全编写在文档中，并且完全可逆。实际上，只需将二进制序列化流的内容转储到控制台，就足以看清类是什么样子，以及它包含什么内容。

这对于安全性有着不良影响。例如，当通过 RMI 进行远程方法调用时，通过连接发送的对象中的任何 private 字段几乎都是以明文的方式出现在套接字流中，这显然容易招致哪怕最简单的安全问题。

幸运的是，序列化允许 “hook” 序列化过程，并在序列化之前和反序列化之后保护（或模糊化）字段数据。可以通过在 `Serializable` 对象上提供一个 `writeObject` 方法来做到这一点。

### 模糊化序列化数据

假设 `Person` 类中的敏感数据是 age 字段。毕竟，女士忌谈年龄。 我们可以在序列化之前模糊化该数据，将数位循环左移一位，然后在反序列化之后复位。（您可以开发更安全的算法，当前这个算法只是作为一个例子。）

为了 “hook” 序列化过程，我们将在 `Person` 上实现一个 `writeObject` 方法；为了 “hook” 反序列化过程，我们将在同一个类上实现一个 `readObject` 方法。重要的是这两个方法的细节要正确 — 如果访问修改方法、参数或名称不同于清单 4 中的内容，那么代码将不被察觉地失败，`Person` 的 age 将暴露。

清单 4. 模糊化序列化数据

```java
`public class Person``    ``implements java.io.Serializable``{``    ``public Person(String fn, String ln, int a)``    ``{``        ``this.firstName = fn; this.lastName = ln; this.age = a;``    ``}` `    ``public String getFirstName() { return firstName; }``    ``public String getLastName() { return lastName; }``    ``public int getAge() { return age; }``    ``public Person getSpouse() { return spouse; }``    ` `    ``public void setFirstName(String value) { firstName = value; }``    ``public void setLastName(String value) { lastName = value; }``    ``public void setAge(int value) { age = value; }``    ``public void setSpouse(Person value) { spouse = value; }` `    ``private void writeObject(java.io.ObjectOutputStream stream)``        ``throws java.io.IOException``    ``{``        ``// "Encrypt"/obscure the sensitive data``        ``age = age << 2;``        ``stream.defaultWriteObject();``    ``}` `    ``private void readObject(java.io.ObjectInputStream stream)``        ``throws java.io.IOException, ClassNotFoundException``    ``{``        ``stream.defaultReadObject();` `        ``// "Decrypt"/de-obscure the sensitive data``        ``age = age << 2;``    ``}``    ` `    ``public String toString()``    ``{``        ``return "[Person: firstName=" + firstName + ``            ``" lastName=" + lastName +``            ``" age=" + age +``            ``" spouse=" + (spouse!=null ? spouse.getFirstName() : "[null]") +``            ``"]";``    ``}      ` `    ``private String firstName;``    ``private String lastName;``    ``private int age;``    ``private Person spouse;``}`
```

如果需要查看被模糊化的数据，总是可以查看序列化数据流/文件。而且，由于该格式被完全文档化，即使不能访问类本身，也仍可以读取序列化流中的内容。

## 3. 序列化的数据可以被签名和密封

上一个技巧假设您想模糊化序列化数据，而不是对其加密或者确保它不被修改。当然，通过使用 `writeObject` 和 `readObject` 可以实现密码加密和签名管理，但其实还有更好的方式。

如果需要对整个对象进行加密和签名，最简单的是将它放在一个 `javax.crypto.SealedObject` 和/或`java.security.SignedObject` 包装器中。两者都是可序列化的，所以将对象包装在 `SealedObject` 中可以围绕原对象创建一种 “包装盒”。必须有对称密钥才能解密，而且密钥必须单独管理。同样，也可以将 `SignedObject` 用于数据验证，并且对称密钥也必须单独管理。

结合使用这两种对象，便可以轻松地对序列化数据进行密封和签名，而不必强调关于数字签名验证或加密的细节。很简洁，是吧？

## 4. 序列化允许将代理放在流中

很多情况下，类中包含一个核心数据元素，通过它可以派生或找到类中的其他字段。在此情况下，没有必要序列化整个对象。可以将字段标记为 *transient*，但是每当有方法访问一个字段时，类仍然必须显式地产生代码来检查它是否被初始化。

如果首要问题是序列化，那么最好指定一个 flyweight 或代理放在流中。为原始 `Person` 提供一个`writeReplace` 方法，可以序列化不同类型的对象来代替它。类似地，如果反序列化期间发现一个`readResolve` 方法，那么将调用该方法，将替代对象提供给调用者。

### 打包和解包代理

`writeReplace` 和 `readResolve` 方法使 `Person` 类可以将它的所有数据（或其中的核心数据）打包到一个 `PersonProxy` 中，将它放入到一个流中，然后在反序列化时再进行解包。

清单 5. 你完整了我，我代替了你

```java
`class PersonProxy``    ``implements java.io.Serializable``{``    ``public PersonProxy(Person orig)``    ``{``        ``data = orig.getFirstName() + "," + orig.getLastName() + "," + orig.getAge();``        ``if (orig.getSpouse() != null)``        ``{``            ``Person spouse = orig.getSpouse();``            ``data = data + "," + spouse.getFirstName() + "," + spouse.getLastName() + ","  ``              ``+ spouse.getAge();``        ``}``    ``}` `    ``public String data;``    ``private Object readResolve()``        ``throws java.io.ObjectStreamException``    ``{``        ``String[] pieces = data.split(",");``        ``Person result = new Person(pieces[0], pieces[1], Integer.parseInt(pieces[2]));``        ``if (pieces.length > 3)``        ``{``            ``result.setSpouse(new Person(pieces[3], pieces[4], Integer.parseInt``              ``(pieces[5])));``            ``result.getSpouse().setSpouse(result);``        ``}``        ``return result;``    ``}``}` `public class Person``    ``implements java.io.Serializable``{``    ``public Person(String fn, String ln, int a)``    ``{``        ``this.firstName = fn; this.lastName = ln; this.age = a;``    ``}` `    ``public String getFirstName() { return firstName; }``    ``public String getLastName() { return lastName; }``    ``public int getAge() { return age; }``    ``public Person getSpouse() { return spouse; }` `    ``private Object writeReplace()``        ``throws java.io.ObjectStreamException``    ``{``        ``return new PersonProxy(this);``    ``}``    ` `    ``public void setFirstName(String value) { firstName = value; }``    ``public void setLastName(String value) { lastName = value; }``    ``public void setAge(int value) { age = value; }``    ``public void setSpouse(Person value) { spouse = value; }   ` `    ``public String toString()``    ``{``        ``return "[Person: firstName=" + firstName + ``            ``" lastName=" + lastName +``            ``" age=" + age +``            ``" spouse=" + spouse.getFirstName() +``            ``"]";``    ``}    ``    ` `    ``private String firstName;``    ``private String lastName;``    ``private int age;``    ``private Person spouse;``}`
```

注意，`PersonProxy` 必须跟踪 `Person` 的所有数据。这通常意味着代理需要是 `Person` 的一个内部类，以便能访问 private 字段。有时候，代理还需要追踪其他对象引用并手动序列化它们，例如 `Person` 的 spouse。

这种技巧是少数几种不需要读/写平衡的技巧之一。例如，一个类被重构成另一种类型后的版本可以提供一个 `readResolve` 方法，以便静默地将被序列化的对象转换成新类型。类似地，它可以采用 `writeReplace` 方法将旧类序列化成新版本。

## 5. 信任，但要验证

认为序列化流中的数据总是与最初写到流中的数据一致，这没有问题。但是，正如一位美国前总统所说的，“信任，但要验证”。

对于序列化的对象，这意味着验证字段，以确保在反序列化之后它们仍具有正确的值，“以防万一”。为此，可以实现 `ObjectInputValidation` 接口，并覆盖 `validateObject()` 方法。如果调用该方法时发现某处有错误，则抛出一个 `InvalidObjectException`。

## 结束语

Java 对象序列化比大多数 Java 开发人员想象的更灵活，这使我们有更多的机会解决棘手的情况。

幸运的是，像这样的编程妙招在 JVM 中随处可见。关键是要知道它们，在遇到难题的时候能用上它们。

*5 件事* 系列下期预告：Java Collections。在此之前，好好享受按自己的想法调整序列化吧！

# 5. 码农翻身版



不要把编码，序列化和json/xml混为一谈，首先序列化和json/xml都需要进行编码。



这里的工作很繁忙，一年365天， 一天24小时几乎不停工。



但是我却是一个闲人， 因为我做的工作最近用的人太少了， 经常被冷落在一边。



大多数时候，我只能羡慕的看着线程、反射、注解、集合、泛型这些明星员工在那里忙忙碌碌， 听着他们充满激情的的大声说笑。



他们都叫我序列化，想想也是，  我的工作就是把一个Java 对象变成二进制的字节流，  或者反过来把字节流变成Java 对象， 这有什么意思？  



当大家需要一个Java 对象的时候， 直接new 出来不就得了，  对象不用了自然有令人胆战心惊的垃圾回收去处理。



但是存在即合理， 在JDk1.1的时代， 我就已经存在了。 当时人们的思想很超前： 网络就是计算机。 一个个Java 对象应该可以在网络中到处旅行 ： 从一个机器出发时，就变成二进制字节流，顺着网络跨过千山万水， 到达另外一台机器，在那里摇身一变，恢复成Java 对象， 在那里继续运算。



![image-20190302145511025](https://ws3.sinaimg.cn/large/006tKfTcly1g0ogg4ddmej30ba0v0td4.jpg)



既然可以以二进制方式在网络中漫游， 那自然也可以把这些字节流存到硬盘中， 当JVM停机，整个世界坍塌以后， 线程，反射，注解都不复存在了， 而我的字节流还会在硬盘上默默等待， 等待下一次JVM的重生， 把对象恢复。



所以我觉得我的工作也很有价值， 从某种意义上来讲， **我可以让Java 对象跨越时间和空间而永生 ！**



这种永生是有代价的， 首先你必须得用Java， 这是废话， 因为我只是java对象序列化。  



虽然那二进制字节流的格式是公开的， 你可以用任何语言(C,C++,Python...)去解析读取， 但是解析以后又有什么用处呢？     那些字节流中会告知你这是哪个类的数据，字段的类型和值，   但是如果你没有相对应的Java 类，还是无法构建出Java 对象出来。  

其次， 做序列化双方的类必须得一致， 要不然肯定出乱子。



大部分人都不知道在上个世纪末和本世纪初， 我还是随着J2EE火了一阵， 当时J2EE中有个叫RMI东西， 其实就是Java RPC。  由于我卓越的工作， 开发人员用可以轻松的调用远程服务器上的Java 方法， 就相当于调用本地方法一样， 很方便。    



可惜的是这个RMI只能用在Java环境中，对于服务器来说这根本不是问题， 但是当时Web应用正在兴起， 一个浏览器中是很难有Java环境的，  所以RMI很快就没落了， 我也随之被打入冷宫， 我也只好蛰伏下来，等待机会。



2XML和JSON的挑战



后来我们这里来了一个叫XML的小伙子，很受大家的欢迎， 都喜欢把Java 对象序列化的工作交给他去做。



我不能坐以待毙， 我仔细的观察了几天以后， 终于发现这个家伙有个大缺点： 太复杂了！



对于我的Java 序列化，大部分情况下你只需要让你的类实现Serializable接口， 我就可以接管后续的所有工作。不用你操心了。



可是用XML, 你还得写一堆代码把一个类中的各个字段和他们的值变成XML标签/属性/值 才行。  当用来表示对象的XML字符串漫游到另外一个机器上， 还得有一堆代码把XML变成对象。



我嘲笑XML说： “小伙子， 你这也太麻烦了吧， 人类的时间多宝贵， 为了用XML做序列化，代价好高嗷！”



“老家伙，没你想的那么复杂， 你可能不知道， 我们有些类库能自动帮助把对象变成XML”    他毫不示弱。



“ 不要忘了 ”  小伙子补充道   “ 我们XML可是语言中立的， 在这里是Java对象， 到了客户端 什么语言都行 ， Java/C/Python/Ruby.... 都没问题， 甚至浏览器里的Javascript都能处理， 这一点你不行了吧？”



这家伙戳到了我的痛处，  在浏览器中我的确需要一个Java 环境才行运行 ，  唉，真是成也Java ,败也Java。



我说： “我知道你是语言无关的， 但是你注意到没有， 你的XML标签冗余太多， 真正的数据很少。 比如有个Person类， 有两个字段name和address,  用你的XML做序列化就变成了这个样子<person><name>abc</name><address>xyz</address></person>， 这在网络上传输起来绝对是一种浪费！  我的java 字节流就不一样了， 二进制的，非常紧凑，一点都不浪费！“



XML小伙子沉默了， 小样， 我也抓住了你的痛点。



过了两天，这个小伙子又带来了一个叫JSON的小弟， 他得意洋洋的向我炫耀： 用了JSON以后，数据精简多了， 不信你看：{"name”：“abc", "address":"xyz"}  ， 现在我们不但语言中立，还很精简， 老家伙，这下你无话可说了吧。





![image-20190302145530981](https://ws4.sinaimg.cn/large/006tKfTcly1g0ogggvixqj30pi0vqgri.jpg)



我认栽， 但是让XML也没高兴多久， 让他没有想到的是， Web时代JSON和Javascript是一对绝配， 联手统治了浏览器。 连XML自己都快没饭吃了。



（码农翻身注： 参见《[Javascript： 一个屌丝的逆袭](http://mp.weixin.qq.com/s?__biz=MzAxOTc0NzExNg==&mid=2665513059&idx=1&sn=a2eaf97d9e3000d15a33681d1b720463&scene=21#wechat_redirect)》）



3新协议的崛起



其实我一直觉得我的二进制序列化方式能减少存储空间， 方便网络传输，只是我的硬伤是无法跨越语言。



不行， 我不能一直守着Java这一亩三分地了， 必须扩展支持多语言， 这样才能脱离Java环境。



有人说： 计算机的所有问题都可以通过增加一个中间层来解决。   我是不是也可以搞个中间层出来？  



让这个中间层来定义/描述消息的格式，然后再弄一个小翻译器（ 不，叫编译器显的更加高大上）， 把这个程序员自定义的消息格式转换成各种语言的实现，例如java, python, c++等等。



在转换好的语言实现里边，自动包含了要被序列化的类的定义， 以及实现序列化和反序列化的代码， 当然序列化以后的数据是二进制的。



等到二进制的字节流通过网络传输到另外一台机器， 就可以反序列化为各种语言（例如Python）的对象了， 当然必须是同一个消息格式产生的Python类。



![image-20190302145545306](https://ws3.sinaimg.cn/large/006tKfTcly1g0oggp9iq6j30q20u4dod.jpg)



不仅仅是Python, C++, Go, C# , 甚至Javascript 都可以用 ！



是不是很爽 ？  既语言中立， 又采用二进制传输， 体积小，解析快， 完美的综合了各种优点！



唯一的额外工作是需要把消息格式的定义编译成各种语言的实现， 为了能支持多语言，这也是没办法的事情 。



我得意的把新方案给XML和JSON这两个家伙看了， 从表情来看，就知道他俩如临大敌了。



我也把方案提交给了我们服务器世界的老大 ， 他大为赞赏， 决定先在部分场景下用起来， 例如对象存入缓存的时候需要序列化， 以前用json,   占用空间很大， 改用了我的新方案以后， 不但减少了空间使用， 还提升了读写的效率， 效果不错。

# 6. 枚举与序列化

> 写在前面：Java SE5提供了一种新的类型-[Java的枚举类型](http://www.hollischuang.com/archives/195)，关键字enum可以将一组具名的值的有限集合创建为一种新的类型，而这些具名的值可以作为常规的程序组件使用，这是一种非常有用的功能。本文将深入分析枚举的源码，看一看枚举是怎么实现的，他是如何保证线程安全的，以及为什么用枚举实现的单例是最好的方式。

## 枚举是如何保证线程安全的

要想看源码，首先得有一个类吧，那么枚举类型到底是什么类呢？是enum吗？答案很明显不是，enum就和class一样，只是一个关键字，他并不是一个类，那么枚举是由什么类维护的呢，我们简单的写一个枚举：

```
public enum t {
    SPRING,SUMMER,AUTUMN,WINTER;
}
```

然后我们使用反编译，看看这段代码到底是怎么实现的，反编译（[Java的反编译](http://www.hollischuang.com/archives/58)）后代码内容如下：

```
public final class T extends Enum
{
    private T(String s, int i)
    {
        super(s, i);
    }
    public static T[] values()
    {
        T at[];
        int i;
        T at1[];
        System.arraycopy(at = ENUM$VALUES, 0, at1 = new T[i = at.length], 0, i);
        return at1;
    }

    public static T valueOf(String s)
    {
        return (T)Enum.valueOf(demo/T, s);
    }

    public static final T SPRING;
    public static final T SUMMER;
    public static final T AUTUMN;
    public static final T WINTER;
    private static final T ENUM$VALUES[];
    static
    {
        SPRING = new T("SPRING", 0);
        SUMMER = new T("SUMMER", 1);
        AUTUMN = new T("AUTUMN", 2);
        WINTER = new T("WINTER", 3);
        ENUM$VALUES = (new T[] {
            SPRING, SUMMER, AUTUMN, WINTER
        });
    }
}
```

通过反编译后代码我们可以看到，`public final class T extends Enum`，说明，该类是继承了Enum类的，同时final关键字告诉我们，这个类也是不能被继承的。当我们使用`enmu`来定义一个枚举类型的时候，编译器会自动帮我们创建一个final类型的类继承Enum类,所以枚举类型不能被继承，我们看到这个类中有几个属性和方法。

我们可以看到：

```
        public static final T SPRING;
        public static final T SUMMER;
        public static final T AUTUMN;
        public static final T WINTER;
        private static final T ENUM$VALUES[];
        static
        {
            SPRING = new T("SPRING", 0);
            SUMMER = new T("SUMMER", 1);
            AUTUMN = new T("AUTUMN", 2);
            WINTER = new T("WINTER", 3);
            ENUM$VALUES = (new T[] {
                SPRING, SUMMER, AUTUMN, WINTER
            });
        }
```

都是static类型的，因为static类型的属性会在类被加载之后被初始化，我们在[深度分析Java的ClassLoader机制（源码级别）](http://www.hollischuang.com/archives/199)和[Java类的加载、链接和初始化](http://www.hollischuang.com/archives/201)两个文章中分别介绍过，当一个Java类第一次被真正使用到的时候静态资源被初始化、Java类的加载和初始化过程都是线程安全的。所以，**创建一个enum类型是线程安全的**。

## 为什么用枚举实现的单例是最好的方式

在[[转+注\]单例模式的七种写法](http://www.hollischuang.com/archives/205)中，我们看到一共有七种实现单例的方式，其中，**Effective Java**作者`Josh Bloch` 提倡使用枚举的方式，既然大神说这种方式好，那我们就要知道它为什么好？

**1. 枚举写法简单**

> 写法简单这个大家看看[转+注]单例模式的七种写法里面的实现就知道区别了。

```
public enum EasySingleton{
    INSTANCE;
}
```

你可以通过`EasySingleton.INSTANCE`来访问。

**2. 枚举自己处理序列化**

> 我们知道，以前的所有的单例模式都有一个比较大的问题，就是一旦实现了Serializable接口之后，就不再是单例得了，因为，每次调用 readObject()方法返回的都是一个新创建出来的对象，有一种解决办法就是使用readResolve()方法来避免此事发生。但是，**为了保证枚举类型像Java规范中所说的那样，每一个枚举类型极其定义的枚举变量在JVM中都是唯一的，在枚举类型的序列化和反序列化上，Java做了特殊的规定。**原文如下：
>
> > Enum constants are serialized differently than ordinary serializable or externalizable objects. The serialized form of an enum constant consists solely of its name; field values of the constant are not present in the form. To serialize an enum constant, ObjectOutputStream writes the value returned by the enum constant’s name method. To deserialize an enum constant, ObjectInputStream reads the constant name from the stream; the deserialized constant is then obtained by calling the java.lang.Enum.valueOf method, passing the constant’s enum type along with the received constant name as arguments. Like other serializable or externalizable objects, enum constants can function as the targets of back references appearing subsequently in the serialization stream. The process by which enum constants are serialized cannot be customized: any class-specific writeObject, readObject, readObjectNoData, writeReplace, and readResolve methods defined by enum types are ignored during serialization and deserialization. Similarly, any serialPersistentFields or serialVersionUID field declarations are also ignored–all enum types have a fixedserialVersionUID of 0L. Documenting serializable fields and data for enum types is unnecessary, since there is no variation in the type of data sent.
>
> 大概意思就是说，在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。 我们看一下这个`valueOf`方法：

```
public static <T extends Enum<T>> T valueOf(Class<T> enumType,String name) {  
            T result = enumType.enumConstantDirectory().get(name);  
            if (result != null)  
                return result;  
            if (name == null)  
                throw new NullPointerException("Name is null");  
            throw new IllegalArgumentException(  
                "No enum const " + enumType +"." + name);  
        }  
```

从代码中可以看到，代码会尝试从调用`enumType`这个`Class`对象的`enumConstantDirectory()`方法返回的`map`中获取名字为`name`的枚举对象，如果不存在就会抛出异常。再进一步跟到`enumConstantDirectory()`方法，就会发现到最后会以反射的方式调用`enumType`这个类型的`values()`静态方法，也就是上面我们看到的编译器为我们创建的那个方法，然后用返回结果填充`enumType`这个`Class`对象中的`enumConstantDirectory`属性。

所以，**JVM对序列化有保证。**

**3.枚举实例创建是thread-safe(线程安全的)**

> 我们在[深度分析Java的ClassLoader机制（源码级别）](http://www.hollischuang.com/archives/199)和[Java类的加载、链接和初始化](http://www.hollischuang.com/archives/201)两个文章中分别介绍过，当一个Java类第一次被真正使用到的时候静态资源被初始化、Java类的加载和初始化过程都是线程安全的。所以，**创建一个enum类型是线程安全的**。
