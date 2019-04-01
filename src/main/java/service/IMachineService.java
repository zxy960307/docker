package service;

import vo.Machine;

import java.util.List;
import java.util.Map;

/**
 * Created by 41463 on 2019/3/30.
 */
public interface IMachineService {
    public List<Machine> getAllMachines() throws Exception;

    public boolean createMachine(Machine vo) throws Exception;


    public boolean deleteMachineById(int id) throws Exception;

    public Map<String,Object> getAllMachinesPag(String paramString1, String paramString2,
                                                  Integer paramInteger1, Integer paramInteger2) throws Exception;

}
