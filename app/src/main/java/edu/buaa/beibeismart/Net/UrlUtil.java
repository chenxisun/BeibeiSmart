package edu.buaa.beibeismart.Net;

import android.text.format.Time;

/**
 * Uri
 */
public class UrlUtil {
    public static final String APPID = "16558";
    public static final String APPSECRET = "2c40181dc97b478db37c6999ce4381bb";

//    public static final String IP = "http://172.19.38.1:8080/spring-mvc-showcase";
//    public static final String IP = "";
    public static final String IP = "http://47.94.223.2:8080";
//    public static final String IP_MATERIAL = "http://47.94.165.157:8083";
    public static final String IP_MATERIAL = "";
//    public static final String IP = "http://192.168.253.1:8080/spring-mvc-showcase";
//    public static final String IP = "http://192.168.56.1:8080/spring-mvc-showcase";
//    public static final String IP = "http://10.240.229.228:8080/spring-mvc-showcase";
//    public static final String IP = "http://172.26.219.1:8080/spring-mvc-showcase";
//    public static final String IP = "http://172.25.37.1:8080/spring-mvc-showcase";
//    public static final String IP = "http://172.30.17.1:8080/spring-mvc-showcase";
//public static final String IP = "http://10.0.2.2:8080/spring-mvc-showcase";


    //请求URL
    public static final String URI_LOGIN = IP+"/apply/login";
    public static final String URI_FINDBY_PHONEORNAME = IP+"/search/byPhoneOrUsername";
    public static final String URI_ADD_ATTENTOR = IP+"/apply/addFriend";
    public static final String URI_UPLOAD_NEWS = IP+"/upload/personalNews";
    public static final String URI_FIND_ATTENTOR = IP+"/apply/findAttentors";
    public static final String URI_ADD_GROUP = IP+"/im/joinGroup";
    public static final String URI_EXIT_GROUP = IP+"/im/exitGroup";
    public static final String URI_PERSONCENTER_MYNEWS = IP+"/personcenter/getmynews";
    public static final String URI_PERSONCENTER_SOCIALIZE = IP+"/personcenter/getsocialize";
    public static final String URI_OPERATION_SOCIALIZE = IP+"/operation/socialize";
    public static final String URI_OPERATION_ZAN = IP+"/operation/zan";
    public static final String URI_OPERATION_SOCIALZAN = IP+"/operation/socialZan";
    public static final String URI_OPERATION_GETSOCIALZAN = IP+"/operation/getSocialZan";
    public static final String URI_OPERATION_SOCIALCOLLECT = IP+"/operation/socialCollect";
    public static final String URI_OPERATION_COMMENT = IP+"/operation/comment";
    public static final String URI_OPERATION_GETCOMMENT = IP+"/operation/getComment";
    public static final String URI_PERSONALNEWS_CIRCLENEWS = IP+"/upload/getCircleNews";
    public static final String URI_SOCIAL_GETHOTNEWS = IP+"/upload/getHotNews";

    //新闻频道
    public static String getNewChannel() {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        String address = "http://route.showapi.com/109-34?" +
                "showapi_appid=" + APPID +
                "&showapi_timestamp=" + localTime.format("%Y%m%d%H%M%S") +
                "&showapi_sign=" + APPSECRET;
        return address;
    }

    //新闻接口URL
    public static String getNewAddress(String channelId, int page) {
        Time localTime = new Time("Asia/Hong_Kong");
        localTime.setToNow();
        String address = "http://route.showapi.com/109-35?" +
                "showapi_appid=" + APPID +
                "&showapi_timestamp=" + localTime.format("%Y%m%d%H%M%S") +
                "&channelId=" + channelId +
                "&channelName=" +
                "&title=" +
                "&page=" + page +
                "&needContent=" +
                "&needHtml=" +
                "&showapi_sign=" + APPSECRET;
        return address;
    }
}
