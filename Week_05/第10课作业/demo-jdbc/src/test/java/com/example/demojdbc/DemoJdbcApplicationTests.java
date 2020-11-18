package com.example.demojdbc;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
class DemoJdbcApplicationTests {

    @Resource
    private JdbcStudentDao jsd;

    @Test
    void contextLoads() {
    }

    /*@Test
    void testAddMethod(){
        int result = jsd.addStudent();
        System.out.println("新增"+result +"个学生。");
    }*/

    @Test
    void testUpdateMethod(){
        int result = jsd.updateStudent("李四",1);
        System.out.println("更新"+ result +"个学生。");
    }

    /*@Test
    void testQueryMethod() throws SQLException {
       jsd.queryStudent(1);
    }

    @Test
    void testDelMethod(){
        int result = jsd.delStudent(1);
        System.out.println("删除"+ result +"个学生。");
    }*/


}
