package com.atguigu2.dao;

import com.atguigu1.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO:Data(base) access object
 * 封装了针对于数据表的通用的操作
 */

public abstract class BaseDAO {
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

    //通用查询操作，返回多条查询结果构成的集合（Version 2.0，考虑事务）

    public <T> List<T> getForList(Connection conn, Class<T> clazz, String sql, Object... args) {
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
            //创建集合对象
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                T t = clazz.newInstance();
                //处理一行数据中的每一个列,给t对象指定的属性赋值
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
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(null, ps, rs);
        }
        return null;
    }

    // 用于查询特殊值的查询方法
    public <E> E getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(null, ps, rs);
        }
        return null;
    }

}
