package com.international.wtw.lottery.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.activity.mine.BetOnRecordActivity;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SizeUtils;

/**
 * Created by XIAOYAN on 2017/11/27.
 */

public class BetPopupWindow extends BaseCustomPopup implements View.OnClickListener {

    private Activity mActivity;
    private View view;
    private TextView tv_today_win, tv_today_no_win;

    public BetPopupWindow(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.popup_bet, SizeUtils.dp2px(100), ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(false)
                .setAnimationStyle(R.style.AnimDown);
    }

    @Override
    protected void initViews(View view) {
        this.view = view;
        tv_today_win = (TextView) view.findViewById(R.id.tv_today_win);
        tv_today_win.setOnClickListener(this);
        tv_today_no_win = (TextView) view.findViewById(R.id.tv_today_no_win);
        tv_today_no_win.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mActivity, BetOnRecordActivity.class);
        switch (v.getId()) {
            case R.id.tv_today_win:
                intent.putExtra("bet_is_win", 1);
                intent.putExtra("tv_today_is_win", tv_today_win.getText().toString());
                if (!tv_today_win.getText().toString().equals("今日已结")) {
                    tv_today_win.setText("今日已结");
                    tv_today_no_win.setText("今日未中");
                } else {
                    tv_today_win.setText("今日中奖");
                    tv_today_no_win.setText("今日未中");
                }
                break;
            case R.id.tv_today_no_win:
                intent.putExtra("bet_is_win", -1);
                intent.putExtra("tv_today_is_win", tv_today_no_win.getText().toString());
                if (!tv_today_no_win.getText().toString().equals("今日已结")) {
                    tv_today_win.setText("今日中奖");
                    tv_today_no_win.setText("今日已结");
                } else {
                    tv_today_win.setText("今日中奖");
                    tv_today_no_win.setText("今日未中");
                }
                break;
        }
        mActivity.startActivity(intent);
        dismiss();
    }
}
