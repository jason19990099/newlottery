package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.MotionEvent;
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
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by XIAOYAN on 2017/10/13.
 */

public class NextPhoneActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView_toLeftArrow;
    private EditText et_phone, et_code;
    private Button btn_code, btn_binding;
    private TextView tv_skip;
    private String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_next;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        InitView();

    }

    private void InitView() {
        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_code = (Button) findViewById(R.id.btn_code);
        btn_binding = (Button) findViewById(R.id.btn_binding);
        tv_skip = (TextView) findViewById(R.id.tv_skip);

        imageView_toLeftArrow.setOnClickListener(this);
        btn_code.setOnClickListener(this);
        btn_binding.setOnClickListener(this);
        tv_skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.btn_code:
                if (!TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                    phone = et_phone.getText().toString().trim();
                    SetData(phone);
                } else {
                    ToastDialog.error("请输入手机号").show(getSupportFragmentManager());
                }
                break;
            case R.id.btn_binding:
                if (!TextUtils.isEmpty(et_code.getText().toString().trim())) {
                    String code = et_code.getText().toString().trim();
                    SetData1(code);
                } else {
                    ToastDialog.error("请输入验证码").show(getSupportFragmentManager());
                }
                break;
            case R.id.tv_skip:
                startActivity(new Intent(NextPhoneActivity.this, MainActivity.class));
                break;
        }
    }

    private void SetData(String phone) {
        String login_oid = SharePreferencesUtil.getString(NextPhoneActivity.this, LotteryId.Login_oid, null);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("oid", login_oid);
        jsonParams.put("mobile_phone", phone);
        jsonParams.put("type_code", 0);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        NextPhoneRequest(body);
    }

    private void NextPhoneRequest(RequestBody body) {
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
                                                startActivity(new Intent(NextPhoneActivity.this, LoginActivity.class));
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

    private void SetData1(String code) {
        String login_oid = SharePreferencesUtil.getString(NextPhoneActivity.this, LotteryId.Login_oid, null);
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("oid", login_oid);
        jsonParams.put("verification_code", code);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), (new JSONObject(jsonParams)).toString());
        CodeRequest1(body);
    }

    private void CodeRequest1(RequestBody body) {
        new Thread() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(Constants.BASE_URL + Constants.SMSAMOUNT)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    ResponseBody body = response.body();
                    int responseCode = response.code();
                    String result = body.string();
                    LogUtil.e("获取红包-" + responseCode + "-" + result);

                    if (responseCode == 200) {
                        Gson gson = new Gson();
                        ErrorPhoneBean errorPhoneBean = gson.fromJson(result, ErrorPhoneBean.class);
                        String info = errorPhoneBean.getInfo();
                        int msg = errorPhoneBean.getMsg();
                        if (msg == 2006) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastDialog.success("绑定成功")
                                            .setDismissListener(new ToastDialog.OnDismissListener() {
                                                @Override
                                                public void onDismiss(ToastDialog dialog) {
                                                    startActivity(new Intent(NextPhoneActivity.this, MainActivity.class));
                                                    SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_phone, phone);
                                                    finish();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                if (KeyBoardUtils.closeKeyboard(this, v)) {
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
