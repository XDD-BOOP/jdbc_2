package com.atguigu2.dao;

import com.atguigu2.bean.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口用于规范针对与Customer表的常用操作
 */
public interface CustomerDAO {
    /**
     * @param conn
     * @param cust
     * @Description 将 cust对象添加到数据库中
     */
    void insert(Connection conn, Customers cust);

    /**
     * @param conn
     * @param id
     * @Description 根据指定的ID删除对应的记录
     */
    void deleteById(Connection conn, int id);

    /**
     * @param conn
     * @param cust
     * @Description 针对于内存中的cust对象，去修改数据表中指定的记录
     */
    void update(Connection conn, Customers cust);

    /**
     * @param conn
     * @param id
     * @Description 针对指定ID查询到对应的Customers对象
     */
    Customers getCustomerById(Connection conn, int id);

    /**
     * @param conn
     * @return
     * @Description 查询表中的所有记录构成的集合
     */
    List<Customers> getAll(Connection conn);

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
    Date getMaxBirth(Connection conn);

}
