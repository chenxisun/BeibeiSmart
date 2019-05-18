package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class IPA3Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa3);

        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa61).setOnClickListener(this);
        findViewById(R.id.ipa62).setOnClickListener(this);
        findViewById(R.id.ipa63).setOnClickListener(this);
        findViewById(R.id.ipa71).setOnClickListener(this);
        findViewById(R.id.ipa72).setOnClickListener(this);
        findViewById(R.id.ipa73).setOnClickListener(this);
        findViewById(R.id.bread).setOnClickListener(this);
        findViewById(R.id.duck).setOnClickListener(this);
        findViewById(R.id.talk).setOnClickListener(this);
        findViewById(R.id.key).setOnClickListener(this);
        findViewById(R.id.pay).setOnClickListener(this);
        findViewById(R.id.eagle).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(IPA3Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa71:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa71);
                mediaplayer.start();
                break;
            case R.id.ipa72:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa72);
                mediaplayer.start();
                break;
            case R.id.ipa73:
                mediaplayer = MediaPlayer.create(this, R.raw.ipa73);
                mediaplayer.start();
                break;
            case R.id.ipa61:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa61);
                mediaplayer.start();
                break;
            case R.id.ipa62:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa62);
                mediaplayer.start();
                break;
            case R.id.ipa63:

                mediaplayer = MediaPlayer.create(this, R.raw.ipa63);
                mediaplayer.start();
                break;

            case R.id.bread:

                mediaplayer = MediaPlayer.create(this, R.raw.bread);
                mediaplayer.start();
                break;
            case R.id.duck:

                mediaplayer = MediaPlayer.create(this, R.raw.duck);
                mediaplayer.start();
                break;
            case R.id.talk:

                mediaplayer = MediaPlayer.create(this, R.raw.talk);
                mediaplayer.start();
                break;
            case R.id.pay:

                mediaplayer = MediaPlayer.create(this, R.raw.pay);
                mediaplayer.start();
                break;
            case R.id.eagle:

                mediaplayer = MediaPlayer.create(this, R.raw.eagle);
                mediaplayer.start();
                break;
            case R.id.key:
                mediaplayer = MediaPlayer.create(this, R.raw.key);
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
