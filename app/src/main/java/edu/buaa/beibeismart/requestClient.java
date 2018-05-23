package edu.buaa.beibeismart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.buaa.beibeismart.Activity.Album_list;
import edu.buaa.beibeismart.Media.OnlineMediaPlayer;

import edu.buaa.beibeismart.Service.RecordBackground;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class requestClient {

    OnlineMediaPlayer onlineMediaPlayer=OnlineMediaPlayer.getInstance();

    final OkHttpClient client = new OkHttpClient();

    String isSleep = "false";
    String waitNext = "false";
    String voicePath = "";
    String command = "0";
    String content = "";
    String musicDuration = "";
    String musicSize = "";
    String musicData = "";
    String musicName = "";
//    private CallBackInterface callBackInterface;
    int flag=0;
    Context context;


    public void playmusic(boolean flag) {

//            finishActivityListener.finishActivity();

//        finishActivityListener.finishActivity();
        try{
        finishActivityListener.finishActivity();}
        catch (Exception e){}
        Intent intent = new Intent(context, Album_list.class);
        if (flag) {
            intent.putExtra("title", "所有音乐");
        } else {
            intent.putExtra("title", "中文故事");
        }
        intent.putExtra("flag", -1);
        intent.putExtra("musicDuration", musicDuration);
        intent.putExtra("musicData", musicData);
        intent.putExtra("musicName", musicName);
        intent.putExtra("musicSize", musicSize);
        context.startActivity(intent);

    }

    public void get(final Context context, String voiceInput) {

        String url = String.format("http://47.94.165.157:8080/voice/get?isSleep=%s&waitNext=%s&voiceInput=%s", isSleep, waitNext, voiceInput);

        Log.e("url", url);

        this.context = context;

//        构造Request对象
//        采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
//                             Toast.makeText(context, "Get 失败", Toast.LENGTH_SHORT).show();
                         }
                         @Override
                         public void onResponse(Call call, final Response response) throws IOException {
                             final String severResult = response.body().string();
                             Log.e("respone", severResult);
                             try {
                                 JSONObject jobject = new JSONObject(severResult);
                                 if (jobject.getString("needWaitNext").equals("YES")) {
                                     waitNext = "true";
                                 } else if (jobject.getString("needWaitNext").equals("NO")) {
                                     waitNext = "false";
                                 }
                                 command = jobject.getString("command");
                                 switch (command) {
                                     case "-21":
                                         isSleep = "true";
                                         break;
                                     case "-22":
                                     case "-11":
                                         isSleep = "false";
                                         flag=2;
                                         voicePath = jobject.getString("voicePath");
                                         if(waitNext.equals("NO"))
                                         onlineMediaPlayer.play(voicePath);
                                         break;
                                     case "-8":
                                     case "-7":
                                         content = jobject.getString("content");
//                                         speakContent(content);
//                                         callBackInterface.callback(content);
                                         onlineMediaPlayer.stopAll();
                                         RecordBackground.callback(content,1);
                                         break;
                                     case "-12":
                                         flag=0;
                                         musicDuration = jobject.getString("forFangSheng_time");
                                         musicData = jobject.getString("voicePath");
                                         musicSize = jobject.getString("forFangSheng_size");
                                         musicName = jobject.getString("forFangSheng_name");
                                         content = jobject.getString("content");
//                                         if (content.length() > 0)
//                                             speakContent(content);
                                         onlineMediaPlayer.stop();
                                         if (waitNext.equals("false")) {
                                             if(content.length()>0)
                                             RecordBackground.callback(content,0);
                                             playmusic(true);
                                             Log.e("r","1");
                                         }
                                         else RecordBackground.callback(content,1);
                                         break;
                                     case "-13":
                                         flag=1;
                                         musicDuration = jobject.getString("forFangSheng_time");
                                         musicData = jobject.getString("voicePath");
                                         musicSize = jobject.getString("forFangSheng_size");
                                         musicName = jobject.getString("forFangSheng_name");;
                                         content = jobject.getString("content");
                                         onlineMediaPlayer.stop();
                                         if (waitNext.equals("false")) {
                                             RecordBackground.callback(content,0);
                                             playmusic(false);
                                             Log.e("s","1");
                                         }
                                         else{RecordBackground.callback(content,1);}
                                         break;
                                     case "-5":
                                         try {
                                             switch (flag){
                                                 case 0:
                                                     playmusic(true);
                                                     break;
                                                 case 1:
                                                     playmusic(false);
                                                     break;
                                                 case 2:
                                                     onlineMediaPlayer.play(voicePath);
                                                     break;
                                             }


                                         } catch (Exception e) {
                                             Log.e("playerro",e.toString());
                                         }
                                         break;
                                 }
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                     }
        );
//        callback(content);
    }
    private static FinishActivityListener finishActivityListener;

    public static void setFinishActivityListener(FinishActivityListener iFinishActivityListener) {
        finishActivityListener = iFinishActivityListener;
    }
    public interface FinishActivityListener {
        void finishActivity();
    }


}
