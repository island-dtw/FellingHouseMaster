package com.dtw.fellinghousemaster.View.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dtw.fellinghousemaster.Bean.SimpleProductBean;
import com.dtw.fellinghousemaster.Bean.SimpleProductListBean;
import com.dtw.fellinghousemaster.Config;
import com.dtw.fellinghousemaster.Presener.MainPresener;
import com.dtw.fellinghousemaster.R;
import com.dtw.fellinghousemaster.View.BaseActivity;
import com.dtw.fellinghousemaster.View.Chart.ChartActivity;
import com.dtw.fellinghousemaster.View.Detail.DetailActivity;
import com.dtw.fellinghousemaster.View.Login.LoginActivity;
import com.dtw.fellinghousemaster.View.Setting.SettingActivity;
import com.dtw.fellinghousemaster.View.SimpleOnRecycleItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainView,NavigationView.OnNavigationItemSelectedListener ,SimpleOnRecycleItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mainRecycle;
    private SwipeRefreshLayout mainSwipeRefresh;
    private ProductStaggeredRecycleAdapter productStaggeredRecycleAdapter;
    private List<SimpleProductBean> simpleProductBeanList =new ArrayList<>();
    private MainPresener mainPresener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainPresener=new MainPresener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainSwipeRefresh= (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_main);
        mainSwipeRefresh.setOnRefreshListener(this);
        mainSwipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.swipeRefreshColor0),
                getResources().getColor(R.color.swipeRefreshColor1),
                getResources().getColor(R.color.swipeRefreshColor2),
                getResources().getColor(R.color.swipeRefreshColor3));

        mainRecycle= (RecyclerView) findViewById(R.id.recycle_main);
        mainRecycle.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        productStaggeredRecycleAdapter=new ProductStaggeredRecycleAdapter(this, simpleProductBeanList);
        productStaggeredRecycleAdapter.setSimpleOnRecycleItemClickListener(this);
        mainRecycle.setAdapter(productStaggeredRecycleAdapter);
        mainPresener.getSimpleProductList(SimpleProductListBean.class);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         //Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_login:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_chart:
                Intent chart = new Intent(this, ChartActivity.class);
                startActivity(chart);
                break;
            case R.id.nav_share:
//                wxSharePresener.sendTextMsg(Config.WXSceneSession,"hello","love");
                break;
            case R.id.nav_setting:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public <T> void onData(T data) {
        mainSwipeRefresh.setRefreshing(false);
        if (data instanceof SimpleProductListBean) {
            SimpleProductListBean simpleProductListBean= (SimpleProductListBean) data;
            simpleProductBeanList.clear();
            simpleProductBeanList.addAll(simpleProductListBean.getSimpleProductBeanList());
            productStaggeredRecycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        mainPresener.getSimpleProductList(SimpleProductListBean.class);
    }

    @Override
    public void onRecycleItemClick(String adapterClassName, View v, int position) {
        if (adapterClassName.equals(ProductStaggeredRecycleAdapter.class.getName())) {
            Log.v("dtw","click position:"+position);
            Intent intent=new Intent(this, DetailActivity.class);
            startActivity(intent);
        }
    }
}
