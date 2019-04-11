package servlet;

import factory.ServiceFactory;
import utils.GeneralUtil;
import vo.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 41463 on 2019/3/14.
 */
public class LoginServlet extends HttpServlet{
    String loginJspUrl = "/pages/login/login.jsp";
    String fordwardJspUrl = "/pages/forward.jsp";

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

        //初始化
        String msg = ""; //表示提示信息
        String url = "";//跳转路径
        boolean msgStatus = true;

        //获取参数
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        //检查是否为空
        if (GeneralUtil.isStrEmpty(userName)||GeneralUtil.isStrEmpty(password)) {
            msg = "用户名或密码不允许为空。";
            msgStatus = false;
            url = loginJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return fordwardJspUrl;
        }

        //从数据库中检索该用户名下的相关数据记录
        Admin admin;
        try {
            admin = ServiceFactory.LoginServiceInstance().findByName(userName);
        } catch (Exception e) {
            msg = "登录时异常。";
            msgStatus = false;
            url = loginJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return fordwardJspUrl;
        }
        if (admin == null) {
            msg = "未查询到该用户名。";
            msgStatus = false;
            url = loginJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return fordwardJspUrl;
        }

        //判断密码是否正确
        if(!admin.getPassword().equals(password)) {
            msg = "密码不正确。";
            msgStatus = false;
            url = loginJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return fordwardJspUrl;
        }
        //保存session
        HttpSession adminSession = req.getSession();
        admin.setPassword("");
        adminSession.setAttribute("admin",admin);

        //登录成功
        msg = "登录成功！";
        msgStatus = true;
        url = "/pages/index.jsp";
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        req.setAttribute("url",url);
        return fordwardJspUrl;
    }

    /**
     * 管理员注销登录
     * @param req
     * @return
     */
    public String loginOut(HttpServletRequest req) {

        //初始化
        String msg = ""; //表示提示信息
        String url = "/pages/login/login.jsp";//跳转路径
        boolean msgStatus = true;
        req.setAttribute("url",url);

        //销毁session
        HttpSession session = req.getSession();
        session.invalidate();

        //退出系统
        msg = "已安全退出系统。";
        req.setAttribute("msgStatus",msgStatus);
        req.setAttribute("msg",msg);
        return fordwardJspUrl;
    }
}
