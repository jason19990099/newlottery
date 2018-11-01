package com.international.wtw.lottery.fragment.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.Newlottery.Bjscpk10Activity;
import com.international.wtw.lottery.activity.lottery.Newlottery.MiaosufeitingActivity;
import com.international.wtw.lottery.activity.lottery.Newlottery.MiaosusaicheActivity;
import com.international.wtw.lottery.activity.lottery.Newlottery.MiaosusscActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.newJason.GameOpentimeModel2;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.TimeFormatter;
import com.international.wtw.lottery.widget.MarqueeView;
import com.international.wtw.lottery.widget.RecyclerViewDivider;
import com.international.wtw.lottery.widget.TitleBar;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;

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
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.marquee)
    MarqueeView marquee;
    private BaseQuickAdapter<GameOpentimeModel2.DataBean, BaseViewHolder> mAdapter;
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

        marquee.setText("欢迎加入爱购彩，祝您游戏开心。。、");
        marquee.start();

        mHandler = new Handler();
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapter = new BaseQuickAdapter<GameOpentimeModel2.DataBean, BaseViewHolder>(R.layout.lottery_hanll) {
            @Override
            protected void convert(BaseViewHolder helper, GameOpentimeModel2.DataBean item) {
                helper.setText(R.id.tv_lotteryname, item.getGameCode());

                int openTime = (int) (item.getCloseTime() - item.getServerTime());
                if (openTime > 0) {
                    helper.setText(R.id.tv_lotterytime, "距截止:" + TimeFormatter.seconds2Time(openTime));
                } else {
                    helper.setText(R.id.tv_lotterytime, "开奖中");
                }
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
                if (!payloads.isEmpty()) {
                    int openTime = (int) payloads.get(0);
                    if (openTime > 0) {
                        holder.setText(R.id.tv_lotterytime, "距截止:" + TimeFormatter.seconds2Time(openTime));
                    } else {
                        holder.setText(R.id.tv_lotterytime, "开奖中");
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
                GameOpentimeModel2.DataBean item = (GameOpentimeModel2.DataBean) adapter.getItem(position);
                if (item == null) {
                    return;
                }
//                Intent intent = new Intent(mActivity, LotteryHistoryActivity.class);
//                intent.putExtra(LotteryId.GAME_CODE, item.getGameCode());
//                startActivity(intent);
                if (null == getActivity())
                    return;
                Intent intent = null;
                switch (item.getGameCode()) {
                    case LotteryId.Miaosusscai:   //秒速时时彩
                        intent = new Intent(getActivity(), MiaosusscActivity.class);
                        intent.putExtra("lotteryname", "秒速时时彩");
                        break;
                    case LotteryId.Miaosufeiting:  //秒速飞艇
                        intent = new Intent(getActivity(), MiaosufeitingActivity.class);
                        intent.putExtra("lotteryname", "秒速飞艇");
                        break;
                    case LotteryId.BJSCPK10:  //北京赛车PK10
                        intent = new Intent(getActivity(), Bjscpk10Activity.class);
                        intent.putExtra("lotteryname", "北京赛车PK10");
                        break;
                    case LotteryId.MiaosuSaiche:  //秒速赛车
                        intent = new Intent(getActivity(), MiaosusaicheActivity.class);
                        intent.putExtra("lotteryname", "秒速赛车");
                        break;
                }
                getActivity().startActivity(intent);
            }
        });
        initListener();
    }


    private void initListener() {
        mTitleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMenuPopup == null) {
                    mMenuPopup = new MenuPopupWindow(getActivity()).createPopup();
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
        String token = SharePreferencesUtil.getString(getContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().getGameOpenTime2(this, token, "", new HttpCallback<GameOpentimeModel2>() {
            @Override
            public void onSuccess(GameOpentimeModel2 data) {
                if (getActivity() != null && isAdded()) {
                    mHandler.removeCallbacks(mCountDownAction);
                    mSwipeRefreshLayout.setRefreshing(false);
                    List<GameOpentimeModel2.DataBean> dates = data.getData();
                    //去除控的选项
                    if (dates != null && !dates.isEmpty()) {
                        Iterator<GameOpentimeModel2.DataBean> iterator = dates.iterator();
                        while (iterator.hasNext()) {
                            GameOpentimeModel2.DataBean item = iterator.next();
                            if (item == null) {
                                iterator.remove();
                            }
                        }
                        mAdapter.setNewData(dates);
                        startCountDown(dates);
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


    private void startCountDown(List<GameOpentimeModel2.DataBean> list) {
        if (null == list)
            return;
        int[] openTime = new int[list.size()];
        int[] isOpen = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            GameOpentimeModel2.DataBean item = list.get(i);
            openTime[i] = (int) (item.getCloseTime() - item.getServerTime());

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
                    if (openTime[i] > 0) {
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
