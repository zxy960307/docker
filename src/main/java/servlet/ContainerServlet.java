package servlet;

import dao.IContainerDao;
import factory.ServiceFactory;
import net.sf.json.JSONObject;
import service.Impl.ContainerServiceImpl;
import utils.FileUtil;
import utils.GeneralUtil;
import utils.HttpClientUtil;
import vo.Container;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            if("createContainer".equals(status)) {
                 this.createContainer(req);
            }
            else if ("startContainer".equals(status)) {
                this.startContainer(req);
            }
            else if ("updateContainers".equals(status)) {
                this.updateContainers(req);
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
        JSONObject response = HttpClientUtil.doPost("http://192.168.43.230:2375/containers/create", FileUtil.readJsonFile(path));

        //容器创建成功
        if (response != null) {
            //获取容器id
            //System.out.println(response.get("0").toString());
            String containerId = response.getString("Id").substring(0, 12);

            //将创建数据写入数据库
            Container container = new Container();//定义Container对象
            container.setContainerId(containerId);
            container.setCreateAdminId(1);//默认管理员id为1
            container.setImage(containerImage);//设置容器镜像
            container.setStatus(0);
            try {   //调用工厂类方法，完成插入数据操作
                if (ServiceFactory.ContainerServiceInstance().createContainer(container)) {
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

    /**
     * 启动一个不在运行状态的容器
     * @param req
     * @return
     */
    public String startContainer(HttpServletRequest req) {

        String msg = ""; //表示提示信息
        String url = ""; // 表示跳转路径

        //获取请求参数并检查数据
        String containerId = req.getParameter("containerId");
        String machineIp = req.getParameter("machineIp");
        if (GeneralUtil.isStrEmpty(containerId)||
                GeneralUtil.isStrEmpty(machineIp)) {
            msg = "容器id不能为空";
            url = "";
            return "";
        }

        //获取容器状态status
        int status = -1;
        Container result = null;
        try {
            result = ServiceFactory.ContainerServiceInstance().
                    queryContainerStatusByContainerId(containerId);
            status = result.getStatus();
            if (result == null ) {
                msg = "容器信息未查询到。";
                url = "";
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "容器信息查询异常。";
            url = "";
            return "";
        }

        //判断容器是否为create状态，并启动容器
        if (status == 0) {
            //发送start容器的Post请求
            String machineUrl = "http://"+machineIp+":2375/containers/"+containerId+"/start";
            JSONObject response = HttpClientUtil.doPost(machineUrl);
            if (response == null) {
                msg = "与Docker服务通信异常，启动容器失败!";
                url = "";
                return "";
            }
            else {
                //改变数据库中该容器status信息
                result.setStatus(1);//修改status为1，表示正在运行
                boolean updateStatusResult = false;
                try {
                    updateStatusResult = ServiceFactory.ContainerServiceInstance().
                            updateContainerStatus(result);
                } catch (Exception e) {
                    msg = "数据库更新容器状态信息异常。";
                    url = "";
                    return "";
                }
                //判断是否更新成功
                if (updateStatusResult) {
                    msg = "容器状态更新成功。";
                    url = "";
                    return "";
                }

            }

        }
        //容器在其他状态，不能启动
        else {
            msg = "容器处于xx状态，不能启动。";
            url = "";
            return "";
        }
        return "";
    }

    /**
     * 更新所有容器信息,并更新数据库
     * @param req
     * @return
     */
    public String updateContainers(HttpServletRequest req) {


        //初始化
        String msg = ""; //表示提示信息
        String url = ""; // 表示跳转路径

        //获取数据库中所有已注册容器container_id
        String containerID[];
        try {
            containerID = ServiceFactory.ContainerServiceInstance().getAllContainersContainerId();
        }
        catch (Exception e) {
            e.printStackTrace();
            msg = "获取所有容器信息异常。";
            url = "";
            return "";
        }

        //与docker服务器通信，获取所有容器信息并进行处理
        String[] urlArray = new String[containerID.length];
        int length = 0;
        for (String container:containerID) {
            urlArray[length] = "http://192.168.43.230:2375/containers/"+container+"/json";
            length ++;
        }

        Map<String,Object> containersInfo = new HashMap<>();
        for (int i =0;i<length;i++) {
            JSONObject result = HttpClientUtil.doGet(urlArray[i]);

            containersInfo.put(containerID[i],result);
        }

        //修改数据库中内容

        return "";
    }
}
