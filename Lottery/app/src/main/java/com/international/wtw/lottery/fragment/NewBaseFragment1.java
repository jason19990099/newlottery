package com.international.wtw.lottery.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.international.wtw.lottery.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

public class NewBaseFragment1 extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("======onCreateView=======onCreate======");
        EventBus.getDefault().register(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        LogUtil.e("======onCreateView=======onDestroy======");
    }
}
