package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Field;

import edu.buaa.beibeismart.Media.SlackMusicPlayer;
import edu.buaa.beibeismart.R;

public class KebencontentActivity extends BaseActivity implements View.OnClickListener {

     MediaPlayer mediaPlayer;
    ImageView kebenpic1;
    Button kebenplay1;
    Button kebenplay2;
    Button playandpause;
    Button replay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebencontent);

        Intent intent=getIntent();
        final String danyuan=intent.getStringExtra("danyuan");
        final String zhangjie=intent.getStringExtra("zhangjie");
        Toast.makeText(this,"当前位于："+danyuan+"_"+zhangjie,Toast.LENGTH_SHORT).show();

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.release();
                if(danyuan.contains("3a")){
                Intent intent = new Intent(KebencontentActivity.this,Keben3Acontent2Activity.class);
                intent.putExtra("danyuan",danyuan);
                startActivity(intent);}else if (danyuan.contains("3b")){
                    Intent intent = new Intent(KebencontentActivity.this,Keben3Bcontent2Activity.class);
                    intent.putExtra("danyuan",danyuan);
                    startActivity(intent);
                }
            }
        });
        kebenpic1=findViewById(R.id.kebenpic1);
        kebenplay1=findViewById(R.id.kebenplay1);
        kebenplay2=findViewById(R.id.kebenplay2);
        playandpause=findViewById(R.id.playandpause);
        replay=findViewById(R.id.replay);


        kebenplay1.setOnClickListener(this);
        kebenplay2.setOnClickListener(this);
        kebenplay1.setEnabled(false);
        kebenplay2.setEnabled(false);
        playandpause.setEnabled(false);
        replay.setEnabled(false);

        if(danyuan.contains("3a")){
            if(zhangjie.equals("storytime")||zhangjie.equals("cartoontime")){
                mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+zhangjie));
                kebenplay1.setEnabled(true);
                playandpause.setEnabled(true);
                replay.setEnabled(true);
            }else if (zhangjie.equals("songtime")){
                mediaPlayer=MediaPlayer.create(this,R.raw.bear);
                kebenplay1.setEnabled(true);
                kebenplay2.setEnabled(true);
                playandpause.setEnabled(true);
                replay.setEnabled(true);
            }else if(zhangjie.equals("checkouttime")){
                if(danyuan.contains("unit1")||danyuan.contains("unit4")||danyuan.contains("unit8")){
                    mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+zhangjie));
                    kebenplay1.setEnabled(true);
                    playandpause.setEnabled(true);
                    replay.setEnabled(true);
                } else{
                    mediaPlayer=MediaPlayer.create(this,R.raw.bear);
                }
            }
            else if(zhangjie.equals("wordlist")){
                if(danyuan.contains("unit6")){
                    mediaPlayer=MediaPlayer.create(this,R.raw.bear);
                    kebenplay1.setEnabled(true);
                }else {
                    mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+zhangjie));
                    kebenplay1.setEnabled(true);
                    playandpause.setEnabled(true);
                    replay.setEnabled(true);
                }
            }else {
                mediaPlayer=MediaPlayer.create(this,R.raw.bear);
            }
        }else if(danyuan.contains("3b")){
            if(zhangjie.equals("storytime")||zhangjie.equals("cartoontime")){
                mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+zhangjie));
                kebenplay1.setEnabled(true);
                playandpause.setEnabled(true);
                replay.setEnabled(true);
            }else if (zhangjie.equals("soundtime")){
                mediaPlayer=MediaPlayer.create(this,R.raw.bear);
                kebenplay1.setEnabled(true);
                kebenplay2.setEnabled(true);
                playandpause.setEnabled(true);
                replay.setEnabled(true);
            }else{
                mediaPlayer=MediaPlayer.create(this,R.raw.bear);
            }

        } else{
            mediaPlayer=MediaPlayer.create(this,R.raw.bear);
        }


        playandpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.seekTo(0);
            }
        });

        kebenpic1.setImageResource(getimages(danyuan+"_"+zhangjie));

        if(danyuan.contains("3a")&&zhangjie.equals("songtime")) {
            kebenplay1.setText("播放音频：" + "letter time");
            kebenplay2.setText("播放音频:"+"song/rhyme time");
        }else if(danyuan.contains("3b")&&zhangjie.equals("soundtime")) {
            kebenplay1.setText("播放音频：" + "sound time");
            kebenplay2.setText("播放音频:"+"song/rhyme time");
        } else{
            kebenplay1.setText("播放音频：" + zhangjie);
        }

    }

    @Override
    protected void initView() {



    }


    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.kebenplay1:
              Intent intent=getIntent();
              final String danyuan=intent.getStringExtra("danyuan");
              final String zhangjie=intent.getStringExtra("zhangjie");
              if(
                      danyuan.equals("keben3a_unit6")&&zhangjie.equals("wordlist")){
              Toast.makeText(this,"暂时缺少音频~",Toast.LENGTH_SHORT).show();
              }else if (danyuan.contains("3a")&&zhangjie.equals("songtime")){
                  mediaPlayer.reset();
                  mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+"lettertime"));
                  mediaPlayer.start();
              }else if(danyuan.contains("3b")&&zhangjie.equals("soundtime")){
                  mediaPlayer.reset();
                  mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan+"_"+zhangjie));
                  mediaPlayer.start();
              }
                  else{
                  mediaPlayer.start();
              }
              break;
          case R.id.kebenplay2:
              Intent intent1=getIntent();
              final String danyuan1=intent1.getStringExtra("danyuan");
              final String zhangjie1=intent1.getStringExtra("zhangjie");
              if(danyuan1.equals("keben3a_unit6")){
                  mediaPlayer.reset();
                  Toast.makeText(this,"暂时缺少音频~",Toast.LENGTH_SHORT).show();
              }else if(danyuan1.equals("keben3b_unit8")){
                  mediaPlayer.reset();
              Toast.makeText(this,"暂时缺少音频~",Toast.LENGTH_SHORT).show();
              }
              else if(danyuan1.contains("3b")){
                  mediaPlayer.reset();
                  mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan1+"_"+"songtime"));
                  mediaPlayer.start();
              }
              else {
              mediaPlayer.reset();
              mediaPlayer= MediaPlayer.create(this,getmusicID(danyuan1+"_"+zhangjie1));
              mediaPlayer.start();
              }
              break;
              default:
                  break;
      }
    }

    public static int getimages(String name){
        Class drawable = R.drawable.class;
        Field field = null;
        try {
            field =drawable.getField(name);
            int images = field.getInt(field.getName());
            return images;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int getmusicID(String name){
        Class raw = R.raw.class;
        Field field = null;
        try {
            field =raw.getField(name);
            int music = field.getInt(field.getName());
            return music;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        mediaPlayer.release();
    }
}
