package com.example.distributedlock.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;


/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2021 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author
 * @since 2021/1/6 下午4:31
 */
@Service("redisLockService")
public class RedisLockServiceImpl implements RedisLockService {

    //Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    /**
     * 锁键
     */
    private String lock_key = "redis_lock";

    /**
     * 锁过期时间
     */
    protected long internalLockLeaseTime = 30000;

    /**
     * 获取锁的超时时间
     */
    private long timeout = 100000;


    /**
     * SET命令的参数
     */
    SetParams params = SetParams.setParams().nx().px(internalLockLeaseTime);



    @Override
    public boolean lock(String id) {
        Jedis jedis = new Jedis("127.0.0.1", 6004);

        Long start = System.currentTimeMillis();
        try{
            for(;;){
                //SET命令返回OK ，则证明获取锁成功
                String lock = jedis.set(lock_key, id, params);
                if("OK".equals(lock)){
                    return true;
                }
                //否则循环等待，在timeout时间内仍未获取到锁，则获取失败
                long l = System.currentTimeMillis() - start;
                if (l>=timeout) {
                    return false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            jedis.close();
        }
    }

    @Override
    public boolean unlock(String id) {
        Jedis jedis = new Jedis("127.0.0.1", 6004);
        String script =
                "if redis.call('get',KEYS[1]) == ARGV[1] then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else" +
                        "   return 0 " +
                        "end";
        try {
            Object result = jedis.eval(script, Collections.singletonList(lock_key),
                    Collections.singletonList(id));
            if("1".equals(result.toString())){
                return true;
            }
            return false;
        }finally {
            jedis.close();
        }
    }

    @Override
    public boolean intStock(String key,String value){
        Jedis jedis = new Jedis("127.0.0.1", 6004);
        if(jedis.set(key,value).equals("OK")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean decrStock(String key,String theadName){
        Jedis jedis = new Jedis("127.0.0.1", 6004);
        Long restValue = jedis.decr(key);
        System.out.println("- "+theadName+" 请求扣减库存,剩余库存="+restValue);
        if(restValue>=0){
            return true;
        }else{
            return false;
        }
    }
}
