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
import android.widget.Button;
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

    Button animal;
    Button fruit;
    Button english;
    Button vegetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike);
        findViewById(R.id.btn_baike_return).setOnClickListener(this);
        findViewById(R.id.btn_baike_fruit).setOnClickListener(this);
        findViewById(R.id.btn_baike_vegetable).setOnClickListener(this);
        findViewById(R.id.btn_baike_english).setOnClickListener(this);
        findViewById(R.id.btn_baike_animal).setOnClickListener(this);
        //LayoutInflater layoutInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
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
        private String image1Path;
        private String image2Path;
        private String image3Path;
        private String image4Path;
        private String image5Path;
        private String voicePath;
        private int dest;

        public void setVoicePath(String voicePath){
            this.voicePath = voicePath;
        }

        public String getVoicePath() {
            return voicePath;
        }

        public String getImage1Path(){
            return image1Path;
        }

        public void setImage1Path(String image1Path){
            this.image1Path = image1Path;
        }

        public String getImage2Path(){
            return image2Path;
        }

        public void setImage2Path(String image2Path){
            this.image2Path = image2Path;
        }

        public String getImage3Path(){
            return image3Path;
        }

        public void setImage3Path(String image3Path){
            this.image3Path = image3Path;
        }

        public String getImage4Path(){
            return image4Path;
        }

        public void setImage4Path(String image4Path){
            this.image4Path = image4Path;
        }

        public String getImage5Path(){
            return image5Path;
        }

        public void setImage5Path(String image5Path){
            this.image5Path = image5Path;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }

    private void setData() {

        System.out.println(ts.content.size());
        cityList = new ArrayList<CityItem>();
        CityItem item = new CityItem();
        for(int i=0;i<ts.content.size();i++){
            if(i >= 10) break;
            item = new CityItem();
            item.setCityName(ts.content.get(i).name);
            item.setVoicePath("http://47.94.165.157:8083" + ts.content.get(i).voicePath);
            item.setImage1Path("http://47.94.165.157:8083" + ts.content.get(i).img5Path);
            item.setImage2Path("http://47.94.165.157:8083" + ts.content.get(i).img2Path);
            item.setImage3Path("http://47.94.165.157:8083" + ts.content.get(i).img3Path);
            item.setImage4Path("http://47.94.165.157:8083" + ts.content.get(i).img4Path);
            item.setImage5Path("http://47.94.165.157:8083" + ts.content.get(i).img1Path);
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
            case R.id.btn_baike_animal:
                Intent intent2 = new Intent(BaikeActivity.this,Baike_ListActivity.class);
                startActivity(intent2);
                break;
            case  R.id.btn_baike_english:
                Intent intent3 = new Intent(BaikeActivity.this,Baike_ListActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_baike_fruit:
                Intent intent4 = new Intent(BaikeActivity.this,Baike_ListActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_baike_vegetable:
                Intent intent5 = new Intent(BaikeActivity.this,Baike_ListActivity.class);
                startActivity(intent5);
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