package edu.buaa.beibeismart.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
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
import edu.buaa.beibeismart.View.LFRecyclerView.LFRecyclerView;

public class EnglishWordsActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, View.OnClickListener, OnRecyclerViewItemClickListener, WordFragment.IButtonNeighborListener, LFRecyclerView.LFRecyclerViewScrollChange, LFRecyclerView.LFRecyclerViewListener {

    public static final int DATALIST_START = 0;
    public static final int DATALIST_MID = 1;
    public static final int DATALIST_END = -1;


    Button btnReturn;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    LFRecyclerView recyclerView;
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

        recyclerView.setLoadMore(true);
        recyclerView.setRefresh(true);
        recyclerView.setNoDateShow();
        recyclerView.setAutoLoadMore(true);
        recyclerView.setLFRecyclerViewListener(this);
        recyclerView.setScrollChangeListener(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置Adapter
        adapter = new CharacterAdapter(this,dataList,this);
        recyclerView.setAdapter(adapter);
    }

    private void setFragment(int i){
        Bundle bundle = new Bundle();
        Log.e("EnglishWordsActivity","dataList pos:"+i);
        Log.e("EnglishWordsActivity","dataList:"+dataList.get(i).toString());
        bundle.putSerializable("param",dataList.get(i));

        //是否是最后一个单词
        bundle.putInt("isEndWord",0);
        bundle.putInt("isStartdWord",0);
        if (i == (dataList.size() - 1) && isLast){
            bundle.putInt("isEndWord",1);
        }
        if(i == 0){
            bundle.putInt("isStartdWord",1);
        }
        wordFragment = new WordFragment();
        wordFragment.setiButtonNeighborListener(this);
        wordFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragment_activity_english_words,wordFragment).commit();
    }

    private int pageSize = 7;
    private int curPageNo = 0;
    private int totalPage = 0;
    private int totalElements = 0;
    private boolean isLast = true;
    private int numberOfElements = 0;
    private boolean isFirst = true;
    String topicContent = null;
    @Override
    protected void initData() {
        Intent intent = getIntent();
        topicContent = intent.getStringExtra("topicContent");
        Toast.makeText(getApplicationContext(),"topicContent:"+topicContent,Toast.LENGTH_SHORT).show();
        //http://47.94.165.157:8080/english/words/list?topic=animal&pageNo=1&pageSize=10
        loadMoreData();
    }

    private void loadMoreData(){

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
            isLast = jsonObject.getBoolean("last");
            contentArray = jsonObject.getJSONArray("content");
            curPageNo = jsonObject.getInt("number");

            for (int i = 0; i < contentArray.length(); i++){
                dataList.add(new EnglishWordBean((JSONObject) contentArray.get(i)));
                //Log.e("EnglishWordsActivity:",contentArray.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //如果是刷新，则刷新提示
        if((dataList.size() - pageSize )<=0){
            Log.e("EnglishWordsActivity","dataList.size() <=0 :"+dataList.size());
            recyclerView.stopRefresh(b);
            adapter.notifyItemInserted(0);
            adapter.notifyItemRangeChanged(0,dataList.size());
        }
        //如果是loadmore，则加载提示
        else{
            isLoadingMore = false;
            Log.e("EnglishWordsActivity","dataList.size() >0 :"+dataList.size());
            adapter.notifyItemRangeInserted(dataList.size()-1,1);
            //显示到curPosition
        }
        //adapter.notifyDataSetChanged();
        if(curPageNo == 0){
            //如果是第一页，则默认显示第一个界面
            setFragment(0);
        }
        recyclerView.setLoadMore(true);
        if(isLast == true){
            recyclerView.setLoadMore(false);
        }
        showToCurPosition();
        curPageNo++;
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    int curPosition = 0;
    @Override
    public void onItemClickListener(View view, int position) {
        Log.e("EnglishWordsActivity","position:"+position);
        Log.e("EnglishWordsActivity","datalist size:"+dataList.size());
        Log.e("EnglishWordsActivity","datalist position:"+dataList.get(position).getEnglishContent());
        for(int i = 0; i < dataList.size(); i++){
            Log.e("EnglishWordsActivity",dataList.get(i).getEnglishContent());
        }

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
                showToCurPosition();
                break;
            //右按钮
            case R.id.btn_next:
                //如果当前位置在dataList范围内，就直接增加
                curPosition++;
                if (curPosition <= dataList.size() - 1){
                    showToCurPosition();
                }
                //如果当前位置在dataList范围外，就向服务器请求
                else{
                    //loadMoreData();
                    onLoadMore();
                }
                break;
        }
    }

    private void showToCurPosition(){
        adapter.setCurPosition(curPosition);
        recyclerView.smoothScrollToPosition(curPosition+1);
        adapter.notifyDataSetChanged();
        setFragment(curPosition);
    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }

    private boolean b = true;
    @Override
    public void onRefresh() {
        Log.e("EnglishWordsActivity","onRefresh");
        curPageNo = 0;
        dataList.clear();
        //adapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b = !b;
                loadMoreData();

            }
        }, 2000);
    }

    boolean isLoadingMore = false;//防止多次请求
    @Override
    public void onLoadMore() {
        if (!isLoadingMore){
            isLoadingMore = true;
            Log.e("EnglishWordsActivity","onLoadMore");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.stopLoadMore();
                    loadMoreData();
//                list.add(list.size(), "leefeng.me" + "==onLoadMore");

                }
            }, 2000);
        }
    }
}
