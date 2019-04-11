package dao;

import vo.Admin;

import java.sql.SQLException;

/**
 * Created by 41463 on 2019/4/11.
 */
public interface ILoginDao extends IDAO<Integer,Admin> {

    /**
     * 通过用户名返回Admin类信息
     * @param name
     * @return
     * @throws SQLException
     */
    public Admin findByName(String name) throws SQLException;
}
