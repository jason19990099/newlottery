package com.international.wtw.lottery.utils;

/**
 * Created by A Bin  on 2017/8/1.
 * 判断重复点击事件
 */

public class ClickUtill {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    public static final long INTERVAL = 1000L; //防止连续点击的时间间隔
    private static long lastClickTime = 0L; //上一次点击的时间

    public static boolean filter() {
        long time = System.currentTimeMillis();
        lastClickTime = time;
        if ((time - lastClickTime) > INTERVAL) {
            return false;
        }
        return true;
    }
}
