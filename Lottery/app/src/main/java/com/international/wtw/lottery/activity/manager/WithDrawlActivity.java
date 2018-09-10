package com.international.wtw.lottery.activity.manager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.fragment.money.WithdrawFragment;

import butterknife.OnClick;

/**
 * 提现（取款）
 * Created by A Bin on 2017/8/15.
 */

public class WithDrawlActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_container, new WithdrawFragment()).commit();
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
