package dao.impl;

import dao.ILoginDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import utils.DBCUtil;
import vo.Admin;
import vo.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by 41463 on 2019/4/11.
 */
public class LoginDaoImpl implements ILoginDao {

    Connection conn = new DBCUtil().getConn();//连接对象

    @Override
    public Admin findByName(String name) throws SQLException {

        Map<String,Object> result = new HashMap<>();//储存第一条结果

        //获得表中信息
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT id,name,password,is_main FROM admin " +
                "WHERE status <> 0 AND name = ?";
        try {
            result=qr.query(conn,sql,new MapHandler(),name);
        } catch (SQLException e) {
            System.out.println("获取admin表信息异常。");
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }

        //封装结果
        if (result == null) {
            System.out.println("获取name相关admin表信息为空。");
            return null;
        }
        Admin admin = new Admin();
        admin.setId(Integer.parseInt(String.valueOf(result.get("id"))));
        admin.setName((String) result.get("name"));
        admin.setIsMain((Integer)result.get("is_main"));
        admin.setPassword((String)result.get("password"));
        return admin;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> findAllBySplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> findAll() throws SQLException {
        return null;
    }

    @Override
    public Admin findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean doRemove(Set<?> ids) throws SQLException {
        return false;
    }

    @Override
    public boolean doUpdate(Admin vo) throws SQLException {
        return false;
    }

    @Override
    public boolean doCreate(Admin vo) throws SQLException {
        return false;
    }
}
