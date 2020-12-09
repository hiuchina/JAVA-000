package com.example.shardingproxy.jdbc;

import com.example.shardingproxy.jdbc.IDbConnectionService;
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
@Service("shardingProxyConnectionService")
public class ShardingProxyConnectionServiceImp implements IDbConnectionService {

    /**
     * 获取数据库连接
     * @return
     * @throws Exception
     */
    @Override
    public Connection getDbConnection() throws Exception{
        Connection con = null;
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3307/order_db?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "123456";
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
