package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

/**
 * Created by XIAOYAN on 2018/2/19.
 */

public class SpeedMarkSixActivity extends MarkSixActivity{

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.speed_mark_six);
    }

    @Override
    public String getLotteryType() {
        return Constants.LOTTERY_TYPE.SPEED_MARK_SIX+"";
    }

}
