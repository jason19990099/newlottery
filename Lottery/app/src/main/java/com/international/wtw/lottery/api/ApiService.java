package com.international.wtw.lottery.api;

import com.google.gson.JsonElement;
import com.international.wtw.lottery.json.AGChangeBean;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.BetRecord;
import com.international.wtw.lottery.json.HistoryResult;
import com.international.wtw.lottery.json.HomeResultBean;
import com.international.wtw.lottery.json.LotterySortingModel;
import com.international.wtw.lottery.json.LotteryVersion;
import com.international.wtw.lottery.json.LotteryWebsite;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.json.MessageBean;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.json.Notice;
import com.international.wtw.lottery.json.OnlinePay;
import com.international.wtw.lottery.json.PayIn;
import com.international.wtw.lottery.json.PayInModel;
import com.international.wtw.lottery.json.PersonalBean;
import com.international.wtw.lottery.json.PreferentialBean;
import com.international.wtw.lottery.json.QRModel;
import com.international.wtw.lottery.json.Quick3Odds;
import com.international.wtw.lottery.json.SummaryBean;
import com.international.wtw.lottery.json.SummaryDetailsBean;
import com.international.wtw.lottery.json.TimeInfoBean;
import com.international.wtw.lottery.json.Token;
import com.international.wtw.lottery.json.TransactionRecord;
import com.international.wtw.lottery.json.UserModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {

    /**
     * 用户注册
     */
    @POST("user/signup")
    Call<BaseModel> register(@Body RequestBody body);

    /**
     * 用户登录
     */
    @POST("user/signin")
    Call<UserModel> login(@Body RequestBody body);

    /**
     * 试玩登录
     */
    @GET("user/signdemo")
    Call<UserModel> loginDemo();

    /**
     * 获取版本服务器apk信息
     */
    @POST("systems/getVersionAndroid")
    Call<LotteryVersion> getVersion();

    /**
     * 轮播图和公告二
     */
    @POST("user/getIndexContent")
    Call<LunbotuBean> getBanner(@Body RequestBody body);

    @POST("movable/promotions")
    Call<PreferentialBean> getPromotions(@Body RequestBody body);

    /**
     * 获取余额及银行卡信息
     */
    @POST("getinfo/money")
    Call<MoneyInfo> requestAmountInfo(@Body RequestBody body);

    /**
     * 获取充值支付渠道信息
     */
    @POST("user/payin")
    Call<PayIn> requestDeposits(@Body RequestBody body);

    /**
     * 获取充值支付渠道信息
     */
    @POST("user/payin")
    Call<PayInModel> requestPayInWays(@Body RequestBody body);

    /**
     * 获取充值/提现记录
     */
    @POST("getinfo/record")
    Call<TransactionRecord> requestRecords(@Body RequestBody body);

    /**
     * 提现
     */
    @POST("user/online_get")
    Call<BaseModel> requestWithdraw(@Body RequestBody body);

    /**
     * 线上支付
     */
    @POST("user/online_pay")
    Call<OnlinePay> requestOnlinePayment(@Body RequestBody body);

    /**
     * 提交线下支付
     */
    @POST("user/offline_pay")
    Call<BaseModel> requestOfflinePay(@Body RequestBody body);

    /**
     * 添加或修改银行卡
     */
    @POST("user/info")
    Call<BaseModel> addOrModifyBankcard(@Body RequestBody body);

    @POST("getinfo/getFirstToken")
    Call<Token> getToken(@Body RequestBody body);

    /**
     * 获取开奖信息
     */
    @POST("getinfo/game")
    Call<TimeInfoBean> requestLotteryInfo(@Body RequestBody body);

    /**
     * 获取赔率信息
     */
    @POST("getinfo/odds")
    Call<JsonElement> requestOdds(@Body RequestBody body);


    /**
     * 获取某彩种所有玩法的赔率
     */
    @POST("getinfo/getAllOdds")
    Call<Quick3Odds> requestAllOdds(@Body RequestBody body);


    /**
     * 下注接口
     */
    @POST("inup")
    Call<JsonElement> requestBet(@Body RequestBody body);

    /**
     * 获取下注记录
     */
    @POST("getinfo/getSummaryBet")
    Call<BetRecord> requestBetRecord(@Body RequestBody body);


    /**
     * 获取账户和AG余额
     */
    @POST("aginfo/getAgInfo")
    Call<AgAccountBalance> requestAccountAndAGBalance(@Body RequestBody body);


    /**
     * 获取各彩种开奖结果及开奖倒计时
     */
    @GET("systems/getgameclosetime")
    Call<HomeResultBean> getGameCloseTime();

    /**
     * 获取某彩种开奖历史
     */
    @POST("user/getResult")
    Call<HistoryResult> getLotteryHistory(@Body RequestBody body);


    /**
     * 账户和AG余额互相转换
     */
    @POST("aginfo/agQuotaConversion")
    Call<AGChangeBean> agQuotaConversion(@Body RequestBody body);


    /**
     * 账户和AG余额互相转换
     */
    @POST("aginfo/getAgGameLink/{type}")
    Call<AGChangeBean> getAgGameLink(@Path("type") int type, @Body RequestBody body);

    @POST("user/getLoginNotice")
    Call<Notice> getLoginNotice(@Body RequestBody body);

    @POST
    Call<QRModel> onlinePay(@Url String url, @QueryMap Map<String, String> map);

    /**
     * 新闻中心
     *
     * @param body
     * @return
     */
    @POST("user/getIndexContent")
    Call<LunbotuBean> getNewsCenter(@Body RequestBody body);

    /**
     * 个人资料
     *
     * @param body
     * @return
     */
    @POST("user/getPersonalCenter")
    Call<PersonalBean> getPersonalData(@Body RequestBody body);

    /**
     * 修改登录密码
     *
     * @param body
     * @return
     */
    @POST("user/info")
    Call<BaseModel> getLoginPassword(@Body RequestBody body);

    /**
     * 修改支付密码
     *
     * @param body
     * @return
     */
    @POST("user/info")
    Call<BaseModel> getPayPassword(@Body RequestBody body);

    /**
     * 我的消息
     *
     * @param body
     * @return
     */
    @POST("user/getMsgInbox")
    Call<MessageBean> getMyMessage(@Body RequestBody body);

    /**
     * 下注记录-未结数
     *
     * @param body
     * @return
     */
    @POST("getinfo/getMissedOrHasClosedBet")
    Call<SummaryDetailsBean> getSummaryDetails(@Body RequestBody body);

    /**
     * 下注记录-汇总
     *
     * @param body
     * @return
     */
    @POST("getinfo/getSummaryBet")
    Call<SummaryBean> getSummary(@Body RequestBody body);

    /**
     * 下注记录-未结已结
     *
     * @param body
     * @return
     */
    @POST("getinfo/getMissedOrHasClosedBet")
    Call<SummaryDetailsBean> getOutStandHave(@Body RequestBody body);

    /**
     * 首页彩种排序
     */
    @GET("systems/game_sort")
    Call<LotterySortingModel> getLotterySorting();

    /**
     * 获取开奖网链接
     */
    @GET("systems/openWebsiteUrl")
    Call<LotteryWebsite> getLotteryWebsite();
}
