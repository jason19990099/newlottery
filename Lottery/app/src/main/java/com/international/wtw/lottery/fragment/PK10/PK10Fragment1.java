package com.international.wtw.lottery.fragment.PK10;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetItemAdapter;
import com.international.wtw.lottery.adapter.PK10adapter;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomListView;
import com.international.wtw.lottery.dialog.RecyclerViewDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.fragment.BetBaseFragment;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.listener.StateInterface;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * PK1O两面盘
 */

public class PK10Fragment1 extends BetBaseFragment implements
        BetItemAdapter.ItemBettingCallback, StateInterface {

    @BindView(R.id.lv_item)
    ListView lvItem;
    Unbinder unbinder;
    private View view;
    private boolean IsFeng;
    private BetItemAdapter adapter;
    private List<View> lmpTopItemView = new ArrayList<>();    //两面盘
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();//选中的数据
    private List<NewOddsBean> currentOddBeans = new ArrayList<>();  //请求到的赔率数据
    private RecyclerViewDialog mDialog;

    private PK10Rate.DataBean.ListPlayGroupBean listPlayGroupBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.activity_pk10, null);
        mFlOddsContainer.addView(view);
        InitViewLmp();
        getData();
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 两面盘
     */
    private void InitViewLmp() {
//        lmpTopItemView.clear();
//        lmpTopItemView.add(view.findViewById(R.id.layout_one));
//        lmpTopItemView.add(view.findViewById(R.id.layout_two));
//        lmpTopItemView.add(view.findViewById(R.id.layout_three));
//        lmpTopItemView.add(view.findViewById(R.id.layout_four));
//        lmpTopItemView.add(view.findViewById(R.id.layout_five));
//        lmpTopItemView.add(view.findViewById(R.id.layout_six));
//        lmpTopItemView.add(view.findViewById(R.id.layout_seven));
//        lmpTopItemView.add(view.findViewById(R.id.layout_eight));
//        lmpTopItemView.add(view.findViewById(R.id.layout_nine));
//        lmpTopItemView.add(view.findViewById(R.id.layout_ten));

    }

    @Override
    protected void refreshOdds() {
        getData();
    }

    private void getData() {
//        requestOdds(mActivity.getLotteryType(), Constants.PK_10_PLAY_TYPE_SERVER.DOUBLE_SIDE);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
//        currentOddBeans = data;
//        showOdds();
//        setupLmpOddsView(lmpTopItemView, currentOddBeans);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        } else {
            clearbettingseleter();
        }
    }


    /**
     * 两面盘赔率设置
     *
     * @param lmpTopItemViews
     * @param currentOddBeans
     */
    private void setupLmpOddsView(List<View> lmpTopItemViews, List<NewOddsBean> currentOddBeans) {
        if (null == currentOddBeans || null == lmpTopItemViews || null == getActivity()) {
            return;
        }
        int size = lmpTopItemViews.size();
        int size2 = currentOddBeans.size();
        //数据不对应的时间会导致空指针
        if (size != size2) {
            return;
        }
        for (int i = 0; i < size; i++) {
            View topItemView = lmpTopItemViews.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            if (null != getActivity()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView btn = (TextView) topItemView.findViewById(R.id.top_item_name);
                        btn.setText(oneBean.getName());
                        CustomListView listView = (CustomListView) topItemView.findViewById(R.id.listViewItems);
                        adapter = new BetItemAdapter(oneBean.getList(), getActivity(), PK10Fragment1.this, IsFeng);
                        listView.setAdapter(adapter);
                        listView.setFocusable(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        }
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

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PK10adapter adapter = new PK10adapter(getActivity(), listPlayGroupBean);
                lvItem.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });



    }


    /**
     * 查找选中状态的BetItem
     *
     * @param topItembeans
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans2(List<NewOddsBean> topItembeans) {
        List<NewOddsBean.ListBean> betItemBeans = new ArrayList<>();
        if (null != topItembeans) {
            int size = topItembeans.size();
            for (int i = 0; i < size; i++) {
                NewOddsBean oneTopBean = topItembeans.get(i);
                String name = oneTopBean.getName();
                int oneTopBeanSize = oneTopBean.getList().size();
                for (int j = 0; j < oneTopBeanSize; j++) {
                    NewOddsBean.ListBean oneBetItem = oneTopBean.getList().get(j);
                    oneBetItem.setType_name(name);
                    if (oneBetItem.getSelectedState()) {
                        betItemBeans.add(oneBetItem);
                    }
                }
            }
        }

        return betItemBeans;
    }

    /**
     * 投注
     */
    public void getBet() {
        if (null == getActivity())
            return;
        bet_money = SharePreferencesUtil.getString(getActivity(), LotteryId.LOTTERY_BET_MONEY, "0");
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        LotteryId.BETTING_MONEY = bet_money;
        selectedBeans = findTheSelectedBeans();
        if (selectedBeans == null || selectedBeans.size() == 0) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
            return;
        }

        mDialog = new RecyclerViewDialog(getActivity(), selectedBeans, bet_money, new RecyclerViewDialog.SureCallBack() {
            @Override
            public void onSure() {
                getBettingData();
                clearbettingseleter();
            }

            @Override
            public void onCancel() {
                clearbettingseleter();
            }
        });
        mDialog.show();
    }

    public void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(Constants.PK_10_PLAY_TYPE_SERVER.DOUBLE_SIDE));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), LotteryId.ROUND, ""));
        //选中的
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean oneBetItem = selectedBeans.get(i);
            betParams.put(oneBetItem.getKey(), bet_money);
        }
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        LogUtil.e("=====请求参数是=====" + (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    /**
     * 查找选中状态的BetItem
     * 如果是两面盘，直接查找otherOddBeans
     * 如果不是两面盘，查找pointOddsBeanInfo里面的选中的Item
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans() {
        return findTheSelectedBeans2(currentOddBeans);
    }

    /**
     * 清除选中
     */
    public void clearbettingseleter() {
//        selectedBeans = findTheSelectedBeans();
//        int selectSize = selectedBeans.size();
//        if (selectSize > 0) {
//            for (int i = 0; i < selectSize; i++) {
//                NewOddsBean.ListBean listBean = selectedBeans.get(i);
//                listBean.setSelectedState(false);
//            }
//            selectedBeans.clear();
//            setupLmpOddsView(lmpTopItemView, currentOddBeans);
//        }
//
//        if (null != getActivity()) {
//            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
//        }

    }

    /**
     * 开封盘刷新界面
     */
    private void openAndCloseInitView() {
//        clearbettingseleter();
//        setupLmpOddsView(lmpTopItemView, currentOddBeans);
    }


    @Override
    public void ItemClick() {
//        int size = findTheSelectedBeans().size();
//
//        if (null != getActivity()) {
//            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(size);
//        }

    }


    @Override
    public void open(Boolean b) {
        IsFeng = b;
        openAndCloseInitView();
    }

    @Override
    public void close(Boolean b) {
        IsFeng = b;
        openAndCloseInitView();
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissDialog();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
