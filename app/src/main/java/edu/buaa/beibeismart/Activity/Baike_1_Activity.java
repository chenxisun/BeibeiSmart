package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.R;


public class Baike_1_Activity extends BaseActivity implements View.OnClickListener{

    public String voicePath;
    OnlineMediaPlayer onlineMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike_1_);

        onlineMediaPlayer = OnlineMediaPlayer.getInstance();
        Intent intent = getIntent();
        if (intent != null){
            voicePath = intent.getStringExtra("voicePath");
        }
        //播放音频
        OnlineMediaPlayer.getInstance().play(voicePath);
        onlineMediaPlayer.play(voicePath);
        findViewById(R.id.btn_baike_1_return).setOnClickListener(this);

        ImageView imageView1 = findViewById(R.id.imageView1_1);
        ImageView imageView2 = findViewById(R.id.imageView2_1);
        ImageView imageView3 = findViewById(R.id.imageView3_1);
        ImageView imageView4 = findViewById(R.id.imageView4_1);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_baike_1_return:
                intent = new Intent(this,BaikeActivity.class);
                startActivity(intent);
                onlineMediaPlayer.stop();
                onlineMediaPlayer.clearVedioUrl();
                break;
            default:
                break;
        }
    }
}
