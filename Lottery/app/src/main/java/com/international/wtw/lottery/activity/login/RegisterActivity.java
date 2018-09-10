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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.LoginEvent;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.RequestResult;
import com.international.wtw.lottery.json.UserModel;
import com.international.wtw.lottery.listener.BaseEvent;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.RegexUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a bin 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    public final String REGISTER_SUCESS = "22";
    private EditText account;
    private EditText password;
    private EditText confirm_password;
    private EditText real_name;
    private EditText withdraw_password;
    private EditText email;
    private View view_1, view_2, view_3, view_4, view_5, view_6;
    private TextView tv_log;
    private ImageView img_show_pwd, img_show_pwd2;
    private boolean isShowPwd, isShowPwd2;
    private Gson gson = new Gson();
    private String logname, logpass;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register1;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        account = holder.get(R.id.edittext_account);
        password = holder.get(R.id.edittext_pwd);
        confirm_password = holder.get(R.id.edittext_confirm_pwd);
        real_name = holder.get(R.id.edittext_real_name);
        withdraw_password = holder.get(R.id.edittext_withdrawl_pwd);
        email = holder.get(R.id.edittext_email);
        real_name.addTextChangedListener(new LimitInputTextWatcher(real_name));
        view_1 = findViewById(R.id.view_1);
        view_2 = findViewById(R.id.view_2);
        view_3 = findViewById(R.id.view_3);
        view_4 = findViewById(R.id.view_4);
        view_5 = findViewById(R.id.view_5);
        view_6 = findViewById(R.id.view_6);
        tv_log = (TextView) findViewById(R.id.tv_log);
        img_show_pwd = holder.get(R.id.img_show_pwd);
        img_show_pwd2 = holder.get(R.id.img_show_pwd2);

        holder.setOnClickListener(this, R.id.btn_register);
        holder.setOnClickListener(this, R.id.btn_next);
        holder.setOnClickListener(this, R.id.imageView_toLeftArrow);
        img_show_pwd.setOnClickListener(this);
        img_show_pwd2.setOnClickListener(this);
        tv_log.setOnClickListener(this);
        setOnEditListener();
    }

    private void setOnEditListener() {
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view_1.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_1.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
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
                    view_2.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_2.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view_3.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_3.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        real_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view_4.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_4.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        withdraw_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view_5.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_5.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
                    if (s.length() == 4) {
                        KeyBoardUtils.closeKeyboard(RegisterActivity.this, withdraw_password);
                    } else {
                        KeyBoardUtils.openKeyboard(RegisterActivity.this, withdraw_password);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    view_6.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.color_cccc));
                } else {
                    view_6.setBackgroundColor(ContextCompat.getColor(RegisterActivity.this, R.color.middle_blue));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegister(BaseEvent<RequestResult> event) {
        RequestResult result = event.getBean();
        if (result != null) {
            if (result.getError_no().equals(REGISTER_SUCESS)) {
                ToastDialog.error(result.getInfo()).show(getSupportFragmentManager());
                //跳回到首页，并且进行登陆，获取uid
            } else {
                ToastDialog.error(result.getInfo()).show(getSupportFragmentManager());
            }
        } else {
            ToastDialog.error("注册失败").show(getSupportFragmentManager());
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register:
                getReg();
                break;
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.tv_log:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.img_show_pwd:
                if (isShowPwd) {
                    img_show_pwd.setImageResource(R.mipmap.no_see_pwd);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPwd = false;
                } else {
                    img_show_pwd.setImageResource(R.mipmap.see_pwd);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPwd = true;
                }
                break;
            case R.id.img_show_pwd2:
                if (isShowPwd2) {
                    img_show_pwd2.setImageResource(R.mipmap.no_see_pwd);
                    confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPwd = false;
                } else {
                    img_show_pwd2.setImageResource(R.mipmap.see_pwd);
                    confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPwd2 = true;
                }
                break;
            case R.id.btn_next:
                getReg();
                break;
        }
    }

    public void getReg() {
        if (TextUtils.isEmpty(account.getText().toString().replace(" ", ""))) {
            ToastDialog.error(getString(R.string.account_empty_fail)).show(getSupportFragmentManager());
            return;
        }
        if (account.getText().toString().trim().length() < 6 || account.getText().toString().trim().length() > 15) {
            ToastDialog.error(getString(R.string.account_length_fail)).show(getSupportFragmentManager());
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString().trim())) {
            ToastDialog.error(getString(R.string.pwd_empty_fail)).show(getSupportFragmentManager());
            return;
        }

        if (password.getText().toString().trim().length() < 6 || password.getText().toString().length() > 15) {
            ToastDialog.error(getString(R.string.pwd_length_fail)).show(getSupportFragmentManager());
            return;
        }

        if (TextUtils.isEmpty(confirm_password.getText().toString().trim())) {
            ToastDialog.error(getString(R.string.confirm_pwd_empty_fail)).show(getSupportFragmentManager());
            return;
        }

        if (TextUtils.isEmpty(confirm_password.getText().toString().trim())) {
            ToastDialog.error(getString(R.string.input_user_psw)).show(getSupportFragmentManager());
            return;
        }
        if (!password.getText().toString().equals(confirm_password.getText().toString())) {
            ToastDialog.error(getString(R.string.pwd_dif)).show(getSupportFragmentManager());
            return;
        }
//        if (!RegexUtil.isChinese(real_name.getText().toString().trim())) {
//            ToastDialog.error(getString(R.string.input_real_name)).show(getSupportFragmentManager());
//            return;
//        }
        if (TextUtils.isEmpty(withdraw_password.getText().toString().trim())) {
            ToastDialog.error(getString(R.string.draw_pwd_empty_fail)).show(getSupportFragmentManager());
            return;
        }
        if (withdraw_password.getText().toString().length() < 4) {
            ToastDialog.error(getString(R.string.draw_pwd_length_fail)).show(getSupportFragmentManager());
            return;
        }
        if (TextUtils.isEmpty(account.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(real_name.getText()) || TextUtils.isEmpty(withdraw_password.getText())) {
            ToastDialog.error(getString(R.string.fill_register_info)).show(getSupportFragmentManager());
            return;
        }

        HttpRequest.getInstance().register(RegisterActivity.this, account.getText().toString(), password.getText().toString(), real_name.getText().toString(), withdraw_password.getText().toString(), email.getText().toString(), "", "", new HttpCallback<BaseModel>() {
            @Override
            public void onSuccess(BaseModel data) {
                SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IS_NEW_REGISTER, true);

                HttpRequest.getInstance().login(RegisterActivity.this, account.getText().toString(), password.getText().toString(), new HttpCallback<UserModel>() {
                    @Override
                    public void onSuccess(UserModel data) {
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_oid, data.getOid());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_username, data.getUsername());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_realname, data.getRealname());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_qqskype, data.getQqskype());
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_monery, data.getMoney());
                        SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IS_SHI_WAN, false);
                        SharePreferencesUtil.addString(getApplicationContext(), LotteryId.Login_phone, data.getTelphone());

                        ToastDialog.success(getString(R.string.register_success))
                                .setDismissListener(new ToastDialog.OnDismissListener() {
                                    @Override
                                    public void onDismiss(ToastDialog dialog) {
                                        Login_Main();
                                        EventBus.getDefault().postSticky(new LoginEvent());
                                        finish();
                                    }
                                })
                                .show(getSupportFragmentManager());
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }

                });
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }

    public void Login_Main() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public class LimitInputTextWatcher implements TextWatcher {
        /**
         * et
         */
        private EditText et = null;
        /**
         * 筛选条件
         */
        private String regex;
        /**
         * 默认的筛选条件(正则:只能输入中文和·)
         */
        private String DEFAULT_REGEX = "[^·\u4E00-\u9FA5]";

        /**
         * 构造方法
         *
         * @param et
         */
        public LimitInputTextWatcher(EditText et) {
            this.et = et;
            this.regex = DEFAULT_REGEX;
        }

        /**
         * 构造方法
         *
         * @param et    et
         * @param regex 筛选条件
         */
        public LimitInputTextWatcher(EditText et, String regex) {
            this.et = et;
            this.regex = regex;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String str = editable.toString();
            String inputStr = clearLimitStr(regex, str);
            et.removeTextChangedListener(this);
            // et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容
            editable.replace(0, editable.length(), inputStr.trim());
            et.addTextChangedListener(this);

        }

        /**
         * 清除不符合条件的内容
         *
         * @param regex
         * @return
         */
        private String clearLimitStr(String regex, String str) {
            return str.replaceAll(regex, "");
        }
    }

    //判断一个字符串的首字符是否为字母
    public boolean test(String s) {
        char c = s.charAt(0);
        int i = (int) c;
        if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean check(String fstrData) {
        char c = fstrData.charAt(0);
        if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
            return true;
        } else {
            return false;
        }
    }
}
