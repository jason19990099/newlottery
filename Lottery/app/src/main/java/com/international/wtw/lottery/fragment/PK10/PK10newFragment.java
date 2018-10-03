package com.international.wtw.lottery.fragment.PK10;
import android.widget.ListView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.Pk10RateEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class PK10newFragment extends NewBaseFragment {
    @BindView(R.id.lv_item)
    ListView lvItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pk10;
    }
    @Override
    protected void initView() {

    }
    @Override
    protected void initData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Pk10RateEvent pk10RateEvent) {
        int size = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(0).getListPlay().size();
        int size2=pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().size();




    }


}
