package com.international.wtw.lottery.utils;

import android.os.Handler;
import android.widget.TextView;

import com.international.wtw.lottery.base.LotteryId;

/**
 * Created by wuya on 2017/7/28.
 */

public class CountDown {
    private Handler handler;
    private Long countDow;
    private TextView textView;

    public CountDown(TextView textView, Long countDow) {
        this.countDow = countDow;
        this.textView = textView;
        if (null==handler){
            handler = new Handler();
        }
        handler.removeCallbacksAndMessages(null);
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (countDow <= LotteryId.TIME_END) {
               textView.setText("00:00");
                handler.removeCallbacks(runnable);
                handler.removeCallbacksAndMessages(null);
                runnable=null;
            }else {
                countDow--;
                textView.setText(secondToTime(countDow));
                handler.postDelayed(runnable, LotteryId.ONE_SCEOND_IN_Millisecond);
            }

        }
    };

    private String secondToTime(long seconds) {
        int mi = 60;
        int hh = mi * 60;
        long hour = seconds / hh;
        long minute = (seconds - hour * hh) / mi;
        long second = (seconds - hour * hh - minute * mi) % mi;
        String h = hour == 0 ? "" : (hour < 10 ? "0" + hour + ":" : hour + ":");
        return h + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }
}
