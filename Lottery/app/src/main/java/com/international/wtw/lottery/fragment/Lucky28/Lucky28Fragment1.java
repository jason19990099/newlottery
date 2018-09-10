package com.international.wtw.lottery.fragment.Lucky28;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetItemAdapter;
import com.international.wtw.lottery.adapter.BetItemCopyAdapter;
import com.international.wtw.lottery.base.Constants;
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
 * Created by XIAOYAN on 2017/7/13.
 */

public class Lucky28Fragment1 extends BetBaseFragment implements BetItemAdapter.ItemBettingCallback, StateInterface, BetItemCopyAdapter.ItemBettingCallback {
    private View view;
    private List<View> mixTopItemView = new ArrayList<>();   //混合和波色
    private Gson gson = new Gson();
    private List<NewOddsBean> currentOddBeans;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private boolean IsFeng;
    private int mSelectNumber = 0;//选择投注时间四个参数的选中的数量
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.view_lucky28_hb, null);
        mFlOddsContainer.addView(view);
        initMixView();
        getData();

        return rootView;
    }

    private void initMixView() {
        mixTopItemView.clear();
        mixTopItemView.add(view.findViewById(R.id.layout_one));
        mixTopItemView.add(view.findViewById(R.id.layout_two));
    }

    @Override
    protected void refreshOdds() {
        getData();
    }


    private void getData() {
        requestOdds(mActivity.getLotteryType(), Constants.LUCKY_28_PLAY_TYPE_SERVER.MIX_TYPE);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        currentOddBeans = data;
        showOdds();
        setupMixOddsView(mixTopItemView, currentOddBeans);
    }

    /**
     * 混合和波色赔率设置
     *
     * @param mixTopItemViews
     * @param currentOddBeans
     */
    private void setupMixOddsView(List<View> mixTopItemViews, List<NewOddsBean> currentOddBeans) {
        if (null == mixTopItemViews || null == currentOddBeans) {
            return;
        }
        for (int i = 0; i < currentOddBeans.size(); i++) {
            View topItemView = mixTopItemViews.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            if (null != getActivity()) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        GridView gridView = (GridView) topItemView.findViewById(R.id.listViewItems);
                        BetItemAdapter adapter = new BetItemAdapter(oneBean.getList().subList(0, 11), getActivity(), Lucky28Fragment1.this, IsFeng);
                        gridView.setAdapter(adapter);
                        gridView.setFocusable(false);

                        GridView gridView1 = (GridView) view.findViewById(R.id.listViewItems1);
                        BetItemCopyAdapter adapter1 = new BetItemCopyAdapter(oneBean.getList().subList(11, 14), getActivity(), Lucky28Fragment1.this, IsFeng);
                        gridView1.setAdapter(adapter1);
                        gridView1.setFocusable(false);
                    }
                });
            }
        }
    }

    public void getBet() {
        if (null == getActivity())
            return;
        bet_money = SharePreferencesUtil.getString(getActivity(), LotteryId.LOTTERY_BET_MONEY, "0");
        LotteryId.BETTING_MONEY = bet_money;
        if (TextUtils.isEmpty(bet_money) || bet_money.equals("0")) {
            ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
            return;
        }
        selectedBeans = findTheSelectedBeans(currentOddBeans);
        int size = selectedBeans.size();
        if (selectedBeans == null || size == 0) {
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
                if (selectedBeans.size() != 0) {
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
     * 清除选中
     */
    public void clearbettingseleter() {
        selectedBeans = findTheSelectedBeans(currentOddBeans);
        int selectSize = selectedBeans.size();
        if (selectSize > 0) {
            for (int i = 0; i < selectSize; i++) {
                NewOddsBean.ListBean listBean = selectedBeans.get(i);
                listBean.setSelectedState(false);
            }
            selectedBeans.clear();
            setupMixOddsView(mixTopItemView, currentOddBeans);
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
        setupMixOddsView(mixTopItemView, currentOddBeans);
    }

    /**
     * 查找选中状态的BetItem
     *
     * @param topItembeans
     * @return
     */
    private List<NewOddsBean.ListBean> findTheSelectedBeans(List<NewOddsBean> topItembeans) {
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
        ArrayMap<String, String> betParams = new ArrayMap<>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(Constants.LUCKY_28_PLAY_TYPE_SERVER.MIX_TYPE));
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
    public void ItemClick() {
        mSelectNumber = 0;
        selectedBeans = findTheSelectedBeans(currentOddBeans);
        int size = selectedBeans.size();
        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(size);
        }
        for (int i = 0; i < size; i++) {
            if (selectedBeans.get(i).getName().equals(getString(R.string.dadan))
                    || selectedBeans.get(i).getName().equals(getString(R.string.xiaodan))
                    || selectedBeans.get(i).getName().equals(getString(R.string.dashuang))
                    || selectedBeans.get(i).getName().equals(getString(R.string.xiaoshuang))) {
                mSelectNumber++;
            }

        }
        if (mSelectNumber >= 2) {
            ToastDialog.error(getString(R.string.only_select_one)).show(getFragmentManager());
            clearbettingseleter();
            if (null != getActivity()) {
                ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
            }
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
