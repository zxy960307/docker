package factory;

import service.IContainerService;
import service.IImageService;
import service.IMachineService;
import service.Impl.ContainerServiceImpl;
import service.Impl.ImageServiceImpl;
import service.Impl.MachineServiceImpl;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ServiceFactory {
    public static IContainerService ContainerServiceInstance() {
        return new ContainerServiceImpl();
    }

    public static IMachineService MachineServiceInstance() {
        return new MachineServiceImpl();
    }

    public static IImageService ImageServiceInstance() {
        return new ImageServiceImpl();
    }
}
