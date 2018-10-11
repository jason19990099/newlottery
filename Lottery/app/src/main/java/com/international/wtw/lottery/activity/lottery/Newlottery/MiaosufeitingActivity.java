package com.international.wtw.lottery.activity.lottery.Newlottery;


import com.international.wtw.lottery.base.LotteryId;

/**
 * 秒速飞艇
 */
public class MiaosufeitingActivity extends  Bjscpk10Activity{

    @Override
    public String getLotteryType() {
        return LotteryId.Miaosufeiting;
    }



    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
