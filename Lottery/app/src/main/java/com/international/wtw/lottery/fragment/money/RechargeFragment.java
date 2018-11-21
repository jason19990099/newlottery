package com.international.wtw.lottery.fragment.money;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PaywayAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.newJason.PaymentMethodModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import butterknife.BindView;

/**
 * 充值
 */
public class RechargeFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String GAME_NAME = "game_name";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.list_payways)
    ListView listPayways;
    private boolean isPrepare;
    private String mGameName;
    private PaywayAdapter paywayAdapter;


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
        requestPayInWays();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            onRefresh();
        }
    }

    private void initRecycler() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        requestPayInWays();
    }

    private void requestPayInWays() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getPaymentMethod(this, token, new HttpCallback<PaymentMethodModel>() {
            @Override
            public void onSuccess(PaymentMethodModel data) {
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                paywayAdapter=new PaywayAdapter(getActivity(),data.getData());
                listPayways.setAdapter(paywayAdapter);
                paywayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                View emptyView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
                TextView tvNotice = (TextView) emptyView.findViewById(R.id.tv_error);
                tvNotice.setText(errorMsg);
            }
        });


    }

}
