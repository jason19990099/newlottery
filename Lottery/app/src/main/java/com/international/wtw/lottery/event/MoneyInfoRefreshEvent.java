package com.international.wtw.lottery.event;

import com.international.wtw.lottery.json.MoneyInfo;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class MoneyInfoRefreshEvent {

    public MoneyInfo moneyInfo;

    public MoneyInfoRefreshEvent(MoneyInfo moneyInfo) {
        this.moneyInfo = moneyInfo;
    }
}
