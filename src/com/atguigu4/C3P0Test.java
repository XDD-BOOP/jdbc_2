package com.atguigu4;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;

public class C3P0Test {
    //方式一：
    @Test
    public void testGetConnection() throws Exception {
        //获取C3P0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        //loads the jdbc driver
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://36.129.1.161:3308/test");
        cpds.setUser("root");
        cpds.setPassword("root");
        //通过设置相关参数，对数据库连接池进行相关管理
        //初始时数据库连接池的连接数
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        //关闭连接池(销毁C3P0连接池)
       // DataSources.destroy(cpds);
    }
    //方式二：使用配置文件：
    @Test
    public void testGetConnection1()throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }
}
