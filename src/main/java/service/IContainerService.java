package service;

import vo.Container;

import java.util.List;
import java.util.Map;

/**
 * Created by 41463 on 2019/3/15.
 */
public interface IContainerService {
    /**
     *  创建容器记录
     * @param vo 容器相关shuju
     * @return 成功返回true,失败返回false
     * @throws Exception
     */
    public boolean createContainer(Container vo) throws Exception;

    /**
     *  通过容器container_id查询容器信息
     * @param containerId
     * @return 容器状态，-1表示未查询到容器数据
     * @throws Exception
     */
    public Container queryContainerByContainerId(String containerId) throws Exception;

    /**
     * 更新容器
     * @param vo 特定容器
     * @return 成功返回true,失败返回false
     * @throws Exception
     */
    public boolean updateContainer(Container vo) throws Exception;

    /**
     * 向docker服务器获取所有容器信息
     * @return
     * @throws Exception
     */
    public List<Container> getAllContainers() throws Exception;

    /**
     *  获得所有容器container_id
     */
    public Container[] getAllContainersByContainerId() throws Exception;

    /**
     *  分页向mysql数据库获取所有容器信息
     * @param paramString1
     * @param paramString2
     * @param paramInteger1
     * @param paramInteger2
     * @return
     * @throws Exception
     */
    public Map<String,Object> getAllContainersPag(String paramString1, String paramString2,
                                                  Integer paramInteger1, Integer paramInteger2) throws Exception;

    public boolean updateContainerStatus(int status,String containerId) throws Exception;

    /**
     * 获取容器当前可用端口号
     * @return
     */
    public int getPort() throws Exception;
}
