package com.example.distributedlock.service;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2021 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2021/1/6 下午4:31
 */
public interface RedisLockService {
    /**
     * 锁
     * @param id
     * @return
     */
    public boolean lock(String id);


    /**
     * 解锁
     * @param id
     * @return
     */
    public boolean unlock(String id);


    /**
     * intStock
     */
    public boolean intStock(String key,String value);

    /**
     * 减库存
     */
    public boolean decrStock(String key,String theadName);
}
