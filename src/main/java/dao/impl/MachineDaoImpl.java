package dao.impl;

import dao.IMachineDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import utils.DBCUtil;
import vo.Container;
import vo.Image;
import vo.Machine;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 41463 on 2019/3/30.
 */
public class MachineDaoImpl implements IMachineDao {

    Connection conn = new DBCUtil().getConn();//连接对象

    @Override
    public boolean doCreate(Machine vo) throws SQLException {
        return false;
    }

    @Override
    public boolean doUpdate(Machine vo) throws SQLException {
        return false;
    }

    @Override
    public boolean doRemove(Set<?> ids) throws SQLException {
        return false;
    }

    @Override
    public Machine findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Machine> findAll() throws SQLException {
        //定义List存放查询结果
        List<Object[]> result = new ArrayList<>();

        //获得表中所有信息
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT id,name,ip,status,create_time FROM machine " +
                "WHERE status <> 0";
        try
        {
            result=qr.query(conn,sql,new ArrayListHandler());
        } catch (SQLException e) {
            System.out.println("获取container表所有信息异常。");
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }

        //封装结果
        if (result == null) {
            System.out.println("获取container表所有信息为空。");
            return null;
        }
        List<Machine> machineResult = new ArrayList<>();//存放封装后的结果
        for(Object[] machine:result) {
            Machine temp = new Machine();
            temp.setId(Integer.parseInt(String.valueOf(machine[0])));
            temp.setName((String) machine[1]);
            temp.setIp((String)machine[2]);
            temp.setStatus((int)machine[3]);
            temp.setCreateTime((Timestamp)machine[4]);
            machineResult.add(temp);
        }

        return machineResult;
    }

    @Override
    public List<Machine> findAllBySplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }

}
