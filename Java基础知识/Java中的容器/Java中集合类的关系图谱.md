## Collection 和 Collections

首先要明确的是，Collection 和 Collections是两个不同的概念。Collection是一个接口，所有的集合类（除Map外）都要继承（实现）自该接口。它提供了对集合对象进行基本操作的通用接口方法。Collections是一个包装类，它包含有各种有关集合操作的静态多态方法。（Collections是一个工具类，不能实例化）

![CollectionVsCollections](https://ws2.sinaimg.cn/large/006tKfTcly1g0e21fmraqj30pa0czt91.jpg)

## Collection家族关系图

![java-collection-hierarchy](https://ws4.sinaimg.cn/large/006tKfTcly1g0e21kmbklj30s00glmxx.jpg)

## Map家族的关系图

![MapClassHierarchy-600x354](https://ws3.sinaimg.cn/large/006tKfTcly1g0e22045joj30go09uwer.jpg)

## 关系图谱

![collection-summary](https://ws2.sinaimg.cn/large/006tKfTcly1g0e225rt89j30h3051a9z.jpg)

## 代码示例

下面是一个简单的例子来说明一些集合类型:

```
List<String> a1 = new ArrayList<String>();
a1.add("Program");
a1.add("Creek");
a1.add("Java");
a1.add("Java");
System.out.println("ArrayList Elements");
System.out.print("\t" + a1 + "\n");

List<String> l1 = new LinkedList<String>();
l1.add("Program");
l1.add("Creek");
l1.add("Java");
l1.add("Java");
System.out.println("LinkedList Elements");
System.out.print("\t" + l1 + "\n");

Set<String> s1 = new HashSet<String>(); // or new TreeSet() will order the elements;
s1.add("Program");
s1.add("Creek");
s1.add("Java");
s1.add("Java");
s1.add("tutorial");
System.out.println("Set Elements");
System.out.print("\t" + s1 + "\n");

Map<String, String> m1 = new HashMap<String, String>(); // or new TreeMap() will order based on keys
m1.put("Windows", "2000");
m1.put("Windows", "XP");
m1.put("Language", "Java");
m1.put("Website", "programcreek.com");
System.out.println("Map Elements");
System.out.print("\t" + m1);
```

输出结果：

```
ArrayList Elements
    [Program, Creek, Java, Java]
LinkedList Elements
    [Program, Creek, Java, Java]
Set Elements
    [tutorial, Creek, Program, Java]
Map Elements
    {Windows=XP, Website=programcreek.com, Language=Java}
```