package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.SettledOrderAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.SettledOrdersModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SettledOrdersActivity extends NewBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_listdata)
    ListView lvListdata;
    @BindView(R.id.tv_bet_money)
    TextView tvBetMoney;
    @BindView(R.id.tv_win_money)
    TextView tvWinMoney;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settledorders;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        String token = SharePreferencesUtil.getString(this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getTodaySettledOrders(this, token, "", "", 1, 10, new HttpCallback<SettledOrdersModel>() {
            @Override
            public void onSuccess(SettledOrdersModel data) throws Exception {
                SettledOrderAdapter adapter=new  SettledOrderAdapter(SettledOrdersActivity.this,data.getData().getData());
                lvListdata.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(SettledOrdersActivity.this,"请求数据失败.",Toast.LENGTH_SHORT).show();
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
