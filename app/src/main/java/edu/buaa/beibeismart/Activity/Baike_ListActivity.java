package edu.buaa.beibeismart.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.buaa.beibeismart.BaikeItem;
import edu.buaa.beibeismart.Media.SlackMusicPlayer;
import edu.buaa.beibeismart.MyImageView;
import edu.buaa.beibeismart.Net.UrlUtil;
import edu.buaa.beibeismart.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Baike_ListActivity extends BaseActivity implements View.OnClickListener{

    TextView title;
    String animal_url = UrlUtil.IP+"/wikipedia/list?pageNo=0&pageSize=10";
    static String requestRes = "";
    String requestResbaikeList = "";
    String titlename="";

    MyImageView img1;
    MyImageView img2;
    MyImageView img3;
    MyImageView img4;
    SlackMusicPlayer slackBaikePlayer = SlackMusicPlayer.instance;
    TextView baikebiankuang1;
    TextView baikebiankuang2;
    TextView baikebiankuang3;
    TextView baikebiankuang4;
    Button baikeplay;
    List<BaikeItem> baikeItems=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baike__list);


        findViewById(R.id.btn_BackBaikeActivity).setOnClickListener(this);
        img1=findViewById(R.id.baikeItemImage1);
        img2=findViewById(R.id.baikeItemImage2);
        img3=findViewById(R.id.baikeItemImage3);
        img4=findViewById(R.id.baikeItemImage4);
        title=findViewById(R.id.BaikeTitle);
        baikebiankuang1=findViewById(R.id.baike_biankuang1);
        baikebiankuang2=findViewById(R.id.baike_biankuang2);
        baikebiankuang3=findViewById(R.id.baike_biankuang3);
        baikebiankuang4=findViewById(R.id.baike_biankuang4);
        titlename=getIntent().getStringExtra("type");
        baikeplay=findViewById(R.id.btn_baikeplay);
        findViewById(R.id.btn_baikeplay).setOnClickListener(this);
        baikeplay.setEnabled(false);

        BaikeAdapater baikeAdapater=new BaikeAdapater(this,R.layout.baike_item,baikeItems);
        ListView listView=findViewById(R.id.baike_listView);
        listView.setAdapter(baikeAdapater);
        setData();


    }

    public void setData(){

        switch (titlename) {
            case "animal":
            title.setText("动物百科");
        }



        baikeItems.add(new BaikeItem("alligator","/question/animal/img/alligator_2.jpg"));
        baikeItems.add(new BaikeItem("bear","/question/animal/img/bear_4.png"));
        baikeItems.add(new BaikeItem("monkey","/question/animal/img/monkey_1.png"));
        baikeItems.add(new BaikeItem("kangaroo","/question/animal/img/kangaroo_1.jpg"));
        baikeItems.add(new BaikeItem("giraffe","/question/animal/img/giraffe_1.png"));
        baikeItems.add(new BaikeItem("panda","/question/animal/img/panda_1.jpg"));
        baikeItems.add(new BaikeItem("lion","/question/animal/img/lion_1.jpg"));
        baikeItems.add(new BaikeItem("tiger","/question/animal/img/tiger_1.jpg"));
        baikeItems.add(new BaikeItem("elephant","/question/animal/img/elephant_1.png"));
        baikeItems.add(new BaikeItem("zebra","/question/animal/img/zebra_1.jpg"));
//        getRemoteData(animal_url);
//        requestResbaikeList = requestRes;
//        extractBaikeList(requestResbaikeList);


    }

    private void getRemoteData(final String url) {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get().build();
                try {
                    Response response = client.newCall(request).execute();
                    final String severResult = response.body().string();
                    extractBaikeList(severResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void extractBaikeList(final String requestResBaikeList) {

        try {
            JSONObject jobject = new JSONObject(requestResBaikeList);
            JSONArray jarray = jobject.getJSONArray("content");
            for (int i = 0; i < jarray.length(); i++) {
                BaikeItem baikeitem= new BaikeItem("","");
                JSONObject item = jarray.getJSONObject(i);
                String name = item.getString("name");
                String path = item.getString("Image5");
                baikeitem.setItemName(name);
                baikeitem.setPiturePath(path);
                baikeItems.add(baikeitem);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        slackBaikePlayer.pause();
        slackBaikePlayer.release();
    }

    @Override
    public void onVolleyFinish(int isSuccess, Object result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_BackBaikeActivity:
                slackBaikePlayer.pause();
                slackBaikePlayer.release();
                Intent intent = new Intent(Baike_ListActivity.this,BaikeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baikeplay:
                if (slackBaikePlayer.isPlaying()) {
                    slackBaikePlayer.pause();
                    baikeplay.setBackgroundResource(R.drawable.englishplay);
                } else {
                    slackBaikePlayer.continuePlay();;
                    baikeplay.setBackgroundResource(R.drawable.englishpause);
                }
            default:
                    break;
        }

    }

    class BaikeAdapater extends ArrayAdapter<BaikeItem> {



        public BaikeAdapater(Context context, int resource, List<BaikeItem> objects){
            super(context,resource,objects);

        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            final BaikeItem baikeItem=getItem(i);
            View onBaikeItemView= LayoutInflater.from(getContext()).inflate(R.layout.baike_item,null,false);
            final MyImageView imageView=onBaikeItemView.findViewById(R.id.baikeGeneralImage);
            TextView textView=onBaikeItemView.findViewById(R.id.baikeItemName);

           // imageView.setImageURL(UrlUtil.IP_Baike+baikeItem.getPiturePath());
            textView.setText(baikeItem.getItemName());


            onBaikeItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    slackBaikePlayer.pause();
                    slackBaikePlayer.release();
                    setPictureData();
                    setPlayerData();
                    baikeplay.setBackgroundResource(R.drawable.englishpause);
                    baikeplay.setEnabled(true);
                    baikebiankuang1.setBackgroundColor(Color.parseColor("#666699"));
                    baikebiankuang2.setBackgroundColor(Color.parseColor("#666699"));
                    baikebiankuang3.setBackgroundColor(Color.parseColor("#666699"));
                    baikebiankuang4.setBackgroundColor(Color.parseColor("#666699"));
                }

                private void setPlayerData() {
                    slackBaikePlayer.play(UrlUtil.IP_Baike+"/question/animal/voice/"+baikeItem.getItemName()+".mp3");
                }

                private void setPictureData() {
                    imageView.setImageURL(UrlUtil.IP_Baike+baikeItem.getPiturePath());

                    if(baikeItem.getItemName().equals("alligator")){
                        img1.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_1.jpg");
                        img2.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_2.jpg");
                        img3.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_3.png");
                        img4.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_4.png");

                    }else if(baikeItem.getPiturePath().endsWith("png")){
                        img1.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_1.png");
                        img2.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_2.png");
                        img3.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_3.png");
                        img4.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_4.png");
                    }else {
                        img1.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_1.jpg");
                        img2.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_2.jpg");
                        img3.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_3.jpg");
                        img4.setImageURL(UrlUtil.IP_Baike+"/question/animal/img/"+baikeItem.getItemName()+"_4.jpg");

                    }
                }

            });

            return onBaikeItemView;

        }



    }


}
