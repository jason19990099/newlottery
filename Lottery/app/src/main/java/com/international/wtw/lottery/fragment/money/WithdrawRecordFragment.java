package com.international.wtw.lottery.fragment.money;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.TradeRecordAdapter;
import com.international.wtw.lottery.adapter.TradeRecordAdapter2;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.RecordDetailDialog;
import com.international.wtw.lottery.json.TransactionRecord;
import com.international.wtw.lottery.newJason.LoginModel;
import com.international.wtw.lottery.newJason.PayinRecordMoudel;
import com.international.wtw.lottery.newJason.WithdrawRecordModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 描述 ：取款记录
 */

public class WithdrawRecordFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    public static final String GAME_NAME = "game_name";
    public static final int PAGE_SIZE = 30;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int pageIndex = 1;
    private TradeRecordAdapter2 mAdapter;
    private boolean isPrepare;

    public static WithdrawRecordFragment newInstance(String gameName) {
        WithdrawRecordFragment fragment = new WithdrawRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GAME_NAME, gameName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade_record;
    }

    @Override
    protected void initData() {
        isPrepare = true;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TradeRecordAdapter2();
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerView.getParent());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                RecordDetailDialog detailDialog = new RecordDetailDialog(mActivity, mAdapter.getItem(position));
//                detailDialog.show();
            }
        });

        requestTransactionRecord();
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        mSwipeRefreshLayout.setRefreshing(true);
        requestTransactionRecord();
    }

    @Override
    public void onLoadMoreRequested() {
        pageIndex++;
        requestTransactionRecord();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isPrepare) {
            onRefresh();
        }
    }

    /**
     * 获取取款记录
     */
    private void requestTransactionRecord() {
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getWithdrawRecord(this, token, pageIndex, PAGE_SIZE, new HttpCallback<WithdrawRecordModel>() {
            @Override
            public void onSuccess(WithdrawRecordModel data) throws Exception {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                List<WithdrawRecordModel.DataBean> res = data.getData();
                if (res.size() < PAGE_SIZE ) {
                    mAdapter.loadMoreEnd();
                } else {
                    mAdapter.loadMoreComplete();
                }
                if (pageIndex == 1) {
                    mAdapter.setNewData(res);
                    mAdapter.disableLoadMoreIfNotFullPage();
                } else {
                    mAdapter.addData(res);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });
    }
}
