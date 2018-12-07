package edu.buaa.beibeismart.SkeletonDetect;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AuthService {
    private static final String TAG = AuthService.class.toString();
    private static final String authHost="https://aip.baidubce.com/oauth/2.0/token?";
     /*
    获取AccessToken
    这个token有效期30天，不需要每次都要请求，可以本地记录下来，每次应用启动只请求一次，或者其他方式
     */
     public static String getAccessToken(String appKey,String secretKey){
         Log.d(TAG,">>>>>>>>>getAccessToken");
         String uri=authHost+
                 "grant_type=client_credentials"+
                 "&client_id=" + appKey +
                 "&client_secret=" + secretKey;
         try{
             URL realUrl=new URL(uri);
             HttpURLConnection conn=(HttpURLConnection)realUrl.openConnection();
             conn.connect();
             //获取所有相应头子段
             Map<String,List<String>> map =conn.getHeaderFields();
             //遍历所有的响应头
             for(String key:map.keySet()){
                 Log.d(TAG,">>>>>>>>>"+map.get(key));
             }
             InputStreamReader instr=new InputStreamReader(conn.getInputStream());
             BufferedReader in =new BufferedReader(instr);
             String result="";
             String line="";
             while((line=in.readLine())!=null){
                 result+=line;
             }
             //返回结果
             JSONObject res=new JSONObject(result);
             String access_token=res.getString("access_token");
             return access_token;
         }catch (IOException e) {
             e.printStackTrace();
             Log.d(TAG,">>>>>>>>>>>>>获取Token失败！");
         } catch (JSONException e) {
             e.printStackTrace();
             Log.d(TAG,">>>>>>>>>>>>>json解析失败！");
         }
         return null;
     }
}
