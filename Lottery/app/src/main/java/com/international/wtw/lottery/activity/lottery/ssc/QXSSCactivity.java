package com.international.wtw.lottery.activity.lottery.ssc;

import com.international.wtw.lottery.activity.lottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;

/**
 *  7星时时彩
 */
public class QXSSCactivity extends Bjscpk10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.XJSSC;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
