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

import static edu.buaa.beibeismart.Service.RecordBackground.isPlaying;
import static edu.buaa.beibeismart.Service.RecordBackground.speakContent;


public class requestClient {
    OnlineMediaPlayer onlineMediaPlayer;

     String isSleep = "false";
     String waitNext = "false";
     String voiceInput = "";
     String voicePath = "";
     String command = "0";
     String content = "";
     String musicDuration = "";
     String musicSize = "";
     String musicData = "";
     String musicName = "";

     Context context;

    public void playmusic(boolean flag) {

        finishActivityListener.finishActivity();
        Intent intent = new Intent(context, Album_list.class);
        if (flag)
        {intent.putExtra("title", "所有音乐");}
        else{intent.putExtra("title", "中文故事");}
        intent.putExtra("flag",-1);
        intent.putExtra("musicDuration", musicDuration);
        intent.putExtra("musicData",musicData);
        intent.putExtra("musicName",musicName);
        intent.putExtra("musicSize",musicSize);
        context.startActivity(intent);
    }

    public void get(final Context context, String voiceInput) {

        String url = String.format("http://47.94.165.157:8080/voice/get?isSleep=%s&waitNext=%s&voiceInput=%s", isSleep, waitNext, voiceInput);
        Log.e("url", url);

        this.context=context;
        final OkHttpClient client = new OkHttpClient();
        //构造Request对象
        //采用建造者模式，链式调用指明进行Get请求,传入Get的请求地址
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        //异步调用并设置回调函数
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "Get 失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String severResult = response.body().string();
                Log.e("respone",severResult);
//                RecordBackground.callback(severResult);

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
                            voicePath = jobject.getString("voicePath");
                            onlineMediaPlayer.play(voicePath);
                            break;
                        case "-8":
                        case "-7":
                            content = jobject.getString("content");
                            if(isPlaying()==false)
                                speakContent(content);
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    speaker.startSpeaking(content, synthesizerListener);
//                                }
//                            }).start();
                            break;
                        case "-12":
                            musicDuration = jobject.getString("forFangSheng_time");
                            musicData = jobject.getString("voicePath");
                            musicSize = jobject.getString("forFangSheng_size");
                            musicName = "";
                            content = jobject.getString("content");
                            if(content.length()>0)
                            speakContent(content);
                            if (waitNext.equals("NO")) {
                                playmusic(true);
                            }
                            break;
                        case "-13":
                            musicDuration = jobject.getString("forFangSheng_time");
                            musicData = jobject.getString("voicePath");
                            musicSize = jobject.getString("forFangSheng_size");
                            musicName = "";
                            if (waitNext.equals("NO")) {
                                playmusic(false);
                            }
                            break;
                        case "-5":
                            try {
                                playmusic(false);
                            } catch (Exception e) {
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        callback(content);



    }
    private static RecordBackground.FinishActivityListener finishActivityListener;

    public static void setFinishActivityListener(RecordBackground.FinishActivityListener iFinishActivityListener) {
        finishActivityListener = iFinishActivityListener;
    }

    public interface FinishActivityListener {
        void finishActivity();
    }
}
