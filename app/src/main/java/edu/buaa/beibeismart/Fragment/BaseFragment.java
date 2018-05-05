package edu.buaa.beibeismart.Fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;


import edu.buaa.beibeismart.Activity.BaseActivity;
import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.View.LoadingView;

/**
 * 作者：csj
 * 邮箱：￥￥￥￥
 */
public abstract class BaseFragment extends Fragment implements VolleyUtil.VolleyFinishListener {
    private String TAG = "BaseFragment";
    protected Context context;

    protected LoadingView loadingView;
//    protected SlidingMenu smRight;

    protected int requestType = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        context = getActivity();
        loadingView = new LoadingView(getActivity());
        //接口回调
//        VolleyUtil.getVolleyUtil().setOnVolleyFinishListener(this);
//        DataSource.getDataSource().setOnDataSourceFinashListener(this);
        //加载数据
        initData();
        //加载页面
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume:接口回调");
        //接口回调
        VolleyUtil.getVolleyUtil().setOnVolleyFinishListener(this);
    }

    /**
     * 初始化Toolbar标题*/
    protected void initToolbar(Toolbar toolbar, String title, int icon) {
        toolbar.setNavigationIcon(icon);//设置导航栏图标
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.whitesmoke));
        toolbar.setTitleTextColor(getResources().getColor(R.color.whitesmoke));
        ((BaseActivity)context).setSupportActionBar(toolbar);
        ((BaseActivity)context).setTitle(title);
        ((BaseActivity)context).getSupportActionBar().setHomeButtonEnabled(true);
        ((BaseActivity)context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home:
//                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onDestroy() {
        //子类销毁
        destroy();
        super.onDestroy();
    }

    //侧滑菜单
    @TargetApi(Build.VERSION_CODES.KITKAT)
//    protected void configSlidingMenu(){
//        //如果侧滑菜单没有创建，则创建侧滑菜单
//        if(smRight == null){
//            smRight = new SlidingMenu(context);
//            //BUG：sliding has already attach to window
//            smRight.setMode(SlidingMenu.RIGHT);
//            smRight.setBehindOffsetRes(R.dimen.sliding_menu_offset);
//            smRight.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//            smRight.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
//            smRight.setMenu(R.layout.layout_slidingmenu);
//            //
//            return;
//        }
//        smRight.setSlidingEnabled(true);
//    }

    //侧滑菜单不可用
    protected void disableSliding(){
//        if(smRight!=null){
//            smRight.setSlidingEnabled(false);
//        }
    }

    //显示进度条
    protected void showLoadingView(){
        if(loadingView.isShow()){
            loadingView.show();
        }
    }

    //隐藏进度条
    protected void dismissLoadingView(){
        if(loadingView!=null && loadingView.isShow()){
            loadingView.dismiss();
        }
    }

    //Volley响应接口
    @Override
    public void onVolleyFinish(int isSuccess, Object result) {
        switch (isSuccess){
            //网络请求成功
            case VolleyUtil.SUCCESS_SUCCESS:
                //子类重新具体逻辑
                disposeVolleySuccess(result);
                //进度条消失
                dismissLoadingView();
                break;
            //网络请求失败
            case VolleyUtil.FAILED_FAILED:
                //子类重新具体逻辑
                disposeVolleyFailed(result);
                //进度条消失
                dismissLoadingView();
                break;
        }
        requestType = -1;
    }


    /*===============================可覆盖方法================================================*/
    //Volley响应成功
    protected void disposeVolleySuccess(Object result){}
    //Volley响应成功
    protected void disposeVolleyFailed(Object result){}
    //子类初始化View方法
    protected void initView(){
        Log.e(TAG,"initView");
    }
    //加载数据
    protected void initData(){
        Log.e(TAG,"initData");
    }
    Dialog curDialog;
    //键盘回退方法
    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.e(TAG,"onKeyDown");
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(loadingView!=null && loadingView.isShow()){
                loadingView.dismiss();
                return true;
            }
            if(curDialog!=null && curDialog.isShowing()){
                Log.e(TAG,"curDialog onKeyDown");
                curDialog.dismiss();
                return true;
            }


        }
        return false;
    }
    //Fragment onDestroy销毁方法
    protected void destroy(){
    }
    /*===============================抽象方法==================================================*/

}
