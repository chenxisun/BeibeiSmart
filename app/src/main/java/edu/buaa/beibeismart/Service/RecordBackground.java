package edu.buaa.beibeismart.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.buaa.beibeismart.Activity.Album_list;

import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.requestClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecordBackground extends Service {

    // 语音识别对象
    private SpeechRecognizer recognizer;

    //语音合成对象
    private static SpeechSynthesizer speaker;

    OnlineMediaPlayer onlineMediaPlayer;

    requestClient requestCli=new requestClient();

    //识别出来的句子
    StringBuilder sentence = null;
    static String requestRes = "";
    static String isSleep = "false";
    static String waitNext = "false";
    String voiceInput = "";
//    String voicePath = "";
//    String command = "0";
//    String content = "";
//    String musicDuration="";
//    String musicSize="";
//    String musicData="";
//    String musicName="";

//    public void playmusic(boolean flag) {
//        finishActivityListener.finishActivity();
//
//        Intent intent = new Intent(this, Album_list.class);
//        if (flag)
//        {intent.putExtra("title", "所有音乐");}
//        else{intent.putExtra("title", "中文故事");}
//        intent.putExtra("flag",-1);
//        intent.putExtra("musicDuration", musicDuration);
//        intent.putExtra("musicData",musicData);
//        intent.putExtra("musicName",musicName);
//        intent.putExtra("musicSize",musicSize);
//        startActivity(intent);
//    }

    private RecognizerListener recognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        /**
         * 开始录音
         */
        @Override
        public void onBeginOfSpeech() {
            Toast.makeText(getApplicationContext(), "开始录音", Toast.LENGTH_SHORT).show();
        }

        /**
         * 结束录音
         */
        @Override
        public void onEndOfSpeech() {
            //结束录音后，根据识别出来的句子，通过语音合成进行反馈
            Toast.makeText(getApplicationContext(), "结束录音", Toast.LENGTH_SHORT).show();
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            if(recognizer.isListening()) {
                recognizer.stopListening();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                recognizer.startListening(recognizerListener);
            }
        }


        private void getRemoteData(final String url) {
            new Thread() {
                @Override
                public void run() {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .get().build();
                    try {
                        Response response = client.newCall(request).execute();
                        requestRes = response.body().string();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }

        /**
         * 听写结果回调接口 , 返回Json格式结果
         * @param recognizerResult  json结果对象
         * @param b                 等于true时会话结束
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            try {
                //处理json结果
                JSONObject jsonObject = new JSONObject(result);
                JSONArray words = jsonObject.getJSONArray("ws");
                //拼成句子
                sentence = new StringBuilder("");
                for (int i = 0; i < words.length(); i++) {
                    JSONObject word = words.getJSONObject(i);
                    JSONArray subArray = word.getJSONArray("cw");
                    JSONObject subWord = subArray.getJSONObject(0);
                    String character = subWord.getString("w");
                    sentence.append(character);
                }

                //打印
                Log.e("TAG", sentence.toString());

                voiceInput = sentence.toString();
                if (voiceInput.length()>0) {

//                    String url = String.format("http://47.94.165.157:8080/voice/get?isSleep=%s&waitNext=%s&voiceInput=%s", isSleep, waitNext, voiceInput);
//                    Log.e("url", url);
                    if (!isPlaying())
                    requestCli.get(getApplicationContext(), voiceInput);
//                    getRemoteData(url);
//                    Log.e("callback3", requestRes);
                }

//                    try {
//                        JSONObject jobject = new JSONObject(requestRes);
//                        if (jobject.getString("needWaitNext").equals("YES")) {
//                            waitNext = "true";
//                        } else if (jobject.getString("needWaitNext").equals("NO")) {
//                            waitNext = "false";
//                        }
//                        command = jobject.getString("command");
//
//                        switch (command) {
//                            case "-21":
//                                isSleep = "true";
//                                break;
//                            case "-22":
//                            case "-11":
//                                isSleep = "false";
//                                voicePath = jobject.getString("voicePath");
//                                onlineMediaPlayer.play(voicePath);
//                                break;
//                            case "-8":
//                            case "-7":
//                                content = jobject.getString("content");
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        speaker.startSpeaking(content, synthesizerListener);
//                                    }
//                                }).start();
//                            case "-12":
//                                musicDuration = jobject.getString("forFangSheng_time");
//                                musicData=jobject.getString("voicePath");
//                                musicSize=jobject.getString("forFangSheng_size");
//                                musicName="";
//                                if (waitNext.equals("NO")){
//                                playmusic(true);}
//                            case "-13":
//                                musicDuration = jobject.getString("forFangSheng_time");
//                                musicData=jobject.getString("voicePath");
//                                musicSize=jobject.getString("forFangSheng_size");
//                                musicName="";
//                                if (waitNext.equals("NO")){
//                                playmusic(false);}
//                            case "-5":
//                                try{
//                                playmusic(false);}
//                                catch(Exception e){
//                                }


//                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                }


//                startActivity(new Intent(getBaseContext(), MainActivity.class));

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        speaker.startSpeaking(sentence.toString(),synthesizerListener);
//                    }
//                }).start();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }

        /**
         * 会话发生错误回调接口
         * @param speechError
         */
        @Override
        public void onError(SpeechError speechError) {

        }


        /**
         * 扩展用接口
         */
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
        }
    };

    //语音合成监听器
    private static SynthesizerListener synthesizerListener = new SynthesizerListener() {
        /**
         * 开始播放
         */
        @Override
        public void onSpeakBegin() {

        }

        /**
         * 缓冲进度回调
         */
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        /**
         * 暂停播放
         */
        @Override
        public void onSpeakPaused() {

        }

        /**
         * 恢复播放回调接口
         */
        @Override
        public void onSpeakResumed() {

        }

        /**
         * 播放进度回调
         */
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        /**
         * 会话结束回调接口，没有错误时，error为null
         */
        @Override
        public void onCompleted(SpeechError speechError) {

        }

        /**
         * 会话事件回调接口
         */
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    //初始化监听器。
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d("TAG", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(getApplicationContext(), "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        onlineMediaPlayer = OnlineMediaPlayer.getInstance();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5abcd50a");
        //获取录音按钮视图

        //初始化语音对象
        recognizer = SpeechRecognizer.createRecognizer(this, mInitListener);
        speaker = SpeechSynthesizer.createSynthesizer(this, mInitListener);

        //设置听写参数
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置为中文
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置为普通话
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        recognizer.setParameter(SpeechConstant.VAD_BOS, "8000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        recognizer.setParameter(SpeechConstant.VAD_EOS, "1000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        recognizer.setParameter(SpeechConstant.ASR_PTT, "1");

        recognizer.setParameter(SpeechConstant.ASR_PTT, "0");

        //设置发音人
        speaker.setParameter(SpeechConstant.VOICE_NAME, "nannan");
        //设置语速
        speaker.setParameter(SpeechConstant.SPEED, "30");
        //设置音量，范围0~100
        speaker.setParameter(SpeechConstant.VOLUME, "200");
        //设置云端
        speaker.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置音调
        speaker.setParameter(SpeechConstant.PITCH, "50");
        // 设置播放器音频流类型
        speaker.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        speaker.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，需要申请WRITE_EXTERNAL_STORAGE权限，如不需保存注释该行代码
        speaker.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

        recognizer.startListening(recognizerListener);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private static FinishActivityListener finishActivityListener;

    public static void setFinishActivityListener(FinishActivityListener iFinishActivityListener) {
        finishActivityListener = iFinishActivityListener;
    }

    public interface FinishActivityListener {
        void finishActivity();
    }

    private static CallBackInterface callBackInterface;

//    public static void callback(String request) {
//        requestRes = request;
//        new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    speaker.startSpeaking(requestRes, synthesizerListener);
//                                }
//                            }).start();
////        isSleep=isSleepy;
////        waitNext=waitNexty;
//    }

    public static void speakContent(final String content){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                speaker.startSpeaking(content, synthesizerListener);
//            }
//        }).start();
    }
    public static  boolean isPlaying(){
        return speaker.isSpeaking();
    }

    public interface CallBackInterface {
        /**
         * 这是一个回调函数，用于回答者B知道答案后给提问者A回电话，并告知提问者A答案是什么
         * 这个回电话的方式callBack是提问者A确定的，所以这个方法的实现类是A类
         * 这个回电话的内容result是回答者B提供的，所以这个变量的值是在B类中确定的
         */
        void callback(String result);
    }


}
