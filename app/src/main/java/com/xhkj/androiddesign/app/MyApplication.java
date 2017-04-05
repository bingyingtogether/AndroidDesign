package com.xhkj.androiddesign.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by xhkj_wjb on 2017/3/29.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
