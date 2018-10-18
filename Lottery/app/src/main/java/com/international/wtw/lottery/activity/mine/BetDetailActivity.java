package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetdetailAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.newJason.BetDetailModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 下注明细
 */
public class BetDetailActivity extends NewBaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_listdata)
    ListView lvListdata;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betdetail;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        String day = getIntent().getStringExtra("day");
        String token = SharePreferencesUtil.getString(this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getBetReportByDay(this, token, day, new HttpCallback<BetDetailModel>() {
            @Override
            public void onSuccess(BetDetailModel data) {
                BetdetailAdapter adapter=new BetdetailAdapter(BetDetailActivity.this,data.getData());
                lvListdata.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(BetDetailActivity.this,"请求数据失败，请重试！",Toast.LENGTH_SHORT).show();
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
