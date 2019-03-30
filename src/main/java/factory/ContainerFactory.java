package factory;

import dao.IContainerDao;
import dao.IMachineDao;
import dao.impl.ContainerDaoImpl;
import dao.impl.MachineDaoImpl;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ContainerFactory {
    public static IContainerDao ContainerInstance() {
        return new ContainerDaoImpl();
    }
}
