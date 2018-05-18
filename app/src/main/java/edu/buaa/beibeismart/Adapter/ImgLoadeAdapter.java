package edu.buaa.beibeismart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import edu.buaa.beibeismart.Net.VolleyUtil;
import edu.buaa.beibeismart.R;

public class ImgLoadeAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> dataList;

    public ImgLoadeAdapter(Context context,ArrayList<String> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        System.out.println("img yaa");
        View mView = LayoutInflater.from(context).inflate(R.layout.item_gridview_word_fragment,viewGroup,false);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,viewGroup.getHeight());
        mView.setLayoutParams(params);
        ImageView imageView = mView.findViewById(R.id.img_gvi_words);
        String url1 = "https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp";
        String url = dataList.get(i);
        //VolleyUtil.getVolleyUtil().loadGlideImage(context, "https:////image.baidu.com//search//detail?ct=503316480&z=0&ipn=d&word=A&step_word=&hs=0&pn=5&spn=0&di=1879413151&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1749834660%2C276434744&os=2530367488%2C2591369708&simid=4294599927%2C647393007&adpicid=0&lpn=0&ln=1911&fr=&fmq=1524914643314_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=250&height=150&face=undefined&ist=&jit=&cg=&bdtype=15&oriquery=&objurl=http%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fthumb%2F2%2F2f%2FDowntownVictoriaBus.jpg%2F250px-DowntownVictoriaBus.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fjg_z%26e3Bothtrj1tw_z%26e3B562AzdH3FothtAzdH3FVtvp56tw_Rj2t5gws_T6wgftp_Syfpj4&gsm=78&rpstart=0&rpnum=0&islist=&querylist=",imageView);
        Glide.with(context).load(url).into(imageView);
        //imageView.setImageResource(R.drawable.read);
        return mView;
    }
}
