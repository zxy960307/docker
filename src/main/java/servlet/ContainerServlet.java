package servlet;

import factory.ServiceFactory;
import net.sf.json.JSONObject;
import utils.FileUtil;
import utils.GeneralUtil;
import utils.HttpClientUtil;
import utils.PropertyUtil;
import vo.Container;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 41463 on 2019/3/14.
 */
public class ContainerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/pages/errors.jsp"; // 定义错误页面
        //获取跳转值
        String status = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1);
        //跳转
        if(status != null) {
            if("createContainer".equals(status)) {
                 path = this.createContainer(req);
            }
            else if ("startContainer".equals(status)) {
                this.startContainer(req);
            }
            else if ("updateContainers".equals(status)) {
                this.updateContainers(req,resp);
                return;
            }
            else if ("getAllContainers".equals(status)) {
                path = this.getAllContainers(req,resp);
            }
            else if("removeContainer".equals(status)) {
                path = this.removeContainer(req);
            }
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    /**
     * 创建容器
     * @param req
     * @return
     */
    public String createContainer(HttpServletRequest req) {

        String msg = ""; //表示提示信息
        String url = ""; // 表示跳转路径
        boolean msgStatus = true;
        boolean alertFlag = true;

        //获取创建容器的类型
        String containerImage = req.getParameter("image");
        //获取容器名
        String name = req.getParameter("name");//暂时不处理
        //获取docker服务器ip地址
        String machineIp = req.getParameter("machine");
        //获取json文件路径
        String path = this.getClass().getResource("/container/json/" + containerImage + ".json").getPath();
        String machineUrl ="http://"+machineIp+"/containers/create";
        //docker服务器响应结果
        JSONObject response = HttpClientUtil.doPost(machineUrl, FileUtil.readJsonFile(path));

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
            container.setMachineIp(machineIp);
            try {   //调用工厂类方法，完成插入数据操作
                if (ServiceFactory.ContainerServiceInstance().createContainer(container)) {
                    msg = "创建容器成功！容器Id为：" + containerId;
                    msgStatus = true;
                } else {
                    msg = "容器信息数据库记录出错了:(";
                    msgStatus = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            msg = "容器创建失败了:(";
            msgStatus = false;
        }

        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        req.setAttribute("alertFlag",alertFlag);

        if(msgStatus)
        {
            return "/container/getAllContainers";
        }

        else
        {
            return "/pages/container/container_create.jsp";
        }

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
                    queryContainerByContainerId(containerId);
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
                            updateContainer(result);
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
    public void updateContainers(HttpServletRequest req ,HttpServletResponse resp) {


        //初始化
        boolean msgStatus = true;
        String msg = ""; //表示提示信息
        String url = ""; // 表示跳转路径
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        //获取数据库中所有已注册容器的container_id && status != 6
        Container[] containers;//存放查询结果
        List<Container> containersList = new ArrayList<>();
        try {
            containersList = Arrays.asList(ServiceFactory.ContainerServiceInstance().
                    getAllContainersByContainerId());
        }
        catch (Exception e) {
            e.printStackTrace();
            msg = "获取所有容器信息异常。";
            msgStatus = false;
        }
        containers = (Container[]) containersList.toArray();

        //与docker服务器通信，获取所有容器信息并进行处理
        String[] urlArray = new String[containers.length];//存放url
        int length = 0;
        for (Container container:containers) {
            urlArray[length] = "http://192.168.43.230:2375/containers/"+container.getContainerId()+"/json";
            length ++;
        }
        JSONObject[]  res = new JSONObject[containers.length];//将get结果放至JSONObject数组中
        for (int i =0;i<length;i++) {
            res[i] = HttpClientUtil.doGet(urlArray[i]);
        }

        //修改刷新的数据status等
        for (int i=0;i<containers.length;i++) {
            JSONObject state =  (JSONObject) res[i].get("State");
            String status = (String)state.get("Status");
            containers[i].setStatus(Integer.parseInt(PropertyUtil.getProperty(status)));
        }

        //修改数据库中内容
        for (int i =0;i<containers.length;i++) {
            try {
                boolean flag =ServiceFactory.ContainerServiceInstance().updateContainer(containers[i]);
                if (!flag) {
                    msg = "更新容器信息失败。";
                    msgStatus = false;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                msg = "更新容器信息异常。";
                msgStatus = false;
            }
        }

        try {
            resp.getWriter().write(msg);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        if (msgStatus == false) {
            return;
        }
        else {
            msg = "同Docker服务器更新数据成功";
            return;
        }
        //String jsonStr = "{\"msg\":\""+msg+"\"}";
        //JSONObject msgJson = new JSONObject();


    }

    /**
     * 分页获取所有容器信息
     * @param req
     * @return
     */
    public String getAllContainers(HttpServletRequest req,HttpServletResponse resp) {

        //调用updateContainers函数，更新所有数据
        this.updateContainers(req,resp);
        if((boolean)req.getAttribute("msgStatus") == false)
        {
            req.setAttribute("alertFlag",true);
            return "/pages/index.jsp";
        }

        //初始化
        boolean alertFlag = true;
        boolean msgStatus = true;//表示执行状态
        String msg = ""; //表示提示信息
        Integer currentPage = Integer.valueOf(1);
        Integer lineSize = Integer.valueOf(4);

        try {
           currentPage = Integer.valueOf(Integer.parseInt(req.getParameter("cp"))); } catch (Exception localException1) {
         }
        try {
           lineSize = Integer.valueOf(Integer.parseInt(req.getParameter("ls"))); } catch (Exception localException2) {
             }
             String keyWord = req.getParameter("kw");
             String column = req.getParameter("col");
             if (keyWord == null)
             {
               keyWord = "";
             }
             if (column == null)
             {
               column = "container_id";
             }

        //调用相关函数从数据库获取所有信息
        List<Container> containers = new ArrayList<>();
        Integer allRecorders = 0;
        try {
            containers = (List<Container>) ServiceFactory.ContainerServiceInstance().
                    getAllContainersPag(column, keyWord, currentPage, lineSize).get("containers");
            allRecorders=(Integer)ServiceFactory.ContainerServiceInstance().
                    getAllContainersPag(column, keyWord, currentPage, lineSize).get("counts");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "获取容器信息列表异常。";
            msgStatus = false;
        }
        //检查结果
        if (containers.size()<=0) {
            msg = "获取容器信息列表为空。";
            msgStatus = false;
        }

        //封装结果
        req.setAttribute("url", "/container/getAllContainers");
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("lineSize", lineSize);
        req.setAttribute("allContainers",containers);
        req.setAttribute("allRecorders",allRecorders);
        req.setAttribute("msg",msg);

        //定义跳转网页url
        if (msgStatus == true)
        {
            return "/pages/container/container_list.jsp";
        }
        else
        {
            req.setAttribute("alertFlag",alertFlag);
            return "/pages/index.jsp";
        }
    }

    //重启容器
    public String restartContainer(HttpServletRequest req) {
        return "";
    }

    //删除容器
    public String removeContainer(HttpServletRequest req) {

        //提示信息
        String msg = "";
        boolean alertFlag = false;

        //获取请求containerId和ip
        String containerId = req.getParameter("containerId");
        String machineIp = req.getParameter("machineIp");

        //与docker服务器通信，删除容器
        String removeUrl = "Http://"+machineIp+"/containers/"+containerId;
        JSONObject res = HttpClientUtil.doDelete(removeUrl);//通信结果
        if (res == null) {
            alertFlag = true;
            msg = "删除容器时与docker服务器通信异常。";
            System.out.println(msg);
            req.setAttribute("msg",msg);
            req.setAttribute("alertFlag",alertFlag);
            return "/pages/index.jsp";
        }

        //更改数据库status
        boolean sqlResult = true;
        int status = 6;
        try {
            sqlResult = ServiceFactory.ContainerServiceInstance().updateContainerStatus(status,containerId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (sqlResult == false) {
            alertFlag = true;
            msg = "删除容器时更改数据库记录失败。";
            System.out.println(msg);
            req.setAttribute("msg",msg);
            req.setAttribute("alertFlag",alertFlag);
            return "/pages/index.jsp";
        }

        return "/container/getAllContainers";
    }

    //暂停容器

    //取消暂停容器

    //同docker服务器更新某一容器

}
