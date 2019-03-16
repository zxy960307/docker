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
    public Container queryContainerStatusByContainerId(String containerId) throws Exception {

        try {
            Container result = ContainerFactory.ContainerInstance().findByContainerId(containerId);
            if (result == null)
                return null;
            else
                return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateContainerStatus(Container vo) throws Exception {

        try {
            return  ContainerFactory.ContainerInstance().doUpdate(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
