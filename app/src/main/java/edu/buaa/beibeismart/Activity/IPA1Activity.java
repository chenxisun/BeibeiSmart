package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import edu.buaa.beibeismart.R;

public class IPA1Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa1);
        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa11).setOnClickListener(this);
        findViewById(R.id.ipa12).setOnClickListener(this);
        findViewById(R.id.ipa13).setOnClickListener(this);
        findViewById(R.id.ipa14).setOnClickListener(this);
        findViewById(R.id.ipa21).setOnClickListener(this);
        findViewById(R.id.ipa22).setOnClickListener(this);
        findViewById(R.id.ipa23).setOnClickListener(this);
        findViewById(R.id.ipa31).setOnClickListener(this);
        findViewById(R.id.ipa32).setOnClickListener(this);
        findViewById(R.id.ipa33).setOnClickListener(this);
        findViewById(R.id.ipa34).setOnClickListener(this);
        findViewById(R.id.ipa35).setOnClickListener(this);
        findViewById(R.id.dog).setOnClickListener(this);
        findViewById(R.id.book).setOnClickListener(this);
        findViewById(R.id.lamb).setOnClickListener(this);
        findViewById(R.id.bird).setOnClickListener(this);
        findViewById(R.id.walk).setOnClickListener(this);
        findViewById(R.id.bar).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(IPA1Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa11:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa11);
                mediaplayer.start();
                break;
            case R.id.ipa12:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa12);
                mediaplayer.start();
                break;
            case R.id.ipa13:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa13);
                mediaplayer.start();
                break;
            case R.id.ipa14:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa14);
                mediaplayer.start();
                break;
            case R.id.ipa21:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa21);
                mediaplayer.start();
                break;
            case R.id.ipa22:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa22);
                mediaplayer.start();
                break;
            case R.id.ipa23:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa23);
                mediaplayer.start();
                break;
            case R.id.ipa31:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa31);
                mediaplayer.start();
                break;
            case R.id.ipa32:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa32);
                mediaplayer.start();
                break;
            case R.id.ipa33:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa33);
                mediaplayer.start();
                break;
            case R.id.ipa34:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa34);
                mediaplayer.start();
                break;
            case R.id.ipa35:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa35);
                mediaplayer.start();
                break;
            case R.id.bar:

                mediaplayer=MediaPlayer.create(this,R.raw.bar);
                mediaplayer.start();
                break;
            case R.id.walk:

                mediaplayer=MediaPlayer.create(this,R.raw.walk);
                mediaplayer.start();
                break;
            case R.id.bird:

                mediaplayer=MediaPlayer.create(this,R.raw.bird);
                mediaplayer.start();
                break;
            case R.id.book:

                mediaplayer=MediaPlayer.create(this,R.raw.book);
                mediaplayer.start();
                break;
            case R.id.dog:

                mediaplayer=MediaPlayer.create(this,R.raw.dog);
                mediaplayer.start();
                break;
            case R.id.lamb:
                mediaplayer=MediaPlayer.create(this,R.raw.lamb);
                mediaplayer.start();
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaplayer.stop();
        mediaplayer.release();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
