package com.international.wtw.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.WithdrawRecordModel;
import com.international.wtw.lottery.utils.DateUtil;

public class TradeRecordAdapter2 extends BaseQuickAdapter<WithdrawRecordModel.DataBean, BaseViewHolder> {

    public TradeRecordAdapter2() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecordModel.DataBean item) {
        helper.setText(R.id.tv_time, DateUtil.convertDateTime(String.valueOf(item.getCreateTime())));
        helper.setText(R.id.tv_amount, "提现："+item.getAmount()+"元");
        helper.setText(R.id.tv_record_status, item.getWithdrawStatusName());
    }
}
