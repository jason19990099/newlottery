package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetdatasAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.BetrecordBydateModel;
import com.international.wtw.lottery.newJason.WeekdateModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.OnClick;


public class BetrecordActivity extends NewBaseActivity {
    @BindView(R.id.lv_listdata)
    ListView lvListdata;
    @BindView(R.id.tv_bet_money)
    TextView tvBetMoney;
    @BindView(R.id.tv_win_money)
    TextView tvWinMoney;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private int betMoney;
    BigDecimal totalWinmoney = new BigDecimal(0);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yijie;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        String token = SharePreferencesUtil.getString(this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getBetreportWeekdate(this, token, new HttpCallback<WeekdateModel>() {
            @Override
            public void onSuccess(WeekdateModel data) {
                getDataBytime(token, data.getData().get(data.getData().size() - 1), data.getData().get(0));
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(BetrecordActivity.this, "请求数据失败，请重试！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getDataBytime(String token, String beginTime, String endTime) {
        HttpRequest.getInstance().getBetReportByDate(this, token, beginTime, endTime, new HttpCallback<BetrecordBydateModel>() {
            @Override
            public void onSuccess(BetrecordBydateModel data) {
                if (data.getData().size()==0){
                    llTitle.setVisibility(View.GONE);
                    ivEmpty.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                }else{
                    llTitle.setVisibility(View.VISIBLE);
                    ivEmpty.setVisibility(View.GONE);
                    llBottom.setVisibility(View.VISIBLE);
                    BetdatasAdapter adapter = new BetdatasAdapter(BetrecordActivity.this, data.getData());
                    lvListdata.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    int size = data.getData().size();
                    for (int i = 0; i < size; i++) {
                        betMoney += data.getData().get(i).getAmount();
                        totalWinmoney = totalWinmoney.add(new BigDecimal(String.valueOf(data.getData().get(i).getWinLoseAmount())));
                    }
                    tvBetMoney.setText("下注金额:" + betMoney);
                    tvWinMoney.setText("输赢金额:" + totalWinmoney);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

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
