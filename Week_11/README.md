## 学习笔记

第22课学习了redis的基础功能和高级功能，

用LUA脚本实现了分布式锁，

用decr实现了减库存的计数器作业





第23课学习了redis的主从模式，哨兵模式和集群模式



一、配置主从模式

1、创建子网络
docker network create --subnet=172.60.0.0/16  mynetwork


2、创建并运行容器

docker run --net mynetwork --ip 172.60.0.2 -p 6001:6001 --name redis-master -v /Users/xiaochun/docker-space/redis/conf/redis-ms/6001/master.conf:/etc/redis/conf/master.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/6001/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/master.conf --appendonly yes


docker run --net mynetwork --ip 172.60.0.3 -p 6004:6004 --name redis-slave1 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/6004/slave1.conf:/etc/redis/conf/slave1.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/6004/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/slave1.conf --appendonly yes


docker run --net mynetwork --ip 172.60.0.4 -p 6005:6005 --name redis-slave2 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/6005/slave2.conf:/etc/redis/conf/slave2.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/6005/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/slave2.conf --appendonly yes



3、查看容器
docker ps -a

CONTAINER ID   IMAGE                COMMAND                  CREATED        STATUS        PORTS                              NAMES
6249b35218a2   redis:6.0.9-alpine   "docker-entrypoint.s…"   16 hours ago   Up 16 hours   0.0.0.0:6005->6005/tcp, 6379/tcp   redis-slave2
0d7dfffe6fd7   redis:6.0.9-alpine   "docker-entrypoint.s…"   16 hours ago   Up 16 hours   0.0.0.0:6004->6004/tcp, 6379/tcp   redis-slave1
90ef2435cbab   redis:6.0.9-alpine   "docker-entrypoint.s…"   16 hours ago   Up 16 hours   0.0.0.0:6001->6001/tcp, 6379/tcp   redis-master



4、查看主从关系
172.60.0.3:6004> info replication

# Replication

role:slave
master_host:172.60.0.2
master_port:6001
master_link_status:up
master_last_io_seconds_ago:3
master_sync_in_progress:0
slave_repl_offset:25899
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:832e1a35c2ae7ac2e484243bfe33b7861f6a7e13
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:25899
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:25899



二、配置sentinel模式

1、配置三个sentinel节点

docker run --net mynetwork --ip 172.60.0.5 -p 5001:5001 --name sentinel1 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/5001/sentinel1.conf:/etc/redis/conf/sentinel1.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/5001/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/sentinel1.conf --sentinel --appendonly yes

docker run --net mynetwork --ip 172.60.0.6 -p 5002:5002 --name sentinel2 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/5002/sentinel2.conf:/etc/redis/conf/sentinel2.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/5002/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/sentinel2.conf --sentinel --appendonly yes

docker run --net mynetwork --ip 172.60.0.7 -p 5003:5003 --name sentinel3 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/5003/sentinel3.conf:/etc/redis/conf/sentinel3.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/5003/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/sentinel3.conf --sentinel --appendonly yes

2、查看容器
CONTAINER ID   IMAGE                COMMAND                  CREATED         STATUS          PORTS                              NAMES
a5a818b6ee2c   redis:6.0.9-alpine   "docker-entrypoint.s…"   8 minutes ago   Up 8 minutes    0.0.0.0:5001->5001/tcp, 6379/tcp   sentinel1
093fc14a1b2e   redis:6.0.9-alpine   "docker-entrypoint.s…"   3 hours ago     Up 3 hours      0.0.0.0:5003->5003/tcp, 6379/tcp   sentinel3
20576295dd32   redis:6.0.9-alpine   "docker-entrypoint.s…"   3 hours ago     Up 3 hours      0.0.0.0:5002->5002/tcp, 6379/tcp   sentinel2
6249b35218a2   redis:6.0.9-alpine   "docker-entrypoint.s…"   20 hours ago    Up 20 hours     0.0.0.0:6005->6005/tcp, 6379/tcp   redis-slave2
0d7dfffe6fd7   redis:6.0.9-alpine   "docker-entrypoint.s…"   20 hours ago    Up 20 hours     0.0.0.0:6004->6004/tcp, 6379/tcp   redis-slave1
90ef2435cbab   redis:6.0.9-alpine   "docker-entrypoint.s…"   20 hours ago    Up 32 minutes   0.0.0.0:6001->6001/tcp, 6379/tcp   redis-master


3、查看sentinel1的日志，master节点：172.60.0.2 6001，slave 172.60.0.3:6004，slave 172.60.0.4:6005， sentinel：172.60.0.6 5002，172.60.0.7 5003
1:X 06 Jan 2021 03:44:32.035 # Sentinel ID is 32427993dd317ee6dcbdedb89556193f362235b8
1:X 06 Jan 2021 03:44:32.035 # +monitor master mymaster 172.60.0.2 6001 quorum 2
1:X 06 Jan 2021 03:44:32.058 * +slave slave 172.60.0.3:6004 172.60.0.3 6004 @ mymaster 172.60.0.2 6001
1:X 06 Jan 2021 03:44:32.063 * +slave slave 172.60.0.4:6005 172.60.0.4 6005 @ mymaster 172.60.0.2 6001
1:X 06 Jan 2021 03:45:20.701 * +sentinel sentinel 5daa0962a2d70355b53cb580de83b70909c19fe3 172.60.0.6 5002 @ mymaster 172.60.0.2 6001
1:X 06 Jan 2021 03:45:35.178 * +sentinel sentinel 4661d65cc595fc199005a8084c580c2beb3af620 172.60.0.7 5003 @ mymaster 172.60.0.2 6001


4、停止master节点172.60.0.2 6001，切换master节点到 172.60.0.3 6004
1:X 06 Jan 2021 06:07:32.563 # +switch-master mymaster 172.60.0.2 6001 172.60.0.3 6004
1:X 06 Jan 2021 06:07:32.564 * +slave slave 172.60.0.4:6005 172.60.0.4 6005 @ mymaster 172.60.0.3 6004
1:X 06 Jan 2021 06:07:32.564 * +slave slave 172.60.0.2:6001 172.60.0.2 6001 @ mymaster 172.60.0.3 6004
1:X 06 Jan 2021 06:07:32.574 # Could not create tmp config file (Permission denied)
1:X 06 Jan 2021 06:07:32.574 # WARNING: Sentinel was not able to save the new configuration on disk!!!: Invalid argument
1:X 06 Jan 2021 06:08:02.611 # +sdown slave 172.60.0.2:6001 172.60.0.2 6001 @ mymaster 172.60.0.3 6004
1:X 06 Jan 2021 06:08:21.809 # -sdown slave 172.60.0.2:6001 172.60.0.2 6001 @ mymaster 172.60.0.3 6004
1:X 06 Jan 2021 06:08:31.788 * +convert-to-slave slave 172.60.0.2:6001 172.60.0.2 6001 @ mymaster 172.60.0.3 6004




三、配置集群模式

1、
docker run --net mynetwork --ip 172.60.0.8 -p 7001:7001 --name cluster1 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/7001/cluster1.conf:/etc/redis/conf/cluster1.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/7001/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/cluster1.conf --appendonly yes

docker run --net mynetwork --ip 172.60.0.9 -p 7002:7002 --name cluster2 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/7002/cluster2.conf:/etc/redis/conf/cluster2.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/7002/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/cluster2.conf --appendonly yes

docker run --net mynetwork --ip 172.60.0.10 -p 7003:7003 --name cluster3 -v /Users/xiaochun/docker-space/redis/conf/redis-ms/7003/cluster3.conf:/etc/redis/conf/cluster3.conf  -v /Users/xiaochun/docker-space/redis/data/redis-ms/7003/data:/etc/redis/data -d redis:6.0.9-alpine redis-server /etc/redis/conf/cluster3.conf --appendonly yes




2、查看docker容器状态
docker ps -a

CONTAINER ID   IMAGE                COMMAND                  CREATED          STATUS             PORTS                              NAMES
cad796ecf6be   redis:6.0.9-alpine   "docker-entrypoint.s…"   15 minutes ago   Up 15 minutes      6379/tcp, 0.0.0.0:7003->7003/tcp   cluster3
f16f73f2c461   redis:6.0.9-alpine   "docker-entrypoint.s…"   15 minutes ago   Up 15 minutes      6379/tcp, 0.0.0.0:7002->7002/tcp   cluster2
26d7093041b2   redis:6.0.9-alpine   "docker-entrypoint.s…"   15 minutes ago   Up 15 minutes      6379/tcp, 0.0.0.0:7001->7001/tcp   cluster1
a5a818b6ee2c   redis:6.0.9-alpine   "docker-entrypoint.s…"   38 minutes ago   Up 38 minutes      0.0.0.0:5001->5001/tcp, 6379/tcp   sentinel1
093fc14a1b2e   redis:6.0.9-alpine   "docker-entrypoint.s…"   3 hours ago      Up 3 hours         0.0.0.0:5003->5003/tcp, 6379/tcp   sentinel3
20576295dd32   redis:6.0.9-alpine   "docker-entrypoint.s…"   3 hours ago      Up 3 hours         0.0.0.0:5002->5002/tcp, 6379/tcp   sentinel2
6249b35218a2   redis:6.0.9-alpine   "docker-entrypoint.s…"   21 hours ago     Up 21 hours        0.0.0.0:6005->6005/tcp, 6379/tcp   redis-slave2
0d7dfffe6fd7   redis:6.0.9-alpine   "docker-entrypoint.s…"   21 hours ago     Up 21 hours        0.0.0.0:6004->6004/tcp, 6379/tcp   redis-slave1
90ef2435cbab   redis:6.0.9-alpine   "docker-entrypoint.s…"   21 hours ago     Up About an hour   0.0.0.0:6001->6001/tcp, 6379/tcp   redis-master




3、登录cluster1命令行，执行创建集群命令
docker exec -it cluster1  /bin/sh

/data # redis-cli --cluster create 172.60.0.8:7001 172.60.0.9:7002 172.60.0.10:7003 --cluster-replicas 0

Can I set the above configuration? (type 'yes' to accept): yes

>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
>>> Waiting for the cluster to join
>>> .
>>> Performing Cluster Check (using node 172.60.0.8:7001)
>>> M: 5d65a4564040dc069651b3255d2ab75f5a81a84e 172.60.0.8:7001
>>> slots:[0-5460] (5461 slots) master
>>> M: cf8d49ae2689722552e781ac279ff57c15a25da5 172.60.0.9:7002
>>> slots:[5461-10922] (5462 slots) master
>>> M: 6fbe1c96304b7da1980bc7dc6f9ca995358e3f7e 172.60.0.10:7003
>>> slots:[10923-16383] (5461 slots) master
>>> [OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
>>> [OK] All 16384 slots covered.



4、集群测试，可以切换槽
/data # redis-cli -c -h 172.60.0.8 -p 7001
172.60.0.8:7001> set ckey1 100
OK
172.60.0.8:7001> set ckey2 100
-> Redirected to slot [9352] located at 172.60.0.9:7002
OK
172.60.0.9:7002> set ckey3 100
-> Redirected to slot [13481] located at 172.60.0.10:7003
OK