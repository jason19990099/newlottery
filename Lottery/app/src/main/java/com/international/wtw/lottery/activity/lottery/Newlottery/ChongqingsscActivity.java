package com.international.wtw.lottery.activity.lottery.Newlottery;

import com.international.wtw.lottery.base.LotteryId;

public class ChongqingsscActivity extends Bjscpk10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.CQSSC;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
