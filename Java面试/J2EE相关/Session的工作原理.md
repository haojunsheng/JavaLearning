# servlet中，如何定制`session`的过期时间？

如何安全的删除HttpSession对象。

HttpSession提供的API。

```java
long getCreationTime();
long getLastAccessedTime();
void setMaxInactiveInterval(int var1);

int getMaxInactiveInterval();
void invalidate();
```

会话的三种死法：

- 超时
  - 在DD中配置超时时间（单位是分钟）
  - 调用setMaxInactiveInterval（单位是秒）
- 调用invalidate方法
- 应用结束（崩溃或者取消部署）



**cookie的其他用处**

cookie的本质是本质是在客户和服务器之间交换的一小段数据（一个键值对）。

cookie的生命周期可以大于session，还可以保存其他信息（除了Session）。



# Servlet中的`session`工作原理 

（`禁用cookie如何使用session`）

由于Http是无状态的，HttpSession负责维护客户的状态。

服务器为每个客户生成一个ID和HttpSession，用户请求的时候携带此ID，服务器就可以把该用户与HTTPSession保存的用户状态相关联。



**当客户禁止使用cookie的时候如何使用session？**

使用URL重写。URL(需要使用response.encodeURL("test")进行编码才可以)+;jsessionid

![image-20190216181520618](https://ws3.sinaimg.cn/large/006tKfTcly1g08fk2qoplj318g0gegrq.jpg)

```java
response.encodeRedirectURL()
```

URL重写是自动的，但是我们必须首先调用response.encodeURL对URL进行编码，URL重写才会生效。

URL编码是由响应进行处理的，也就是response对象。

**注意：**

静态的界面无法进行URL重写，所以，如果依赖于会话，就必须使用动态生成的界面。

























