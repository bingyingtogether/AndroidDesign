package com.xhkj.androiddesign.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * ViewHolder基类(相当于一个界面类,内部存放DataBing类对象,用于之后与数据进行绑定)
 * itemView相当于之前的ContentView
 * Created by yzy on 2016/9/26.
 */
public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private T dataBing;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public T getDataBing() {
        return dataBing;
    }

    public void setDataBing(T dataBing) {
        this.dataBing = dataBing;
    }
}