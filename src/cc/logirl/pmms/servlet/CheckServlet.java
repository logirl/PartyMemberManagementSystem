package cc.logirl.pmms.servlet;

import cc.logirl.pmms.domain.User;
import cc.logirl.pmms.ioc.MainContainer;
import cc.logirl.pmms.service.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xinxi on 2016/10/22.
 */
@WebServlet(name = "CheckServlet", urlPatterns = "/check")
public class CheckServlet extends HttpServlet {
    private UserService userService = MainContainer.get("userService");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        if (StringUtils.isBlank(name)) {
            response.getWriter().write("false");
            return;
        }
        User user = userService.getUserByName(name);
        if (user != null) {
            response.getWriter().write("false");
            return;
        } else {
            response.getWriter().write("true");
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
