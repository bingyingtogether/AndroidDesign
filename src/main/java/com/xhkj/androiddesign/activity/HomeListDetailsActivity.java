package com.xhkj.androiddesign.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xhkj.androiddesign.R;
import com.xhkj.androiddesign.utils.StatusBarUtil;

public class HomeListDetailsActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list_details);
        imageView = (ImageView) findViewById(R.id.ivImage);
        // StatusBarUtil.setTranslucent(this);
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
        //Toolbar
        setToolBar();
        setSecondTitle();
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
}
