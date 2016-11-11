package cc.logirl.dbhelper;

import cc.logirl.dbhelper.exception.DBHelperException;
import cc.logirl.dbhelper.handler.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;

/**
 * Created by xinxi on 2016/10/21.
 */
public class DBHelper {
    private static DataSource ds = null;
    private static ThreadLocal<Connection> t1 = new ThreadLocal<>();
    private static DBHelper instance = new DBHelper();
    static final Logger logger = LoggerFactory.getLogger(DBHelper.class);

    private DBHelper() {
    }

    /**
     * 初始化DBHelper
     *
     * @param dataSource
     */
    public static void init(DataSource dataSource) {
        ds = dataSource;
    }

    /**
     * 获取DBHelper的实例
     *
     * @return
     */
    public static DBHelper getInstance() {
        return instance;
    }

    private static void checkInited() {
        if (ds == null) {
            throw new IllegalStateException("请先初始化DBHelper: DBHelper.init(DataSource ds)");
        }
    }


    /**
     * 从线程局部变量中获取数据库连接，线程局部变量中没有的话就先从数据源中获取然后放入线程局部变量中
     */
    private static Connection getConnection() {

        try {
            Connection conn = t1.get();
            if (conn == null) {
                checkInited();
                conn = ds.getConnection();
                t1.set(conn);
            }
            return conn;
        } catch (SQLException e) {
            throw new DBHelperException("获取数据库连接失败.", e);
        }
    }

    /**
     * 完成DML语句(insert delete update)
     *
     * @param sql    需要执行的DML语句
     * @param params 参数
     */
    public void update(String sql, Object[] params) {
        logger.debug("------->SQL : {}", sql);
        logger.debug("------------->Params : {}", Arrays.toString(params));
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean flag = true;
        try {
            // 尝试从当前线程中获得连接，如果当前线程中没有，则自己从连接池中获取
            // 如果从当前前程中获得，则不负责连接的释放
            conn = t1.get();
            if (conn == null) {
                checkInited();
                flag = false;
                conn = ds.getConnection();
            }
            stmt = conn.prepareStatement(sql);
            // 设置参数
            fillParameter(params, stmt);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DBHelperException(e);
        } finally {
            if (flag) {
                release(rs, stmt, null);// 释放资源
            } else {
                release(rs, stmt, conn);
            }
        }
    }

    /**
     * 完成DML语句(insert delete update)，注意不会释放数据库连接资源
     *
     * @param conn   数据库连接(不会释放)
     * @param sql    需要执行的DML语句
     * @param params 参数
     */
    public void update(Connection conn, String sql, Object[] params) {
        logger.debug("------->SQL : {}", sql);
        logger.debug("------------->Params : {}", Arrays.toString(params));
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            // 设置参数
            fillParameter(params, stmt);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DBHelperException(e);
        } finally {
            release(null, stmt, null);// 释放资源
        }
    }


    /**
     * 完成DQL语句(select)
     *
     * @param sql    需要执行的DQL语句
     * @param params 参数
     * @param rsh    结果集处理器
     * @return
     */
    public <T> T query(String sql, Object[] params, ResultSetHandler rsh) {
        logger.debug("------->SQL : {}", sql);
        logger.debug("------------->Params : {}", Arrays.toString(params));
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean flag = true;
        try {
            // 尝试从当前线程中获得连接，如果当前线程中没有，则自己从连接池中获取
            // 如果从当前前程中获得，则不负责连接的释放
            conn = t1.get();
            if (conn == null) {
                checkInited();
                flag = false;
                conn = ds.getConnection();
            }
            stmt = conn.prepareStatement(sql);
            // 设置参数
            fillParameter(params, stmt);
            rs = stmt.executeQuery();
            // 将结果集封装到对象中
            return (T) rsh.handler(rs);
        } catch (Exception e) {
            throw new DBHelperException(e);
        } finally {
            if (flag) {
                release(rs, stmt, null);// 释放资源
            } else {
                release(rs, stmt, conn);
            }
        }
    }

    /**
     * 完成DQL语句(select)，注意不会释放数据库连接资源
     *
     * @param conn   数据库连接
     * @param sql    需要执行的DQL语句
     * @param params 参数
     * @param rsh    结果集处理器
     * @return
     */
    public <T> T query(Connection conn, String sql, Object[] params, ResultSetHandler rsh) {
        logger.debug("------->SQL : {}", sql);
        logger.debug("------------->Params : {}", Arrays.toString(params));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            // 设置参数
            fillParameter(params, stmt);
            rs = stmt.executeQuery();
            // 将结果集封装到对象中
            return (T) rsh.handler(rs);
        } catch (Exception e) {
            throw new DBHelperException(e);
        } finally {
            release(rs, stmt, null);// 释放资源
        }
    }

    /**
     * 给SQL语句中的占位符设置参数
     */
    private void fillParameter(Object[] params, PreparedStatement stmt) throws SQLException {
        ParameterMetaData pmd = stmt.getParameterMetaData();
        int count = pmd.getParameterCount();// 得到sql语句中的占位符的个数
        if (count > 0) {
            // 有参数
            if (params == null || params.length < 1) {
                throw new IllegalArgumentException("The parameter is wrong.");
            }
            if (params.length != count) {// 参数个数不匹配
                throw new IllegalArgumentException("The parameter count is wrong.");
            }
            // 参数个数匹配
            for (int i = 0; i < count; i++) {
                stmt.setObject(i + 1, params[i]);
            }
        }
    }


    /**
     * 开启事务
     */
    public static void startTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DBHelperException("开启事务失败.", e);
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        try {
            Connection conn = t1.get();
            if (conn == null) {
                throw new IllegalStateException("事务回滚失败，你确定你开启了事务？");
            }
            conn.rollback();
        } catch (SQLException e) {
            throw new DBHelperException("事务回滚失败.", e);
        } finally {
            release0();
        }
    }

    /**
     * 提交事务
     */
    public static void commit() {
        try {
            Connection conn = t1.get();
            if (conn == null) {
                throw new IllegalStateException("事务提交失败，你确定你开启了事务？");
            }
            conn.commit();
        } catch (SQLException e) {
            throw new DBHelperException("提交事务失败.", e);
        } finally {
            release0();
        }
    }

    /**
     * 释放资源，但是你完全可以不调用此方法，在事务回滚或提交的时候，资源释放操作将自动完成
     */
    public static void release() {
        logger.info("您并不需要手动调用DBHelper的release()静态方法，他会在事务提交或回滚时自动调用");
        // 可能有人习惯于比如事务提交之后在finally块中手动释放资源，所以这个方法的存在仅仅是为了照顾一下他们的编程习惯
    }

    /**
     * 释放线程局部变量中的数据库连接（如果有的话）
     */
    private static void release0() {
        try {
            Connection conn = t1.get();
            if (conn != null) {
                conn.close();
                t1.remove();
            }
        } catch (SQLException e) {
            // 资源释放失败，但上层并不能为此做什么
            logger.error("资源释放失败." + e);
        }
    }

    /**
     * 释放资源
     */
    private static void release(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }
}
