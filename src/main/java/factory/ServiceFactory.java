package factory;

import service.IContainerService;
import service.Impl.ContainerServiceImpl;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ServiceFactory {
    public static IContainerService ContainerServiceInstance() {
        return new ContainerServiceImpl();
    }
}
