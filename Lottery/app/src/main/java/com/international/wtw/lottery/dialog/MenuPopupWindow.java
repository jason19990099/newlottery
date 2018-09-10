package com.international.wtw.lottery.dialog;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.login.LoginActivity;
import com.international.wtw.lottery.activity.mine.BetOnRecordActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.Locale;

/**
 * Created by Yuan on 2017/10/5.
 * 描述：
 */

public class MenuPopupWindow extends BaseCustomPopup implements View.OnClickListener {

    private FragmentActivity mActivity;
    private TextView mTvBalance;
    private TextView mTvRingtone;

    public MenuPopupWindow(FragmentActivity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_main_menu, SizeUtils.dp2px(210), ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {
        mTvBalance = getView(R.id.tv_balance);
        mTvRingtone = getView(R.id.tv_lottery_ringtone);
        getView(R.id.tv_recharge).setOnClickListener(this);
        getView(R.id.tv_withdraw).setOnClickListener(this);
        getView(R.id.tv_bet_record).setOnClickListener(this);
        getView(R.id.tv_game_rule).setOnClickListener(this);
        getView(R.id.tv_personal_center).setOnClickListener(this);
        getView(R.id.tv_service).setOnClickListener(this);
        getView(R.id.tv_log_out).setOnClickListener(this);
        getView(R.id.tv_lottery_ringtone).setOnClickListener(this);
        boolean isPlay = SharePreferencesUtil.getBoolean(mActivity, LotteryId.PLAY_RINGTONE, false);
        mTvRingtone.setSelected(isPlay);
    }

    @Override
    public void showAtAnchorView(@NonNull View anchor, @VerticalGravity int vertGravity, @HorizontalGravity int horizGravity) {
        setBalance();
        super.showAtAnchorView(anchor, vertGravity, horizGravity);
    }

    public MenuPopupWindow setBalance() {
        MoneyInfo moneyInfo = MoneyInfoManager.get().getMoneyInfo();
        mTvBalance.setText(moneyInfo == null ? "¥" + "0.00" : "¥" + String.format(Locale.CHINESE, "%.2f", moneyInfo.getMoney()));
        return this;
    }

    @Override
    public void onClick(View v) {
        boolean aBoolean = SharePreferencesUtil.getBoolean(mActivity, LotteryId.IS_SHI_WAN, false);
        switch (v.getId()) {
            case R.id.tv_recharge:
                if (!aBoolean) {
                    if (mActivity instanceof MainActivity) {
                        MainActivity activty = (MainActivity) mActivity;
                        activty.showMoneyFragment(0);
                    } else {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 0);
                        mActivity.startActivity(intent);
                    }
                } else {
                    ToastDialog.error(mActivity.getString(R.string.login_is_shiwan)).show(mActivity.getSupportFragmentManager());
                }
                dismiss();
                break;
            case R.id.tv_withdraw:
                if (!aBoolean) {
                    if (mActivity instanceof MainActivity) {
                        MainActivity activty = (MainActivity) mActivity;
                        activty.showMoneyFragment(1);
                    } else {
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_FRAGMENT_INDEX, 2);
                        intent.putExtra(MainActivity.EXTRA_CURRENT_MONEY_INDEX, 1);
                        mActivity.startActivity(intent);
                    }
                } else {
                    ToastDialog.error(mActivity.getString(R.string.login_is_shiwan)).show(mActivity.getSupportFragmentManager());
                }
                dismiss();
                break;
            case R.id.tv_bet_record:
                Intent intent3 = new Intent(mActivity,BetOnRecordActivity.class);
                intent3.putExtra("is_shi_wan",aBoolean);
                mActivity.startActivity(intent3);
                dismiss();
                break;
            case R.id.tv_lottery_ringtone:
                mTvRingtone.setSelected(!mTvRingtone.isSelected());
                SharePreferencesUtil.addBoolean(mActivity, LotteryId.PLAY_RINGTONE, mTvRingtone.isSelected());
                break;
            case R.id.tv_game_rule:
                Intent intent1 = new Intent(mActivity, WebViewActivity.class);
                intent1.putExtra(WebViewActivity.EXTRA_WEB_TITLE, "游戏规则");
                intent1.putExtra(WebViewActivity.EXTRA_WEB_URL, Constants.GAME_WEBSITE);
                mActivity.startActivity(intent1);
                dismiss();
                break;
            case R.id.tv_personal_center:
                if (mActivity instanceof MainActivity) {
                    MainActivity activty = (MainActivity) mActivity;
                    activty.changeShowFragment(1);
                } else {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    intent.putExtra("to_mine_frag", true);
                    mActivity.startActivity(intent);
                }
                dismiss();
                break;
            case R.id.tv_service:
                String serviceUrl = SharePreferencesUtil.getString(mActivity, LotteryId.SERVICE_URL, "");
                if (!TextUtils.isEmpty(serviceUrl)) {
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, mActivity.getString(R.string.service_online));
                    intent.putExtra(WebViewActivity.EXTRA_WEB_URL, serviceUrl);
                    intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                    mActivity.startActivity(intent);
                }
                dismiss();
                break;
            case R.id.tv_log_out:
                createDialog();
                dismiss();
                break;
        }
    }

    private void createDialog() {
        if (null == mActivity)
            return;
        View view = LayoutInflater.from(mActivity).inflate(R.layout.out_login_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(view);
        builder.setCancelable(false);
        builder.create();
        final AlertDialog dialog = builder.show();

        Button btn_out_login_cancel = (Button) view.findViewById(R.id.btn_out_login_cancel);
        Button btn_out_login_ok = (Button) view.findViewById(R.id.btn_out_login_ok);

        btn_out_login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_out_login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                dialog.dismiss();
            }
        });
    }

    private void logout() {
        SharePreferencesUtil.addString(mActivity, LotteryId.Login_oid, null);
        MoneyInfoManager.get().setMoneyInfo(null);
        Intent intent = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(intent);
    }
}
