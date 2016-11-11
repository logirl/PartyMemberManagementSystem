package cc.logirl.dbhelper.handler;

import cc.logirl.dbhelper.exception.DBHelperException;

import java.sql.ResultSet;


/**
 * Created by xinxi on 2016/10/21.
 * <p>
 * 前提：返回的结果集中只有一行一列
 * 注意：该处理器只适合结果集中只有一行一列的情况
 */
public class ScalarHandler implements ResultSetHandler {

    @Override
    public Object handler(ResultSet rs) {
        try {
            if (rs.next()) {
                return rs.getObject(1);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DBHelperException(e);
        }
    }

}
