package com.atguigu3;

import com.atguigu2.bean.Customers;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomersDAOImpl extends BaseDAO <Customers> implements CustomerDAO {

    @Override
    public void insert(Connection conn, Customers cust) {
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customers cust) {
        String sql = "update customers set name = ?,email = ?,birth = ? where id =?";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth(), cust.getId());
    }

    @Override
    public Customers getCustomerById(Connection conn, int id) {
        String sql = "select id,name,email,birth from customers where id = ?";
        Customers customers = getInstance(conn,sql,id);
        return customers;
    }

    @Override
    public List<Customers> getAll(Connection conn) {
        String sql = "select name,email,birth from customers";
        List<Customers> List = getForList(conn,sql);
        return List;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers ";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }
}
