package com.international.wtw.lottery.listener;

import okhttp3.RequestBody;

/**
 * Created by wuya on 2017/5/15.
 */

public interface BetApi {


    String performBet(String gameCode, String typeCode, String round, String uid, int ip_3001_3011, int ip_3001_3012);

    String performBet(RequestBody gameCodeInfo);

}
