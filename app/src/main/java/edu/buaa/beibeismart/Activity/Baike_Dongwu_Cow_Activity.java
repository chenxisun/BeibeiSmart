package edu.buaa.beibeismart.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.buaa.beibeismart.R;

public class Baike_Dongwu_Cow_Activity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike__dongwu__cow_);

        findViewById(R.id.button_baike_cow).setOnClickListener(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_baike_cow:
                Intent intent = new Intent(Baike_Dongwu_Cow_Activity.this,Baike_Dongwu_Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
