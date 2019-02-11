**工厂模式定义**：

百度百科：实例化对象，用工厂方法代替new操作。工厂模式是我们最常用的模式了，著名的Jive论坛 ,就大量使用了工厂模式，工厂模式在Java程序系统可以说是随处可见。因为工厂模式就相当于创建实例对象的new，我们经常要根据类Class生成实例对象，如A a=new A() 工厂模式也是用来创建实例对象的，所以以后new时就要多个心眼，是否可以考虑使用工厂模式，虽然这样做，可能多做一些工作，但会给你系统带来更大的可扩展性和尽量少的修改量。

下面的demo源码在这里下载：http://download.csdn.net/detail/qiyijay/6446729

1.下面我们先写个使用了工厂模式的例子

FactoryDemo1.java

```java
interface Fruit{//我们有水果的这类产品  
       public void produce();//产品要一份有说明书  
   }  
     
   class Apple implements Fruit{//产品一：苹果  
       public Apple(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个苹果");//苹果的说明书  
       }  
   }  
     
   class Orange implements Fruit{//产品二：橘子  
       public Orange(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个橘子");//橘子的说明书  
       }  
   }    
     
   class Factory{//这是一个工厂  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           if(className.equals("apple")){  
               f=new Apple();  
           }else{  
               f=new Orange();  
           }  
           return f;  
       }  
   }  
     
   public class FactoryDemo1 {  
       public static void main(String[] args){  
           String s="apple";//客户说需要一个苹果  
           Fruit f=Factory.getInstance(s);//把客户需求放入工厂开始生产  
           f.produce();//打印说明书  
       }  
   } 
```

 

以上实现的就是一个简单的工厂模式，但是假如我现在的产品增多了是不是在工厂中每一次都要加入判断呢？工厂类会变成这样：

   

```java
class Factory{//这是一个工厂  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           if(className.equals("apple")){  
               f=new Apple();  
           }else if(className.equals("Orange"){  
               f=new Orange();  
           }else if(className.equals("梨子"){  
               ...  
           }  
           else if(className.equals("西瓜"){  
               ...  
           }  
           else if(className.equals("飞机大炮..."){  
               ...  
           }  
           return f;  
       }  
   }  
```

2.接下来我们就将工厂模式与反射机制相结合

复制FactoryDemo1.java命名为FactoryDemo2.java，我们只改Factory这个工厂类，改成如下：

```java
  class Factory{  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           try {  
               f=(Fruit)Class.forName(className).newInstance();  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
           return f;  
       }  
   }  
```

将这时需要将FactoryDemo2.java中main方法的字符串不再是s=“apple”。需要写成包名加类名，我这里包是com.demo2所以我的s="com.demo2.Apple"。

运行看看结果~~，这样就减少了很多判断。但是这种方法之后我们发现每次都要把包名和类名加上也比较麻烦。于是就想到了一个方法，比如用一个带键值对的容器去装它

键装的是它的小名，值装的是它的全名（如苹果：   键 ——“apple”；值——“com.demo2.Apple”）。

3.从上面的例子中引申出的问题。添加一个项目配置文件可以解决上面的问题，同时还能学习到配置文件的好处

1）不懂什么是配置文件？

先去百度一下，简单的理解配置文件就是程序生成的一些文件，里面有一些配置信息，每一次程序启动就会加载配置文件里面的信息，比如一个应用的配置文件里面记录下了它的桌面背景为某张图片，下一次这个应用启动背景就自动成为这张背景，就不必每一次去改桌面背景了。

2）使用Properties类创建和读取配置文件。Properties简单的使用方法

   

```java
Properties pro=new Properties();  
           pro.storeToXML(OutputStream, comment);//创建一个配置文件需要一个输出流对象和节点名称。  
           pro.loadFromXML(InputStream)//读取一个XML文件需要一个输入流对象  
           pro.setProperty(key, value)//创建一个键值对  
           String s=pro.getProperty(key)//取值  
```

3）复制FactoryDemo2.java命名为FactoryDemo3.java 代码修改如下

  

```java
 interface Fruit{  
       public void produce();  
   }  
     
   class Apple implements Fruit{  
       public Apple(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个苹果");  
       }  
   }  
     
   class Orange implements Fruit{  
       public Orange(){  
           System.out.println("苹果生产了");  
       }  
       public void produce(){  
           System.out.println("这是一个橘子");  
       }  
   }    
     
   class Factory{  
       public static Fruit getInstance(String className){  
           Fruit f=null;  
           try {  
               f=(Fruit)Class.forName(className).newInstance();  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
           return f;  
       }  
   }  
     
   class PropertiesOperate{//创建一个配置文件操作类  
       private Properties pro=null;  
       File file=new File("D:"+File.separator+"fruit.properties");//创建一个File对象 地址为：D：/fruit.properties  
       public PropertiesOperate(){  
           this.pro=new Properties();  
           if(file.exists()){//文件如果存在  
               try {  
                   pro.loadFromXML(new FileInputStream(file));//加载配置文件  
               } catch (Exception e) {  
                   e.printStackTrace();  
               }  
           }else{//如果不存在  
               this.save();  
           }  
       }  
       public Properties getPro(){  
           return this.pro;  
       }  
       public void save(){//创建一个新的配置文件  
           this.pro.setProperty("apple", "com.demo3.Apple");//添加文件内容  
           this.pro.setProperty("orange", "com.demo3.Orange");//添加文件内容  
           try {  
               this.pro.storeToXML(new FileOutputStream(this.file), "Fruit");//把文件保存到指定目录，节点为Fruit  
           } catch (Exception e) {  
               e.printStackTrace();  
           }  
       }  
   }  
     
   public class FactoryDemo3 {  
       public static void main(String[] args){  
           Properties pro=new PropertiesOperate().getPro();//创建Properties对象  
           Fruit f=Factory.getInstance(pro.getProperty("apple"));//使用getProperty(key)方法获得apple全名  
           f.produce();  
       }  
   }  
```


看看运行结果，是不是一样呢？

4.执行完结果之后大家请看电脑的D盘是否多了一个fruit.properties文件呢?这个就是我们创建出来的配置文件，用记事本打开看看吧

```
   Fruit  
   com.demo3.Apple  
   com.demo3.Orange  
```

​    


相信里面的意义大家一目了然了吧，这其实就是一个xml文件。里面记录的信息就是我们所创建的信息。假如我们把它这个文件改一下，将其中一句

   com.demo3.Apple  
   改成：  
   com.demo3.Orange  

这时再一次执行程序试试。没错，执行结果为：

橘子生产了
这是一个橘子

我们现在没有修改任何的程序代码，只修改了它的配置文件就将其结果改变了，这时大家能体会到配置文件的强大及好处了吧。

5.知识点：1）熟悉工厂模式的原理，2）使用反射机制创建对象与工厂模式想结合，3）配置文件的创建与使用。

总结：上面的例子是用一个简单的程序去实验，也显得很繁琐，但是当项目越来越大的时候，这样的模式对程序的扩展和优化都起到非常大的作用。