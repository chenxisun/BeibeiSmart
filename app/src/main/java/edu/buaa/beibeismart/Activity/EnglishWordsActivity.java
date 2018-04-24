package edu.buaa.beibeismart.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.R;

public class EnglishWordsActivity extends BaseActivity implements Response.ErrorListener, Response.Listener<String>, View.OnClickListener {

    Button btnReturn;
    StringRequest stringRequest;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    List<Character> dataList;

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

        initData();

       // recyclerView =

    }

    @Override
    protected void initData() {
        dataList = new ArrayList<Character>();
        for (int i = 0; i < 26; i++ ){
            dataList.add((char)('A'+i));
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {
        System.out.print("volley:"+result);
        Log.e("Volley", (String) result);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.print("volley:"+error);
        Log.e("Volley","error");
    }

    @Override
    public void onResponse(String response) {
        System.out.print("volley:"+response);
        Log.i("Volley",response);
    }

    @Override
    public void onClick(View view) {
        //String url = "http://192.168.1.137:8089/translate/好人";
        String url = "https://fanyi.youdao.com/openapi.do?keyfrom=xinlei&key=759115437&type=data&doctype=json&version=1.1&q=%E6%88%91%E7%9A%84";
        stringRequest = new StringRequest(Request.Method.GET,url,this,this);
        requestQueue = Volley.newRequestQueue(EnglishWordsActivity.this);

        requestQueue.add(stringRequest);
    }
}
