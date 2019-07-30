package com.international.wtw.lottery.activity.lottery.saiche;


import com.international.wtw.lottery.base.LotteryId;

/**
 * 秒速飞艇
 */
public class QixingsaicheActivity extends Bjscpk10Activity {

    @Override
    public String getLotteryType() {
        return LotteryId.QXSC;
    }



    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
