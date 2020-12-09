package com.example.shardingproxy;


import com.example.shardingproxy.jdbc.OrderServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoShardingProxyApplicationTests {
    @Resource
    private OrderServiceImp orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddOrder(){
        System.out.println("成功新增"+orderService.addOrder()+"个订单！");
    }

    @Test
    void testDelOrder(){
        System.out.println("成功删除"+orderService.delOrder(5879812469004001280L,1L)+"个订单！");
    }

    @Test
    void tetsUpdateOrder(){
        System.out.println("成功更新"+orderService.updateOrder(5879812469004001280L,22,1L)+"个订单！");
    }

    @Test
    void testqueryOrder(){
       orderService.queryOrder(5879812469004001280L,1L);
    }
}
