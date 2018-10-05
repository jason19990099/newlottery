package com.international.wtw.lottery.activity.lottery.Newlottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.fragment.LotteryInfoFragment;



/**
 *  新的彩票的父类
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //初始化开盘封盘
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LotteryInfoFragment fragment = LotteryInfoFragment.newInstance(getLotteryType());
        ft.replace(R.id.fl_lottery_info_container, fragment).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }


    /**
     * 获取彩票彩种
     */
    public abstract String getLotteryType();

}
