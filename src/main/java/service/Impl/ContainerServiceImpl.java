package service.Impl;

import factory.ContainerFactory;
import service.IContainerService;
import vo.Container;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ContainerServiceImpl implements IContainerService {
    @Override
    public boolean createContainer(Container vo) throws Exception {

        try {
            return ContainerFactory.ContainerInstance().doCreate(vo);
        } catch( Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int queryContainerStatusByContainerId(String containerId) throws Exception {

        try {
              Container result = ContainerFactory.ContainerInstance().findByContainerId(containerId);
            if (result == null)
                return -1;
            else
                return result.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
