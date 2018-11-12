package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetdetailSettledAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.SettledOrdersModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 今日已結投注明细
 */
public class TodaySettledOrdersActivity extends NewBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_listdata)
    ListView lvListdata;
    @BindView(R.id.tv_bet_money)
    TextView tvBetMoney;
    @BindView(R.id.tv_win_money)
    TextView tvWinMoney;
    private String day;
    private String game_code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betdetai_settled;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        day = getIntent().getStringExtra("day");
        game_code = getIntent().getStringExtra(LotteryId.GAME_CODE);
        String token = SharePreferencesUtil.getString(this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getTodaySettledOrders(this, token, game_code, "", day, day, 1, 999, new HttpCallback<SettledOrdersModel>() {
            @Override
            public void onSuccess(SettledOrdersModel data) {
                BetdetailSettledAdapter adapter = new BetdetailSettledAdapter(TodaySettledOrdersActivity.this, data.getData().getData());
                lvListdata.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tvBetMoney.setText("下注金额:"+data.getData().getAmount());
                tvWinMoney.setText("输赢金额:" +data.getData().getWinamount()+"元");
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(TodaySettledOrdersActivity.this, "请求数据失败，请重试。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }


}
