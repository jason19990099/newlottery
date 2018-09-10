package com.international.wtw.lottery.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.GameModel;

import java.util.List;
/**
 * Created by Yuan on 2017/10/6.
 * 描述：
 */

public class HomeMiddleAdapter extends BaseQuickAdapter<GameModel, BaseViewHolder> {

    public HomeMiddleAdapter(List<GameModel> list) {
        super(R.layout.item_home_game, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameModel item) {
        helper.setBackgroundRes(R.id.iv_game_logo, item.logoResId);
        helper.setText(R.id.tv_game_name, item.gameName);
    }
}
