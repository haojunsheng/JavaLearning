[TOC]

# 前言

Java中I/O操作主要是指使用Java进行输入，输出操作. Java所有的I/O机制都是基于数据流进行输入输出，这些数据流表示了字符或者字节数据的流动序列。

<u>什么是数据流</u>？

数据流是一串连续不断的数据的集合，就象水管里的水流，在水管的一端一点一点地供水，而在水管的另一端看到的是一股连续不断的水流。数据写入程序可以是一段、一段地向数据流管道中写入数据，这些数据段会按先后顺序形成一个长的数据流。对数据读取程序来说，看不到数据流在写入时的分段情况，每次可以读取其中的任意长度的数据，但只能先读取前面的数据后，再读取后面的数据（不能随机读取）。不管写入时是将数据分多次写入，还是作为一个整体一次写入，读取时的效果都是完全一样的。

简而言之：**数据流是一组有序，有起点和终点的字节的数据序列。包括输入流和输出流。**

当程序需要读取数据的时候，就会建立一个通向数据源的连接，这个数据源可以是**文件，内存，或是网络连接**。类似的，当程序需要写入数据的时候，就会建立一个通向目的地的连接。

<u>数据流分类：</u>

流序列中的数据既可以是未经加工的原始二进制数据，也可以是经一定编码处理后符合某种格式规定的特定数据。因此Java中的流分为两种： 

**1) 字节流：**数据流中最小的数据单元是字节

 **2) 字符流：**数据流中最小的数据单元是字符， Java中的字符是Unicode编码，一个字符占用两个字节。

# 1.概览

Java.io包中最重要的就是5个类和一个接口。5个类指的是File、OutputStream、InputStream、Writer、Reader；一个接口指的是Serializable。掌握了这些就掌握了Java I/O的精髓了。

Java I/O主要包括如下3层次：

1. 流式部分——最主要的部分。如：OutputStream、InputStream、Writer、Reader等
2. 非流式部分——如：File类、RandomAccessFile类和FileDescriptor等类
3. 其他——文件读取部分的与安全相关的类，如：SerializablePermission类，以及与本地操作系统相关的文件系统的类，如：FileSystem类和Win32FileSystem类和WinNTFileSystem类。

主要类如下：

1. File（文件特征与管理）：用于文件或者目录的描述信息，例如生成新目录，修改文件名，删除文件，判断文件所在路径等。
2. InputStream（字节流，二进制格式操作）：抽象类，基于字节的输入操作，是所有输入流的父类。定义了所有输入流都具有的共同特征。
3. OutputStream（字节流，二进制格式操作）：抽象类。基于字节的输出操作。是所有输出流的父类。定义了所有输出流都具有的共同特征。
4. Reader（字符流，文本格式操作）：抽象类，基于字符的输入操作。
5. Writer（字符流，文本格式操作）：抽象类，基于字符的输出操作。
6. RandomAccessFile（随机文件操作）：它的功能丰富，**可以从文件的任意位置进行存取（输入输出）操作**。

![I:O体系.png](https://ws4.sinaimg.cn/large/006tKfTcly1g0ghgt6nw9j30gv09mdgb.jpg)

# 2.I/O流

java.io包里有4个基本类：InputStream、OutputStream及Reader、Writer类，它们分别处理字节流和字符流。

其他各种各样的流都是由这4个派生出来的。

![I/O流](https://ws2.sinaimg.cn/large/006tKfTcly1g0ghh7z0kzj30u012qwgk.jpg)

**按来源/去向分类：**

1. File（文件）： FileInputStream, FileOutputStream, FileReader, FileWriter
2. byte[]：ByteArrayInputStream, ByteArrayOutputStream
3. Char[]: CharArrayReader, CharArrayWriter
4. String: StringBufferInputStream, StringReader, StringWriter
5. 网络数据流：InputStream, OutputStream, Reader, Writer

## 2.1 字节流

### 2.1.1 InputStream

InputStream 为字节输入流，它本身为一个抽象类，必须依靠其子类实现各种功能，此抽象类是表示字节输入流的所有类的超类。 继承自InputStream 的流都是向程序中输入数据的，且数据单位为字节（8bit）；

InputStream是输入字节数据用的类，所以InputStream类提供了3种重载的read方法.Inputstream类中的常用方法：

- public abstract int read( )：读取一个byte的数据，返回值是高位补0的int类型值。若返回值=-1说明没有读取到任何字节读取工作结束。
- public int read(byte b[ ])：读取b.length个字节的数据放到b数组中。返回值是读取的字节数。该方法实际上是调用下一个方法实现的
- public int read(byte b[ ], int off, int len)：从输入流中最多读取len个字节的数据，存放到偏移量为off的b数组中。
- public int available( )：返回输入流中可以读取的字节数。注意：若输入阻塞，当前线程将被挂起，如果InputStream对象调用这个方法的话，它只会返回0，这个方法必须由继承InputStream类的子类对象调用才有用，
- public long skip(long n)：忽略输入流中的n个字节，返回值是实际忽略的字节数, 跳过一些字节来读取
- public int close( ) ：使用完后，必须对我们打开的流进行关闭。

来看看几种不同的InputStream：

构造函数的参数不同。

1. FileInputStream把一个文件作为InputStream，实现对文件的读取操作
2. ByteArrayInputStream：把内存中的一个字节数组缓冲区作为InputStream使用
3. StringBufferInputStream：把一个String对象作为InputStream(不建议使用)
4. PipedInputStream：实现了pipe的概念，主要在线程中使用
5. SequenceInputStream：把多个InputStream合并为一个InputStream

### 2.1.2 OutputStream

OutputStream提供了3个write方法来做数据的输出，这个是和InputStream是相对应的。

- public void write(byte b[ ])：将参数b中的字节写到输出流。
- public void write(byte b[ ], int off, int len) ：将参数b的从偏移量off开始的len个字节写到输出流。
- public abstract void write(int b) ：先将int转换为byte类型，把低字节写入到输出流中。
- public void flush( ) : 将数据缓冲区中数据全部输出，并清空缓冲区。
- public void close( ) : 关闭输出流并释放与流相关的系统资源。

几种不同的OutputStream：

1. ByteArrayOutputStream：把信息存入内存中的一个缓冲区中。自己创建字节数组。
2. FileOutputStream：把信息存入文件中。
3. PipedOutputStream：实现了pipe的概念，主要在线程中使用
4. SequenceOutputStream：把多个OutStream合并为一个OutStream

Reader和InputStream类似；Writer和OutputStream类似。

有两个需要注意的：

1. **InputStreamReader** ： 从输入流读取字节，在将它们转换成字符。
2. **BufferReader :**接受Reader对象作为参数，并对其添加字符缓冲器，使用readline()方法可以读取一行。



## 2.2 字符流

InputStream和OutputStream在面向字节的I/O中提供了极有价值的功能，Reader和Writer提供了兼容Unicode与面向字符的I/O功能。为了支持国际化，InputStream仅支持8位字节流，不能很好的处理16位的Unicode字符。java中的char是两个字节，16位。

所以我们应该尽量采用字符（Reader和Writer），只有当编译失败的时候，才采用字节流。

InputStreamReader可以把InputStream转化为Reader，OutputStreamWriter可以把OutputStream转化为Writer。





### 2.2.1 Reader

### 2.2.2 Writer



# 3. 如何选择I/O流

1. 确定是输入还是输出
   输入:输入流 InputStream Reader
   输出:输出流 OutputStream Writer
2. 明确操作的数据对象是否是纯文本
   是:字符流 Reader，Writer
   否:字节流 InputStream，OutputStream
3. 明确具体的设备。
   - 文件：
     读：FileInputStream,, FileReader,
     写：FileOutputStream，FileWriter
   - 数组：
     byte[ ]：ByteArrayInputStream, ByteArrayOutputStream
     char[ ]：CharArrayReader, CharArrayWriter
   - String：
     StringBufferInputStream(已过时，因为其只能用于String的每个字符都是8位的字符串), StringReader, StringWriter
   - Socket流
     键盘：用System.in（是一个InputStream对象）读取，用System.out（是一个OutoutStream对象）打印
4. 是否需要转换流
   是，就使用转换流，从Stream转化为Reader、Writer：InputStreamReader，OutputStreamWriter
5. 是否需要缓冲提高效率
   是就加上Buffered：BufferedInputStream, BufferedOuputStream, BufferedReader, BufferedWriter
6. 是否需要格式化输出

# 4. 示例代码

- 将标准输入（键盘输入）显示到标准输出（显示器），支持字符。

```
`char` `ch;``BufferedReader in = ``new` `BufferedReader(``new` `InputStreamReader(System.in));  ``//将字节流转为字符流，带缓冲``try` `{``    ``while` `((ch = (``char``) in.read()) != -``1``){``        ``System.out.print(ch);``    ``}``} ``catch` `(IOException e) {``    ``e.printStackTrace();``}`
```

- 将AtomicityTest.java的内容打印到显示器

方法一：

```
`BufferedReader in = ``new` `BufferedReader(``new` `FileReader(``"AtomicityTest.java"``));``String s;``try` `{``    ``while` `((s = in.readLine()) != ``null``){``        ``System.out.println(s);``    ``}``    ``in.close();``} ``catch` `(IOException e) {``    ``e.printStackTrace();``}`
```

方法二：

```
`FileReader in = ``new` `FileReader(``"AtomicityTest.java"``);``int` `b;``try` `{``    ``while` `((b = in.read()) != -``1``){``        ``System.out.print((``char``)b);``    ``}``    ``in.close();``} ``catch` `(IOException e) {``    ``e.printStackTrace();``}`
```

方法三：(有可能出现乱码）

```
`FileInputStream in = ``new` `FileInputStream(``"AtomicityTest.java"``);``int` `n = ``50``;``byte``[] buffer = ``new` `byte``[n];``try` `{``    ``while` `((in.read(buffer,``0``,n) != -``1` `&& n > ``0``)){``        ``System.out.print(``new` `String(buffer));``    ``}``    ``in.close();``} ``catch` `(IOException e) {``    ``e.printStackTrace();``}`
```

- 将文件A的内容拷贝到文件B

```
`FileInputStream in = ``new` `FileInputStream(``"AtomicityTest.java"``);``FileOutputStream out = ``new` `FileOutputStream(``"copy.txt"``);``int` `b;``while` `((b = in.read()) != -``1``){``    ``out.write(b);``}``out.flush();``in.close();``out.close();`
```

- 将标准输入的内容写入文件

```
`Scanner in = ``new` `Scanner(System.in);``FileWriter out = ``new` `FileWriter(``"systemIn.log"``);``String s;``while` `(!(s = in.nextLine()).equals(``"Q"``)){``    ``out.write(s + ``"\n"``);``}``out.flush();``out.close();``in.close();`
```



























