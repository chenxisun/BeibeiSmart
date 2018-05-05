package edu.buaa.beibeismart.Fragment;


import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;

import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.item_attrib;

import static android.media.AudioManager.STREAM_MUSIC;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishPlayerFragment extends BaseFragment {

    TextView englishplayname;
    ImageButton englishplayandpause;
    ImageButton englishreplay;
    TextView totalTime_text;
    TextView playingTime_text;
    SeekBar playingProcess;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Boolean playtag;
    private Handler hangler = new Handler();
    static item_attrib item;
    int totalTime = (int) item.getDuration();
    String[] musicname = item.getName().split("\\.");


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
        playingProcess = getActivity().findViewById(R.id.englishplayerseek);
        totalTime_text = getActivity().findViewById(R.id.englishplayertotalTime);
        playingTime_text = getActivity().findViewById(R.id.englishplayerplayingTime);

        //初始化播放器
        initPlayer();

        //设置seekbar的用户改变音乐进度监听事件
        playingProcess.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateTime();
                mediaPlayer.seekTo(playingProcess.getProgress());
            }
        });

        //设置播放和暂停
        englishplayandpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playtag = false;
                    englishplayandpause.setBackgroundResource(R.mipmap.englishplay);
                } else {
                    mediaPlayer.start();
                    englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
                    playtag = true;
                    refreshTime();
                }

            }
        });
        //设置重播
        englishreplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mediaPlayer.stop();
                // playtag = false;
                //  try {
                //     mediaPlayer.prepare();
                //  } catch (IOException e) {
                //    e.printStackTrace();
                //  }
                 if(mediaPlayer.isPlaying()){

                     playingProcess.setProgress(0);
                     playingTime_text.setText("00:00");
                     mediaPlayer.seekTo(0);
                     englishplayandpause.setBackgroundResource(R.mipmap.englishpause);

                 }else {
                     playingProcess.setProgress(0);
                     playingTime_text.setText("00:00");
                     mediaPlayer.seekTo(0);
                     mediaPlayer.start();
                     playtag = true;
                     refreshTime();
                     englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
                 }
                }

        });

    }

    private void initPlayer() {
        //判断歌曲是否收藏并获得歌曲播放路径与信息

        //给播放器一个标题
        englishplayname.setText(musicname[0]);

        //初始化播放器,显示音乐总时长,设置seekbar总时长和初始值
        try {
            mediaPlayer.setDataSource(item.getData());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String time = getFormatDateTime("mm:ss", totalTime);
        totalTime_text.setText(time);

        playingProcess.setProgress(0);
        playingProcess.setMax(totalTime);

    }


    //定义newInstance方法在打开该播放器时获得音乐对象
    public static EnglishPlayerFragment newInstance(item_attrib item_in) {
        item = item_in;
        //String music_name= item_in.getName();
        EnglishPlayerFragment fragment = new EnglishPlayerFragment();
        Bundle args = new Bundle();
        //args.putString("param", music_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 退出当前fragment时候关闭播放器和进度线程
        mediaPlayer.release();
        Thread.interrupted();
    }

    //定义转换时间格式的方法
    public static String getFormatDateTime(String pattern, int dateTime) {
        Date d = new Date(dateTime);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    //进度条改变时候更新时间和播放进度
    public void updateTime() {
        int time = playingProcess.getProgress()+1000;
        if (time >= totalTime-1000) {
            time = 0;
            mediaPlayer.seekTo(0);
            mediaPlayer.pause();
            playtag=false;
            englishplayandpause.setBackgroundResource(R.mipmap.englishplay);
        }
        playingProcess.setProgress(time);
        playingTime_text.setText(getFormatDateTime("mm:ss", time));

    }

    //启动线程改变画面-进度条移动和更新时间
    public void refreshTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (playtag&&playingProcess.getProgress() < totalTime-1000) {

                    hangler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateTime();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}

