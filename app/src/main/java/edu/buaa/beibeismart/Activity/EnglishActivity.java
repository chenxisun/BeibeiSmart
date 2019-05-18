package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class EnglishActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english);
        findViewById(R.id.back_btn_English).setOnClickListener(this);
        findViewById(R.id.danciLearn).setOnClickListener(this);
        findViewById(R.id.yinbiaoLearn).setOnClickListener(this);
        findViewById(R.id.kebenLearn).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_English:
                Intent intent = new Intent(EnglishActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.danciLearn:
                Intent intent2 = new Intent(EnglishActivity.this,LearnEnglishActivity.class);
                startActivity(intent2);
                break;
            case R.id.yinbiaoLearn:
                Intent intent3= new Intent(EnglishActivity.this,IpaLearnActivity.class);
                startActivity(intent3);
                break;
            case R.id.kebenLearn:
                Intent intent4= new Intent(EnglishActivity.this,KenbenActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }

    }
}
