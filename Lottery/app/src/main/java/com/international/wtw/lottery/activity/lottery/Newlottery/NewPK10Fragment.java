package com.international.wtw.lottery.activity.lottery.Newlottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PK10adapter;
import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.fragment.NewBaseFragment;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NewPK10Fragment extends NewBaseFragment {
    @BindView(R.id.lv_item)
    ListView lvItem;
    Unbinder unbinder;
    private View view;
    private PK10Rate.DataBean.ListPlayGroupBean listPlayGroupBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_pk10, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Pk10RateEvent pk10RateEvent) {
        //找到兩面盤的數據
        int size = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().size();
        for (int i = 0; i < size; i++) {
            if (pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i).getCode().equals("liangmianpan")) {
                listPlayGroupBean = pk10RateEvent.getPk10Rate().getData().get(0).getListPlayGroup().get(i);
            }
        }

        PK10adapter adapter = new PK10adapter(getActivity(), listPlayGroupBean);
        lvItem.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        setListViewHeightBasedOnChildren(lvItem);
    }


    /**
     * @param listView
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
