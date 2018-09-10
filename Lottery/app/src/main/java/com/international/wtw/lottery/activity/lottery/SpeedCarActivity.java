package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

/**
 * Created by XIAOYAN on 2018/2/12.
 */

public class SpeedCarActivity extends PK10Activity {

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.speed_car);
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.SPEED_CAR;
    }

}
