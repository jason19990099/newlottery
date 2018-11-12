package com.international.wtw.lottery.activity.lottery.Newlottery;

import com.international.wtw.lottery.base.LotteryId;

public class XJSSCActivity extends Bjscpk10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.XJSSC;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
