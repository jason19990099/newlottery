package com.international.wtw.lottery.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.PersonalBean;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

/**
 * 个人中心
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imageView_toLeftArrow;
    private TextView oer_tv_zh, oer_tv_ye, oer_tv_zsxm, oer_tv_yx, oer_tv_phone1;
    private TextView tv_recharge, tv_withdrawal, tv_unbundling, tv_replacement, tv_bundling;
    private LinearLayout ll_phone_ok, ll_phone_no;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personalcenter;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
        SetData();
    }

    private void InitView() {
        oer_tv_zh = (TextView) findViewById(R.id.oer_tv_zh);
        oer_tv_ye = (TextView) findViewById(R.id.oer_tv_ye);
        oer_tv_zsxm = (TextView) findViewById(R.id.oer_tv_zsxm);
        oer_tv_yx = (TextView) findViewById(R.id.oer_tv_yx);
        oer_tv_phone1 = (TextView) findViewById(R.id.oer_tv_phone1);
        ll_phone_ok = (LinearLayout) findViewById(R.id.ll_phone_ok);
        ll_phone_no = (LinearLayout) findViewById(R.id.ll_phone_no);

        tv_recharge = (TextView) findViewById(R.id.tv_recharge);
        tv_withdrawal = (TextView) findViewById(R.id.tv_withdrawal);
        tv_unbundling = (TextView) findViewById(R.id.tv_unbundling);
        tv_replacement = (TextView) findViewById(R.id.tv_replacement);
        tv_bundling = (TextView) findViewById(R.id.tv_bundling);

        imageView_toLeftArrow = (ImageView) findViewById(R.id.imageView_toLeftArrow);

        tv_recharge.setOnClickListener(this);
        tv_withdrawal.setOnClickListener(this);
        tv_unbundling.setOnClickListener(this);
        tv_replacement.setOnClickListener(this);
        tv_bundling.setOnClickListener(this);
        imageView_toLeftArrow.setOnClickListener(this);

        //        String Login_phone = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.Login_phone, null);
        //        if (TextUtils.isEmpty(Login_phone)) {
        //            ll_phone_ok.setVisibility(View.GONE);
        //            ll_phone_no.setVisibility(View.VISIBLE);
        //        } else {
        //            ll_phone_ok.setVisibility(View.VISIBLE);
        //            ll_phone_no.setVisibility(View.GONE);
        //        }

    }

    @Override
    public void onClick(View v) {
        Activity mActivity = PersonalCenterActivity.this;
        switch (v.getId()) {
            case R.id.imageView_toLeftArrow:
                finish();
                break;
            case R.id.tv_recharge:
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                intent.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 0);
                mActivity.startActivity(intent);
                break;
            case R.id.tv_withdrawal:
                Intent intent1 = new Intent(mActivity, MainActivity.class);
                intent1.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                intent1.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 1);
                mActivity.startActivity(intent1);
                break;
            case R.id.tv_unbundling:
                startActivity(new Intent(PersonalCenterActivity.this, UnbundlingActivity.class));
                break;
            case R.id.tv_replacement:
                startActivity(new Intent(PersonalCenterActivity.this, ReplacementActivity.class));
                break;
            case R.id.tv_bundling:
                startActivity(new Intent(PersonalCenterActivity.this, NextPhoneActivity.class));
                break;
        }
    }

    private void SetData() {
        String login_oid = SharePreferencesUtil.getString(PersonalCenterActivity.this, LotteryId.Login_oid, null);
        HttpRequest.getInstance().getPersonalData(PersonalCenterActivity.this, login_oid, new HttpCallback<PersonalBean>() {
            @Override
            public void onSuccess(PersonalBean data) {
                oer_tv_zh.setText(data.getUserName());
                oer_tv_ye.setText(data.getBalance());
                oer_tv_zsxm.setText(data.getRealName());
                oer_tv_yx.setText(data.getMailbox());

                oer_tv_phone1.setText(data.getTelphone());
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg)
                        .setDismissListener(new ToastDialog.OnDismissListener() {
                            @Override
                            public void onDismiss(ToastDialog dialog) {
                                finish();
                            }
                        }).show(getSupportFragmentManager());
            }
        });

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
