package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class IPA6Activity extends BaseActivity implements View.OnClickListener {
    MediaPlayer mediaplayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa6);
        findViewById(R.id.btn_BackIPALearn).setOnClickListener(this);
        findViewById(R.id.ipa121).setOnClickListener(this);
        findViewById(R.id.ipa122).setOnClickListener(this);
        findViewById(R.id.ipa123).setOnClickListener(this);
        findViewById(R.id.ipa131).setOnClickListener(this);
        findViewById(R.id.ipa141).setOnClickListener(this);
        findViewById(R.id.ipa142).setOnClickListener(this);
        findViewById(R.id.no).setOnClickListener(this);
        findViewById(R.id.mud).setOnClickListener(this);
        findViewById(R.id.swan).setOnClickListener(this);
        findViewById(R.id.ink).setOnClickListener(this);
        findViewById(R.id.line).setOnClickListener(this);
        findViewById(R.id.visual).setOnClickListener(this);


    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_BackIPALearn:
                Intent intent = new Intent(IPA6Activity.this, IpaLearnActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa121:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa121);
                mediaplayer.start();
                break;
            case R.id.ipa122:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa122);
                mediaplayer.start();
                break;
            case R.id.ipa123:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa123);
                mediaplayer.start();
                break;
            case R.id.ipa131:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa131);
                mediaplayer.start();
                break;
            case R.id.ipa141:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa141);
                mediaplayer.start();
                break;
            case R.id.ipa142:

                mediaplayer=MediaPlayer.create(this,R.raw.ipa142);
                mediaplayer.start();
                break;

            case R.id.no:

                mediaplayer=MediaPlayer.create(this,R.raw.no);
                mediaplayer.start();
                break;
            case R.id.swan:

                mediaplayer=MediaPlayer.create(this,R.raw.swan);
                mediaplayer.start();
                break;
            case R.id.visual:

                mediaplayer=MediaPlayer.create(this,R.raw.visual);
                mediaplayer.start();
                break;
            case R.id.ink:

                mediaplayer=MediaPlayer.create(this,R.raw.ink);
                mediaplayer.start();
                break;
            case R.id.line:

                mediaplayer=MediaPlayer.create(this,R.raw.line);
                mediaplayer.start();
                break;
            case R.id.mud:
                mediaplayer=MediaPlayer.create(this,R.raw.mud);
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
