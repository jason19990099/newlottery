package com.international.wtw.lottery.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.utils.LotteryUtil;

import java.util.List;

/**
 * 描述：用于显示开奖属性的View
 */

public class LotteryPropertyView extends LinearLayout {

    public LotteryPropertyView(Context context) {
        super(context);
    }

    public LotteryPropertyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryPropertyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置开奖号码
     *
     * @param time
     * @param gameCode 彩种编号
     * @param numbers  开奖号码
     */
    public void setNumbers(Long time, String gameCode, List<String> numbers) {
        removeAllViews();
        String sum = sum(numbers);
        switch (gameCode) {
//            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY://香港六合彩
//            case Constants.LOTTERY_TYPE.SPEED_MARK_SIX://极速六合彩
//                //总和大小
//                addPropertyTitleView("总和", 2);
//                addPropertyView(sum);
//                //生肖属性
//                addPropertyTitleView("生肖", 2);
//                for (String number : numbers) {
//                    addPropertyView(LotteryUtil.get().getZodiac(time,number));
//                }
//                break;
            case LotteryId.BJSCPK10://北京赛车
            case LotteryId.Miaosufeiting://秒速飞艇
            case LotteryId.MiaosuSaiche://秒速赛车
                //冠亚和属性
                int sum1 = Integer.parseInt(numbers.get(0)) + Integer.parseInt(numbers.get(1));
                addPropertyTitleView("冠亚和", 2);
                addPropertyView(String.valueOf(sum1));
                addPropertyView(sum1 >= 12 ? "大" : "小");
                addPropertyView(sum1 % 2 == 0 ? "双" : "单");
                //1-5球属性
                addPropertyTitleView("1-5球", 2);
                for (int i = 0; i < 5; i++) {
                    int property = Integer.parseInt(numbers.get(i)) - Integer.parseInt(numbers.get(numbers.size() - 1 - i));
                    addPropertyView(property > 0 ? "龙" : "虎");
                }
                break;
//            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY://广东快乐十分
//            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY://重庆幸运农场
//                //总和
//                addPropertyTitleView("总和", 2);
//                addPropertyView(sum);
//                //尾大小
//                addPropertyTitleView("尾大小", 2);
//                addPropertyView(Integer.parseInt(sum) % 10 >= 5 ? "尾大" : "尾小");
//                //1-4球属性
//                addPropertyTitleView("1-4球", 2);
//                for (int i = 0; i < 4; i++) {
//                    int property = Integer.parseInt(numbers.get(i)) - Integer.parseInt(numbers.get(numbers.size() - 1 - i));
//                    addPropertyView(property > 0 ? "龙" : "虎");
//                }
//                break;
//            case Constants.LOTTERY_TYPE.CJ_LOTTERY://重庆时时彩
//            case Constants.LOTTERY_TYPE.ROME_LOTTERY://罗马时时彩
//            case Constants.LOTTERY_TYPE.SPEED_SSC://极速时时彩
//                //总和
//                addPropertyTitleView("总和", 2);
//                addPropertyView(sum);
//                addPropertyView(Integer.parseInt(sum) >= 23 ? "大" : "小");
//                addPropertyView(Integer.parseInt(sum) % 2 == 0 ? "双" : "单");
//                //龙虎
//                addPropertyTitleView("龙虎", 2);
//                int property = Integer.parseInt(numbers.get(0)) - Integer.parseInt(numbers.get(4));
//                if (property > 0) {
//                    addPropertyView("龙");
//                } else if (property < 0) {
//                    addPropertyView("虎");
//                } else {
//                    addPropertyView("和");
//                }
//                break;
//            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY://PC蛋蛋
//                //总和(大小单双)
//                addPropertyTitleView("总和", 0);
//                addPropertyView(sum);
//                addPropertyView(Integer.parseInt(sum) >= 14 ? "大" : "小");
//                addPropertyView(Integer.parseInt(sum) % 2 == 0 ? "双" : "单");
//                break;
//            case Constants.LOTTERY_TYPE.JS_QUICK_3://江苏快3
//                //总和
//                addPropertyTitleView("总和", 2);
//                addPropertyView(sum);
//                if (numbers.get(0).equals(numbers.get(1)) && numbers.get(0).equals(numbers.get(2))) {
//                    addPropertyView("通吃");
//                } else {
//                    addPropertyView(Integer.parseInt(sum) >= 11 ? "大" : "小");
//                }
//                //鱼虾蟹
//                addPropertyTitleView("鱼虾蟹", 2);
//                for (String number : numbers) {
//                    addPropertyView(LotteryUtil.get().getYuXiaXie(number));
//                }
//                break;
        }
    }

    private void addPropertyTitleView(String text, int weight) {
        LayoutParams layoutParams = new LayoutParams(-2, -1, weight);
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.color.gray_f2);
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_normal));
        addView(textView, layoutParams);
    }

    private void addPropertyView(String text) {
        LayoutParams layoutParams = new LayoutParams(-2, -1, 1);
        TextView textView = new TextView(getContext());
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.text_normal));
        addView(textView, layoutParams);
    }

    /**
     * 计算和值
     *
     * @param numbers 开奖号码
     */
    private String sum(List<String> numbers) {
        int sum = 0;
        for (String number : numbers) {
            sum += Integer.valueOf(number);
        }
        return String.valueOf(sum);
    }
}
