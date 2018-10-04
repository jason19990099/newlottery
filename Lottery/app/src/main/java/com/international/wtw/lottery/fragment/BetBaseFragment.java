package com.international.wtw.lottery.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.ArrayCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.app.BetBaseActivity;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.json.BetResultBean;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.MoneyInfoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.RequestBody;

/**
 * 下注页面, 把一些方法抽出来..!
 */
public abstract class BetBaseFragment extends BaseFragment  {

    protected View rootView;
    protected FrameLayout mFlOddsContainer;
    protected LinearLayout mLlErrorView;
    private TextView mTvError;
    protected BetBaseActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BetBaseActivity) getActivity();
        rootView = inflater.inflate(R.layout.fragment_bet_base, container, false);
        mFlOddsContainer = (FrameLayout) rootView.findViewById(R.id.fl_odds_container);
        mLlErrorView = (LinearLayout) rootView.findViewById(R.id.ll_error_view);
        mTvError = (TextView) rootView.findViewById(R.id.tv_error);
        mLlErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                mLlErrorView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshOdds();
                    }
                }, 1000);

            }
        });
        return rootView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    protected void showOdds() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mFlOddsContainer.setVisibility(View.VISIBLE);
                mLlErrorView.setVisibility(View.GONE);
            }
        });
    }

    protected void showErrorView(String errorMsg) {
        mFlOddsContainer.setVisibility(View.GONE);
        mLlErrorView.setVisibility(View.VISIBLE);
        mTvError.setText(errorMsg);
    }

    protected void requestOdds(int gameCode, int typeCode) {
        showLoadingDialog();
        HttpRequest.getInstance().requestOdds2(this, gameCode, typeCode, new ArrayCallback<List<NewOddsBean>>() {
            @Override
            public void onSuccess(List<NewOddsBean> data) {
                dismissDialog();
                onGetOdds(data);
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                dismissDialog();
                ToastDialog.error(errorMsg).show(getFragmentManager());
            }
        });
    }

    protected void requestBet(RequestBody body) {
        showLoadingDialog();
        HttpRequest.getInstance().requestBet(this, body, new ArrayCallback<List<BetResultBean>>() {
            @Override
            public void onSuccess(List<BetResultBean> data) {
                dismissDialog();
                ToastDialog.success(getString(R.string.congratulations_on_your_betting_success)).show(getFragmentManager());
                MoneyInfoManager.get().requestMoneyInfo();
                if (data != null && !data.isEmpty()) {
                    //下注成功后更新token
                    MemoryCacheManager.getInstance().putToken(data.get(0).getToken());
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                dismissDialog();
                ToastDialog.error(errorMsg).show(getFragmentManager());
                //下注失败后将token置空
                MemoryCacheManager.getInstance().putToken(null);
            }
        });
    }

    protected abstract void onGetOdds(List<NewOddsBean> data);

    protected abstract void refreshOdds();


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
