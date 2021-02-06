## Java0期训练营毕业总结

#### 1)JVM

- 学习了怎么javap查看字节码，类加载器，java虚拟机的内存管理等知识。
  对字节码有了一定的了解，对照着HelloByteCode.java，MovingAverage.java，LocalVariableTest.java过了一遍。学习了类加载器之后，还自己写了一个类加载器，作为了作业提交。
- 学习了Jstat，Jmap，Jstack，JVisualVM，JMC等工具，通过工具来看内存的使用情况更直观。还用jstat，Jstack和 Jmap分析了服务器上一个应用的内存情况。

- 高吞吐量用 并行GC ，低延迟用 G1 GC或者 CMS。 

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-120027.png)

#### 2)NIO

- 学习了Socket编程，知道了可以通过Socket怎么写http Server。不同的实现方式，吞吐量不一样。学习了NIO的基本知识，知道了，阻塞、非阻塞、同步和异步组合成的5种模式的特点及应用场景。最后还学习了netty实现NIO 模式的http服务器的编写。

- 学习了netty编程的基本知识，netty如何实现高性能。主要是三种reactor的模式，单线程reactor，多线程reactor，主从reactor。记住了BECH = Broker，EventLoop，Channel，Handler。

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-121522.png)

  

#### 3) 并发编程

- 线程的基础知识（基础接口、分类、线程状态、属性和重要方法）

- 线程安全相关知识

- 并发的可见性

- 线程池相关的工具类

- 并发包（锁、原子类、信号量工具类、线程安全集合类、ThreadLocal和并行Stream）

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-100003.png)

#### 4)Spring 和 ORM 等框架

- Spring框架的发展与框架，Spring AOP（最主要的作用是对类的增强），Spring Bean， Spring XML配置， Spring JMS。还学习Spring Boot， Springboot Starter，数据持久化Hibernate ，mybatis ，JPA。

- Java8的新功能：Lambda，Stream

- 好用的工具Lombok,Guava,

- 设计原则（SOLISD）,设计模式（常用的有23种）, 单元测试以及编码规范（google，Ali，唯品会）

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-123423.png)


#### 5)MySQL 数据库和 SQL

- 数据库表设计的范式（不能拘泥于范式，范式是可以打破的）

- mysql的设计优化和配置优化

- 学习了Mysql事务与锁，SQL优化，印象最深的是SQL执行的先后顺序的那个图。Mysql的主从复制，读写分离，还有高可用。

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-124553.png)

#### 6) 分库分表

- 学习了分库分表，还有分布式事务。

- 事务类型包括：刚性事务：XA，柔性事务BASE：TCC ，TAC，。

- 用sharding-proxy，实现了分库分表。数据库2个，每个库16张表。

  - 因为16/2余数为0，如果按订单ID来分库，会导致奇数ID的全部到一个数据库(order_db_1)，偶数ID到全部熬一个数据库（order_db_0）,所以我配置sharding-proxy采用了数据库分片算法不按订单ID而是用户ID，这样可以保障16张表都有数据。

    ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-130927.png)

    

#### 7)RPC 和微服务

- RPC就是像调用本地方法一样调用远程方法

- RPC原理：

  1.本地代理存根: Stub

  2.本地序列化反序列化 

  3.网络通信

  4.远程序列化反序列化 

  5.远程服务存根: Skeleton 

  6.调用实际业务服务

  7.原路返回服务结果 

  8.返回给本地调用方

- 常见的RPC技术：Hessian，Thrift , gRPC

- 学习了微服务的发展历史：单体架构-->垂直架构-->SOA架构-->微服务架构

- 学习了微服务的发展历程：

  1、响应式微服务

  2、服务网格与云原生

  3、数据库网格

  4、单元化架构

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-132712.png)

#### 8) 分布式缓存

- 学习了redis的基础功能和高级功能，用LUA脚本实现了分布式锁，用decr实现了减库存的计数器作业
- 学习了redis的主从模式，哨兵模式和集群模式。
- 用docker在本地将redis三种模式全部配置了一次。

![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-134443.png)



#### 9) 分布式消息队列

- 习了消息队列

- 学习了第二代消息队列KAFKA

- 学习了第一代消息队列RabbitMQ、第三代消息队列Pulsar（计算和存储分离）

- 学习了EIP 和架构设计

  ![](https://xcmdpic.oss-cn-beijing.aliyuncs.com/zotero/2021-02-06-134831.png)

#### 总结

通过0期训练营的学习收获良多，不仅收获了java进阶的整体脉络，老师们的加课学到了很多除技术之外的知识。源码学习群，打开了一条开源的道路。非常感谢秦老师、猫大人、cuicui老师、还有很多助教老师的帮助和指导。虽然消化这些知识还需要时间，至少收获了一个地图和学习的方法，可以少走好多弯路。后面要花1年时间把2/3的作业都做完。