package edu.buaa.beibeismart.Activity;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity2 extends BaseActivity  {
    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    /*
    private String TAG="MainActivity";

    RadioButton rbLeft,rbRight;
    int requestType = -1;
    public LoadingView loadingView;
    LoadingDialog loadingDialog;
    //是否已有本地登陆者
    boolean isLogin = false;
    //切换的三个Fragment
    SocialFragment socialFragment;
    LoginFragment loginFragment;
    PersonalNewsFragment personalNewsFragment;
    RadioGroup rbGroup;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"ua1KHiq6HbFKMH7NVimOwfv3");

        //获取组件
        rbLeft = (RadioButton) findViewById(R.id.rb_left);
        rbRight = (RadioButton) findViewById(R.id.rb_right);
        rbGroup = (RadioGroup) findViewById(R.id.rb_group);
        loadingView = new LoadingView(this);
        loadingDialog = new LoadingDialog(this);

        //接口回调
        DataSource.getDataSource().setOnDataSourceFinashListener(this);

//        setActionbarText(getString(R.string.news_social));
        //获取数据
        if(DataSource.getDataSource().getChannelList().size() > 0){
            //如果频道List不为空，则显示SocialFragment
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new SocialFragment()).commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new Main2Activity()).commit();
        }
        else{
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();
        }
        //判断是否有用户已经登录
        isUserLogin();
        //监听事件
        rbLeft.setOnCheckedChangeListener(this);
        rbRight.setOnCheckedChangeListener(this);
        //界面配置
//        ((Button)findViewById(R.id.btn_actionbar_withback)).setVisibility(View.GONE);
        CommentActivity.setOnCommentSuccess(this);
        PersonalNewsAddActivity.setShowSnackerBarListener(this);
    }

    //标记是否有用户登录
    private boolean isUserLogin(){
        Log.e(TAG,"isUserLogin");
        //如果系统变量中有登录的用户
        if(CommonUtil.getCurUser(this)!=null){
            isLogin = true;
            return isLogin;
        }
        //从本地中获取用户，获取本地用户名，密码不保存，登录成功的保存用户名
        String strUser = SharedPreUtil.getString(getApplicationContext(),SharedPreUtil.KEY_USER);
        if(!TextUtils.isEmpty(strUser)){
            isLogin = true;
            User curUser = GsonUtil.changeGsonToBean(strUser,User.class);
            //将当前用户保存到系统全局变量中
            CommonUtil.setCurUser(this,curUser);
        }
        return isLogin;
    }

//    public void setIsLogin(boolean flag){
//        isLogin = flag;
//    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked){
            return;
        }
        String FRAGMENT_TAG = null;
        switch (buttonView.getId()){
            case R.id.rb_left:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,new SocialFragment()).commitAllowingStateLoss();
                break;
            case R.id.rb_right:
                //如果已经登录
                Log.e(TAG, "right"+isLogin);
                isUserLogin();
                if(isLogin){
                    //如果personlNewsFragment不存在
                    if(personalNewsFragment == null){
                        FRAGMENT_TAG = "personalNews";
//                    personalNewsFragment = new PersonalNewsFragment();
                        personalNewsFragment = PersonalNewsFragment_.builder().build();
                    }
                    baseFragment = personalNewsFragment;
                }
                //尚未登录
                else{
                    if(loginFragment == null){
                        FRAGMENT_TAG = "loginFragment";
//                        loginFragment = new LoginFragment();
                        loginFragment = LoginFragment_.builder().build();
                    }
                    baseFragment = loginFragment;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,baseFragment,FRAGMENT_TAG).commitAllowingStateLoss();
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("loginFragment");
                Log.e(TAG,"fragment is "+fragment);
                break;
        }
    }

    /*==================================共用方法==========================================*/

/*
    @Override
    protected void initView() {

    }

    @Override
    protected void destroy() {
        super.destroy();
        Log.e("MainActivity","onDestroy");
    }


    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }*/
}

