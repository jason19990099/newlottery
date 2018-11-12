package com.international.wtw.lottery.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.LotteryHistoryModel;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.LotteryPropertyView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

public class LotteryHistoryAdapter extends BaseQuickAdapter<LotteryHistoryModel.DataBean, BaseViewHolder> {

    private String mGameCode;

    public LotteryHistoryAdapter(String gameCode) {
        super(R.layout.item_lottery_history);
        mGameCode = gameCode;
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryHistoryModel.DataBean item) {
        helper.setText(R.id.tv_round, item.getExpectNo());
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        Long time = Long.valueOf(item.getOpenTime() + "000");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        helper.setText(R.id.tv_time, format.format(time));
        if (null== item.getCode())
            return;
        String[] numbers = item.getCode().split(",");
        LotteryNumberView numberView = helper.getView(R.id.lotteryNumberView);
        numberView.setNumbers(mGameCode, Arrays.asList(numbers));
        LotteryPropertyView propertyView = helper.getView(R.id.lotteryPropertyView);
        if (item.getGameCode().equals("msssc")){
            propertyView.setVisibility(View.GONE);
        }else {
            propertyView.setVisibility(View.VISIBLE);
            propertyView.setNumbers(time,mGameCode, Arrays.asList(numbers));
        }

    }
}
