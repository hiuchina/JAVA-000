package com.example.db.dbpractice.jdbc;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/12/1 9:32 下午
 */
@Service("dbConnectionService")
public class MysqlDbConnectionServiceImp implements IDbConnectionService {

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    @Override
    public Connection getDbConnection() throws Exception{
        Connection con = null;
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://XXXX:63306/123";
        String username = "root";
        String password = "root123";
        try{
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            try{
                if(con != null) {
                    con.close();
                }
            }catch (Exception ex){}
            throw new RuntimeException(e);
        }

        return con;
    }

}
