package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 修改取款密码
 */
public class TakeoutMoneyPasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private Button update_loginpwd_btn_determine;
    private EditText et_update_login_pwd_old, et_update_login_pwd_new, et_update_login_pwd_newonce;
    private ImageView imageView_toLeftArrow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mypass_login;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
    }

    private void InitView() {
        tvTitle.setText("取款密码修改");
        update_loginpwd_btn_determine = (Button) findViewById(R.id.update_loginpwd_btn_determine);
        update_loginpwd_btn_determine.setOnClickListener(this);

        et_update_login_pwd_old = (EditText) findViewById(R.id.et_update_login_pwd_old);
        et_update_login_pwd_new = (EditText) findViewById(R.id.et_update_login_pwd_new);
        et_update_login_pwd_newonce = (EditText) findViewById(R.id.et_update_login_pwd_newonce);

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);
        imageView_toLeftArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_loginpwd_btn_determine:

                String pwd_old = et_update_login_pwd_old.getText().toString();
                String pwd_new = et_update_login_pwd_new.getText().toString();
                String pwd_newonce = et_update_login_pwd_newonce.getText().toString();
                int pwd_new_length = pwd_new.length();
                int pwd_newonce_length = pwd_newonce.length();
                if (TextUtils.isEmpty(pwd_old)) {
                    ToastDialog.error("请输入旧密码").show(getSupportFragmentManager());
                    return;
                }
                if (TextUtils.isEmpty(pwd_new)) {
                    ToastDialog.error("请输入新密码").show(getSupportFragmentManager());
                    return;
                }
                if (pwd_new_length < 6 || pwd_new_length > 15) {
                    ToastDialog.error("输入6-15位密码").show(getSupportFragmentManager());
                    return;
                }
                if (TextUtils.isEmpty(pwd_newonce)) {
                    ToastDialog.error("请再次输入新密码").show(getSupportFragmentManager());
                    return;
                }
                if (pwd_newonce_length < 6 || pwd_newonce_length > 15) {
                    ToastDialog.error("输入6-15位密码").show(getSupportFragmentManager());
                    return;
                }
                if (!pwd_new.equals(pwd_newonce)) {
                    ToastDialog.error("两次输入密码不一样").show(getSupportFragmentManager());
                    return;
                }
                if (pwd_old.equals(pwd_new)) {
                    ToastDialog.error("新密码不能与旧密码相同").show(getSupportFragmentManager());
                    return;
                }

                String token = SharePreferencesUtil.getString(TakeoutMoneyPasswordActivity.this, LotteryId.TOKEN, null);
                HttpRequest.getInstance().changeTakeoutMoneyPassword(TakeoutMoneyPasswordActivity.this, token, pwd_old, pwd_new, new HttpCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        ToastDialog.success(getString(R.string.updatelogin_xgcg)).setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                startActivity(new Intent(TakeoutMoneyPasswordActivity.this, UpdateLoginPwdActivity.class));
                                SharePreferencesUtil.addString(TakeoutMoneyPasswordActivity.this, LotteryId.Login_oid, null);
                                finish();
                            }
                        }).show(getSupportFragmentManager());
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                    }
                });
                break;
            case R.id.imageView_toLeftArrow:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
