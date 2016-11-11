package cc.logirl.pmms.service;

import cc.logirl.pmms.domain.User;
import cc.logirl.pmms.ioc.Transactional;

/**
 * Created by xinxi on 2016/10/21.
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 根据用户名获取用户
     *
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 用户注册
     *
     * @param user
     */
    @Transactional
    void register(User user);
}
