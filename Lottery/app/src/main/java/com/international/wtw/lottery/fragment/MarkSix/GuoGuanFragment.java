package com.international.wtw.lottery.fragment.MarkSix;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.MarkSixActivity;
import com.international.wtw.lottery.adapter.BetColorsItemAdapter;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.RecyclerViewDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.fragment.BetBaseFragment;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.listener.StateInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 过关
 * Created by a bin on 2017/7/13.
 */

public class GuoGuanFragment extends BetBaseFragment implements View.OnClickListener, BetColorsItemAdapter.OnItemClickListener, StateInterface {
    private View viewGuoGuan;
    private boolean isFeng;
    private List<NewOddsBean> guoGuanBeanList; //请求到的赔率数据
    private Button btn_bet;
    private TextView tv_bottom_num;
    private EditText et_betting_amount;
    private ScrollView scrollView;
    private LinearLayout ll_bottom_remove;
    private List<NewOddsBean.ListBean> selectedBeans = new ArrayList<>();
    private RecyclerView recycle_view1, recycle_view2, recycle_view3, recycle_view4, recycle_view5, recycle_view6;
    private BetColorsItemAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6;
    private List<View> guoGuanTopItemView = new ArrayList<View>();
    private static final int ODDS_SUCESSFULLY = 0x01;
    private MarkSixActivity sixActivity;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ODDS_SUCESSFULLY:
                    guoGuanBeanList = (List<NewOddsBean>) msg.obj;
                    setupGuoGuanOddsView(guoGuanBeanList);
                    break;
            }
        }
    };
    private RecyclerViewDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewGuoGuan = inflater.inflate(R.layout.view_six_zm_1_6, null);
        mFlOddsContainer.addView(viewGuoGuan);
        viewGuoGuan.findViewById(R.id.btn_bet).setOnClickListener(this);
        initViewGuoGuan();
        requestOddsInfo(12);
        return rootView;
    }

    /**
     * 初始化view
     */
    private void initViewGuoGuan() {
        guoGuanTopItemView.clear();
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_one));
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_two));
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_three));
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_four));
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_five));
        guoGuanTopItemView.add(viewGuoGuan.findViewById(R.id.ly_zm_six));

        et_betting_amount = (EditText) viewGuoGuan.findViewById(R.id.et_betting_amount);
        new EditTextTools(et_betting_amount, 6, 0);

        et_betting_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals("")) {
                    int i = Integer.parseInt(s1);
                    if (i > 100000) {
                        et_betting_amount.setText("100000");
                    }
                }
            }
        });

        btn_bet = (Button) viewGuoGuan.findViewById(R.id.btn_bet);
        ll_bottom_remove = (LinearLayout) viewGuoGuan.findViewById(R.id.ll_bottom_remove);
        ll_bottom_remove.setOnClickListener(this);
        tv_bottom_num = (TextView) viewGuoGuan.findViewById(R.id.tv_bottom_num);
        scrollView = (ScrollView) viewGuoGuan.findViewById(R.id.scrollView);
    }

    @Override
    protected void refreshOdds() {
        requestOddsInfo(12);
    }

    private void requestOddsInfo(int type_code) {
        requestOdds(mActivity.getLotteryType(), type_code);
    }

    @Override
    protected void onGetOdds(List<NewOddsBean> data) {
        guoGuanBeanList = data;
        setupGuoGuanOddsView(guoGuanBeanList);
        showOdds();
    }

    /**
     * 赔率设置
     *
     * @param currentOddBeans
     */
    private void setupGuoGuanOddsView(List<NewOddsBean> currentOddBeans) { //, int position
        if (et_betting_amount != null && tv_bottom_num != null) {
            et_betting_amount.setText(null);
            tv_bottom_num.setText(null);
            tv_bottom_num.setVisibility(View.GONE);
        }
        if (currentOddBeans == null)
            return;

        int size = guoGuanTopItemView.size();
        for (int i = 0; i < size; i++) {
            View topItemView = guoGuanTopItemView.get(i);
            NewOddsBean oneBean = currentOddBeans.get(i);
            TextView btn = (TextView) topItemView.findViewById(R.id.tv_ball_name);
            btn.setText(oneBean.getName());
            if (i == 0) {
                if (null == adapter1 && null != getActivity()) {
                    recycle_view1 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_one);
                    adapter1 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view1, false, isFeng, this);
                    recycle_view1.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view1.setAdapter(adapter1);
                    recycle_view1.setFocusable(false);
                } else {
                    adapter1.refreshData(oneBean.getList());
                }
                recycle_view1.setFocusable(false);
            } else if (i == 1) {
                if (null == adapter2 && null != getActivity()) {
                    recycle_view2 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_two);
                    adapter2 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view2, false, isFeng, this);
                    recycle_view2.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view2.setAdapter(adapter2);
                } else {
                    adapter2.refreshData(oneBean.getList());
                }
                recycle_view2.setFocusable(false);
            } else if (i == 2) {
                if (null == adapter3 && null != getActivity()) {
                    recycle_view3 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_three);
                    adapter3 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view3, false, isFeng, this);
                    recycle_view3.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view3.setAdapter(adapter3);
                } else {
                    adapter3.refreshData(oneBean.getList());
                }
                recycle_view3.setFocusable(false);
            } else if (i == 3) {
                if (null == adapter4 && null != getActivity()) {
                    recycle_view4 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_four);
                    adapter4 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view4, false, isFeng, this);
                    recycle_view4.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view4.setAdapter(adapter4);
                } else {
                    adapter4.refreshData(oneBean.getList());
                }
                recycle_view4.setFocusable(false);
            } else if (i == 4) {
                if (null == adapter5 && null != getActivity()) {
                    recycle_view5 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_five);
                    adapter5 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view5, false, isFeng, this);
                    recycle_view5.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view5.setAdapter(adapter5);
                } else {
                    adapter5.refreshData(oneBean.getList());
                }
                recycle_view5.setFocusable(false);
            } else if (i == 5) {
                if (null == adapter6 && null != getActivity()) {
                    recycle_view6 = (RecyclerView) topItemView.findViewById(R.id.recycle_view_six);
                    adapter6 = new BetColorsItemAdapter(this.getActivity(), R.layout.item_six_mark, oneBean.getList(), recycle_view6, false, isFeng, this);
                    recycle_view6.setLayoutManager(new LinearLayoutManager(GuoGuanFragment.this.getActivity()));
                    recycle_view6.setAdapter(adapter6);
                } else {
                    adapter6.refreshData(oneBean.getList());
                }
                recycle_view6.setFocusable(false);
            }
            scrollView.smoothScrollTo(0, 0);
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
        return findTheSelectedBeans(guoGuanBeanList);
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

    private void setGuoGuanParam(ArrayMap<String, String> betParams, List<NewOddsBean> beanList) {
        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        List<NewOddsBean.ListBean> betItemBeans = new ArrayList<>();

        for (int i = 0; i < beanList.size(); i++) {
            NewOddsBean oneTopBean = beanList.get(i);
            for (int j = 0; j < oneTopBean.getList().size(); j++) {
                NewOddsBean.ListBean oneBetItem = oneTopBean.getList().get(j);
                if (oneBetItem.getSelectedState()) {
                    betItemBeans.add(oneBetItem);
                }
            }
        }

        StringBuilder selectedPoints = new StringBuilder();
        for (int i = 0; i < betItemBeans.size(); i++) {
            NewOddsBean.ListBean b = betItemBeans.get(i);
            selectedPoints.append(betItemBeans.get(i).getKey().subSequence(3, b.getKey().length()));
            if (i < betItemBeans.size() - 1) {
                selectedPoints.append(",");
            }
        }

        // put param
        betParams.put(LotteryId.BET_NUMBER, selectedPoints.toString());
        betParams.put(LotteryId.BET_MONEY, et_betting_amount.getText().toString());
    }

    /**
     * 投注
     */
    private void getBettingData() {
        ArrayMap<String, String> betParams = new ArrayMap<String, String>();
        betParams.put(LotteryId.GAME_CODE, String.valueOf(mActivity.getLotteryType()));
        betParams.put(LotteryId.TYPE_CODE, String.valueOf(12));
        betParams.put(LotteryId.ROUND, SharePreferencesUtil.getString(getActivity(), "round", ""));
        setGuoGuanParam(betParams, guoGuanBeanList);
        //选中的
       /* for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean oneBetItem = selectedBeans.get(i);
            betParams.put(oneBetItem.getKey(), et_betting_amount.getText().toString());
        }*/
        betParams.put(LotteryId.OID, SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, ""));
        betParams.put(LotteryId.TOKEN, MemoryCacheManager.getInstance().getToken());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), (new JSONObject(betParams)).toString());
        LogUtil.e("=====请求参数是=====" + (new JSONObject(betParams)).toString());
        requestBet(body);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.sixActivity = (MarkSixActivity) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bet:
                String mon1 = et_betting_amount.getText().toString();
                this.sixActivity.setMoney(mon1);
                selectedBeans = findTheSelectedBeans();
                if (selectedBeans == null || selectedBeans.size() == 0) {
                    ToastDialog.error(getString(R.string.select_betting_item)).show(getFragmentManager());
                    return;
                }
                if (TextUtils.isEmpty(mon1)) {
                    ToastDialog.error(getString(R.string.type_in_money)).show(getFragmentManager());
                    return;
                }

                mDialog = new RecyclerViewDialog(getActivity(), selectedBeans, mon1, new RecyclerViewDialog.SureCallBack() {
                    @Override
                    public void onSure() {
                        getBettingData();
                        tv_bottom_num.setVisibility(View.GONE);
                        clearBettingSelect(true);
                    }

                    @Override
                    public void onCancel() {
                        clearBettingSelect(true);
                        tv_bottom_num.setVisibility(View.GONE);
                    }
                });
                mDialog.show();
                break;
            case R.id.ll_bottom_remove:

                selectedBeans = findTheSelectedBeans();
                if (selectedBeans.size() != 0) {
                    clearBettingSelect(true);
                }

                tv_bottom_num.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 清除选中
     */
    private void clearBettingSelect(boolean isClear) {
        selectedBeans = findTheSelectedBeans();
        if (null == selectedBeans || selectedBeans.size() == 0 && isClear)
            return;
        for (int i = 0; i < selectedBeans.size(); i++) {
            NewOddsBean.ListBean listBean = selectedBeans.get(i);
            listBean.setSelectedState(false);
            selectedBeans.clear();
        }
        if (adapter1 == null || adapter2 == null || adapter3 == null || adapter4 == null || adapter5 == null || adapter6 == null)
            return;
        adapter1.refreshData(guoGuanBeanList.get(0).getList());
        adapter2.refreshData(guoGuanBeanList.get(1).getList());
        adapter3.refreshData(guoGuanBeanList.get(2).getList());
        adapter4.refreshData(guoGuanBeanList.get(3).getList());
        adapter5.refreshData(guoGuanBeanList.get(4).getList());
        adapter6.refreshData(guoGuanBeanList.get(5).getList());

        //et_betting_amount.setText(null);
        tv_bottom_num.setText(null);
        tv_bottom_num.setVisibility(View.GONE);

        if (null != getActivity()) {
            ((ShowSelectNumbersInterface) getActivity()).showSelextNum(0);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getUserVisibleHint()) {
            setupGuoGuanOddsView(guoGuanBeanList);
        } else {
            if (null != et_betting_amount) {
                et_betting_amount.setText(sixActivity.getMoney());
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
        int size = findTheSelectedBeans().size();
        if (size > 0) {
            tv_bottom_num.setVisibility(View.VISIBLE);
            tv_bottom_num.setText(String.valueOf(size));
        } else if (size == 0) {
            tv_bottom_num.setText(null);
            tv_bottom_num.setVisibility(View.GONE);
        }
    }

    @Override
    public void open(Boolean b) {
        isFeng = b;
        if (tv_bottom_num != null)
            tv_bottom_num.setVisibility(View.GONE);
        if (btn_bet != null) {
            btn_bet.setEnabled(true);
            btn_bet.setBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.textcolortrue));
        }
        clearBettingSelect(false);
    }

    @Override
    public void close(Boolean b) {
        isFeng = b;
        if (tv_bottom_num != null)
            tv_bottom_num.setVisibility(View.GONE);
        if (btn_bet != null) {
            btn_bet.setEnabled(false);
            btn_bet.setBackgroundColor(ContextCompat.getColor(this.getActivity(), R.color.gray));
        }
        clearBettingSelect(false);
        if (b && mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != handler)
            handler.removeCallbacksAndMessages(null);
    }
}
