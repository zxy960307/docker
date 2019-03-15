package servlet;

import net.sf.json.JSONObject;
import utils.File;
import utils.httpclient.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 41463 on 2019/3/14.
 */
public class ContainerServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取跳转值
        String status = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1);
        //跳转
        if(status != null) {
            if("createContainer".equals(status)){
                //path = this.createContainer(req);
            }
        }
    }

    /**
     * 创建容器
     * @param req
     * @return
     */
    public String createContainer(HttpServletRequest req) {
        //获取创建容器的类型
        String containerImage = req.getParameter("image");
        //获取json文件路径
        String path = this.getClass().getResource("/container/json/"+containerImage+".json").getPath();
        //docker服务器响应结果
        JSONObject response = HttpClient.doPost("",File.readJsonFile(path));

        //容器创建成功
        if (response != null) {
            //获取容器id

            //将创建数据写入数据库
        }

        //容器创建失败

        return "";
    }
}
