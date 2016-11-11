package cc.logirl.dbhelper.handler;

import cc.logirl.dbhelper.exception.DBHelperException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Created by xinxi on 2016/10/21.
 * <p>
 * 前提：JavaBean中的字段与数据库表中的字段一致
 * 注意：该处理器只适合结果集中只有一条记录的情况
 */
public class BeanHandler implements ResultSetHandler {
    private Class clazz;

    public BeanHandler(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object handler(ResultSet rs) {
        try {
            if (rs.next()) {
                Object bean = clazz.newInstance();//目标JavaBean实例
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();//返回记录列数
                for (int i = 0; i < count; i++) {
                    String columnName = rsmd.getColumnName(i + 1);//列名
                    Object columnValue = rs.getObject(i + 1);//列值
                    //通过反射为对象字段设置值
                    Field f = null;//对象字段与数据库表中字段一致
                    try {
                        f = clazz.getDeclaredField(columnName);
                    } catch (NoSuchFieldException e) {
                        // bean中没有字段对应数据库表中的列，直接跳过
                        continue;
                    }
                    f.setAccessible(true);
                    f.set(bean, columnValue);
                }
                return bean;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new DBHelperException(e);
        }
    }

}
