package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class IpaLearnActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipa_learn);
        findViewById(R.id.back_btn_IPA).setOnClickListener(this);
        findViewById(R.id.ipa1).setOnClickListener(this);
        findViewById(R.id.ipa2).setOnClickListener(this);
        findViewById(R.id.ipa3).setOnClickListener(this);
        findViewById(R.id.ipa4).setOnClickListener(this);
        findViewById(R.id.ipa5).setOnClickListener(this);
        findViewById(R.id.ipa6).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn_IPA:
                Intent intent = new Intent(IpaLearnActivity.this, EnglishActivity.class);
                startActivity(intent);
                break;
            case R.id.ipa1:
                Intent intent1 = new Intent(IpaLearnActivity.this, IPA1Activity.class);
                startActivity(intent1);
                break;
            case R.id.ipa2:
                Intent intent2 = new Intent(IpaLearnActivity.this, Ipa2Activity.class);
                startActivity(intent2);
                break;
            case R.id.ipa3:
                Intent intent3 = new Intent(IpaLearnActivity.this, IPA3Activity.class);
                startActivity(intent3);
                break;
            case R.id.ipa4:
                Intent intent4 = new Intent(IpaLearnActivity.this, IPA4Activity.class);
                startActivity(intent4);
                break;
            case R.id.ipa5:
                Intent intent5 = new Intent(IpaLearnActivity.this, IPA5Activity.class);
                startActivity(intent5);
                break;
            case R.id.ipa6:
                Intent intent6 = new Intent(IpaLearnActivity.this, IPA6Activity.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
