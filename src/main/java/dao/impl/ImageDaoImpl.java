package dao.impl;

import dao.IImageDao;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DBCUtil;
import vo.Image;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by 41463 on 2019/3/30.
 */
public class ImageDaoImpl  implements IImageDao {
    Connection conn = new DBCUtil().getConn();//连接对象

    @Override
    public boolean insertNotExit(Image image) throws SQLException {
        //完成插入操作
        QueryRunner qr = new QueryRunner();

        int result = 0;
        String sql = "INSERT IGNORE INTO image (image_id,repo_tags,machine_ip) " +
                "VALUES (?,?,?)";//定义sql插入语句
        try {
            qr.update(conn,sql,image.getImageId(),image.getRepoTags(),image.getMachineIp());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DbUtils.close(conn);
        }
        return true;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws SQLException {
        String sql = "SELECT COUNT(*) FROM image WHERE " + column + " LIKE ?";
        QueryRunner qr = new QueryRunner();
        Integer result = 0;
        try {
            result = Integer.parseInt(String.valueOf(qr.query(conn,sql,new ScalarHandler(),"%" + keyWord + "%")));
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("查询所有image数量失败。");
            return null;
        } finally {
            DbUtils.close(conn);
        }

        return result;
    }

    @Override
    public List<Image> findAllBySplit(String column, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        return null;
    }

    @Override
    public List<Image> findAll() throws SQLException {
        return null;
    }

    @Override
    public Image findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public boolean doRemove(Set<?> ids) throws SQLException {
        return false;
    }

    @Override
    public boolean doUpdate(Image vo) throws SQLException {
        return false;
    }

    @Override
    public boolean doCreate(Image vo) throws SQLException {
        return false;
    }

    @Override
    public List<Image> getAllImagesPag(String clown, String keyWord, Integer currentPage, Integer lineSize) throws SQLException {
        //查询所有镜像image
        List<Object[]> result = new ArrayList<>();
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT id,image_id,repo_tags,machine_ip " +
                " FROM image WHERE " + clown + " LIKE ?  LIMIT ?,?";
        Object[] params = new Object[3];
        params[0]="%" + keyWord + "%";
        params[1]=(currentPage.intValue() - 1) * lineSize.intValue();
        params[2] = lineSize.intValue();//currentPage.intValue() *
        try {result=qr.query(conn,sql,new ArrayListHandler(),params);
        } catch (SQLException e) {
            System.out.println("分页获取image表所有信息异常。");
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }

        //封装结果
        //判断结果是否为空
        if (result.size() <= 0) {
            System.out.println("获取image表所有信息为空。");
            return null;
        }
        List<Image> imageResults = new ArrayList<>();//存放封装后的结果
        for(Object[] container:result) {
            Image temp = new Image();
            temp.setId(Integer.parseInt(String.valueOf(container[0])));
            temp.setImageId((String) container[1]);
            temp.setRepoTags((String) container[2]);
            temp.setMachineIp((String) container[3]);
            imageResults.add(temp);
        }

        return imageResults;
    }
}
