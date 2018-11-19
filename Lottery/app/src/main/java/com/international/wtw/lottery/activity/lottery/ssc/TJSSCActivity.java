package com.international.wtw.lottery.activity.lottery.ssc;

import com.international.wtw.lottery.activity.lottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;


/**
 *  天津时时彩
 */
public class TJSSCActivity extends Bjscpk10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.TJSSC;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
