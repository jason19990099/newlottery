package com.international.wtw.lottery.activity.manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.event.PaymentCompletedEvent;
import com.international.wtw.lottery.widget.WebViewEmptyViewClient;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 2017/7/2.
 * 在线支付网页
 */

public class WebOnlinePayActivity extends BaseActivity implements View.OnClickListener {
    public static final String PAY_URL = "pay_url";
    public static final String PAY_ID = "pay_id";
    public static final String USER_ID = "user_id";
    public static final String PAY_MONEY = "pay_money";
    public static final String PAY_TYPE = "pay_type";
    public static final String BANK_NAME = "bank_name";
    public static final String IsShow_Bank = "is_show_bank";
    public static final String GAME_NAME = "game_name";


    private ImageView img_back;
    private TextView tv_title;
    private WebView web_online_pay;
    private ProgressDialog dialog;
    private String payUrl, userid, payId, money, bankName;
    private String payType;
    private boolean isShowBank;
    private String gameName;
    private WebSettings settings;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_online_pay_web;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        payUrl = getIntent().getStringExtra(PAY_URL);
        userid = getIntent().getStringExtra(USER_ID);
        payId = getIntent().getStringExtra(PAY_ID);
        bankName = getIntent().getStringExtra(BANK_NAME);
        gameName = getIntent().getStringExtra(GAME_NAME);
        isShowBank = getIntent().getBooleanExtra(IsShow_Bank, false);
        money = getIntent().getStringExtra(PAY_MONEY);
        payType = getIntent().getStringExtra(PAY_TYPE);
        img_back = getViewHolder().get(R.id.img_back);
        tv_title = getViewHolder().get(R.id.tv_title);
        web_online_pay = getViewHolder().get(R.id.web_online_pay);
        img_back.setOnClickListener(this);

        dialog = new ProgressDialog(this, R.style.MyDialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setProgressNumberFormat("");
        dialog.setProgressPercentFormat(null);
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        dialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
        dialog.setMax(100);
        dialog.show();
        initWebSetting();

    }

    private void initWebSetting() {
        settings = web_online_pay.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setBlockNetworkImage(false);// 解决图片不显示
        //设置 缓存模式
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 开启 DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 将图片调整到合适的大小
        settings.setUseWideViewPort(true);
        //提高渲染优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //把图片加载放在最后来加载渲染 
        //settings.setBlockNetworkImage(true);
        //web_online_pay.loadUrl(payUrl);
        postUrl(web_online_pay, payUrl, userid, payId, money);

        web_online_pay.setWebViewClient(new WebViewEmptyViewClient(this));
        web_online_pay.setWebChromeClient(new AppCacheWebChromeClient());
    }

    private void postUrl(WebView webView, String payUrl, String userid, String payId, String money) {
        if (TextUtils.isEmpty(userid) || TextUtils.isEmpty(payId) || TextUtils.isEmpty(money)) {
            web_online_pay.loadUrl(payUrl);
        } else {
            //第一步：拼接请求参数
            String postData;
            if (isShowBank) {
                postData = "userid=" + userid
                        + "&pay_id=" + payId
                        + "&PayID=" + getPayID(bankName)
                        + "&GameName=" + gameName
                        + "&money=" + money;
            } else {
                postData = "userid=" + userid
                        + "&pay_id=" + payId
                        + "&GameName=" + gameName
                        + "&money=" + money;
            }
            //第二步：直接postURl
            webView.postUrl(payUrl, /*EncodingUtils.getBytes(postData, "BASE64")*/getBytes(postData, "BASE64"));
        }
    }

    private String getPayID(String bankName) {
        String payID = "UNP";
        switch (bankName) {
            case "工商银行":
                payID = "ICBC";
                break;
            case "建设银行":
                payID = "CCB";
                break;
            case "招商银行":
                payID = "CMB";
                break;
            case "农业银行":
                payID = "ABC";
                break;
            case "中国银行":
                payID = "BOC";
                break;
            case "邮政储蓄银行":
                payID = "PSBS";
                break;
            case "民生银行":
                payID = "CMBC";
                break;
            case "兴业银行":
                payID = "CIB";
                break;
            case "中信银行":
                payID = "CTTIC";
                break;
            case "渤海银行":
                payID = "CBHB";
                break;
            case "光大银行":
                payID = "CEB";
                break;
            case "广发银行":
                payID = "GDB";
                break;
            case "华夏银行":
                payID = "HXB";
                break;
            case "平安银行":
                payID = "PAB";
                break;
            case "浦发银行":
                payID = "SPDB";
                break;
            case "北京农商银行":
                payID = "BJRCB";
                break;
            case "上海银行":
                payID = "SHB";
                break;
            case "交通银行":
                payID = "BOCO";
                break;
            case "上海农商银行":
                payID = "SRCB";
                break;
            case "北京银行":
                payID = "BCCB";
                break;
        }
        return payID;
    }

    /**
     * EncodingUtils里的源码差不多也是这两句
     */
    private byte[] getBytes(@NonNull String data, @NonNull String charsetName) {
        try {
            return data.getBytes(charsetName);
        } catch (final UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    private class AppCacheWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                dialog.dismiss();
                web_online_pay.setVisibility(View.VISIBLE);
            } else {
                web_online_pay.setVisibility(View.GONE);
                dialog.setProgress(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.contains("com") && null != payType) {
                tv_title.setText(payType);
            } else {
                tv_title.setText(title);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        /*if (pageGoBack(web_online_pay, mWebViewClient)) {
            web_online_pay.goBack();
        } else {*/
        EventBus.getDefault().post(new PaymentCompletedEvent());
        super.onBackPressed();
        //}
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (web_online_pay != null) {
            web_online_pay.destroy();
        }
        super.onDestroy();
    }
}