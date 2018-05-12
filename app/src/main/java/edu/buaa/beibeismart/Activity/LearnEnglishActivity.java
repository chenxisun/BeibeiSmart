package edu.buaa.beibeismart.Activity;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.LearnEnglishAdapter;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;

public class LearnEnglishActivity extends BaseActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<String> {

    private GridView gvCatalogs;
    private ArrayList<Map<String,Object>> dataList =new ArrayList<>();
    private LearnEnglishAdapter adapter;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_learn_english);
        gvCatalogs = findViewById(R.id.gv_catalog);
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

        String topicId = (String) dataList.get(i).get("topicId");
        Intent intent = new Intent(LearnEnglishActivity.this,EnglishWordsActivity.class);
        intent.putExtra("topicId",topicId);

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

        String[] topicNames = {"ABC","水果","蔬菜","动物","植物"};
        String[] topicIds = {"abc","fruit","vegetable","animal","plant"};

        int catalogIcnos[] = {R.drawable.abc_64,R.drawable.fruit_64,R.drawable.vegetable_64,R.drawable.animal_64,R.drawable.plant_64};
        for (int i = 0; i<catalogIcnos.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("img",catalogIcnos[i]);
            map.put("topicName",topicNames[i]);
            map.put("topicId",topicIds[i]);
            dataList.add(map);
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

        Log.e("LearnEnglishActivityVolley", (String) result);
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
        Log.e("LearnEnglishActivityVolley", (String) response);
    }
}
