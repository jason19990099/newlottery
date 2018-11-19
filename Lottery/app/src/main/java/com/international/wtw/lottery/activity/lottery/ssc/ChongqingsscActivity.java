package com.international.wtw.lottery.activity.lottery.ssc;

import com.international.wtw.lottery.activity.lottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;


/**
 * 重庆时时彩
 */
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
