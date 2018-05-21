package edu.buaa.beibeismart.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import edu.buaa.beibeismart.Adapter.GridViewAdapter;
import edu.buaa.beibeismart.Interface.GetRequest_Interface;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaikeActivity extends BaseActivity implements View.OnClickListener,Response.ErrorListener, Response.Listener<String>{


    List<CityItem> cityList;
    RelativeLayout itmel;
    GridView gridView;
    String url = UrlUtil.IP+"/wikipedia/list?pageNo=0&pageSize=10";
    String Getstring;
    public Translation ts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike);
        findViewById(R.id.btn_baike_return).setOnClickListener(this);
        //LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
        gridView = (GridView)findViewById(R.id.grid);
        request(this);

        //getImage();
        //setData();
        //setGridView();
    }

    public void request(final BaikeActivity act) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.94.165.157:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<Translation> call = request.getCall();

        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, retrofit2.Response<Translation> response) {

                System.out.println("shqgdkqhdkjqhdk");
                act.ts = response.body();
                act.setData();
                act.setGridView();
            }
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }


    public class CityItem {
        private String cityName;
        private String cityCode;
        private String imagePath;
        private String voicePath;
        private int dest;

        public void setVoicePath(String voicePath){
            this.voicePath = voicePath;
        }

        public String getVoicePath() {
            return voicePath;
        }

        public String getImagePath(){

            return imagePath;
        }

        public void setImagePath(String imagePath){
            this.imagePath = imagePath;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }
    }

    private void setData() {

        System.out.println(ts.content.size());
        cityList = new ArrayList<CityItem>();
        CityItem item = new CityItem();
        for(int i=0;i<ts.content.size();i++){
            item = new CityItem();
            item.setCityName(ts.content.get(i).name);
            item.setCityCode("000");
            item.setVoicePath("http://47.94.165.157:8083" + ts.content.get(i).voicePath);
            String s = "http://47.94.165.157:8083" + ts.content.get(i).img1Path;
            item.setImagePath(s);
            cityList.add(item);
        }
        //cityList.addAll(cityList);
    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView() {
        int size = cityList.size();
        int length = 400;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int)(size * (length + 10) * density);
        int itemWidth = (int)(length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(40); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(), cityList, this);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        super.initData();
    }

    /**
     * 点击事件
     */

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_baike_return:
                Intent intent = new Intent(BaikeActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println(error);
    }

    public void onResponse(String response) {
        Log.e("BaikeActivityVolley", (String) response);
    }
}