[TOC]

# 前言

一、静态包含指令<%@include file=“fileurl”%>

1. 两个jsp页面的<%@page contentType=“text/html；charset=gbk”%>应该保持一致
2. 不能通过fileurl向被包含的jsp页面传递参数，因为此静态包含是发生在jsp页面转换为servlet的转换期间，此时的参数是服务器端设置的死的参数，完全没有经过客户端，这种参数是没有意义的，如<%@include file=“fileurl?user=admin”%>，而且此时会报错。
3. 包含的jsp页面与被包含的jsp页面共用一个request内置对象。
4. 比如说在客户端访问包含页面时地址栏后面直接加上参数后传递，这种形式的传参是客户端送来的，两个页面都能够访问此参数。我们可以通过这两个页面合成的servlet中可以看到有传递的参数成为servlet的成员变量。
5. 包含的jsp页面与被包含的jsp页面最好没有重复的html标签。否则会发生覆盖现象。

二、动态包含<jsp :include page=“a.jsp”/>与静态包含<%@include file=“fileurl”%>的区别

1. 动态包含用的元素是page，而且有两种形式。静态包含用的是file,只有一种形式。
2. 生成的文件不同，静态的包含是将两个jsp文件二合一，生成一个以包含页面命名的servlet和class文件，动态包含的两个jsp文件各自生成自己的servlet和class文件。
3. 传参方式一：<jsp:include page=“a.jsp?param=123”/>时被包含的jsp页面是可以访问该参数的。
4. 传参方式二：

<jsp:include page=“a.jsp”>

<jsp:param name=“” value=“”>

<jsp:param name=“” value=“”>

</ jsp:include >

\5. 在客户端访问包含页面时地址栏后面直接加上参数后传递，这种形式的传参是客户端送来的，但是这两个页面的request对象不是同一个，因为3中已经说了包含的页面可以向被包含的页面传递参数，所以被包含的request对象含的参数个数应该大于等于包含页面的参数个数的。所以它们各有各的request对象。而且被包含的jsp页面可以访问传到包含页面的参数。

\6. 动态包含只有在执行到它的时候才加载，所以它才叫动态包含。

另一种简答说法：

1. 动态INCLUDE用jsp:include动作实现<jsp:include page = included.jsp flush = "true">它总会检查所含文件中的变化，适用于动态页面并且可以带参数。
2. 静态INCLUDE用include伪码实现，定不会检查所含文件的变化，适用于包含静态页面<%@include file=included.html%> 

 