package utils;


import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Created by 41463 on 2019/3/14.
 */
public class HttpClientUtil {
    /**
     * post请求
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url,JSONObject json){

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            res = httpclient.execute(post);
            //消息码正常时
            if(res.getStatusLine().getStatusCode() == 201) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.fromObject(result);//解析响应实体
//                System.out.println(response.get("0").toString());
//                System.out.println("111");
            }
            else
                return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
