package com.international.wtw.lottery.widget;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.international.wtw.lottery.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WebViewEmptyViewClient extends WebViewClient {
    private Context mContext;
    private ViewStub mLoadViewStub, mErrorViewStub;
    private boolean isError = false;
    private int mErrorLayout, mErrorLayoutId, mLoadLayout, mLoadLayoutId;

    public WebViewEmptyViewClient(Context context) {
        this(context, R.layout.layout_error_view, R.id.error_layout, R.layout.dialog_loading, R.id.dialog_loading_view);
    }

    public WebViewEmptyViewClient(Context context, int mErrorLayout, int mErrorLayoutId, int mLoadLayout, int mLoadLayoutId) {
        this.mContext = context;
        this.mErrorLayout = mErrorLayout;
        this.mErrorLayoutId = mErrorLayoutId;
        this.mLoadLayout = mLoadLayout;
        this.mLoadLayoutId = mLoadLayoutId;
    }


    @Override
    public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (mErrorViewStub == null) {
            mErrorViewStub = new ViewStub(mContext);
            mErrorViewStub.setInflatedId(mErrorLayoutId);
            mErrorViewStub.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mErrorViewStub.setLayoutInflater(LayoutInflater.from(mContext));
            mErrorViewStub.setLayoutResource(mErrorLayout);
            mErrorViewStub.setClickable(true);
            view.addView(mErrorViewStub);

            View errorView = mErrorViewStub.inflate();
            errorView.findViewById(R.id.error_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.reload();
                }
            });
        } else {
            mErrorViewStub.setVisibility(VISIBLE);
        }
        isError = true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mLoadViewStub == null) {
            mLoadViewStub = new ViewStub(mContext);
            mLoadViewStub.setInflatedId(mLoadLayoutId);
            mLoadViewStub.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mLoadViewStub.setLayoutInflater(LayoutInflater.from(mContext));
            mLoadViewStub.setLayoutResource(mLoadLayout);
            view.addView(mLoadViewStub);
            mLoadViewStub.inflate();
        } else {
            mLoadViewStub.setVisibility(VISIBLE);
        }
        isError = false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mLoadViewStub.setVisibility(GONE);
        if (!isError && mErrorViewStub != null)
            mErrorViewStub.setVisibility(GONE);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            if (url.startsWith("weixin://") //微信
                    || url.startsWith("alipays://") //支付宝
                    || url.startsWith("mqqwpa://"))/*QQ支付*/ {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(intent);
                return false;
            }
        } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return false;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }
        return false;
    }
}
