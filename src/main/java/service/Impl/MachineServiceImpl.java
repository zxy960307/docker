package service.Impl;

import factory.ContainerFactory;
import factory.MachineFactory;
import service.IMachineService;
import vo.Machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean createMachine(Machine vo) throws Exception {

        try {
            return MachineFactory.MachineInstance().doCreate(vo);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteMachineById(int id) throws Exception {
        try {
            return MachineFactory.MachineInstance().removeById(id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, Object> getAllMachinesPag(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2) throws Exception {
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("machines", MachineFactory.MachineInstance().getAllMachinesPag
                    (paramString1, paramString2, paramInteger1, paramInteger2));
            result.put("counts",MachineFactory.MachineInstance().getAllCount(paramString1, paramString2));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changeStatus(int id, int changeStatus) throws Exception {
        try {
            return MachineFactory.MachineInstance().changeStatus(id,changeStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
