package com.international.wtw.lottery.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.BetTypeItem;

import java.util.List;


public class BetTypeRecyclerAdapter extends BaseMultiItemQuickAdapter<BetTypeItem, BaseViewHolder> {

    private boolean isClosed;

    public BetTypeRecyclerAdapter(@Nullable List<BetTypeItem> data) {
        super(data);
        addItemType(BetTypeItem.TYPE_NORMAL, R.layout.item_type_title);
        addItemType(BetTypeItem.TYPE_COMBO, R.layout.item_lm_type);
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, BetTypeItem item) {
        helper.setBackgroundRes(R.id.fy_type, item.isSelected() ? R.drawable.bg_selected_type : R.drawable.bg_normal_item);
        helper.setVisible(R.id.img_mark, item.isSelected());
        helper.setText(R.id.tv_type_name, item.getTypeName());
        if (item.getItemType() == BetTypeItem.TYPE_COMBO) {
            helper.setText(R.id.tv_type_code, isClosed || TextUtils.isEmpty(item.getOdds()) ? "--" : item.getOdds());
        }
    }
}
