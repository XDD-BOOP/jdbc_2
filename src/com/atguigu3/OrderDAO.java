package com.atguigu3;

import com.atguigu2.bean.Order;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口用于规范Order表的常用操作
 */

public interface OrderDAO {
    /**
     * @Description 将order对象添加到数据库中
     * @param conn
     * @param order
     */
    void insert(Connection conn, Order order);

    /**
     * @Description 根据id删除数据库中的记录
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,int id);

    /**
     * @Description 针对于内存中的order对象，去修改数据表中指定的记录
     * @param conn
     * @param order
     */
    void update(Connection conn,Order order);

    /**
     * @Description 针对指定ID查询到对应的Order对象
     * @param conn
     * @param id
     * @return
     */
    Order getOrderById(Connection conn,int id);

    /**
     * @Description 查询表中的所有记录构成的集合
     * @param conn
     * @return
     */
    List<Order> getAll(Connection conn);
    /**
     * @Description 返回数据表中数据的条目数
     * @param conn
     * @return
     */
    Long getCount(Connection conn);
    /**
     * @Description 返回数据表中最大的生日
     * @param conn
     * @return
     */
    Date getMaxdate(Connection conn);
}
