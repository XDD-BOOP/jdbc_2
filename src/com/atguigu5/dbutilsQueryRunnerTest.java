package com.atguigu5;

import com.atguigu2.bean.Customers;
import com.atguigu4.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，它是对JDBC的简单封装，学习成本极低，并且使用dbutils能极大简化jdbc编码的工作量，同时也不会影响程序的性能。
 * 封装了针对于数据库的CRUD操作
 */
public class dbutilsQueryRunnerTest {
    //插入测试
    @Test
    public void testInserter() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";
            int count = runner.update(conn, sql, "ls", "13322421686", new Date(213545413L));
            System.out.println("添加了 " + count + " 条记录");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * @throws SQLException BeanHandler:是ResultSetHandler 接口的实现类，用于封装表中的一条记录
     * @Description 查询操作
     */
    @Test
    public void testQuery1() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id = ?";
            BeanHandler<Customers> handler = new BeanHandler<>(Customers.class);
            Customers query = runner.query(conn, sql, handler, 2);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * BeanListHandler:是ResultSetHandler 接口的实现类,用于封装表中的多条记录构成的集合
     *
     * @throws SQLException
     */
    @Test
    public void testQuery2() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id < ?";
            BeanListHandler<Customers> cblh = new BeanListHandler<>(Customers.class);
            List<Customers> list = runner.query(conn, sql, cblh, 5);
            list.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }

    }

    /**
     * @throws SQLException MapHandler:是ResultSetHandler 接口的实现类，对应表中的一条记录，
     *                      将字段名和字段值作为map中的key和value
     * @Description 查询操作
     */
    @Test
    public void testQuery3() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> query = runner.query(conn, sql, handler, 2);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * @throws SQLException MapListHandler:是ResultSetHandler 接口的实现类，对应表中的多条记录，
     *                      将字段名和字段值作为map中的key和value，将map添加到List中
     * @Description 查询操作
     */
    @Test
    public void testQuery4() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id < ?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> query = runner.query(conn, sql, handler, 10);
            query.forEach(System.out::println);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * ScalarHandler:是ResultSetHandler 接口的实现类，用于返回特殊需求
     */
    @Test
    public void testQuery5() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select count(*) from customers";
            ScalarHandler handler = new ScalarHandler();
            Object query = runner.query(conn, sql, handler);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    /**
     * 自定义了一个ResultSetHandler实现类
     */

    @Test
    public void testQuery7() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnection3();
            String sql = "select id,name,email,birth from customers where id = ?";
            ResultSetHandler<Customers> handler = new ResultSetHandler<Customers>() {
                @Override
                public Customers handle(ResultSet rs) throws SQLException {
                    // return new Customers(25,"lsssss","123456@qq.com",new Date(2255555L));
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Date birth = rs.getDate("birth");
                        Customers customer = new Customers(id, name, email, birth);
                        return customer;
                    }
                    return null;
                }
            };
            Customers query = runner.query(conn, sql, handler, 12);
            System.out.println(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }
}
