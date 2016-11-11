package cc.logirl.pmms.listener;

import cc.logirl.dbhelper.DBHelper;
import cc.logirl.pmms.util.DBCPUtil;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 注册BeanUtils的类型转换组件
 */
@WebListener()
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initBeanUtils(); // 给BeanUtils注册自定义的类型转换器
        initDBHelper(); // 初始化DBHelper(传入数据源)
    }

    private void initDBHelper() {
        DBHelper.init(DBCPUtil.getDataSource());
    }

    private void initBeanUtils() {
        ConvertUtils.register(new Converter() {
            //type：目标类型
            //value：当前传入的值
            public Object convert(Class type, Object value) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                if (value instanceof String) {
                    //字符串转换为Date
                    String v = (String) value;
                    try {
                        return df.parse(v);
                    } catch (Exception e) {
                        throw new ConversionException(e);
                    }
                } else {
                    //Date转换为字符串
                    Date d = (Date) value;
                    return df.format(d);
                }
            }
        }, Date.class);
        ConvertUtils.register(new Converter() {
            //type：目标类型
            //value：当前传入的值
            public Object convert(Class type, Object value) {
                if (value instanceof String) {
                    //字符串转换为Date
                    String v = (String) value;
                    if (StringUtils.isEmpty(v)) {
                        return -1;
                    }
                    try {
                        return Integer.parseInt(v);
                    } catch (Exception e) {
                        throw new ConversionException(e);
                    }
                } else {
                    return value + "";
                }
            }
        }, Integer.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
