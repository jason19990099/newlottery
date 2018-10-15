package com.international.wtw.lottery.activity.lottery.Newlottery;

import com.international.wtw.lottery.base.LotteryId;

public class MiaosusscActivity extends Bjscpk10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.Miaosusscai;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
