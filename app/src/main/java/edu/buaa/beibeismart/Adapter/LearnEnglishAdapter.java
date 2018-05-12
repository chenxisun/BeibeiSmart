package edu.buaa.beibeismart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import edu.buaa.beibeismart.R;

public class LearnEnglishAdapter extends BaseAdapter {

    Context context;
    ArrayList<Map<String,Object>> dataList;


    public LearnEnglishAdapter(Context context,ArrayList<Map<String,Object>> dataList){
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
        View mView = LayoutInflater.from(context).inflate(R.layout.item_gridview_learn_english,null);

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,viewGroup.getHeight());
        mView.setLayoutParams(params);

        ImageView imageView = mView.findViewById(R.id.img_gvi_learn_english);
        TextView textView = mView.findViewById(R.id.tv_gvi_learn_english);

        imageView.setImageResource((Integer) dataList.get(i).get("img"));
        textView.setText((CharSequence) dataList.get(i).get("topicName"));

        return mView;
    }
}
