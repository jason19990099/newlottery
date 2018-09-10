package com.international.wtw.lottery.adapter;


import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.TransactionRecord;
import com.international.wtw.lottery.utils.DateUtil;

public class TradeRecordAdapter extends BaseQuickAdapter<TransactionRecord.ResBean, BaseViewHolder> {

    public TradeRecordAdapter() {
        super(R.layout.item_record);
    }

    @Override
    protected void convert(BaseViewHolder holder, TransactionRecord.ResBean item) {
        holder.setText(R.id.tv_time, DateUtil.convertDateTime(item.getAddtime()));
        if ("0".equals(item.getType_code())) {
            if ("AG".equals(item.getBank_name())) {
                holder.setText(R.id.tv_amount, item.getBank_account() + "：" + item.getMoney());
            } else {
                holder.setText(R.id.tv_amount, mContext.getResources().getString(R.string.deposit) + item.getMoney());
            }
        } else if ("1".equals(item.getType_code())) {
            if ("AG".equals(item.getBank_name())) {
                holder.setText(R.id.tv_amount, item.getBank_account() + "：" + item.getMoney());
            } else {
                holder.setText(R.id.tv_amount, mContext.getResources().getString(R.string.take_out) + item.getMoney());
            }
        }

        if ("0".equals(item.getStatus())) {
            holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.untreated));
            holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.bet_color_blue));
        } else if ("1".equals(item.getStatus())) {
            if ("0".equals(item.getIs_clear())) {
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.fail));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.red));
            } else if ("1".equals(item.getIs_clear())) {
                holder.setText(R.id.tv_record_status, mContext.getResources().getString(R.string.success));
                holder.setTextColor(R.id.tv_record_status, ContextCompat.getColor(mContext, R.color.gray));
            }
        }
    }
}
