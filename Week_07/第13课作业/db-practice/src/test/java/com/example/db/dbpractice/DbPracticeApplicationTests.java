package com.example.db.dbpractice;

import com.example.db.dbpractice.jdbc.MysqlDbConnectionServiceImp;
import com.example.db.dbpractice.jdbc.OrderServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DbPracticeApplicationTests {

    @Resource
    private OrderServiceImp orderService;

    @Test
    void contextLoads() {

    }

    @Test
    void testImportData(){
        orderService.importData();
    }


    @Test
    void testLoadData(){ orderService.loadData();}
}
