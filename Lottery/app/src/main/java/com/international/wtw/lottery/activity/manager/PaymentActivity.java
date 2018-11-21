package com.international.wtw.lottery.activity.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.PaymentMethodModel;
import com.international.wtw.lottery.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends NewBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.textView_lotteryTypeName)
    TextView textViewLotteryTypeName;
    @BindView(R.id.downArrow)
    LinearLayout downArrow;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.et_recharge_amount)
    EditText etRechargeAmount;
    @BindView(R.id.tv_selectpaystyle)
    TextView tvSelectpaystyle;
    private PaymentMethodModel.DataBean dataBean;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        dataBean = (PaymentMethodModel.DataBean) getIntent().getSerializableExtra("paydata");
        LogUtil.e("===============" + new Gson().toJson(dataBean));

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }


    @OnClick({R.id.iv_back, R.id.tv_selectpaystyle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_selectpaystyle:
                break;
        }
    }
}
