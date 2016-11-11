package cc.logirl.pmms.servlet;

import cc.logirl.pmms.domain.Info;
import cc.logirl.pmms.ioc.MainContainer;
import cc.logirl.pmms.json.JsonOperator;
import cc.logirl.pmms.page.Page;
import cc.logirl.pmms.service.InfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xinxi on 2016/10/22.
 */
@WebServlet(name = "InfoServlet", urlPatterns = "/info/*")
public class InfoServlet extends HttpServlet {

    private InfoService infoService = MainContainer.get("infoService");


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith(".jsp")) {
            request.getRequestDispatcher("/WEB-INF" + requestURI).forward(request, response);
            return;
        }
        String operation = requestURI.substring("/info/".length());
        switch (operation) {
            case "allinfos":
                allinfos(request, response);
                break;
            case "checkStudentNumber":
                checkStudentNumber(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "toModify":
                toModify(request, response);
                break;
            case "modify":
                modify(request, response);
                break;
            case "getPage":
                getPage(request, response);
                break;
            case "batchDelete":
                batchDelete(request, response);
                break;
            default:
                break;
        }
    }

    /**
     * 批量删除
     *
     * @param request
     * @param response
     */
    private void batchDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("ids");
        infoService.batchDelete(ids);
        JsonOperator.renderJson("删除成功", request, response);
    }

    /**
     * 分页
     *
     * @param request
     * @param response
     */
    private void getPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagenum = request.getParameter("pagenum");
        if (StringUtils.isBlank(pagenum)) {
            pagenum = "1";
        }
        Page<Info> page = infoService.findPageReocrds(pagenum);
        JsonOperator.renderJson(page, request, response);
    }

    /**
     * 修改
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void modify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Info info = new Info();

        try {
            BeanUtils.populate(info, request.getParameterMap());
        } catch (Exception e) {
            JsonOperator.renderJson(0, "无效的输入", request, response);
            return;
        }

        if (!info.validate()) {
            JsonOperator.renderJson(0, "无效的输入", request, response);
            return;
        }
        infoService.modifyInfo(info);
        JsonOperator.renderJson("修改成功", request, response);
    }

    /**
     * 去向修改页面
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void toModify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            JsonOperator.renderJson(0, "无效的操作", request, response);
        }
        Info info = infoService.getInfoById(id);
        request.setAttribute("info", info);
        request.getRequestDispatcher("/WEB-INF/info/toModify.jsp").forward(request, response);
    }

    /**
     * 删除党员信息
     *
     * @param request
     * @param response
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            JsonOperator.renderJson(0, "无效的操作", request, response);
        }
        infoService.deleteInfoById(id);
        JsonOperator.renderJson("删除成功", request, response);
    }

    /**
     * 添加党员信息
     *
     * @param request
     * @param response
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Info info = new Info();

        try {
            BeanUtils.populate(info, request.getParameterMap());
        } catch (Exception e) {
            JsonOperator.renderJson(0, "无效的输入", request, response);
            return;
        }

        if (!info.validate()) {
            JsonOperator.renderJson(0, "无效的输入", request, response);
            return;
        }
        infoService.addInfo(info);
        JsonOperator.renderJson("添加成功", request, response);
    }

    /**
     * 添加党员信息时，检查学号是否已存在
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void checkStudentNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentNumber = request.getParameter("studentNumber");
        PrintWriter out = response.getWriter();
        try {
            // 有可能转换会出异常，在下面一并返回false
            int num = Integer.parseInt(studentNumber);
            Info info = infoService.getInfoByStudentNumber(num);
            if (info == null) {
                out.write("true");//只有这一种情况是true，其他情况都是false
                return;
            }
        } catch (Exception e) {
            out.write("false");
            return;
        }
        out.write("false");
        return;
    }

    /**
     * 获取所有的Info信息
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void allinfos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagenum = request.getParameter("pagenum");//用户要看的页码
        if (StringUtils.isEmpty(pagenum)) {
            pagenum = "1";
        }
        Page page = infoService.findPageReocrds(pagenum);
        JsonOperator.renderJson(page, request, response);
    }
}
