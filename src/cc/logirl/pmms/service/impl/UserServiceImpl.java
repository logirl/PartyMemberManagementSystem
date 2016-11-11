package cc.logirl.pmms.service.impl;

import cc.logirl.pmms.dao.UserDao;
import cc.logirl.pmms.dao.impl.UserDaoImpl;
import cc.logirl.pmms.domain.User;
import cc.logirl.pmms.service.UserService;

/**
 * Created by xinxi on 2016/10/21.
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(User user) {
        return userDao.findUserByNameAndPassword(user);
    }

    @Override
    public User getUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    public void register(User user) {
        userDao.addUser(user);
    }
}
