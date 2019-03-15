package dao.impl;

import dao.IContainerDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import utils.DBCUtil;
import vo.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by 41463 on 2019/3/15.
 */
public class ContainerDaoImpl implements IContainerDao {

    public DBCUtil dbcUtil = new DBCUtil();
    /**
     * 数据表中创建容器记录
     * @param vo 表示要执行操作的对象
     * @return
     * @throws SQLException
     */

    @Override
    public boolean doCreate(Container vo) throws SQLException {

        //初始化
        Connection conn = dbcUtil.getConn();//获取连接对象
        vo.setCreateTime(  new java.sql.Date(new Date().getTime()));//vo中设置时间

        //完成插入操作
        QueryRunner qr = new QueryRunner();
        int result = 0;
        String sql = "INSERT INTO container(container_id,create_admin_id,create_time,status,image) " +
                "VALUES (?,?,?,?,?)";//定义sql插入语句
        try {
            result = qr.update(conn,sql);
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
}
