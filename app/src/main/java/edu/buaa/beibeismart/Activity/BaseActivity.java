package edu.buaa.beibeismart.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import edu.buaa.beibeismart.Fragment.BaseFragment;
import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.View.LoadingDialog;


/**
 * @note:Activity基本类
 */
public abstract class BaseActivity extends AppCompatActivity implements  VolleyUtil.VolleyFinishListener {

    String TAG = "BaseActivity";
    LoadingDialog loadingDialog;
    int requestType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        //获取数据
        initData();
        //初始化界面
        initView();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
        VolleyUtil.getVolleyUtil().setOnVolleyFinishListener(this);
    }

    @Override
    protected void onDestroy() {
        destroy();
        super.onDestroy();
    }

    //键盘响应
    BaseFragment baseFragment;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(baseFragment != null){
            //键盘事件
            baseFragment.onKeyDown(keyCode,event);
        }
        return super.onKeyDown(keyCode, event);
    }

    /*=============================功能性函数==================================*/
    /**
     * 显示loadingView*/
    protected void showLoadingView(){
//        if(!loadingView.isShow()){
//            loadingView.show();
//        }
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
    }
//    protected void setActionbarText(String text){
//        if(tvActionbar == null) return;
//        tvActionbar.setText(text);
//    }

    /**
     * 隐藏虚拟键盘*/
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    /**
     * 修改Toolbar标题*/
    protected void initToolbar(Toolbar toolbar, String title) {
        /*toolbar.setNavigationIcon(R.mipmap.icon_back);//设置导航栏图标
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.whitesmoke));
        toolbar.setTitleTextColor(getResources().getColor(R.color.whitesmoke));
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(title);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });*/
    }
    protected abstract void initView();
    //初始化数据
    protected void initData(){}

    protected void destroy(){
        VolleyUtil.distoryVolley();
    }

    /*============================继承函数========================================*/
    //网络响应完成
    protected void disposeVolleySuccess( Object result){}
    protected void disposeVolleyFailed(Object result){}

}
