package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取property配置文件中的信息
 */
public class PropertyUtil {
    private static Properties p = null;
    static {
        InputStream in = null;
        try {
            in=PropertyUtil.class.getClassLoader().getResourceAsStream("app.properties");
            p=new Properties();
            p.load(in);
        } catch (IOException e) {
            System.out.println("1");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //通过键名获取配置信息
    public static String getProperty(String key) {
        return p.getProperty(key);
    }

    public static void main(String[] args)
    {
        System.out.println(PropertyUtil.getProperty("jdbc.driver"));
    }
}
