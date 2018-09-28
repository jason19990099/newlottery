package com.international.wtw.lottery.fragment.MarkSix;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.MarkSixActivity;
import com.international.wtw.lottery.adapter.BetColorsItemAdapter;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.RecyclerViewDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.fragment.BetBaseFragment;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.listener.StateInterface;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by A Bin on 2017/7/13.
 * 正码 1-6
 */

public class ZeMaSixFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View viewZMa6;
    private boolean isFeng;
    private List<NewOddsBean> zeMaSixBeanList;   //请求到的赔率数据
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private BetColorsItemAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6;
    private RecyclerView recycle_view1, recycle_view2, recycle_view3, recycle_view4, recycle_view5, recycle_view6;
    private MarkSixActivity sixActivity;
    private List<View> zm6TopItemView = new ArrayList<>();
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewZMa6 = inflater.inflate(R.layout.view_six_zm_1_6, null);
        mFlOddsContainer.addView(viewZMa6);
        initViewZeMa();
        requestOddsInfo(11);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initViewZeMa() {
        zm6TopItemView.clear();
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_one));
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_two));
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_three));
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_four));
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_five));
        zm6TopItemView.add(viewZMa6.findViewById(R.id.ly_zm_six));

    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo(11);
    }

    private void requestOddsInfo(int type_code) {
//        requestOdds(mActivity.getLotteryType(), type_code);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        zeMaSixBeanList = data;
        setupZeMaOddsView(zeMaSixBeanList);
        showOdds();
    }

    /**
     * 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupZeMaOddsView(List<NewOddsBean> currentOddBeans) { //, int position
        if (currentOddBeans == null || zm6TopItemView == null || null == getActivity())
            return;

        int size = zm6TopItemView.size();
        for (int i = 0; i < size; i++) {
            View topItemView = zm6TopItemView.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            TextView btn = (TextView) topItemView.findViewById(R.id.tv_ball_name);
            btn.setText(oneBean.getName());
            if (i == 0) {
                if (null == adapter1) {
                    recycle_view1 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_one);
                    adapter1 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view1, false, isFeng, this);
                    recycle_view1.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view1.setAdapter(adapter1);
                } else {
                    adapter1.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view1.setFocusable(false);
            } else if (i == 1) {
                if (null == adapter2) {
                    recycle_view2 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_two);
                    adapter2 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view2, false, isFeng, this);
                    recycle_view2.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view2.setAdapter(adapter2);
                } else {
                    adapter2.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view2.setFocusable(false);
            } else if (i == 2) {
                if (null == adapter3) {
                    recycle_view3 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_three);
                    adapter3 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view3, false, isFeng, this);
                    recycle_view3.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view3.setAdapter(adapter3);
                } else {
                    adapter3.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view3.setFocusable(false);
            } else if (i == 3) {
                if (null == adapter4) {
                    recycle_view4 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_four);
                    adapter4 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view4, false, isFeng, this);
                    recycle_view4.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view4.setAdapter(adapter4);
                } else {
                    adapter4.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view4.setFocusable(false);
            } else if (i == 4) {
                if (null == adapter5) {
                    recycle_view5 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_five);
                    adapter5 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view5, false, isFeng, this);
                    recycle_view5.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view5.setAdapter(adapter5);
                } else {
                    adapter5.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view5.setFocusable(false);
            } else if (i == 5) {
                if (null == adapter6) {
                    recycle_view6 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_six);
                    adapter6 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view6, false, isFeng, this);
                    recycle_view6.setLayoutManager(new LinearLayoutManager(ZeMaSixFragment.this.getActivity()));
                    recycle_view6.setAdapter(adapter6);
                } else {
                    adapter6.refreshData(oneBean.getList(),isFeng);
                }
                recycle_view6.setFocusable(false);
            }
        }
    }

    /**
     * 查找选中状态的BetItem
     * 如果是两面盘，直接查找otherOddBeans
     * 如果不是两面盘，查找pointOddsBeanInfo里面的选中的Item
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans() {
        return findTheSelectedBeans(zeMaSixBeanList);
    }

    /**
     * 查找选中状态的BetItem
     *
     * @param topItemBeans
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans(List<NewOddsBean> topItemBeans) {
        List<NewOddsBean.ListBean> betItemBeans = new ArrayList<>();
        if (null != topItemBeans) {
            for (int i = 0; i < topItemBeans.size(); i++) {
                NewOddsBean oneTopBean = topItemBeans.get(i);
                String name = oneTopBean.getName();
                for (int j = 0; j < oneTopBean.getList().size(); j++) {
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
    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(11));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), "round", ""));
        //选中的
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean oneBetItem = selectedBeans.get(i);
            betParams.put(oneBetItem.getKey(), bet_money);
        }
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.sixActivity = (MarkSixActivity) context;
    }

    public void getBet() {
        if (null == getActivity())
            return;
        bet_money = SharePreferencesUtil.getString(getActivity(), LotteryId.LOTTERY_BET_MONEY, "0");
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        this.sixActivity.setMoney(bet_money);
        selectedBeans = findTheSelectedBeans();
        if (selectedBeans == null || selectedBeans.size() == 0) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
            return;
        }

        mDialog = new RecyclerViewDialog(getActivity(), selectedBeans, bet_money, new RecyclerViewDialog.SureCallBack() {
            @Override
            public void onSure() {
                getBettingData();
                clearBettingSelect();
            }

            @Override
            public void onCancel() {
                clearBettingSelect();
            }
        });
        mDialog.show();
    }

    /**
     * 清除选中
     */
    public void clearBettingSelect() {
        selectedBeans = findTheSelectedBeans();
        if (null == selectedBeans)
            return;
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean listBean = selectedBeans.get(i);
            listBean.setSelectedState(false);
            selectedBeans.clear();
        }
        if (adapter1 == null || adapter2 == null || adapter3 == null || adapter4 == null || adapter5 == null || adapter6 == null)
            return;
        adapter1.refreshData(zeMaSixBeanList.get(0).getList(),isFeng);
        adapter2.refreshData(zeMaSixBeanList.get(1).getList(),isFeng);
        adapter3.refreshData(zeMaSixBeanList.get(2).getList(),isFeng);
        adapter4.refreshData(zeMaSixBeanList.get(3).getList(),isFeng);
        adapter5.refreshData(zeMaSixBeanList.get(4).getList(),isFeng);
        adapter6.refreshData(zeMaSixBeanList.get(5).getList(),isFeng);

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser) {
            clearBettingSelect();
        }

    }

    @Override
    public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
        int size = findTheSelectedBeans().size();
        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(size);
        }
    }

    /**
     * 开盘
     */
    @Override
    public void open(Boolean b) {
        isFeng = b;
        clearBettingSelect();
    }

    /**
     * 封盘
     */
    @Override
    public void close(Boolean b) {
        isFeng = b;
        clearBettingSelect();
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
