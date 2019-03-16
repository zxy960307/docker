package dao;

import dao.IDAO;
import vo.Container;

import java.sql.SQLException;

/**
 * Created by 41463 on 2019/3/15.
 */
public interface IContainerDao extends IDAO<String, Container>{
    /**
     *  通过container_id查询容器信息
     * @param containerId 容器在docker服务器中的id
     * @return 容器信息
     * @throws SQLException
     */
    public Container findByContainerId(String containerId) throws SQLException;
}
