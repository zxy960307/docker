package dao;

import vo.Image;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 41463 on 2019/3/30.
 */
public interface IImageDao extends IDAO<Integer, Image> {
    public boolean insertNotExit(Image image) throws SQLException;

    public List<Image> getAllImagesPag(String clown, String keyWord,
                                       Integer currentPage, Integer lineSize) throws SQLException;
}
