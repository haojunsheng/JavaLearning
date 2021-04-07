# Java Developer

java开发需要掌握的知识。

java基础。spring，mysql，redis。

# 目标

## JVM

1. JVM 基础知识、Java 字节码技术、JVM 类加载器、JVM 内存模型、JVM 启动参数详解；

2. JDK 内置命令行工具、JDK 内置图形界面工具、JDWP 简介、JMX 与相关工具；

3. 常见的 JVM GC 算法（Parallel GC/CMS GC/G1 GC）基本原理和特点；

4. 新一代 GC 算法（Java11 ZGC/Java12 Shenandoah) 和 Oracle GraalVM；

5. GC 日志解读与分析、JVM 的线程堆栈等数据分析、内存 dump 和内存分析工具；

6. fastThread 相关工具以及面临复杂问题时的几个高级工具的使用；

7. JVM 问题排查分析的常用手段、性能调优的最佳实践经验等；

8. JVM 相关的常见面试问题必知必会、全面分析。

## NIO

1. 同步/异步、阻塞/非阻塞、BIO、NIO、AIO、Reactor/Proactor；

2. ByteBuff/Acceptor/Channel/Handler、NioEventLoopGroup/EventLoop、bossGroup/workerGroup；

3. Netty 的启动和执行过程、线程模型、事件驱动、服务端和客户端的使用方式；

4. 常见的 API Gateway/HTTP Server、SEDA 原理、业务 API 网关的功能和结构；

5. Throughout/TPS/QPS、Latency/P99/P95/P90、ApacheBench/Wrk/JMeter/LoadRunner。

## 并发编程

1. Java 多线程基础：线程、锁、synchronized、volatile/final、sleep/await/notify/fork/join；

2. Java 并发包基础：线程池 Executor、AQS/CAS、Atomic 原子操作、Lock/ReadWriteLock/Condition、Callable/Future；

3. Java 并发容器与工具：BlockingQueue/CopyOnWriteList/ConcurrentHashMap、CountDownLatch/CyclicBarrier/Semaphore等；

4. 其他：万金油 ThreadLocal，化繁为简 Java8 parallelStream 等。

## Spring

1. Spring 技术体系（Spring Core/Web/MVC/Data/Messaging、Spring Boot 等）；

2. ORM 技术体系（JPA、Hibernate、MyBatis 等）。

## 性能分析与 MySQL 优化

1. 系统可观测性（日志、调用链跟踪、指标度量），80/20 优化原则，CPU、内存、磁盘/网络 IO 等分析；

2. MySQL 的锁、事务、索引、并发级别、死锁、执行计划、慢 SQL 统计、缓存失效、参数优化；

3. 库表设计优化，引擎选择，表结构优化设计，列类型选择，索引设计，外键等；

4. SQL 查询优化，索引选择，连接优化，聚合查询优化，Union 优化，子查询优化，条件优化等；

5. 场景分析，主键生成与优化，高效分页，快速导入导出数据，解决死锁问题等。



## 分布式

1. 基础知识：RPC、通信与数据协议、WebService、Hessian、REST、gRPC、Protocol Buffers 等；

2. 服务化：服务治理、配置管理、注册发现、服务分组、版本管理、集群管理、负载均衡、限流与降级熔断等；

3. 框架：Apache Dubbo 的功能与原理分析，Spring Cloud 体系，具体的案例实践；

4. 微服务：微服务架构的 6 个最佳实践，从微服务到服务网格、云原生的介绍。

## 分布式缓存Redis

1. 缓存的应用场景，缓存加载策略与失效策略，缓存与数据库同步等；

2. 缓存预热、缓存失效、缓存击穿、缓存雪崩、多级缓存、缓存与 Spring+ORM 框架集成；

3. 缓存中间件，Redis（几种常用数据结构、分布式锁、Lua 支持、集群），Hazelcast（Java 数据结构、内存网格、事务支持、集群）；

4. 缓存的应用场景，排行数据展示，分布式 ID 生成，Session 共享，热点账户操作等。

## 分布式消息

1. 消息队列的基本知识，Broker 与 Client，消息模式（点对点、发布订阅），消息协议（STOMP、JMS、AMQP、OpenMessaging 等），消息 QoS（最多一次、最少一次、有且仅有一次），消息重试，延迟投递，事务性，消息幂等与去重；

2. 消息中间件：ActiveMQ 的简单入门，Kafka 的基本功能与使用，高可用（集群、分区、副本）、性能，RabbitMQ 和 RocketMQ，Pulsar 的简单介绍；

3. 消息的 4 个主要功能，搭建一个 Kafka 集群，实现常用的消息发送、消息消费功能；

4. 典型使用场景，使用 MQ 实现交易订单的处理，动手实现一个简化版的消息队列。

## 数据结构与算法

数据结构与算法之美。

