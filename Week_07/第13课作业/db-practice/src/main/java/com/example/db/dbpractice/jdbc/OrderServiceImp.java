package com.example.db.dbpractice.jdbc;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;



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
    private MysqlDbConnectionServiceImp dbConnectionService;

    /**
     * 导入100万订单数据
     */
    @Override
    public void importData(){
        try{
            long startTime = System.currentTimeMillis();
            Connection con = dbConnectionService.getDbConnection();
            if(con != null){
                con.close();
                String lineTxt = null;
                BufferedReader br = null;
                String sql = "";
                String preSql="INSERT INTO tb_order(order_sn,order_sum_price,order_count,user_id,user_address_id,order_status) VALUES";
                StringBuilder sb = null;
                int index= 1;
                for (int i = 1; i < 11; i++) {
                    try {
                        br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("/Users/xiaochun/study/python/orderData100w_"+i+".txt")),
                                "UTF-8"));
                        lineTxt = null;
                        index = 1;
                        sb = new StringBuilder(preSql);
                        while ((lineTxt = br.readLine()) != null) {
                            String[] cols = lineTxt.split(",");
                            sb.append("(");
                            for (int j = 0; j < cols.length; j++) {
                                if(j == (cols.length -1)){
                                    sb.append(cols[j]+"),");
                                }else{
                                    sb.append(cols[j]+",");
                                }
                            }
                            if(index % 500 == 0){
                                //500条入一次库
                                sql= sb.toString().substring(0,sb.toString().length()-1);
                                System.out.println(i+",index="+index+",sql="+sql);
                                System.out.println(i+"=入库条数="+insertData(sql)+"，index="+index);
                                sb = new StringBuilder(preSql);
                            }
                            index++;
                        }

                    } catch (Exception e) {
                        System.err.println("read errors :" + e);
                    }

                }
                long endTime = System.currentTimeMillis();
                long durTime = endTime-startTime;
                System.out.println("startTime="+startTime+"，endTime="+endTime+",durTime="+durTime);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 导入100万订单数据
     * 通过mysql数据库自带的 load data 命令
     */
    @Override
    public void loadData(){
        long startTime = System.currentTimeMillis();
        String filePath="";
        for (int i = 1; i < 11; i++) {
            filePath = "/opt/orderdata/orderData100w_"+i+".txt";
            loadfileData(filePath);
        }

        long endTime = System.currentTimeMillis();
        long durTime = endTime-startTime;
        System.out.println("startTime2="+startTime+"，endTime2="+endTime+",durTime2="+durTime);
    }


    /**
     * 入库数据
     * @param sqlinfo
     * @return
     */
    private int insertData(String sqlinfo){
        Connection con = null;
        try{
            con = dbConnectionService.getDbConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        int result =0;
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sqlinfo);
            result = pstmt.executeUpdate();
        }catch (Exception e){
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


    /**
     * 通过文件导入数据库（load data）
     */
    private void loadfileData(String filePath){
        Connection con = null;
        try{
            con = dbConnectionService.getDbConnection();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        int result =0;
        String sql="LOAD DATA INFILE '" +filePath+"' "+
                "INTO TABLE tb_order2  FIELDS TERMINATED BY ',' " + "LINES TERMINATED BY '\\n' " +
                " (order_sn,order_sum_price,order_count,user_id,user_address_id,order_status) ";

        System.out.println("sql="+sql);

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(sql);
            result = pstmt.executeUpdate();
        }catch (Exception e){
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
