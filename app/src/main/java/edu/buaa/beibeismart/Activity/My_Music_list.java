package edu.buaa.beibeismart.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import edu.buaa.beibeismart.R;

public class My_Music_list extends BaseActivity {

    public static final String Action = "edu.buaa.beibeismart.intent.music_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.music_list);
        CreatListener();

    }

    private void CreatListener() {
        findViewById(R.id.all_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(My_Music_list.this, Album_list.class);
                intent.putExtra("title", "中文儿歌");
                startActivity(intent);

            }
        });

        findViewById(R.id.love_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Music_list.this, Album_list.class);
                intent.putExtra("title", "我的收藏");
                startActivity(intent);

            }
        });
        findViewById(R.id.soft_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Music_list.this, Album_list.class);
                intent.putExtra("title", "轻音乐");
                startActivity(intent);

            }
        });

        findViewById(R.id.english_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Music_list.this, Album_list.class);
                intent.putExtra("title", "英文儿歌");
                startActivity(intent);
            }
        });
        findViewById(R.id.back_btn_music_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_Music_list.this,MainActivity.class));
            }
        });
    }


    @Override
    protected void initView() {


    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
