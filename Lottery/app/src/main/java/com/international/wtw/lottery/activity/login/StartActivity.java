package com.international.wtw.lottery.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.json.LotteryVersion;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.dialog.UploadApkDialog;

import org.json.JSONObject;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 启动页 检查是否更新
 */
public class StartActivity extends BaseActivity {
    private LotteryVersion lotteryVersion;
    private UploadApkDialog uploadApkDialog;  //升级的对话框
    private SweetAlertDialog SweetAlertDialog, SweetAlertDialog2;//1.提示升级的对话框  2.版本低于4.2的提示对话框
    private Gson gson = new Gson();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        ImageView view = (ImageView) findViewById(R.id.img_start);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                try {
                    checkAndroidvertion();
                } catch (Exception e) {
                    getVersion();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /**
     * 获取手机android的版本号
     */
    private void checkAndroidvertion() {
        String androidVertion = android.os.Build.VERSION.RELEASE;
        if (null != androidVertion && Double.valueOf(androidVertion.substring(0, 3)) < 4.2) {
            SweetAlertDialog2 = new SweetAlertDialog(StartActivity.this, cn.pedant.SweetAlert.SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            SweetAlertDialog2.setTitleText(getString(R.string.important_message))
                    .setCustomImage(R.mipmap.update)
                    .setContentText(getString(R.string.center_text2))
                    .setConfirmText(getString(R.string.close_app))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            SweetAlertDialog2.dismiss();
                            finish();
                        }
                    })
                    .show();
        } else {
            getVersion();
        }
    }

    /**
     * 获取升级的版本号
     */
    private void getVersion() {
        new Thread() {
            @Override
            public void run() {
                Map<String, Object> jsonParams = new ArrayMap<>();
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        (new JSONObject(jsonParams)).toString());
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.GET_VERSION_ANDROID)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body2 = response.body();
                    String result = body2.string();
                    lotteryVersion = gson.fromJson(result, LotteryVersion.class);
                    String vertionname = getAppVersionName(getApplicationContext());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (lotteryVersion == null) {
                                return;
                            }
                            if ("4003".equals(lotteryVersion.getMsg())) {
                                Intent intent = new Intent(StartActivity.this, MaintenanceActivity.class);
                                //清空Activity栈
                                intent.putExtra(MaintenanceActivity.TIME_BEGIN, lotteryVersion.getBegin());
                                intent.putExtra(MaintenanceActivity.TIME_END, lotteryVersion.getEnd());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            if (null != lotteryVersion.getForcedUpdate() &&
                                    null != lotteryVersion.getVersion() &&
                                    Float.parseFloat(lotteryVersion.getVersion()) > Float.parseFloat(vertionname)) {
                                if (isFinishing()) {
                                    return;
                                }
                                if (lotteryVersion.getForcedUpdate().equals("yes")) {
                                    SweetAlertDialog = new SweetAlertDialog(StartActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                                    SweetAlertDialog.setTitleText(getString(R.string.app_update) + lotteryVersion.getVersion())
                                            .setCustomImage(R.mipmap.update)
                                            .setContentText(getString(R.string.center_text))
                                            .setConfirmText(getString(R.string.confirm_text))
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    SweetAlertDialog.dismiss();
                                                    showUpdateDialog();
                                                }
                                            })
                                            .show();
                                } else {
                                    SweetAlertDialog = new SweetAlertDialog(StartActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                                    SweetAlertDialog.setTitleText(getString(R.string.app_update) + lotteryVersion.getVersion())
                                            .setCustomImage(R.mipmap.update)
                                            .setContentText(getString(R.string.center_text))
                                            .setCancelText(getString(R.string.cancle_text))
                                            .setConfirmText(getString(R.string.confirm_text))
                                            .showCancelButton(true)
                                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    SweetAlertDialog.dismiss();
                                                    goNext();
                                                }
                                            })
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    SweetAlertDialog.dismiss();
                                                    showUpdateDialog();
                                                }
                                            })
                                            .show();
                                }
                            } else {
                                goNext();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    goNext();
                }

            }


        }.start();
    }

    /**
     * 自动更新失败或者没有新的版本更新  走下一步
     */
    private void goNext() {
        String Login_oid = SharePreferencesUtil.getString(StartActivity.this, LotteryId.Login_oid, null);
        if (null == Login_oid) {
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        } else {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
        finish();
    }


    /**
     * 获取当前版本号
     */
    private String getAppVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "1.0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 展示加载升级对话框
     *
     * 
     */
    private void showUpdateDialog() {
        if (!isFinishing()) {
            if (null == uploadApkDialog) {
                uploadApkDialog = new UploadApkDialog(StartActivity.this, lotteryVersion.getUrl());
            }
            uploadApkDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != uploadApkDialog) {
            uploadApkDialog.dismiss();
        }
        if (null != SweetAlertDialog) {
            SweetAlertDialog.dismiss();
        }
        if (null != SweetAlertDialog2) {
            SweetAlertDialog2.dismiss();
        }
    }
}
