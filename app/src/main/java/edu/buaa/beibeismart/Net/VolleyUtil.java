package edu.buaa.beibeismart.Net;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VolleyUtil 网络请求
 */
public class VolleyUtil {
    //请求方式
    public static final int METHOD_POST = 1;
    public static final int METHOD_GET = 2;

    private static int requestType = -1;
    static String cookies;
    String TAGURL = "VOLLEYURL:";

    public static final int REQUEST_LOGIN = 10;
    public static final int REQUEST_FIND_FRIEND = 2;
    public static final int REQUEST_ADD_ATTENTOR = 200;
    public static final int REQUEST_FIND_ATTENTOR = 201;
    public static final int REQUEST_JOIN_GROUP = 202;
    public static final int REQUEST_EXIT_GROUP = 203;
    public static final int REQUEST_OPERATION_SOCIALZAN = 213;
    public static final int REQUEST_OPERATION_GETSOCIALZAN = 214;
    /*===========================外部请求函数==================================*/

    //Glide加载图片
    public void loadGlideImage(Context context, String url, ImageView imageView){
        //图片
        if(url.startsWith("/")){
            url = UrlUtil.IP + url;
        }
        Log.e(TAG_IMG,""+url);
        //新闻图片
 /*       Glide.with(context).load(url)
//                    .fitCenter()
//                    .placeholder(R.drawable.bg_default_news)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.img_error_150)
                .into(imageView);*/
    }

    //获取用户关注人
    public void requestFindAttentor(Context pContext,int pRequestType, int pMethod, String pUrl,String fansId){
        context = pContext;
        requestType = pRequestType;
        requestQueue = Volley.newRequestQueue(context);
        pUrl = pUrl +"/"+fansId;
        Log.e(TAGURL,pUrl);
        if(pMethod == VolleyUtil.METHOD_GET){
            stringRequest = new StringRequest(Request.Method.GET,pUrl,stringListener,errorListener);
        }
        requestQueue.add(stringRequest);
    }

    //添加好友 curUserId:当前用户ID aimUserId：要添加的目标关注人ID
    public void requestAddFriend(Context pContext,int pRequestType, int pMethod, String pUrl,String curUserId, String aimUserId){
        context = pContext;
        requestType = pRequestType;
        requestQueue = Volley.newRequestQueue(pContext);
        pUrl = pUrl+"/"+curUserId+"/"+aimUserId;
        Log.e(TAGURL,pUrl);
        if(pMethod == VolleyUtil.METHOD_GET){
            stringRequest = new StringRequest(Request.Method.GET,pUrl,stringListener,errorListener);
        }
        requestQueue.add(stringRequest);
    }
    //查找好友
    public void requestFindFriend(Context pContext,int pRequestType, int pMethod, String pUrl,String userId, String attentData){
        context = pContext;
        requestType = pRequestType;
        requestQueue = Volley.newRequestQueue(pContext);
//        pUrl = pUrl + "?data="+ Uri.encode(data,"utf-8");
        pUrl = pUrl + "/"+userId+"/"+ Uri.encode(attentData,"utf-8");
//        pUrl = UrlUtil.IP+"/login/saveFactoryUser3";
        Log.e(TAGURL,pUrl);
        if(pMethod == VolleyUtil.METHOD_GET){
            stringRequest = new StringRequest(Request.Method.GET,pUrl,stringListener,errorListener);
        }
        requestQueue.add(stringRequest);
    }
    //登录
    public void reuqestLogin(Context pContext, int pMethod, String pUrl,int pRequestType, String userName, String passWord){
        context = pContext;
        requestType = pRequestType;
        requestQueue = Volley.newRequestQueue(pContext);
        pUrl = pUrl+"/"+userName+"/"+passWord;
        Log.e(TAGURL,pUrl);
        if(pMethod == VolleyUtil.METHOD_GET){
            stringRequest = new StringRequest(Request.Method.GET,pUrl,stringListener,errorListener);
        }
        else{}
        requestQueue.add(stringRequest);
    }
    //登录
    public void reuqestLogin1(Context pContext, int pMethod, String pUrl,int pRequestType, final JSONObject jsonParam){
        context = pContext;
        requestType = pRequestType;
        requestQueue = Volley.newRequestQueue(pContext);
        Log.e(TAGURL,pUrl);
        Log.e("JsonParam",jsonParam.toString());
//        if(pMethod == VolleyUtil.METHOD_GET){}
//        else{
//            jsonRequest = new JsonObjectRequest(Request.Method.POST,pUrl,jsonParam,jsonListener,errorListener);
            stringRequest = new StringRequest(Request.Method.POST,pUrl,stringListener,errorListener){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map map = new HashMap();
                    map.put("data",jsonParam.toString());
                    return map;
                }
            };
//        }
        requestQueue.add(stringRequest);
    }
    /*=======================================Volley监听类Listener==============================================*/
    private String TAGSTRING = "VOLLEYSTRING";
    /**
     * @注释：Volley响应成功Listener_String格式
     */
    String TAG = "VolleyUtil";
    private class StringResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String s) {
            Log.e(TAGSTRING,"响应成功："+s);
            switch (requestType){
                //登录
                case REQUEST_LOGIN:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
                    break;
                //查找朋友
                case REQUEST_FIND_FRIEND:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
                    break;
                //添加朋友
                case REQUEST_ADD_ATTENTOR:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
                    break;
                //获取关注人列表
                case REQUEST_FIND_ATTENTOR:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
                    break;
                case REQUEST_EXIT_GROUP:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
                    break;
                //请求我的新闻
                default:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,s);
            }
            requestType = -1;
        }
    }

    private String TAGJSON = "VOLLEYJSON";
    /**
     * @注释：Volley响应成功Listener_JSON格式
     */
    private class JsonResponseListener implements com.android.volley.Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject resultJson) {
            Log.e(TAGJSON,"响应成功："+resultJson.toString());
            switch (requestType){
                //请求加入交流组
                case REQUEST_JOIN_GROUP:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,resultJson);
                    break;
                //点赞操作
                case REQUEST_OPERATION_SOCIALZAN:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,resultJson);
                    break;
                //获取点赞情况
                case REQUEST_OPERATION_GETSOCIALZAN:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,resultJson);
                    break;
                default:
                    volleyFinishListener.onVolleyFinish(SUCCESS_SUCCESS,resultJson);
            }
            requestType = -1;
        }
    }

    /**
     * @注释:Volley响应失败Listener
     */
    private class ResponseErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Log.e("VolleyErrorListener", "" + volleyError.getMessage() + ":" + volleyError.toString());
            //当连接不上服务器时
            String errorMsg = "连接服务器失败";
            errorMsg = disposeDefaultError(volleyError);
            if(volleyFinishListener!= null){
                volleyFinishListener.onVolleyFinish(FAILED_FAILED,errorMsg);
            }
            requestType = -1;
        }
    }


    /**
     * @note:默认错误处理
     */
    private String disposeDefaultError(VolleyError volleyError){
        String errorMsg = "连接服务器失败";
        //服务器返回的错误信息
        if(volleyError.networkResponse != null){
            byte[] htmlBodyBytes = volleyError.networkResponse.data;
            String errorInfo = new String(htmlBodyBytes);
            Log.e("VolleyErrorListener","Server:"+ errorInfo);
            //服务器传递的错误信息
            try {
                JSONObject errorJson = new JSONObject(errorInfo);
                errorMsg = errorJson.getString("exceptionInfo");
            } catch (JSONException e) {
                errorMsg = errorInfo;
            }
        }
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        return errorMsg;
    }

    /*=====================Volley具体操作==============================================*/



    /*=====================公用方法==============================================*/
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @return true 表示网络可用
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
                else{
                    volleyFinishListener.onVolleyFinish(FAILED_FAILED,"网络连接不可用");
                    return false;
                }
            }
        }
        return false;
    }

    /*=====================Volley回调接口==========================================*/

    //连接服务器结果
    public static final int SUCCESS_SUCCESS = 11;
    public static final int FAILED_FAILED = 13;
    List<VolleyUtil> volleyUtilList;
    VolleyFinishListener volleyFinishListener;
    /**
     * @note:Volley完成接口*/
    public interface VolleyFinishListener{
        void onVolleyFinish(int isSuccess, Object result);
    }
    public void setOnVolleyFinishListener(VolleyFinishListener volleyFinishListener){
        this.volleyFinishListener =volleyFinishListener;
    }

    /*=====================Volley配置函数==============================================*/
    private static Context context;
    private static VolleyUtil volleyUtil = null;
    private static RequestQueue requestQueue = null;
    private static JsonObjectRequest jsonRequest;
    private static JsonResponseListener jsonListener;
    private static ResponseErrorListener errorListener;
    private static StringRequest stringRequest;
    private static StringResponseListener stringListener;
//    private static ImageLoader imageLoader;
    private static ImageLoader.ImageListener imageListener;
    private VolleyUtil() {
        //创建请求队列
        jsonListener = new JsonResponseListener();
        errorListener = new ResponseErrorListener();
        stringListener = new StringResponseListener();
    }
//    ImageLoader.ImageListener listener;
    public void loadImage(Context context,ImageView imageView,String url){
        requestQueue = Volley.newRequestQueue(context);
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        Log.e(TAG_IMG,"url:"+url);
    }

    private String TAG_IMG = "VolleyImage";

    public static VolleyUtil getVolleyUtil() {
        if (volleyUtil == null) {
            volleyUtil = new VolleyUtil();
        }
        return  volleyUtil;
    }

    public static void distoryVolley(){
        volleyUtil = null;
    }
}
