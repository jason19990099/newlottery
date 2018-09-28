package com.international.wtw.lottery.fragment.MarkSix;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * 一肖尾数
 * Created by a bin on 2017/7/13.
 */

public class YxWsFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View viewYxWs;
    private boolean isFeng;
    private List<NewOddsBean> yXwSBeanList;   //请求到的赔率数据
    private List<NewOddsBean.ListBean> yXBeanList, wSBeanList;
    private RecyclerView recycle_sx, recycle_ws;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>();
    private BetColorsItemAdapter adapter1, adapter2;
    private MarkSixActivity sixActivity;
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewYxWs = inflater.inflate(R.layout.view_six_1x, null);
        mFlOddsContainer.addView(viewYxWs);
        initViewYsWs();
        requestOddsInfo(15);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initViewYsWs() {
        recycle_sx = (RecyclerView) viewYxWs.findViewById(R.id.recycle_sx);
        recycle_ws = (RecyclerView) viewYxWs.findViewById(R.id.recycle_ws);
    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo(15);
    }

    private void requestOddsInfo(int type_code) {
//        requestOdds(mActivity.getLotteryType(), type_code);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        yXwSBeanList = data;
        setupYsWsOddsView(yXwSBeanList);
        showOdds();
    }

    /**
     * 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupYsWsOddsView(List<NewOddsBean> currentOddBeans) { //, int position
        if (currentOddBeans == null || null == recycle_sx || null == getActivity())
            return;
        yXBeanList = new ArrayList<>();
        wSBeanList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            yXBeanList.add(currentOddBeans.get(0).getList().get(i));
        }
        for (int i = 12; i < currentOddBeans.get(0).getList().size(); i++) {
            wSBeanList.add(currentOddBeans.get(0).getList().get(i));
        }

        if (null == adapter1) {
            adapter1 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, yXBeanList, recycle_sx, false, isFeng, this);
            recycle_sx.setLayoutManager(new GridLayoutManager(YxWsFragment.this.getActivity(), 4));
            recycle_sx.setAdapter(adapter1);
        } else {
            adapter1.refreshData(yXBeanList);
        }

        if (null == adapter2) {
            adapter2 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, wSBeanList, recycle_ws, false, isFeng, this);
            recycle_ws.setLayoutManager(new GridLayoutManager(YxWsFragment.this.getActivity(), 4));
            GridLayoutManager layoutManager = new GridLayoutManager(YxWsFragment.this.getContext(), 4);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position > 7 ? 2 : 1;
                }
            });
            recycle_ws.setLayoutManager(layoutManager);
            recycle_ws.setAdapter(adapter2);
        } else {
            adapter2.refreshData(wSBeanList);
        }
        recycle_ws.setFocusable(false);
        recycle_sx.setFocusable(false);
    }

    /**
     * 查找选中状态的BetItem
     * 如果是两面盘，直接查找otherOddBeans
     * 如果不是两面盘，查找pointOddsBeanInfo里面的选中的Item
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans() {
        return findTheSelectedBeans(yXwSBeanList);
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

    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(15));
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
        selectedBeans = findTheSelectedBeans();
        this.sixActivity.setMoney(bet_money);

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

        if (adapter1 == null || adapter2 == null)
            return;
        adapter1.refreshData(yXBeanList,isFeng);
        adapter2.refreshData(wSBeanList,isFeng);

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

    @Override
    public void open(Boolean b) {
        isFeng = b;
        clearBettingSelect();
    }

    @Override
    public void close(Boolean b) {
        isFeng = b;
        clearBettingSelect();
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

}
