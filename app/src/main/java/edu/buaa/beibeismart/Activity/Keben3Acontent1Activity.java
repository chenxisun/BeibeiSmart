package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.buaa.beibeismart.R;

public class Keben3Acontent1Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keben3_acontent1);
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.unit1).setOnClickListener(this);
        findViewById(R.id.unit2).setOnClickListener(this);
        findViewById(R.id.unit3).setOnClickListener(this);
        findViewById(R.id.unit4).setOnClickListener(this);
        findViewById(R.id.unit5).setOnClickListener(this);
        findViewById(R.id.unit6).setOnClickListener(this);
        findViewById(R.id.unit7).setOnClickListener(this);
        findViewById(R.id.unit8).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                Intent intent = new Intent(Keben3Acontent1Activity.this,KenbenActivity.class);
                startActivity(intent);
                break;
            case R.id.unit1:
                Intent intent1 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent1.putExtra("danyuan","keben3a_unit1");
                startActivity(intent1);
                break;
            case R.id.unit2:
                Intent intent2 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent2.putExtra("danyuan","keben3a_unit2");
                startActivity(intent2);
                break;
            case R.id.unit3:
                Intent intent3 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent3.putExtra("danyuan","keben3a_unit3");
                startActivity(intent3);
                break;
            case R.id.unit4:
                Intent intent4 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent4.putExtra("danyuan","keben3a_unit4");
                startActivity(intent4);
                break;
            case R.id.unit5:
                Intent intent5 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent5.putExtra("danyuan","keben3a_unit5");
                startActivity(intent5);
                break;
            case R.id.unit6:
                Intent intent6 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent6.putExtra("danyuan","keben3a_unit6");
                startActivity(intent6);
                break;
            case R.id.unit7:
                Intent intent7 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent7.putExtra("danyuan","keben3a_unit7");
                startActivity(intent7);
                break;
            case R.id.unit8:
                Intent intent8 = new Intent(Keben3Acontent1Activity.this,Keben3Acontent2Activity.class);
                intent8.putExtra("danyuan","keben3a_unit8");
                startActivity(intent8);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
