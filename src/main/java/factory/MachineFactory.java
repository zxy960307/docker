package factory;

import dao.IMachineDao;
import dao.impl.MachineDaoImpl;

/**
 * Created by 41463 on 2019/3/30.
 */
public class MachineFactory {
    public static IMachineDao MachineInstance() {
        return new MachineDaoImpl();
    }
}
