package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

/**
 * Created by XIAOYAN on 2018/2/19.
 */

public class Horse88Activity extends PK10Activity {

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.horse_88);
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.HORSE_88;
    }

}
