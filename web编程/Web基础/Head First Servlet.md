[TOC]

# 前言

# 1. 为什么使用Servlet和JSP

用户请求过程：

![image-20190215161701441](https://ws4.sinaimg.cn/large/006tKfTcly1g076imrqo3j30tk0ziqd4.jpg)

![image-20190215162843233](https://ws1.sinaimg.cn/large/006tKfTcly1g076uthe5aj30sc0x47e7.jpg)

Web服务器（apach）只能返回静态界面，不能处理动态内容，不能在服务器上保存数据。

![image-20190215171056479](https://ws2.sinaimg.cn/large/006tKfTcly1g0782qgn0cj30t610wduj.jpg)

# 2. Web应用体系结构

## 2.1 综述

![image-20190215171519531](https://ws3.sinaimg.cn/large/006tKfTcly1g0787acsmzj311q0sqamw.jpg)

## 2.2 容器

### 2.2.1 什么是容器

管理Servlet对象的生命周期。Servlet没有main方法。

### 2.2.2 容器能提供什么

![image-20190215172436929](https://ws3.sinaimg.cn/large/006tKfTcly1g078gyttkzj30tm0z07hy.jpg)

![image-20190215172758817](https://ws4.sinaimg.cn/large/006tKfTcly1g078kh4mw4j30ta0xs168.jpg)

### 2.2.3 部署文件的作用

![image-20190215173156941](https://ws3.sinaimg.cn/large/006tKfTcly1g078oln4r5j30ro0wwdrn.jpg)

![image-20190215180000437](https://ws4.sinaimg.cn/large/006tKfTcly1g079hsrn06j312u0mcwnh.jpg)

# 3. MVC实战

![image-20190215182139938](/Users/anapodoton/Library/Application Support/typora-user-images/image-20190215182139938.png)

![image-20190216103940347](https://ws3.sinaimg.cn/large/006tKfTcly1g082dzmukuj30t20vgh0f.jpg)

# 4. Servlet的请求和响应

## 4.1 Servlet技术模型

![image-20190216104739287](https://ws4.sinaimg.cn/large/006tKfTcly1g082m8z5nsj30r612ik9b.jpg)

![image-20190216110756235](https://ws1.sinaimg.cn/large/006tKfTcly1g0837dnae1j30s80x4dty.jpg)

![image-20190216111312951](https://ws1.sinaimg.cn/large/006tKfTcly1g083cuj2xwj30r00xswvm.jpg)

![image-20190216111428400](https://ws1.sinaimg.cn/large/006tKfTcly1g083e5bg1kj30to0wknct.jpg)

![image-20190216113935377](https://ws4.sinaimg.cn/large/006tKfTcly1g0844afc8kj30pg0niwrq.jpg)

## 4.2 Get和Post的区别

![image-20190216114309591](https://ws3.sinaimg.cn/large/006tKfTcly1g0847z4h6yj30rk0w818d.jpg)

数据大小，安全问题，还有书签问题（是否允许用户建立书签，get可以，post不可以）。

![image-20190216120318241](https://ws1.sinaimg.cn/large/006tKfTcly1g084t0qx25j30v90u0x3w.jpg)

![image-20190216132646154](https://ws4.sinaimg.cn/large/006tKfTcly1g0877u9zdqj30u00v01kx.jpg)



# 5. 属性和监听

![image-20190216135720405](https://ws2.sinaimg.cn/large/006tKfTcly1g0883maeaqj30j60x6k3k.jpg)





# 6. 会话状态：会话管理



Http无状态。

HttpSession

维持着一个唯一的会话ID。

由于Http是无状态的，HttpSession负责维护客户的状态。

服务器为每个客户生成一个ID和HttpSession，用户请求的时候携带此ID，服务器就可以把该用户与HTTPSession保存的用户状态相关联。

**客户和容器如何交换会话ID信息**

容器会生成会话ID，创建新的Cookie对象，把会话ID放入cookie中，把cookie设置为响应的一部分等工作由容器负责完成。

Cookie



**在响应中发送一个会话Cookie：**

HttpSession httpSession=request.getSession();

注意，这个方法不是创建一个会话。容器会为我们创建新的HttpSession对象，生成唯一的会话ID，建立新的Cookie对象，ID和cookie对象进行关联，在响应中设置Cookie。

**从请求中得到会话ID：**

HttpSession httpSession=request.getSession();



![image-20190216192011612](https://ws4.sinaimg.cn/large/006tKfTcly1g08hfkttqoj311a0u0k66.jpg)



![image-20190216193822478](https://ws1.sinaimg.cn/large/006tKfTcly1g08hyhdad9j30tg0x4tha.jpg)



# 7. JSP

![image-20190217141857512](https://ws2.sinaimg.cn/large/006tKfTcly1g09ecesl18j30oc0yq48i.jpg)



![image-20190217141946762](https://ws4.sinaimg.cn/large/006tKfTcly1g09ed97907j310w09gtcj.jpg)













# 11. 部署Web应用

![image-20190216205604471](https://ws3.sinaimg.cn/large/006tKfTcly1g08k7e4uhrj30li0piteb.jpg)



![image-20190216205939247](https://ws3.sinaimg.cn/large/006tKfTcly1g08kb17060j30qm0wuwpt.jpg)



## 11.1 War文件

Web归档（WebARchive），是Web应用结构的一个快照。

## 11.2 使静态内容和JSP可以直接访问

直接访问：在浏览器直接输入资源的路径进行访问。

部署的时候，可以进行选择，把资源放入WEB-INF下就能避免直接访问。打包为WAR文件时，可以把不允许访问的文件放在META-INF目录下。

## 11.3 Servlet的映射



# 12. Web应用安全

![image-20190217093014845](https://ws2.sinaimg.cn/large/006tKfTcly1g096004cxpj30p00mmgq1.jpg)



# 13. 过滤器和包装器

拦截用户的请求，控制响应。

![image-20190217094114900](https://ws1.sinaimg.cn/large/006tKfTcly1g096bgdk8tj30qu0sawne.jpg)

![image-20190217094648740](https://ws2.sinaimg.cn/large/006tKfTcly1g096ha1iptj310m0u0k5z.jpg)



## 13.1 过滤器的生命周期

![image-20190217101802102](https://ws1.sinaimg.cn/large/006tKfTcly1g097dz77srj30fa0ty0z2.jpg)















