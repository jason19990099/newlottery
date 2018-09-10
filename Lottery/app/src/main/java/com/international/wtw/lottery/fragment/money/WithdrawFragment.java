package com.international.wtw.lottery.fragment.money;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.StringUtils;

import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  入款的面页
 */
public class WithdrawFragment extends NewBaseFragment {

    public static final String GAME_NAME = "game_name";

    @BindView(R.id.iv_bank_logo)
    ImageView mIvBankLogo;
    @BindView(R.id.tv_bank_name)
    TextView mTvBankName;
    @BindView(R.id.tv_bank_card_number)
    TextView mTvBankcardId;
    @BindView(R.id.rl_bankcard_info)
    RelativeLayout mRlBankInfo;
    @BindView(R.id.rl_add_bankcard)
    RelativeLayout mRlAddBank;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.et_embodied_amount)
    EditText mEtAmount;
    @BindView(R.id.edt_tx_psd)
    EditText mEtPayPassword;
    private boolean isDemo;//是否是试玩
    private MoneyInfo moneyInfo;
    private String mBankName;
    private String mBankcardId;
    private String mAmount;
    private String mPayPassword;

    private String mGameName;
    private AgAccountBalance.BalanceBean agInfo;

    public static WithdrawFragment newInstance(String gameName) {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_drawing;
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
        }
        isDemo = SharePreferencesUtil.getBoolean(mActivity, LotteryId.IS_SHI_WAN, false);
        mEtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (s.length() == 1 && text.equals("0")) {
                    s.clear();
                }
                //限制小数点后只能两位
                int posDot = text.indexOf(".");
                if (posDot <= 0)
                    return;
                if (text.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }
            }
        });

        moneyInfo = MoneyInfoManager.get().getMoneyInfo();
        if (moneyInfo != null) {
            setBankInfo();
        }

        //设置监听、4位数
        mEtPayPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                } else {
                    if (s.length() == 4) {
                        KeyBoardUtils.closeKeyboard(getActivity(), mEtPayPassword);
                    } else {
                        KeyBoardUtils.openKeyboard(getActivity(), mEtPayPassword);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setBankInfo() {
        mBankName = moneyInfo.getBank_name();
        mBankcardId = moneyInfo.getBank_code();
        if (TextUtils.isEmpty(mBankName) || TextUtils.isEmpty(mBankcardId)) {
            mRlBankInfo.setVisibility(View.GONE);
            mRlAddBank.setVisibility(View.VISIBLE);
        } else {
            mRlBankInfo.setVisibility(View.VISIBLE);
            mRlAddBank.setVisibility(View.GONE);
            mTvBankName.setText(mBankName);
            mIvBankLogo.setBackgroundResource(getImageRes(mBankName));
            mTvBankcardId.setText(StringUtils.formatBankcardId(mBankcardId));
        }
        String name = "账户";
        if (!TextUtils.isEmpty(mGameName)) {
            name = mGameName;
            agInfo = MoneyInfoManager.get().getAgInfo();
            if (agInfo != null) {
                mTvAccountBalance.setText(String.format(Locale.US, "(%s余额%.2f)", name, agInfo.getAgBalance()));
            }
        } else {
            mTvAccountBalance.setText(String.format(Locale.US, "(%s余额%.2f)", name, moneyInfo.getMoney()));
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEvent(MoneyInfoRefreshEvent event) {
        moneyInfo = event.moneyInfo;
        if (moneyInfo != null) {
            setBankInfo();
        }
    }

    @Subscribe
    public void onEvent(AgAccountBalance.BalanceBean bean) {
        agInfo = bean;
        mTvAccountBalance.setText(String.format(Locale.US, "(%s余额%.2f)", mGameName, bean.getAgBalance()));
    }

    @OnClick({R.id.rl_bankcard_info, R.id.rl_add_bankcard, R.id.btn_confirm, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bankcard_info:
                toBankcardActivity(getString(R.string.modify_bank_info), true);
                break;
            case R.id.rl_add_bankcard:
                toBankcardActivity(getString(R.string.add_bank_info), false);
                break;
            case R.id.btn_confirm:
                withdraw();
                break;
            case R.id.btn_reset:
                resetInput();
                break;
        }
    }

    /**
     * 添加或许编辑(修改)银行卡
     */
    private void toBankcardActivity(String title, boolean isModify) {
        if (isDemo) {
            ToastDialog.error(getString(R.string.no_permission)).show(getFragmentManager());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(BankcardActivity.TITLE, title);
        bundle.putBoolean(BankcardActivity.IS_MODIFY, isModify);
        bundle.putSerializable(BankcardActivity.BANK_INFO, moneyInfo);
        openActivity(BankcardActivity.class, bundle, false);
    }

    /**
     * 取款
     */
    private void withdraw() {
        mAmount = mEtAmount.getText().toString().trim();
        if (mAmount.endsWith(".")) {
            mAmount = mAmount.replace(".", "");
        }
        mPayPassword = mEtPayPassword.getText().toString().trim();
        if (isDemo) {
            ToastDialog.error(getString(R.string.no_permission)).show(getFragmentManager());
        } else if (moneyInfo == null || ("AG".equals(mGameName) && agInfo == null)) {
            ToastDialog.error(getString(R.string.is_loading_user_info)).show(getFragmentManager());
        } else if (TextUtils.isEmpty(mBankName) || TextUtils.isEmpty(mBankcardId)) {
            ToastDialog.error(getString(R.string.bind_bankcard_first)).show(getFragmentManager());
        } else if (TextUtils.isEmpty(mAmount)) {
            ToastDialog.error(getString(R.string.input_tx_amount)).show(getFragmentManager());
        } else if (Double.valueOf(mAmount.replace(",", "")) < 100.00) {
            ToastDialog.error(getString(R.string.money_beyond1)).show(getFragmentManager());
        } else {
            double balance = "AG".equals(mGameName) ? agInfo.getAgBalance() : moneyInfo.getMoney();
            if (Double.valueOf(mAmount.replace(",", "")) > balance) {
                ToastDialog.error(String.format(Locale.CHINESE, getString(R.string.money_beyond), balance)).show(getFragmentManager());
            } else if (TextUtils.isEmpty(mPayPassword)) {
                ToastDialog.error(getString(R.string.input_tx_pwd)).show(getFragmentManager());
            } else if (mPayPassword.length() != 4) {
                ToastDialog.error(getString(R.string.input_tx_pwd1)).show(getFragmentManager());
            } else {
                requestWithdraw();
            }
        }
    }

    private void resetInput() {
        mEtAmount.setText("");
        mEtPayPassword.setText("");
    }

    /**
     * 请求提款接口进行提款
     */
    private void requestWithdraw() {
        showLoadingDialog();
        HttpRequest.getInstance().requestWithdraw(this, mAmount, mPayPassword, mBankName,
                moneyInfo.getBank_address(), mBankcardId, mGameName, new HttpCallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        dismissLoadingDialog();
                        if (TextUtils.isEmpty(mGameName)) {
                            MoneyInfoManager.get().requestMoneyInfo();
                        } else if ("AG".equals(mGameName)) {
                            MoneyInfoManager.get().requestAgInfo();
                        }
                        ToastDialog.success(getString(R.string.operation_success)).show(getFragmentManager());
                        resetInput();
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        dismissLoadingDialog();
                        if ("AG".equals(mGameName)) {
                            MoneyInfoManager.get().requestAgInfo();
                        }
                        ToastDialog.error(errorMsg).show(getFragmentManager());
                    }
                });
    }

    private int getImageRes(String bankName) {
        switch (bankName) {
            case "工商银行":
                return R.mipmap.icon_bank_logo_icbc;
            case "建设银行":
                return R.mipmap.icon_bank_logo_ccb;
            case "招商银行":
                return R.mipmap.icon_bank_logo_cmb;
            case "农业银行":
                return R.mipmap.icon_bank_logo_abc;
            case "中国银行":
                return R.mipmap.icon_bank_logo_bc;
            case "邮政储蓄银行":
                return R.mipmap.icon_bank_logo_psbc;
            case "民生银行":
                return R.mipmap.icon_bank_logo_cmbc;
            case "兴业银行":
                return R.mipmap.icon_bank_logo_ibc;
            case "中信银行":
                return R.mipmap.icon_bank_logo_cib;
            case "渤海银行":
                return R.mipmap.icon_bank_logo_cbhb;
            case "光大银行":
                return R.mipmap.icon_bank_logo_ceb;
            case "广发银行":
                return R.mipmap.icon_bank_logo_gdb;
            case "华夏银行":
                return R.mipmap.icon_bank_logo_hxb;
            case "平安银行":
                return R.mipmap.icon_bank_logo_pab;
            case "浦发银行":
                return R.mipmap.icon_bank_logo_spdb;
            case "北京农商银行":
                return R.mipmap.icon_bank_logo_brcb;
            case "上海银行":
                return R.mipmap.icon_bank_logo_bs;
            case "交通银行":
                return R.mipmap.icon_bank_logo_bcomm;
        }
        return R.mipmap.bank_car_icon;
    }
}
