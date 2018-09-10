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
            /*
            真TM尴尬.. 本来想对所有请求做统一的错误处理, 但是这接口也太不规范太不统一了.
            蛋疼
            1.有的接口code=0表示成功, code=1表示失败
            2.有的接口code=1表示成功, code=0表示失败, msg表示提示信息
            3.有的接口code=200表示成功
            4.有的接口code=1&&msg=2006表示成功, 提示信息根据msg对应的编号提示
            5.有的接口返回了info字段来表示错误信息...
            太TM乱了
            * */
            if (model instanceof QRModel) {
                if (model.getCode() == 1) {
                    onSuccess(model);
                } else {
                    onApiFailure(model);
                }
            } else if (model instanceof Notice) {
                Notice item = (Notice) model;
                if (item.getInfo() == null) {
                    onSuccess(model);
                } else {
                    onApiFailure(model);
                }
            } else if (model.getCode() == 0 || (model.getCode() == 1 && "2006".equals(model.getMsg()))) {
                onSuccess(model);
            } else if (!TextUtils.isEmpty(model.getInfo()) && !"4001".equals(model.getMsg()) && !"4003".equals(model.getMsg())) {
                onFailure(model.getMsg(), model.getInfo());
            } else {
                onApiFailure(model);
            }
        } else {
            onFailure(call, new IOException("Unexpected code " + response.code()));
        }
    }

    private void onApiFailure(T model) {
        String msgCode = model.getMsg();
        if ("4001".equals(msgCode)) {
            FragmentActivity currActivity = (FragmentActivity) ActivityManager.getInstance().getCurrentActivity();
            SharePreferencesUtil.addString(BaseApplication.getAppContext(), LotteryId.Login_oid, null);
            ToastDialog.error(Constants.getErrorCodeInfo("4001"))
                    .setDismissListener(new ToastDialog.OnDismissListener() {
                        @Override
                        public void onDismiss(ToastDialog dialog) {
                            Intent intent = new Intent(currActivity, LoginActivity.class);
                            currActivity.startActivity(intent);
                        }
                    }).show(currActivity.getSupportFragmentManager());
        } else if ("4003".equals(msgCode)) {
            //系统维护, 跳转到维护页面
            Activity currActivity = ActivityManager.getInstance().getCurrentActivity();
            Intent intent = new Intent(currActivity, MaintenanceActivity.class);//清空之前的Activity栈
            //清空Activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(MaintenanceActivity.TIME_BEGIN, model.getBegin());
            intent.putExtra(MaintenanceActivity.TIME_END, model.getEnd());
            currActivity.startActivity(intent);
        }
        onFailure(msgCode, Constants.getErrorCodeInfo(msgCode));
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

    public abstract void onSuccess(T data);

    public abstract void onFailure(String msgCode, String errorMsg);
}
