package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActiovity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch(view.getId()){
            case R.id.iv_back:
                finish();
        }
    }
}
