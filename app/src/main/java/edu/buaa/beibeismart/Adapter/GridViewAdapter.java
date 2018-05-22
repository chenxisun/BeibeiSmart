package edu.buaa.beibeismart.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.content.Intent;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.buaa.beibeismart.Activity.BaikeActivity;
import edu.buaa.beibeismart.Activity.Baike_0_Activity;
import edu.buaa.beibeismart.R;

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
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ItemImage);
        final BaikeActivity.CityItem city = list.get(position);

        //Glide.with(context).load(city.getImagePath()).into(imageView);
        //imageView.setImageResource(city.getImageId());
        //System.out.println(city.getImagePath());
        //imageView.setImageResource(R.drawable.animal);
        tvCity.setText(city.getCityName());
        final BitMapClass bmc = new BitMapClass();

        Runnable networkImg = new Runnable() {
            @Override
            public void run(){
                try{
                    URL httpUrl = new URL(city.getImage1Path());
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
            System.out.println(position);
            continue;
        }
        imageView.setImageBitmap(bmc.bitmap);

        imageView.setId(position);
        imageView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        int num = view.getId();
        intent = new Intent(baikeActivity, Baike_0_Activity.class);
        intent.putExtra("voicePath",list.get(num).getVoicePath());
        intent.putExtra("img2Path",list.get(num).getImage2Path());
        intent.putExtra("img3Path",list.get(num).getImage3Path());
        intent.putExtra("img4Path",list.get(num).getImage4Path());
        intent.putExtra("img5Path",list.get(num).getImage5Path());
        intent.putExtra("position",num);
        //intent.putExtra("position",num);
        baikeActivity.startActivity(intent);
    }
}

class BitMapClass{
    public Bitmap bitmap = null;
}