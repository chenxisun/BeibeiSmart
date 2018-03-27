package edu.buaa.beibeismart.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.buaa.beibeismart.R;


public class MainActivity extends BaseActivity {
    //BaseActivity activity = new BaseActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideBottomUIMenu();
    }

    @Override
    protected void initView() {}

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }
}
