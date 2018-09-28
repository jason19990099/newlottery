package com.international.wtw.lottery.activity.lottery;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;

/**
 * Created by XiaoXin on 2018/1/15.
 * 描述：
 */

public class VeniceActivity extends PK10Activity  {


    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.venice_speedboat);
    }

    @Override
    public String getLotteryType() {
        return Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT+"";
    }
}
