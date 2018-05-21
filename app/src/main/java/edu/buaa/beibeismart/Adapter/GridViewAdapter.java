package edu.buaa.beibeismart.Adapter;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.content.Intent;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;

import edu.buaa.beibeismart.Activity.BaikeActivity;
import edu.buaa.beibeismart.Activity.Baike_0_Activity;
import edu.buaa.beibeismart.Activity.Baike_1_Activity;
import edu.buaa.beibeismart.R;
import retrofit2.http.Url;

public class GridViewAdapter extends BaseAdapter implements View.OnClickListener{
    Context context;
    List<BaikeActivity.CityItem> list;
    BaikeActivity baikeActivity;


    public GridViewAdapter(Context _context, List<BaikeActivity.CityItem> _list, BaikeActivity baikeActivity) {
        this.list = _list;
        this.context = _context;
        this.baikeActivity = baikeActivity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_gridview_baike, null);
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
        final BaikeActivity.CityItem city = list.get(position);

        //Glide.with(context).load(city.getImagePath()).into(imageView);
        //imageView.setImageResource(city.getImageId());
        //System.out.println(city.getImagePath());
        //imageView.setImageResource(R.drawable.animal);
        tvCity.setText(city.getCityName());
        tvCode.setText(city.getCityCode());
        final BitMapClass bmc = new BitMapClass();

        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{

                    URL httpUrl = new URL(city.getImagePath());
                    HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                    conn.setConnectTimeout(600);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    InputStream in = conn.getInputStream();
                    bmc.bitmap = BitmapFactory.decodeStream(in);
                    in.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(networkImg).start();
        while(bmc.bitmap == null){
            System.out.println("aaa");
            continue;
        }
        imageView.setImageBitmap(bmc.bitmap);
        imageView.setId(position % 5);
        imageView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case 0:
                intent = new Intent(baikeActivity, Baike_0_Activity.class);
                intent.putExtra("voicePath",list.get(0).getVoicePath());
                baikeActivity.startActivity(intent);
                break;
            case 1:
                intent = new Intent(baikeActivity, Baike_1_Activity.class);
                intent.putExtra("voicePath",list.get(1).getVoicePath());
                baikeActivity.startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}

class BitMapClass{
    public Bitmap bitmap = null;
}