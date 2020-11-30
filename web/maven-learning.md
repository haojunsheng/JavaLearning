# 1 Maven介绍

------

## 1.1 Maven是什么

Maven是一个项目管理工具，它包含了一个项目对象模型 (Project Object Model)，一组标准集合，一个项目生命周期(Project Lifecycle)，一个依赖管理系统(Dependency Management System)，和用来运行定义在生命周期阶段(phase)中插件(plugin)目标(goal)的逻辑。 当你使用Maven的时候，你用一个明确定义的项目对象模型来描述你的项目，然后 Maven 可以应用横切的逻辑，这些逻辑来自一组共享的(或者自定义的)插件。Maven支持定义项目结构、项目依赖，并使用统一的方式进行自动化构建。

## 1.2 Maven的构建

### 1.2.1 Maven生命周期

Maven 拥有三套独立的生命周期，它们分别是 clean、default 和 site。clean 生命周期的目的是清理项目；default 生命周期的目的是构建项目；site 生命周期的目的是建立项目站点。其中clean和default生命周期使用较多，site生命周期平时不常使用。

每个生命周期又包含了多个阶段。这些阶段在执行的时候是有固定顺序的。后面的阶段一定要等前面的阶段执行完成后才能被执行。

#### clean 生命周期

clean 生命周期的目的是清理项目，它包括以下三个阶段。

- pre-clean：执行清理前需要完成的工作。
- clean：清理上一次构建过程中生成的文件，例如删除target目录及其包含的所有文件
- post-clean：执行清理后需要完成的工作。

**注意：mvn clean** 中的clean就是上面的clean，在一个生命周期中，运行某个阶段的时候，它之前的所有阶段都会被运行，也就是说，**mvn clean** 等同于 **mvn pre-clean clean** ，如果我们运行 **mvn post-clean** ，那么 pre-clean，clean 都会被运行。这是Maven很重要的一个规则，可以大大简化命令行的输入。

#### default 生命周期

default 生命周期定义了构建项目时所需要的执行步骤，它是所有生命周期中最核心部分，绝大部分工作都发生在这个生命周期中。默认生命周期包含了以下的阶段（这里只列出常用的）

- process-resources：复制并处理资源文件，至目标目录，准备打包。

- compile：编译项目的源代码。

- process-test-resources：复制并处理资源文件，至目标测试目录。

- test-compile：编译测试源代码。

- test：使用合适的单元测试框架运行测试。这些测试代码不会被打包或部署。可以通过执行-Dmaven.test.skip=true在打包时候跳过test阶段

- package：接受编译好的代码，打包成可发布的格式，如 Jar、War等

- install：将包安装至本地仓库，以让其它项目依赖。例如把Pom中定义的依赖 Jar包安装到 ~/.m2/repository 目录，下次使用时候如果发现本地repository已经装有某个版本的Jar包，就不需要从远程仓库拉取了。所以如果出现由于缓存原因导致不能拉取某个Version的最新Jar包，可以考虑删除 ~/.m2/repository 对应的Jar包

- deploy：将最终的包复制到远程的仓库，以让其它开发人员与项目共享。目前我们都是通过Plus发布，很少会用到这个命令。

  

上述clean生命周期和default生命周期是两个独立的阶段，我们很容易会认为清理生命周期是在默认生命周期之前的必经阶段。也就是说，会被认为如果执行默认生命周期中的阶段的话，清理生命周期中的所有阶段都会被执行。而这是不对的，不同一套的生命周期之间是相互独立的，其中的阶段并不会相互影响。如果需要在打包之前先清理之前打包的内容，需要单独运行清理生命周期。当然了，一般也会建议这么做，否则可能出现一些打包的奇怪问题，比如有一些文件并没有被更新的问题。

### 1.2.2 **Maven插件和目标**

插件：maven的核心仅仅定定义了抽像的生命周期，具体的任务都是交给插件完在的，插件以独立的构件形式存在，对于插件本身为了能够复用代码，往往能够完成多个任务，例如：maven-dependency-plugin能够基于项目依赖做很多事情，它能够分析项目依赖，帮助找出无用的依赖，列出项目的依赖树，和依赖之间的冲突。因此这些功能聚集在一块叫插件。每个功能就叫目标

目标：表示一个特定的、对构建和管理工程有帮助的任务。可以绑定 0 个或多个构建阶段。没有绑定任何构建阶段的目标可以在构建生命周期之外被直接调用执行，执行的顺序取决于调用目标和构建阶段的顺序。

```
先执行clean生命周期，然后执行dependency插件的copy-dependencies目标，最后执行deault生命周期的package
mvn clean dependency:copy-dependencies package
```

### 1.2.3 Maven的执行方式

Maven对构建的过程进行了抽象和定义，这个过程被称为构建的生命周期(lifecycle)。生命周期(lifecycle)由多个阶段(phase)组成,每个阶段(phase)会绑定一到多个goal。goal是maven里定义任务的最小单元，goal分为两类，一类是绑定phase的，就是执行到某个phase，那么这个goal就会触发，另外一类不绑定，就是单独任务。

​    

- 以阶段phase来构建，mvn clean package

  表明maven会执行到某个生命周期(lifecycle)的某个阶段(phase)这个phase以及它前面所有phase绑定的目标(goal)都会执行， 每个phase都会邦定maven默认的goal或者没有goal， 或者自定义的goal。
  也可以通过传入参数跳过(skip)某些phase，例如-Dmaven.test.skip=true

  

  pre-cleancleanprocess-resourcescompiletestpackagephasegoalresources:resourcescompiler:compileclean:cleansurefire:testjar:jarclean lifecycledefault lifecycle

  

- 以goal来构建，mvn clean:clean resources:resources compiler:compile surefire:test jar:jar和上面以phase来构建得到相同的结果

  这类就是没有绑定phase的goal，但是这类goal却通常会有个执行前提，就是project必须执行到某个phase，那么执行这个goal，其实也会触发maven执行到前提要求的phase。
  例如jetty:run是个非绑定phase的goal，它的前提是test-compile，这个前提是由plugin的代码逻辑制定的



## 1.3 Maven的依赖管理

Maven 核心特点之一是依赖管理。一旦我们开始处理多模块工程（包含数百个子模块或者子工程）的时候，模块间的依赖关系就变得非常复杂，管理也变得很困难。针对此种情形，Maven 提供了一种高度控制的方法：依赖管理(dependencyManagerment)，依赖(dependencies)。

### 1.3.1 依赖关系传递

一个复杂的项目将会包含很多依赖，也有可能包含依赖于其它构件的依赖。基于传递性依赖，你不必找出所有这些依赖然后把它们写在你的pom.xml里，你只需要加上你直接依赖的那些库，Maven会隐式的把这些库间接依赖的库也加入到你的项目中。Maven也会处理这些依赖中的冲突，同时能让你自定义默认行为，或者排除一些特定的传递性依赖。依赖关系的级别数没有限制，仅当发现循环依赖性时才会出现问题。

功能功能描述依赖调解深度最少的版本被使用。 如果两个依赖版本在依赖树里的深度是一样的时候，第一个被声明的依赖将会被使用。依赖管理直接的指定手动创建的某个版本被使用。例如当一个工程 C 在自己的以来管理模块包含工程 B，即 B 依赖于 A， 那么 A 即可指定在 B 被引用时所使用的版本。依赖范围包含在构建过程每个阶段的依赖。依赖排除任何可传递的依赖都可以通过 "exclusion" 元素被排除在外。举例说明，A 依赖 B， B 依赖 C，因此 A 可以标记 C 为 “被排除的”。依赖可选任何可传递的依赖可以被标记为可选的，通过使用 "optional" 元素。例如：A 依赖 B， B 依赖 C。因此，B 可以标记 C 为可选的， 这样 A 就可以不再使用 C。

### 1.3.2 依赖范围

范围描述compile默认取值。此依赖范围对 于编译、测试、运行三种classpath都有效provided该范围只对编译和测试的classpath有效，对运行的classpath无效。例如servlet-api，常用的web一般已经包含了上述jar包runtime该范围只对测试和运行的classpath有效，对编译的classpath无效，例如JDBC的驱动实现，项目主代码编译的时候只需要JDK提供的JDBC接口，只有在测试和运行的时候才需要实现上述接口的具体JDBC驱动。test该范围只对测试classpath有效，在编译主代码和项目运行时，都将无法使用该依赖，例如 Junitsystem该范围与provided依赖范围完全一致，但是系统依赖范围必须通过配置systemPath元素来显示指定依赖文件的路径，此类依赖不是由maven仓库解析的，而且往往与本机系统绑定，可能造成构件的不可移植import该范围不会对三种classpath产生影响，该依赖范围只能与dependencyManagement元素配合使用，其功能为将目标pom文件中dependencyManagement的配置导入合并到当前pom的dependencyManagement中

依赖范围传递

A->B(compile)   第一关系: a依赖b  compile

B->C(compile)   第二关系: b依赖c  compile

当在A中配置

| B->C A->B             A->C | compile    | provided | runtime  | test |
| -------------------------- | ---------- | -------- | -------- | ---- |
| compile                    | compile(*) | -        | runtime  | -    |
| provided                   | provided   | -        | provided | -    |
| runtime                    | runtime    | -        | runtime  | -    |
| test                       | test       | -        | test     | -    |

# 2 Maven常用插件介绍

------

Maven本质上是一个插件执行框架，所有工作都由插件完成。Maven包括两类插件：

- **Build plugins** ：用来在不同的phase中绑定不同的插件构建工程
- **Reporting plugins**：用来生成一些报表，例如Maven-javadoc-plugin，生成javadoc



常用Build plugins分为以下几种type：

- core plugin：default生命周期默认绑定插件
- packaging types：根据不同的packaging type生成不同的包
- 其他Tools：

| plugins                 | type                  | 作用                                                     | **life cycle phase**    |
| :---------------------- | :-------------------- | :------------------------------------------------------- | :---------------------- |
| maven-clean-plugin      | core                  | 清理上一次执行创建的目标文件                             | clean                   |
| maven-resources-plugin  | core                  | 处理源资源文件和测试资源文件                             | resources,testResources |
| maven-compiler-plugin   | core                  | 编译源文件和测试源文件                                   | compile,testCompile     |
| maven-surefire-plugin   | core                  | 执行测试文件                                             | test                    |
| maven-install-plugin    | core                  | 安装 jar，将创建生成的 jar 拷贝到 .m2/repository 下面    | install                 |
| maven-jar-plugin        | Packaging types/tools | 创建 jar                                                 |                         |
| maven-war-plugin        | Packaging types/tools | 创建 war                                                 |                         |
| maven-shade-plugin      | Packaging types/tools | 创建 shade                                               | package                 |
| maven-dependency-plugin | 其他Tools             | 将依赖jar包从本地或远程存储库复制和/或解压缩到指定位置。 | package                 |
| maven-assembly-plugin   | 其他Tools             | 支持定制化打包方式，例如 apache 项目的打包方式           | package                 |

 