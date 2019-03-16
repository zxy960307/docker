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
}
