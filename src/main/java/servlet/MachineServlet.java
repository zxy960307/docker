package servlet;

import factory.ServiceFactory;
import utils.GeneralUtil;
import utils.HttpClientUtil;
import vo.Machine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 41463 on 2019/4/1.
 */
public class MachineServlet  extends HttpServlet {

    public String forwardJspUrl = "/pages/forward.jsp";//提示url
    public String allMachinesSplitServletUrl = "/machine/getAllMachinesSplit";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = "/pages/errors.jsp"; // 定义错误页面
        //获取跳转值
        String status = req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/")+1);
        //跳转
        if(status != null) {
            if("createMachine".equals(status)) {
                path = this.createMachine(req);
            } else if("getAllMachinesSplit".equals(status)) {
                path = this.getAllMachinesSplit(req,resp);
            }
            else if("deleteMachine".equals(status)) {
                path = this.deleteMachine(req);
            }
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    /**
     * 创建机器
     * @param req
     * @return
     */
    public String createMachine(HttpServletRequest req) {

        //初始化
        String url = "";//跳转至机器分页信息
        boolean msgStatus = true;
        String msg = "";
        String createJspUrl = "/pages/machine/machine_create.jsp";


        //获取参数
        String name = req.getParameter("name");
        String ip = req.getParameter("ip");
        if (GeneralUtil.isStrEmpty(name) == true || GeneralUtil.isStrEmpty(ip)) {   //参数未空时
            msgStatus = false;
            msg = "创建机器时参数未填写完整";
            url = createJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return forwardJspUrl;
        }

        //调用相关service函数，向数据库插入数据
        //包装machine
        Machine machine = new Machine();
        machine.setName(name);
        machine.setIp(ip);
        machine.setStatus(1);
        boolean createResult = false;
        try {
            createResult = ServiceFactory.MachineServiceInstance().createMachine(machine);
        } catch (Exception e) {
            e.printStackTrace();
            msgStatus = false;
            msg = "创建机器时异常。";
            url = "/pages/index.jsp";
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return forwardJspUrl;
        }
        if (createResult == false) {
            msgStatus = false;
            msg = "创建机器失败。";
            url = createJspUrl;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            req.setAttribute("url",url);
            return forwardJspUrl;
        }


        msgStatus = true;
        msg = "创建机器成功。";
        url = createJspUrl;
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        req.setAttribute("url",url);
        return forwardJspUrl;
    }

    /**
     * 删除机器
     * @param req
     * @return
     */
    public String deleteMachine(HttpServletRequest req) {

        //初始化
        String url = allMachinesSplitServletUrl;
        String msg = "";
        boolean msgStatus = true;
        req.setAttribute("url",url);

        //获取参数
        int machineId = Integer.parseInt(req.getParameter("id"));
        if (machineId == 0) {
            msgStatus = false;
            msg = "删除机器时machine_id不合法。";
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }

        //调用service从数据库中删除记录
        boolean deleteResult = false;
        try {
            deleteResult = ServiceFactory.MachineServiceInstance().deleteMachineById(machineId);
        } catch (Exception e) {
            msgStatus = false;
            msg = "删除机器时数据库操作异常。";
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }
        if (deleteResult == false) {
            msgStatus = false;
            msg = "删除机器时数据库操作失败。";
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }

        msgStatus = true;
        msg = "删除机器成功。";
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        return forwardJspUrl;
    }

    /**
     * 分页获取机器信息
     * @param req
     * @param resp
     * @return
     */
    public String getAllMachinesSplit(HttpServletRequest req,HttpServletResponse resp) {

        //初始化
        boolean msgStatus = true;//表示执行状态
        String msg = ""; //表示提示信息
        String url = "/pages/index.jsp";
        Integer currentPage = Integer.valueOf(1);
        Integer lineSize = Integer.valueOf(4);
        req.setAttribute("url",url);

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
            column = "id";
        }

        //调用相关函数从数据库获取所有信息
        List<Machine> machines = new ArrayList<>();
        Integer allRecorders = 0;
        try {
            machines = (List<Machine>) ServiceFactory.MachineServiceInstance().
                    getAllMachinesPag(column, keyWord, currentPage, lineSize).get("machines");
            allRecorders=(Integer)ServiceFactory.MachineServiceInstance().
                    getAllMachinesPag(column, keyWord, currentPage, lineSize).get("counts");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "获取机器信息列表异常。";
            msgStatus = false;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }
        //检查结果
        if (machines.size()<=0) {
            msg = "获取容器信息列表为空。";
            msgStatus = false;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }

        //获取ping结果
        for (Machine machine:machines) {
            machine.setPingFlag(HttpClientUtil.ping(machine.getIp().
                    substring(0,machine.getIp().indexOf(":"))));

        }

        //封装结果
        req.setAttribute("url",allMachinesSplitServletUrl);//复写url信息
        msg = "获取分页信息成功";
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("lineSize", lineSize);
        req.setAttribute("allMachines",machines);
        req.setAttribute("allRecorders",allRecorders);
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);

        return "/pages/machine/machine_list.jsp";
    }

    /**
     * 改变机器状态
     * @param req
     * @return
     */
    public String changeMachineStatus(HttpServletRequest req) {
        return  "";
    }

}

