之前在我的知识星球的直面Java板块中，给粉丝们出了这样一道题：

**在Java中，如何获取不同时区的当前时间？**

你知道这道题的正确答案应该如何回答吗？背后的原理又是什么呢？

然后，紧接着，我又提出了以下问题：

**为什么以下代码无法得到美国时间。（在东八区的计算机上）**

```java
System.out.println(Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles")).getTime());
```

接下来，本文就围绕这两个问题，来带领读者一起学习一下哪些和Java中的时间有关的概念。

### 时区

前面提到了时区，可能很多读者不知道什么是时区，先来简单介绍一下。

时区是地球上的区域使用同一个时间定义。以前，人们通过观察太阳的位置（时角）决定时间，这就使得不同经度的地方的时间有所不同（地方时）。1863年，首次使用时区的概念。时区通过设立一个区域的标准时间部分地解决了这个问题。

世界各个国家位于地球不同位置上，因此不同国家，特别是东西跨度大的国家日出、日落时间必定有所偏差。这些偏差就是所谓的时差。

为了照顾到各地区的使用方便，又使其他地方的人容易将本地的时间换算到别的地方时间上去。有关国际会议决定将地球表面按经线从东到西，划成一个个区域，并且规定相邻区域的时间相差1小时。在同一区域内的东端和西端的人看到太阳升起的时间最多相差不过1小时。当人们跨过一个区域，就将自己的时钟校正1小时（向西减1小时，向东加1小时），跨过几个区域就加或减几小时。这样使用起来就很方便。

现今全球共分为24个时区。由于实用上常常1个国家，或1个省份同时跨着2个或更多时区，为了照顾到行政上的方便，常将1个国家或1个省份划在一起。所以时区并不严格按南北直线来划分，而是按自然条件来划分。**例如，中国幅员宽广，差不多跨5个时区，但为了使用方便简单，实际上在只用东八时区的标准时即北京时间为准。**

### 格林威治时间

前面提到了，时区通过设立一个区域的标准时间部分地解决了不同地方看到的太阳位置不一样而无法定义时间的问题。那么这个标准时间是什么呢？

前面还提到。中国位于东八区，一般是用`GMT+8`来表示东八区这个时区。那么，看起来`GMT`就是这个所谓的标准时间。`GMT`是个什么东西呢？为什么要在他的基础上`+8`来表示东八区呢？

GMT，是Greenwich Mean Time的缩写，及格林尼治（格林威治）平时，是指位于英国伦敦郊区的皇家格林尼治天文台当地的平太阳时，因为本初子午线被定义为通过那里的经线。

自1924年2月5日开始，格林尼治天文台负责每隔一小时向全世界发放调时信息。国际天文学联合会于1928年决定，将由格林威治平子夜起算的平太阳时作为世界时，也就是通常所说的格林威治时间

一般使用GMT+8表示中国的时间，是因为中国位于东八区，时间上比格林威治时间快8个小时。

北京时间还可以用CST表示，即China Standard Time，又名中国标准时间，是中国的标准时间。当格林威治时间为凌晨0：00时，中国标准时间正好为上午8：00。

所以，有等式：`CST=GMT +8 小时`

### 时间戳

前面提到了全世界各个时区的时间可能都是不一样的，那么有没有一个什么样的办法可以不受时区的限制，可以精确的表示时间呢。

其实是有的，这个方法就是时间戳。

时间戳（timestamp），一个能表示一份数据在某个特定时间之前已经存在的、 完整的、 可验证的数据,通常是一个字符序列，唯一地标识某一刻的时间。

**时间戳是指格林威治时间1970年01月01日00时00分00秒起至现在的总秒数。**

有了时间戳，无论我们深处哪个时区，从格林威治时间1970年01月01日00时00分00秒到现在这一时刻的总秒数应该是一样的。所以说，时间戳是一份能够表示一份数据在一个特定时间点已经存在的完整的可验证的数据。

**1970-01-01**

不知道大家有没有注意到一个比较特殊的时间，1970-01-01，相信每一个开发者对这个时间都并不陌生。一般如果软件系统中出现这个时间的时候，代表着出现了网络故障、线上bug等。

![img](https://ws1.sinaimg.cn/large/006tKfTcly1g0jpx8zgnsj30go0tn77w.jpg)￼

当有些计算机存储或者传输时间戳出错时，这个时间戳就会取默认值。而在计算机中，默认值通常是 0。

当 Timestamp 为 0，就表示时间（GMT）1970年1月1日0时0分0秒。中国使用北京时间，处于东 8 区，相应就是早上 8 点。因此在中国这边，时间出错了，就经常会显示成 1970年1月1日 08:00。

```
System.out.println(new Date(0));
//Thu Jan 01 08:00:00 CST 1970
```

当我们在Java代码中使用new Date(0)来创建时间的时候，得到的结果就是`Thu Jan 01 08:00:00 CST 1970`，既1970年1月1日 上午08点整。

### Date

前面提到了`java.util.Java`中的Date类，这个类通常用来表示时间。你可以通过getTime()方法访问java.util.Date实例的日期和时间，比如像这样：

```
Date date = new Date();
long time = date.getTime();
```

以上代码，其实得到的就是时间戳，在源码中也有明确的表述：

![-w716](https://www.hollischuang.com/wp-content/uploads/2018/12/15443433292098.jpg)￼

所以，我们就可以认为`java.util.Java`其实表示的就是从格林威治1970年1月1日零点到现在这一时刻的总秒数。

从Date的源码中也可以看到，**Date中是不包含时区有关的信息的，因为时间戳和时区没有关系。**

那么，如果想要把一个时间戳转换成不同时区的时间输出应该怎么做呢？

### 显示不同时区的时间

想要把时间戳转换成对应时区的时间，总要有个地方可以获取时区吧。其实，我们的计算机中是有时区相关的信息的。

无论我们使用的是哪种操作系统的电脑，都是可以查看时间的，而一般情况下，我们拿到的电脑都会展示中国时间，那是因为操作系统中已经设置了一个默认时区。

其实，Java中的时区信息也是从操作系统中取到的，默认情况下会使用操作系统的时区。

当我们使用`System.out.println`来输出一个时间的时候，他会调用Date类的toString方法，而该方法会读取操作系统的默认时区来进行时间的转换。

```java
public String toString() {
    // "EEE MMM dd HH:mm:ss zzz yyyy";
    BaseCalendar.Date date = normalize();
    ...
}

private final BaseCalendar.Date normalize() {
    ...
    TimeZone tz = TimeZone.getDefaultRef();
    if (tz != cdate.getZone()) {
        cdate.setZone(tz);
        CalendarSystem cal = getCalendarSystem(cdate);
        cal.getCalendarDate(fastTime, cdate);
    }
    return cdate;
}

static TimeZone getDefaultRef() {
    TimeZone defaultZone = defaultTimeZone;
    if (defaultZone == null) {
        // Need to initialize the default time zone.
        defaultZone = setDefaultZone();
        assert defaultZone != null;
    }
    // Don't clone here.
    return defaultZone;
}
```

主要代码如上。也就是说如果我们想要通过`System.out.println`输出一个Date类的时候，输出美国洛杉矶时间的话，就需要想办法把`defaultTimeZone`改为`America/Los_Angeles`，这个方法就是：

```
TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
```

所以，当我们想要输出美国洛杉矶时间时，可以选择这种方式：

```
TimeZone.setDefault(TimeZone.getTimeZone("America/Los_Angeles"));
Date date = new Date();
System.out.println(date);
```

还有一种方式，就是通过SimpleDateFormat来处理，这种方式我们在[你真的会使用SimpleDateFormat吗](https://www.hollischuang.com/archives/3017)中也介绍过。这里就不再展开了。

接下来，我们再回到文章开始的那个问题：

**为什么以下代码无法得到美国时间。（在东八区的计算机上）**

```
System.out.println(Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles")).getTime());
```

其实答案前面也已经说过了，我们通过查看`Date.toString`的源码，发现在输出的过程中该方法只会去获取系统的默认时区，只有修改了默认时区才会显示该时区的时间。

但是，通过阅读Calendar的源码，我们可以发现，`getInstance`方法虽然有一个参数可以传入时区，但是并没有将默认时区设置成传入的时区。

而在Calendar.getInstance.getTime后得到的时间只是一个时间戳，其中未保留任何和时区有关的信息，所以，在输出时，还是显示的是当前系统默认时区的时间。

### Java 8与时区

了解Java8 的朋友可能都知道，Java8提供了一套新的时间处理API，这套API比以前的时间处理API要友好的多。

Java8 中加入了对时区的支持，带时区的时间为分别为：ZonedDate、ZonedTime、ZonedDateTime。其中每个时区都对应着 ID，地区ID都为 “{区域}/{城市}”的格式，如`Asia/Shanghai`、`America/Los_Angeles`等。

在Java8中，直接使用以下代码即可输出美国洛杉矶的时间：

```
LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
System.out.println(now);
```

### 总结

世界上有很多时区，不同的时区的时间不一样，中国使用东八区的时间作为标准时间。美国自东海岸至西海岸横跨西五区至西十区，共六个时区。

所谓东八区，一般表示成`GMT+8`，这里的GMT指的是格林威治时间。计算机中经常使用时间戳来表示时间，时间戳指的就是当前时间举例格林威治的1970-01-01 00：00：00的总秒数。

而Java中的Date类中是不包含时区信息的，在使用`System.out.println`打印Date的时候，回调用Date.toString方法，该方法会获取系统的默认时区来转换时间。

在Java8中可以使用ZonedTime、ZonedDate和ZonedDateTime来表示带有时区信息的时间。

### 拓展知识

**什么是冬令时？什么是夏令时？**

夏令时、冬令时的出现，是为了充分利用夏天的日照，所以时钟要往前拨快一小时，冬天再把表往回拨一小时。其中夏令时从3月第二个周日持续到11月第一个周日。

冬令时：北京和洛杉矶时差16小时，北京和纽约时差13小时。 夏令时：北京和洛杉矶时差12小时，北京和纽约时差15小时。

**CET,UTC,GMT,CST几种常见时间的含义和关系？**

CET，欧洲中部时间（英語：Central European Time，CET）是比世界标准时间（UTC）早一个小时的时区名称之一。

UTC，协调世界时，又称世界标准时间或世界协调时间，简称UTC。

GMT，格林尼治标准时间，是指位于英国伦敦郊区的皇家格林尼治天文台的标准时间，因为本初子午线被定义在通过那里的经线。

CST，北京时间，China Standard Time，又名中国标准时间，是中国的标准时间。

CET=UTC/GMT + 1小时、CST=UTC/GMT +8 小时、CST=CET+9