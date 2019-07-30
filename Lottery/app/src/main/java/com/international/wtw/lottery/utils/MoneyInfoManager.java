package com.international.wtw.lottery.utils;

import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.json.MoneyInfo;
import org.greenrobot.eventbus.EventBus;

/**
 * 描述：用户信息(用户名/余额/银行卡/等管理)
 */

public class MoneyInfoManager {

    private static MoneyInfo sMoneyInfo;


    private MoneyInfoManager() {
    }

    private static class SingletonHolder {

        static MoneyInfoManager INSTANCE = new MoneyInfoManager();
    }

    public static MoneyInfoManager get() {
        return SingletonHolder.INSTANCE;
    }



    public void setMoneyInfo(MoneyInfo moneyInfo) {
        sMoneyInfo = moneyInfo;
        EventBus.getDefault().post(new MoneyInfoRefreshEvent(moneyInfo));
    }

    public MoneyInfo getMoneyInfo() {
        if (sMoneyInfo == null) {
//            requestMoneyInfo();
        }
        return sMoneyInfo;
    }

    public void requestMoneyInfo() {
        HttpRequest.getInstance().requestAmountInfo(this, new HttpCallback<MoneyInfo>() {
            @Override
            public void onSuccess(MoneyInfo data) {
                setMoneyInfo(data);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                //empty
            }
        });
    }
}
