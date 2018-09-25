package com.international.wtw.lottery.fragment.addbankcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.AddcardEvent;
import com.international.wtw.lottery.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AmptyFragment extends NewBaseFragment {

    Unbinder unbinder;
    @BindView(R.id.iv_addbank)
    ImageView ivAddbank;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ampty;
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.iv_addbank)
    public void onViewClicked() {
        LogUtil.e("============000=======");
        AddcardEvent messageEvent = new AddcardEvent();
        EventBus.getDefault().post(messageEvent);
    }


}
