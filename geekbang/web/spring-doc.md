# 前言

[Spring官方文档。](https://docs.spring.io/spring-framework/docs/current/reference/html/)

# 核心技术

## 1. IoC容器

## 1.1 IoC容器和Beans的简介

IoC容器负责管理Beans，最核心的是[`BeanFactory`](https://docs.spring.io/spring-framework/docs/5.3.8/javadoc-api/org/springframework/beans/factory/BeanFactory.html) 接口和[`ApplicationContext`](https://docs.spring.io/spring-framework/docs/5.3.8/javadoc-api/org/springframework/context/ApplicationContext.html)接口。其中，后者是前者的子接口，增加了更多的功能特性。

beans是被Spring IoC容器实例化，装配和管理的对象。

## 1.2. 容器简介

![container magic](https://cdn.jsdelivr.net/gh/haojunsheng/ImageHost@master/img/20210628211106.png)

### 1.2.1 配置元数据

配置元数据告诉Spring如何实例化，管理Beans对象。

我们一般使用xml或者Java注解。

xml的话，一般是：`<bean/>`，Java注解的话是@Bean注解方法，@Configuration注解类。

