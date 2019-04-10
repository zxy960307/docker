import net.sf.json.JSONObject;
import utils.FileUtil;
import utils.HttpClientUtil;

/**
 * Created by 41463 on 2019/3/14.
 */
public class Test {
    public Test(){}

    public void testJson(){
        //String path = Test.class.getClassLoader().getResource("/container/json/mysqlzxy.json").getPath();
        System.out.println("C:\\Users\\41463\\IdeaProjects\\docker\\src\\main\\resources\\container\\json\\mysqlzxy.json");
        JSONObject jobj = FileUtil.readJsonFile("C:\\Users\\41463\\IdeaProjects\\docker\\src\\main\\resources\\container\\json\\mysqlzxy.json");
        //System.out.println(jobj);
        JSONObject res = HttpClientUtil.doPost("http://192.168.43.230:2375/containers/create",jobj);
        //System.out.println(res.get("0").toString());
        String resId = res.getString("Id");
        //System.out.println(resId);
        System.out.println(res);
    }
    public static void main(String [] args) {

        boolean pingFlag = HttpClientUtil.ping("192.168.43.23");
        System.out.print(pingFlag);
    }
}
