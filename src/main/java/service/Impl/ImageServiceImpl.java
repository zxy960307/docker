package service.Impl;

import factory.ContainerFactory;
import factory.ImageFactory;
import service.IImageService;
import vo.Image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 41463 on 2019/3/30.
 */
public class ImageServiceImpl implements IImageService {
    @Override
    public boolean insertNotExit(Image image) throws Exception {
        try {
            return  ImageFactory.ImageInstance().insertNotExit(image);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insertImage(Image image) throws Exception {
        try {
            return ImageFactory.ImageInstance().insertImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isImageExit(Image image) throws Exception {
        try {
            return ImageFactory.ImageInstance().isImageExit(image);
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, Object> getAllImagesPag(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2) throws Exception {
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("images", ImageFactory.ImageInstance().getAllImagesPag
                    (paramString1, paramString2, paramInteger1, paramInteger2));
            result.put("counts",ImageFactory.ImageInstance().getAllCount(paramString1, paramString2));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Image> getMachineImages(String machineIp) throws Exception {
        try {
            return ImageFactory.ImageInstance().getMachineImages(machineIp);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteByImageId(String imageId) throws Exception {
        try {
            return ImageFactory.ImageInstance().deleteByImageId(imageId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
