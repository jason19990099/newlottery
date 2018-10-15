package com.international.wtw.lottery.activity.lottery.Newlottery;

import com.international.wtw.lottery.base.LotteryId;

public class MiaosusaicheActivity  extends  Bjscpk10Activity{

    @Override
    public String getLotteryType() {
        return LotteryId.MiaosuSaiche;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
