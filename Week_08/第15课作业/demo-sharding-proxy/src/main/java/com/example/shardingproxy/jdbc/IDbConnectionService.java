package com.example.shardingproxy.jdbc;

import java.sql.Connection;

/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/12/1 9:55 下午
 */
public interface IDbConnectionService {
    /**
     * 获取jdbc连接
     * @return
     * @throws Exception
     */
    public Connection getDbConnection() throws Exception;
}
