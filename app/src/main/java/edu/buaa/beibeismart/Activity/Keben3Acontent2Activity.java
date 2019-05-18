package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.buaa.beibeismart.R;

public class Keben3Acontent2Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keben3_acontent2);

        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.cartoontime).setOnClickListener(this);
        findViewById(R.id.funtime).setOnClickListener(this);
        findViewById(R.id.checkoutandticking).setOnClickListener(this);
        findViewById(R.id.storytime).setOnClickListener(this);
        findViewById(R.id.letterandsong).setOnClickListener(this);
        findViewById(R.id.wordlist).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                Intent intent = new Intent(Keben3Acontent2Activity.this, Keben3Acontent1Activity.class);
                startActivity(intent);
                break;
            case R.id.storytime:
                Intent intent11=getIntent();
                String danyuan1=intent11.getStringExtra("danyuan");
                Intent intent1 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent1.putExtra("danyuan",danyuan1);
                intent1.putExtra("zhangjie","storytime");
                startActivity(intent1);
                break;
            case R.id.cartoontime:
                Intent intent21=getIntent();
                String danyuan2=intent21.getStringExtra("danyuan");
                Intent intent2 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent2.putExtra("danyuan",danyuan2);
                intent2.putExtra("zhangjie","cartoontime");
                startActivity(intent2);
                break;
            case R.id.funtime:
                Intent intent31=getIntent();
                String danyuan3=intent31.getStringExtra("danyuan");
                Intent intent3 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent3.putExtra("danyuan",danyuan3);
                intent3.putExtra("zhangjie","funtime");
                startActivity(intent3);
                break;
            case R.id.letterandsong:
                Intent intent41=getIntent();
                String danyuan4=intent41.getStringExtra("danyuan");
                Intent intent4 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent4.putExtra("danyuan",danyuan4);
                intent4.putExtra("zhangjie","songtime");
                startActivity(intent4);
                break;
            case R.id.checkoutandticking:
                Intent intent51=getIntent();
                String danyuan5=intent51.getStringExtra("danyuan");
                Intent intent5 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent5.putExtra("danyuan",danyuan5);
                intent5.putExtra("zhangjie","checkouttime");
                startActivity(intent5);
                break;
            case R.id.wordlist:
                Intent intent61=getIntent();
                String danyuan6=intent61.getStringExtra("danyuan");
                Intent intent6 = new Intent(Keben3Acontent2Activity.this, KebencontentActivity.class);
                intent6.putExtra("danyuan",danyuan6);
                intent6.putExtra("zhangjie","wordlist");
                startActivity(intent6);
                break;
        }

    }
}
