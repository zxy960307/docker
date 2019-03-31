package service;

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

    public List<Image> getMachineImages(String machineIp) throws Exception;

}
