package com.international.wtw.lottery.utils;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.international.wtw.lottery.base.app.BaseApplication;

/**
 * 在没传入上下文的地方获取Resources
 */
public class ResourcesUtil {

    public static Resources getResources() {
        return BaseApplication.getAppContext().getResources();
    }

    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    public static String getString(@StringRes int resId, Object... formatArgs) {
        return getResources().getString(resId, formatArgs);
    }

    public static int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(BaseApplication.getAppContext(), resId);
    }
}
