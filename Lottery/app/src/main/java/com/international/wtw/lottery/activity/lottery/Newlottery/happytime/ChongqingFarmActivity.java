package com.international.wtw.lottery.activity.lottery.Newlottery.happytime;
import com.international.wtw.lottery.base.LotteryId;

/**
 * 重庆幸运农场
 */
public class ChongqingFarmActivity  extends Guangdonghappy10Activity {
    @Override
    public String getLotteryType() {
        return LotteryId.CQXYNC;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }
}
