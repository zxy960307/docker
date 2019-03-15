import net.sf.json.JSONObject;
import utils.File;
import utils.httpclient.HttpClient;

/**
 * Created by 41463 on 2019/3/14.
 */
public class Test {
    public Test(){}

    public void testJson(){
        //String path = Test.class.getClassLoader().getResource("/container/json/mysql.json").getPath();
        System.out.println("C:\\Users\\41463\\IdeaProjects\\docker\\src\\main\\resources\\container\\json\\mysql.json");
        JSONObject jobj = File.readJsonFile("C:\\Users\\41463\\IdeaProjects\\docker\\src\\main\\resources\\container\\json\\mysql.json");
        //System.out.println(jobj);
        JSONObject res = HttpClient.doPost("http://192.168.43.230:2375/containers/create",jobj);
        System.out.println(res);
    }
    public static void main(String [] args) {
        new Test().testJson();
    }
}
