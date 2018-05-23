package edu.buaa.beibeismart.Fragment;


import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


import edu.buaa.beibeismart.Activity.Album_list;
import edu.buaa.beibeismart.Net.FileUtils;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.item_attrib;

import static android.media.AudioManager.STREAM_MUSIC;
import static com.iflytek.sunflower.config.a.r;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnglishPlayerFragment extends BaseFragment implements Album_list.stopMediaPlay {

    TextView englishplayname;
    ImageButton englishplayandpause;
    ImageButton englishreplay;
    ImageButton musicCellection;
    TextView totalTime_text;
    TextView playingTime_text;
    SeekBar playingProcess;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Boolean playtag;
    Handler hangler = new Handler();
    Thread thread=null;
    static item_attrib item;
    String[] musicname = item.getName().split("\\.");
    int totalTime= (int) item.getDuration();
    String musicstyle=item.getData().substring(item.getData().length()-4,item.getData().length());
    String playerPath= Environment.getExternalStorageDirectory().toString()+"/voice/"+item.getName()+musicstyle;
    File file = new File(playerPath);


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
        musicCellection=getActivity().findViewById(R.id.btn_music_collection);
        playingProcess = getActivity().findViewById(R.id.englishplayerseek);
        totalTime_text = getActivity().findViewById(R.id.englishplayertotalTime);
        playingTime_text = getActivity().findViewById(R.id.englishplayerplayingTime);

        //初始化播放器
        initPlayer();
        Album_list.setStopMediaplay(this);

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

        musicCellection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getData().contains("http")&&!file.exists()){
                    downLoad(item.getData(),item.getName()+musicstyle);
                    Toast.makeText(getActivity(),"正在下载~稍后将添加至收藏",Toast.LENGTH_LONG).show();
                }
                if(item.getData().contains("http")&&file.exists()){
                    Toast.makeText(getActivity(),"这首歌已经下载啦",Toast.LENGTH_SHORT).show();
                }

                if(!item.getData().contains("http")){

                    Toast.makeText(getActivity(),"这首歌已经在本地咯",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initPlayer() {
        //给播放器一个标题
        englishplayname.setText(musicname[0]);

        // 显示音乐总时长,设置seekbar总时长和初始值

        String time = getFormatDateTime("mm:ss", totalTime);
        totalTime_text.setText(time);

        playingProcess.setProgress(0);
        playingProcess.setMax(totalTime);

        // 判断歌曲是否收藏并获得歌曲播放路径与信息


       if(item.getData().contains("http")&&!file.exists()){

        // downLoad(item.getData(),item.getName()+musicstyle);

            //初始化播放器
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(item.getData());
                mediaPlayer.prepare();
                mediaPlayer.start();
                englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
                playtag = true;
                refreshTime();
            } catch (IOException e) {
                e.printStackTrace();
            }

           // Toast.makeText(getActivity(),"正在下载音乐~",Toast.LENGTH_LONG).show();

       }else if(file.exists()) {
           try {
               mediaPlayer.reset();
               mediaPlayer.setDataSource(playerPath);
               mediaPlayer.prepare();
               mediaPlayer.start();
               englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
               playtag = true;
               refreshTime();
           } catch (IOException e) {
               e.printStackTrace();
           }

       }else{
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(item.getData());
                mediaPlayer.prepare();
                mediaPlayer.start();
                englishplayandpause.setBackgroundResource(R.mipmap.englishpause);
                playtag = true;
                refreshTime();
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
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
        playtag=false;
        mediaPlayer.release();
       // thread.interrupt();


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
        if (time >= totalTime) {
            time = 0;
            mediaPlayer.seekTo(0);
            mediaPlayer.pause();
            playtag = false;
            englishplayandpause.setBackgroundResource(R.mipmap.englishplay);
        }
            playingProcess.setProgress(time);
            playingTime_text.setText(getFormatDateTime("mm:ss", time));

    }

    //启动线程改变画面-进度条移动和更新时间
    public void refreshTime() {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (playtag&&playingProcess.getProgress() < totalTime) {

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
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread.start();
    }

    //从服务器下载歌曲,图片
   public void downLoad(final String path, final String FileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(5000);
                    con.setConnectTimeout(5000);
                    con.setRequestProperty("Charset", "UTF-8");
                    con.setRequestMethod("GET");
                    if (con.getResponseCode()== 200) {
                        InputStream is = con.getInputStream();//获取输入流
                        FileOutputStream fileOutputStream = null;//文件输出流
                        if (is != null) {
                            FileUtils fileUtils = new FileUtils();
                            fileOutputStream = new FileOutputStream(fileUtils.createFile(FileName));//指定文件保存路径
                            byte[] buf = new byte[1024];
                            int ch;
                            while ((ch = is.read(buf)) != -1) {
                                fileOutputStream.write(buf, 0, ch);//将获取到的流写入文件中
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void stopPlay() {
        mediaPlayer.stop();
    }
}

