package com.international.wtw.lottery.fragment.GDHappy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
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
 * Created by XIAOYAN on 2017/7/12.
 */

public class GDHappyFragment3 extends BetBaseFragment implements BetItemAdapter.ItemBettingCallback, StateInterface {
    private View view;
    private List<View> fiveToEightTopItemView = new ArrayList<>();    //五到八
    private boolean IsFeng;
    private List<NewOddsBean.ListBean> alreadyBettedBeans;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();//选中的数据
    private List<NewOddsBean> currentOddBeans = new ArrayList<>();  //请求到的赔率数据
    private RecyclerViewDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_gd_happy_eight, null);
        mFlOddsContainer.addView(view);
        initalViewFiveToEight();
        getData();

        return rootView;
    }

    /**
     * 五到八
     */
    private void initalViewFiveToEight() {
        fiveToEightTopItemView.clear();
        fiveToEightTopItemView.add(view.findViewById(R.id.layout_one));
        fiveToEightTopItemView.add(view.findViewById(R.id.layout_two));
        fiveToEightTopItemView.add(view.findViewById(R.id.layout_three));
        fiveToEightTopItemView.add(view.findViewById(R.id.layout_four));
    }

    @Override
    protected void refreshOdds() {
        getData();
    }

    private void getData() {
//        requestOdds(mActivity.getLotteryType(), Constants.GD_HAPPY_PLAY_TYPE.FIVE_TO_EIGHT);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        currentOddBeans = data;
        showOdds();
        setupFiveToEightOddsView(fiveToEightTopItemView, currentOddBeans);
    }

    /**
     * 设置5到8球的赔率信息
     */
    private void setupFiveToEightOddsView(List<View> topItemViews, List<NewOddsBean> beans) {
        if (null == topItemViews || null == beans || null == getActivity()) {
            return;
        }
        int size = topItemViews.size();
        int size2 = beans.size();
        //数据不对应的时间会导致空指针
        if (size != size2) {
            return;
        }
        for (int i = 0; i < size; i++) {
            View topItemView = topItemViews.get(i);
            NewOddsBean oneBean = beans.get(i);
            if (null != getActivity()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = (TextView) topItemView.findViewById(R.id.top_item_name);
                        textView.setText(oneBean.getName());

                        CustomListView listView = (CustomListView) topItemView.findViewById(R.id.listViewItems);
                        BetItemAdapter adapter = new BetItemAdapter(oneBean.getList(), getActivity(), GDHappyFragment3.this, IsFeng);
                        listView.setAdapter(adapter);
                        listView.setFocusable(false);
                    }
                });
            }
        }

    }


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
                alreadyBettedBeans = findTheSelectedBeans();
                if (alreadyBettedBeans.size() != 0) {
                    clearbettingseleter();
                }
            }
        });
        mDialog.show();
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

    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(Constants.GD_HAPPY_PLAY_TYPE.FIVE_TO_EIGHT));
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
     * 清除选中
     */
    public void clearbettingseleter() {
        selectedBeans = findTheSelectedBeans();
        int selectSize = selectedBeans.size();
        if (selectSize > 0) {
            for (int i = 0; i < selectSize; i++) {
                NewOddsBean.ListBean listBean = selectedBeans.get(i);
                listBean.setSelectedState(false);
            }
            selectedBeans.clear();
            setupFiveToEightOddsView(fiveToEightTopItemView, currentOddBeans);
        }

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }

    }

    /**
     * 开封盘刷新界面
     */
    private void openAndCloseInitView() {
        clearbettingseleter();
        setupFiveToEightOddsView(fiveToEightTopItemView, currentOddBeans);
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

}
