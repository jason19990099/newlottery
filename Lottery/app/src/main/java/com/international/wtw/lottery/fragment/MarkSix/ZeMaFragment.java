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
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.MarkSixActivity;
import com.international.wtw.lottery.adapter.BetColorsItemAdapter;
import com.international.wtw.lottery.adapter.MarkSixAdapter;
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
 * 正码
 * Created by a bin on 2017/7/12.
 */

public class ZeMaFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View view;
    private boolean isFeng;
    private List<NewOddsBean> zeMaABeanList, zeMaBBeanList;   //请求到的赔率数据
    private int currentPlayTypeCode = 3;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private BetColorsItemAdapter adapter1, adapter2;
    private RecyclerView recycle_te_a, recycle_te_b;
    private MarkSixActivity sixActivity;
    private List<View> teMaTopItemView = new ArrayList<>();
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_six_tm, null);
        mFlOddsContainer.addView(view);
        initViewZeMa();
        requestOddsInfo(3);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initViewZeMa() {
        teMaTopItemView.clear();
        teMaTopItemView.add(view.findViewById(R.id.ly_tm_one));
        teMaTopItemView.add(view.findViewById(R.id.ly_tm_two));
        RecyclerView recycle_tm;
        ArrayList<NewOddsBean.ListBean> zmTypeList = new ArrayList<>();
        recycle_tm = (RecyclerView) view.findViewById(R.id.recycle_tm);
        zmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.z_a), "3", true));
        zmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.z_b), "4", false));

        MarkSixAdapter adapter2 = new MarkSixAdapter(this.getActivity(), R.layout.item_type_title, zmTypeList, recycle_tm);
        recycle_tm.setLayoutManager(new GridLayoutManager(this.getActivity(), 2));
        recycle_tm.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetChanged();
                switch (position) {
                    case 0:
                        currentPlayTypeCode = 3;
                        setupZeMaOddsView(zeMaABeanList);
                        break;
                    case 1:
                        currentPlayTypeCode = 4;
                        if (null == zeMaBBeanList || zeMaBBeanList.size() == 0) {
                            requestOddsInfo(4);
                        } else {
                            setupZeMaOddsView(zeMaBBeanList);
                        }
                        break;
                }
            }
        });


        currentPlayTypeCode = 3;
    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo(currentPlayTypeCode);
    }

    private void requestOddsInfo(int type_code) {
//        requestOdds(mActivity.getLotteryType(), type_code);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        switch (currentPlayTypeCode) {
            case 3:
                zeMaABeanList = data;
                setupZeMaOddsView(zeMaABeanList);
                break;
            case 4:
                zeMaBBeanList = data;
                setupZeMaOddsView(zeMaBBeanList);
        }
        showOdds();
    }

    /**
     * 特码/正码/正码特 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupZeMaOddsView(List<NewOddsBean> currentOddBeans) { //, int position
        if (currentOddBeans == null || teMaTopItemView == null || null == getActivity())
            return;
        int size = teMaTopItemView.size();
        int size2 = currentOddBeans.size();
        //数据不对应的时间会导致空指针
        if (size != size2) {
            return;
        }
        for (int i = 0; i < size; i++) {
            View topItemView = teMaTopItemView.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            TextView btn = (TextView) topItemView.findViewById(R.id.tv_ball_name);
            btn.setText(oneBean.getName());
            if (i == 0) {
                if (null == adapter1) {
                    recycle_te_a = (RecyclerView) topItemView.findViewById(R.id.recycle_te);
                    adapter1 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_te_a, true, isFeng, this);
                    GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 20);
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (position > 43 && position < 49) ? 4 : 5;
                        }
                    });
                    recycle_te_a.setLayoutManager(layoutManager);
                    recycle_te_a.setAdapter(adapter1);
                } else {
                    adapter1.refreshData(oneBean.getList(), isFeng);
                }
                recycle_te_a.setFocusable(false);
            }
            if (i == 1) {
                if (null == adapter2) {
                    recycle_te_b = (RecyclerView) topItemView.findViewById(R.id.recycle_dx_ds);
                    adapter2 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_te_b, false, isFeng, this);
                    GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 20);
                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return (position > 11 && position < 17) ? 4 : 5;
                        }
                    });
                    recycle_te_b.setLayoutManager(layoutManager);
                    recycle_te_b.setAdapter(adapter2);
                } else {
                    adapter2.refreshData(oneBean.getList(), isFeng);
                }
                recycle_te_b.setFocusable(false);
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
        List<NewOddsBean.ListBean> beans = null;
        switch (currentPlayTypeCode) {
            case 3:
                beans = findTheSelectedBeans(zeMaABeanList);
                break;
            case 4:
                beans = findTheSelectedBeans(zeMaBBeanList);
                break;
        }
        return beans;
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
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(currentPlayTypeCode));
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
        if (adapter1 == null || adapter2 == null)
            return;
        if (currentPlayTypeCode == 3) {
            adapter1.refreshData(zeMaABeanList.get(0).getList(), isFeng);
            adapter2.refreshData(zeMaABeanList.get(1).getList(), isFeng);
        }
        if (currentPlayTypeCode == 4) {
            adapter1.refreshData(zeMaBBeanList.get(0).getList(), isFeng);
            adapter2.refreshData(zeMaBBeanList.get(1).getList(), isFeng);
        }

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (currentPlayTypeCode == 3) {
                setupZeMaOddsView(zeMaABeanList);
            }
            if (currentPlayTypeCode == 4) {
                setupZeMaOddsView(zeMaBBeanList);
            }
        } else {
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
