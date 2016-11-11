package cc.logirl.pmms.ioc;

import cc.logirl.dbhelper.DBHelper;
import cc.logirl.pmms.service.InfoService;
import cc.logirl.pmms.service.UserService;
import cc.logirl.pmms.service.impl.InfoServiceImpl;
import cc.logirl.pmms.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xinxi on 2016/10/22.
 */
public class MainContainer {
    private static Map<String, Object> context = new HashMap<>();

    static {
        UserService userService = new UserServiceImpl();
        Object userServiceProxy = Proxy.newProxyInstance(userService.getClass().getClassLoader(),
                userService.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    return checkAndBeginTransaction(userService, method, args);
                });
        InfoService infoService = new InfoServiceImpl();
        Object infoServiceProxy = Proxy.newProxyInstance(infoService.getClass().getClassLoader(),
                infoService.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    return checkAndBeginTransaction(infoService, method, args);
                });
        context.put("userService", userServiceProxy);
        context.put("infoService", infoServiceProxy);
    }

    /**
     * 如果目标方法上标注了 @Transactional 注解，则为该方法启用事务支持
     *
     * @param infoService
     * @param method
     * @param args
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @see Transactional
     */
    private static Object checkAndBeginTransaction(Object infoService, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Transactional.class)) {
            try {
                DBHelper.startTransaction();
                Object ret = method.invoke(infoService, args);
                DBHelper.commit();
                return ret;
            } catch (InvocationTargetException e) {
                DBHelper.rollback();
                Throwable cause = e.getCause();
                if (cause != null) {
                    throw cause; // 目标方法本身抛出了异常
                }
                throw e;
            } catch (Exception e) {
                DBHelper.rollback();
                throw e;
            }
        }
        try {
            return method.invoke(infoService, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                throw cause; // 目标方法本身抛出了异常
            }
            throw e;
        }
    }

    public static <T> T get(String name) {
        return (T) context.get(name);
    }
}
