package com.international.wtw.lottery.json;

import com.international.wtw.lottery.adapter.PayWaysAdapter;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class PayInTitle extends MultiPayInItem {
    public String title;
    public String subTitle;

    public PayInTitle(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public int getItemType() {
        return PayWaysAdapter.TYPE_TITLE;
    }
}
