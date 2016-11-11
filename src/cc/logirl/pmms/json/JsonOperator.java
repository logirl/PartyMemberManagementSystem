package cc.logirl.pmms.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by xinxi on 2016/10/23.
 */
public class JsonOperator {
    public static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public static String toJson(Object src) {
        return toJson(1, src);
    }

    public static String toJson(int code, Object src) {
        JsonResult jr = new JsonResult();
        jr.setCode(code);
        jr.setData(src);
        return GSON.toJson(jr);
    }

    public static void renderJson(Object src, ServletRequest req, ServletResponse resp) throws IOException {
        renderJson(1, src, req, resp);
    }

    public static void renderJson(int code, Object src, ServletRequest req, ServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=" + req.getCharacterEncoding());
        resp.getWriter().write(toJson(code, src));
    }
}
