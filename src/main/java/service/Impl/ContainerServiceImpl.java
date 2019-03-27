package service.Impl;

import factory.ContainerFactory;
import service.IContainerService;
import vo.Container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Container queryContainerByContainerId(String containerId) throws Exception {

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
    public boolean updateContainer(Container vo) throws Exception {

        try {
            return  ContainerFactory.ContainerInstance().doUpdate(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Container[] getAllContainersByContainerId() throws Exception {

        try {
            List<Container> resultAll = ContainerFactory.ContainerInstance().findAll();
            Container[] resultContainerId = new Container[resultAll.size()];
            int length = 0;
            for (Container container:resultAll) {
                resultContainerId[length] = container;
                length ++;
            }
            return resultContainerId;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String,Object> getAllContainersPag(String paramString1, String paramString2,
                                                  Integer paramInteger1, Integer paramInteger2) throws Exception {

       Map<String,Object> result = new HashMap<>();
        try {
            result.put("containers",ContainerFactory.ContainerInstance().getAllContainersPag
                    (paramString1, paramString2, paramInteger1, paramInteger2));
            result.put("counts",ContainerFactory.ContainerInstance().getAllCount(paramString1, paramString2));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateContainerStatus(int status, String containerId) throws Exception {
        try {
            return ContainerFactory.ContainerInstance().updateContainerStatus(status,containerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
