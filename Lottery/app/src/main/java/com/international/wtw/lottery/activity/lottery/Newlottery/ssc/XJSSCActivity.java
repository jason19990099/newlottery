package com.international.wtw.lottery.activity.lottery.Newlottery.ssc;

import com.international.wtw.lottery.activity.lottery.Newlottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;

/**
 *  新疆时时彩
 */
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
