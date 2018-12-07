package edu.buaa.beibeismart.SkeletonDetect;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.bodyanalysis.AipBodyAnalysis;

import org.json.JSONObject;

import java.util.HashMap;

public class bodyAnalysis {
    private  final String TAG=bodyAnalysis.class.toString();
    //定义一个client对象
    private  AipBodyAnalysis client;
    String app_id,app_key,secret_key;
    public void init(){
        //初始化client
        app_id=Init.getKey()[0];
        app_key=Init.getKey()[1];
        secret_key=Init.getKey()[2];
        client=new AipBodyAnalysis(app_id,app_key,secret_key);
        if(client==null){
            Log.e(TAG,"Error:client create failed!");
            return ;
        }
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        Log.d(TAG, "Body Analyze init success!");
    }
    public  BodyKeyPoint doKeyPointDetect(byte[] data){
        String token = AuthService.getAccessToken(app_key, secret_key);
        Log.d(TAG,">>>>>>>>>>start to do bodykeypoint detection");
        JSONObject res=client.bodyAnalysis(data,new HashMap<String, String>());
        Log.d(TAG,">>>>>>>>>>>>>>>>"+res.toString());
        BodyKeyPoint detect= JSON.parseObject(res.toString(),BodyKeyPoint.class);
        if (detect.getError_code() != 0 || detect.getError_msg() != null){
            Log.e(TAG, ">>>>>>>>>>>>>Error:" + detect.getError_msg());
        }
        return detect;
    }

}
