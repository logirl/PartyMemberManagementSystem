package cc.logirl.pmms.dao.impl;

import cc.logirl.dbhelper.DBHelper;
import cc.logirl.dbhelper.handler.BeanHandler;
import cc.logirl.pmms.dao.UserDao;
import cc.logirl.pmms.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xinxi on 2016/10/21.
 */
public class UserDaoImpl implements UserDao {
    private DBHelper dh = DBHelper.getInstance();
    static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findUserByNameAndPassword(User user) {
        logger.debug("查找用户：[{},{}]", user.getName(), user.getPassword());
        return dh.query("select * from user where name=? and password=?",
                new Object[]{user.getName(), user.getPassword()}, new BeanHandler(User.class));
    }

    @Override
    public void addUser(User user) {
        logger.debug("添加用户：[{},{}]", user.getName(), user.getPassword());
        dh.update("insert into user(name,password) values(?,?)", new Object[]{user.getName(), user.getPassword()});
    }

    @Override
    public User findUserByName(String name) {
        logger.debug("查找用户：[{}]", name);
        return dh.query("select * from user where name=?", new Object[]{name}, new BeanHandler(User.class));
    }
}
