package com.international.wtw.lottery.utils;

import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.json.MoneyInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by XiaoXin on 2017/10/6.
 * 描述：用户信息(用户名/余额/银行卡/等管理)
 */

public class MoneyInfoManager {

    private static MoneyInfo sMoneyInfo;
    private static AgAccountBalance.BalanceBean sBalanceBean;

    private MoneyInfoManager() {
    }

    private static class SingletonHolder {

        static MoneyInfoManager INSTANCE = new MoneyInfoManager();
    }

    public static MoneyInfoManager get() {
        return SingletonHolder.INSTANCE;
    }

    public void requestAgInfo() {
        HttpRequest.getInstance().requestAccountAndAGBalance(this, new HttpCallback<AgAccountBalance>() {
            @Override
            public void onSuccess(AgAccountBalance data) {
                AgAccountBalance.BalanceBean balanceBean = data.getBalance();
                if (balanceBean != null) {
                    setBalanceBean(balanceBean);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });
    }

    public static void setBalanceBean(AgAccountBalance.BalanceBean balanceBean) {
        sBalanceBean = balanceBean;
        EventBus.getDefault().post(balanceBean);
    }

    public AgAccountBalance.BalanceBean getAgInfo() {
        if (sBalanceBean == null) {
            requestAgInfo();
        }
        return sBalanceBean;
    }

    public void setMoneyInfo(MoneyInfo moneyInfo) {
        sMoneyInfo = moneyInfo;
        EventBus.getDefault().post(new MoneyInfoRefreshEvent(moneyInfo));
    }

    public MoneyInfo getMoneyInfo() {
        if (sMoneyInfo == null) {
            requestMoneyInfo();
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
