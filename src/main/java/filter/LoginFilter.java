package filter;

import vo.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 41463 on 2019/4/11.
 */

@WebFilter(filterName="LoginFilter", urlPatterns="/*")
public class LoginFilter implements Filter
{

    //不被拦截的请求路径
    private static final List<String> unFilterUrlList = Arrays.
            asList("/pages/login/login.jsp","/login/doLogin","/login/loginOut","/pages/forward.jsp"
            ,"http://localhost:8081/","http://localhost:8081/docker");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 判断请求是否需要拦截
     * @param url
     * @return 若请求不需要拦截，返回flase;请求需要拦截时，返回true
     */
    private boolean isFilter(String url){
        for(String s: unFilterUrlList) {
            if(url.indexOf(s) > -1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
        if(isFilter(hrequest.getRequestURL().toString())) {
            if (isLogin(hrequest)) {
                filterChain.doFilter(servletRequest,servletResponse);
            }
            else {
                hrequest.setAttribute("msg", "游客请登录！");
                hrequest.setAttribute("url", "/pages/login/login.jsp");
                hrequest.getRequestDispatcher("/pages/forward.jsp").forward(servletRequest, servletResponse);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }

    public boolean isLogin(HttpServletRequest req) {
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (admin == null) {
            return false;
        }
        else
            return true;
    }
}
