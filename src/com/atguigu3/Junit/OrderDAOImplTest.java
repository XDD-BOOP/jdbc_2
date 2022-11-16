package com.atguigu3.Junit;

import com.atguigu1.util.JDBCUtils;
import com.atguigu2.bean.Order;

import com.atguigu3.OrderDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class OrderDAOImplTest extends OrderDAOImpl {
    OrderDAOImpl dao = new OrderDAOImpl();

    @Test
    public void testTestInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Order order = new Order(3, "CC", new Date(54646161L));
            dao.insert(conn, order);
            System.out.println("插入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestDeleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            dao.deleteById(conn, 4);
            System.out.println("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestUpdate() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Order order = new Order(2, "DD", new Date(656565656L));
            dao.update(conn, order);
            System.out.println("修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestGetOrderById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Order order = dao.getOrderById(conn, 3);
            System.out.println(order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestGetAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Order> list = dao.getAll(conn);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestGetCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println("总个数为： " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

}