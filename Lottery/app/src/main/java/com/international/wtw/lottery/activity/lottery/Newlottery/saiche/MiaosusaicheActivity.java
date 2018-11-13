package com.international.wtw.lottery.activity.lottery.Newlottery.saiche;

import com.international.wtw.lottery.activity.lottery.Newlottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;

/**
 * 秒速赛车
 */
public class MiaosusaicheActivity  extends Bjscpk10Activity {

    @Override
    public String getLotteryType() {
        return LotteryId.MiaosuSaiche;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
