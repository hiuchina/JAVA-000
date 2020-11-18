package com.example.demojdbc;


import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * 应用模块名称: <p>
 * 代码描述: <p>
 * Copyright: Copyright (C) 2020 , Inc. All rights reserved. <p>
 * Company: <p>
 *
 * @author xiaochun
 * @since 2020/11/18 11:28 下午
 */
@Repository
public class JdbcStudentDao {
    /**
     * 新增
     */
    public int addStudent() {
        int result = 0;
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.1.1.39:3306/123";
        String username = "root";
        String password = "root123";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "INSERT INTO tb_students(ID,NAME,AGE) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,1);
            pstmt.setString(2, "张三");
            pstmt.setInt(3, 30);
            result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs != null) {rs.close();};
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
            }
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public int delStudent(int id) {
        int result = 0;
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.1.1.39:3306/123";
        String username = "root";
        String password = "root123";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "DELETE FROM tb_students WHERE ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,1);
            result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs != null) {rs.close();};
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
            }
        }
    }

    /**
     * 更新
     * @param name
     * @param id
     * @return
     */
    public int updateStudent(String name,int id) {
        int result = 0;
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.1.1.39:3306/123";
        String username = "root";
        String password = "root123";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "UPDATE tb_students SET NAME=? WHERE ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,name);
            pstmt.setInt(2,id);
            result = pstmt.executeUpdate();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs != null) {rs.close();};
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
            }
        }
    }


    /**
     * 查询
     * @param id
     * @return
     */
    public void queryStudent(int id) {

        String driverClassName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://192.1.1.39:3306/123";
        String username = "root";
        String password = "root123";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            String sql = "SELECT ID,NAME,AGE FROM tb_students WHERE ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("查询到学生信息："+ rs.getInt("ID") +","+rs.getString("NAME")+","+ rs.getInt("AGE") );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(rs != null) {rs.close();};
                if(pstmt != null) {pstmt.close();};
                if(con != null) {con.close();};
            } catch (Exception e) {
            }
        }
    }

}
