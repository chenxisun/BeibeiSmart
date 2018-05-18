package edu.buaa.beibeismart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import edu.buaa.beibeismart.Bean.EnglishWordBean;
import edu.buaa.beibeismart.Interface.OnRecyclerViewItemClickListener;
import edu.buaa.beibeismart.R;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> implements View.OnClickListener {

    Context context;
    ArrayList<EnglishWordBean> dataList;
    int curPosition = 0;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;


    public CharacterAdapter(Context context,ArrayList dataList, OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.context = context;
        this.dataList = dataList;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setCurPosition(int position){
        curPosition = position;
    }

    @Override
    public void onClick(View view) {
        onRecyclerViewItemClickListener.onItemClickListener(view,((int)view.getTag()));
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_recyclerview_english_word);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView  = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_english_word_activity,null,false);
        mView.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(mView);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setSelected(curPosition == position);
        String chac = ""+dataList.get(position).getEnglishContent();
        holder.textView.setText(chac );
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
