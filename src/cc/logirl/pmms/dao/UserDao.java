package cc.logirl.pmms.dao;

import cc.logirl.pmms.domain.User;

/**
 * Created by xinxi on 2016/10/21.
 */
public interface UserDao {
    /**
     * 根据用户名和密码查找用户
     *
     * @param user
     * @return
     */
    User findUserByNameAndPassword(User user);

    /**
     * 添加用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 根据姓名查找用户
     *
     * @param name
     * @return
     */
    User findUserByName(String name);
}
