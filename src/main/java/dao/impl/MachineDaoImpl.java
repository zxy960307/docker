package dao.impl;

import dao.IMachineDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
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

        //初始化
        vo.setCreateTime( new Timestamp(System.currentTimeMillis()));//vo中设置时间

        //完成插入操作
        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "INSERT IGNORE INTO machine(name,ip,status,create_time ) " +
                "VALUES (?,?,?,?)";//定义sql插入语句
        try {
            result = qr.update(conn,sql,vo.getName(),vo.getIp(),vo.getStatus(),vo.getCreateTime());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }

        //检查结果
        if (result != 0)
            return  true;
        else
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
        String sql = "SELECT COUNT(*) FROM machine WHERE " + column + " LIKE ? ";
        QueryRunner qr = new QueryRunner();
        Integer result = 0;
        try {
            result = Integer.parseInt(String.valueOf(qr.query(conn,sql,new ScalarHandler(),"%" + keyWord + "%")));
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询所有machine数量失败。");
            return null;
        } finally {
            DbUtils.close(conn);
        }

        return result;
    }

    @Override
    public List<Machine> getAllMachinesPag(String clown, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        //查询所有machine
        List<Object[]> result = new ArrayList<>();
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT id,name,ip,status,create_time " +
                " FROM machine WHERE " + clown + " LIKE ? LIMIT ?,?";
        Object[] params = new Object[3];
        params[0]="%" + keyWord + "%";
        params[1]=(currentPage.intValue() - 1) * lineSize.intValue();
        params[2] = lineSize.intValue();//currentPage.intValue() *
        try {result=qr.query(conn,sql,new ArrayListHandler(),params);
        } catch (SQLException e) {
            System.out.println("分页获取machine表所有信息异常。");
            e.printStackTrace();
            return null;
        } finally {
            DbUtils.close(conn);
        }

        //封装结果
        //判断结果是否为空
        if (result.size() <= 0) {
            System.out.println("获取machine表所有信息为空。");
            return new ArrayList<Machine>();
        }
        List<Machine> machineResult = new ArrayList<>();//存放封装后的结果
        for(Object[] container:result) {
            Machine temp = new Machine();
            temp.setId(Integer.parseInt(String.valueOf(container[0])));
            temp.setName((String) container[1]);
            temp.setIp((String) container[2]);
            temp.setStatus((Integer) container[3]);
            temp.setCreateTime((Timestamp) container[4]);
            machineResult.add(temp);
        }
        return machineResult;
    }

    @Override
    public boolean removeById(int id) throws Exception {

        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "DELETE FROM machine WHERE id = ?";
        try {
            result = qr.update(conn,sql,id);
            if (result != 0) {
                return true;
            }
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            DbUtils.close(conn);
        }

    }

    @Override
    public boolean changeStatus(int id, int changeStatus) throws SQLException {
        //更新字段
        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "UPDATE machine SET status = ? WHERE id = ?";
        Object[] params = {changeStatus,id};
        try {
            result = qr.update(conn,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }

        //检查结果
        if (result != 0) {
            return true;
        }

        return false;
    }
}
