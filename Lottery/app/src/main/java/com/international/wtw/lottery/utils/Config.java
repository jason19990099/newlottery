package com.international.wtw.lottery.utils;

import android.content.Context;
import android.util.LruCache;

/**
 * Created by wuya on 2017/5/2.
 */


/**
 * 用户设置
 */
public class Config {
    private static int M = 1024 * 1024;
    private volatile static Config mConfig;
    private static LruCache<String, Object> mLruCache = new LruCache<>(1 * M);


    private Config(Context context) {

    }

    public static Config init(Context context) {
        if (null == mConfig) {
            synchronized (Config.class) {
                if (null == mConfig) {
                    mConfig = new Config(context);
                }
            }
        }
        return mConfig;
    }

    public static Config getSingleInstance() {
        return mConfig;
    }
}