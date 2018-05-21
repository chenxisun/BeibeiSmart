package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;


public class Baike_0_Activity extends BaseActivity implements View.OnClickListener{

    public String voicePath;
    OnlineMediaPlayer onlineMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike_0_);

        onlineMediaPlayer = OnlineMediaPlayer.getInstance();
        Intent intent = getIntent();
        if (intent != null){
            voicePath = intent.getStringExtra("voicePath");
        }
        findViewById(R.id.btn_baike_0_return).setOnClickListener(this);

        //播放音频
        OnlineMediaPlayer.getInstance().play(voicePath);
        onlineMediaPlayer.play(voicePath);
        //System.out.println(UrlUtil.IP_MATERIAL);
        //System.out.println(voicePath);
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
            case R.id.btn_baike_0_return:
                onlineMediaPlayer.stop();
                onlineMediaPlayer.clearVedioUrl();
                intent = new Intent(this,BaikeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
