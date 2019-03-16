package service;

import vo.Container;

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
     *  通过容器container_id查询容器状态
     * @param containerId
     * @return 容器状态，-1表示未查询到容器数据
     * @throws Exception
     */
    
    public Container queryContainerStatusByContainerId(String containerId) throws Exception;

    /**
     * 更新容器状态status
     * @param vo 特定容器
     * @return 成功返回true,失败返回false
     * @throws Exception
     */
    public boolean updateContainerStatus(Container vo) throws Exception;
}
