> 写在前面：泛型。很重要～



### 普通泛型

```java
class Point< T>{  // 此处可以随便写标识符号，T是type的简称  
 private T var ; // var的类型由T指定，即：由外部指定  
 public T getVar(){ // 返回值的类型由外部决定  
  return var ;  
 }  
 public void setVar(T var){ // 设置的类型也由外部决定  
  this.var = var ;  
 }  
};  
public class GenericsDemo06{  
 public static void main(String args[]){  
  Point< String> p = new Point< String>() ; // 里面的var类型为String类型  
  p.setVar("it") ;  // 设置字符串  
  System.out.println(p.getVar().length()) ; // 取得字符串的长度  
 }  
};  
```

------

```java
class Notepad< K,V>{  // 此处指定了两个泛型类型  
 private K key ;  // 此变量的类型由外部决定  
 private V value ; // 此变量的类型由外部决定  
 public K getKey(){  
  return this.key ;  
 }  
 public V getValue(){  
  return this.value ;  
 }  
 public void setKey(K key){  
  this.key = key ;  
 }  
 public void setValue(V value){  
  this.value = value ;  
 }  
};  
public class GenericsDemo09{  
 public static void main(String args[]){  
  Notepad< String,Integer> t = null ;  // 定义两个泛型类型的对象  
  t = new Notepad< String,Integer>() ;  // 里面的key为String，value为Integer  
  t.setKey("汤姆") ;  // 设置第一个内容  
  t.setValue(20) ;   // 设置第二个内容  
  System.out.print("姓名；" + t.getKey()) ;  // 取得信息  
  System.out.print("，年龄；" + t.getValue()) ;  // 取得信息  

 }  
};  
```

### 通配符

```java
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo14{  
 public static void main(String args[]){  
  Info< String> i = new Info< String>() ;  // 使用String为泛型类型  
  i.setVar("it") ;       // 设置内容  
  fun(i) ;  
 }  
 public static void fun(Info< ?> temp){  // 可以接收任意的泛型对象  
  System.out.println("内容：" + temp) ;  
 }  
};  
```

### 受限泛型

```
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo17{  
 public static void main(String args[]){  
  Info< Integer> i1 = new Info< Integer>() ;  // 声明Integer的泛型对象  
  Info< Float> i2 = new Info< Float>() ;   // 声明Float的泛型对象  
  i1.setVar(30) ;         // 设置整数，自动装箱  
  i2.setVar(30.1f) ;        // 设置小数，自动装箱  
  fun(i1) ;  
  fun(i2) ;  
 }  
 public static void fun(Info< ? extends Number> temp){ // 只能接收Number及其Number的子类  
  System.out.print(temp + "、") ;  
 }  
};  
```

------

```
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo21{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  // 声明String的泛型对象  
  Info< Object> i2 = new Info< Object>() ;  // 声明Object的泛型对象  
  i1.setVar("hello") ;  
  i2.setVar(new Object()) ;  
  fun(i1) ;  
  fun(i2) ;  
 }  
 public static void fun(Info< ? super String> temp){ // 只能接收String或Object类型的泛型  
  System.out.print(temp + "、") ;  
 }  
};  
```

### Java泛型无法向上转型

```
class Info< T>{  
 private T var ;  // 定义泛型变量  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public String toString(){ // 直接打印  
  return this.var.toString() ;  
 }  
};  
public class GenericsDemo23{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  // 泛型类型为String  
  Info< Object> i2 = null ;  
  i2 = i1 ;        //这句会出错 incompatible types  
 }  
};  
```

### Java泛型接口

```
interface Info< T>{  // 在接口上定义泛型  
 public T getVar() ; // 定义抽象方法，抽象方法的返回值就是泛型类型  
}  
class InfoImpl< T> implements Info< T>{ // 定义泛型接口的子类  
 private T var ;    // 定义属性  
 public InfoImpl(T var){  // 通过构造方法设置属性内容  
  this.setVar(var) ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
};  
public class GenericsDemo24{  
 public static void main(String arsg[]){  
  Info< String> i = null;  // 声明接口对象  
  i = new InfoImpl< String>("汤姆") ; // 通过子类实例化对象  
  System.out.println("内容：" + i.getVar()) ;  
 }  
};  
```

------

```
interface Info< T>{  // 在接口上定义泛型  
 public T getVar() ; // 定义抽象方法，抽象方法的返回值就是泛型类型  
}  
class InfoImpl implements Info< String>{ // 定义泛型接口的子类  
 private String var ;    // 定义属性  
 public InfoImpl(String var){  // 通过构造方法设置属性内容  
  this.setVar(var) ;   
 }  
 public void setVar(String var){  
  this.var = var ;  
 }  
 public String getVar(){  
  return this.var ;  
 }  
};  
public class GenericsDemo25{  
 public static void main(String arsg[]){  
  Info i = null;  // 声明接口对象  
  i = new InfoImpl("汤姆") ; // 通过子类实例化对象  
  System.out.println("内容：" + i.getVar()) ;  
 }  
};  
```

### Java泛型方法

```
class Demo{  
 public < T> T fun(T t){   // 可以接收任意类型的数据  
  return t ;     // 直接把参数返回  
 }  
};  
public class GenericsDemo26{  
 public static void main(String args[]){  
  Demo d = new Demo() ; // 实例化Demo对象  
  String str = d.fun("汤姆") ; // 传递字符串  
  int i = d.fun(30) ;  // 传递数字，自动装箱  
  System.out.println(str) ; // 输出内容  
  System.out.println(i) ;  // 输出内容  
 }  
};  
```

### 通过泛型方法返回泛型类型实例

```
class Info< T extends Number>{ // 指定上限，只能是数字类型  
 private T var ;  // 此类型由外部决定  
 public T getVar(){  
  return this.var ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public String toString(){  // 覆写Object类中的toString()方法  
  return this.var.toString() ;   
 }  
};  
public class GenericsDemo27{  
 public static void main(String args[]){  
  Info< Integer> i = fun(30) ;  
  System.out.println(i.getVar()) ;  
 }  
 public static < T extends Number> Info< T> fun(T param){//方法中传入或返回的泛型类型由调用方法时所设置的参数类型决定  
  Info< T> temp = new Info< T>() ;  // 根据传入的数据类型实例化Info  
  temp.setVar(param) ;  // 将传递的内容设置到Info对象的var属性之中  
  return temp ; // 返回实例化对象  
 }  
};  
```

### 使用泛型统一传入的参数类型

```
class Info< T>{ // 指定上限，只能是数字类型  
 private T var ;  // 此类型由外部决定  
 public T getVar(){  
  return this.var ;   
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public String toString(){  // 覆写Object类中的toString()方法  
  return this.var.toString() ;   
 }  
};  
public class GenericsDemo28{  
 public static void main(String args[]){  
  Info< String> i1 = new Info< String>() ;  
  Info< String> i2 = new Info< String>() ;  
  i1.setVar("HELLO") ;  // 设置内容  
  i2.setVar("汤姆") ;  // 设置内容  
  add(i1,i2) ;  
 }  
 public static < T> void add(Info< T> i1,Info< T> i2){  
  System.out.println(i1.getVar() + " " + i2.getVar()) ;  
 }  
};  
```

### Java泛型数组

```
public class GenericsDemo30{  
 public static void main(String args[]){  
  Integer i[] = fun1(1,2,3,4,5,6) ; // 返回泛型数组  
  fun2(i) ;  
 }  
 public static < T> T[] fun1(T...arg){ // 接收可变参数  
  return arg ;   // 返回泛型数组  
 }  
 public static < T> void fun2(T param[]){ // 输出  
  System.out.print("接收泛型数组：") ;  
  for(T t:param){  
   System.out.print(t + "、") ;  
  }  
 }  
};  
```

### Java泛型的嵌套设置

```
class Info< T,V>{  // 接收两个泛型类型  
 private T var ;  
 private V value ;  
 public Info(T var,V value){  
  this.setVar(var) ;  
  this.setValue(value) ;  
 }  
 public void setVar(T var){  
  this.var = var ;  
 }  
 public void setValue(V value){  
  this.value = value ;  
 }  
 public T getVar(){  
  return this.var ;  
 }  
 public V getValue(){  
  return this.value ;  
 }  
};  
class Demo< S>{  
 private S info ;  
 public Demo(S info){  
  this.setInfo(info) ;  
 }  
 public void setInfo(S info){  
  this.info = info ;  
 }  
 public S getInfo(){  
  return this.info ;  
 }  
};  
public class GenericsDemo31{  
 public static void main(String args[]){  
  Demo< Info< String,Integer>> d = null ;  // 将Info作为Demo的泛型类型  
  Info< String,Integer> i = null ; // Info指定两个泛型类型  
  i = new Info< String,Integer>("汤姆",30) ;  // 实例化Info对象  
  d = new Demo< Info< String,Integer>>(i) ; // 在Demo类中设置Info类的对象  
  System.out.println("内容一：" + d.getInfo().getVar()) ;  
  System.out.println("内容二：" + d.getInfo().getValue()) ;  
 }  
};  
```