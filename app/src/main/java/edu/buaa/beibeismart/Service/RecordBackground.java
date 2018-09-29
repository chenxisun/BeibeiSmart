package edu.buaa.beibeismart.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;

import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.Media.SlackMusicPlayer;
import edu.buaa.beibeismart.requestClient;

public class RecordBackground extends Service {

    // 语音识别对象
    private SpeechRecognizer recognizer;

    //语音合成对象
    private static SpeechSynthesizer speaker;

    requestClient requestCli = new requestClient();

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
//            if (recognizer.isListening()) {
//                recognizer.stopListening();
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                recognizer.startListening(recognizerListener);
//            }
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
                if (voiceInput.length() > 0) {

                    if (!isPlaying())
                        requestCli.get(getApplicationContext(), voiceInput);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
            Log.e("RecordBackground","onSpeakBegin");
        }

        /**
         * 缓冲进度回调
         */
        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
            Log.e("RecordBackground","onBufferProgress");

        }

        /**
         * 暂停播放
         */
        @Override
        public void onSpeakPaused() {
            Log.e("RecordBackground","onSpeakPaused");

        }

        /**
         * 恢复播放回调接口
         */
        @Override
        public void onSpeakResumed() {
            Log.e("RecordBackground","onSpeakResumed");

        }

        /**
         * 播放进度回调
         */
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            Log.e("RecordBackground","onSpeakProgress");

        }

        /**
         * 会话结束回调接口，没有错误时，error为null
         */
        @Override
        public void onCompleted(SpeechError speechError) {
            Log.e("RecordBackground","onCompleted");

        }

        /**
         * 会话事件回调接口
         */
        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            Log.e("RecordBackground","onEvent");

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
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5abcd50a");
        //获取录音按钮视图

        //初始化语音对象
        recognizer = SpeechRecognizer.createRecognizer(this, mInitListener);


        //设置听写参数
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        //设置为中文
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置为普通话
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        recognizer.setParameter(SpeechConstant.VAD_BOS, "5000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        recognizer.setParameter(SpeechConstant.VAD_EOS, "1500");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        recognizer.setParameter(SpeechConstant.ASR_PTT, "1");

        recognizer.setParameter(SpeechConstant.ASR_PTT, "0");
        // create the synthesizer.
        speaker = SpeechSynthesizer.createSynthesizer(this, mInitListener);
        //设置发音人
        speaker.setParameter(SpeechConstant.VOICE_NAME, "nannan");
        //设置语速
        speaker.setParameter(SpeechConstant.SPEED, "60");
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

        speaker.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        speaker.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() +"/msc/tts.wav");

        // 设置音频保存路径，需要申请WRITE_EXTERNAL_STORAGE权限，如不需保存注释该行代码
//        speaker.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        recognizer.startListening(recognizerListener);

    }
    public void speak(String text) {


        Log.e("==========","=========语音播放code"+text);

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

    public static void speakContent(final String sent) {
//        new Thread(){
//            @Override
//            public void run() {
//                speaker.startSpeaking(sent, synthesizerListener);
//                speaker.stopSpeaking();
//            }
//        }.start();
    }
    public static boolean isPlaying() {
        return speaker.isSpeaking();
    }

    public static void callback(final String res,final int flag){
//        speaker.startSpeaking(res,synthesizerListener);
//        new Thread(){
//            @Override
//            public void run() {

                File dirFile = new File(Environment.getExternalStorageDirectory() +"/msc/tts.wav");
                while(dirFile.exists()){
                    Log.e("RecordBackground","1"+dirFile.exists());
                    dirFile.delete();
                }

                speaker.synthesizeToUri(res,Environment.getExternalStorageDirectory() +"/msc/tts.wav",synthesizerListener);
               while (!dirFile.exists()){}
                if (flag==1){
                    Log.e("RecordBackground",res);

//                    MediaPlayer speakingPlayer =new MediaPlayer();
//                    try {
//                        speakingPlayer.setDataSource(Environment.getExternalStorageDirectory() +"/msc/tts.wav");
//                        speakingPlayer.prepare();
//                        speakingPlayer.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    Log.e("RecordBackground","p"+dirFile.exists());

                    PlayLocalMusic(Environment.getExternalStorageDirectory() +"/msc/tts.wav");

                    Log.e("RecordBackground","2"+dirFile.exists());
                    while(dirFile.exists()){
                        Log.e("RecordBackground","3"+dirFile.exists());
                        dirFile.delete();
                    }
                    Log.e("RecordBackground","4"+dirFile.exists());
                }
            }
//        }.start();
//    }

    public static void PlayLocalMusic(String uri) {
//        speakingPlayer =new MediaPlayer();

          SlackMusicPlayer playerOnActivity=SlackMusicPlayer.instance;
//           MediaPlayer speakingPlayer=new MediaPlayer();
        OnlineMediaPlayer speakingPlayer=OnlineMediaPlayer.getInstance();

        if (playerOnActivity.isPlaying() && playerOnActivity.getCurrentPosition()>0) {
            Log.e("isplaying", String.valueOf(playerOnActivity.isPlaying()));
            playerOnActivity.pause();
            try {
                speakingPlayer.reset();
                speakingPlayer.setDataSource(uri);
                speakingPlayer.prepare();
                speakingPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (speakingPlayer.isPlaying()) {
            }
            playerOnActivity.continuePlay();

        } else {

            try {
                speakingPlayer.reset();
                speakingPlayer.setDataSource(uri);
                speakingPlayer.prepare();
                speakingPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}