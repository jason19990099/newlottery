package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.newJason.PersonalModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActiovity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.topTitle)
    RelativeLayout topTitle;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.mine_tv_name)
    TextView mineTvName;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.oer_tv_zh)
    TextView oerTvZh;
    @BindView(R.id.oer_tv_zsxm)
    TextView oerTvZsxm;
    @BindView(R.id.oer_tv_yx)
    TextView oerTvYx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        String token = SharePreferencesUtil.getString(PersonalActiovity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getDetail(this, token, new HttpCallback<PersonalModel>() {
            @Override
            public void onSuccess(PersonalModel data)  {
                oerTvZh.setText(data.getData().getName());
                oerTvZsxm.setText(data.getData().getTrueName());
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(PersonalActiovity.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
