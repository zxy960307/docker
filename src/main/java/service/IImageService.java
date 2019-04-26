package service;

import dao.IMachineDao;
import vo.Image;

import java.util.List;
import java.util.Map;

/**
 * Created by 41463 on 2019/3/30.
 */
public interface IImageService {
    public boolean insertNotExit(Image image) throws Exception;

    public Map<String,Object> getAllImagesPag(String paramString1, String paramString2,
                                                  Integer paramInteger1, Integer paramInteger2) throws Exception;
/**
    * 通过machineIp获取数据库中相关image记录
    * @param machineIp
    * @return
            * @throws Exception
    */
    public List<Image> getMachineImages(String machineIp) throws Exception;

    /**
     * 通过imageId删除记录
     * @param imageId
     * @return
     * @throws Exception
     */
    public boolean deleteByImageId(String imageId) throws Exception;

    /**
     * 判断机器中是否已存在该image
     * @param image
     * @return 若存在返回true;否则返回false
     * @throws Exception
     */
    public boolean isImageExit(Image image) throws Exception;

    /**
     * 向image表中插入单条数据
     * @param image
     * @return
     * @throws Exception
     */
    public boolean insertImage(Image image) throws Exception;

}
