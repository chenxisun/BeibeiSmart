package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.CatalogAdapter;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
import android.widget.Button;

import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.View.DividerItemLineDecoration;
import edu.buaa.beibeismart.Service.RecordBackground;


public class MainActivity extends BaseActivity implements OnRecyclerViewItemClickListener {

    ArrayList<Map> dataList = new ArrayList();
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    super.setIntegerProperty("splashscreen", R.drawable.wandapad_splash);


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
        RecyclerView rvCatalog = findViewById(R.id.gallery_mainactivity);
        rvCatalog.addItemDecoration(new DividerItemLineDecoration(this, DividerItemLineDecoration.HORIZONTAL_LIST,R.drawable.divider,40));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        rvCatalog.setLayoutManager(layoutManager);
        CatalogAdapter adapter = new CatalogAdapter(getApplicationContext(),dataList,this);
        rvCatalog.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        Map mapMusic = new HashMap();
        mapMusic.put("catalogId","music");
        mapMusic.put("catalog","音乐");
        mapMusic.put("img",R.drawable.catalog_music_128);

        Map mapStory = new HashMap();
        mapStory.put("catalogId","story");
        mapStory.put("catalog","故事");
        mapStory.put("img",R.drawable.catalog_story_128);

        Map mapBio = new HashMap();
        mapBio.put("catalogId","baike");
        mapBio.put("catalog","百科");
        mapBio.put("img",R.drawable.catalog_baike_128);

        Map mapEng = new HashMap();
        mapEng.put("catalogId","wordLearning");
        mapEng.put("catalog","单词学习");
        mapEng.put("img",R.drawable.catalog_words_128);

        Map mapDownload = new HashMap();
        mapDownload.put("catalogId","download");
        mapDownload.put("catalog","在线下载");
        mapDownload.put("img",R.drawable.catalog_download_128);

        dataList.add(mapMusic);
        dataList.add(mapStory);
        dataList.add(mapBio);
        dataList.add(mapEng);
        dataList.add(mapDownload);

    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    /**recyclerView item click*/
    @Override
    public void onItemClickListener(View view, int position) {
        String catalogId = (String) dataList.get(position).get("catalogId");
        switch (catalogId){
            case "music":
                startActivity(new Intent(My_Music_list.Action));
                break;
            case "story":
                startActivity(new Intent(My_story_list.Action));
                break;
            case "baike":
                intent = new Intent(MainActivity.this,BaikeActivity.class);
                startActivity(intent);
                break;
            case "wordLearning":
                intent = new Intent(MainActivity.this,LearnEnglishActivity.class);
                startActivity(intent);
                break;
            case "download":
                break;
        }
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
