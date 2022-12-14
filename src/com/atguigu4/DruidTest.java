package com.atguigu4;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;


public class DruidTest {
    @Test
    public void testGetConnection() throws Exception {
        Properties pros = new Properties();
        // ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
        FileInputStream is = new FileInputStream(new File("src/druid.properties"));
        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
