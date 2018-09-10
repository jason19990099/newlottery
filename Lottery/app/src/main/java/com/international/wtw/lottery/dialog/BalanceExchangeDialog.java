package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;
import com.international.wtw.lottery.json.AGChangeBean;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.utils.KeyBoardUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BalanceExchangeDialog extends BaseNiceDialog implements View.OnClickListener {

    public static final String LOTTERY_BALANCE = "lottery_balance";
    public static final String AG_BALANCE = "ag_balance";

    private RadioButton mRadioBrn1;
    private RadioButton mRadioBrn2;
    private EditText mEtExchangeAmount;
    private double lotteryBalance;
    private double agBalance;
    private static ExchangeListener sExchangeListener;

    public static BalanceExchangeDialog init(double lotteryBalance, double agBalance, ExchangeListener exchangeListener) {
        BalanceExchangeDialog dialog = new BalanceExchangeDialog();
        Bundle bundle = new Bundle();
        bundle.putDouble(LOTTERY_BALANCE, lotteryBalance);
        bundle.putDouble(AG_BALANCE, agBalance);
        dialog.setArguments(bundle);
        sExchangeListener = exchangeListener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lotteryBalance = getArguments().getDouble(LOTTERY_BALANCE);
        agBalance = getArguments().getDouble(AG_BALANCE);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(AgAccountBalance.BalanceBean event) {
        lotteryBalance = event.getUserBalance();
        agBalance = event.getAgBalance();
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_balance_exchange;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.ll_lottery_2_ag, this);
        holder.setOnClickListener(R.id.ll_ag_2_lottery, this);
        holder.setOnClickListener(R.id.tv_confirm, this);
        holder.setOnClickListener(R.id.tv_reset, this);
        holder.setOnClickListener(R.id.iv_close, this);
        mRadioBrn1 = holder.getView(R.id.radio_btn_1);
        mRadioBrn2 = holder.getView(R.id.radio_btn_2);
        mEtExchangeAmount = holder.getView(R.id.et_exchange_amount);
        mEtExchangeAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //首位不能输入0
                if (s.length() == 1 && "0".equals(s.toString())) {
                    s.clear();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_lottery_2_ag:
                mRadioBrn1.setChecked(true);
                mRadioBrn2.setChecked(false);
                break;
            case R.id.ll_ag_2_lottery:
                mRadioBrn2.setChecked(true);
                mRadioBrn1.setChecked(false);
                break;
            case R.id.tv_confirm:
                commitExchange();
                break;
            case R.id.tv_reset:
                mEtExchangeAmount.setText("");
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    private void commitExchange() {
        String amount = mEtExchangeAmount.getText().toString().trim();
        //type=1 彩票转Ag type=0 Ag转彩票
        String type = mRadioBrn1.isChecked() ? "1" : "0";
        if (TextUtils.isEmpty(amount)) {
            ToastDialog.error(getString(R.string.input_exchange_amount)).show(getChildFragmentManager());
        } else if ("1".equals(type) && Double.parseDouble(amount) > lotteryBalance) {
            ToastDialog.error(getString(R.string.lottery_balance_not_enough)).show(getChildFragmentManager());
        } else if ("0".equals(type) && Double.parseDouble(amount) > agBalance) {
            ToastDialog.error(getString(R.string.ag_balance_not_enough)).show(getChildFragmentManager());
        } else {
            requestExchange(amount, type);
            dismiss();
            KeyBoardUtils.closeKeyboard(getContext(), mEtExchangeAmount);
        }
    }

    private void requestExchange(String amount, String type) {
        if (sExchangeListener != null) {
            sExchangeListener.onCommit();
        }
        HttpRequest.getInstance().agQuotaConversion(this, amount, type, new HttpCallback<AGChangeBean>() {
            @Override
            public void onSuccess(AGChangeBean data) {
                if (sExchangeListener != null)
                    sExchangeListener.onSuccess(data);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                if (sExchangeListener != null)
                    sExchangeListener.onFailure(errorMsg);
            }
        });
    }

    public interface ExchangeListener {

        void onCommit();

        void onSuccess(AGChangeBean data);

        void onFailure(String errorMsg);
    }
}
