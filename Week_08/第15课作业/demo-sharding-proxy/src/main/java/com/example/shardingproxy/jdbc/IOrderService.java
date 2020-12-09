package com.example.shardingproxy.jdbc;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/12/1 9:56 下午
 */
public interface IOrderService {
    /**
     * 新增订单
     * @return
     */
    public int  addOrder();

    /**
     * 删除订单
     * @return
     */
    public int delOrder(long order_id,long user_id);

    /**
     * 更新订单
     * @return
     */
    public int updateOrder(long order_id,int order_count,long user_id);


    /**
     * 查询订单
     */
    public void queryOrder(long order_id,long user_id);
}
