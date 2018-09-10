package com.international.wtw.lottery.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.OfflinePayModel;
import com.international.wtw.lottery.json.OnlinePayModel;
import com.international.wtw.lottery.json.PayInTitle;

import java.util.List;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class PayWaysAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ONLINE_PAY = 1;
    public static final int TYPE_OFFLINE_PAY = 3;

    public PayWaysAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_TITLE, R.layout.item_pay_title);
        addItemType(TYPE_ONLINE_PAY, R.layout.item_pay_item);
        addItemType(TYPE_OFFLINE_PAY, R.layout.item_pay_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_TITLE:
                PayInTitle item0 = (PayInTitle) item;
                helper.setText(R.id.tv_title, item0.title);
                helper.setText(R.id.tv_sub_title, item0.subTitle);
                break;
            case TYPE_ONLINE_PAY:
                OnlinePayModel item1 = (OnlinePayModel) item;
                helper.setBackgroundRes(R.id.iv_logo, item1.getSmallLogoRes());
                helper.setText(R.id.tv_pay_name, item1.getPayName());
                break;
            case TYPE_OFFLINE_PAY:
                OfflinePayModel item3 = (OfflinePayModel) item;
                helper.setBackgroundRes(R.id.iv_logo, item3.getSmallLogoRes());
                helper.setText(R.id.tv_pay_name, item3.getPayName());
                break;
        }
    }
}