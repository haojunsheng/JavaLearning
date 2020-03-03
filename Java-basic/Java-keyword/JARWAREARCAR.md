# 前言

## JAR (`Java Archive file`)

> **包含内容**：`class`、`properties`文件，**是文件封装的最小单元**；包含`Java类的普通库`、`资源（resources）`、`辅助文件（auxiliary files）`等
> **部署文件** ： application-client.xml
> **容器**： 应用服务器（application servers）
> **级别**：小



### WAR (`Web Archive file`)

> **包含内容**：`Servlet`、`JSP页面`、`JSP标记库`、`JAR库文件、HTML/XML文档和`其他公用资源文件，如`图片、音频文件`等
> **部署文件** ： web.xml
> **容器**： 小型服务程序容器（servlet containers）
> **级别**：中

### EAR（`Enterprise Archive file`）

> **包含内容**：除了包含`JAR`、`WAR`以外，还包括`EJB组件`
> **部署文件** ： application.xml
> **容器**： EJB容器（EJB containers）
> **级别**： 大

### car包(`webx特有的打包方式`)

> 传统的web工程就是将工程打包成一个war包部署到web服务器上就可以运行web服务。
> Webx工程是以car包为单位，一个工程可以打包为一个car包，多个car包可以打包成一个war包部署到 web服务器上。
> 这样做的好处不言而喻就是可以将一个大工程分解为多个小工程独立去开发部署。