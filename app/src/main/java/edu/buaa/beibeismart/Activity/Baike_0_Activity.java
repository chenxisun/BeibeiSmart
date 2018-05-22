package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;


public class Baike_0_Activity extends BaseActivity implements View.OnClickListener{

    public String voicePath;
    public String img2Path;
    public String img3Path;
    public String img4Path;
    public String img5Path;
    public int position = 100;
    OnlineMediaPlayer onlineMediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike_0_);

        onlineMediaPlayer = OnlineMediaPlayer.getInstance();
        Intent intent = getIntent();
        if (intent != null){
            voicePath = intent.getStringExtra("voicePath");
        }
        findViewById(R.id.btn_baike_0_return).setOnClickListener(this);
        //播放音频
        OnlineMediaPlayer.getInstance().play(voicePath);
        onlineMediaPlayer.play(voicePath);
        //System.out.println(UrlUtil.IP_MATERIAL);
        //System.out.println(voicePath);
        setImg2();
        setImg3();
        setImg4();
        setImg5();
    }
    @Override
    protected void initView() {

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_baike_0_return:
                onlineMediaPlayer.stop();
                onlineMediaPlayer.clearVedioUrl();
                intent = new Intent(this,BaikeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void setImg2(){
        ImageView imageView2 = findViewById(R.id.imageView2_0);
        Intent intent = getIntent();
        img2Path = intent.getStringExtra("img2Path");
        //intent.getIntExtra("position",position);
        final BitClass bmc = new BitClass();
        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{

                    URL httpUrl = new URL(img2Path);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(600);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bmc.bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bmc.bitmap == null){
            System.out.println("aa");
            System.out.println(2);
            System.out.println(img2Path);
            continue;
        }
        imageView2.setImageBitmap(bmc.bitmap);
    }

    public void setImg3(){
        ImageView imageView3 = findViewById(R.id.imageView3_0);
        Intent intent = getIntent();
        img3Path = intent.getStringExtra("img3Path");
        final BitClass bmc = new BitClass();
        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{
                    URL httpUrl = new URL(img3Path);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(600);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bmc.bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bmc.bitmap == null){
            System.out.println("aa");
            System.out.println(3);
            System.out.println(img3Path);
            continue;
        }
        imageView3.setImageBitmap(bmc.bitmap);
    }

    public void setImg4(){
        ImageView imageView4 = findViewById(R.id.imageView4_0);
        Intent intent = getIntent();
        img4Path = intent.getStringExtra("img4Path");
        final BitClass bmc = new BitClass();
        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{

                    URL httpUrl = new URL(img4Path);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(600);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bmc.bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bmc.bitmap == null){
            System.out.println("aa");
            System.out.println(4);
            System.out.println(img4Path);
            continue;
        }
        imageView4.setImageBitmap(bmc.bitmap);
    }

    public void setImg5(){
        ImageView imageView5 = findViewById(R.id.imageView5_0);
        Intent intent = getIntent();
        img5Path = intent.getStringExtra("img5Path");
        final BitClass bmc = new BitClass();
        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{
                    URL httpUrl = new URL(img5Path);
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(600);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bmc.bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bmc.bitmap == null){
            System.out.println("aa");
            System.out.println(5);
            System.out.println(img5Path);
            continue;
        }
        imageView5.setImageBitmap(bmc.bitmap);
    }
}
class BitClass{
    public Bitmap bitmap = null;
}


