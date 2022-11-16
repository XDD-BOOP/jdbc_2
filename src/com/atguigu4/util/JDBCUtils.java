package com.atguigu4.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    /**
     * @Description 数据连接池只需一个就行
     * 使用C3P0的数据库连接技术
     */
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

    public static Connection getConnection1() {
        Connection conn = null;
        try {
            conn = cpds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }


    /**
     * 使用dbpc的数据库连接技术获取数据库连接
     * 静态代码块保证经创建
     */
    private static DataSource source;

    static {
        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            // 创建一个DBCP数据库连接池
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() {
        Connection conn = null;
        try {
            conn = source.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * @Description 使用 Druid 的数据库连接技术获取数据库连接
     * @return
     * @throws Exception
     */
    private static DataSource source1;

    static {
        try {
            Properties pros = new Properties();
            FileInputStream is = new FileInputStream(new File("src/druid.properties"));
            pros.load(is);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection3() {

        Connection conn = null;
        try {
            conn = source1.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driver = pros.getProperty("driver");

        //2.加载驱动
        Class.forName(driver);
        //3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static void colseResource(Connection conn, PreparedStatement ps) {
        /**
         * @Description 关闭资源
         */
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void colseResource(Connection conn, PreparedStatement ps, ResultSet rs) {
        /**
         * @Description 关闭资源
         */
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @param conn
     * @param ps
     * @param rs
     * @Description 使用 dbutils.jar 中提供的Dbutils工具类，实现资源的关闭
     */
    public static void colseResource1(Connection conn, PreparedStatement ps, ResultSet rs) {
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }
}
