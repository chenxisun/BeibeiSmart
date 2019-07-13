package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class Ipa2Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa2);
        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa41).setOnClickListener(this);
        findViewById(R.id.ipa42).setOnClickListener(this);
        findViewById(R.id.ipa43).setOnClickListener(this);
        findViewById(R.id.ipa44).setOnClickListener(this);
        findViewById(R.id.ipa45).setOnClickListener(this);
        findViewById(R.id.ipa51).setOnClickListener(this);
        findViewById(R.id.ipa52).setOnClickListener(this);
        findViewById(R.id.ipa53).setOnClickListener(this);
        findViewById(R.id.goat).setOnClickListener(this);
        findViewById(R.id.mouse).setOnClickListener(this);
        findViewById(R.id.oyster).setOnClickListener(this);
        findViewById(R.id.bear).setOnClickListener(this);
        findViewById(R.id.beer).setOnClickListener(this);
        findViewById(R.id.write).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(Ipa2Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa41:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa41);
                mediaplayer.start();
                break;
            case R.id.ipa42:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa42);
                mediaplayer.start();
                break;
            case R.id.ipa43:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa43);
                mediaplayer.start();
                break;
            case R.id.ipa44:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa44);
                mediaplayer.start();
                break;
            case R.id.ipa45:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa45);
                mediaplayer.start();
                break;
            case R.id.ipa51:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa51);
                mediaplayer.start();
                break;
            case R.id.ipa52:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa52);
                mediaplayer.start();
                break;
            case R.id.ipa53:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa53);
                mediaplayer.start();
                break;

            case R.id.bear:

                mediaplayer = MediaPlayer.create(this, R.raw.bear);
                mediaplayer.start();
                break;
            case R.id.beer:

                mediaplayer = MediaPlayer.create(this, R.raw.beer);
                mediaplayer.start();
                break;
            case R.id.mouse:

                mediaplayer = MediaPlayer.create(this, R.raw.mouse);
                mediaplayer.start();
                break;
            case R.id.write:

                mediaplayer = MediaPlayer.create(this, R.raw.write);
                mediaplayer.start();
                break;
            case R.id.oyster:

                mediaplayer = MediaPlayer.create(this, R.raw.oyster);
                mediaplayer.start();
                break;
            case R.id.goat:
                mediaplayer = MediaPlayer.create(this, R.raw.goat);
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

