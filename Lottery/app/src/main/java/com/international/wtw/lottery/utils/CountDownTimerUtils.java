package com.international.wtw.lottery.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.international.wtw.lottery.R;

/**
 * Created by XIAOYAN on 2017/10/14.
 */

public class CountDownTimerUtils extends CountDownTimer {
    private Button btn;

    public CountDownTimerUtils(Button btn, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false); //设置不可点击
        btn.setText(millisUntilFinished / 1000 + "秒后发送");  //设置倒计时时间
        btn.setBackgroundResource(R.drawable.bg_identify_code_press); //设置按钮为灰色，这时是不能点击的

        SpannableString spannableString = new SpannableString(btn.getText().toString());  //获取按钮上的文字
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        btn.setText(spannableString);
    }

    @Override
    public void onFinish() {
        btn.setText("获取验证码");
        btn.setClickable(true);//重新获得点击
        btn.setBackgroundResource(R.drawable.bg_identify_code_normal);  //还原背景色
    }
}