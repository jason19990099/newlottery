package com.international.wtw.lottery.fragment.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.BankcardActivity;
import com.international.wtw.lottery.activity.mine.BankcardContralActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.fragment.addbankcard.CardInfoFragment;
import com.international.wtw.lottery.fragment.addbankcard.EmptyFragment;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.newJason.BankcardsModel;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MD5util;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import org.greenrobot.eventbus.Subscribe;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 出款的面页
 */
public class WithdrawFragment extends NewBaseFragment {

    RelativeLayout mRlBankInfo;
    @BindView(R.id.rl_add_bankcard)
    LinearLayout mRlAddBank;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.et_embodied_amount)
    EditText mEtAmount;
    @BindView(R.id.edt_tx_psd)
    EditText mEtPayPassword;
    @BindView(R.id.tv_embodied_amount)
    TextView tvEmbodiedAmount;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.ll_havabankcard)
    LinearLayout llHavabankcard;
    @BindView(R.id.btn_addbankcard)
    Button btnAddbankcard;
    private boolean isDemo;//是否是试玩
    private MoneyInfo moneyInfo;
    private String mBankName;
    private String mBankcardId;
    private String mAmount;
    private String mPayPassword;

    public static WithdrawFragment newInstance(String gameName) {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_drawing;
    }

    @Override
    protected void initData() {
        isDemo = SharePreferencesUtil.getBoolean(mActivity, LotteryId.IS_SHI_WAN, false);

        /**
         * 初始化view
         */
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        /**
         *  获取银行卡信息
         */

//        getBankCardInfo();
    }

    private void initViews() {
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

    private void getBankCardInfo() {
        String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, null);
        HttpRequest.getInstance().getUserBank(getActivity(), token, new HttpCallback<BankcardsModel>() {
            @Override
            public void onSuccess(BankcardsModel data) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                if (data.getData()==null){
                    ft.replace(R.id.fl_container, new EmptyFragment()).commit();
                }else{
                    ft.replace(R.id.fl_container, new CardInfoFragment()).commit();
                }
            }
            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(getActivity(),"请求失败，请重试",Toast.LENGTH_SHORT).show();
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
        }
        String name = "账户";
        mTvAccountBalance.setText(String.format(Locale.US, "(%s余额%.2f)", name, moneyInfo.getMoney()));

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


    @OnClick({R.id.rl_add_bankcard, R.id.btn_confirm,R.id.btn_addbankcard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_add_bankcard:
            case R.id.btn_addbankcard:
//                toBankcardActivity(getString(R.string.add_bank_info), false);
                Intent intent = new Intent(getActivity(), BankcardContralActivity.class);
                intent.putExtra("is_shi_wan", false);
                startActivity(intent);
                break;
            case R.id.btn_confirm:
                withdraw();
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
        mPayPassword= MD5util.MD5Encode(MD5util.MD5Encode(mPayPassword,"utf-8"),"utf-8");

        if (isDemo) {
            ToastDialog.error(getString(R.string.no_permission)).show(getFragmentManager());
        }
        if (TextUtils.isEmpty(mAmount)) {
            Toast.makeText(getActivity(),getString(R.string.input_tx_amount),Toast.LENGTH_SHORT).show();
        }
        if (Double.valueOf(mAmount.replace(",", "")) < 100.00) {
            Toast.makeText(getActivity(),getString(R.string.money_beyond1),Toast.LENGTH_SHORT).show();
        } else {
            requestWithdraw();
        }
    }

    /**
     * 重置数据
     */
    private void resetInput() {
        mEtAmount.setText("");
        mEtPayPassword.setText("");
    }

    /**
     * 请求提款接口进行提款
     */
    private void requestWithdraw() {
        showLoadingDialog();
        String token = SharePreferencesUtil.getString(getActivity(), LotteryId.TOKEN, null);
        HttpRequest.getInstance().withdraw(this, token, mAmount, mPayPassword, new HttpCallback<BaseModel>() {
            @Override
            public void onSuccess(BaseModel data) {
                dismissLoadingDialog();
                resetInput();
                Toast.makeText(getActivity(),"提现成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                dismissLoadingDialog();
                Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }




}
