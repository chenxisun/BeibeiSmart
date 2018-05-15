package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.CatalogAdapter;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;

import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.View.DividerItemLineDecoration;
import edu.buaa.beibeismart.Service.RecordBackground;


public class MainActivity extends BaseActivity implements OnRecyclerViewItemClickListener {

    ArrayList<Map> dataList = new ArrayList();
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

    private void complete(Context context,final long id) {
        final DownloadManager  downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        //监视下载进度
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO 自动生成的方法存根
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == reference) {
                    //对下载的文件进行一些操作
                    DownloadManager.Query myDownloadQuery = new DownloadManager.Query();    //查询器查询下载请求的状态，进度和详细信息
                    myDownloadQuery.setFilterById(reference);  //传入下载进程的id
                    //myDownloadQuery.setFilterByStatus(DownloadManager.STATUS_PAUSED); //使用setFilterByStatue过滤下载状态,被暂停的下载
                    Cursor myDownload = downloadManager.query(myDownloadQuery);   //获取查询的数据结果，Cursor是一种数据结构
                    //查询结果中的第一个。可以while(myDownload.moveToNext())遍历结果
                    if (myDownload.moveToFirst()) {
                        int fileNameIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);  //文件名称，
                        int fileUriIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);   //下载地址
//                      int reasonIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_REASON);   //暂停原因
//                      int titleIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_TITLE);  //下载标题
//                      int fileSizeIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);   //文件总大小
//                      int bytesDLIdx = myDownload.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);   //剩余下载大小

                        String fileName = myDownload.getString(fileNameIdx);
                        String fileUri = myDownload.getString(fileUriIdx);
//                      String title = myDownload.getString(titleIdx);
//                      int fileSize = myDownload.getInt(fileSizeIdx);
//                      int bytesDL = myDownload.getInt(bytesDLIdx);
//                      int reason = myDownload.getInt(reasonIdx);  // 将暂停原因转化为友好的文本
//                        String reasonString = "Unknown";
//                        switch (reason) {
//                              case DownloadManager.PAUSED_QUEUED_FOR_WIFI :
//                                reasonString = "Waiting for WiFi"; break;
//                              case DownloadManager.PAUSED_WAITING_FOR_NETWORK :
//                                reasonString = "Waiting for connectivity"; break;
//                              case DownloadManager.PAUSED_WAITING_TO_RETRY :
//                                reasonString = "Waiting to retry"; break;
//                              default : break;
//                        }
                        //对文件进行一些操作
                        Log.d("网络操作", fileName + " : " + fileUri);
                    }
                    myDownload.close();  //关闭结果

                }
            }
        };

        ((Activity)context).registerReceiver(receiver, filter);
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
        mapEng.put("catalog","英语学习");
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
        Log.e("MainActivity",catalogId);
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
