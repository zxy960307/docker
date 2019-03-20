package service;

import vo.Container;

import java.util.List;

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
     * 获取所有容器信息
     * @return
     * @throws Exception
     */
    public List<Container> getAllContainers() throws Exception;

    /**
     *  获得所有容器container_id
     */
    public Container[] getAllContainersByContainerId() throws Exception;
}
