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
import com.international.wtw.lottery.json.TransactionRecord;
import com.international.wtw.lottery.json.UserModel;
import com.international.wtw.lottery.newJason.Allgame;
import com.international.wtw.lottery.newJason.GetUserinfo;
import com.international.wtw.lottery.newJason.Login;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.newJason.getGameOpentime;

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
     * 获取token
     */
    @POST("index/GetToken")
    Call<Login> getToken(@Body RequestBody body);

    /**
     * 获取验证码
     */
    @POST("index/CheckCode")
    Call<Login> getCheckCode(@Body RequestBody body);

    /**
     * 用户注册
     */
    @POST("index/Register")
    Call<BaseModel> register(@Body RequestBody body);

    /**
     * 用户登录
     */
    @POST("index/Login")
    Call<Login> login(@Body RequestBody body);

    /**
     * 用户登出
     */
    @POST("index/Logout")
    Call<Login> loginout(@Body RequestBody body);

    /**
     * 试玩登录
     */
    @POST("index/LoginTry")
    Call<UserModel> loginDemo();


    /**
     * 获取登录信息
     */
    @POST("User/GetLoginInfo")
    Call<GetUserinfo> getLoginfo(@Body RequestBody body);

    /**
     * 消息列表
     */
    @POST("Message/List")
    Call<Login> getMessageList(@Body RequestBody body);

    /**
     * 获取余额
     */
    @POST("User/GetBalance")
    Call<Login> getBanlance(@Body RequestBody body);

    /**
     * 修改登录密码
     */
    @POST("User/UpdatePassword")
    Call<BaseModel> getLoginPassword(@Body RequestBody body);

    /**
     * 修改资金密码
     */
    @POST("User/UpdateWithdrawPassword")
    Call<BaseModel> changeMoneyPass(@Body RequestBody body);

    /**
     *  查询用户银行卡
     */
    @POST("User/GetUserBank")
    Call<Login> getUserbank(@Body RequestBody body);


    /**
     * 查询所有游戏
     */
    @POST("Game/GetGame")
    Call<Allgame> getAllgames(@Body RequestBody body);


    /**
     * 获取当前期进行投注的期号和时间
     */
    @POST("Game/GetGameOpenTime")
    Call<getGameOpentime> getGameOpenTime(@Body RequestBody body);

    /**
     * 获取玩法赔率
     */
    @POST("Game/GetGamePlayRate")
    Call<PK10Rate> getPlayRate(@Body RequestBody body);

    /**
     * 投注
     */
    @POST("Game/SaveOrders")
    Call<PK10Rate> saveOrders(@Body RequestBody body);






























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
