package com.international.wtw.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.PayinRecordMoudel;
import com.international.wtw.lottery.utils.DateUtil;

public class TradeRecordAdapter extends BaseQuickAdapter<PayinRecordMoudel.DataBean, BaseViewHolder> {

    public TradeRecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayinRecordMoudel.DataBean item) {
        helper.setText(R.id.tv_time, DateUtil.convertDateTime(String.valueOf(item.getCreateTime())));
        helper.setText(R.id.tv_amount, "存入："+item.getAmount()+"元");
        helper.setText(R.id.tv_record_status, item.getRechargeStatusName());
    }
}
