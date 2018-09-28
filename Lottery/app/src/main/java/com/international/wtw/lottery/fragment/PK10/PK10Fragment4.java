package com.international.wtw.lottery.fragment.PK10;

import android.os.Bundle;
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
 * Created by 18Steven on 2017/7/11.1-5名
 */

public class PK10Fragment4 extends BetBaseFragment implements BetItemAdapter.ItemBettingCallback, StateInterface {
    private View view;
    private boolean IsFeng;
    private BetItemAdapter adapter;
    private int currentPlayTypeCode = Constants.PK_10_PLAY_TYPE_SERVER.SIX_TO_TEN;
    private List<View> ydwdTopItemView = new ArrayList<>();    //一~五名
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();//选中的数据
    private List<NewOddsBean> currentOddBeans = new ArrayList<>();  //请求到的赔率数据
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_ldsh, null);
        mFlOddsContainer.addView(view);
        initView();
        getData();
        return rootView;
    }

    private void initView() {
        ydwdTopItemView.clear();
        ydwdTopItemView.add(view.findViewById(R.id.layout_ldsh_six));
        ydwdTopItemView.add(view.findViewById(R.id.layout_ldsh_seven));
        ydwdTopItemView.add(view.findViewById(R.id.layout_ldsh_eight));
        ydwdTopItemView.add(view.findViewById(R.id.layout_ldsh_nine));
        ydwdTopItemView.add(view.findViewById(R.id.layout_ldsh_ten));

    }

    @Override
    protected void refreshOdds() {
        getData();
    }

    private void getData() {
//        requestOdds(mActivity.getLotteryType(), currentPlayTypeCode);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        currentOddBeans = data;
        showOdds();
        setupLmpOddsView(ydwdTopItemView, currentOddBeans);
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
     * 两面盘赔率设置
     *
     * @param lmpTopItemViews
     * @param currentOddBeans
     */
    private void setupLmpOddsView(List<View> lmpTopItemViews, List<NewOddsBean> currentOddBeans) {
        if (null == currentOddBeans || null == lmpTopItemViews || null == getActivity()) {
            return;
        }
        int size = lmpTopItemViews.size();
        int size2 = currentOddBeans.size();
        //数据不对应的时间会导致空指针
        if (size != size2) {
            return;
        }
        for (int i = 0; i < size; i++) {
            View topItemView = lmpTopItemViews.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            if (null != getActivity()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView btn = (TextView) topItemView.findViewById(R.id.top_item_name);
                        btn.setText(oneBean.getName());
                        CustomListView listView = (CustomListView) topItemView.findViewById(R.id.listViewItems);
                        adapter = new BetItemAdapter(oneBean.getList(), getActivity(), PK10Fragment4.this, IsFeng);
                        listView.setAdapter(adapter);
                        listView.setFocusable(false);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        }
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
            for (int i = 0; i < topItembeans.size(); i++) {
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
                clearbettingseleter();
            }
        });
        mDialog.show();
    }

    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(currentPlayTypeCode));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), LotteryId.ROUND, ""));
        //选中的
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean oneBetItem = selectedBeans.get(i);
            betParams.put(oneBetItem.getKey(), bet_money);
        }
        betParams.put("oid", SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        LogUtil.e("=====请求参数是=====" + (new JSONObject(betParams)).toString());
        requestBet(body);
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
            setupLmpOddsView(ydwdTopItemView, currentOddBeans);
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
        setupLmpOddsView(ydwdTopItemView, currentOddBeans);
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
