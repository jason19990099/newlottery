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
 * 全不中
 * Created by a bin on 2017/7/14.
 */

public class QBZFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View qbzView;
    private boolean isFeng;
    private List<NewOddsBean.ListBean> zho5BeanList, zho6BeanList, zho7BeanList, zho8BeanList, zho9BeanList, zho10BeanList, zho11BeanList, zho12BeanList;
    private ScrollView scrollView;
    private RecyclerView recycle_qbz_data;
    private int currentPlayTypeCode = 40;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>();
    private BetColorsItemAdapter adapter;
    private MarkSixActivity sixActivity;
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        qbzView = inflater.inflate(R.layout.view_six_hx, null);
        mFlOddsContainer.addView(qbzView);
        initQBZView();
        requestOddsInfo(40);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initQBZView() {
        TextView tv_sxl_name = (TextView) qbzView.findViewById(R.id.tv_ball_name);
        tv_sxl_name.setText(getResources().getString(R.string.sxl_2));
        RecyclerView recycle_wxl = (RecyclerView) qbzView.findViewById(R.id.recycle_hx);
        recycle_qbz_data = (RecyclerView) qbzView.findViewById(R.id.recycle_hx_data);
        ArrayList<NewOddsBean.ListBean> qbzTypeList = new ArrayList<>();
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_5), "40", true));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_6), "41", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_7), "42", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_8), "43", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_9), "44", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_10), "45", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_11), "46", false));
        qbzTypeList.add(new NewOddsBean.ListBean(getResources().getString(R.string.qbz_12), "47", false));

        MarkSixAdapter adapter2 = new MarkSixAdapter(QBZFragment.this.getActivity(), R.layout.item_type_title, qbzTypeList, recycle_wxl);
        recycle_wxl.setLayoutManager(new GridLayoutManager(QBZFragment.this.getContext(), 4));
        recycle_wxl.setAdapter(adapter2);
        adapter2.setOnItemClickListener(new MarkSixAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                adapter2.setSelectedPosition(position);
                adapter2.notifyDataSetChanged();
                tv_sxl_name.setText(bean.getName());
                currentPlayTypeCode = 40 + position;
                switch (position) {
                    case 0:
                        setupQBZOddsView(zho5BeanList);
                        break;
                    case 1:
                        if (null == zho6BeanList || zho6BeanList.size() == 0) {
                            requestOddsInfo(41);
                        } else {
                            setupQBZOddsView(zho6BeanList);
                        }
                        break;
                    case 2:
                        if (null == zho7BeanList || zho7BeanList.size() == 0) {
                            requestOddsInfo(42);
                        } else {
                            setupQBZOddsView(zho7BeanList);
                        }
                        break;
                    case 3:
                        if (null == zho8BeanList || zho8BeanList.size() == 0) {
                            requestOddsInfo(43);
                        } else {
                            setupQBZOddsView(zho8BeanList);
                        }
                        break;
                    case 4:
                        if (null == zho9BeanList || zho9BeanList.size() == 0) {
                            requestOddsInfo(44);
                        } else {
                            setupQBZOddsView(zho9BeanList);
                        }
                        break;
                    case 5:
                        if (null == zho10BeanList || zho10BeanList.size() == 0) {
                            requestOddsInfo(45);
                        } else {
                            setupQBZOddsView(zho10BeanList);
                        }
                        break;
                    case 6:
                        if (null == zho11BeanList || zho11BeanList.size() == 0) {
                            requestOddsInfo(46);
                        } else {
                            setupQBZOddsView(zho11BeanList);
                        }
                        break;
                    case 7:
                        if (null == zho12BeanList || zho12BeanList.size() == 0) {
                            requestOddsInfo(47);
                        } else {
                            setupQBZOddsView(zho12BeanList);
                        }
                        break;
                }
            }
        });

        scrollView = (ScrollView) qbzView.findViewById(R.id.scrollView);
        currentPlayTypeCode = 40;
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
            case 40:
                zho5BeanList = data.get(0).getList();
                setupQBZOddsView(zho5BeanList);
                break;
            case 41:
                zho6BeanList = data.get(0).getList();
                setupQBZOddsView(zho6BeanList);
                break;
            case 42:
                zho7BeanList = data.get(0).getList();
                setupQBZOddsView(zho7BeanList);
                break;
            case 43:
                zho8BeanList = data.get(0).getList();
                setupQBZOddsView(zho8BeanList);
                break;
            case 44:
                zho9BeanList = data.get(0).getList();
                setupQBZOddsView(zho9BeanList);
                break;
            case 45:
                zho10BeanList = data.get(0).getList();
                setupQBZOddsView(zho10BeanList);
                break;
            case 46:
                zho11BeanList = data.get(0).getList();
                setupQBZOddsView(zho11BeanList);
                break;
            case 47:
                zho12BeanList = data.get(0).getList();
                setupQBZOddsView(zho12BeanList);
                break;
        }
        showOdds();
    }

    /**
     * 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupQBZOddsView(List<NewOddsBean.ListBean> currentOddBeans) { //, int position
        if (currentOddBeans == null || recycle_qbz_data == null)
            return;

        if (null == adapter) {
            adapter = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, currentOddBeans, recycle_qbz_data, true, isFeng, this);
            GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 20);
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (position > 43 && position < 49) ? 4 : 5;
                }
            });
            recycle_qbz_data.setLayoutManager(layoutManager);
            recycle_qbz_data.setAdapter(adapter);
        } else {
            adapter.refreshData(currentOddBeans,isFeng);
        }
        recycle_qbz_data.setFocusable(false);

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
            case 40:
                beans = findTheSelectedBeans(zho5BeanList);
                break;
            case 41:
                beans = findTheSelectedBeans(zho6BeanList);
                break;
            case 42:
                beans = findTheSelectedBeans(zho7BeanList);
                break;
            case 43:
                beans = findTheSelectedBeans(zho8BeanList);
                break;
            case 44:
                beans = findTheSelectedBeans(zho9BeanList);
                break;
            case 45:
                beans = findTheSelectedBeans(zho10BeanList);
                break;
            case 46:
                beans = findTheSelectedBeans(zho11BeanList);
                break;
            case 47:
                beans = findTheSelectedBeans(zho12BeanList);
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

    /**
     * 投注
     */
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

    /**
     * 配置尾数连投注参数
     */
    private void setWXLParam(ArrayMap<String, String> betParams) {
        List<NewOddsBean.ListBean> beanList = null;
        switch (currentPlayTypeCode) {
            case 40:
                beanList = zho5BeanList;
                break;
            case 41:
                beanList = zho6BeanList;
                break;
            case 42:
                beanList = zho7BeanList;
                break;
            case 43:
                beanList = zho8BeanList;
                break;
            case 44:
                beanList = zho9BeanList;
                break;
            case 45:
                beanList = zho10BeanList;
                break;
            case 46:
                beanList = zho11BeanList;
                break;
            case 47:
                beanList = zho12BeanList;
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
            case 40: //五不中
                if (selectedBeans.size() < 5 || selectedBeans.size() > 10) {
                    ToastDialog.error(String.format(bet_lest_item, 5) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                    return;
                }
                break;
            case 41: //六不中
                if (selectedBeans.size() < 6 || selectedBeans.size() > 10) {
                    ToastDialog.error(String.format(bet_lest_item, 6) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                    return;
                }
                break;
            case 42: //七不中
                if (selectedBeans.size() < 7 || selectedBeans.size() > 10) {
                    ToastDialog.error(String.format(bet_lest_item, 7) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                    return;
                }
                break;
            case 43: //八不中
                if (selectedBeans.size() < 8 || selectedBeans.size() > 10) {
                    ToastDialog.error(String.format(bet_lest_item, 8) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                    return;
                }
            case 44: //九不中
                if (selectedBeans.size() < 9 || selectedBeans.size() > 11) {
                    ToastDialog.error(String.format(bet_lest_item, 9) + String.format(bet_max_lest, 11)).show(getFragmentManager());
                    return;
                }
                break;
            case 45: //十不中
                if (selectedBeans.size() < 10 || selectedBeans.size() > 12) {
                    ToastDialog.error(String.format(bet_lest_item, 10) + String.format(bet_max_lest, 12)).show(getFragmentManager());
                    return;
                }
                break;
            case 46: //十一不中
                if (selectedBeans.size() < 11 || selectedBeans.size() > 13) {
                    ToastDialog.error(String.format(bet_lest_item, 11) + String.format(bet_max_lest, 13)).show(getFragmentManager());
                    return;
                }
                break;
            case 47: //十二不中
                if (selectedBeans.size() < 12 || selectedBeans.size() > 15) {
                    ToastDialog.error(String.format(bet_lest_item, 12) + String.format(bet_max_lest, 15)).show(getFragmentManager());
                    return;
                }
                break;
        }

        mDialog = new SxlViewDialog(this.getContext(), currentPlayTypeCode, null, selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
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
    public void clearBettingSelect( ) {
        selectedBeans = findTheSelectedBeans();
        if (null == selectedBeans )
            return;
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean listBean = selectedBeans.get(i);
            listBean.setSelectedState(false);
            selectedBeans.clear();
        }
        if (adapter == null)
            return;
        if (currentPlayTypeCode == 40) {
            adapter.refreshData(zho5BeanList,isFeng);
        } else if (currentPlayTypeCode == 41) {
            adapter.refreshData(zho6BeanList,isFeng);
        } else if (currentPlayTypeCode == 42) {
            adapter.refreshData(zho7BeanList,isFeng);
        } else if (currentPlayTypeCode == 43) {
            adapter.refreshData(zho8BeanList,isFeng);
        } else if (currentPlayTypeCode == 44) {
            adapter.refreshData(zho9BeanList,isFeng);
        } else if (currentPlayTypeCode == 45) {
            adapter.refreshData(zho10BeanList,isFeng);
        } else if (currentPlayTypeCode == 46) {
            adapter.refreshData(zho11BeanList,isFeng);
        } else if (currentPlayTypeCode == 47) {
            adapter.refreshData(zho12BeanList,isFeng);
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
