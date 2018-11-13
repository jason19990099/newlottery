package com.international.wtw.lottery.activity.lottery.Newlottery.dandan;

import com.international.wtw.lottery.base.LotteryId;

/**
 * PC蛋蛋
 */
public class XYDDActivity extends PCDDActivity {
    @Override
    public String getLotteryType() {
        return LotteryId.XYDD;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
