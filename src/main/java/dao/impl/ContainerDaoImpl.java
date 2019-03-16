package dao.impl;

import dao.IContainerDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import utils.DBCUtil;
import vo.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ContainerDaoImpl implements IContainerDao {

    Connection conn = new DBCUtil().getConn();//连接对象
    /**
     * 数据表中创建容器记录
     * @param vo 表示要执行操作的对象
     * @return
     * @throws SQLException
     */

    @Override
    public boolean doCreate(Container vo) throws SQLException {

        //初始化
        vo.setCreateTime( new Timestamp(System.currentTimeMillis()));//vo中设置时间

        //完成插入操作
        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "INSERT INTO container(container_id,create_admin_id,create_time,status,image) " +
                "VALUES (?,?,?,?,?)";//定义sql插入语句
        try {
            result = qr.update(conn,sql,vo.getContainerId(),vo.getCreateAdminId(),
                    vo.getCreateTime(),vo.getStatus(),vo.getImage());
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
    public boolean doUpdate(Container vo) throws SQLException {

        //更新字段
        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "UPDATE container SET container_id=?,create_admin_id=?," +
                "create_time=?,image=?,status = ? WHERE id = ?";
        Object[] params = {vo.getContainerId(),vo.getCreateAdminId(),vo.getCreateTime(),
                            vo.getImage(),vo.getStatus(),vo.getId()};
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

    @Override
    public boolean doRemove(Set<?> ids) throws SQLException {
        return false;
    }

    @Override
    public Container findById(String id) throws SQLException {

        return null;
    }

    @Override
    public List<Container> findAll() throws SQLException {
        return null;
    }

    @Override
    public List<Container> findAllBySplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        return null;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }

    @Override
    public Container findByContainerId(String containerId) throws SQLException {

        //初始化

        //根据容器id查询数据库中记录
        QueryRunner qr = new QueryRunner();
        Container result = new Container();//记录查询结果
        String sql = "SELECT * FROM container WHERE container_id=?";
        //Object params[] = {containerId};
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            resultMap = qr.query(conn,sql,new MapHandler(),containerId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("通过container_id查询数据失败。");
            return null;
        } finally {
            DbUtils.close(conn);
        }

        //处理结果集
        result.setContainerId(containerId);
        result.setStatus((Integer) resultMap.get("status"));
        result.setImage((String)resultMap.get("image"));
        result.setCreateAdminId((Integer) resultMap.get("create_admin_id"));
        result.setCreateTime((Timestamp)resultMap.get("create_time"));
        long idLong = (long)resultMap.get("id");
        int id = new Integer(String.valueOf(idLong));
        result.setId(id);
        System.out.println(result);

        return result;
    }
}
