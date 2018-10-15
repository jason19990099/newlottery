package com.international.wtw.lottery.base.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.international.wtw.lottery.api.HttpLoggingInterceptor;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.listener.RequestClient;
import com.umeng.analytics.MobclickAgent;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;


public abstract class BaseActivity extends AppCompatActivity {
    protected RequestClient mRequestClient;
    protected ViewHolder mViewHolder;
    protected OkHttpClient client;
    private HttpLoggingInterceptor interceptor;
    protected Context mContext;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mRequestClient = RequestClient.getSingleInstance();
        mViewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        setContentView(mViewHolder.getRootView());
        ButterKnife.bind(this);
        initDatas();
        initViews(mViewHolder, mViewHolder.getRootView());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //友盟数据统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        HttpRequest.getInstance().cancel(this);
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化数据，调用位置在 initViews 之前
     */
    protected void initDatas() {
    }

    /**
     * 初始化 View， 调用位置在 initDatas 之后
     */
    protected abstract void initViews(ViewHolder holder, View root);

    // 默认点击左上角是结束当前 Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    public void openActivity(Class<?> cls) {
        openActivity(this, cls);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 打开 Activity 的同时传递一个数据
     */
    protected <V extends Serializable> void openActivity(Class<?> cls, String key, V value) {
        openActivity(this, cls, key, value);
    }


    /**
     * 打开 Activity 的同时传递一个数据
     */
    public <V extends Serializable> void openActivity(Context context, Class<?> cls, String key, V value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }



}
