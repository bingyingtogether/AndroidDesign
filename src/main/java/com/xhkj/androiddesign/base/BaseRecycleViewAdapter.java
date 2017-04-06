package com.xhkj.androiddesign.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecycleViewAdapter基类(可直接初始化)
 * Created by xhkj_wjb on 2017/3/29.
 */
public class BaseRecycleViewAdapter<T, K extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<K>> {
    protected List<T> lists;    //数据源
    protected int resouceId;    //布局ID
    protected int variableId;   //布局内VariableId

    public BaseRecycleViewAdapter(List<T> lists, int resouceId, int variableId) {
        this.lists = lists;
        this.resouceId = resouceId;
        this.variableId = variableId;
    }

    @Override
    public BaseViewHolder<K> onCreateViewHolder(ViewGroup parent, int viewType) {
        K itemBing = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), resouceId, parent, false);   //获取DataBing相当于获取View
        BaseViewHolder<K> holder = new BaseViewHolder<K>(itemBing.getRoot());//初始化ViewHolder存放View
        holder.setDataBing(itemBing);
        return holder;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<K> holder, int position) {
        T data = lists.get(position);//获取数据
        holder.getDataBing().setVariable(variableId, data);//赋值
        holder.getDataBing().executePendingBindings();//刷新界面
    }

    @Override
    public int getItemCount() {
        return lists == null ? 0 : lists.size();
    }
}
