package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * 文件操作常用工具方法
 * Created by 41463 on 2019/3/14.
 */
public class File {
    /**
     *  读取json文件，返回JSONObject对象
     * @param filePath 文件路径
     * @return
     */
    public static JSONObject readJsonFile (String filePath) {

        //获取json字符串
        JsonObject object = null;
        try {
            JsonParser parser=new JsonParser();  //创建JSON解析器
            object=(JsonObject) parser.parse(new FileReader(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将JsonObject转化为字符串，再转化为JSONObject对象
        String jsonStr = object.toString();
        JSONObject jobj = JSONObject.fromObject(jsonStr);

        return jobj;
    }
}
