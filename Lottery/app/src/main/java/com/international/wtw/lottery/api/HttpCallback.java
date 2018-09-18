package com.international.wtw.lottery.api;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.login.MaintenanceActivity;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.Notice;
import com.international.wtw.lottery.json.QRModel;
import com.international.wtw.lottery.utils.ActivityManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class HttpCallback<T extends BaseModel> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (call.isCanceled()) {
            return;
        }
        if (response.isSuccessful()) {
            T model = response.body();
            if (model == null) {
                return;
            }

            if (model.isStatus()) {
                try {
                    onSuccess(model);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                onApiFailure(model);
            }


        } else {
            onFailure(call, new IOException("Unexpected code " + response.code()));
        }
    }

    private void onApiFailure(T model) {
        String msgCode = model.getMsg();
//        if ("4001".equals(msgCode)) {
//            FragmentActivity currActivity = (FragmentActivity) ActivityManager.getInstance().getCurrentActivity();
//            SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.Login_oid, null);
//            ToastDialog.error(Constants.getErrorCodeInfo("4001"))
//                    .setDismissListener(new ToastDialog.OnDismissListener() {
//                        @Override
//                        public void onDismiss(ToastDialog dialog) {
//                            Intent intent = new Intent(currActivity, LoginActivity.class);
//                            currActivity.startActivity(intent);
//                        }
//                    }).show(currActivity.getSupportFragmentManager());
//        } else if ("4003".equals(msgCode)) {
//            //系统维护, 跳转到维护页面
//            Activity currActivity = ActivityManager.getInstance().getCurrentActivity();
//            Intent intent = new Intent(currActivity, MaintenanceActivity.class);//清空之前的Activity栈
//            //清空Activity栈
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            currActivity.startActivity(intent);
//        }
        onFailure(msgCode,msgCode);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        t.printStackTrace();
        if (call.isCanceled()) {
            return;
        }
        if (t instanceof JSONException || t instanceof JsonParseException || t instanceof ParseException) {
            onFailure("10011", Constants.getErrorCodeInfo("10011"));
        } else if (t instanceof ConnectException) {
            onFailure("10012", Constants.getErrorCodeInfo("10012"));
        } else if (t instanceof SSLHandshakeException) {
            onFailure("10013", Constants.getErrorCodeInfo("10013"));
        } else if (t instanceof UnknownHostException) {
            onFailure("10014", Constants.getErrorCodeInfo("10014"));
        } else if (t instanceof SocketTimeoutException) {
            onFailure("10015", Constants.getErrorCodeInfo("10015"));
        } else {
            onFailure("10016", Constants.getErrorCodeInfo("10016"));
        }
    }

    public abstract void onSuccess(T data) throws Exception;

    public abstract void onFailure(String msgCode, String errorMsg);
}
