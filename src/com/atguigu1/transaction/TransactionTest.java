package com.atguigu1.transaction;

import com.atguigu1.util.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 1. 什么叫数据库事务？
 * 一组逻辑操作单元,使数据从一种状态变换到另一种状态。
 * 一组逻辑操作单元：一个或多个DML操作（增删改）
 * 2.数据一但提交就不可回滚
 * 3.那些操作会导致数据自动提交？
 * DDL操作一旦执行，都会自动提交
 * >set autocommit = false 的方式对DDL失效
 * DML默认情况下，一旦执行，就会自动提交
 * >可以通过set autocommit = false 的方式取消DML操作的自动提交
 * 默认关闭数据库连接会自动提交
 */

public class TransactionTest {
    //****************未考虑数据库事务的转账操作******************

    /**
     * 针对于user_tabel
     * 演示AA转账BB1000块钱
     * update user_table set balance = balance - 100 where user = ‘AA’
     * update user_table set balance = balance + 100 where user = ‘BB’
     */
    @Test
    public void testUpdate() {
        String sql1 = "update user_table set balance = balance - 100 where user = ?";
        int aa = update(sql1, "AA");
        String sql2 = "update user_table set balance = balance + 100 where user = ?";
        int bb = update(sql2, "BB");
        System.out.println("转账成功！");
    }

    /**
     * 通用的增删改操作----version 1.0
     */
    public int update(String sql, Object... args) {//sql中的占位符的个数=可变形参的长度
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.预编译SQL语句，返回PreparedStatement实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                //注意参数声明错误！
                ps.setObject(i + 1, args[i]);
            }
            //4.执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.关闭资源
            JDBCUtils.colseResource(conn, ps);
        }
        return 0;
    }

    //****************考虑数据库事务的转账操作******************
    @Test
    public void testUpdateWithTransaction() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //取消数据的自动提交
            conn.setAutoCommit(false);
            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            int aa = update(conn, sql1, "AA");
            // 模拟网络异常
            System.out.println(10 / 0);
            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            int bb = update(conn, sql2, "BB");
            System.out.println("转账成功！");
            // 提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //3.回滚数据
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            //4.修改为自动提交数据
            //主要针对于使用数据库连接池的使用
            try {
                conn.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * 通用的增删改操作----version 2.0
     */
    public int update(Connection conn, String sql, Object... args) {//sql中的占位符的个数=可变形参的长度
        PreparedStatement ps = null;
        try {
            //1.预编译SQL语句，返回PreparedStatement实例
            ps = conn.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                //注意参数声明错误！
                ps.setObject(i + 1, args[i]);
            }
            //3.执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.关闭资源
            JDBCUtils.colseResource(null, ps);
        }
        return 0;
    }

    // *****************************************************
    //通用查询操作，用于返回数据表中的一条记录（Version 2.0，考虑事务）
    @Test
    public void testTransactionSelect() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        //获取当前连接的隔离级别
        System.out.println(conn.getTransactionIsolation());
        //取消自动提交
        conn.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user = ? ";
        User user = getInstance(conn, User.class, sql, "CC");
        System.out.println(user);


    }

    @Test
    public void testTransactionUpdate() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "update user_table set balance = ? where user = ?";
        update(conn, sql, 5000, "CC");
        Thread.sleep(15000);
        System.out.println("修改结束！");

    }

    public <T> T getInstance(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                //处理一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columvalue = rs.getObject(i + 1);
                    //获取每个列的列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    //给cust指定的某个属性赋值为columvalue.通过反射
                    Field filed = clazz.getDeclaredField(columnLabel);
                    filed.setAccessible(true);
                    filed.set(t, columvalue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(null, ps, rs);
        }
        return null;

    }
}
