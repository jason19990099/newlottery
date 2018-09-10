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
import com.international.wtw.lottery.adapter.LianMaAdapter;
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
 * 连码
 * Created by a bin on 2017/7/14.
 */

public class LianMaFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View lmView;
    private boolean isFeng;
    private List<NewOddsBean.ListBean> lmTypeList;
    private RecyclerView recycle_lm_data;
    private int currentPlayTypeCode = 13;
    private MarkSixActivity sixActivity;
    private List<NewOddsBean.ListBean> lianMaOddsBeans; //连码投注球1-49
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>();
    private BetColorsItemAdapter adapter;
    private String lmTypeCode;
    private SxlViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        lmView = inflater.inflate(R.layout.view_six_hx, null);
        mFlOddsContainer.addView(lmView);
        initLmView();
        requestOddsInfo();
        return rootView;
    }

    /**
     * 连码view初始化
     */
    private void initLmView() {
        TextView tv_lm_name = (TextView) lmView.findViewById(R.id.tv_ball_name);
        tv_lm_name.setText(getResources().getString(R.string.lian_ma));
        lianMaOddsBeans = new ArrayList<>();
        lianMaOddsBeans = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            NewOddsBean.ListBean one = new NewOddsBean.ListBean();
            one.setName(String.valueOf(i + 1));
            one.setSelectedState(false);
            lianMaOddsBeans.add(one);
        }
        recycle_lm_data = (RecyclerView) lmView.findViewById(R.id.recycle_hx_data);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getActivity(), 20);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position > 43 && position < 49) ? 4 : 5;
            }
        });
        recycle_lm_data.setLayoutManager(layoutManager);
        adapter = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, lianMaOddsBeans, recycle_lm_data, true, isFeng, this);
        recycle_lm_data.setAdapter(adapter);
        recycle_lm_data.setFocusable(false);
        currentPlayTypeCode = 13;
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        if (null != lmView)
            return;
        if (adapter != null && null != lianMaOddsBeans && lianMaOddsBeans.size() > 0) {
            adapter.refreshData(lianMaOddsBeans);
        }
    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo();
    }

    private void requestOddsInfo() {
        requestOdds(mActivity.getLotteryType(), 13);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        lmTypeList = data.get(0).getList();
        setupLmOddsView(lmTypeList);
        showOdds();
    }

    /**
     * 连码赔率设置
     */
    private void setupLmOddsView(List<NewOddsBean.ListBean> currentListBean) {
        if (currentListBean == null)
            return;
        RecyclerView recycle_lm = (RecyclerView) lmView.findViewById(R.id.recycle_hx);
        List<NewOddsBean.ListBean> listBeenList = currentListBean;
        LianMaAdapter adapter = new LianMaAdapter(this.getContext(), R.layout.item_lm_type, listBeenList, recycle_lm);
        recycle_lm.setLayoutManager(new GridLayoutManager(this.getContext(), 3));

        recycle_lm.setAdapter(adapter);
        adapter.setOnItemClickListener(new LianMaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                initLmView();
                lmTypeCode = bean.getKey().substring(3, bean.getKey().length());
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetChanged();
            }
        });
        recycle_lm.setFocusable(false);
    }

    /**
     * 设置连码投注参数
     *
     * @param betParams
     */
    private void setLianMaParam(ArrayMap<String, String> betParams) {
        if (lmTypeList == null || lmTypeList.size() <= 0) {
            return;
        }
        NewOddsBean.ListBean selectedMethodType = null;
        for (int i = 0; i < lmTypeList.size(); i++) {
            NewOddsBean.ListBean oneMethod = lmTypeList.get(i);
            if (oneMethod.getSelectedState()) {
                selectedMethodType = oneMethod;
                break;
            }
        }
        if (selectedMethodType == null) {
            return;
        }

        StringBuilder selectedPoints = new StringBuilder();
        List<NewOddsBean.ListBean> selectedItems = new ArrayList<NewOddsBean.ListBean>();
        for (int i = 0; i < lianMaOddsBeans.size(); i++) {
            NewOddsBean.ListBean onePoint = lianMaOddsBeans.get(i);
            if (onePoint.getSelectedState()) {
                selectedItems.add(onePoint);
            }
        }
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedPoints.append(selectedItems.get(i).getName());
            if (i < selectedItems.size() - 1) {
                selectedPoints.append(",");
            }
        }
        // put param
        betParams.put(LotteryId.TYPECODE, selectedMethodType.getKey().substring(3, selectedMethodType.getKey().length()));
        betParams.put(LotteryId.BET_NUMBER, selectedPoints.toString());
        betParams.put(LotteryId.BET_MONEY, bet_money);

    }


    /**
     * 投注
     */
    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(currentPlayTypeCode));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), "round", ""));
        setLianMaParam(betParams);
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
        sixActivity.setMoney(bet_money);
        selectedBeans = findTheLianMaSelectedBeans();
        if (selectedBeans == null || selectedBeans.size() == 0) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
            return;
        }
        if (TextUtils.isEmpty(bet_money)) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        String bet_max_lest = getResources().getString(R.string.bet_max_item);
        String bet_lest_item = getResources().getString(R.string.bet_lest_item);
        if (TextUtils.isEmpty(lmTypeCode))
            lmTypeCode = "613";
        if ("613".equals(lmTypeCode) || "615".equals(lmTypeCode) || "616".equals(lmTypeCode)) { //二全中 中二 特串
            if (selectedBeans.size() < 2 || selectedBeans.size() > 10) {
                ToastDialog.error(String.format(bet_lest_item, 2) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                return;
            }
        } else if ("619".equals(lmTypeCode) || "617".equals(lmTypeCode)) { // 中三  三全中
            if (selectedBeans.size() < 3 || selectedBeans.size() > 10) {
                ToastDialog.error(String.format(bet_lest_item, 3) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                return;
            }
        } else if ("808".equals(lmTypeCode)) { //四中一
            if (selectedBeans.size() < 4 || selectedBeans.size() > 10) {
                ToastDialog.error(String.format(bet_lest_item, 4) + String.format(bet_max_lest, 10)).show(getFragmentManager());
                return;
            }
        }

        mDialog = new SxlViewDialog(LianMaFragment.this.getContext(), 0, lmTypeCode, selectedBeans, bet_money, new SxlViewDialog.SureCallBack() {
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
     * 查找连码中选中的数据
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheLianMaSelectedBeans() {
        List<NewOddsBean.ListBean> selectedBean = new ArrayList<>();
        if (null == lianMaOddsBeans) {
            return selectedBean;
        }
        for (int i = 0; i < lianMaOddsBeans.size(); i++) {
            NewOddsBean.ListBean one;
            one = lianMaOddsBeans.get(i);
            if (one.getSelectedState()) {
                selectedBean.add(one);
            }
        }
        return selectedBean;
    }

    /**
     * 清除选中
     */
    public void clearBettingSelect() {
        selectedBeans = findTheLianMaSelectedBeans();
        if (null == selectedBeans)
            return;
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean listBean = selectedBeans.get(i);
            listBean.setSelectedState(false);
            selectedBeans.clear();
        }
        if (adapter == null)
            return;
        adapter.refreshData(lianMaOddsBeans);

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }
    }



    @Override
    public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
        int size = findTheLianMaSelectedBeans().size();
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
