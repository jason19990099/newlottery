package com.international.wtw.lottery.activity.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.BalanceExchangeDialog;
import com.international.wtw.lottery.dialog.GoRegisterDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.AGChangeBean;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.TitleBar;

import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class NewAgGameActivity extends NewBaseActivity {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_ag_balance)
    TextView mTvAgBalance;
    @BindView(R.id.tv_lottery_balance)
    TextView mTvLotteryBalance;
    @BindView(R.id.ll_top_info)
    LinearLayout mLlTopInfo;
    @BindView(R.id.fl_bg)
    FrameLayout mFlBg;

    private boolean isDemo;
    private double lotteryBalance;
    private double agBalance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_ag_game;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mTitleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        isDemo = SharePreferencesUtil.getBoolean(this, LotteryId.IS_SHI_WAN, false);
        if (isDemo) {
            mLlTopInfo.setVisibility(View.GONE);
            mFlBg.setBackgroundResource(R.drawable.img_ag_bg);
        } else {
            mLlTopInfo.setVisibility(View.VISIBLE);
            mFlBg.setBackgroundResource(R.drawable.img_ag_bg_demo);
            showProgressDialog(getString(R.string.loading));
        }
    }

    public void requestAgInfo() {
        HttpRequest.getInstance().requestAccountAndAGBalance(this, new HttpCallback<AgAccountBalance>() {
            @Override
            public void onSuccess(AgAccountBalance data) {
                AgAccountBalance.BalanceBean balanceBean = data.getBalance();
                if (balanceBean != null) {
                    MoneyInfoManager.setBalanceBean(balanceBean);
                }
                dismissProgressDialog();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.success(errorMsg).show(getSupportFragmentManager());
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEvent(AgAccountBalance.BalanceBean event) {
        if (!isDemo) {
            lotteryBalance = event.getUserBalance();
            agBalance = event.getAgBalance();
            mTvAgBalance.setText(String.format(Locale.CHINESE, "%.2f", event.getAgBalance()));
            mTvLotteryBalance.setText(String.format(Locale.CHINESE, "%.2f", event.getUserBalance()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDemo) {
            requestAgInfo();
        }
    }

    @OnClick({R.id.tv_balance_exchange, R.id.tv_ag_recharge, R.id.tv_ag_withdraw, R.id.iv_try_game, R.id.iv_full_game})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_balance_exchange:
                showExchangeDialog();
                break;
            case R.id.tv_ag_recharge:
                toAGMoneyManage(0);
                break;
            case R.id.tv_ag_withdraw:
                toAGMoneyManage(1);
                break;
            case R.id.iv_try_game:
                requestAgUrl(0);
                break;
            case R.id.iv_full_game:
                if (isDemo) {
                    showRegisterDialog();
                } else {
                    //先判断是否有余额
                    if (agBalance <= 0) {
                        ToastDialog.warning("AG余额不足").show(getSupportFragmentManager());
                        return;
                    }
                    requestAgUrl(1);
                }
                break;
        }
    }

    private void toAGMoneyManage(int value) {
        Intent intent = new Intent(this, AGMoneyActivity.class);
        intent.putExtra(AGMoneyActivity.CURRENT_POSITION, value);
        intent.putExtra(AGMoneyActivity.AG_BALANCE, agBalance);
        startActivity(intent);
    }

    private void showExchangeDialog() {
        BalanceExchangeDialog.init(lotteryBalance, agBalance, new BalanceExchangeDialog.ExchangeListener() {

            @Override
            public void onCommit() {
                showProgressDialog("转换中...");
            }

            @Override
            public void onSuccess(AGChangeBean data) {
                dismissProgressDialog();
                ToastDialog.success("转换成功").show(getSupportFragmentManager());
                requestAgInfo();
            }

            @Override
            public void onFailure(String errorMsg) {
                dismissProgressDialog();
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        }).setMargin(40)
                .showDialog(getSupportFragmentManager());
    }

    private void showRegisterDialog() {
        new GoRegisterDialog()
                .setMargin(40)
                .showDialog(getSupportFragmentManager());
    }

    /**
     * @param type url类型:1-正式游戏  0-试玩
     */
    private void requestAgUrl(int type) {
        showProgressDialog(getString(R.string.loading));
        HttpRequest.getInstance().getAgGameLink(this, type, new HttpCallback<AGChangeBean>() {
            @Override
            public void onSuccess(AGChangeBean data) {
                dismissProgressDialog();
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, "AG");
                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, data.getLink());
                intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                dismissProgressDialog();
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
            }
        });
    }
}