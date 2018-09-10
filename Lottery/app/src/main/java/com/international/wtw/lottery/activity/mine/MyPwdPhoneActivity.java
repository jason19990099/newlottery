package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.ErrorPhoneBean;
import com.international.wtw.lottery.utils.CountDownTimerUtils;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by XIAOYAN on 2017/10/29.
 */

public class MyPwdPhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView_toLeftArrow;
    private TextView tv_title;
    private EditText et_phone, et_code, et_new_pwd;
    private Button btn_code, btn_qrxg;
    private int isLoginPaly;
    private ImageView img_show_pwd;
    private boolean isShowPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pwd_phone;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
    }

    private void InitView() {
        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_qrxg = (Button) findViewById(R.id.btn_qrxg);
        img_show_pwd = (ImageView) findViewById(R.id.img_show_pwd);

        imageView_toLeftArrow.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_qrxg.setOnClickListener(this);
        img_show_pwd.setOnClickListener(this);

        isLoginPaly = getIntent().getIntExtra("IsLoginPaly", 0);
        if (isLoginPaly == 0) {
            tv_title.setText("修改登录密码");
        } else if (isLoginPaly == 1) {
            tv_title.setText("修改支付密码");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.btn_code:
                if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                    ToastDialog.error("请输入手机号").show(getSupportFragmentManager());
                    return;
                }
                String phone = et_phone.getText().toString().trim();
                if (isLoginPaly == 0) {
                    SetData(phone, 3);
                } else if (isLoginPaly == 1) {
                    SetData(phone, 2);
                }
                break;
            case R.id.btn_qrxg:
                if (TextUtils.isEmpty(et_code.getText().toString().trim())) {
                    ToastDialog.error("请输入验证码").show(getSupportFragmentManager());
                    return;
                }
                if (TextUtils.isEmpty(et_new_pwd.getText().toString().trim())) {
                    ToastDialog.error("请输入新密码").show(getSupportFragmentManager());
                    return;
                }
                String code = et_code.getText().toString().trim();
                String new_pwd = et_new_pwd.getText().toString().trim();
                SetData1(code, new_pwd, isLoginPaly);
                break;
            case R.id.img_show_pwd:
                if (isShowPwd) {
                    img_show_pwd.setImageResource(R.mipmap.no_see_pwd);
                    et_new_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPwd = false;
                } else {
                    img_show_pwd.setImageResource(R.mipmap.see_pwd);
                    et_new_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPwd = true;
                }
                break;
        }
    }

    private void SetData(String phone, int type) {
        String login_oid = SharePreferencesUtil.getString(MyPwdPhoneActivity.this, LotteryId.Login_oid, null);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("oid", login_oid);
        jsonParams.put("mobile_phone", phone);
        jsonParams.put("type_code", type);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        CodeRequest(body);
    }

    private void CodeRequest(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.VERIFICATIONCODE)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("手机验证码-" + responseCode + "-" + result);

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
                            ToastDialog.error(Constants.getErrorCodeInfo(msg + ""))
                                    .setDismissListener(new ToastDialog.OnDismissListener() {
                                        @Override
                                        public void onDismiss(ToastDialog dialog) {
                                            if (msg == 4001) {
                                                startActivity(new Intent(MyPwdPhoneActivity.this, LoginActivity.class));
                                            }
                                        }
                                    }).show(getSupportFragmentManager());
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

    private void SetData1(String code, String password, int isLoginPaly) {
        String login_oid = SharePreferencesUtil.getString(MyPwdPhoneActivity.this, LotteryId.Login_oid, null);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("oid", login_oid);
        jsonParams.put("verification_code", code);
        if (isLoginPaly == 0) {
            jsonParams.put("new_password", password);
        } else if (isLoginPaly == 1) {
            jsonParams.put("new_paypasswd", password);
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        if (isLoginPaly == 0) {
            ReplaceRequest1(body, Constants.CHANGELOGINPSD);
        } else if (isLoginPaly == 1) {
            ReplaceRequest1(body, Constants.CHANGEPAYPSD);
        }

    }

    private void ReplaceRequest1(RequestBody body, String url) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("修改密码-" + responseCode + "-" + result);

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
                                                    startActivity(new Intent(MyPwdPhoneActivity.this, MainActivity.class));
                                                }
                                            }).show(getSupportFragmentManager());
                                }
                            });
                        } else {
                            ToastDialog.error(Constants.getErrorCodeInfo(msg + ""))
                                    .setDismissListener(new ToastDialog.OnDismissListener() {
                                        @Override
                                        public void onDismiss(ToastDialog dialog) {
                                            if (msg == 4001) {
                                                startActivity(new Intent(MyPwdPhoneActivity.this, LoginActivity.class));
                                            }
                                        }
                                    }).show(getSupportFragmentManager());
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
