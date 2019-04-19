package servlet;

import factory.ServiceFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.HttpClientUtil;
import vo.Image;
import vo.Machine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 41463 on 2019/3/27.
 */
public class ImageServlet extends HttpServlet {

    String forwardJspUrl = "/pages/forward.jsp";

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
            if("getAllImagesSplit".equals(status)) {
                path = this.getAllImagesSplit(req,resp);
            }
            else if("getMachineImages".equals(status)) {
                this.getMachineImages(req,resp);
                return;
            }
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    /**
     * 分页获取所有机器中的镜像信息
     * @param req
     * @param resp
     * @return 跳转路径
     */
    public String getAllImagesSplit(HttpServletRequest req,HttpServletResponse resp) {

        //初始化
        boolean msgStatus = true;//表示执行状态
        String msg = ""; //表示提示信息
        String url = "/pages/index.jsp";
        Integer currentPage = Integer.valueOf(1);
        Integer lineSize = Integer.valueOf(3);
        req.setAttribute("url",url);

        //同docker服务器更新数据并更新数据库信息
        this.updateImages(req);

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
            column = "image_id";
        }

        //调用相关函数从数据库获取所有信息
        List<Image> images = new ArrayList<>();
        Integer allRecorders = 0;
        try {
            images = (List<Image>) ServiceFactory.ImageServiceInstance().
                    getAllImagesPag(column, keyWord, currentPage, lineSize).get("images");
            allRecorders=(Integer)ServiceFactory.ImageServiceInstance().
                    getAllImagesPag(column, keyWord, currentPage, lineSize).get("counts");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "获取镜像信息列表异常。";
            msgStatus = false;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }
        //检查结果
        if (images.size()<=0) {
            msg = "获取镜像信息列表为空。";
            msgStatus = false;
            req.setAttribute("msg",msg);
            req.setAttribute("msgStatus",msgStatus);
            return forwardJspUrl;
        }

        //封装结果
        req.setAttribute("url","/images/getAllImagesSplit");//复写url信息
        msg = "获取镜像分页信息成功";
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("lineSize", lineSize);
        req.setAttribute("allImages",images);
        req.setAttribute("allRecorders",allRecorders);
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);

        return "/pages/image/image_list.jsp";
    }

    /**
     *  与docker服务器通信获取所有镜像信息并修改数据库内容
     * @param req
     */
    public void updateImages(HttpServletRequest req) {

        //初始化
        boolean msgStatus = true;//表示执行状态
        String msg = ""; //表示提示信息
        String url = "/pages/index.jsp";
        Integer currentPage = Integer.valueOf(1);
        Integer lineSize = Integer.valueOf(4);
        req.setAttribute("url",url);

        //获取所有机器信息
        //后期可以完善的功能：获取所有可以ping通的机器信息
        List<Machine> allMachines = null;
        try {
            allMachines = ServiceFactory.MachineServiceInstance().getAllMachines();
        } catch (Exception e) {
            e.printStackTrace();
            msgStatus = false;
            req.setAttribute("msgStatus",msgStatus);
            msg = "获取所有机器信息异常。";
            req.setAttribute("msg",msg);
            return ;
        }
        if (allMachines == null || allMachines.size() <=0) {
            msgStatus = false;
            req.setAttribute("msgStatus",msgStatus);
            msg = "获取所有机器信息为null。";
            req.setAttribute("msg",msg);
            return ;
        }

        //与docker服务器通信
        List<String> urls = new ArrayList<>();//存放通信的url
        for (Machine machine:allMachines) {
            String urlTemp = "http://";
            urlTemp = urlTemp+machine.getIp()+"/images/json";
            urls.add(urlTemp);
        }
        List<Image> results = new ArrayList<>();//存放所有通信的结果
        for (int i = 0;i<urls.size();i++) {
            JSONArray result = HttpClientUtil.doGetArray(urls.get(i));//和机器i通信后的结果
            //封装结果
            for (int j = 0;j<result.size();j++) {
                Image image = new Image();
                image.setImageId(result.getJSONObject(j).get("Id").toString().substring(7,19));
                image.setRepoTags(result.getJSONObject(j).getJSONArray("RepoTags").get(0).toString());
                image.setMachineIp(urls.get(i).substring(7,urls.get(i).indexOf("/",7)));
                results.add(image);
            }
        }

        //通过image_id检查记录是否存在，若不存在则更新记录
        for (Image image:results) {
            try {
                if (!ServiceFactory.ImageServiceInstance().insertNotExit(image)) {
                    msgStatus = false;
                    req.setAttribute("msgStatus",msgStatus);
                    msg = "向数据库插入镜像数据时失败。";
                    req.setAttribute("msg",msg);
                    return ;
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgStatus = false;
                req.setAttribute("msgStatus",msgStatus);
                req.setAttribute("msg",msg);
                msg = "向数据库插入镜像数据时异常。";
                return ;
            }
        }


        //将数据库中所有imge记录与result对比，若result不存在则删除该记录



        msgStatus = true;
        msg = "同docker服务器更新镜像信息成功。";
        req.setAttribute("msg",msg);
        req.setAttribute("msgStatus",msgStatus);
        return;
    }

    public void getMachineImages(HttpServletRequest req,HttpServletResponse resp) {
        resp.setContentType("text/json");
        resp.setCharacterEncoding("utf-8");

        boolean msgStatus = true;//表示执行状态
        String msg = ""; //表示提示信息
        String url = "/pages/index.jsp";
        Integer currentPage = Integer.valueOf(1);
        Integer lineSize = Integer.valueOf(4);
        req.setAttribute("url",url);

        //获取请求参数
        String machineIp = (String)req.getParameter("machineIp");

        //与docker服务器同步镜像信息
        this.updateImages(req);
        //同步失败
        if ((boolean)req.getAttribute("msgStatus") == false){
            try {
                resp.getWriter().write((String)req.getAttribute("msg"));
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //从数据库获取镜像信息
        List<Image> images = null;
        try {
            images = ServiceFactory.ImageServiceInstance().getMachineImages(machineIp);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                resp.getWriter().write("获取machine相关镜像信息异常");
                return;
            } catch (Exception E) {
            }
            return;
        }
        if (images == null || images.size() <= 0) {
            try {
                resp.getWriter().write("获取machine相关镜像信息为空");
                return;
            } catch (Exception e) {
            }
        }

        //封装信息为jsonArray
        JSONArray imagesJson = JSONArray.fromObject(images);
        try {
            resp.getWriter().print(imagesJson);
        } catch (Exception e) {}
        return;
    }
}
