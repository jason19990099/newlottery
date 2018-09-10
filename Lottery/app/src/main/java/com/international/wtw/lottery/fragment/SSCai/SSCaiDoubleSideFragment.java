package com.international.wtw.lottery.fragment.SSCai;

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
import com.international.wtw.lottery.activity.lottery.SSCaiActivity;
import com.international.wtw.lottery.adapter.BetColorsItemAdapter;
import com.international.wtw.lottery.adapter.BetItemAdapter;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomListView;
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
 * 时时彩 - 两面盘
 * Created by a bin  on 2017/7/12.
 */

public class SSCaiDoubleSideFragment extends BetBaseFragment implements BetColorsItemAdapter.OnItemClickListener, BetItemAdapter.ItemBettingCallback, StateInterface {
    private View view;
    private boolean isFeng;
    private List<NewOddsBean> currentOddBeans;  //请求到的赔率数据
    private ScrollView scrollView;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<NewOddsBean.ListBean>();
    private SSCaiActivity ssCaiActivity;
    //两面盘
    private List<View> lmpTopItemView = new ArrayList<View>();
    private final int ODDS_SUCESSFULLY = 0x01;
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_cj_2sides_disk, null);
        mFlOddsContainer.addView(view);
        initViewLmp();
        getData();

        return rootView;
    }

    /**
     * 两面盘
     */
    private void initViewLmp() {
        lmpTopItemView.clear();
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_one));
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_two));
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_three));
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_four));
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_five));
        lmpTopItemView.add(view.findViewById(R.id.ly_lm_six));

        scrollView = (ScrollView) view.findViewById(R.id.scrollView1);
    }

    @Override
    protected void refreshOdds() {
        getData();
    }

    private void getData() {
        requestOdds(mActivity.getLotteryType(), Constants.CJ_LOTTERY_PLAY_TYPE.DOUBLE_SIDE);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        currentOddBeans = data;
        showOdds();
        setupLmpOddsView(lmpTopItemView, currentOddBeans);
    }

    /**
     * 两面盘赔率设置
     *
     * @param lmpTopItemViews
     * @param currentOddBeans
     */
    private void setupLmpOddsView(List<View> lmpTopItemViews, List<NewOddsBean> currentOddBeans) {
        if (lmpTopItemViews == null || currentOddBeans == null || null == getActivity()) {
            return;
        }
        int size1 = lmpTopItemViews.size();
        int size2 = currentOddBeans.size();
        if (size1 != size2) {
            return;
        }

        int size = Math.min(lmpTopItemViews.size(), currentOddBeans.size());
        for (int i = 0; i < size; i++) {
            View topItemView = lmpTopItemViews.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);

            TextView textView = (TextView) topItemView.findViewById(R.id.tv_ball_name);
            textView.setText(oneBean.getName());

            if (i < size - 1) {
                CustomListView listView = (CustomListView) topItemView.findViewById(R.id.listViewItems);
                BetItemAdapter adapter = new BetItemAdapter(oneBean.getList(), getActivity(), this, isFeng);
                listView.setAdapter(adapter);
                listView.setFocusable(false);
                adapter.notifyDataSetChanged();
            } else {
                RecyclerView recycle_zh_lm = (RecyclerView) topItemView.findViewById(R.id.recycle_zh_lm);
                BetColorsItemAdapter adapter = new BetColorsItemAdapter(SSCaiDoubleSideFragment.this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_zh_lm, false, isFeng, this);
                GridLayoutManager layoutManager = new GridLayoutManager(SSCaiDoubleSideFragment.this.getActivity(), 12);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (position > 3) ? 4 : 3;
                    }
                });
                recycle_zh_lm.setLayoutManager(layoutManager);
                recycle_zh_lm.setFocusable(false);
                recycle_zh_lm.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }
        scrollView.scrollTo(0, 0);
    }

    /**
     * 查找选中状态的BetItem
     * 如果是两面盘，直接查找otherOddBeans
     * 如果不是两面盘，查找pointOddsBeanInfo里面的选中的Item
     *
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans() {
        return findTheSelectedBeans(currentOddBeans);
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

    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(ssCaiActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(Constants.CJ_LOTTERY_PLAY_TYPE.DOUBLE_SIDE));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), "round", ""));
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
        this.ssCaiActivity = (SSCaiActivity) context;
    }

    public void getBet() {
        if (null == getActivity())
            return;
        bet_money = SharePreferencesUtil.getString(getActivity(), LotteryId.LOTTERY_BET_MONEY, "0");
        ssCaiActivity.setMoney(bet_money);
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
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
        int selectSize = selectedBeans.size();
        if (selectSize > 0) {
            for (int i = 0; i < selectSize; i++) {
                NewOddsBean.ListBean listBean = selectedBeans.get(i);
                listBean.setSelectedState(false);
            }
            selectedBeans.clear();
            setupLmpOddsView(lmpTopItemView, currentOddBeans);
        }

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }

    }

    /**
     * 开封盘刷新界面
     */
    private void openAndCloseInitView() {
        clearBettingSelect();
        setupLmpOddsView(lmpTopItemView, currentOddBeans);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
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
    public void ItemClick() {
        int size = findTheSelectedBeans().size();

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(size);
        }
    }

    @Override
    public void open(Boolean b) {
        isFeng = b;
        openAndCloseInitView();
    }

    @Override
    public void close(Boolean b) {
        isFeng = b;
        openAndCloseInitView();
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
