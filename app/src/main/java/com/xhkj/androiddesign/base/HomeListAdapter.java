package com.xhkj.androiddesign.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhkj.androiddesign.R;
import com.xhkj.androiddesign.bean.RedModel;

import java.util.List;

public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<RedModel.Data.Red> list;

    public HomeListAdapter(Context context, List<RedModel.Data.Red> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<RedModel.Data.Red> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<RedModel.Data.Red> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.homelist_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getRed_img()).into(viewHolder.imageView);
        viewHolder.title.setText(list.get(position).getFirm_name());
       viewHolder.content.setText(list.get(position).getRed_name());
        return convertView;
    }

    class ViewHolder {
         ImageView imageView;
         TextView title, content;
    }
}  