package com.international.wtw.lottery.fragment;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.LotteryHistoryActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.json.TimeInfoBean;
import com.international.wtw.lottery.json.Token;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.LotteryPropertyView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by XiaoXin on 2017/9/29.
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
    @BindView(R.id.tv_time_seconds)
    TextView mTvTimeSeconds;
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
    private int gameCode;
    //离开奖时间秒数
    private int endSeconds;
    //离封盘时间秒数
    private int closeSeconds;
    private Handler mHandler;
    //开奖提示音的播放, 一般这种提示音会用SoundPool播放,
    //但产品给定MP3文件资源太大, SoundPool播放不全, 直接用MediaPlayer了..
    //SoundPool的音频文件大小不能超过1M同时时间超过5-6秒可能会出错。
    private SoundPool soundPool;//声明一个SoundPool
    private int soundID;//创建某个声音对应的音频ID
    private int streamID;
    private boolean isFirstTime = true;
    private String mNextRound = "";

    public static LotteryInfoFragment newInstance(int gameCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(GAME_CODE, gameCode);
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
        gameCode = getArguments().getInt(GAME_CODE);
        if (Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY == gameCode || Constants.LOTTERY_TYPE.SPEED_MARK_SIX == gameCode) {
            mLlTime.setVisibility(View.GONE);
            mLlTime2.setVisibility(View.VISIBLE);
            mTvLotteryTime.setText("00:00:00");
        } else {
            mLlTime.setVisibility(View.VISIBLE);
            mLlTime2.setVisibility(View.GONE);
            mTvLotteryTime.setText("00:00");
        }

        initSound();
//        getFirstToken();

        ll_lottery_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LotteryHistoryActivity.class);
                intent.putExtra(LotteryId.GAME_CODE, gameCode);
                startActivity(intent);
            }
        });
    }

    public String getNextRound() {
        return mNextRound;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null == mHandler)
            mHandler = new Handler();
        //请求开奖信息
        requestTimeInfo();
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

//    private void getFirstToken() {
//        HttpRequest.getInstance().getToken(this, new HttpCallback<Token>() {
//            @Override
//            public void onSuccess(Token data) {
//                //先把后台返回的token存起来
//                MemoryCacheManager.getInstance().putToken(data.token);
//            }
//
//            @Override
//            public void onFailure(String msgCode, String errorMsg) {
//                //请求失败把token置空
//                MemoryCacheManager.getInstance().putToken(null);
//            }
//        });
//    }

    private void requestTimeInfo() {
        HttpRequest.getInstance().requestLotteryInfo(this, gameCode, new HttpCallback<TimeInfoBean>() {
            @Override
            public void onSuccess(TimeInfoBean data) {
                if (isAdded())
                    setLotteryInfo(data);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestTimeInfo();
                    }
                }, 5 * 1000);
            }
        });
    }

    /**
     * @param data 设置本期和上期的开奖信息
     */
    private void setLotteryInfo(TimeInfoBean data) {

        TimeInfoBean.NextBean nextBean = data.getNext();
        TimeInfoBean.LastBean lastBean = data.getLast();
        //余额
        mTvBalance.setText(String.format(Locale.getDefault(), "%.2f", data.getLcurrency()));
        //这时间去通知修改右上角弹框的金额、因为requestMoneyInfo是异步请求 没有及时刷新 所以提前刷新一次（待优化）
        MoneyInfoManager.get().requestMoneyInfo();
        //下期开奖数据
        if (nextBean != null) {
            String nextRound = nextBean.getRound();
            if (nextRound != null && !nextRound.equals(mNextRound)) {
                mNextRound = nextBean.getRound();
                SharePreferencesUtil.addString(mActivity, LotteryId.ROUND, mNextRound);
                mTvNextRoundNo.setText(String.format(Locale.getDefault(), "%s期", mNextRound));
                if (nextBean.getEndtime() == null || nextBean.getClosetime() == null || nextBean.getTimestap() == null) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestTimeInfo();
                        }
                    }, 5000);
                    return;
                }
                if (TextUtils.isEmpty(nextBean.getEndtime()) || TextUtils.isEmpty(nextBean.getTimestap())) {
                    endSeconds = 0;
                } else {
                    endSeconds = (int) (Long.parseLong(nextBean.getEndtime())
                            - Long.parseLong(nextBean.getTimestap()));
                }
                if (TextUtils.isEmpty(nextBean.getClosetime()) || TextUtils.isEmpty(nextBean.getTimestap())) {
                    closeSeconds = 0;
                } else {
                    closeSeconds = (int) (Long.parseLong(nextBean.getClosetime())
                            - Long.parseLong(nextBean.getTimestap()));
                }

                //是否封盘
                //如果返回了isclose参数(只有晚上封盘时返回), 则判断isclose参数
                //否则还是以返回的封盘时间作判断
                boolean isClose = nextBean.isclose() != null && nextBean.isclose() || closeSeconds <= 0;
                LogUtil.e("=======gameCode=====" + gameCode);
                EventBus.getDefault().post(new BetClosedEvent(gameCode, isClose));
                LogUtil.e("是否封盘: =========" + isClose);
                //显示开奖/封盘时间, 并倒计时
                refreshTime(isClose);
                if (endSeconds > 0) {//如果开奖倒计时大于0 开始倒计时
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (endSeconds > 0) {
                                endSeconds--;
                                closeSeconds--;
                                if (closeSeconds == 0) {
                                    LogUtil.e("" + closeSeconds);
                                    EventBus.getDefault().post(new BetClosedEvent(gameCode, true));
                                }
                                refreshTime(isClose);
                                mHandler.postDelayed(this, 1000);
                            }
                            if (endSeconds == 0) {
                                mHandler.removeCallbacks(this);
                                requestTimeInfo();
                            }
                        }
                    }, 1000);
                }
            }
        }

        //上一期开奖数据
        if (lastBean == null) {
            return;
        }
        String lastRound = lastBean.getRound();
        if (lastRound != null && lastBean.getNumber() != null) {
            showLastLotteryNumbers(Long.valueOf(lastBean.getEndtime() + "000"), lastRound, lastBean.getNumber());
        }

        if (TextUtils.isEmpty(mNextRound) || TextUtils.isEmpty(lastRound)) {
            return;
        }
        String[] nextS = mNextRound.split("-");
        String[] lastS = lastRound.split("-");
        long roundDiff = Long.valueOf(nextS[nextS.length - 1]) - Long.valueOf(lastS[lastS.length - 1]);
        LogUtil.e(roundDiff + "");
        //如果上一期开奖还没刷新出来, 则每过10秒请求一次数据, 直到刷出开奖结果为止
        if (Math.abs(roundDiff) != 1) {
            if (isFirstTime) {
                isFirstTime = false;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestTimeInfo();
                }
            }, 10 * 1000);
        } else {
            //播放音频
            boolean isPlay = SharePreferencesUtil.getBoolean(mActivity, LotteryId.PLAY_RINGTONE, false);
            if (isFirstTime) {
                isFirstTime = false;
            } else if (isPlay) {
                playSound();
            }
        }
    }

    private void refreshTime(boolean isClose) {
        String timeEnd = TimeFormatter.seconds2Time(endSeconds);
        if (!isAdded()) {
            mHandler.removeCallbacksAndMessages(null);
            return;
        }
        mTvLotteryTime.setText(timeEnd);
        if (closeSeconds > 0) {
            String hour = TimeFormatter.getHour(closeSeconds);
            String minute = TimeFormatter.getMinuteOfHour(closeSeconds);
            String second = TimeFormatter.getSecondOfMinute(closeSeconds);
            if (!"00".equals(hour)) {
                mLlTime.setVisibility(View.GONE);
                mLlTime2.setVisibility(View.VISIBLE);
                mTvTimeHour.setText(isClose ? "--" : hour);
                mTvTimeMinute2.setText(isClose ? "--" : minute);
                mTvTimeSeconds2.setText(isClose ? "--" : second);
            } else {
                mLlTime.setVisibility(View.VISIBLE);
                mLlTime2.setVisibility(View.GONE);
                mTvTimeMinute.setText(isClose ? "--" : minute);
                mTvTimeSeconds.setText(isClose ? "--" : second);
            }
        } else {
            mTvTimeHour.setText("--");
            mTvTimeMinute.setText("--");
            mTvTimeSeconds.setText("--");
            mTvTimeMinute2.setText("--");
            mTvTimeSeconds2.setText("--");
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

    @Override
    public void onDestroy() {
        stopSound();
        super.onDestroy();
    }

    private void initSound() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundID = soundPool.load(getContext(), R.raw.lottery_ring, 1);
    }

    /**
     * 播放开奖音频
     */
    private void playSound() {
        streamID = soundPool.play(soundID,
                1,//左耳道音量【0~1】
                1,//右耳道音量【0~1】
                100, //播放优先级【0表示最低优先级】
                0, //循环模式【0表示播放一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1//播放速度【1是正常，范围从0~2】
        );
    }

    /**
     * 停止播放音频
     */
    private void stopSound() {
        soundPool.stop(streamID);
    }
}
