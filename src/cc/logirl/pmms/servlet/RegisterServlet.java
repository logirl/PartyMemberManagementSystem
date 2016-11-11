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

/**
 * Created by xinxi on 2016/10/22.
 */
@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = MainContainer.get("userService");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (Exception e) {
            throw new ConversionException(e);
        }
        if (user.validate()) {
            User user1 = userService.getUserByName(user.getName());
            // 检查用户名是否已存在
            if (user1 != null) {
                JsonOperator.renderJson(0, "用户名已存在", request, response);
                return;
            }
            // 执行注册
            userService.register(user);
            JsonOperator.renderJson("注册成功", request, response);
            return;
        } else {
            JsonOperator.renderJson(0, "无效的输入", request, response);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
