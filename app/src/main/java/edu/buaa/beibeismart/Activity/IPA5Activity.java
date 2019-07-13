package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class IPA5Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa5);
        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa101).setOnClickListener(this);
        findViewById(R.id.ipa102).setOnClickListener(this);
        findViewById(R.id.ipa103).setOnClickListener(this);
        findViewById(R.id.ipa111).setOnClickListener(this);
        findViewById(R.id.ipa112).setOnClickListener(this);
        findViewById(R.id.ipa113).setOnClickListener(this);
        findViewById(R.id.chicken).setOnClickListener(this);
        findViewById(R.id.ostrich).setOnClickListener(this);
        findViewById(R.id.jar).setOnClickListener(this);
        findViewById(R.id.drink).setOnClickListener(this);
        findViewById(R.id.beds).setOnClickListener(this);
        findViewById(R.id.cats).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(IPA5Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa101:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa101);
                mediaplayer.start();
                break;
            case R.id.ipa102:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa102);
                mediaplayer.start();
                break;
            case R.id.ipa103:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa103);
                mediaplayer.start();
                break;
            case R.id.ipa111:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa111);
                mediaplayer.start();
                break;
            case R.id.ipa112:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa112);
                mediaplayer.start();
                break;
            case R.id.ipa113:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa113);
                mediaplayer.start();
                break;

            case R.id.beds:

                mediaplayer = MediaPlayer.create(this, R.raw.beds);
                mediaplayer.start();
                break;
            case R.id.drink:

                mediaplayer = MediaPlayer.create(this, R.raw.drink);
                mediaplayer.start();
                break;
            case R.id.chicken:

                mediaplayer = MediaPlayer.create(this, R.raw.chick);
                mediaplayer.start();
                break;
            case R.id.jar:

                mediaplayer = MediaPlayer.create(this, R.raw.jar);
                mediaplayer.start();
                break;
            case R.id.ostrich:

                mediaplayer = MediaPlayer.create(this, R.raw.ostrich);
                mediaplayer.start();
                break;
            case R.id.cats:
                mediaplayer = MediaPlayer.create(this, R.raw.cats);
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
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
