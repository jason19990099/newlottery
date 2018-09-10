package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

public class RomeSSCaiActivity extends SSCaiActivity {

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.roma_ssc);
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.ROME_LOTTERY;
    }
}
