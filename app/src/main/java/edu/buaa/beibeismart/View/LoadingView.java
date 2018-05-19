package edu.buaa.beibeismart.View;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import edu.buaa.beibeismart.R;


/**
 * @note:自定义进度条页面
 */
public class LoadingView{

    Context context;
    //布局容器
    RelativeLayout layoutFile,rlLoading;
    View viewLoading;
    //弹出框
    PopupWindow popupWindow;

    //布局解析器
    private LayoutInflater inflater;
    //旋转动画
    private RotateAnimation rotateAnimation;
    //渐进/出动画
    private AlphaAnimation alphaAnimationIn,alphaAnimationOut;
    //View的背景资源
    int viewBgResid;
    boolean isShowing = false;


    //初始化动画
    private void initAnimation(){
        //建立旋转动画对象并为其配置参数
        rotateAnimation = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        //渐进动画
        alphaAnimationIn = new AlphaAnimation(0f,1f);
        alphaAnimationIn.setFillAfter(true);
        alphaAnimationIn.setDuration(200);
        alphaAnimationIn.setInterpolator(new LinearInterpolator());

        //渐出动画
        alphaAnimationOut = new AlphaAnimation(1f,0f);
        alphaAnimationOut.setFillAfter(true);
        alphaAnimationOut.setDuration(200);
        alphaAnimationOut.setInterpolator(new LinearInterpolator());

        //动画结束时，隐藏
        alphaAnimationOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }

    public void show(){
        initAnimation();
        //布局文件对象
        layoutFile = (RelativeLayout) inflater.inflate(R.layout.view_loading, null);
        //布局容器RelativeLayout
        rlLoading = (RelativeLayout) layoutFile.findViewById(R.id.rl_loading);
        //loading图片,并设置loading的背景图片
        viewLoading = layoutFile.findViewById(R.id.view_loading);
        viewLoading.setBackgroundResource(viewBgResid);
        //获得处了标题栏外的部分
        View parentView = ((Activity)context).getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        //初始化弹窗
        popupWindow = new PopupWindow(layoutFile, ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.FILL_PARENT);
        //标志已显示
        isShowing = true;
        //将标题栏外部分的View放入弹窗中
        popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        rlLoading.startAnimation(alphaAnimationIn);
        viewLoading.startAnimation(rotateAnimation);
    }

    public void dismiss(){
        Log.e("LOADINGVIEW","!!");
        if(popupWindow != null && popupWindow.isShowing()){
            Log.e("LOADINGVIEW","popupWindow dismiss");
            isShowing = false;
            rlLoading.clearAnimation();
            viewLoading.clearAnimation();
            popupWindow.setFocusable(false);
            popupWindow.dismiss();
        }
    }

    //外界获得loadingView的状态
    public boolean isShow(){
        return isShowing;
    }

    public LoadingView(Context pContext){
        context = pContext;
        viewBgResid = R.drawable.loading1;
        inflater = LayoutInflater.from(context);
    }
}
