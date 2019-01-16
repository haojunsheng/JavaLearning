[TOC]

# 前言

主要是关于编译与反编译的一些介绍。

能够回答下面的问题：

**即时编译器、编译优化**

# 1. HotSpot 虚拟机对象探秘

请读者首先注意本篇的题目中的限定语“HotSpot 虚拟机”，在虚拟机规范中明确写道：“所有在虚拟机规范之中没有明确描述的实现细节，都不应成为虚拟机设计者发挥创造性的牵绊，设计者可以完全自主决定所有规范中不曾描述的虚拟机内部细节，例如：运行时数据区的内存如何布局、选用哪种垃圾收集的算法等”。因此，本篇（整个内存篇中所有的文章）的内容会涉及到虚拟机“自主决定”的实现，我们的讨论将在 HotSpot VM 的范围内展开。同时，我也假定读者已经理解了虚拟机规范中所定义的 JVM 公共内存模型，例如运行时数据区域、栈帧结构等基础知识。

## 1.1 对象的创建

Java 是一门面向对象的编程语言，Java 程序运行过程中每时每刻都有对象被创建出来。在语言层面上，创建对象通常（例外：克隆、反序列化）仅仅是一个 new 关键字而已，而在虚拟机中，对象（本文中讨论的对象限于普通 Java 对象，不包括数组和 Class 对象等）的创建又是怎样一个过程呢？

虚拟机遇到一条 new 指令时，首先将去检查这个指令的参数是否能在常量池中定位到一个类的符号引用，并且检查这个符号引用代表的类是否已被加载、解析和初始化过的。如果没有，那必须先执行相应的类加载过程。

在类加载通过后，接下来虚拟机将为新生对象分配内存。对象所需内存的大小在类加载完成后便可完全确定（如何确定在下一节对象内存布局时再详细讲解），为对象分配空间的任务具体便等同于一块确定大小的内存从 Java 堆中划分出来，怎么划呢？假设 Java 堆中内存是绝对规整的，所有用过的内存都被放在一边，空闲的内存被放在另一边，中间放着一个指针作为分界点的指示器，那所分配内存就仅仅是把那个指针向空闲空间那边挪动一段与对象大小相等的距离，这种分配方式称为“指针碰撞”（Bump The Pointer）。如果 Java 堆中的内存并不是规整的，已被使用的内存和空闲的内存相互交错，那就没有办法简单的进行指针碰撞了，虚拟机就必须维护一个列表，记录上哪些内存块是可用的，在分配的时候从列表中找到一块足够大的空间划分给对象实例，并更新列表上的记录，这种分配方式称为“空闲列表”（Free List）。选择哪种分配方式由 Java 堆是否规整决定，而 Java 堆是否规整又由所采用的垃圾收集器是否带有压缩整理功能决定。因此在使用 Serial、ParNew 等带 Compact 过程的收集器时，系统采用的分配算法是指针碰撞，而使用 CMS 这种基于 Mark-Sweep 算法的收集器时（说明一下，CMS 收集器可以通过 UseCMSCompactAtFullCollection 或 CMSFullGCsBeforeCompaction 来整理内存），就通常采用空闲列表。

除如何划分可用空间之外，还有另外一个需要考虑的问题是对象创建在虚拟机中是非常频繁的行为，即使是仅仅修改一个指针所指向的位置，在并发情况下也并不是线程安全的，可能出现正在给对象 A 分配内存，指针还没来得及修改，对象 B 又同时使用了原来的指针来分配内存。解决这个问题有两个方案，一种是对分配内存空间的动作进行同步——实际上虚拟机是采用 CAS 配上失败重试的方式保证更新操作的原子性；另外一种是把内存分配的动作按照线程划分在不同的空间之中进行，即每个线程在 Java 堆中预先分配一小块内存，称为本地线程分配缓冲，（TLAB ，Thread Local Allocation Buffer），哪个线程要分配内存，就在哪个线程的 TLAB 上分配，只有 TLAB 用完，分配新的 TLAB 时才需要同步锁定。虚拟机是否使用 TLAB，可以通过 -XX:+/-UseTLAB 参数来设定。

内存分配完成之后，虚拟机需要将分配到的内存空间都初始化为零值（不包括对象头），如果使用 TLAB 的话，这一个工作也可以提前至 TLAB 分配时进行。这步操作保证了对象的实例字段在 Java 代码中可以不赋初始值就直接使用，程序能访问到这些字段的数据类型所对应的零值。

接下来，虚拟机要对对象进行必要的设置，例如这个对象是哪个类的实例、如何才能找到类的元数据信息、对象的哈希码、对象的 GC 分代年龄等信息。这些信息存放在对象的对象头（Object Header）之中。根据虚拟机当前的运行状态的不同，如是否启用偏向锁等，对象头会有不同的设置方式。关于对象头的具体内容，在下一节再详细介绍。

在上面工作都完成之后，在虚拟机的视角来看，一个新的对象已经产生了。但是在 Java 程序的视角看来，对象创建才刚刚开始——<init> 方法还没有执行，所有的字段都为零呢。所以一般来说（由字节码中是否跟随有 invokespecial 指令所决定），new 指令之后会接着就是执行 <init> 方法，把对象按照程序员的意愿进行初始化，这样一个真正可用的对象才算完全产生出来。

下面代码是 HotSpot 虚拟机 bytecodeInterpreter.cpp 中的代码片段（这个解释器实现很少机会实际使用，大部分平台上都使用模板解释器；当代码通过 JIT 编译器执行时差异就更大了。不过这段代码用于了解 HotSpot 的运作过程是没有什么问题的）。

**代码清单 1：HotSpot 解释器代码片段**

```c++
// 确保常量池中存放的是已解释的类 
if (!constants->tag_at(index).is_unresolved_klass()) { 
    // 断言确保是 klassOop 和 instanceKlassOop（这部分下一节介绍） 
    oop entry = (klassOop) *constants->obj_at_addr(index); 
    assert(entry->is_klass(), "Should be resolved klass"); 
    klassOop k_entry = (klassOop) entry; 
    assert(k_entry->klass_part()->oop_is_instance(), "Should be instanceKlass"); 
    instanceKlass* ik = (instanceKlass*) k_entry->klass_part(); 
    // 确保对象所属类型已经经过初始化阶段 
    if ( ik->is_initialized() && ik->can_be_fastpath_allocated() ) { 
        // 取对象长度 
        size_t obj_size = ik->size_helper(); 
        oop result = NULL; 
        // 记录是否需要将对象所有字段置零值 
        bool need_zero = !ZeroTLAB; 
        // 是否在 TLAB 中分配对象 
        if (UseTLAB) { 
            result = (oop) THREAD->tlab().allocate(obj_size); 
        } 
        if (result == NULL) { 
            need_zero = true; 
            // 直接在 eden 中分配对象 
            retry: 
                HeapWord* compare_to = *Universe::heap()->top_addr(); 
                HeapWord* new_top = compare_to + obj_size; 
                // cmpxchg 是 x86 中的 CAS 指令，这里是一个 C++ 方法，通过 CAS 方式分配空间，并发失败的话，转到 retry 中重试直至成功分配为止 
                if (new_top <= *Universe::heap()->end_addr()) { 
                    if (Atomic::cmpxchg_ptr(new_top, Universe::heap()->top_addr(), compare_to) != compare_to) { 
                        goto retry; 
                    } 
                    result = (oop) compare_to; 
                } 
        } 
        if (result != NULL) { 
            // 如果需要，为对象初始化零值 
            if (need_zero ) { 
                HeapWord* to_zero = (HeapWord*) result + sizeof(oopDesc) / oopSize; 
                obj_size -= sizeof(oopDesc) / oopSize; 
                if (obj_size > 0 ) { 
                    memset(to_zero, 0, obj_size * HeapWordSize); 
                } 
            } 
            // 根据是否启用偏向锁，设置对象头信息 
            if (UseBiasedLocking) { 
                result->set_mark(ik->prototype_header()); 
            } else { 
                result->set_mark(markOopDesc::prototype()); 
            } 
            result->set_klass_gap(0); 
            result->set_klass(k_entry); 
            // 将对象引用入栈，继续执行下一条指令 
            SET_STACK_OBJECT(result, 0); 
            UPDATE_PC_AND_TOS_AND_CONTINUE(3, 1); 
        } 
    } 
}
```



## 1.2 对象的内存布局

HotSpot 虚拟机中，对象在内存中存储的布局可以分为三块区域：对象头（Header）、实例数据（Instance Data）和对齐填充（Padding）。

HotSpot 虚拟机的对象头包括两部分信息，第一部分用于存储对象自身的运行时数据，如哈希码（HashCode）、GC 分代年龄、锁状态标志、线程持有的锁、偏向线程 ID、偏向时间戳等等，这部分数据的长度在 32 位和 64 位的虚拟机（暂不考虑开启压缩指针的场景）中分别为 32 个和 64 个 Bits，官方称它为“Mark Word”。对象需要存储的运行时数据很多，其实已经超出了 32、64 位 Bitmap 结构所能记录的限度，但是对象头信息是与对象自身定义的数据无关的额外存储成本，考虑到虚拟机的空间效率，Mark Word 被设计成一个非固定的数据结构以便在极小的空间内存储尽量多的信息，它会根据对象的状态复用自己的存储空间。例如在 32 位的 HotSpot 虚拟机中对象未被锁定的状态下，Mark Word 的 32 个 Bits 空间中的 25Bits 用于存储对象哈希码（HashCode），4Bits 用于存储对象分代年龄，2Bits 用于存储锁标志位，1Bit 固定为 0，在其他状态（轻量级锁定、重量级锁定、GC 标记、可偏向）下对象的存储内容如下表所示。

**表 1 HotSpot 虚拟机对象头 Mark Word** 

| **存储内容**                          | **标志位** | **状态**           |
| ------------------------------------- | ---------- | ------------------ |
| 对象哈希码、对象分代年龄              | 01         | 未锁定             |
| 指向锁记录的指针                      | 00         | 轻量级锁定         |
| 指向重量级锁的指针                    | 10         | 膨胀（重量级锁定） |
| 空，不需要记录信息                    | 11         | GC 标记            |
| 偏向线程 ID、偏向时间戳、对象分代年龄 | 01         | 可偏向             |

对象头的另外一部分是类型指针，即是对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例。并不是所有的虚拟机实现都必须在对象数据上保留类型指针，换句话说查找对象的元数据信息并不一定要经过对象本身，这点我们在下一节讨论。另外，如果对象是一个 Java 数组，那在对象头中还必须有一块用于记录数组长度的数据，因为虚拟机可以通过普通 Java 对象的元数据信息确定 Java 对象的大小，但是从数组的元数据中无法确定数组的大小。

以下是 HotSpot 虚拟机 markOop.cpp 中的代码（注释）片段，它描述了 32bits 下 MarkWord 的存储状态：

```c++
// Bit-format of an object header (most significant first, big endian layout below): 
// 
// 32 bits: 
// -------- 
// hash:25 ------------>| age:4 biased_lock:1 lock:2 (normal object) 
// JavaThread*:23 epoch:2 age:4 biased_lock:1 lock:2 (biased object) 
// size:32 ------------------------------------------>| (CMS free block) 
// PromotedObject*:29 ---------->| promo_bits:3 ----->| (CMS promoted object) 
```

接下来实例数据部分是对象真正存储的有效信息，也既是我们在程序代码里面所定义的各种类型的字段内容，无论是从父类继承下来的，还是在子类中定义的都需要记录袭来。这部分的存储顺序会受到虚拟机分配策略参数（FieldsAllocationStyle）和字段在 Java 源码中定义顺序的影响。HotSpot 虚拟机默认的分配策略为 longs/doubles、ints、shorts/chars、bytes/booleans、oops（Ordinary Object Pointers），从分配策略中可以看出，相同宽度的字段总是被分配到一起。在满足这个前提条件的情况下，在父类中定义的变量会出现在子类之前。如果 CompactFields 参数值为 true（默认为 true），那子类之中较窄的变量也可能会插入到父类变量的空隙之中。

第三部分对齐填充并不是必然存在的，也没有特别的含义，它仅仅起着占位符的作用。由于 HotSpot VM 的自动内存管理系统要求对象起始地址必须是 8 字节的整数倍，换句话说就是对象的大小必须是 8 字节的整数倍。对象头部分正好似 8 字节的倍数（1 倍或者 2 倍），因此当对象实例数据部分没有对齐的话，就需要通过对齐填充来补全。

## 1.3 对象的访问定位

建立对象是为了使用对象，我们的 Java 程序需要通过栈上的 reference 数据来操作堆上的具体对象。由于 reference 类型在 Java 虚拟机规范里面只规定了是一个指向对象的引用，并没有定义这个引用应该通过什么种方式去定位、访问到堆中的对象的具体位置，对象访问方式也是取决于虚拟机实现而定的。主流的访问方式有使用句柄和直接指针两种。

- 如果使用句柄访问的话，Java 堆中将会划分出一块内存来作为句柄池，reference 中存储的就是对象的句柄地址，而句柄中包含了对象实例数据与类型数据的具体各自的地址信息。如图 1 所示。
- ![img](https://ws3.sinaimg.cn/large/006tNc79ly1fz675jpc7sj30f306tmxi.jpg)

- ​									**图 1 通过句柄访问对象**

- 如果使用直接指针访问的话，Java 堆对象的布局中就必须考虑如何放置访问类型数据的相关信息，reference 中存储的直接就是对象地址，如图 2 所示。
- ![img](https://ws2.sinaimg.cn/large/006tNc79ly1fz675q447oj30f306tdg4.jpg)

- ​								**图 2 通过直接指针访问对象**

这两种对象访问方式各有优势，使用句柄来访问的最大好处就是 reference 中存储的是稳定句柄地址，在对象被移动（垃圾收集时移动对象是非常普遍的行为）时只会改变句柄中的实例数据指针，而 reference 本身不需要被修改。

使用直接指针来访问最大的好处就是速度更快，它节省了一次指针定位的时间开销，由于对象访问的在 Java 中非常频繁，因此这类开销积小成多也是一项非常可观的执行成本。从上一部分讲解的对象内存布局可以看出，就虚拟机 HotSpot 而言，它是使用第二种方式进行对象访问，但在整个软件开发的范围来看，各种语言、框架中使用句柄来访问的情况也十分常见。



------

# 2. [HotSpot 源码](https://github.com/openjdk-mirror/jdk7u-hotspot)



# 3. [深入分析Java的编译原理](https://www.hollischuang.com/archives/2322)

在此之前，我们首先学习下[Java代码的编译与反编译那些事儿](https://www.hollischuang.com/archives/58)

## 3.1 [Java代码的编译与反编译那些事儿](https://www.hollischuang.com/archives/58)

### 3.1.1 编程语言

在介绍编译和反编译之前，我们先来简单介绍下编程语言（Programming Language）。编程语言（Programming Language）分为低级语言（Low-level Language）和高级语言（High-level Language）。

机器语言（Machine Language）和汇编语言（Assembly Language）属于低级语言，直接用计算机指令编写程序。

而C、C++、Java、Python等属于高级语言，用语句（Statement）编写程序，语句是计算机指令的抽象表示。

举个例子，同样一个语句用C语言、汇编语言和机器语言分别表示如下：

计算机只能对数字做运算，符号、声音、图像在计算机内部都要用数字表示，指令也不例外，上表中的机器语言完全由十六进制数字组成。最早的程序员都是直接用机器语言编程，但是很麻烦，需要查大量的表格来确定每个数字表示什么意思，编写出来的程序很不直观，而且容易出错，于是有了汇编语言，把机器语言中一组一组的数字用助记符（Mnemonic）表示，直接用这些助记符写出汇编程序，然后让汇编器（Assembler）去查表把助记符替换成数字，也就把汇编语言翻译成了机器语言。

但是，汇编语言用起来同样比较复杂，后面，就衍生出了Java、C、C++等高级语言。

###3.1.2 什么是编译

上面提到语言有两种，一种低级语言，一种高级语言。可以这样简单的理解：低级语言是计算机认识的语言、高级语言是程序员认识的语言。

那么如何从高级语言转换成低级语言呢？这个过程其实就是编译。

从上面的例子还可以看出，C语言的语句和低级语言的指令之间不是简单的一一对应关系，一条`a=b+1`;语句要翻译成三条汇编或机器指令，这个过程称为编译（Compile），由编译器（Compiler）来完成，显然编译器的功能比汇编器要复杂得多。用C语言编写的程序必须经过编译转成机器指令才能被计算机执行，编译需要花一些时间，这是用高级语言编程的一个缺点，然而更多的是优点。首先，用C语言编程更容易，写出来的代码更紧凑，可读性更强，出了错也更容易改正。

**将便于人编写、阅读、维护的高级计算机语言所写作的源代码程序，翻译为计算机能解读、运行的低阶机器语言的程序的过程就是编译。负责这一过程的处理的工具叫做编译器**

现在我们知道了什么是编译，也知道了什么是编译器。不同的语言都有自己的编译器，Java语言中负责编译的编译器是一个命令：`javac`

> javac是收录于JDK中的Java语言编译器。该工具可以将后缀名为.java的源文件编译为后缀名为.class的可以运行于Java虚拟机的字节码。

**当我们写完一个HelloWorld.java文件后，我们可以使用javac HelloWorld.java命令来生成HelloWorld.class文件，这个class类型的文件是JVM可以识别的文件。通常我们认为这个过程叫做Java语言的编译。其实，class文件仍然不是机器能够识别的语言，因为机器只能识别机器语言，还需要JVM再将这种class文件类型字节码转换成机器可以识别的机器语言。**

### 3.1.3 什么是反编译

反编译的过程与编译刚好相反，就是将已编译好的编程语言还原到未编译的状态，也就是找出程序语言的源代码。就是将机器看得懂的语言转换成程序员可以看得懂的语言。Java语言中的反编译一般指将`class`文件转换成`java`文件。

有了反编译工具，我们可以做很多事情，最主要的功能就是有了反编译工具，我们就能读得懂Java编译器生成的字节码。如果你想问读懂字节码有啥用，那么我可以很负责任的告诉你，好处大大的。比如我的博文几篇典型的原理性文章，都是通过反编译工具得到反编译后的代码分析得到的。如深入理解多线程（一）——Synchronized的实现原理、深度分析Java的枚举类型—-枚举的线程安全性及序列化问题、Java中的Switch对整型、字符型、字符串型的具体实现细节、Java的类型擦除等。我最近在GitChat写了一篇关于Java语法糖的文章，其中大部分内容都用到反编译工具来洞悉语法糖背后的原理。

### 3.1.4 Java反编译工具

本文主要介绍3个Java的反编译工具：**javap**、**jad**和**cfr**

#### javap

`javap`是jdk自带的一个工具，可以对代码反编译，也可以查看java编译器生成的字节码<u>。`javap`和其他两个反编译工具最大的区别是他生成的文件并不是`java`文件，也不像其他两个工具生成代码那样更容易理解。</u>拿一段简单的代码举例，如我们想分析Java 7中的`switch`是如何支持`String`的，我们先有以下可以编译通过的源代码：

```java
public class switchDemoString {
    public static void main(String[] args) {
        String str = "world";
        switch (str) {
            case "hello":
                System.out.println("hello");
                break;
            case "world":
                System.out.println("world");
                break;
            default:
                break;
        }
    }
}
```

执行以下两个命令：

```
javac switchDemoString.java
javap -c switchDemoString.class
```

生成代码如下：

```java
public class com.hollis.suguar.switchDemoString {
  public com.hollis.suguar.switchDemoString();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: ldc           #2                  // String world
       2: astore_1
       3: aload_1
       4: astore_2
       5: iconst_m1
       6: istore_3
       7: aload_2
       8: invokevirtual #3                  // Method java/lang/String.hashCode:()I
      11: lookupswitch  { // 2
              99162322: 36
             113318802: 50
               default: 61
          }
      36: aload_2
      37: ldc           #4                  // String hello
      39: invokevirtual #5                  // Method java/lang/String.equals:(Ljava/lang/Object;)Z
      42: ifeq          61
      45: iconst_0
      46: istore_3
      47: goto          61
      50: aload_2
      51: ldc           #2                  // String world
      53: invokevirtual #5                  // Method java/lang/String.equals:(Ljava/lang/Object;)Z
      56: ifeq          61
      59: iconst_1
      60: istore_3
      61: iload_3
      62: lookupswitch  { // 2
                     0: 88
                     1: 99
               default: 110
          }
      88: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
      91: ldc           #4                  // String hello
      93: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      96: goto          110
      99: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
     102: ldc           #2                  // String world
     104: invokevirtual #7                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
     107: goto          110
     110: return
}
```

我个人的理解，`javap`并没有将字节码反编译成`java`文件，而是生成了一种我们可以看得懂字节码。其实javap生成的文件仍然是字节码，只是程序员可以稍微看得懂一些。如果你对字节码有所掌握，还是可以看得懂以上的代码的。其实就是把String转成hashcode，然后进行比较。

**javap的优缺点：**

个人认为，一般情况下我们会用到`javap`命令的时候不多，一般只有在真的需要看字节码的时候才会用到。但是字节码中间暴露的东西是最全的，你肯定有机会用到，比如我在分析`synchronized`的原理的时候就有是用到`javap`。通过`javap`生成的字节码，我发现`synchronized`底层依赖了`ACC_SYNCHRONIZED`标记和`monitorenter`、`monitorexit`两个指令来实现同步。

#### jad

jad是一个比较不错的反编译工具，只要下载一个执行工具，就可以实现对`class`文件的反编译了。还是上面的源代码，使用jad反编译后内容如下：

命令：`jad switchDemoString.class`

```java
public class switchDemoString
{
    public switchDemoString()
    {
    }
    public static void main(String args[])
    {
        String str = "world";
        String s;
        switch((s = str).hashCode())
        {
        default:
            break;
        case 99162322:
            if(s.equals("hello"))
                System.out.println("hello");
            break;
        case 113318802:
            if(s.equals("world"))
                System.out.println("world");
            break;
        }
    }
}
```

看，这个代码你肯定看的懂，因为这不就是标准的java的源代码么。这个就很清楚的可以看到原来**字符串的switch是通过equals()和hashCode()方法来实现的**。

但是，jad已经很久不更新了，在对Java7生成的字节码进行反编译时，偶尔会出现不支持的问题，在对Java 8的lambda表达式反编译时就彻底失败。

#### CFR

jad很好用，但是无奈的是很久没更新了，所以只能用一款新的工具替代他，CFR是一个不错的选择，相比jad来说，他的语法可能会稍微复杂一些，但是好在他可以work。

如，我们使用cfr对刚刚的代码进行反编译。执行一下命令：

```shell
java -jar cfr_0_125.jar switchDemoString.class --decodestringswitch false
```

得到以下代码：

```java
public class switchDemoString {
    public static void main(String[] arrstring) {
        String string;
        String string2 = string = "world";
        int n = -1;
        switch (string2.hashCode()) {
            case 99162322: {
                if (!string2.equals("hello")) break;
                n = 0;
                break;
            }
            case 113318802: {
                if (!string2.equals("world")) break;
                n = 1;
            }
        }
        switch (n) {
            case 0: {
                System.out.println("hello");
                break;
            }
            case 1: {
                System.out.println("world");
                break;
            }
        }
    }
}
```

通过这段代码也能得到字符串的switch是通过`equals()`和`hashCode()`方法来实现的结论。

相比Jad来说，CFR有很多参数，还是刚刚的代码，如果我们使用以下命令，输出结果就会不同：

```shell
java -jar cfr_0_125.jar switchDemoString.class

public class switchDemoString {
    public static void main(String[] arrstring) {
        String string;
        switch (string = "world") {
            case "hello": {
                System.out.println("hello");
                break;
            }
            case "world": {
                System.out.println("world");
                break;
            }
        }
    }
}
```

所以`--decodestringswitch`表示对于switch支持string的细节进行解码。类似的还有`--decodeenumswitch`、`--decodefinally`、`--decodelambdas`等。在我的关于语法糖的文章中，我使用`--decodelambdas`对lambda表达式进行了反编译。 源码：

```java
public static void main(String... args) {
    List<String> strList = ImmutableList.of("Hollis", "公众号：Hollis", "博客：www.hollischuang.com");

    strList.forEach( s -> { System.out.println(s); } );
}
```

`java -jar cfr_0_125.jar lambdaDemo.class --decodelambdas false`反编译后代码：

```java
public static /* varargs */ void main(String ... args) {
    ImmutableList strList = ImmutableList.of((Object)"Hollis", (Object)"\u516c\u4f17\u53f7\uff1aHollis", (Object)"\u535a\u5ba2\uff1awww.hollischuang.com");
    strList.forEach((Consumer<String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$main$0(java.lang.String ), (Ljava/lang/String;)V)());
}

private static /* synthetic */ void lambda$main$0(String s) {
    System.out.println(s);
}
```

CFR还有很多其他参数，均用于不同场景，读者可以使用`java -jar cfr_0_125.jar --help`进行了解。这里不逐一介绍了。

### 3.1.5 如何防止反编译

由于我们有工具可以对`Class`文件进行反编译，所以，对开发人员来说，如何保护Java程序就变成了一个非常重要的挑战。但是，魔高一尺、道高一丈。当然有对应的技术可以应对反编译咯。但是，这里还是要说明一点，和网络安全的防护一样，无论做出多少努力，其实都只是提高攻击者的成本而已。无法彻底防治。

典型的应对策略有以下几种：

- 隔离Java程序
  - 让用户接触不到你的Class文件
- 对Class文件进行加密
  - 提到破解难度
- 代码混淆
  - 将代码转换成功能上等价，但是难于阅读和理解的形式

## 3.2 深入分析java的编译原理

在《[Java代码的编译与反编译](http://www.hollischuang.com/archives/58)》中，有过关于Java语言的编译和反编译的介绍。我们可以通过`javac`命令将Java程序的源代码编译成Java字节码，即我们常说的class文件。这是我们通常意义上理解的编译。

但是，字节码并不是机器语言，要想让机器能够执行，还需要把字节码翻译成机器指令。这个过程是Java虚拟机做的，这个过程也叫编译。是更深层次的编译。

在编译原理中，把源代码翻译成机器指令，一般要经过以下几个重要步骤：

![QQ20180414-203816](https://ws1.sinaimg.cn/large/006tNc79ly1fz68hcc7vfj30mr0eb0uf.jpg)

根据完成任务不同，可以将编译器的组成部分划分为前端（Front End）与后端（Back End）。

> 前端编译主要指与源语言有关但与目标机无关的部分，包括词法分析、语法分析、语义分析与中间代码生成。
>
> 后端编译主要指与目标机有关的部分，包括代码优化和目标代码生成等。

**我们可以把将.java文件编译成.class的编译过程称之为前端编译。把将.class文件翻译成机器指令的编译过程称之为后端编译。**

### 3.2.1 Java中的前端编译

前端编译主要指与源语言有关但与目标机无关的部分，包括词法分析、语法分析、语义分析与中间代码生成。

我们所熟知的`javac`的编译就是前端编译。除了这种以外，我们使用的很多IDE，如eclipse，idea等，都内置了前端编译器。主要功能就是把`.java`代码转换成`.class`代码。

####词法分析

词法分析阶段是编译过程的第一个阶段。这个阶段的任务是从左到右一个字符一个字符地读入源程序，将字符序列转换为标记（token）序列的过程。这里的标记是一个字符串，是构成源代码的最小单位。在这个过程中，词法分析器还会对标记进行分类。

词法分析器通常不会关心标记之间的关系（属于语法分析的范畴），举例来说：词法分析器能够将括号识别为标记，但并不保证括号是否匹配。

#### 语法分析

语法分析的任务是在词法分析的基础上将单词序列组合成各类语法短语，如“程序”，“语句”，“表达式”等等.语法分析程序判断源程序在结构上是否正确.源程序的结构由上下文无关文法描述。

#### 语义分析

语义分析是编译过程的一个逻辑阶段， 语义分析的任务是对结构上正确的源程序进行上下文有关性质的审查，进行类型审查。语义分析是审查源程序有无语义错误，为代码生成阶段收集类型信息。

语义分析的一个重要部分就是类型检查。比如很多语言要求数组下标必须为整数，如果使用浮点数作为下标，编译器就必须报错。再比如，很多语言允许某些类型转换，称为自动类型转换。

#### 中间代码生成

在源程序的语法分析和语义分析完成之后，很多编译器生成一个明确的低级的或类机器语言的中间表示。该中间表示有两个重要的性质： 1.易于生成； 2.能够轻松地翻译为目标机器上的语言。

在Java中，`javac`执行的结果就是得到一个字节码，而这个字节码其实就是一种中间代码。

PS：著名的解语法糖操作，也是在javac中完成的。

### 3.2.2 Java中的后端编译

首先，我们大家都知道，通常通过 `javac` 将程序源代码编译，转换成 java 字节码，JVM 通过解释字节码将其翻译成对应的机器指令，逐条读入，逐条解释翻译。很显然，经过解释执行，其执行速度必然会比可执行的二进制字节码程序慢很多。这就是传统的JVM的**解释器（Interpreter）**的功能。为了解决这种效率问题，引入了 **JIT** (Just in Time)技术。

JAVA程序还是通过解释器进行解释执行，当JVM发现某个方法或代码块运行特别频繁的时候，就会认为这是“热点代码”（Hot Spot Code)。然后JIT会把部分“热点代码”**翻译**成本地机器相关的机器码，并进行**优化**，然后再把翻译后的机器码**缓存**起来(方法区)，以备下次使用。

HotSpot虚拟机中内置了两个JIT编译器：Client Complier和Server Complier，分别用在客户端和服务端，目前主流的HotSpot虚拟机中默认是采用解释器与其中一个编译器直接配合的方式工作。

当 JVM 执行代码时，它并不立即开始编译代码。首先，如果这段代码本身在将来只会被执行一次，那么从本质上看，编译就是在浪费精力。因为将代码翻译成 java 字节码相对于编译这段代码并执行代码来说，要快很多。第二个原因是最优化，当 JVM 执行某一方法或遍历循环的次数越多，就会更加了解代码结构，那么 JVM 在编译代码的时候就做出相应的优化。

在机器上，执行`java -version`命令就可以看到自己安装的JDK中JIT是哪种模式:

![javaversion](https://ws3.sinaimg.cn/large/006tNc79ly1fz6b4ix136j30ck02fmxb.jpg)

上图是我的机器上安装的jdk1.8，可以看到，他是Server Compile，但是，需要说明的是，无论是Client Complier还是Server Complier，解释器与编译器的搭配使用方式都是混合模式，即上图中的mixed mode。

#### 热点检测

上面我们说过，要想触发JIT，首先需要识别出热点代码。目前主要的热点代码识别方式是热点探测（Hot Spot Detection），有以下两种：

1、基于采样的方式探测（Sample Based Hot Spot Detection) ：周期性检测各个线程的栈顶，发现某个方法经常出现在栈顶，就认为是热点方法。好处就是简单，缺点就是无法精确确认一个方法的热度。容易受线程阻塞或别的原因干扰热点探测。

2、基于计数器的热点探测（Counter Based Hot Spot Detection)。采用这种方法的虚拟机会为每个方法，甚至是代码块建立计数器，统计方法的执行次数，某个方法超过阀值就认为是热点方法，触发JIT编译。

在HotSpot虚拟机中使用的是第二种——基于计数器的热点探测方法，因此它为每个方法准备了两个计数器：方法调用计数器和回边计数器。

方法计数器。顾名思义，就是记录一个方法被调用次数的计数器。

回边计数器。是记录方法中的for或者while的运行次数的计数器。

#### 编译优化

前面提到过，JIT除了具有缓存的功能外，还会对代码做各种优化。说到这里，不得不佩服HotSpot的开发者，他们在JIT中对于代码优化真的算是面面俱到了。

这里简答提及几个我觉得比较重要的优化技术，并不准备直接展开，读者感兴趣的话，我后面再写文章单独介绍。

逃逸分析、 锁消除、 锁膨胀、 方法内联、 空值检查消除、 类型检测消除、 公共子表达式消除



------

# 4. [对象和数组并不是都在堆上分配内存的](https://www.hollischuang.com/archives/2398)

前段时间，给星球的球友们专门码了一篇文章《深入分析Java的编译原理》，其中深入的介绍了Java中的javac编译和JIT编译的区别及原理。并在文中提到：JIT编译除了具有缓存的功能外，还会对代码做各种优化，比如：逃逸分析、 锁消除、 锁膨胀、 方法内联、 空值检查消除、 类型检测   消除、 公共子表达式消除等。

有球友阅读完这部分内容后，对JVM产生了浓厚的兴趣，自己回去专门学习了一下，在学习过程中遇到一个小问题，关于Java内存分配的。所以和我在微信上做过简单的交流。主要涉及到Java中的堆和栈、数组内存分配、逃逸分析、编译优化等技术及原理。本文也是关于这部分知识点的分享。

## 4.1 JVM内存分配策略

关于JVM的内存结构及内存分配方式，不是本文的重点，这里只做简单回顾。以下是我们知道的一些常识：

1、根据Java虚拟机规范，Java虚拟机所管理的内存包括方法区、虚拟机栈、本地方法栈、堆、程序计数器等。

2、我们通常认为JVM中运行时数据存储包括堆和栈。这里所提到的栈其实指的是虚拟机栈，或者说是虚拟栈中的局部变量表。

3、栈中存放一些基本类型的变量数据（int/short/long/byte/float/double/Boolean/char）和对象引用。

4、堆中主要存放对象，即通过new关键字创建的对象。

5、数组引用变量是存放在栈内存中，数组元素是存放在堆内存中。

在《深入理解Java虚拟机中》关于Java堆内存有这样一段描述：

但是，随着JIT编译期的发展与逃逸分析技术逐渐成熟，栈上分配、标量替换优化技术将会导致一些微妙的变化，所有的对象都分配到堆上也渐渐变得不那么“绝对”了。

这里只是简单提了一句，并没有深入分析，很多人看到这里由于对JIT、逃逸分析等技术不了解，所以也无法真正理解上面这段话的含义。

**PS：这里默认大家都了解什么是JIT，不了解的朋友可以先自行Google了解下，或者加入我的知识星球，阅读那篇球友专享文章。**

其实，在编译期间，JIT会对代码做很多优化。其中有一部分优化的目的就是减少内存堆分配压力，其中一种重要的技术叫做**逃逸分析**。

## 4.2 逃逸分析

逃逸分析(Escape Analysis)是目前Java虚拟机中比较前沿的优化技术。这是一种可以有效减少Java 程序中同步负载和内存堆分配压力的跨函数全局数据流分析算法。通过逃逸分析，Java Hotspot编译器能够分析出一个新的对象的引用的使用范围从而决定是否要将这个对象分配到堆上。

逃逸分析的基本行为就是分析对象动态作用域：**当一个对象在方法中被定义后，它可能被外部方法所引用，例如作为调用参数传递到其他地方中，称为方法逃逸。**

例如：

```java
public static StringBuffer craeteStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb;
}
```

StringBuffer sb是一个方法内部变量，上述代码中直接将sb返回，这样这个StringBuffer有可能被其他方法所改变，这样它的作用域就不只是在方法内部，虽然它是一个局部变量，称其逃逸到了方法外部。甚至还有可能被外部线程访问到，譬如赋值给类变量或可以在其他线程中访问的实例变量，称为线程逃逸。

上述代码如果想要StringBuffer sb不逃出方法，可以这样写：

```java
public static String createStringBuffer(String s1, String s2) {
    StringBuffer sb = new StringBuffer();
    sb.append(s1);
    sb.append(s2);
    return sb.toString();
}
```

不直接返回 StringBuffer，那么StringBuffer将不会逃逸出方法。

使用逃逸分析，编译器可以对代码做如下优化：

一、同步省略。如果一个对象被发现只能从一个线程被访问到，那么对于这个对象的操作可以不考虑同步。

二、将堆分配转化为栈分配。如果一个对象在子程序中被分配，要使指向该对象的指针永远不会逃逸，对象可能是栈分配的候选，而不是堆分配。

三、分离对象或标量替换。有的对象可能不需要作为一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在内存，而是存储在CPU寄存器中。

上面的关于同步省略的内容，我在《[深入理解多线程（五）—— Java虚拟机的锁优化技术](http://www.hollischuang.com/archives/2344)》中有介绍过，即锁优化中的锁消除技术，依赖的也是逃逸分析技术。

本文，主要来介绍逃逸分析的第二个用途：**将堆分配转化为栈分配**。

> 其实，以上三种优化中，栈上内存分配其实是依靠标量替换来实现的。由于不是本文重点，这里就不展开介绍了。如果大家感兴趣，我后面专门出一篇文章，全面介绍下逃逸分析。

在Java代码运行时，通过JVM参数可指定是否开启逃逸分析， `-XX:+DoEscapeAnalysis` ： 表示开启逃逸分析 `-XX:-DoEscapeAnalysis` ： 表示关闭逃逸分析 从jdk 1.7开始已经默认开始逃逸分析，如需关闭，需要指定`-XX:-DoEscapeAnalysis`

## 4.3 对象的栈上内存分配

我们知道，在一般情况下，对象和数组元素的内存分配是在堆内存上进行的。但是随着JIT编译器的日渐成熟，很多优化使这种分配策略并不绝对。JIT编译器就可以在编译期间根据逃逸分析的结果，来决定是否可以将对象的内存分配从堆转化为栈。

我们来看以下代码：

```java
public static void main(String[] args) {
    long a1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
        alloc();
    }
    // 查看执行时间
    long a2 = System.currentTimeMillis();
    System.out.println("cost " + (a2 - a1) + " ms");
    // 为了方便查看堆内存中对象个数，线程sleep
    try {
        Thread.sleep(100000);
    } catch (InterruptedException e1) {
        e1.printStackTrace();
    }
}

private static void alloc() {
    User user = new User();
}

static class User {

}
```

其实代码内容很简单，就是使用for循环，在代码中创建100万个User对象。

**我们在alloc方法中定义了User对象，但是并没有在方法外部引用他。也就是说，这个对象并不会逃逸到alloc外部。经过JIT的逃逸分析之后，就可以对其内存分配进行优化。**

我们指定以下JVM参数并运行：

```shell
-Xmx4G -Xms4G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError 
```

在程序打印出 `cost XX ms` 后，代码运行结束之前，我们使用`[jmap][1]`命令，来查看下当前堆内存中有多少个User对象：

```shell
➜  ~ jps
2809 StackAllocTest
2810 Jps
➜  ~ jmap -histo 2809

 num     #instances         #bytes  class name
----------------------------------------------
   1:           524       87282184  [I
   2:       1000000       16000000  StackAllocTest$User
   3:          6806        2093136  [B
   4:          8006        1320872  [C
   5:          4188         100512  java.lang.String
   6:           581          66304  java.lang.Class
```

从上面的jmap执行结果中我们可以看到，堆中共创建了100万个`StackAllocTest$User`实例。

在关闭逃避分析的情况下（-XX:-DoEscapeAnalysis），虽然在alloc方法中创建的User对象并没有逃逸到方法外部，但是还是被分配在堆内存中。也就说，如果没有JIT编译器优化，没有逃逸分析技术，正常情况下就应该是这样的。即所有对象都分配到堆内存中。

接下来，我们开启逃逸分析，再来执行下以上代码。

```shell
-Xmx4G -Xms4G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError 
```

在程序打印出 `cost XX ms` 后，代码运行结束之前，我们使用`jmap`命令，来查看下当前堆内存中有多少个User对象：

```shell
➜  ~ jps
709
2858 Launcher
2859 StackAllocTest
2860 Jps
➜  ~ jmap -histo 2859

 num     #instances         #bytes  class name
----------------------------------------------
   1:           524      101944280  [I
   2:          6806        2093136  [B
   3:         83619        1337904  StackAllocTest$User
   4:          8006        1320872  [C
   5:          4188         100512  java.lang.String
   6:           581          66304  java.lang.Class
```

从以上打印结果中可以发现，开启了逃逸分析之后（-XX:+DoEscapeAnalysis），在堆内存中只有8万多个`StackAllocTest$User`对象。也就是说在经过JIT优化之后，堆内存中分配的对象数量，从100万降到了8万。

> 除了以上通过jmap验证对象个数的方法以外，读者还可以尝试将堆内存调小，然后执行以上代码，根据GC的次数来分析，也能发现，开启了逃逸分析之后，在运行期间，GC次数会明显减少。正是因为很多堆上分配被优化成了栈上分配，所以GC次数有了明显的减少。

## 4.4 总结

所以，如果以后再有人问你：是不是所有的对象和数组都会在堆内存分配空间？

那么你可以告诉他：不一定，随着JIT编译器的发展，在编译期间，如果JIT经过逃逸分析，发现有些对象没有逃逸出方法，那么有可能堆内存分配会被优化成栈内存分配。但是这也并不是绝对的。就像我们前面看到的一样，在开启逃逸分析之后，也并不是所有User对象都没有在堆上分配。



# 5. [深入理解多线程（五）—— Java虚拟机的锁优化技术](http://www.hollischuang.com/archives/2344)（锁消除部分）

第五部分，锁消除部分。

 [深入理解多线程.md](深入理解多线程.md) 































