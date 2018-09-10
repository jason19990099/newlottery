package com.international.wtw.lottery.listener;

import android.content.Context;
import android.support.annotation.NonNull;

import com.international.wtw.lottery.json.BaseImpl;
import com.international.wtw.lottery.json.BetResultBean;
import com.international.wtw.lottery.json.BetResultBeanSucess;
import com.international.wtw.lottery.utils.UUIDGenerator;

import okhttp3.RequestBody;

/**
 * Created by wuya on 2017/5/15.
 */

public class BetImpl extends BaseImpl<BetService> implements BetApi {

    public BetImpl(@NonNull Context context) {
        super(context);
    }

    @Override
    public String performBet(String gameCode, String typeCode, String round, String uid, int ip_3001_3011, int ip_3001_3012) {
        final String uuid = UUIDGenerator.getUUID();
        mService.performBetting(gameCode,typeCode,round,uid,ip_3001_3011,ip_3001_3012).enqueue(new BaseCallback<BetResultBean>(new BaseEvent<BetResultBean>(uuid)));
        return uuid;
    }

    @Override
    public String performBet(RequestBody betParamInfos) {
        final String uuid = UUIDGenerator.getUUID();
        mService.performBetting(betParamInfos).enqueue(new BaseCallback<BetResultBeanSucess>(new BaseEvent<BetResultBeanSucess>(uuid)));
        return uuid;
    }
}
