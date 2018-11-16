package com.international.wtw.lottery.activity.manager;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.NewBaseActivity;

public class PaymentActivity extends NewBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected boolean useEventBus() {
        return false;
    }
}
