package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.buaa.beibeismart.R;

public class BaikeActivity extends BaseActivity implements View.OnClickListener{

    Button animal;
    Button fruit;
    Button english;
    Button vegetable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike);
        findViewById(R.id.btn_baike_return).setOnClickListener(this);
        findViewById(R.id.btn_baike_fruit).setOnClickListener(this);
        findViewById(R.id.btn_baike_vegetable).setOnClickListener(this);
        findViewById(R.id.btn_baike_letter).setOnClickListener(this);
        findViewById(R.id.btn_baike_animal).setOnClickListener(this);

    }

    @Override
    protected void initView() {

    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baike_return:
                Intent intent = new Intent(BaikeActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baike_animal:
                Intent intent2 = new Intent(BaikeActivity.this,Baike_ListActivity.class);
                intent2.putExtra("type","animal");
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }



}