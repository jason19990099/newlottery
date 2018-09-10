package com.international.wtw.lottery.listener;

import com.international.wtw.lottery.json.NewOddsBean;

import java.util.List;

/**
 * Created by 18Steven on 2017/9/4.
 * 点击选中下注项然后通知到主activity
 */

public interface ShowSelectNumbersInterface {
    void showSelextNum(int sum);
//    void getBettingData(List<NewOddsBean.ListBean> selectedBeans );//暂时没用到
}
