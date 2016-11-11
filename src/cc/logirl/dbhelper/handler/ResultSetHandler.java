package cc.logirl.dbhelper.handler;

import java.sql.ResultSet;

/**
 * Created by xinxi on 2016/10/21.
 * <p>
 * 所有的结果集处理器需实现此接口，当然可以通过实现此接口来自定义结果集处理器
 */
public interface ResultSetHandler {
    /**
     * 封装结果集
     *
     * @param rs
     * @return
     */
    Object handler(ResultSet rs);
}
