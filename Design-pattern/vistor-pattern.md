# 综述
　　观察者模式（Observer Pattern）也叫做发布-订阅(Publish/Subscribe)模式、模型-视图(Model/View)模式。这个模式的一个最重要的作用就是解耦。也就是将被观察者和观察者进行解耦，使得他们之间的依赖性更小，甚至做到毫无依赖。在观察者模式中它定义了一种一对多的依赖关系，使得当每一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。下面就来看一下观察者模式的具体实现。

# 观察者模式实现
　　在这里我们假定一个场景，在一个图书管理系统中，现在有这么一个需求，部分读者希望在图书馆添加新书的时候能够收到通知。于是这些读者就订阅该功能。而当图书馆添加新书的时候，系统会自动将新书的信息发送给订阅的读者。 
　　上面这个场景就是典型的观察者模式。对于读者我们可以称为观察者，而图书管理系统则可以作为被观察者。在图书管理系统（被观察者）中去添加注册需要接收通知的读者（观察者）。这时候在系统中添加新书后，系统便会通知在系统注册该功能的读者。下面看一下代码实现。 
　　既然是观察者模式，首先我们需要创建一个观察者接口。

```
public interface Observer {
    public void update(Object object);
}
```

　　紧接着我们在创建一个被观察者，对于被观察者中，里面的功能必然有添加一个观察者，删除观察者，通知更新观察者这三个公共方法，于是我们可以写一个抽象类。

```java
public abstract class Observable {
//定义一个观察这数组
private Vector<Observer> obVector = new Vector<>();

//添加一个观察者
public void addObserver(Observer observer) {
    this.obVector.add(observer);
}

//删除一个观察者
public void delObserver(Observer observer) {
    this.obVector.remove(observer);
}

//通知所有观察者
public void notifyObservers(Book book) {
    for (Observer observer : obVector) {
        observer.update(book);
    }
}
}
```


　　下面我们就来实现上述场景。既然是图书管理系统，我们首先需要创建一个Book类。

    public class Book {
    //书名
    public String bookName;
    //作者
    public String author;
    
    public Book(String bookName, String author) {
        this.bookName = bookName;
        this.author = author;
    }
    
    @Override
    public String toString() {
        return "Book [bookName=" + bookName + ", author=" + author + "]";
    }
    }

　　下面创建一个Library类，由于这个Library作为被观察者，我们使其继承自抽象类Observable。



```java
import java.util.ArrayList;
import java.util.List;

public class Library extends Observable{
//使用list用于存放图书
private List<Book> bookList;

public Library() {
    // TODO Auto-generated constructor stub
    this.bookList = new ArrayList<>();
    //添加两本书
    Book android = new Book("Android","李江东");
    Book HongLou = new Book("红楼梦", "曹雪芹");
    this.bookList.add(android);
    this.bookList.add(HongLou);
}

public void addBook(Book book) {
    this.bookList.add(book);
    super.notifyObservers(book);
}

public void delBook(Book book) {
    this.bookList.remove(book);
}
}
```


　　下面再创建两个读者类ReaderA，ReaderB。它们作为观察者，就叫他们实现Observer接口。 
　　ReaderA



    public class ReaderA implements Observer{
    public ReaderA() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public void update(Object object) {
        // TODO Auto-generated method stub
        System.out.println("我是读者A,收到了新书:" + object.toString());
    }
    }


　　ReaderB



    public class ReaderB implements Observer{
    public ReaderB() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public void update(Object object) {
        // TODO Auto-generated method stub
        System.out.println("我是读者B,收到了新书:"+object.toString());
    }
    }

　　下面我们就来测试一下结果。



    public class Test {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Library library = new Library();
        Observer readerAObserver = new ReaderA();
        Observer readerBObserver = new ReaderB();
        //添加读者A
        library.addObserver(readerAObserver);
        //添加读者B
        library.addObserver(readerBObserver);
        //添加一本新书
        Book book = new Book("朝花夕拾", "鲁迅");
        library.addBook(book);
    }
    }

　　最后我们看一下运行结果。 

# 观察者模式通用代码
　　首先我们看一下观察者模式的通用类图 

![](https://ws2.sinaimg.cn/large/006tKfTcly1g0boxakgd8j30qs0f0myh.jpg)


　　在这里对上面类图中的角色进行一下详细介绍

Subject:抽象主题，也就是上面的被观察者(Observable)角色。Subject把所有观察者对象的引用保存在一个集合里，并且能够动态的增加、取消观察者。
Observer:抽象观察者，他是观察者的抽象类，在这里定义个一个更新的接口，目的就是为了在接收到被观察者的更改通知是更新自己。
ConcreteSubject:具体主题，也就是具体的被观察者，在上面对应于Library类。在ConcreteSubject里面定义一些被观察者自己的业务逻辑，当ConcreteSubject内部状态发生改变时，给所有注册过的观察者发送通知。
ConcreteObserver:具体观察者，ConcreteObserver实现了抽象观察者所定义的更新接口，再被观察者通知改变是更新自身状态。 
　

# 下面我们就来看一下观察者模式的通用代码。 
　　Sunject（被观察者类）


    import java.util.Vector;
    
    public abstract class Subject {
    //定义一个观察这数组
    private Vector<Observer> obVector = new Vector<>();
    
    //添加一个观察者
    public void addObserver(Observer observer) {
        this.obVector.add(observer);
    }
    
    //删除一个观察者
    public void delObserver(Observer observer) {
        this.obVector.remove(observer);
    }
    
    //通知所有观察者
    public void notifyObservers() {
        for (Observer observer : obVector) {
            observer.update();
        }
    }
    　　ConcreteSubject（具体被观察者）


    public class ConcreteSubject extends Subject{
    // 具体业务
    public void doSomething() {
        super.notifyObservers();
    }

　　Observer（观察者类）



public interface Observer {
    public void update();
}
　　Observer（具体观察者）



    public class ConcreteObserver implements Observer{
    public ConcreteObserver() {
        // TODO Auto-generated constructor stub
    }
    //实现更新方法
    @Override
    public void update() {
        // TODO Auto-generated method stub
        System.out.println("我已接收到消息");
    }

　　Client（场景类）

    public class Client {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ConcreteSubject subject = new ConcreteSubject();
        Observer observer = new ConcreteObserver();
        subject.addObserver(observer);
        subject.doSomething();
    }

# Java对观察者模式提供的支持
　　在java.util库里面，提供了一个Observable类和一个Observer接口。下面我们就来看一下这个Observable类和Observer接口。 
　　Observer接口 
　　在Observer接口中只提供了一个update方法，在被观察者发生变化时通过notifyObservers方法通知观察者做出改变，也就是执行了update方法。

package java.util;

public interface Observer {
    void update(Observable o, Object arg);
}

　　Observable类 
　　下面被观察者类也是为我们提供了对于观察者添加，删除，通知观察者改变等方法。当我们的需要通知观察者并且需要调用观察者update方法，我们需要调用setChanged方法。

package java.util;

public class Observable {
    private boolean changed = false;  //标记此 Observable对象为已改变的对象

    private Vector<Observer> obs;
    
    public Observable() {
        obs = new Vector<>();
    }
    
    /**
     * 添加观察者
     */
    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }
    
    /**
     * 删除观察者
     */
    public synchronized void deleteObserver(Observer o) {
        obs.removeElement(o);
    }
    
    public void notifyObservers() {
        notifyObservers(null);
    }
    
     /**
     * 如果当前Observable对象有变化（那时hasChanged 方法会返回true）
     * 调用这个方法通知所有登记的观察者，即调用它们的update()方法
     * 传入Observer和arg作为参数
     */
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
    
    /**
     * 删除所有的观察者
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }
    
    /**
     *将此Observable对象标记为已改变
     */
    protected synchronized void setChanged() {
        changed = true;
    }
    
    /**
     *将此Observable对象标记为未改变
     */
    protected synchronized void clearChanged() {
        changed = false;
    }
    
    /**
     *检测当前的Observable是否发生改变
     */
    public synchronized boolean hasChanged() {
        return changed;
    }
    
    /**
     * 观察者的数量
     */
    public synchronized int countObservers() {
        return obs.size();
    }
    
    　　
下面我们就用Java为我们提供的接口来实现我们文章开始所假定的场景。 
　　Library类

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Library extends Observable{

    private List<Book> bookList;
    
    public Library() {
        // TODO Auto-generated constructor stub
        this.bookList = new ArrayList<>();
        //添加两本书
        Book android = new Book("Android","李江东");
        Book HongLou = new Book("红楼梦", "曹雪芹");
        this.bookList.add(android);
        this.bookList.add(HongLou);
    }
    
    public void addBook(Book book) {
        super.setChanged();
        this.bookList.add(book);
        super.notifyObservers(book);
    }
    
    public void delBook(Book book) {
        this.bookList.remove(book);
    }

　　当我们添加一本书的时候，也就是addBook方法内，我们调用了父类setChanged方法，这样才能够执行观察者的update方法。对于与观察者Reader我们只需要实现java.util中的Observer接口即可。

package com.ljd.example.observer;

import java.util.Observable;
import java.util.Observer;

public class ReaderA implements Observer{

    public ReaderA() {
        // TODO Auto-generated constructor stub
    }
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        System.out.println("我是读者A,收到了新书:" + arg.toString());
    }


　　ReaderB与ReaderA一样，就不在重复粘贴。下面测试一下上述程序。

package com.ljd.example.observer;

import java.util.Observer;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Library library = new Library();
        Observer readerAObserver = new ReaderA();
        Observer readerBObserver = new ReaderB();
    
        //添加读者A
        library.addObserver(readerAObserver);
        //添加读者B
        library.addObserver(readerBObserver);
        //添加一本新书
        Book book = new Book("朝花夕拾", "鲁迅");
        library.addBook(book);
        System.out.println("asdsdsd");
    }
    　　运行结果 
    　　
# 总结

对于观察者模式在Java的设计模式当中也是非常常用的，在Android中对于观察者模式使用的场景也有很多。例如BroadcastReceiver，Eventbus，RxJava等等都采用了观察者模式。

