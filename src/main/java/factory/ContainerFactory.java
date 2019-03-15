package factory;

import dao.IContainerDao;
import dao.impl.ContainerDaoImpl;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ContainerFactory {
    public static IContainerDao createContainerInstance() {
        return new ContainerDaoImpl();
    }
}
