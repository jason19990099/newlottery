package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;


/**
 * Created by 18Steven on 2017/7/11. 新的PK10
 */

public class LuckyFlyActivity extends PK10Activity {

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.lucy_fly);
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY;
    }
}
