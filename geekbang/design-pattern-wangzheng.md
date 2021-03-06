<!--ts-->

   * [前言](#\xE5\x89\x8D\xE8\xA8\x80)
   * [设计模式导读](#\xE8\xAE\xBE\xE8\xAE\xA1\xE6\xA8\xA1\xE5\xBC\x8F\xE5\xAF\xBC\xE8\xAF\xBB)
      * [01 | 为什么说每个程序员都要尽早地学习并掌握设计模式相关知识？](#01--\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE8\xAF\xB4\xE6\xAF\x8F\xE4\xB8\xAA\xE7\xA8\x8B\xE5\xBA\x8F\xE5\x91\x98\xE9\x83\xBD\xE8\xA6\x81\xE5\xB0\xBD\xE6\x97\xA9\xE5\x9C\xB0\xE5\xAD\xA6\xE4\xB9\xA0\xE5\xB9\xB6\xE6\x8E\x8C\xE6\x8F\xA1\xE8\xAE\xBE\xE8\xAE\xA1\xE6\xA8\xA1\xE5\xBC\x8F\xE7\x9B\xB8\xE5\x85\xB3\xE7\x9F\xA5\xE8\xAF\x86)
   * [设计原则与思想：面向对象](#\xE8\xAE\xBE\xE8\xAE\xA1\xE5\x8E\x9F\xE5\x88\x99\xE4\xB8\x8E\xE6\x80\x9D\xE6\x83\xB3\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1)
      * [04 | 理论一：当谈论面向对象的时候，我们到底在谈论什么？](#04--\xE7\x90\x86\xE8\xAE\xBA\xE4\xB8\x80\xE5\xBD\x93\xE8\xB0\x88\xE8\xAE\xBA\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\x9A\x84\xE6\x97\xB6\xE5\x80\x99\xE6\x88\x91\xE4\xBB\xAC\xE5\x88\xB0\xE5\xBA\x95\xE5\x9C\xA8\xE8\xB0\x88\xE8\xAE\xBA\xE4\xBB\x80\xE4\xB9\x88)
         * [什么是面向对象编程和面向对象编程语言？](#\xE4\xBB\x80\xE4\xB9\x88\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\xBC\x96\xE7\xA8\x8B\xE5\x92\x8C\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\xBC\x96\xE7\xA8\x8B\xE8\xAF\xAD\xE8\xA8\x80)
         * [如何判定某编程语言是否是面向对象编程语言？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\x88\xA4\xE5\xAE\x9A\xE6\x9F\x90\xE7\xBC\x96\xE7\xA8\x8B\xE8\xAF\xAD\xE8\xA8\x80\xE6\x98\xAF\xE5\x90\xA6\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\xBC\x96\xE7\xA8\x8B\xE8\xAF\xAD\xE8\xA8\x80)
         * [什么是面向对象分析和面向对象设计？](#\xE4\xBB\x80\xE4\xB9\x88\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE5\x88\x86\xE6\x9E\x90\xE5\x92\x8C\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE8\xAE\xBE\xE8\xAE\xA1)
         * [什么是 UML？我们是否需要 UML？](#\xE4\xBB\x80\xE4\xB9\x88\xE6\x98\xAF-uml\xE6\x88\x91\xE4\xBB\xAC\xE6\x98\xAF\xE5\x90\xA6\xE9\x9C\x80\xE8\xA6\x81-uml)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93)
      * [05 | 理论二：封装、抽象、继承、多态分别可以解决哪些编程问题？](#05--\xE7\x90\x86\xE8\xAE\xBA\xE4\xBA\x8C\xE5\xB0\x81\xE8\xA3\x85\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xBB\xA7\xE6\x89\xBF\xE5\xA4\x9A\xE6\x80\x81\xE5\x88\x86\xE5\x88\xAB\xE5\x8F\xAF\xE4\xBB\xA5\xE8\xA7\xA3\xE5\x86\xB3\xE5\x93\xAA\xE4\xBA\x9B\xE7\xBC\x96\xE7\xA8\x8B\xE9\x97\xAE\xE9\xA2\x98)
         * [封装（Encapsulation）](#\xE5\xB0\x81\xE8\xA3\x85encapsulation)
         * [抽象（Abstraction）](#\xE6\x8A\xBD\xE8\xB1\xA1abstraction)
         * [继承（Inheritance）](#\xE7\xBB\xA7\xE6\x89\xBFinheritance)
         * [多态（Polymorphism）](#\xE5\xA4\x9A\xE6\x80\x81polymorphism)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93-1)
      * [06 | 理论三：面向对象相比面向过程有哪些优势？面向过程真的过时了吗？](#06--\xE7\x90\x86\xE8\xAE\xBA\xE4\xB8\x89\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\x9B\xB8\xE6\xAF\x94\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE6\x9C\x89\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBC\x98\xE5\x8A\xBF\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\x9C\x9F\xE7\x9A\x84\xE8\xBF\x87\xE6\x97\xB6\xE4\xBA\x86\xE5\x90\x97)
         * [什么是面向过程编程与面向过程编程语言？](#\xE4\xBB\x80\xE4\xB9\x88\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\xBC\x96\xE7\xA8\x8B\xE4\xB8\x8E\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\xBC\x96\xE7\xA8\x8B\xE8\xAF\xAD\xE8\xA8\x80)
         * [面向对象编程相比面向过程编程有哪些优势？](#\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\xBC\x96\xE7\xA8\x8B\xE7\x9B\xB8\xE6\xAF\x94\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\xBC\x96\xE7\xA8\x8B\xE6\x9C\x89\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBC\x98\xE5\x8A\xBF)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93-2)
      * [07 | 理论四：哪些代码设计看似是面向对象，实际是面向过程的？](#07--\xE7\x90\x86\xE8\xAE\xBA\xE5\x9B\x9B\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBB\xA3\xE7\xA0\x81\xE8\xAE\xBE\xE8\xAE\xA1\xE7\x9C\x8B\xE4\xBC\xBC\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE5\xAE\x9E\xE9\x99\x85\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\x9A\x84)
         * [哪些代码设计看似是面向对象，实际是面向过程的？](#\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBB\xA3\xE7\xA0\x81\xE8\xAE\xBE\xE8\xAE\xA1\xE7\x9C\x8B\xE4\xBC\xBC\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE5\xAE\x9E\xE9\x99\x85\xE6\x98\xAF\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\x9A\x84)
         * [在面向对象编程中，为什么容易写出面向过程风格的代码？](#\xE5\x9C\xA8\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE7\xBC\x96\xE7\xA8\x8B\xE4\xB8\xAD\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE5\xAE\xB9\xE6\x98\x93\xE5\x86\x99\xE5\x87\xBA\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE9\xA3\x8E\xE6\xA0\xBC\xE7\x9A\x84\xE4\xBB\xA3\xE7\xA0\x81)
         * [面向过程编程及面向过程编程语言就真的无用武之地了吗？](#\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\xBC\x96\xE7\xA8\x8B\xE5\x8F\x8A\xE9\x9D\xA2\xE5\x90\x91\xE8\xBF\x87\xE7\xA8\x8B\xE7\xBC\x96\xE7\xA8\x8B\xE8\xAF\xAD\xE8\xA8\x80\xE5\xB0\xB1\xE7\x9C\x9F\xE7\x9A\x84\xE6\x97\xA0\xE7\x94\xA8\xE6\xAD\xA6\xE4\xB9\x8B\xE5\x9C\xB0\xE4\xBA\x86\xE5\x90\x97)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93-3)
      * [08 | 理论五：接口vs抽象类的区别？如何用普通的类模拟抽象类和接口？](#08--\xE7\x90\x86\xE8\xAE\xBA\xE4\xBA\x94\xE6\x8E\xA5\xE5\x8F\xA3vs\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xB1\xBB\xE7\x9A\x84\xE5\x8C\xBA\xE5\x88\xAB\xE5\xA6\x82\xE4\xBD\x95\xE7\x94\xA8\xE6\x99\xAE\xE9\x80\x9A\xE7\x9A\x84\xE7\xB1\xBB\xE6\xA8\xA1\xE6\x8B\x9F\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xB1\xBB\xE5\x92\x8C\xE6\x8E\xA5\xE5\x8F\xA3)
         * [什么是抽象类和接口？区别在哪里？](#\xE4\xBB\x80\xE4\xB9\x88\xE6\x98\xAF\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xB1\xBB\xE5\x92\x8C\xE6\x8E\xA5\xE5\x8F\xA3\xE5\x8C\xBA\xE5\x88\xAB\xE5\x9C\xA8\xE5\x93\xAA\xE9\x87\x8C)
         * [抽象类和接口能解决什么编程问题？](#\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xB1\xBB\xE5\x92\x8C\xE6\x8E\xA5\xE5\x8F\xA3\xE8\x83\xBD\xE8\xA7\xA3\xE5\x86\xB3\xE4\xBB\x80\xE4\xB9\x88\xE7\xBC\x96\xE7\xA8\x8B\xE9\x97\xAE\xE9\xA2\x98)
         * [如何决定该用抽象类还是接口？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\x86\xB3\xE5\xAE\x9A\xE8\xAF\xA5\xE7\x94\xA8\xE6\x8A\xBD\xE8\xB1\xA1\xE7\xB1\xBB\xE8\xBF\x98\xE6\x98\xAF\xE6\x8E\xA5\xE5\x8F\xA3)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93-4)
      * [09 | 理论六：为什么基于接口而非实现编程？有必要为每个类都定义接口吗？](#09--\xE7\x90\x86\xE8\xAE\xBA\xE5\x85\xAD\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE5\x9F\xBA\xE4\xBA\x8E\xE6\x8E\xA5\xE5\x8F\xA3\xE8\x80\x8C\xE9\x9D\x9E\xE5\xAE\x9E\xE7\x8E\xB0\xE7\xBC\x96\xE7\xA8\x8B\xE6\x9C\x89\xE5\xBF\x85\xE8\xA6\x81\xE4\xB8\xBA\xE6\xAF\x8F\xE4\xB8\xAA\xE7\xB1\xBB\xE9\x83\xBD\xE5\xAE\x9A\xE4\xB9\x89\xE6\x8E\xA5\xE5\x8F\xA3\xE5\x90\x97)
         * [如何解读原则中的“接口”二字？](#\xE5\xA6\x82\xE4\xBD\x95\xE8\xA7\xA3\xE8\xAF\xBB\xE5\x8E\x9F\xE5\x88\x99\xE4\xB8\xAD\xE7\x9A\x84\xE6\x8E\xA5\xE5\x8F\xA3\xE4\xBA\x8C\xE5\xAD\x97)
         * [如何将这条原则应用到实战中？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\xB0\x86\xE8\xBF\x99\xE6\x9D\xA1\xE5\x8E\x9F\xE5\x88\x99\xE5\xBA\x94\xE7\x94\xA8\xE5\x88\xB0\xE5\xAE\x9E\xE6\x88\x98\xE4\xB8\xAD)
         * [是否需要为每个类定义接口？](#\xE6\x98\xAF\xE5\x90\xA6\xE9\x9C\x80\xE8\xA6\x81\xE4\xB8\xBA\xE6\xAF\x8F\xE4\xB8\xAA\xE7\xB1\xBB\xE5\xAE\x9A\xE4\xB9\x89\xE6\x8E\xA5\xE5\x8F\xA3)
         * [总结](#\xE6\x80\xBB\xE7\xBB\x93-5)
      * [10 | 理论七：为何说要多用组合少用继承？如何决定该用组合还是继承？](#10--\xE7\x90\x86\xE8\xAE\xBA\xE4\xB8\x83\xE4\xB8\xBA\xE4\xBD\x95\xE8\xAF\xB4\xE8\xA6\x81\xE5\xA4\x9A\xE7\x94\xA8\xE7\xBB\x84\xE5\x90\x88\xE5\xB0\x91\xE7\x94\xA8\xE7\xBB\xA7\xE6\x89\xBF\xE5\xA6\x82\xE4\xBD\x95\xE5\x86\xB3\xE5\xAE\x9A\xE8\xAF\xA5\xE7\x94\xA8\xE7\xBB\x84\xE5\x90\x88\xE8\xBF\x98\xE6\x98\xAF\xE7\xBB\xA7\xE6\x89\xBF)
         * [为什么不推荐使用继承？](#\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE4\xB8\x8D\xE6\x8E\xA8\xE8\x8D\x90\xE4\xBD\xBF\xE7\x94\xA8\xE7\xBB\xA7\xE6\x89\xBF)
      * [11. 实战一（上）：业务开发常用的基于贫血模型的MVC架构违背OOP吗？](#11-\xE5\xAE\x9E\xE6\x88\x98\xE4\xB8\x80\xE4\xB8\x8A\xE4\xB8\x9A\xE5\x8A\xA1\xE5\xBC\x80\xE5\x8F\x91\xE5\xB8\xB8\xE7\x94\xA8\xE7\x9A\x84\xE5\x9F\xBA\xE4\xBA\x8E\xE8\xB4\xAB\xE8\xA1\x80\xE6\xA8\xA1\xE5\x9E\x8B\xE7\x9A\x84mvc\xE6\x9E\xB6\xE6\x9E\x84\xE8\xBF\x9D\xE8\x83\x8Coop\xE5\x90\x97)
      * [12 | 实战一（下）：如何利用基于充血模型的DDD开发一个虚拟钱包系统？](#12--\xE5\xAE\x9E\xE6\x88\x98\xE4\xB8\x80\xE4\xB8\x8B\xE5\xA6\x82\xE4\xBD\x95\xE5\x88\xA9\xE7\x94\xA8\xE5\x9F\xBA\xE4\xBA\x8E\xE5\x85\x85\xE8\xA1\x80\xE6\xA8\xA1\xE5\x9E\x8B\xE7\x9A\x84ddd\xE5\xBC\x80\xE5\x8F\x91\xE4\xB8\x80\xE4\xB8\xAA\xE8\x99\x9A\xE6\x8B\x9F\xE9\x92\xB1\xE5\x8C\x85\xE7\xB3\xBB\xE7\xBB\x9F)
      * [13 | 实战二（上）：如何对接口鉴权这样一个功能开发做面向对象分析？](#13--\xE5\xAE\x9E\xE6\x88\x98\xE4\xBA\x8C\xE4\xB8\x8A\xE5\xA6\x82\xE4\xBD\x95\xE5\xAF\xB9\xE6\x8E\xA5\xE5\x8F\xA3\xE9\x89\xB4\xE6\x9D\x83\xE8\xBF\x99\xE6\xA0\xB7\xE4\xB8\x80\xE4\xB8\xAA\xE5\x8A\x9F\xE8\x83\xBD\xE5\xBC\x80\xE5\x8F\x91\xE5\x81\x9A\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE5\x88\x86\xE6\x9E\x90)
      * [14 | 实战二（下）：如何利用面向对象设计和编程开发接口鉴权功能？](#14--\xE5\xAE\x9E\xE6\x88\x98\xE4\xBA\x8C\xE4\xB8\x8B\xE5\xA6\x82\xE4\xBD\x95\xE5\x88\xA9\xE7\x94\xA8\xE9\x9D\xA2\xE5\x90\x91\xE5\xAF\xB9\xE8\xB1\xA1\xE8\xAE\xBE\xE8\xAE\xA1\xE5\x92\x8C\xE7\xBC\x96\xE7\xA8\x8B\xE5\xBC\x80\xE5\x8F\x91\xE6\x8E\xA5\xE5\x8F\xA3\xE9\x89\xB4\xE6\x9D\x83\xE5\x8A\x9F\xE8\x83\xBD)
   * [设计原则与思想：设计原则](#\xE8\xAE\xBE\xE8\xAE\xA1\xE5\x8E\x9F\xE5\x88\x99\xE4\xB8\x8E\xE6\x80\x9D\xE6\x83\xB3\xE8\xAE\xBE\xE8\xAE\xA1\xE5\x8E\x9F\xE5\x88\x99)
      * [15 | 理论一：对于单一职责原则，如何判定某个类的职责是否够“单一”？](#15--\xE7\x90\x86\xE8\xAE\xBA\xE4\xB8\x80\xE5\xAF\xB9\xE4\xBA\x8E\xE5\x8D\x95\xE4\xB8\x80\xE8\x81\x8C\xE8\xB4\xA3\xE5\x8E\x9F\xE5\x88\x99\xE5\xA6\x82\xE4\xBD\x95\xE5\x88\xA4\xE5\xAE\x9A\xE6\x9F\x90\xE4\xB8\xAA\xE7\xB1\xBB\xE7\x9A\x84\xE8\x81\x8C\xE8\xB4\xA3\xE6\x98\xAF\xE5\x90\xA6\xE5\xA4\x9F\xE5\x8D\x95\xE4\xB8\x80)
         * [如何理解单一职责原则（SRP）？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3\xE5\x8D\x95\xE4\xB8\x80\xE8\x81\x8C\xE8\xB4\xA3\xE5\x8E\x9F\xE5\x88\x99srp)
         * [如何判断类的职责是否足够单一？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\x88\xA4\xE6\x96\xAD\xE7\xB1\xBB\xE7\x9A\x84\xE8\x81\x8C\xE8\xB4\xA3\xE6\x98\xAF\xE5\x90\xA6\xE8\xB6\xB3\xE5\xA4\x9F\xE5\x8D\x95\xE4\xB8\x80)
         * [类的职责是否设计得越单一越好？](#\xE7\xB1\xBB\xE7\x9A\x84\xE8\x81\x8C\xE8\xB4\xA3\xE6\x98\xAF\xE5\x90\xA6\xE8\xAE\xBE\xE8\xAE\xA1\xE5\xBE\x97\xE8\xB6\x8A\xE5\x8D\x95\xE4\xB8\x80\xE8\xB6\x8A\xE5\xA5\xBD)
      * [16 | 理论二：如何做到“对扩展开放、修改关闭”？扩展和修改各指什么？](#16--\xE7\x90\x86\xE8\xAE\xBA\xE4\xBA\x8C\xE5\xA6\x82\xE4\xBD\x95\xE5\x81\x9A\xE5\x88\xB0\xE5\xAF\xB9\xE6\x89\xA9\xE5\xB1\x95\xE5\xBC\x80\xE6\x94\xBE\xE4\xBF\xAE\xE6\x94\xB9\xE5\x85\xB3\xE9\x97\xAD\xE6\x89\xA9\xE5\xB1\x95\xE5\x92\x8C\xE4\xBF\xAE\xE6\x94\xB9\xE5\x90\x84\xE6\x8C\x87\xE4\xBB\x80\xE4\xB9\x88)
         * [如何理解“对扩展开放、修改关闭”？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3\xE5\xAF\xB9\xE6\x89\xA9\xE5\xB1\x95\xE5\xBC\x80\xE6\x94\xBE\xE4\xBF\xAE\xE6\x94\xB9\xE5\x85\xB3\xE9\x97\xAD)
         * [修改代码就意味着违背开闭原则吗？](#\xE4\xBF\xAE\xE6\x94\xB9\xE4\xBB\xA3\xE7\xA0\x81\xE5\xB0\xB1\xE6\x84\x8F\xE5\x91\xB3\xE7\x9D\x80\xE8\xBF\x9D\xE8\x83\x8C\xE5\xBC\x80\xE9\x97\xAD\xE5\x8E\x9F\xE5\x88\x99\xE5\x90\x97)
         * [如何做到“对扩展开放、修改关闭”？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\x81\x9A\xE5\x88\xB0\xE5\xAF\xB9\xE6\x89\xA9\xE5\xB1\x95\xE5\xBC\x80\xE6\x94\xBE\xE4\xBF\xAE\xE6\x94\xB9\xE5\x85\xB3\xE9\x97\xAD)
         * [如何在项目中灵活应用开闭原则？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\x9C\xA8\xE9\xA1\xB9\xE7\x9B\xAE\xE4\xB8\xAD\xE7\x81\xB5\xE6\xB4\xBB\xE5\xBA\x94\xE7\x94\xA8\xE5\xBC\x80\xE9\x97\xAD\xE5\x8E\x9F\xE5\x88\x99)
      * [17 | 理论三：里式替换（LSP）跟多态有何区别？哪些代码违背了LSP？](#17--\xE7\x90\x86\xE8\xAE\xBA\xE4\xB8\x89\xE9\x87\x8C\xE5\xBC\x8F\xE6\x9B\xBF\xE6\x8D\xA2lsp\xE8\xB7\x9F\xE5\xA4\x9A\xE6\x80\x81\xE6\x9C\x89\xE4\xBD\x95\xE5\x8C\xBA\xE5\x88\xAB\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBB\xA3\xE7\xA0\x81\xE8\xBF\x9D\xE8\x83\x8C\xE4\xBA\x86lsp)
         * [如何理解“里式替换原则”？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3\xE9\x87\x8C\xE5\xBC\x8F\xE6\x9B\xBF\xE6\x8D\xA2\xE5\x8E\x9F\xE5\x88\x99)
         * [哪些代码明显违背了 LSP？](#\xE5\x93\xAA\xE4\xBA\x9B\xE4\xBB\xA3\xE7\xA0\x81\xE6\x98\x8E\xE6\x98\xBE\xE8\xBF\x9D\xE8\x83\x8C\xE4\xBA\x86-lsp)
      * [18 | 理论四：接口隔离原则有哪三种应用？原则中的“接口”该如何理解？](#18--\xE7\x90\x86\xE8\xAE\xBA\xE5\x9B\x9B\xE6\x8E\xA5\xE5\x8F\xA3\xE9\x9A\x94\xE7\xA6\xBB\xE5\x8E\x9F\xE5\x88\x99\xE6\x9C\x89\xE5\x93\xAA\xE4\xB8\x89\xE7\xA7\x8D\xE5\xBA\x94\xE7\x94\xA8\xE5\x8E\x9F\xE5\x88\x99\xE4\xB8\xAD\xE7\x9A\x84\xE6\x8E\xA5\xE5\x8F\xA3\xE8\xAF\xA5\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3)
         * [如何理解“接口隔离原则”？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3\xE6\x8E\xA5\xE5\x8F\xA3\xE9\x9A\x94\xE7\xA6\xBB\xE5\x8E\x9F\xE5\x88\x99)
         * [把“接口”理解为一组 API 接口集合](#\xE6\x8A\x8A\xE6\x8E\xA5\xE5\x8F\xA3\xE7\x90\x86\xE8\xA7\xA3\xE4\xB8\xBA\xE4\xB8\x80\xE7\xBB\x84-api-\xE6\x8E\xA5\xE5\x8F\xA3\xE9\x9B\x86\xE5\x90\x88)
         * [把“接口”理解为单个 API 接口或函数](#\xE6\x8A\x8A\xE6\x8E\xA5\xE5\x8F\xA3\xE7\x90\x86\xE8\xA7\xA3\xE4\xB8\xBA\xE5\x8D\x95\xE4\xB8\xAA-api-\xE6\x8E\xA5\xE5\x8F\xA3\xE6\x88\x96\xE5\x87\xBD\xE6\x95\xB0)
         * [把“接口”理解为 OOP 中的接口概念](#\xE6\x8A\x8A\xE6\x8E\xA5\xE5\x8F\xA3\xE7\x90\x86\xE8\xA7\xA3\xE4\xB8\xBA-oop-\xE4\xB8\xAD\xE7\x9A\x84\xE6\x8E\xA5\xE5\x8F\xA3\xE6\xA6\x82\xE5\xBF\xB5)
      * [19 | 理论五：控制反转、依赖反转、依赖注入，这三者有何区别和联系？](#19--\xE7\x90\x86\xE8\xAE\xBA\xE4\xBA\x94\xE6\x8E\xA7\xE5\x88\xB6\xE5\x8F\x8D\xE8\xBD\xAC\xE4\xBE\x9D\xE8\xB5\x96\xE5\x8F\x8D\xE8\xBD\xAC\xE4\xBE\x9D\xE8\xB5\x96\xE6\xB3\xA8\xE5\x85\xA5\xE8\xBF\x99\xE4\xB8\x89\xE8\x80\x85\xE6\x9C\x89\xE4\xBD\x95\xE5\x8C\xBA\xE5\x88\xAB\xE5\x92\x8C\xE8\x81\x94\xE7\xB3\xBB)
         * [控制反转（IOC）](#\xE6\x8E\xA7\xE5\x88\xB6\xE5\x8F\x8D\xE8\xBD\xACioc)
         * [依赖注入（DI）](#\xE4\xBE\x9D\xE8\xB5\x96\xE6\xB3\xA8\xE5\x85\xA5di)
         * [依赖注入框架（DI Framework）](#\xE4\xBE\x9D\xE8\xB5\x96\xE6\xB3\xA8\xE5\x85\xA5\xE6\xA1\x86\xE6\x9E\xB6di-framework)
         * [依赖反转原则（DIP）](#\xE4\xBE\x9D\xE8\xB5\x96\xE5\x8F\x8D\xE8\xBD\xAC\xE5\x8E\x9F\xE5\x88\x99dip)
      * [20 | 理论六：我为何说KISS、YAGNI原则看似简单，却经常被用错？](#20--\xE7\x90\x86\xE8\xAE\xBA\xE5\x85\xAD\xE6\x88\x91\xE4\xB8\xBA\xE4\xBD\x95\xE8\xAF\xB4kissyagni\xE5\x8E\x9F\xE5\x88\x99\xE7\x9C\x8B\xE4\xBC\xBC\xE7\xAE\x80\xE5\x8D\x95\xE5\x8D\xB4\xE7\xBB\x8F\xE5\xB8\xB8\xE8\xA2\xAB\xE7\x94\xA8\xE9\x94\x99)
         * [如何理解“KISS 原则”？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3kiss-\xE5\x8E\x9F\xE5\x88\x99)
   * [设计模式与范式：创建型 (7讲)](#\xE8\xAE\xBE\xE8\xAE\xA1\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\x8E\xE8\x8C\x83\xE5\xBC\x8F\xE5\x88\x9B\xE5\xBB\xBA\xE5\x9E\x8B-7\xE8\xAE\xB2)
      * [41 | 单例模式（上）：为什么说支持懒加载的双重检测不比饿汉式更优？](#41--\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\x8A\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE8\xAF\xB4\xE6\x94\xAF\xE6\x8C\x81\xE6\x87\x92\xE5\x8A\xA0\xE8\xBD\xBD\xE7\x9A\x84\xE5\x8F\x8C\xE9\x87\x8D\xE6\xA3\x80\xE6\xB5\x8B\xE4\xB8\x8D\xE6\xAF\x94\xE9\xA5\xBF\xE6\xB1\x89\xE5\xBC\x8F\xE6\x9B\xB4\xE4\xBC\x98)
         * [为什么要使用单例？](#\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE8\xA6\x81\xE4\xBD\xBF\xE7\x94\xA8\xE5\x8D\x95\xE4\xBE\x8B)
         * [如何实现一个单例？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\xAE\x9E\xE7\x8E\xB0\xE4\xB8\x80\xE4\xB8\xAA\xE5\x8D\x95\xE4\xBE\x8B)
      * [42 | 单例模式（中）：我为什么不推荐使用单例模式？又有何替代方案？](#42--\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\xAD\xE6\x88\x91\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE4\xB8\x8D\xE6\x8E\xA8\xE8\x8D\x90\xE4\xBD\xBF\xE7\x94\xA8\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F\xE5\x8F\x88\xE6\x9C\x89\xE4\xBD\x95\xE6\x9B\xBF\xE4\xBB\xA3\xE6\x96\xB9\xE6\xA1\x88)
         * [单例存在哪些问题?](#\xE5\x8D\x95\xE4\xBE\x8B\xE5\xAD\x98\xE5\x9C\xA8\xE5\x93\xAA\xE4\xBA\x9B\xE9\x97\xAE\xE9\xA2\x98)
         * [有何替代解决方案？](#\xE6\x9C\x89\xE4\xBD\x95\xE6\x9B\xBF\xE4\xBB\xA3\xE8\xA7\xA3\xE5\x86\xB3\xE6\x96\xB9\xE6\xA1\x88)
      * [43 | 单例模式（下）：如何设计实现一个集群环境下的分布式单例模式？](#43--\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\x8B\xE5\xA6\x82\xE4\xBD\x95\xE8\xAE\xBE\xE8\xAE\xA1\xE5\xAE\x9E\xE7\x8E\xB0\xE4\xB8\x80\xE4\xB8\xAA\xE9\x9B\x86\xE7\xBE\xA4\xE7\x8E\xAF\xE5\xA2\x83\xE4\xB8\x8B\xE7\x9A\x84\xE5\x88\x86\xE5\xB8\x83\xE5\xBC\x8F\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F)
         * [如何理解单例模式中的唯一性？](#\xE5\xA6\x82\xE4\xBD\x95\xE7\x90\x86\xE8\xA7\xA3\xE5\x8D\x95\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\xAD\xE7\x9A\x84\xE5\x94\xAF\xE4\xB8\x80\xE6\x80\xA7)
         * [如何实现线程唯一的单例？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\xAE\x9E\xE7\x8E\xB0\xE7\xBA\xBF\xE7\xA8\x8B\xE5\x94\xAF\xE4\xB8\x80\xE7\x9A\x84\xE5\x8D\x95\xE4\xBE\x8B)
         * [如何实现集群环境下的单例？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\xAE\x9E\xE7\x8E\xB0\xE9\x9B\x86\xE7\xBE\xA4\xE7\x8E\xAF\xE5\xA2\x83\xE4\xB8\x8B\xE7\x9A\x84\xE5\x8D\x95\xE4\xBE\x8B)
         * [如何实现一个多例模式？](#\xE5\xA6\x82\xE4\xBD\x95\xE5\xAE\x9E\xE7\x8E\xB0\xE4\xB8\x80\xE4\xB8\xAA\xE5\xA4\x9A\xE4\xBE\x8B\xE6\xA8\xA1\xE5\xBC\x8F)
      * [44 | 工厂模式（上）：我为什么说没事不要随便用工厂模式创建对象？](#44--\xE5\xB7\xA5\xE5\x8E\x82\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\x8A\xE6\x88\x91\xE4\xB8\xBA\xE4\xBB\x80\xE4\xB9\x88\xE8\xAF\xB4\xE6\xB2\xA1\xE4\xBA\x8B\xE4\xB8\x8D\xE8\xA6\x81\xE9\x9A\x8F\xE4\xBE\xBF\xE7\x94\xA8\xE5\xB7\xA5\xE5\x8E\x82\xE6\xA8\xA1\xE5\xBC\x8F\xE5\x88\x9B\xE5\xBB\xBA\xE5\xAF\xB9\xE8\xB1\xA1)
         * [简单工厂（Simple Factory）](#\xE7\xAE\x80\xE5\x8D\x95\xE5\xB7\xA5\xE5\x8E\x82simple-factory)
         * [工厂方法（Factory Method）](#\xE5\xB7\xA5\xE5\x8E\x82\xE6\x96\xB9\xE6\xB3\x95factory-method)
         * [抽象工厂（Abstract Factory）](#\xE6\x8A\xBD\xE8\xB1\xA1\xE5\xB7\xA5\xE5\x8E\x82abstract-factory)
      * [45 | 工厂模式（下）：如何设计实现一个Dependency Injection框架？](#45--\xE5\xB7\xA5\xE5\x8E\x82\xE6\xA8\xA1\xE5\xBC\x8F\xE4\xB8\x8B\xE5\xA6\x82\xE4\xBD\x95\xE8\xAE\xBE\xE8\xAE\xA1\xE5\xAE\x9E\xE7\x8E\xB0\xE4\xB8\x80\xE4\xB8\xAAdependency-injection\xE6\xA1\x86\xE6\x9E\xB6)
         * [工厂模式和 DI 容器有何区别？](#\xE5\xB7\xA5\xE5\x8E\x82\xE6\xA8\xA1\xE5\xBC\x8F\xE5\x92\x8C-di-\xE5\xAE\xB9\xE5\x99\xA8\xE6\x9C\x89\xE4\xBD\x95\xE5\x8C\xBA\xE5\x88\xAB)
         * [DI 容器的核心功能有哪些？](#di-\xE5\xAE\xB9\xE5\x99\xA8\xE7\x9A\x84\xE6\xA0\xB8\xE5\xBF\x83\xE5\x8A\x9F\xE8\x83\xBD\xE6\x9C\x89\xE5\x93\xAA\xE4\xBA\x9B)
   * [开源与项目实战：开源实战 (14讲)](#\xE5\xBC\x80\xE6\xBA\x90\xE4\xB8\x8E\xE9\xA1\xB9\xE7\x9B\xAE\xE5\xAE\x9E\xE6\x88\x98\xE5\xBC\x80\xE6\xBA\x90\xE5\xAE\x9E\xE6\x88\x98-14\xE8\xAE\xB2)
      * [76 | 开源实战一（上）：通过剖析Java JDK源码学习灵活应用设计模式](#76--\xE5\xBC\x80\xE6\xBA\x90\xE5\xAE\x9E\xE6\x88\x98\xE4\xB8\x80\xE4\xB8\x8A\xE9\x80\x9A\xE8\xBF\x87\xE5\x89\x96\xE6\x9E\x90java-jdk\xE6\xBA\x90\xE7\xA0\x81\xE5\xAD\xA6\xE4\xB9\xA0\xE7\x81\xB5\xE6\xB4\xBB\xE5\xBA\x94\xE7\x94\xA8\xE8\xAE\xBE\xE8\xAE\xA1\xE6\xA8\xA1\xE5\xBC\x8F)
         * [工厂模式在 Calendar 类中的应用](#\xE5\xB7\xA5\xE5\x8E\x82\xE6\xA8\xA1\xE5\xBC\x8F\xE5\x9C\xA8-calendar-\xE7\xB1\xBB\xE4\xB8\xAD\xE7\x9A\x84\xE5\xBA\x94\xE7\x94\xA8)

<!-- Added by: haojunsheng, at: 2020年12月15日 星期二 17时21分11秒 CST -->

<!--te-->

# 前言

![img](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201111000230.png)

# 设计模式导读

## 01 | 为什么说每个程序员都要尽早地学习并掌握设计模式相关知识？



# 设计原则与思想：面向对象

## 04 | 理论一：当谈论面向对象的时候，我们到底在谈论什么？

在第一个模块中，我们又首先会讲到面向对象相关的理论知识。提到面向对象，我相信很多人都不陌生，随口都可以说出面向对象的四大特性：封装、抽象、继承、多态。实际上，面向对象这个概念包含的内容还不止这些。

### 什么是面向对象编程和面向对象编程语言？

面向对象编程的英文缩写是 OOP，全称是 Object Oriented Programming。对应地，面向对象编程语言的英文缩写是 OOPL，全称是 Object Oriented Programming Language。

面向对象编程中有两个非常重要、非常基础的概念，那就是类（class）和对象（object）。这两个概念最早出现在 1960 年，在 Simula 这种编程语言中第一次使用。而面向对象编程这个概念第一次被使用是在 Smalltalk 这种编程语言中。Smalltalk 被认为是第一个真正意义上的面向对象编程语言。

1980 年左右，C++ 的出现，带动了面向对象编程的流行，也使得面向对象编程被越来越多的人认可。直到今天，如果不按照严格的定义来说，大部分编程语言都是面向对象编程语言，比如 Java、C++、Go、Python、C#、Ruby、JavaScript、Objective-C、Scala、PHP、Perl 等等。除此之外，大部分程序员在开发项目的时候，都是基于面向对象编程语言进行的面向对象编程。

以上是面向对象编程的大概发展历史。在刚刚的描述中，我着重提到了两个概念，面向对象编程和面向对象编程语言。那究竟什么是面向对象编程？什么语言才算是面向对象编程语言呢？如果非得给出一个定义的话，我觉得可以用下面两句话来概括。

- 面向对象编程是一种编程范式或编程风格。它以类或对象作为组织代码的基本单元，并将封装、抽象、继承、多态四个特性，作为代码设计和实现的基石 。
- 面向对象编程语言是支持类或对象的语法机制，并有现成的语法机制，能方便地实现面向对象编程四大特性（封装、抽象、继承、多态）的编程语言。

一般来讲， 面向对象编程都是通过使用面向对象编程语言来进行的，但是，不用面向对象编程语言，我们照样可以进行面向对象编程。反过来讲，即便我们使用面向对象编程语言，写出来的代码也不一定是面向对象编程风格的，也有可能是面向过程编程风格的。这里听起来是不是有点绕？不过没关系，我们在后面的第 7 节课中，会详细讲解这个问题。

除此之外，从定义中，我们还可以发现，理解面向对象编程及面向对象编程语言两个概念，其中最关键的一点就是理解面向对象编程的四大特性。这四大特性分别是：封装、抽象、继承、多态。不过，关于面向对象编程的特性，也有另外一种说法，那就是只包含三大特性：封装、继承、多态，不包含抽象。为什么会有这种分歧呢？抽象为什么可以排除在面向对象编程特性之外呢？关于这个问题，在下一节课详细讲解这四大特性的时候，我还会再拿出来说一下。不过，话说回来，实际上，我们没必要纠结到底是四大特性还是三大特性，关键还是理解每种特性讲的是什么内容、存在的意义以及能解决什么问题。

而且，在技术圈里，封装、抽象、继承、多态也并不是固定地被叫作“四大特性”（features），也有人称它们为面向对象编程的四大概念（concepts）、四大基石（cornerstones）、四大基础（fundamentals）、四大支柱（pillars）等等。你也发现了吧，叫法挺混乱的。不过，叫什么并不重要。我们只需要知道，这是前人进行面向对象编程过程中总结出来的、能让我们更容易地实现各种设计思路的几个编程套路，这就够了。在之后的课程讲解中，我统一把它们叫作“四大特性”。

### 如何判定某编程语言是否是面向对象编程语言？

如果你足够细心，你可能已经留意到，在我刚刚的讲解中，我提到，“如果不按照严格的定义来说，大部分编程语言都是面向对象编程语言”。为什么要加上“如果不按照严格的定义”这个前提呢？那是因为，如果按照刚刚我们给出的严格的面向对象编程语言的定义，前面提到的有些编程语言，并不是严格意义上的面向对象编程语言，比如 JavaScript，它不支持封装和继承特性，按照严格的定义，它不算是面向对象编程语言，但在某种意义上，它又可以算得上是一种面向对象编程语言。我为什么这么说呢？到底该如何判断一个编程语言是否是面向对象编程语言呢？

还记得我们前面给出的面向对象编程及面向对象编程语言的定义吗？如果忘记了，你可以先翻到上面回顾一下。不过，我必须坦诚告诉你，那个定义是我自己给出的。实际上，对于什么是面向对象编程、什么是面向对象编程语言，并没有一个官方的、统一的定义。而且，从 1960 年，也就是 60 年前面向对象编程诞生开始，这两个概念就在不停地演化，所以，也无法给出一个明确的定义，也没有必要给出一个明确定义。

实际上，面向对象编程从字面上，按照最简单、最原始的方式来理解，就是将对象或类作为代码组织的基本单元，来进行编程的一种编程范式或者编程风格，并不一定需要封装、抽象、继承、多态这四大特性的支持。但是，在进行面向对象编程的过程中，人们不停地总结发现，有了这四大特性，我们就能更容易地实现各种面向对象的代码设计思路。

比如，我们在面向对象编程的过程中，经常会遇到 is-a 这种类关系（比如狗是一种动物），而继承这个特性就能很好地支持这种 is-a 的代码设计思路，并且解决代码复用的问题，所以，继承就成了面向对象编程的四大特性之一。但是随着编程语言的不断迭代、演化，人们发现继承这种特性容易造成层次不清、代码混乱，所以，很多编程语言在设计的时候就开始摒弃继承特性，比如 Go 语言。但是，我们并不能因为它摒弃了继承特性，就一刀切地认为它不是面向对象编程语言了。

实际上，我个人觉得，只要某种编程语言支持类或对象的语法概念，并且以此作为组织代码的基本单元，那就可以被粗略地认为它就是面向对象编程语言了。至于是否有现成的语法机制，完全地支持了面向对象编程的四大特性、是否对四大特性有所取舍和优化，可以不作为判定的标准。基于此，我们才有了前面的说法，按照严格的定义，很多语言都不能算得上面向对象编程语言，但按照不严格的定义来讲，现在流行的大部分编程语言都是面向对象编程语言。

### 什么是面向对象分析和面向对象设计？

前面我们讲了面向对象编程（OOP），实际上，跟面向对象编程经常放到一块儿来讲的还有另外两个概念，那就是面向对象分析（OOA）和面向对象设计（OOD）。面向对象分析英文缩写是 OOA，全称是 Object Oriented Analysis；面向对象设计的英文缩写是 OOD，全称是 Object Oriented Design。OOA、OOD、OOP 三个连在一起就是面向对象分析、设计、编程（实现），正好是面向对象软件开发要经历的三个阶段。

关于什么是面向对象编程，我们前面已经讲过了。我们现在再来讲一下，什么是面向对象分析和设计。这两个概念相对来说要简单一些。面向对象分析与设计中的“分析”和“设计”这两个词，我们完全可以从字面上去理解，不需要过度解读，简单类比软件开发中的需求分析、系统设计即可。不过，你可能会说，那为啥前面还加了个修饰词“面向对象”呢？有什么特殊的意义吗？

之所以在前面加“面向对象”这几个字，是因为我们是围绕着对象或类来做需求分析和设计的。分析和设计两个阶段最终的产出是类的设计，包括程序被拆解为哪些类，每个类有哪些属性方法，类与类之间如何交互等等。它们比其他的分析和设计更加具体、更加落地、更加贴近编码，更能够顺利地过渡到面向对象编程环节。这也是面向对象分析和设计，与其他分析和设计最大的不同点。

看到这里，你可能会问，那面向对象分析、设计、编程到底都负责做哪些工作呢？简单点讲，面向对象分析就是要搞清楚做什么，面向对象设计就是要搞清楚怎么做，面向对象编程就是将分析和设计的的结果翻译成代码的过程。今天，我们只是简单介绍一下概念，不展开详细讲解。在后面的面向对象实战环节中，我会用两节课的时间，通过一个实际例子，详细讲解如何进行面向对象分析、设计和编程。

### 什么是 UML？我们是否需要 UML？

### 总结

1. 什么是面向对象编程？

面向对象编程是一种编程范式或编程风格。它以类或对象作为组织代码的基本单元，并将封装、抽象、继承、多态四个特性，作为代码设计和实现的基石 。

2. 什么是面向对象编程语言？

面向对象编程语言是支持类或对象的语法机制，并有现成的语法机制，能方便地实现面向对象编程四大特性（封装、抽象、继承、多态）的编程语言。

1. 如何判定一个编程语言是否是面向对象编程语言？

如果按照严格的的定义，需要有现成的语法支持类、对象、四大特性才能叫作面向对象编程语言。如果放宽要求的话，只要某种编程语言支持类、对象语法机制，那基本上就可以说这种编程语言是面向对象编程语言了，不一定非得要求具有所有的四大特性。

1. 面向对象编程和面向对象编程语言之间有何关系？

面向对象编程一般使用面向对象编程语言来进行，但是，不用面向对象编程语言，我们照样可以进行面向对象编程。反过来讲，即便我们使用面向对象编程语言，写出来的代码也不一定是面向对象编程风格的，也有可能是面向过程编程风格的。

1. 什么是面向对象分析和面向对象设计？

简单点讲，面向对象分析就是要搞清楚做什么，面向对象设计就是要搞清楚怎么做。两个阶段最终的产出是类的设计，包括程序被拆解为哪些类，每个类有哪些属性方法、类与类之间如何交互等等。

## 05 | 理论二：封装、抽象、继承、多态分别可以解决哪些编程问题？

### 封装（Encapsulation）

首先，我们来看封装特性。封装也叫作信息隐藏或者数据访问保护。类通过暴露有限的访问接口，授权外部仅能通过类提供的方式（或者叫函数）来访问内部信息或者数据。这句话怎么理解呢？我们通过一个简单的例子来解释一下。

下面这段代码是金融系统中一个简化版的虚拟钱包的代码实现。在金融系统中，我们会给每个用户创建一个虚拟钱包，用来记录用户在我们的系统中的虚拟货币量。对于虚拟钱包的业务背景，这里你只需要简单了解一下即可。在面向对象的实战篇中，我们会有单独两节课，利用 OOP 的设计思想来详细介绍虚拟钱包的设计实现。

```java
public class Wallet {
  private String id;
  private long createTime;
  private BigDecimal balance;
  private long balanceLastModifiedTime;
  // ...省略其他属性...

  public Wallet() {
     this.id = IdGenerator.getInstance().generate();
     this.createTime = System.currentTimeMillis();
     this.balance = BigDecimal.ZERO;
     this.balanceLastModifiedTime = System.currentTimeMillis();
  }

  // 注意：下面对get方法做了代码折叠，是为了减少代码所占文章的篇幅
  public String getId() { return this.id; }
  public long getCreateTime() { return this.createTime; }
  public BigDecimal getBalance() { return this.balance; }
  public long getBalanceLastModifiedTime() { return this.balanceLastModifiedTime;  }

  public void increaseBalance(BigDecimal increasedAmount) {
    if (increasedAmount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException("...");
    }
    this.balance.add(increasedAmount);
    this.balanceLastModifiedTime = System.currentTimeMillis();
  }

  public void decreaseBalance(BigDecimal decreasedAmount) {
    if (decreasedAmount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException("...");
    }
    if (decreasedAmount.compareTo(this.balance) > 0) {
      throw new InsufficientAmountException("...");
    }
    this.balance.subtract(decreasedAmount);
    this.balanceLastModifiedTime = System.currentTimeMillis();
  }
}
```

从代码中，我们可以发现，Wallet 类主要有四个属性（也可以叫作成员变量），也就是我们前面定义中提到的信息或者数据。其中，id 表示钱包的唯一编号，createTime 表示钱包创建的时间，balance 表示钱包中的余额，balanceLastModifiedTime 表示上次钱包余额变更的时间。

我们参照封装特性，对钱包的这四个属性的访问方式进行了限制。调用者只允许通过下面这六个方法来访问或者修改钱包里的数据。

- String getId()
- long getCreateTime()
- BigDecimal getBalance()
- long getBalanceLastModifiedTime()
- void increaseBalance(BigDecimal increasedAmount)
- void decreaseBalance(BigDecimal decreasedAmount)

之所以这样设计，是因为从业务的角度来说，id、createTime 在创建钱包的时候就确定好了，之后不应该再被改动，所以，我们并没有在 Wallet 类中，暴露 id、createTime 这两个属性的任何修改方法，比如 set 方法。而且，这两个属性的初始化设置，对于 Wallet 类的调用者来说，也应该是透明的，所以，我们在 Wallet 类的构造函数内部将其初始化设置好，而不是通过构造函数的参数来外部赋值。

对于钱包余额 balance 这个属性，从业务的角度来说，只能增或者减，不会被重新设置。所以，我们在 Wallet 类中，只暴露了 increaseBalance() 和 decreaseBalance() 方法，并没有暴露 set 方法。对于 balanceLastModifiedTime 这个属性，它完全是跟 balance 这个属性的修改操作绑定在一起的。只有在 balance 修改的时候，这个属性才会被修改。所以，我们把 balanceLastModifiedTime 这个属性的修改操作完全封装在了 increaseBalance() 和 decreaseBalance() 两个方法中，不对外暴露任何修改这个属性的方法和业务细节。这样也可以保证 balance 和 balanceLastModifiedTime 两个数据的一致性。

对于封装这个特性，我们需要编程语言本身提供一定的语法机制来支持。这个语法机制就是访问权限控制。例子中的 private、public 等关键字就是 Java 语言中的访问权限控制语法。private 关键字修饰的属性只能类本身访问，可以保护其不被类之外的代码直接访问。如果 Java 语言没有提供访问权限控制语法，所有的属性默认都是 public 的，那任意外部代码都可以通过类似 wallet.id=123; 这样的方式直接访问、修改属性，也就没办法达到隐藏信息和保护数据的目的了，也就无法支持封装特性了。

封装特性的定义讲完了，我们再来看一下，封装的意义是什么？它能解决什么编程问题？

如果我们对类中属性的访问不做限制，那任何代码都可以访问、修改类中的属性，虽然这样看起来更加灵活，但从另一方面来说，过度灵活也意味着不可控，属性可以随意被以各种奇葩的方式修改，而且修改逻辑可能散落在代码中的各个角落，势必影响代码的可读性、可维护性。比如某个同事在不了解业务逻辑的情况下，在某段代码中“偷偷地”重设了 wallet 中的 balanceLastModifiedTime 属性，这就会导致 balance 和 balanceLastModifiedTime 两个数据不一致。

除此之外，类仅仅通过有限的方法暴露必要的操作，也能提高类的易用性。如果我们把类属性都暴露给类的调用者，调用者想要正确地操作这些属性，就势必要对业务细节有足够的了解。而这对于调用者来说也是一种负担。相反，如果我们将属性封装起来，暴露少许的几个必要的方法给调用者使用，调用者就不需要了解太多背后的业务细节，用错的概率就减少很多。这就好比，如果一个冰箱有很多按钮，你就要研究很长时间，还不一定能操作正确。相反，如果只有几个必要的按钮，比如开、停、调节温度，你一眼就能知道该如何来操作，而且操作出错的概率也会降低很多。

### 抽象（Abstraction）

讲完了封装特性，我们再来看抽象特性。 封装主要讲的是如何隐藏信息、保护数据，而抽象讲的是如何隐藏方法的具体实现，让调用者只需要关心方法提供了哪些功能，并不需要知道这些功能是如何实现的。

在面向对象编程中，我们常借助编程语言提供的接口类（比如 Java 中的 interface 关键字语法）或者抽象类（比如 Java 中的 abstract 关键字语法）这两种语法机制，来实现抽象这一特性。

这里我稍微说明一下，在专栏中，我们把编程语言提供的接口语法叫作“接口类”而不是“接口”。之所以这么做，是因为“接口”这个词太泛化，可以指好多概念，比如 API 接口等，所以，我们用“接口类”特指编程语言提供的接口语法。

对于抽象这个特性，我举一个例子来进一步解释一下。

```java
public interface IPictureStorage {
  void savePicture(Picture picture);
  Image getPicture(String pictureId);
  void deletePicture(String pictureId);
  void modifyMetaInfo(String pictureId, PictureMetaInfo metaInfo);
}

public class PictureStorage implements IPictureStorage {
  // ...省略其他属性...
  @Override
  public void savePicture(Picture picture) { ... }
  @Override
  public Image getPicture(String pictureId) { ... }
  @Override
  public void deletePicture(String pictureId) { ... }
  @Override
  public void modifyMetaInfo(String pictureId, PictureMetaInfo metaInfo) { ... }
}
```

 在上面的这段代码中，我们利用 Java 中的 interface 接口语法来实现抽象特性。调用者在使用图片存储功能的时候，只需要了解 IPictureStorage 这个接口类暴露了哪些方法就可以了，不需要去查看 PictureStorage 类里的具体实现逻辑。

实际上，抽象这个特性是非常容易实现的，并不需要非得依靠接口类或者抽象类这些特殊语法机制来支持。换句话说，并不是说一定要为实现类（PictureStorage）抽象出接口类（IPictureStorage），才叫作抽象。即便不编写 IPictureStorage 接口类，单纯的 PictureStorage 类本身就满足抽象特性。

之所以这么说，那是因为，类的方法是通过编程语言中的“函数”这一语法机制来实现的。通过函数包裹具体的实现逻辑，这本身就是一种抽象。调用者在使用函数的时候，并不需要去研究函数内部的实现逻辑，只需要通过函数的命名、注释或者文档，了解其提供了什么功能，就可以直接使用了。比如，我们在使用 C 语言的 malloc() 函数的时候，并不需要了解它的底层代码是怎么实现的。

除此之外，在上一节课中，我们还提到，抽象有时候会被排除在面向对象的四大特性之外，当时我卖了一个关子，现在我就来解释一下为什么。

抽象这个概念是一个非常通用的设计思想，并不单单用在面向对象编程中，也可以用来指导架构设计等。而且这个特性也并不需要编程语言提供特殊的语法机制来支持，只需要提供“函数”这一非常基础的语法机制，就可以实现抽象特性、所以，它没有很强的“特异性”，有时候并不被看作面向对象编程的特性之一。

抽象特性的定义讲完了，我们再来看一下，抽象的意义是什么？它能解决什么编程问题？

实际上，如果上升一个思考层面的话，抽象及其前面讲到的封装都是人类处理复杂性的有效手段。在面对复杂系统的时候，人脑能承受的信息复杂程度是有限的，所以我们必须忽略掉一些非关键性的实现细节。而抽象作为一种只关注功能点不关注实现的设计思路，正好帮我们的大脑过滤掉许多非必要的信息。

除此之外，抽象作为一个非常宽泛的设计思想，在代码设计中，起到非常重要的指导作用。很多设计原则都体现了抽象这种设计思想，比如基于接口而非实现编程、开闭原则（对扩展开放、对修改关闭）、代码解耦（降低代码的耦合性）等。我们在讲到后面的内容的时候，会具体来解释。

换一个角度来考虑，我们在定义（或者叫命名）类的方法的时候，也要有抽象思维，不要在方法定义中，暴露太多的实现细节，以保证在某个时间点需要改变方法的实现逻辑的时候，不用去修改其定义。举个简单例子，比如 getAliyunPictureUrl() 就不是一个具有抽象思维的命名，因为某一天如果我们不再把图片存储在阿里云上，而是存储在私有云上，那这个命名也要随之被修改。相反，如果我们定义一个比较抽象的函数，比如叫作 getPictureUrl()，那即便内部存储方式修改了，我们也不需要修改命名。

### 继承（Inheritance）

学习完了封装和抽象两个特性，我们再来看继承特性。如果你熟悉的是类似 Java、C++ 这样的面向对象的编程语言，那你对继承这一特性，应该不陌生了。继承是用来表示类之间的 is-a 关系，比如猫是一种哺乳动物。从继承关系上来讲，继承可以分为两种模式，单继承和多继承。单继承表示一个子类只继承一个父类，多继承表示一个子类可以继承多个父类，比如猫既是哺乳动物，又是爬行动物。

为了实现继承这个特性，编程语言需要提供特殊的语法机制来支持，比如 Java 使用 extends 关键字来实现继承，C++ 使用冒号（class B : public A），Python 使用 paraentheses()，Ruby 使用 <。不过，有些编程语言只支持单继承，不支持多重继承，比如 Java、PHP、C#、Ruby 等，而有些编程语言既支持单重继承，也支持多重继承，比如 C++、Python、Perl 等。

继承特性的定义讲完了，我们再来看，继承存在的意义是什么？它能解决什么编程问题？

继承最大的一个好处就是代码复用。假如两个类有一些相同的属性和方法，我们就可以将这些相同的部分，抽取到父类中，让两个子类继承父类。这样，两个子类就可以重用父类中的代码，避免代码重复写多遍。不过，这一点也并不是继承所独有的，我们也可以通过其他方式来解决这个代码复用的问题，比如利用组合关系而不是继承关系。

如果我们再上升一个思维层面，去思考继承这一特性，可以这么理解：我们代码中有一个猫类，有一个哺乳动物类。猫属于哺乳动物，从人类认知的角度上来说，是一种 is-a 关系。我们通过继承来关联两个类，反应真实世界中的这种关系，非常符合人类的认知，而且，从设计的角度来说，也有一种结构美感。

继承的概念很好理解，也很容易使用。不过，过度使用继承，继承层次过深过复杂，就会导致代码可读性、可维护性变差。为了了解一个类的功能，我们不仅需要查看这个类的代码，还需要按照继承关系一层一层地往上查看“父类、父类的父类……”的代码。还有，子类和父类高度耦合，修改父类的代码，会直接影响到子类。

所以，继承这个特性也是一个非常有争议的特性。很多人觉得继承是一种反模式。我们应该尽量少用，甚至不用。关于这个问题，在后面讲到“多用组合少用继承”这种设计思想的时候，我会非常详细地再讲解，这里暂时就不展开讲解了。

### 多态（Polymorphism）

学习完了封装、抽象、继承之后，我们再来看面向对象编程的最后一个特性，多态。多态是指，子类可以替换父类，在实际的代码运行过程中，调用子类的方法实现。对于多态这种特性，纯文字解释不好理解，我们还是看一个具体的例子。

```java
public class DynamicArray {
  private static final int DEFAULT_CAPACITY = 10;
  protected int size = 0;
  protected int capacity = DEFAULT_CAPACITY;
  protected Integer[] elements = new Integer[DEFAULT_CAPACITY];
  
  public int size() { return this.size; }
  public Integer get(int index) { return elements[index];}
  //...省略n多方法...
  
  public void add(Integer e) {
    ensureCapacity();
    elements[size++] = e;
  }
  
  protected void ensureCapacity() {
    //...如果数组满了就扩容...代码省略...
  }
}

public class SortedDynamicArray extends DynamicArray {
  @Override
  public void add(Integer e) {
    ensureCapacity();
    int i;
    for (i = size-1; i>=0; --i) { //保证数组中的数据有序
      if (elements[i] > e) {
        elements[i+1] = elements[i];
      } else {
        break;
      }
    }
    elements[i+1] = e;
    ++size;
  }
}

public class Example {
  public static void test(DynamicArray dynamicArray) {
    dynamicArray.add(5);
    dynamicArray.add(1);
    dynamicArray.add(3);
    for (int i = 0; i < dynamicArray.size(); ++i) {
      System.out.println(dynamicArray.get(i));
    }
  }
  
  public static void main(String args[]) {
    DynamicArray dynamicArray = new SortedDynamicArray();
    test(dynamicArray); // 打印结果：1、3、5
  }
}
```

多态这种特性也需要编程语言提供特殊的语法机制来实现。在上面的例子中，我们用到了三个语法机制来实现多态。

- 第一个语法机制是编程语言要支持**父类对象可以引用子类对象**，也就是可以将 SortedDynamicArray 传递给 DynamicArray。
- 第二个语法机制是编程语言要支持**继承**，也就是 SortedDynamicArray 继承了 DynamicArray，才能将 SortedDyamicArray 传递给 DynamicArray。
- 第三个语法机制是编程语言要支持**子类可以重写（override）父类**中的方法，也就是 SortedDyamicArray 重写了 DynamicArray 中的 add() 方法。

通过这三种语法机制配合在一起，我们就实现了在 test() 方法中，子类 SortedDyamicArray 替换父类 DynamicArray，执行子类 SortedDyamicArray 的 add() 方法，也就是实现了多态特性。

对于多态特性的实现方式，除了利用“继承加方法重写”这种实现方式之外，我们还有其他两种比较常见的的实现方式，一个是利用接口类语法，另一个是利用 duck-typing 语法。不过，并不是每种编程语言都支持接口类或者 duck-typing 这两种语法机制，比如 C++ 就不支持接口类语法，而 duck-typing 只有一些动态语言才支持，比如 Python、JavaScript 等。

接下来，我们先来看如何利用接口类来实现多态特性。我们还是先来看一段代码。

```java
public interface Iterator {
  boolean hasNext();
  String next();
  String remove();
}

public class Array implements Iterator {
  private String[] data;
  
  public boolean hasNext() { ... }
  public String next() { ... }
  public String remove() { ... }
  //...省略其他方法...
}

public class LinkedList implements Iterator {
  private LinkedListNode head;
  
  public boolean hasNext() { ... }
  public String next() { ... }
  public String remove() { ... }
  //...省略其他方法... 
}

public class Demo {
  private static void print(Iterator iterator) {
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }
  
  public static void main(String[] args) {
    Iterator arrayIterator = new Array();
    print(arrayIterator);
    
    Iterator linkedListIterator = new LinkedList();
    print(linkedListIterator);
  }
}
```

在这段代码中，Iterator 是一个接口类，定义了一个可以遍历集合数据的迭代器。Array 和 LinkedList 都实现了接口类 Iterator。我们通过传递不同类型的实现类（Array、LinkedList）到 print(Iterator iterator) 函数中，支持动态的调用不同的 next()、hasNext() 实现。

具体点讲就是，当我们往 print(Iterator iterator) 函数传递 Array 类型的对象的时候，print(Iterator iterator) 函数就会调用 Array 的 next()、hasNext() 的实现逻辑；当我们往 print(Iterator iterator) 函数传递 LinkedList 类型的对象的时候，print(Iterator iterator) 函数就会调用 LinkedList 的 next()、hasNext() 的实现逻辑。

刚刚讲的是用接口类来实现多态特性。现在，我们再来看下，如何用 duck-typing 来实现多态特性。我们还是先来看一段代码。这是一段 Python 代码。

```java
class Logger:
    def record(self):
        print(“I write a log into file.”)
        
class DB:
    def record(self):
        print(“I insert data into db. ”)
        
def test(recorder):
    recorder.record()

def demo():
    logger = Logger()
    db = DB()
    test(logger)
    test(db)
```

从这段代码中，我们发现，duck-typing 实现多态的方式非常灵活。Logger 和 DB 两个类没有任何关系，既不是继承关系，也不是接口和实现的关系，但是只要它们都有定义了 record() 方法，就可以被传递到 test() 方法中，在实际运行的时候，执行对应的 record() 方法。

也就是说，只要两个类具有相同的方法，就可以实现多态，并不要求两个类之间有任何关系，这就是所谓的 duck-typing，是一些动态语言所特有的语法机制。而像 Java 这样的静态语言，通过继承实现多态特性，必须要求两个类之间有继承关系，通过接口实现多态特性，类必须实现对应的接口。

多态特性讲完了，我们再来看，多态特性存在的意义是什么？它能解决什么编程问题？

多态特性能提高代码的可扩展性和复用性。为什么这么说呢？我们回过头去看讲解多态特性的时候，举的第二个代码实例（Iterator 的例子）。

在那个例子中，我们利用多态的特性，仅用一个 print() 函数就可以实现遍历打印不同类型（Array、LinkedList）集合的数据。当再增加一种要遍历打印的类型的时候，比如 HashMap，我们只需让 HashMap 实现 Iterator 接口，重新实现自己的 hasNext()、next() 等方法就可以了，完全不需要改动 print() 函数的代码。所以说，多态提高了代码的可扩展性。

如果我们不使用多态特性，我们就无法将不同的集合类型（Array、LinkedList）传递给相同的函数（print(Iterator iterator) 函数）。我们需要针对每种要遍历打印的集合，分别实现不同的 print() 函数，比如针对 Array，我们要实现 print(Array array) 函数，针对 LinkedList，我们要实现 print(LinkedList linkedList) 函数。而利用多态特性，我们只需要实现一个 print() 函数的打印逻辑，就能应对各种集合数据的打印操作，这显然提高了代码的复用性。

除此之外，多态也是很多设计模式、设计原则、编程技巧的代码实现基础，比如策略模式、基于接口而非实现编程、依赖倒置原则、里式替换原则、利用多态去掉冗长的 if-else 语句等等。关于这点，在学习后面的章节中，你慢慢会有更深的体会。

### 总结

1. 关于封装特性

封装也叫作信息隐藏或者数据访问保护。类通过暴露有限的访问接口，授权外部仅能通过类提供的方式来访问内部信息或者数据。它需要编程语言提供权限访问控制语法来支持，例如 Java 中的 private、protected、public 关键字。封装特性存在的意义，一方面是保护数据不被随意修改，提高代码的可维护性；另一方面是仅暴露有限的必要接口，提高类的易用性。

2. 关于抽象特性

封装主要讲如何隐藏信息、保护数据，那抽象就是讲如何隐藏方法的具体实现，让使用者只需要关心方法提供了哪些功能，不需要知道这些功能是如何实现的。抽象可以通过接口类或者抽象类来实现，但也并不需要特殊的语法机制来支持。抽象存在的意义，一方面是提高代码的可扩展性、维护性，修改实现不需要改变定义，减少代码的改动范围；另一方面，它也是处理复杂系统的有效手段，能有效地过滤掉不必要关注的信息。

3. 关于继承特性

继承是用来表示类之间的 is-a 关系，分为两种模式：单继承和多继承。单继承表示一个子类只继承一个父类，多继承表示一个子类可以继承多个父类。为了实现继承这个特性，编程语言需要提供特殊的语法机制来支持。继承主要是用来解决代码复用的问题。

4. 关于多态特性

多态是指子类可以替换父类，在实际的代码运行过程中，调用子类的方法实现。多态这种特性也需要编程语言提供特殊的语法机制来实现，比如继承、接口类、duck-typing。多态可以提高代码的扩展性和复用性，是很多设计模式、设计原则、编程技巧的代码实现基础。

## 06 | 理论三：面向对象相比面向过程有哪些优势？面向过程真的过时了吗？

在上两节课中，我们讲了面向对象这种现在非常流行的编程范式，或者说编程风格。实际上，除了面向对象之外，被大家熟知的编程范式还有另外两种，面向过程编程和函数式编程。面向过程这种编程范式随着面向对象的出现，已经慢慢退出了舞台，而函数式编程目前还没有被广泛接受。

在专栏中，我不会对函数式编程做讲解，但我会花两节课的时间，讲一下面向过程这种编程范式。你可能会问，既然面向对象已经成为主流的编程范式，而面向过程已经不那么推荐使用，那为什么又要浪费时间讲它呢？

那是因为在过往的工作中，我发现很多人搞不清楚面向对象和面向过程的区别，总以为使用面向对象编程语言来做开发，就是在进行面向对象编程了。而实际上，他们只是在用面向对象编程语言，编写面向过程风格的代码而已，并没有发挥面向对象编程的优势。这就相当于手握一把屠龙刀，却只是把它当作一把普通的刀剑来用，相当可惜。

### 什么是面向过程编程与面向过程编程语言？

如果你是一名比较资深的程序员，最开始学习编程的时候，接触的是 Basic、Pascal、C 等面向过程的编程语言，那你对这两个概念肯定不陌生。但如果你是新生代的程序员，一开始学编程的时候，接触的就是面向对象编程语言，那你对这两个概念可能会比较不熟悉。所以，在对比面向对象与面向过程优劣之前，我们先把面向过程编程和面向过程编程语言这两个概念搞清楚。

实际上，我们可以对比着面向对象编程和面向对象编程语言这两个概念，来理解面向过程编程和面向过程编程语言。还记得我们之前是如何定义面向对象编程和面向对象编程语言的吗？让我们一块再来回顾一下。

- 面向对象编程是一种编程范式或编程风格。它以类或对象作为组织代码的基本单元，并将封装、抽象、继承、多态四个特性，作为代码设计和实现的基石 。
- 面向对象编程语言是支持类或对象的语法机制，并有现成的语法机制，能方便地实现面向对象编程四大特性（封装、抽象、继承、多态）的编程语言。

类比面向对象编程与面向对象编程语言的定义，对于面向过程编程和面向过程编程语言这两个概念，我给出下面这样的定义。

- 面向过程编程也是一种编程范式或编程风格。它以过程（可以理解为方法、函数、操作）作为组织代码的基本单元，以数据（可以理解为成员变量、属性）与方法相分离为最主要的特点。面向过程风格是一种流程化的编程风格，通过拼接一组顺序执行的方法来操作数据完成一项功能。
- 面向过程编程语言首先是一种编程语言。它最大的特点是不支持类和对象两个语法概念，不支持丰富的面向对象编程特性（比如继承、多态、封装），仅支持面向过程编程。

不过，这里我必须声明一下，就像我们在之前讲到的，面向对象编程和面向对象编程语言并没有官方的定义一样，这里我给出的面向过程编程和面向过程编程语言的定义，也并不是严格的官方定义。之所以要给出这样的定义，只是为了跟面向对象编程及面向对象编程语言做个对比，以方便你理解它们的区别。

定义不是很严格，也比较抽象，所以，我再用一个例子进一步解释一下。假设我们有一个记录了用户信息的文本文件 users.txt，每行文本的格式是 name&age&gender（比如，小王 &28& 男）。我们希望写一个程序，从 users.txt 文件中逐行读取用户信息，然后格式化成 name\tage\tgender（其中，\t 是分隔符）这种文本格式，并且按照 age 从小到大排序之后，重新写入到另一个文本文件 formatted_users.txt 中。针对这样一个小程序的开发，我们一块来看看，用面向过程和面向对象两种编程风格，编写出来的代码有什么不同。

首先，我们先来看，用面向过程这种编程风格写出来的代码是什么样子的。注意，下面的代码是用 C 语言这种面向过程的编程语言来编写的。

```c
struct User {
  char name[64];
  int age;
  char gender[16];
};

struct User parse_to_user(char* text) {
  // 将text(“小王&28&男”)解析成结构体struct User
}

char* format_to_text(struct User user) {
  // 将结构体struct User格式化成文本（"小王\t28\t男"）
}

void sort_users_by_age(struct User users[]) {
  // 按照年龄从小到大排序users
}

void format_user_file(char* origin_file_path, char* new_file_path) {
  // open files...
  struct User users[1024]; // 假设最大1024个用户
  int count = 0;
  while(1) { // read until the file is empty
    struct User user = parse_to_user(line);
    users[count++] = user;
  }
  
  sort_users_by_age(users);
  
  for (int i = 0; i < count; ++i) {
    char* formatted_user_text = format_to_text(users[i]);
    // write to new file...
  }
  // close files...
}

int main(char** args, int argv) {
  format_user_file("/home/zheng/user.txt", "/home/zheng/formatted_users.txt");
}
```

然后，我们再来看，用面向对象这种编程风格写出来的代码是什么样子的。注意，下面的代码是用 Java 这种面向对象的编程语言来编写的。

```java

 public class User {
  private String name;
  private int age;
  private String gender;
  
  public User(String name, int age, String gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
  }
  
  public static User praseFrom(String userInfoText) {
    // 将text(“小王&28&男”)解析成类User
  }
  
  public String formatToText() {
    // 将类User格式化成文本（"小王\t28\t男"）
  }
}

public class UserFileFormatter {
  public void format(String userFile, String formattedUserFile) {
    // Open files...
    List users = new ArrayList<>();
    while (1) { // read until file is empty 
      // read from file into userText...
      User user = User.parseFrom(userText);
      users.add(user);
    }
    // sort users by age...
    for (int i = 0; i < users.size(); ++i) {
      String formattedUserText = user.formatToText();
      // write to new file...
    }
    // close files...
  }
}

public class MainApplication {
  public static void main(String[] args) {
    UserFileFormatter userFileFormatter = new UserFileFormatter();
    userFileFormatter.format("/home/zheng/users.txt", "/home/zheng/formatted_users.txt");
  }
}
```

从上面的代码中，我们可以看出，面向过程和面向对象最基本的区别就是，代码的组织方式不同。面向过程风格的代码被组织成了一组方法集合及其数据结构（struct User），方法和数据结构的定义是分开的。面向对象风格的代码被组织成一组类，方法和数据结构被绑定一起，定义在类中。

看完这个例子之后，你可能会说，面向对象编程和面向过程编程，两种风格的区别就这么一点吗？当然不是，对于这两种编程风格的更多区别，我们继续往下看。

### 面向对象编程相比面向过程编程有哪些优势？

刚刚我们介绍了面向过程编程及面向过程编程语言的定义，并跟面向对象编程及面向对象编程语言做了一个简单对比。接下来，我们再来看一下，为什么面向对象编程晚于面向过程编程出现，却能取而代之，成为现在主流的编程范式？面向对象编程跟面向过程编程比起来，到底有哪些优势？

1. OOP 更加能够应对大规模复杂程序的开发

看了刚刚举的那个格式化文本文件的例子，你可能会有这样的疑问，两种编程风格实现的代码貌似差不多啊，顶多就是代码的组织方式有点区别，没有感觉到面向对象编程有什么明显的优势呀！你的感觉没错。之所以有这种感觉，主要原因是这个例子程序比较简单、不够复杂。

对于简单程序的开发来说，不管是用面向过程编程风格，还是用面向对象编程风格，差别确实不会很大，甚至有的时候，面向过程的编程风格反倒更有优势。因为需求足够简单，整个程序的处理流程只有一条主线，很容易被划分成顺序执行的几个步骤，然后逐句翻译成代码，这就非常适合采用面向过程这种面条式的编程风格来实现。

但对于大规模复杂程序的开发来说，整个程序的处理流程错综复杂，并非只有一条主线。如果把整个程序的处理流程画出来的话，会是一个网状结构。如果我们再用面向过程编程这种流程化、线性的思维方式，去翻译这个网状结构，去思考如何把程序拆解为一组顺序执行的方法，就会比较吃力。这个时候，面向对象的编程风格的优势就比较明显了。

面向对象编程是以类为思考对象。在进行面向对象编程的时候，我们并不是一上来就去思考，如何将复杂的流程拆解为一个一个方法，而是采用曲线救国的策略，先去思考如何给业务建模，如何将需求翻译为类，如何给类之间建立交互关系，而完成这些工作完全不需要考虑错综复杂的处理流程。当我们有了类的设计之后，然后再像搭积木一样，按照处理流程，将类组装起来形成整个程序。这种开发模式、思考问题的方式，能让我们在应对复杂程序开发的时候，思路更加清晰。

除此之外，面向对象编程还提供了一种更加清晰的、更加模块化的代码组织方式。比如，我们开发一个电商交易系统，业务逻辑复杂，代码量很大，可能要定义数百个函数、数百个数据结构，那如何分门别类地组织这些函数和数据结构，才能不至于看起来比较凌乱呢？类就是一种非常好的组织这些函数和数据结构的方式，是一种将代码模块化的有效手段。

你可能会说，像 C 语言这种面向过程的编程语言，我们也可以按照功能的不同，把函数和数据结构放到不同的文件里，以达到给函数和数据结构分类的目的，照样可以实现代码的模块化。你说得没错。只不过面向对象编程本身提供了类的概念，强制你做这件事情，而面向过程编程并不强求。这也算是面向对象编程相对于面向过程编程的一个微创新吧。

实际上，利用面向过程的编程语言照样可以写出面向对象风格的代码，只不过可能会比用面向对象编程语言来写面向对象风格的代码，付出的代价要高一些。而且，面向过程编程和面向对象编程并非完全对立的。很多软件开发中，尽管利用的是面向过程的编程语言，也都有借鉴面向对象编程的一些优点。

2. OOP 风格的代码更易复用、易扩展、易维护

在刚刚的那个例子中，因为代码比较简单，所以只用到到了类、对象这两个最基本的面向对象概念，并没有用到更加高级的四大特性，封装、抽象、继承、多态。因此，面向对象编程的优势其实并没有发挥出来。

面向过程编程是一种非常简单的编程风格，并没有像面向对象编程那样提供丰富的特性。而面向对象编程提供的封装、抽象、继承、多态这些特性，能极大地满足复杂的编程需求，能方便我们写出更易复用、易扩展、易维护的代码。为什么这么说呢？还记得我们在上一节课中讲到的封装、抽象、继承、多态存在的意义吗？我们再来简单回顾一下。

首先，我们先来看下封装特性。封装特性是面向对象编程相比于面向过程编程的一个最基本的区别，因为它基于的是面向对象编程中最基本的类的概念。面向对象编程通过类这种组织代码的方式，将数据和方法绑定在一起，通过访问权限控制，只允许外部调用者通过类暴露的有限方法访问数据，而不会像面向过程编程那样，数据可以被任意方法随意修改。因此，面向对象编程提供的封装特性更有利于提高代码的易维护性。

其次，我们再来看下抽象特性。我们知道，函数本身就是一种抽象，它隐藏了具体的实现。我们在使用函数的时候，只需要了解函数具有什么功能，而不需要了解它是怎么实现的。从这一点上，不管面向过程编程还是是面向对象编程，都支持抽象特性。不过，面向对象编程还提供了其他抽象特性的实现方式。这些实现方式是面向过程编程所不具备的，比如基于接口实现的抽象。基于接口的抽象，可以让我们在不改变原有实现的情况下，轻松替换新的实现逻辑，提高了代码的可扩展性。

再次，我们来看下继承特性。继承特性是面向对象编程相比于面向过程编程所特有的两个特性之一（另一个是多态）。如果两个类有一些相同的属性和方法，我们就可以将这些相同的代码，抽取到父类中，让两个子类继承父类。这样两个子类也就可以重用父类中的代码，避免了代码重复写多遍，提高了代码的复用性。

最后，我们来看下多态特性。基于这个特性，我们在需要修改一个功能实现的时候，可以通过实现一个新的子类的方式，在子类中重写原来的功能逻辑，用子类替换父类。在实际的代码运行过程中，调用子类新的功能逻辑，而不是在原有代码上做修改。这就遵从了“对修改关闭、对扩展开放”的设计原则，提高代码的扩展性。除此之外，利用多态特性，不同的类对象可以传递给相同的方法，执行不同的代码逻辑，提高了代码的复用性。

所以说，基于这四大特性，利用面向对象编程，我们可以更轻松地写出易复用、易扩展、易维护的代码。当然，我们不能说，利用面向过程风格就不可以写出易复用、易扩展、易维护的代码，但没有四大特性的帮助，付出的代价可能就要高一些。

3. OOP 语言更加人性化、更加高级、更加智能

人类最开始跟机器打交道是通过 0、1 这样的二进制指令，然后是汇编语言，再之后才出现了高级编程语言。在高级编程语言中，面向过程编程语言又早于面向对象编程语言出现。之所以先出现面向过程编程语言，那是因为跟机器交互的方式，从二进制指令、汇编语言到面向过程编程语言，是一个非常自然的过渡，都是一种流程化的、面条式的编程风格，用一组指令顺序操作数据，来完成一项任务。

从指令到汇编再到面向过程编程语言，跟机器打交道的方式在不停地演进，从中我们很容易发现这样一条规律，那就是编程语言越来越人性化，让人跟机器打交道越来越容易。笼统点讲，就是编程语言越来越高级。实际上，在面向过程编程语言之后，面向对象编程语言的出现，也顺应了这样的发展规律，也就是说，面向对象编程语言比面向过程编程语言更加高级！

跟二进制指令、汇编语言、面向过程编程语言相比，面向对象编程语言的编程套路、思考问题的方式，是完全不一样的。前三者是一种计算机思维方式，而面向对象是一种人类的思维方式。我们在用前面三种语言编程的时候，我们是在思考，如何设计一组指令，告诉机器去执行这组指令，操作某些数据，帮我们完成某个任务。而在进行面向对象编程时候，我们是在思考，如何给业务建模，如何将真实的世界映射为类或者对象，这让我们更加能聚焦到业务本身，而不是思考如何跟机器打交道。可以这么说，越高级的编程语言离机器越“远”，离我们人类越“近”，越“智能”。

### 总结

1. 什么是面向过程编程？什么是面向过程编程语言？

实际上，面向过程编程和面向过程编程语言并没有严格的官方定义。理解这两个概念最好的方式是跟面向对象编程和面向对象编程语言进行对比。相较于面向对象编程以类为组织代码的基本单元，面向过程编程则是以过程（或方法）作为组织代码的基本单元。它最主要的特点就是数据和方法相分离。相较于面向对象编程语言，面向过程编程语言最大的特点就是不支持丰富的面向对象编程特性，比如继承、多态、封装。

2. 面向对象编程相比面向过程编程有哪些优势？

面向对象编程相比起面向过程编程的优势主要有三个。

- 对于大规模复杂程序的开发，程序的处理流程并非单一的一条主线，而是错综复杂的网状结构。面向对象编程比起面向过程编程，更能应对这种复杂类型的程序开发。
- 面向对象编程相比面向过程编程，具有更加丰富的特性（封装、抽象、继承、多态）。利用这些特性编写出来的代码，更加易扩展、易复用、易维护。
- 从编程语言跟机器打交道的方式的演进规律中，我们可以总结出：面向对象编程语言比起面向过程编程语言，更加人性化、更加高级、更加智能。

## 07 | 理论四：哪些代码设计看似是面向对象，实际是面向过程的？

### 哪些代码设计看似是面向对象，实际是面向过程的？

三种违反面向对象编程风格的典型代码设计：

1. 滥用 getter、setter 方法

在之前参与的项目开发中，我经常看到，有同事定义完类的属性之后，就顺手把这些属性的 getter、setter 方法都定义上。有些同事更加省事，直接用 IDE 或者 Lombok 插件（如果是 Java 项目的话）自动生成所有属性的 getter、setter 方法。

当我问起，为什么要给每个属性都定义 getter、setter 方法的时候，他们的理由一般是，为了以后可能会用到，现在事先定义好，类用起来就更加方便，而且即便用不到这些 getter、setter 方法，定义上它们也无伤大雅。

实际上，这样的做法我是非常不推荐的。它违反了面向对象编程的封装特性，相当于将面向对象编程风格退化成了面向过程编程风格。我通过下面这个例子来给你解释一下这句话。

```java
public class ShoppingCart {
  private int itemsCount;
  private double totalPrice;
  private List<ShoppingCartItem> items = new ArrayList<>();
  
  public int getItemsCount() {
    return this.itemsCount;
  }
  
  public void setItemsCount(int itemsCount) {
    this.itemsCount = itemsCount;
  }
  
  public double getTotalPrice() {
    return this.totalPrice;
  }
  
  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public List<ShoppingCartItem> getItems() {
    return this.items;
  }
  
  public void addItem(ShoppingCartItem item) {
    items.add(item);
    itemsCount++;
    totalPrice += item.getPrice();
  }
  // ...省略其他方法...
}
```

在这段代码中，ShoppingCart 是一个简化后的购物车类，有三个私有（private）属性：itemsCount、totalPrice、items。对于 itemsCount、totalPrice 两个属性，我们定义了它们的 getter、setter 方法。对于 items 属性，我们定义了它的 getter 方法和 addItem() 方法。代码很简单，理解起来不难。那你有没有发现，这段代码有什么问题呢？

我们先来看前两个属性，itemsCount 和 totalPrice。虽然我们将它们定义成 private 私有属性，但是提供了 public 的 getter、setter 方法，这就跟将这两个属性定义为 public 公有属性，没有什么两样了。外部可以通过 setter 方法随意地修改这两个属性的值。除此之外，任何代码都可以随意调用 setter 方法，来重新设置 itemsCount、totalPrice 属性的值，这也会导致其跟 items 属性的值不一致。

而面向对象封装的定义是：通过访问权限控制，隐藏内部数据，外部仅能通过类提供的有限的接口访问、修改内部数据。所以，暴露不应该暴露的 setter 方法，明显违反了面向对象的封装特性。数据没有访问权限控制，任何代码都可以随意修改它，代码就退化成了面向过程编程风格的了。

看完了前两个属性，我们再来看 items 这个属性。对于 items 这个属性，我们定义了它的 getter 方法和 addItem() 方法，并没有定义它的 setter 方法。这样的设计貌似看起来没有什么问题，但实际上并不是。

对于 itemsCount 和 totalPrice 这两个属性来说，定义一个 public 的 getter 方法，确实无伤大雅，毕竟 getter 方法不会修改数据。但是，对于 items 属性就不一样了，这是因为 items 属性的 getter 方法，返回的是一个 List集合容器。外部调用者在拿到这个容器之后，是可以操作容器内部数据的，也就是说，外部代码还是能修改 items 中的数据。比如像下面这样：

```java
ShoppingCart cart = new ShoppCart();
...
cart.getItems().clear(); // 清空购物车
```

你可能会说，清空购物车这样的功能需求看起来合情合理啊，上面的代码没有什么不妥啊。你说得没错，需求是合理的，但是这样的代码写法，会导致 itemsCount、totalPrice、items 三者数据不一致。我们不应该将清空购物车的业务逻辑暴露给上层代码。正确的做法应该是，在 ShoppingCart 类中定义一个 clear() 方法，将清空购物车的业务逻辑封装在里面，透明地给调用者使用。ShoppingCart 类的 clear() 方法的具体代码实现如下：

```java
public class ShoppingCart {
  // ...省略其他代码...
  public void clear() {
    items.clear();
    itemsCount = 0;
    totalPrice = 0.0;
  }
}
```

你可能还会说，我有一个需求，需要查看购物车中都买了啥，那这个时候，ShoppingCart 类不得不提供 items 属性的 getter 方法了，那又该怎么办才好呢？

如果你熟悉 Java 语言，那解决这个问题的方法还是挺简单的。我们可以通过 Java 提供的 Collections.unmodifiableList() 方法，让 getter 方法返回一个不可被修改的 UnmodifiableList 集合容器，而这个容器类重写了 List 容器中跟修改数据相关的方法，比如 add()、clear() 等方法。一旦我们调用这些修改数据的方法，代码就会抛出 UnsupportedOperationException 异常，这样就避免了容器中的数据被修改。具体的代码实现如下所示。

```java
public class ShoppingCart {
  // ...省略其他代码...
  public List<ShoppingCartItem> getItems() {
    return Collections.unmodifiableList(this.items);
  }
}

public class UnmodifiableList<E> extends UnmodifiableCollection<E>
                          implements List<E> {
  public boolean add(E e) {
    throw new UnsupportedOperationException();
  }
  public void clear() {
    throw new UnsupportedOperationException();
  }
  // ...省略其他代码...
}

ShoppingCart cart = new ShoppingCart();
List<ShoppingCartItem> items = cart.getItems();
items.clear();//抛出UnsupportedOperationException异常
```

不过，这样的实现思路还是有点问题。因为当调用者通过 ShoppingCart 的 getItems() 获取到 items 之后，虽然我们没法修改容器中的数据，但我们仍然可以修改容器中每个对象（ShoppingCartItem）的数据。听起来有点绕，看看下面这几行代码你就明白了。

```java
ShoppingCart cart = new ShoppingCart();
cart.add(new ShoppingCartItem(...));
List<ShoppingCartItem> items = cart.getItems();
ShoppingCartItem item = items.get(0);
item.setPrice(19.0); // 这里修改了item的价格属性
```

getter、setter 问题我们就讲完了，我稍微总结一下，在设计实现类的时候，除非真的需要，否则，尽量不要给属性定义 setter 方法。除此之外，尽管 getter 方法相对 setter 方法要安全些，但是如果返回的是集合容器（比如例子中的 List 容器），也要防范集合内部数据被修改的危险。

2. 滥用全局变量和全局方法

我们再来看，另外一个违反面向对象编程风格的例子，那就是滥用全局变量和全局方法。首先，我们先来看，什么是全局变量和全局方法？

如果你是用类似 C 语言这样的面向过程的编程语言来做开发，那对全局变量、全局方法肯定不陌生，甚至可以说，在代码中到处可见。但如果你是用类似 Java 这样的面向对象的编程语言来做开发，全局变量和全局方法就不是很多见了。

在面向对象编程中，常见的全局变量有单例类对象、静态成员变量、常量等，常见的全局方法有静态方法。单例类对象在全局代码中只有一份，所以，它相当于一个全局变量。静态成员变量归属于类上的数据，被所有的实例化对象所共享，也相当于一定程度上的全局变量。而常量是一种非常常见的全局变量，比如一些代码中的配置参数，一般都设置为常量，放到一个 Constants 类中。静态方法一般用来操作静态变量或者外部数据。你可以联想一下我们常用的各种 Utils 类，里面的方法一般都会定义成静态方法，可以在不用创建对象的情况下，直接拿来使用。静态方法将方法与数据分离，破坏了封装特性，是典型的面向过程风格。

在刚刚介绍的这些全局变量和全局方法中，Constants 类和 Utils 类最常用到。现在，我们就结合这两个几乎在每个软件开发中都会用到的类，来深入探讨一下全局变量和全局方法的利与弊。

我们先来看一下，在我过去参与的项目中，一种常见的 Constants 类的定义方法。

```java
public class Constants {
  public static final String MYSQL_ADDR_KEY = "mysql_addr";
  public static final String MYSQL_DB_NAME_KEY = "db_name";
  public static final String MYSQL_USERNAME_KEY = "mysql_username";
  public static final String MYSQL_PASSWORD_KEY = "mysql_password";
  
  public static final String REDIS_DEFAULT_ADDR = "192.168.7.2:7234";
  public static final int REDIS_DEFAULT_MAX_TOTAL = 50;
  public static final int REDIS_DEFAULT_MAX_IDLE = 50;
  public static final int REDIS_DEFAULT_MIN_IDLE = 20;
  public static final String REDIS_DEFAULT_KEY_PREFIX = "rt:";
  
  // ...省略更多的常量定义...
}
```

在这段代码中，我们把程序中所有用到的常量，都集中地放到这个 Constants 类中。不过，定义一个如此大而全的 Constants 类，并不是一种很好的设计思路。为什么这么说呢？原因主要有以下几点。

首先，这样的设计会影响代码的可维护性。

如果参与开发同一个项目的工程师有很多，在开发过程中，可能都要涉及修改这个类，比如往这个类里添加常量，那这个类就会变得越来越大，成百上千行都有可能，查找修改某个常量也会变得比较费时，而且还会增加提交代码冲突的概率。

其次，这样的设计还会增加代码的编译时间。

当 Constants 类中包含很多常量定义的时候，依赖这个类的代码就会很多。那每次修改 Constants 类，都会导致依赖它的类文件重新编译，因此会浪费很多不必要的编译时间。不要小看编译花费的时间，对于一个非常大的工程项目来说，编译一次项目花费的时间可能是几分钟，甚至几十分钟。而我们在开发过程中，每次运行单元测试，都会触发一次编译的过程，这个编译时间就有可能会影响到我们的开发效率。

最后，这样的设计还会影响代码的复用性。

如果我们要在另一个项目中，复用本项目开发的某个类，而这个类又依赖 Constants 类。即便这个类只依赖 Constants 类中的一小部分常量，我们仍然需要把整个 Constants 类也一并引入，也就引入了很多无关的常量到新的项目中。

那如何改进 Constants 类的设计呢？

我这里有两种思路可以借鉴。第一种是将 Constants 类拆解为功能更加单一的多个类，比如跟 MySQL 配置相关的常量，我们放到 MysqlConstants 类中；跟 Redis 配置相关的常量，我们放到 RedisConstants 类中。当然，还有一种我个人觉得更好的设计思路，那就是并不单独地设计 Constants 常量类，而是哪个类用到了某个常量，我们就把这个常量定义到这个类中。比如，RedisConfig 类用到了 Redis 配置相关的常量，那我们就直接将这些常量定义在 RedisConfig 中，这样也提高了类设计的内聚性和代码的复用性。

讲完了 Constants 类，我们再来讨论一下 Utils 类。首先，我想问你这样一个问题，我们为什么需要 Utils 类？Utils 类存在的意义是什么？希望你先思考一下，然后再来看我下面的讲解。

实际上，Utils 类的出现是基于这样一个问题背景：如果我们有两个类 A 和 B，它们要用到一块相同的功能逻辑，为了避免代码重复，我们不应该在两个类中，将这个相同的功能逻辑，重复地实现两遍。这个时候我们该怎么办呢？

我们在讲面向对象特性的时候，讲过继承可以实现代码复用。利用继承特性，我们把相同的属性和方法，抽取出来，定义到父类中。子类复用父类中的属性和方法，达到代码复用的目的。但是，有的时候，从业务含义上，A 类和 B 类并不一定具有继承关系，比如 Crawler 类和 PageAnalyzer 类，它们都用到了 URL 拼接和分割的功能，但并不具有继承关系（既不是父子关系，也不是兄弟关系）。仅仅为了代码复用，生硬地抽象出一个父类出来，会影响到代码的可读性。如果不熟悉背后设计思路的同事，发现 Crawler 类和 PageAnalyzer 类继承同一个父类，而父类中定义的却是 URL 相关的操作，会觉得这个代码写得莫名其妙，理解不了。

既然继承不能解决这个问题，我们可以定义一个新的类，实现 URL 拼接和分割的方法。而拼接和分割两个方法，不需要共享任何数据，所以新的类不需要定义任何属性，这个时候，我们就可以把它定义为只包含静态方法的 Utils 类了。

实际上，只包含静态方法不包含任何属性的 Utils 类，是彻彻底底的面向过程的编程风格。但这并不是说，我们就要杜绝使用 Utils 类了。实际上，从刚刚讲的 Utils 类存在的目的来看，它在软件开发中还是挺有用的，能解决代码复用问题。所以，这里并不是说完全不能用 Utils 类，而是说，要尽量避免滥用，不要不加思考地随意去定义 Utils 类。

在定义 Utils 类之前，你要问一下自己，你真的需要单独定义这样一个 Utils 类吗？是否可以把 Utils 类中的某些方法定义到其他类中呢？如果在回答完这些问题之后，你还是觉得确实有必要去定义这样一个 Utils 类，那就大胆地去定义它吧。因为即便在面向对象编程中，我们也并不是完全排斥面向过程风格的代码。只要它能为我们写出好的代码贡献力量，我们就可以适度地去使用。

除此之外，类比 Constants 类的设计，我们设计 Utils 类的时候，最好也能细化一下，针对不同的功能，设计不同的 Utils 类，比如 FileUtils、IOUtils、StringUtils、UrlUtils 等，不要设计一个过于大而全的 Utils 类。

3. 定义数据和方法分离的类

我们再来看最后一种面向对象编程过程中，常见的面向过程风格的代码。那就是，数据定义在一个类中，方法定义在另一个类中。你可能会觉得，这么明显的面向过程风格的代码，谁会这么写呢？实际上，如果你是基于 MVC 三层结构做 Web 方面的后端开发，这样的代码你可能天天都在写。

传统的 MVC 结构分为 Model 层、Controller 层、View 层这三层。不过，在做前后端分离之后，三层结构在后端开发中，会稍微有些调整，被分为 Controller 层、Service 层、Repository 层。Controller 层负责暴露接口给前端调用，Service 层负责核心业务逻辑，Repository 层负责数据读写。而在每一层中，我们又会定义相应的 VO（View Object）、BO（Business Object）、Entity。一般情况下，VO、BO、Entity 中只会定义数据，不会定义方法，所有操作这些数据的业务逻辑都定义在对应的 Controller 类、Service 类、Repository 类中。这就是典型的面向过程的编程风格。

实际上，这种开发模式叫作基于贫血模型的开发模式，也是我们现在非常常用的一种 Web 项目的开发模式。看到这里，你内心里应该有很多疑惑吧？既然这种开发模式明显违背面向对象的编程风格，为什么大部分 Web 项目都是基于这种开发模式来开发呢？

### 在面向对象编程中，为什么容易写出面向过程风格的代码？

我们在进行面向对象编程的时候，很容易不由自主地就写出面向过程风格的代码，或者说感觉面向过程风格的代码更容易写。这是为什么呢？

你可以联想一下，在生活中，你去完成一个任务，你一般都会思考，应该先做什么、后做什么，如何一步一步地顺序执行一系列操作，最后完成整个任务。面向过程编程风格恰恰符合人的这种流程化思维方式。而面向对象编程风格正好相反。它是一种自底向上的思考方式。它不是先去按照执行流程来分解任务，而是将任务翻译成一个一个的小的模块（也就是类），设计类之间的交互，最后按照流程将类组装起来，完成整个任务。我们在上一节课讲到了，这样的思考路径比较适合复杂程序的开发，但并不是特别符合人类的思考习惯。

除此之外，面向对象编程要比面向过程编程难一些。在面向对象编程中，类的设计还是挺需要技巧，挺需要一定设计经验的。你要去思考如何封装合适的数据和方法到一个类里，如何设计类之间的关系，如何设计类之间的交互等等诸多设计问题。

### 面向过程编程及面向过程编程语言就真的无用武之地了吗？

前面我们讲了面向对象编程相比面向过程编程的各种优势，又讲了哪些代码看起来像面向对象风格，而实际上是面向过程编程风格的。那是不是面向过程编程风格就过时了被淘汰了呢？是不是在面向对象编程开发中，我们就要杜绝写面向过程风格的代码呢？

前面我们有讲到，如果我们开发的是微小程序，或者是一个数据处理相关的代码，以算法为主，数据为辅，那脚本式的面向过程的编程风格就更适合一些。当然，面向过程编程的用武之地还不止这些。实际上，面向过程编程是面向对象编程的基础，面向对象编程离不开基础的面向过程编程。为什么这么说？我们仔细想想，类中每个方法的实现逻辑，不就是面向过程风格的代码吗？

除此之外，面向对象和面向过程两种编程风格，也并不是非黑即白、完全对立的。在用面向对象编程语言开发的软件中，面向过程风格的代码并不少见，甚至在一些标准的开发库（比如 JDK、Apache Commons、Google Guava）中，也有很多面向过程风格的代码。

不管使用面向过程还是面向对象哪种风格来写代码，我们最终的目的还是写出易维护、易读、易复用、易扩展的高质量代码。只要我们能避免面向过程编程风格的一些弊端，控制好它的副作用，在掌控范围内为我们所用，我们就大可不用避讳在面向对象编程中写面向过程风格的代码。

### 总结

1. 滥用 getter、setter 方法

在设计实现类的时候，除非真的需要，否则尽量不要给属性定义 setter 方法。除此之外，尽管 getter 方法相对 setter 方法要安全些，但是如果返回的是集合容器，那也要防范集合内部数据被修改的风险。

2. Constants 类、Utils 类的设计问题

对于这两种类的设计，我们尽量能做到职责单一，定义一些细化的小类，比如 RedisConstants、FileUtils，而不是定义一个大而全的 Constants 类、Utils 类。除此之外，如果能将这些类中的属性和方法，划分归并到其他业务类中，那是最好不过的了，能极大地提高类的内聚性和代码的可复用性。

3. 基于贫血模型的开发模式

关于这一部分，我们只讲了为什么这种开发模式是彻彻底底的面向过程编程风格的。这是因为数据和操作是分开定义在 VO/BO/Entity 和 Controler/Service/Repository 中的。今天，你只需要掌握这一点就可以了。为什么这种开发模式如此流行？如何规避面向过程编程的弊端？有没有更好的可替代的开发模式？

## 08 | 理论五：接口vs抽象类的区别？如何用普通的类模拟抽象类和接口？

在面向对象编程中，抽象类和接口是两个经常被用到的语法概念，是面向对象四大特性，以及很多设计模式、设计思想、设计原则编程实现的基础。比如，我们可以使用接口来实现面向对象的抽象特性、多态特性和基于接口而非实现的设计原则，使用抽象类来实现面向对象的继承特性和模板设计模式等等。

不过，并不是所有的面向对象编程语言都支持这两个语法概念，比如，C++ 这种编程语言只支持抽象类，不支持接口；而像 Python 这样的动态编程语言，既不支持抽象类，也不支持接口。尽管有些编程语言没有提供现成的语法来支持接口和抽象类，我们仍然可以通过一些手段来模拟实现这两个语法概念。

### 什么是抽象类和接口？区别在哪里？

首先，我们来看一下，在 Java 这种编程语言中，我们是如何定义抽象类的。

下面这段代码是一个比较典型的抽象类的使用场景（模板设计模式）。Logger 是一个记录日志的抽象类，FileLogger 和 MessageQueueLogger 继承 Logger，分别实现两种不同的日志记录方式：记录日志到文件中和记录日志到消息队列中。FileLogger 和 MessageQueueLogger 两个子类复用了父类 Logger 中的 name、enabled、minPermittedLevel 属性和 log() 方法，但因为这两个子类写日志的方式不同，它们又各自重写了父类中的 doLog() 方法。

```java
// 抽象类
public abstract class Logger {
  private String name;
  private boolean enabled;
  private Level minPermittedLevel;

  public Logger(String name, boolean enabled, Level minPermittedLevel) {
    this.name = name;
    this.enabled = enabled;
    this.minPermittedLevel = minPermittedLevel;
  }
  
  public void log(Level level, String message) {
    boolean loggable = enabled && (minPermittedLevel.intValue() <= level.intValue());
    if (!loggable) return;
    doLog(level, message);
  }
  
  protected abstract void doLog(Level level, String message);
}
// 抽象类的子类：输出日志到文件
public class FileLogger extends Logger {
  private Writer fileWriter;

  public FileLogger(String name, boolean enabled,
    Level minPermittedLevel, String filepath) {
    super(name, enabled, minPermittedLevel);
    this.fileWriter = new FileWriter(filepath); 
  }
  
  @Override
  public void doLog(Level level, String mesage) {
    // 格式化level和message,输出到日志文件
    fileWriter.write(...);
  }
}
// 抽象类的子类: 输出日志到消息中间件(比如kafka)
public class MessageQueueLogger extends Logger {
  private MessageQueueClient msgQueueClient;
  
  public MessageQueueLogger(String name, boolean enabled,
    Level minPermittedLevel, MessageQueueClient msgQueueClient) {
    super(name, enabled, minPermittedLevel);
    this.msgQueueClient = msgQueueClient;
  }
  
  @Override
  protected void doLog(Level level, String mesage) {
    // 格式化level和message,输出到消息中间件
    msgQueueClient.send(...);
  }
}
```

通过上面的这个例子，我们来看一下，抽象类具有哪些特性。我总结了下面三点。

- 抽象类不允许被实例化，只能被继承。也就是说，你不能 new 一个抽象类的对象出来（Logger logger = new Logger(…); 会报编译错误）。
- 抽象类可以包含属性和方法。方法既可以包含代码实现（比如 Logger 中的 log() 方法），也可以不包含代码实现（比如 Logger 中的 doLog() 方法）。不包含代码实现的方法叫作抽象方法。
- 子类继承抽象类，必须实现抽象类中的所有抽象方法。对应到例子代码中就是，所有继承 Logger 抽象类的子类，都必须重写 doLog() 方法。

刚刚我们讲了如何定义抽象类，现在我们再来看一下，在 Java 这种编程语言中，我们如何定义接口。

```java
// 接口
public interface Filter {
  void doFilter(RpcRequest req) throws RpcException;
}
// 接口实现类：鉴权过滤器
public class AuthencationFilter implements Filter {
  @Override
  public void doFilter(RpcRequest req) throws RpcException {
    //...鉴权逻辑..
  }
}
// 接口实现类：限流过滤器
public class RateLimitFilter implements Filter {
  @Override
  public void doFilter(RpcRequest req) throws RpcException {
    //...限流逻辑...
  }
}
// 过滤器使用Demo
public class Application {
  // filters.add(new AuthencationFilter());
  // filters.add(new RateLimitFilter());
  private List<Filter> filters = new ArrayList<>();
  
  public void handleRpcRequest(RpcRequest req) {
    try {
      for (Filter filter : filters) {
        filter.doFilter(req);
      }
    } catch(RpcException e) {
      // ...处理过滤结果...
    }
    // ...省略其他处理逻辑...
  }
}
```

上面这段代码是一个比较典型的接口的使用场景。我们通过 Java 中的 interface 关键字定义了一个 Filter 接口。AuthencationFilter 和 RateLimitFilter 是接口的两个实现类，分别实现了对 RPC 请求鉴权和限流的过滤功能。

代码非常简洁。结合代码，我们再来看一下，接口都有哪些特性。我也总结了三点。

- 接口不能包含属性（也就是成员变量）。
- 接口只能声明方法，方法不能包含代码实现。
- 类实现接口的时候，必须实现接口中声明的所有方法。

前面我们讲了抽象类和接口的定义，以及各自的语法特性。从语法特性上对比，这两者有比较大的区别，比如抽象类中可以定义属性、方法的实现，而接口中不能定义属性，方法也不能包含代码实现等等。除了语法特性，从设计的角度，两者也有比较大的区别。

抽象类实际上就是类，只不过是一种特殊的类，这种类不能被实例化为对象，只能被子类继承。我们知道，继承关系是一种 is-a 的关系，那抽象类既然属于类，也表示一种 is-a 的关系。相对于抽象类的 is-a 关系来说，接口表示一种 has-a 关系，表示具有某些功能。对于接口，有一个更加形象的叫法，那就是协议（contract）。

### 抽象类和接口能解决什么编程问题？

首先，我们来看一下，我们为什么需要抽象类？它能够解决什么编程问题？

刚刚我们讲到，抽象类不能实例化，只能被继承。而前面的章节中，我们还讲到，继承能解决代码复用的问题。所以，抽象类也是为代码复用而生的。多个子类可以继承抽象类中定义的属性和方法，避免在子类中，重复编写相同的代码。

不过，既然继承本身就能达到代码复用的目的，而继承也并不要求父类一定是抽象类，那我们不使用抽象类，照样也可以实现继承和复用。从这个角度上来讲，我们貌似并不需要抽象类这种语法呀。那抽象类除了解决代码复用的问题，还有什么其他存在的意义吗？

我们还是拿之前那个打印日志的例子来讲解。我们先对上面的代码做下改造。在改造之后的代码中，Logger 不再是抽象类，只是一个普通的父类，删除了 Logger 中 log()、doLog() 方法，新增了 isLoggable() 方法。FileLogger 和 MessageQueueLogger 还是继承 Logger 父类，以达到代码复用的目的。具体的代码如下：

```java
// 父类：非抽象类，就是普通的类. 删除了log(),doLog()，新增了isLoggable().
public class Logger {
  private String name;
  private boolean enabled;
  private Level minPermittedLevel;

  public Logger(String name, boolean enabled, Level minPermittedLevel) {
    //...构造函数不变，代码省略...
  }

  protected boolean isLoggable() {
    boolean loggable = enabled && (minPermittedLevel.intValue() <= level.intValue());
    return loggable;
  }
}
// 子类：输出日志到文件
public class FileLogger extends Logger {
  private Writer fileWriter;

  public FileLogger(String name, boolean enabled,
    Level minPermittedLevel, String filepath) {
    //...构造函数不变，代码省略...
  }
  
  public void log(Level level, String mesage) {
    if (!isLoggable()) return;
    // 格式化level和message,输出到日志文件
    fileWriter.write(...);
  }
}
// 子类: 输出日志到消息中间件(比如kafka)
public class MessageQueueLogger extends Logger {
  private MessageQueueClient msgQueueClient;
  
  public MessageQueueLogger(String name, boolean enabled,
    Level minPermittedLevel, MessageQueueClient msgQueueClient) {
    //...构造函数不变，代码省略...
  }
  
  public void log(Level level, String mesage) {
    if (!isLoggable()) return;
    // 格式化level和message,输出到消息中间件
    msgQueueClient.send(...);
  }
}
```

这个设计思路虽然达到了代码复用的目的，但是无法使用多态特性了。像下面这样编写代码，就会出现编译错误，因为 Logger 中并没有定义 log() 方法。

```java
Logger logger = new FileLogger("access-log", true, Level.WARN, "/users/wangzheng/access.log");
logger.log(Level.ERROR, "This is a test log message.");
```

你可能会说，这个问题解决起来很简单啊。我们在 Logger 父类中，定义一个空的 log() 方法，让子类重写父类的 log() 方法，实现自己的记录日志的逻辑，不就可以了吗？

```java
public class Logger {
  // ...省略部分代码...
  public void log(Level level, String mesage) { // do nothing... }
}
public class FileLogger extends Logger {
  // ...省略部分代码...
  @Override
  public void log(Level level, String mesage) {
    if (!isLoggable()) return;
    // 格式化level和message,输出到日志文件
    fileWriter.write(...);
  }
}
public class MessageQueueLogger extends Logger {
  // ...省略部分代码...
  @Override
  public void log(Level level, String mesage) {
    if (!isLoggable()) return;
    // 格式化level和message,输出到消息中间件
    msgQueueClient.send(...);
  }
}
```

这个设计思路能用，但是，它显然没有之前通过抽象类的实现思路优雅。我为什么这么说呢？主要有以下几点原因。

- 在 Logger 中定义一个空的方法，会影响代码的可读性。如果我们不熟悉 Logger 背后的设计思想，代码注释又不怎么给力，我们在阅读 Logger 代码的时候，就可能对为什么定义一个空的 log() 方法而感到疑惑，需要查看 Logger、FileLogger、MessageQueueLogger 之间的继承关系，才能弄明白其设计意图。
- 当创建一个新的子类继承 Logger 父类的时候，我们有可能会忘记重新实现 log() 方法。之前基于抽象类的设计思路，编译器会强制要求子类重写 log() 方法，否则会报编译错误。你可能会说，我既然要定义一个新的 Logger 子类，怎么会忘记重新实现 log() 方法呢？我们举的例子比较简单，Logger 中的方法不多，代码行数也很少。但是，如果 Logger 有几百行，有 n 多方法，除非你对 Logger 的设计非常熟悉，否则忘记重新实现 log() 方法，也不是不可能的。
- Logger 可以被实例化，换句话说，我们可以 new 一个 Logger 出来，并且调用空的 log() 方法。这也增加了类被误用的风险。当然，这个问题可以通过设置私有的构造函数的方式来解决。不过，显然没有通过抽象类来的优雅。

**其次，我们再来看一下，我们为什么需要接口？它能够解决什么编程问题？**

抽象类更多的是为了代码复用，而接口就更侧重于解耦。接口是对行为的一种抽象，相当于一组协议或者契约，你可以联想类比一下 API 接口。调用者只需要关注抽象的接口，不需要了解具体的实现，具体的实现代码对调用者透明。接口实现了约定和实现相分离，可以降低代码间的耦合性，提高代码的可扩展性。

实际上，接口是一个比抽象类应用更加广泛、更加重要的知识点。比如，我们经常提到的“基于接口而非实现编程”，就是一条几乎天天会用到，并且能极大地提高代码的灵活性、扩展性的设计思想。关于接口这个知识点，我会单独再用一节课的时间，更加详细全面的讲解，这里就不展开了。

**如何模拟抽象类和接口两个语法概念？**

在前面举的例子中，我们使用 Java 的接口语法实现了一个 Filter 过滤器。不过，如果你熟悉的是 C++ 这种编程语言，你可能会说，C++ 只有抽象类，并没有接口，那从代码实现的角度上来说，是不是就无法实现 Filter 的设计思路了呢？

实际上，我们可以通过抽象类来模拟接口。怎么来模拟呢？这是一个不错的面试题，你可以先思考一下，然后再来看我的讲解。

我们先来回忆一下接口的定义：接口中没有成员变量，只有方法声明，没有方法实现，实现接口的类必须实现接口中的所有方法。只要满足这样几点，从设计的角度上来说，我们就可以把它叫作接口。实际上，要满足接口的这些语法特性并不难。在下面这段 C++ 代码中，我们就用抽象类模拟了一个接口（下面这段代码实际上是策略模式中的一段代码）。

```
class Strategy { // 用抽象类模拟接口
  public:
    ~Strategy();
    virtual void algorithm()=0;
  protected:
    Strategy();
};
```

抽象类 Strategy 没有定义任何属性，并且所有的方法都声明为 virtual 类型（等同于 Java 中的 abstract 关键字），这样，所有的方法都不能有代码实现，并且所有继承这个抽象类的子类，都要实现这些方法。从语法特性上来看，这个抽象类就相当于一个接口。

不过，如果你熟悉的既不是 Java，也不是 C++，而是现在比较流行的动态编程语言，比如 Python、Ruby 等，你可能还会有疑问：在这些动态语言中，不仅没有接口的概念，也没有类似 abstract、virtual 这样的关键字来定义抽象类，那该如何实现上面的讲到的 Filter、Logger 的设计思路呢？实际上，除了用抽象类来模拟接口之外，我们还可以用普通类来模拟接口。具体的 Java 代码实现如下所示。

```
public class MockInteface {
  protected MockInteface() {}
  public void funcA() {
    throw new MethodUnSupportedException();
  }
}
```

我们知道类中的方法必须包含实现，这个不符合接口的定义。但是，我们可以让类中的方法抛出 MethodUnSupportedException 异常，来模拟不包含实现的接口，并且能强迫子类在继承这个父类的时候，都去主动实现父类的方法，否则就会在运行时抛出异常。那又如何避免这个类被实例化呢？实际上很简单，我们只需要将这个类的构造函数声明为 protected 访问权限就可以了。

刚刚我们讲了如何用抽象类来模拟接口，以及如何用普通类来模拟接口，那如何用普通类来模拟抽象类呢？这个问题留给你自己思考，你可以留言说说你的实现方法。

### 如何决定该用抽象类还是接口？

刚刚的讲解可能有些偏理论，现在，我们就从真实项目开发的角度来看一下，在代码设计、编程开发的时候，什么时候该用抽象类？什么时候该用接口？

实际上，判断的标准很简单。如果我们要表示一种 is-a 的关系，并且是为了解决代码复用的问题，我们就用抽象类；如果我们要表示一种 has-a 关系，并且是为了解决抽象而非代码复用的问题，那我们就可以使用接口。

从类的继承层次上来看，抽象类是一种自下而上的设计思路，先有子类的代码重复，然后再抽象成上层的父类（也就是抽象类）。而接口正好相反，它是一种自上而下的设计思路。我们在编程的时候，一般都是先设计接口，再去考虑具体的实现。

### 总结

1. 抽象类和接口的语法特性

抽象类不允许被实例化，只能被继承。它可以包含属性和方法。方法既可以包含代码实现，也可以不包含代码实现。不包含代码实现的方法叫作抽象方法。子类继承抽象类，必须实现抽象类中的所有抽象方法。接口不能包含属性，只能声明方法，方法不能包含代码实现。类实现接口的时候，必须实现接口中声明的所有方法。

2. 抽象类和接口存在的意义

抽象类是对成员变量和方法的抽象，是一种 is-a 关系，是为了解决代码复用问题。接口仅仅是对方法的抽象，是一种 has-a 关系，表示具有某一组行为特性，是为了解决解耦问题，隔离接口和具体的实现，提高代码的扩展性。

3. 抽象类和接口的应用场景区别

什么时候该用抽象类？什么时候该用接口？实际上，判断的标准很简单。如果要表示一种 is-a 的关系，并且是为了解决代码复用问题，我们就用抽象类；如果要表示一种 has-a 关系，并且是为了解决抽象而非代码复用问题，那我们就用接口。

## 09 | 理论六：为什么基于接口而非实现编程？有必要为每个类都定义接口吗？

### 如何解读原则中的“接口”二字？

“基于接口而非实现编程”这条原则的英文描述是：“Program to an interface, not an implementation”。我们理解这条原则的时候，千万不要一开始就与具体的编程语言挂钩，局限在编程语言的“接口”语法中（比如 Java 中的 interface 接口语法）。这条原则最早出现于 1994 年 GoF 的《设计模式》这本书，它先于很多编程语言而诞生（比如 Java 语言），是一条比较抽象、泛化的设计思想。

实际上，理解这条原则的关键，就是理解其中的“接口”两个字。还记得我们上一节课讲的“接口”的定义吗？从本质上来看，“接口”就是一组“协议”或者“约定”，是功能提供者提供给使用者的一个“功能列表”。“接口”在不同的应用场景下会有不同的解读，比如服务端与客户端之间的“接口”，类库提供的“接口”，甚至是一组通信的协议都可以叫作“接口”。刚刚对“接口”的理解，都比较偏上层、偏抽象，与实际的写代码离得有点远。如果落实到具体的编码，“基于接口而非实现编程”这条原则中的“接口”，可以理解为编程语言中的接口或者抽象类。

前面我们提到，这条原则能非常有效地提高代码质量，之所以这么说，那是因为，应用这条原则，可以将接口和实现相分离，封装不稳定的实现，暴露稳定的接口。上游系统面向接口而非实现编程，不依赖不稳定的实现细节，这样当实现发生变化的时候，上游系统的代码基本上不需要做改动，以此来降低耦合性，提高扩展性。

实际上，“基于接口而非实现编程”这条原则的另一个表述方式，是“基于抽象而非实现编程”。后者的表述方式其实更能体现这条原则的设计初衷。在软件开发中，最大的挑战之一就是需求的不断变化，这也是考验代码设计好坏的一个标准。越抽象、越顶层、越脱离具体某一实现的设计，越能提高代码的灵活性，越能应对未来的需求变化。好的代码设计，不仅能应对当下的需求，而且在将来需求发生变化的时候，仍然能够在不破坏原有代码设计的情况下灵活应对。而抽象就是提高代码扩展性、灵活性、可维护性最有效的手段之一。

### 如何将这条原则应用到实战中？

对于这条原则，我们结合一个具体的实战案例来进一步讲解一下。假设我们的系统中有很多涉及图片处理和存储的业务逻辑。图片经过处理之后被上传到阿里云上。为了代码复用，我们封装了图片存储相关的代码逻辑，提供了一个统一的 AliyunImageStore 类，供整个系统来使用。具体的代码实现如下所示：

```java
public class AliyunImageStore {
  //...省略属性、构造函数等...
  
  public void createBucketIfNotExisting(String bucketName) {
    // ...创建bucket代码逻辑...
    // ...失败会抛出异常..
  }
  
  public String generateAccessToken() {
    // ...根据accesskey/secrectkey等生成access token
  }
  
  public String uploadToAliyun(Image image, String bucketName, String accessToken) {
    //...上传图片到阿里云...
    //...返回图片存储在阿里云上的地址(url）...
  }
  
  public Image downloadFromAliyun(String url, String accessToken) {
    //...从阿里云下载图片...
  }
}

// AliyunImageStore类的使用举例
public class ImageProcessingJob {
  private static final String BUCKET_NAME = "ai_images_bucket";
  //...省略其他无关代码...
  
  public void process() {
    Image image = ...; //处理图片，并封装为Image对象
    AliyunImageStore imageStore = new AliyunImageStore(/*省略参数*/);
    imageStore.createBucketIfNotExisting(BUCKET_NAME);
    String accessToken = imageStore.generateAccessToken();
    imagestore.uploadToAliyun(image, BUCKET_NAME, accessToken);
  }
  
}
```

整个上传流程包含三个步骤：创建 bucket（你可以简单理解为存储目录）、生成 access token 访问凭证、携带 access token 上传图片到指定的 bucket 中。代码实现非常简单，类中的几个方法定义得都很干净，用起来也很清晰，乍看起来没有太大问题，完全能满足我们将图片存储在阿里云的业务需求。

不过，软件开发中唯一不变的就是变化。过了一段时间后，我们自建了私有云，不再将图片存储到阿里云了，而是将图片存储到自建私有云上。为了满足这样一个需求的变化，我们该如何修改代码呢？

我们需要重新设计实现一个存储图片到私有云的 PrivateImageStore 类，并用它替换掉项目中所有的 AliyunImageStore 类对象。这样的修改听起来并不复杂，只是简单替换而已，对整个代码的改动并不大。不过，我们经常说，“细节是魔鬼”。这句话在软件开发中特别适用。实际上，刚刚的设计实现方式，就隐藏了很多容易出问题的“魔鬼细节”，我们一块来看看都有哪些。

新的 PrivateImageStore 类需要设计实现哪些方法，才能在尽量最小化代码修改的情况下，替换掉 AliyunImageStore 类呢？这就要求我们必须将 AliyunImageStore 类中所定义的所有 public 方法，在 PrivateImageStore 类中都逐一定义并重新实现一遍。而这样做就会存在一些问题，我总结了下面两点。

首先，AliyunImageStore 类中有些函数命名暴露了实现细节，比如，uploadToAliyun() 和 downloadFromAliyun()。如果开发这个功能的同事没有接口意识、抽象思维，那这种暴露实现细节的命名方式就不足为奇了，毕竟最初我们只考虑将图片存储在阿里云上。而我们把这种包含“aliyun”字眼的方法，照抄到 PrivateImageStore 类中，显然是不合适的。如果我们在新类中重新命名 uploadToAliyun()、downloadFromAliyun() 这些方法，那就意味着，我们要修改项目中所有使用到这两个方法的代码，代码修改量可能就会很大。

其次，将图片存储到阿里云的流程，跟存储到私有云的流程，可能并不是完全一致的。比如，阿里云的图片上传和下载的过程中，需要生产 access token，而私有云不需要 access token。一方面，AliyunImageStore 中定义的 generateAccessToken() 方法不能照抄到 PrivateImageStore 中；另一方面，我们在使用 AliyunImageStore 上传、下载图片的时候，代码中用到了 generateAccessToken() 方法，如果要改为私有云的上传下载流程，这些代码都需要做调整。

那这两个问题该如何解决呢？解决这个问题的根本方法就是，在编写代码的时候，要遵从“基于接口而非实现编程”的原则，具体来讲，我们需要做到下面这 3 点。

1. 函数的命名不能暴露任何实现细节。比如，前面提到的 uploadToAliyun() 就不符合要求，应该改为去掉 aliyun 这样的字眼，改为更加抽象的命名方式，比如：upload()。
2. 封装具体的实现细节。比如，跟阿里云相关的特殊上传（或下载）流程不应该暴露给调用者。我们对上传（或下载）流程进行封装，对外提供一个包裹所有上传（或下载）细节的方法，给调用者使用。
3. 为实现类定义抽象的接口。具体的实现类都依赖统一的接口定义，遵从一致的上传功能协议。使用者依赖接口，而不是具体的实现类来编程。

我们按照这个思路，把代码重构一下。重构后的代码如下所示：

```java

public interface ImageStore {
  String upload(Image image, String bucketName);
  Image download(String url);
}

public class AliyunImageStore implements ImageStore {
  //...省略属性、构造函数等...

  public String upload(Image image, String bucketName) {
    createBucketIfNotExisting(bucketName);
    String accessToken = generateAccessToken();
    //...上传图片到阿里云...
    //...返回图片在阿里云上的地址(url)...
  }

  public Image download(String url) {
    String accessToken = generateAccessToken();
    //...从阿里云下载图片...
  }

  private void createBucketIfNotExisting(String bucketName) {
    // ...创建bucket...
    // ...失败会抛出异常..
  }

  private String generateAccessToken() {
    // ...根据accesskey/secrectkey等生成access token
  }
}

// 上传下载流程改变：私有云不需要支持access token
public class PrivateImageStore implements ImageStore  {
  public String upload(Image image, String bucketName) {
    createBucketIfNotExisting(bucketName);
    //...上传图片到私有云...
    //...返回图片的url...
  }

  public Image download(String url) {
    //...从私有云下载图片...
  }

  private void createBucketIfNotExisting(String bucketName) {
    // ...创建bucket...
    // ...失败会抛出异常..
  }
}

// ImageStore的使用举例
public class ImageProcessingJob {
  private static final String BUCKET_NAME = "ai_images_bucket";
  //...省略其他无关代码...
  
  public void process() {
    Image image = ...;//处理图片，并封装为Image对象
    ImageStore imageStore = new PrivateImageStore(...);
    imagestore.upload(image, BUCKET_NAME);
  }
}
```

除此之外，很多人在定义接口的时候，希望通过实现类来反推接口的定义。先把实现类写好，然后看实现类中有哪些方法，照抄到接口定义中。如果按照这种思考方式，就有可能导致接口定义不够抽象，依赖具体的实现。这样的接口设计就没有意义了。不过，如果你觉得这种思考方式更加顺畅，那也没问题，只是将实现类的方法搬移到接口定义中的时候，要有选择性的搬移，不要将跟具体实现相关的方法搬移到接口中，比如 AliyunImageStore 中的 generateAccessToken() 方法。

总结一下，我们在做软件开发的时候，一定要有抽象意识、封装意识、接口意识。在定义接口的时候，不要暴露任何实现细节。接口的定义只表明做什么，而不是怎么做。而且，在设计接口的时候，我们要多思考一下，这样的接口设计是否足够通用，是否能够做到在替换具体的接口实现的时候，不需要任何接口定义的改动。

### 是否需要为每个类定义接口？

看了刚刚的讲解，你可能会有这样的疑问：为了满足这条原则，我是不是需要给每个实现类都定义对应的接口呢？在开发的时候，是不是任何代码都要只依赖接口，完全不依赖实现编程呢？

做任何事情都要讲求一个“度”，过度使用这条原则，非得给每个类都定义接口，接口满天飞，也会导致不必要的开发负担。至于什么时候，该为某个类定义接口，实现基于接口的编程，什么时候不需要定义接口，直接使用实现类编程，我们做权衡的根本依据，还是要回归到设计原则诞生的初衷上来。只要搞清楚了这条原则是为了解决什么样的问题而产生的，你就会发现，很多之前模棱两可的问题，都会变得豁然开朗。

前面我们也提到，这条原则的设计初衷是，将接口和实现相分离，封装不稳定的实现，暴露稳定的接口。上游系统面向接口而非实现编程，不依赖不稳定的实现细节，这样当实现发生变化的时候，上游系统的代码基本上不需要做改动，以此来降低代码间的耦合性，提高代码的扩展性。

从这个设计初衷上来看，如果在我们的业务场景中，某个功能只有一种实现方式，未来也不可能被其他实现方式替换，那我们就没有必要为其设计接口，也没有必要基于接口编程，直接使用实现类就可以了。

除此之外，越是不稳定的系统，我们越是要在代码的扩展性、维护性上下功夫。相反，如果某个系统特别稳定，在开发完之后，基本上不需要做维护，那我们就没有必要为其扩展性，投入不必要的开发时间。

### 总结

1. “基于接口而非实现编程”，这条原则的另一个表述方式，是“基于抽象而非实现编程”。后者的表述方式其实更能体现这条原则的设计初衷。我们在做软件开发的时候，一定要有抽象意识、封装意识、接口意识。越抽象、越顶层、越脱离具体某一实现的设计，越能提高代码的灵活性、扩展性、可维护性。
2. 我们在定义接口的时候，一方面，命名要足够通用，不能包含跟具体实现相关的字眼；另一方面，与特定实现有关的方法不要定义在接口中。
3. “基于接口而非实现编程”这条原则，不仅仅可以指导非常细节的编程开发，还能指导更加上层的架构设计、系统设计等。比如，服务端与客户端之间的“接口”设计、类库的“接口”设计。

## 10 | 理论七：为何说要多用组合少用继承？如何决定该用组合还是继承？

### 为什么不推荐使用继承？

继承是面向对象的四大特性之一，用来表示类之间的 is-a 关系，可以解决代码复用的问题。虽然继承有诸多作用，但继承层次过深、过复杂，也会影响到代码的可维护性。所以，对于是否应该在项目中使用继承，网上有很多争议。很多人觉得继承是一种反模式，应该尽量少用，甚至不用。为什么会有这样的争议？我们通过一个例子来解释一下。

假设我们要设计一个关于鸟的类。我们将“鸟类”这样一个抽象的事物概念，定义为一个抽象类 AbstractBird。所有更细分的鸟，比如麻雀、鸽子、乌鸦等，都继承这个抽象类。

我们知道，大部分鸟都会飞，那我们可不可以在 AbstractBird 抽象类中，定义一个 fly() 方法呢？答案是否定的。尽管大部分鸟都会飞，但也有特例，比如鸵鸟就不会飞。鸵鸟继承具有 fly() 方法的父类，那鸵鸟就具有“飞”这样的行为，这显然不符合我们对现实世界中事物的认识。当然，你可能会说，我在鸵鸟这个子类中重写（override）fly() 方法，让它抛出 UnSupportedMethodException 异常不就可以了吗？具体的代码实现如下所示：

```java
public class AbstractBird {
  //...省略其他属性和方法...
  public void fly() { //... }
}

public class Ostrich extends AbstractBird { //鸵鸟
  //...省略其他属性和方法...
  public void fly() {
    throw new UnSupportedMethodException("I can't fly.'");
  }
}
```

这种设计思路虽然可以解决问题，但不够优美。因为除了鸵鸟之外，不会飞的鸟还有很多，比如企鹅。对于这些不会飞的鸟来说，我们都需要重写 fly() 方法，抛出异常。这样的设计，一方面，徒增了编码的工作量；另一方面，也违背了我们之后要讲的最小知识原则（Least Knowledge Principle，也叫最少知识原则或者迪米特法则），暴露不该暴露的接口给外部，增加了类使用过程中被误用的概率。

你可能又会说，那我们再通过 AbstractBird 类派生出两个更加细分的抽象类：会飞的鸟类 AbstractFlyableBird 和不会飞的鸟类 AbstractUnFlyableBird，让麻雀、乌鸦这些会飞的鸟都继承 AbstractFlyableBird，让鸵鸟、企鹅这些不会飞的鸟，都继承 AbstractUnFlyableBird 类，不就可以了吗？具体的继承关系如下图所示：

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201117132604.jpg)

从图中我们可以看出，继承关系变成了三层。不过，整体上来讲，目前的继承关系还比较简单，层次比较浅，也算是一种可以接受的设计思路。我们再继续加点难度。在刚刚这个场景中，我们只关注“鸟会不会飞”，但如果我们还关注“鸟会不会叫”，那这个时候，我们又该如何设计类之间的继承关系呢？

是否会飞？是否会叫？两个行为搭配起来会产生四种情况：会飞会叫、不会飞会叫、会飞不会叫、不会飞不会叫。如果我们继续沿用刚才的设计思路，那就需要再定义四个抽象类（AbstractFlyableTweetableBird、AbstractFlyableUnTweetableBird、AbstractUnFlyableTweetableBird、AbstractUnFlyableUnTweetableBird）。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201215172507.jpg)

如果我们还需要考虑“是否会下蛋”这样一个行为，那估计就要组合爆炸了。类的继承层次会越来越深、继承关系会越来越复杂。而这种层次很深、很复杂的继承关系，一方面，会导致代码的可读性变差。因为我们要搞清楚某个类具有哪些方法、属性，必须阅读父类的代码、父类的父类的代码……一直追溯到最顶层父类的代码。另一方面，这也破坏了类的封装特性，将父类的实现细节暴露给了子类。子类的实现依赖父类的实现，两者高度耦合，一旦父类代码修改，就会影响所有子类的逻辑。

总之，继承最大的问题就在于：继承层次过深、继承关系过于复杂会影响到代码的可读性和可维护性。这也是为什么我们不推荐使用继承。那刚刚例子中继承存在的问题，我们又该如何来解决呢？你可以先自己思考一下，再听我下面的讲解。

### 组合相比继承有哪些优势？

实际上，我们可以利用组合（composition）、接口、委托（delegation）三个技术手段，一块儿来解决刚刚继承存在的问题。

我们前面讲到接口的时候说过，接口表示具有某种行为特性。针对“会飞”这样一个行为特性，我们可以定义一个 Flyable 接口，只让会飞的鸟去实现这个接口。对于会叫、会下蛋这些行为特性，我们可以类似地定义 Tweetable 接口、EggLayable 接口。我们将这个设计思路翻译成 Java 代码的话，就是下面这个样子：

```java
public interface Flyable {
  void fly();
}
public interface Tweetable {
  void tweet();
}
public interface EggLayable {
  void layEgg();
}
public class FlyAbility implements Flyable {
  @Override
  public void fly() { //... }
}
//省略Tweetable/TweetAbility/EggLayable/EggLayAbility

public class Ostrich implements Tweetable, EggLayable {//鸵鸟
  private TweetAbility tweetAbility = new TweetAbility(); //组合
  private EggLayAbility eggLayAbility = new EggLayAbility(); //组合
  //... 省略其他属性和方法...
  @Override
  public void tweet() {
    tweetAbility.tweet(); // 委托
  }
  @Override
  public void layEgg() {
    eggLayAbility.layEgg(); // 委托
  }
}
```

不过，我们知道，接口只声明方法，不定义实现。也就是说，每个会下蛋的鸟都要实现一遍 layEgg() 方法，并且实现逻辑是一样的，这就会导致代码重复的问题。那这个问题又该如何解决呢？

我们可以针对三个接口再定义三个实现类，它们分别是：实现了 fly() 方法的 FlyAbility 类、实现了 tweet() 方法的 TweetAbility 类、实现了 layEgg() 方法的 EggLayAbility 类。然后，通过组合和委托技术来消除代码重复。具体的代码实现如下所示：

```java
public interface Flyable {
  void fly()；
}
public class FlyAbility implements Flyable {
  @Override
  public void fly() { //... }
}
//省略Tweetable/TweetAbility/EggLayable/EggLayAbility

public class Ostrich implements Tweetable, EggLayable {//鸵鸟
  private TweetAbility tweetAbility = new TweetAbility(); //组合
  private EggLayAbility eggLayAbility = new EggLayAbility(); //组合
  //... 省略其他属性和方法...
  @Override
  public void tweet() {
    tweetAbility.tweet(); // 委托
  }
  @Override
  public void layEgg() {
    eggLayAbility.layEgg(); // 委托
  }
}
```

我们知道继承主要有三个作用：表示 is-a 关系，支持多态特性，代码复用。而这三个作用都可以通过其他技术手段来达成。比如 is-a 关系，我们可以通过组合和接口的 has-a 关系来替代；多态特性我们可以利用接口来实现；代码复用我们可以通过组合和委托来实现。所以，从理论上讲，通过组合、接口、委托三个技术手段，我们完全可以替换掉继承，在项目中不用或者少用继承关系，特别是一些复杂的继承关系。

### 如何判断该用组合还是继承？

尽管我们鼓励多用组合少用继承，但组合也并不是完美的，继承也并非一无是处。在实际的项目开发中，我们还是要根据具体的情况，来选择该用继承还是组合。如果类之间的继承结构稳定，层次比较浅，关系不复杂，我们就可以大胆地使用继承。反之，我们就尽量使用组合来替代继承。除此之外，还有一些设计模式、特殊的应用场景，会固定使用继承或者组合。

如果类之间的继承结构稳定（不会轻易改变），继承层次比较浅（比如，最多有两层继承关系），继承关系不复杂，我们就可以大胆地使用继承。反之，系统越不稳定，继承层次很深，继承关系复杂，我们就尽量使用组合来替代继承。

除此之外，还有一些设计模式会固定使用继承或者组合。比如，装饰者模式（decorator pattern）、策略模式（strategy pattern）、组合模式（composite pattern）等都使用了组合关系，而模板模式（template pattern）使用了继承关系。

前面我们讲到继承可以实现代码复用。利用继承特性，我们把相同的属性和方法，抽取出来，定义到父类中。子类复用父类中的属性和方法，达到代码复用的目的。但是，有的时候，从业务含义上，A 类和 B 类并不一定具有继承关系。比如，Crawler 类和 PageAnalyzer 类，它们都用到了 URL 拼接和分割的功能，但并不具有继承关系（既不是父子关系，也不是兄弟关系）。仅仅为了代码复用，生硬地抽象出一个父类出来，会影响到代码的可读性。如果不熟悉背后设计思路的同事，发现 Crawler 类和 PageAnalyzer 类继承同一个父类，而父类中定义的却只是 URL 相关的操作，会觉得这个代码写得莫名其妙，理解不了。这个时候，使用组合就更加合理、更加灵活。具体的代码实现如下所示：

```java
public class Url {
  //...省略属性和方法
}

public class Crawler {
  private Url url; // 组合
  public Crawler() {
    this.url = new Url();
  }
  //...
}

public class PageAnalyzer {
  private Url url; // 组合
  public PageAnalyzer() {
    this.url = new Url();
  }
  //..
}
```

还有一些特殊的场景要求我们必须使用继承。如果你不能改变一个函数的入参类型，而入参又非接口，为了支持多态，只能采用继承来实现。比如下面这样一段代码，其中 FeignClient 是一个外部类，我们没有权限去修改这部分代码，但是我们希望能重写这个类在运行时执行的 encode() 函数。这个时候，我们只能采用继承来实现了。

```java
public class FeignClient { // Feign Client框架代码
  //...省略其他代码...
  public void encode(String url) { //... }
}

public void demofunction(FeignClient feignClient) {
  //...
  feignClient.encode(url);
  //...
}

public class CustomizedFeignClient extends FeignClient {
  @Override
  public void encode(String url) { //...重写encode的实现...}
}

// 调用
FeignClient client = new CustomizedFeignClient();
demofunction(client);
```

尽管有些人说，要杜绝继承，100% 用组合代替继承，但是我的观点没那么极端！之所以“多用组合少用继承”这个口号喊得这么响，只是因为，长期以来，我们过度使用继承。还是那句话，组合并不完美，继承也不是一无是处。只要我们控制好它们的副作用、发挥它们各自的优势，在不同的场合下，恰当地选择使用继承还是组合，这才是我们所追求的境界。

### 总结

1. 为什么不推荐使用继承？

继承是面向对象的四大特性之一，用来表示类之间的 is-a 关系，可以解决代码复用的问题。虽然继承有诸多作用，但继承层次过深、过复杂，也会影响到代码的可维护性。在这种情况下，我们应该尽量少用，甚至不用继承。

2. 组合相比继承有哪些优势？

继承主要有三个作用：表示 is-a 关系，支持多态特性，代码复用。而这三个作用都可以通过组合、接口、委托三个技术手段来达成。除此之外，利用组合还能解决层次过深、过复杂的继承关系影响代码可维护性的问题。

3. 如何判断该用组合还是继承？

尽管我们鼓励多用组合少用继承，但组合也并不是完美的，继承也并非一无是处。在实际的项目开发中，我们还是要根据具体的情况，来选择该用继承还是组合。如果类之间的继承结构稳定，层次比较浅，关系不复杂，我们就可以大胆地使用继承。反之，我们就尽量使用组合来替代继承。除此之外，还有一些设计模式、特殊的应用场景，会固定使用继承或者组合。

## 11. 实战一（上）：业务开发常用的基于贫血模型的MVC架构违背OOP吗？

据我了解，大部分工程师都是做业务开发的，所以，今天我们讲的这个实战项目也是一个典型的业务系统开发案例。我们都知道，很多业务系统都是基于 MVC 三层架构来开发的。实际上，更确切点讲，这是一种基于贫血模型的 MVC 三层架构开发模式。

虽然这种开发模式已经成为标准的 Web 项目的开发模式，但它却违反了面向对象编程风格，是一种彻彻底底的面向过程的编程风格，因此而被有些人称为反模式（anti-pattern）。特别是领域驱动设计（Domain Driven Design，简称 DDD）盛行之后，这种基于贫血模型的传统的开发模式就更加被人诟病。而基于充血模型的 DDD 开发模式越来越被人提倡。所以，我打算用两节课的时间，结合一个虚拟钱包系统的开发案例，带你彻底弄清楚这两种开发模式。

### 什么是基于贫血模型的传统开发模式？

我相信，对于大部分的后端开发工程师来说，MVC 三层架构都不会陌生。不过，为了统一我们之间对 MVC 的认识，我还是带你一块来回顾一下，什么是 MVC 三层架构。

MVC 三层架构中的 M 表示 Model，V 表示 View，C 表示 Controller。它将整个项目分为三层：展示层、逻辑层、数据层。MVC 三层开发架构是一个比较笼统的分层方式，落实到具体的开发层面，很多项目也并不会 100% 遵从 MVC 固定的分层方式，而是会根据具体的项目需求，做适当的调整。

比如，现在很多 Web 或者 App 项目都是前后端分离的，后端负责暴露接口给前端调用。这种情况下，我们一般就将后端项目分为 Repository 层、Service 层、Controller 层。其中，Repository 层负责数据访问，Service 层负责业务逻辑，Controller 层负责暴露接口。当然，这只是其中一种分层和命名方式。不同的项目、不同的团队，可能会对此有所调整。不过，万变不离其宗，只要是依赖数据库开发的 Web 项目，基本的分层思路都大差不差。

刚刚我们回顾了 MVC 三层开发架构。现在，我们再来看一下，什么是贫血模型？实际上，你可能一直都在用贫血模型做开发，只是自己不知道而已。不夸张地讲，据我了解，目前几乎所有的业务后端系统，都是基于贫血模型的。我举一个简单的例子来给你解释一下。

```java
////////// Controller+VO(View Object) //////////
public class UserController {
  private UserService userService; //通过构造函数或者IOC框架注入
  
  public UserVo getUserById(Long userId) {
    UserBo userBo = userService.getUserById(userId);
    UserVo userVo = [...convert userBo to userVo...];
    return userVo;
  }
}

public class UserVo {//省略其他属性、get/set/construct方法
  private Long id;
  private String name;
  private String cellphone;
}

////////// Service+BO(Business Object) //////////
public class UserService {
  private UserRepository userRepository; //通过构造函数或者IOC框架注入
  
  public UserBo getUserById(Long userId) {
    UserEntity userEntity = userRepository.getUserById(userId);
    UserBo userBo = [...convert userEntity to userBo...];
    return userBo;
  }
}

public class UserBo {//省略其他属性、get/set/construct方法
  private Long id;
  private String name;
  private String cellphone;
}

////////// Repository+Entity //////////
public class UserRepository {
  public UserEntity getUserById(Long userId) { //... }
}

public class UserEntity {//省略其他属性、get/set/construct方法
  private Long id;
  private String name;
  private String cellphone;
}
```

我们平时开发 Web 后端项目的时候，基本上都是这么组织代码的。其中，UserEntity 和 UserRepository 组成了数据访问层，UserBo 和 UserService 组成了业务逻辑层，UserVo 和 UserController 在这里属于接口层。

从代码中，我们可以发现，UserBo 是一个纯粹的数据结构，只包含数据，不包含任何业务逻辑。业务逻辑集中在 UserService 中。我们通过 UserService 来操作 UserBo。换句话说，Service 层的数据和业务逻辑，被分割为 BO 和 Service 两个类中。像 UserBo 这样，只包含数据，不包含业务逻辑的类，就叫作贫血模型（Anemic Domain Model）。同理，UserEntity、UserVo 都是基于贫血模型设计的。这种贫血模型将数据与操作分离，破坏了面向对象的封装特性，是一种典型的面向过程的编程风格。

### 什么是基于充血模型的 DDD 开发模式？

刚刚我们讲了基于贫血模型的传统的开发模式。现在我们再讲一下，另外一种最近更加被推崇的开发模式：基于充血模型的 DDD 开发模式。

首先，我们先来看一下，什么是充血模型？

在贫血模型中，数据和业务逻辑被分割到不同的类中。充血模型（Rich Domain Model）正好相反，数据和对应的业务逻辑被封装到同一个类中。因此，这种充血模型满足面向对象的封装特性，是典型的面向对象编程风格。

接下来，我们再来看一下，什么是领域驱动设计？

领域驱动设计，即 DDD，主要是用来指导如何解耦业务系统，划分业务模块，定义业务领域模型及其交互。领域驱动设计这个概念并不新颖，早在 2004 年就被提出了，到现在已经有十几年的历史了。不过，它被大众熟知，还是基于另一个概念的兴起，那就是微服务。

我们知道，除了监控、调用链追踪、API 网关等服务治理系统的开发之外，微服务还有另外一个更加重要的工作，那就是针对公司的业务，合理地做微服务拆分。而领域驱动设计恰好就是用来指导划分服务的。所以，微服务加速了领域驱动设计的盛行。

不过，我个人觉得，领域驱动设计有点儿类似敏捷开发、SOA、PAAS 等概念，听起来很高大上，但实际上只值“五分钱”。即便你没有听说过领域驱动设计，对这个概念一无所知，只要你是在开发业务系统，也或多或少都在使用它。做好领域驱动设计的关键是，看你对自己所做业务的熟悉程度，而并不是对领域驱动设计这个概念本身的掌握程度。即便你对领域驱动搞得再清楚，但是对业务不熟悉，也并不一定能做出合理的领域设计。所以，不要把领域驱动设计当银弹，不要花太多的时间去过度地研究它。

实际上，基于充血模型的 DDD 开发模式实现的代码，也是按照 MVC 三层架构分层的。Controller 层还是负责暴露接口，Repository 层还是负责数据存取，Service 层负责核心业务逻辑。它跟基于贫血模型的传统开发模式的区别主要在 Service 层。

在基于贫血模型的传统开发模式中，Service 层包含 Service 类和 BO 类两部分，BO 是贫血模型，只包含数据，不包含具体的业务逻辑。业务逻辑集中在 Service 类中。在基于充血模型的 DDD 开发模式中，Service 层包含 Service 类和 Domain 类两部分。Domain 就相当于贫血模型中的 BO。不过，Domain 与 BO 的区别在于它是基于充血模型开发的，既包含数据，也包含业务逻辑。而 Service 类变得非常单薄。总结一下的话就是，基于贫血模型的传统的开发模式，重 Service 轻 BO；基于充血模型的 DDD 开发模式，轻 Service 重 Domain。

基于充血模型的 DDD 设计模式的概念，今天我们只是简单地介绍了一下。在下一节课中，我会结合具体的项目，通过代码来给你展示，如何基于这种开发模式来开发一个系统。

### 为什么基于贫血模型的传统开发模式如此受欢迎？

前面我们讲过，基于贫血模型的传统开发模式，将数据与业务逻辑分离，违反了 OOP 的封装特性，实际上是一种面向过程的编程风格。但是，现在几乎所有的 Web 项目，都是基于这种贫血模型的开发模式，甚至连 Java Spring 框架的官方 demo，都是按照这种开发模式来编写的。

我们前面也讲过，面向过程编程风格有种种弊端，比如，数据和操作分离之后，数据本身的操作就不受限制了。任何代码都可以随意修改数据。既然基于贫血模型的这种传统开发模式是面向过程编程风格的，那它又为什么会被广大程序员所接受呢？关于这个问题，我总结了下面三点原因。

第一点原因是，大部分情况下，我们开发的系统业务可能都比较简单，简单到就是基于 SQL 的 CRUD 操作，所以，我们根本不需要动脑子精心设计充血模型，贫血模型就足以应付这种简单业务的开发工作。除此之外，因为业务比较简单，即便我们使用充血模型，那模型本身包含的业务逻辑也并不会很多，设计出来的领域模型也会比较单薄，跟贫血模型差不多，没有太大意义。

第二点原因是，充血模型的设计要比贫血模型更加有难度。因为充血模型是一种面向对象的编程风格。我们从一开始就要设计好针对数据要暴露哪些操作，定义哪些业务逻辑。而不是像贫血模型那样，我们只需要定义数据，之后有什么功能开发需求，我们就在 Service 层定义什么操作，不需要事先做太多设计。

第三点原因是，思维已固化，转型有成本。基于贫血模型的传统开发模式经历了这么多年，已经深得人心、习以为常。你随便问一个旁边的大龄同事，基本上他过往参与的所有 Web 项目应该都是基于这个开发模式的，而且也没有出过啥大问题。如果转向用充血模型、领域驱动设计，那势必有一定的学习成本、转型成本。很多人在没有遇到开发痛点的情况下，是不愿意做这件事情的。

### 什么项目应该考虑使用基于充血模型的 DDD 开发模式？

既然基于贫血模型的开发模式已经成为了一种约定俗成的开发习惯，那什么样的项目应该考虑使用基于充血模型的 DDD 开发模式呢？

刚刚我们讲到，基于贫血模型的传统的开发模式，比较适合业务比较简单的系统开发。相对应的，基于充血模型的 DDD 开发模式，更适合业务复杂的系统开发。比如，包含各种利息计算模型、还款模型等复杂业务的金融系统。

你可能会有一些疑问，这两种开发模式，落实到代码层面，区别不就是一个将业务逻辑放到 Service 类中，一个将业务逻辑放到 Domain 领域模型中吗？为什么基于贫血模型的传统开发模式，就不能应对复杂业务系统的开发？而基于充血模型的 DDD 开发模式就可以呢？

实际上，除了我们能看到的代码层面的区别之外（一个业务逻辑放到 Service 层，一个放到领域模型中），还有一个非常重要的区别，那就是两种不同的开发模式会导致不同的开发流程。基于充血模型的 DDD 开发模式的开发流程，在应对复杂业务系统的开发的时候更加有优势。为什么这么说呢？我们先来回忆一下，我们平时基于贫血模型的传统的开发模式，都是怎么实现一个功能需求的。

不夸张地讲，我们平时的开发，大部分都是 SQL 驱动（SQL-Driven）的开发模式。我们接到一个后端接口的开发需求的时候，就去看接口需要的数据对应到数据库中，需要哪张表或者哪几张表，然后思考如何编写 SQL 语句来获取数据。之后就是定义 Entity、BO、VO，然后模板式地往对应的 Repository、Service、Controller 类中添加代码。

业务逻辑包裹在一个大的 SQL 语句中，而 Service 层可以做的事情很少。SQL 都是针对特定的业务功能编写的，复用性差。当我要开发另一个业务功能的时候，只能重新写个满足新需求的 SQL 语句，这就可能导致各种长得差不多、区别很小的 SQL 语句满天飞。

所以，在这个过程中，很少有人会应用领域模型、OOP 的概念，也很少有代码复用意识。对于简单业务系统来说，这种开发方式问题不大。但对于复杂业务系统的开发来说，这样的开发方式会让代码越来越混乱，最终导致无法维护。

如果我们在项目中，应用基于充血模型的 DDD 的开发模式，那对应的开发流程就完全不一样了。在这种开发模式下，我们需要事先理清楚所有的业务，定义领域模型所包含的属性和方法。领域模型相当于可复用的业务中间层。新功能需求的开发，都基于之前定义好的这些领域模型来完成。

我们知道，越复杂的系统，对代码的复用性、易维护性要求就越高，我们就越应该花更多的时间和精力在前期设计上。而基于充血模型的 DDD 开发模式，正好需要我们前期做大量的业务调研、领域模型设计，所以它更加适合这种复杂系统的开发。

### 总结

我们平时做 Web 项目的业务开发，大部分都是基于贫血模型的 MVC 三层架构，在专栏中我把它称为传统的开发模式。之所以称之为“传统”，是相对于新兴的基于充血模型的 DDD 开发模式来说的。基于贫血模型的传统开发模式，是典型的面向过程的编程风格。相反，基于充血模型的 DDD 开发模式，是典型的面向对象的编程风格。

不过，DDD 也并非银弹。对于业务不复杂的系统开发来说，基于贫血模型的传统开发模式简单够用，基于充血模型的 DDD 开发模式有点大材小用，无法发挥作用。相反，对于业务复杂的系统开发来说，基于充血模型的 DDD 开发模式，因为前期需要在设计上投入更多时间和精力，来提高代码的复用性和可维护性，所以相比基于贫血模型的开发模式，更加有优势。

## 12 | 实战一（下）：如何利用基于充血模型的DDD开发一个虚拟钱包系统？

### 钱包业务背景介绍

很多具有支付、购买功能的应用（比如淘宝、滴滴出行、极客时间等）都支持钱包的功能。应用为每个用户开设一个系统内的虚拟钱包账户，支持用户充值、提现、支付、冻结、透支、转赠、查询账户余额、查询交易流水等操作。下图是一张典型的钱包功能界面，你可以直观地感受一下。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201215211943.jpg)

一般来讲，每个虚拟钱包账户都会对应用户的一个真实的支付账户，有可能是银行卡账户，也有可能是三方支付账户（比如支付宝、微信钱包）。为了方便后续的讲解，我们限定钱包暂时只支持充值、提现、支付、查询余额、查询交易流水这五个核心的功能，其他比如冻结、透支、转赠等不常用的功能，我们暂不考虑。为了让你理解这五个核心功能是如何工作的，接下来，我们来一块儿看下它们的业务实现流程。

1. 充值

用户通过三方支付渠道，把自己银行卡账户内的钱，充值到虚拟钱包账号中。这整个过程，我们可以分解为三个主要的操作流程：第一个操作是从用户的银行卡账户转账到应用的公共银行卡账户；第二个操作是将用户的充值金额加到虚拟钱包余额上；第三个操作是记录刚刚这笔交易流水。

![img](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201118221324.jpg)

2. 支付

用户用钱包内的余额，支付购买应用内的商品。实际上，支付的过程就是一个转账的过程，从用户的虚拟钱包账户划钱到商家的虚拟钱包账户上。除此之外，我们也需要记录这笔支付的交易流水信息。

![img](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201118221418.jpg)

3. 提现

除了充值、支付之外，用户还可以将虚拟钱包中的余额，提现到自己的银行卡中。这个过程实际上就是扣减用户虚拟钱包中的余额，并且触发真正的银行转账操作，从应用的公共银行账户转钱到用户的银行账户。同样，我们也需要记录这笔提现的交易流水信息。

![img](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201118221445.jpg)

4. 查询余额

用户还可以将虚拟钱包中的余额，提现到自己的银行卡中。这个过程实际上就是扣减用户虚拟钱包中的余额，并且触发真正的银行转账操作，从应用的公共银行账户转钱到用户的银行账户。同样，我们也需要记录这笔提现的交易流水信息。

![img](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201118221513.jpg)

5. 查询交易流水

在用户充值、支付、提现的时候，我们会记录相应的交易信息。在需要查询的时候，我们只需要将之前记录的交易流水，按照时间、类型等条件过滤之后，显示出来即可。



**钱包系统的设计思路**：

根据刚刚讲的业务实现流程和数据流转图，我们可以把整个钱包系统的业务划分为两部分，其中一部分单纯跟应用内的虚拟钱包账户打交道，另一部分单纯跟银行账户打交道。我们基于这样一个业务划分，给系统解耦，将整个钱包系统拆分为两个子系统：虚拟钱包系统和三方支付系统。

现在我们来看下，如果要支持钱包的这五个核心功能，虚拟钱包系统需要对应实现哪些操作。我画了一张图，列出了这五个功能都会对应虚拟钱包的哪些操作。注意，交易流水的记录和查询，我暂时在图中打了个问号，那是因为这块比较特殊，我们待会再讲。

<img src="https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost/img/20201119232852.jpg" alt="img" style="zoom:33%;" />

从图中我们可以看出，虚拟钱包系统要支持的操作非常简单，就是余额的加加减减。其中，充值、提现、查询余额三个功能，只涉及一个账户余额的加减操作，而支付功能涉及两个账户的余额加减操作：一个账户减余额，另一个账户加余额。

现在，我们再来看一下图中问号的那部分，也就是交易流水该如何记录和查询？我们先来看一下，交易流水都需要包含哪些信息。我觉得下面这几个信息是必须包含的。   

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201119233415.jpg" alt="img" style="zoom: 33%;" />

从图中我们可以发现，交易流水的数据格式包含两个钱包账号，一个是入账钱包账号，一个是出账钱包账号。为什么要有两个账号信息呢？这主要是为了兼容支付这种涉及两个账户的交易类型。不过，对于充值、提现这两种交易类型来说，我们只需要记录一个钱包账户信息就够了，所以，这样的交易流水数据格式的设计稍微有点浪费存储空间。

实际上，我们还有另外一种交易流水数据格式的设计思路，可以解决这个问题。我们把“支付”这个交易类型，拆为两个子类型：支付和被支付。支付单纯表示出账，余额扣减，被支付单纯表示入账，余额增加。这样我们在设计交易流水数据格式的时候，只需要记录一个账户信息即可。我画了一张两种交易流水数据格式的对比图，你可以对比着看一下。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201121233717.jpg)

那么哪个好一点呢？

答案是第一种设计思路更好些。因为交易流水有两个功能：一个是业务功能，比如，提供用户查询交易流水信息；另一个是非业务功能，保证数据的一致性。这里主要是指支付操作数据的一致性。

支付实际上就是一个转账的操作，在一个账户上加上一定的金额，在另一个账户上减去相应的金额。我们需要保证加金额和减金额这两个操作，要么都成功，要么都失败。如果一个成功，一个失败，就会导致数据的不一致，一个账户明明减掉了钱，另一个账户却没有收到钱。

保证数据一致性的方法有很多，比如依赖数据库事务的原子性，将两个操作放在同一个事务中执行。但是，这样的做法不够灵活，因为我们的有可能做了分库分表，支付涉及的两个账户可能存储在不同的库中，无法直接利用数据库本身的事务特性，在一个事务中执行两个账户的操作。当然，我们还有一些支持分布式事务的开源框架，但是，为了保证数据的强一致性，它们的实现逻辑一般都比较复杂、本身的性能也不高，会影响业务的执行时间。所以，更加权衡的一种做法就是，不保证数据的强一致性，只实现数据的最终一致性，也就是我们刚刚提到的交易流水要实现的非业务功能。

对于支付这样的类似转账的操作，我们在操作两个钱包账户余额之前，先记录交易流水，并且标记为“待执行”，当两个钱包的加减金额都完成之后，我们再回过头来，将交易流水标记为“成功”。在给两个钱包加减金额的过程中，如果有任意一个操作失败，我们就将交易记录的状态标记为“失败”。我们通过后台补漏 Job，拉取状态为“失败”或者长时间处于“待执行”状态的交易记录，重新执行或者人工介入处理。

如果选择第二种交易流水的设计思路，使用两条交易流水来记录支付操作，那记录两条交易流水本身又存在数据的一致性问题，有可能入账的交易流水记录成功，出账的交易流水信息记录失败。所以，权衡利弊，我们选择第一种稍微有些冗余的数据格式设计思路。

现在，我们再思考这样一个问题：充值、提现、支付这些业务交易类型，是否应该让虚拟钱包系统感知？换句话说，我们是否应该在虚拟钱包系统的交易流水中记录这三种类型？答案是否定的。虚拟钱包系统不应该感知具体的业务交易类型。我们前面讲到，虚拟钱包支持的操作，仅仅是余额的加加减减操作，不涉及复杂业务概念，职责单一、功能通用。如果耦合太多业务概念到里面，势必影响系统的通用性，而且还会导致系统越做越复杂。因此，我们不希望将充值、支付、提现这样的业务概念添加到虚拟钱包系统中。

但是，如果我们不在虚拟钱包系统的交易流水中记录交易类型，那在用户查询交易流水的时候，如何显示每条交易流水的交易类型呢？

从系统设计的角度，我们不应该在虚拟钱包系统的交易流水中记录交易类型。从产品需求的角度来说，我们又必须记录交易流水的交易类型。听起来比较矛盾，这个问题该如何解决呢？我们可以通过记录两条交易流水信息的方式来解决。我们前面讲到，整个钱包系统分为两个子系统，上层钱包系统的实现，依赖底层虚拟钱包系统和三方支付系统。对于钱包系统来说，它可以感知充值、支付、提现等业务概念，所以，我们在钱包系统这一层额外再记录一条包含交易类型的交易流水信息，而在底层的虚拟钱包系统中记录不包含交易类型的交易流水信息。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201121234905.jpg)

我们通过查询上层钱包系统的交易流水信息，去满足用户查询交易流水的功能需求，而虚拟钱包中的交易流水就只是用来解决数据一致性问题。实际上，它的作用还有很多，比如用来对账等。

### 基于贫血模型的传统开发模式

这是一个典型的 Web 后端项目的三层结构。其中，Controller 和 VO 负责暴露接口，具体的代码实现如下所示。注意，Controller 中，接口实现比较简单，主要就是调用 Service 的方法，所以，我省略了具体的代码实现。

```
public class VirtualWalletController {
  // 通过构造函数或者IOC框架注入
  private VirtualWalletService virtualWalletService;
  
  public BigDecimal getBalance(Long walletId) { ... } //查询余额
  public void debit(Long walletId, BigDecimal amount) { ... } //出账
  public void credit(Long walletId, BigDecimal amount) { ... } //入账
  public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) { ...} //转账
}
```

Service 和 BO 负责核心业务逻辑，Repository 和 Entity 负责数据存取。Repository 这一层的代码实现比较简单，不是我们讲解的重点，所以我也省略掉了。Service 层的代码如下所示。注意，这里我省略了一些不重要的校验代码，比如，对 amount 是否小于 0、钱包是否存在的校验等等。

```java
public class VirtualWalletBo {//省略getter/setter/constructor方法
  private Long id;
  private Long createTime;
  private BigDecimal balance;
}

public class VirtualWalletService {
  // 通过构造函数或者IOC框架注入
  private VirtualWalletRepository walletRepo;
  private VirtualWalletTransactionRepository transactionRepo;
  
  public VirtualWalletBo getVirtualWallet(Long walletId) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    VirtualWalletBo walletBo = convert(walletEntity);
    return walletBo;
  }
  
  public BigDecimal getBalance(Long walletId) {
    return walletRepo.getBalance(walletId);
  }
  
  public void debit(Long walletId, BigDecimal amount) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    BigDecimal balance = walletEntity.getBalance();
    if (balance.compareTo(amount) < 0) {
      throw new NoSufficientBalanceException(...);
    }
    walletRepo.updateBalance(walletId, balance.subtract(amount));
  }
  
  public void credit(Long walletId, BigDecimal amount) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    BigDecimal balance = walletEntity.getBalance();
    walletRepo.updateBalance(walletId, balance.add(amount));
  }
  
  public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
    VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
    transactionEntity.setAmount(amount);
    transactionEntity.setCreateTime(System.currentTimeMillis());
    transactionEntity.setFromWalletId(fromWalletId);
    transactionEntity.setToWalletId(toWalletId);
    transactionEntity.setStatus(Status.TO_BE_EXECUTED);
    Long transactionId = transactionRepo.saveTransaction(transactionEntity);
    try {
      debit(fromWalletId, amount);
      credit(toWalletId, amount);
    } catch (InsufficientBalanceException e) {
      transactionRepo.updateStatus(transactionId, Status.CLOSED);
      ...rethrow exception e...
    } catch (Exception e) {
      transactionRepo.updateStatus(transactionId, Status.FAILED);
      ...rethrow exception e...
    }
    transactionRepo.updateStatus(transactionId, Status.EXECUTED);
  }
}
```

以上便是利用基于贫血模型的传统开发模式来实现的虚拟钱包系统。尽管我们对代码稍微做了简化，但整体的业务逻辑就是上面这样子。其中大部分代码逻辑都非常简单，最复杂的是 Service 中的 transfer() 转账函数。我们为了保证转账操作的数据一致性，添加了一些跟 transaction 相关的记录和状态更新的代码，理解起来稍微有点难度，你可以对照着之前讲的设计思路，自己多思考一下。

### 基于充血模型的 DDD 开发模式

在上一节课中，我们讲到，基于充血模型的 DDD 开发模式，跟基于贫血模型的传统开发模式的主要区别就在 Service 层，Controller 层和 Repository 层的代码基本上相同。所以，我们重点看一下，Service 层按照基于充血模型的 DDD 开发模式该如何来实现。

在这种开发模式下，我们把虚拟钱包 VirtualWallet 类设计成一个充血的 Domain 领域模型，并且将原来在 Service 类中的部分业务逻辑移动到 VirtualWallet 类中，让 Service 类的实现依赖 VirtualWallet 类。具体的代码实现如下所示：

```java
public class VirtualWallet { // Domain领域模型(充血模型)
  private Long id;
  private Long createTime = System.currentTimeMillis();;
  private BigDecimal balance = BigDecimal.ZERO;
  
  public VirtualWallet(Long preAllocatedId) {
    this.id = preAllocatedId;
  }
  
  public BigDecimal balance() {
    return this.balance;
  }
  
  public void debit(BigDecimal amount) {
    if (this.balance.compareTo(amount) < 0) {
      throw new InsufficientBalanceException(...);
    }
    this.balance.subtract(amount);
  }
  
  public void credit(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException(...);
    }
    this.balance.add(amount);
  }
}

public class VirtualWalletService {
  // 通过构造函数或者IOC框架注入
  private VirtualWalletRepository walletRepo;
  private VirtualWalletTransactionRepository transactionRepo;
  
  public VirtualWallet getVirtualWallet(Long walletId) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    VirtualWallet wallet = convert(walletEntity);
    return wallet;
  }
  
  public BigDecimal getBalance(Long walletId) {
    return walletRepo.getBalance(walletId);
  }
  
  public void debit(Long walletId, BigDecimal amount) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    VirtualWallet wallet = convert(walletEntity);
    wallet.debit(amount);
    walletRepo.updateBalance(walletId, wallet.balance());
  }
  
  public void credit(Long walletId, BigDecimal amount) {
    VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
    VirtualWallet wallet = convert(walletEntity);
    wallet.credit(amount);
    walletRepo.updateBalance(walletId, wallet.balance());
  }
  
  public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
    //...跟基于贫血模型的传统开发模式的代码一样...
  }
}
```

看了上面的代码，你可能会说，领域模型 VirtualWallet 类很单薄，包含的业务逻辑很简单。相对于原来的贫血模型的设计思路，这种充血模型的设计思路，貌似并没有太大优势。你说得没错！这也是大部分业务系统都使用基于贫血模型开发的原因。不过，如果虚拟钱包系统需要支持更复杂的业务逻辑，那充血模型的优势就显现出来了。比如，我们要支持透支一定额度和冻结部分余额的功能。这个时候，我们重新来看一下 VirtualWallet 类的实现代码。

```java
public class VirtualWallet {
  private Long id;
  private Long createTime = System.currentTimeMillis();;
  private BigDecimal balance = BigDecimal.ZERO;
  private boolean isAllowedOverdraft = true;
  private BigDecimal overdraftAmount = BigDecimal.ZERO;
  private BigDecimal frozenAmount = BigDecimal.ZERO;
  
  public VirtualWallet(Long preAllocatedId) {
    this.id = preAllocatedId;
  }
  
  public void freeze(BigDecimal amount) { ... }
  public void unfreeze(BigDecimal amount) { ...}
  public void increaseOverdraftAmount(BigDecimal amount) { ... }
  public void decreaseOverdraftAmount(BigDecimal amount) { ... }
  public void closeOverdraft() { ... }
  public void openOverdraft() { ... }
  
  public BigDecimal balance() {
    return this.balance;
  }
  
  public BigDecimal getAvailableBalance() {
    BigDecimal totalAvaliableBalance = this.balance.subtract(this.frozenAmount);
    if (isAllowedOverdraft) {
      totalAvaliableBalance += this.overdraftAmount;
    }
    return totalAvaliableBalance;
  }
  
  public void debit(BigDecimal amount) {
    BigDecimal totalAvaliableBalance = getAvailableBalance();
    if (totoalAvaliableBalance.compareTo(amount) < 0) {
      throw new InsufficientBalanceException(...);
    }
    this.balance.subtract(amount);
  }
  
  public void credit(BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidAmountException(...);
    }
    this.balance.add(amount);
  }
}
```

领域模型 VirtualWallet 类添加了简单的冻结和透支逻辑之后，功能看起来就丰富了很多，代码也没那么单薄了。如果功能继续演进，我们可以增加更加细化的冻结策略、透支策略、支持钱包账号（VirtualWallet id 字段）自动生成的逻辑（不是通过构造函数经外部传入 ID，而是通过分布式 ID 生成算法来自动生成 ID）等等。VirtualWallet 类的业务逻辑会变得越来越复杂，也就很值得设计成充血模型了。

### 思考

对于虚拟钱包系统的设计与两种开发模式的代码实现，我想你应该有个比较清晰的了解了。不过，我觉得还有两个问题值得讨论一下。

第一个要讨论的问题是：在基于充血模型的 DDD 开发模式中，将业务逻辑移动到 Domain 中，Service 类变得很薄，但在我们的代码设计与实现中，并没有完全将 Service 类去掉，这是为什么？或者说，Service 类在这种情况下担当的职责是什么？哪些功能逻辑会放到 Service 类中？

区别于 Domain 的职责，Service 类主要有下面这样几个职责。

1. Service 类负责与 Repository 交流。在我的设计与代码实现中，VirtualWalletService 类负责与 Repository 层打交道，调用 Respository 类的方法，获取数据库中的数据，转化成领域模型 VirtualWallet，然后由领域模型 VirtualWallet 来完成业务逻辑，最后调用 Repository 类的方法，将数据存回数据库。这里我再稍微解释一下，之所以让 VirtualWalletService 类与 Repository 打交道，而不是让领域模型 VirtualWallet 与 Repository 打交道，那是因为我们想保持领域模型的独立性，不与任何其他层的代码（Repository 层的代码）或开发框架（比如 Spring、MyBatis）耦合在一起，将流程性的代码逻辑（比如从 DB 中取数据、映射数据）与领域模型的业务逻辑解耦，让领域模型更加可复用。
2. Service 类负责跨领域模型的业务聚合功能。VirtualWalletService 类中的 transfer() 转账函数会涉及两个钱包的操作，因此这部分业务逻辑无法放到 VirtualWallet 类中，所以，我们暂且把转账业务放到 VirtualWalletService 类中了。当然，虽然功能演进，使得转账业务变得复杂起来之后，我们也可以将转账业务抽取出来，设计成一个独立的领域模型。
3.  Service 类负责一些非功能性及与三方系统交互的工作。比如幂等、事务、发邮件、发消息、记录日志、调用其他系统的 RPC 接口等，都可以放到 Service 类中。

第二个要讨论问题是：在基于充血模型的 DDD 开发模式中，尽管 Service 层被改造成了充血模型，但是Controller 层和 Repository 层还是贫血模型，是否有必要也进行充血领域建模呢？

答案是没有必要。Controller 层主要负责接口的暴露，Repository 层主要负责与数据库打交道，这两层包含的业务逻辑并不多，前面我们也提到了，如果业务逻辑比较简单，就没必要做充血建模，即便设计成充血模型，类也非常单薄，看起来也很奇怪。

尽管这样的设计是一种面向过程的编程风格，但我们只要控制好面向过程编程风格的副作用，照样可以开发出优秀的软件。那这里的副作用怎么控制呢？

就拿 Repository 的 Entity 来说，即便它被设计成贫血模型，违反面向对象编程的封装特性，有被任意代码修改数据的风险，但 Entity 的生命周期是有限的。一般来讲，我们把它传递到 Service 层之后，就会转化成 BO 或者 Domain 来继续后面的业务逻辑。Entity 的生命周期到此就结束了，所以也并不会被到处任意修改。

我们再来说说 Controller 层的 VO。实际上 VO 是一种 DTO（Data Transfer Object，数据传输对象）。它主要是作为接口的数据传输承载体，将数据发送给其他系统。从功能上来讲，它理应不包含业务逻辑、只包含数据。所以，我们将它设计成贫血模型也是比较合理的。

### 总结

基于充血模型的 DDD 开发模式跟基于贫血模型的传统开发模式相比，主要区别在 Service 层。在基于充血模型的开发模式下，我们将部分原来在 Service 类中的业务逻辑移动到了一个充血的 Domain 领域模型中，让 Service 类的实现依赖这个 Domain 类。

在基于充血模型的 DDD 开发模式下，Service 类并不会完全移除，而是负责一些不适合放在 Domain 类中的功能。比如，负责与 Repository 层打交道、跨领域模型的业务聚合功能、幂等事务等非功能性的工作。

基于充血模型的 DDD 开发模式跟基于贫血模型的传统开发模式相比，Controller 层和 Repository 层的代码基本上相同。这是因为，Repository 层的 Entity 生命周期有限，Controller 层的 VO 只是单纯作为一种 DTO。两部分的业务逻辑都不会太复杂。业务逻辑主要集中在 Service 层。所以，Repository 层和 Controller 层继续沿用贫血模型的设计思路是没有问题的。

## 13 | 实战二（上）：如何对接口鉴权这样一个功能开发做面向对象分析？
面向对象分析（OOA）、面向对象设计（OOD）、面向对象编程（OOP），是面向对象开发的三个主要环节。在前面的章节中，我对三者的讲解比较偏理论、偏概括性，目的是让你先有一个宏观的了解，知道什么是 OOA、OOD、OOP。不过，光知道“是什么”是不够的，我们更重要的还是要知道“如何做”，也就是，如何进行面向对象分析、设计与编程。

对于“如何做需求分析，如何做职责划分？需要定义哪些类？每个类应该具有哪些属性、方法？类与类之间该如何交互？如何组装类成一个可执行的程序？”等等诸多问题，都没有清晰的思路，更别提利用成熟的设计原则、思想或者设计模式，开发出具有高内聚低耦合、易扩展、易读等优秀特性的代码了。

### 案例介绍和难点剖析

假设，你正在参与开发一个微服务。微服务通过 HTTP 协议暴露接口给其他系统调用，说直白点就是，其他系统通过 URL 来调用微服务的接口。有一天，你的 leader 找到你说，“为了保证接口调用的安全性，我们希望设计实现一个接口调用鉴权功能，只有经过认证之后的系统才能调用我们的接口，没有认证过的系统调用我们的接口会被拒绝。我希望由你来负责这个任务的开发，争取尽快上线。”

leader 丢下这些话就走了。这个时候，你该如何来做呢？有没有脑子里一团浆糊，一时间无从下手的感觉呢？为什么会有这种感觉呢？我个人觉得主要有下面两点原因。

1. 需求不明确

leader 给到的需求过于模糊、笼统，不够具体、细化，离落地到设计、编码还有一定的距离。而人的大脑不擅长思考这种过于抽象的问题。这也是真实的软件开发区别于应试教育的地方。应试教育中的考试题目，一般都是一个非常具体的问题，我们去解答就好了。而真实的软件开发中，需求几乎都不是很明确。

我们前面讲过，面向对象分析主要的分析对象是“需求”，因此，面向对象分析可以粗略地看成“需求分析”。实际上，不管是需求分析还是面向对象分析，我们首先要做的都是将笼统的需求细化到足够清晰、可执行。我们需要通过沟通、挖掘、分析、假设、梳理，搞清楚具体的需求有哪些，哪些是现在要做的，哪些是未来可能要做的，哪些是不用考虑做的。

2. 缺少锻炼

相比单纯的业务 CRUD 开发，鉴权这个开发任务，要更有难度。鉴权作为一个跟具体业务无关的功能，我们完全可以把它开发成一个独立的框架，集成到很多业务系统中。而作为被很多系统复用的通用框架，比起普通的业务代码，我们对框架的代码质量要求要更高。

开发这样通用的框架，对工程师的需求分析能力、设计能力、编码能力，甚至逻辑思维能力的要求，都是比较高的。如果你平时做的都是简单的 CRUD 业务开发，那这方面的锻炼肯定不会很多，所以，一旦遇到这种开发需求，很容易因为缺少锻炼，脑子放空，不知道从何入手，完全没有思路。

### 对案例进行需求分析

实际上，需求分析的工作很琐碎，也没有太多固定的章法可寻，所以，我不打算很牵强地罗列那些听着有用、实际没用的方法论，而是希望通过鉴权这个例子，来给你展示一下，面对需求分析的时候，我的完整的思考路径是什么样的。希望你能自己去体会，举一反三地类比应用到其他项目的需求分析中。

尽管针对框架、组件、类库等非业务系统的开发，我们一定要有组件化意识、框架意识、抽象意识，开发出来的东西要足够通用，不能局限于单一的某个业务需求，但这并不代表我们就可以脱离具体的应用场景，闷头拍脑袋做需求分析。多跟业务团队聊聊天，甚至自己去参与几个业务系统的开发，只有这样，我们才能真正知道业务系统的痛点，才能分析出最有价值的需求。不过，针对鉴权这一功能的开发，最大的需求方还是我们自己，所以，我们也可以先从满足我们自己系统的需求开始，然后再迭代优化。

1. 第一轮基础分析

对于如何做鉴权这样一个问题，最简单的解决方案就是，通过用户名加密码来做认证。我们给每个允许访问我们服务的调用方，派发一个应用名（或者叫应用 ID、AppID）和一个对应的密码（或者叫秘钥）。调用方每次进行接口请求的时候，都携带自己的 AppID 和密码。微服务在接收到接口调用请求之后，会解析出 AppID 和密码，跟存储在微服务端的 AppID 和密码进行比对。如果一致，说明认证成功，则允许接口调用请求；否则，就拒绝接口调用请求。

2. 第二轮分析优化

不过，这样的验证方式，每次都要明文传输密码。密码很容易被截获，是不安全的。那如果我们借助加密算法（比如 SHA），对密码进行加密之后，再传递到微服务端验证，是不是就可以了呢？实际上，这样也是不安全的，因为加密之后的密码及 AppID，照样可以被未认证系统（或者说黑客）截获，未认证系统可以携带这个加密之后的密码以及对应的 AppID，伪装成已认证系统来访问我们的接口。这就是典型的“重放攻击”。

提出问题，然后再解决问题，是一个非常好的迭代优化方法。对于刚刚这个问题，我们可以借助 OAuth 的验证思路来解决。调用方将请求接口的 URL 跟 AppID、密码拼接在一起，然后进行加密，生成一个 token。调用方在进行接口请求的的时候，将这个 token 及 AppID，随 URL 一块传递给微服务端。微服务端接收到这些数据之后，根据 AppID 从数据库中取出对应的密码，并通过同样的 token 生成算法，生成另外一个 token。用这个新生成的 token 跟调用方传递过来的 token 对比。如果一致，则允许接口调用请求；否则，就拒绝接口调用请求。

这个方案稍微有点复杂，我画了一张示例图，来帮你理解整个流程。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201216111922.jpg)

3. 第三轮分析优化

不过，这样的设计仍然存在重放攻击的风险，还是不够安全。每个 URL 拼接上 AppID、密码生成的 token 都是固定的。未认证系统截获 URL、token 和 AppID 之后，还是可以通过重放攻击的方式，伪装成认证系统，调用这个 URL 对应的接口。

为了解决这个问题，我们可以进一步优化 token 生成算法，引入一个随机变量，让每次接口请求生成的 token 都不一样。我们可以选择时间戳作为随机变量。原来的 token 是对 URL、AppID、密码三者进行加密生成的，现在我们将 URL、AppID、密码、时间戳四者进行加密来生成 token。调用方在进行接口请求的时候，将 token、AppID、时间戳，随 URL 一并传递给微服务端。

微服务端在收到这些数据之后，会验证当前时间戳跟传递过来的时间戳，是否在一定的时间窗口内（比如一分钟）。如果超过一分钟，则判定 token 过期，拒绝接口请求。如果没有超过一分钟，则说明 token 没有过期，就再通过同样的 token 生成算法，在服务端生成新的 token，与调用方传递过来的 token 比对，看是否一致。如果一致，则允许接口调用请求；否则，就拒绝接口调用请求。

优化之后的认证流程如下图所示。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201216112023.jpg)

4. 第四轮分析优化

不过，你可能会说，这样还是不够安全啊。未认证系统还是可以在这一分钟的 token 失效窗口内，通过截获请求、重放请求，来调用我们的接口啊！

你说得没错。不过，攻与防之间，本来就没有绝对的安全。我们能做的就是，尽量提高攻击的成本。这个方案虽然还有漏洞，但是实现起来足够简单，而且不会过度影响接口本身的性能（比如响应时间）。所以，权衡安全性、开发成本、对系统性能的影响，这个方案算是比较折中、比较合理的了。

实际上，还有一个细节我们没有考虑到，那就是，如何在微服务端存储每个授权调用方的 AppID 和密码。当然，这个问题并不难。最容易想到的方案就是存储到数据库里，比如 MySQL。不过，开发像鉴权这样的非业务功能，最好不要与具体的第三方系统有过度的耦合。

针对 AppID 和密码的存储，我们最好能灵活地支持各种不同的存储方式，比如 ZooKeeper、本地配置文件、自研配置中心、MySQL、Redis 等。我们不一定针对每种存储方式都去做代码实现，但起码要留有扩展点，保证系统有足够的灵活性和扩展性，能够在我们切换存储方式的时候，尽可能地减少代码的改动。

5. 最终确定需求

到此，需求已经足够细化和具体了。现在，我们按照鉴权的流程，对需求再重新描述一下。如果你熟悉 UML，也可以用时序图、流程图来描述。不过，用什么描述不是重点，描述清楚才是最重要的。考虑到在接下来的面向对象设计环节中，我会基于文字版本的需求描述，来进行类、属性、方法、交互等的设计，所以，这里我给出的最终需求描述是文字版本的。

- 调用方进行接口请求的时候，将 URL、AppID、密码、时间戳拼接在一起，通过加密算法生成 token，并且将 token、AppID、时间戳拼接在 URL 中，一并发送到微服务端。
- 微服务端在接收到调用方的接口请求之后，从请求中拆解出 token、AppID、时间戳。
- 微服务端首先检查传递过来的时间戳跟当前时间，是否在 token 失效时间窗口内。如果已经超过失效时间，那就算接口调用鉴权失败，拒绝接口调用请求。
- 如果 token 验证没有过期失效，微服务端再从自己的存储中，取出 AppID 对应的密码，通过同样的 token 生成算法，生成另外一个 token，与调用方传递过来的 token 进行匹配；如果一致，则鉴权成功，允许接口调用，否则就拒绝接口调用。

这就是我们需求分析的整个思考过程，从最粗糙、最模糊的需求开始，通过“提出问题 - 解决问题”的方式，循序渐进地进行优化，最后得到一个足够清晰、可落地的需求描述。

### 总结

针对框架、类库、组件等非业务系统的开发，其中一个比较大的难点就是，需求一般都比较抽象、模糊，需要你自己去挖掘，做合理取舍、权衡、假设，把抽象的问题具象化，最终产生清晰的、可落地的需求定义。需求定义是否清晰、合理，直接影响了后续的设计、编码实现是否顺畅。所以，作为程序员，你一定不要只关心设计与实现，前期的需求分析同等重要。

需求分析的过程实际上是一个不断迭代优化的过程。我们不要试图一下就能给出一个完美的解决方案，而是先给出一个粗糙的、基础的方案，有一个迭代的基础，然后再慢慢优化，这样一个思考过程能让我们摆脱无从下手的窘境。

##  14 | 实战二（下）：如何利用面向对象设计和编程开发接口鉴权功能？

在上一节课中，针对接口鉴权功能的开发，我们讲了如何进行面向对象分析（OOA），也就是需求分析。实际上，需求定义清楚之后，这个问题就已经解决了一大半，这也是为什么我花了那么多篇幅来讲解需求分析。今天，我们再来看一下，针对面向对象分析产出的需求，如何来进行面向对象设计（OOD）和面向对象编程（OOP）。

### 如何进行面向对象设计？

1. 划分职责进而识别出有哪些类

在面向对象有关书籍中经常讲到，类是现实世界中事物的一个建模。但是，并不是每个需求都能映射到现实世界，也并不是每个类都与现实世界中的事物一一对应。对于一些抽象的概念，我们是无法通过映射现实世界中的事物的方式来定义类的。

所以，大多数讲面向对象的书籍中，还会讲到另外一种识别类的方法，那就是把需求描述中的名词罗列出来，作为可能的候选类，然后再进行筛选。对于没有经验的初学者来说，这个方法比较简单、明确，可以直接照着做。

不过，我个人更喜欢另外一种方法，那就是根据需求描述，把其中涉及的功能点，一个一个罗列出来，然后再去看哪些功能点职责相近，操作同样的属性，是否应该归为同一个类。我们来看一下，针对鉴权这个例子，具体该如何来做。

- 调用方进行接口请求的时候，将 URL、AppID、密码、时间戳拼接在一起，通过加密算法生成 token，并且将 token、AppID、时间戳拼接在 URL 中，一并发送到微服务端。
- 微服务端在接收到调用方的接口请求之后，从请求中拆解出 token、AppID、时间戳。
- 微服务端首先检查传递过来的时间戳跟当前时间，是否在 token 失效时间窗口内。如果已经超过失效时间，那就算接口调用鉴权失败，拒绝接口调用请求。
- 如果 token 验证没有过期失效，微服务端再从自己的存储中，取出 AppID 对应的密码，通过同样的 token 生成算法，生成另外一个 token，与调用方传递过来的 token 进行匹配。如果一致，则鉴权成功，允许接口调用；否则就拒绝接口调用。

首先，我们要做的是逐句阅读上面的需求描述，拆解成小的功能点，一条一条罗列下来。注意，拆解出来的每个功能点要尽可能的小。每个功能点只负责做一件很小的事情（专业叫法是“单一职责”，后面章节中我们会讲到）。下面是我逐句拆解上述需求描述之后，得到的功能点列表：

- 把 URL、AppID、密码、时间戳拼接为一个字符串；
- 对字符串通过加密算法加密生成 token；
- 将 token、AppID、时间戳拼接到 URL 中，形成新的 URL；
- 解析 URL，得到 token、AppID、时间戳等信息；
- 从存储中取出 AppID 和对应的密码；
- 根据时间戳判断 token 是否过期失效；验证两个 token 是否匹配；

从上面的功能列表中，我们发现，1、2、6、7 都是跟 token 有关，负责 token 的生成、验证；3、4 都是在处理 URL，负责 URL 的拼接、解析；5 是操作 AppID 和密码，负责从存储中读取 AppID 和密码。所以，我们可以粗略地得到三个核心的类：AuthToken、Url、CredentialStorage。AuthToken 负责实现 1、2、6、7 这四个操作；Url 负责 3、4 两个操作；CredentialStorage 负责 5 这个操作。

当然，这是一个初步的类的划分，其他一些不重要的、边边角角的类，我们可能暂时没法一下子想全，但这也没关系，面向对象分析、设计、编程本来就是一个循环迭代、不断优化的过程。根据需求，我们先给出一个粗糙版本的设计方案，然后基于这样一个基础，再去迭代优化，会更加容易一些，思路也会更加清晰一些。

不过，我还要再强调一点，接口调用鉴权这个开发需求比较简单，所以，需求对应的面向对象设计并不复杂，识别出来的类也并不多。但如果我们面对的是更加大型的软件开发、更加复杂的需求开发，涉及的功能点可能会很多，对应的类也会比较多，像刚刚那样根据需求逐句罗列功能点的方法，最后会得到一个长长的列表，就会有点凌乱、没有规律。针对这种复杂的需求开发，我们首先要做的是进行模块划分，将需求先简单划分成几个小的、独立的功能模块，然后再在模块内部，应用我们刚刚讲的方法，进行面向对象设计。而模块的划分和识别，跟类的划分和识别，是类似的套路。

2. 定义类及其属性和方法

**AuthToken 类相关的功能点有四个**：

- 把 URL、AppID、密码、时间戳拼接为一个字符串；
- 对字符串通过加密算法加密生成 token；
- 根据时间戳判断 token 是否过期失效；
- 验证两个 token 是否匹配。

对于方法的识别，很多面向对象相关的书籍，一般都是这么讲的，识别出需求描述中的动词，作为候选的方法，再进一步过滤筛选。类比一下方法的识别，我们可以把功能点中涉及的名词，作为候选属性，然后同样进行过滤筛选。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201122114500.jpg)从上面的类图中，我们可以发现这样三个小细节。

- 第一个细节：并不是所有出现的名词都被定义为类的属性，比如 URL、AppID、密码、时间戳这几个名词，我们把它作为了方法的参数。
- 第二个细节：我们还需要挖掘一些没有出现在功能点描述中属性，比如 createTime，expireTimeInterval，它们用在 isExpired() 函数中，用来判定 token 是否过期。
- 第三个细节：我们还给 AuthToken 类添加了一个功能点描述中没有提到的方法 getToken()。

第一个细节告诉我们，从业务模型上来说，不应该属于这个类的属性和方法，不应该被放到这个类里。比如 URL、AppID 这些信息，从业务模型上来说，不应该属于 AuthToken，所以我们不应该放到这个类中。

第二、第三个细节告诉我们，在设计类具有哪些属性和方法的时候，不能单纯地依赖当下的需求，还要分析这个类从业务模型上来讲，理应具有哪些属性和方法。这样可以一方面保证类定义的完整性，另一方面不仅为当下的需求还为未来的需求做些准备。

**Url 类相关的功能点有两个：**

- 将 token、AppID、时间戳拼接到 URL 中，形成新的 URL；
- 解析 URL，得到 token、AppID、时间戳等信息。

虽然需求描述中，我们都是以 URL 来代指接口请求，但是，接口请求并不一定是以 URL 的形式来表达，还有可能是 Dubbo、RPC 等其他形式。为了让这个类更加通用，命名更加贴切，我们接下来把它命名为 ApiRequest。下面是我根据功能点描述设计的 ApiRequest 类。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201122123535.jpg" alt="img" style="zoom: 25%;" />

**CredentialStorage 类相关的功能点有一个**：

- 从存储中取出 AppID 和对应的密码。

CredentialStorage 类非常简单，类图如下所示。为了做到抽象封装具体的存储方式，我们将 CredentialStorage 设计成了接口，基于接口而非具体的实现编程。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201122123659.jpg)

3. 定义类与类之间的交互关系

- 泛化（Generalization）可以简单理解为继承关系。具体到 Java 代码就是下面这样：

```
public class A { ... }
public class B extends A { ... }
```

- 实现（Realization）一般是指接口和实现类之间的关系。具体到 Java 代码就是下面这样：

```
public interface A {...}
public class B implements A { ... }
```

- 聚合（Aggregation）是一种包含关系，A 类对象包含 B 类对象，B 类对象的生命周期可以不依赖 A 类对象的生命周期，也就是说可以单独销毁 A 类对象而不影响 B 对象，比如课程与学生之间的关系。具体到 Java 代码就是下面这样：

```
public class A {
  private B b;
  public A(B b) {
    this.b = b;
  }
}
```

- 组合（Composition）也是一种包含关系。A 类对象包含 B 类对象，B 类对象的生命周期依赖 A 类对象的生命周期，B 类对象不可单独存在，比如鸟与翅膀之间的关系。具体到 Java 代码就是下面这样：

```
public class A {
  private B b;
  public A() {
    this.b = new B();
  }
}
```

- 关联（Association）是一种非常弱的关系，包含聚合、组合两种关系。具体到代码层面，如果 B 类对象是 A 类的成员变量，那 B 类和 A 类就是关联关系。具体到 Java 代码就是下面这样：

```
public class A {
  private B b;
  public A(B b) {
    this.b = b;
  }
}
或者
public class A {
  private B b;
  public A() {
    this.b = new B();
  }
}
```

- 依赖（Dependency）是一种比关联关系更加弱的关系，包含关联关系。不管是 B 类对象是 A 类对象的成员变量，还是 A 类的方法使用 B 类对象作为参数或者返回值、局部变量，只要 B 类对象和 A 类对象有任何使用关系，我们都称它们有依赖关系。具体到 Java 代码就是下面这样：

```
public class A {
  private B b;
  public A(B b) {
    this.b = b;
  }
}
或者
public class A {
  private B b;
  public A() {
    this.b = new B();
  }
}
或者
public class A {
  public void func(B b) { ... }
}
```

4. 将类组装起来并提供执行入口

类定义好了，类之间必要的交互关系也设计好了，接下来我们要将所有的类组装在一起，提供一个执行入口。这个入口可能是一个 main() 函数，也可能是一组给外部用的 API 接口。通过这个入口，我们能触发整个代码跑起来。

接口鉴权并不是一个独立运行的系统，而是一个集成在系统上运行的组件，所以，我们封装所有的实现细节，设计了一个最顶层的 ApiAuthenticator 接口类，暴露一组给外部调用者使用的 API 接口，作为触发执行鉴权逻辑的入口。具体的类的设计如下所示：

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201122125223.jpg" alt="img" style="zoom:25%;" />

### **如何进行面向对象编程？**

面向对象设计完成之后，我们已经定义清晰了类、属性、方法、类之间的交互，并且将所有的类组装起来，提供了统一的执行入口。接下来，面向对象编程的工作，就是将这些设计思路翻译成代码实现。有了前面的类图，这部分工作相对来说就比较简单了。所以，这里我只给出比较复杂的 ApiAuthenticator 的实现。

```java
public interface ApiAuthenticator {
  void auth(String url);
  void auth(ApiRequest apiRequest);
}

public class DefaultApiAuthenticatorImpl implements ApiAuthenticator {
  private CredentialStorage credentialStorage;
  
  public DefaultApiAuthenticatorImpl() {
    this.credentialStorage = new MysqlCredentialStorage();
  }
  
  public DefaultApiAuthenticatorImpl(CredentialStorage credentialStorage) {
    this.credentialStorage = credentialStorage;
  }

  @Override
  public void auth(String url) {
    ApiRequest apiRequest = ApiRequest.buildFromUrl(url);
    auth(apiRequest);
  }

  @Override
  public void auth(ApiRequest apiRequest) {
    String appId = apiRequest.getAppId();
    String token = apiRequest.getToken();
    long timestamp = apiRequest.getTimestamp();
    String originalUrl = apiRequest.getOriginalUrl();

    AuthToken clientAuthToken = new AuthToken(token, timestamp);
    if (clientAuthToken.isExpired()) {
      throw new RuntimeException("Token is expired.");
    }

    String password = credentialStorage.getPasswordByAppId(appId);
    AuthToken serverAuthToken = AuthToken.generate(originalUrl, appId, password, timestamp);
    if (!serverAuthToken.match(clientAuthToken)) {
      throw new RuntimeException("Token verfication failed.");
    }
  }
}
```

### 总结

1. 划分职责进而识别出有哪些类

根据需求描述，我们把其中涉及的功能点，一个一个罗列出来，然后再去看哪些功能点职责相近，操作同样的属性，可否归为同一个类。 2. 定义类及其属性和方法

我们识别出需求描述中的动词，作为候选的方法，再进一步过滤筛选出真正的方法，把功能点中涉及的名词，作为候选属性，然后同样再进行过滤筛选。

3. 定义类与类之间的交互关系

UML 统一建模语言中定义了六种类之间的关系。它们分别是：泛化、实现、关联、聚合、组合、依赖。我们从更加贴近编程的角度，对类与类之间的关系做了调整，保留四个关系：泛化、实现、组合、依赖。

4. 将类组装起来并提供执行入口

我们要将所有的类组装在一起，提供一个执行入口。这个入口可能是一个 main() 函数，也可能是一组给外部用的 API 接口。通过这个入口，我们能触发整个代码跑起来。

# 设计原则与思想：设计原则

## 15 | 理论一：对于单一职责原则，如何判定某个类的职责是否够“单一”？

### 如何理解单一职责原则（SRP）？

文章的开头我们提到了 SOLID 原则，实际上，SOLID 原则并非单纯的 1 个原则，而是由 5 个设计原则组成的，它们分别是：单一职责原则、开闭原则、里式替换原则、接口隔离原则和依赖反转原则，依次对应 SOLID 中的 S、O、L、I、D 这 5 个英文字母。我们今天要学习的是 SOLID 原则中的第一个原则：单一职责原则。

单一职责原则的英文是 Single Responsibility Principle，缩写为 SRP。这个原则的英文描述是这样的：A class or module should have a single responsibility。如果我们把它翻译成中文，那就是：一个类或者模块只负责完成一个职责（或者功能）。

单一职责原则的定义描述非常简单，也不难理解。一个类只负责完成一个职责或者功能。也就是说，不要设计大而全的类，要设计粒度小、功能单一的类。换个角度来讲就是，一个类包含了两个或者两个以上业务不相干的功能，那我们就说它职责不够单一，应该将它拆分成多个功能更加单一、粒度更细的类。

我举一个例子来解释一下。比如，一个类里既包含订单的一些操作，又包含用户的一些操作。而订单和用户是两个独立的业务领域模型，我们将两个不相干的功能放到同一个类中，那就违反了单一职责原则。为了满足单一职责原则，我们需要将这个类拆分成两个粒度更细、功能更加单一的两个类：订单类和用户类。

### 如何判断类的职责是否足够单一？

在一个社交产品中，我们用下面的 UserInfo 类来记录用户的信息。你觉得，UserInfo 类的设计是否满足单一职责原则呢？

```java
public class UserInfo {
  private long userId;
  private String username;
  private String email;
  private String telephone;
  private long createTime;
  private long lastLoginTime;
  private String avatarUrl;
  private String provinceOfAddress; // 省
  private String cityOfAddress; // 市
  private String regionOfAddress; // 区 
  private String detailedAddress; // 详细地址
  // ...省略其他属性和方法...
}
```

对于这个问题，有两种不同的观点。一种观点是，UserInfo 类包含的都是跟用户相关的信息，所有的属性和方法都隶属于用户这样一个业务模型，满足单一职责原则；另一种观点是，地址信息在 UserInfo 类中，所占的比重比较高，可以继续拆分成独立的 UserAddress 类，UserInfo 只保留除 Address 之外的其他信息，拆分之后的两个类的职责更加单一。

哪种观点更对呢？实际上，要从中做出选择，我们不能脱离具体的应用场景。如果在这个社交产品中，用户的地址信息跟其他信息一样，只是单纯地用来展示，那 UserInfo 现在的设计就是合理的。但是，如果这个社交产品发展得比较好，之后又在产品中添加了电商的模块，用户的地址信息还会用在电商物流中，那我们最好将地址信息从 UserInfo 中拆分出来，独立成用户物流信息（或者叫地址信息、收货信息等）。

我们再进一步延伸一下。如果做这个社交产品的公司发展得越来越好，公司内部又开发出了很多其他产品（可以理解为其他 App）。公司希望支持统一账号系统，也就是用户一个账号可以在公司内部的所有产品中登录。这个时候，我们就需要继续对 UserInfo 进行拆分，将跟身份认证相关的信息（比如，email、telephone 等）抽取成独立的类。

从刚刚这个例子，我们可以总结出，不同的应用场景、不同阶段的需求背景下，对同一个类的职责是否单一的判定，可能都是不一样的。在某种应用场景或者当下的需求背景下，一个类的设计可能已经满足单一职责原则了，但如果换个应用场景或着在未来的某个需求背景下，可能就不满足了，需要继续拆分成粒度更细的类。

除此之外，从不同的业务层面去看待同一个类的设计，对类是否职责单一，也会有不同的认识。比如，例子中的 UserInfo 类。如果我们从“用户”这个业务层面来看，UserInfo 包含的信息都属于用户，满足职责单一原则。如果我们从更加细分的“用户展示信息”“地址信息”“登录认证信息”等等这些更细粒度的业务层面来看，那 UserInfo 就应该继续拆分。

综上所述，评价一个类的职责是否足够单一，我们并没有一个非常明确的、可以量化的标准，可以说，这是件非常主观、仁者见仁智者见智的事情。实际上，在真正的软件开发中，我们也没必要过于未雨绸缪，过度设计。所以，我们可以先写一个粗粒度的类，满足业务需求。随着业务的发展，如果粗粒度的类越来越庞大，代码越来越多，这个时候，我们就可以将这个粗粒度的类，拆分成几个更细粒度的类。这就是所谓的持续重构（后面的章节中我们会讲到）。

- 类中的代码行数、函数或属性过多，会影响代码的可读性和可维护性，我们就需要考虑对类进行拆分；
- 类依赖的其他类过多，或者依赖类的其他类过多，不符合高内聚、低耦合的设计思想，我们就需要考虑对类进行拆分；
- 私有方法过多，我们就要考虑能否将私有方法独立到新的类中，设置为 public 方法，供更多的类使用，从而提高代码的复用性；
- 比较难给类起一个合适名字，很难用一个业务名词概括，或者只能用一些笼统的 Manager、Context 之类的词语来命名，这就说明类的职责定义得可能不够清晰；
- 类中大量的方法都是集中操作类中的某几个属性，比如，在 UserInfo 例子中，如果一半的方法都是在操作 address 信息，那就可以考虑将这几个属性和对应的方法拆分出来。

### 类的职责是否设计得越单一越好？

```java
/**
 * Protocol format: identifier-string;{gson string}
 * For example: UEUEUE;{"a":"A","b":"B"}
 */
public class Serialization {
  private static final String IDENTIFIER_STRING = "UEUEUE;";
  private Gson gson;
  
  public Serialization() {
    this.gson = new Gson();
  }
  
  public String serialize(Map<String, String> object) {
    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append(IDENTIFIER_STRING);
    textBuilder.append(gson.toJson(object));
    return textBuilder.toString();
  }
  
  public Map<String, String> deserialize(String text) {
    if (!text.startsWith(IDENTIFIER_STRING)) {
        return Collections.emptyMap();
    }
    String gsonStr = text.substring(IDENTIFIER_STRING.length());
    return gson.fromJson(gsonStr, Map.class);
  }
}
```

如果我们想让类的职责更加单一，我们对 Serialization 类进一步拆分，拆分成一个只负责序列化工作的 Serializer 类和另一个只负责反序列化工作的 Deserializer 类。拆分后的具体代码如下所示：

```java
public class Serializer {
  private static final String IDENTIFIER_STRING = "UEUEUE;";
  private Gson gson;
  
  public Serializer() {
    this.gson = new Gson();
  }
  
  public String serialize(Map<String, String> object) {
    StringBuilder textBuilder = new StringBuilder();
    textBuilder.append(IDENTIFIER_STRING);
    textBuilder.append(gson.toJson(object));
    return textBuilder.toString();
  }
}

public class Deserializer {
  private static final String IDENTIFIER_STRING = "UEUEUE;";
  private Gson gson;
  
  public Deserializer() {
    this.gson = new Gson();
  }
  
  public Map<String, String> deserialize(String text) {
    if (!text.startsWith(IDENTIFIER_STRING)) {
        return Collections.emptyMap();
    }
    String gsonStr = text.substring(IDENTIFIER_STRING.length());
    return gson.fromJson(gsonStr, Map.class);
  }
}
```

虽然经过拆分之后，Serializer 类和 Deserializer 类的职责更加单一了，但也随之带来了新的问题。如果我们修改了协议的格式，数据标识从“UEUEUE”改为“DFDFDF”，或者序列化方式从 JSON 改为了 XML，那 Serializer 类和 Deserializer 类都需要做相应的修改，代码的内聚性显然没有原来 Serialization 高了。而且，如果我们仅仅对 Serializer 类做了协议修改，而忘记了修改 Deserializer 类的代码，那就会导致序列化、反序列化不匹配，程序运行出错，也就是说，拆分之后，代码的可维护性变差了。

### 总结

1. 如何理解单一职责原则（SRP）？

一个类只负责完成一个职责或者功能。不要设计大而全的类，要设计粒度小、功能单一的类。单一职责原则是为了实现代码高内聚、低耦合，提高代码的复用性、可读性、可维护性。

2. 如何判断类的职责是否足够单一？

不同的应用场景、不同阶段的需求背景、不同的业务层面，对同一个类的职责是否单一，可能会有不同的判定结果。实际上，一些侧面的判断指标更具有指导意义和可执行性，比如，出现下面这些情况就有可能说明这类的设计不满足单一职责原则：类中的代码行数、函数或者属性过多；类依赖的其他类过多，或者依赖类的其他类过多；私有方法过多；比较难给类起一个合适的名字；类中大量的方法都是集中操作类中的某几个属性。

3. 类的职责是否设计得越单一越好？

单一职责原则通过避免设计大而全的类，避免将不相关的功能耦合在一起，来提高类的内聚性。同时，类职责单一，类依赖的和被依赖的其他类也会变少，减少了代码的耦合性，以此来实现代码的高内聚、低耦合。但是，如果拆分得过细，实际上会适得其反，反倒会降低内聚性，也会影响代码的可维护性。

## 16 | 理论二：如何做到“对扩展开放、修改关闭”？扩展和修改各指什么？

### 如何理解“对扩展开放、修改关闭”？

开闭原则的英文全称是 Open Closed Principle，简写为 OCP。它的英文描述是：software entities (modules, classes, functions, etc.) should be open for extension , but closed for modification。我们把它翻译成中文就是：软件实体（模块、类、方法等）应该“对扩展开放、对修改关闭”。

这个描述比较简略，如果我们详细表述一下，那就是，添加一个新的功能应该是，在已有代码基础上扩展代码（新增模块、类、方法等），而非修改已有代码（修改模块、类、方法等）。

这是一段 API 接口监控告警的代码。其中，AlertRule 存储告警规则，可以自由设置。Notification 是告警通知类，支持邮件、短信、微信、手机等多种通知渠道。NotificationEmergencyLevel 表示通知的紧急程度，包括 SEVERE（严重）、URGENCY（紧急）、NORMAL（普通）、TRIVIAL（无关紧要），不同的紧急程度对应不同的发送渠道。关于 API 接口监控告警这部分，更加详细的业务需求分析和设计，我们会在后面的设计模式模块再拿出来进一步讲解，这里你只要简单知道这些，就够我们今天用了。

```java
public class Alert {
  private AlertRule rule;
  private Notification notification;

  public Alert(AlertRule rule, Notification notification) {
    this.rule = rule;
    this.notification = notification;
  }

  public void check(String api, long requestCount, long errorCount, long durationOfSeconds) {
    long tps = requestCount / durationOfSeconds;
    if (tps > rule.getMatchedRule(api).getMaxTps()) {
      notification.notify(NotificationEmergencyLevel.URGENCY, "...");
    }
    if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
      notification.notify(NotificationEmergencyLevel.SEVERE, "...");
    }
  }
}
```

上面这段代码非常简单，业务逻辑主要集中在 check() 函数中。当接口的 TPS 超过某个预先设置的最大值时，以及当接口请求出错数大于某个最大允许值时，就会触发告警，通知接口的相关负责人或者团队。

现在，如果我们需要添加一个功能，当每秒钟接口超时请求个数，超过某个预先设置的最大阈值时，我们也要触发告警发送通知。这个时候，我们该如何改动代码呢？主要的改动有两处：第一处是修改 check() 函数的入参，添加一个新的统计数据 timeoutCount，表示超时接口请求数；第二处是在 check() 函数中添加新的告警逻辑。具体的代码改动如下所示：

```java
public class Alert {
  // ...省略AlertRule/Notification属性和构造函数...
  
  // 改动一：添加参数timeoutCount
  public void check(String api, long requestCount, long errorCount, long timeoutCount, long durationOfSeconds) {
    long tps = requestCount / durationOfSeconds;
    if (tps > rule.getMatchedRule(api).getMaxTps()) {
      notification.notify(NotificationEmergencyLevel.URGENCY, "...");
    }
    if (errorCount > rule.getMatchedRule(api).getMaxErrorCount()) {
      notification.notify(NotificationEmergencyLevel.SEVERE, "...");
    }
    // 改动二：添加接口超时处理逻辑
    long timeoutTps = timeoutCount / durationOfSeconds;
    if (timeoutTps > rule.getMatchedRule(api).getMaxTimeoutTps()) {
      notification.notify(NotificationEmergencyLevel.URGENCY, "...");
    }
  }
}
```

这样的代码修改实际上存在挺多问题的。一方面，我们对接口进行了修改，这就意味着调用这个接口的代码都要做相应的修改。另一方面，修改了 check() 函数，相应的单元测试都需要修改（关于单元测试的内容我们在重构那部分会详细介绍）。

上面的代码改动是基于“修改”的方式来实现新功能的。如果我们遵循开闭原则，也就是“对扩展开放、对修改关闭”。那如何通过“扩展”的方式，来实现同样的功能呢？

我们先重构一下之前的 Alert 代码，让它的扩展性更好一些。重构的内容主要包含两部分：

- 第一部分是将 check() 函数的多个入参封装成 ApiStatInfo 类；
- 第二部分是引入 handler 的概念，将 if 判断逻辑分散在各个 handler 中。

```java
public class Alert {
  private List<AlertHandler> alertHandlers = new ArrayList<>();
  
  public void addAlertHandler(AlertHandler alertHandler) {
    this.alertHandlers.add(alertHandler);
  }

  public void check(ApiStatInfo apiStatInfo) {
    for (AlertHandler handler : alertHandlers) {
      handler.check(apiStatInfo);
    }
  }
}

public class ApiStatInfo {//省略constructor/getter/setter方法
  private String api;
  private long requestCount;
  private long errorCount;
  private long durationOfSeconds;
}

public abstract class AlertHandler {
  protected AlertRule rule;
  protected Notification notification;
  public AlertHandler(AlertRule rule, Notification notification) {
    this.rule = rule;
    this.notification = notification;
  }
  public abstract void check(ApiStatInfo apiStatInfo);
}

public class TpsAlertHandler extends AlertHandler {
  public TpsAlertHandler(AlertRule rule, Notification notification) {
    super(rule, notification);
  }

  @Override
  public void check(ApiStatInfo apiStatInfo) {
    long tps = apiStatInfo.getRequestCount()/ apiStatInfo.getDurationOfSeconds();
    if (tps > rule.getMatchedRule(apiStatInfo.getApi()).getMaxTps()) {
      notification.notify(NotificationEmergencyLevel.URGENCY, "...");
    }
  }
}

public class ErrorAlertHandler extends AlertHandler {
  public ErrorAlertHandler(AlertRule rule, Notification notification){
    super(rule, notification);
  }

  @Override
  public void check(ApiStatInfo apiStatInfo) {
    if (apiStatInfo.getErrorCount() > rule.getMatchedRule(apiStatInfo.getApi()).getMaxErrorCount()) {
      notification.notify(NotificationEmergencyLevel.SEVERE, "...");
    }
  }
}
```

ApplicationContext 是一个单例类，负责 Alert 的创建、组装（alertRule 和 notification 的依赖注入）、初始化（添加 handlers）工作。

```java
public class ApplicationContext {
  private AlertRule alertRule;
  private Notification notification;
  private Alert alert;
  
  public void initializeBeans() {
    alertRule = new AlertRule(/*.省略参数.*/); //省略一些初始化代码
    notification = new Notification(/*.省略参数.*/); //省略一些初始化代码
    alert = new Alert();
    alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
    alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
  }
  public Alert getAlert() { return alert; }

  // 饿汉式单例
  private static final ApplicationContext instance = new ApplicationContext();
  private ApplicationContext() {
    initializeBeans();
  }
  public static ApplicationContext getInstance() {
    return instance;
  }
}

public class Demo {
  public static void main(String[] args) {
    ApiStatInfo apiStatInfo = new ApiStatInfo();
    // ...省略设置apiStatInfo数据值的代码
    ApplicationContext.getInstance().getAlert().check(apiStatInfo);
  }
}
```

现在，我们再来看下，基于重构之后的代码，如果再添加上面讲到的那个新功能，每秒钟接口超时请求个数超过某个最大阈值就告警，我们又该如何改动代码呢？主要的改动有下面四处。

- 第一处改动是：在 ApiStatInfo 类中添加新的属性 timeoutCount。
- 第二处改动是：添加新的 TimeoutAlertHander 类。
- 第三处改动是：在 ApplicationContext 类的 initializeBeans() 方法中，往 alert 对象中注册新的 timeoutAlertHandler。
- 第四处改动是：在使用 Alert 类的时候，需要给 check() 函数的入参 apiStatInfo 对象设置 timeoutCount 的值。

```java

public class Alert { // 代码未改动... }
public class ApiStatInfo {//省略constructor/getter/setter方法
  private String api;
  private long requestCount;
  private long errorCount;
  private long durationOfSeconds;
  private long timeoutCount; // 改动一：添加新字段
}
public abstract class AlertHandler { //代码未改动... }
public class TpsAlertHandler extends AlertHandler {//代码未改动...}
public class ErrorAlertHandler extends AlertHandler {//代码未改动...}
// 改动二：添加新的handler
public class TimeoutAlertHandler extends AlertHandler {//省略代码...}

public class ApplicationContext {
  private AlertRule alertRule;
  private Notification notification;
  private Alert alert;
  
  public void initializeBeans() {
    alertRule = new AlertRule(/*.省略参数.*/); //省略一些初始化代码
    notification = new Notification(/*.省略参数.*/); //省略一些初始化代码
    alert = new Alert();
    alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
    alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
    // 改动三：注册handler
    alert.addAlertHandler(new TimeoutAlertHandler(alertRule, notification));
  }
  //...省略其他未改动代码...
}

public class Demo {
  public static void main(String[] args) {
    ApiStatInfo apiStatInfo = new ApiStatInfo();
    // ...省略apiStatInfo的set字段代码
    apiStatInfo.setTimeoutCount(289); // 改动四：设置tiemoutCount值
    ApplicationContext.getInstance().getAlert().check(apiStatInfo);
}
```

重构之后的代码更加灵活和易扩展。如果我们要想添加新的告警逻辑，只需要基于扩展的方式创建新的 handler 类即可，不需要改动原来的 check() 函数的逻辑。而且，我们只需要为新的 handler 类添加单元测试，老的单元测试都不会失败，也不用修改。

### 修改代码就意味着违背开闭原则吗？

看了上面重构之后的代码，你可能还会有疑问：在添加新的告警逻辑的时候，尽管改动二（添加新的 handler 类）是基于扩展而非修改的方式来完成的，但改动一、三、四貌似不是基于扩展而是基于修改的方式来完成的，那改动一、三、四不就违背了开闭原则吗？

1. 往 ApiStatInfo 类中添加新的属性 timeoutCount。

实际上，我们不仅往 ApiStatInfo 类中添加了属性，还添加了对应的 getter/setter 方法。那这个问题就转化为：给类中添加新的属性和方法，算作“修改”还是“扩展”？

我们再一块回忆一下开闭原则的定义：软件实体（模块、类、方法等）应该“对扩展开放、对修改关闭”。从定义中，我们可以看出，开闭原则可以应用在不同粒度的代码中，可以是模块，也可以类，还可以是方法（及其属性）。同样一个代码改动，在粗代码粒度下，被认定为“修改”，在细代码粒度下，又可以被认定为“扩展”。比如，改动一，添加属性和方法相当于修改类，在类这个层面，这个代码改动可以被认定为“修改”；但这个代码改动并没有修改已有的属性和方法，在方法（及其属性）这一层面，它又可以被认定为“扩展”。

实际上，我们也没必要纠结某个代码改动是“修改”还是“扩展”，更没必要太纠结它是否违反“开闭原则”。我们回到这条原则的设计初衷：只要它没有破坏原有的代码的正常运行，没有破坏原有的单元测试，我们就可以说，这是一个合格的代码改动。

2. 在 ApplicationContext 类的 initializeBeans() 方法中，往 alert 对象中注册新的 timeoutAlertHandler；在使用 Alert 类的时候，需要给 check() 函数的入参 apiStatInfo 对象设置 timeoutCount 的值。

这两处改动都是在方法内部进行的，不管从哪个层面（模块、类、方法）来讲，都不能算是“扩展”，而是地地道道的“修改”。不过，有些修改是在所难免的，是可以被接受的。为什么这么说呢？我来解释一下。

在重构之后的 Alert 代码中，我们的核心逻辑集中在 Alert 类及其各个 handler 中，当我们在添加新的告警逻辑的时候，Alert 类完全不需要修改，而只需要扩展一个新 handler 类。如果我们把 Alert 类及各个 handler 类合起来看作一个“模块”，那模块本身在添加新的功能的时候，完全满足开闭原则。

而且，我们要认识到，添加一个新功能，不可能任何模块、类、方法的代码都不“修改”，这个是做不到的。类需要创建、组装、并且做一些初始化操作，才能构建成可运行的的程序，这部分代码的修改是在所难免的。我们要做的是尽量让修改操作更集中、更少、更上层，尽量让最核心、最复杂的那部分逻辑代码满足开闭原则。

### 如何做到“对扩展开放、修改关闭”？

实际上，开闭原则讲的就是代码的扩展性问题，是判断一段代码是否易扩展的“金标准”。如果某段代码在应对未来需求变化的时候，能够做到“对扩展开放、对修改关闭”，那就说明这段代码的扩展性比较好。所以，问如何才能做到“对扩展开放、对修改关闭”，也就粗略地等同于在问，如何才能写出扩展性好的代码。

在讲具体的方法论之前，我们先来看一些更加偏向顶层的指导思想。为了尽量写出扩展性好的代码，我们要时刻具备扩展意识、抽象意识、封装意识。这些“潜意识”可能比任何开发技巧都重要。

比如，我们代码中通过 Kafka 来发送异步消息。对于这样一个功能的开发，我们要学会将其抽象成一组跟具体消息队列（Kafka）无关的异步消息接口。所有上层系统都依赖这组抽象的接口编程，并且通过依赖注入的方式来调用。当我们要替换新的消息队列的时候，比如将 Kafka 替换成 RocketMQ，可以很方便地拔掉老的消息队列实现，插入新的消息队列实现。具体代码如下所示：

```java
// 这一部分体现了抽象意识
public interface MessageQueue { //... }
public class KafkaMessageQueue implements MessageQueue { //... }
public class RocketMQMessageQueue implements MessageQueue {//...}

public interface MessageFromatter { //... }
public class JsonMessageFromatter implements MessageFromatter {//...}
public class ProtoBufMessageFromatter implements MessageFromatter {//...}

public class Demo {
  private MessageQueue msgQueue; // 基于接口而非实现编程
  public Demo(MessageQueue msgQueue) { // 依赖注入
    this.msgQueue = msgQueue;
  }
  
  // msgFormatter：多态、依赖注入
  public void sendNotification(Notification notification, MessageFormatter msgFormatter) {
    //...    
  }
}
```

### 如何在项目中灵活应用开闭原则？

如果你开发的是一个业务导向的系统，比如金融系统、电商系统、物流系统等，要想识别出尽可能多的扩展点，就要对业务有足够的了解，能够知道当下以及未来可能要支持的业务需求。如果你开发的是跟业务无关的、通用的、偏底层的系统，比如，框架、组件、类库，你需要了解“它们会被如何使用？今后你打算添加哪些功能？使用者未来会有哪些更多的功能需求？”等问题。

## 17 | 理论三：里式替换（LSP）跟多态有何区别？哪些代码违背了LSP？

### 如何理解“里式替换原则”？

里式替换原则的英文翻译是：Liskov Substitution Principle，缩写为 LSP。这个原则最早是在 1986 年由 Barbara Liskov 提出，他是这么描述这条原则的：

If S is a subtype of T, then objects of type T may be replaced with objects of type S, without breaking the program。

在 1996 年，Robert Martin 在他的 SOLID 原则中，重新描述了这个原则，英文原话是这样的：

Functions that use pointers of references to base classes must be able to use objects of derived classes without knowing it。

我们综合两者的描述，将这条原则用中文描述出来，是这样的：子类对象（object of subtype/derived class）能够替换程序（program）中父类对象（object of base/parent class）出现的任何地方，并且保证原来程序的逻辑行为（behavior）不变及正确性不被破坏。

这么说还是比较抽象，我们通过一个例子来解释一下。如下代码中，父类 Transporter 使用 org.apache.http 库中的 HttpClient 类来传输网络数据。子类 SecurityTransporter 继承父类 Transporter，增加了额外的功能，支持传输 appId 和 appToken 安全认证信息。

```java
public class Transporter {
  private HttpClient httpClient;
  
  public Transporter(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Response sendRequest(Request request) {
    // ...use httpClient to send request
  }
}

public class SecurityTransporter extends Transporter {
  private String appId;
  private String appToken;

  public SecurityTransporter(HttpClient httpClient, String appId, String appToken) {
    super(httpClient);
    this.appId = appId;
    this.appToken = appToken;
  }

  @Override
  public Response sendRequest(Request request) {
    if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appToken)) {
      request.addPayload("app-id", appId);
      request.addPayload("app-token", appToken);
    }
    return super.sendRequest(request);
  }
}

public class Demo {    
  public void demoFunction(Transporter transporter) {    
    Reuqest request = new Request();
    //...省略设置request中数据值的代码...
    Response response = transporter.sendRequest(request);
    //...省略其他逻辑...
  }
}

// 里式替换原则
Demo demo = new Demo();
demo.demofunction(new SecurityTransporter(/*省略参数*/););
```

在上面的代码中，子类 SecurityTransporter 的设计完全符合里式替换原则，可以替换父类出现的任何位置，并且原来代码的逻辑行为不变且正确性也没有被破坏。

不过，你可能会有这样的疑问，刚刚的代码设计不就是简单利用了面向对象的多态特性吗？多态和里式替换原则说的是不是一回事呢？从刚刚的例子和定义描述来看，里式替换原则跟多态看起来确实有点类似，但实际上它们完全是两回事。为什么这么说呢？我们还是通过刚才这个例子来解释一下。不过，我们需要对 SecurityTransporter 类中 sendRequest() 函数稍加改造一下。改造前，如果 appId 或者 appToken 没有设置，我们就不做校验；改造后，如果 appId 或者 appToken 没有设置，则直接抛出 NoAuthorizationRuntimeException 未授权异常。改造前后的代码对比如下所示：

```java
// 改造前：
public class SecurityTransporter extends Transporter {
  //...省略其他代码..
  @Override
  public Response sendRequest(Request request) {
    if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appToken)) {
      request.addPayload("app-id", appId);
      request.addPayload("app-token", appToken);
    }
    return super.sendRequest(request);
  }
}

// 改造后：
public class SecurityTransporter extends Transporter {
  //...省略其他代码..
  @Override
  public Response sendRequest(Request request) {
    if (StringUtils.isBlank(appId) || StringUtils.isBlank(appToken)) {
      throw new NoAuthorizationRuntimeException(...);
    }
    request.addPayload("app-id", appId);
    request.addPayload("app-token", appToken);
    return super.sendRequest(request);
  }
}
```

在改造之后的代码中，如果传递进 demoFunction() 函数的是父类 Transporter 对象，那 demoFunction() 函数并不会有异常抛出，但如果传递给 demoFunction() 函数的是子类 SecurityTransporter 对象，那 demoFunction() 有可能会有异常抛出。尽管代码中抛出的是运行时异常（Runtime Exception），我们可以不在代码中显式地捕获处理，但子类替换父类传递进 demoFunction 函数之后，整个程序的逻辑行为有了改变。

虽然改造之后的代码仍然可以通过 Java 的多态语法，动态地用子类 SecurityTransporter 来替换父类 Transporter，也并不会导致程序编译或者运行报错。但是，从设计思路上来讲，SecurityTransporter 的设计是不符合里式替换原则的。

好了，我们稍微总结一下。虽然从定义描述和代码实现上来看，多态和里式替换有点类似，但它们关注的角度是不一样的。多态是面向对象编程的一大特性，也是面向对象编程语言的一种语法。它是一种代码实现的思路。而里式替换是一种设计原则，是用来指导继承关系中子类该如何设计的，子类的设计要保证在替换父类的时候，不改变原有程序的逻辑以及不破坏原有程序的正确性。

### 哪些代码明显违背了 LSP？

看起来比较抽象，我来进一步解读一下。子类在设计的时候，要遵守父类的行为约定（或者叫协议）。父类定义了函数的行为约定，那子类可以改变函数的内部实现逻辑，但不能改变函数原有的行为约定。这里的行为约定包括：函数声明要实现的功能；对输入、输出、异常的约定；甚至包括注释中所罗列的任何特殊说明。实际上，定义中父类和子类之间的关系，也可以替换成接口和实现类之间的关系。

为了更好地理解这句话，我举几个违反里式替换原则的例子来解释一下。

1. 子类违背父类声明要实现的功能

父类中提供的 sortOrdersByAmount() 订单排序函数，是按照金额从小到大来给订单排序的，而子类重写这个 sortOrdersByAmount() 订单排序函数之后，是按照创建日期来给订单排序的。那子类的设计就违背里式替换原则。

2. 子类违背父类对输入、输出、异常的约定

在父类中，某个函数约定：运行出错的时候返回 null；获取数据为空的时候返回空集合（empty collection）。而子类重载函数之后，实现变了，运行出错返回异常（exception），获取不到数据返回 null。那子类的设计就违背里式替换原则。

在父类中，某个函数约定，输入数据可以是任意整数，但子类实现的时候，只允许输入数据是正整数，负数就抛出，也就是说，子类对输入的数据的校验比父类更加严格，那子类的设计就违背了里式替换原则。

在父类中，某个函数约定，只会抛出 ArgumentNullException 异常，那子类的设计实现中只允许抛出 ArgumentNullException 异常，任何其他异常的抛出，都会导致子类违背里式替换原则。

3. 子类违背父类注释中所罗列的任何特殊说明

父类中定义的 withdraw() 提现函数的注释是这么写的：“用户的提现金额不得超过账户余额……”，而子类重写 withdraw() 函数之后，针对 VIP 账号实现了透支提现的功能，也就是提现金额可以大于账户余额，那这个子类的设计也是不符合里式替换原则的。

以上便是三种典型的违背里式替换原则的情况。除此之外，判断子类的设计实现是否违背里式替换原则，还有一个小窍门，那就是拿父类的单元测试去验证子类的代码。如果某些单元测试运行失败，就有可能说明，子类的设计实现没有完全地遵守父类的约定，子类有可能违背了里式替换原则。

## 18 | 理论四：接口隔离原则有哪三种应用？原则中的“接口”该如何理解？

### 如何理解“接口隔离原则”？

接口隔离原则的英文翻译是“ Interface Segregation Principle”，缩写为 ISP。Robert Martin 在 SOLID 原则中是这样定义它的：“Clients should not be forced to depend upon interfaces that they do not use。”直译成中文的话就是：客户端不应该被强迫依赖它不需要的接口。其中的“客户端”，可以理解为接口的调用者或者使用者。

在这条原则中，我们可以把“接口”理解为下面三种东西：

- 一组 API 接口集合
- 单个 API 接口或函数
- OOP 中的接口概念

### 把“接口”理解为一组 API 接口集合

我们还是结合一个例子来讲解。微服务用户系统提供了一组跟用户相关的 API 给其他系统使用，比如：注册、登录、获取用户信息等。具体代码如下所示：

```java
public interface UserService {
  boolean register(String cellphone, String password);
  boolean login(String cellphone, String password);
  UserInfo getUserInfoById(long id);
  UserInfo getUserInfoByCellphone(String cellphone);
}

public class UserServiceImpl implements UserService {
  //...
}
```

现在，我们的后台管理系统要实现删除用户的功能，希望用户系统提供一个删除用户的接口。这个时候我们该如何来做呢？你可能会说，这不是很简单吗，我只需要在 UserService 中新添加一个 deleteUserByCellphone() 或 deleteUserById() 接口就可以了。这个方法可以解决问题，但是也隐藏了一些安全隐患。

删除用户是一个非常慎重的操作，我们只希望通过后台管理系统来执行，所以这个接口只限于给后台管理系统使用。如果我们把它放到 UserService 中，那所有使用到 UserService 的系统，都可以调用这个接口。不加限制地被其他业务系统调用，就有可能导致误删用户。

当然，最好的解决方案是从架构设计的层面，通过接口鉴权的方式来限制接口的调用。不过，如果暂时没有鉴权框架来支持，我们还可以从代码设计的层面，尽量避免接口被误用。我们参照接口隔离原则，调用者不应该强迫依赖它不需要的接口，将删除接口单独放到另外一个接口 RestrictedUserService 中，然后将 RestrictedUserService 只打包提供给后台管理系统来使用。具体的代码实现如下所示：

```java
public interface UserService {
  boolean register(String cellphone, String password);
  boolean login(String cellphone, String password);
  UserInfo getUserInfoById(long id);
  UserInfo getUserInfoByCellphone(String cellphone);
}

public interface RestrictedUserService {
  boolean deleteUserByCellphone(String cellphone);
  boolean deleteUserById(long id);
}

public class UserServiceImpl implements UserService, RestrictedUserService {
  // ...省略实现代码...
}
```

在刚刚的这个例子中，我们把接口隔离原则中的接口，理解为一组接口集合，它可以是某个微服务的接口，也可以是某个类库的接口等等。在设计微服务或者类库接口的时候，如果部分接口只被部分调用者使用，那我们就需要将这部分接口隔离出来，单独给对应的调用者使用，而不是强迫其他调用者也依赖这部分不会被用到的接口。

### 把“接口”理解为单个 API 接口或函数

现在我们再换一种理解方式，把接口理解为单个接口或函数（以下为了方便讲解，我都简称为“函数”）。那接口隔离原则就可以理解为：函数的设计要功能单一，不要将多个不同的功能逻辑在一个函数中实现。接下来，我们还是通过一个例子来解释一下。

```java
public class Statistics {
  private Long max;
  private Long min;
  private Long average;
  private Long sum;
  private Long percentile99;
  private Long percentile999;
  //...省略constructor/getter/setter等方法...
}

public Statistics count(Collection<Long> dataSet) {
  Statistics statistics = new Statistics();
  //...省略计算逻辑...
  return statistics;
}
```

在上面的代码中，count() 函数的功能不够单一，包含很多不同的统计功能，比如，求最大值、最小值、平均值等等。按照接口隔离原则，我们应该把 count() 函数拆成几个更小粒度的函数，每个函数负责一个独立的统计功能。拆分之后的代码如下所示：

```java
public Long max(Collection<Long> dataSet) { //... }
public Long min(Collection<Long> dataSet) { //... } 
public Long average(Colletion<Long> dataSet) { //... }
// ...省略其他统计函数...
```

如果在项目中，对每个统计需求，Statistics 定义的那几个统计信息都有涉及，那 count() 函数的设计就是合理的。相反，如果每个统计需求只涉及 Statistics 罗列的统计信息中一部分，比如，有的只需要用到 max、min、average 这三类统计信息，有的只需要用到 average、sum。而 count() 函数每次都会把所有的统计信息计算一遍，就会做很多无用功，势必影响代码的性能，特别是在需要统计的数据量很大的时候。所以，在这个应用场景下，count() 函数的设计就有点不合理了，我们应该按照第二种设计思路，将其拆分成粒度更细的多个统计函数。

不过，你应该已经发现，接口隔离原则跟单一职责原则有点类似，不过稍微还是有点区别。单一职责原则针对的是模块、类、接口的设计。而接口隔离原则相对于单一职责原则，一方面它更侧重于接口的设计，另一方面它的思考的角度不同。它提供了一种判断接口是否职责单一的标准：通过调用者如何使用接口来间接地判定。如果调用者只使用部分接口或接口的部分功能，那接口的设计就不够职责单一。

### 把“接口”理解为 OOP 中的接口概念

除了刚讲过的两种理解方式，我们还可以把“接口”理解为 OOP 中的接口概念，比如 Java 中的 interface。

假设我们的项目中用到了三个外部系统：Redis、MySQL、Kafka。每个系统都对应一系列配置信息，比如地址、端口、访问超时时间等。为了在内存中存储这些配置信息，供项目中的其他模块来使用，我们分别设计实现了三个 Configuration 类：RedisConfig、MysqlConfig、KafkaConfig。具体的代码实现如下所示。注意，这里我只给出了 RedisConfig 的代码实现，另外两个都是类似的，我这里就不贴了。

```mysql
public class RedisConfig {
    private ConfigSource configSource; //配置中心（比如zookeeper）
    private String address;
    private int timeout;
    private int maxTotal;
    //省略其他配置: maxWaitMillis,maxIdle,minIdle...

    public RedisConfig(ConfigSource configSource) {
        this.configSource = configSource;
    }

    public String getAddress() {
        return this.address;
    }
    //...省略其他get()、init()方法...

    public void update() {
      //从configSource加载配置到address/timeout/maxTotal...
    }
}

public class KafkaConfig { //...省略... }
public class MysqlConfig { //...省略... }
```

现在，我们有一个新的功能需求，希望支持 Redis 和 Kafka 配置信息的热更新。所谓“热更新（hot update）”就是，如果在配置中心中更改了配置信息，我们希望在不用重启系统的情况下，能将最新的配置信息加载到内存中（也就是 RedisConfig、KafkaConfig 类中）。但是，因为某些原因，我们并不希望对 MySQL 的配置信息进行热更新。

为了实现这样一个功能需求，我们设计实现了一个 ScheduledUpdater 类，以固定时间频率（periodInSeconds）来调用 RedisConfig、KafkaConfig 的 update() 方法更新配置信息。具体的代码实现如下所示：

```java
public interface Updater {
  void update();
}

public class RedisConfig implemets Updater {
  //...省略其他属性和方法...
  @Override
  public void update() { //... }
}

public class KafkaConfig implements Updater {
  //...省略其他属性和方法...
  @Override
  public void update() { //... }
}

public class MysqlConfig { //...省略其他属性和方法... }

public class ScheduledUpdater {
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();;
    private long initialDelayInSeconds;
    private long periodInSeconds;
    private Updater updater;

    public ScheduleUpdater(Updater updater, long initialDelayInSeconds, long periodInSeconds) {
        this.updater = updater;
        this.initialDelayInSeconds = initialDelayInSeconds;
        this.periodInSeconds = periodInSeconds;
    }

    public void run() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updater.update();
            }
        }, this.initialDelayInSeconds, this.periodInSeconds, TimeUnit.SECONDS);
    }
}

public class Application {
  ConfigSource configSource = new ZookeeperConfigSource(/*省略参数*/);
  public static final RedisConfig redisConfig = new RedisConfig(configSource);
  public static final KafkaConfig kafkaConfig = new KakfaConfig(configSource);
  public static final MySqlConfig mysqlConfig = new MysqlConfig(configSource);

  public static void main(String[] args) {
    ScheduledUpdater redisConfigUpdater = new ScheduledUpdater(redisConfig, 300, 300);
    redisConfigUpdater.run();
    
    ScheduledUpdater kafkaConfigUpdater = new ScheduledUpdater(kafkaConfig, 60, 60);
    redisConfigUpdater.run();
  }
}
```

刚刚的热更新的需求我们已经搞定了。现在，我们又有了一个新的监控功能需求。通过命令行来查看 Zookeeper 中的配置信息是比较麻烦的。所以，我们希望能有一种更加方便的配置信息查看方式。我们可以在项目中开发一个内嵌的 SimpleHttpServer，输出项目的配置信息到一个固定的 HTTP 地址，比如：http://127.0.0.1:2389/config 。我们只需要在浏览器中输入这个地址，就可以显示出系统的配置信息。不过，出于某些原因，我们只想暴露 MySQL 和 Redis 的配置信息，不想暴露 Kafka 的配置信息。为了实现这样一个功能，我们还需要对上面的代码做进一步改造。改造之后的代码如下所示：

```mysql
public interface Updater {
  void update();
}

public interface Viewer {
  String outputInPlainText();
  Map<String, String> output();
}

public class RedisConfig implemets Updater, Viewer {
  //...省略其他属性和方法...
  @Override
  public void update() { //... }
  @Override
  public String outputInPlainText() { //... }
  @Override
  public Map<String, String> output() { //...}
}

public class KafkaConfig implements Updater {
  //...省略其他属性和方法...
  @Override
  public void update() { //... }
}

public class MysqlConfig implements Viewer {
  //...省略其他属性和方法...
  @Override
  public String outputInPlainText() { //... }
  @Override
  public Map<String, String> output() { //...}
}

public class SimpleHttpServer {
  private String host;
  private int port;
  private Map<String, List<Viewer>> viewers = new HashMap<>();
  
  public SimpleHttpServer(String host, int port) {//...}
  
  public void addViewers(String urlDirectory, Viewer viewer) {
    if (!viewers.containsKey(urlDirectory)) {
      viewers.put(urlDirectory, new ArrayList<Viewer>());
    }
    this.viewers.get(urlDirectory).add(viewer);
  }
  
  public void run() { //... }
}

public class Application {
    ConfigSource configSource = new ZookeeperConfigSource();
    public static final RedisConfig redisConfig = new RedisConfig(configSource);
    public static final KafkaConfig kafkaConfig = new KakfaConfig(configSource);
    public static final MySqlConfig mysqlConfig = new MySqlConfig(configSource);
    
    public static void main(String[] args) {
        ScheduledUpdater redisConfigUpdater =
            new ScheduledUpdater(redisConfig, 300, 300);
        redisConfigUpdater.run();
        
        ScheduledUpdater kafkaConfigUpdater =
            new ScheduledUpdater(kafkaConfig, 60, 60);
        redisConfigUpdater.run();
        
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(“127.0.0.1”, 2389);
        simpleHttpServer.addViewer("/config", redisConfig);
        simpleHttpServer.addViewer("/config", mysqlConfig);
        simpleHttpServer.run();
    }
}
```

至此，热更新和监控的需求我们就都实现了。我们来回顾一下这个例子的设计思想。

我们设计了两个功能非常单一的接口：Updater 和 Viewer。ScheduledUpdater 只依赖 Updater 这个跟热更新相关的接口，不需要被强迫去依赖不需要的 Viewer 接口，满足接口隔离原则。同理，SimpleHttpServer 只依赖跟查看信息相关的 Viewer 接口，不依赖不需要的 Updater 接口，也满足接口隔离原则。

你可能会说，如果我们不遵守接口隔离原则，不设计 Updater 和 Viewer 两个小接口，而是设计一个大而全的 Config 接口，让 RedisConfig、KafkaConfig、MysqlConfig 都实现这个 Config 接口，并且将原来传递给 ScheduledUpdater 的 Updater 和传递给 SimpleHttpServer 的 Viewer，都替换为 Config，那会有什么问题呢？我们先来看一下，按照这个思路来实现的代码是什么样的。

```java
public interface Config {
  void update();
  String outputInPlainText();
  Map<String, String> output();
}

public class RedisConfig implements Config {
  //...需要实现Config的三个接口update/outputIn.../output
}

public class KafkaConfig implements Config {
  //...需要实现Config的三个接口update/outputIn.../output
}

public class MysqlConfig implements Config {
  //...需要实现Config的三个接口update/outputIn.../output
}

public class ScheduledUpdater {
  //...省略其他属性和方法..
  private Config config;

  public ScheduleUpdater(Config config, long initialDelayInSeconds, long periodInSeconds) {
      this.config = config;
      //...
  }
  //...
}

public class SimpleHttpServer {
  private String host;
  private int port;
  private Map<String, List<Config>> viewers = new HashMap<>();
 
  public SimpleHttpServer(String host, int port) {//...}
  
  public void addViewer(String urlDirectory, Config config) {
    if (!viewers.containsKey(urlDirectory)) {
      viewers.put(urlDirectory, new ArrayList<Config>());
    }
    viewers.get(urlDirectory).add(config);
  }
  
  public void run() { //... }
}
```

这样的设计思路也是能工作的，但是对比前后两个设计思路，在同样的代码量、实现复杂度、同等可读性的情况下，第一种设计思路显然要比第二种好很多。为什么这么说呢？主要有两点原因。

首先，第一种设计思路更加灵活、易扩展、易复用。因为 Updater、Viewer 职责更加单一，单一就意味了通用、复用性好。比如，我们现在又有一个新的需求，开发一个 Metrics 性能统计模块，并且希望将 Metrics 也通过 SimpleHttpServer 显示在网页上，以方便查看。这个时候，尽管 Metrics 跟 RedisConfig 等没有任何关系，但我们仍然可以让 Metrics 类实现非常通用的 Viewer 接口，复用 SimpleHttpServer 的代码实现。具体的代码如下所示：

```java
public class ApiMetrics implements Viewer {//...}
public class DbMetrics implements Viewer {//...}

public class Application {
    ConfigSource configSource = new ZookeeperConfigSource();
    public static final RedisConfig redisConfig = new RedisConfig(configSource);
    public static final KafkaConfig kafkaConfig = new KakfaConfig(configSource);
    public static final MySqlConfig mySqlConfig = new MySqlConfig(configSource);
    public static final ApiMetrics apiMetrics = new ApiMetrics();
    public static final DbMetrics dbMetrics = new DbMetrics();
    
    public static void main(String[] args) {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(“127.0.0.1”, 2389);
        simpleHttpServer.addViewer("/config", redisConfig);
        simpleHttpServer.addViewer("/config", mySqlConfig);
        simpleHttpServer.addViewer("/metrics", apiMetrics);
        simpleHttpServer.addViewer("/metrics", dbMetrics);
        simpleHttpServer.run();
    }
}
```

其次，第二种设计思路在代码实现上做了一些无用功。因为 Config 接口中包含两类不相关的接口，一类是 update()，一类是 output() 和 outputInPlainText()。理论上，KafkaConfig 只需要实现 update() 接口，并不需要实现 output() 相关的接口。同理，MysqlConfig 只需要实现 output() 相关接口，并需要实现 update() 接口。但第二种设计思路要求 RedisConfig、KafkaConfig、MySqlConfig 必须同时实现 Config 的所有接口函数（update、output、outputInPlainText）。除此之外，如果我们要往 Config 中继续添加一个新的接口，那所有的实现类都要改动。相反，如果我们的接口粒度比较小，那涉及改动的类就比较少。

## 19 | 理论五：控制反转、依赖反转、依赖注入，这三者有何区别和联系？

### 控制反转（IOC）

```java
public class UserServiceTest {
  public static boolean doTest() {
    // ... 
  }
  
  public static void main(String[] args) {//这部分逻辑可以放到框架中
    if (doTest()) {
      System.out.println("Test succeed.");
    } else {
      System.out.println("Test failed.");
    }
  }
}
```

在上面的代码中，所有的流程都由程序员来控制。如果我们抽象出一个下面这样一个框架，我们再来看，如何利用框架来实现同样的功能。具体的代码实现如下所示：

```java
public abstract class TestCase {
  public void run() {
    if (doTest()) {
      System.out.println("Test succeed.");
    } else {
      System.out.println("Test failed.");
    }
  }
  
  public abstract boolean doTest();
}

public class JunitApplication {
  private static final List<TestCase> testCases = new ArrayList<>();
  
  public static void register(TestCase testCase) {
    testCases.add(testCase);
  }
  
  public static final void main(String[] args) {
    for (TestCase case: testCases) {
      case.run();
    }
  }
```

把这个简化版本的测试框架引入到工程中之后，我们只需要在框架预留的扩展点，也就是 TestCase 类中的 doTest() 抽象函数中，填充具体的测试代码就可以实现之前的功能了，完全不需要写负责执行流程的 main() 函数了。 具体的代码如下所示：

```java
public class UserServiceTest extends TestCase {
  @Override
  public boolean doTest() {
    // ... 
  }
}

// 注册操作还可以通过配置的方式来实现，不需要程序员显示调用register()
JunitApplication.register(new UserServiceTest();
```

刚刚举的这个例子，就是典型的通过框架来实现“控制反转”的例子。框架提供了一个可扩展的代码骨架，用来组装对象、管理整个执行流程。程序员利用框架进行开发的时候，只需要往预留的扩展点上，添加跟自己业务相关的代码，就可以利用框架来驱动整个程序流程的执行。

这里的“控制”指的是对程序执行流程的控制，而“反转”指的是在没有使用框架之前，程序员自己控制整个程序的执行。在使用框架之后，整个程序的执行流程可以通过框架来控制。流程的控制权从程序员“反转”到了框架。

实际上，实现控制反转的方法有很多，除了刚才例子中所示的类似于模板设计模式的方法之外，还有马上要讲到的依赖注入等方法，所以，控制反转并不是一种具体的实现技巧，而是一个比较笼统的设计思想，一般用来指导框架层面的设计。

### 依赖注入（DI）

接下来，我们再来看依赖注入。依赖注入跟控制反转恰恰相反，它是一种具体的编码技巧。依赖注入的英文翻译是 Dependency Injection，缩写为 DI。那到底什么是依赖注入呢？我们用一句话来概括就是：不通过 new() 的方式在类内部创建依赖类对象，而是将依赖的类对象在外部创建好之后，通过构造函数、函数参数等方式传递（或注入）给类使用。

```java
// 非依赖注入实现方式
public class Notification {
  private MessageSender messageSender;
  
  public Notification() {
    this.messageSender = new MessageSender(); //此处有点像hardcode
  }
  
  public void sendMessage(String cellphone, String message) {
    //...省略校验逻辑等...
    this.messageSender.send(cellphone, message);
  }
}

public class MessageSender {
  public void send(String cellphone, String message) {
    //....
  }
}
// 使用Notification
Notification notification = new Notification();

// 依赖注入的实现方式
public class Notification {
  private MessageSender messageSender;
  
  // 通过构造函数将messageSender传递进来
  public Notification(MessageSender messageSender) {
    this.messageSender = messageSender;
  }
  
  public void sendMessage(String cellphone, String message) {
    //...省略校验逻辑等...
    this.messageSender.send(cellphone, message);
  }
}
//使用Notification
MessageSender messageSender = new MessageSender();
Notification notification = new Notification(messageSender);
```

通过依赖注入的方式来将依赖的类对象传递进来，这样就提高了代码的扩展性，我们可以灵活地替换依赖的类。这一点在我们之前讲“开闭原则”的时候也提到过。当然，上面代码还有继续优化的空间，我们还可以把 MessageSender 定义成接口，基于接口而非实现编程。改造后的代码如下所示：

```java
public class Notification {
  private MessageSender messageSender;
  
  public Notification(MessageSender messageSender) {
    this.messageSender = messageSender;
  }
  
  public void sendMessage(String cellphone, String message) {
    this.messageSender.send(cellphone, message);
  }
}

public interface MessageSender {
  void send(String cellphone, String message);
}

// 短信发送类
public class SmsSender implements MessageSender {
  @Override
  public void send(String cellphone, String message) {
    //....
  }
}

// 站内信发送类
public class InboxSender implements MessageSender {
  @Override
  public void send(String cellphone, String message) {
    //....
  }
}

//使用Notification
MessageSender messageSender = new SmsSender();
Notification notification = new Notification(messageSender);
```

实际上，你只需要掌握刚刚举的这个例子，就等于完全掌握了依赖注入。尽管依赖注入非常简单，但却非常有用，在后面的章节中，我们会讲到，它是编写可测试性代码最有效的手段。

### 依赖注入框架（DI Framework）

在采用依赖注入实现的 Notification 类中，虽然我们不需要用类似 hard code 的方式，在类内部通过 new 来创建 MessageSender 对象，但是，这个创建对象、组装（或注入）对象的工作仅仅是被移动到了更上层代码而已，还是需要我们程序员自己来实现。具体代码如下所示：

```java
public class Demo {
  public static final void main(String args[]) {
    MessageSender sender = new SmsSender(); //创建对象
    Notification notification = new Notification(sender);//依赖注入
    notification.sendMessage("13918942177", "短信验证码：2346");
  }
}
```

在实际的软件开发中，一些项目可能会涉及几十、上百、甚至几百个类，类对象的创建和依赖注入会变得非常复杂。如果这部分工作都是靠程序员自己写代码来完成，容易出错且开发成本也比较高。而对象创建和依赖注入的工作，本身跟具体的业务无关，我们完全可以抽象成框架来自动完成。

你可能已经猜到，这个框架就是“依赖注入框架”。我们只需要通过依赖注入框架提供的扩展点，简单配置一下所有需要创建的类对象、类与类之间的依赖关系，就可以实现由框架来自动创建对象、管理对象的生命周期、依赖注入等原本需要程序员来做的事情。

实际上，现成的依赖注入框架有很多，比如 Google Guice、Java Spring、Pico Container、Butterfly Container 等。不过，如果你熟悉 Java Spring 框架，你可能会说，Spring 框架自己声称是控制反转容器（Inversion Of Control Container）。

实际上，这两种说法都没错。只是控制反转容器这种表述是一种非常宽泛的描述，DI 依赖注入框架的表述更具体、更有针对性。因为我们前面讲到实现控制反转的方式有很多，除了依赖注入，还有模板模式等，而 Spring 框架的控制反转主要是通过依赖注入来实现的。不过这点区分并不是很明显，也不是很重要，你稍微了解一下就可以了。

### 依赖反转原则（DIP）

前面讲了控制反转、依赖注入、依赖注入框架，现在，我们来讲一讲今天的主角：依赖反转原则。依赖反转原则的英文翻译是 Dependency Inversion Principle，缩写为 DIP。中文翻译有时候也叫依赖倒置原则。

为了追本溯源，我先给出这条原则最原汁原味的英文描述：

> High-level modules shouldn’t depend on low-level modules. Both modules should depend on abstractions. In addition, abstractions shouldn’t depend on details. Details depend on abstractions.

我们将它翻译成中文，大概意思就是：高层模块（high-level modules）不要依赖低层模块（low-level）。高层模块和低层模块应该通过抽象（abstractions）来互相依赖。除此之外，抽象（abstractions）不要依赖具体实现细节（details），具体实现细节（details）依赖抽象（abstractions）。

所谓高层模块和低层模块的划分，简单来说就是，在调用链上，调用者属于高层，被调用者属于低层。在平时的业务代码开发中，高层模块依赖底层模块是没有任何问题的。实际上，这条原则主要还是用来指导框架层面的设计，跟前面讲到的控制反转类似。我们拿 Tomcat 这个 Servlet 容器作为例子来解释一下。

Tomcat 是运行 Java Web 应用程序的容器。我们编写的 Web 应用程序代码只需要部署在 Tomcat 容器下，便可以被 Tomcat 容器调用执行。按照之前的划分原则，Tomcat 就是高层模块，我们编写的 Web 应用程序代码就是低层模块。Tomcat 和应用程序代码之间并没有直接的依赖关系，两者都依赖同一个“抽象”，也就是 Servlet 规范。Servlet 规范不依赖具体的 Tomcat 容器和应用程序的实现细节，而 Tomcat 容器和应用程序依赖 Servlet 规范。

## 20 | 理论六：我为何说KISS、YAGNI原则看似简单，却经常被用错？

### 如何理解“KISS 原则”？

KISS 原则的英文描述有好几个版本，比如下面这几个。

- Keep It Simple and Stupid.
- Keep It Short and Simple.
- Keep It Simple and Straightforward.

不过，仔细看你就会发现，它们要表达的意思其实差不多，翻译成中文就是：尽量保持简单。

### 代码行数越少就越“简单”吗？

我们先一起看一个例子。下面这三段代码可以实现同样一个功能：检查输入的字符串 ipAddress 是否是合法的 IP 地址。一个合法的 IP 地址由四个数字组成，并且通过“.”来进行分割。每组数字的取值范围是 0~255。第一组数字比较特殊，不允许为 0。对比这三段代码，你觉得哪一段代码最符合 KISS 原则呢？如果让你来实现这个功能，你会选择用哪种实现方法呢？你可以先自己思考一下，然后再看我下面的讲解。

```java
// 第一种实现方式: 使用正则表达式
// 优化：https://github.com/gdhucoder/Algorithms4/blob/master/designpattern/u20/TestPerformance.java
public boolean isValidIpAddressV1(String ipAddress) {
  if (StringUtils.isBlank(ipAddress)) return false;
  String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
  return ipAddress.matches(regex);
}

// 第二种实现方式: 使用现成的工具类
public boolean isValidIpAddressV2(String ipAddress) {
  if (StringUtils.isBlank(ipAddress)) return false;
  String[] ipUnits = StringUtils.split(ipAddress, '.');
  if (ipUnits.length != 4) {
    return false;
  }
  for (int i = 0; i < 4; ++i) {
    int ipUnitIntValue;
    try {
      ipUnitIntValue = Integer.parseInt(ipUnits[i]);
    } catch (NumberFormatException e) {
      return false;
    }
    if (ipUnitIntValue < 0 || ipUnitIntValue > 255) {
      return false;
    }
    if (i == 0 && ipUnitIntValue == 0) {
      return false;
    }
  }
  return true;
}

// 第三种实现方式: 不使用任何工具类
public boolean isValidIpAddressV3(String ipAddress) {
  char[] ipChars = ipAddress.toCharArray();
  int length = ipChars.length;
  int ipUnitIntValue = -1;
  boolean isFirstUnit = true;
  int unitsCount = 0;
  for (int i = 0; i < length; ++i) {
    char c = ipChars[i];
    if (c == '.') {
      if (ipUnitIntValue < 0 || ipUnitIntValue > 255) return false;
      if (isFirstUnit && ipUnitIntValue == 0) return false;
      if (isFirstUnit) isFirstUnit = false;
      ipUnitIntValue = -1;
      unitsCount++;
      continue;
    }
    if (c < '0' || c > '9') {
      return false;
    }
    if (ipUnitIntValue == -1) ipUnitIntValue = 0;
    ipUnitIntValue = ipUnitIntValue * 10 + (c - '0');
  }
  if (ipUnitIntValue < 0 || ipUnitIntValue > 255) return false;
  if (unitsCount != 3) return false;
  return true;
}
```

第一种实现方式利用的是正则表达式，只用三行代码就把这个问题搞定了。它的代码行数最少，那是不是就最符合 KISS 原则呢？答案是否定的。虽然代码行数最少，看似最简单，实际上却很复杂。这正是因为它使用了正则表达式。

一方面，正则表达式本身是比较复杂的，写出完全没有 bug 的正则表达本身就比较有挑战；另一方面，并不是每个程序员都精通正则表达式。对于不怎么懂正则表达式的同事来说，看懂并且维护这段正则表达式是比较困难的。这种实现方式会导致代码的可读性和可维护性变差，所以，从 KISS 原则的设计初衷上来讲，这种实现方式并不符合 KISS 原则。

讲完了第一种实现方式，我们再来看下其他两种实现方式。

第二种实现方式使用了 StringUtils 类、Integer 类提供的一些现成的工具函数，来处理 IP 地址字符串。第三种实现方式，不使用任何工具函数，而是通过逐一处理 IP 地址中的字符，来判断是否合法。从代码行数上来说，这两种方式差不多。但是，第三种要比第二种更加有难度，更容易写出 bug。从可读性上来说，第二种实现方式的代码逻辑更清晰、更好理解。所以，在这两种实现方式中，第二种实现方式更加“简单”，更加符合 KISS 原则。

不过，你可能会说，第三种实现方式虽然实现起来稍微有点复杂，但性能要比第二种实现方式高一些啊。从性能的角度来说，选择第三种实现方式是不是更好些呢？在回答这个问题之前，我先解释一下，为什么说第三种实现方式性能会更高一些。一般来说，工具类的功能都比较通用和全面，所以，在代码实现上，需要考虑和处理更多的细节，执行效率就会有所影响。而第三种实现方式，完全是自己操作底层字符，只针对 IP 地址这一种格式的数据输入来做处理，没有太多多余的函数调用和其他不必要的处理逻辑，所以，在执行效率上，这种类似定制化的处理代码方式肯定比通用的工具类要高些。不过，尽管第三种实现方式性能更高些，但我还是更倾向于选择第二种实现方法。那是因为第三种实现方式实际上是一种过度优化。除非 isValidIpAddress() 函数是影响系统性能的瓶颈代码，否则，这样优化的投入产出比并不高，增加了代码实现的难度、牺牲了代码的可读性，性能上的提升却并不明显。

### 代码逻辑复杂就违背 KISS 原则吗？

比如KMP算法。不过，平时的项目开发中涉及的字符串匹配问题，大部分都是针对比较小的文本。在这种情况下，直接调用编程语言提供的现成的字符串匹配函数就足够了。如果非得用 KMP 算法、BM 算法来实现字符串匹配，那就真的违背 KISS 原则了。也就是说，同样的代码，在某个业务场景下满足 KISS 原则，换一个应用场景可能就不满足了。

### 如何写出满足 KISS 原则的代码？

实际上，我们前面已经讲到了一些方法。这里我稍微总结一下。

- 不要使用同事可能不懂的技术来实现代码。比如前面例子中的正则表达式，还有一些编程语言中过于高级的语法等。

- 不要重复造轮子，要善于使用已经有的工具类库。经验证明，自己去实现这些类库，出 bug 的概率会更高，维护的成本也比较高。
- 不要过度优化。不要过度使用一些奇技淫巧（比如，位运算代替算术运算、复杂的条件语句代替 if-else、使用一些过于底层的函数等）来优化代码，牺牲代码的可读性。

实际上，代码是否足够简单是一个挺主观的评判。同样的代码，有的人觉得简单，有的人觉得不够简单。而往往自己编写的代码，自己都会觉得够简单。所以，评判代码是否简单，还有一个很有效的间接方法，那就是 code review。如果在 code review 的时候，同事对你的代码有很多疑问，那就说明你的代码有可能不够“简单”，需要优化啦。

### YAGNI 跟 KISS 说的是一回事吗？

YAGNI 原则的英文全称是：You Ain’t Gonna Need It。直译就是：你不会需要它。这条原则也算是万金油了。当用在软件开发中的时候，它的意思是：不要去设计当前用不到的功能；不要去编写当前用不到的代码。实际上，这条原则的核心思想就是：不要做过度设计。

比如，我们的系统暂时只用 Redis 存储配置信息，以后可能会用到 ZooKeeper。根据 YAGNI 原则，在未用到 ZooKeeper 之前，我们没必要提前编写这部分代码。当然，这并不是说我们就不需要考虑代码的扩展性。我们还是要预留好扩展点，等到需要的时候，再去实现 ZooKeeper 存储配置信息这部分代码。

再比如，我们不要在项目中提前引入不需要依赖的开发包。对于 Java 程序员来说，我们经常使用 Maven 或者 Gradle 来管理依赖的类库（library）。我发现，有些同事为了避免开发中 library 包缺失而频繁地修改 Maven 或者 Gradle 配置文件，提前往项目里引入大量常用的 library 包。实际上，这样的做法也是违背 YAGNI 原则的。

从刚刚的分析我们可以看出，YAGNI 原则跟 KISS 原则并非一回事儿。KISS 原则讲的是“如何做”的问题（尽量保持简单），而 YAGNI 原则说的是“要不要做”的问题（当前不需要的就不要做）。

### 总结

KISS 原则是保持代码可读和可维护的重要手段。KISS 原则中的“简单”并不是以代码行数来考量的。代码行数越少并不代表代码越简单，我们还要考虑逻辑复杂度、实现难度、代码的可读性等。而且，本身就复杂的问题，用复杂的方法解决，并不违背 KISS 原则。除此之外，同样的代码，在某个业务场景下满足 KISS 原则，换一个应用场景可能就不满足了。

## 21 | 理论七：重复的代码就一定违背DRY吗？如何提高代码的复用性？

在上一节课中，我们讲了 KISS 原则和 YAGNI 原则，KISS 原则可以说是人尽皆知。今天，我们再学习一个你肯定听过的原则，那就是 DRY 原则。它的英文描述为：Don’t Repeat Yourself。中文直译为：不要重复自己。将它应用在编程中，可以理解为：不要写重复的代码。

你可能会觉得，这条原则非常简单、非常容易应用。只要两段代码长得一样，那就是违反 DRY 原则了。真的是这样吗？答案是否定的。这是很多人对这条原则存在的误解。实际上，重复的代码不一定违反 DRY 原则，而且有些看似不重复的代码也有可能违反 DRY 原则。

DRY 原则的定义非常简单，我就不再过度解读。今天，我们主要讲三种典型的代码重复情况，它们分别是：实现逻辑重复、功能语义重复和代码执行重复。这三种代码重复，有的看似违反 DRY，实际上并不违反；有的看似不违反，实际上却违反了。

### 实现逻辑重复

我们先来看下面这样一段代码是否违反了 DRY 原则。如果违反了，你觉得应该如何重构，才能让它满足 DRY 原则？如果没有违反，那又是为什么呢？

```java
public class UserAuthenticator {
  public void authenticate(String username, String password) {
    if (!isValidUsername(username)) {
      // ...throw InvalidUsernameException...
    }
    if (!isValidPassword(password)) {
      // ...throw InvalidPasswordException...
    }
    //...省略其他代码...
  }

  private boolean isValidUsername(String username) {
    // check not null, not empty
    if (StringUtils.isBlank(username)) {
      return false;
    }
    // check length: 4~64
    int length = username.length();
    if (length < 4 || length > 64) {
      return false;
    }
    // contains only lowcase characters
    if (!StringUtils.isAllLowerCase(username)) {
      return false;
    }
    // contains only a~z,0~9,dot
    for (int i = 0; i < length; ++i) {
      char c = username.charAt(i);
      if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
        return false;
      }
    }
    return true;
  }

  private boolean isValidPassword(String password) {
    // check not null, not empty
    if (StringUtils.isBlank(password)) {
      return false;
    }
    // check length: 4~64
    int length = password.length();
    if (length < 4 || length > 64) {
      return false;
    }
    // contains only lowcase characters
    if (!StringUtils.isAllLowerCase(password)) {
      return false;
    }
    // contains only a~z,0~9,dot
    for (int i = 0; i < length; ++i) {
      char c = password.charAt(i);
      if (!(c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '.') {
        return false;
      }
    }
    return true;
  }
}
```

代码很简单，我就不做过多解释了。在代码中，有两处非常明显的重复的代码片段：isValidUserName() 函数和 isValidPassword() 函数。重复的代码被敲了两遍，或者简单 copy-paste 了一下，看起来明显违反 DRY 原则。为了移除重复的代码，我们对上面的代码做下重构，将 isValidUserName() 函数和 isValidPassword() 函数，合并为一个更通用的函数 isValidUserNameOrPassword()。重构后的代码如下所示：

```java
public class UserAuthenticatorV2 {

  public void authenticate(String userName, String password) {
    if (!isValidUsernameOrPassword(userName)) {
      // ...throw InvalidUsernameException...
    }

    if (!isValidUsernameOrPassword(password)) {
      // ...throw InvalidPasswordException...
    }
  }

  private boolean isValidUsernameOrPassword(String usernameOrPassword) {
    //省略实现逻辑
    //跟原来的isValidUsername()或isValidPassword()的实现逻辑一样...
    return true;
  }
}
```

经过重构之后，代码行数减少了，也没有重复的代码了，是不是更好了呢？答案是否定的，这可能跟你预期的不一样，我来解释一下为什么。单从名字上看，我们就能发现，合并之后的 isValidUserNameOrPassword() 函数，负责两件事情：验证用户名和验证密码，违反了“单一职责原则”和“接口隔离原则”。实际上，即便将两个函数合并成 isValidUserNameOrPassword()，代码仍然存在问题。

因为 isValidUserName() 和 isValidPassword() 两个函数，虽然从代码实现逻辑上看起来是重复的，但是从语义上并不重复。所谓“语义不重复”指的是：从功能上来看，这两个函数干的是完全不重复的两件事情，一个是校验用户名，另一个是校验密码。尽管在目前的设计中，两个校验逻辑是完全一样的，但如果按照第二种写法，将两个函数的合并，那就会存在潜在的问题。在未来的某一天，如果我们修改了密码的校验逻辑，比如，允许密码包含大写字符，允许密码的长度为 8 到 64 个字符，那这个时候，isValidUserName() 和 isValidPassword() 的实现逻辑就会不相同。我们就要把合并后的函数，重新拆成合并前的那两个函数。尽管代码的实现逻辑是相同的，但语义不同，我们判定它并不违反 DRY 原则。对于包含重复代码的问题，我们可以通过抽象成更细粒度函数的方式来解决。比如将校验只包含 a~z、0~9、dot 的逻辑封装成 boolean onlyContains(String str, String charlist); 函数。

### 功能语义重复

现在我们再来看另外一个例子。在同一个项目代码中有下面两个函数：isValidIp() 和 checkIfIpValid()。尽管两个函数的命名不同，实现逻辑不同，但功能是相同的，都是用来判定 IP 地址是否合法的。

之所以在同一个项目中会有两个功能相同的函数，那是因为这两个函数是由两个不同的同事开发的，其中一个同事在不知道已经有了 isValidIp() 的情况下，自己又定义并实现了同样用来校验 IP 地址是否合法的 checkIfIpValid() 函数。

那在同一项目代码中，存在如下两个函数，是否违反 DRY 原则呢？

```java
public boolean isValidIp(String ipAddress) {
  if (StringUtils.isBlank(ipAddress)) return false;
  String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
          + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
  return ipAddress.matches(regex);
}

public boolean checkIfIpValid(String ipAddress) {
  if (StringUtils.isBlank(ipAddress)) return false;
  String[] ipUnits = StringUtils.split(ipAddress, '.');
  if (ipUnits.length != 4) {
    return false;
  }
  for (int i = 0; i < 4; ++i) {
    int ipUnitIntValue;
    try {
      ipUnitIntValue = Integer.parseInt(ipUnits[i]);
    } catch (NumberFormatException e) {
      return false;
    }
    if (ipUnitIntValue < 0 || ipUnitIntValue > 255) {
      return false;
    }
    if (i == 0 && ipUnitIntValue == 0) {
      return false;
    }
  }
  return true;
}
```

这个例子跟上个例子正好相反。上一个例子是代码实现逻辑重复，但语义不重复，我们并不认为它违反了 DRY 原则。而在这个例子中，尽管两段代码的实现逻辑不重复，但语义重复，也就是功能重复，我们认为它违反了 DRY 原则。我们应该在项目中，统一一种实现思路，所有用到判断 IP 地址是否合法的地方，都统一调用同一个函数。

假设我们不统一实现思路，那有些地方调用了 isValidIp() 函数，有些地方又调用了 checkIfIpValid() 函数，这就会导致代码看起来很奇怪，相当于给代码“埋坑”，给不熟悉这部分代码的同事增加了阅读的难度。同事有可能研究了半天，觉得功能是一样的，但又有点疑惑，觉得是不是有更高深的考量，才定义了两个功能类似的函数，最终发现居然是代码设计的问题。

除此之外，如果哪天项目中 IP 地址是否合法的判定规则改变了，比如：255.255.255.255 不再被判定为合法的了，相应地，我们对 isValidIp() 的实现逻辑做了相应的修改，但却忘记了修改 checkIfIpValid() 函数。又或者，我们压根就不知道还存在一个功能相同的 checkIfIpValid() 函数，这样就会导致有些代码仍然使用老的 IP 地址判断逻辑，导致出现一些莫名其妙的 bug。

### 代码执行重复

前两个例子一个是实现逻辑重复，一个是语义重复，我们再来看第三个例子。其中，UserService 中 login() 函数用来校验用户登录是否成功。如果失败，就返回异常；如果成功，就返回用户信息。具体代码如下所示：

```java
public class UserService {
  private UserRepo userRepo;//通过依赖注入或者IOC框架注入

  public User login(String email, String password) {
    boolean existed = userRepo.checkIfUserExisted(email, password);
    if (!existed) {
      // ... throw AuthenticationFailureException...
    }
    User user = userRepo.getUserByEmail(email);
    return user;
  }
}

public class UserRepo {
  public boolean checkIfUserExisted(String email, String password) {
    if (!EmailValidation.validate(email)) {
      // ... throw InvalidEmailException...
    }

    if (!PasswordValidation.validate(password)) {
      // ... throw InvalidPasswordException...
    }

    //...query db to check if email&password exists...
  }

  public User getUserByEmail(String email) {
    if (!EmailValidation.validate(email)) {
      // ... throw InvalidEmailException...
    }
    //...query db to get user by email...
  }
}
```

上面这段代码，既没有逻辑重复，也没有语义重复，但仍然违反了 DRY 原则。这是因为代码中存在“执行重复”。我们一块儿来看下，到底哪些代码被重复执行了？重复执行最明显的一个地方，就是在 login() 函数中，email 的校验逻辑被执行了两次。一次是在调用 checkIfUserExisted() 函数的时候，另一次是调用 getUserByEmail() 函数的时候。这个问题解决起来比较简单，我们只需要将校验逻辑从 UserRepo 中移除，统一放到 UserService 中就可以了。

除此之外，代码中还有一处比较隐蔽的执行重复，不知道你发现了没有？实际上，login() 函数并不需要调用 checkIfUserExisted() 函数，只需要调用一次 getUserByEmail() 函数，从数据库中获取到用户的 email、password 等信息，然后跟用户输入的 email、password 信息做对比，依次判断是否登录成功。

实际上，这样的优化是很有必要的。因为 checkIfUserExisted() 函数和 getUserByEmail() 函数都需要查询数据库，而数据库这类的 I/O 操作是比较耗时的。我们在写代码的时候，应当尽量减少这类 I/O 操作。

按照刚刚的修改思路，我们把代码重构一下，移除“重复执行”的代码，只校验一次 email 和 password，并且只查询一次数据库。重构之后的代码如下所示：

```java
public class UserService {
  private UserRepo userRepo;//通过依赖注入或者IOC框架注入

  public User login(String email, String password) {
    if (!EmailValidation.validate(email)) {
      // ... throw InvalidEmailException...
    }
    if (!PasswordValidation.validate(password)) {
      // ... throw InvalidPasswordException...
    }
    User user = userRepo.getUserByEmail(email);
    if (user == null || !password.equals(user.getPassword()) {
      // ... throw AuthenticationFailureException...
    }
    return user;
  }
}

public class UserRepo {
  public boolean checkIfUserExisted(String email, String password) {
    //...query db to check if email&password exists
  }

  public User getUserByEmail(String email) {
    //...query db to get user by email...
  }
}
```

## 22 | 理论八：如何用迪米特法则（LOD）实现“高内聚、松耦合”？

### 何为“高内聚、松耦合”？

“高内聚、松耦合”是一个非常重要的设计思想，能够有效地提高代码的可读性和可维护性，缩小功能改动导致的代码改动范围。实际上，在前面的章节中，我们已经多次提到过这个设计思想。很多设计原则都以实现代码的“高内聚、松耦合”为目的，比如单一职责原则、基于接口而非实现编程等。

“高内聚、松耦合”是一个非常重要的设计思想，能够有效地提高代码的可读性和可维护性，缩小功能改动导致的代码改动范围。实际上，在前面的章节中，我们已经多次提到过这个设计思想。很多设计原则都以实现代码的“高内聚、松耦合”为目的，比如单一职责原则、基于接口而非实现编程等。

在这个设计思想中，“高内聚”用来指导类本身的设计，“松耦合”用来指导类与类之间依赖关系的设计。不过，这两者并非完全独立不相干。高内聚有助于松耦合，松耦合又需要高内聚的支持。

所谓高内聚，就是指相近的功能应该放到同一个类中，不相近的功能不要放到同一个类中。相近的功能往往会被同时修改，放到同一个类中，修改会比较集中，代码容易维护。实际上，我们前面讲过的单一职责原则是实现代码高内聚非常有效的设计原则。

所谓松耦合是说，在代码中，类与类之间的依赖关系简单清晰。即使两个类有依赖关系，一个类的代码改动不会或者很少导致依赖类的代码改动。实际上，我们前面讲的依赖注入、接口隔离、基于接口而非实现编程，以及今天讲的迪米特法则，都是为了实现代码的松耦合。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210405185659.jpg)

图中左边部分的代码设计中，类的粒度比较小，每个类的职责都比较单一。相近的功能都放到了一个类中，不相近的功能被分割到了多个类中。这样类更加独立，代码的内聚性更好。因为职责单一，所以每个类被依赖的类就会比较少，代码低耦合。一个类的修改，只会影响到一个依赖类的代码改动。我们只需要测试这一个依赖类是否还能正常工作就行了。图中右边部分的代码设计中，类粒度比较大，低内聚，功能大而全，不相近的功能放到了一个类中。这就导致很多其他类都依赖这个类。当我们修改这个类的某一个功能代码的时候，会影响依赖它的多个类。我们需要测试这三个依赖类，是否还能正常工作。这也就是所谓的“牵一发而动全身”。

### “迪米特法则”理论描述

关于这个设计原则，我们先来看一下它最原汁原味的英文定义：

> Each unit should have only limited knowledge about other units: only units “closely” related to the current unit. Or: Each unit should only talk to its friends; Don’t talk to strangers.

我们把它直译成中文，就是下面这个样子：

> 每个模块（unit）只应该了解那些与它关系密切的模块（units: only units “closely” related to the current unit）的有限知识（knowledge）。或者说，每个模块只和自己的朋友“说话”（talk），不和陌生人“说话”（talk）。

### 理论解读与代码实战一

我们先来看这条原则中的前半部分，“不该有直接依赖关系的类之间，不要有依赖”。我举个例子解释一下。

这个例子实现了简化版的搜索引擎爬取网页的功能。代码中包含三个主要的类。其中，NetworkTransporter 类负责底层网络通信，根据请求获取数据；HtmlDownloader 类用来通过 URL 获取网页；Document 表示网页文档，后续的网页内容抽取、分词、索引都是以此为处理对象。具体的代码实现如下所示：

```java
public class NetworkTransporter {
    // 省略属性和其他方法...
    public Byte[] send(HtmlRequest htmlRequest) {
      //...
    }
}

public class HtmlDownloader {
  private NetworkTransporter transporter;//通过构造函数或IOC注入
  
  public Html downloadHtml(String url) {
    Byte[] rawHtml = transporter.send(new HtmlRequest(url));
    return new Html(rawHtml);
  }
}

public class Document {
  private Html html;
  private String url;
  
  public Document(String url) {
    this.url = url;
    HtmlDownloader downloader = new HtmlDownloader();
    this.html = downloader.downloadHtml(url);
  }
  //...
}
```

这段代码虽然“能用”，能实现我们想要的功能，但是它不够“好用”，有比较多的设计缺陷。你可以先试着思考一下，看看都有哪些缺陷，然后再来看我下面的讲解。

首先，我们来看 NetworkTransporter 类。作为一个底层网络通信类，我们希望它的功能尽可能通用，而不只是服务于下载 HTML，所以，我们不应该直接依赖太具体的发送对象 HtmlRequest。从这一点上讲，NetworkTransporter 类的设计违背迪米特法则，依赖了不该有直接依赖关系的 HtmlRequest 类。

我们应该如何进行重构，让 NetworkTransporter 类满足迪米特法则呢？我这里有个形象的比喻。假如你现在要去商店买东西，你肯定不会直接把钱包给收银员，让收银员自己从里面拿钱，而是你从钱包里把钱拿出来交给收银员。这里的 HtmlRequest 对象就相当于钱包，HtmlRequest 里的 address 和 content 对象就相当于钱。我们应该把 address 和 content 交给 NetworkTransporter，而非是直接把 HtmlRequest 交给 NetworkTransporter。根据这个思路，NetworkTransporter 重构之后的代码如下所示：

```java
public class NetworkTransporter {
    // 省略属性和其他方法...
    public Byte[] send(String address, Byte[] data) {
      //...
    }
}
```

我们再来看 HtmlDownloader 类。这个类的设计没有问题。不过，我们修改了 NetworkTransporter 的 send() 函数的定义，而这个类用到了 send() 函数，所以我们需要对它做相应的修改，修改后的代码如下所示：

```
public class HtmlDownloader {
  private NetworkTransporter transporter;//通过构造函数或IOC注入
  
  // HtmlDownloader这里也要有相应的修改
  public Html downloadHtml(String url) {
    HtmlRequest htmlRequest = new HtmlRequest(url);
    Byte[] rawHtml = transporter.send(
      htmlRequest.getAddress(), htmlRequest.getContent().getBytes());
    return new Html(rawHtml);
  }
}
```

最后，我们来看下 Document 类。这个类的问题比较多，主要有三点。第一，构造函数中的 downloader.downloadHtml() 逻辑复杂，耗时长，不应该放到构造函数中，会影响代码的可测试性。代码的可测试性我们后面会讲到，这里你先知道有这回事就可以了。第二，HtmlDownloader 对象在构造函数中通过 new 来创建，违反了基于接口而非实现编程的设计思想，也会影响到代码的可测试性。第三，从业务含义上来讲，Document 网页文档没必要依赖 HtmlDownloader 类，违背了迪米特法则。

虽然 Document 类的问题很多，但修改起来比较简单，只要一处改动就可以解决所有问题。修改之后的代码如下所示：

```java
public class Document {
  private Html html;
  private String url;
  
  public Document(String url, Html html) {
    this.html = html;
    this.url = url;
  }
  //...
}

// 通过一个工厂方法来创建Document
public class DocumentFactory {
  private HtmlDownloader downloader;
  
  public DocumentFactory(HtmlDownloader downloader) {
    this.downloader = downloader;
  }
  
  public Document createDocument(String url) {
    Html html = downloader.downloadHtml(url);
    return new Document(url, html);
  }
}
```

### 理论解读与代码实战二

现在，我们再来看一下这条原则中的后半部分：“有依赖关系的类之间，尽量只依赖必要的接口”。我们还是结合一个例子来讲解。下面这段代码非常简单，Serialization 类负责对象的序列化和反序列化。提醒你一下，有个类似的例子在之前的第 15 节课中讲过，你可以结合着一块儿看一下。

```java
public class Serialization {
  public String serialize(Object object) {
    String serializedResult = ...;
    //...
    return serializedResult;
  }
  
  public Object deserialize(String str) {
    Object deserializedResult = ...;
    //...
    return deserializedResult;
  }
}
```

单看这个类的设计，没有一点问题。不过，如果我们把它放到一定的应用场景里，那就还有继续优化的空间。假设在我们的项目中，有些类只用到了序列化操作，而另一些类只用到反序列化操作。那基于迪米特法则后半部分“有依赖关系的类之间，尽量只依赖必要的接口”，只用到序列化操作的那部分类不应该依赖反序列化接口。同理，只用到反序列化操作的那部分类不应该依赖序列化接口。

根据这个思路，我们应该将 Serialization 类拆分为两个更小粒度的类，一个只负责序列化（Serializer 类），一个只负责反序列化（Deserializer 类）。拆分之后，使用序列化操作的类只需要依赖 Serializer 类，使用反序列化操作的类只需要依赖 Deserializer 类。拆分之后的代码如下所示：

```java
public class Serializer {
  public String serialize(Object object) {
    String serializedResult = ...;
    ...
    return serializedResult;
  }
}

public class Deserializer {
  public Object deserialize(String str) {
    Object deserializedResult = ...;
    ...
    return deserializedResult;
  }
}
```

不知道你有没有看出来，尽管拆分之后的代码更能满足迪米特法则，但却违背了高内聚的设计思想。高内聚要求相近的功能要放到同一个类中，这样可以方便功能修改的时候，修改的地方不至于过于分散。对于刚刚这个例子来说，如果我们修改了序列化的实现方式，比如从 JSON 换成了 XML，那反序列化的实现逻辑也需要一并修改。在未拆分的情况下，我们只需要修改一个类即可。在拆分之后，我们需要修改两个类。显然，这种设计思路的代码改动范围变大了。

如果我们既不想违背高内聚的设计思想，也不想违背迪米特法则，那我们该如何解决这个问题呢？实际上，通过引入两个接口就能轻松解决这个问题，具体的代码如下所示。

```java
public interface Serializable {
  String serialize(Object object);
}

public interface Deserializable {
  Object deserialize(String text);
}

public class Serialization implements Serializable, Deserializable {
  @Override
  public String serialize(Object object) {
    String serializedResult = ...;
    ...
    return serializedResult;
  }
  
  @Override
  public Object deserialize(String str) {
    Object deserializedResult = ...;
    ...
    return deserializedResult;
  }
}

public class DemoClass_1 {
  private Serializable serializer;
  
  public Demo(Serializable serializer) {
    this.serializer = serializer;
  }
  //...
}

public class DemoClass_2 {
  private Deserializable deserializer;
  
  public Demo(Deserializable deserializer) {
    this.deserializer = deserializer;
  }
  //...
}
```

尽管我们还是要往 DemoClass_1 的构造函数中，传入包含序列化和反序列化的 Serialization 实现类，但是，我们依赖的 Serializable 接口只包含序列化操作，DemoClass_1 无法使用 Serialization 类中的反序列化接口，对反序列化操作无感知，这也就符合了迪米特法则后半部分所说的“依赖有限接口”的要求。

### 辩证思考与灵活应用

对于实战二最终的设计思路，你有没有什么不同的观点呢？

整个类只包含序列化和反序列化两个操作，只用到序列化操作的使用者，即便能够感知到仅有的一个反序列化函数，问题也不大。那为了满足迪米特法则，我们将一个非常简单的类，拆分出两个接口，是否有点过度设计的意思呢？

设计原则本身没有对错，只有能否用对之说。不要为了应用设计原则而应用设计原则，我们在应用设计原则的时候，一定要具体问题具体分析。

对于刚刚这个 Serialization 类来说，只包含两个操作，确实没有太大必要拆分成两个接口。但是，如果我们对 Serialization 类添加更多的功能，实现更多更好用的序列化、反序列化函数，我们来重新考虑一下这个问题。修改之后的具体的代码如下：

```
public class Serializer { // 参看JSON的接口定义
  public String serialize(Object object) { //... }
  public String serializeMap(Map map) { //... }
  public String serializeList(List list) { //... }
  
  public Object deserialize(String objectString) { //... }
  public Map deserializeMap(String mapString) { //... }
  public List deserializeList(String listString) { //... }
}
```

在这种场景下，第二种设计思路要更好些。因为基于之前的应用场景来说，大部分代码只需要用到序列化的功能。对于这部分使用者，没必要了解反序列化的“知识”，而修改之后的 Serialization 类，反序列化的“知识”，从一个函数变成了三个。一旦任一反序列化操作有代码改动，我们都需要检查、测试所有依赖 Serialization 类的代码是否还能正常工作。为了减少耦合和测试工作量，我们应该按照迪米特法则，将反序列化和序列化的功能隔离开来。

## 23 | 实战一（上）：针对业务系统的开发，如何做需求分析和设计？

### 需求分析

积分是一种常见的营销手段，很多产品都会通过它来促进消费、增加用户粘性，比如淘宝积分、信用卡积分、商场消费积分等等。假设你是一家类似淘宝这样的电商平台的工程师，平台暂时还没有积分系统。Leader 希望由你来负责开发这样一个系统，你会如何来做呢？

你可能会说，只要产品经理给我产品设计文档（PRD）、线框图，我照着实现就可以了。我觉得，这种想法有点狭隘。我认为，技术人员应该更多地参与到产品设计中。在 Google 工作的时候，我很明显能感受到，Google 工程师跟其他公司工程师有一个很大区别，那就是大部分人都具备产品思维，并不是完全的“技术控”。所以，Google 很多产品的初期设计都是工程师来完成的，在产品发展壮大到一定程度的时候，才会引入产品经理的角色。

笼统地来讲，积分系统无外乎就两个大的功能点，一个是赚取积分，另一个是消费积分。赚取积分功能包括积分赚取渠道，比如下订单、每日签到、评论等；还包括积分兑换规则，比如订单金额与积分的兑换比例，每日签到赠送多少积分等。消费积分功能包括积分消费渠道，比如抵扣订单金额、兑换优惠券、积分换购、参与活动扣积分等；还包括积分兑换规则，比如多少积分可以换算成抵扣订单的多少金额，一张优惠券需要多少积分来兑换等等。

通过上面讲的方法，我们就可以将功能需求大致弄清楚了。积分系统的需求实际上并不复杂，我总结罗列了一下，如下所示。

1. 积分赚取和兑换规则积分的赚取渠道包括：下订单、每日签到、评论等。积分兑换规则可以是比较通用的。比如，签到送 10 积分。再比如，按照订单总金额的 10% 兑换成积分，也就是 100 块钱的订单可以积累 10 积分。除此之外，积分兑换规则也可以是比较细化的。比如，不同的店铺、不同的商品，可以设置不同的积分兑换比例。对于积分的有效期，我们可以根据不同渠道，设置不同的有效期。积分到期之后会作废；在消费积分的时候，优先使用快到期的积分。
2. 积分消费和兑换规则积分的消费渠道包括：抵扣订单金额、兑换优惠券、积分换购、参与活动扣积分等。我们可以根据不同的消费渠道，设置不同的积分兑换规则。比如，积分换算成消费抵扣金额的比例是 10%，也就是 10 积分可以抵扣 1 块钱；100 积分可以兑换 15 块钱的优惠券等。
3. 积分及其明细查询查询用户的总积分，以及赚取积分和消费积分的历史记录。

### 系统设计

面向对象设计聚焦在代码层面（主要是针对类），那系统设计就是聚焦在架构层面（主要是针对模块），两者有很多相似之处。很多设计原则和思想不仅仅可以应用到代码设计中，还能用到架构设计中。还记得面向对象设计的四个步骤吗？实际上，我们也可以借鉴那个过程来做系统设计。

1. 合理地将功能划分到不同模块

前面讲到面向对象设计的时候，我们提到，面向对象设计的本质就是把合适的代码放到合适的类中。合理地划分代码可以实现代码的高内聚、低耦合，类与类之间的交互简单清晰，代码整体结构一目了然，那代码的质量就不会差到哪里去。类比面向对象设计，系统设计实际上就是将合适的功能放到合适的模块中。合理地划分模块也可以做到模块层面的高内聚、低耦合，架构整洁清晰。

对于前面罗列的所有功能点，我们有下面三种模块划分方法。第一种划分方式是：积分赚取渠道及兑换规则、消费渠道及兑换规则的管理和维护（增删改查），不划分到积分系统中，而是放到更上层的营销系统中。这样积分系统就会变得非常简单，只需要负责增加积分、减少积分、查询积分、查询积分明细等这几个工作。

我举个例子解释一下。比如，用户通过下订单赚取积分。订单系统通过异步发送消息或者同步调用接口的方式，告知营销系统订单交易成功。营销系统根据拿到的订单信息，查询订单对应的积分兑换规则（兑换比例、有效期等），计算得到订单可兑换的积分数量，然后调用积分系统的接口给用户增加积分。

第二种划分方式是：积分赚取渠道及兑换规则、消费渠道及兑换规则的管理和维护，分散在各个相关业务系统中，比如订单系统、评论系统、签到系统、换购商城、优惠券系统等。还是刚刚那个下订单赚取积分的例子，在这种情况下，用户下订单成功之后，订单系统根据商品对应的积分兑换比例，计算所能兑换的积分数量，然后直接调用积分系统给用户增加积分。

第三种划分方式是：所有的功能都划分到积分系统中，包括积分赚取渠道及兑换规则、消费渠道及兑换规则的管理和维护。还是同样的例子，用户下订单成功之后，订单系统直接告知积分系统订单交易成功，积分系统根据订单信息查询积分兑换规则，给用户增加积分。

怎么判断哪种模块划分合理呢？实际上，我们可以反过来通过看它是否符合高内聚、低耦合特性来判断。如果一个功能的修改或添加，经常要跨团队、跨项目、跨系统才能完成，那说明模块划分的不够合理，职责不够清晰，耦合过于严重。

除此之外，为了避免业务知识的耦合，让下层系统更加通用，一般来讲，我们不希望下层系统（也就是被调用的系统）包含太多上层系统（也就是调用系统）的业务信息，但是，可以接受上层系统包含下层系统的业务信息。比如，订单系统、优惠券系统、换购商城等作为调用积分系统的上层系统，可以包含一些积分相关的业务信息。但是，反过来，积分系统中最好不要包含太多跟订单、优惠券、换购等相关的信息。

所以，综合考虑，我们更倾向于第一种和第二种模块划分方式。但是，不管选择这两种中的哪一种，积分系统所负责的工作是一样的，只包含积分的增、减、查询，以及积分明细的记录和查询。

2. 设计模块与模块之间的交互关系

在面向对象设计中，类设计好之后，我们需要设计类之间的交互关系。类比到系统设计，系统职责划分好之后，接下来就是设计系统之间的交互，也就是确定有哪些系统跟积分系统之间有交互以及如何进行交互。

比较常见的系统之间的交互方式有两种，一种是同步接口调用，另一种是利用消息中间件异步调用。第一种方式简单直接，第二种方式的解耦效果更好。

比如，用户下订单成功之后，订单系统推送一条消息到消息中间件，营销系统订阅订单成功消息，触发执行相应的积分兑换逻辑。这样订单系统就跟营销系统完全解耦，订单系统不需要知道任何跟积分相关的逻辑，而营销系统也不需要直接跟订单系统交互。

除此之外，上下层系统之间的调用倾向于通过同步接口，同层之间的调用倾向于异步消息调用。比如，营销系统和积分系统是上下层关系，它们之间就比较推荐使用同步接口调用。

3. 设计模块的接口、数据库、业务模型

刚刚讲了模块的功能划分，模块之间的交互的设计，现在，我们再来看，模块本身如何来设计。实际上，业务系统本身的设计无外乎有这样三方面的工作要做：接口设计、数据库设计和业务模型设计。这部分的具体内容我们放到下一下节课中跟实现一块进行讲解。

**面向对象设计的本质就是把合适的代码放到合适的类中。合理地划分代码可以实现代码的高内聚、低耦合，类与类之间的交互简单清晰，代码整体结构一目了然。类比面向对象设计，系统设计实际上就是将合适的功能放到合适的模块中。合理地划分模块也可以做到模块层面的高内聚、低耦合，架构整洁清晰。在面向对象设计中，类设计好之后，我们需要设计类之间的交互关系。类比到系统设计，系统职责划分好之后，接下来就是设计系统之间的交互了。**

## 24 | 实战一（下）：如何实现一个遵从设计原则的积分兑换系统？

为什么要分 MVC 三层来开发？为什么要针对每层定义不同的数据对象？最后，我还会总结这其中都蕴含哪些设计原则和思想，让你知其然知其所以然，做到真正地透彻理解。

### 业务开发包括哪些工作？

实际上，我们平时做业务系统的设计与开发，无外乎有这样三方面的工作要做：接口设计、数据库设计和业务模型设计（也就是业务逻辑）。

数据库和接口的设计非常重要，一旦设计好并投入使用之后，这两部分都不能轻易改动。改动数据库表结构，需要涉及数据的迁移和适配；改动接口，需要推动接口的使用者作相应的代码修改。这两种情况，即便是微小的改动，执行起来都会非常麻烦。因此，我们在设计接口和数据库的时候，一定要多花点心思和时间，切不可过于随意。相反，业务逻辑代码侧重内部实现，不涉及被外部依赖的接口，也不包含持久化的数据，所以对改动的容忍性更大。

针对积分系统，我们先来看，如何设计数据库。数据库的设计比较简单。实际上，我们只需要一张记录积分流水明细的表就可以了。表中记录积分的赚取和消费流水。用户积分的各种统计数据，比如总积分、总可用积分等，都可以通过这张表来计算得到。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210405220925.jpg)

接下来，我们再来看，如何设计积分系统的接口。

接口设计要符合单一职责原则，粒度越小通用性就越好。但是，接口粒度太小也会带来一些问题。比如，一个功能的实现要调用多个小接口，一方面如果接口调用走网络（特别是公网），多次远程接口调用会影响性能；另一方面，本该在一个接口中完成的原子操作，现在分拆成多个小接口来完成，就可能会涉及分布式事务的数据一致性问题（一个接口执行成功了，但另一个接口执行失败了）。所以，为了兼顾易用性和性能，我们可以借鉴 facade（外观）设计模式，在职责单一的细粒度接口之上，再封装一层粗粒度的接口给外部使用。

对于积分系统来说，我们需要设计如下这样几个接口。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210405221107.jpg)

最后，我们来看业务模型的设计。

前面我们讲到，从代码实现角度来说，大部分业务系统的开发都可以分为 Controller、Service、Repository 三层。Controller 层负责接口暴露，Repository 层负责数据读写，Service 层负责核心业务逻辑，也就是这里说的业务模型。

除此之外，前面我们还提到两种开发模式，基于贫血模型的传统开发模式和基于充血模型的 DDD 开发模式。前者是一种面向过程的编程风格，后者是一种面向对象的编程风格。不管是 DDD 还是 OOP，高级开发模式的存在一般都是为了应对复杂系统，应对系统的复杂性。对于我们要开发的积分系统来说，因为业务相对比较简单，所以，选择简单的基于贫血模型的传统开发模式就足够了。

从开发的角度来说，我们可以把积分系统作为一个独立的项目，来独立开发，也可以跟其他业务代码（比如营销系统）放到同一个项目中进行开发。从运维的角度来说，我们可以将它跟其他业务一块部署，也可以作为一个微服务独立部署。具体选择哪种开发和部署方式，我们可以参考公司当前的技术架构来决定。

实际上，积分系统业务比较简单，代码量也不多，我更倾向于将它跟营销系统放到一个项目中开发部署。只要我们做好代码的模块化和解耦，让积分相关的业务代码跟其他业务代码之间边界清晰，没有太多耦合，后期如果需要将它拆分成独立的项目来开发部署，那也并不困难。

### 为什么要分 MVC 三层开发？

我们刚刚提到，大部分业务系统的开发都可以分为三层：Contoller 层、Service 层、Repository 层。对于这种分层方式，我相信大部分人都很认同，甚至成为了一种开发习惯，但你有没有想过，为什么我们要分层开发？很多业务都比较简单，一层代码搞定所有的数据读取、业务逻辑、接口暴露不好吗？你可以把它作为一道面试题，试着自己思考下，然后再看我下面的讲解。

1. 分层能起到代码复用的作用

同一个 Repository 可能会被多个 Service 来调用，同一个 Service 可能会被多个 Controller 调用。比如，UserService 中的 getUserById() 接口封装了通过 ID 获取用户信息的逻辑，这部分逻辑可能会被 UserController 和 AdminController 等多个 Controller 使用。如果没有 Service 层，每个 Controller 都要重复实现这部分逻辑，显然会违反 DRY 原则。

2. 分层能起到隔离变化的作用

分层体现了一种抽象和封装的设计思想。比如，Repository 层封装了对数据库访问的操作，提供了抽象的数据访问接口。基于接口而非实现编程的设计思想，Service 层使用 Repository 层提供的接口，并不关心其底层依赖的是哪种具体的数据库。当我们需要替换数据库的时候，比如从 MySQL 到 Oracle，从 Oracle 到 Redis，只需要改动 Repository 层的代码，Service 层的代码完全不需要修改。

除此之外，Controller、Service、Repository 三层代码的稳定程度不同、引起变化的原因不同，所以分成三层来组织代码，能有效地隔离变化。比如，Repository 层基于数据库表，而数据库表改动的可能性很小，所以 Repository 层的代码最稳定，而 Controller 层提供适配给外部使用的接口，代码经常会变动。分层之后，Controller 层中代码的频繁改动并不会影响到稳定的 Repository 层。

3. 分层能起到隔离关注点的作用

Repository 层只关注数据的读写。Service 层只关注业务逻辑，不关注数据的来源。Controller 层只关注与外界打交道，数据校验、封装、格式转换，并不关心业务逻辑。三层之间的关注点不同，分层之后，职责分明，更加符合单一职责原则，代码的内聚性更好。

4. 分层能提高代码的可测试性

后面讲单元测试的时候，我们会讲到，单元测试不依赖不可控的外部组件，比如数据库。分层之后，Repsitory 层的代码通过依赖注入的方式供 Service 层使用，当要测试包含核心业务逻辑的 Service 层代码的时候，我们可以用 mock 的数据源替代真实的数据库，注入到 Service 层代码中。代码的可测试性和单元测试我们后面会讲到，这里你稍微了解即可。

5. 分层能应对系统的复杂性

所有的代码都放到一个类中，那这个类的代码就会因为需求的迭代而无限膨胀。我们知道，当一个类或一个函数的代码过多之后，可读性、可维护性就会变差。那我们就要想办法拆分。拆分有垂直和水平两个方向。水平方向基于业务来做拆分，就是模块化；垂直方向基于流程来做拆分，就是这里说的分层。

### BO、VO、Entity 存在的意义是什么？

在前面的章节中，我们提到，针对 Controller、Service、Repository 三层，每层都会定义相应的数据对象，它们分别是 VO（View Object）、BO（Business Object）、Entity，例如 UserVo、UserBo、UserEntity。在实际的开发中，VO、BO、Entity 可能存在大量的重复字段，甚至三者包含的字段完全一样。在开发的过程中，我们经常需要重复定义三个几乎一样的类，显然是一种重复劳动。

相对于每层定义各自的数据对象来说，是不是定义一个公共的数据对象更好些呢？

实际上，我更加推荐每层都定义各自的数据对象这种设计思路，主要有以下 3 个方面的原因。

- VO、BO、Entity 并非完全一样。比如，我们可以在 UserEntity、UserBo 中定义 Password 字段，但显然不能在 UserVo 中定义 Password 字段，否则就会将用户的密码暴露出去。
- VO、BO、Entity 三个类虽然代码重复，但功能语义不重复，从职责上讲是不一样的。所以，也并不能算违背 DRY 原则。在前面讲到 DRY 原则的时候，针对这种情况，如果合并为同一个类，那也会存在后期因为需求的变化而需要再拆分的问题。
- 为了尽量减少每层之间的耦合，把职责边界划分明确，每层都会维护自己的数据对象，层与层之间通过接口交互。数据从下一层传递到上一层的时候，将下一层的数据对象转化成上一层的数据对象，再继续处理。虽然这样的设计稍微有些繁琐，每层都需要定义各自的数据对象，需要做数据对象之间的转化，但是分层清晰。对于非常大的项目来说，结构清晰是第一位的！

既然 VO、BO、Entity 不能合并，那如何解决代码重复的问题呢？从设计的角度来说，VO、BO、Entity 的设计思路并不违反 DRY 原则，为了分层清晰、减少耦合，多维护几个类的成本也并不是不能接受的。但是，如果你真的有代码洁癖，对于代码重复的问题，我们也有一些办法来解决。

我们前面讲到，继承可以解决代码重复问题。我们可以将公共的字段定义在父类中，让 VO、BO、Entity 都继承这个父类，各自只定义特有的字段。因为这里的继承层次很浅，也不复杂，所以使用继承并不会影响代码的可读性和可维护性。后期如果因为业务的需要，有些字段需要从父类移动到子类，或者从子类提取到父类，代码改起来也并不复杂。

前面在讲“多用组合，少用继承”设计思想的时候，我们提到，组合也可以解决代码重复的问题，所以，这里我们还可以将公共的字段抽取到公共的类中，VO、BO、Entity 通过组合关系来复用这个类的代码。

代码重复问题解决了，那不同分层之间的数据对象该如何互相转化呢？

当下一层的数据通过接口调用传递到上一层之后，我们需要将它转化成上一层对应的数据对象类型。比如，Service 层从 Repository 层获取的 Entity 之后，将其转化成 BO，再继续业务逻辑的处理。所以，整个开发的过程会涉及“Entity 到 BO”和“BO 到 VO”这两种转化。

最简单的转化方式是手动复制。自己写代码在两个对象之间，一个字段一个字段的赋值。但这样的做法显然是没有技术含量的低级劳动。Java 中提供了多种数据对象转化工具，比如 BeanUtils、Dozer 等，可以大大简化繁琐的对象转化工作。如果你是用其他编程语言来做开发，也可以借鉴 Java 这些工具类的设计思路，自己在项目中实现对象转化工具类。

VO、BO、Entity 都是基于贫血模型的，而且为了兼容框架或开发库（比如 MyBatis、Dozer、BeanUtils），我们还需要定义每个字段的 set 方法。这些都违背 OOP 的封装特性，会导致数据被随意修改。那到底该怎么办好呢？

前面我们也提到过，Entity 和 VO 的生命周期是有限的，都仅限在本层范围内。而对应的 Repository 层和 Controller 层也都不包含太多业务逻辑，所以也不会有太多代码随意修改数据，即便设计成贫血、定义每个字段的 set 方法，相对来说也是安全的。

不过，Service 层包含比较多的业务逻辑代码，所以 BO 就存在被任意修改的风险了。但是，设计的问题本身就没有最优解，只有权衡。为了使用方便，我们只能做一些妥协，放弃 BO 的封装特性，由程序员自己来负责这些数据对象的不被错误使用。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210405223233.jpg)

### 总结

1. 为什么要分 MVC 三层开发？对于这个问题，我总结了以下 5 点原因。
   - 分层能起到代码复用的作用
   - 分层能起到隔离变化的作用
   - 分层能起到隔离关注点的作用
   - 分层能提高代码的可测试性
   - 分层能应对系统的复杂性
2. BO、VO、Entity 存在的意义是什么？
   - 从设计的角度来说，VO、BO、Entity 的设计思路并不违反 DRY 原则，为了分层清晰、减少耦合，多维护几个类的成本也并不是不能接受的。但是，如果你真的有代码洁癖，对于代码重复的问题，我们可以通过继承或者组合来解决。
   - 如何进行数据对象之间的转化？最简单的方式就是手动复制。当然，你也可以使用 Java 中提供了数据对象转化工具，比如 BeanUtils、Dozer 等，可以大大简化繁琐的对象转化工作。
   - 尽管 VO、BO、Entity 的设计违背 OOP 的封装特性，有被随意修改的风险。但 Entity 和 VO 的生命周期是有限的，都仅限在本层范围内，相对来说是安全的。Service 层包含比较多的业务逻辑代码，所以 BO 就存在被任意修改的风险了。为了使用方便，我们只能做一些妥协，放弃 BO 的封装特性，由程序员自己来负责这些数据对象的不被错误使用。

## 25 | 实战二（上）：针对非业务的通用框架开发，如何做需求分析和设计？

### 项目背景

我们希望设计开发一个小的框架，能够获取接口调用的各种统计信息，比如，响应时间的最大值（max）、最小值（min）、平均值（avg）、百分位值（percentile）、接口调用次数（count）、频率（tps） 等，并且支持将统计结果以各种显示格式（比如：JSON 格式、网页格式、自定义显示格式等）输出到各种终端（Console 命令行、HTTP 网页、Email、日志文件、自定义输出终端等），以方便查看。

我们假设这是真实项目中的一个开发需求，如果让你来负责开发这样一个通用的框架，应用到各种业务系统中，支持实时计算、查看数据的统计信息，你会如何设计和实现呢？你可以先自己主动思考一下，然后再来看我的分析思路。

### 需求分析

性能计数器作为一个跟业务无关的功能，我们完全可以把它开发成一个独立的框架或者类库，集成到很多业务系统中。而作为可被复用的框架，除了功能性需求之外，非功能性需求也非常重要。所以，接下来，我们从这两个方面来做需求分析。

### 1.功能性需求分析

相对于一大长串的文字描述，人脑更容易理解短的、罗列的比较规整、分门别类的列表信息。显然，刚才那段需求描述不符合这个规律。我们需要把它拆解成一个一个的“干条条”。拆解之后我写在下面了，是不是看起来更加清晰、有条理？

- 接口统计信息：包括接口响应时间的统计信息，以及接口调用次数的统计信息等。
- 统计信息的类型：max、min、avg、percentile、count、tps 等。
- 统计信息显示格式：Json、Html、自定义显示格式。
- 统计信息显示终端：Console、Email、HTTP 网页、日志、自定义显示终端。

除此之外，我们还可以借助设计产品的时候，经常用到的线框图，把最终数据的显示样式画出来，会更加一目了然。具体的线框图如下所示：

![img](https://static001.geekbang.org/resource/image/f0/e5/f04b341ad5fda418ae24f166d0a4dde5.jpg)

实际上，从线框图中，我们还能挖掘出了下面几个隐藏的需求。

- 统计触发方式：包括主动和被动两种。主动表示以一定的频率定时统计数据，并主动推送到显示终端，比如邮件推送。被动表示用户触发统计，比如用户在网页中选择要统计的时间区间，触发统计，并将结果显示给用户。
- 统计时间区间：框架需要支持自定义统计时间区间，比如统计最近 10 分钟的某接口的 tps、访问次数，或者统计 12 月 11 日 00 点到 12 月 12 日 00 点之间某接口响应时间的最大值、最小值、平均值等。
- 统计时间间隔：对于主动触发统计，我们还要支持指定统计时间间隔，也就是多久触发一次统计显示。比如，每间隔 10s 统计一次接口信息并显示到命令行中，每间隔 24 小时发送一封统计信息邮件。

### 2. 非功能性需求分析

对于这样一个通用的框架的开发，我们还需要考虑很多非功能性的需求。具体来讲，我总结了以下几个比较重要的方面。

### 框架设计

对于性能计数器这个框架的开发来说，我们可以先聚焦于一个非常具体、简单的应用场景，比如统计用户注册、登录这两个接口的响应时间的最大值和平均值、接口调用次数，并且将统计结果以 JSON 的格式输出到命令行中。现在这个需求简单、具体、明确，设计实现起来难度降低了很多。

```java
//应用场景：统计下面两个接口(注册和登录）的响应时间和访问次数
public class UserController {
  public void register(UserVo user) {
    //...
  }
  
  public UserVo login(String telephone, String password) {
    //...
  }
}
```

要输出接口的响应时间的最大值、平均值和接口调用次数，我们首先要采集每次接口请求的响应时间，并且存储起来，然后按照某个时间间隔做聚合统计，最后才是将结果输出。在原型系统的代码实现中，我们可以把所有代码都塞到一个类中，暂时不用考虑任何代码质量、线程安全、性能、扩展性等等问题，怎么简单怎么来就行。

最小原型的代码实现如下所示。其中，recordResponseTime() 和 recordTimestamp() 两个函数分别用来记录接口请求的响应时间和访问时间。startRepeatedReport() 函数以指定的频率统计数据并输出结果。

```java
public class Metrics {
  // Map的key是接口名称，value对应接口请求的响应时间或时间戳；
  private Map<String, List<Double>> responseTimes = new HashMap<>();
  private Map<String, List<Double>> timestamps = new HashMap<>();
  private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

  public void recordResponseTime(String apiName, double responseTime) {
    responseTimes.putIfAbsent(apiName, new ArrayList<>());
    responseTimes.get(apiName).add(responseTime);
  }

  public void recordTimestamp(String apiName, double timestamp) {
    timestamps.putIfAbsent(apiName, new ArrayList<>());
    timestamps.get(apiName).add(timestamp);
  }

  public void startRepeatedReport(long period, TimeUnit unit){
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        Gson gson = new Gson();
        Map<String, Map<String, Double>> stats = new HashMap<>();
        for (Map.Entry<String, List<Double>> entry : responseTimes.entrySet()) {
          String apiName = entry.getKey();
          List<Double> apiRespTimes = entry.getValue();
          stats.putIfAbsent(apiName, new HashMap<>());
          stats.get(apiName).put("max", max(apiRespTimes));
          stats.get(apiName).put("avg", avg(apiRespTimes));
        }
  
        for (Map.Entry<String, List<Double>> entry : timestamps.entrySet()) {
          String apiName = entry.getKey();
          List<Double> apiTimestamps = entry.getValue();
          stats.putIfAbsent(apiName, new HashMap<>());
          stats.get(apiName).put("count", (double)apiTimestamps.size());
        }
        System.out.println(gson.toJson(stats));
      }
    }, 0, period, unit);
  }

  private double max(List<Double> dataset) {//省略代码实现}
  private double avg(List<Double> dataset) {//省略代码实现}
}
```

我们通过不到 50 行代码就实现了最小原型。接下来，我们再来看，如何用它来统计注册、登录接口的响应时间和访问次数。具体的代码如下所示：

```java
//应用场景：统计下面两个接口(注册和登录）的响应时间和访问次数
public class UserController {
  private Metrics metrics = new Metrics();
  
  public UserController() {
    metrics.startRepeatedReport(60, TimeUnit.SECONDS);
  }

  public void register(UserVo user) {
    long startTimestamp = System.currentTimeMillis();
    metrics.recordTimestamp("regsiter", startTimestamp);
    //...
    long respTime = System.currentTimeMillis() - startTimestamp;
    metrics.recordResponseTime("register", respTime);
  }

  public UserVo login(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();
    metrics.recordTimestamp("login", startTimestamp);
    //...
    long respTime = System.currentTimeMillis() - startTimestamp;
    metrics.recordResponseTime("login", respTime);
  }
}
```

最小原型的代码实现虽然简陋，但它却帮我们将思路理顺了很多，我们现在就基于它做最终的框架设计。下面是我针对性能计数器框架画的一个粗略的系统设计图。图可以非常直观地体现设计思想，并且能有效地帮助我们释放更多的脑空间，来思考其他细节问题。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210407194028.jpg)

如图所示，我们把整个框架分为四个模块：数据采集、存储、聚合统计、显示。每个模块负责的工作简单罗列如下。

- 数据采集：负责打点采集原始数据，包括记录每次接口请求的响应时间和请求时间。数据采集过程要高度容错，不能影响到接口本身的可用性。除此之外，因为这部分功能是暴露给框架的使用者的，所以在设计数据采集 API 的时候，我们也要尽量考虑其易用性。
- 存储：负责将采集的原始数据保存下来，以便后面做聚合统计。数据的存储方式有多种，比如：Redis、MySQL、HBase、日志、文件、内存等。数据存储比较耗时，为了尽量地减少对接口性能（比如响应时间）的影响，采集和存储的过程异步完成。
- 聚合统计：负责将原始数据聚合为统计数据，比如：max、min、avg、pencentile、count、tps 等。为了支持更多的聚合统计规则，代码希望尽可能灵活、可扩展。
- 显示：负责将统计数据以某种格式显示到终端，比如：输出到命令行、邮件、网页、自定义显示终端等。

## 26 | 实战二（下）：如何实现一个支持各种统计规则的性能计数器？

### 面向对象设计与实现

#### 1. 划分职责进而识别出有哪些类

根据需求描述，我们先大致识别出下面几个接口或类。这一步不难，完全就是翻译需求。

- MetricsCollector 类负责提供 API，来采集接口请求的原始数据。我们可以为 MetricsCollector 抽象出一个接口，但这并不是必须的，因为暂时我们只能想到一个 MetricsCollector 的实现方式。
- MetricsStorage 接口负责原始数据存储，RedisMetricsStorage 类实现 MetricsStorage 接口。这样做是为了今后灵活地扩展新的存储方法，比如用 HBase 来存储。
- Aggregator 类负责根据原始数据计算统计数据。
- ConsoleReporter 类、EmailReporter 类分别负责以一定频率统计并发送统计数据到命令行和邮件。至于 ConsoleReporter 和 EmailReporter 是否可以抽象出可复用的抽象类，或者抽象出一个公共的接口，我们暂时还不能确定。

#### 2. 定义类及类与类之间的关系

接下来就是定义类及属性和方法，定义类与类之间的关系。这两步没法分得很开，所以，我们今天将它们合在一起来讲解。

大致地识别出几个核心的类之后，我的习惯性做法是，先在 IDE 中创建好这几个类，然后开始试着定义它们的属性和方法。在设计类、类与类之间交互的时候，我会不断地用之前学过的设计原则和思想来审视设计是否合理，比如，是否满足单一职责原则、开闭原则、依赖注入、KISS 原则、DRY 原则、迪米特法则，是否符合基于接口而非实现编程思想，代码是否高内聚、低耦合，是否可以抽象出可复用代码等等。

MetricsCollector 类的定义非常简单，具体代码如下所示。对比上一节课中最小原型的代码，MetricsCollector 通过引入 RequestInfo 类来封装原始数据信息，用一个采集函数代替了之前的两个函数。

```java
public class MetricsCollector {
  private MetricsStorage metricsStorage;//基于接口而非实现编程

  //依赖注入
  public MetricsCollector(MetricsStorage metricsStorage) {
    this.metricsStorage = metricsStorage;
  }

  //用一个函数代替了最小原型中的两个函数
  public void recordRequest(RequestInfo requestInfo) {
    if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
      return;
    }
    metricsStorage.saveRequestInfo(requestInfo);
  }
}

public class RequestInfo {
  private String apiName;
  private double responseTime;
  private long timestamp;
  //...省略constructor/getter/setter方法...
}
```

MetricsStorage 类和 RedisMetricsStorage 类的属性和方法也比较明确。具体的代码实现如下所示。注意，一次性取太长时间区间的数据，可能会导致拉取太多的数据到内存中，有可能会撑爆内存。对于 Java 来说，就有可能会触发 OOM（Out Of Memory）。而且，即便不出现 OOM，内存还够用，但也会因为内存吃紧，导致频繁的 Full GC，进而导致系统接口请求处理变慢，甚至超时。这个问题解决起来并不难，先留给你自己思考一下。我会在第 40 节课中解答。

```java
public interface MetricsStorage {
  void saveRequestInfo(RequestInfo requestInfo);

  List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);

  Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
}

public class RedisMetricsStorage implements MetricsStorage {
  //...省略属性和构造函数等...
  @Override
  public void saveRequestInfo(RequestInfo requestInfo) {
    //...
  }

  @Override
  public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
    //...
  }

  @Override
  public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
    //...
  }
}
```

MetricsCollector 类和 MetricsStorage 类的设计思路比较简单，不同的人给出的设计结果应该大差不差。但是，统计和显示这两个功能就不一样了，可以有多种设计思路。实际上，如果我们把统计显示所要完成的功能逻辑细分一下的话，主要包含下面 4 点：

1. 根据给定的时间区间，从数据库中拉取数据；
2. 根据原始数据，计算得到统计数据；
3. 将统计数据显示到终端（命令行或邮件）；
4. 定时触发以上 3 个过程的执行。

实际上，如果用一句话总结一下的话，面向对象设计和实现要做的事情，就是把合适的代码放到合适的类中。所以，我们现在要做的工作就是，把以上的 4 个功能逻辑划分到几个类中。划分的方法有很多种，比如，我们可以把前两个逻辑放到一个类中，第 3 个逻辑放到另外一个类中，第 4 个逻辑作为上帝类（God Class）组合前面两个类来触发前 3 个逻辑的执行。当然，我们也可以把第 2 个逻辑单独放到一个类中，第 1、3、4 都放到另外一个类中。

至于到底选择哪种排列组合方式，判定的标准是，让代码尽量地满足低耦合、高内聚、单一职责、对扩展开放对修改关闭等之前讲到的各种设计原则和思想，尽量地让设计满足代码易复用、易读、易扩展、易维护。

我们暂时选择把第 1、3、4 逻辑放到 ConsoleReporter 或 EmailReporter 类中，把第 2 个逻辑放到 Aggregator 类中。其中，Aggregator 类负责的逻辑比较简单，我们把它设计成只包含静态方法的工具类。具体的代码实现如下所示：

```java
public class Aggregator {
  public static RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMillis) {
    double maxRespTime = Double.MIN_VALUE;
    double minRespTime = Double.MAX_VALUE;
    double avgRespTime = -1;
    double p999RespTime = -1;
    double p99RespTime = -1;
    double sumRespTime = 0;
    long count = 0;
    for (RequestInfo requestInfo : requestInfos) {
      ++count;
      double respTime = requestInfo.getResponseTime();
      if (maxRespTime < respTime) {
        maxRespTime = respTime;
      }
      if (minRespTime > respTime) {
        minRespTime = respTime;
      }
      sumRespTime += respTime;
    }
    if (count != 0) {
      avgRespTime = sumRespTime / count;
    }
    long tps = (long)(count / durationInMillis * 1000);
    Collections.sort(requestInfos, new Comparator<RequestInfo>() {
      @Override
      public int compare(RequestInfo o1, RequestInfo o2) {
        double diff = o1.getResponseTime() - o2.getResponseTime();
        if (diff < 0.0) {
          return -1;
        } else if (diff > 0.0) {
          return 1;
        } else {
          return 0;
        }
      }
    });
    int idx999 = (int)(count * 0.999);
    int idx99 = (int)(count * 0.99);
    if (count != 0) {
      p999RespTime = requestInfos.get(idx999).getResponseTime();
      p99RespTime = requestInfos.get(idx99).getResponseTime();
    }
    RequestStat requestStat = new RequestStat();
    requestStat.setMaxResponseTime(maxRespTime);
    requestStat.setMinResponseTime(minRespTime);
    requestStat.setAvgResponseTime(avgRespTime);
    requestStat.setP999ResponseTime(p999RespTime);
    requestStat.setP99ResponseTime(p99RespTime);
    requestStat.setCount(count);
    requestStat.setTps(tps);
    return requestStat;
  }
}

public class RequestStat {
  private double maxResponseTime;
  private double minResponseTime;
  private double avgResponseTime;
  private double p999ResponseTime;
  private double p99ResponseTime;
  private long count;
  private long tps;
  //...省略getter/setter方法...
}
```

ConsoleReporter 类相当于一个上帝类，定时根据给定的时间区间，从数据库中取出数据，借助 Aggregator 类完成统计工作，并将统计结果输出到命令行。具体的代码实现如下所示：

```java
public class ConsoleReporter {
  private MetricsStorage metricsStorage;
  private ScheduledExecutorService executor;

  public ConsoleReporter(MetricsStorage metricsStorage) {
    this.metricsStorage = metricsStorage;
    this.executor = Executors.newSingleThreadScheduledExecutor();
  }
  
  // 第4个代码逻辑：定时触发第1、2、3代码逻辑的执行；
  public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
    executor.scheduleAtFixedRate(new Runnable() {
      @Override
      public void run() {
        // 第1个代码逻辑：根据给定的时间区间，从数据库中拉取数据；
        long durationInMillis = durationInSeconds * 1000;
        long endTimeInMillis = System.currentTimeMillis();
        long startTimeInMillis = endTimeInMillis - durationInMillis;
        Map<String, List<RequestInfo>> requestInfos =
                metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
        Map<String, RequestStat> stats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
          String apiName = entry.getKey();
          List<RequestInfo> requestInfosPerApi = entry.getValue();
          // 第2个代码逻辑：根据原始数据，计算得到统计数据；
          RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
          stats.put(apiName, requestStat);
        }
        // 第3个代码逻辑：将统计数据显示到终端（命令行或邮件）；
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(stats));
      }
    }, 0, periodInSeconds, TimeUnit.SECONDS);
  }
}

public class EmailReporter {
  private static final Long DAY_HOURS_IN_SECONDS = 86400L;

  private MetricsStorage metricsStorage;
  private EmailSender emailSender;
  private List<String> toAddresses = new ArrayList<>();

  public EmailReporter(MetricsStorage metricsStorage) {
    this(metricsStorage, new EmailSender(/*省略参数*/));
  }

  public EmailReporter(MetricsStorage metricsStorage, EmailSender emailSender) {
    this.metricsStorage = metricsStorage;
    this.emailSender = emailSender;
  }

  public void addToAddress(String address) {
    toAddresses.add(address);
  }

  public void startDailyReport() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    Date firstTime = calendar.getTime();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
        long endTimeInMillis = System.currentTimeMillis();
        long startTimeInMillis = endTimeInMillis - durationInMillis;
        Map<String, List<RequestInfo>> requestInfos =
                metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
        Map<String, RequestStat> stats = new HashMap<>();
        for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
          String apiName = entry.getKey();
          List<RequestInfo> requestInfosPerApi = entry.getValue();
          RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
          stats.put(apiName, requestStat);
        }
        // TODO: 格式化为html格式，并且发送邮件
      }
    }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
  }
}
```

#### 3. 将类组装起来并提供执行入口

因为这个框架稍微有些特殊，有两个执行入口：一个是 MetricsCollector 类，提供了一组 API 来采集原始数据；另一个是 ConsoleReporter 类和 EmailReporter 类，用来触发统计显示。框架具体的使用方式如下所示：

```java
public class Demo {
  public static void main(String[] args) {
    MetricsStorage storage = new RedisMetricsStorage();
    ConsoleReporter consoleReporter = new ConsoleReporter(storage);
    consoleReporter.startRepeatedReport(60, 60);

    EmailReporter emailReporter = new EmailReporter(storage);
    emailReporter.addToAddress("wangzheng@xzg.com");
    emailReporter.startDailyReport();

    MetricsCollector collector = new MetricsCollector(storage);
    collector.recordRequest(new RequestInfo("register", 123, 10234));
    collector.recordRequest(new RequestInfo("register", 223, 11234));
    collector.recordRequest(new RequestInfo("register", 323, 12334));
    collector.recordRequest(new RequestInfo("login", 23, 12434));
    collector.recordRequest(new RequestInfo("login", 1223, 14234));

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
```

### Review 设计与实现

我们前面讲到了 SOLID、KISS、DRY、YAGNI、LOD 等设计原则，基于接口而非实现编程、多用组合少用继承、高内聚低耦合等设计思想。我们现在就来看下，上面的代码实现是否符合这些设计原则和思想。

- MetricsCollector。MetricsCollector 负责采集和存储数据，职责相对来说还算比较单一。它基于接口而非实现编程，通过依赖注入的方式来传递 MetricsStorage 对象，可以在不需要修改代码的情况下，灵活地替换不同的存储方式，满足开闭原则。
- MetricsStorage、RedisMetricsStorage。MetricsStorage 和 RedisMetricsStorage 的设计比较简单。当我们需要实现新的存储方式的时候，只需要实现 MetricsStorage 接口即可。因为所有用到 MetricsStorage 和 RedisMetricsStorage 的地方，都是基于相同的接口函数来编程的，所以，除了在组装类的地方有所改动（从 RedisMetricsStorage 改为新的存储实现类），其他接口函数调用的地方都不需要改动，满足开闭原则。
- Aggregator。Aggregator 类是一个工具类，里面只有一个静态函数，有 50 行左右的代码量，负责各种统计数据的计算。当需要扩展新的统计功能的时候，需要修改 aggregate() 函数代码，并且一旦越来越多的统计功能添加进来之后，这个函数的代码量会持续增加，可读性、可维护性就变差了。所以，从刚刚的分析来看，这个类的设计可能存在职责不够单一、不易扩展等问题，需要在之后的版本中，对其结构做优化。
- ConsoleReporter、EmailReporter。ConsoleReporter 和 EmailReporter 中存在代码重复问题。在这两个类中，从数据库中取数据、做统计的逻辑都是相同的，可以抽取出来复用，否则就违反了 DRY 原则。而且整个类负责的事情比较多，职责不是太单一。特别是显示部分的代码，可能会比较复杂（比如 Email 的展示方式），最好是将显示部分的代码逻辑拆分成独立的类。除此之外，因为代码中涉及线程操作，并且调用了 Aggregator 的静态函数，所以代码的可测试性不好。

# 设计原则与思想：规范与重构（11讲）

## 27 | 理论一：什么情况下要重构？到底重构什么？又该如何重构？



## 34 | 实战一（上）：通过一段ID生成器代码，学习如何发现代码质量问题

### ID 生成器需求背景介绍

“ID”中文翻译为“标识（Identifier）”。这个概念在生活、工作中随处可见，比如身份证、商品条形码、二维码、车牌号、驾照号。聚焦到软件开发中，ID 常用来表示一些业务信息的唯一标识，比如订单的单号或者数据库中的唯一主键，比如地址表中的 ID 字段（实际上是没有业务含义的，对用户来说是透明的，不需要关注）。

假设你正在参与一个后端业务系统的开发，为了方便在请求出错时排查问题，我们在编写代码的时候会在关键路径上打印日志。某个请求出错之后，我们希望能搜索出这个请求对应的所有日志，以此来查找问题的原因。而实际情况是，在日志文件中，不同请求的日志会交织在一起。如果没有东西来标识哪些日志属于同一个请求，我们就无法关联同一个请求的所有日志。

这听起来有点像微服务中的调用链追踪。不过，微服务中的调用链追踪是服务间的追踪，我们现在要实现的是服务内的追踪。

借鉴微服务调用链追踪的实现思路，我们可以给每个请求分配一个唯一 ID，并且保存在请求的上下文（Context）中，比如，处理请求的工作线程的局部变量中。在 Java 语言中，我们可以将 ID 存储在 Servlet 线程的 ThreadLocal 中，或者利用 Slf4j 日志框架的 MDC（Mapped Diagnostic Contexts）来实现（实际上底层原理也是基于线程的 ThreadLocal）。每次打印日志的时候，我们从请求上下文中取出请求 ID，跟日志一块输出。这样，同一个请求的所有日志都包含同样的请求 ID 信息，我们就可以通过请求 ID 来搜索同一个请求的所有日志了。

### 一份“能用”的代码实现

假设 leader 让小王负责这个 ID 生成器的开发。对于稍微有点开发经验的程序员来说，实现这样一个简单的 ID 生成器，并不是件难事。所以，小王很快就完成了任务，将代码写了出来，具体如下所示：

```java
public class IdGenerator {
  private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

  public static String generate() {
    String id = "";
    try {
      String hostName = InetAddress.getLocalHost().getHostName();
      String[] tokens = hostName.split("\\.");
      if (tokens.length > 0) {
        hostName = tokens[tokens.length - 1];
      }
      char[] randomChars = new char[8];
      int count = 0;
      Random random = new Random();
      while (count < 8) {
        int randomAscii = random.nextInt(122);
        if (randomAscii >= 48 && randomAscii <= 57) {
          randomChars[count] = (char)('0' + (randomAscii - 48));
          count++;
        } else if (randomAscii >= 65 && randomAscii <= 90) {
          randomChars[count] = (char)('A' + (randomAscii - 65));
          count++;
        } else if (randomAscii >= 97 && randomAscii <= 122) {
          randomChars[count] = (char)('a' + (randomAscii - 97));
          count++;
        }
      }
      id = String.format("%s-%d-%s", hostName,
              System.currentTimeMillis(), new String(randomChars));
    } catch (UnknownHostException e) {
      logger.warn("Failed to get the host name.", e);
    }

    return id;
  }
}
```

上面的代码生成的 ID 示例如下所示。整个 ID 由三部分组成。第一部分是本机名的最后一个字段。第二部分是当前时间戳，精确到毫秒。第三部分是 8 位的随机字符串，包含大小写字母和数字。尽管这样生成的 ID 并不是绝对唯一的，有重复的可能，但事实上重复的概率非常低。对于我们的日志追踪来说，极小概率的 ID 重复是完全可以接受的。

```
103-1577456311467-3nR3Do45
103-1577456311468-0wnuV5yw
103-1577456311468-sdrnkFxN
103-1577456311468-8lwk0BP0
```

不过，在我看来，像小王的这份代码只能算得上“能用”，勉强及格。我为啥这么说呢？这段代码只有短短不到 40 行，里面却有很多值得优化的地方。你可以先思考一下，在纸上试着罗列一下这段代码存在的问题，然后再对比来看我下面的讲解。

### 如何发现代码质量问题？

从大处着眼的话，我们可以参考之前讲过的代码质量评判标准，看这段代码是否可读、可扩展、可维护、灵活、简洁、可复用、可测试等等。落实到具体细节，我们可以从以下几个方面来审视代码。

- 目录设置是否合理、模块划分是否清晰、代码结构是否满足“高内聚、松耦合”？
- 是否遵循经典的设计原则和设计思想（SOLID、DRY、KISS、YAGNI、LOD 等）？
- 设计模式是否应用得当？是否有过度设计？
- 代码是否容易扩展？如果要添加新功能，是否容易实现？
- 代码是否可以复用？是否可以复用已有的项目代码或类库？是否有重复造轮子？
- 代码是否容易测试？单元测试是否全面覆盖了各种正常和异常的情况？
- 代码是否易读？是否符合编码规范（比如命名和注释是否恰当、代码风格是否一致等）？

以上是一些通用的关注点，可以作为常规检查项，套用在任何代码的重构上。除此之外，我们还要关注代码实现是否满足业务本身特有的功能和非功能需求。我罗列了一些比较有共性的问题，如下所示。这份列表可能还不够全面，剩下的需要你针对具体的业务、具体的代码去具体分析。

- 代码是否实现了预期的业务需求？
- 逻辑是否正确？是否处理了各种异常情况？
- 日志打印是否得当？是否方便 debug 排查问题？
- 接口是否易用？是否支持幂等、事务等？
- 代码是否存在并发问题？是否线程安全？
- 性能是否有优化空间，比如，SQL、算法是否可以优化？
- 是否有安全漏洞？比如输入输出校验是否全面？

首先，IdGenerator 的代码比较简单，只有一个类，所以，不涉及目录设置、模块划分、代码结构问题，也不违反基本的 SOLID、DRY、KISS、YAGNI、LOD 等设计原则。它没有应用设计模式，所以也不存在不合理使用和过度设计的问题。

其次，IdGenerator 设计成了实现类而非接口，调用者直接依赖实现而非接口，违反基于接口而非实现编程的设计思想。实际上，将 IdGenerator 设计成实现类，而不定义接口，问题也不大。如果哪天 ID 生成算法改变了，我们只需要直接修改实现类的代码就可以。但是，如果项目中需要同时存在两种 ID 生成算法，也就是要同时存在两个 IdGenerator 实现类。比如，我们需要将这个框架给更多的系统来使用。系统在使用的时候，可以灵活地选择它需要的生成算法。这个时候，我们就需要将 IdGenerator 定义为接口，并且为不同的生成算法定义不同的实现类。

再次，把 IdGenerator 的 generate() 函数定义为静态函数，[会影响使用该函数的代码的可测试性](https://testing.googleblog.com/2008/12/static-methods-are-death-to-testability.html)。同时，generate() 函数的代码实现依赖运行环境（本机名）、时间函数、随机函数，所以 generate() 函数本身的可测试性也不好，需要做比较大的重构。除此之外，小王也没有编写单元测试代码，我们需要在重构时对其进行补充。

最后，虽然 IdGenerator 只包含一个函数，并且代码行数也不多，但代码的可读性并不好。特别是随机字符串生成的那部分代码，一方面，代码完全没有注释，生成算法比较难读懂，另一方面，代码里有很多魔法数，严重影响代码的可读性。在重构的时候，我们需要重点提高这部分代码的可读性。

前面我们提到，虽然小王的代码生成的 ID 并非绝对的唯一，但是对于追踪打印日志来说，是可以接受小概率 ID 冲突的，满足我们预期的业务需求。不过，获取 hostName 这部分代码逻辑貌似有点问题，并未处理“hostName 为空”的情况。除此之外，尽管代码中针对获取不到本机名的情况做了异常处理，但是小王对异常的处理是在 IdGenerator 内部将其吐掉，然后打印一条报警日志，并没有继续往上抛出。这样的异常处理是否得当呢？你可以先自己思考一下，我们把这部分内容放到第 36、37 讲中具体讲解。

小王代码的日志打印得当，日志描述能够准确反应问题，方便 debug，并且没有过多的冗余日志。IdGenerator 只暴露一个 generate() 接口供使用者使用，接口的定义简单明了，不存在不易用问题。generate() 函数代码中没有涉及共享变量，所以代码线程安全，多线程环境下调用 generate() 函数不存在并发问题。

性能方面，ID 的生成不依赖外部存储，在内存中生成，并且日志的打印频率也不会很高，所以小王的代码在性能方面足以应对目前的应用场景。不过，每次生成 ID 都需要获取本机名，获取主机名会比较耗时，所以，这部分可以考虑优化一下。还有，randomAscii 的范围是 0～122，但可用数字仅包含三段子区间（0~9，a~z，A~Z），极端情况下会随机生成很多三段区间之外的无效数字，需要循环很多次才能生成随机字符串，所以随机字符串的生成算法也可以优化一下。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210407222839.jpg)

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210407222844.jpg)

## 35 | 实战一（下）：手把手带你将ID生成器代码从“能用”重构为“好用”

- 第一轮重构：提高代码的可读性
- 第二轮重构：提高代码的可测试性
- 第三轮重构：编写完善的单元测试
- 第四轮重构：所有重构完成之后添加注释

### 第一轮重构：提高代码的可读性

首先，我们要解决最明显、最急需改进的代码可读性问题。具体有下面几点：

- hostName 变量不应该被重复使用，尤其当这两次使用时的含义还不同的时候；
- 将获取 hostName 的代码抽离出来，定义为 getLastfieldOfHostName() 函数；
- 删除代码中的魔法数，比如，57、90、97、122；
- 将随机数生成的代码抽离出来，定义为 generateRandomAlphameric() 函数；
- generate() 函数中的三个 if 逻辑重复了，且实现过于复杂，我们要对其进行简化；
- 对 IdGenerator 类重命名，并且抽象出对应的接口。

这里我们重点讨论下最后一个修改。实际上，对于 ID 生成器的代码，有下面三种类的命名方式。你觉得哪种更合适呢？

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210408131741.jpg)

我们来逐一分析一下三种命名方式。

第一种命名方式，将接口命名为 IdGenerator，实现类命名为 LogTraceIdGenerator，这可能是很多人最先想到的命名方式了。在命名的时候，我们要考虑到，以后两个类会如何使用、会如何扩展。从使用和扩展的角度来分析，这样的命名就不合理了。

首先，如果我们扩展新的日志 ID 生成算法，也就是要创建另一个新的实现类，因为原来的实现类已经叫 LogTraceIdGenerator 了，命名过于通用，那新的实现类就不好取名了，无法取一个跟 LogTraceIdGenerator 平行的名字了。

其次，你可能会说，假设我们没有日志 ID 的扩展需求，但要扩展其他业务的 ID 生成算法，比如针对用户的（UserldGenerator）、订单的（OrderIdGenerator），第一种命名方式是不是就是合理的呢？答案也是否定的。基于接口而非实现编程，主要的目的是为了方便后续灵活地替换实现类。而 LogTraceIdGenerator、UserIdGenerator、OrderIdGenerator 三个类从命名上来看，涉及的是完全不同的业务，不存在互相替换的场景。也就是说，我们不可能在有关日志的代码中，进行下面这种替换。所以，让这三个类实现同一个接口，实际上是没有意义的。

```
IdGenearator idGenerator = new LogTraceIdGenerator();
替换为:
IdGenearator idGenerator = new UserIdGenerator();
```

第二种命名方式是不是就合理了呢？答案也是否定的。其中，LogTraceIdGenerator 接口的命名是合理的，但是 HostNameMillisIdGenerator 实现类暴露了太多实现细节，只要代码稍微有所改动，就可能需要改动命名，才能匹配实现。

第三种命名方式是我比较推荐的。在目前的 ID 生成器代码实现中，我们生成的 ID 是一个随机 ID，不是递增有序的，所以，命名成 RandomIdGenerator 是比较合理的，即便内部生成算法有所改动，只要生成的还是随机的 ID，就不需要改动命名。如果我们需要扩展新的 ID 生成算法，比如要实现一个递增有序的 ID 生成算法，那我们可以命名为 SequenceIdGenerator。

实际上，更好的一种命名方式是，我们抽象出两个接口，一个是 IdGenerator，一个是 LogTraceIdGenerator，LogTraceIdGenerator 继承 IdGenerator。实现类实现接口 IdGenerator，命名为 RandomIdGenerator、SequenceIdGenerator 等。这样，实现类可以复用到多个业务模块中，比如前面提到的用户、订单。

根据上面的优化策略，我们对代码进行第一轮的重构，重构之后的代码如下所示：

```java
public interface IdGenerator {
  String generate();
}

public interface LogTraceIdGenerator extends IdGenerator {
}

public class RandomIdGenerator implements LogTraceIdGenerator {
  private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

  @Override
  public String generate() {
    String substrOfHostName = getLastfieldOfHostName();
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s",
            substrOfHostName, currentTimeMillis, randomString);
    return id;
  }

  private String getLastfieldOfHostName() {
    String substrOfHostName = null;
    try {
      String hostName = InetAddress.getLocalHost().getHostName();
      String[] tokens = hostName.split("\\.");
      substrOfHostName = tokens[tokens.length - 1];
      return substrOfHostName;
    } catch (UnknownHostException e) {
      logger.warn("Failed to get the host name.", e);
    }
    return substrOfHostName;
  }

  private String generateRandomAlphameric(int length) {
    char[] randomChars = new char[length];
    int count = 0;
    Random random = new Random();
    while (count < length) {
      int maxAscii = 'z';
      int randomAscii = random.nextInt(maxAscii);
      boolean isDigit= randomAscii >= '0' && randomAscii <= '9';
      boolean isUppercase= randomAscii >= 'A' && randomAscii <= 'Z';
      boolean isLowercase= randomAscii >= 'a' && randomAscii <= 'z';
      if (isDigit|| isUppercase || isLowercase) {
        randomChars[count] = (char) (randomAscii);
        ++count;
      }
    }
    return new String(randomChars);
  }
}

//代码使用举例
LogTraceIdGenerator logTraceIdGenerator = new RandomIdGenerator();
```

### 第二轮重构：提高代码的可测试性

关于代码可测试性的问题，主要包含下面两个方面：

- generate() 函数定义为静态函数，会影响使用该函数的代码的可测试性；
- generate() 函数的代码实现依赖运行环境（本机名）、时间函数、随机函数，所以 generate() 函数本身的可测试性也不好。

对于第一点，我们已经在第一轮重构中解决了。我们将 RandomIdGenerator 类中的 generate() 静态函数重新定义成了普通函数。调用者可以通过依赖注入的方式，在外部创建好 RandomIdGenerator 对象后注入到自己的代码中，从而解决静态函数调用影响代码可测试性的问题。

对于第二点，我们需要在第一轮重构的基础之上再进行重构。重构之后的代码如下所示，主要包括以下几个代码改动。

- 从 getLastfieldOfHostName() 函数中，将逻辑比较复杂的那部分代码剥离出来，定义为 getLastSubstrSplittedByDot() 函数。因为 getLastfieldOfHostName() 函数依赖本地主机名，所以，剥离出主要代码之后这个函数变得非常简单，可以不用测试。我们重点测试 getLastSubstrSplittedByDot() 函数即可。
- 将 generateRandomAlphameric() 和 getLastSubstrSplittedByDot() 这两个函数的访问权限设置为 protected。这样做的目的是，可以直接在单元测试中通过对象来调用两个函数进行测试。
- 给 generateRandomAlphameric() 和 getLastSubstrSplittedByDot() 两个函数添加 Google Guava 的 annotation @VisibleForTesting。这个 annotation 没有任何实际的作用，只起到标识的作用，告诉其他人说，这两个函数本该是 private 访问权限的，之所以提升访问权限到 protected，只是为了测试，只能用于单元测试中。

```java
public class RandomIdGenerator implements LogTraceIdGenerator {
  private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

  @Override
  public String generate() {
    String substrOfHostName = getLastfieldOfHostName();
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s",
            substrOfHostName, currentTimeMillis, randomString);
    return id;
  }

  private String getLastfieldOfHostName() {
    String substrOfHostName = null;
    try {
      String hostName = InetAddress.getLocalHost().getHostName();
      substrOfHostName = getLastSubstrSplittedByDot(hostName);
    } catch (UnknownHostException e) {
      logger.warn("Failed to get the host name.", e);
    }
    return substrOfHostName;
  }

  @VisibleForTesting
  protected String getLastSubstrSplittedByDot(String hostName) {
    String[] tokens = hostName.split("\\.");
    String substrOfHostName = tokens[tokens.length - 1];
    return substrOfHostName;
  }

  @VisibleForTesting
  protected String generateRandomAlphameric(int length) {
    char[] randomChars = new char[length];
    int count = 0;
    Random random = new Random();
    while (count < length) {
      int maxAscii = 'z';
      int randomAscii = random.nextInt(maxAscii);
      boolean isDigit= randomAscii >= '0' && randomAscii <= '9';
      boolean isUppercase= randomAscii >= 'A' && randomAscii <= 'Z';
      boolean isLowercase= randomAscii >= 'a' && randomAscii <= 'z';
      if (isDigit|| isUppercase || isLowercase) {
        randomChars[count] = (char) (randomAscii);
        ++count;
      }
    }
    return new String(randomChars);
  }
}
```

在上一节课的课堂讨论中，我们提到，打印日志的 Logger 对象被定义为 static final 的，并且在类内部创建，这是否影响到代码的可测试性？是否应该将 Logger 对象通过依赖注入的方式注入到类中呢？

依赖注入之所以能提高代码可测试性，主要是因为，通过这样的方式我们能轻松地用 mock 对象替换依赖的真实对象。那我们为什么要 mock 这个对象呢？这是因为，这个对象参与逻辑执行（比如，我们要依赖它输出的数据做后续的计算）但又不可控。对于 Logger 对象来说，我们只往里写入数据，并不读取数据，不参与业务逻辑的执行，不会影响代码逻辑的正确性，所以，我们没有必要 mock Logger 对象。

除此之外，一些只是为了存储数据的值对象，比如 String、Map、UseVo，我们也没必要通过依赖注入的方式来创建，直接在类中通过 new 创建就可以了。

### 第三轮重构：编写完善的单元测试

经过上面的重构之后，代码存在的比较明显的问题，基本上都已经解决了。我们现在为代码补全单元测试。RandomIdGenerator 类中有 4 个函数。

```java
public String generate();
private String getLastfieldOfHostName();
@VisibleForTesting
protected String getLastSubstrSplittedByDot(String hostName);
@VisibleForTesting
protected String generateRandomAlphameric(int length);
```

我们先来看后两个函数。这两个函数包含的逻辑比较复杂，是我们测试的重点。而且，在上一步重构中，为了提高代码的可测试性，我们已经将这两个部分代码跟不可控的组件（本机名、随机函数、时间函数）进行了隔离。所以，我们只需要设计完备的单元测试用例即可。具体的代码实现如下所示（注意，我们使用了 JUnit 测试框架）：

```java
public class RandomIdGeneratorTest {
  @Test
  public void testGetLastSubstrSplittedByDot() {
    RandomIdGenerator idGenerator = new RandomIdGenerator();
    String actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1.field2.field3");
    Assert.assertEquals("field3", actualSubstr);

    actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1");
    Assert.assertEquals("field1", actualSubstr);

    actualSubstr = idGenerator.getLastSubstrSplittedByDot("field1#field2$field3");
    Assert.assertEquals("field1#field2#field3", actualSubstr);
  }

  // 此单元测试会失败，因为我们在代码中没有处理hostName为null或空字符串的情况
  // 这部分优化留在第36、37节课中讲解
  @Test
  public void testGetLastSubstrSplittedByDot_nullOrEmpty() {
    RandomIdGenerator idGenerator = new RandomIdGenerator();
    String actualSubstr = idGenerator.getLastSubstrSplittedByDot(null);
    Assert.assertNull(actualSubstr);

    actualSubstr = idGenerator.getLastSubstrSplittedByDot("");
    Assert.assertEquals("", actualSubstr);
  }

  @Test
  public void testGenerateRandomAlphameric() {
    RandomIdGenerator idGenerator = new RandomIdGenerator();
    String actualRandomString = idGenerator.generateRandomAlphameric(6);
    Assert.assertNotNull(actualRandomString);
    Assert.assertEquals(6, actualRandomString.length());
    for (char c : actualRandomString.toCharArray()) {
      Assert.assertTrue(('0' < c && c < '9') || ('a' < c && c < 'z') || ('A' < c && c < 'Z'));
    }
  }

  // 此单元测试会失败，因为我们在代码中没有处理length<=0的情况
  // 这部分优化留在第36、37节课中讲解
  @Test
  public void testGenerateRandomAlphameric_lengthEqualsOrLessThanZero() {
    RandomIdGenerator idGenerator = new RandomIdGenerator();
    String actualRandomString = idGenerator.generateRandomAlphameric(0);
    Assert.assertEquals("", actualRandomString);

    actualRandomString = idGenerator.generateRandomAlphameric(-1);
    Assert.assertNull(actualRandomString);
  }
}
```

我们再来看 generate() 函数。这个函数也是我们唯一一个暴露给外部使用的 public 函数。虽然逻辑比较简单，最好还是测试一下。但是，它依赖主机名、随机函数、时间函数，我们该如何测试呢？需要 mock 这些函数的实现吗？

实际上，这要分情况来看。我们前面讲过，写单元测试的时候，测试对象是函数定义的功能，而非具体的实现逻辑。这样我们才能做到，函数的实现逻辑改变了之后，单元测试用例仍然可以工作。那 generate() 函数实现的功能是什么呢？这完全是由代码编写者自己来定义的。

比如，针对同一份 generate() 函数的代码实现，我们可以有 3 种不同的功能定义，对应 3 种不同的单元测试。

1. 如果我们把 generate() 函数的功能定义为：“生成一个随机唯一 ID”，那我们只要测试多次调用 generate() 函数生成的 ID 是否唯一即可。
2. 如果我们把 generate() 函数的功能定义为：“生成一个只包含数字、大小写字母和中划线的唯一 ID”，那我们不仅要测试 ID 的唯一性，还要测试生成的 ID 是否只包含数字、大小写字母和中划线。
3. 如果我们把 generate() 函数的功能定义为：“生成唯一 ID，格式为：{主机名 substr}-{时间戳}-{8 位随机数}。在主机名获取失败时，返回：null-{时间戳}-{8 位随机数}”，那我们不仅要测试 ID 的唯一性，还要测试生成的 ID 是否完全符合格式要求。

总结一下，单元测试用例如何写，关键看你如何定义函数。针对 generate() 函数的前两种定义，我们不需要 mock 获取主机名函数、随机函数、时间函数等，但对于第 3 种定义，我们需要 mock 获取主机名函数，让其返回 null，测试代码运行是否符合预期。

最后，我们来看下 getLastfieldOfHostName() 函数。实际上，这个函数不容易测试，因为它调用了一个静态函数（InetAddress.getLocalHost().getHostName();），并且这个静态函数依赖运行环境。但是，这个函数的实现非常简单，肉眼基本上可以排除明显的 bug，所以我们可以不为其编写单元测试代码。毕竟，我们写单元测试的目的是为了减少代码 bug，而不是为了写单元测试而写单元测试。

### 第四轮重构：添加注释

前面我们提到，注释不能太多，也不能太少，主要添加在类和函数上。有人说，好的命名可以替代注释，清晰的表达含义。这点对于变量的命名来说是适用的，但对于类或函数来说就不一定对了。类或函数包含的逻辑往往比较复杂，单纯靠命名很难清晰地表明实现了什么功能，这个时候我们就需要通过注释来补充。比如，前面我们提到的对于 generate() 函数的 3 种功能定义，就无法用命名来体现，需要补充到注释里面。

总结一下，主要就是写清楚：做什么、为什么、怎么做、怎么用，对一些边界条件、特殊情况进行说明，以及对函数输入、输出、异常进行说明。

```java
/**
 * Id Generator that is used to generate random IDs.
 *
 * <p>
 * The IDs generated by this class are not absolutely unique,
 * but the probability of duplication is very low.
 */
public class RandomIdGenerator implements LogTraceIdGenerator {
  private static final Logger logger = LoggerFactory.getLogger(RandomIdGenerator.class);

  /**
   * Generate the random ID. The IDs may be duplicated only in extreme situation.
   *
   * @return an random ID
   */
  @Override
  public String generate() {
    //...
  }

  /**
   * Get the local hostname and
   * extract the last field of the name string splitted by delimiter '.'.
   *
   * @return the last field of hostname. Returns null if hostname is not obtained.
   */
  private String getLastfieldOfHostName() {
    //...
  }

  /**
   * Get the last field of {@hostName} splitted by delemiter '.'.
   *
   * @param hostName should not be null
   * @return the last field of {@hostName}. Returns empty string if {@hostName} is empty string.
   */
  @VisibleForTesting
  protected String getLastSubstrSplittedByDot(String hostName) {
    //...
  }

  /**
   * Generate random string which
   * only contains digits, uppercase letters and lowercase letters.
   *
   * @param length should not be less than 0
   * @return the random string. Returns empty string if {@length} is 0
   */
  @VisibleForTesting
  protected String generateRandomAlphameric(int length) {
    //...
  }
}
```

## 36 | 实战二（上）：程序出错该返回啥？NULL、异常、错误码、空对象？36 | 实战二（上）：程序出错该返回啥？NULL、异常、错误码、空对象？

我们可以把函数的运行结果分为两类。一类是预期的结果，也就是函数在正常情况下输出的结果。一类是非预期的结果，也就是函数在异常（或叫出错）情况下输出的结果。比如，在上一节课中，获取本机名的函数，在正常情况下，函数返回字符串格式的本机名；在异常情况下，获取本机名失败，函数返回 UnknownHostException 异常对象。

在正常情况下，函数返回数据的类型非常明确，但是，在异常情况下，函数返回的数据类型却非常灵活，有多种选择。除了刚刚提到的类似 UnknownHostException 这样的异常对象之外，函数在异常情况下还可以返回错误码、NULL 值、特殊值（比如 -1）、空对象（比如空字符串、空集合）等。

每一种异常返回数据类型，都有各自的特点和适用场景。但有的时候，在异常情况下，函数到底该返回什么样的数据类型，并不那么容易判断。比如，上节课中，在本机名获取失败的时候，ID 生成器的 generate() 函数应该返回什么呢？是异常？空字符？还是 NULL 值？又或者是其他特殊值（比如 null-15293834874-fd3A9KBn，null 表示本机名未获取到）呢？

函数是代码的一个非常重要的编写单元，而函数的异常处理，又是我们在编写函数的时候，时刻都要考虑的。所以，今天我们就聊一聊，如何设计函数在异常情况下的返回数据类型。

### 从上节课的 ID 生成器代码讲起

上两节课中，我们把一份非常简单的 ID 生成器的代码，从“能用”重构成了“好用”。最终给出的代码看似已经很完美了，但是如果我们再用心推敲一下，代码中关于出错处理的方式，还有进一步优化的空间，值得我们拿出来再讨论一下。

这段代码中有四个函数。针对这四个函数的出错处理方式，我总结出下面这样几个问题。

- 对于 generate() 函数，如果本机名获取失败，函数返回什么？这样的返回值是否合理？
- 对于 getLastFiledOfHostName() 函数，是否应该将 UnknownHostException 异常在函数内部吞掉（try-catch 并打印日志）？还是应该将异常继续往上抛出？如果往上抛出的话，是直接把 UnknownHostException 异常原封不动地抛出，还是封装成新的异常抛出？
- 对于 getLastSubstrSplittedByDot(String hostName) 函数，如果 hostName 为 NULL 或者是空字符串，这个函数应该返回什么？
- 对于 generateRandomAlphameric(int length) 函数，如果 length 小于 0 或者等于 0，这个函数应该返回什么？

### 函数出错应该返回啥？

关于函数出错返回数据类型，我总结了 4 种情况，它们分别是：错误码、NULL 值、空对象、异常对象。接下来，我们就一一来看它们的用法以及适用场景。

1. 返回错误码

C 语言中没有异常这样的语法机制，因此，返回错误码便是最常用的出错处理方式。而在 Java、Python 等比较新的编程语言中，大部分情况下，我们都用异常来处理函数出错的情况，极少会用到错误码。

在 C 语言中，错误码的返回方式有两种：一种是直接占用函数的返回值，函数正常执行的返回值放到出参中；另一种是将错误码定义为全局变量，在函数执行出错时，函数调用者通过这个全局变量来获取错误码。针对这两种方式，我举个例子来进一步解释。具体代码如下所示：

```java
// 错误码的返回方式一：pathname/flags/mode为入参；fd为出参，存储打开的文件句柄。
int open(const char *pathname, int flags, mode_t mode, int* fd) {
  if (/*文件不存在*/) {
    return EEXIST;
  }
  
  if (/*没有访问权限*/) {
    return EACCESS;
  }
  
  if (/*打开文件成功*/) {
    return SUCCESS; // C语言中的宏定义：#define SUCCESS 0
  }
  // ...
}
//使用举例
int fd;
int result = open(“c:\test.txt”, O_RDWR, S_IRWXU|S_IRWXG|S_IRWXO, &fd);
if (result == SUCCESS) {
  // 取出fd使用
} else if (result == EEXIST) {
  //...
} else if (result == EACESS) {
  //...
}

// 错误码的返回方式二：函数返回打开的文件句柄，错误码放到errno中。
int errno; // 线程安全的全局变量
int open(const char *pathname, int flags, mode_t mode）{
  if (/*文件不存在*/) {
    errno = EEXIST;
    return -1;
  }
  
  if (/*没有访问权限*/) {
    errno = EACCESS;
    return -1;
  }
  
  // ...
}
// 使用举例
int hFile = open(“c:\test.txt”, O_RDWR, S_IRWXU|S_IRWXG|S_IRWXO);
if (-1 == hFile) {
  printf("Failed to open file, error no: %d.\n", errno);
  if (errno == EEXIST ) {
    // ...        
  } else if(errno == EACCESS) {
    // ...    
  }
  // ...
}
```

实际上，如果你熟悉的编程语言中有异常这种语法机制，那就尽量不要使用错误码。异常相对于错误码，有诸多方面的优势，比如可以携带更多的错误信息（exception 中可以有 message、stack trace 等信息）等。关于异常，我们待会还会非常详细地讲解。

2. 返回 NULL 值

在多数编程语言中，我们用 NULL 来表示“不存在”这种语义。不过，网上很多人不建议函数返回 NULL 值，认为这是一种不好的设计思路，主要的理由有以下两个。

- 如果某个函数有可能返回 NULL 值，我们在使用它的时候，忘记了做 NULL 值判断，就有可能会抛出空指针异常（Null Pointer Exception，缩写为 NPE）。
- 如果我们定义了很多返回值可能为 NULL 的函数，那代码中就会充斥着大量的 NULL 值判断逻辑，一方面写起来比较繁琐，另一方面它们跟正常的业务逻辑耦合在一起，会影响代码的可读性。

```java
public class UserService {
  private UserRepo userRepo; // 依赖注入
  
  public User getUser(String telephone) {
    // 如果用户不存在，则返回null
    return null;
  }
}

// 使用函数getUser()
User user = userService.getUser("18917718965");
if (user != null) { // 做NULL值判断，否则有可能会报NPE
  String email = user.getEmail();
  if (email != null) { // 做NULL值判断，否则有可能会报NPE
    String escapedEmail = email.replaceAll("@", "#");
  }
}
```

那我们是否可以用异常来替代 NULL 值，在查找用户不存在的时候，让函数抛出 UserNotFoundException 异常呢？

我个人觉得，尽管返回 NULL 值有诸多弊端，但对于以 get、find、select、search、query 等单词开头的查找函数来说，数据不存在，并非一种异常情况，这是一种正常行为。所以，返回代表不存在语义的 NULL 值比返回异常更加合理。

不过，话说回来，刚刚讲的这个理由，也并不是特别有说服力。对于查找数据不存在的情况，函数到底是该用 NULL 值还是异常，有一个比较重要的参考标准是，看项目中的其他类似查找函数都是如何定义的，只要整个项目遵从统一的约定即可。如果项目从零开始开发，并没有统一约定和可以参考的代码，那你选择两者中的任何一种都可以。你只需要在函数定义的地方注释清楚，让调用者清晰地知道数据不存在的时候会返回什么就可以了。

再补充说明一点，对于查找函数来说，除了返回数据对象之外，有的还会返回下标位置，比如 Java 中的 indexOf() 函数，用来实现在某个字符串中查找另一个子串第一次出现的位置。函数的返回值类型为基本类型 int。这个时候，我们就无法用 NULL 值来表示不存在的情况了。对于这种情况，我们有两种处理思路，一种是返回 NotFoundException，一种是返回一个特殊值，比如 -1。不过，显然 -1 更加合理，理由也是同样的，也就是说“没有查找到”是一种正常而非异常的行为。

3. 返回空对象

刚刚我们讲到，返回 NULL 值有各种弊端。应对这个问题有一个比较经典的策略，那就是应用空对象设计模式（Null Object Design Pattern）。关于这个设计模式，我们在后面章节会详细讲，现在就不展开来讲解了。不过，我们今天来讲两种比较简单、比较特殊的空对象，那就是空字符串和空集合。

当函数返回的数据是字符串类型或者集合类型的时候，我们可以用空字符串或空集合替代 NULL 值，来表示不存在的情况。这样，我们在使用函数的时候，就可以不用做 NULL 值判断。我举个例子来解释下。具体代码如下所示：

```java
// 使用空集合替代NULL
public class UserService {
  private UserRepo userRepo; // 依赖注入
  
  public List<User> getUsers(String telephonePrefix) {
   // 没有查找到数据
    return Collections.emptyList();
  }
}
// getUsers使用示例
List<User> users = userService.getUsers("189");
for (User user : users) { //这里不需要做NULL值判断
  // ...
}

// 使用空字符串替代NULL
public String retrieveUppercaseLetters(String text) {
  // 如果text中没有大写字母，返回空字符串，而非NULL值
  return "";
}
// retrieveUppercaseLetters()使用举例
String uppercaseLetters = retrieveUppercaseLetters("wangzheng");
int length = uppercaseLetters.length();// 不需要做NULL值判断 
System.out.println("Contains " + length + " upper case letters.");
```

4. 抛出异常对象

尽管前面讲了很多函数出错的返回数据类型，但是，最常用的函数出错处理方式就是抛出异常。异常可以携带更多的错误信息，比如函数调用栈信息。除此之外，异常可以将正常逻辑和异常逻辑的处理分离开来，这样代码的可读性就会更好。

不同的编程语言的异常语法稍有不同。像 C++ 和大部分的动态语言（Python、Ruby、JavaScript 等）都只定义了一种异常类型：运行时异常（Runtime Exception）。而像 Java，除了运行时异常外，还定义了另外一种异常类型：编译时异常（Compile Exception）。

对于运行时异常，我们在编写代码的时候，可以不用主动去 try-catch，编译器在编译代码的时候，并不会检查代码是否有对运行时异常做了处理。相反，对于编译时异常，我们在编写代码的时候，需要主动去 try-catch 或者在函数定义中声明，否则编译就会报错。所以，运行时异常也叫作非受检异常（Unchecked Exception），编译时异常也叫作受检异常（Checked Exception）。

如果你熟悉的编程语言中，只定义了一种异常类型，那用起来反倒比较简单。如果你熟悉的编程语言中（比如 Java），定义了两种异常类型，那在异常出现的时候，我们应该选择抛出哪种异常类型呢？是受检异常还是非受检异常？

对于代码 bug（比如数组越界）以及不可恢复异常（比如数据库连接失败），即便我们捕获了，也做不了太多事情，所以，我们倾向于使用非受检异常。对于可恢复异常、业务异常，比如提现金额大于余额的异常，我们更倾向于使用受检异常，明确告知调用者需要捕获处理。

我举一个例子解释一下，代码如下所示。当 Redis 的地址（参数 address）没有设置的时候，我们直接使用默认的地址（比如本地地址和默认端口）；当 Redis 的地址格式不正确的时候，我们希望程序能 fail-fast，也就是说，把这种情况当成不可恢复的异常，直接抛出运行时异常，将程序终止掉。

```java
// address格式："192.131.2.33:7896"
public void parseRedisAddress(String address) {
  this.host = RedisConfig.DEFAULT_HOST;
  this.port = RedisConfig.DEFAULT_PORT;
  
  if (StringUtils.isBlank(address)) {
    return;
  }

  String[] ipAndPort = address.split(":");
  if (ipAndPort.length != 2) {
    throw new RuntimeException("...");
  }
  
  this.host = ipAndPort[0];
  // parseInt()解析失败会抛出NumberFormatException运行时异常
  this.port = Integer.parseInt(ipAndPort[1]);
}
```

实际上，Java 支持的受检异常一直被人诟病，很多人主张所有的异常情况都应该使用非受检异常。支持这种观点的理由主要有以下三个。

- 受检异常需要显式地在函数定义中声明。如果函数会抛出很多受检异常，那函数的定义就会非常冗长，这就会影响代码的可读性，使用起来也不方便。
- 编译器强制我们必须显示地捕获所有的受检异常，代码实现会比较繁琐。而非受检异常正好相反，我们不需要在定义中显示声明，并且是否需要捕获处理，也可以自由决定。
- 受检异常的使用违反开闭原则。如果我们给某个函数新增一个受检异常，这个函数所在的函数调用链上的所有位于其之上的函数都需要做相应的代码修改，直到调用链中的某个函数将这个新增的异常 try-catch 处理掉为止。而新增非受检异常可以不改动调用链上的代码。我们可以灵活地选择在某个函数中集中处理，比如在 Spring 中的 AOP 切面中集中处理异常。

不过，非受检异常也有弊端，它的优点其实也正是它的缺点。从刚刚的表述中，我们可以看出，非受检异常使用起来更加灵活，怎么处理的主动权这里就交给了程序员。我们前面也讲到，过于灵活会带来不可控，非受检异常不需要显式地在函数定义中声明，那我们在使用函数的时候，就需要查看代码才能知道具体会抛出哪些异常。非受检异常不需要强制捕获处理，那程序员就有可能漏掉一些本应该捕获处理的异常。

对于应该用受检异常还是非受检异常，网上的争论有很多，但并没有一个非常强有力的理由能够说明一个就一定比另一个更好。所以，我们只需要根据团队的开发习惯，在同一个项目中，制定统一的异常处理规范即可。

刚刚我们讲了两种异常类型，现在我们再来讲下，如何处理函数抛出的异常？总结一下，一般有下面三种处理方法。

- 直接吞掉。具体的代码示例如下所示：

```java
public void func1() throws Exception1 {
  // ...
}

public void func2() {
  //...
  try {
    func1();
  } catch(Exception1 e) {
    log.warn("...", e); //吐掉：try-catch打印日志
  }
  //...
}
```

- 原封不动地 re-throw。具体的代码示例如下所示：

```java
public void func1() throws Exception1 {
  // ...
}


public void func2() throws Exception1 {//原封不动的re-throw Exception1
  //...
  func1();
  //...
}
```

- 包装成新的异常 re-throw。具体的代码示例如下所示：

```java
public void func1() throws Exception1 {
  // ...
}


public void func2() throws Exception2 {
  //...
  try {
    func1();
  } catch(Exception1 e) {
   throw new Exception2("...", e); // wrap成新的Exception2然后re-throw
  }
  //...
}
```

当我们面对函数抛出异常的时候，应该选择上面的哪种处理方式呢？我总结了下面三个参考原则：

- 如果 func1() 抛出的异常是可以恢复，且 func2() 的调用方**并不关心此异常**，我们完全可以在 func2() 内将 func1() 抛出的异常吞掉；
- 如果 func1() 抛出的异常对 func2() 的调用方来说，也是**可以理解的、关心的** ，并且在业务概念上有一定的相关性，我们可以选择直接将 func1 抛出的异常 re-throw；
- 如果 func1() 抛出的异常太底层，对 func2() 的调用方来说，**缺乏背景去理解、且业务概念上无关**，我们可以将它重新包装成调用方可以理解的新异常，然后 re-throw。

对于函数出错返回数据类型，我总结了 4 种情况，它们分别是：错误码、NULL 值、空对象、异常对象。

1. 返回错误码

C 语言没有异常这样的语法机制，返回错误码便是最常用的出错处理方式。而 Java、Python 等比较新的编程语言中，大部分情况下，我们都用异常来处理函数出错的情况，极少会用到错误码。

2. 返回 NULL 值

在多数编程语言中，我们用 NULL 来表示“不存在”这种语义。对于查找函数来说，数据不存在并非一种异常情况，是一种正常行为，所以返回表示不存在语义的 NULL 值比返回异常更加合理。

3. 返回空对象

返回 NULL 值有各种弊端，对此有一个比较经典的应对策略，那就是应用空对象设计模式。当函数返回的数据是字符串类型或者集合类型的时候，我们可以用空字符串或空集合替代 NULL 值，来表示不存在的情况。这样，我们在使用函数的时候，就可以不用做 NULL 值判断。

4. 抛出异常对象

尽管前面讲了很多函数出错的返回数据类型，但是，最常用的函数出错处理方式是抛出异常。异常有两种类型：受检异常和非受检异常。

对于应该用受检异常还是非受检异常，网上的争论有很多，但也并没有一个非常强有力的理由，说明一个就一定比另一个更好。所以，我们只需要根据团队的开发习惯，在同一个项目中，制定统一的异常处理规范即可。

对于函数抛出的异常，我们有三种处理方法：直接吞掉、直接往上抛出、包裹成新的异常抛出。

## 37 | 实战二（下）：重构ID生成器项目中各函数的异常处理代码

### 重构 generate() 函数

首先，我们来看，对于 generate() 函数，如果本机名获取失败，函数返回什么？这样的返回值是否合理？

```java
public String generate() {
    String substrOfHostName = getLastFieldOfHostName();
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s",
            substrOfHostName, currentTimeMillis, randomString);
    return id;
}
```

ID 由三部分构成：本机名、时间戳和随机数。时间戳和随机数的生成函数不会出错，唯独主机名有可能获取失败。在目前的代码实现中，如果主机名获取失败，substrOfHostName 为 NULL，那 generate() 函数会返回类似“null-16723733647-83Ab3uK6”这样的数据。如果主机名获取失败，substrOfHostName 为空字符串，那 generate() 函数会返回类似“-16723733647-83Ab3uK6”这样的数据。

在异常情况下，返回上面两种特殊的 ID 数据格式，这样的做法是否合理呢？这个其实很难讲，我们要看具体的业务是怎么设计的。不过，我更倾向于明确地将异常告知调用者。所以，这里最好是抛出受检异常，而非特殊值。

按照这个设计思路，我们对 generate() 函数进行重构。重构之后的代码如下所示：

```java
public String generate() throws IdGenerationFailureException {
    String substrOfHostName = getLastFieldOfHostName();
    if (substrOfHostName == null || substrOfHostName.isEmpty()) {
      throw new IdGenerationFailureException("host name is empty.");
    }
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s",
            substrOfHostName, currentTimeMillis, randomString);
    return id;
  }
```

### 重构 getLastFieldOfHostName() 函数

对于 getLastFieldOfHostName() 函数，是否应该将 UnknownHostException 异常在函数内部吞掉（try-catch 并打印日志），还是应该将异常继续往上抛出？如果往上抛出的话，是直接把 UnknownHostException 异常原封不动地抛出，还是封装成新的异常抛出？

```java
private String getLastFieldOfHostName() {
    String substrOfHostName = null;
    try {
      String hostName = InetAddress.getLocalHost().getHostName();
      substrOfHostName = getLastSubstrSplittedByDot(hostName);
    } catch (UnknownHostException e) {
      logger.warn("Failed to get the host name.", e);
    }
    return substrOfHostName;
 }
```

现在的处理方式是当主机名获取失败的时候，getLastFieldOfHostName() 函数返回 NULL 值。我们前面讲过，是返回 NULL 值还是异常对象，要看获取不到数据是正常行为，还是异常行为。获取主机名失败会影响后续逻辑的处理，并不是我们期望的，所以，它是一种异常行为。这里最好是抛出异常，而非返回 NULL 值。

至于是直接将 UnknownHostException 抛出，还是重新封装成新的异常抛出，要看函数跟异常是否有业务相关性。getLastFieldOfHostName() 函数用来获取主机名的最后一个字段，UnknownHostException 异常表示主机名获取失败，两者算是业务相关，所以可以直接将 UnknownHostException 抛出，不需要重新包裹成新的异常。

按照上面的设计思路，我们对 getLastFieldOfHostName() 函数进行重构。重构后的代码如下所示：

```java
private String getLastFieldOfHostName() throws UnknownHostException{
    String substrOfHostName = null;
    String hostName = InetAddress.getLocalHost().getHostName();
    substrOfHostName = getLastSubstrSplittedByDot(hostName);
    return substrOfHostName;
 }
```

getLastFieldOfHostName() 函数修改之后，generate() 函数也要做相应的修改。我们需要在 generate() 函数中，捕获 getLastFieldOfHostName() 抛出的 UnknownHostException 异常。当我们捕获到这个异常之后，应该怎么处理呢？

按照之前的分析，ID 生成失败的时候，我们需要明确地告知调用者。所以，我们不能在 generate() 函数中，将 UnknownHostException 这个异常吞掉。那我们应该原封不动地抛出，还是封装成新的异常抛出呢？

我们选择后者。在 generate() 函数中，我们需要捕获 UnknownHostException 异常，并重新包裹成新的异常 IdGenerationFailureException 往上抛出。之所以这么做，有下面三个原因。

- 调用者在使用 generate() 函数的时候，只需要知道它生成的是随机唯一 ID，并不关心 ID 是如何生成的。也就说是，这是依赖抽象而非实现编程。如果 generate() 函数直接抛出 UnknownHostException 异常，实际上是暴露了实现细节。
- 从代码封装的角度来讲，我们不希望将 UnknownHostException 这个比较底层的异常，暴露给更上层的代码，也就是调用 generate() 函数的代码。而且，调用者拿到这个异常的时候，并不能理解这个异常到底代表了什么，也不知道该如何处理。
- UnknownHostException 异常跟 generate() 函数，在业务概念上没有相关性。

按照上面的设计思路，我们对 generate() 的函数再次进行重构。重构后的代码如下所示：

```java
public String generate() throws IdGenerationFailureException {
    String substrOfHostName = null;
    try {
      substrOfHostName = getLastFieldOfHostName();
    } catch (UnknownHostException e) {
      throw new IdGenerationFailureException("host name is empty.");
    }
    long currentTimeMillis = System.currentTimeMillis();
    String randomString = generateRandomAlphameric(8);
    String id = String.format("%s-%d-%s",
            substrOfHostName, currentTimeMillis, randomString);
    return id;
  }
```

#### 重构 getLastSubstrSplittedByDot() 函数

对于 getLastSubstrSplittedByDot(String hostName) 函数，如果 hostName 为 NULL 或者空字符串，这个函数应该返回什么？

```
  @VisibleForTesting
  protected String getLastSubstrSplittedByDot(String hostName) {
    String[] tokens = hostName.split("\\.");
    String substrOfHostName = tokens[tokens.length - 1];
    return substrOfHostName;
  }
```

理论上讲，参数传递的正确性应该有程序员来保证，我们无需做 NULL 值或者空字符串的判断和特殊处理。调用者本不应该把 NULL 值或者空字符串传递给 getLastSubstrSplittedByDot() 函数。如果传递了，那就是 code bug，需要修复。但是，话说回来，谁也保证不了程序员就一定不会传递 NULL 值或者空字符串。那我们到底该不该做 NULL 值或空字符串的判断呢？

如果函数是 private 类私有的，只在类内部被调用，完全在你自己的掌控之下，自己保证在调用这个 private 函数的时候，不要传递 NULL 值或空字符串就可以了。所以，我们可以不在 private 函数中做 NULL 值或空字符串的判断。如果函数是 public 的，你无法掌控会被谁调用以及如何调用（有可能某个同事一时疏忽，传递进了 NULL 值，这种情况也是存在的），为了尽可能提高代码的健壮性，我们最好是在 public 函数中做 NULL 值或空字符串的判断。

那你可能会说，getLastSubstrSplittedByDot() 是 protected 的，既不是 private 函数，也不是 public 函数，那要不要做 NULL 值或空字符串的判断呢？

之所以将它设置为 protected，是为了方便写单元测试。不过，单元测试可能要测试一些 corner case，比如输入是 NULL 值或者空字符串的情况。所以，这里我们最好也加上 NULL 值或空字符串的判断逻辑。虽然加上有些冗余，但多加些检验总归不会错的。

按照这个设计思路，我们对 getLastSubstrSplittedByDot() 函数进行重构。重构之后的代码如下所示：

```
@VisibleForTesting
  protected String getLastSubstrSplittedByDot(String hostName) {
    if (hostName == null || hostName.isEmpty()) {
      throw IllegalArgumentException("..."); //运行时异常
    }
    String[] tokens = hostName.split("\\.");
    String substrOfHostName = tokens[tokens.length - 1];
    return substrOfHostName;
  }
```

按照上面讲的，我们在使用这个函数的时候，自己也要保证不传递 NULL 值或者空字符串进去。所以，getLastFieldOfHostName() 函数的代码也要作相应的修改。修改之后的代码如下所示：

```
private String getLastFieldOfHostName() throws UnknownHostException{
    String substrOfHostName = null;
    String hostName = InetAddress.getLocalHost().getHostName();
    if (hostName == null || hostName.isEmpty()) { // 此处做判断
      throw new UnknownHostException("...");
    }
    substrOfHostName = getLastSubstrSplittedByDot(hostName);
    return substrOfHostName;
 }
```

#### 重构 generateRandomAlphameric() 函数

对于 generateRandomAlphameric(int length) 函数，如果 length < 0 或 length = 0，这个函数应该返回什么？

```
@VisibleForTesting
  protected String generateRandomAlphameric(int length) {
    char[] randomChars = new char[length];
    int count = 0;
    Random random = new Random();
    while (count < length) {
      int maxAscii = 'z';
      int randomAscii = random.nextInt(maxAscii);
      boolean isDigit= randomAscii >= '0' && randomAscii <= '9';
      boolean isUppercase= randomAscii >= 'A' && randomAscii <= 'Z';
      boolean isLowercase= randomAscii >= 'a' && randomAscii <= 'z';
      if (isDigit|| isUppercase || isLowercase) {
        randomChars[count] = (char) (randomAscii);
        ++count;
      }
    }
    return new String(randomChars);
  }
}
```

我们先来看 length < 0 的情况。生成一个长度为负值的随机字符串是不符合常规逻辑的，是一种异常行为。所以，当传入的参数 length < 0 的时候，我们抛出 IllegalArgumentException 异常。

我们再来看 length = 0 的情况。length = 0 是否是异常行为呢？这就看你自己怎么定义了。我们既可以把它定义为一种异常行为，抛出 IllegalArgumentException 异常，也可以把它定义为一种正常行为，让函数在入参 length = 0 的情况下，直接返回空字符串。不管选择哪种处理方式，最关键的一点是，要在函数注释中，明确告知 length = 0 的情况下，会返回什么样的数据。

# 设计原则与思想：总结篇

## 38 | 总结回顾面向对象、设计原则、编程规范、重构技巧等知识点

到今天为止，设计原则和思想已经全部讲完了，其中包括：面向对象、设计原则、规范与重构三个模块的内容。除此之外，我们还学习了贯穿整个专栏的代码质量评判标准。专栏的进度已经接近一半，马上就要进入设计模式内容的学习了。在此之前，我先带你一块来总结回顾一下，我们已经学过的所有知识点。今天的内容比较多，有一万多字，但都是之前学过的，算是一个总结回顾，主要是想带你复习一下，温故而知新。如果你看了之后，感觉都有印象，那就说明学得还不错；如果还能在脑子里形成自己的知识架构，闭上眼睛都能回忆上来，那说明你学得很好；如果能有自己的理解，并且在项目开发中，开始思考代码质量问题，开始用已经学过的设计原则和思想来审视代码，那说明你已经掌握这些内容的精髓。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210409213624.png)

一、代码质量评判标准

- 如何评价代码质量的高低？

代码质量的评价有很强的主观性，描述代码质量的词汇也有很多，比如可读性、可维护性、灵活、优雅、简洁。这些词汇是从不同的维度去评价代码质量的。它们之间有互相作用，并不是独立的，比如，代码的可读性好、可扩展性好就意味着代码的可维护性好。代码质量高低是一个综合各种因素得到的结论。我们并不能通过单一维度去评价一段代码的好坏。

- 最常用的评价标准有哪几个？

最常用到几个评判代码质量的标准有：可维护性、可读性、可扩展性、灵活性、简洁性、可复用性、可测试性。其中，可维护性、可读性、可扩展性又是提到最多的、最重要的三个评价标准。

- 如何才能写出高质量的代码？

要写出高质量代码，我们就需要掌握一些更加细化、更加能落地的编程方法论，这就包含面向对象设计思想、设计原则、设计模式、编码规范、重构技巧等。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210409213747.jpg)

二、面向对象

1. 面向对象概述

现在，主流的编程范式或者编程风格有三种，它们分别是面向过程、面向对象和函数式编程。面向对象这种编程风格又是这其中最主流的。现在比较流行的编程语言大部分都是面向对象编程语言。大部分项目也都是基于面向对象编程风格开发的。面向对象编程因为其具有丰富的特性（封装、抽象、继承、多态），可以实现很多复杂的设计思路，是很多设计原则、设计模式编码实现的基础。

# 设计模式与范式：创建型 (7讲)

## 41 | 单例模式（上）：为什么说支持懒加载的双重检测不比饿汉式更优？

### 为什么要使用单例？

单例设计模式（Singleton Design Pattern）理解起来非常简单。一个类只允许创建一个对象（或者实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。

1. 处理资源访问冲突

我们先来看第一个例子。在这个例子中，我们自定义实现了一个往文件中打印日志的 Logger 类。具体的代码实现如下所示：

```java
public class Logger {
  private FileWriter writer;
  
  public Logger() {
    File file = new File("/Users/wangzheng/log.txt");
    writer = new FileWriter(file, true); //true表示追加写入
  }
  
  public void log(String message) {
    writer.write(mesasge);
  }
}

// Logger类的应用示例：
public class UserController {
  private Logger logger = new Logger();
  
  public void login(String username, String password) {
    // ...省略业务逻辑代码...
    logger.log(username + " logined!");
  }
}

public class OrderController {
  private Logger logger = new Logger();
  
  public void create(OrderVo order) {
    // ...省略业务逻辑代码...
    logger.log("Created an order: " + order.toString());
  }
}
```

在上面的代码中，我们注意到，所有的日志都写入到同一个文件 /Users/wangzheng/log.txt 中。在 UserController 和 OrderController 中，我们分别创建两个 Logger 对象。在 Web 容器的 Servlet 多线程环境下，如果两个 Servlet 线程同时分别执行 login() 和 create() 两个函数，并且同时写日志到 log.txt 文件中，那就有可能存在日志信息互相覆盖的情况。

为什么会出现互相覆盖呢？我们可以这么类比着理解。在多线程环境下，如果两个线程同时给同一个共享变量加 1，因为共享变量是竞争资源，所以，共享变量最后的结果有可能并不是加了 2，而是只加了 1。同理，这里的 log.txt 文件也是竞争资源，两个线程同时往里面写数据，就有可能存在互相覆盖的情况。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201124200614.jpg)

那如何来解决这个问题呢？我们最先想到的就是通过加锁的方式：给 log() 函数加互斥锁（Java 中可以通过 synchronized 的关键字），同一时刻只允许一个线程调用执行 log() 函数。具体的代码实现如下所示：

```java
public class Logger {
  private FileWriter writer;

  public Logger() {
    File file = new File("/Users/wangzheng/log.txt");
    writer = new FileWriter(file, true); //true表示追加写入
  }
  
  public void log(String message) {
    synchronized(this) {
      writer.write(mesasge);
    }
  }
}
```

不过，你仔细想想，这真的能解决多线程写入日志时互相覆盖的问题吗？答案是否定的。这是因为，这种锁是一个对象级别的锁，一个对象在不同的线程下同时调用 log() 函数，会被强制要求顺序执行。但是，不同的对象之间并不共享同一把锁。在不同的线程下，通过不同的对象调用执行 log() 函数，锁并不会起作用，仍然有可能存在写入日志互相覆盖的问题。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20201124200818.jpg)

我这里稍微补充一下，在刚刚的讲解和给出的代码中，我故意“隐瞒”了一个事实：我们给 log() 函数加不加对象级别的锁，其实都没有关系。因为 FileWriter 本身就是线程安全的，它的内部实现中本身就加了对象级别的锁，因此，在外层调用 write() 函数的时候，再加对象级别的锁实际上是多此一举。因为不同的 Logger 对象不共享 FileWriter 对象，所以，FileWriter 对象级别的锁也解决不了数据写入互相覆盖的问题。

那我们该怎么解决这个问题呢？实际上，要想解决这个问题也不难，我们只需要把对象级别的锁，换成类级别的锁就可以了。让所有的对象都共享同一把锁。这样就避免了不同对象之间同时调用 log() 函数，而导致的日志覆盖问题。具体的代码实现如下所示：

```
public class Logger {
  private FileWriter writer;

  public Logger() {
    File file = new File("/Users/wangzheng/log.txt");
    writer = new FileWriter(file, true); //true表示追加写入
  }
  
  public void log(String message) {
    synchronized(Logger.class) { // 类级别的锁
      writer.write(mesasge);
    }
  }
}
```

除了使用类级别锁之外，实际上，解决资源竞争问题的办法还有很多，分布式锁是最常听到的一种解决方案。不过，实现一个安全可靠、无 bug、高性能的分布式锁，并不是件容易的事情。除此之外，并发队列（比如 Java 中的 BlockingQueue）也可以解决这个问题：多个线程同时往并发队列里写日志，一个单独的线程负责将并发队列中的数据，写入到日志文件。这种方式实现起来也稍微有点复杂。

相对于这两种解决方案，单例模式的解决思路就简单一些了。单例模式相对于之前类级别锁的好处是，不用创建那么多 Logger 对象，一方面节省内存空间，另一方面节省系统文件句柄（对于操作系统来说，文件句柄也是一种资源，不能随便浪费）。

我们将 Logger 设计成一个单例类，程序中只允许创建一个 Logger 对象，所有的线程共享使用的这一个 Logger 对象，共享一个 FileWriter 对象，而 FileWriter 本身是对象级别线程安全的，也就避免了多线程情况下写日志会互相覆盖的问题。

```
public class Logger {
  private FileWriter writer;
  private static final Logger instance = new Logger();

  private Logger() {
    File file = new File("/Users/wangzheng/log.txt");
    writer = new FileWriter(file, true); //true表示追加写入
  }
  
  public static Logger getInstance() {
    return instance;
  }
  
  public void log(String message) {
    writer.write(mesasge);
  }
}

// Logger类的应用示例：
public class UserController {
  public void login(String username, String password) {
    // ...省略业务逻辑代码...
    Logger.getInstance().log(username + " logined!");
  }
}

public class OrderController {  
  public void create(OrderVo order) {
    // ...省略业务逻辑代码...
    Logger.getInstance().log("Created a order: " + order.toString());
  }
}
```

2. 表示全局唯一类

从业务概念上，如果有些数据在系统中只应保存一份，那就比较适合设计为单例类。

比如，配置信息类。在系统中，我们只有一个配置文件，当配置文件被加载到内存之后，以对象的形式存在，也理所应当只有一份。再比如，唯一递增 ID 号码生成器（第 34 讲中我们讲的是唯一 ID 生成器，这里讲的是唯一递增 ID 生成器），如果程序中有两个对象，那就会存在生成重复 ID 的情况，所以，我们应该将 ID 生成器类设计为单例。

```java
import java.util.concurrent.atomic.AtomicLong;
public class IdGenerator {
  // AtomicLong是一个Java并发库中提供的一个原子变量类型,
  // 它将一些线程不安全需要加锁的复合操作封装为了线程安全的原子操作，
  // 比如下面会用到的incrementAndGet().
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance = new IdGenerator();
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}

// IdGenerator使用举例
long id = IdGenerator.getInstance().getId();
```

### 如何实现一个单例？

要实现一个单例，我们需要关注的点无外乎下面几个：

- 构造函数需要是 private 访问权限的，这样才能避免外部通过 new 创建实例；
- 考虑对象创建时的线程安全问题；
- 考虑是否支持延迟加载；
- 考虑 getInstance() 性能是否高（是否加锁）。

1. 饿汉式

饿汉式的实现方式比较简单。在类加载的时候，instance 静态实例就已经创建并初始化好了，所以，instance 实例的创建过程是线程安全的。不过，这样的实现方式不支持延迟加载（在真正用到 IdGenerator 的时候，再创建实例），从名字中我们也可以看出这一点。具体的代码实现如下所示：

```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static final IdGenerator instance = new IdGenerator();
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

有人觉得这种实现方式不好，因为不支持延迟加载，如果实例占用资源多（比如占用内存多）或初始化耗时长（比如需要加载各种配置文件），提前初始化实例是一种浪费资源的行为。最好的方法应该在用到的时候再去初始化。不过，我个人并不认同这样的观点。

如果初始化耗时长，那我们最好不要等到真正要用它的时候，才去执行这个耗时长的初始化过程，这会影响到系统的性能（比如，在响应客户端接口请求的时候，做这个初始化操作，会导致此请求的响应时间变长，甚至超时）。采用饿汉式实现方式，将耗时的初始化操作，提前到程序启动的时候完成，这样就能避免在程序运行的时候，再去初始化导致的性能问题。

如果实例占用资源多，按照 fail-fast 的设计原则（有问题及早暴露），那我们也希望在程序启动时就将这个实例初始化好。如果资源不够，就会在程序启动的时候触发报错（比如 Java 中的 PermGen Space OOM），我们可以立即去修复。这样也能避免在程序运行一段时间后，突然因为初始化这个实例占用资源过多，导致系统崩溃，影响系统的可用性。

2. 懒汉式

```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static IdGenerator instance;
  private IdGenerator() {}
  public static synchronized IdGenerator getInstance() {
    if (instance == null) {
      instance = new IdGenerator();
    }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

不过懒汉式的缺点也很明显，我们给 getInstance() 这个方法加了一把大锁（synchronzed），导致这个函数的并发度很低。量化一下的话，并发度是 1，也就相当于串行操作了。而这个函数是在单例使用期间，一直会被调用。如果这个单例类偶尔会被用到，那这种实现方式还可以接受。但是，如果频繁地用到，那频繁加锁、释放锁及并发度低等问题，会导致性能瓶颈，这种实现方式就不可取了。

3. 双重检测

饿汉式不支持延迟加载，懒汉式有性能问题，不支持高并发。那我们再来看一种既支持延迟加载、又支持高并发的单例实现方式，也就是双重检测实现方式。

在这种实现方式中，只要 instance 被创建之后，即便再调用 getInstance() 函数也不会再进入到加锁逻辑中了。所以，这种实现方式解决了懒汉式并发度低的问题。具体的代码实现如下所示：

```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private static volatile IdGenerator instance;
  private IdGenerator() {}
  public static IdGenerator getInstance() {
    if (instance == null) {
      synchronized(IdGenerator.class) { // 此处为类级别的锁
        if (instance == null) {
          instance = new IdGenerator();
        }
      }
    }
    return instance;
  }
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

网上有人说，这种实现方式有些问题。因为指令重排序，可能会导致 IdGenerator 对象被 new 出来，并且赋值给 instance 之后，还没来得及初始化（执行构造函数中的代码逻辑），就被另一个线程使用了。要解决这个问题，我们需要给 instance 成员变量加上 volatile 关键字，禁止指令重排序才行。实际上，只有很低版本的 Java 才会有这个问题。我们现在用的高版本的 Java 已经在 JDK 内部实现中解决了这个问题（解决的方法很简单，只要把对象 new 操作和初始化操作设计为原子操作，就自然能禁止重排序）。

4. 静态内部类

```java
public class IdGenerator { 
  private AtomicLong id = new AtomicLong(0);
  private IdGenerator() {}

  private static class SingletonHolder{
    private static final IdGenerator instance = new IdGenerator();
  }
  
  public static IdGenerator getInstance() {
    return SingletonHolder.instance;
  }
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

SingletonHolder 是一个静态内部类，当外部类 IdGenerator 被加载的时候，并不会创建 SingletonHolder 实例对象。只有当调用 getInstance() 方法时，SingletonHolder 才会被加载，这个时候才会创建 instance。**instance 的唯一性、创建过程的线程安全性，都由 JVM 来保证**。所以，这种实现方法既保证了线程安全，又能做到延迟加载。

5. 枚举

```java
public enum IdGenerator {
  INSTANCE;
  private AtomicLong id = new AtomicLong(0);
 
  public long getId() { 
    return id.incrementAndGet();
  }
}
```

## 42 | 单例模式（中）：我为什么不推荐使用单例模式？又有何替代方案？

### 单例存在哪些问题?

大部分情况下，我们在项目中使用单例，都是用它来表示一些全局唯一类，比如配置信息类、连接池类、ID 生成器类。单例模式书写简洁、使用方便，在代码中，我们不需要创建对象，直接通过类似 IdGenerator.getInstance().getId() 这样的方法来调用就可以了。但是，这种使用方法有点类似硬编码（hard code），会带来诸多问题。

1. 单例对 OOP 特性的支持不友好

我们知道，OOP 的四大特性是封装、抽象、继承、多态。单例这种设计模式对于其中的抽象、继承、多态都支持得不好。为什么这么说呢？我们还是通过 IdGenerator 这个例子来讲解。

```java
public class Order {
  public void create(...) {
    //...
    long id = IdGenerator.getInstance().getId();
    //...
  }
}

public class User {
  public void create(...) {
    // ...
    long id = IdGenerator.getInstance().getId();
    //...
  }
}
```

IdGenerator 的使用方式违背了基于接口而非实现的设计原则，也就违背了广义上理解的 OOP 的抽象特性。如果未来某一天，我们希望针对不同的业务采用不同的 ID 生成算法。比如，订单 ID 和用户 ID 采用不同的 ID 生成器来生成。为了应对这个需求变化，我们需要修改所有用到 IdGenerator 类的地方，这样代码的改动就会比较大。

```java
public class Order {
  public void create(...) {
    //...
    long id = IdGenerator.getInstance().getId();
    // 需要将上面一行代码，替换为下面一行代码
    long id = OrderIdGenerator.getIntance().getId();
    //...
  }
}

public class User {
  public void create(...) {
    // ...
    long id = IdGenerator.getInstance().getId();
    // 需要将上面一行代码，替换为下面一行代码
    long id = UserIdGenerator.getIntance().getId();
  }
}
```

除此之外，单例对继承、多态特性的支持也不友好。这里我之所以会用“不友好”这个词，而非“完全不支持”，是因为从理论上来讲，单例类也可以被继承、也可以实现多态，只是实现起来会非常奇怪，会导致代码的可读性变差。不明白设计意图的人，看到这样的设计，会觉得莫名其妙。所以，一旦你选择将某个类设计成到单例类，也就意味着放弃了继承和多态这两个强有力的面向对象特性，也就相当于损失了可以应对未来需求变化的扩展性。

2. 单例会隐藏类之间的依赖关系

我们知道，代码的可读性非常重要。在阅读代码的时候，我们希望一眼就能看出类与类之间的依赖关系，搞清楚这个类依赖了哪些外部类。通过构造函数、参数传递等方式声明的类之间的依赖关系，我们通过查看函数的定义，就能很容易识别出来。但是，单例类不需要显示创建、不需要依赖参数传递，在函数中直接调用就可以了。如果代码比较复杂，这种调用关系就会非常隐蔽。在阅读代码的时候，我们就需要仔细查看每个函数的代码实现，才能知道这个类到底依赖了哪些单例类。

3. 单例对代码的扩展性不友好

我们知道，单例类只能有一个对象实例。如果未来某一天，我们需要在代码中创建两个实例或多个实例，那就要对代码有比较大的改动。你可能会说，会有这样的需求吗？既然单例类大部分情况下都用来表示全局类，怎么会需要两个或者多个实例呢？

实际上，这样的需求并不少见。我们拿数据库连接池来举例解释一下。在系统设计初期，我们觉得系统中只应该有一个数据库连接池，这样能方便我们控制对数据库连接资源的消耗。所以，我们把数据库连接池类设计成了单例类。但之后我们发现，系统中有些 SQL 语句运行得非常慢。这些 SQL 语句在执行的时候，长时间占用数据库连接资源，导致其他 SQL 请求无法响应。为了解决这个问题，我们希望将慢 SQL 与其他 SQL 隔离开来执行。为了实现这样的目的，我们可以在系统中创建两个数据库连接池，慢 SQL 独享一个数据库连接池，其他 SQL 独享另外一个数据库连接池，这样就能避免慢 SQL 影响到其他 SQL 的执行。

如果我们将数据库连接池设计成单例类，显然就无法适应这样的需求变更，也就是说，单例类在某些情况下会影响代码的扩展性、灵活性。所以，数据库连接池、线程池这类的资源池，最好还是不要设计成单例类。实际上，一些开源的数据库连接池、线程池也确实没有设计成单例类。

4. 单例对代码的可测试性不友好

单例模式的使用会影响到代码的可测试性。如果单例类依赖比较重的外部资源，比如 DB，我们在写单元测试的时候，希望能通过 mock 的方式将它替换掉。而单例类这种硬编码式的使用方式，导致无法实现 mock 替换。

除此之外，如果单例类持有成员变量（比如 IdGenerator 中的 id 成员变量），那它实际上相当于一种全局变量，被所有的代码共享。如果这个全局变量是一个可变全局变量，也就是说，它的成员变量是可以被修改的，那我们在编写单元测试的时候，还需要注意不同测试用例之间，修改了单例类中的同一个成员变量的值，从而导致测试结果互相影响的问题。关于这一点，你可以回过头去看下第 29 讲中的“其他常见的 Anti-Patterns：全局变量”那部分的代码示例和讲解。

5. 单例不支持有参数的构造函数

单例不支持有参数的构造函数，比如我们创建一个连接池的单例对象，我们没法通过参数来指定连接池的大小。针对这个问题，我们来看下都有哪些解决方案。

第一种解决思路是：创建完实例之后，再调用 init() 函数传递参数。需要注意的是，我们在使用这个单例类的时候，要先调用 init() 方法，然后才能调用 getInstance() 方法，否则代码会抛出异常。具体的代码实现如下所示：

```
public class Singleton {
  private static Singleton instance = null;
  private final int paramA;
  private final int paramB;

  private Singleton(int paramA, int paramB) {
    this.paramA = paramA;
    this.paramB = paramB;
  }

  public static Singleton getInstance() {
    if (instance == null) {
       throw new RuntimeException("Run init() first.");
    }
    return instance;
  }

  public synchronized static Singleton init(int paramA, int paramB) {
    if (instance != null){
       throw new RuntimeException("Singleton has been created!");
    }
    instance = new Singleton(paramA, paramB);
    return instance;
  }
}

Singleton.init(10, 50); // 先init，再使用
Singleton singleton = Singleton.getInstance();
```

第二种解决思路是：将参数放到 getIntance() 方法中。具体的代码实现如下所示：

```
public class Singleton {
  private static Singleton instance = null;
  private final int paramA;
  private final int paramB;

  private Singleton(int paramA, int paramB) {
    this.paramA = paramA;
    this.paramB = paramB;
  }

  public synchronized static Singleton getInstance(int paramA, int paramB) {
    if (instance == null) {
      instance = new Singleton(paramA, paramB);
    }
    return instance;
  }
}

Singleton singleton = Singleton.getInstance(10, 50);
```

不知道你有没有发现，上面的代码实现稍微有点问题。如果我们如下两次执行 getInstance() 方法，那获取到的 singleton1 和 signleton2 的 paramA 和 paramB 都是 10 和 50。也就是说，第二次的参数（20，30）没有起作用，而构建的过程也没有给与提示，这样就会误导用户。

```
Singleton singleton1 = Singleton.getInstance(10, 50);
Singleton singleton2 = Singleton.getInstance(20, 30);
```

第三种解决思路是：将参数放到另外一个全局变量中。具体的代码实现如下。Config 是一个存储了 paramA 和 paramB 值的全局变量。里面的值既可以像下面的代码那样通过静态常量来定义，也可以从配置文件中加载得到。实际上，这种方式是最值得推荐的。

```
public class Config {
  public static final int PARAM_A = 123;
  public static final int PARAM_B = 245;
}

public class Singleton {
  private static Singleton instance = null;
  private final int paramA;
  private final int paramB;

  private Singleton() {
    this.paramA = Config.PARAM_A;
    this.paramB = Config.PARAM_B;
  }

  public synchronized static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```

### 有何替代解决方案？

为了保证全局唯一，除了使用单例，我们还可以用静态方法来实现。这也是项目开发中经常用到的一种实现思路。比如，上一节课中讲的 ID 唯一递增生成器的例子，用静态方法实现一下，就是下面这个样子：

```
// 静态方法实现方式
public class IdGenerator {
  private static AtomicLong id = new AtomicLong(0);
  
  public static long getId() { 
    return id.incrementAndGet();
  }
}
// 使用举例
long id = IdGenerator.getId();
```

不过，静态方法这种实现思路，并不能解决我们之前提到的问题。实际上，它比单例更加不灵活，比如，它无法支持延迟加载。我们再来看看有没有其他办法。实际上，单例除了我们之前讲到的使用方法之外，还有另外一种使用方法。具体的代码如下所示：

```
// 1. 老的使用方式
public demofunction() {
  //...
  long id = IdGenerator.getInstance().getId();
  //...
}

// 2. 新的使用方式：依赖注入
public demofunction(IdGenerator idGenerator) {
  long id = idGenerator.getId();
}
// 外部调用demofunction()的时候，传入idGenerator
IdGenerator idGenerator = IdGenerator.getInsance();
demofunction(idGenerator);
```

基于新的使用方式，我们将单例生成的对象，作为参数传递给函数（也可以通过构造函数传递给类的成员变量），可以解决单例隐藏类之间依赖关系的问题。不过，对于单例存在的其他问题，比如对 OOP 特性、扩展性、可测性不友好等问题，还是无法解决。

所以，如果要完全解决这些问题，我们可能要从根上，寻找其他方式来实现全局唯一类。实际上，类对象的全局唯一性可以通过多种不同的方式来保证。我们既可以通过单例模式来强制保证，也可以通过工厂模式、IOC 容器（比如 Spring IOC 容器）来保证，还可以通过程序员自己来保证（自己在编写代码的时候自己保证不要创建两个类对象）。这就类似 Java 中内存对象的释放由 JVM 来负责，而 C++ 中由程序员自己负责，道理是一样的。

## 43 | 单例模式（下）：如何设计实现一个集群环境下的分布式单例模式？

### 如何理解单例模式中的唯一性？

首先，我们重新看一下单例的定义：“一个类只允许创建唯一一个对象（或者实例），那这个类就是一个单例类，这种设计模式就叫作单例设计模式，简称单例模式。

”定义中提到，“一个类只允许创建唯一一个对象”。那对象的唯一性的作用范围是什么呢？是指线程内只允许创建一个对象，还是指进程内只允许创建一个对象？答案是后者，也就是说，单例模式创建的对象是进程唯一的。这里有点不好理解，我来详细地解释一下。

我们编写的代码，通过编译、链接，组织在一起，就构成了一个操作系统可以执行的文件，也就是我们平时所说的“可执行文件”（比如 Windows 下的 exe 文件）。可执行文件实际上就是代码被翻译成操作系统可理解的一组指令，你完全可以简单地理解为就是代码本身。

当我们使用命令行或者双击运行这个可执行文件的时候，操作系统会启动一个进程，将这个执行文件从磁盘加载到自己的进程地址空间（可以理解操作系统为进程分配的内存存储区，用来存储代码和数据）。接着，进程就一条一条地执行可执行文件中包含的代码。比如，当进程读到代码中的 User user = new User(); 这条语句的时候，它就在自己的地址空间中创建一个 user 临时变量和一个 User 对象。

进程之间是不共享地址空间的，如果我们在一个进程中创建另外一个进程（比如，代码中有一个 fork() 语句，进程执行到这条语句的时候会创建一个新的进程），操作系统会给新进程分配新的地址空间，并且将老进程地址空间的所有内容，重新拷贝一份到新进程的地址空间中，这些内容包括代码、数据（比如 user 临时变量、User 对象）。

所以，单例类在老进程中存在且只能存在一个对象，在新进程中也会存在且只能存在一个对象。而且，这两个对象并不是同一个对象，这也就说，单例类中对象的唯一性的作用范围是进程内的，在进程间是不唯一的。

### 如何实现线程唯一的单例？

刚刚我们讲了单例类对象是进程唯一的，一个进程只能有一个单例对象。那如何实现一个线程唯一的单例呢？

我们先来看一下，什么是线程唯一的单例，以及“线程唯一”和“进程唯一”的区别。

“进程唯一”指的是进程内唯一，进程间不唯一。类比一下，“线程唯一”指的是线程内唯一，线程间可以不唯一。实际上，“进程唯一”还代表了线程内、线程间都唯一，这也是“进程唯一”和“线程唯一”的区别之处。这段话听起来有点像绕口令，我举个例子来解释一下。

假设 IdGenerator 是一个线程唯一的单例类。在线程 A 内，我们可以创建一个单例对象 a。因为线程内唯一，在线程 A 内就不能再创建新的 IdGenerator 对象了，而线程间可以不唯一，所以，在另外一个线程 B 内，我们还可以重新创建一个新的单例对象 b。

尽管概念理解起来比较复杂，但线程唯一单例的代码实现很简单，如下所示。在代码中，我们通过一个 HashMap 来存储对象，其中 key 是线程 ID，value 是对象。这样我们就可以做到，不同的线程对应不同的对象，同一个线程只能对应一个对象。实际上，Java 语言本身提供了 ThreadLocal 工具类，可以更加轻松地实现线程唯一单例。不过，ThreadLocal 底层实现原理也是基于下面代码中所示的 HashMap。

```java
public class IdGenerator {
  private AtomicLong id = new AtomicLong(0);

  private static final ConcurrentHashMap<Long, IdGenerator> instances
          = new ConcurrentHashMap<>();

  private IdGenerator() {}

  public static IdGenerator getInstance() {
    Long currentThreadId = Thread.currentThread().getId();
    instances.putIfAbsent(currentThreadId, new IdGenerator());
    return instances.get(currentThreadId);
  }

  public long getId() {
    return id.incrementAndGet();
  }
}
```

### 如何实现集群环境下的单例？

刚刚我们讲了“进程唯一”的单例和“线程唯一”的单例，现在，我们再来看下，“集群唯一”的单例。

首先，我们还是先来解释一下，什么是“集群唯一”的单例。我们还是将它跟“进程唯一”“线程唯一”做个对比。“进程唯一”指的是进程内唯一、进程间不唯一。“线程唯一”指的是线程内唯一、线程间不唯一。集群相当于多个进程构成的一个集合，“集群唯一”就相当于是进程内唯一、进程间也唯一。也就是说，不同的进程间共享同一个对象，不能创建同一个类的多个对象。

我们知道，经典的单例模式是进程内唯一的，那如何实现一个进程间也唯一的单例呢？如果严格按照不同的进程间共享同一个对象来实现，那集群唯一的单例实现起来就有点难度了。

具体来说，我们需要把这个单例对象序列化并存储到外部共享存储区（比如文件）。进程在使用这个单例对象的时候，需要先从外部共享存储区中将它读取到内存，并反序列化成对象，然后再使用，使用完成之后还需要再存储回外部共享存储区。

为了保证任何时刻，在进程间都只有一份对象存在，一个进程在获取到对象之后，需要对对象加锁，避免其他进程再将其获取。在进程使用完这个对象之后，还需要显式地将对象从内存中删除，并且释放对对象的加锁。

按照这个思路，我用伪代码实现了一下这个过程，具体如下所示：

```
public class IdGenerator {
  private AtomicLong id = new AtomicLong(0);
  private static IdGenerator instance;
  private static SharedObjectStorage storage = FileSharedObjectStorage(/*入参省略，比如文件地址*/);
  private static DistributedLock lock = new DistributedLock();
  
  private IdGenerator() {}

  public synchronized static IdGenerator getInstance() 
    if (instance == null) {
      lock.lock();
      instance = storage.load(IdGenerator.class);
    }
    return instance;
  }
  
  public synchroinzed void freeInstance() {
    storage.save(this, IdGeneator.class);
    instance = null; //释放对象
    lock.unlock();
  }
  
  public long getId() { 
    return id.incrementAndGet();
  }
}

// IdGenerator使用举例
IdGenerator idGeneator = IdGenerator.getInstance();
long id = idGenerator.getId();
IdGenerator.freeInstance();
```

### 如何实现一个多例模式？

“单例”指的是，一个类只能创建一个对象。对应地，“多例”指的就是，一个类可以创建多个对象，但是个数是有限制的，比如只能创建 3 个对象。如果用代码来简单示例一下的话，就是下面这个样子：

```java
public class BackendServer {
  private long serverNo;
  private String serverAddress;

  private static final int SERVER_COUNT = 3;
  private static final Map<Long, BackendServer> serverInstances = new HashMap<>();

  static {
    serverInstances.put(1L, new BackendServer(1L, "192.134.22.138:8080"));
    serverInstances.put(2L, new BackendServer(2L, "192.134.22.139:8080"));
    serverInstances.put(3L, new BackendServer(3L, "192.134.22.140:8080"));
  }

  private BackendServer(long serverNo, String serverAddress) {
    this.serverNo = serverNo;
    this.serverAddress = serverAddress;
  }

  public BackendServer getInstance(long serverNo) {
    return serverInstances.get(serverNo);
  }

  public BackendServer getRandomInstance() {
    Random r = new Random();
    int no = r.nextInt(SERVER_COUNT)+1;
    return serverInstances.get(no);
  }
}
```

实际上，对于多例模式，还有一种理解方式：同一类型的只能创建一个对象，不同类型的可以创建多个对象。这里的“类型”如何理解呢？

我们还是通过一个例子来解释一下，具体代码如下所示。在代码中，logger name 就是刚刚说的“类型”，同一个 logger name 获取到的对象实例是相同的，不同的 logger name 获取到的对象实例是不同的。

```
public class Logger {
  private static final ConcurrentHashMap<String, Logger> instances
          = new ConcurrentHashMap<>();

  private Logger() {}

  public static Logger getInstance(String loggerName) {
    instances.putIfAbsent(loggerName, new Logger());
    return instances.get(loggerName);
  }

  public void log() {
    //...
  }
}

//l1==l2, l1!=l3
Logger l1 = Logger.getInstance("User.class");
Logger l2 = Logger.getInstance("User.class");
Logger l3 = Logger.getInstance("Order.class");
```

这种多例模式的理解方式有点类似工厂模式。它跟工厂模式的不同之处是，多例模式创建的对象都是同一个类的对象，而工厂模式创建的是不同子类的对象，关于这一点，下一节课中就会讲到。实际上，它还有点类似享元模式，两者的区别等到我们讲到享元模式的时候再来分析。除此之外，实际上，枚举类型也相当于多例模式，一个类型只能对应一个对象，一个类可以创建多个对象。

## 44 | 工厂模式（上）：我为什么说没事不要随便用工厂模式创建对象？

### 简单工厂（Simple Factory）

在下面这段代码中，我们根据配置文件的后缀（json、xml、yaml、properties），选择不同的解析器（JsonRuleConfigParser、XmlRuleConfigParser……），将存储在文件中的配置解析成内存对象 RuleConfig。

```java
public class RuleConfigSource {
  public RuleConfig load(String ruleConfigFilePath) {
    String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
    IRuleConfigParser parser = null;
    if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
      parser = new JsonRuleConfigParser();
    } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
      parser = new XmlRuleConfigParser();
    } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
      parser = new YamlRuleConfigParser();
    } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
      parser = new PropertiesRuleConfigParser();
    } else {
      throw new InvalidRuleConfigException(
             "Rule config file format is not supported: " + ruleConfigFilePath);
    }

    String configText = "";
    //从ruleConfigFilePath文件中读取配置文本到configText中
    RuleConfig ruleConfig = parser.parse(configText);
    return ruleConfig;
  }

  private String getFileExtension(String filePath) {
    //...解析文件名获取扩展名，比如rule.json，返回json
    return "json";
  }
}
```

在“规范和重构”那一部分中，我们有讲到，为了让代码逻辑更加清晰，可读性更好，我们要善于将功能独立的代码块封装成函数。按照这个设计思路，我们可以将代码中涉及 parser 创建的部分逻辑剥离出来，抽象成 createParser() 函数。重构之后的代码如下所示：

```java
public RuleConfig load(String ruleConfigFilePath) {
    String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
    IRuleConfigParser parser = createParser(ruleConfigFileExtension);
    if (parser == null) {
      throw new InvalidRuleConfigException(
              "Rule config file format is not supported: " + ruleConfigFilePath);
    }

    String configText = "";
    //从ruleConfigFilePath文件中读取配置文本到configText中
    RuleConfig ruleConfig = parser.parse(configText);
    return ruleConfig;
  }

  private String getFileExtension(String filePath) {
    //...解析文件名获取扩展名，比如rule.json，返回json
    return "json";
  }

  private IRuleConfigParser createParser(String configFormat) {
    IRuleConfigParser parser = null;
    if ("json".equalsIgnoreCase(configFormat)) {
      parser = new JsonRuleConfigParser();
    } else if ("xml".equalsIgnoreCase(configFormat)) {
      parser = new XmlRuleConfigParser();
    } else if ("yaml".equalsIgnoreCase(configFormat)) {
      parser = new YamlRuleConfigParser();
    } else if ("properties".equalsIgnoreCase(configFormat)) {
      parser = new PropertiesRuleConfigParser();
    }
    return parser;
  }
}
```

为了让类的职责更加单一、代码更加清晰，我们还可以进一步将 createParser() 函数剥离到一个独立的类中，让这个类只负责对象的创建。而这个类就是我们现在要讲的简单工厂模式类。具体的代码如下所示：

```java
public class RuleConfigSource {
  public RuleConfig load(String ruleConfigFilePath) {
    String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
    IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);
    if (parser == null) {
      throw new InvalidRuleConfigException(
              "Rule config file format is not supported: " + ruleConfigFilePath);
    }

    String configText = "";
    //从ruleConfigFilePath文件中读取配置文本到configText中
    RuleConfig ruleConfig = parser.parse(configText);
    return ruleConfig;
  }

  private String getFileExtension(String filePath) {
    //...解析文件名获取扩展名，比如rule.json，返回json
    return "json";
  }
}

public class RuleConfigParserFactory {
  public static IRuleConfigParser createParser(String configFormat) {
    IRuleConfigParser parser = null;
    if ("json".equalsIgnoreCase(configFormat)) {
      parser = new JsonRuleConfigParser();
    } else if ("xml".equalsIgnoreCase(configFormat)) {
      parser = new XmlRuleConfigParser();
    } else if ("yaml".equalsIgnoreCase(configFormat)) {
      parser = new YamlRuleConfigParser();
    } else if ("properties".equalsIgnoreCase(configFormat)) {
      parser = new PropertiesRuleConfigParser();
    }
    return parser;
  }
}
```

大部分工厂类都是以“Factory”这个单词结尾的，但也不是必须的，比如 Java 中的 DateFormat、Calender。除此之外，工厂类中创建对象的方法一般都是 create 开头，比如代码中的 createParser()，但有的也命名为 getInstance()、createInstance()、newInstance()，有的甚至命名为 valueOf()（比如 Java String 类的 valueOf() 函数）等等，这个我们根据具体的场景和习惯来命名就好。

在上面的代码实现中，我们每次调用 RuleConfigParserFactory 的 createParser() 的时候，都要创建一个新的 parser。实际上，如果 parser 可以复用，为了节省内存和对象创建的时间，我们可以将 parser 事先创建好缓存起来。当调用 createParser() 函数的时候，我们从缓存中取出 parser 对象直接使用。

这有点类似单例模式和简单工厂模式的结合，具体的代码实现如下所示。在接下来的讲解中，我们把上一种实现方法叫作简单工厂模式的第一种实现方法，把下面这种实现方法叫作简单工厂模式的第二种实现方法。

```java
public class RuleConfigParserFactory {
  private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

  static {
    cachedParsers.put("json", new JsonRuleConfigParser());
    cachedParsers.put("xml", new XmlRuleConfigParser());
    cachedParsers.put("yaml", new YamlRuleConfigParser());
    cachedParsers.put("properties", new PropertiesRuleConfigParser());
  }

  public static IRuleConfigParser createParser(String configFormat) {
    if (configFormat == null || configFormat.isEmpty()) {
      return null;//返回null还是IllegalArgumentException全凭你自己说了算
    }
    IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
    return parser;
  }
}
```

对于上面两种简单工厂模式的实现方法，如果我们要添加新的 parser，那势必要改动到 RuleConfigParserFactory 的代码，那这是不是违反开闭原则呢？实际上，如果不是需要频繁地添加新的 parser，只是偶尔修改一下 RuleConfigParserFactory 代码，稍微不符合开闭原则，也是完全可以接受的。

除此之外，在 RuleConfigParserFactory 的第一种代码实现中，有一组 if 分支判断逻辑，是不是应该用多态或其他设计模式来替代呢？实际上，如果 if 分支并不是很多，代码中有 if 分支也是完全可以接受的。应用多态或设计模式来替代 if 分支判断逻辑，也并不是没有任何缺点的，它虽然提高了代码的扩展性，更加符合开闭原则，但也增加了类的个数，牺牲了代码的可读性。关于这一点，我们在后面章节中会详细讲到。

总结一下，尽管简单工厂模式的代码实现中，有多处 if 分支判断逻辑，违背开闭原则，但权衡扩展性和可读性，这样的代码实现在大多数情况下（比如，不需要频繁地添加 parser，也没有太多的 parser）是没有问题的。

### 工厂方法（Factory Method）

如果我们非得要将 if 分支逻辑去掉，那该怎么办呢？比较经典处理方法就是利用多态。按照多态的实现思路，对上面的代码进行重构。重构之后的代码如下所示：

```java
public interface IRuleConfigParserFactory {
  IRuleConfigParser createParser();
}

public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
  @Override
  public IRuleConfigParser createParser() {
    return new JsonRuleConfigParser();
  }
}

public class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
  @Override
  public IRuleConfigParser createParser() {
    return new XmlRuleConfigParser();
  }
}

public class YamlRuleConfigParserFactory implements IRuleConfigParserFactory {
  @Override
  public IRuleConfigParser createParser() {
    return new YamlRuleConfigParser();
  }
}

public class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
  @Override
  public IRuleConfigParser createParser() {
    return new PropertiesRuleConfigParser();
  }
}
```

实际上，这就是工厂方法模式的典型代码实现。这样当我们新增一种 parser 的时候，只需要新增一个实现了 IRuleConfigParserFactory 接口的 Factory 类即可。所以，工厂方法模式比起简单工厂模式更加符合开闭原则。

从上面的工厂方法的实现来看，一切都很完美，但是实际上存在挺大的问题。问题存在于这些工厂类的使用上。接下来，我们看一下，如何用这些工厂类来实现 RuleConfigSource 的 load() 函数。具体的代码如下所示：

```java
public class RuleConfigSource {
  public RuleConfig load(String ruleConfigFilePath) {
    String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

    IRuleConfigParserFactory parserFactory = null;
    if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
      parserFactory = new JsonRuleConfigParserFactory();
    } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
      parserFactory = new XmlRuleConfigParserFactory();
    } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
      parserFactory = new YamlRuleConfigParserFactory();
    } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
      parserFactory = new PropertiesRuleConfigParserFactory();
    } else {
      throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
    }
    IRuleConfigParser parser = parserFactory.createParser();

    String configText = "";
    //从ruleConfigFilePath文件中读取配置文本到configText中
    RuleConfig ruleConfig = parser.parse(configText);
    return ruleConfig;
  }

  private String getFileExtension(String filePath) {
    //...解析文件名获取扩展名，比如rule.json，返回json
    return "json";
  }
}
```

从上面的代码实现来看，工厂类对象的创建逻辑又耦合进了 load() 函数中，跟我们最初的代码版本非常相似，引入工厂方法非但没有解决问题，反倒让设计变得更加复杂了。那怎么来解决这个问题呢？

我们可以为工厂类再创建一个简单工厂，也就是工厂的工厂，用来创建工厂类对象。这段话听起来有点绕，我把代码实现出来了，你一看就能明白了。其中，RuleConfigParserFactoryMap 类是创建工厂对象的工厂类，getParserFactory() 返回的是缓存好的单例工厂对象。

```java
public class RuleConfigSource {
  public RuleConfig load(String ruleConfigFilePath) {
    String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

    IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
    if (parserFactory == null) {
      throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
    }
    IRuleConfigParser parser = parserFactory.createParser();

    String configText = "";
    //从ruleConfigFilePath文件中读取配置文本到configText中
    RuleConfig ruleConfig = parser.parse(configText);
    return ruleConfig;
  }

  private String getFileExtension(String filePath) {
    //...解析文件名获取扩展名，比如rule.json，返回json
    return "json";
  }
}

//因为工厂类只包含方法，不包含成员变量，完全可以复用，
//不需要每次都创建新的工厂类对象，所以，简单工厂模式的第二种实现思路更加合适。
public class RuleConfigParserFactoryMap { //工厂的工厂
  private static final Map<String, IRuleConfigParserFactory> cachedFactories = new HashMap<>();

  static {
    cachedFactories.put("json", new JsonRuleConfigParserFactory());
    cachedFactories.put("xml", new XmlRuleConfigParserFactory());
    cachedFactories.put("yaml", new YamlRuleConfigParserFactory());
    cachedFactories.put("properties", new PropertiesRuleConfigParserFactory());
  }

  public static IRuleConfigParserFactory getParserFactory(String type) {
    if (type == null || type.isEmpty()) {
      return null;
    }
    IRuleConfigParserFactory parserFactory = cachedFactories.get(type.toLowerCase());
    return parserFactory;
  }
}
```

当我们需要添加新的规则配置解析器的时候，我们只需要创建新的 parser 类和 parser factory 类，并且在 RuleConfigParserFactoryMap 类中，将新的 parser factory 对象添加到 cachedFactories 中即可。代码的改动非常少，基本上符合开闭原则。

实际上，对于规则配置文件解析这个应用场景来说，工厂模式需要额外创建诸多 Factory 类，也会增加代码的复杂性，而且，每个 Factory 类只是做简单的 new 操作，功能非常单薄（只有一行代码），也没必要设计成独立的类，所以，在这个应用场景下，简单工厂模式简单好用，比工厂方法模式更加合适。

那什么时候该用工厂方法模式，而非简单工厂模式呢？

我们前面提到，之所以将某个代码块剥离出来，独立为函数或者类，原因是这个代码块的逻辑过于复杂，剥离之后能让代码更加清晰，更加可读、可维护。但是，如果代码块本身并不复杂，就几行代码而已，我们完全没必要将它拆分成单独的函数或者类。

基于这个设计思想，当对象的创建逻辑比较复杂，不只是简单的 new 一下就可以，而是要组合其他类对象，做各种初始化操作的时候，我们推荐使用工厂方法模式，将复杂的创建逻辑拆分到多个工厂类中，让每个工厂类都不至于过于复杂。而使用简单工厂模式，将所有的创建逻辑都放到一个工厂类中，会导致这个工厂类变得很复杂。

除此之外，在某些场景下，如果对象不可复用，那工厂类每次都要返回不同的对象。如果我们使用简单工厂模式来实现，就只能选择第一种包含 if 分支逻辑的实现方式。如果我们还想避免烦人的 if-else 分支逻辑，这个时候，我们就推荐使用工厂方法模式。

### 抽象工厂（Abstract Factory）

讲完了简单工厂、工厂方法，我们再来看抽象工厂模式。抽象工厂模式的应用场景比较特殊，没有前两种常用，所以不是我们本节课学习的重点，你简单了解一下就可以了。

在简单工厂和工厂方法中，类只有一种分类方式。比如，在规则配置解析那个例子中，解析器类只会根据配置文件格式（Json、Xml、Yaml……）来分类。但是，如果类有两种分类方式，比如，我们既可以按照配置文件格式来分类，也可以按照解析的对象（Rule 规则配置还是 System 系统配置）来分类，那就会对应下面这 8 个 parser 类。

```
针对规则配置的解析器：基于接口IRuleConfigParser
JsonRuleConfigParser
XmlRuleConfigParser
YamlRuleConfigParser
PropertiesRuleConfigParser

针对系统配置的解析器：基于接口ISystemConfigParser
JsonSystemConfigParser
XmlSystemConfigParser
YamlSystemConfigParser
PropertiesSystemConfigParser
```

针对这种特殊的场景，如果还是继续用工厂方法来实现的话，我们要针对每个 parser 都编写一个工厂类，也就是要编写 8 个工厂类。如果我们未来还需要增加针对业务配置的解析器（比如 IBizConfigParser），那就要再对应地增加 4 个工厂类。而我们知道，过多的类也会让系统难维护。这个问题该怎么解决呢？

抽象工厂就是针对这种非常特殊的场景而诞生的。我们可以让一个工厂负责创建多个不同类型的对象（IRuleConfigParser、ISystemConfigParser 等），而不是只创建一种 parser 对象。这样就可以有效地减少工厂类的个数。具体的代码实现如下所示：

```
public interface IConfigParserFactory {
  IRuleConfigParser createRuleParser();
  ISystemConfigParser createSystemParser();
  //此处可以扩展新的parser类型，比如IBizConfigParser
}

public class JsonConfigParserFactory implements IConfigParserFactory {
  @Override
  public IRuleConfigParser createRuleParser() {
    return new JsonRuleConfigParser();
  }

  @Override
  public ISystemConfigParser createSystemParser() {
    return new JsonSystemConfigParser();
  }
}

public class XmlConfigParserFactory implements IConfigParserFactory {
  @Override
  public IRuleConfigParser createRuleParser() {
    return new XmlRuleConfigParser();
  }

  @Override
  public ISystemConfigParser createSystemParser() {
    return new XmlSystemConfigParser();
  }
}

// 省略YamlConfigParserFactory和PropertiesConfigParserFactory代码
```

总结：

当创建逻辑比较复杂，是一个“大工程”的时候，我们就考虑使用工厂模式，封装对象的创建过程，将对象的创建和使用相分离。何为创建逻辑比较复杂呢？我总结了下面两种情况。

- 第一种情况：类似规则配置解析的例子，代码中存在 if-else 分支判断，动态地根据不同的类型创建不同的对象。针对这种情况，我们就考虑使用工厂模式，将这一大坨 if-else 创建对象的代码抽离出来，放到工厂类中。
- 还有一种情况，尽管我们不需要根据不同的类型创建不同的对象，但是，单个对象本身的创建过程比较复杂，比如前面提到的要组合其他类对象，做各种初始化操作。在这种情况下，我们也可以考虑使用工厂模式，将对象的创建过程封装到工厂类中。

对于第一种情况，当每个对象的创建逻辑都比较简单的时候，我推荐使用简单工厂模式，将多个对象的创建逻辑放到一个工厂类中。当每个对象的创建逻辑都比较复杂的时候，为了避免设计一个过于庞大的简单工厂类，我推荐使用工厂方法模式，将创建逻辑拆分得更细，每个对象的创建逻辑独立到各自的工厂类中。同理，对于第二种情况，因为单个对象本身的创建逻辑就比较复杂，所以，我建议使用工厂方法模式。

除了刚刚提到的这几种情况之外，如果创建对象的逻辑并不复杂，那我们就直接通过 new 来创建对象就可以了，不需要使用工厂模式。

现在，我们上升一个思维层面来看工厂模式，它的作用无外乎下面这四个。这也是判断要不要使用工厂模式的最本质的参考标准。

- 封装变化：创建逻辑有可能变化，封装成工厂类之后，创建逻辑的变更对调用者透明。
- 代码复用：创建代码抽离到独立的工厂类之后可以复用。
- 隔离复杂性：封装复杂的创建逻辑，调用者无需了解如何创建对象。
- 控制复杂度：将创建代码抽离出来，让原本的函数或类职责更单一，代码更简洁。

### 课后问题？

1. 工厂模式是一种非常常用的设计模式，在很多开源项目、工具类中到处可见，比如 Java 中的 Calendar、DateFormat 类。除此之外，你还知道哪些用工厂模式实现类？可以留言说一说它们为什么要设计成工厂模式类？



2. 实际上，简单工厂模式还叫作静态工厂方法模式（Static Factory Method Pattern）。之所以叫静态工厂方法模式，是因为其中创建对象的方法是静态的。那为什么要设置成静态的呢？设置成静态的，在使用的时候，是否会影响到代码的可测试性呢？



## 45 | 工厂模式（下）：如何设计实现一个Dependency Injection框架？

在上一节课我们讲到，当创建对象是一个“大工程”的时候，我们一般会选择使用工厂模式，来封装对象复杂的创建过程，将对象的创建和使用分离，让代码更加清晰。那何为“大工程”呢？上一节课中我们讲了两种情况，一种是创建过程涉及复杂的 if-else 分支判断，另一种是对象创建需要组装多个其他类对象或者需要复杂的初始化过程。

今天，我们再来讲一个创建对象的“大工程”，依赖注入框架，或者叫依赖注入容器（Dependency Injection Container），简称 DI 容器。在今天的讲解中，我会带你一块搞清楚这样几个问题：DI 容器跟我们讲的工厂模式又有何区别和联系？DI 容器的核心功能有哪些，以及如何实现一个简单的 DI 容器？

### 工厂模式和 DI 容器有何区别？

实际上，DI 容器底层最基本的设计思路就是基于工厂模式的。DI 容器相当于一个大的工厂类，负责在程序启动的时候，根据配置（要创建哪些类对象，每个类对象的创建需要依赖哪些其他类对象）事先创建好对象。当应用程序需要使用某个类对象的时候，直接从容器中获取即可。正是因为它持有一堆对象，所以这个框架才被称为“容器”。

DI 容器相对于我们上节课讲的工厂模式的例子来说，它处理的是更大的对象创建工程。上节课讲的工厂模式中，一个工厂类只负责某个类对象或者某一组相关类对象（继承自同一抽象类或者接口的子类）的创建，而 DI 容器负责的是整个应用中所有类对象的创建。

除此之外，DI 容器负责的事情要比单纯的工厂模式要多。比如，它还包括配置的解析、对象生命周期的管理。接下来，我们就详细讲讲，一个简单的 DI 容器应该包含哪些核心功能。

### DI 容器的核心功能有哪些？

总结一下，一个简单的 DI 容器的核心功能一般有三个：配置解析、对象创建和对象生命周期管理。

**首先，我们来看配置解析。**

在上节课讲的工厂模式中，工厂类要创建哪个类对象是事先确定好的，并且是写死在工厂类代码中的。作为一个通用的框架来说，框架代码跟应用代码应该是高度解耦的，DI 容器事先并不知道应用会创建哪些对象，不可能把某个应用要创建的对象写死在框架代码中。所以，我们需要通过一种形式，让应用告知 DI 容器要创建哪些对象。这种形式就是我们要讲的配置。

我们将需要由 DI 容器来创建的类对象和创建类对象的必要信息（使用哪个构造函数以及对应的构造函数参数都是什么等等），放到配置文件中。容器读取配置文件，根据配置文件提供的信息来创建对象。

下面是一个典型的 Spring 容器的配置文件。Spring 容器读取这个配置文件，解析出要创建的两个对象：rateLimiter 和 redisCounter，并且得到两者的依赖关系：rateLimiter 依赖 redisCounter。

```java
public class RateLimiter {
  private RedisCounter redisCounter;
  public RateLimiter(RedisCounter redisCounter) {
    this.redisCounter = redisCounter;
  }
  public void test() {
    System.out.println("Hello World!");
  }
  //...
}

public class RedisCounter {
  private String ipAddress;
  private int port;
  public RedisCounter(String ipAddress, int port) {
    this.ipAddress = ipAddress;
    this.port = port;
  }
  //...
}

配置文件beans.xml：
<beans>
   <bean id="rateLimiter" class="com.xzg.RateLimiter">
      <constructor-arg ref="redisCounter"/>
   </bean>
 
   <bean id="redisCounter" class="com.xzg.redisCounter">
     <constructor-arg type="String" value="127.0.0.1">
     <constructor-arg type="int" value=1234>
   </bean>
</beans>
```

**其次，我们再来看对象创建。**

在 DI 容器中，如果我们给每个类都对应创建一个工厂类，那项目中类的个数会成倍增加，这会增加代码的维护成本。要解决这个问题并不难。我们只需要将所有类对象的创建都放到一个工厂类中完成就可以了，比如 BeansFactory。

你可能会说，如果要创建的类对象非常多，BeansFactory 中的代码会不会线性膨胀（代码量跟创建对象的个数成正比）呢？实际上并不会。待会讲到 DI 容器的具体实现的时候，我们会讲“反射”这种机制，它能在程序运行的过程中，动态地加载类、创建对象，不需要事先在代码中写死要创建哪些对象。所以，不管是创建一个对象还是十个对象，BeansFactory 工厂类代码都是一样的。

**最后，我们来看对象的生命周期管理。**

上一节课我们讲到，简单工厂模式有两种实现方式，一种是每次都返回新创建的对象，另一种是每次都返回同一个事先创建好的对象，也就是所谓的单例对象。在 Spring 框架中，我们可以通过配置 scope 属性，来区分这两种不同类型的对象。scope=prototype 表示返回新创建的对象，scope=singleton 表示返回单例对象。

除此之外，我们还可以配置对象是否支持懒加载。如果 lazy-init=true，对象在真正被使用到的时候（比如：BeansFactory.getBean(“userService”)）才被被创建；如果 lazy-init=false，对象在应用启动的时候就事先创建好。

不仅如此，我们还可以配置对象的 init-method 和 destroy-method 方法，比如 init-method=loadProperties()，destroy-method=updateConfigFile()。DI 容器在创建好对象之后，会主动调用 init-method 属性指定的方法来初始化对象。在对象被最终销毁之前，DI 容器会主动调用 destroy-method 属性指定的方法来做一些清理工作，比如释放数据库连接池、关闭文件。

### 如何实现一个简单的 DI 容器？

实际上，用 Java 语言来实现一个简单的 DI 容器，核心逻辑只需要包括这样两个部分：配置文件解析、根据配置文件通过“反射”语法来创建对象。

1. 最小原型设计

因为我们主要是讲解设计模式，所以，在今天的讲解中，我们只实现一个 DI 容器的最小原型。像 Spring 框架这样的 DI 容器，它支持的配置格式非常灵活和复杂。为了简化代码实现，重点讲解原理，在最小原型中，我们只支持下面配置文件中涉及的配置语法。

```
配置文件beans.xml
<beans>
   <bean id="rateLimiter" class="com.xzg.RateLimiter">
      <constructor-arg ref="redisCounter"/>
   </bean>
 
   <bean id="redisCounter" class="com.xzg.redisCounter" scope="singleton" lazy-init="true">
     <constructor-arg type="String" value="127.0.0.1">
     <constructor-arg type="int" value=1234>
   </bean>
</bean
```

最小原型的使用方式跟 Spring 框架非常类似，示例代码如下所示：

```
public class Demo {
  public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "beans.xml");
    RateLimiter rateLimiter = (RateLimiter) applicationContext.getBean("rateLimiter");
    rateLimiter.test();
    //...
  }
}
```

2. 提供执行入口

前面我们讲到，面向对象设计的最后一步是：组装类并提供执行入口。在这里，执行入口就是一组暴露给外部使用的接口和类。

通过刚刚的最小原型使用示例代码，我们可以看出，执行入口主要包含两部分：ApplicationContext 和 ClassPathXmlApplicationContext。其中，ApplicationContext 是接口，ClassPathXmlApplicationContext 是接口的实现类。两个类具体实现如下所示：

```java
public interface ApplicationContext {
  Object getBean(String beanId);
}

public class ClassPathXmlApplicationContext implements ApplicationContext {
  private BeansFactory beansFactory;
  private BeanConfigParser beanConfigParser;

  public ClassPathXmlApplicationContext(String configLocation) {
    this.beansFactory = new BeansFactory();
    this.beanConfigParser = new XmlBeanConfigParser();
    loadBeanDefinitions(configLocation);
  }

  private void loadBeanDefinitions(String configLocation) {
    InputStream in = null;
    try {
      in = this.getClass().getResourceAsStream("/" + configLocation);
      if (in == null) {
        throw new RuntimeException("Can not find config file: " + configLocation);
      }
      List<BeanDefinition> beanDefinitions = beanConfigParser.parse(in);
      beansFactory.addBeanDefinitions(beanDefinitions);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // TODO: log error
        }
      }
    }
  }

  @Override
  public Object getBean(String beanId) {
    return beansFactory.getBean(beanId);
  }
}
```

从上面的代码中，我们可以看出，ClassPathXmlApplicationContext 负责组装 BeansFactory 和 BeanConfigParser 两个类，串联执行流程：从 classpath 中加载 XML 格式的配置文件，通过 BeanConfigParser 解析为统一的 BeanDefinition 格式，然后，BeansFactory 根据 BeanDefinition 来创建对象。

3. 配置文件解析

配置文件解析主要包含 BeanConfigParser 接口和 XmlBeanConfigParser 实现类，负责将配置文件解析为 BeanDefinition 结构，以便 BeansFactory 根据这个结构来创建对象。

配置文件的解析比较繁琐，不涉及我们专栏要讲的理论知识，不是我们讲解的重点，所以这里我只给出两个类的大致设计思路，并未给出具体的实现代码。如果感兴趣的话，你可以自行补充完整。具体的代码框架如下所示：

```java
public interface BeanConfigParser {
  List<BeanDefinition> parse(InputStream inputStream);
  List<BeanDefinition> parse(String configContent);
}

public class XmlBeanConfigParser implements BeanConfigParser {

  @Override
  public List<BeanDefinition> parse(InputStream inputStream) {
    String content = null;
    // TODO:...
    return parse(content);
  }

  @Override
  public List<BeanDefinition> parse(String configContent) {
    List<BeanDefinition> beanDefinitions = new ArrayList<>();
    // TODO:...
    return beanDefinitions;
  }

}

public class BeanDefinition {
  private String id;
  private String className;
  private List<ConstructorArg> constructorArgs = new ArrayList<>();
  private Scope scope = Scope.SINGLETON;
  private boolean lazyInit = false;
  // 省略必要的getter/setter/constructors
 
  public boolean isSingleton() {
    return scope.equals(Scope.SINGLETON);
  }


  public static enum Scope {
    SINGLETON,
    PROTOTYPE
  }
  
  public static class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;
    // 省略必要的getter/setter/constructors
  }
}
```

4. 核心工厂类设计

最后，我们来看，BeansFactory 是如何设计和实现的。这也是我们这个 DI 容器最核心的一个类了。它负责根据从配置文件解析得到的 BeanDefinition 来创建对象。

如果对象的 scope 属性是 singleton，那对象创建之后会缓存在 singletonObjects 这样一个 map 中，下次再请求此对象的时候，直接从 map 中取出返回，不需要重新创建。如果对象的 scope 属性是 prototype，那每次请求对象，BeansFactory 都会创建一个新的对象返回。

实际上，BeansFactory 创建对象用到的主要技术点就是 Java 中的反射语法：一种动态加载类和创建对象的机制。我们知道，JVM 在启动的时候会根据代码自动地加载类、创建对象。至于都要加载哪些类、创建哪些对象，这些都是在代码中写死的，或者说提前写好的。但是，如果某个对象的创建并不是写死在代码中，而是放到配置文件中，我们需要在程序运行期间，动态地根据配置文件来加载类、创建对象，那这部分工作就没法让 JVM 帮我们自动完成了，我们需要利用 Java 提供的反射语法自己去编写代码。

搞清楚了反射的原理，BeansFactory 的代码就不难看懂了。具体代码实现如下所示：

```java
public class BeansFactory {
  private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
  private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

  public void addBeanDefinitions(List<BeanDefinition> beanDefinitionList) {
    for (BeanDefinition beanDefinition : beanDefinitionList) {
      this.beanDefinitions.putIfAbsent(beanDefinition.getId(), beanDefinition);
    }

    for (BeanDefinition beanDefinition : beanDefinitionList) {
      if (beanDefinition.isLazyInit() == false && beanDefinition.isSingleton()) {
        createBean(beanDefinition);
      }
    }
  }

  public Object getBean(String beanId) {
    BeanDefinition beanDefinition = beanDefinitions.get(beanId);
    if (beanDefinition == null) {
      throw new NoSuchBeanDefinitionException("Bean is not defined: " + beanId);
    }
    return createBean(beanDefinition);
  }

  @VisibleForTesting
  protected Object createBean(BeanDefinition beanDefinition) {
    if (beanDefinition.isSingleton() && singletonObjects.contains(beanDefinition.getId())) {
      return singletonObjects.get(beanDefinition.getId());
    }

    Object bean = null;
    try {
      Class beanClass = Class.forName(beanDefinition.getClassName());
      List<BeanDefinition.ConstructorArg> args = beanDefinition.getConstructorArgs();
      if (args.isEmpty()) {
        bean = beanClass.newInstance();
      } else {
        Class[] argClasses = new Class[args.size()];
        Object[] argObjects = new Object[args.size()];
        for (int i = 0; i < args.size(); ++i) {
          BeanDefinition.ConstructorArg arg = args.get(i);
          if (!arg.getIsRef()) {
            argClasses[i] = arg.getType();
            argObjects[i] = arg.getArg();
          } else {
            BeanDefinition refBeanDefinition = beanDefinitions.get(arg.getArg());
            if (refBeanDefinition == null) {
              throw new NoSuchBeanDefinitionException("Bean is not defined: " + arg.getArg());
            }
            argClasses[i] = Class.forName(refBeanDefinition.getClassName());
            argObjects[i] = createBean(refBeanDefinition);
          }
        }
        bean = beanClass.getConstructor(argClasses).newInstance(argObjects);
      }
    } catch (ClassNotFoundException | IllegalAccessException
            | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
      throw new BeanCreationFailureException("", e);
    }

    if (bean != null && beanDefinition.isSingleton()) {
      singletonObjects.putIfAbsent(beanDefinition.getId(), bean);
      return singletonObjects.get(beanDefinition.getId());
    }
    return bean;
  }
}
```

效果如下：

![image-20210415190639061](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210415190640.png)

### 重点回顾

DI 容器在一些软件开发中已经成为了标配，比如 Spring IOC、Google Guice。但是，大部分人可能只是把它当作一个黑盒子来使用，并未真正去了解它的底层是如何实现的。当然，如果只是做一些简单的小项目，简单会用就足够了，但是，如果我们面对的是非常复杂的系统，当系统出现问题的时候，对底层原理的掌握程度，决定了我们排查问题的能力，直接影响到我们排查问题的效率。

今天，我们讲解了一个简单的 DI 容器的实现原理，其核心逻辑主要包括：配置文件解析，以及根据配置文件通过“反射”语法来创建对象。其中，创建对象的过程就应用到了我们在学的工厂模式。对象创建、组装、管理完全有 DI 容器来负责，跟具体业务代码解耦，让程序员聚焦在业务代码的开发上。

### 课后问题

BeansFactory 类中的 createBean() 函数是一个递归函数。当构造函数的参数是 ref 类型时，会递归地创建 ref 属性指向的对象。如果我们在配置文件中错误地配置了对象之间的依赖关系，导致存在循环依赖，那 BeansFactory 的 createBean() 函数是否会出现堆栈溢出？又该如何解决这个问题呢？

## 46 | 建造者模式：详解构造函数、set方法、建造者模式三种对象创建方式

上两节课中，我们学习了工厂模式，讲了工厂模式的应用场景，并带你实现了一个简单的 DI 容器。今天，我们再来学习另外一个比较常用的创建型设计模式，Builder 模式，中文翻译为建造者模式或者构建者模式，也有人叫它生成器模式。

实际上，建造者模式的原理和代码实现非常简单，掌握起来并不难，难点在于应用场景。比如，你有没有考虑过这样几个问题：直接使用构造函数或者配合 set 方法就能创建对象，为什么还需要建造者模式来创建呢？建造者模式和工厂模式都可以创建对象，那它们两个的区别在哪里呢？

### 为什么需要建造者模式？

在平时的开发中，创建一个对象最常用的方式是，使用 new 关键字调用类的构造函数来完成。我的问题是，什么情况下这种方式就不适用了，就需要采用建造者模式来创建对象呢？你可以先思考一下，下面我通过一个例子来带你看一下。

假设有这样一道设计面试题：我们需要定义一个资源池配置类 ResourcePoolConfig。这里的资源池，你可以简单理解为线程池、连接池、对象池等。在这个资源池配置类中，有以下几个成员变量，也就是可配置项。现在，请你编写代码实现这个 ResourcePoolConfig 类。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210415220957.jpg" alt="img" style="zoom:50%;" />

只要你稍微有点开发经验，那实现这样一个类对你来说并不是件难事。最常见、最容易想到的实现思路如下代码所示。因为 maxTotal、maxIdle、minIdle 不是必填变量，所以在创建 ResourcePoolConfig 对象的时候，我们通过往构造函数中，给这几个参数传递 null 值，来表示使用默认值。

```java
public class ResourcePoolConfig {
  private static final int DEFAULT_MAX_TOTAL = 8;
  private static final int DEFAULT_MAX_IDLE = 8;
  private static final int DEFAULT_MIN_IDLE = 0;

  private String name;
  private int maxTotal = DEFAULT_MAX_TOTAL;
  private int maxIdle = DEFAULT_MAX_IDLE;
  private int minIdle = DEFAULT_MIN_IDLE;

  public ResourcePoolConfig(String name, Integer maxTotal, Integer maxIdle, Integer minIdle) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("name should not be empty.");
    }
    this.name = name;

    if (maxTotal != null) {
      if (maxTotal <= 0) {
        throw new IllegalArgumentException("maxTotal should be positive.");
      }
      this.maxTotal = maxTotal;
    }

    if (maxIdle != null) {
      if (maxIdle < 0) {
        throw new IllegalArgumentException("maxIdle should not be negative.");
      }
      this.maxIdle = maxIdle;
    }

    if (minIdle != null) {
      if (minIdle < 0) {
        throw new IllegalArgumentException("minIdle should not be negative.");
      }
      this.minIdle = minIdle;
    }
  }
  //...省略getter方法...
}
```

现在，ResourcePoolConfig 只有 4 个可配置项，对应到构造函数中，也只有 4 个参数，参数的个数不多。但是，如果可配置项逐渐增多，变成了 8 个、10 个，甚至更多，那继续沿用现在的设计思路，构造函数的参数列表会变得很长，代码在可读性和易用性上都会变差。在使用构造函数的时候，我们就容易搞错各参数的顺序，传递进错误的参数值，导致非常隐蔽的 bug。

```java
// 参数太多，导致可读性差、参数可能传递错误
ResourcePoolConfig config = new ResourcePoolConfig("dbconnectionpool", 16, null, 8, null, false , true, 10, 20，false， true);
```

解决这个问题的办法你应该也已经想到了，那就是用 set() 函数来给成员变量赋值，以替代冗长的构造函数。我们直接看代码，具体如下所示。其中，配置项 name 是必填的，所以我们把它放到构造函数中设置，强制创建类对象的时候就要填写。其他配置项 maxTotal、maxIdle、minIdle 都不是必填的，所以我们通过 set() 函数来设置，让使用者自主选择填写或者不填写。

```java
public class ResourcePoolConfig {
  private static final int DEFAULT_MAX_TOTAL = 8;
  private static final int DEFAULT_MAX_IDLE = 8;
  private static final int DEFAULT_MIN_IDLE = 0;

  private String name;
  private int maxTotal = DEFAULT_MAX_TOTAL;
  private int maxIdle = DEFAULT_MAX_IDLE;
  private int minIdle = DEFAULT_MIN_IDLE;
  
  public ResourcePoolConfig(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("name should not be empty.");
    }
    this.name = name;
  }

  public void setMaxTotal(int maxTotal) {
    if (maxTotal <= 0) {
      throw new IllegalArgumentException("maxTotal should be positive.");
    }
    this.maxTotal = maxTotal;
  }

  public void setMaxIdle(int maxIdle) {
    if (maxIdle < 0) {
      throw new IllegalArgumentException("maxIdle should not be negative.");
    }
    this.maxIdle = maxIdle;
  }

  public void setMinIdle(int minIdle) {
    if (minIdle < 0) {
      throw new IllegalArgumentException("minIdle should not be negative.");
    }
    this.minIdle = minIdle;
  }
  //...省略getter方法...
}
```

接下来，我们来看新的 ResourcePoolConfig 类该如何使用。我写了一个示例代码，如下所示。没有了冗长的函数调用和参数列表，代码在可读性和易用性上提高了很多。

```
// ResourcePoolConfig使用举例
ResourcePoolConfig config = new ResourcePoolConfig("dbconnectionpool");
config.setMaxTotal(16);
config.setMaxIdle(8);
```

至此，我们仍然没有用到建造者模式，通过构造函数设置必填项，通过 set() 方法设置可选配置项，就能实现我们的设计需求。如果我们把问题的难度再加大点，比如，还需要解决下面这三个问题，那现在的设计思路就不能满足了。

- 我们刚刚讲到，name 是必填的，所以，我们把它放到构造函数中，强制创建对象的时候就设置。如果**必填的配置项有很多**，把这些必填配置项都放到构造函数中设置，那构造函数就又会出现参数列表很长的问题。如果我们把必填项也通过 set() 方法设置，那校验这些必填项是否已经填写的逻辑就无处安放了。
- 除此之外，假设**配置项之间有一定的依赖关系**，比如，如果用户设置了 maxTotal、maxIdle、minIdle 其中一个，就必须显式地设置另外两个；或者配置项之间有一定的约束条件，比如，maxIdle 和 minIdle 要小于等于 maxTotal。如果我们继续使用现在的设计思路，那这些配置项之间的依赖关系或者约束条件的校验逻辑就无处安放了。
- 如果我们希望 ResourcePoolConfig 类对象是不可变对象，也就是说，对象在创建好之后，就不能再修改内部的属性值。要实现这个功能，我们就不能在 ResourcePoolConfig 类中暴露 set() 方法。

为了解决这些问题，建造者模式就派上用场了。

我们可以把校验逻辑放置到 Builder 类中，先创建建造者，并且通过 set() 方法设置建造者的变量值，然后在使用 build() 方法真正创建对象之前，做集中的校验，校验通过之后才会创建对象。除此之外，我们把 ResourcePoolConfig 的构造函数改为 private 私有权限。这样我们就只能通过建造者来创建 ResourcePoolConfig 类对象。并且，ResourcePoolConfig 没有提供任何 set() 方法，这样我们创建出来的对象就是不可变对象了。

我们用建造者模式重新实现了上面的需求，具体的代码如下所示：

```java
public class ResourcePoolConfig {
  private String name;
  private int maxTotal;
  private int maxIdle;
  private int minIdle;

  private ResourcePoolConfig(Builder builder) {
    this.name = builder.name;
    this.maxTotal = builder.maxTotal;
    this.maxIdle = builder.maxIdle;
    this.minIdle = builder.minIdle;
  }
  //...省略getter方法...

  //我们将Builder类设计成了ResourcePoolConfig的内部类。
  //我们也可以将Builder类设计成独立的非内部类ResourcePoolConfigBuilder。
  public static class Builder {
    private static final int DEFAULT_MAX_TOTAL = 8;
    private static final int DEFAULT_MAX_IDLE = 8;
    private static final int DEFAULT_MIN_IDLE = 0;

    private String name;
    private int maxTotal = DEFAULT_MAX_TOTAL;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;

    public ResourcePoolConfig build() {
      // 校验逻辑放到这里来做，包括必填项校验、依赖关系校验、约束条件校验等
      if (StringUtils.isBlank(name)) {
        throw new IllegalArgumentException("...");
      }
      if (maxIdle > maxTotal) {
        throw new IllegalArgumentException("...");
      }
      if (minIdle > maxTotal || minIdle > maxIdle) {
        throw new IllegalArgumentException("...");
      }

      return new ResourcePoolConfig(this);
    }

    public Builder setName(String name) {
      if (StringUtils.isBlank(name)) {
        throw new IllegalArgumentException("...");
      }
      this.name = name;
      return this;
    }

    public Builder setMaxTotal(int maxTotal) {
      if (maxTotal <= 0) {
        throw new IllegalArgumentException("...");
      }
      this.maxTotal = maxTotal;
      return this;
    }

    public Builder setMaxIdle(int maxIdle) {
      if (maxIdle < 0) {
        throw new IllegalArgumentException("...");
      }
      this.maxIdle = maxIdle;
      return this;
    }

    public Builder setMinIdle(int minIdle) {
      if (minIdle < 0) {
        throw new IllegalArgumentException("...");
      }
      this.minIdle = minIdle;
      return this;
    }
  }
}

// 这段代码会抛出IllegalArgumentException，因为minIdle>maxIdle
ResourcePoolConfig config = new ResourcePoolConfig.Builder()
        .setName("dbconnectionpool")
        .setMaxTotal(16)
        .setMaxIdle(10)
        .setMinIdle(12)
        .build();
```

实际上，使用建造者模式创建对象，还能避免对象存在无效状态。我再举个例子解释一下。比如我们定义了一个长方形类，如果不使用建造者模式，采用先创建后 set 的方式，那就会导致在第一个 set 之后，对象处于无效状态。具体代码如下所示：

```java
Rectangle r = new Rectange(); // r is invalid
r.setWidth(2); // r is invalid
r.setHeight(3); // r is valid
```

为了避免这种无效状态的存在，我们就需要使用构造函数一次性初始化好所有的成员变量。如果构造函数参数过多，我们就需要考虑使用建造者模式，先设置建造者的变量，然后再一次性地创建对象，让对象一直处于有效状态。

实际上，如果我们并不是很关心对象是否有短暂的无效状态，也不是太在意对象是否是可变的。比如，对象只是用来映射数据库读出来的数据，那我们直接暴露 set() 方法来设置类的成员变量值是完全没问题的。而且，使用建造者模式来构建对象，代码实际上是有点重复的，ResourcePoolConfig 类中的成员变量，要在 Builder 类中重新再定义一遍。

### 与工厂模式有何区别？

从上面的讲解中，我们可以看出，建造者模式是让建造者类来负责对象的创建工作。上一节课中讲到的工厂模式，是由工厂类来负责对象创建的工作。那它们之间有什么区别呢？

实际上，工厂模式是用来创建不同但是相关类型的对象（继承同一父类或者接口的一组子类），由给定的参数来决定创建哪种类型的对象。建造者模式是用来创建一种类型的复杂对象，通过设置不同的可选参数，“定制化”地创建不同的对象。

网上有一个经典的例子很好地解释了两者的区别。

顾客走进一家餐馆点餐，我们利用工厂模式，根据用户不同的选择，来制作不同的食物，比如披萨、汉堡、沙拉。对于披萨来说，用户又有各种配料可以定制，比如奶酪、西红柿、起司，我们通过建造者模式根据用户选择的不同配料来制作披萨。

实际上，我们也不要太学院派，非得把工厂模式、建造者模式分得那么清楚，我们需要知道的是，每个模式为什么这么设计，能解决什么问题。只有了解了这些最本质的东西，我们才能不生搬硬套，才能灵活应用，甚至可以混用各种模式创造出新的模式，来解决特定场景的问题。

### 重点回顾

建造者模式的原理和实现比较简单，重点是掌握应用场景，避免过度使用。

如果一个类中有很多属性，为了避免构造函数的参数列表过长，影响代码的可读性和易用性，我们可以通过构造函数配合 set() 方法来解决。但是，如果存在下面情况中的任意一种，我们就要考虑使用建造者模式了。

- 我们把类的必填属性放到构造函数中，强制创建对象的时候就设置。如果必填的属性有很多，把这些必填属性都放到构造函数中设置，那构造函数就又会出现参数列表很长的问题。如果我们把必填属性通过 set() 方法设置，那校验这些必填属性是否已经填写的逻辑就无处安放了。
- 如果类的属性之间有一定的依赖关系或者约束条件，我们继续使用构造函数配合 set() 方法的设计思路，那这些依赖关系或约束条件的校验逻辑就无处安放了。
- 如果我们希望创建不可变对象，也就是说，对象在创建好之后，就不能再修改内部的属性值，要实现这个功能，我们就不能在类中暴露 set() 方法。构造函数配合 set() 方法来设置属性值的方式就不适用了。

除此之外，在今天的讲解中，我们还对比了工厂模式和建造者模式的区别。工厂模式是用来创建**不同但是相关类型的对象**（继承同一父类或者接口的一组子类），由给定的参数来决定创建哪种类型的对象。建造者模式是用来创建一种类型的复杂对象，可以通过设置不同的可选参数，“定制化”地创建不同的对象。

### 课堂讨论

在下面的 ConstructorArg 类中，当 isRef 为 true 的时候，arg 表示 String 类型的 refBeanId，type 不需要设置；当 isRef 为 false 的时候，arg、type 都需要设置。请根据这个需求，完善 ConstructorArg 类。

```
public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;
    // TODO: 待完善...
  }
```

答案：

```java
public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;

    public boolean isRef() {
        return isRef;
    }

    public Class getType() {
        return type;
    }

    public Object getArg() {
        return arg;
    }

    private ConstructorArg(Builder builder) {
        this.isRef = builder.isRef;
        this.arg = builder.arg;
        this.type = builder.type;
    }

    @Override
    public String toString() {
        return "ConstructorArg{" +
                "isRef=" + isRef +
                ", type=" + type +
                ", arg=" + arg +
                '}';
    }

    public static class Builder {
        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg build() {
            if (isRef && type != null) {
                throw new IllegalArgumentException("...");
            }

            if (!isRef && type == null) {
                throw new IllegalArgumentException("...");
            }

            if (this.isRef && (arg != null && arg.getClass() != String.class)) {
                throw new IllegalArgumentException("...");
            }

            if (!this.isRef && arg == null) {
                throw new IllegalArgumentException("...");
            }
            return new ConstructorArg(this);
        }

        public Builder setRef(boolean ref) {
            this.isRef = ref;
            return this;
        }

        public Builder setType(Class type) {
            if (type == null) {
                throw new IllegalArgumentException("...");
            }
            this.type = type;
            return this;
        }

        public Builder setArg(Object arg) {
            if (arg == null) {
                throw new IllegalArgumentException("...");
            }
            this.arg = arg;
            return this;
        }
    }
}
```

测试：

```java
public class ConstructorArgTest {
    public static void main(String[] args) {
        ConstructorArg constructorArg = new ConstructorArg.Builder()
                .setArg("123")
                .setRef(false)
                .setType(Integer.class).build();
        System.out.println(constructorArg);
    }
}
```

![image-20210415234354361](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210415234358.png)

## 47 | 原型模式：如何最快速地clone一个HashMap散列表？

对于创建型模式，前面我们已经讲了单例模式、工厂模式、建造者模式，今天我们来讲最后一个：原型模式。

对于熟悉 JavaScript 语言的前端程序员来说，原型模式是一种比较常用的开发模式。这是因为，有别于 Java、C++ 等基于类的面向对象编程语言，JavaScript 是一种基于原型的面向对象编程语言。即便 JavaScript 现在也引入了类的概念，但它也只是基于原型的语法糖而已。不过，如果你熟悉的是 Java、C++ 等这些编程语言，那在实际的开发中，就很少用到原型模式了。

今天的讲解跟具体某一语言的语法机制无关，而是通过一个 clone 散列表的例子带你搞清楚：原型模式的应用场景，以及它的两种实现方式：深拷贝和浅拷贝。虽然原型模式的原理和代码实现非常简单，但今天举的例子还是稍微有点复杂的，你要跟上我的思路，多动脑思考一下。

### 原型模式的原理与应用

如果对象的创建成本比较大，而同一个类的不同对象之间差别不大（大部分字段都相同），在这种情况下，我们可以利用对已有对象（原型）进行复制（或者叫拷贝）的方式来创建新对象，以达到节省创建时间的目的。这种基于原型来创建对象的方式就叫作原型设计模式（Prototype Design Pattern），简称原型模式。

那何为“对象的创建成本比较大”？

实际上，创建对象包含的申请内存、给成员变量赋值这一过程，本身并不会花费太多时间，或者说对于大部分业务系统来说，这点时间完全是可以忽略的。应用一个复杂的模式，只得到一点点的性能提升，这就是所谓的过度设计，得不偿失。

但是，如果对象中的数据需要经过复杂的计算才能得到（比如排序、计算哈希值），或者需要从 RPC、网络、数据库、文件系统等非常慢速的 IO 中读取，这种情况下，我们就可以利用原型模式，从其他已有对象中直接拷贝得到，而不用每次在创建新对象的时候，都重复执行这些耗时的操作。

这么说还是比较理论，接下来，我们通过一个例子来解释一下刚刚这段话。

假设数据库中存储了大约 10 万条“搜索关键词”信息，每条信息包含关键词、关键词被搜索的次数、信息最近被更新的时间等。系统 A 在启动的时候会加载这份数据到内存中，用于处理某些其他的业务需求。为了方便快速地查找某个关键词对应的信息，我们给关键词建立一个散列表索引。

如果你熟悉的是 Java 语言，可以直接使用语言中提供的 HashMap 容器来实现。其中，HashMap 的 key 为搜索关键词，value 为关键词详细信息（比如搜索次数）。我们只需要将数据从数据库中读取出来，放入 HashMap 就可以了。

不过，我们还有另外一个系统 B，专门用来分析搜索日志，定期（比如间隔 10 分钟）批量地更新数据库中的数据，并且标记为新的数据版本。比如，在下面的示例图中，我们对 v2 版本的数据进行更新，得到 v3 版本的数据。这里我们假设只有更新和新添关键词，没有删除关键词的行为。

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210416000259.jpg)

为了保证系统 A 中数据的实时性（不一定非常实时，但数据也不能太旧），系统 A 需要定期根据数据库中的数据，更新内存中的索引和数据。

我们该如何实现这个需求呢？

实际上，也不难。我们只需要在系统 A 中，记录当前数据的版本 Va 对应的更新时间 Ta，从数据库中捞出更新时间大于 Ta 的所有搜索关键词，也就是找出 Va 版本与最新版本数据的“差集”，然后针对差集中的每个关键词进行处理。如果它已经在散列表中存在了，我们就更新相应的搜索次数、更新时间等信息；如果它在散列表中不存在，我们就将它插入到散列表中。

按照这个设计思路，我给出的示例代码如下所示：

```java
public class Demo {
  private ConcurrentHashMap<String, SearchWord> currentKeywords = new ConcurrentHashMap<>();
  private long lastUpdateTime = -1;

  public void refresh() {
    // 从数据库中取出更新时间>lastUpdateTime的数据，放入到currentKeywords中
    List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
    long maxNewUpdatedTime = lastUpdateTime;
    for (SearchWord searchWord : toBeUpdatedSearchWords) {
      if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
        maxNewUpdatedTime = searchWord.getLastUpdateTime();
      }
      if (currentKeywords.containsKey(searchWord.getKeyword())) {
        currentKeywords.replace(searchWord.getKeyword(), searchWord);
      } else {
        currentKeywords.put(searchWord.getKeyword(), searchWord);
      }
    }

    lastUpdateTime = maxNewUpdatedTime;
  }

  private List<SearchWord> getSearchWords(long lastUpdateTime) {
    // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
    return null;
  }
}
```

不过，现在，我们有一个特殊的要求：任何时刻，系统 A 中的所有数据都必须是同一个版本的，要么都是版本 a，要么都是版本 b，不能有的是版本 a，有的是版本 b。那刚刚的更新方式就不能满足这个要求了。除此之外，我们还要求：在更新内存数据的时候，系统 A 不能处于不可用状态，也就是不能停机更新数据。

那我们该如何实现现在这个需求呢？

实际上，也不难。我们把正在使用的数据的版本定义为“服务版本”，当我们要更新内存中的数据的时候，我们并不是直接在服务版本（假设是版本 a 数据）上更新，而是重新创建另一个版本数据（假设是版本 b 数据），等新的版本数据建好之后，再一次性地将服务版本从版本 a 切换到版本 b。这样既保证了数据一直可用，又避免了中间状态的存在。

```java
public class Demo {
  private HashMap<String, SearchWord> currentKeywords=new HashMap<>();

  public void refresh() {
    HashMap<String, SearchWord> newKeywords = new LinkedHashMap<>();

    // 从数据库中取出所有的数据，放入到newKeywords中
    List<SearchWord> toBeUpdatedSearchWords = getSearchWords();
    for (SearchWord searchWord : toBeUpdatedSearchWords) {
      newKeywords.put(searchWord.getKeyword(), searchWord);
    }

    currentKeywords = newKeywords;
  }

  private List<SearchWord> getSearchWords() {
    // TODO: 从数据库中取出所有的数据
    return null;
  }
}
```

不过，在上面的代码实现中，newKeywords 构建的成本比较高。我们需要将这 10 万条数据从数据库中读出，然后计算哈希值，构建 newKeywords。这个过程显然是比较耗时。为了提高效率，原型模式就派上用场了。

我们拷贝 currentKeywords 数据到 newKeywords 中，然后从数据库中只捞出新增或者有更新的关键词，更新到 newKeywords 中。而相对于 10 万条数据来说，每次新增或者更新的关键词个数是比较少的，所以，这种策略大大提高了数据更新的效率。

按照这个设计思路，我给出的示例代码如下所示：

```java
public class Demo {
  private HashMap<String, SearchWord> currentKeywords=new HashMap<>();
  private long lastUpdateTime = -1;

  public void refresh() {
    // 原型模式就这么简单，拷贝已有对象的数据，更新少量差值
    HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();

    // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
    List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
    long maxNewUpdatedTime = lastUpdateTime;
    for (SearchWord searchWord : toBeUpdatedSearchWords) {
      if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
        maxNewUpdatedTime = searchWord.getLastUpdateTime();
      }
      if (newKeywords.containsKey(searchWord.getKeyword())) {
        SearchWord oldSearchWord = newKeywords.get(searchWord.getKeyword());
        oldSearchWord.setCount(searchWord.getCount());
        oldSearchWord.setLastUpdateTime(searchWord.getLastUpdateTime());
      } else {
        newKeywords.put(searchWord.getKeyword(), searchWord);
      }
    }

    lastUpdateTime = maxNewUpdatedTime;
    currentKeywords = newKeywords;
  }

  private List<SearchWord> getSearchWords(long lastUpdateTime) {
    // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
    return null;
  }
}
```

这里我们利用了 Java 中的 clone() 语法来复制一个对象。如果你熟悉的语言没有这个语法，那把数据从 currentKeywords 中一个个取出来，然后再重新计算哈希值，放入到 newKeywords 中也是可以接受的。毕竟，最耗时的还是从数据库中取数据的操作。相对于数据库的 IO 操作来说，内存操作和 CPU 计算的耗时都是可以忽略的。

不过，不知道你有没有发现，实际上，刚刚的代码实现是有问题的。要弄明白到底有什么问题，我们需要先了解另外两个概念：深拷贝（Deep Copy）和浅拷贝（Shallow Copy）。

### 原型模式的实现方式：深拷贝和浅拷贝

我们来看，在内存中，用散列表组织的搜索关键词信息是如何存储的。我画了一张示意图，大致结构如下所示。从图中我们可以发现，散列表索引中，每个结点存储的 key 是搜索关键词，value 是 SearchWord 对象的内存地址。SearchWord 对象本身存储在散列表之外的内存空间中。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210416175051.jpg" alt="img" style="zoom:25%;" />

浅拷贝和深拷贝的区别在于，浅拷贝只会复制图中的索引（散列表），不会复制数据（SearchWord 对象）本身。相反，深拷贝不仅仅会复制索引，还会复制数据本身。浅拷贝得到的对象（newKeywords）跟原始对象（currentKeywords）共享数据（SearchWord 对象），而深拷贝得到的是一份完完全全独立的对象。具体的对比如下图所示：

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210416175905.jpg)

在 Java 语言中，Object 类的 clone() 方法执行的就是我们刚刚说的浅拷贝。它只会拷贝对象中的基本数据类型的数据（比如，int、long），以及引用对象（SearchWord）的内存地址，不会递归地拷贝引用对象本身。

在上面的代码中，我们通过调用 HashMap 上的 clone() 浅拷贝方法来实现原型模式。当我们通过 newKeywords 更新 SearchWord 对象的时候（比如，更新“设计模式”这个搜索关键词的访问次数），newKeywords 和 currentKeywords 因为指向相同的一组 SearchWord 对象，就会导致 currentKeywords 中指向的 SearchWord，有的是老版本的，有的是新版本的，就没法满足我们之前的需求：currentKeywords 中的数据在任何时刻都是同一个版本的，不存在介于老版本与新版本之间的中间状态。

现在，我们又该如何来解决这个问题呢？

我们可以将浅拷贝替换为深拷贝。newKeywords 不仅仅复制 currentKeywords 的索引，还把 SearchWord 对象也复制一份出来，这样 newKeywords 和 currentKeywords 就指向不同的 SearchWord 对象，也就不存在更新 newKeywords 的数据会导致 currentKeywords 的数据也被更新的问题了。

那如何实现深拷贝呢？总结一下的话，有下面两种方法。

第一种方法：递归拷贝对象、对象的引用对象以及引用对象的引用对象……直到要拷贝的对象只包含基本数据类型数据，没有引用对象为止。根据这个思路对之前的代码进行重构。重构之后的代码如下所示：

```java
public class Demo {
  private HashMap<String, SearchWord> currentKeywords=new HashMap<>();
  private long lastUpdateTime = -1;

  public void refresh() {
    // Deep copy
    HashMap<String, SearchWord> newKeywords = new HashMap<>();
    for (HashMap.Entry<String, SearchWord> e : currentKeywords.entrySet()) {
      SearchWord searchWord = e.getValue();
      SearchWord newSearchWord = new SearchWord(
              searchWord.getKeyword(), searchWord.getCount(), searchWord.getLastUpdateTime());
      newKeywords.put(e.getKey(), newSearchWord);
    }

    // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
    List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
    long maxNewUpdatedTime = lastUpdateTime;
    for (SearchWord searchWord : toBeUpdatedSearchWords) {
      if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
        maxNewUpdatedTime = searchWord.getLastUpdateTime();
      }
      if (newKeywords.containsKey(searchWord.getKeyword())) {
        SearchWord oldSearchWord = newKeywords.get(searchWord.getKeyword());
        oldSearchWord.setCount(searchWord.getCount());
        oldSearchWord.setLastUpdateTime(searchWord.getLastUpdateTime());
      } else {
        newKeywords.put(searchWord.getKeyword(), searchWord);
      }
    }

    lastUpdateTime = maxNewUpdatedTime;
    currentKeywords = newKeywords;
  }

  private List<SearchWord> getSearchWords(long lastUpdateTime) {
    // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
    return null;
  }

}
```

第二种方法：先将对象序列化，然后再反序列化成新的对象。具体的示例代码如下所示：

```java
public Object deepCopy(Object object) {
  ByteArrayOutputStream bo = new ByteArrayOutputStream();
  ObjectOutputStream oo = new ObjectOutputStream(bo);
  oo.writeObject(object);
  
  ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
  ObjectInputStream oi = new ObjectInputStream(bi);
  
  return oi.readObject();
}
```

刚刚的两种实现方法，不管采用哪种，深拷贝都要比浅拷贝耗时、耗内存空间。针对我们这个应用场景，有没有更快、更省内存的实现方式呢？

我们可以先采用浅拷贝的方式创建 newKeywords。对于需要更新的 SearchWord 对象，我们再使用深度拷贝的方式创建一份新的对象，替换 newKeywords 中的老对象。毕竟需要更新的数据是很少的。这种方式即利用了浅拷贝节省时间、空间的优点，又能保证 currentKeywords 中的中数据都是老版本的数据。具体的代码实现如下所示。这也是标题中讲到的，在我们这个应用场景下，最快速 clone 散列表的方式。

```java
public class Demo {
  private HashMap<String, SearchWord> currentKeywords=new HashMap<>();
  private long lastUpdateTime = -1;

  public void refresh() {
    // Shallow copy
    HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();

    // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
    List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
    long maxNewUpdatedTime = lastUpdateTime;
    for (SearchWord searchWord : toBeUpdatedSearchWords) {
      if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
        maxNewUpdatedTime = searchWord.getLastUpdateTime();
      }
      if (newKeywords.containsKey(searchWord.getKeyword())) {
        newKeywords.remove(searchWord.getKeyword());
      }
      newKeywords.put(searchWord.getKeyword(), searchWord);
    }

    lastUpdateTime = maxNewUpdatedTime;
    currentKeywords = newKeywords;
  }

  private List<SearchWord> getSearchWords(long lastUpdateTime) {
    // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
    return null;
  }
}
```

### 重点回顾

1. 什么是原型模式？

如果对象的创建成本比较大，而同一个类的不同对象之间差别不大（大部分字段都相同），在这种情况下，我们可以利用对已有对象（原型）进行复制（或者叫拷贝）的方式，来创建新对象，以达到节省创建时间的目的。这种基于原型来创建对象的方式就叫作原型设计模式，简称原型模式。

2. 原型模式的两种实现方法

原型模式有两种实现方法，深拷贝和浅拷贝。浅拷贝只会复制对象中基本数据类型数据和引用对象的内存地址，不会递归地复制引用对象，以及引用对象的引用对象……而深拷贝得到的是一份完完全全独立的对象。所以，深拷贝比起浅拷贝来说，更加耗时，更加耗内存空间。

如果要拷贝的对象是不可变对象，浅拷贝共享不可变对象是没问题的，但对于可变对象来说，浅拷贝得到的对象和原始对象会共享部分数据，就有可能出现数据被修改的风险，也就变得复杂多了。除非像我们今天实战中举的那个例子，需要从数据库中加载 10 万条数据并构建散列表索引，操作非常耗时，这种情况下比较推荐使用浅拷贝，否则，没有充分的理由，不要为了一点点的性能提升而使用浅拷贝。

# 设计模式与范式：结构型

## 48 | 代理模式：代理在RPC、缓存、监控等场景中的应用

前面几节，我们学习了设计模式中的创建型模式。**创建型模式主要解决对象的创建问题，封装复杂的创建过程，解耦对象的创建代码和使用代码。**

**其中，单例模式用来创建全局唯一的对象。工厂模式用来创建不同但是相关类型的对象（继承同一父类或者接口的一组子类），由给定的参数来决定创建哪种类型的对象。建造者模式是用来创建复杂对象，可以通过设置不同的可选参数，“定制化”地创建不同的对象。原型模式针对创建成本比较大的对象，利用对已有对象进行复制的方式进行创建，以达到节省创建时间的目的。**

从今天起，我们开始学习另外一种类型的设计模式：结构型模式。**结构型模式主要总结了一些类或对象组合在一起的经典结构**，这些经典的结构可以解决特定应用场景的问题。结构型模式包括：代理模式、桥接模式、装饰器模式、适配器模式、门面模式、组合模式、享元模式。今天我们要讲其中的代理模式。它也是在实际开发中经常被用到的一种设计模式。

### 代理模式的原理解析

代理模式（Proxy Design Pattern）的原理和代码实现都不难掌握。它在不改变原始类（或叫被代理类）代码的情况下，通过引入代理类来给原始类附加功能。我们通过一个简单的例子来解释一下这段话。

这个例子来自我们在第 25、26、39、40 节中讲的性能计数器。当时我们开发了一个 MetricsCollector 类，用来收集接口请求的原始数据，比如访问时间、处理时长等。在业务系统中，我们采用如下方式来使用这个 MetricsCollector 类：

```java
public class UserController {
  //...省略其他属性和方法...
  private MetricsCollector metricsCollector; // 依赖注入

  public UserVo login(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    // ... 省略login逻辑...

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    //...返回UserVo数据...
  }

  public UserVo register(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    // ... 省略register逻辑...

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    //...返回UserVo数据...
  }
}
```

很明显，上面的写法有两个问题。第一，性能计数器框架代码侵入到业务代码中，跟业务代码高度耦合。如果未来需要替换这个框架，那替换的成本会比较大。第二，收集接口请求的代码跟业务代码无关，本就不应该放到一个类中。业务类最好职责更加单一，只聚焦业务处理。

为了将框架代码和业务代码解耦，代理模式就派上用场了。代理类 UserControllerProxy 和原始类 UserController 实现相同的接口 IUserController。UserController 类只负责业务功能。代理类 UserControllerProxy 负责在业务代码执行前后附加其他逻辑代码，并通过委托的方式调用原始类来执行业务代码。具体的代码实现如下所示：

```java
public interface IUserController {
  UserVo login(String telephone, String password);
  UserVo register(String telephone, String password);
}

public class UserController implements IUserController {
  //...省略其他属性和方法...

  @Override
  public UserVo login(String telephone, String password) {
    //...省略login逻辑...
    //...返回UserVo数据...
  }

  @Override
  public UserVo register(String telephone, String password) {
    //...省略register逻辑...
    //...返回UserVo数据...
  }
}

public class UserControllerProxy implements IUserController {
  private MetricsCollector metricsCollector;
  private UserController userController;

  public UserControllerProxy(UserController userController) {
    this.userController = userController;
    this.metricsCollector = new MetricsCollector();
  }

  @Override
  public UserVo login(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    // 委托
    UserVo userVo = userController.login(telephone, password);

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    return userVo;
  }

  @Override
  public UserVo register(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    UserVo userVo = userController.register(telephone, password);

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    return userVo;
  }
}

//UserControllerProxy使用举例
//因为原始类和代理类实现相同的接口，是基于接口而非实现编程
//将UserController类对象替换为UserControllerProxy类对象，不需要改动太多代码
IUserController userController = new UserControllerProxy(new UserController());
```

参照基于接口而非实现编程的设计思想，将原始类对象替换为代理类对象的时候，为了让代码改动尽量少，在刚刚的代理模式的代码实现中，**代理类和原始类需要实现相同的接口**。但是，如果原始类并没有定义接口，并且原始类代码并不是我们开发维护的（比如它来自一个第三方的类库），我们也没办法直接修改原始类，给它重新定义一个接口。在这种情况下，我们该如何实现代理模式呢？

对于这种外部类的扩展，我们一般都是采用继承的方式。这里也不例外。我们让代理类继承原始类，然后扩展附加功能。原理很简单，不需要过多解释，你直接看代码就能明白。具体代码如下所示：

```java
public class UserControllerProxy extends UserController {
  private MetricsCollector metricsCollector;

  public UserControllerProxy() {
    this.metricsCollector = new MetricsCollector();
  }

  public UserVo login(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    UserVo userVo = super.login(telephone, password);

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("login", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    return userVo;
  }

  public UserVo register(String telephone, String password) {
    long startTimestamp = System.currentTimeMillis();

    UserVo userVo = super.register(telephone, password);

    long endTimeStamp = System.currentTimeMillis();
    long responseTime = endTimeStamp - startTimestamp;
    RequestInfo requestInfo = new RequestInfo("register", responseTime, startTimestamp);
    metricsCollector.recordRequest(requestInfo);

    return userVo;
  }
}
//UserControllerProxy使用举例
UserController userController = new UserControllerProxy();
```

### 动态代理的原理解析

不过，刚刚的代码实现还是有点问题。一方面，我们需要在代理类中，将原始类中的所有的方法，都重新实现一遍，并且为每个方法都附加相似的代码逻辑。另一方面，如果要添加的附加功能的类有不止一个，我们需要针对每个类都创建一个代理类。

如果有 50 个要添加附加功能的原始类，那我们就要创建 50 个对应的代理类。这会导致项目中类的个数成倍增加，增加了代码维护成本。并且，每个代理类中的代码都有点像模板式的“重复”代码，也增加了不必要的开发成本。那这个问题怎么解决呢？

我们可以使用动态代理来解决这个问题。所谓动态代理（Dynamic Proxy），就是我们不事先为每个原始类编写代理类，而是在运行的时候，动态地创建原始类对应的代理类，然后在系统中用代理类替换掉原始类。那如何实现动态代理呢？

如果你熟悉的是 Java 语言，实现动态代理就是件很简单的事情。因为 Java 语言本身就已经提供了动态代理的语法（实际上，动态代理底层依赖的就是 Java 的反射语法）。我们来看一下，如何用 Java 的动态代理来实现刚刚的功能。具体的代码如下所示。其中，MetricsCollectorProxy 作为一个动态代理类，动态地给每个需要收集接口请求信息的类创建代理类。

```java
public class MetricsCollectorProxy {
  private MetricsCollector metricsCollector;

  public MetricsCollectorProxy() {
    this.metricsCollector = new MetricsCollector();
  }

  public Object createProxy(Object proxiedObject) {
    Class<?>[] interfaces = proxiedObject.getClass().getInterfaces();
    DynamicProxyHandler handler = new DynamicProxyHandler(proxiedObject);
    return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfaces, handler);
  }

  private class DynamicProxyHandler implements InvocationHandler {
    private Object proxiedObject;

    public DynamicProxyHandler(Object proxiedObject) {
      this.proxiedObject = proxiedObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      long startTimestamp = System.currentTimeMillis();
      Object result = method.invoke(proxiedObject, args);
      long endTimeStamp = System.currentTimeMillis();
      long responseTime = endTimeStamp - startTimestamp;
      String apiName = proxiedObject.getClass().getName() + ":" + method.getName();
      RequestInfo requestInfo = new RequestInfo(apiName, responseTime, startTimestamp);
      metricsCollector.recordRequest(requestInfo);
      return result;
    }
  }
}

//MetricsCollectorProxy使用举例
MetricsCollectorProxy proxy = new MetricsCollectorProxy();
IUserController userController = (IUserController) proxy.createProxy(new UserController());
```

实际上，Spring AOP 底层的实现原理就是基于动态代理。用户配置好需要给哪些类创建代理，并定义好在执行原始类的业务代码前后执行哪些附加功能。Spring 为这些类创建动态代理对象，并在 JVM 中替代原始类对象。原本在代码中执行的原始类的方法，被换作执行代理类的方法，也就实现了**给原始类添加附加功能**的目的。

### 代理模式的应用场景

代理模式的应用场景非常多，我这里列举一些比较常见的用法，希望你能举一反三地应用在你的项目开发中。

1. 业务系统的非功能性需求开发

代理模式最常用的一个应用场景就是，在业务系统中开发一些非功能性需求，比如：监控、统计、鉴权、限流、事务、幂等、日志。我们将这些附加功能与业务功能解耦，放到代理类中统一处理，让程序员只需要关注业务方面的开发。实际上，前面举的搜集接口请求信息的例子，就是这个应用场景的一个典型例子。

2. 代理模式在 RPC、缓存中的应用

实际上，RPC 框架也可以看作一种代理模式，GoF 的《设计模式》一书中把它称作远程代理。通过远程代理，将网络通信、数据编解码等细节隐藏起来。客户端在使用 RPC 服务的时候，就像使用本地函数一样，无需了解跟服务器交互的细节。除此之外，RPC 服务的开发者也只需要开发业务逻辑，就像开发本地使用的函数一样，不需要关注跟客户端的交互细节。

我们再来看代理模式在缓存中的应用。假设我们要开发一个接口请求的缓存功能，对于某些接口请求，如果入参相同，在设定的过期时间内，直接返回缓存结果，而不用重新进行逻辑处理。比如，针对获取用户个人信息的需求，我们可以开发两个接口，一个支持缓存，一个支持实时查询。对于需要实时数据的需求，我们让其调用实时查询接口，对于不需要实时数据的需求，我们让其调用支持缓存的接口。那如何来实现接口请求的缓存功能呢？

最简单的实现方法就是刚刚我们讲到的，给每个需要支持缓存的查询需求都开发两个不同的接口，一个支持缓存，一个支持实时查询。但是，这样做显然增加了开发成本，而且会让代码看起来非常臃肿（接口个数成倍增加），也不方便缓存接口的集中管理（增加、删除缓存接口）、集中配置（比如配置每个接口缓存过期时间）。

针对这些问题，代理模式就能派上用场了，确切地说，应该是动态代理。如果是基于 Spring 框架来开发的话，那就可以在 AOP 切面中完成接口缓存的功能。在应用启动的时候，我们从配置文件中加载需要支持缓存的接口，以及相应的缓存策略（比如过期时间）等。当请求到来的时候，我们在 AOP 切面中拦截请求，如果请求中带有支持缓存的字段（比如 http://…?..&cached=true），我们便从缓存（内存缓存或者 Redis 缓存等）中获取数据直接返回。

### 重点回顾

1. 代理模式的原理与实现

在不改变原始类（或叫被代理类）的情况下，通过引入代理类来给原始类附加功能。一般情况下，我们让代理类和原始类实现同样的接口。但是，如果原始类并没有定义接口，并且原始类代码并不是我们开发维护的。在这种情况下，我们可以通过让代理类继承原始类的方法来实现代理模式。

2. 动态代理的原理与实现

静态代理需要针对每个类都创建一个代理类，并且每个代理类中的代码都有点像模板式的“重复”代码，增加了维护成本和开发成本。对于静态代理存在的问题，我们可以通过动态代理来解决。我们不事先为每个原始类编写代理类，而是在运行的时候动态地创建原始类对应的代理类，然后在系统中用代理类替换掉原始类。

3. 代理模式的应用场景代理

模式常用在业务系统中开发一些非功能性需求，比如：监控、统计、鉴权、限流、事务、幂等、日志。我们将这些附加功能与业务功能解耦，放到代理类统一处理，让程序员只需要关注业务方面的开发。除此之外，代理模式还可以用在 RPC、缓存等应用场景中。

### 课堂讨论

1. 除了 Java 语言之外，在你熟悉的其他语言中，如何实现动态代理呢？
2. 我们今天讲了两种代理模式的实现方法，一种是基于组合，一种基于继承，请对比一下两者的优缺点。

## 49 | 桥接模式：如何实现支持不同类型和渠道的消息推送系统？

上一节课我们学习了第一种结构型模式：代理模式。它在不改变原始类（或者叫被代理类）代码的情况下，通过引入代理类来给原始类附加功能。代理模式在平时的开发经常被用到，常用在业务系统中开发一些非功能性需求，比如：监控、统计、鉴权、限流、事务、幂等、日志。

今天，我们再学习另外一种结构型模式：桥接模式。桥接模式的代码实现非常简单，但是理解起来稍微有点难度，并且应用场景也比较局限，所以，相当于代理模式来说，桥接模式在实际的项目中并没有那么常用，你只需要简单了解，见到能认识就可以，并不是我们学习的重点。

### 桥接模式的原理解析

## 50 | 装饰器模式：通过剖析Java IO类库源码学习装饰器模式

### Java IO 类的“奇怪”用法

Java IO 类库非常庞大和复杂，有几十个类，负责 IO 数据的读取和写入。如果对 Java IO 类做一下分类，我们可以从下面两个维度将它划分为四类。具体如下所示：

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210417160415.jpg)

针对不同的读取和写入场景，Java IO 又在这四个父类基础之上，扩展出了很多子类。具体如下所示：

![img](https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210417160441.jpg)

在我初学 Java 的时候，曾经对 Java IO 的一些用法产生过很大疑惑，比如下面这样一段代码。我们打开文件 test.txt，从中读取数据。其中，InputStream 是一个抽象类，FileInputStream 是专门用来读取文件流的子类。BufferedInputStream 是一个支持带缓存功能的数据读取类，可以提高数据读取的效率。

```java
InputStream in = new FileInputStream("/user/wangzheng/test.txt");
InputStream bin = new BufferedInputStream(in);
byte[] data = new byte[128];
while (bin.read(data) != -1) {
  //...
}
```

初看上面的代码，我们会觉得 Java IO 的用法比较麻烦，需要先创建一个 FileInputStream 对象，然后再传递给 BufferedInputStream 对象来使用。我在想，Java IO 为什么不设计一个继承 FileInputStream 并且支持缓存的 BufferedFileInputStream 类呢？这样我们就可以像下面的代码中这样，直接创建一个 BufferedFileInputStream 类对象，打开文件读取数据，用起来岂不是更加简单？

```
InputStream bin = new BufferedFileInputStream("/user/wangzheng/test.txt");
byte[] data = new byte[128];
while (bin.read(data) != -1) {
  //...
}
```

### 基于继承的设计方案

如果 InputStream 只有一个子类 FileInputStream 的话，那我们在 FileInputStream 基础之上，再设计一个孙子类 BufferedFileInputStream，也算是可以接受的，毕竟继承结构还算简单。但实际上，继承 InputStream 的子类有很多。我们需要给每一个 InputStream 的子类，再继续派生支持缓存读取的子类。

除了支持缓存读取之外，如果我们还需要对功能进行其他方面的增强，比如下面的 DataInputStream 类，支持按照基本数据类型（int、boolean、long 等）来读取数据。

```
FileInputStream in = new FileInputStream("/user/wangzheng/test.txt");
DataInputStream din = new DataInputStream(in);
int data = din.readInt();
```

在这种情况下，如果我们继续按照继承的方式来实现的话，就需要再继续派生出 DataFileInputStream、DataPipedInputStream 等类。如果我们还需要既支持缓存、又支持按照基本类型读取数据的类，那就要再继续派生出 BufferedDataFileInputStream、BufferedDataPipedInputStream 等 n 多类。这还只是附加了两个增强功能，如果我们需要附加更多的增强功能，那就会导致组合爆炸，类继承结构变得无比复杂，代码既不好扩展，也不好维护。这也是我们在第 10 节中讲的不推荐使用继承的原因。

### 基于装饰器模式的设计方案

在第 10 节中，我们还讲到“组合优于继承”，可以“使用组合来替代继承”。针对刚刚的继承结构过于复杂的问题，我们可以通过将继承关系改为组合关系来解决。下面的代码展示了 Java IO 的这种设计思路。不过，我对代码做了简化，只抽象出了必要的代码结构，如果你感兴趣的话，可以直接去查看 JDK 源码。

```java
public abstract class InputStream {
  //...
  public int read(byte b[]) throws IOException {
    return read(b, 0, b.length);
  }
  
  public int read(byte b[], int off, int len) throws IOException {
    //...
  }
  
  public long skip(long n) throws IOException {
    //...
  }

  public int available() throws IOException {
    return 0;
  }
  
  public void close() throws IOException {}

  public synchronized void mark(int readlimit) {}
    
  public synchronized void reset() throws IOException {
    throw new IOException("mark/reset not supported");
  }

  public boolean markSupported() {
    return false;
  }
}

public class BufferedInputStream extends InputStream {
  protected volatile InputStream in;

  protected BufferedInputStream(InputStream in) {
    this.in = in;
  }
  
  //...实现基于缓存的读数据接口...  
}

public class DataInputStream extends InputStream {
  protected volatile InputStream in;

  protected DataInputStream(InputStream in) {
    this.in = in;
  }
  
  //...实现读取基本类型数据的接口
}
```

(我没看出来)看了上面的代码，你可能会问，那装饰器模式就是简单的“用组合替代继承”吗？当然不是。从 Java IO 的设计来看，装饰器模式相对于简单的组合关系，还有两个比较特殊的地方。

第一个比较特殊的地方是：装饰器类和原始类继承同样的父类，这样我们可以对原始类“嵌套”多个装饰器类。比如，下面这样一段代码，我们对 FileInputStream 嵌套了两个装饰器类：BufferedInputStream 和 DataInputStream，让它既支持缓存读取，又支持按照基本数据类型来读取数据。

```java
InputStream in = new FileInputStream("/user/wangzheng/test.txt");
InputStream bin = new BufferedInputStream(in);
DataInputStream din = new DataInputStream(bin);
int data = din.readInt();
```

第二个比较特殊的地方是：装饰器类是对功能的增强，这也是装饰器模式应用场景的一个重要特点。实际上，符合“组合关系”这种代码结构的设计模式有很多，比如之前讲过的代理模式、桥接模式，还有现在的装饰器模式。尽管它们的代码结构很相似，但是每种设计模式的意图是不同的。就拿比较相似的代理模式和装饰器模式来说吧，**代理模式中，代理类附加的是跟原始类无关的功能，而在装饰器模式中，装饰器类附加的是跟原始类相关的增强功能。**

```java
// 代理模式的代码结构(下面的接口也可以替换成抽象类)
public interface IA {
  void f();
}
public class A impelements IA {
  public void f() { //... }
}
public class AProxy implements IA {
  private IA a;
  public AProxy(IA a) {
    this.a = a;
  }
  
  public void f() {
    // 新添加的代理逻辑
    a.f();
    // 新添加的代理逻辑
  }
}

// 装饰器模式的代码结构(下面的接口也可以替换成抽象类)
public interface IA {
  void f();
}
public class A implements IA {
  public void f() { //... }
}
public class ADecorator implements IA {
  private IA a;
  public ADecorator(IA a) {
    this.a = a;
  }
  
  public void f() {
    // 功能增强代码
    a.f();
    // 功能增强代码
  }
}
```

实际上，如果去查看 JDK 的源码，你会发现，BufferedInputStream、DataInputStream 并非继承自 InputStream，而是另外一个叫 FilterInputStream 的类。那这又是出于什么样的设计意图，才引入这样一个类呢？

我们再重新来看一下 BufferedInputStream 类的代码。InputStream 是一个抽象类而非接口，而且它的大部分函数（比如 read()、available()）都有默认实现，按理来说，我们只需要在 BufferedInputStream 类中重新实现那些需要增加缓存功能的函数就可以了，其他函数继承 InputStream 的默认实现。但实际上，这样做是行不通的。

对于即便是不需要增加缓存功能的函数来说，BufferedInputStream 还是必须把它重新实现一遍，简单包裹对 InputStream 对象的函数调用。具体的代码示例如下所示。如果不重新实现，那 BufferedInputStream 类就无法将最终读取数据的任务，委托给传递进来的 InputStream 对象来完成。这一部分稍微有点不好理解，你自己多思考一下。

### 重点回顾

装饰器模式主要解决继承关系过于复杂的问题，通过组合来替代继承。它主要的作用是给原始类添加增强功能。这也是判断是否该用装饰器模式的一个重要的依据。除此之外，装饰器模式还有一个特点，那就是可以对原始类嵌套使用多个装饰器。为了满足这个应用场景，在设计的时候，装饰器类需要跟原始类继承相同的抽象类或者接口。



## 51 | 适配器模式：代理、适配器、桥接、装饰，这四个模式有何区别？

前面几节课我们学习了代理模式、桥接模式、装饰器模式，今天，我们再来学习一个比较常用的结构型模式：适配器模式。这个模式相对来说还是比较简单、好理解的，应用场景也很具体，总体上来讲比较好掌握。

关于适配器模式，今天我们主要学习它的两种实现方式，类适配器和对象适配器，以及 5 种常见的应用场景。同时，我还会通过剖析 slf4j 日志框架，来给你展示这个模式在真实项目中的应用。除此之外，在文章的最后，我还对代理、桥接、装饰器、适配器，这 4 种代码结构非常相似的设计模式做简单的对比，对这几节内容做一个简单的总结。

### **适配器模式的原理与实现**

适配器模式的英文翻译是 Adapter Design Pattern。顾名思义，这个模式就是用来做适配的，它将不兼容的接口转换为可兼容的接口，让原本由于接口不兼容而不能一起工作的类可以一起工作。对于这个模式，有一个经常被拿来解释它的例子，就是 USB 转接头充当适配器，把两种不兼容的接口，通过转接变得可以一起工作。

原理很简单，我们再来看下它的代码实现。适配器模式有两种实现方式：类适配器和对象适配器。其中，类适配器使用继承关系来实现，对象适配器使用组合关系来实现。具体的代码实现如下所示。其中，ITarget 表示要转化成的接口定义。Adaptee 是一组不兼容 ITarget 接口定义的接口，Adaptor 将 Adaptee 转化成一组符合 ITarget 接口定义的接口。

```java
// 类适配器: 基于继承
public interface ITarget {
  void f1();
  void f2();
  void fc();
}

public class Adaptee {
  public void fa() { //... }
  public void fb() { //... }
  public void fc() { //... }
}

public class Adaptor extends Adaptee implements ITarget {
  public void f1() {
    super.fa();
  }
  
  public void f2() {
    //...重新实现f2()...
  }
  
  // 这里fc()不需要实现，直接继承自Adaptee，这是跟对象适配器最大的不同点
}

// 对象适配器：基于组合
public interface ITarget {
  void f1();
  void f2();
  void fc();
}

public class Adaptee {
  public void fa() { //... }
  public void fb() { //... }
  public void fc() { //... }
}

public class Adaptor implements ITarget {
  private Adaptee adaptee;
  
  public Adaptor(Adaptee adaptee) {
    this.adaptee = adaptee;
  }
  
  public void f1() {
    adaptee.fa(); //委托给Adaptee
  }
  
  public void f2() {
    //...重新实现f2()...
  }
  
  public void fc() {
    adaptee.fc();
  }
}
```

针对这两种实现方式，在实际的开发中，到底该如何选择使用哪一种呢？判断的标准主要有两个，一个是 Adaptee 接口的个数，另一个是 Adaptee 和 ITarget 的契合程度。

- 如果 Adaptee 接口并不多，那两种实现方式都可以。
- 如果 Adaptee 接口很多，而且 Adaptee 和 ITarget 接口定义大部分都相同，那我们推荐使用类适配器，因为 Adaptor 复用父类 Adaptee 的接口，比起对象适配器的实现方式，Adaptor 的代码量要少一些。如果 Adaptee 接口很多，而且 Adaptee 和 ITarget 接口定义大部分都不相同，那我们推荐使用对象适配器，因为组合结构相对于继承更加灵活。
- 如果 Adaptee 接口很多，而且 Adaptee 和 ITarget 接口定义大部分都不相同，那我们推荐使用对象适配器，因为组合结构相对于继承更加灵活。

### 适配器模式应用场景总结

原理和实现讲完了，都不复杂。我们再来看，到底什么时候会用到适配器模式呢？

一般来说，适配器模式可以看作一种“补偿模式”，用来补救设计上的缺陷。应用这种模式算是“无奈之举”。如果在设计初期，我们就能协调规避接口不兼容的问题，那这种模式就没有应用的机会了。

前面我们反复提到，适配器模式的应用场景是“接口不兼容”。那在实际的开发中，什么情况下才会出现接口不兼容呢？我建议你先自己思考一下这个问题，然后再来看我下面的总结 。

1. 封装有缺陷的接口设计

假设我们依赖的外部系统在接口设计方面有缺陷（比如包含大量静态方法），引入之后会影响到我们自身代码的可测试性。为了隔离设计上的缺陷，我们希望对外部系统提供的接口进行二次封装，抽象出更好的接口设计，这个时候就可以使用适配器模式了。

具体我还是举个例子来解释一下，你直接看代码应该会更清晰。具体代码如下所示：

```java
public class CD { //这个类来自外部sdk，我们无权修改它的代码
  //...
  public static void staticFunction1() { //... }
  
  public void uglyNamingFunction2() { //... }

  public void tooManyParamsFunction3(int paramA, int paramB, ...) { //... }
  
   public void lowPerformanceFunction4() { //... }
}

// 使用适配器模式进行重构
public class ITarget {
  void function1();
  void function2();
  void fucntion3(ParamsWrapperDefinition paramsWrapper);
  void function4();
  //...
}
// 注意：适配器类的命名不一定非得末尾带Adaptor
public class CDAdaptor extends CD implements ITarget {
  //...
  public void function1() {
     super.staticFunction1();
  }
  
  public void function2() {
    super.uglyNamingFucntion2();
  }
  
  public void function3(ParamsWrapperDefinition paramsWrapper) {
     super.tooManyParamsFunction3(paramsWrapper.getParamA(), ...);
  }
  
  public void function4() {
    //...reimplement it...
  }
}
```

2. 统一多个类的接口设计

某个功能的实现依赖多个外部系统（或者说类）。通过适配器模式，将它们的接口适配为统一的接口定义，然后我们就可以使用多态的特性来复用代码逻辑。具体我还是举个例子来解释一下。

假设我们的系统要对用户输入的文本内容做敏感词过滤，为了提高过滤的召回率，我们引入了多款第三方敏感词过滤系统，依次对用户输入的内容进行过滤，过滤掉尽可能多的敏感词。但是，每个系统提供的过滤接口都是不同的。这就意味着我们没法复用一套逻辑来调用各个系统。这个时候，我们就可以使用适配器模式，将所有系统的接口适配为统一的接口定义，这样我们可以复用调用敏感词过滤的代码。

你可以配合着下面的代码示例，来理解我刚才举的这个例子。

```java
public class ASensitiveWordsFilter { // A敏感词过滤系统提供的接口
  //text是原始文本，函数输出用***替换敏感词之后的文本
  public String filterSexyWords(String text) {
    // ...
  }
  
  public String filterPoliticalWords(String text) {
    // ...
  } 
}

public class BSensitiveWordsFilter  { // B敏感词过滤系统提供的接口
  public String filter(String text) {
    //...
  }
}

public class CSensitiveWordsFilter { // C敏感词过滤系统提供的接口
  public String filter(String text, String mask) {
    //...
  }
}

// 未使用适配器模式之前的代码：代码的可测试性、扩展性不好
public class RiskManagement {
  private ASensitiveWordsFilter aFilter = new ASensitiveWordsFilter();
  private BSensitiveWordsFilter bFilter = new BSensitiveWordsFilter();
  private CSensitiveWordsFilter cFilter = new CSensitiveWordsFilter();
  
  public String filterSensitiveWords(String text) {
    String maskedText = aFilter.filterSexyWords(text);
    maskedText = aFilter.filterPoliticalWords(maskedText);
    maskedText = bFilter.filter(maskedText);
    maskedText = cFilter.filter(maskedText, "***");
    return maskedText;
  }
}

// 使用适配器模式进行改造
public interface ISensitiveWordsFilter { // 统一接口定义
  String filter(String text);
}

public class ASensitiveWordsFilterAdaptor implements ISensitiveWordsFilter {
  private ASensitiveWordsFilter aFilter;
  public String filter(String text) {
    String maskedText = aFilter.filterSexyWords(text);
    maskedText = aFilter.filterPoliticalWords(maskedText);
    return maskedText;
  }
}
//...省略BSensitiveWordsFilterAdaptor、CSensitiveWordsFilterAdaptor...

// 扩展性更好，更加符合开闭原则，如果添加一个新的敏感词过滤系统，
// 这个类完全不需要改动；而且基于接口而非实现编程，代码的可测试性更好。
public class RiskManagement { 
  private List<ISensitiveWordsFilter> filters = new ArrayList<>();
 
  public void addSensitiveWordsFilter(ISensitiveWordsFilter filter) {
    filters.add(filter);
  }
  
  public String filterSensitiveWords(String text) {
    String maskedText = text;
    for (ISensitiveWordsFilter filter : filters) {
      maskedText = filter.filter(maskedText);
    }
    return maskedText;
  }
}
```

3. 替换依赖的外部系统

当我们把项目中依赖的一个外部系统替换为另一个外部系统的时候，利用适配器模式，可以减少对代码的改动。具体的代码示例如下所示：

```java
// 外部系统A
public interface IA {
  //...
  void fa();
}
public class A implements IA {
  //...
  public void fa() { //... }
}
// 在我们的项目中，外部系统A的使用示例
public class Demo {
  private IA a;
  public Demo(IA a) {
    this.a = a;
  }
  //...
}
Demo d = new Demo(new A());

// 将外部系统A替换成外部系统B
public class BAdaptor implemnts IA {
  private B b;
  public BAdaptor(B b) {
    this.b= b;
  }
  public void fa() {
    //...
    b.fb();
  }
}
// 借助BAdaptor，Demo的代码中，调用IA接口的地方都无需改动，
// 只需要将BAdaptor如下注入到Demo即可。
Demo d = new Demo(new BAdaptor(new B()));
```

4. 兼容老版本接口

在做版本升级的时候，对于一些要废弃的接口，我们不直接将其删除，而是暂时保留，并且标注为 deprecated，并将内部实现逻辑委托为新的接口实现。这样做的好处是，让使用它的项目有个过渡期，而不是强制进行代码修改。这也可以粗略地看作适配器模式的一个应用场景。同样，我还是通过一个例子，来进一步解释一下。

JDK1.0 中包含一个遍历集合容器的类 Enumeration。JDK2.0 对这个类进行了重构，将它改名为 Iterator 类，并且对它的代码实现做了优化。但是考虑到如果将 Enumeration 直接从 JDK2.0 中删除，那使用 JDK1.0 的项目如果切换到 JDK2.0，代码就会编译不通过。为了避免这种情况的发生，我们必须把项目中所有使用到 Enumeration 的地方，都修改为使用 Iterator 才行。

单独一个项目做 Enumeration 到 Iterator 的替换，勉强还能接受。但是，使用 Java 开发的项目太多了，一次 JDK 的升级，导致所有的项目不做代码修改就会编译报错，这显然是不合理的。这就是我们经常所说的不兼容升级。为了做到兼容使用低版本 JDK 的老代码，我们可以暂时保留 Enumeration 类，并将其实现替换为直接调用 Itertor。代码示例如下所示：

```java
public class Collections {
  public static Emueration emumeration(final Collection c) {
    return new Enumeration() {
      Iterator i = c.iterator();
      
      public boolean hasMoreElments() {
        return i.hashNext();
      }
      
      public Object nextElement() {
        return i.next():
      }
    }
  }
}
```

5. 适配不同格式的数据

前面我们讲到，适配器模式主要用于接口的适配，实际上，它还可以用在不同格式的数据之间的适配。比如，把从不同征信系统拉取的不同格式的征信数据，统一为相同的格式，以方便存储和使用。再比如，Java 中的 Arrays.asList() 也可以看作一种数据适配器，将数组类型的数据转化为集合容器类型。

```
List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
```

### 剖析适配器模式在 Java 日志中的应用

Java 中有很多日志框架，在项目开发中，我们常常用它们来打印日志信息。其中，比较常用的有 log4j、logback，以及 JDK 提供的 JUL(java.util.logging) 和 Apache 的 JCL(Jakarta Commons Logging) 等。

大部分日志框架都提供了相似的功能，比如按照不同级别（debug、info、warn、erro……）打印日志等，但它们却并没有实现统一的接口。这主要可能是历史的原因，它不像 JDBC 那样，一开始就制定了数据库操作的接口规范。

如果我们只是开发一个自己用的项目，那用什么日志框架都可以，log4j、logback 随便选一个就好。但是，如果我们开发的是一个集成到其他系统的组件、框架、类库等，那日志框架的选择就没那么随意了。

比如，项目中用到的某个组件使用 log4j 来打印日志，而我们项目本身使用的是 logback。将组件引入到项目之后，我们的项目就相当于有了两套日志打印框架。每种日志框架都有自己特有的配置方式。所以，我们要针对每种日志框架编写不同的配置文件（比如，日志存储的文件地址、打印日志的格式）。如果引入多个组件，每个组件使用的日志框架都不一样，那日志本身的管理工作就变得非常复杂。所以，为了解决这个问题，我们需要统一日志打印框架。

如果你是做 Java 开发的，那 Slf4j 这个日志框架你肯定不陌生，它相当于 JDBC 规范，提供了一套打印日志的统一接口规范。不过，它只定义了接口，并没有提供具体的实现，需要配合其他日志框架（log4j、logback……）来使用。

不仅如此，Slf4j 的出现晚于 JUL、JCL、log4j 等日志框架，所以，这些日志框架也不可能牺牲掉版本兼容性，将接口改造成符合 Slf4j 接口规范。Slf4j 也事先考虑到了这个问题，所以，它不仅仅提供了统一的接口定义，还提供了针对不同日志框架的适配器。对不同日志框架的接口进行二次封装，适配成统一的 Slf4j 接口定义。具体的代码示例如下所示：

```java
// slf4j统一的接口定义
package org.slf4j;
public interface Logger {
  public boolean isTraceEnabled();
  public void trace(String msg);
  public void trace(String format, Object arg);
  public void trace(String format, Object arg1, Object arg2);
  public void trace(String format, Object[] argArray);
  public void trace(String msg, Throwable t);
 
  public boolean isDebugEnabled();
  public void debug(String msg);
  public void debug(String format, Object arg);
  public void debug(String format, Object arg1, Object arg2)
  public void debug(String format, Object[] argArray)
  public void debug(String msg, Throwable t);

  //...省略info、warn、error等一堆接口
}

// log4j日志框架的适配器
// Log4jLoggerAdapter实现了LocationAwareLogger接口，
// 其中LocationAwareLogger继承自Logger接口，
// 也就相当于Log4jLoggerAdapter实现了Logger接口。
package org.slf4j.impl;
public final class Log4jLoggerAdapter extends MarkerIgnoringBase
  implements LocationAwareLogger, Serializable {
  final transient org.apache.log4j.Logger logger; // log4j
 
  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }
 
  public void debug(String msg) {
    logger.log(FQCN, Level.DEBUG, msg, null);
  }
 
  public void debug(String format, Object arg) {
    if (logger.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg);
      logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft.getThrowable());
    }
  }
 
  public void debug(String format, Object arg1, Object arg2) {
    if (logger.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
      logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft.getThrowable());
    }
  }
 
  public void debug(String format, Object[] argArray) {
    if (logger.isDebugEnabled()) {
      FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
      logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft.getThrowable());
    }
  }
 
  public void debug(String msg, Throwable t) {
    logger.log(FQCN, Level.DEBUG, msg, t);
  }
  //...省略一堆接口的实现...
}
```

所以，在开发业务系统或者开发框架、组件的时候，我们统一使用 Slf4j 提供的接口来编写打印日志的代码，具体使用哪种日志框架实现（log4j、logback……），是可以动态地指定的（使用 Java 的 SPI 技术，这里我不多解释，你自行研究吧），只需要将相应的 SDK 导入到项目中即可。

不过，你可能会说，如果一些老的项目没有使用 Slf4j，而是直接使用比如 JCL 来打印日志，那如果想要替换成其他日志框架，比如 log4j，该怎么办呢？实际上，Slf4j 不仅仅提供了从其他日志框架到 Slf4j 的适配器，还提供了反向适配器，也就是从 Slf4j 到其他日志框架的适配。我们可以先将 JCL 切换为 Slf4j，然后再将 Slf4j 切换为 log4j。经过两次适配器的转换，我们就能成功将 log4j 切换为了 logback。

### 代理、桥接、装饰器、适配器 4 种设计模式的区别

代理、桥接、装饰器、适配器，这 4 种模式是比较常用的结构型设计模式。它们的代码结构非常相似。笼统来说，它们都可以称为 Wrapper 模式，也就是通过 Wrapper 类二次封装原始类。

尽管代码结构相似，但这 4 种设计模式的用意完全不同，也就是说要解决的问题、应用场景不同，这也是它们的主要区别。这里我就简单说一下它们之间的区别。

- 代理模式：代理模式在不改变原始类接口的条件下，为原始类定义一个代理类，主要目的是控制访问，而非加强功能，这是它跟装饰器模式最大的不同。

- 桥接模式：桥接模式的目的是将接口部分和实现部分分离，从而让它们可以较为容易、也相对独立地加以改变。

- 装饰器模式：装饰者模式在不改变原始类接口的情况下，对原始类功能进行增强，并且支持多个装饰器的嵌套使用。

- 适配器模式：适配器模式是一种事后的补救策略。适配器提供跟原始类不同的接口，而代理模式、装饰器模式提供的都是跟原始类相同的接口。

### 重点回顾

适配器模式是用来做适配，它将不兼容的接口转换为可兼容的接口，让原本由于接口不兼容而不能一起工作的类可以一起工作。适配器模式有两种实现方式：类适配器和对象适配器。其中，类适配器使用继承关系来实现，对象适配器使用组合关系来实现。

一般来说，适配器模式可以看作一种“补偿模式”，用来补救设计上的缺陷。应用这种模式算是“无奈之举”，如果在设计初期，我们就能协调规避接口不兼容的问题，那这种模式就没有应用的机会了。

那在实际的开发中，什么情况下才会出现接口不兼容呢？我总结下了下面这样 5 种场景：

- 封装有缺陷的接口设计
- 统一多个类的接口设计
- 替换依赖的外部系统
- 兼容老版本接口
- 适配不同格式的数据

# 设计模式与范式：行为型

## 56 | 观察者模式（上）：详解各种应用场景下观察者模式的不同实现方式

> 我们常把 23 种经典的设计模式分为三类：创建型、结构型、行为型。前面我们已经学习了创建型和结构型，从今天起，我们开始学习行为型设计模式。我们知道，创建型设计模式主要解决“对象的创建”问题，结构型设计模式主要解决“类或对象的组合或组装”问题，那行为型设计模式主要解决的就是“类或对象之间的交互”问题。

行为型设计模式比较多，有 11 个，几乎占了 23 种经典设计模式的一半。它们分别是：观察者模式、模板模式、策略模式、职责链模式、状态模式、迭代器模式、访问者模式、备忘录模式、命令模式、解释器模式、中介模式。

### 原理及应用场景剖析

观察者模式（Observer Design Pattern）也被称为发布订阅模式（Publish-Subscribe Design Pattern）。在 GoF 的《设计模式》一书中，它的定义是这样的：

> Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

翻译成中文就是：在对象之间定义一个一对多的依赖，当一个对象状态改变的时候，所有依赖的对象都会自动收到通知。

一般情况下，被依赖的对象叫作被观察者（Observable），依赖的对象叫作观察者（Observer）。不过，在实际的项目开发中，这两种对象的称呼是比较灵活的，有各种不同的叫法，比如：Subject-Observer、Publisher-Subscriber、Producer-Consumer、EventEmitter-EventListener、Dispatcher-Listener。不管怎么称呼，只要应用场景符合刚刚给出的定义，都可以看作观察者模式。

实际上，观察者模式是一个比较抽象的模式，根据不同的应用场景和需求，有完全不同的实现方式，待会我们会详细地讲到。现在，我们先来看其中最经典的一种实现方式。这也是在讲到这种模式的时候，很多书籍或资料给出的最常见的实现方式。具体的代码如下所示：

```java
public interface Subject {
  void registerObserver(Observer observer);
  void removeObserver(Observer observer);
  void notifyObservers(Message message);
}

public interface Observer {
  void update(Message message);
}

public class ConcreteSubject implements Subject {
  private List<Observer> observers = new ArrayList<Observer>();

  @Override
  public void registerObserver(Observer observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(Message message) {
    for (Observer observer : observers) {
      observer.update(message);
    }
  }

}

public class ConcreteObserverOne implements Observer {
  @Override
  public void update(Message message) {
    //TODO: 获取消息通知，执行自己的逻辑...
    System.out.println("ConcreteObserverOne is notified.");
  }
}

public class ConcreteObserverTwo implements Observer {
  @Override
  public void update(Message message) {
    //TODO: 获取消息通知，执行自己的逻辑...
    System.out.println("ConcreteObserverTwo is notified.");
  }
}

public class Demo {
  public static void main(String[] args) {
    ConcreteSubject subject = new ConcreteSubject();
    subject.registerObserver(new ConcreteObserverOne());
    subject.registerObserver(new ConcreteObserverTwo());
    subject.notifyObservers(new Message());
  }
}
```

实际上，上面的代码算是观察者模式的“模板代码”，只能反映大体的设计思路。在真实的软件开发中，并不需要照搬上面的模板代码。观察者模式的实现方法各式各样，函数、类的命名等会根据业务场景的不同有很大的差别，比如 register 函数还可以叫作 attach，remove 函数还可以叫作 detach 等等。不过，万变不离其宗，设计思路都是差不多的。

原理和代码实现都非常简单，也比较好理解，不需要我过多的解释。我们还是通过一个具体的例子来重点讲一下，什么情况下需要用到这种设计模式？或者说，这种设计模式能解决什么问题呢？

假设我们在开发一个 P2P 投资理财系统，用户注册成功之后，我们会给用户发放投资体验金。代码实现大致是下面这个样子的：

```java
public class UserController {
  private UserService userService; // 依赖注入
  private PromotionService promotionService; // 依赖注入

  public Long register(String telephone, String password) {
    //省略输入参数的校验代码
    //省略userService.register()异常的try-catch代码
    long userId = userService.register(telephone, password);
    promotionService.issueNewUserExperienceCash(userId);
    return userId;
  }
}
```

虽然注册接口做了两件事情，注册和发放体验金，违反单一职责原则，但是，如果没有扩展和修改的需求，现在的代码实现是可以接受的。如果非得用观察者模式，就需要引入更多的类和更加复杂的代码结构，反倒是一种过度设计。

相反，如果需求频繁变动，比如，用户注册成功之后，不再发放体验金，而是改为发放优惠券，并且还要给用户发送一封“欢迎注册成功”的站内信。这种情况下，我们就需要频繁地修改 register() 函数中的代码，违反开闭原则。而且，如果注册成功之后需要执行的后续操作越来越多，那 register() 函数的逻辑会变得越来越复杂，也就影响到代码的可读性和可维护性。

这个时候，观察者模式就能派上用场了。利用观察者模式，我对上面的代码进行了重构。重构之后的代码如下所示：

```java
public interface RegObserver {
  void handleRegSuccess(long userId);
}

public class RegPromotionObserver implements RegObserver {
  private PromotionService promotionService; // 依赖注入

  @Override
  public void handleRegSuccess(long userId) {
    promotionService.issueNewUserExperienceCash(userId);
  }
}

public class RegNotificationObserver implements RegObserver {
  private NotificationService notificationService;

  @Override
  public void handleRegSuccess(long userId) {
    notificationService.sendInboxMessage(userId, "Welcome...");
  }
}

public class UserController {
  private UserService userService; // 依赖注入
  private List<RegObserver> regObservers = new ArrayList<>();

  // 一次性设置好，之后也不可能动态的修改
  public void setRegObservers(List<RegObserver> observers) {
    regObservers.addAll(observers);
  }

  public Long register(String telephone, String password) {
    //省略输入参数的校验代码
    //省略userService.register()异常的try-catch代码
    long userId = userService.register(telephone, password);

    for (RegObserver observer : regObservers) {
      observer.handleRegSuccess(userId);
    }

    return userId;
  }
}
```

当我们需要添加新的观察者的时候，比如，用户注册成功之后，推送用户注册信息给大数据征信系统，基于观察者模式的代码实现，UserController 类的 register() 函数完全不需要修改，只需要再添加一个实现了 RegObserver 接口的类，并且通过 setRegObservers() 函数将它注册到 UserController 类中即可。

不过，你可能会说，当我们把发送体验金替换为发送优惠券的时候，需要修改 RegPromotionObserver 类中 handleRegSuccess() 函数的代码，这还是违反开闭原则呀？你说得没错，不过，相对于 register() 函数来说，handleRegSuccess() 函数的逻辑要简单很多，修改更不容易出错，引入 bug 的风险更低。

前面我们已经学习了很多设计模式，不知道你有没有发现，实际上，**设计模式要干的事情就是解耦。创建型模式是将创建和使用代码解耦，结构型模式是将不同功能代码解耦，行为型模式是将不同的行为代码解耦，具体到观察者模式，它是将观察者和被观察者代码解耦。**借助设计模式，我们利用更好的代码结构，将一大坨代码拆分成职责更单一的小类，让其满足开闭原则、高内聚松耦合等特性，以此来控制和应对代码的复杂性，提高代码的可扩展性。

### 基于不同应用场景的不同实现方式

观察者模式的应用场景非常广泛，小到代码层面的解耦，大到架构层面的系统解耦，再或者一些产品的设计思路，都有这种模式的影子，比如，邮件订阅、RSS Feeds，本质上都是观察者模式。

不同的应用场景和需求下，这个模式也有截然不同的实现方式，开篇的时候我们也提到，有同步阻塞的实现方式，也有异步非阻塞的实现方式；有进程内的实现方式，也有跨进程的实现方式。

之前讲到的实现方式，从刚刚的分类方式上来看，它是一种同步阻塞的实现方式。观察者和被观察者代码在同一个线程内执行，被观察者一直阻塞，直到所有的观察者代码都执行完成之后，才执行后续的代码。对照上面讲到的用户注册的例子，register() 函数依次调用执行每个观察者的 handleRegSuccess() 函数，等到都执行完成之后，才会返回结果给客户端。

如果注册接口是一个调用比较频繁的接口，对性能非常敏感，希望接口的响应时间尽可能短，那我们可以将同步阻塞的实现方式改为异步非阻塞的实现方式，以此来减少响应时间。具体来讲，当 userService.register() 函数执行完成之后，我们启动一个新的线程来执行观察者的 handleRegSuccess() 函数，这样 userController.register() 函数就不需要等到所有的 handleRegSuccess() 函数都执行完成之后才返回结果给客户端。userController.register() 函数从执行 3 个 SQL 语句才返回，减少到只需要执行 1 个 SQL 语句就返回，响应时间粗略来讲减少为原来的 1/3。

那如何实现一个异步非阻塞的观察者模式呢？简单一点的做法是，在每个 handleRegSuccess() 函数中，创建一个新的线程执行代码。不过，我们还有更加优雅的实现方式，那就是基于 EventBus 来实现。今天，我们就不展开讲解了。在下一讲中，我会用一节课的时间，借鉴 Google Guava EventBus 框架的设计思想，手把手带你开发一个支持异步非阻塞的 EventBus 框架。它可以复用在任何需要异步非阻塞观察者模式的应用场景中。

刚刚讲到的两个场景，不管是同步阻塞实现方式还是异步非阻塞实现方式，都是进程内的实现方式。如果用户注册成功之后，我们需要发送用户信息给大数据征信系统，而大数据征信系统是一个独立的系统，跟它之间的交互是跨不同进程的，那如何实现一个跨进程的观察者模式呢？

如果大数据征信系统提供了发送用户注册信息的 RPC 接口，我们仍然可以沿用之前的实现思路，在 handleRegSuccess() 函数中调用 RPC 接口来发送数据。但是，我们还有更加优雅、更加常用的一种实现方式，那就是基于消息队列（Message Queue，比如 ActiveMQ）来实现。

当然，这种实现方式也有弊端，那就是需要引入一个新的系统（消息队列），增加了维护成本。不过，它的好处也非常明显。在原来的实现方式中，观察者需要注册到被观察者中，被观察者需要依次遍历观察者来发送消息。而基于消息队列的实现方式，被观察者和观察者解耦更加彻底，两部分的耦合更小。被观察者完全不感知观察者，同理，观察者也完全不感知被观察者。被观察者只管发送消息到消息队列，观察者只管从消息队列中读取消息来执行相应的逻辑。

### 重点回顾

设计模式要干的事情就是解耦，创建型模式是将创建和使用代码解耦，结构型模式是将不同功能代码解耦，行为型模式是将不同的行为代码解耦，具体到观察者模式，它将观察者和被观察者代码解耦。借助设计模式，我们利用更好的代码结构，将一大坨代码拆分成职责更单一的小类，让其满足开闭原则、高内聚低耦合等特性，以此来控制和应对代码的复杂性，提高代码的可扩展性。

观察者模式的应用场景非常广泛，小到代码层面的解耦，大到架构层面的系统解耦，再或者一些产品的设计思路，都有这种模式的影子，比如，邮件订阅、RSS Feeds，本质上都是观察者模式。不同的应用场景和需求下，这个模式也有截然不同的实现方式，有同步阻塞的实现方式，也有异步非阻塞的实现方式；有进程内的实现方式，也有跨进程的实现方式。

### 课堂讨论

1. 请对比一下“生产者 - 消费者”模型和观察者模式的区别和联系。

   生产者之间存在竞争关系，消费者之间存在竞争关系。观察者模式不存在竞争关系。

2. 除了今天提到的观察者模式的几个应用场景，比如邮件订阅，你还能想到有哪些其他的应用场景吗？

## 58 | 模板模式（上）：剖析模板模式在JDK、Servlet、JUnit等中的应用

**模板模式主要是用来解决复用和扩展两个问题**。我们今天会结合 Java Servlet、JUnit TestCase、Java InputStream、Java AbstractList 四个例子来具体讲解这两个作用。

### 模板模式的原理与实现

模板模式，全称是模板方法设计模式，英文是 Template Method Design Pattern。在 GoF 的《设计模式》一书中，它是这么定义的：

> Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm’s structure.

翻译成中文就是：模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。

这里的“算法”，我们可以理解为广义上的“业务逻辑”，并不特指数据结构和算法中的“算法”。这里的算法骨架就是“模板”，包含算法骨架的方法就是“模板方法”，这也是模板方法模式名字的由来。

原理很简单，代码实现就更加简单，我写了一个示例代码，如下所示。templateMethod() 函数定义为 final，是为了避免子类重写它。method1() 和 method2() 定义为 abstract，是为了强迫子类去实现。不过，这些都不是必须的，在实际的项目开发中，模板模式的代码实现比较灵活，待会儿讲到应用场景的时候，我们会有具体的体现。

```java
public abstract class AbstractClass {
  public final void templateMethod() {
    //...
    method1();
    //...
    method2();
    //...
  }
  
  protected abstract void method1();
  protected abstract void method2();
}

public class ConcreteClass1 extends AbstractClass {
  @Override
  protected void method1() {
    //...
  }
  
  @Override
  protected void method2() {
    //...
  }
}

public class ConcreteClass2 extends AbstractClass {
  @Override
  protected void method1() {
    //...
  }
  
  @Override
  protected void method2() {
    //...
  }
}

AbstractClass demo = new ConcreteClass1();
demo.templateMethod();
```

### 模板模式作用一：复用

开篇的时候，我们讲到模板模式有两大作用：复用和扩展。我们先来看它的第一个作用：复用。

模板模式把一个算法中不变的流程抽象到父类的模板方法 templateMethod() 中，将可变的部分 method1()、method2() 留给子类 ContreteClass1 和 ContreteClass2 来实现。所有的子类都可以复用父类中模板方法定义的流程代码。我们通过两个小例子来更直观地体会一下。

#### 1.Java InputStream

Java IO 类库中，有很多类的设计用到了模板模式，比如 InputStream、OutputStream、Reader、Writer。我们拿 InputStream 来举例说明一下。

我把 InputStream 部分相关代码贴在了下面。在代码中，read() 函数是一个模板方法，定义了读取数据的整个流程，并且暴露了一个可以由子类来定制的抽象方法。不过这个方法也被命名为了 read()，只是参数跟模板方法不同。

```java
public abstract class InputStream implements Closeable {
  //...省略其他代码...
  
  public int read(byte b[], int off, int len) throws IOException {
    if (b == null) {
      throw new NullPointerException();
    } else if (off < 0 || len < 0 || len > b.length - off) {
      throw new IndexOutOfBoundsException();
    } else if (len == 0) {
      return 0;
    }

    int c = read();
    if (c == -1) {
      return -1;
    }
    b[off] = (byte)c;

    int i = 1;
    try {
      for (; i < len ; i++) {
        c = read();
        if (c == -1) {
          break;
        }
        b[off + i] = (byte)c;
      }
    } catch (IOException ee) {
    }
    return i;
  }
  
  public abstract int read() throws IOException;
}

public class ByteArrayInputStream extends InputStream {
  //...省略其他代码...
  
  @Override
  public synchronized int read() {
    return (pos < count) ? (buf[pos++] & 0xff) : -1;
  }
}
```

#### 2.Java AbstractList

在 Java AbstractList 类中，addAll() 函数可以看作模板方法，add() 是子类需要重写的方法，尽管没有声明为 abstract 的，但函数实现直接抛出了 UnsupportedOperationException 异常。前提是，如果子类不重写是不能使用的。

```java
public boolean addAll(int index, Collection<? extends E> c) {
    rangeCheckForAdd(index);
    boolean modified = false;
    for (E e : c) {
        add(index++, e);
        modified = true;
    }
    return modified;
}

public void add(int index, E element) {
    throw new UnsupportedOperationException();
}
```

### 模板模式作用二：扩展

模板模式的第二大作用的是扩展。这里所说的扩展，并不是指代码的扩展性，而是指框架的扩展性，有点类似我们之前讲到的控制反转，你可以结合第 19 节来一块理解。基于这个作用，模板模式常用在框架的开发中，让框架用户可以在不修改框架源码的情况下，定制化框架的功能。我们通过 Junit TestCase、Java Servlet 两个例子来解释一下。

#### 1.Java Servlet

对于 Java Web 项目开发来说，常用的开发框架是 SpringMVC。利用它，我们只需要关注业务代码的编写，底层的原理几乎不会涉及。但是，如果我们抛开这些高级框架来开发 Web 项目，必然会用到 Servlet。实际上，使用比较底层的 Servlet 来开发 Web 项目也不难。我们只需要定义一个继承 HttpServlet 的类，并且重写其中的 doGet() 或 doPost() 方法，来分别处理 get 和 post 请求。具体的代码示例如下所示：

```java
public class HelloServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.getWriter().write("Hello World.");
  }
}
```

除此之外，我们还需要在配置文件 web.xml 中做如下配置。Tomcat、Jetty 等 Servlet 容器在启动的时候，会自动加载这个配置文件中的 URL 和 Servlet 之间的映射关系。

```xml
<servlet>
    <servlet-name>HelloServlet</servlet-name>
    <servlet-class>com.xzg.cd.HelloServlet</servlet-class>
</servlet>

<servlet-mapping>
    <servlet-name>HelloServlet</servlet-name>
    <url-pattern>/hello</url-pattern>
</servlet-mapping>
```

当我们在浏览器中输入网址（比如，http://127.0.0.1:8080/hello ）的时候，Servlet 容器会接收到相应的请求，并且根据 URL 和 Servlet 之间的映射关系，找到相应的 Servlet（HelloServlet），然后执行它的 service() 方法。service() 方法定义在父类 HttpServlet 中，它会调用 doGet() 或 doPost() 方法，然后输出数据（“Hello world”）到网页。

我们现在来看，HttpServlet 的 service() 函数长什么样子。

```java
public void service(ServletRequest req, ServletResponse res)
    throws ServletException, IOException
{
    HttpServletRequest  request;
    HttpServletResponse response;
    if (!(req instanceof HttpServletRequest &&
            res instanceof HttpServletResponse)) {
        throw new ServletException("non-HTTP request or response");
    }
    request = (HttpServletRequest) req;
    response = (HttpServletResponse) res;
    service(request, response);
}

protected void service(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException
{
    String method = req.getMethod();
    if (method.equals(METHOD_GET)) {
        long lastModified = getLastModified(req);
        if (lastModified == -1) {
            // servlet doesn't support if-modified-since, no reason
            // to go through further expensive logic
            doGet(req, resp);
        } else {
            long ifModifiedSince = req.getDateHeader(HEADER_IFMODSINCE);
            if (ifModifiedSince < lastModified) {
                // If the servlet mod time is later, call doGet()
                // Round down to the nearest second for a proper compare
                // A ifModifiedSince of -1 will always be less
                maybeSetLastModified(resp, lastModified);
                doGet(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            }
        }
    } else if (method.equals(METHOD_HEAD)) {
        long lastModified = getLastModified(req);
        maybeSetLastModified(resp, lastModified);
        doHead(req, resp);
    } else if (method.equals(METHOD_POST)) {
        doPost(req, resp);
    } else if (method.equals(METHOD_PUT)) {
        doPut(req, resp);
    } else if (method.equals(METHOD_DELETE)) {
        doDelete(req, resp);
    } else if (method.equals(METHOD_OPTIONS)) {
        doOptions(req,resp);
    } else if (method.equals(METHOD_TRACE)) {
        doTrace(req,resp);
    } else {
        String errMsg = lStrings.getString("http.method_not_implemented");
        Object[] errArgs = new Object[1];
        errArgs[0] = method;
        errMsg = MessageFormat.format(errMsg, errArgs);
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, errMsg);
    }
}
```

从上面的代码中我们可以看出，HttpServlet 的 service() 方法就是一个模板方法，它实现了整个 HTTP 请求的执行流程，doGet()、doPost() 是模板中可以由子类来定制的部分。实际上，这就相当于 Servlet 框架提供了一个扩展点（doGet()、doPost() 方法），让框架用户在不用修改 Servlet 框架源码的情况下，将业务代码通过扩展点镶嵌到框架中执行。

#### 2.JUnit TestCase

跟 Java Servlet 类似，JUnit 框架也通过模板模式提供了一些功能扩展点（setUp()、tearDown() 等），让框架用户可以在这些扩展点上扩展功能。

在使用 JUnit 测试框架来编写单元测试的时候，我们编写的测试类都要继承框架提供的 TestCase 类。在 TestCase 类中，runBare() 函数是模板方法，它定义了执行测试用例的整体流程：先执行 setUp() 做些准备工作，然后执行 runTest() 运行真正的测试代码，最后执行 tearDown() 做扫尾工作。

TestCase 类的具体代码如下所示。尽管 setUp()、tearDown() 并不是抽象函数，还提供了默认的实现，不强制子类去重新实现，但这部分也是可以在子类中定制的，所以也符合模板模式的定义。

```java
public abstract class TestCase extends Assert implements Test {
  public void runBare() throws Throwable {
    Throwable exception = null;
    setUp();
    try {
      runTest();
    } catch (Throwable running) {
      exception = running;
    } finally {
      try {
        tearDown();
      } catch (Throwable tearingDown) {
        if (exception == null) exception = tearingDown;
      }
    }
    if (exception != null) throw exception;
  }
  
  /**
  * Sets up the fixture, for example, open a network connection.
  * This method is called before a test is executed.
  */
  protected void setUp() throws Exception {
  }

  /**
  * Tears down the fixture, for example, close a network connection.
  * This method is called after a test is executed.
  */
  protected void tearDown() throws Exception {
  }
}
```

### 重点回顾

模板方法模式在一个方法中定义一个算法骨架，并将某些步骤推迟到子类中实现。模板方法模式可以让子类在不改变算法整体结构的情况下，重新定义算法中的某些步骤。这里的“算法”，我们可以理解为广义上的“业务逻辑”，并不特指数据结构和算法中的“算法”。这里的算法骨架就是“模板”，包含算法骨架的方法就是“模板方法”，这也是模板方法模式名字的由来。

在模板模式经典的实现中，模板方法定义为 final，可以避免被子类重写。需要子类重写的方法定义为 abstract，可以强迫子类去实现。不过，在实际项目开发中，模板模式的实现比较灵活，以上两点都不是必须的。

**模板模式有两大作用：复用和扩展。其中，复用指的是，所有的子类可以复用父类中提供的模板方法的代码。扩展指的是，框架通过模板模式提供功能扩展点，让框架用户可以在不修改框架源码的情况下，基于扩展点定制化框架的功能。**

### 课堂讨论

假设一个框架中的某个类暴露了两个模板方法，并且定义了一堆供模板方法调用的抽象方法，代码示例如下所示。在项目开发中，即便我们只用到这个类的其中一个模板方法，我们还是要在子类中把所有的抽象方法都实现一遍，这相当于无效劳动，有没有其他方式来解决这个问题呢？

```java
public abstract class AbstractClass {
  public final void templateMethod1() {
    //...
    method1();
    //...
    method2();
    //...
  }
  
  public final void templateMethod2() {
    //...
    method3();
    //...
    method4();
    //...
  }
  
  protected abstract void method1();
  protected abstract void method2();
  protected abstract void method3();
  protected abstract void method4();
}
```





# 开源与项目实战：开源实战 (14讲)

## 76 | 开源实战一（上）：通过剖析Java JDK源码学习灵活应用设计模式

### 工厂模式在 Calendar 类中的应用

在前面讲到工厂模式的时候，大部分工厂类都是以 Factory 作为后缀来命名，并且工厂类主要负责创建对象这样一件事情。但在实际的项目开发中，工厂类的设计更加灵活。那我们就来看下，工厂模式在 Java JDK 中的一个应用：java.util.Calendar。从命名上，我们无法看出它是一个工厂类。

Calendar 类提供了大量跟日期相关的功能代码，同时，又提供了一个 getInstance() 工厂方法，用来根据不同的 TimeZone 和 Locale 创建不同的 Calendar 子类对象。也就是说，功能代码和工厂方法代码耦合在了一个类中。所以，即便我们去查看它的源码，如果不细心的话，也很难发现它用到了工厂模式。同时，因为它不单单是一个工厂类，所以，它并没有以 Factory 作为后缀来命名。

Calendar 类的相关代码如下所示，大部分代码都已经省略，我只给出了 getInstance() 工厂方法的代码实现。从代码中，我们可以看出，getInstance() 方法可以根据不同 TimeZone 和 Locale，创建不同的 Calendar 子类对象，比如 BuddhistCalendar、JapaneseImperialCalendar、GregorianCalendar，这些细节完全封装在工厂方法中，使用者只需要传递当前的时区和地址，就能够获得一个 Calendar 类对象来使用，而获得的对象具体是哪个 Calendar 子类的对象，使用者在使用的时候并不关心。

```java
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
  //...
  public static Calendar getInstance(TimeZone zone, Locale aLocale){
    return createCalendar(zone, aLocale);
  }

  private static Calendar createCalendar(TimeZone zone,Locale aLocale) {
    CalendarProvider provider = LocaleProviderAdapter.getAdapter(
        CalendarProvider.class, aLocale).getCalendarProvider();
    if (provider != null) {
      try {
        return provider.getInstance(zone, aLocale);
      } catch (IllegalArgumentException iae) {
        // fall back to the default instantiation
      }
    }

    Calendar cal = null;
    if (aLocale.hasExtensions()) {
      String caltype = aLocale.getUnicodeLocaleType("ca");
      if (caltype != null) {
        switch (caltype) {
          case "buddhist":
            cal = new BuddhistCalendar(zone, aLocale);
            break;
          case "japanese":
            cal = new JapaneseImperialCalendar(zone, aLocale);
            break;
          case "gregory":
            cal = new GregorianCalendar(zone, aLocale);
            break;
        }
      }
    }
    if (cal == null) {
      if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
        cal = new BuddhistCalendar(zone, aLocale);
      } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja" && aLocale.getCountry() == "JP") {
        cal = new JapaneseImperialCalendar(zone, aLocale);
      } else {
        cal = new GregorianCalendar(zone, aLocale);
      }
    }
    return cal;
  }
  //...
}
```

### 建造者模式在 Calendar 类中的应用

还是刚刚的 Calendar 类，它不仅仅用到了工厂模式，还用到了建造者模式。我们知道，建造者模式有两种实现方法，一种是单独定义一个 Builder 类，另一种是将 Builder 实现为原始类的内部类。Calendar 就采用了第二种实现思路。我们先来看代码再讲解，相关代码我贴在了下面。

```java
public abstract class Calendar implements Serializable, Cloneable, Comparable<Calendar> {
  //...
  public static class Builder {
    private static final int NFIELDS = FIELD_COUNT + 1;
    private static final int WEEK_YEAR = FIELD_COUNT;
    private long instant;
    private int[] fields;
    private int nextStamp;
    private int maxFieldIndex;
    private String type;
    private TimeZone zone;
    private boolean lenient = true;
    private Locale locale;
    private int firstDayOfWeek, minimalDaysInFirstWeek;

    public Builder() {}
    
    public Builder setInstant(long instant) {
        if (fields != null) {
            throw new IllegalStateException();
        }
        this.instant = instant;
        nextStamp = COMPUTED;
        return this;
    }
    //...省略n多set()方法
    
    public Calendar build() {
      if (locale == null) {
        locale = Locale.getDefault();
      }
      if (zone == null) {
        zone = TimeZone.getDefault();
      }
      Calendar cal;
      if (type == null) {
        type = locale.getUnicodeLocaleType("ca");
      }
      if (type == null) {
        if (locale.getCountry() == "TH" && locale.getLanguage() == "th") {
          type = "buddhist";
        } else {
          type = "gregory";
        }
      }
      switch (type) {
        case "gregory":
          cal = new GregorianCalendar(zone, locale, true);
          break;
        case "iso8601":
          GregorianCalendar gcal = new GregorianCalendar(zone, locale, true);
          // make gcal a proleptic Gregorian
          gcal.setGregorianChange(new Date(Long.MIN_VALUE));
          // and week definition to be compatible with ISO 8601
          setWeekDefinition(MONDAY, 4);
          cal = gcal;
          break;
        case "buddhist":
          cal = new BuddhistCalendar(zone, locale);
          cal.clear();
          break;
        case "japanese":
          cal = new JapaneseImperialCalendar(zone, locale, true);
          break;
        default:
          throw new IllegalArgumentException("unknown calendar type: " + type);
      }
      cal.setLenient(lenient);
      if (firstDayOfWeek != 0) {
        cal.setFirstDayOfWeek(firstDayOfWeek);
        cal.setMinimalDaysInFirstWeek(minimalDaysInFirstWeek);
      }
      if (isInstantSet()) {
        cal.setTimeInMillis(instant);
        cal.complete();
        return cal;
      }

      if (fields != null) {
        boolean weekDate = isSet(WEEK_YEAR) && fields[WEEK_YEAR] > fields[YEAR];
        if (weekDate && !cal.isWeekDateSupported()) {
          throw new IllegalArgumentException("week date is unsupported by " + type);
        }
        for (int stamp = MINIMUM_USER_STAMP; stamp < nextStamp; stamp++) {
          for (int index = 0; index <= maxFieldIndex; index++) {
            if (fields[index] == stamp) {
              cal.set(index, fields[NFIELDS + index]);
              break;
             }
          }
        }

        if (weekDate) {
          int weekOfYear = isSet(WEEK_OF_YEAR) ? fields[NFIELDS + WEEK_OF_YEAR] : 1;
          int dayOfWeek = isSet(DAY_OF_WEEK) ? fields[NFIELDS + DAY_OF_WEEK] : cal.getFirstDayOfWeek();
          cal.setWeekDate(fields[NFIELDS + WEEK_YEAR], weekOfYear, dayOfWeek);
        }
        cal.complete();
      }
      return cal;
    }
  }
}
```

看了上面的代码，我有一个问题请你思考一下：既然已经有了 getInstance() 工厂方法来创建 Calendar 类对象，为什么还要用 Builder 来创建 Calendar 类对象呢？这两者之间的区别在哪里呢？

实际上，在前面讲到这两种模式的时候，我们对它们之间的区别做了详细的对比，现在，我们再来一块回顾一下。**工厂模式是用来创建不同但是相关类型的对象（继承同一父类或者接口的一组子类），由给定的参数来决定创建哪种类型的对象。建造者模式用来创建一种类型的复杂对象，通过设置不同的可选参数，“定制化”地创建不同的对象。**

网上有一个经典的例子很好地解释了两者的区别。

> 顾客走进一家餐馆点餐，我们利用工厂模式，根据用户不同的选择，来制作不同的食物，比如披萨、汉堡、沙拉。对于披萨来说，用户又有各种配料可以定制，比如奶酪、西红柿、起司，我们通过建造者模式根据用户选择的不同配料来制作不同的披萨。

粗看 Calendar 的 Builder 类的 build() 方法，你可能会觉得它有点像工厂模式。你的感觉没错，前面一半代码确实跟 getInstance() 工厂方法类似，根据不同的 type 创建了不同的 Calendar 子类。实际上，后面一半代码才属于标准的建造者模式，根据 setXXX() 方法设置的参数，来定制化刚刚创建的 Calendar 子类对象。

实际上，从 Calendar 这个例子，我们也能学到，不要过于死板地套用各种模式的原理和实现，不要不敢做丝毫的改动。模式是死的，用的人是活的。在实际上的项目开发中，不仅各种模式可以混合在一起使用，而且具体的代码实现，也可以根据具体的功能需求做灵活的调整。

### 装饰器模式在 Collections 类中的应用

我们前面讲到，Java IO 类库是装饰器模式的非常经典的应用。实际上，Java 的 Collections 类也用到了装饰器模式。

Collections 类是一个集合容器的工具类，提供了很多静态方法，用来创建各种集合容器，比如通过 unmodifiableColletion() 静态方法，来创建 UnmodifiableCollection 类对象。而这些容器类中的 UnmodifiableCollection 类、CheckedCollection 和 SynchronizedCollection 类，就是针对 Collection 类的装饰器类。

因为刚刚提到的这三个装饰器类，在代码结构上几乎一样，所以，我们这里只拿其中的 UnmodifiableCollection 类来举例讲解一下。UnmodifiableCollection 类是 Collections 类的一个内部类，相关代码我摘抄到了下面，你可以先看下。

```java
public class Collections {
  private Collections() {}
    
  public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> c) {
    return new UnmodifiableCollection<>(c);
  }

  static class UnmodifiableCollection<E> implements Collection<E>,   Serializable {
    private static final long serialVersionUID = 1820017752578914078L;
    final Collection<? extends E> c;

    UnmodifiableCollection(Collection<? extends E> c) {
      if (c==null)
        throw new NullPointerException();
      this.c = c;
    }

    public int size()                   {return c.size();}
    public boolean isEmpty()            {return c.isEmpty();}
    public boolean contains(Object o)   {return c.contains(o);}
    public Object[] toArray()           {return c.toArray();}
    public <T> T[] toArray(T[] a)       {return c.toArray(a);}
    public String toString()            {return c.toString();}

    public Iterator<E> iterator() {
      return new Iterator<E>() {
        private final Iterator<? extends E> i = c.iterator();

        public boolean hasNext() {return i.hasNext();}
        public E next()          {return i.next();}
        public void remove() {
          throw new UnsupportedOperationException();
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
          // Use backing collection version
          i.forEachRemaining(action);
        }
      };
    }

    public boolean add(E e) {
      throw new UnsupportedOperationException();
    }
    public boolean remove(Object o) {
       hrow new UnsupportedOperationException();
    }
    public boolean containsAll(Collection<?> coll) {
      return c.containsAll(coll);
    }
    public boolean addAll(Collection<? extends E> coll) {
      throw new UnsupportedOperationException();
    }
    public boolean removeAll(Collection<?> coll) {
      throw new UnsupportedOperationException();
    }
    public boolean retainAll(Collection<?> coll) {
      throw new UnsupportedOperationException();
    }
    public void clear() {
      throw new UnsupportedOperationException();
    }

    // Override default methods in Collection
    @Override
    public void forEach(Consumer<? super E> action) {
      c.forEach(action);
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
      throw new UnsupportedOperationException();
    }
    @SuppressWarnings("unchecked")
    @Override
    public Spliterator<E> spliterator() {
      return (Spliterator<E>)c.spliterator();
    }
    @SuppressWarnings("unchecked")
    @Override
    public Stream<E> stream() {
      return (Stream<E>)c.stream();
    }
    @SuppressWarnings("unchecked")
    @Override
    public Stream<E> parallelStream() {
      return (Stream<E>)c.parallelStream();
    }
  }
}
```

### 适配器模式在 Collections 类中的应用

在第 51 讲中我们讲到，适配器模式可以用来兼容老的版本接口。当时我们举了一个 JDK 的例子，这里我们再重新仔细看一下。

老版本的 JDK 提供了 Enumeration 类来遍历容器。新版本的 JDK 用 Iterator 类替代 Enumeration 类来遍历容器。为了兼容老的客户端代码（使用老版本 JDK 的代码），我们保留了 Enumeration 类，并且在 Collections 类中，仍然保留了 enumaration() 静态方法（因为我们一般都是通过这个静态函数来创建一个容器的 Enumeration 类对象）。

不过，保留 Enumeration 类和 enumeration() 函数，都只是为了兼容，实际上，跟适配器没有一点关系。那到底哪一部分才是适配器呢？

在新版本的 JDK 中，Enumeration 类是适配器类。它适配的是客户端代码（使用 Enumeration 类）和新版本 JDK 中新的迭代器 Iterator 类。不过，从代码实现的角度来说，这个适配器模式的代码实现，跟经典的适配器模式的代码实现，差别稍微有点大。enumeration() 静态函数的逻辑和 Enumeration 适配器类的代码耦合在一起，enumeration() 静态函数直接通过 new 的方式创建了匿名类对象。具体的代码如下所示：

```Java
/**
 * Returns an enumeration over the specified collection.  This provides
 * interoperability with legacy APIs that require an enumeration
 * as input.
 *
 * @param  <T> the class of the objects in the collection
 * @param c the collection for which an enumeration is to be returned.
 * @return an enumeration over the specified collection.
 * @see Enumeration
 */
public static <T> Enumeration<T> enumeration(final Collection<T> c) {
  return new Enumeration<T>() {
    private final Iterator<T> i = c.iterator();

    public boolean hasMoreElements() {
      return i.hasNext();
    }

    public T nextElement() {
      return i.next();
    }
  };
}
```

### 重点回顾

好了，今天的内容到此就讲完了。我们一块来总结回顾一下，你需要重点掌握的内容。

今天，我重点讲了工厂模式、建造者模式、装饰器模式、适配器模式，这四种模式在 Java JDK 中的应用，主要目的是给你展示真实项目中是如何灵活应用设计模式的。

从今天的讲解中，我们可以学习到，尽管在之前的理论讲解中，我们都有讲到每个模式的经典代码实现，但是，在真实的项目开发中，这些模式的应用更加灵活，代码实现更加自由，可以根据具体的业务场景、功能需求，对代码实现做很大的调整，甚至还可能会对模式本身的设计思路做调整。

比如，Java JDK 中的 Calendar 类，就耦合了业务功能代码、工厂方法、建造者类三种类型的代码，而且，在建造者类的 build() 方法中，前半部分是工厂方法的代码实现，后半部分才是真正的建造者模式的代码实现。这也告诉我们，在项目中应用设计模式，切不可生搬硬套，过于学院派，要学会结合实际情况做灵活调整，做到心中无剑胜有剑。

### 课堂讨论

在 Java 中，经常用到的 StringBuilder 类是否是建造者模式的应用呢？你可以试着像我一样从源码的角度去剖析一下。

## 77 | 开源实战一（下）：通过剖析Java JDK源码学习灵活应用设计模式

### 模板模式在 Collections 类中的应用

我们前面提到，策略、模板、职责链三个模式常用在框架的设计中，提供框架的扩展点，让框架使用者，在不修改框架源码的情况下，基于扩展点定制化框架的功能。Java 中的 Collections 类的 sort() 函数就是利用了模板模式的这个扩展特性。

首先，我们看下 Collections.sort() 函数是如何使用的。我写了一个示例代码，如下所示。这个代码实现了按照不同的排序方式（按照年龄从小到大、按照名字字母序从小到大、按照成绩从大到小）对 students 数组进行排序。

```java
public class Demo {
  public static void main(String[] args) {
    List<Student> students = new ArrayList<>();
    students.add(new Student("Alice", 19, 89.0f));
    students.add(new Student("Peter", 20, 78.0f));
    students.add(new Student("Leo", 18, 99.0f));

    Collections.sort(students, new AgeAscComparator());
    print(students);
    
    Collections.sort(students, new NameAscComparator());
    print(students);
    
    Collections.sort(students, new ScoreDescComparator());
    print(students);
  }

  public static void print(List<Student> students) {
    for (Student s : students) {
      System.out.println(s.getName() + " " + s.getAge() + " " + s.getScore());
    }
  }

  public static class AgeAscComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
      return o1.getAge() - o2.getAge();
    }
  }

  public static class NameAscComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

  public static class ScoreDescComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
      if (Math.abs(o1.getScore() - o2.getScore()) < 0.001) {
        return 0;
      } else if (o1.getScore() < o2.getScore()) {
        return 1;
      } else {
        return -1;
      }
    }
  }
}
```

结合刚刚这个例子，我们再来看下，为什么说 Collections.sort() 函数用到了模板模式？

Collections.sort() 实现了对集合的排序。为了扩展性，它将其中“比较大小”这部分逻辑，委派给用户来实现。如果我们把比较大小这部分逻辑看作整个排序逻辑的其中一个步骤，那我们就可以把它看作模板模式。不过，从代码实现的角度来看，它看起来有点类似之前讲过的 JdbcTemplate，并不是模板模式的经典代码实现，而是基于 Callback 回调机制来实现的。

不过，在其他资料中，我还看到有人说，Collections.sort() 使用的是策略模式。这样的说法也不是没有道理的。如果我们并不把“比较大小”看作排序逻辑中的一个步骤，而是看作一种算法或者策略，那我们就可以把它看作一种策略模式的应用。

不过，这也不是典型的策略模式，我们前面讲到，在典型的策略模式中，策略模式分为策略的定义、创建、使用这三部分。策略通过工厂模式来创建，并且在程序运行期间，根据配置、用户输入、计算结果等这些不确定因素，动态决定使用哪种策略。而在 Collections.sort() 函数中，策略的创建并非通过工厂模式，策略的使用也非动态确定。

### 观察者模式在 JDK 中的应用

在讲到观察者模式的时候，我们重点讲解了 Google Guava 的 EventBus 框架，它提供了观察者模式的骨架代码。使用 EventBus，我们不需要从零开始开发观察者模式。实际上，Java JDK 也提供了观察者模式的简单框架实现。在平时的开发中，如果我们不希望引入 Google Guava 开发库，可以直接使用 Java 语言本身提供的这个框架类。

不过，它比 EventBus 要简单多了，只包含两个类：java.util.Observable 和 java.util.Observer。前者是被观察者，后者是观察者。它们的代码实现也非常简单，为了方便你查看，我直接 copy-paste 到了这里。

```java
public interface Observer {
    void update(Observable o, Object arg);
}

public class Observable {
    private boolean changed = false;
    private Vector<Observer> obs;

    public Observable() {
        obs = new Vector<>();
    }

    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(Object arg) {
        Object[] arrLocal;

        synchronized (this) {
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }

    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }
}
```

对于 Observable、Observer 的代码实现，大部分都很好理解，我们重点来看其中的两个地方。一个是 changed 成员变量，另一个是 notifyObservers() 函数。

我们先来看 changed 成员变量。

它用来表明被观察者（Observable）有没有状态更新。当有状态更新时，我们需要手动调用 setChanged() 函数，将 changed 变量设置为 true，这样才能在调用 notifyObservers() 函数的时候，真正触发观察者（Observer）执行 update() 函数。否则，即便你调用了 notifyObservers() 函数，观察者的 update() 函数也不会被执行。

也就是说，当通知观察者被观察者状态更新的时候，我们需要依次调用 setChanged() 和 notifyObservers() 两个函数，单独调用 notifyObservers() 函数是不起作用的。你觉得这样的设计是不是多此一举呢？这个问题留给你思考，你可以在留言区说说你的看法。

我们再来看 notifyObservers() 函数。

为了保证在多线程环境下，添加、移除、通知观察者三个操作之间不发生冲突，Observable 类中的大部分函数都通过 synchronized 加了锁，不过，也有特例，notifyObservers() 这函数就没有加 synchronized 锁。这是为什么呢？在 JDK 的代码实现中，notifyObservers() 函数是如何保证跟其他函数操作不冲突的呢？这种加锁方法是否存在问题？又存在什么问题呢？

notifyObservers() 函数之所以没有像其他函数那样，一把大锁加在整个函数上，主要还是出于性能的考虑。

notifyObservers() 函数依次执行每个观察者的 update() 函数，每个 update() 函数执行的逻辑提前未知，有可能会很耗时。如果在 notifyObservers() 函数上加 synchronized 锁，notifyObservers() 函数持有锁的时间就有可能会很长，这就会导致其他线程迟迟获取不到锁，影响整个 Observable 类的并发性能。

我们知道，Vector 类不是线程安全的，在多线程环境下，同时添加、删除、遍历 Vector 类对象中的元素，会出现不可预期的结果。所以，在 JDK 的代码实现中，为了避免直接给 notifyObservers() 函数加锁而出现性能问题，JDK 采用了一种折中的方案。这个方案有点类似于我们之前讲过的让迭代器支持”快照“的解决方案。

在 notifyObservers() 函数中，我们先拷贝一份观察者列表，赋值给函数的局部变量，我们知道，局部变量是线程私有的，并不在线程间共享。这个拷贝出来的线程私有的观察者列表就相当于一个快照。我们遍历快照，逐一执行每个观察者的 update() 函数。而这个遍历执行的过程是在快照这个局部变量上操作的，不存在线程安全问题，不需要加锁。所以，我们只需要对拷贝创建快照的过程加锁，加锁的范围减少了很多，并发性能提高了。

为什么说这是一种折中的方案呢？这是因为，这种加锁方法实际上是存在一些问题的。在创建好快照之后，添加、删除观察者都不会更新快照，新加入的观察者就不会被通知到，新删除的观察者仍然会被通知到。这种权衡是否能接受完全看你的业务场景。实际上，这种处理方式也是多线程编程中减小锁粒度、提高并发性能的常用方法。

### 单例模式在 Runtime 类中的应用

JDK 中 java.lang.Runtime 类就是一个单例类。这个类你有没有比较眼熟呢？是的，我们之前讲到 Callback 回调的时候，添加 shutdown hook 就是通过这个类来实现的。

每个 Java 应用在运行时会启动一个 JVM 进程，每个 JVM 进程都只对应一个 Runtime 实例，用于查看 JVM 状态以及控制 JVM 行为。进程内唯一，所以比较适合设计为单例。在编程的时候，我们不能自己去实例化一个 Runtime 对象，只能通过 getRuntime() 静态方法来获得。

Runtime 类的的代码实现如下所示。这里面只包含部分相关代码，其他代码做了省略。从代码中，我们也可以看出，它使用了最简单的饿汉式的单例实现方式。

```java
/**
 * Every Java application has a single instance of class
 * <code>Runtime</code> that allows the application to interface with
 * the environment in which the application is running. The current
 * runtime can be obtained from the <code>getRuntime</code> method.
 * <p>
 * An application cannot create its own instance of this class.
 *
 * @author  unascribed
 * @see     java.lang.Runtime#getRuntime()
 * @since   JDK1.0
 */
public class Runtime {
  private static Runtime currentRuntime = new Runtime();

  public static Runtime getRuntime() {
    return currentRuntime;
  }
  
  /** Don't let anyone else instantiate this class */
  private Runtime() {}
  
  //....
  public void addShutdownHook(Thread hook) {
    SecurityManager sm = System.getSecurityManager();
    if (sm != null) {
       sm.checkPermission(new RuntimePermission("shutdownHooks"));
    }
    ApplicationShutdownHooks.add(hook);
  }
  //...
}
```

### 其他模式在 JDK 中的应用汇总

实际上，我们在讲解理论部分的时候，已经讲过很多模式在 Java JDK 中的应用了。这里我们一块再回顾一下，如果你对哪一部分有所遗忘，可以再回过头去看下。

在讲到模板模式的时候，我们结合 Java Servlet、JUnit TestCase、Java InputStream、Java AbstractList 四个例子，来具体讲解了它的两个作用：扩展性和复用性。

在讲到享元模式的时候，我们讲到 Integer 类中的 -128~127 之间的整型对象是可以复用的，还讲到 String 类型中的常量字符串也是可以复用的。这些都是享元模式的经典应用。

在讲到职责链模式的时候，我们讲到Java Servlet 中的 Filter 就是通过职责链来实现的，同时还对比了 Spring 中的 interceptor。实际上，拦截器、过滤器这些功能绝大部分都是采用职责链模式来实现的。

在讲到的迭代器模式的时候，我们重点剖析了 Java 中 Iterator 迭代器的实现，手把手带你实现了一个针对线性数据结构的迭代器。

### 重点回顾

这两节课主要剖析了 JDK 中用到的几个经典设计模式，其中重点剖析的有：工厂模式、建造者模式、装饰器模式、适配器模式、模板模式、观察者模式，除此之外，我们还汇总了其他模式在 JDK 中的应用，比如：单例模式、享元模式、职责链模式、迭代器模式。

实际上，源码都很简单，理解起来都不难，都没有跳出我们之前讲解的理论知识的范畴。学习的重点并不是表面上去理解、记忆某某类用了某某设计模式，而是让你了解我反复强调的一点，也是标题中突出的一点，在真实的项目开发中，如何灵活应用设计模式，做到活学活用，能够根据具体的场景、需求，做灵活的设计和实现上的调整。这也是模式新手和老手的最大区别。

### 课堂讨论

针对 Java JDK 中观察者模式的代码实现，我有两个问题请你思考。

1. 每个函数都加一把 synchronized 大锁，会不会影响并发性能？有没有优化的方法？
2. changed 成员变量是否多此一举？

## 84 | 开源实战四（上）：剖析Spring框架中蕴含的经典设计思想或原则

在 Java 世界里，Spring 框架已经几乎成为项目开发的必备框架。作为如此优秀和受欢迎的开源项目，它是我们源码阅读的首选材料之一，不管是设计思想，还是代码实现，都有很多值得我们学习的地方。接下来，我们就详细讲讲 Spring 框架中蕴含的设计思想、原则和模式。因为内容比较多，我分三部分来讲解。

- 第一部分，我们讲解 Spring 框架中蕴含的经典设计思想或原则。
- 第二部分，我们讲解 Spring 框架中用来支持扩展的两种设计模式。
- 第三部分，我们总结罗列 Spring 框架中用到的其他十几种设计模式。

### Spring 框架简单介绍

考虑到你可能不熟悉 Spring，我这里对它做下简单介绍。我们常说的 Spring 框架，是指 Spring Framework 基础框架。Spring Framework 是整个 Spring 生态（也被称作 Spring 全家桶）的基石。除了 Spring Framework，Spring 全家桶中还有更多基于 Spring Framework 开发出来的、整合更多功能的框架，比如 Spring Boot、Spring Cloud。

在 Spring 全家桶中，Spring Framework 是最基础、最底层的一部分。它提供了最基础、最核心的 IOC 和 AOP 功能。当然，它包含的功能还不仅如此，还有其他比如事务管理（Transactions）、MVC 框架（Spring MVC）等很多功能。下面这个表格，是我从 Spring 官网上找的，关于 Spring Framework 的功能介绍，你可以大略地看下有个印象。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210509221134.jpeg" alt="img" style="zoom:33%;" />

在 Spring Framework 中，Spring MVC 出镜率很高，经常被单独拎出来使用。它是支持 Web 开发的 MVC 框架，提供了 URL 路由、Session 管理、模板引擎等跟 Web 开发相关的一系列功能。

Spring Boot 是基于 Spring Framework 开发的。它更加专注于微服务开发。之所以名字里带有“Boot”一词，跟它的设计初衷有关。Spring Boot 的设计初衷是快速启动一个项目，利用它可以快速地实现一个项目的开发、部署和运行。Spring Boot 支持的所有功能都是围绕着这个初衷设计的，比如：集成很多第三方开发包、简化配置（比如，规约优于配置）、集成内嵌 Web 容器（比如，Tomcat、Jetty）等。

单个的微服务开发，使用 Spring Boot 就足够了，但是，如果要构建整个微服务集群，就需要用到 Spring Cloud 了。Spring Cloud 主要负责微服务集群的服务治理工作，包含很多独立的功能组件，比如 Spring Cloud Sleuth 调用链追踪、Spring Cloud Config 配置中心等。

### 从 Spring 看框架的作用

如果你使用过一些框架来做开发，你应该能感受到使用框架开发的优势。这里我稍微总结一下。利用框架的好处有：解耦业务和非业务开发、让程序员聚焦在业务开发上；隐藏复杂实现细节、降低开发难度、减少代码 bug；实现代码复用、节省开发时间；规范化标准化项目开发、降低学习和维护成本等等。实际上，如果要用一句话来总结一下的话，那就是简化开发！

对于刚刚的总结，我们再详细解释一下。

相比单纯的 CRUD 业务代码开发，非业务代码开发要更难一些。所以，将一些非业务的通用代码开发为框架，在项目中复用，除了节省开发时间之外，也降低了项目开发的难度。除此之外，框架经过多个项目的多次验证，比起每个项目都重新开发，代码的 bug 会相对少一些。而且，不同的项目使用相同的框架，对于研发人员来说，从一个项目切换到另一个项目的学习成本，也会降低很多。

接下来，我们再拿常见的 Web 项目开发来举例说明一下。

通过在项目中引入 Spring MVC 开发框架，开发一个 Web 应用，我们只需要创建 Controller、Service、Repository 三层类，在其中填写相应的业务代码，然后做些简单的配置，告知框架 Controller、Service、Repository 类之间的调用关系，剩下的非业务相关的工作，比如，对象的创建、组装、管理，请求的解析、封装，URL 与 Controller 之间的映射，都由框架来完成。

不仅如此，如果我们直接引入功能更强大的 Spring Boot，那将应用部署到 Web 容器的工作都省掉了。Spring Boot 内嵌了 Tomcat、Jetty 等 Web 容器。在编写完代码之后，我们用一条命令就能完成项目的部署、运行。

### Spring 框架蕴含的设计思想

在 Google Guava 源码讲解中，我们讲到开发通用功能模块的一些比较普适的开发思想，比如产品意识、服务意识、代码质量意识、不要重复早轮子等。今天，我们剖析一下 Spring 框架背后的一些经典设计思想（或开发技巧）。这些设计思想并非 Spring 独有，都比较通用，能借鉴应用在很多通用功能模块的设计开发中。这也是我们学习 Spring 源码的价值所在。

1. 约定优于配置

在使用 Spring 开发的项目中，配置往往会比较复杂、繁琐。比如，我们利用 Spring MVC 来开发 Web 应用，需要配置每个 Controller 类以及 Controller 类中的接口对应的 URL。

如何来简化配置呢？一般来讲，有两种方法，一种是基于注解，另一种是基于约定。这两种配置方式在 Spring 中都有用到。Spring 在最小化配置方面做得淋漓尽致，有很多值得我们借鉴的地方。

基于注解的配置方式，我们在指定类上使用指定的注解，来替代集中的 XML 配置。比如，我们使用 @RequestMapping 注解，在 Controller 类或者接口上，标注对应的 URL；使用 @Transaction 注解表明支持事务等。

基于约定的配置方式，也常叫作“约定优于配置”或者“规约优于配置”（Convention over Configuration）。通过约定的代码结构或者命名来减少配置。说直白点，就是提供配置的默认值，优先使用默认值。程序员只需要设置那些偏离约定的配置就可以了。

比如，在 Spring JPA（基于 ORM 框架、JPA 规范的基础上，封装的一套 JPA 应用框架）中，我们约定类名默认跟表名相同，属性名默认跟表字段名相同，String 类型对应数据库中的 varchar 类型，long 类型对应数据库中的 bigint 类型等等。

基于刚刚的约定，代码中定义的 Order 类就对应数据库中的“order”表。只有在偏离这一约定的时候，例如数据库中表命名为“order_info”而非“order”，我们才需要显示地去配置类与表的映射关系（Order 类 ->order_info 表）。

实际上，约定优于配置，很好地体现了“二八法则”。在平时的项目开发中，80% 的配置使用默认配置就可以了，只有 20% 的配置必须用户显式地去设置。所以，基于约定来配置，在没有牺牲配置灵活性的前提下，节省了我们大量编写配置的时间，省掉了很多不动脑子的纯体力劳动，提高了开发效率。除此之外，基于相同的约定来做开发，也减少了项目的学习成本和维护成本。

2. 低侵入、松耦合

框架的侵入性是衡量框架好坏的重要指标。所谓低侵入指的是，框架代码很少耦合在业务代码中。低侵入意味着，当我们要替换一个框架的时候，对原有的业务代码改动会很少。相反，如果一个框架是高度侵入的，代码高度侵入到业务代码中，那替换成另一个框架的成本将非常高，甚至几乎不可能。这也是一些长期维护的老项目，使用的框架、技术比较老旧，又无法更新的一个很重要的原因。

实际上，低侵入是 Spring 框架遵循的一个非常重要的设计思想。

Spring 提供的 IOC 容器，在不需要 Bean 继承任何父类或者实现任何接口的情况下，仅仅通过配置，就能将它们纳入进 Spring 的管理中。如果我们换一个 IOC 容器，也只是重新配置一下就可以了，原有的 Bean 都不需要任何修改。

除此之外，Spring 提供的 AOP 功能，也体现了低侵入的特性。在项目中，对于非业务功能，比如请求日志、数据采点、安全校验、事务等等，我们没必要将它们侵入进业务代码中。因为一旦侵入，这些代码将分散在各个业务代码中，删除、修改的成本就变得很高。而基于 AOP 这种开发模式，将非业务代码集中放到切面中，删除、修改的成本就变得很低了。

3. 模块化、轻量级

我们知道，十几年前，EJB 是 Java 企业级应用的主流开发框架。但是，它非常臃肿、复杂，侵入性、耦合性高，开发、维护和学习成本都不低。所以，为了替代笨重的 EJB，Rod Johnson 开发了一套开源的 Interface21 框架，提供了最基本的 IOC 功能。实际上，Interface21 框架就是 Spring 框架的前身。

但是，随着不断的发展，Spring 现在也不单单只是一个只包含 IOC 功能的小框架了，它显然已经壮大成了一个“平台”或者叫“生态”，包含了各种五花八门的功能。尽管如此，但它也并没有重蹈覆辙，变成一个像 EJB 那样的庞大难用的框架。那 Spring 是怎么做到的呢？

这就要归功于 Spring 的模块化设计思想。我们先看一张图，如下所示，它是 Spring Framework 的模块和分层介绍图。

<img src="https://gitee.com/haojunsheng/ImageHost/raw/master/img/20210509231557.png" alt="img" style="zoom: 33%;" />

从图中我们可以看出，Spring 在分层、模块化方面做得非常好。每个模块都只负责一个相对独立的功能。模块之间关系，仅有上层对下层的依赖关系，而同层之间以及下层对上层，几乎没有依赖和耦合。除此之外，在依赖 Spring 的项目中，开发者可以有选择地引入某几个模块，而不会因为需要一个小的功能，就被强迫引入整个 Spring 框架。所以，尽管 Spring Framework 包含的模块很多，已经有二十几个，但每个模块都非常轻量级，都可以单独拿来使用。正因如此，到现在，Spring 框架仍然可以被称为是一个轻量级的开发框架。

4. 再封装、再抽象

Spring 不仅仅提供了各种 Java 项目开发的常用功能模块，而且还对市面上主流的中间件、系统的访问类库，做了进一步的封装和抽象，提供了更高层次、更统一的访问接口。

比如，Spring 提供了 spring-data-redis 模块，对 Redis Java 开发类库（比如 Jedis、Lettuce）做了进一步的封装，适配 Spring 的访问方式，让编程访问 Redis 更加简单。

还有我们下节课要讲的 Spring Cache，实际上也是一种再封装、再抽象。它定义了统一、抽象的 Cache 访问接口，这些接口不依赖具体的 Cache 实现（Redis、Guava Cache、Caffeine 等）。在项目中，我们基于 Spring 提供的抽象统一的接口来访问 Cache。这样，我们就能在不修改代码的情况下，实现不同 Cache 之间的切换。

除此之外，还记得我们之前在模板模式中，讲过的 JdbcTemplate 吗？实际上，它也是对 JDBC 的进一步封装和抽象，为的是进一步简化数据库编程。不仅如此，Spring 对 JDBC 异常也做了进一步的封装。封装的数据库异常继承自 DataAccessException 运行时异常。这类异常在开发中无需强制捕获，从而减少了不必要的异常捕获和处理。除此之外，Spring 封装的数据库异常，还屏蔽了不同数据库异常的细节（比如，不同的数据库对同一报错定义了不同的错误码），让异常的处理更加简单。

### 重点回顾

借助 Spring 框架，我们总结了框架的作用：解耦业务和非业务开发、让程序员聚焦在业务开发上；隐藏复杂实现细节、降低开发难度、减少代码 bug；实现代码复用、节省开发时间；规范化标准化项目开发、降低学习和维护成本等。实际上，如果要用一句话来总结一下的话，那就是简化开发！

除此之外，我们还重点讲解了 Sping 背后蕴含的一些经典设计思想，主要有：约定优于配置，低侵入、松耦合，模块化、轻量级，再封装、再抽象。这些设计思想都比较通用，我们可以借鉴到其他框架的开发中。

### 课堂讨论

1. “约定优于配置”在很多开发场景中都有体现，比如 Maven、Gradle 构建工具，它们约定了一套默认的项目目录结构，除此之外，你还能想到体现这条设计思想的其他哪些开发场景吗？
2. 参照 Spring 的设计思想，分析一个你熟悉框架、类库、功能组件背后的设计思想。

## 85 | 开源实战四（中）：剖析Spring框架中用来支持扩展的两种设计模式

### 观察者模式在 Spring 中的应用

在前面我们讲到，Java、Google Guava 都提供了观察者模式的实现框架。Java 提供的框架比较简单，只包含 java.util.Observable 和 java.util.Observer 两个类。Google Guava 提供的框架功能比较完善和强大：通过 EventBus 事件总线来实现观察者模式。实际上，Spring 也提供了观察者模式的实现框架。今天，我们就再来讲一讲它。

Spring 中实现的观察者模式包含三部分：Event 事件（相当于消息）、Listener 监听者（相当于观察者）、Publisher 发送者（相当于被观察者）。我们通过一个例子来看下，Spring 提供的观察者模式是怎么使用的。代码如下所示：

```java
// Event事件
public class DemoEvent extends ApplicationEvent {
  private String message;

  public DemoEvent(Object source, String message) {
    super(source);
  }

  public String getMessage() {
    return this.message;
  }
}

// Listener监听者
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
  @Override
  public void onApplicationEvent(DemoEvent demoEvent) {
    String message = demoEvent.getMessage();
    System.out.println(message);
  }
}

// Publisher发送者
@Component
public class DemoPublisher {
  @Autowired
  private ApplicationContext applicationContext;

  public void publishEvent(DemoEvent demoEvent) {
    this.applicationContext.publishEvent(demoEvent);
  }
}
```

从代码中，我们可以看出，框架使用起来并不复杂，主要包含三部分工作：定义一个继承 ApplicationEvent 的事件（DemoEvent）；定义一个实现了 ApplicationListener 的监听器（DemoListener）；定义一个发送者（DemoPublisher），发送者调用 ApplicationContext 来发送事件消息。

其中，ApplicationEvent 和 ApplicationListener 的代码实现都非常简单，内部并不包含太多属性和方法。实际上，它们最大的作用是做类型标识之用（继承自 ApplicationEvent 的类是事件，实现 ApplicationListener 的类是监听器）。

### 模板模式在 Spring 中的应用

刚刚讲的是观察者模式在 Spring 中的应用，现在我们再讲下模板模式。

我们来看下一下经常在面试中被问到的一个问题：请你说下 Spring Bean 的创建过程包含哪些主要的步骤。这其中就涉及模板模式。它也体现了 Spring 的扩展性。利用模板模式，Spring 能让用户定制 Bean 的创建过程。

Spring Bean 的创建过程，可以大致分为两大步：对象的创建和对象的初始化。

对象的创建是通过反射来动态生成对象，而不是 new 方法。不管是哪种方式，说白了，总归还是调用构造函数来生成对象，没有什么特殊的。对象的初始化有两种实现方式。一种是在类中自定义一个初始化函数，并且通过配置文件，显式地告知 Spring，哪个函数是初始化函数。我举了一个例子解释一下。如下所示，在配置文件中，我们通过 init-method 属性来指定初始化函数。

```java
public class DemoClass {
  //...
  
  public void initDemo() {
    //...初始化..
  }
}

// 配置：需要通过init-method显式地指定初始化方法
<bean id="demoBean" class="com.xzg.cd.DemoClass" init-method="initDemo"></bean>
```

这种初始化方式有一个缺点，初始化函数并不固定，由用户随意定义，这就需要 Spring 通过反射，在运行时动态地调用这个初始化函数。而反射又会影响代码执行的性能，那有没有替代方案呢？

Spring 提供了另外一个定义初始化函数的方法，那就是让类实现 Initializingbean 接口。这个接口包含一个固定的初始化函数定义（afterPropertiesSet() 函数）。Spring 在初始化 Bean 的时候，可以直接通过 bean.afterPropertiesSet() 的方式，调用 Bean 对象上的这个函数，而不需要使用反射来调用了。我举个例子解释一下，代码如下所示。

```java
public class DemoClass implements InitializingBean{
  @Override
  public void afterPropertiesSet() throws Exception {
    //...初始化...      
  }
}

// 配置：不需要显式地指定初始化方法
<bean id="demoBean" class="com.xzg.cd.DemoClass"></bean>
```

尽管这种实现方式不会用到反射，执行效率提高了，但业务代码（DemoClass）跟框架代码（InitializingBean）耦合在了一起。框架代码侵入到了业务代码中，替换框架的成本就变高了。所以，我并不是太推荐这种写法。

实际上，在 Spring 对 Bean 整个生命周期的管理中，还有一个跟初始化相对应的过程，那就是 Bean 的销毁过程。我们知道，在 Java 中，对象的回收是通过 JVM 来自动完成的。但是，我们可以在将 Bean 正式交给 JVM 垃圾回收前，执行一些销毁操作（比如关闭文件句柄等等）。

销毁过程跟初始化过程非常相似，也有两种实现方式。一种是通过配置 destroy-method 指定类中的销毁函数，另一种是让类实现 DisposableBean 接口。因为 destroy-method、DisposableBean 跟 init-method、InitializingBean 非常相似，所以，这部分我们就不详细讲解了，你可以自行研究下。

实际上，Spring 针对对象的初始化过程，还做了进一步的细化，将它拆分成了三个小步骤：初始化前置操作、初始化、初始化后置操作。其中，中间的初始化操作就是我们刚刚讲的那部分，初始化的前置和后置操作，定义在接口 BeanPostProcessor 中。BeanPostProcessor 的接口定义如下所示：

```java
public interface BeanPostProcessor {
  Object postProcessBeforeInitialization(Object var1, String var2) throws BeansException;

  Object postProcessAfterInitialization(Object var1, String var2) throws BeansException;
}
```

我们再来看下，如何通过 BeanPostProcessor 来定义初始化前置和后置操作？

我们只需要定义一个实现了 BeanPostProcessor 接口的处理器类，并在配置文件中像配置普通 Bean 一样去配置就可以了。Spring 中的 ApplicationContext 会自动检测在配置文件中实现了 BeanPostProcessor 接口的所有 Bean，并把它们注册到 BeanPostProcessor 处理器列表中。在 Spring 容器创建 Bean 的过程中，Spring 会逐一去调用这些处理器。

通过上面的分析，我们基本上弄清楚了 Spring Bean 的整个生命周期（创建加销毁）。针对这个过程，我画了一张图，你可以结合着刚刚讲解一块看下。

<img src="https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20210510174832.jpg" alt="img" style="zoom: 33%;" />

不过，你可能会说，这里哪里用到了模板模式啊？模板模式不是需要定义一个包含模板方法的抽象模板类，以及定义子类实现模板方法吗？

实际上，这里的模板模式的实现，并不是标准的抽象类的实现方式，而是有点类似我们前面讲到的 Callback 回调的实现方式，也就是将要执行的函数封装成对象（比如，初始化方法封装成 InitializingBean 对象），传递给模板（BeanFactory）来执行。

### 重点回顾

今天我讲到了 Spring 中用到的两种支持扩展的设计模式，观察者模式和模板模式。

其中，观察者模式在 Java、Google Guava、Spring 中都有提供相应的实现代码。在平时的项目开发中，基于这些实现代码，我们可以轻松地实现一个观察者模式。

Java 提供的框架比较简单，只包含 java.util.Observable 和 java.util.Observer 两个类。Google Guava 提供的框架功能比较完善和强大，可以通过 EventBus 事件总线来实现观察者模式。Spring 提供了观察者模式包含 Event 事件、Listener 监听者、Publisher 发送者三部分。事件发送到 ApplicationContext 中，然后，ApplicationConext 将消息发送给事先注册好的监听者。

除此之外，我们还讲到模板模式在 Spring 中的一个典型应用，那就是 Bean 的创建过程。Bean 的创建包含两个大的步骤，对象的创建和对象的初始化。其中，对象的初始化又可以分解为 3 个小的步骤：初始化前置操作、初始化、初始化后置操作。

## 86 | 开源实战四（下）：总结Spring框架用到的11种设计模式

上一节课，我们讲解了 Spring 中支持扩展功能的两种设计模式：观察者模式和模板模式。这两种模式能够帮助我们创建扩展点，让框架的使用者在不修改源码的情况下，基于扩展点定制化框架功能。

实际上，Spring 框架中用到的设计模式非常多，不下十几种。我们今天就总结罗列一下它们。限于篇幅，我不可能对每种设计模式都进行非常详细的讲解。有些前面已经讲过的或者比较简单的，我就点到为止。如果有什么不是很懂的地方，你可以通过阅读源码，查阅之前的理论讲解，自己去搞定它。如果一直跟着我的课程学习，相信你现在已经具备这样的学习能力。

### 适配器模式在 Spring 中的应用

在 Spring MVC 中，定义一个 Controller 最常用的方式是，通过 @Controller 注解来标记某个类是 Controller 类，通过 @RequesMapping 注解来标记函数对应的 URL。不过，定义一个 Controller 远不止这一种方法。我们还可以通过让类实现 Controller 接口或者 Servlet 接口，来定义一个 Controller。针对这三种定义方式，我写了三段示例代码，如下所示：

```java
// 方法一：通过@Controller、@RequestMapping来定义
@Controller
public class DemoController {
    @RequestMapping("/employname")
    public ModelAndView getEmployeeName() {
        ModelAndView model = new ModelAndView("Greeting");        
        model.addObject("message", "Dinesh");       
        return model; 
    }  
}

// 方法二：实现Controller接口 + xml配置文件:配置DemoController与URL的对应关系
public class DemoController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView model = new ModelAndView("Greeting");
        model.addObject("message", "Dinesh Madhwal");
        return model;
    }
}

// 方法三：实现Servlet接口 + xml配置文件:配置DemoController类与URL的对应关系
public class DemoServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    this.doPost(req, resp);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.getWriter().write("Hello World.");
  }
}
```

在应用启动的时候，Spring 容器会加载这些 Controller 类，并且解析出 URL 对应的处理函数，封装成 Handler 对象，存储到 HandlerMapping 对象中。当有请求到来的时候，DispatcherServlet 从 HanderMapping 中，查找请求 URL 对应的 Handler，然后调用执行 Handler 对应的函数代码，最后将执行结果返回给客户端。



## 87 | 开源实战五（上）：MyBatis如何权衡易用性、性能和灵活性？

### Mybatis 和 ORM 框架介绍

熟悉 Java 的同学应该知道，MyBatis 是一个 ORM（Object Relational Mapping，对象 - 关系映射）框架。ORM 框架主要是根据类和数据库表之间的映射关系，帮助程序员自动实现对象与数据库中数据之间的互相转化。说得更具体点就是，ORM 负责将程序中的对象存储到数据库中、将数据库中的数据转化为程序中的对象。实际上，Java 中的 ORM 框架有很多，除了刚刚提到的 MyBatis 之外，还有 Hibernate、TopLink 等。

在剖析 Spring 框架的时候，我们讲到，如果用一句话来总结框架作用的话，那就是简化开发。MyBatis 框架也不例外。它简化的是数据库方面的开发。那 MyBatis 是如何简化数据库开发的呢？我们结合第 59 讲中的 JdbcTemplate 的例子来说明一下。

在第 59 讲中，我们讲到，Java 提供了 JDBC 类库来封装不同类型的数据库操作。不过，直接使用 JDBC 来进行数据库编程，还是有点麻烦的。于是，Spring 提供了 JdbcTemplate，对 JDBC 进一步封装，来进一步简化数据库编程。

使用 JdbcTemplate 进行数据库编程，我们只需要编写跟业务相关的代码（比如，SQL 语句、数据库中数据与对象之间的互相转化的代码），其他流程性质的代码（比如，加载驱动、创建数据库连接、创建 statement、关闭连接、关闭 statement 等）都封装在了 JdbcTemplate 类中，不需要我们重复编写。

当时，为了展示使用 JdbcTemplate 是如何简化数据库编程的，我们还举了一个查询数据库中用户信息的例子。还是同样这个例子，我再来看下，使用 MyBatis 该如何实现，是不是比使用 JdbcTemplate 更加简单。

因为 MyBatis 依赖 JDBC 驱动，所以，在项目中使用 MyBatis，除了需要引入 MyBatis 框架本身（mybatis.jar）之外，还需要引入 JDBC 驱动（比如，访问 MySQL 的 JDBC 驱动实现类库 mysql-connector-java.jar）。将两个 jar 包引入项目之后，我们就可以开始编程了。使用 MyBatis 来访问数据库中用户信息的代码如下所示：

```java
// 1. 定义UserDO
public class UserDo {
  private long id;
  private String name;
  private String telephone;
  // 省略setter/getter方法
}

// 2. 定义访问接口
public interface UserMapper {
  public UserDo selectById(long id);
}

// 3. 定义映射关系：UserMapper.xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org/DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="cn.xzg.cd.a87.repo.mapper.UserMapper">
    <select id="selectById" resultType="cn.xzg.cd.a87.repo.UserDo">
        select * from user where id=#{id}
    </select>
</mapper>

// 4. 全局配置文件: mybatis.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"></transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8" />
                <property name="username" value="root" />
                <property name="password" value="..." />
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
</configuration>
```

需要注意的是，在 UserMapper.xml 配置文件中，我们只定义了接口和 SQL 语句之间的映射关系，并没有显式地定义类（UserDo）字段与数据库表（user）字段之间的映射关系。实际上，这就体现了“约定优于配置”的设计原则。类字段与数据库表字段之间使用了默认映射关系：类字段跟数据库表中拼写相同的字段一一映射。当然，如果没办法做到一一映射，我们也可以自定义它们之间的映射关系。

有了上面的代码和配置，我们就可以像下面这样来访问数据库中的用户信息了。

```java
public class MyBatisDemo {
  public static void main(String[] args) throws IOException {
    Reader reader = Resources.getResourceAsReader("mybatis.xml");
    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
    SqlSession session = sessionFactory.openSession();
    UserMapper userMapper = session.getMapper(UserMapper.class);
    UserDo userDo = userMapper.selectById(8);
    //...
  }
}
```

![image-20210510204351089](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20210510204407.png)

从代码中，我们可以看出，相对于使用 JdbcTemplate 的实现方式，使用 MyBatis 的实现方式更加灵活。在使用 JdbcTemplate 的实现方式中，对象与数据库中数据之间的转化代码、SQL 语句，是硬编码在业务代码中的。而在使用 MyBatis 的实现方式中，类字段与数据库字段之间的映射关系、接口与 SQL 之间的映射关系，是写在 XML 配置文件中的，是跟代码相分离的，这样会更加灵活、清晰，维护起来更加方便。

### 如何平衡易用性、性能和灵活性？

刚刚我们对 MyBatis 框架做了简单介绍，接下来，我们再对比一下另外两个框架：JdbcTemplate 和 Hibernate。通过对比我们来看，MyBatis 是如何权衡代码的易用性、性能和灵活性的。

我们先来看 JdbcTemplate。相对于 MyBatis 来说，JdbcTemplate 更加轻量级。因为它对 JDBC 只做了很简单的封装，所以性能损耗比较少。相对于其他两个框架来说，它的性能最好。但是，它的缺点也比较明显，那就是 SQL 与代码耦合在一起，而且不具备 ORM 的功能，需要自己编写代码，解析对象跟数据库中的数据之间的映射关系。所以，在易用性上它不及其他两个框架。

我们再来看 Hibernate。相对于 MyBatis 来说，Hibernate 更加重量级。Hibernate 提供了更加高级的映射功能，能够根据业务需求自动生成 SQL 语句。我们不需要像使用 MyBatis 那样自己编写 SQL。因此，有的时候，我们也把 MyBatis 称作半自动化的 ORM 框架，把 Hibernate 称作全自动化的 ORM 框架。不过，虽然自动生成 SQL 简化了开发，但是毕竟是自动生成的，没有针对性的优化。在性能方面，这样得到的 SQL 可能没有程序员编写得好。同时，这样也丧失了程序员自己编写 SQL 的灵活性。

实际上，不管用哪种实现方式，从数据库中取出数据并且转化成对象，这个过程涉及的代码逻辑基本是一致的。不同实现方式的区别，只不过是哪部分代码逻辑放到了哪里。有的框架提供的功能比较强大，大部分代码逻辑都由框架来完成，程序员只需要实现很小的一部分代码就可以了。这样框架的易用性就更好些。但是，框架集成的功能越多，为了处理逻辑的通用性，就会引入更多额外的处理代码。比起针对具体问题具体编程，这样性能损耗就相对大一些。

JdbcTemplate 提供的功能最简单，易用性最差，性能损耗最少，用它编程性能最好。Hibernate 提供的功能最完善，易用性最好，但相对来说性能损耗就最高了。MyBatis 介于两者中间，在易用性、性能、灵活性三个方面做到了权衡。它支持程序员自己编写 SQL，能够延续程序员对 SQL 知识的积累。相对于完全黑盒子的 Hibernate，很多程序员反倒是更加喜欢 MyBatis 这种半透明的框架。这也提醒我们，过度封装，提供过于简化的开发方式，也会丧失开发的灵活性。

### 重点回顾

如果你熟悉 Java 和 MyBatis，那你应该掌握今天讲到 JDBC、JdbcTemplate、MyBatis、Hibernate 之间的区别。JDBC 是 Java 访问数据库的开发规范，提供了一套抽象的统一的开发接口，隐藏不同数据库的访问细节。

JdbcTemplate、MyBatis、Hibernate 都是对 JDBC 的二次封装，为的是进一步简化数据库开发。其中，JdbcTemplate 不能算得上是 ORM 框架，因为还需要程序员自己编程来实现对象和数据库数据之间的互相转化。相对于 Hibernate 这种连 SQL 都不用程序员自己写的全自动 ORM 框架，MyBatis 算是一种半自动化的 ORM 框架。

# 开源与项目实战：项目实战

















































