package servlet;

import factory.ServiceFactory;
import net.sf.json.JSONObject;
import service.Impl.ContainerServiceImpl;
import utils.FileUtil;
import utils.HttpClientUtil;
import vo.Container;

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
                 this.createContainer(req);
            }
        }
        //req.getRequestDispatcher(path).forward(request,response);
    }

    /**
     * 创建容器
     * @param req
     * @return
     */
    public String createContainer(HttpServletRequest req) {

        String msg = ""; //表示提示信息
        String url = ""; // 表示跳转路径

        //获取创建容器的类型
        String containerImage = req.getParameter("image");
        //获取json文件路径
        String path = this.getClass().getResource("/container/json/" + containerImage + ".json").getPath();
        //docker服务器响应结果
        JSONObject response = HttpClientUtil.doPost("", FileUtil.readJsonFile(path));

        //容器创建成功
        if (response != null) {
            //获取容器id
            //System.out.println(response.get("0").toString());
            String containerId = response.getString("Id").substring(0, 12);

            //将创建数据写入数据库
            Container container = null;//定义Container对象
            container.setContainerId(containerId);
            container.setCreateAdminId("1");//默认管理员id为1
            container.setImage(containerImage);//设置容器镜像
            container.setStatus(0);
            try {   //调用工厂类方法，完成插入数据操作
                if (ServiceFactory.createContainerServiceInstance().createContainer(container)) {
                    msg = "创建容器成功！容器Id为：" + containerId;
                    url = "/pages/back/index.jsp";
                } else {
                    msg = "容器信息数据库记录出错了:(";
                    url = "/pages/back/index.jsp";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            msg = "容器创建失败了:(";
            url = "/pages/back/index.jsp";
        }

        req.setAttribute("msg",msg);
        req.setAttribute("url",url);
        System.out.println(msg);
        return "";
    }
}
