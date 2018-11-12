package com.international.wtw.lottery.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.BetSelectData;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.event.OpenAndClosedEvent;
import com.international.wtw.lottery.newJason.getGameOpentimeModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.LotteryPropertyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;

/**
 * 描述：开奖信息的部分单独在一个Fragment中处理
 */

public class LotteryInfoFragment extends NewBaseFragment {
    public static final String GAME_CODE = "game_code";
    @BindView(R.id.tv_next_round_no)
    TextView mTvNextRoundNo;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_lottery_time)
    TextView mTvLotteryTime;
    @BindView(R.id.ll_time)
    LinearLayout mLlTime;
    @BindView(R.id.tv_time_minute)
    TextView mTvTimeMinute;
    @BindView(R.id.ll_time2)
    LinearLayout mLlTime2;
    @BindView(R.id.tv_time_hour)
    TextView mTvTimeHour;
    @BindView(R.id.tv_time_minute2)
    TextView mTvTimeMinute2;
    @BindView(R.id.tv_time_seconds2)
    TextView mTvTimeSeconds2;
    @BindView(R.id.tv_last_round)
    TextView mTvLastRound;
    @BindView(R.id.lotteryNumberView)
    LotteryNumberView mLotteryNumberView;
    @BindView(R.id.lotteryPropertyView)
    LotteryPropertyView mLotteryPropertyView;
    @BindView(R.id.ll_lottery_history)
    LinearLayout ll_lottery_history;
    private String gameCode;
    //离开奖时间秒数
    private int endSeconds;
    //离封盘时间秒数
    private int closeSeconds;
    private Handler mHandler;
    private String mNextRound = "";

    public static LotteryInfoFragment newInstance(String gameCode) {
        Bundle bundle = new Bundle();
        bundle.putString(GAME_CODE, gameCode);
        LotteryInfoFragment fragment = new LotteryInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery_info;
    }

    @Override
    protected void initData() {
        gameCode = getArguments().getString(GAME_CODE);
        mLlTime.setVisibility(View.VISIBLE);
        mLlTime2.setVisibility(View.GONE);
        mTvLotteryTime.setText("00:00");
    }

    public String getNextRound() {
        return mNextRound;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == mHandler)
            mHandler = new Handler();
        //获取上期和本期的信息
        getGameOpenTime();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }


    @Subscribe
    public void onEvent(MoneyInfoRefreshEvent event) {
        if (event.moneyInfo != null) {
            mTvBalance.setText(String.format(Locale.getDefault(), "%.2f", event.moneyInfo.getMoney()));
        }
    }


    private void getGameOpenTime() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getGameOpenTime(this,token, gameCode, new HttpCallback<getGameOpentimeModel>() {
            @Override
            public void onSuccess(getGameOpentimeModel data) {
                if (isAdded()) {
                    setLotteryInfothisRound(data);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getGameOpenTime();
                    }
                }, 5 * 1000);
            }
        });
    }

    /**
     *  设置本期的开奖时间
     * @param data
     */
    private void setLotteryInfothisRound(getGameOpentimeModel data) {
        mTvNextRoundNo.setText(String.format(Locale.getDefault(), "%s期", data.getData().getExpectNoNext()));
        if (null!=data.getData().getExpectNo()&&null!=data.getData().getCode()){
            String str = data.getData().getCode();
            String[] strs=str.split(",");
            List list=new ArrayList();
            for(int i=0,len=strs.length;i<len;i++){
                list.add(strs[i]);
            }
             //设置上一期的开奖号码
            showLastLotteryNumbers(00,data.getData().getExpectNo(),list);

            //设置本期的开奖时间
            String serverTime=data.getData().getServerTime();
            String OpenTime=data.getData().getOpenTime();
            String CloseTime=data.getData().getCloseTime();

                //closeSeconds 剩余封盘时间
                if (TextUtils.isEmpty(CloseTime) || TextUtils.isEmpty(serverTime)) {
                    closeSeconds = 0;
                } else {
                    closeSeconds = (int) (Long.parseLong(CloseTime) - Long.parseLong(serverTime));
                }

                if (closeSeconds>0){
                    EventBus.getDefault().postSticky(new OpenAndClosedEvent(gameCode, data.getData().getExpectNoNext(),false,false));
                }else{
                    EventBus.getDefault().postSticky(new OpenAndClosedEvent(gameCode, data.getData().getExpectNoNext(),true,true));
                }

                 //endSeconds  剩余开奖时间
                if (TextUtils.isEmpty(OpenTime) || TextUtils.isEmpty(serverTime)) {
                    endSeconds = 0;
                } else {
                    endSeconds = (int) (Long.parseLong(OpenTime) - Long.parseLong(serverTime));
                }

            //如果开奖倒计时大于0 开始倒计时
            if (endSeconds > 0) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (endSeconds > 0) {
                            endSeconds--;
                            closeSeconds--;
                            if (closeSeconds == 0) {
                                //清除选中的数据
                                EventBus.getDefault().postSticky(new BetSelectData(false,null,true));
                                //发送通知
                                EventBus.getDefault().postSticky(new OpenAndClosedEvent(gameCode, data.getData().getExpectNoNext(),true,true));

                            }
                            refreshTime(data);
                            mHandler.postDelayed(this, 1000);
                        }else{
                            mHandler.removeCallbacks(this);
                            getGameOpenTime();
                        }
                    }
                }, 100);
            }

        }

    }


    /**
     * 刷新倒计时
     */
    private void refreshTime(getGameOpentimeModel data) {
        String timeEnd = TimeFormatter.seconds2Time(endSeconds);
        String timeClose = TimeFormatter.seconds2Time(closeSeconds);
        if (!isAdded()) {
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        mTvLotteryTime.setText(timeEnd);

        if (closeSeconds > 0) {
            mTvTimeMinute.setText(timeClose);
        }else{
            mTvTimeMinute.setText("--:--");
        }


        //如果上一期的号码一直没开出来，5秒刷新一次
        if (Long.valueOf(data.getData().getExpectNoNext())-Long.valueOf(data.getData().getExpectNo())>1){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getGameOpenTime();
                }
            },8*1000);
        }

    }

    /**
     * 显示上一期开奖(期数,号码.属性)
     */
    private void showLastLotteryNumbers(long time, String round, List<String> numbers) {
        mTvLastRound.setText(String.format("%s期：", round));
        mLotteryNumberView.setNumbers(gameCode, numbers);
            if (mLotteryPropertyView.getVisibility() != View.VISIBLE) {
                mLotteryPropertyView.setVisibility(View.VISIBLE);
            }
            mLotteryPropertyView.setNumbers(time, gameCode, numbers);


    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        mNextRound = "";
    }

}
