“首先，一个java bean 其实就是一个普通的java 类， 但我们对这个类有些要求： 

1. 这个类需要是public 的， 然后需要有个无参数的构造函数
2. 这个类的属性应该是private 的， 通过setXXX()和getXXX()来访问
3.  这个类需要能支持“事件”， 例如addXXXXListener(XXXEvent e),  事件可以是Click事件，Keyboard事件等等， 当然咱们也支持自定义的事件。 
4. 我们得提供一个所谓的自省/反射机制， 这样能在运行时查看java bean 的各种信息
5. 这个类应该是可以序列化的， 即可以把bean的状态保存的硬盘上， 以便以后来恢复。 



JSP + Servlet+Java Bean

用java bean 来封装业务逻辑，保存数据到数据库， 像这样：

![image-20190301192257058](https://ws4.sinaimg.cn/large/006tKfTcly1g0nikejfzyj31360fogqb.jpg)











