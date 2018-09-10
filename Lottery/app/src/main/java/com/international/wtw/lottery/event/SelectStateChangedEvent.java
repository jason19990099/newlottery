package com.international.wtw.lottery.event;

import com.international.wtw.lottery.json.MultiBetItem;

/**
 * Created by XiaoXin on 2017/9/21.
 * 描述：下注选项选中状态
 */

public class SelectStateChangedEvent extends BaseEvent {

    public MultiBetItem mBetItem;

    public SelectStateChangedEvent(MultiBetItem mBetItem) {
        this.mBetItem = mBetItem;
    }
}
