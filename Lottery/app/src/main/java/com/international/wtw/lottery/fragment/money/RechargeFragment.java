package com.international.wtw.lottery.fragment.money;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.OfflinePaymentActivity;
import com.international.wtw.lottery.activity.manager.OnlinePaymentActivity;
import com.international.wtw.lottery.adapter.PayWaysAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.json.OfflinePayModel;
import com.international.wtw.lottery.json.OnlinePayChannel;
import com.international.wtw.lottery.json.OnlinePayModel;
import com.international.wtw.lottery.json.PayInModel;
import com.international.wtw.lottery.json.PayInTitle;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.RecyclerViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RechargeFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String GAME_NAME = "game_name";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private PayWaysAdapter mAdapter;
    private List<OfflinePayModel> mOfflinePayModels;
    private boolean isPrepare;
    private String mGameName;

    public static RechargeFragment newInstance(String gameName) {
        RechargeFragment fragment = new RechargeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void initData() {
        isPrepare = true;
        if (getArguments() != null) {
            mGameName = getArguments().getString(GAME_NAME);
        }
        initRecycler();
        mAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerView.getParent());
        requestPayInWays();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("RechargeFragment", isVisibleToUser + " setUserVisibleHint");
        if (isVisibleToUser && isPrepare) {
            onRefresh();
        }
    }

    private void initRecycler() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mActivity,
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mAdapter = new PayWaysAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemEntity item = mAdapter.getData().get(position);
                switch (item.getItemType()) {
                    case PayWaysAdapter.TYPE_ONLINE_PAY:
                        //在线支付
                        OnlinePayModel onlinePayItem = (OnlinePayModel) item;
                        Intent intent = new Intent(mActivity, OnlinePaymentActivity.class);
                        intent.putExtra(OnlinePaymentActivity.PAY_IN_DATA, onlinePayItem);
                        startActivity(intent);
                        break;
                    case PayWaysAdapter.TYPE_OFFLINE_PAY:
                        //线下支付
                        OfflinePayModel offlinePayModel = (OfflinePayModel) item;
                        Intent intent1 = new Intent(mActivity, OfflinePaymentActivity.class);
                        intent1.putExtra(OfflinePaymentActivity.SELECTED_ITEM, offlinePayModel);
                        intent1.putExtra(OfflinePaymentActivity.OFFLINE_PAYEE_INFO, (Serializable) mOfflinePayModels);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        requestPayInWays();
    }

    private void requestPayInWays() {
//        HttpRequest.getInstance().requestPayInWays(this, new HttpCallback<PayInModel>() {
//            @Override
//            public void onSuccess(PayInModel model) {
//                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//                dealPayInData(model);
//            }
//
//            @Override
//            public void onFailure(String msgCode, String errorMsg) {
//                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//                View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
//                TextView tvNotice = (TextView) emptyView.findViewById(R.id.tv_error);
//                tvNotice.setText(errorMsg);
//                emptyView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerView.getParent());
//                        requestPayInWays();
//                    }
//                });
//                mAdapter.setEmptyView(emptyView);
//            }
//        });

        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().recharge(this, token, "200", new HttpCallback<LoginModel>() {
            @Override
            public void onSuccess(LoginModel data) throws Exception {

            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });


    }

    /**
     * 接口返回的是些什么鬼数据, 还需要我们自己拼装.
     */
    private void dealPayInData(PayInModel data) {
        List<MultiItemEntity> payWays = new ArrayList<>();
        if (data.online_bankU != null && !data.online_bankU.isEmpty()) {
            OnlinePayModel model = new OnlinePayModel();
            model.type = 0;
            model.minMoney = data.moneylimit.bankmin;
            model.maxMoney = data.moneylimit.bankmax;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            model.channels = new ArrayList<>();
            for (int i = 0; i < data.online_bankU.size(); i++) {
                OnlinePayChannel channel = new OnlinePayChannel();
                channel.type = 0;
                channel.channelName = "通道" + (i + 1);
                channel.payUrl = data.online_bankU.get(i);
                channel.payId = data.online_bank.get(i);
                channel.largeLogoRes = R.drawable.img_logo_union_large;
                model.channels.add(channel);
            }
            payWays.add(model);
        }
        if (data.online_alipayU != null && !data.online_alipayU.isEmpty()) {
            OnlinePayModel model = new OnlinePayModel();
            model.type = 1;
            model.minMoney = data.moneylimit.alipaymin;
            model.maxMoney = data.moneylimit.alipaymax;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            model.channels = new ArrayList<>();
            for (int i = 0; i < data.online_alipayU.size(); i++) {
                OnlinePayChannel channel = new OnlinePayChannel();
                channel.type = 1;
                channel.channelName = "通道" + (i + 1);
                channel.payUrl = data.online_alipayU.get(i);
                channel.payId = data.online_alipay.get(i);
                channel.largeLogoRes = R.drawable.img_logo_alipay_large;
                model.channels.add(channel);
            }
            payWays.add(model);
        }
        if (data.online_wechatU != null && !data.online_wechatU.isEmpty()) {
            OnlinePayModel model = new OnlinePayModel();
            model.type = 2;
            model.minMoney = data.moneylimit.wechatmin;
            model.maxMoney = data.moneylimit.wechatmax;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            model.channels = new ArrayList<>();
            for (int i = 0; i < data.online_wechatU.size(); i++) {
                OnlinePayChannel channel = new OnlinePayChannel();
                channel.type = 2;
                channel.channelName = "通道" + (i + 1);
                channel.payUrl = data.online_wechatU.get(i);
                channel.payId = data.online_wechat.get(i);
                channel.largeLogoRes = R.drawable.img_logo_wecaht_large;
                model.channels.add(channel);
            }
            payWays.add(model);
        }
        if (data.online_cftU != null && !data.online_cftU.isEmpty()) {
            OnlinePayModel model = new OnlinePayModel();
            model.type = 3;
            model.minMoney = data.moneylimit.cftpaymin;
            model.maxMoney = data.moneylimit.cftpaymax;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            model.channels = new ArrayList<>();
            for (int i = 0; i < data.online_cftU.size(); i++) {
                OnlinePayChannel channel = new OnlinePayChannel();
                channel.type = 3;
                channel.channelName = "通道" + (i + 1);
                channel.payUrl = data.online_cftU.get(i);
                channel.payId = data.online_cft.get(i);
                channel.largeLogoRes = R.drawable.img_logo_cft_large;
                model.channels.add(channel);
            }
            payWays.add(model);
        }
        if (data.online_quickpayU != null && !data.online_quickpayU.isEmpty()) {
            OnlinePayModel model = new OnlinePayModel();
            model.type = 4;
            model.minMoney = data.moneylimit.quickpaymin;
            model.maxMoney = data.moneylimit.quickpaymax;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            model.channels = new ArrayList<>();
            for (int i = 0; i < data.online_quickpayU.size(); i++) {
                OnlinePayChannel channel = new OnlinePayChannel();
                channel.type = 4;
                channel.channelName = "通道" + (i + 1);
                channel.payUrl = data.online_quickpayU.get(i);
                channel.payId = data.online_quickpay.get(i);
                channel.largeLogoRes = R.drawable.img_logo_union_large;
                model.channels.add(channel);
            }
            payWays.add(model);
        }
        if (!payWays.isEmpty()) {
            payWays.add(0, new PayInTitle("在线支付", "Online Payment"));
        }

        mOfflinePayModels = new ArrayList<>();
        if (data.bankpay_array != null && !data.bankpay_array.isEmpty()) {
            OfflinePayModel model = data.bankpay_array.get(0);
            model.type = 0;
            model.minMoney = data.moneylimit.linedownmin;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            mOfflinePayModels.add(model);
        }
        if (data.alipay_array != null && !data.alipay_array.isEmpty()) {
            OfflinePayModel model = data.alipay_array.get(0);
            model.type = 1;
            model.minMoney = data.moneylimit.linedownmin;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            mOfflinePayModels.add(model);
        }
        if (data.wechat_array != null && !data.wechat_array.isEmpty()) {
            OfflinePayModel model = data.wechat_array.get(0);
            model.type = 2;
            model.minMoney = data.moneylimit.linedownmin;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            mOfflinePayModels.add(model);
        }
        if (data.cft_array != null && !data.cft_array.isEmpty()) {
            OfflinePayModel model = data.cft_array.get(0);
            model.type = 3;
            model.minMoney = data.moneylimit.linedownmin;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            mOfflinePayModels.add(model);
        }
        if (data.quickpay_array != null && !data.quickpay_array.isEmpty()) {
            OfflinePayModel model = data.quickpay_array.get(0);
            model.type = 3;
            model.minMoney = data.moneylimit.linedownmin;
            if (!TextUtils.isEmpty(mGameName)) {
                model.gameName = mGameName;
            }
            mOfflinePayModels.add(model);
        }
        if (mOfflinePayModels.size() > 0) {
            payWays.add(new PayInTitle("线下支付", "Offline Payment"));
            payWays.addAll(mOfflinePayModels);
        }

        mAdapter.setNewData(payWays);
    }
}
