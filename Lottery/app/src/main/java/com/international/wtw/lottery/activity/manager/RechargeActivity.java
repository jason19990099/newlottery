package com.international.wtw.lottery.activity.manager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.fragment.money.RechargeFragment;

import butterknife.OnClick;

/**
 * 充值
 * Created by A Bin on 2017/8/15.
 * 2017/9/14修改: 直接复用DepositsFragment里的布局和逻辑
 */

public class RechargeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_container, new RechargeFragment()).commit();
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
