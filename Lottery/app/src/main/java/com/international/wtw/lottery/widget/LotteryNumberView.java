package com.international.wtw.lottery.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.List;

/**
 * Created by XiaoXin on 2018/1/11.
 * 描述：用于显示开奖号码的View
 * (现在开奖号码都是一行显示的, 直接继承LinearLayout吧, 如果多行显示可以考虑用FlexboxLayout)
 */

public class LotteryNumberView extends LinearLayout {

    public LotteryNumberView(Context context) {
        super(context);
    }

    public LotteryNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置开奖号码
     *
     * @param gameCode 彩种编号
     * @param numbers  开奖号码
     */
    public void setNumbers(int gameCode, List<String> numbers) {
        removeAllViews();
        for (int i = 0; i < numbers.size(); i++) {
            String number = numbers.get(i);
            int size = SizeUtils.dp2px(20);
            LayoutParams layoutParams = new LayoutParams(size, size);
            layoutParams.setMargins(SizeUtils.dp2px(3), 0, 0, 0);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            //添加TextView显示开奖号码
            TextView textView = new TextView(getContext());
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(12);
            switch (gameCode) {
                case Constants.LOTTERY_TYPE.PJ_PK_10://北京赛车
                case Constants.LOTTERY_TYPE.SPEED_CAR://极速赛车
                case Constants.LOTTERY_TYPE.HORSE_88://88赛马
                case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY://幸运飞艇
                case Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT://威尼斯赛艇
                case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://幸运农场
                case Constants.LOTTERY_TYPE.JS_QUICK_3://江苏快3
                    //这几个彩种直接使用图片作为背景, 且图片中已包含了数字, 不再设置号码
                    break;
                default:
                    textView.setText(number);
            }
            textView.setBackgroundResource(LotteryUtil.get().getBackgroundRes(gameCode, number));
            addView(textView, layoutParams);

            //以下是处理一些特殊的情况
            switch (gameCode) {
                case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
                case Constants.LOTTERY_TYPE.SPEED_MARK_SIX://极速六合彩
                    //相关六合彩, 特码与其他号码间用'+'号连接
                    if (i == 5) {
                        TextView tvPlus = new TextView(getContext());
                        tvPlus.setText("+");
                        tvPlus.setGravity(Gravity.CENTER);
                        tvPlus.setTextSize(14);
                        tvPlus.setTextColor(Color.GRAY);
                        addView(tvPlus, layoutParams);
                    }
                    break;
                case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
                    //PC蛋蛋, 每个号码之间用'+'号连接, 和值用'='连接
                    if (i != numbers.size() - 1) {
                        TextView tvPlus = new TextView(getContext());
                        tvPlus.setText("+");
                        tvPlus.setGravity(Gravity.CENTER);
                        tvPlus.setTextSize(14);
                        tvPlus.setTextColor(Color.GRAY);
                        addView(tvPlus, layoutParams);
                    } else {
                        TextView tvPlus = new TextView(getContext());
                        tvPlus.setText("=");
                        tvPlus.setGravity(Gravity.CENTER);
                        tvPlus.setTextSize(14);
                        tvPlus.setTextColor(Color.GRAY);
                        addView(tvPlus, layoutParams);
                        //和值
                        TextView tvSum = new TextView(getContext());
                        tvSum.setTextColor(Color.WHITE);
                        tvSum.setGravity(Gravity.CENTER);
                        tvSum.setTextSize(12);
                        String sum = sum(numbers);
                        tvSum.setText(sum);
                        tvSum.setBackgroundResource(LotteryUtil.get().getPCSumBg(sum));
                        addView(tvSum, layoutParams);
                    }
                    break;
            }
        }
    }

    /**
     * 计算和值
     *
     * @param numbers 开奖号码
     */
    private String sum(List<String> numbers) {
        int sum = 0;
        for (String number : numbers) {
            if (number.equals("")) {
                number = String.valueOf(0);
            }
            sum += Integer.valueOf(number);
        }
        return String.valueOf(sum);
    }
}
