package com.international.wtw.lottery.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.json.PromotionModel;
import com.squareup.picasso.Picasso;

public class HomeBottomAdapter extends BaseQuickAdapter<PromotionModel.PromotionItem, BaseViewHolder> {

    public HomeBottomAdapter() {
        super(R.layout.item_home_promotion);
    }

    @Override
    protected void convert(BaseViewHolder helper, PromotionModel.PromotionItem item) {
        ImageView view = helper.getView(R.id.iv_promotion);
        Picasso.with(view.getContext())
                .load(Constants.BASE_URL + item.image)
                .into(view);
        helper.setText(R.id.tv_desc, item.text);
    }
}
