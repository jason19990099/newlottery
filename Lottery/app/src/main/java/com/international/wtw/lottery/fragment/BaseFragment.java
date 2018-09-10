package com.international.wtw.lottery.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.international.wtw.lottery.api.HttpLoggingInterceptor;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.dialog.SimpleProgressDialog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * fragment父类
 * Created by 18Steven on 2017/6/24.
 */

public class BaseFragment extends Fragment {
    protected Dialog dialog;
    protected OkHttpClient client;
    private HttpLoggingInterceptor interceptor;
    protected String bet_money;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalOKHttpClient();
    }

    /**
     * 展示loading。。。。
     */
    public void showLoadingDialog() {
        if (null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (null == dialog) {
                        dialog = new SimpleProgressDialog(getActivity(), "请稍等...");
                    }
                    dialog.show();
                }
            });
        }
    }

    public void dismissDialog() {
        if (null != dialog && null != getActivity()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * 初始化okhttp
     */
    private void initalOKHttpClient() {
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 配置 client
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)                // 设置拦截器
                .retryOnConnectionFailure(true)             // 是否重试
                .connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)        // 连接超时事件
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)           // 读取超时时间
                .build();
    }

    @Override
    public void onDestroyView() {
        dismissDialog();
        HttpRequest.getInstance().cancel(this);
        super.onDestroyView();
    }

}
