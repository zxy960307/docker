package utils;

/**
 * 类中包含一些常用工具方法
 */
public class GeneralUtil {

    /**
     * 判断字符串是否为null或“”
     * @param str 字符串
     * @return true表示null或“”,false表示字符串不为空
     */
    public static boolean isStrEmpty(String str) {
        if (str == null || str.length() <= 0)
            return true;
        else
        return false;
    }

    /**
     * 返回容器status对应的String字符串
     * @param status
     * @return
     */
    public static String returnStatusStr(Integer status) {
        if (status == 0)
            return "created";
        else if(status == 1)
            return "running";
        else if(status == 2)
            return "exited";
        else if(status == 3)
            return "paused";
        else if(status == 4)
            return "restarting";
        else if(status == 5)
            return "removing";
        else if(status == 6)
            return "dead";
        return "unknown status";
    }
}
