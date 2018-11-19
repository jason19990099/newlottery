package com.international.wtw.lottery.activity.lottery.ssc;

import com.international.wtw.lottery.activity.lottery.saiche.Bjscpk10Activity;
import com.international.wtw.lottery.base.LotteryId;


/**
 * 秒速时时彩
 */
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
