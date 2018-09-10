package com.international.wtw.lottery.listener;

import android.view.View;

/**
 * Created by XiaoXin on 2018/1/22.
 * 描述：防止重复提交(1秒内最多只能点击一次)
 */

public abstract class OnSingleClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastClickTime > MIN_CLICK_DELAY_TIME) {
            //超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = currentTimeMillis;
            onSingleClick(v);
        }
    }

    protected abstract void onSingleClick(View v);
}
