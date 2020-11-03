# 前言



# 1. 概述

如果第一次使用spring boot：

- **From scratch:** [Overview](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-introducing-spring-boot) | [Requirements](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-system-requirements) | [Installation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-installing-spring-boot)
- **Tutorial:** [Part 1](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application) | [Part 2](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-code)
- **Running your example:** [Part 1](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-run) | [Part 2](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application-executable-jar)

准备好使用的话：

- **Build systems:** [Maven](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-maven) | [Gradle](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-gradle) | [Ant](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-ant) | [Starters](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-starter)
- **Best practices:** [Code Structure](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-structuring-your-code) | [@Configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-configuration-classes) | [@EnableAutoConfiguration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-auto-configuration) | [Beans and Dependency Injection](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-spring-beans-and-dependency-injection)
- **Running your code:** [IDE](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-running-from-an-ide) | [Packaged](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-running-as-a-packaged-application) | [Maven](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-running-with-the-maven-plugin) | [Gradle](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-running-with-the-gradle-plugin)
- **Packaging your app:** [Production jars](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-packaging-for-production)
- **Spring Boot CLI:** [Using the CLI](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#cli)

spring boot的核心功能

- **Core Features:** [SpringApplication](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-application) | [External Configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config) | [Profiles](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-profiles) | [Logging](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-logging)
- **Web Applications:** [MVC](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-mvc) | [Embedded Containers](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-embedded-container)
- **Working with data:** [SQL](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-sql) | [NO-SQL](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-nosql)
- **Messaging:** [Overview](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-messaging) | [JMS](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-jms)
- **Testing:** [Overview](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing) | [Boot Applications](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications) | [Utils](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-test-utilities)
- **Extending:** [Auto-configuration](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-developing-auto-configuration) | [@Conditions](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-condition-annotations)

生产环境的部署：

- **Management endpoints:** [Overview](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints)
- **Connection options:** [HTTP](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-monitoring) | [JMX](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-jmx)
- **Monitoring:** [Metrics](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-metrics) | [Auditing](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-auditing) | [HTTP Tracing](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-http-tracing) | [Process](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-process-monitoring)

高级技巧：

- **Spring Boot Applications Deployment:** [Cloud Deployment](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#cloud-deployment) | [OS Service](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment-service)
- **Build tool plugins:** [Maven](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#build-tool-plugins-maven-plugin) | [Gradle](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#build-tool-plugins-gradle-plugin)
- **Appendix:** [Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#common-application-properties) | [Configuration Metadata](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#configuration-metadata) | [Auto-configuration Classes](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#auto-configuration-classes) | [Test Auto-configuration Annotations](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#test-auto-configuration) | [Executable Jars](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#executable-jar) | [Dependency Versions](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#dependency-versions)

# 2. 开始使用

回答了what，how，why的问题。

## 2.1 Spring Boot的介绍

Spring Boot帮助您创建可以运行的独立的、生产级的基于Spring的应用程序。且配置很少。我们既可以使用java -jar的方式来运行，来可以运行war包。

主要目标：

- 为所有Spring开发提供根本上更快且可广泛访问的入门体验。
- 开箱即用。
- 提供一系列大型项目通用的非功能性功能（例如嵌入式服务器，安全性，指标，运行状况检查和外部化配置）。
- 完全不需要代码生成，也不需要XML配置。

## 2.2 系统要求

| java   | 1.8-15 |
| ------ | ------ |
| Spring | 5.2.10 |
| Maven  | 3.3+   |
| Gradle | 6.3+   |

嵌入式的容器：

| Name         | Servlet Version |
| :----------- | :-------------- |
| Tomcat 9.0   | 4.0             |
| Jetty 9.4    | 3.1             |
| Undertow 2.0 | 4.0             |

## 2.3 安装Spring Boot

### 2.3.1 Java开发者的安装说明

可以把Spring Boot当做一个Java库来使用。把spring-boot-*.jar放到classpath中即可。**建议**：用Maven或者Gradle来管理Spring Boot。

### 2.3.2 CLI的安装说明

TODO

### 2.3.3 Spring Boot的升级

从1.x迁移到2.x，可以参考 [“migration guide” on the project wiki](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide)。

值得注意的是，升级之后，某些属性可能被重命名，我们可以使用Spring Boot提供的工具来帮助我们升级：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-properties-migrator</artifactId>
    <scope>runtime</scope>
</dependency>
```

## 2.4 第一个Spring Boot应用

1. pom文件

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>myproject</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.5.RELEASE</version>
    </parent>

    <description/>
    <developers>
        <developer/>
    </developers>
    <licenses>
        <license/>
    </licenses>
    <scm>
        <url/>
    </scm>
    <url/>

    <!-- Additional lines to be added here... -->

</project>
```

2. 添加依赖

```
mvn dependency:tree
```

3. 写代码

```java
@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }

}
```

可执行jar包，也称为fat jars，内部包含编译好的类的代码和所依赖的jar包。

```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

mvn package

![image-20201102113056405](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201102113103.png)

```
jar tvf reference2-4-1.0-SNAPSHOT.jar 
     0 Mon Nov 02 11:30:34 CST 2020 META-INF/
   443 Mon Nov 02 11:30:34 CST 2020 META-INF/MANIFEST.MF
     0 Fri Feb 01 00:00:00 CST 1980 org/
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/
  5871 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/ClassPathIndexFile.class
  6806 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/ExecutableArchiveLauncher.class
  3966 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/JarLauncher.class
  1483 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/LaunchedURLClassLoader$DefinePackageCallType.class
  1535 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/LaunchedURLClassLoader$UseFastConnectionExceptionsEnumeration.class
 11154 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/LaunchedURLClassLoader.class
  6042 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/Launcher.class
  1536 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/MainMethodRunner.class
   266 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/PropertiesLauncher$1.class
  1484 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/PropertiesLauncher$ArchiveEntryFilter.class
  8106 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/PropertiesLauncher$ClassPathArchives.class
  1953 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/PropertiesLauncher$PrefixMatchingArchiveFilter.class
 18923 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/PropertiesLauncher.class
  1750 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/WarLauncher.class
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/
   302 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/Archive$Entry.class
   511 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/Archive$EntryFilter.class
  4745 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/Archive.class
  6093 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive$AbstractIterator.class
  2180 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive$ArchiveIterator.class
  1857 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive$EntryIterator.class
  1269 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive$FileEntry.class
  2443 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive$SimpleJarFileArchive.class
  5262 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/ExplodedArchive.class
  2884 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/JarFileArchive$AbstractIterator.class
  1981 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/JarFileArchive$EntryIterator.class
  1081 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/JarFileArchive$JarFileEntry.class
  2528 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/JarFileArchive$NestedArchiveIterator.class
  7485 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/archive/JarFileArchive.class
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/
   485 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/RandomAccessData.class
   282 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/RandomAccessDataFile$1.class
  2680 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/RandomAccessDataFile$DataInputStream.class
  3259 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/RandomAccessDataFile$FileAccess.class
  4015 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/data/RandomAccessDataFile.class
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/
  4976 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/AsciiBytes.class
   616 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/Bytes.class
   295 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryEndRecord$1.class
  3401 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryEndRecord$Zip64End.class
  2004 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryEndRecord$Zip64Locator.class
  4682 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryEndRecord.class
  6223 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryFileHeader.class
  4620 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryParser.class
   540 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/CentralDirectoryVisitor.class
   345 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/FileHeader.class
 11457 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/Handler.class
  3697 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarEntry.class
   299 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarEntryFilter.class
  2296 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFile$1.class
  1299 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFile$JarEntryEnumeration.class
  1374 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFile$JarFileType.class
 16948 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFile.class
  1593 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFileEntries$1.class
  2258 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFileEntries$EntryIterator.class
 14857 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarFileEntries.class
   702 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarURLConnection$1.class
  4302 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarURLConnection$JarEntryName.class
  9941 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/JarURLConnection.class
  3559 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/StringSequence.class
  1813 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jar/ZipInflaterInputStream.class
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jarmode/
   293 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jarmode/JarMode.class
  2201 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jarmode/JarModeLauncher.class
  1292 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/jarmode/TestJarMode.class
     0 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/util/
  5174 Fri Feb 01 00:00:00 CST 1980 org/springframework/boot/loader/util/SystemPropertyUtils.class
     0 Mon Nov 02 11:30:34 CST 2020 META-INF/maven/
     0 Mon Nov 02 11:30:34 CST 2020 META-INF/maven/com.hjs.springboot/
     0 Mon Nov 02 11:30:34 CST 2020 META-INF/maven/com.hjs.springboot/reference2-4/
     0 Mon Nov 02 11:30:34 CST 2020 BOOT-INF/
     0 Mon Nov 02 11:30:34 CST 2020 BOOT-INF/classes/
   938 Mon Nov 02 11:25:56 CST 2020 BOOT-INF/classes/Example.class
   952 Mon Nov 02 11:29:16 CST 2020 META-INF/maven/com.hjs.springboot/reference2-4/pom.xml
    72 Mon Nov 02 10:41:56 CST 2020 META-INF/maven/com.hjs.springboot/reference2-4/pom.properties
     0 Mon Nov 02 11:30:34 CST 2020 BOOT-INF/lib/
  4796 Thu Jun 11 22:50:44 CST 2020 BOOT-INF/lib/spring-boot-starter-web-2.3.1.RELEASE.jar
  4761 Thu Jun 11 22:50:44 CST 2020 BOOT-INF/lib/spring-boot-starter-2.3.1.RELEASE.jar
1118833 Thu Jun 11 22:51:12 CST 2020 BOOT-INF/lib/spring-boot-2.3.1.RELEASE.jar
1467500 Thu Jun 11 22:50:38 CST 2020 BOOT-INF/lib/spring-boot-autoconfigure-2.3.1.RELEASE.jar
  4752 Thu Jun 11 22:50:44 CST 2020 BOOT-INF/lib/spring-boot-starter-logging-2.3.1.RELEASE.jar
290339 Fri Mar 31 20:20:12 CST 2017 BOOT-INF/lib/logback-classic-1.2.3.jar
471901 Fri Mar 31 20:19:58 CST 2017 BOOT-INF/lib/logback-core-1.2.3.jar
 41472 Mon Dec 16 22:03:32 CST 2019 BOOT-INF/lib/slf4j-api-1.7.30.jar
 17461 Sun May 10 12:10:04 CST 2020 BOOT-INF/lib/log4j-to-slf4j-2.13.3.jar
292301 Sun May 10 12:07:56 CST 2020 BOOT-INF/lib/log4j-api-2.13.3.jar
  4592 Mon Dec 16 22:00:18 CST 2019 BOOT-INF/lib/jul-to-slf4j-1.7.30.jar
 25058 Fri Aug 02 11:08:52 CST 2019 BOOT-INF/lib/jakarta.annotation-api-1.3.5.jar
1441820 Tue Jun 09 06:43:00 CST 2020 BOOT-INF/lib/spring-core-5.2.7.RELEASE.jar
 23961 Tue Jun 09 06:42:50 CST 2020 BOOT-INF/lib/spring-jcl-5.2.7.RELEASE.jar
309001 Fri Feb 28 09:07:14 CST 2020 BOOT-INF/lib/snakeyaml-1.26.jar
  4740 Thu Jun 11 22:50:44 CST 2020 BOOT-INF/lib/spring-boot-starter-json-2.3.1.RELEASE.jar
1418028 Sun Apr 26 00:15:34 CST 2020 BOOT-INF/lib/jackson-databind-2.11.0.jar
 68175 Sat Apr 25 23:37:48 CST 2020 BOOT-INF/lib/jackson-annotations-2.11.0.jar
351529 Sat Apr 25 23:57:36 CST 2020 BOOT-INF/lib/jackson-core-2.11.0.jar
 34399 Sun Apr 26 01:50:50 CST 2020 BOOT-INF/lib/jackson-datatype-jdk8-2.11.0.jar
111072 Sun Apr 26 01:50:28 CST 2020 BOOT-INF/lib/jackson-datatype-jsr310-2.11.0.jar
  9330 Sun Apr 26 01:50:40 CST 2020 BOOT-INF/lib/jackson-module-parameter-names-2.11.0.jar
  4789 Thu Jun 11 22:50:44 CST 2020 BOOT-INF/lib/spring-boot-starter-tomcat-2.3.1.RELEASE.jar
3373581 Wed Jun 03 18:03:54 CST 2020 BOOT-INF/lib/tomcat-embed-core-9.0.36.jar
237826 Mon Aug 26 10:58:30 CST 2019 BOOT-INF/lib/jakarta.el-3.0.3.jar
268491 Wed Jun 03 18:03:54 CST 2020 BOOT-INF/lib/tomcat-embed-websocket-9.0.36.jar
1440651 Tue Jun 09 06:43:38 CST 2020 BOOT-INF/lib/spring-web-5.2.7.RELEASE.jar
688811 Tue Jun 09 06:43:06 CST 2020 BOOT-INF/lib/spring-beans-5.2.7.RELEASE.jar
956463 Tue Jun 09 06:43:42 CST 2020 BOOT-INF/lib/spring-webmvc-5.2.7.RELEASE.jar
372705 Tue Jun 09 06:43:12 CST 2020 BOOT-INF/lib/spring-aop-5.2.7.RELEASE.jar
1227929 Tue Jun 09 06:43:30 CST 2020 BOOT-INF/lib/spring-context-5.2.7.RELEASE.jar
282183 Tue Jun 09 06:43:00 CST 2020 BOOT-INF/lib/spring-expression-5.2.7.RELEASE.jar
  1135 Mon Nov 02 11:30:34 CST 2020 BOOT-INF/classpath.idx
```

我们可以看到：

![image-20201102113333984](https://raw.githubusercontent.com/haojunsheng/ImageHost/master/img/20201102113334.png)

```shell
➜  target git:(master) ✗ java -jar reference2-4-1.0-SNAPSHOT.jar 
```

# 3. Spring Boot的使用

## 3.1 构建系统

maven，gradle，ant。

### 3.1.5 Starters

| Name                                          | Description                                                  |
| :-------------------------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter`                         | Core starter, including auto-configuration support, logging and YAML |
| `spring-boot-starter-activemq`                | Starter for JMS messaging using Apache ActiveMQ              |
| `spring-boot-starter-amqp`                    | Starter for using Spring AMQP and Rabbit MQ                  |
| `spring-boot-starter-aop`                     | Starter for aspect-oriented programming with Spring AOP and AspectJ |
| `spring-boot-starter-artemis`                 | Starter for JMS messaging using Apache Artemis               |
| `spring-boot-starter-batch`                   | Starter for using Spring Batch                               |
| `spring-boot-starter-cache`                   | Starter for using Spring Framework’s caching support         |
| `spring-boot-starter-data-cassandra`          | Starter for using Cassandra distributed database and Spring Data Cassandra |
| `spring-boot-starter-data-cassandra-reactive` | Starter for using Cassandra distributed database and Spring Data Cassandra Reactive |
| `spring-boot-starter-data-couchbase`          | Starter for using Couchbase document-oriented database and Spring Data Couchbase |
| `spring-boot-starter-data-couchbase-reactive` | Starter for using Couchbase document-oriented database and Spring Data Couchbase Reactive |
| `spring-boot-starter-data-elasticsearch`      | Starter for using Elasticsearch search and analytics engine and Spring Data Elasticsearch |
| `spring-boot-starter-data-jdbc`               | Starter for using Spring Data JDBC                           |
| `spring-boot-starter-data-jpa`                | Starter for using Spring Data JPA with Hibernate             |
| `spring-boot-starter-data-ldap`               | Starter for using Spring Data LDAP                           |
| `spring-boot-starter-data-mongodb`            | Starter for using MongoDB document-oriented database and Spring Data MongoDB |
| `spring-boot-starter-data-mongodb-reactive`   | Starter for using MongoDB document-oriented database and Spring Data MongoDB Reactive |
| `spring-boot-starter-data-neo4j`              | Starter for using Neo4j graph database and Spring Data Neo4j |
| `spring-boot-starter-data-r2dbc`              | Starter for using Spring Data R2DBC                          |
| `spring-boot-starter-data-redis`              | Starter for using Redis key-value data store with Spring Data Redis and the Lettuce client |
| `spring-boot-starter-data-redis-reactive`     | Starter for using Redis key-value data store with Spring Data Redis reactive and the Lettuce client |
| `spring-boot-starter-data-rest`               | Starter for exposing Spring Data repositories over REST using Spring Data REST |
| `spring-boot-starter-data-solr`               | Starter for using the Apache Solr search platform with Spring Data Solr |
| `spring-boot-starter-freemarker`              | Starter for building MVC web applications using FreeMarker views |
| `spring-boot-starter-groovy-templates`        | Starter for building MVC web applications using Groovy Templates views |
| `spring-boot-starter-hateoas`                 | Starter for building hypermedia-based RESTful web application with Spring MVC and Spring HATEOAS |
| `spring-boot-starter-integration`             | Starter for using Spring Integration                         |
| `spring-boot-starter-jdbc`                    | Starter for using JDBC with the HikariCP connection pool     |
| `spring-boot-starter-jersey`                  | Starter for building RESTful web applications using JAX-RS and Jersey. An alternative to [`spring-boot-starter-web`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-web) |
| `spring-boot-starter-jooq`                    | Starter for using jOOQ to access SQL databases. An alternative to [`spring-boot-starter-data-jpa`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-data-jpa) or [`spring-boot-starter-jdbc`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-jdbc) |
| `spring-boot-starter-json`                    | Starter for reading and writing json                         |
| `spring-boot-starter-jta-atomikos`            | Starter for JTA transactions using Atomikos                  |
| `spring-boot-starter-jta-bitronix`            | Starter for JTA transactions using Bitronix. Deprecated since 2.3.0 |
| `spring-boot-starter-mail`                    | Starter for using Java Mail and Spring Framework’s email sending support |
| `spring-boot-starter-mustache`                | Starter for building web applications using Mustache views   |
| `spring-boot-starter-oauth2-client`           | Starter for using Spring Security’s OAuth2/OpenID Connect client features |
| `spring-boot-starter-oauth2-resource-server`  | Starter for using Spring Security’s OAuth2 resource server features |
| `spring-boot-starter-quartz`                  | Starter for using the Quartz scheduler                       |
| `spring-boot-starter-rsocket`                 | Starter for building RSocket clients and servers             |
| `spring-boot-starter-security`                | Starter for using Spring Security                            |
| `spring-boot-starter-test`                    | Starter for testing Spring Boot applications with libraries including JUnit, Hamcrest and Mockito |
| `spring-boot-starter-thymeleaf`               | Starter for building MVC web applications using Thymeleaf views |
| `spring-boot-starter-validation`              | Starter for using Java Bean Validation with Hibernate Validator |
| `spring-boot-starter-web`                     | Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container |
| `spring-boot-starter-web-services`            | Starter for using Spring Web Services                        |
| `spring-boot-starter-webflux`                 | Starter for building WebFlux applications using Spring Framework’s Reactive Web support |
| `spring-boot-starter-websocket`               | Starter for building WebSocket applications using Spring Framework’s WebSocket support |

In addition to the application starters, the following starters can be used to add *[production ready](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)* features:

| Name                           | Description                                                  |
| :----------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter-actuator` | Starter for using Spring Boot’s Actuator which provides production ready features to help you monitor and manage your application |

Finally, Spring Boot also includes the following starters that can be used if you want to exclude or swap specific technical facets:

| Name                                | Description                                                  |
| :---------------------------------- | :----------------------------------------------------------- |
| `spring-boot-starter-jetty`         | Starter for using Jetty as the embedded servlet container. An alternative to [`spring-boot-starter-tomcat`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-tomcat) |
| `spring-boot-starter-log4j2`        | Starter for using Log4j2 for logging. An alternative to [`spring-boot-starter-logging`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-logging) |
| `spring-boot-starter-logging`       | Starter for logging using Logback. Default logging starter   |
| `spring-boot-starter-reactor-netty` | Starter for using Reactor Netty as the embedded reactive HTTP server. |
| `spring-boot-starter-tomcat`        | Starter for using Tomcat as the embedded servlet container. Default servlet container starter used by [`spring-boot-starter-web`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-web) |
| `spring-boot-starter-undertow`      | Starter for using Undertow as the embedded servlet container. An alternative to [`spring-boot-starter-tomcat`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#spring-boot-starter-tomcat) |

## 3.2 结构化代码

虽然Spring Boot对代码的布局没有任何要求，但是下面的是最佳实践。

### 3.2.1 默认的package

不建议使用，@ComponentScan`, `@ConfigurationPropertiesScan`, `@EntityScan`, or `@SpringBootApplication这些注解可能会出问题。

### 3.2.2 Main Application的位置

main application class最好放到根目录。

```
com
 +- example
     +- myapplication
         +- Application.java
         |
         +- customer
         |   +- Customer.java
         |   +- CustomerController.java
         |   +- CustomerService.java
         |   +- CustomerRepository.java
         |
         +- order
             +- Order.java
             +- OrderController.java
             +- OrderService.java
             +- OrderRepository.java
```

```
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

## 3.3 配置类

Spring Boot尽管支持xml的配置，但是最好不要使用。通常是把配置类用@Configuration来标识。

### 3.3.1 导入其他的配置类

类中不必使用@Configuration注解，可以使用@Import和@ComponentScan注解。

### 3.3.2 导入xml配置

使用@ImportResource注解。

## 3.4 自动配置

使用@EnableAutoConfiguration或者@SpringBootApplication注解。

排除某些自动配置：

```
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.jdbc.*;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MyApplication {
}
```

## 3.5 Spring Beans and Dependency Injection

我们经常使用@ComponentScan来发现bean，使用@Autowired来注入bean。



# 4. Spring Boot的功能





# 参考

[spring boot官方文档](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-documentation)