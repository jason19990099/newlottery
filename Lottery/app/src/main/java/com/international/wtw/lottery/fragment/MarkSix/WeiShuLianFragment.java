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
import android.widget.ScrollView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.MarkSixActivity;
import com.international.wtw.lottery.adapter.BetColorsItemAdapter;
import com.international.wtw.lottery.adapter.MarkSixAdapter;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.SxlViewDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.fragment.BetBaseFragment;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.listener.StateInterface;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 尾数连
 * Created by a bin on 2017/7/13.
 */

public class WeiShuLianFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View sxlView;
    private boolean isFeng;
    private List<NewOddsBean.ListBean> wei2BeanList, wei3BeanList, wei4BeanList, wei2NBeanList, wei3NBeanList, wei4NBeanList;
    private ScrollView scrollView;
    private RecyclerView recycle_sxl_data;
    private int currentPlayTypeCode = 34;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private BetColorsItemAdapter adapter;
    private MarkSixActivity sixActivity;
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sxlView = inflater.inflate(R.layout.view_six_hx, null);
        mFlOddsContainer.addView(sxlView);
        initWXLView();
        requestOddsInfo(34);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initWXLView() {
        TextView tv_sxl_name = (TextView) sxlView.findViewById(R.id.tv_ball_name);
        tv_sxl_name.setText(getResources().getString(R.string.sxl_2));
        RecyclerView recycle_wxl = (RecyclerView) sxlView.findViewById(R.id.recycle_hx);
        recycle_sxl_data = (RecyclerView) sxlView.findViewById(R.id.recycle_hx_data);
        ArrayList<NewOddsBean.ListBean> wslTypeList = new ArrayList<>();
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_2), "34", true));
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_3), "35", false));
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_4), "36", false));
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_2_no), "37", false));
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_3_no), "38", false));
        wslTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wsl_4_no), "39", false));

        MarkSixAdapter adapter2 = new MarkSixAdapter(WeiShuLianFragment.this.getActivity(), R.layout.item_type_title, wslTypeList, recycle_wxl);
        recycle_wxl.setLayoutManager(new GridLayoutManager(WeiShuLianFragment.this.getContext(), 3));
        recycle_wxl.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetChanged();
                tv_sxl_name.setText(bean.getName());
                currentPlayTypeCode = 34 + position;
                switch (position) {
                    case 0:
                        setupWSLOddsView(wei2BeanList);
                        break;
                    case 1:
                        if (null == wei3BeanList || wei3BeanList.size() == 0) {
                            requestOddsInfo(35);
                        } else {
                            setupWSLOddsView(wei3BeanList);
                        }
                        break;
                    case 2:
                        if (null == wei4BeanList || wei4BeanList.size() == 0) {
                            requestOddsInfo(36);
                        } else {
                            setupWSLOddsView(wei4BeanList);
                        }
                        break;
                    case 3:
                        if (null == wei2NBeanList || wei2NBeanList.size() == 0) {
                            requestOddsInfo(37);
                        } else {
                            setupWSLOddsView(wei2NBeanList);
                        }
                        break;
                    case 4:
                        if (null == wei3NBeanList || wei3NBeanList.size() == 0) {
                            requestOddsInfo(38);
                        } else {
                            setupWSLOddsView(wei3NBeanList);
                        }
                        break;
                    case 5:
                        if (null == wei4NBeanList || wei4NBeanList.size() == 0) {
                            requestOddsInfo(39);
                        } else {
                            setupWSLOddsView(wei4NBeanList);
                        }
                        break;
                }
            }
        });

        scrollView = (ScrollView) sxlView.findViewById(R.id.scrollView);
        currentPlayTypeCode = 34;
    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo(currentPlayTypeCode);
    }

    private void requestOddsInfo(int type_code) {
        requestOdds(mActivity.getLotteryType(), type_code);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        switch (currentPlayTypeCode) {
            case 34:
                wei2BeanList = data.get(0).getList();
                setupWSLOddsView(wei2BeanList);
                break;
            case 35:
                wei3BeanList = data.get(0).getList();
                setupWSLOddsView(wei3BeanList);
                break;
            case 36:
                wei4BeanList = data.get(0).getList();
                setupWSLOddsView(wei4BeanList);
                break;
            case 37:
                wei2NBeanList = data.get(0).getList();
                setupWSLOddsView(wei2NBeanList);
                break;
            case 38:
                wei3NBeanList = data.get(0).getList();
                setupWSLOddsView(wei3NBeanList);
                break;
            case 39:
                wei4NBeanList = data.get(0).getList();
                setupWSLOddsView(wei4NBeanList);
                break;
        }
        showOdds();
    }

    /**
     * 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupWSLOddsView(List<NewOddsBean.ListBean> currentOddBeans) { //, int position
        if (currentOddBeans == null)
            return;

        if (null == adapter) {
            adapter = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, currentOddBeans, recycle_sxl_data, true, isFeng, this);
            GridLayoutManager layoutManager = new GridLayoutManager(WeiShuLianFragment.this.getContext(), 12);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return position > 5 ? 3 : 4;
                }
            });
            recycle_sxl_data.setLayoutManager(layoutManager);
            recycle_sxl_data.setAdapter(adapter);
        } else {
            adapter.refreshData(currentOddBeans);
        }
        recycle_sxl_data.setFocusable(false);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
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
            case 34:
                beans = findTheSelectedBeans(wei2BeanList);
                break;
            case 35:
                beans = findTheSelectedBeans(wei3BeanList);
                break;
            case 36:
                beans = findTheSelectedBeans(wei4BeanList);
                break;
            case 37:
                beans = findTheSelectedBeans(wei2NBeanList);
                break;
            case 38:
                beans = findTheSelectedBeans(wei3NBeanList);
                break;
            case 39:
                beans = findTheSelectedBeans(wei4NBeanList);
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
    private List<NewOddsBean.ListBean> findTheSelectedBeans(List<NewOddsBean.ListBean> topItemBeans) {

        List<NewOddsBean.ListBean> betItemBeans = new ArrayList<>();
        if (null != topItemBeans) {
            for (int i = 0; i < topItemBeans.size(); i++) {
                NewOddsBean.ListBean oneBetItem = topItemBeans.get(i);
                if (oneBetItem.getSelectedState()) {
                    betItemBeans.add(oneBetItem);
                }
            }

        }
        return betItemBeans;
    }

    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(currentPlayTypeCode));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), "round", ""));
        setWXLParam(betParams);
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    private void setWXLParam(ArrayMap<String, String> betParams) {
        List<NewOddsBean.ListBean> beanList = null;
        switch (currentPlayTypeCode) {
            case 34:
                beanList = wei2BeanList;
                break;
            case 35:
                beanList = wei3BeanList;
                break;
            case 36:
                beanList = wei4BeanList;
                break;
            case 37:
                beanList = wei2NBeanList;
                break;
            case 38:
                beanList = wei3NBeanList;
                break;
            case 39:
                beanList = wei4NBeanList;
                break;
        }
        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        List<NewOddsBean.ListBean> betItemBeans = new ArrayList<>();

        for (int j = 0; j < beanList.size(); j++) {
            NewOddsBean.ListBean oneBetItem = beanList.get(j);
            if (oneBetItem.getSelectedState()) {
                betItemBeans.add(oneBetItem);
            }
        }

        StringBuilder selectedPoints = new StringBuilder();
        for (int i = 0; i < betItemBeans.size(); i++) {
            NewOddsBean.ListBean b = betItemBeans.get(i);
            selectedPoints.append(b.getKey().substring(3, b.getKey().length()));
            if (i < betItemBeans.size() - 1) {
                selectedPoints.append(",");
            }
        }

        // put param
        betParams.put(LotteryId.BET_NUMBER, selectedPoints.toString());
        betParams.put(LotteryId.BET_MONEY, bet_money);
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
        this.sixActivity.setMoney(bet_money);
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        selectedBeans = findTheSelectedBeans();
        if (selectedBeans == null || selectedBeans.size() == 0) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
            return;
        }

        String bet_max_lest = getResources().getString(R.string.bet_max_item);
        String bet_lest_item = getResources().getString(R.string.bet_lest_item);
        switch (currentPlayTypeCode) {
            case 34: //尾数连 二尾连中
            case 37: //尾数连 二尾不连中
                if (selectedBeans.size() < 2 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 2) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
            case 35: //尾数连 三尾连中
            case 38: //尾数连 三尾不连中
                if (selectedBeans.size() < 3 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 3) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
            case 36: //尾数连 四尾连中
            case 39: //尾数连 四尾不连中
                if (selectedBeans.size() < 4 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 4) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
        }

        mDialog = new SxlViewDialog(WeiShuLianFragment.this.getActivity(), currentPlayTypeCode, null, selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
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
        if (adapter == null)
            return;
        if (currentPlayTypeCode == 34) {
            adapter.refreshData(wei2BeanList, isFeng);
        } else if (currentPlayTypeCode == 35) {
            adapter.refreshData(wei3BeanList, isFeng);
        } else if (currentPlayTypeCode == 36) {
            adapter.refreshData(wei4BeanList, isFeng);
        } else if (currentPlayTypeCode == 37) {
            adapter.refreshData(wei2NBeanList, isFeng);
        } else if (currentPlayTypeCode == 38) {
            adapter.refreshData(wei3NBeanList, isFeng);
        } else if (currentPlayTypeCode == 39) {
            adapter.refreshData(wei4NBeanList, isFeng);
        }

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
