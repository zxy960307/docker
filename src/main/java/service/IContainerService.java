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
}
