package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class IPA4Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa4);
        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa81).setOnClickListener(this);
        findViewById(R.id.ipa82).setOnClickListener(this);
        findViewById(R.id.ipa83).setOnClickListener(this);
        findViewById(R.id.ipa91).setOnClickListener(this);
        findViewById(R.id.ipa92).setOnClickListener(this);
        findViewById(R.id.ipa93).setOnClickListener(this);
        findViewById(R.id.ipa84).setOnClickListener(this);
        findViewById(R.id.ipa85).setOnClickListener(this);
        findViewById(R.id.ipa94).setOnClickListener(this);
        findViewById(R.id.ipa95).setOnClickListener(this);
        findViewById(R.id.zebra).setOnClickListener(this);
        findViewById(R.id.south).setOnClickListener(this);
        findViewById(R.id.they).setOnClickListener(this);
        findViewById(R.id.sit).setOnClickListener(this);
        findViewById(R.id.fish).setOnClickListener(this);
        findViewById(R.id.rain).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(IPA4Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa81:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa81);
                mediaplayer.start();
                break;
            case R.id.ipa82:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa82);
                mediaplayer.start();
                break;
            case R.id.ipa83:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa83);
                mediaplayer.start();
                break;
            case R.id.ipa91:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa91);
                mediaplayer.start();
                break;
            case R.id.ipa92:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa92);
                mediaplayer.start();
                break;
            case R.id.ipa93:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa93);
                mediaplayer.start();
                break;
            case R.id.ipa85:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa85);
                mediaplayer.start();
                break;
            case R.id.ipa84:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa84);
                mediaplayer.start();
                break;
            case R.id.ipa95:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa95);
                mediaplayer.start();
                break;
            case R.id.ipa94:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa94);
                mediaplayer.start();
                break;

            case R.id.fish:

                mediaplayer = MediaPlayer.create(this, R.raw.fish);
                mediaplayer.start();
                break;
            case R.id.south:

                mediaplayer = MediaPlayer.create(this, R.raw.south);
                mediaplayer.start();
                break;
            case R.id.they:

                mediaplayer = MediaPlayer.create(this, R.raw.they);
                mediaplayer.start();
                break;
            case R.id.zebra:

                mediaplayer = MediaPlayer.create(this, R.raw.zebra);
                mediaplayer.start();
                break;
            case R.id.sit:

                mediaplayer = MediaPlayer.create(this, R.raw.sit);
                mediaplayer.start();
                break;
            case R.id.rain:
                mediaplayer = MediaPlayer.create(this, R.raw.rain);
                mediaplayer.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
