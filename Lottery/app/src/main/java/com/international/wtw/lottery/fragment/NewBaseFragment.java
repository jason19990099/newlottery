package com.international.wtw.lottery.fragment;

import android.support.v4.app.Fragment;
import org.greenrobot.eventbus.EventBus;




public class NewBaseFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
