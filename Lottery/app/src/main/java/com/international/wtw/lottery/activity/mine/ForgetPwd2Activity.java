package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.ErrorPhoneBean;
import com.international.wtw.lottery.utils.LogUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 忘记密码
 */
public class ForgetPwd2Activity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView_toLeftArrow;
    private EditText et_new_pwd, et_new_pwd_ok;
    private Button btn_binding;
    private String info, et_username;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd2;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
    }

    private void InitView() {
        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_new_pwd_ok = (EditText) findViewById(R.id.et_new_pwd_ok);
        btn_binding = (Button) findViewById(R.id.btn_binding);

        imageView_toLeftArrow.setOnClickListener(this);
        btn_binding.setOnClickListener(this);

        et_username = getIntent().getStringExtra("et_username");
        info = getIntent().getStringExtra("info");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.btn_binding:
                if (!TextUtils.isEmpty(et_new_pwd.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(et_new_pwd_ok.getText().toString().trim())) {
                        if (et_new_pwd.getText().toString().trim().equals(et_new_pwd_ok.getText().toString().trim())) {
                            SetData1(et_username, info, et_new_pwd_ok.getText().toString().trim());
                        } else {
                            ToastDialog.error("两次密码输入不一致").show(getSupportFragmentManager());
                        }
                    } else {
                        ToastDialog.error("请输入新密码").show(getSupportFragmentManager());
                    }
                } else {
                    ToastDialog.error("请再次输入新密码").show(getSupportFragmentManager());
                }
                break;
        }
    }

    private void SetData1(String username, String info, String new_password) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", username);
        jsonParams.put("Verification_char", info);
        jsonParams.put("new_password", new_password);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        ForgetRequest1(body);
    }

    private void ForgetRequest1(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.CHANGEPASSWD)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("忘记密码---" + responseCode + "-" + result);

                    if (responseCode == 200) {
                        Gson gson = new Gson();
                        ErrorPhoneBean errorPhoneBean = gson.fromJson(result, ErrorPhoneBean.class);
                        String info = errorPhoneBean.getInfo();
                        int msg = errorPhoneBean.getMsg();
                        if (msg == 2006) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastDialog.success(info)
                                            .setDismissListener(new ToastDialog.OnDismissListener() {
                                                @Override
                                                public void onDismiss(ToastDialog dialog) {
                                                    startActivity(new Intent(ForgetPwd2Activity.this, UpdateLoginPwdActivity.class));
                                                }
                                            })
                                            .show(getSupportFragmentManager());
                                }
                            });
                        } else {
                            ToastDialog.error(info).show(getSupportFragmentManager());
                        }
                    } else {
                        LogUtil.e("请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
