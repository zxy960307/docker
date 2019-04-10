package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 41463 on 2019/3/14.
 */
public class LoginServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/pages/errors.jsp"; // 定义错误页面
        //获取跳转值
        String status = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1);
        //跳转
        if(status != null) {
            if("doLogin".equals(status)) {
                path = this.doLogin(req);
            }
            else if ("loginOut".equals(status)) {
                path = this.loginOut(req);
            }
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    /**
     * 管理员登录
     * @param req
     * @return
     */
    public String doLogin(HttpServletRequest req) {
        return "";
    }

    /**
     * 管理员注销登录
     * @param req
     * @return
     */
    public String loginOut(HttpServletRequest req) {
        return "";
    }
}
