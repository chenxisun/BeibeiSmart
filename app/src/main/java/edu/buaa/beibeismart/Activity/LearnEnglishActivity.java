package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.LearnEnglishAdapter;
import edu.buaa.beibeismart.Bean.EnglishTopicBean;
import edu.buaa.beibeismart.Media.OnlineMediaPlayer;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;

public class LearnEnglishActivity extends BaseActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<String> {

    private GridView gvCatalogs;
    private ArrayList<EnglishTopicBean> dataList =new ArrayList<>();
    private LearnEnglishAdapter adapter;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    OnlineMediaPlayer onlineMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlineMediaPlayer = OnlineMediaPlayer.getInstance();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_learn_english);
        gvCatalogs = findViewById(R.id.gv_catalog);
        gvCatalogs.setSelector(new ColorDrawable(Color.TRANSPARENT));
        Button btnReture = findViewById(R.id.btn_learn_english_return);

        btnReture.setOnClickListener(this);

        adapter = new LearnEnglishAdapter(this,dataList);
        gvCatalogs.setAdapter(adapter);
        gvCatalogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                disposeTypeItemClick(view,i);
            }
        });
    }

    private void disposeTypeItemClick( View view, int i){
        //播放音频
        onlineMediaPlayer.playAppend(UrlUtil.IP_MATERIAL+dataList.get(i).getEnglishVoicePath());
        onlineMediaPlayer.playAppend(UrlUtil.IP_MATERIAL+dataList.get(i).getChineseVoicePath());

        String  topicContent =  dataList.get(i).getContent();
        Intent intent = new Intent(LearnEnglishActivity.this,EnglishWordsActivity.class);
        intent.putExtra("topicContent",topicContent);
        startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();

        String url = UrlUtil.IP+"/english/words/topics";
        //String url = "http://47.94.165.157:8080/english/words/topics";
        stringRequest = new StringRequest(Request.Method.GET,url,this,this);
        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error);
    }

    @Override
    public void onResponse(String response) {
        Log.e("LearnEnglishActivity",response);
        try {
            JSONArray jsonArray = new JSONArray( response);
            for (int i =0; i < jsonArray.length(); i++){
                dataList.add(new EnglishTopicBean((JSONObject) jsonArray.get(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }


}
