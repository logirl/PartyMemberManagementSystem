package cc.logirl.pmms.util;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by xinxi on 2016/10/22.
 */
public class DBCPUtil {
    private static DataSource ds = null;
    static{
        try {
            InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties props = new Properties();
            props.load(in);
            ds = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static DataSource getDataSource(){
        return ds;
    }
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

