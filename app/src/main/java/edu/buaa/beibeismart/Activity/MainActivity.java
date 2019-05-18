package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.CatalogAdapter;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.Service.RecordBackground;
import edu.buaa.beibeismart.SkeletonDetect.SkeletonActivity;
import edu.buaa.beibeismart.View.DividerItemLineDecoration;


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

        startService(new Intent(this,   RecordBackground.class));
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        RecyclerView rvCatalog = findViewById(R.id.gallery_mainactivity);
        rvCatalog.addItemDecoration(new DividerItemLineDecoration(this, DividerItemLineDecoration.HORIZONTAL_LIST,R.drawable.divider,40));
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvCatalog.setLayoutManager(gridLayoutManager);

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
        mapStory.put("catalog","文学");
        mapStory.put("img",R.drawable.catalog_story_128);

        Map mapBio = new HashMap();
        mapBio.put("catalogId","baike");
        mapBio.put("catalog","百科");
        mapBio.put("img",R.drawable.catalog_baike_128);

        Map mapEng = new HashMap();
        mapEng.put("catalogId","wordLearning");
        mapEng.put("catalog","英语");
        mapEng.put("img",R.drawable.catalog_words_128);


        Map mapWeather = new HashMap();
        mapWeather.put("catalogId","weather");
        mapWeather.put("catalog","天气预报");
        mapWeather.put("img",R.drawable.catalog_weather_128);

        Map mapFaceRec = new HashMap();
        mapFaceRec.put("catalogId","faceRec");
        mapFaceRec.put("catalog","人脸识别");
        mapFaceRec.put("img",R.drawable.catalog_face_128);

        Map mapPoseMoniter = new HashMap();
        mapPoseMoniter.put("catalogId","poseMoniter");
        mapPoseMoniter.put("catalog","行为监控");
        mapPoseMoniter.put("img",R.drawable.catalog_behave_128);

        Map mapFaceAnsys = new HashMap();
        mapFaceAnsys.put("catalogId","faceAnsys");
        mapFaceAnsys.put("catalog","表情识别");
        mapFaceAnsys.put("img",R.drawable.catalog_emoji_128);

        dataList.add(mapMusic);
        dataList.add(mapStory);
        dataList.add(mapBio);
        dataList.add(mapEng);
        dataList.add(mapWeather);
        dataList.add(mapFaceRec);
        dataList.add(mapFaceAnsys);
        dataList.add(mapPoseMoniter);



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
                intent = new Intent(MainActivity.this,EnglishActivity.class);
                startActivity(intent);
                break;
            case "faceRec":
                intent=new Intent(MainActivity.this, FaceRecActivity.class);
                startActivity(intent);
                break;
            case "faceAnsys":
                startActivity(new Intent(MainActivity.this,EmotionActivity.class));
                break;
            case "poseMoniter":
                //intent=new Intent(MainActivity.this, SkeletonActivity.class);
                //startActivity(intent);
                startActivity(new Intent(MainActivity.this,SkeletonActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,   RecordBackground.class));
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
