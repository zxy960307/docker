package service;

import vo.Machine;

import java.util.List;

/**
 * Created by 41463 on 2019/3/30.
 */
public interface IMachineService {
    public List<Machine> getAllMachines() throws Exception;
}
