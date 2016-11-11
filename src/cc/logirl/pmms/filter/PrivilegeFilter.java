package cc.logirl.pmms.filter;

import cc.logirl.pmms.domain.User;
import cc.logirl.pmms.json.JsonOperator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xinxi on 2016/10/26.
 */
@WebFilter(filterName = "PrivilegeFilter", urlPatterns = "/info/*")
public class PrivilegeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 管理员才能进行删除操作
        if (request.getRequestURI().toLowerCase().contains("delete")) {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null || user.getFlag() != 1) {
                JsonOperator.renderJson(0, "无效的操作", request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
