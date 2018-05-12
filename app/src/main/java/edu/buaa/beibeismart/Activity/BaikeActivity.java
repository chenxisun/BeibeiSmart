package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import edu.buaa.beibeismart.Fragment.DongwuFragment;
import edu.buaa.beibeismart.Fragment.ShucaiFragment;
import edu.buaa.beibeismart.Adapter.TabFragmentPagerAdapter;
import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.R;

public class BaikeActivity extends BaseActivity implements View.OnClickListener{


    private ViewPager myViewPager;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike);
        findViewById(R.id.btn_baike_return).setOnClickListener(this);
        initView();
        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new DongwuFragment());
        list.add(new ShucaiFragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
    }

    @Override
    protected void initView() {
        myViewPager = findViewById(R.id.myViewPager);
    }



    /**
     * 点击事件
     */

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baike_return:
                Intent intent = new Intent(BaikeActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {

        }
    }

}