package com.international.wtw.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.HistoryResult;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.LotteryPropertyView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;

public class LotteryHistoryAdapter extends BaseQuickAdapter<HistoryResult.ResultBean, BaseViewHolder> {

    private int mGameCode;

    public LotteryHistoryAdapter(int gameCode) {
        super(R.layout.item_lottery_history);
        mGameCode = gameCode;
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryResult.ResultBean item) {
        helper.setText(R.id.tv_round, item.getRound());
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.CHINA);
        Long time = Long.valueOf(item.getEndtime() + "000");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        helper.setText(R.id.tv_time, format.format(time));
        String[] numbers = item.getNumber().split(",");
        LotteryNumberView numberView = helper.getView(R.id.lotteryNumberView);
        numberView.setNumbers(mGameCode, Arrays.asList(numbers));
        LotteryPropertyView propertyView = helper.getView(R.id.lotteryPropertyView);
        propertyView.setNumbers(time,mGameCode, Arrays.asList(numbers));
    }
}
