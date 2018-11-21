package com.international.wtw.lottery.activity.lottery.happytime;

import com.international.wtw.lottery.base.LotteryId;

public class GD11X5Activity extends Guangdonghappy10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.GD11X5;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
