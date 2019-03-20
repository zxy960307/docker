package service.Impl;

import factory.ContainerFactory;
import service.IContainerService;
import vo.Container;

import java.util.List;

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
    public List<Container> getAllContainers() throws Exception {
        try {
            return ContainerFactory.ContainerInstance().findAll();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public String[] getAllContainersContainerId() throws Exception {

        try {
            List<Container> resultAll = ContainerFactory.ContainerInstance().findAll();
            String[] resultContainerId = new String[resultAll.size()];
            int length = 0;
            for (Container container:resultAll) {
                resultContainerId[length] = container.getContainerId();
                length ++;
            }
            return resultContainerId;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
