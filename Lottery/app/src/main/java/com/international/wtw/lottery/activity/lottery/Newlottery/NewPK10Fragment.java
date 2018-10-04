package com.international.wtw.lottery.activity.lottery.Newlottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.international.wtw.lottery.fragment.NewBaseFragment1;

public class NewPK10Fragment extends NewBaseFragment1 {
    private PK10Rate.DataBean.ListPlayGroupBean listPlayGroupBean;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LogUtil.e("======onCreateView=============");
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("======onCreate=============");
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtil.e("======onStart=============");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Pk10RateEvent pk10RateEvent) {
        LogUtil.e("==============threadMode================");
        //找到兩面盤的數據
        int size = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().size();
        for (int i = 0; i < size; i++) {
            if (pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i).getCode().equals("liangmianpan")) {
                listPlayGroupBean = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i);
            }
        }

//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                PK10adapter adapter = new PK10adapter(getActivity(), listPlayGroupBean);
//                lvItem.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        });



    }

}
