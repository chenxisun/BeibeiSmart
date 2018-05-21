package edu.buaa.beibeismart.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.TimeUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.buaa.beibeismart.Fragment.EnglishPlayerFragment;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.Service.RecordBackground;
import edu.buaa.beibeismart.item_attrib;
import edu.buaa.beibeismart.requestClient;
import edu.buaa.beibeismart.time_transfer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fan on 2018/4/8.
 */

public class Album_list extends BaseActivity implements RecordBackground.FinishActivityListener {

    String title = "";
    final String url_music_list = "http://47.94.165.157:8080/song/list?topic=song&pageNo=0&pageSize=1000";
    final String url_english_list = "http://47.94.165.157:8080/song/list?topic=english&pageNo=0&pageSize=1000";
    final String url_story_list="http://47.94.165.157:8080/story/list?topic=tory&pageNo=0&pageSize=1000";
    static String requestRes = "";
    String requestResMusicList = "";
    String requestResEnglishList = "";
    private TextView album_list_none;
    private ListView album_list;
    private TextView album_title;
    private TextView test_album_list;
    int flag = 0;

    private ArrayList<item_attrib> musicitems;
    private item_attrib voice2Music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_total);
        initData();
        initView();
        album_title.setText(title);
        verifyStoragePermissions(Album_list.this);
        getMusicData();

        RecordBackground.setFinishActivityListener(this);

        findViewById(R.id.back_btn_album_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title.endsWith("故事")){
                    startActivity(new Intent(My_story_list.Action));
                }
                else
                startActivity(new Intent(My_Music_list.Action));
            }
        });

//        initPlayMusic(2);

        album_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                item_attrib item = musicitems.get(i);
                // test_album_list.setText(item.getName()+item.getData());
                EnglishPlayerFragment f1 = EnglishPlayerFragment.newInstance(item);
                FragmentTransaction Mf = getFragmentManager().beginTransaction();
                Mf.replace(R.id.Player, f1);
                Mf.commit();
            }
        });


    }


    protected void initView() {

        album_list = (ListView) findViewById(R.id.album_list);
        album_list_none = findViewById(R.id.album_list_none);
        album_title = findViewById(R.id.album_title);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (musicitems != null && musicitems.size() > 0) {
                album_list_none.setVisibility(View.GONE);

                album_list.setAdapter(new musicListAdapter());
            } else {
                album_list_none.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    protected void initData() {

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        if (title.equals("所有音乐")||title.equals("中文故事")){
        flag = intent.getIntExtra("flag", 0);
        if(flag==-1){item_attrib item=new item_attrib();
        item.setSize(Integer.valueOf(intent.getStringExtra("musicSize"))*1000);
        item.setName(intent.getStringExtra("musicName"));
        item.setDuration(Integer.valueOf(intent.getStringExtra("musicDuration"))*1000);
        item.setData(intent.getStringExtra("musicData"));
        voice2Music=item;}

        }
    }

    private void getMusicData() {
        switch (title) {
            case "所有音乐":
                getRemoteData(url_music_list);
                requestResMusicList = requestRes;
                extractMusicList(requestResMusicList);
                break;
            case "我的收藏":
                getlocaldata();
                break;
            case "轻音乐":
                break;
            case "儿歌":
                break;
            case "英文歌":
                getRemoteData(url_english_list);
                requestResEnglishList = requestRes;
                extractMusicList(requestResEnglishList);
                break;
            case"中文故事":
                getRemoteData(url_story_list);
                requestResEnglishList = requestRes;
                extractMusicList(requestResEnglishList);
                break;
        }
        if(flag==-1){
            EnglishPlayerFragment f1 = EnglishPlayerFragment.newInstance(voice2Music);
            FragmentTransaction Mf = getFragmentManager().beginTransaction();
            Mf.replace(R.id.Player, f1);
            Mf.commit();
        }
    }

    private void getRemoteData(final String url) {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get().build();
                try {
                    Response response = client.newCall(request).execute();
                    final String severResult = response.body().string();
                    extractMusicList(severResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    private void extractMusicList(final String requestResMusicList) {
        musicitems = new ArrayList<item_attrib>();
        try {
            JSONObject jobject = new JSONObject(requestResMusicList);
            JSONArray jarray = jobject.getJSONArray("content");
            for (int i = 0; i < jarray.length(); i++) {
                item_attrib item_music = new item_attrib();
                JSONObject item = jarray.getJSONObject(i);
                String name = item.getString("name");
                String path = item.getString("voicePath");
                int duration = Integer.valueOf(item.getString("timeLength")) *1000;
                int size =Integer.valueOf(item.getInt("size"))*1000 ;
                item_music.setName(name);
                item_music.setDuration(duration);
                item_music.setSize(size);         //需要更改
                item_music.setData(path);
                musicitems.add(item_music);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initPlayMusic(int id) {

        if (id > 0) {

            item_attrib item = musicitems.get(2);
            // test_album_list.setText(item.getName()+item.getData());
            EnglishPlayerFragment f1 = EnglishPlayerFragment.newInstance(item);
            FragmentTransaction Mf = getFragmentManager().beginTransaction();
            Mf.replace(R.id.Player, f1);
            Mf.commit();

        }

    }

    private void getlocaldata() {
        //新建媒体扫描线程
        new Thread() {
            public void run() {
                musicitems = new ArrayList<item_attrib>();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] project = {
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA,
                };

                Cursor cursor = getContentResolver().query(uri, project, null, null, null);

                while (cursor.moveToNext()) {

                    item_attrib item = new item_attrib();
                    String name = cursor.getString(0);
                    item.setName(name);
                    long duration = cursor.getLong(1);
                    item.setDuration(duration);
                    long size = cursor.getLong(2);
                    item.setSize(size);
                    String data = cursor.getString(3);
                    item.setData(data);
//                    if(size>500000)
                    musicitems.add(item);

                }

                cursor.close();
                //发消息到主线程
                handler.sendEmptyMessage(1);

            }

//            ;
        }.start();
    }

    public static void callback(String request) {
        requestRes = request;
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {
        requestRes = null;

    }

    @Override
    public void finishActivity() {
        finish();
    }

    private stopMediaPlay stopMediaPlay;
    public static void setStopMediaplay(stopMediaPlay stopMediaPlay){
        Log.e("info","shut down music play.");
    }
    public interface stopMediaPlay{
        void stopPlay();
    }

    class musicListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return musicitems.size();
        }

        @Override
        public Object getItem(int i) {

            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1 = View.inflate(Album_list.this, R.layout.item_in_listview, null);
//            TextView tv = new TextView(Album_list.this);
//            tv.setTextSize(20);
//            tv.setText(musicitems.get(i).toString());
//            tv.setTextColor(Color.WHITE);
            TextView music_name = view1.findViewById(R.id.music_name);
            TextView music_duration = view1.findViewById(R.id.music_duration);
            TextView music_size = view1.findViewById(R.id.music_size);
            item_attrib item = musicitems.get(i);
            music_name.setText(item.getName());
            music_size.setText(Formatter.formatFileSize(Album_list.this, item.getSize()));
            music_duration.setText(time_transfer.timeParse(item.getDuration()));

            return view1;
        }
    }
}
