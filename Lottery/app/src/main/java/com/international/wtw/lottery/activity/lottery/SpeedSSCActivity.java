package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

/**
 * Created by XIAOYAN on 2018/2/12.
 */

public class SpeedSSCActivity extends SSCaiActivity {

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.speed_ssc);
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.SPEED_SSC;
    }

}
