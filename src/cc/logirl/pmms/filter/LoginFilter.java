package cc.logirl.pmms.filter;

import cc.logirl.pmms.json.JsonOperator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xinxi on 2016/10/21.
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/user/*", "/info/*"})
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getSession().getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            JsonOperator.renderJson(0, "登陆之后才能访问", request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
