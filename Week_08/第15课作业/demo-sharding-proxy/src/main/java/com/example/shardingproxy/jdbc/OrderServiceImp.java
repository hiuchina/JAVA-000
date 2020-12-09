package com.example.shardingproxy.jdbc;

import com.example.shardingproxy.utils.IdWorkerUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;


/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/12/1 9:31 下午
 */
@Service("orderService")
public class OrderServiceImp implements IOrderService {

    @Resource
    private ShardingProxyConnectionServiceImp shardingProxyConnectionService;

    @Override
    public int addOrder(){
        int result = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        try{
            con = shardingProxyConnectionService.getDbConnection();
            String sql = "INSERT INTO tb_order(order_id,order_sn,order_sum_price,order_count,user_id,user_address_id,order_status) " +
                    " VALUES(?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            IdWorkerUtils idWorkerUtils = IdWorkerUtils.getInstance();
            pstmt.setLong(1,idWorkerUtils.createUUID()+new Random().nextInt(16));
            pstmt.setString(2, "20201209121212000232");
            pstmt.setBigDecimal(3, new BigDecimal("10.02"));
            pstmt.setInt(4, 5);
            pstmt.setInt(5, 2);
            pstmt.setInt(6, 2);
            pstmt.setInt(7, 1);

            result = pstmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public int delOrder(long order_id,long user_id) {
        int result = 0;
        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            con = shardingProxyConnectionService.getDbConnection();
            String sql = "DELETE FROM tb_order WHERE order_id = ? AND user_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1,order_id);
            pstmt.setLong(2,user_id);
            result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public int updateOrder(long order_id,int order_count,long user_id) {
        int result = 0;

        Connection con = null;
        PreparedStatement pstmt = null;

        try {

            con = shardingProxyConnectionService.getDbConnection();
            String sql = "UPDATE tb_order SET order_count=? WHERE order_id = ? AND user_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,order_count);
            pstmt.setLong(2,order_id);
            pstmt.setLong(3,user_id);

            result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void queryOrder(long order_id,long user_id) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = shardingProxyConnectionService.getDbConnection();
            String sql = "SELECT order_id,order_sn,order_sum_price FROM tb_order WHERE order_id = ? AND user_id=?";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = null;
            pstmt.setLong(1,order_id);
            pstmt.setLong(2,user_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("查询到订单信息："+ rs.getLong("order_id") +","+rs.getString("order_sn")+","+ rs.getBigDecimal("order_sum_price") );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
