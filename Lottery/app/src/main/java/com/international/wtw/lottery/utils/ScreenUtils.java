package com.international.wtw.lottery.utils;

import android.content.Context;


public class ScreenUtils {
    /**
     *  获取屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (null ==context) {
            return 1080;
        }
        return context.getApplicationContext().getResources().getDisplayMetrics().widthPixels;

    }
}
