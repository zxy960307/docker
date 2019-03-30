package service.Impl;

import factory.MachineFactory;
import service.IMachineService;
import vo.Machine;

import java.util.List;

/**
 * Created by 41463 on 2019/3/30.
 */
public class MachineServiceImpl implements IMachineService {
    @Override
    public List<Machine> getAllMachines() throws Exception {

        try {
            return MachineFactory.MachineInstance().findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
