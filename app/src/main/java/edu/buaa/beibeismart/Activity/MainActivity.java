package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.Service.RecordBackground;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    //BaseActivity activity = new BaseActivity();
    Button btnMusic,btnStory,btnBaike,btnEnglish,btnDownload;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermisionUtils.verifyStoragePermissions(this);

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.RECORD_AUDIO},1);
        }

        initView();
        startService(new Intent(this, RecordBackground.class));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        btnMusic = findViewById(R.id.btn_mainactivity_music);
        btnStory = findViewById(R.id.btn_mainactivity_story);
        btnBaike = findViewById(R.id.btn_mainactivity_baike);
        btnEnglish = findViewById(R.id.btn_mainactivity_learnenglish);
        btnDownload = findViewById(R.id.btn_mainactivity_download);
        btnMusic.setOnClickListener(this);
        btnStory.setOnClickListener(this);
        btnBaike.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_mainactivity_music:
                startActivity(new Intent(My_Music_list.Action));
                break;
            case R.id.btn_mainactivity_story:
                startActivity(new Intent(My_story_list.Action));
                break;
            case R.id.btn_mainactivity_baike:
                break;
            case R.id.btn_mainactivity_learnenglish:
                intent = new Intent(MainActivity.this,LearnEnglishActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mainactivity_download:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    public static class PermisionUtils {

        // Storage Permissions
        private static final int REQUEST_EXTERNAL_STORAGE = 1;
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        /**
         * Checks if the app has permission to write to device storage
         * If the app does not has permission then the user will be prompted to
         * grant permissions
         *
         * @param activity
         */
        public static void verifyStoragePermissions(Activity activity) {
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE);
            }
        }
    }

}
