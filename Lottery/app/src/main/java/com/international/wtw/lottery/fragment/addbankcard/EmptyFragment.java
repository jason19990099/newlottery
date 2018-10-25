package com.international.wtw.lottery.fragment.addbankcard;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.AddcardEvent;
import org.greenrobot.eventbus.EventBus;
import butterknife.OnClick;

public class EmptyFragment extends NewBaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ampty;
    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.iv_addbank)
    public void onViewClicked() {
        AddcardEvent messageEvent = new AddcardEvent();
        EventBus.getDefault().post(messageEvent);
    }
}
