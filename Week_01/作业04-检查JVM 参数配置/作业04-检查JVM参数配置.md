检查JVM参数配置



1. 查看java进程

   jps -l

   > 112206 dataopradmin-system-2.5.jar

2. jstat查看内存

   jstat -gcutil 112206 1000 1000

   

   >  S0      S1        E         O      M     CCS     YGC    YGCT   FGC  FGCT   GCT  
   >
   >  0.00 14.59 43.53 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.54 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.64 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.64 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.64 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.64 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.69 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 43.69 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 44.09 81.29 94.21 91.31 12795  70.868  32  5.472  76.340
   >
   >  0.00 14.59 44.14 81.29 94.21 91.31 12795  70.868  32  5.472  76.340

3. jstat数据分析内存：

   - 老年代使用率81.29%
   - 元空间使用率94.21%
   - 压缩class空间使用率91.31%
   - 年轻代GC一次的时间，T= YGCT/YGC=70.868（s）/12795=5.5ms
   - FULLGC一次的时间，T =FGCT / FGC = 5.472（s）/32=171ms

4. jstack查看内存

   jstack -l 112206

   

   > "http-nio-8012-exec-7" #42 daemon prio=5 os_prio=0 tid=0x00007feeada37800 nid=0x9efb runnable [0x00007fee798e4000]
   >
   >   java.lang.Thread.State: RUNNABLE
   >
   > ​	at java.net.PlainSocketImpl.socketConnect(Native Method)
   >
   > ​	at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
   >
   > ​	- locked <0x00000000d990f2c8> (a java.net.SocksSocketImpl)
   >
   > ​	at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
   >
   > ​	at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
   >
   > ​	at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
   >
   > ​	at java.net.Socket.connect(Socket.java:589)
   >
   > ​	at com.mysql.cj.protocol.StandardSocketFactory.connect(StandardSocketFactory.java:155)
   >
   > ​	at com.mysql.cj.protocol.a.NativeSocketConnection.connect(NativeSocketConnection.java:65)
   >
   > ​	at com.mysql.cj.NativeSession.connect(NativeSession.java:152)
   >
   > ​	at com.mysql.cj.jdbc.ConnectionImpl.connectWithRetries(ConnectionImpl.java:849)
   >
   > ​	at com.mysql.cj.jdbc.ConnectionImpl.createNewIO(ConnectionImpl.java:830)
   >
   > ​	- locked <0x00000000db9c3790> (a com.mysql.cj.jdbc.ConnectionImpl)
   >
   > ​	at com.mysql.cj.jdbc.ConnectionImpl.<init>(ConnectionImpl.java:455)
   >
   > ​	at com.mysql.cj.jdbc.ConnectionImpl.getInstance(ConnectionImpl.java:240)
   >
   > ​	at com.mysql.cj.jdbc.NonRegisteringDriver.connect(NonRegisteringDriver.java:207)
   >
   > ​	at com.alibaba.druid.pool.DruidAbstractDataSource.createPhysicalConnection(DruidAbstractDataSource.java:1644)
   >
   > ​	at com.alibaba.druid.pool.DruidAbstractDataSource.createPhysicalConnection(DruidAbstractDataSource.java:1710)
   >
   > ​	at com.alibaba.druid.pool.DruidDataSource.init(DruidDataSource.java:923)
   >
   > ​	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1383)
   >
   > ​	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:1379)
   >
   > ​	at com.alibaba.druid.pool.DruidDataSource.getConnection(DruidDataSource.java:109)

5. jstack 数据分析

   - #42线程 ， 在运行（runnable），锁了网络和到mysql的连接。
   - - locked <0x00000000d990f2c8> (a java.net.SocksSocketImpl)
     - locked <0x00000000db9c3790> (a com.mysql.cj.jdbc.ConnectionImpl)

   - 推测：分析是查询比较耗时

6. jmap查看内存

   jmap -heap 112206

   > Debugger attached successfully.
   >
   > Server compiler detected.
   >
   > JVM version is 25.181-b13
   >
   > 
   >
   > using thread-local object allocation.
   >
   > Mark Sweep Compact GC
   >
   > 
   >
   > Heap Configuration:
   >
   >   MinHeapFreeRatio     = 40
   >
   >   MaxHeapFreeRatio     = 70
   >
   >   MaxHeapSize       = 645922816 (616.0MB)
   >
   >   NewSize         = 13959168 (13.3125MB)
   >
   >   MaxNewSize        = 215285760 (205.3125MB)
   >
   >   OldSize         = 27983872 (26.6875MB)
   >
   >   NewRatio         = 2
   >
   >   SurvivorRatio      = 8
   >
   >   MetaspaceSize      = 21807104 (20.796875MB)
   >
   >   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   >
   >   MaxMetaspaceSize     = 17592186044415 MB
   >
   >   G1HeapRegionSize     = 0 (0.0MB)
   >
   > 
   >
   > Heap Usage:
   >
   > New Generation (Eden + 1 Survivor Space):
   >
   >   capacity = 39649280 (37.8125MB)
   >
   >   used   = 17638320 (16.821212768554688MB)
   >
   >   free   = 22010960 (20.991287231445312MB)
   >
   >   44.48585194989669% used
   >
   > Eden Space:
   >
   >   capacity = 35258368 (33.625MB)
   >
   >   used   = 15084456 (14.385658264160156MB)
   >
   >   free   = 20173912 (19.239341735839844MB)
   >
   >   42.78262680791124% used
   >
   > From Space:
   >
   >   capacity = 4390912 (4.1875MB)
   >
   >   used   = 2553864 (2.4355545043945312MB)
   >
   >   free   = 1837048 (1.7519454956054688MB)
   >
   >   58.16249562733209% used
   >
   > To Space:
   >
   >   capacity = 4390912 (4.1875MB)
   >
   >   used   = 0 (0.0MB)
   >
   >   free   = 4390912 (4.1875MB)
   >
   >   0.0% used
   >
   > tenured generation:
   >
   >   capacity = 88002560 (83.92578125MB)
   >
   >   used   = 82568992 (78.74392700195312MB)
   >
   >   free   = 5433568 (5.181854248046875MB)
   >
   >   93.82567052594834% used

7. jmap -heap 数据分析

   - 垃圾回收不是Parallel GC,用的是CMS，我们对系统延迟，要求并不高，并不要求降低GC停顿导致的系统延迟。对系统的吞吐量要求高，后面会改成Parallel GC来试试。

   

