package dao;

import vo.Image;
import vo.Machine;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 41463 on 2019/3/30.
 */
public interface IMachineDao extends IDAO<Integer, Machine> {

    public boolean removeById(int id) throws Exception;

    /**
     *  分页查询容器信息
     * @param clown
     * @param keyWord
     * @param currentPage
     * @param lineSize
     * @return
     * @throws SQLException
     */
    public List<Machine> getAllMachinesPag(String clown, String keyWord,
                                           Integer currentPage, Integer lineSize) throws SQLException;

    public boolean changeStatus(int id,int changeStatus) throws SQLException;

}
