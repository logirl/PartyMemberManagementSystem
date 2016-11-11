package cc.logirl.pmms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by xinxi on 2016/10/22.
 */
@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {
    private FilterConfig filterConfig;

    public void init(FilterConfig arg0) throws ServletException {
        this.filterConfig = arg0;
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 获取配置过滤器参数encoding的值：如果没有，给定一个默认值
        String encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = "UTF-8";
        }
        // 设置POST请求方式的中文请求参数的编码
        request.setCharacterEncoding(encoding);
        // 设置响应输出时的编码，字符流和字节流
        response.setCharacterEncoding(encoding);// 字节流输出时通知客户端的解码码表
        response.setContentType("text/html;charset=" + encoding);// 字节流输出时通知客户端的解码码表；字符流输出时使用的码表及通知客户端解码用的码表

        MyHttpServletRequest mrequest = new MyHttpServletRequest(request);
        // 放行
        chain.doFilter(mrequest, response);
    }

    public void destroy() {

    }

}


class MyHttpServletRequest extends HttpServletRequestWrapper {

    public MyHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    // 只对get请求方式进行改写
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null)
            return value;
        // 得到请求方式
        String method = super.getMethod();
        if ("get".equalsIgnoreCase(method)) {
            try {
                value = new String(value.getBytes("ISO-8859-1"), super.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
}
