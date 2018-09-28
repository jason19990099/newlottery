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
 * 生肖连
 * Created by a bin on 2017/7/13.
 */

public class SheXLFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View sxlView;
    private boolean isFeng;
    private List<NewOddsBean.ListBean> xiao2BeanList, xiao3BeanList, xiao4BeanList, xiao5BeanList, xiao2NBeanList, xiao3NBeanList, xiao4NBeanList;
    private ScrollView scrollView;
    private RecyclerView recycle_sxl_data;
    private int currentPlayTypeCode = 27;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>();
    private BetColorsItemAdapter adapter;
    private MarkSixActivity sixActivity;
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        sxlView = inflater.inflate(R.layout.view_six_hx, null);
        mFlOddsContainer.addView(sxlView);
        initSXLView();
        requestOddsInfo(27);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initSXLView() {
        TextView tv_sxl_name = (TextView) sxlView.findViewById(R.id.tv_ball_name);
        tv_sxl_name.setText(getResources().getString(R.string.sxl_2));
        RecyclerView recycle_sxl = (RecyclerView) sxlView.findViewById(R.id.recycle_hx);
        recycle_sxl_data = (RecyclerView) sxlView.findViewById(R.id.recycle_hx_data);
        ArrayList<NewOddsBean.ListBean> sxlTypeList = new ArrayList<>();
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_2), "27", true));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_3), "28", false));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_4), "29", false));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_5), "30", false));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_2_no), "31", false));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_3_no), "32", false));
        sxlTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.sxl_4_no), "33", false));

        MarkSixAdapter adapter2 = new MarkSixAdapter(SheXLFragment.this.getActivity(), R.layout.item_type_title, sxlTypeList, recycle_sxl);
        GridLayoutManager manager = new GridLayoutManager(SheXLFragment.this.getContext(), 12);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position > 3 ? 4 : 3;
            }
        });
        recycle_sxl.setLayoutManager(manager);
        recycle_sxl.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetChanged();
                tv_sxl_name.setText(bean.getName());
                currentPlayTypeCode = 27 + position;
                switch (position) {
                    case 0:
                        setupSXLOddsView(xiao2BeanList);
                        break;
                    case 1:
                        if (null == xiao3BeanList || xiao3BeanList.size() == 0) {
                            requestOddsInfo(28);
                        } else {
                            setupSXLOddsView(xiao3BeanList);
                        }
                        break;
                    case 2:
                        if (null == xiao4BeanList || xiao4BeanList.size() == 0) {
                            requestOddsInfo(29);
                        } else {
                            setupSXLOddsView(xiao4BeanList);
                        }
                        break;
                    case 3:
                        if (null == xiao5BeanList || xiao5BeanList.size() == 0) {
                            requestOddsInfo(30);
                        } else {
                            setupSXLOddsView(xiao5BeanList);
                        }
                        break;
                    case 4:
                        if (null == xiao2NBeanList || xiao2NBeanList.size() == 0) {
                            requestOddsInfo(31);
                        } else {
                            setupSXLOddsView(xiao2NBeanList);
                        }
                        break;
                    case 5:
                        if (null == xiao3NBeanList || xiao3NBeanList.size() == 0) {
                            requestOddsInfo(32);
                        } else {
                            setupSXLOddsView(xiao3NBeanList);
                        }
                        break;
                    case 6:
                        if (null == xiao4NBeanList || xiao4NBeanList.size() == 0) {
                            requestOddsInfo(33);
                        } else {
                            setupSXLOddsView(xiao4NBeanList);
                        }
                        break;
                }
            }
        });

        scrollView = (ScrollView) sxlView.findViewById(R.id.scrollView);
        currentPlayTypeCode = 27;
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
            case 27:
                xiao2BeanList = data.get(0).getList();
                setupSXLOddsView(xiao2BeanList);
                break;
            case 28:
                xiao3BeanList = data.get(0).getList();
                setupSXLOddsView(xiao3BeanList);
                break;
            case 29:
                xiao4BeanList = data.get(0).getList();
                setupSXLOddsView(xiao4BeanList);
                break;
            case 30:
                xiao5BeanList = data.get(0).getList();
                setupSXLOddsView(xiao5BeanList);
                break;
            case 31:
                xiao2NBeanList = data.get(0).getList();
                setupSXLOddsView(xiao2NBeanList);
                break;
            case 32:
                xiao3NBeanList = data.get(0).getList();
                setupSXLOddsView(xiao3NBeanList);
                break;
            case 33:
                xiao4NBeanList = data.get(0).getList();
                setupSXLOddsView(xiao4NBeanList);
                break;
        }
        showOdds();
    }

    /**
     * 特码/正码/正码特 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupSXLOddsView(List<NewOddsBean.ListBean> currentOddBeans) { //, int position
        if (currentOddBeans == null || recycle_sxl_data == null)
            return;

        if (null == adapter) {
            adapter = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, currentOddBeans, recycle_sxl_data, false, isFeng, this);
            recycle_sxl_data.setLayoutManager(new GridLayoutManager(SheXLFragment.this.getActivity(), 4));
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
            case 27:
                beans = findTheSelectedBeans(xiao2BeanList);
                break;
            case 28:
                beans = findTheSelectedBeans(xiao3BeanList);
                break;
            case 29:
                beans = findTheSelectedBeans(xiao4BeanList);
                break;
            case 30:
                beans = findTheSelectedBeans(xiao5BeanList);
                break;
            case 31:
                beans = findTheSelectedBeans(xiao2NBeanList);
                break;
            case 32:
                beans = findTheSelectedBeans(xiao3NBeanList);
                break;
            case 33:
                beans = findTheSelectedBeans(xiao4NBeanList);
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
        setSXLParam(betParams);
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    private void setSXLParam(ArrayMap<String, String> betParams) {
        List<NewOddsBean.ListBean> beanList = null;
        switch (currentPlayTypeCode) {
            case 27:
                beanList = xiao2BeanList;
                break;
            case 28:
                beanList = xiao3BeanList;
                break;
            case 29:
                beanList = xiao4BeanList;
                break;
            case 30:
                beanList = xiao5BeanList;
                break;
            case 31:
                beanList = xiao2NBeanList;
                break;
            case 32:
                beanList = xiao3NBeanList;
                break;
            case 33:
                beanList = xiao4NBeanList;
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
        sixActivity.setMoney(bet_money);
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
            case 27: // 二肖连中
            case 31: //二肖不连中
                if (selectedBeans.size() < 2 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 2) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
            case 28: // 三肖连中
            case 32: // 三肖不连中
                if (selectedBeans.size() < 3 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 3) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
            case 29: //四肖连中
            case 33: // 四肖不连中
                if (selectedBeans.size() < 4 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 4) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
            case 30:  //五肖连中
                if (selectedBeans.size() < 5 || selectedBeans.size() > 8) {
                    ToastDialog.error(String.format(bet_lest_item, 5) + String.format(bet_max_lest, 8)).show(getFragmentManager());
                    return;
                }
                break;
        }

        mDialog = new SxlViewDialog(SheXLFragment.this.getActivity(), currentPlayTypeCode, null, selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
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
        if (currentPlayTypeCode == 27) {
            adapter.refreshData(xiao2BeanList, isFeng);
        } else if (currentPlayTypeCode == 28) {
            adapter.refreshData(xiao3BeanList, isFeng);
        } else if (currentPlayTypeCode == 29) {
            adapter.refreshData(xiao4BeanList, isFeng);
        } else if (currentPlayTypeCode == 30) {
            adapter.refreshData(xiao5BeanList, isFeng);
        } else if (currentPlayTypeCode == 31) {
            adapter.refreshData(xiao2NBeanList, isFeng);
        } else if (currentPlayTypeCode == 32) {
            adapter.refreshData(xiao3NBeanList, isFeng);
        } else if (currentPlayTypeCode == 33) {
            adapter.refreshData(xiao4NBeanList, isFeng);
        }

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (!isVisibleToUser) {
            clearBettingSelect();
        }
        super.setUserVisibleHint(isVisibleToUser);
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
