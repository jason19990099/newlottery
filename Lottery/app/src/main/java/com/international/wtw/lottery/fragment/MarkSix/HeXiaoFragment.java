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
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 合肖
 * Created by a bin on 2017/7/13.
 */

public class HeXiaoFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View heXiaoView;
    private boolean isFeng;
    private List<NewOddsBean.ListBean> xiao2BeanList, xiao3BeanList, xiao4BeanList, xiao5BeanList, xiao6BeanList;   //请求到的赔率数据
    private ScrollView scrollView;
    private RecyclerView recycle_hx_data;
    private int currentPlayTypeCode = 17;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private BetColorsItemAdapter adapter;
    private MarkSixActivity sixActivity;
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        heXiaoView = inflater.inflate(R.layout.view_six_hx, null);
        mFlOddsContainer.addView(heXiaoView);
        initHeXiaoView();
        requestOddsInfo(17);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initHeXiaoView() {
        TextView tv_hex_name = (TextView) heXiaoView.findViewById(R.id.tv_ball_name);
        RecyclerView recycle_hx = (RecyclerView) heXiaoView.findViewById(R.id.recycle_hx);
        recycle_hx_data = (RecyclerView) heXiaoView.findViewById(R.id.recycle_hx_data);
        ArrayList<NewOddsBean.ListBean> ztmTypeList = new ArrayList<>();
        ztmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.er_x), "17", true));
        ztmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.san_x), "18", false));
        ztmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.si_x), "19", false));
        ztmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.wu_x), "20", false));
        ztmTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.liu_x), "21", false));

        MarkSixAdapter adapter2 = new MarkSixAdapter(HeXiaoFragment.this.getActivity(), R.layout.item_type_title, ztmTypeList, recycle_hx);
        recycle_hx.setLayoutManager(new GridLayoutManager(HeXiaoFragment.this.getActivity(), 5));
        recycle_hx.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetChanged();
                tv_hex_name.setText(bean.getName());
                currentPlayTypeCode = 17 + position;
                switch (position) {
                    case 0:
                        setupHeXiaoOddsView(xiao2BeanList);
                        break;
                    case 1:
                        if (null == xiao3BeanList || xiao3BeanList.size() == 0) {
                            requestOddsInfo(18);
                        } else {
                            setupHeXiaoOddsView(xiao3BeanList);
                        }
                        break;
                    case 2:
                        if (null == xiao4BeanList || xiao4BeanList.size() == 0) {
                            requestOddsInfo(19);
                        } else {
                            setupHeXiaoOddsView(xiao4BeanList);
                        }
                        break;
                    case 3:
                        if (null == xiao5BeanList || xiao5BeanList.size() == 0) {
                            requestOddsInfo(20);
                        } else {
                            setupHeXiaoOddsView(xiao5BeanList);
                        }
                        break;
                    case 4:
                        if (null == xiao6BeanList || xiao6BeanList.size() == 0) {
                            requestOddsInfo(21);
                        } else {
                            setupHeXiaoOddsView(xiao6BeanList);
                        }
                        break;
                }
            }
        });

        scrollView = (ScrollView) heXiaoView.findViewById(R.id.scrollView);
        currentPlayTypeCode = 17;
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
            case 17:
                xiao2BeanList = data.get(0).getList();
                setupHeXiaoOddsView(xiao2BeanList);
                break;
            case 18:
                xiao3BeanList = data.get(0).getList();
                setupHeXiaoOddsView(xiao3BeanList);
                break;
            case 19:
                xiao4BeanList = data.get(0).getList();
                setupHeXiaoOddsView(xiao4BeanList);
                break;
            case 20:
                xiao5BeanList = data.get(0).getList();
                setupHeXiaoOddsView(xiao5BeanList);
                break;
            case 21:
                xiao6BeanList = data.get(0).getList();
                setupHeXiaoOddsView(xiao6BeanList);
                break;
        }
        showOdds();
    }

    /**
     * 特码/正码/正码特 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupHeXiaoOddsView(List<NewOddsBean.ListBean> currentOddBeans) { //, int position
        if (currentOddBeans == null)
            return;

        if (null == adapter) {
            adapter = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, currentOddBeans, recycle_hx_data, false, isFeng, this);
            recycle_hx_data.setLayoutManager(new GridLayoutManager(HeXiaoFragment.this.getActivity(), 4));
            recycle_hx_data.setAdapter(adapter);
        } else {
            adapter.refreshData(currentOddBeans);
        }
        recycle_hx_data.setFocusable(false);

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
            case 17:
                beans = findTheSelectedBeans(xiao2BeanList);
                break;
            case 18:
                beans = findTheSelectedBeans(xiao3BeanList);
                break;
            case 19:
                beans = findTheSelectedBeans(xiao4BeanList);
                break;
            case 20:
                beans = findTheSelectedBeans(xiao5BeanList);
                break;
            case 21:
                beans = findTheSelectedBeans(xiao6BeanList);
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

        setHeXiaoParam(betParams);
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    private void setHeXiaoParam(ArrayMap<String, String> betParams) {
        List<NewOddsBean.ListBean> beanList = null;
        switch (currentPlayTypeCode) {
            case 17:
                beanList = xiao2BeanList;
                break;
            case 18:
                beanList = xiao3BeanList;
                break;
            case 19:
                beanList = xiao4BeanList;
                break;
            case 20:
                beanList = xiao5BeanList;
                break;
            case 21:
                beanList = xiao6BeanList;
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

        String bet_just = getResources().getString(R.string.bet_just);
        switch (currentPlayTypeCode) {
            case 17:  //二肖
                if (selectedBeans.size() != 2) {
                    ToastDialog.error(String.format(bet_just, 2)).show(getFragmentManager());
                    return;
                }
                break;
            case 18: //三肖
                if (selectedBeans.size() != 3) {
                    ToastDialog.error(String.format(bet_just, 3)).show(getFragmentManager());
                    return;
                }
                break;
            case 19:  //四肖
                if (selectedBeans.size() != 4) {
                    ToastDialog.error(String.format(bet_just, 4)).show(getFragmentManager());
                    return;
                }
                break;
            case 20:  //五肖
                if (selectedBeans.size() != 5) {
                    ToastDialog.error(String.format(bet_just, 5)).show(getFragmentManager());
                    return;
                }
                break;
            case 21: //六肖
                if (selectedBeans.size() != 6) {
                    ToastDialog.error(String.format(bet_just, 6)).show(getFragmentManager());
                    return;
                }
                break;
        }
        mDialog = new SxlViewDialog(HeXiaoFragment.this.getActivity(), currentPlayTypeCode, null, selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
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
        if (selectedBeans.size() > 0) {
            for (int i = 0; i < selectedBeans.size(); i++) {
                NewOddsBean.ListBean listBean = selectedBeans.get(i);
                listBean.setSelectedState(false);
                selectedBeans.clear();
            }
        }

        if (adapter == null)
            return;
        if (currentPlayTypeCode == 17) {
            adapter.refreshData(xiao2BeanList, isFeng);
        } else if (currentPlayTypeCode == 18) {
            adapter.refreshData(xiao3BeanList, isFeng);
        } else if (currentPlayTypeCode == 19) {
            adapter.refreshData(xiao4BeanList, isFeng);
        } else if (currentPlayTypeCode == 20) {
            adapter.refreshData(xiao5BeanList, isFeng);
        } else if (currentPlayTypeCode == 21) {
            adapter.refreshData(xiao6BeanList, isFeng);
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
