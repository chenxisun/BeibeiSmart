package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.buaa.beibeismart.R;

public class Baike_ListActivity extends BaseActivity implements View.OnClickListener{

    TextView title;
    Button backtoBaike;
    Button example1;
    Button example2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike__list);

        title=findViewById(R.id.baike_title);
        findViewById(R.id.btn_example1).setOnClickListener(this);
        findViewById(R.id.btn_example2).setOnClickListener(this);
        findViewById(R.id.btn_BackBaikelist).setOnClickListener(this);
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
            case R.id.btn_BackBaikelist:
                Intent intent = new Intent(Baike_ListActivity.this,BaikeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_example1:
                title.setText("example1");
                v.setPressed(true);
                break;
            case  R.id.btn_example2:
                title.setText("example2");
                v.setPressed(true);
                break;

            default:
                break;
        }

    }
}
