package com.example.distributedlock.controller;

import com.example.distributedlock.service.RedisLockService;
import com.example.distributedlock.utils.IdWorkerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.*;


/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2021 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2021/1/6 下午5:49
 */
@RestController
public class TestController {
    @Resource
    private RedisLockService redisLockService;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/lock")
    @ResponseBody
    public String lock() throws InterruptedException {


        //通过雪花算法获取唯一的ID字符串
        Long id = IdWorkerUtils.getInstance().createUUID();

        int clientcount =6;


        ExecutorService executorService=new ThreadPoolExecutor(2,clientcount,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        long start = System.currentTimeMillis();
        for (int i = 0;i<clientcount;i++){
            executorService.execute(() -> {
                boolean flag = redisLockService.lock(id + "");
                System.out.println("-执行线程Name="+Thread.currentThread().getName()+"，lock结果="+flag);
                if(flag){
                    try {
                        //5秒后释放锁给其他线程
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    redisLockService.unlock(id+"");
                }

                long end = System.currentTimeMillis();
                logger.info("-执行线程Name:{},总耗时:{}",Thread.currentThread().getName(),end-start);

            });
        }
        return "Hello lock";
    }

    @RequestMapping("/decr")
    @ResponseBody
    public String decr() throws InterruptedException {
        /**
         * 10人抢5个库存
         */
        int clientcount =10;
        System.out.println("库存初始化5个，结果"+redisLockService.intStock("stock", "5"));

        ExecutorService executorService=new ThreadPoolExecutor(2,clientcount,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        long start = System.currentTimeMillis();
        for (int i = 0;i<clientcount;i++){
            executorService.execute(() -> {
                boolean flag = redisLockService.decrStock("stock",Thread.currentThread().getName());
                System.out.println("-执行线程Name="+Thread.currentThread().getName()+"，扣减库存结果="+flag);

                long end = System.currentTimeMillis();
                logger.info("-扣减库存-执行线程Name:{},总耗时:{}",Thread.currentThread().getName(),end-start);
            });
        }
        return "Hello decr";
    }
}
