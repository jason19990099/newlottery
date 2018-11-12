package com.international.wtw.lottery.fragment.bjscpk10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PK10adapter;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.event.OpenAndClosedEvent;
import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.fragment.NewBaseFragment;
import com.international.wtw.lottery.newJason.PK10RateModel;
import com.international.wtw.lottery.utils.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PK10No6to10Fragment extends NewBaseFragment {
    @BindView(R.id.lv_item)
    ListView lvItem;
    Unbinder unbinder;
    private View view;
    private PK10RateModel.DataBean.ListPlayGroupBean listPlayGroupBean;
    private PK10adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pk10, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessage(Pk10RateEvent pk10RateEvent) {
        //找到兩面盤的數據
        int size = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().size();
        for (int i = 0; i < size; i++) {
            if (pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i).getCode().equals("no6~10")
                    ||pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i).getCode().equals("douniu")) {
                listPlayGroupBean = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i);
            }
        }

        adapter = new PK10adapter(getActivity(), listPlayGroupBean,false);
        lvItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEvent(OpenAndClosedEvent event) {
        if (event.getGameCode().equals(LotteryId.BJSCPK10)||event.getGameCode().equals(LotteryId.Miaosufeiting)
                ||event.getGameCode().equals(LotteryId.MiaosuSaiche)||event.getGameCode().equals(LotteryId.Miaosusscai)) {
            if (event.isClearSelect()){
                int size=listPlayGroupBean.getListPlay().size();
                for (int i=0;i<size;i++){
                    int size2=listPlayGroupBean.getListPlay().get(i).getListPlayRate().size();
                    for (int m=0;m<size2;m++){
                        listPlayGroupBean.getListPlay().get(i).getListPlayRate().get(m).setSelect(false);
                    }
                }
            }
            adapter=new PK10adapter(getActivity(), listPlayGroupBean,event.isClosed());
            lvItem.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
