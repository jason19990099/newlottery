package com.international.wtw.lottery.fragment.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.LotteryHistoryActivity;
import com.international.wtw.lottery.activity.mine.WebViewActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.json.HomeResultBean;
import com.international.wtw.lottery.json.HomeResultMsgBean;
import com.international.wtw.lottery.json.LotteryWebsite;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.widget.LotteryNumberView;
import com.international.wtw.lottery.widget.RecyclerViewDivider;
import com.international.wtw.lottery.widget.TitleBar;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 描述：开奖趋势
 * 主要逻辑: 每10秒轮询一次服务器获取数据, 因为上一期的开奖数据不会在这一期开始时马上刷出来,
 * 为了及时更新开奖数据, 暂时的想法是每15秒轮询1次服务器数据
 * 倒计时功能, 新建一个Runnable进行倒计时, 通过调用Adapter的notifyItemChanged(int position, Object payload)局部刷新数据
 */

public class TrendFragment extends NewBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_website)
    TextView mTvWebsite;
    @BindView(R.id.rl_website)
    RelativeLayout mRlWebsite;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private BaseQuickAdapter<HomeResultMsgBean, BaseViewHolder> mAdapter;
    private MenuPopupWindow mMenuPopup;
    private Handler mHandler;
    private Runnable mCountDownAction;
    private Runnable mPollingRunnable;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lottery_trend;
    }

    @Override
    protected void initData() {
        mHandler = new Handler();
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BaseQuickAdapter<HomeResultMsgBean, BaseViewHolder>(R.layout.item_lottery_hall) {
            @Override
            protected void convert(BaseViewHolder helper, HomeResultMsgBean item) {
                helper.setText(R.id.tv_game_name, item.getGame_name());
                helper.setText(R.id.tv_round_number, String.format(Locale.CHINESE, "%s期", item.getRound()));
                LotteryNumberView lotteryNumberView = helper.getView(R.id.lotteryNumberView);
                String[] numbers = item.getResult().split(" ");
                lotteryNumberView.setNumbers(item.getGame_code(), Arrays.asList(numbers));
                if (null != item.getIsOpen() && item.getIsOpen().equals("1") && !TextUtils.isEmpty(item.getTime()) && item.getServerTime() != null) {
                    int openTime = (int) (Long.parseLong(item.getTime()) - item.getServerTime());
                    if (openTime > 0) {
                        helper.setText(R.id.tv_lottery_time, TimeFormatter.seconds2Time(openTime));
                    } else {
                        helper.setText(R.id.tv_lottery_time, "封盘");
                    }
                } else {
                    helper.setText(R.id.tv_lottery_time, "封盘");
                }
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
                if (!payloads.isEmpty()) {
                    int openTime = (int) payloads.get(0);
                    if (openTime > 0) {
                        holder.setText(R.id.tv_lottery_time, TimeFormatter.seconds2Time(openTime));
                    } else {
                        holder.setText(R.id.tv_lottery_time, "封盘");
                    }
                } else {
                    super.onBindViewHolder(holder, position, payloads);
                }
            }
        };
        mAdapter.setHasStableIds(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeResultMsgBean item = (HomeResultMsgBean) adapter.getItem(position);
                if (item == null) {
                    return;
                }
                Intent intent = new Intent(mActivity, LotteryHistoryActivity.class);
                intent.putExtra(LotteryId.GAME_CODE, item.getGame_code());
                startActivity(intent);
            }
        });
        initListener();
        //获取开奖网链接
        requestLotteryWebsiteUrl();
    }

    private void requestLotteryWebsiteUrl() {
        HttpRequest.getInstance().getLotteryWebsite(this, new Callback<LotteryWebsite>() {
            @Override
            public void onResponse(Call<LotteryWebsite> call, Response<LotteryWebsite> response) {
                if (response.isSuccessful()) {
                    LotteryWebsite lotteryWebsite = response.body();
                    if (lotteryWebsite != null && !TextUtils.isEmpty(lotteryWebsite.domain)) {
                        String website = lotteryWebsite.domain
                                .replace("https://", "")
                                .replace("http://", "")
                                .replace("www.", "")
                                .replace("/", "");
                        mTvWebsite.setText(website);
                        mRlWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra(WebViewActivity.EXTRA_WEB_TITLE, "开奖网");
                                intent.putExtra(WebViewActivity.EXTRA_WEB_URL, lotteryWebsite.domain);
                                intent.putExtra(WebViewActivity.EXTRA_IS_THIRD, true);
                                intent.putExtra(WebViewActivity.EXTRA_HIDE_TITLE, true);
                                startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<LotteryWebsite> call, Throwable t) {

            }
        });
    }

    private void initListener() {
        mTitleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuPopup == null) {
                    mMenuPopup = new MenuPopupWindow(getActivity())
                            .createPopup();
                }
                mMenuPopup.showAtAnchorView(mTitleBar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        pollingServer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mPollingRunnable);
    }

    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //每次进入页面开始轮询服务器, 离开页面则停止轮询
        if (!hidden) {
            pollingServer();
        } else {
            stopPollingServer();
        }
    }

    @Override
    public void onRefresh() {
        pollingServer();
    }

    /**
     * 轮询服务器获取数据, 每10秒钟拉去数据
     */
    private void pollingServer() {
        requestGameCloseTime();
        if (mPollingRunnable != null)
            mHandler.removeCallbacks(mPollingRunnable);
        mPollingRunnable = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 15 * 1000);
                requestGameCloseTime();
            }
        };
        mHandler.postDelayed(mPollingRunnable, 15 * 1000);
    }

    private void stopPollingServer() {
        mHandler.removeCallbacks(mPollingRunnable);
    }

    private void requestGameCloseTime() {
        HttpRequest.getInstance().requestGameCloseTime(this, new HttpCallback<HomeResultBean>() {
            @Override
            public void onSuccess(HomeResultBean data) {
                if (getActivity() != null && isAdded()) {
                    mHandler.removeCallbacks(mCountDownAction);
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<HomeResultMsgBean> newData = data.getTimeStamp();
                    //Collections.sort(newData);

                    if (newData != null && !newData.isEmpty()) {
                        //这个接口返回的list里面某些item可能为null, 需要过滤掉.
                        Iterator<HomeResultMsgBean> iterator = newData.iterator();
                        while (iterator.hasNext()) {
                            HomeResultMsgBean item = iterator.next();
                            if (item == null) {
                                iterator.remove();
                            }
                        }
                        mAdapter.setNewData(newData);
                        startCountDown(newData);
                    }
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                if (getActivity() != null && isAdded()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void startCountDown(List<HomeResultMsgBean> list) {
        if (null == list)
            return;
        int[] openTime = new int[list.size()];
        int[] isOpen = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            HomeResultMsgBean item = list.get(i);
            isOpen[i] = Integer.valueOf(item.getIsOpen());
            if (isOpen[i] == 1) {
                openTime[i] = (int) (Long.parseLong(item.getTime()) - item.getServerTime());
            }
        }
        //倒计时Runnable
        mCountDownAction = new Runnable() {
            @Override
            public void run() {
                mHandler.postDelayed(this, 1000);
                //是否马上刷新
                boolean refresh = false;
                for (int i = 0; i < openTime.length; i++) {
                    //开盘状态才进行倒计时
                    if (isOpen[i] == 1) {
                        openTime[i]--;
                        mAdapter.notifyItemChanged(i, openTime[i]);
                        if (openTime[i] == 0) {
                            //其中一个倒计时变成0, 则刷新数据
                            refresh = true;
                        }
                    }
                }
                //马上刷新
                if (refresh) {
                    requestGameCloseTime();
                }
            }
        };
        mHandler.postDelayed(mCountDownAction, 1000);
    }
}
