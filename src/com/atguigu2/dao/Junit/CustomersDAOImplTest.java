import com.atguigu4.util.JDBCUtils;
import com.atguigu2.bean.Customers;
import com.atguigu2.dao.CustomersDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomersDAOImplTest extends CustomersDAOImpl {
    CustomersDAOImpl dao = new CustomersDAOImpl();

    @Test
    public void testTestInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customers cust = new Customers(1, "chl", "3183179404", new Date(5646464164L));
            dao.insert(conn, cust);
            System.out.println("添加成功");
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
            dao.deleteById(conn, 30);

            System.out.println("删除成功");
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
            Customers cust = new Customers(18, "chl", "3183179404@qq.com", new Date(515465161L));
            dao.update(conn, cust);

            System.out.println("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestGetCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            Customers cust = dao.getCustomerById(conn, 18);
            System.out.println(cust);
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
            List<Customers> list = dao.getAll(conn);
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
            System.out.println("表中的记录数为： "+count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }

    @Test
    public void testTestGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);

            System.out.println("最大生日为： "+ maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.colseResource(conn, null);
        }
    }
}