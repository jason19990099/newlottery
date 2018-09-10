package com.international.wtw.lottery.listener;

import android.content.Context;
import android.support.annotation.NonNull;


import okhttp3.RequestBody;

/**
 * Created by wuya on 2017/5/2
 * 网络请求实现类，没有回调接口，使用 EventBus 来接收数据
 */
public class RequestClient implements BetApi {
    private static BetImpl sBetImpl;
    private volatile static RequestClient mRequestClient;
    private RequestClient() {
    }
    public static RequestClient getSingleInstance() {
        if (null == mRequestClient) {
            synchronized (RequestClient.class) {
                if (null == mRequestClient) {
                    mRequestClient = new RequestClient();
                }
            }
        }
        return mRequestClient;
    }


    //--- 初始化 ---------------------------------------------------------------------------------

    public static RequestClient init(@NonNull Context context) {
        initImplement(context);
        return getSingleInstance();
    }


    private static void initImplement(Context context) {
    //    Logger.i("初始化 implement");
        try {
            sBetImpl = new BetImpl(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
      //  Logger.i("初始化 implement 结束");
    }

    @Override
    public String performBet(String gameCode, String typeCode, String round, String uid, int ip_3001_3011, int ip_3001_3012) {
        return sBetImpl.performBet(gameCode,typeCode,round,uid,ip_3001_3011,ip_3001_3012);
    }

    @Override
    public String performBet(RequestBody betParamInfo) {
        return sBetImpl.performBet(betParamInfo);
    }


}