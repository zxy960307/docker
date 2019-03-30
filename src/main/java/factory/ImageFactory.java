package factory;

import dao.IImageDao;
import dao.impl.ImageDaoImpl;

/**
 * Created by 41463 on 2019/3/30.
 */
public class ImageFactory {
    public static IImageDao ImageInstance() {
        return new ImageDaoImpl();
    }
}
