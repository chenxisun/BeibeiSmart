package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.buaa.beibeismart.R;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    //BaseActivity activity = new BaseActivity();
    Button btnMusic,btnStory,btnBaike,btnEnglish,btnDownload;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        btnMusic = findViewById(R.id.btn_mainactivity_music);
        btnStory = findViewById(R.id.btn_mainactivity_story);
        btnBaike = findViewById(R.id.btn_mainactivity_baike);
        btnEnglish = findViewById(R.id.btn_mainactivity_learnenglish);
        btnDownload = findViewById(R.id.btn_mainactivity_download);
        btnMusic.setOnClickListener(this);
        btnStory.setOnClickListener(this);
        btnBaike.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_mainactivity_music:
                break;
            case R.id.btn_mainactivity_story:
                break;
            case R.id.btn_mainactivity_baike:
                break;
            case R.id.btn_mainactivity_learnenglish:
                intent = new Intent(MainActivity.this,LearnEnglishActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mainactivity_download:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

}
