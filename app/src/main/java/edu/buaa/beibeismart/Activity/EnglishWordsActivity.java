package edu.buaa.beibeismart.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.buaa.beibeismart.Adapter.CharacterAdapter;
import edu.buaa.beibeismart.Bean.EnglishWordBean;
import edu.buaa.beibeismart.Fragment.BaseFragment;
import edu.buaa.beibeismart.Fragment.WordFragment;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.R;

public class EnglishWordsActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, View.OnClickListener, OnRecyclerViewItemClickListener, WordFragment.IButtonNeighborListener {

    public static final int DATALIST_START = 0;
    public static final int DATALIST_MID = 1;
    public static final int DATALIST_END = -1;


    Button btnReturn;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    ArrayList<EnglishWordBean> dataList = new ArrayList();
    CharacterAdapter adapter;
    WordFragment wordFragment;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_english_words);
        btnReturn = findViewById(R.id.btn_englishwords_return);
        btnReturn.setOnClickListener(this);
        recyclerView = findViewById(R.id.rv_englishwords_return);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //设置Adapter
        adapter = new CharacterAdapter(this,dataList,this);
        recyclerView.setAdapter(adapter);
    }

    private void setFragment(int i){
        Bundle bundle = new Bundle();
        try {
            bundle.putString("param",contentArray.get(i).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //是否是最后一个单词
        bundle.putInt("isEnd",0);
        bundle.putInt("isStart",0);
        if (i == (dataList.size() - 1)){
            bundle.putInt("isEnd",1);
        }
        if(i == 0){
            bundle.putInt("isStart",1);
        }
        wordFragment = new WordFragment();
        wordFragment.setiButtonNeighborListener(this);
        wordFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_activity_english_words,wordFragment).commit();
    }

    private int pageSize = 15;
    private int curPageNo = 0;
    private int totalPage = 0;
    private int totalElements = 0;
    private boolean isLast = true;
    private int numberOfElements = 0;
    private boolean isFirst = true;

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String topicContent = intent.getStringExtra("topicContent");
        Toast.makeText(getApplicationContext(),"topicContent:"+topicContent,Toast.LENGTH_SHORT).show();
        //http://47.94.165.157:8080/english/words/list?topic=animal&pageNo=1&pageSize=10
        String url = UrlUtil.IP+"/english/words/list?topic="+topicContent+"&pageNo="+curPageNo+"&pageSize="+pageSize;
        Log.e("EnglishWordsActivity",url);
        //String url = "http://47.94.165.157:8080/mock/hello1";
        //String url = "http://47.94.165.157:8080/english/words/topics";
        stringRequest = new StringRequest(Request.Method.GET,url,this,this);
        requestQueue = Volley.newRequestQueue(EnglishWordsActivity.this);

        requestQueue.add(stringRequest);
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {
        System.out.print("onVolleyFinish:"+result);
        Log.e("onVolleyFinish", (String) result);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.print("volley:"+error);
        Log.e("Volley","error");
    }

    JSONArray contentArray;
    @Override
    public void onResponse(String response) {

        Log.e("EnglishWordsActivity:",response);
        try {
            jsonObject = new JSONObject(response);
            totalPage = jsonObject.getInt("totalPages");
            totalElements = jsonObject.getInt("totalElements");
            isLast = jsonObject.getBoolean("last");
            numberOfElements = jsonObject.getInt("numberOfElements");
            isFirst = jsonObject.getBoolean("first");
            contentArray = jsonObject.getJSONArray("content");

            for (int i = 0; i < contentArray.length(); i++){
                dataList.add(new EnglishWordBean((JSONObject) contentArray.get(i)));
                //Log.e("EnglishWordsActivity:",contentArray.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
        setFragment(0);
        System.out.print("volley:"+dataList.size());
    }

    @Override
    public void onClick(View view) {

        finish();
    }

    int curPosition = 0;
    @Override
    public void onItemClickListener(View view, int position) {
        if (curPosition != position){
            curPosition = position;
            adapter.setCurPosition(position);
            adapter.notifyDataSetChanged();
            setFragment(position);
        }
    }

    //fragment中左右按钮
    @Override
    public void onNeighborButtonClick(int viewId) {
        switch (viewId){
            //左按钮
            case R.id.btn_pre:
                curPosition--;
                adapter.setCurPosition(curPosition);
                adapter.notifyDataSetChanged();
                setFragment(curPosition);
                break;
            //右按钮
            case R.id.btn_next:
                curPosition++;
                adapter.setCurPosition(curPosition);
                adapter.notifyDataSetChanged();
                setFragment(curPosition);
                break;
        }
    }
}
