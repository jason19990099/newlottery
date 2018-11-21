package com.international.wtw.lottery.api;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.json.BaseModel;
import com.international.wtw.lottery.json.BetResultBean;
import com.international.wtw.lottery.json.HomeResultBean;
import com.international.wtw.lottery.json.LotteryVersion;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.json.Notice;
import com.international.wtw.lottery.json.OddsModel;
import com.international.wtw.lottery.json.OnlinePay;
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
import com.international.wtw.lottery.newJason.AllgameModel;
import com.international.wtw.lottery.newJason.BankcardModel;
import com.international.wtw.lottery.newJason.BankcardsModel;
import com.international.wtw.lottery.newJason.BetDetailModel;
import com.international.wtw.lottery.newJason.BetrecordBydateModel;
import com.international.wtw.lottery.newJason.GetUserinfoModel;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.newJason.LotteryHistoryModel;
import com.international.wtw.lottery.newJason.MessageDetailModel;
import com.international.wtw.lottery.newJason.MessageModel;
import com.international.wtw.lottery.newJason.NoticeListModel;
import com.international.wtw.lottery.newJason.PK10RateModel;
import com.international.wtw.lottery.newJason.PayinRecordMoudel;
import com.international.wtw.lottery.newJason.PaymentMethodModel;
import com.international.wtw.lottery.newJason.PersonalModel;
import com.international.wtw.lottery.newJason.SettledOrdersModel;
import com.international.wtw.lottery.newJason.WeekdateModel;
import com.international.wtw.lottery.newJason.WithdrawRecordModel;
import com.international.wtw.lottery.newJason.getGameOpentimeModel;
import com.international.wtw.lottery.newJason.GameOpentimeModel2;
import com.international.wtw.lottery.utils.JsonUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.international.wtw.lottery.base.LotteryId.GAME_CODE;
import static com.international.wtw.lottery.base.LotteryId.OID;

/**
 * 描述：网络请求类，将网络请求的方法放到此处统一管理  单例
 * 每个请求方法都传了tag标签,Activity和Fragment中请传this,方便生命周期管理.
 */

public class HttpRequest {

    private ApiService mService = ApiClient.getInstance().mApiService;
    private static ArrayMap<Object, List<Call>> mCallMap = new ArrayMap<>();

    private HttpRequest() {
    }

    private static class SingletonHolder {
        private static HttpRequest instance = new HttpRequest();
    }

    public static HttpRequest getInstance() {
        return SingletonHolder.instance;
    }

    private synchronized void putCall(Object tag, Call call) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null) {
            callList = Collections.synchronizedList(new ArrayList<>());
        }
        callList.add(call);
        mCallMap.put(tag, callList);
    }

    public synchronized void cancel(Object tag) {
        if (tag == null) {
            return;
        }
        List<Call> callList = mCallMap.get(tag);
        if (callList == null || callList.size() == 0) {
            return;
        }
        for (Call call : callList) {
            if (!call.isCanceled()) {
                call.cancel();
            }
        }
        mCallMap.remove(tag);
    }

    private String getOid() {
        return SharePreferencesUtil.getString(BaseApplication.getAppContext(), LotteryId.Login_oid, null);
    }

    private class RequestBodyBuilder {

        Map<String, Object> params;

        private RequestBodyBuilder() {
            params = new HashMap<>();
        }

        private RequestBodyBuilder addParam(String key, Object value) {
            params.put(key, value);
            return this;
        }

        private RequestBody build() {
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonUtil.toJson(params));
        }
    }


    /**
     * 开局获取token
     */

    public void getToken(Object tag, Callback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<LoginModel> call = mService.getToken(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 用户注册
     */
    public void register(Object tag, @Nullable String parentName, String username, String password, String TrueName,
                         String payPassword, String email,
                         HttpCallback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("ParentUsername", parentName)
                .addParam("Name", username)
                .addParam("Password", password)
                .addParam("ConfirmPassword", password)
                .addParam("TrueName", TrueName)
                .addParam("WithdrawPassword", payPassword)
                .addParam("Email", email)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<BaseModel> call = mService.register(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 用户登录
     */
    public void login(Object tag, String token, String userName, String password, String CheckCode, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.USER_NAME, userName)
                .addParam(LotteryId.PASSWORD, password)
                .addParam("CheckCode", CheckCode)
                .addParam("token", token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<LoginModel> call = mService.login(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 用户试玩登录
     */
    public void loginDemo(Object tag , String token,Callback<UserModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<UserModel> call = mService.loginDemo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取登录信息
     */
    public void getLoginfo(Object tag, String token, HttpCallback<GetUserinfoModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<GetUserinfoModel> call = mService.getLoginfo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 登出
     */
    public void Loginout(Object tag, String token, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<LoginModel> call = mService.loginout(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取消息列表
     */
    public void getMessageList(Object tag, String token, String pageIndex, String pageSize, HttpCallback<MessageModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<MessageModel> call = mService.getMessageList(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取消息详情
     */
    public void getMessageDetail(Object tag, String token, String id,  HttpCallback<MessageDetailModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("id", id)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<MessageDetailModel> call = mService.getMessageDetail(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取余额
     */
    public void getBalance(Object tag, String token, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<LoginModel> call = mService.getBanlance(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 修改登录密码
     */
    public void getLoginPassword(Object tag, String token, String password, String newPassword, HttpCallback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("password", password)
                .addParam("newPassword", newPassword)
                .addParam("confirmPassword", newPassword)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<BaseModel> call = mService.getLoginPassword(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 修改取款密码
     */
    public void changeTakeoutMoneyPassword(Object tag, String token, String password, String newPassword, HttpCallback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("password", password)
                .addParam("newPassword", newPassword)
                .addParam("confirmPassword", newPassword)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<BaseModel> call = mService.changeMoneyPass(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 查询用户银行卡
     */
    public void getUserBank(Object tag, String token, HttpCallback<BankcardsModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<BankcardsModel> call = mService.getUserbank(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *   设置用户的银行卡
     */
    public void setUserbank(Object tag, String token,String UserId,String BankId, String BankAccount,String BankAccountName,
                            String BankAddress,String Description,HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("UserId", UserId)
                .addParam("BankId", BankId)
                .addParam("BankAccount", BankAccount)
                .addParam("BankAccountName", BankAccountName)
                .addParam("BankAddress",BankAddress)
                .addParam("Description",Description)
                .build();
        Call<LoginModel> call = mService.setUserbank(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 查询所有游戏
     */
    public void getAllgames(Object tag, String token, HttpCallback<AllgameModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<AllgameModel> call = mService.getAllgames(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取最新期号的开奖时间
     *
     * @param tag
     * @param gameCode
     * @param callback
     */
    public void getGameOpenTime(Object tag, String token, String gameCode, HttpCallback<getGameOpentimeModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("gameCode", gameCode)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<getGameOpentimeModel> call = mService.getGameOpenTime(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取最新期号的开奖时间
     *  游戏大厅的接口
     */
    public void getGameOpenTime2(Object tag, String token, String gameCode, HttpCallback<GameOpentimeModel2> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("gameCode", gameCode)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<GameOpentimeModel2> call = mService.getGameOpenTime2(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取验证码
     */
    public void getCheckCode(Object tag, String token, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<LoginModel> call = mService.getCheckCode(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取玩法赔率
     */
    public void getPlayRate(Object tag, String token, String playtype, HttpCallback<PK10RateModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("Code", playtype)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<PK10RateModel> call = mService.getPlayRate(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 投注
     */
    public void saveOrders(Object tag, String token, String gameCode, String expectNo, Object betdata, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("GameCode", gameCode)
                .addParam("ExpectNo", expectNo)
                .addParam("Data", betdata)
                .build();
        Call<LoginModel> call = mService.saveOrders(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 提现记录列表
     */
    public void getWithdrawRecord(Object tag, String token, int pageIndex, int pageSize,
                           HttpCallback<WithdrawRecordModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .build();
        Call<WithdrawRecordModel> call = mService.getWithdrawRecord(body);
        putCall(tag, call);
        call.enqueue(callback);
    }




    /**
     * 充值记录列表
     */
    public void rechargeRecord(Object tag, String token, int pageIndex, int pageSize,
                                  HttpCallback<PayinRecordMoudel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .build();
        Call<PayinRecordMoudel> call = mService.rechargeRecord(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 查询开奖结果
     */
    public void getCollectResult(Object tag, String token, String GameCode, String OpenDate, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("GameCode", GameCode)
                .addParam("OpenDate", OpenDate)
                .build();
        Call<LoginModel> call = mService.getCollectResult(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 充值
     */
    public void recharge(Object tag, String token, String amount,String PaymentModeCode,String  PaymentMethodCode,
                         String BankCode,HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("amount", amount)
                .addParam("PaymentModeCode", PaymentModeCode)
                .addParam("PaymentMethodCode", PaymentMethodCode)
                .addParam("BankCode", BankCode)
                .build();
        Call<LoginModel> call = mService.recharge(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *  提现
     */
    public void withdraw(Object tag, String token, String Amount, String WithdrawPassword, HttpCallback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("Amount", Amount)
                .addParam("WithdrawPassword", WithdrawPassword)
                .build();
        Call<BaseModel> call = mService.withdraw(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *  详情
     */
    public void getDetail(Object tag, String token, HttpCallback<PersonalModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<PersonalModel> call = mService.getDetail(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取一周的下注列表
     */
    public void getBetreportWeekdate(Object tag, String token, HttpCallback<WeekdateModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<WeekdateModel> call = mService.getWeekDate(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 下注报表
     */
    public void getBetReportByDate(Object tag, String token,String beginTime,String endTime, HttpCallback<BetrecordBydateModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("beginTime", beginTime)
                .addParam("endTime", endTime)
                .build();
        Call<BetrecordBydateModel> call = mService.getBetReportByDate(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *  根据时间查询下注数据
     */
    public void getBetReportByDay(Object tag, String token,String beginTime, HttpCallback<BetDetailModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("beginTime", beginTime)
                .build();
        Call<BetDetailModel> call = mService.getBetReportByDay(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     *  查詢今日已结投注记录
     */
    public void getTodaySettledOrders(Object tag, String token,String GameCode,String ExpectNo, String beginTime,String endTime,int pageIndex,int pageSize,  HttpCallback<SettledOrdersModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("GameCode", GameCode)
                .addParam("ExpectNo", ExpectNo)
                .addParam("beginTime", beginTime)
                .addParam("endTime", endTime)
                .addParam("pageIndex", pageIndex)
                .addParam("pageSize", pageSize)
                .build();
        Call<SettledOrdersModel> call = mService.getTodaySettledOrders(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     *   获取所有的字典
     */
    public void getdictionaryAll(Object tag, String token,String dictionaryGroupId,HttpCallback<BankcardModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("dictionaryGroupId", dictionaryGroupId)
                .build();
        Call<BankcardModel> call = mService.getDictionaryAll(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取公告列表
     */
    public void getNoticeList(Object tag, String token,HttpCallback<NoticeListModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<NoticeListModel> call = mService.getNoticerlist(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     *   查询开奖结果
     */
    public void getCollectResultByPages(Object tag, String token,String GameCode,String OpenDate,
           int pageIndex,int pageSize, HttpCallback<LotteryHistoryModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("GameCode", GameCode)
                .addParam("OpenDate", OpenDate)
                .addParam("pageIndex",pageIndex)
                .addParam("pageSize", pageSize)
                .build();
        Call<LotteryHistoryModel> call = mService.getCollectResultByPages(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取支付方式
     */
    public void getPaymentMethod(Object tag, String token, HttpCallback<PaymentMethodModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .build();
        Call<PaymentMethodModel> call = mService.getPaymentMethod(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 手机app请求第三方充值
     */
    public void phoneDeposit(Object tag, String token,String orderNo, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("orderNo", orderNo)
                .build();
        Call<LoginModel> call = mService.phoneDeposit(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 手机app请求第三方充值
     */
    public void PhoneQrcode(Object tag, String token,String orderNo, HttpCallback<LoginModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.TOKEN, token)
                .addParam("sourcetype", LotteryId.sourcetype)
                .addParam("version", LotteryId.version)
                .addParam("orderNo", orderNo)
                .build();
        Call<LoginModel> call = mService.PhoneQrcode(body);
        putCall(tag, call);
        call.enqueue(callback);
    }
//======================================================================================================================







    public void getPromotions(Object tag, int num, Callback<PreferentialBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam("num", num)
                .build();
        Call<PreferentialBean> call = mService.getPromotions(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取账号及余额信息
     */
    public void requestAmountInfo(Object tag, Callback<MoneyInfo> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .build();
        Call<MoneyInfo> call = mService.requestAmountInfo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取用户支持的入款方式
     */
    public void requestPayInWays(Object tag, Callback<PayInModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .build();
        Call<PayInModel> call = mService.requestPayInWays(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取用户存取款记录
     *
     * @param pageIndex 第几页 默认值 1
     * @param pageSize  每页多少条数据 最大值30
     */
    public void requestRecords(Object tag, int pageIndex, int pageSize, String gameName, Callback<TransactionRecord> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(LotteryId.RECORD_PAGE, pageIndex)
                .addParam(LotteryId.RECORD_NUMBER, pageSize)
                .addParam(LotteryId.GAME_NAME, gameName)
                .build();
        Call<TransactionRecord> call = mService.requestRecords(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 在线提款接口
     *
     * @param amount      提款金额
     * @param payPassword 支付密码
     * @param bankName    银行名称
     * @param bankAddress 开户行地址
     * @param bankcardId  银行卡号
     */
    public void requestWithdraw(Object tag, String amount, String payPassword, String bankName,
                                String bankAddress, String bankcardId, String gameName, Callback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(LotteryId.DRAW_MONEY, amount)
                .addParam(LotteryId.PAY_PWD, payPassword)
                .addParam(LotteryId.BANKNAME, bankName)
                .addParam(LotteryId.BANKADDRESS, bankAddress)
                .addParam(LotteryId.BANKCODE, bankcardId)
                .addParam(LotteryId.GAME_NAME, gameName)
                .build();
        Call<BaseModel> call = mService.requestWithdraw(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 在线支付
     *
     * @param payId 支付id
     */
    public void requestOnlinePayment(Object tag, String payId, String gameName, Callback<OnlinePay> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(LotteryId.PAYID, payId)
                .addParam(LotteryId.GAME_NAME, gameName)
                .addParam("is_json", "yes")
                .build();
        Call<OnlinePay> call = mService.requestOnlinePayment(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 线下支付
     *
     * @param amount   支付金额
     * @param orderNo  订单后四位
     * @param realName 真实姓名
     * @param typeName 支付类型(银行名称/支付宝/微信)
     * @param date     日期
     * @param payWay   支付方式
     * @param bankId   支付id
     * @param remarks  备注
     */
    public void requestOfflinePay(Object tag, String amount, String orderNo, String realName,
                                  String typeName, String date, String payWay, String bankId,
                                  String remarks, String gameName, Callback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam("amount", amount)
                .addParam("orderNo", orderNo)
                .addParam("realname", realName)
                .addParam("typeName", typeName)
                .addParam("date", date)
                .addParam("way", payWay)
                .addParam("bank_id", bankId)
                .addParam("payReason", remarks)
                .addParam(LotteryId.GAME_NAME, gameName)
                .build();
        Call<BaseModel> call = mService.requestOfflinePay(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 增加或修改银行卡信息
     *
     * @param bankName    银行名称
     * @param bankCode    银行卡号
     * @param bankAddress 开户地址
     */
    public void addOrModifyBankcard(Object tag, String bankName, String bankCode,
                                    String bankAddress, Callback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(LotteryId.BANK_NAME, bankName)
                .addParam(LotteryId.BANK_CODE, bankCode)
                .addParam(LotteryId.BANK_ADDRESS, bankAddress)
                .build();
        Call<BaseModel> call = mService.addOrModifyBankcard(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取开奖信息 含本期和上期
     *
     * @param gameCode 游戏数字代号
     */
    public void requestLotteryInfo(Object tag, int gameCode, Callback<TimeInfoBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(GAME_CODE, gameCode)
                .build();
        Call<TimeInfoBean> call = mService.requestLotteryInfo(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取赔率信息
     *
     * @param gameCode 游戏数字代号
     * @param typeCode 玩法数字代号
     */
    public void requestOdds(Object tag, int gameCode, int typeCode, ArrayCallback<List<OddsModel>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(GAME_CODE, gameCode)
                .addParam(LotteryId.TYPE_CODE, typeCode)
                .build();
        Call<JsonElement> call = mService.requestOdds(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void requestAllOdds(Object tag, int gameCode, HttpCallback<Quick3Odds> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(GAME_CODE, gameCode)
                .build();
        Call<Quick3Odds> call = mService.requestAllOdds(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * @param gameCode 游戏数字代号
     * @param typeCode 玩法数字代号
     */
    public void requestOdds2(Object tag, int gameCode, int typeCode, ArrayCallback<List<NewOddsBean>> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .addParam(GAME_CODE, gameCode)
                .addParam(LotteryId.TYPE_CODE, typeCode)
                .build();
        Call<JsonElement> call = mService.requestOdds(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注游戏一: 本参数试用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
     * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
     *
     * @param gameCode 游戏数字代号
     * @param typeCode 玩法数字代号
     * @param round    游戏期数
     * @param betList  选中的要下注的列表
     * @param betMoney 每注下注金额
     */
    public void requestBet(Object tag, int gameCode, int typeCode, String round, List<MultiBetItem> betList,
                           int betMoney, ArrayCallback<List<BetResultBean>> callback) {
        String token = SharePreferencesUtil.getString(BaseApplication.getAppContext(), LotteryId.TOKEN, null);
        RequestBodyBuilder builder = new RequestBodyBuilder()
                .addParam(LotteryId.OID, getOid())
                .addParam(LotteryId.GAME_CODE, gameCode)
                .addParam(LotteryId.TOKEN, token)
                .addParam(LotteryId.TYPE_CODE, typeCode)
                .addParam(LotteryId.ROUND, round);
        for (MultiBetItem betItem : betList) {
            builder.addParam(betItem.getBetItem().getKey(), betMoney);
        }
        Call<JsonElement> call = mService.requestBet(builder.build());
        putCall(tag, call);
        call.enqueue(callback);
    }

    public void requestBet(Object tag, RequestBody body, ArrayCallback<List<BetResultBean>> callback) {
        Call<JsonElement> call = mService.requestBet(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注游戏一: 本参数试用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
     * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
     *
     * @param gameCode 游戏数字代号
     * @param typeCode 玩法数字代号
     * @param round    游戏期数
     * @param betList  选中的要下注的列表
     * @param betMoney 每注下注金额
     */
    public void requestBet(Object tag, int gameCode, int typeCode, String round, String betMoney, List<NewOddsBean.ListBean> betList,
                           ArrayCallback<List<BetResultBean>> callback) {
        String token = SharePreferencesUtil.getString(BaseApplication.getAppContext(), LotteryId.TOKEN, null);
        RequestBodyBuilder builder = new RequestBodyBuilder()
                .addParam(LotteryId.OID, getOid())
                .addParam(LotteryId.TOKEN, token)
                .addParam(LotteryId.GAME_CODE, gameCode)
                .addParam(LotteryId.TYPE_CODE, typeCode)
                .addParam(LotteryId.ROUND, round);
        for (NewOddsBean.ListBean betItem : betList) {
            builder.addParam(betItem.getKey(), betMoney);
        }
        Call<JsonElement> call = mService.requestBet(builder.build());
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注游戏二: 适用于 广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）
     * 下注游戏三: 适用于（过关 ，合肖，生肖连，尾数连，全不中）
     *
     * @param gameCode  游戏数字代号
     * @param typeCode  玩法数字代号
     * @param round     游戏期数(游戏接口获取)
     * @param comboCode 选择连中(仅连码需要此参数)/ 三:无此参数 传null
     * @param number    二:下注的球号用英文逗号拼接(1-20号球)/ 三:去掉前缀的key值用英文逗号拼接
     * @param betMoney  金额
     */
    public void requestBet(Object tag, int gameCode, int typeCode, String round, String comboCode,
                           String number, String betMoney, ArrayCallback<List<BetResultBean>> callback) {
        String token = SharePreferencesUtil.getString(BaseApplication.getAppContext(), LotteryId.TOKEN, null);
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, getOid())
                .addParam(LotteryId.GAME_CODE, gameCode)
                .addParam(LotteryId.TOKEN, token)
                .addParam(LotteryId.TYPE_CODE, typeCode)
                .addParam(LotteryId.ROUND, round)
                .addParam(LotteryId.TYPECODE, comboCode)
                .addParam(LotteryId.BET_NUMBER, number)
                .addParam(LotteryId.BET_MONEY, betMoney)
                .build();
        Call<JsonElement> call = mService.requestBet(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 获取各彩种开奖结果及开奖倒计时
     */
    public void requestGameCloseTime(Object tag, HttpCallback<HomeResultBean> callback) {
        Call<HomeResultBean> call = mService.getGameCloseTime();
        putCall(tag, call);
        call.enqueue(callback);
    }






    public void getLoginNotice(Object tag, HttpCallback<Notice> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(OID, getOid())
                .build();
        Call<Notice> call = mService.getLoginNotice(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * @param payUrl   支付链接
     * @param userId   用户id
     * @param payId    payId
     * @param amount   支付金额
     * @param gameName 游戏名称
     * @param bankName 银行名称
     */
    public void onlinePay(Object tag, String payUrl, String userId, String payId, String amount,
                          String gameName, String bankName, Callback<QRModel> callback) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userId);
        map.put("pay_id", payId);
        map.put("money", amount);
        map.put("is_json", "yes");
        if (!TextUtils.isEmpty(gameName)) {
            map.put("GameName", gameName);
        }
        if (!TextUtils.isEmpty(bankName)) {
            map.put("PayID", bankName);
        }
        Call<QRModel> call = mService.onlinePay(payUrl, map);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 个人资料
     *
     * @param oid 用户id
     */
    public void getPersonalData(Object tag, String oid, HttpCallback<PersonalBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, oid)
                .build();
        Call<PersonalBean> call = mService.getPersonalData(body);
        putCall(tag, call);
        call.enqueue(callback);
    }


    /**
     * 修改支付密码
     *
     * @param oid          用户id
     * @param oldpaypasswd 旧密码
     * @param paypasswd    新密码
     */
    public void getPayPassword(Object tag, String oid, String oldpaypasswd, String paypasswd, HttpCallback<BaseModel> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, oid)
                .addParam("oldpaypasswd", oldpaypasswd)
                .addParam("paypasswd", paypasswd)
                .build();
        Call<BaseModel> call = mService.getPayPassword(body);
        putCall(tag, call);
        call.enqueue(callback);
    }



    /**
     * 下注记录-未结数
     *
     * @param oid    用户id
     * @param page   页数
     * @param number 每页数量
     * @param type   未结 0 已结 1
     * @param time   当天日期
     * @param win    全部 0 输 -1 赢 1
     */
    public void getSummaryDetails(Object tag, String oid, String page, String number, String type, String time, int win, HttpCallback<SummaryDetailsBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, oid)
                .addParam("page", page)
                .addParam("number", number)
                .addParam("type", type)
                .addParam("time", time)
                .addParam("win", win)
                .build();
        Call<SummaryDetailsBean> call = mService.getSummaryDetails(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注记录-汇总
     *
     * @param oid 用户id
     */
    public void getSummary(Object tag, String oid, HttpCallback<SummaryBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, oid)
                .build();
        Call<SummaryBean> call = mService.getSummary(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 下注记录-未结已结
     *
     * @param oid    用户id
     * @param page   页数
     * @param number 每页数量
     * @param type   未结 0 已结 1
     * @param time   当天日期
     * @param win    全部 0 输 -1 赢 1
     */
    public void getOutStandHave(Object tag, String oid, int page, String number, String type, String time, int win, HttpCallback<SummaryDetailsBean> callback) {
        RequestBody body = new RequestBodyBuilder()
                .addParam(LotteryId.OID, oid)
                .addParam("page", page)
                .addParam("number", number)
                .addParam("type", type)
                .addParam("time", time)
                .addParam("win", win)
                .build();
        Call<SummaryDetailsBean> call = mService.getOutStandHave(body);
        putCall(tag, call);
        call.enqueue(callback);
    }

    /**
     * 获取android的版本升级信息
     *
     * @param tag
     * @param callback
     */
    public void getAndroidVertion(Object tag, HttpCallback<LotteryVersion> callback) {
        Call<LotteryVersion> call = mService.getVersion();
        putCall(tag, call);
        call.enqueue(callback);
    }


}
