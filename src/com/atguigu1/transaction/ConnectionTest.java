package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import com.atguigu2.bean.Customers;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnectionTest {
    @Test
    public void testConnection() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select id,name,email,birth from customers where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,5);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String email = rs.getString(3);
            Date birth = rs.getDate(4);
            Customers customers = new Customers(id, name, email, birth);
            System.out.println(customers);
        }
        JDBCUtils.colseResource(conn,ps);
    }
}
