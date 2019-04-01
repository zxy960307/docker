package utils;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by 41463 on 2019/3/14.
 */
public class HttpClientUtil {

    private static final String userAgent = "Mozilla/5.0 (Windows NT 6.2; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";
    /**
     * post请求发送json数据
     * @param url
     * @param json
     * @return
     */
    public static JSONObject doPost(String url,JSONObject json){

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpPost post = new HttpPost(url);
        JSONObject response = null;

        //发送json数据
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            res = httpclient.execute(post);
            //消息码正常时
            if(res.getStatusLine().getStatusCode() == 201 || res.getStatusLine().getStatusCode() == 204) {
                HttpEntity entity = res.getEntity();
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.fromObject(result);//解析响应实体
            }
            else
            {
                System.out.println("与docker服务器http连接异常!");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    /**
     * 发送不带参数的post请求
     * @param url
     * @return
     */
    public static JSONObject doPost(String url) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpPost post = new HttpPost(url);
        System.out.println(post);
        post.setHeader("User-Agent",userAgent);
        JSONObject response = null;

        //发送json数据
        try {
            res = httpclient.execute(post);
            //消息码正常时
            if(res.getStatusLine().getStatusCode() == 201 || res.getStatusLine().getStatusCode() == 204) {
                //响应实体为空时
                try {
                    String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                    response = JSONObject.fromObject(result);//解析响应实体
                    response.put("result",true);
                } catch (Exception e) {
                    System.out.println("数据发送正常，响应数据异常");
                    response = new JSONObject();
                    response.put("msg","数据发送正常，响应数据异常");
                    response.put("result",false);
                    return response;
                }
            }
            else
            {
                System.out.println("与docker服务器http连接异常!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("与docker服务器http连接异常,数据未发送!");
            return null;
        }
        return response;
    }

    public static JSONObject doGet(String url) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpGet get = new HttpGet(url);
        JSONObject response = null;

        try {
            res = httpclient.execute(get);
            //消息码正常时
            if(res.getStatusLine().getStatusCode() == 200) {
                try {
                    String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                    response = JSONObject.fromObject(result);//解析响应实体
                } catch (Exception e) {
                    System.out.println("数据发送正常，响应数据异常");
                    response = new JSONObject();
                    response.put("msg","数据发送正常，响应数据异常");
                    return response;
                }
            }
            else
            {
                System.out.println("与docker服务器http连接异常!");
                return null;
            }
        }
        catch (IOException e) {
            System.out.println("与docker服务器http连接异常,数据未发送!");
            return null;
        }

        return response;
    }

    //执行delete请求
    public static JSONObject doDelete(String url) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpDelete delete = new HttpDelete(url);
        JSONObject response = null;

        try {
            res = httpclient.execute(delete);
            if(res.getStatusLine().getStatusCode() == 204) {
                try {
                    String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                    response = JSONObject.fromObject(result);//解析响应实体
                    response.put("result",true);
                } catch (Exception e) {
                    System.out.println("数据发送正常，响应数据异常");
                    response = new JSONObject();
                    response.put("msg","数据发送正常，响应数据异常");
                    response.put("result",false);
                    return response;
                }
            }
            else
            {
                System.out.println("与docker服务器http连接异常!");
                return null;
            }
        } catch (Exception e) {
            System.out.println("与docker服务器http连接异常,数据未发送!");
            return null;
        }

        return response;
    }

    /**
     *  get请求返回jsonarray
     * @param url
     * @return
     */
    public static JSONArray doGetArray(String url) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse res = null;
        HttpGet get = new HttpGet(url);
        JSONArray response = null;

        try {
            res = httpclient.execute(get);
            //消息码正常时
            if(res.getStatusLine().getStatusCode() == 200) {
                try {
                    String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                    response = JSONArray.fromObject(result);//解析响应实体
                } catch (Exception e) {
                    System.out.println("数据发送正常，响应数据异常");
                    response = new JSONArray();
                    return response;
                }
            }
            else
            {
                System.out.println("与docker服务器http连接异常!");
                return null;
            }
        }
        catch (IOException e) {
            System.out.println("与docker服务器http连接异常,数据未发送!");
            return null;
        }

        return response;
    }

    public static boolean ping(String ipAddress)  {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        try {
            return InetAddress.getByName(ipAddress).isReachable(timeOut);
            // 当返回值是true时，说明host是可用的，false则不可。
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
