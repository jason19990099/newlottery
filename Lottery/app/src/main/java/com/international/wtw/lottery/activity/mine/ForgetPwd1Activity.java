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
import com.international.wtw.lottery.utils.CountDownTimerUtils;
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
public class ForgetPwd1Activity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView_toLeftArrow;
    private EditText et_username, et_phone, et_code;
    private Button btn_code, btn_binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd1;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
    }

    private void InitView() {
        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        et_username = (EditText) findViewById(R.id.et_username);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_binding = (Button) findViewById(R.id.btn_binding);

        imageView_toLeftArrow.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_binding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.btn_code:
                if (!TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    if (!TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                        SetData1(et_username.getText().toString().trim(), et_phone.getText().toString().trim());
                    } else {
                        ToastDialog.error("请输入手机号").show(getSupportFragmentManager());
                    }
                } else {
                    ToastDialog.error("请输入用户名").show(getSupportFragmentManager());
                }
                break;
            case R.id.btn_binding:
                if (!TextUtils.isEmpty(et_code.getText().toString().trim())) {
                    SetData2(et_username.getText().toString().trim(), et_code.getText().toString().trim());
                } else {
                    ToastDialog.error("请输入验证码").show(getSupportFragmentManager());
                }
                break;
        }
    }

    private void SetData1(String username, String phone) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", username);
        jsonParams.put("mobile_phone", phone);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        CodeRequest1(body);
    }

    private void CodeRequest1(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.SEND_VERIFICATIONCODE)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("忘记密码---手机验证码-" + responseCode + "-" + result);

                    if (responseCode == 200) {
                        Gson gson = new Gson();
                        ErrorPhoneBean errorPhoneBean = gson.fromJson(result, ErrorPhoneBean.class);
                        String info = errorPhoneBean.getInfo();
                        int msg = errorPhoneBean.getMsg();
                        if (msg == 2006) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(btn_code, 60000, 1000);
                                    countDownTimerUtils.start();
                                    ToastDialog.success(info).show(getSupportFragmentManager());
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

    private void SetData2(String username, String code) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", username);
        jsonParams.put("verification_code", code);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        CodeRequest2(body);
    }

    private void CodeRequest2(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.MAKESURE_VERIFICATIONCODE)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("忘记密码---验证码验证-" + responseCode + "-" + result);

                    if (responseCode == 200) {
                        Gson gson = new Gson();
                        ErrorPhoneBean errorPhoneBean = gson.fromJson(result, ErrorPhoneBean.class);
                        String info = errorPhoneBean.getInfo();
                        int msg = errorPhoneBean.getMsg();
                        if (msg == 2006) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastDialog.success("验证码通过")
                                            .setDismissListener(new ToastDialog.OnDismissListener() {
                                                @Override
                                                public void onDismiss(ToastDialog dialog) {
                                                    Intent intent = new Intent(ForgetPwd1Activity.this, ForgetPwd2Activity.class);
                                                    intent.putExtra("et_username", et_username.getText().toString().trim());
                                                    intent.putExtra("info", info);
                                                    startActivity(intent);
                                                }
                                            }).show(getSupportFragmentManager());
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
