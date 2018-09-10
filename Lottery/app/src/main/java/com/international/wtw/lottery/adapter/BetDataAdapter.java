package com.international.wtw.lottery.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.OddsItem;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.HashSet;
import java.util.List;

public class BetDataAdapter extends BaseMultiItemQuickAdapter<MultiBetItem, BaseViewHolder> {

    private int gameCode;
    private boolean isClosed;
    private HashSet<Integer> selectPositions = new HashSet<>();

    public BetDataAdapter(int gameCode, List<MultiBetItem> data) {
        super(data);
        this.gameCode = gameCode;
        addItemType(MultiBetItem.TITLE, R.layout.item_bet_data_title);
        addItemType(MultiBetItem.CONTENT_TEXT, R.layout.item_bet_content_text);
        addItemType(MultiBetItem.CONTENT_NUM, R.layout.item_bet_content_num);
        addItemType(MultiBetItem.CONTENT_LIAN, R.layout.item_bet_content_num);
        addItemType(MultiBetItem.CONTENT_MULTI_NUM, R.layout.item_bet_content_multi_num);
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBetItem item) {
        switch (item.getItemType()) {
            case MultiBetItem.TITLE://标题
                helper.setText(R.id.tv_bet_title, item.getTypeName());
                break;
            case MultiBetItem.CONTENT_TEXT:
                OddsItem betItem = item.getBetItem();
                helper.setText(R.id.tv_item_name, betItem.getName());
                setData(helper, item);
                break;
            case MultiBetItem.CONTENT_NUM:
                setData(helper, item);
                if (gameCode != Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY
                        && gameCode != Constants.LOTTERY_TYPE.PJ_PK_10
                        && gameCode != Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY
                        && gameCode != Constants.LOTTERY_TYPE.JS_QUICK_3) {
                    helper.setText(R.id.tv_item_name, item.getBetItem().getName());
                }
                helper.setBackgroundRes(R.id.tv_item_name, LotteryUtil.get().getBackgroundRes(gameCode, item.getBetItem().getName()));
                break;
            case MultiBetItem.CONTENT_LIAN://连码的选项
                helper.getView(R.id.tv_item_odds).setVisibility(View.GONE);
                if (gameCode != Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY) {
                    helper.setText(R.id.tv_item_name, String.valueOf(item.getNumber()));
                }
                if (item.getIsSelected()) {
                    selectPositions.add(helper.getAdapterPosition());
                    helper.getView(R.id.ll_bet_item).setSelected(true);
                } else {
                    if (selectPositions.contains(helper.getAdapterPosition())) {
                        selectPositions.remove(helper.getAdapterPosition());
                    }
                    helper.getView(R.id.ll_bet_item).setSelected(false);
                }
                helper.setBackgroundRes(R.id.tv_item_name, LotteryUtil.get().getBackgroundRes(gameCode, String.valueOf(item.getNumber())));
                break;
            case MultiBetItem.CONTENT_MULTI_NUM:
                setData(helper, item);
                LinearLayout llNumberContainer = helper.getView(R.id.ll_number_container);
                llNumberContainer.removeAllViews();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(24), SizeUtils.dp2px(24));
                layoutParams.setMargins(SizeUtils.dp2px(1), SizeUtils.dp2px(1), SizeUtils.dp2px(1), SizeUtils.dp2px(1));
                String[] numbers = item.getBetItem().getName().split(",");
                for (String number : numbers) {
                    TextView textView = new TextView(mContext);
                    textView.setTextSize(17);
                    textView.setTextColor(Color.WHITE);
                    textView.setGravity(Gravity.CENTER);
                    //textView.setText(number);
                    textView.setBackgroundResource(LotteryUtil.get().getBackgroundRes(gameCode, number));
                    llNumberContainer.addView(textView, layoutParams);
                }
                break;
        }
    }

    private void setData(BaseViewHolder helper, MultiBetItem item) {
        OddsItem betItem = item.getBetItem();
        //helper.setText(R.id.tv_item_name, betItem.getName());
        if (isClosed) {
            helper.setText(R.id.tv_item_odds, "--");
            if (item.getIsSelected()) {
                item.setIsSelected(false);
            }
        } else {
            helper.setText(R.id.tv_item_odds, betItem.getOdds());
        }
        if (item.getIsSelected()) {
            selectPositions.add(helper.getAdapterPosition());
            helper.getView(R.id.ll_bet_item).setSelected(true);
        } else {
            if (selectPositions.contains(helper.getAdapterPosition())) {
                selectPositions.remove(helper.getAdapterPosition());
            }
            helper.getView(R.id.ll_bet_item).setSelected(false);
        }
    }

    public void clearSelect() {
        for (Integer position : selectPositions) {
            MultiBetItem item = getItem(position);
            if (item != null) {
                item.setIsSelected(false);
                notifyItemChanged(position, 0);
            }
        }
    }
}
