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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.buaa.beibeismart.Fragment.EnglishPlayerFragment;
import edu.buaa.beibeismart.R;
import edu.buaa.beibeismart.item_attrib;
import edu.buaa.beibeismart.time_transfer;

/**
 * Created by fan on 2018/4/8.
 */

public class Album_list extends BaseActivity {

    String title = "";
    private TextView album_list_none;
    private ListView album_list;
    private TextView album_title;
    private TextView test_album_list;

    private ArrayList<item_attrib> musicitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list_total);
        initData();
        initView();
        album_title.setText(title);
        verifyStoragePermissions(Album_list.this);
        getMusicData();




        findViewById(R.id.back_btn_album_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(My_Music_list.Action));
            }
        });
        album_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               item_attrib item=  musicitems.get(i);
              // test_album_list.setText(item.getName()+item.getData());
                EnglishPlayerFragment f1=EnglishPlayerFragment.newInstance(item);
                FragmentTransaction Mf=getFragmentManager().beginTransaction();
                Mf.replace(R.id.Player,f1);
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


    }

    private void getMusicData() {
        switch (title) {
            case "所有音乐":
                getlocaldata();
                break;
            case "我的收藏":
                break;
            case "轻音乐":
                break;
            case "儿歌":
                break;
            case "英文歌":
                break;
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

            ;
        }.start();
    }


    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

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

            View view1=View.inflate(Album_list.this,R.layout.item_in_listview,null);
//
//            TextView tv = new TextView(Album_list.this);
//            tv.setTextSize(20);
//            tv.setText(musicitems.get(i).toString());
//            tv.setTextColor(Color.WHITE);
            TextView music_name = view1.findViewById(R.id.music_name);
            TextView music_duration=view1.findViewById(R.id.music_duration);
            TextView music_size=view1.findViewById(R.id.music_size);
            item_attrib item=musicitems.get(i);
            music_name.setText(item.getName());
            music_size.setText(Formatter.formatFileSize(Album_list.this,item.getSize()));
            music_duration.setText(time_transfer.timeParse(item.getDuration()));


            return view1;
        }
    }
}
