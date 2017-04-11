package com.xhkj.androiddesign.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xhkj.androiddesign.R;
import com.xhkj.androiddesign.utils.StatusBarUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeListDetailsActivity extends AppCompatActivity {
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list_details);
        tv_content = (TextView) findViewById(R.id.tv_content);
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
        setToolBar();
        setSecondTitle();
        getRedData();
    }

    //toolbar
    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    //使用CollapsingToolbarLayout后，title需要设置到CollapsingToolbarLayout上
    public void setSecondTitle() {
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getIntent().getStringExtra("title"));
        ImageView imageView = (ImageView) findViewById(R.id.ivImage);
        Glide.with(this).load(getIntent().getStringExtra("image")).into(imageView);
    }

    //网络请求
    public void getRedData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.connectTimeout(20, TimeUnit.SECONDS).build();
        //建立拼接表单
        FormBody.Builder form = new FormBody.Builder();
        //建立请求体
        RequestBody requestBody = form.build();
        //请求
        final Request request = new Request.Builder().url("http://www.lianjia.com/client/log")
                .post(requestBody).addHeader("Connection", "close").build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HomeListDetailsActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("result", result);
                        tv_content.setText(result);
                    }
                });
            }
        });
    }
}
