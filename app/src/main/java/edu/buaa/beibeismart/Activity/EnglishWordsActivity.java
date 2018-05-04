package edu.buaa.beibeismart.Activity;

import android.app.Activity;
import android.app.Fragment;
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
import edu.buaa.beibeismart.Fragment.BaseFragment;
import edu.buaa.beibeismart.Fragment.WordFragment;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
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
    ArrayList dataList = new ArrayList();
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
        //setFragment(0);

    }


    private void setFragment(int i){
        JSONObject fragmentParamJson = new JSONObject();
        try {
            String chacter = (String) jsonObject.names().get(i);
            JSONObject content = jsonObject.getJSONObject(chacter);
            fragmentParamJson.put("chacter",chacter);
            fragmentParamJson.put("content",content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString("param",fragmentParamJson.toString());
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

    @Override
    protected void initData() {

        String url = "http://47.94.165.157:8080/mock/hello";
        //String url = "https://fanyi.youdao.com/openapi.do?keyfrom=xinlei&key=759115437&type=data&doctype=json&version=1.1&q=%E6%88%91%E7%9A%84";
        stringRequest = new StringRequest(Request.Method.GET,url,this,this);
        requestQueue = Volley.newRequestQueue(EnglishWordsActivity.this);

        requestQueue.add(stringRequest);
        /*initJson();*/

    }

    private void initJson(){
        try {
            JSONObject jsonObjecta = new JSONObject();
            jsonObjecta.put("chinese_introduction","A 的中文解释");
            jsonObjecta.put("english_vedio","服务端自定义");
            jsonObjecta.put("english_introduction","The word, Apple, can be regard as a kind of fruits or a smart phone. You can get both of them!");
            jsonObjecta.put("chinese_vedio","服务端自定义");
            JSONObject imgUrl = new JSONObject();
            imgUrl.put("img1","https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=A&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1875575115,2012986140&os=1566147858,1294510543&simid=3494427969,347114886&pn=9&rn=1&di=49293184930&ln=1859&fr=&fmq=1524901101456_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004180229_erFVP.jpeg&rpstart=0&rpnum=0&adpicid=0&ctd=1524901166713^3_1231X550%1");
            imgUrl.put("img2","https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=A&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=1875575115,2012986140&os=1566147858,1294510543&simid=3494427969,347114886&pn=9&rn=1&di=49293184930&ln=1859&fr=&fmq=1524901101456_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201410%2F04%2F20141004180229_erFVP.jpeg&rpstart=0&rpnum=0&adpicid=0&ctd=1524901166713^3_1231X550%1");
            imgUrl.put("img3","https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=A&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=4209834082,3252743465&os=768096571,2884893162&simid=4154893210,670882886&pn=34&rn=1&di=184883309820&ln=1859&fr=&fmq=1524901101456_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fimg2.3lian.com%2F2014%2Ff4%2F181%2Fd%2F50.jpg&rpstart=0&rpnum=0&adpicid=0&ctd=1524901487488^3_1231X550%1");
            jsonObjecta.put("imgUrl",imgUrl);

            JSONObject jsonObjectb = new JSONObject();
            jsonObjectb.put("chinese_introduction","B 的中文解释");
            jsonObjectb.put("english_vedio","服务端自定义");
            jsonObjectb.put("english_introduction","B introduction content");
            jsonObjectb.put("chinese_vedio","服务端自定义");
            jsonObjectb.put("imgUrl",imgUrl);
            JSONObject jsonObjectc = new JSONObject();
            jsonObjectc.put("chinese_introduction","C 的中文解释");
            jsonObjectc.put("english_vedio","服务端自定义");
            jsonObjectc.put("english_introduction","C introduction content");
            jsonObjectc.put("chinese_vedio","服务端自定义");
            jsonObjectc.put("imgUrl",imgUrl);
            jsonObject = new JSONObject();
            jsonObject.put("A",jsonObjecta);
            jsonObject.put("B",jsonObjectb);
            jsonObject.put("C",jsonObjectc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onResponse(String response) {
        Log.i("onResponse",response);
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0; i < jsonObject.names().length();i++){
            try {
                dataList.add(jsonObject.names().get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
