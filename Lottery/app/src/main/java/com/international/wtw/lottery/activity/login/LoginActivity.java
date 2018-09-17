package com.international.wtw.lottery.activity.login;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.mine.ForgetPwd1Activity;
import com.international.wtw.lottery.activity.mine.MyPasswordLoginActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.LoginEvent;
import com.international.wtw.lottery.json.UserModel;
import com.international.wtw.lottery.newJason.Login;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.RandomCode;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private EditText edittext_yzm;
    private ImageView iv_showCode, img_show_pwd;
    private String realCode;  //产生的验证码
    private boolean isShi;
    private boolean isShow;
    private View view1, view2, view3;
    private boolean isChecked = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(ViewHolder holder, View root) {
        username = holder.get(R.id.edittext_account);
        password = holder.get(R.id.edittext_pwd);

        holder.setOnClickListener(this, R.id.btn_login);
        holder.setOnClickListener(this, R.id.btn_regist);
        holder.setOnClickListener(this, R.id.textView_forgetPwd);
        holder.setOnClickListener(this, R.id.imageView_toLeftArrow);
        holder.setOnClickListener(this, R.id.bt_shi_wan);

        edittext_yzm = holder.get(R.id.edittext_yzm);

        iv_showCode = holder.get(R.id.iv_showCode);
        iv_showCode.setImageBitmap(RandomCode.getInstance().createBitmap());
        realCode = RandomCode.getInstance().getCode().toLowerCase();
        iv_showCode.setOnClickListener(this);

        img_show_pwd = holder.get(R.id.img_show_pwd);
        img_show_pwd.setOnClickListener(this);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        if (username.getText().length() > 0) {
            view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
        } else {
            view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
        }
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                } else {
                    view1.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view2.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                } else {
                    view2.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edittext_yzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view3.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.color_cccc));
                    KeyBoardUtils.openKeyboard(LoginActivity.this, edittext_yzm);
                } else {
                    view3.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.middle_blue));
                    if (s.length() == 4) {
                        KeyBoardUtils.closeKeyboard(LoginActivity.this, edittext_yzm);
                    } else {
                        KeyBoardUtils.openKeyboard(LoginActivity.this, edittext_yzm);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         *  选择是否记住密码
         */
        isChecked = SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, false);
        CheckBox cbx = (CheckBox) findViewById(R.id.checkbox);
        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, true);
                } else {
                    SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, false);
                }
            }
        });
        cbx.setChecked(isChecked);
        if (SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IF_REMEMBER_PASSWORD, false)) {
            username.setText(SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_username_remember, ""));
            password.setText(SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_paw_remember, ""));
        } else {
            username.setText("");
            password.setText("");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String name = username.getText().toString();
                String pwd = password.getText().toString();
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_username, name);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.USER_PSW, pwd);
                String yzm = edittext_yzm.getText().toString();
                if (name.isEmpty()) {
                    ToastDialog.error(getString(R.string.type_in_username)).show(getSupportFragmentManager());
                    return;
                }
                if (pwd.isEmpty()) {
                    ToastDialog.error(getString(R.string.pwd_empty_fail)).show(getSupportFragmentManager());
                    return;
                }
                if (pwd.length() < 6 || pwd.length() > 15) {
                    ToastDialog.error(getString(R.string.pwd_length_fail)).show(getSupportFragmentManager());
                    return;
                }

                if (TextUtils.isEmpty(edittext_yzm.getText().toString().trim())) {
                    ToastDialog.error(getString(R.string.yzm_empty)).show(getSupportFragmentManager());
                    return;
                }

                if (!yzm.equals(realCode)) {
                    ToastDialog.error(getString(R.string.yzm_fail)).show(getSupportFragmentManager());
                    return;
                }

                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_username_remember, name);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_paw_remember, pwd);
                HttpRequest.getInstance().login(LoginActivity.this, name, pwd, new HttpCallback<Login>() {
                    @Override
                    public void onSuccess(Login data) {

                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.TOKEN, data.getData());

                            ToastDialog.success("登录成功").setDismissListener(new ToastDialog.OnDismissListener() {
                                @Override
                                public void onDismiss(ToastDialog dialog) {
                                    Login_Main();
                                    EventBus.getDefault().postSticky(new LoginEvent());
                                    finish();
                                }
                            }).show(getSupportFragmentManager());
                        }


                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        if ("2013".equals(msgCode)) {
                            //如果用户被禁, 不自动消失
                            ToastDialog.error(errorMsg, false).show(getSupportFragmentManager());
                        } else {
                            ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                        }
                    }
                });
                break;

            case R.id.btn_regist:
                openActivity(RegisterActivity.class);
                break;

            case R.id.textView_forgetPwd:
                openActivity(ForgetPwd1Activity.class);
                break;

            case R.id.imageView_toLeftArrow:
                Login_Main();
                isShi = !isShi;
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_oid, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_username, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.USER_PSW, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_realname, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_qqskype, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_monery, null);
                SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_phone, null);
                finish();
                break;
            case R.id.iv_showCode:
                iv_showCode.setImageBitmap(RandomCode.getInstance().createBitmap());
                realCode = RandomCode.getInstance().getCode().toLowerCase();
                break;

            case R.id.img_show_pwd:
                if (isShow) {
                    img_show_pwd.setImageResource(R.mipmap.no_see_pwd);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShow = false;
                } else {
                    img_show_pwd.setImageResource(R.mipmap.see_pwd);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShow = true;
                }
                break;
            //试玩
            case R.id.bt_shi_wan:
                isShi = true;
                HttpRequest.getInstance().loginDemo(LoginActivity.this, new HttpCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data) {
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_oid, data.getOid());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_username, data.getUsername());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_realname, data.getRealname());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_qqskype, data.getQqskype());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_monery, data.getMoney());
                        SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IS_SHI_WAN, true);
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_phone, data.getTelphone());

                        ToastDialog.success("登录成功").setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                Login_Main();
                                EventBus.getDefault().postSticky(new LoginEvent());
                                finish();
                            }
                        }).show(getSupportFragmentManager());

                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        if ("2013".equals(msgCode)) {
                            //如果用户被禁, 不自动消失
                            ToastDialog.error(errorMsg, false).show(getSupportFragmentManager());
                        } else {
                            ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                        }
                    }
                });
                break;
        }
    }

    public void Login_Main() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("Login_Main", true);
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                if (KeyBoardUtils.closeKeyboard(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
