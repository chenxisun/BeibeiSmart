package edu.buaa.beibeismart.Fragment;


import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import edu.buaa.beibeismart.Activity.Album_list;
import edu.buaa.beibeismart.Media.SlackMusicPlayer;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.item_attrib;


/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishPlayerFragment extends BaseFragment {

    TextView englishplayname;
    ImageButton englishplayandpause;
    ImageButton englishreplay;
    ImageButton musicCellection;
    TextView totalTime_text;
    TextView playingTime_text;
    SeekBar playingProcess;

//    OnlineMediaPlayer mediaPlayer = OnlineMediaPlayer.RefreshPlayer();

    SlackMusicPlayer slackMusicPlayer = SlackMusicPlayer.instance;

    //OnlineMediaPlayer mediaPlayer=OnlineMediaPlayer.getInstance();
    Boolean playtag;
    Handler hangler = new Handler();
    //    static item_attrib item;
    static item_attrib item = null;
    String[] musicname;
    int totalTime;
    String musicstyle;
    String playerPath;
    File file;
    playerHandler PlayerHandler = new playerHandler();
    testWorkerThread t = new testWorkerThread();


    public EnglishPlayerFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_english_player, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        //设置按钮的监控事件
        englishplayname = getActivity().findViewById(R.id.EnglishPlayerName);
        englishplayandpause = getActivity().findViewById(R.id.btn_EnglishPlayandPause);
        englishreplay = getActivity().findViewById(R.id.btn_EnglishReplay);
        musicCellection = getActivity().findViewById(R.id.btn_music_collection);
        playingProcess = getActivity().findViewById(R.id.englishplayerseek);
        totalTime_text = getActivity().findViewById(R.id.englishplayertotalTime);
        playingTime_text = getActivity().findViewById(R.id.englishplayerplayingTime);

        englishreplay.setEnabled(false);
        englishplayandpause.setEnabled(false);
        musicCellection.setEnabled(false);


        //设置seekbar的用户改变音乐进度监听事件
        playingProcess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                slackMusicPlayer.pause();
                playtag = false;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (playingProcess.getProgress() == playingProcess.getMax()) {
//                    slackMusicPlayer.seekTo(0);
//                    slackMusicPlayer.pause();
////                        mediaPlayer.pause();
//                    playtag = false;
//                    englishplayandpause.setBackgroundResource(R.mipmap.englishplay);
//                    playingProcess.setProgress(slackMusicPlayer.getCurrentPosition());
//                    playingTime_text.setText(getFormatDateTime("mm:ss", slackMusicPlayer.getCurrentPosition()));
                    setPlay(0);

                } else {
//                    slackMusicPlayer.seekTo(playingProcess.getProgress());
//                    slackMusicPlayer.continuePlay();
//                    englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
//                    playtag = true;
//                    refreshTime();
                    setPlay(playingProcess.getProgress());
                }

//                mediaPlayer.seekTo(playingProcess.getProgress());

            }
        });

        //设置播放和暂停
        englishplayandpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t.isAlive()) {
                } else {

                    if (slackMusicPlayer.isPlaying()) {
                      setPause();
                    } else {
                    setContinuePlay();
                    }
                }
            }
        });
        //设置重播
        englishreplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t.isAlive()) {
                } else {
                        setPlay(0);
                        setContinuePlay();
//                    if (slackMusicPlayer.isPlaying()) {
//                        playingProcess.setProgress(0);
//                        playingTime_text.setText("00:00");
////                        mediaPlayer.seekTo(0);
//                        slackMusicPlayer.seekTo(0);
//                        englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
//
//                    } else {
//                        playingProcess.setProgress(0);
//                        playingTime_text.setText("00:00");
//
////                        mediaPlayer.seekTo(0);
//
//////                        mediaPlayer.start();
//
//                        slackMusicPlayer.continuePlay();
//                        slackMusicPlayer.seekTo(0);
//
//                        playtag = true;
//                        refreshTime();
//                        englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
//                    }
                }
            }
        });

        musicCellection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (t.isAlive()) {
                } else {
                    if (item.getData().contains("http")) {
                        String musicstyle = item.getData().substring(item.getData().length() - 4, item.getData().length());
                        String playerPath = Environment.getExternalStorageDirectory().toString() + "/voice/" + item.getName() + musicstyle;
                        File file = new File(playerPath);
                        if (file.exists()) {
                            Toast.makeText(getActivity(), "下载过啦~在收藏中播放更快哦", Toast.LENGTH_SHORT).show();
                        } else {
                            //创建下载任务,downloadUrl就是下载链接
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(item.getData()));
                            //指定下载路径和下载文件名
                            request.setDestinationInExternalPublicDir("/voice/", item.getName() + musicstyle);
                            //获取下载管理器
                            DownloadManager downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                            //将下载任务加入下载队列，否则不会进行下载
                            downloadManager.enqueue(request);
                            Toast.makeText(getActivity(), "开始下载~", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                        mediaPlayer.pause();
                        slackMusicPlayer.pause();
                        playtag = false;
                        slackMusicPlayer.release();
//                        mediaPlayer.reset();
//                        mediaPlayer.reset();
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File file = new File(item.getData());
                        Uri uri = Uri.fromFile(file);
                        intent.setData(uri);
                        getActivity().sendBroadcast(intent);
                        file.delete();
                        Toast.makeText(getActivity(), "歌曲已经删除~", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Album_list.setStartPlayerListener(new Album_list.StartPlayerListener() {

            @Override
            public void startPlay(item_attrib item) throws IOException {

                slackMusicPlayer.release();
                Log.e("time", "00");
                setPlayer(item);
            }
        });
    }


    public void setPlayer(item_attrib item1) throws IOException {

        item = item1;
        musicname = item.getName().split("\\.");
        totalTime = (int) item.getDuration();
        musicstyle = item.getData().substring(item.getData().length() - 4, item.getData().length());
        playerPath = Environment.getExternalStorageDirectory().toString() + "/voice/" + item.getName() + musicstyle;
        file = new File(playerPath);
        playingTime_text.setText("00:00");

        //给播放器一个标题
        englishplayname.setText(musicname[0]);

        if (item.getData().contains("http")) {
            Toast.makeText(getActivity(), "正在加载歌曲~", Toast.LENGTH_SHORT).show();
            if (file.exists()) {
                musicCellection.setBackgroundResource(R.drawable.englishplaycollected);
            } else {
                musicCellection.setBackgroundResource(R.drawable.englishplaycollection);
            }
        } else {
            musicCellection.setBackgroundResource(R.drawable.delete);
        }

        // 显示音乐总时长,设置seekbar总时长和初始值
        String time = getFormatDateTime("mm:ss", totalTime);
        totalTime_text.setText(time);
        playingProcess.setProgress(0);
        playingProcess.setMax(totalTime);
        englishplayandpause.setBackgroundResource(R.drawable.englishpause);
        // 判断歌曲是否收藏并获得歌曲播放路径与信息
        Log.e("time", "01");
        t.start();
//        while( playingProcess.getProgress() >0 ){
        englishreplay.setEnabled(true);
        englishplayandpause.setEnabled(true);
        musicCellection.setEnabled(true);
//     }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出当前fragment时候关闭播放器和进度线程
        playtag = false;
        // while (t.isAlive()){}
        slackMusicPlayer.release();

        Log.e("time", "07");

    }

    //定义转换时间格式的方法
    public static String getFormatDateTime(String pattern, int dateTime) {
        Date d = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    //进度条改变时候更新时间和播放进度
    public void updateTime() {

        int time = slackMusicPlayer.getCurrentPosition();

        if (time >= totalTime-100) {

            setPlay(0);
        }
        setTimeandProsess();

    }

    //启动线程改变画面-进度条移动和更新时间
    public void refreshTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (playtag && playingProcess.getProgress() < totalTime) {
                    hangler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateTime();
                        }
                    });
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Log.e("thread refresh", "finish");
    }

    public void setPlay(int position){
        if(position==0){
            slackMusicPlayer.seekTo(0);
            slackMusicPlayer.pause();
            playtag = false;
            englishplayandpause.setBackgroundResource(R.drawable.englishplay);
            setTimeandProsess();
        }else{
            slackMusicPlayer.seekTo(playingProcess.getProgress());
            setContinuePlay();
        }

    }

    public void setPause(){
        slackMusicPlayer.pause();
        playtag = false;
        englishplayandpause.setBackgroundResource(R.drawable.englishplay);
    }

    public void setContinuePlay(){
        slackMusicPlayer.continuePlay();
        englishplayandpause.setBackgroundResource(R.drawable.englishpause);
        playtag = true;
        refreshTime();
    }

     public void setTimeandProsess(){
         playingProcess.setProgress(slackMusicPlayer.getCurrentPosition());
         playingTime_text.setText(getFormatDateTime("mm:ss", slackMusicPlayer.getCurrentPosition()));
     }

    class playerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;

            playtag = true;
            refreshTime();
            Log.e("time", "06");
        }
    }

    class testWorkerThread extends Thread {
        @Override
        public void run() {
            Log.e("time", "02");

            if (file.exists()) {
                slackMusicPlayer.play(playerPath);
            } else {
                slackMusicPlayer.play(item.getData());
            }
            Log.e("time", "04");
            String s = "finish";
            Message msg = PlayerHandler.obtainMessage();
            msg.obj = s;
            PlayerHandler.sendMessage(msg);
            Log.e("time", "05");
        }
    }

}

