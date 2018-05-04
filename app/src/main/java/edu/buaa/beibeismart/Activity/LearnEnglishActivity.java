package edu.buaa.beibeismart.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.buaa.beibeismart.Adapter.LearnEnglishAdapter;
import edu.buaa.beibeismart.R;

public class LearnEnglishActivity extends BaseActivity implements View.OnClickListener {

    private GridView gvCatalogs;
    private ArrayList<Map<String,Object>> dataList;
    private LearnEnglishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //加载数据
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
        Intent intent;
        switch (i){
            case 0:
                intent = new Intent(LearnEnglishActivity.this,EnglishWordsActivity.class);
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }

    @Override
    protected void initData() {
        super.initData();

        String[] catalogNames = {"ABC","水果","蔬菜","动物","植物"};
        int catalogIcnos[] = {R.drawable.abc_64,R.drawable.fruit_64,R.drawable.vegetable_64,R.drawable.animal_64,R.drawable.plant_64};
        dataList = new ArrayList<Map<String,Object>>();
        for (int i = 0; i<catalogIcnos.length;i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("img",catalogIcnos[i]);
            map.put("text",catalogNames[i]);
            dataList.add(map);
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
