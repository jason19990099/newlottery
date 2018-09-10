package com.international.wtw.lottery.listener;


import com.international.wtw.lottery.json.BetResultBean;
import com.international.wtw.lottery.json.BetResultBeanSucess;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by wuya on 2017/5/15.
 */

public interface BetService {

    @FormUrlEncoded
    @Headers({"Content-Type:application/x-www-form-urlencoded"})
    @POST("InOrder")
    Call<BetResultBean>   performBetting(@Field("game_code") String gameCode, @Field("type_code") String typeCode, @Field("round") String round,

                                         @Field("uid") String uid, @Field("ip_3001-3011") int ip_3001_3011, @Field("ip_3001-3012") int ip_3001_3012);

     @POST("inup")
     Call<BetResultBeanSucess> performBetting(@Body RequestBody betParamInfo);


}
