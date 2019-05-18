package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class KenbenActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kenbenctivity);
        findViewById(R.id.back_btn_English).setOnClickListener(this);
        findViewById(R.id.keben3a).setOnClickListener(this);
        findViewById(R.id.keben3b).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_English:
                Intent intent = new Intent(KenbenActivity.this,EnglishActivity.class);
                startActivity(intent);
                break;
            case R.id.keben3a:
                Intent intent2 = new Intent(KenbenActivity.this,Keben3Acontent1Activity.class);
                startActivity(intent2);
                break;
            case R.id.keben3b:
                Intent intent3= new Intent(KenbenActivity.this,Keben3Bcontent1Activity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
