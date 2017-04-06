package com.xhkj.androiddesign.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhkj.androiddesign.R;

import java.util.List;

/**
 * Created by xhkj_wjb on 2017/3/29.
 */

public class HomeListwwwAdapter extends RecyclerView.Adapter<HomeListwwwAdapter.MyViewHolder> {

    private List<RedModel.Data.Red> list;
    private Context context;
    private LayoutInflater inflater;

    public HomeListwwwAdapter(List<RedModel.Data.Red> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homelist_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getRed_img()).into(holder.imageView);
        holder.title.setText(list.get(position).getFirm_name());
        holder.content.setText(list.get(position).getRed_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title, content;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            title = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
