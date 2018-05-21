package edu.buaa.beibeismart.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
import edu.buaa.beibeismart.R;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> implements View.OnClickListener {
    private ArrayList<Map> dataList;
    Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public CatalogAdapter(Context context, ArrayList dataList, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.dataList = dataList;
        this.context = context;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_catalog,parent,false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource((Integer) dataList.get(position).get("img"));

        String text = (String) dataList.get(position).get("catalog");
        holder.textView.setTextSize(24);
        if (text.trim().length() >= 3){
            holder.textView.setTextSize(24);
        }
        holder.textView.setText(text);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {
        onRecyclerViewItemClickListener.onItemClickListener(view,((int)view.getTag()));
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.img_gvi_catalog);
            textView = itemView.findViewById(R.id.tv_gvi_catalog);
        }
    }

}
