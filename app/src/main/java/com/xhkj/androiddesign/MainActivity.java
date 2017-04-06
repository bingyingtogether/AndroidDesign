package com.xhkj.androiddesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.scu.miomin.shswiperefresh.core.SHSwipeRefreshLayout;
import com.scu.miomin.shswiperefresh.view.SHListView;
import com.xhkj.androiddesign.activity.HomeListDetailsActivity;
import com.xhkj.androiddesign.base.HomeListAdapter;
import com.xhkj.androiddesign.base.MyBaseAdapter;
import com.xhkj.androiddesign.bean.GlideImageLoader;
import com.xhkj.androiddesign.bean.RedModel;
import com.xhkj.androiddesign.utils.GlideCircleTransform;
import com.xhkj.androiddesign.utils.StatusBarUtil;
import com.xhkj.androiddesign.utils.UtilTools;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SHSwipeRefreshLayout.SHSOnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TRANSLATE_VIEW = "sharedView";
    private SHListView recyclerView;
    private SHSwipeRefreshLayout refreshLayout;
    private List<RedModel.Data.Red> list;
    private MyBaseAdapter adapter;
    private HomeListAdapter homeListAdapter;
    private int state = 0;
    private int flag = 0;
    private DrawerLayout drawer;
    //头布局Banner图
    private View view;
    private Banner banner;
    private List<Integer> bannerImages;
    private int[] images = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawLayout();
        initViews();
        addListHeader();
    }

    //设置tBarool和侧滑菜单
    public void setDrawLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(MainActivity.this, drawer, getResources().getColor(R.color.holo_green_dark));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View drawview = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageView imageView = (ImageView) drawview.findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.mine).transform(new GlideCircleTransform(this)).into(imageView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag++;
                if (flag % 2 == 0) {
                    recyclerView.setAdapter(adapter);
                    Snackbar.make(view, "BindingView", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    recyclerView.setAdapter(homeListAdapter);
                    Snackbar.make(view, "CardView", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    //实例化主页面view
    public void initViews() {
        recyclerView = (SHListView) findViewById(R.id.recyclerView);
        recyclerView.setOnItemClickListener(this);
        refreshLayout = (SHSwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        list = new ArrayList<>();
        adapter = new MyBaseAdapter(MainActivity.this, R.layout.redlist_item, list, com.xhkj.androiddesign.BR.redBean);
        homeListAdapter = new HomeListAdapter(this, list);
        recyclerView.setAdapter(adapter);
        getRedData();
    }

    //网络请求
    public void getRedData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.connectTimeout(20, TimeUnit.SECONDS).build();
        //建立拼接表单
        FormBody.Builder form = new FormBody.Builder();
        //建立请求体
        RequestBody requestBody = form.build();
        final Request request = new Request.Builder().url("http://app.kxjie.cn/index.php/API/Red/index")
                .post(requestBody).addHeader("Connection", "close").build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("result", result);
                        Gson gson = new Gson();
                        RedModel redModel = gson.fromJson(result.toString(), RedModel.class);
                        list = redModel.getData().getRed();
                        if (state == 0) {
                            adapter.setList(list);
                            homeListAdapter.setList(list);
                        } else if (state == 1) {
                            adapter.setList(list);
                            homeListAdapter.setList(list);
                            refreshLayout.finishRefresh();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        state = 1;
        getRedData();
    }

    @Override
    public void onLoading() {
        state = 2;
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishLoadmore();
                Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
            }
        }, 1600);
    }

    /**
     * 监听下拉刷新过程中的状态改变
     *
     * @param percent 当前下拉距离的百分比（0-1）
     * @param state   分三种状态{NOT_OVER_TRIGGER_POINT：还未到触发下拉刷新的距离；OVER_TRIGGER_POINT：已经到触发下拉刷新的距离；START：正在下拉刷新}
     */
    @Override
    public void onRefreshPulStateChange(float percent, int state) {
        switch (state) {
            case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                refreshLayout.setRefreshViewText("下拉刷新");
                break;
            case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                refreshLayout.setRefreshViewText("松开刷新");
                break;
            case SHSwipeRefreshLayout.START:
                refreshLayout.setRefreshViewText("正在刷新");
                break;
        }
    }

    @Override
    public void onLoadmorePullStateChange(float percent, int state) {
        switch (state) {
            case SHSwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
                refreshLayout.setLoaderViewText("上拉加载");
                break;
            case SHSwipeRefreshLayout.OVER_TRIGGER_POINT:
                refreshLayout.setLoaderViewText("松开加载");
                break;
            case SHSwipeRefreshLayout.START:
                refreshLayout.setLoaderViewText("正在加载...");
                break;
        }
    }

    public void addListHeader() {
        view = LayoutInflater.from(this).inflate(R.layout.listheader_banner, null, false);
        banner = (Banner) view.findViewById(R.id.banner);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) banner.getLayoutParams();
        linearParams.width = UtilTools.getScreenWidth(this);
        linearParams.height = (UtilTools.getScreenWidth(this) * 33) / 50;
        banner.setLayoutParams(linearParams);
        bannerImages = new ArrayList<>();
        //添加本地图片
        for (int i = 0; i < images.length; i++) {
            bannerImages.add(images[i]);
        }
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(8000);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Accordion);
        banner.setImages(bannerImages);
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
            }
        });
        banner.start();
        recyclerView.addHeaderView(view);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i > 0) {
            Intent intent = new Intent(this, HomeListDetailsActivity.class);
            intent.putExtra("title", list.get(i - 1).getRed_name());
            intent.putExtra("image", list.get(i - 1).getRed_img());
            ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, view.findViewById(R.id.item_icon), TRANSLATE_VIEW).toBundle());
        }
    }
}
