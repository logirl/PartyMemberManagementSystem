package cc.logirl.pmms.servlet;

import cc.logirl.pmms.domain.User;
import cc.logirl.pmms.ioc.MainContainer;
import cc.logirl.pmms.json.JsonOperator;
import cc.logirl.pmms.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by xinxi on 2016/10/21.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    Logger logger = Logger.getLogger(LoginServlet.class.getName());
    private UserService userService = MainContainer.get("userService");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (Exception e) {
            throw new ConversionException(e);
        }
        if (user.validate()) {
            User user1 = userService.login(user);
            if (user1 != null) {
                request.getSession().setAttribute("user", user1);
                JsonOperator.renderJson("登录成功", request, response);
                return;
            }
        }
        JsonOperator.renderJson(0, "错误的用户名或密码", request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
