package cc.logirl.pmms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by xinxi on 2016/10/22.
 */
@WebFilter(filterName = "ExceptionFilter", urlPatterns = "/*")
public class ExceptionFilter implements Filter {
    static final Logger logger = LoggerFactory.getLogger(ExceptionFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, resp);
        } catch (Throwable e) {
            Map<String, String[]> params = req.getParameterMap();
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                sb.append("[" + entry.getKey() + " : " + Arrays.toString(entry.getValue()) + "]");
            }
            logger.error("请求路径：{}", ((HttpServletRequest) req).getRequestURI());
            logger.error("请求参数：{}", sb);
            logger.error("用户信息：{}", ((HttpServletRequest) req).getSession().getAttribute("user"));
            logger.error("异常信息：{}", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
