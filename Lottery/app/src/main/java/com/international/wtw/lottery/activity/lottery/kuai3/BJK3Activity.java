package com.international.wtw.lottery.activity.lottery.kuai3;


import com.international.wtw.lottery.base.LotteryId;

public class BJK3Activity  extends JSK3Activity {

    @Override
    public String getLotteryType() {
        return LotteryId.BJKS;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
