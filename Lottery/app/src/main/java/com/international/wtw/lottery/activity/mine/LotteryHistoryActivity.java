package com.international.wtw.lottery.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.LotteryHistoryAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.CalendarDialog;
import com.international.wtw.lottery.dialog.SwitchPagePopup;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.newJason.LotteryHistoryModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.RecyclerViewDivider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;


/**
 *  开奖历史
 */
public class LotteryHistoryActivity extends NewBaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final int PAGE_SIZE = 20;
    @BindView(R.id.tv_bet_title)
    TextView mTvBetTitle;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_calendar)
    ImageView mIvCalendar;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.rlBottom)
    RelativeLayout rlBottom;
    @BindView(R.id.llCurrentPage)
    LinearLayout llCurrentPage;
    @BindView(R.id.tvCurrentPage)
    TextView tvCurrentPage;
    @BindView(R.id.tvPreviousPage)
    TextView tvPreviousPage;
    @BindView(R.id.tvNextPage)
    TextView tvNextPage;
    private View mErrorView;
    private TextView mTvError;
    private String mGameCode,mGameName;
    private int pageIndex = 1;
    private String dateStr;
    private LotteryHistoryAdapter mAdapter;
    private BaseNiceDialog mCalendarDialog;
    private int mPageCount = 1;
    private SwitchPagePopup pagePopup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottery_history;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mGameCode = getIntent().getStringExtra(LotteryId.GAME_CODE);
        mGameName= getIntent().getStringExtra(LotteryId.GAME_NAME);
        initTitle();
        initEmptyView();
        initRecycler();
        //获取历史开奖数据
        refresh();
    }

    @Override
    protected boolean useEventBus() {
        return  false;
    }

    private void initTitle() {
        mTvBetTitle.setText(mGameName);
//        if (mGameCode == Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY) {
//            mIvCalendar.setVisibility(View.GONE);
//        }

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        dateStr=sdf.format(d);
    }

    private void initRecycler() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(), R.color.color_primary));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
        mAdapter = new LotteryHistoryAdapter(mGameCode);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initEmptyView() {
        mErrorView = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        mTvError = (TextView) mErrorView.findViewById(R.id.tv_error);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.ll_title, R.id.tv_calendar, R.id.tvPreviousPage, R.id.tvNextPage, R.id.llCurrentPage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_title:
                showSwitchGamePopup();
                break;
            case R.id.tv_calendar:
                if (mCalendarDialog == null) {
                    mCalendarDialog = CalendarDialog.newInstance(new CalendarDialog.DateSelectedListener() {
                        @Override
                        public void onDateSelected(Date date) {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                            dateStr = df.format(date);
                            mTvDate.setText(dateStr);
                            refresh();
                        }
                    }).setMargin(40);
                }
                mCalendarDialog.showDialog(getSupportFragmentManager());
                break;
            case R.id.tvPreviousPage:
                pageIndex--;
                setPageStatus();
                break;
            case R.id.tvNextPage:
                pageIndex++;
                setPageStatus();
                break;
            case R.id.llCurrentPage:
                showSwitchPagePopup();
                break;
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        pageIndex = 1;
        setPageStatus();
    }

    private void setPageStatus() {
        checkStatus();
        tvCurrentPage.setText(String.format(Locale.CHINA, "第%d页", pageIndex));
        mSwipeRefreshLayout.setRefreshing(true);
        requestLotteryHistory();
    }

    private void checkStatus() {
        if (pageIndex == 1 && tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(false);
        } else if (pageIndex != 1 && !tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(true);
        }
        if (pageIndex == mPageCount && tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(false);
        } else if (pageIndex != mPageCount && !tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(true);
        }
        if (mPageCount <= 1 && llCurrentPage.isEnabled()) {
            llCurrentPage.setEnabled(false);
        } else if (mPageCount > 1 && !llCurrentPage.isEnabled()) {
            llCurrentPage.setEnabled(true);
        }
    }

    private void requestLotteryHistory() {
        String token = SharePreferencesUtil.getString(LotteryHistoryActivity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance()
                .getCollectResultByPages(this,token, mGameCode,dateStr, pageIndex, PAGE_SIZE,  new HttpCallback<LotteryHistoryModel>() {
                    @Override
                    public void onSuccess(LotteryHistoryModel data) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        mAdapter.setNewData(data.getData());
                        mRecyclerView.scrollToPosition(0);
                        initPages(data.getCount());
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (pageIndex == 1) {
                            mTvError.setText(errorMsg);
                            mAdapter.setEmptyView(mErrorView);
                        } else {
                            mAdapter.loadMoreFail();
                        }
                    }
                });
    }

    private void initPages(int allnumb) {
        mPageCount = allnumb % PAGE_SIZE == 0 ? allnumb / PAGE_SIZE : allnumb / PAGE_SIZE + 1;
        checkStatus();
    }

    private void showSwitchGamePopup() {
//        new SwitchGamePopupWindow2(this, mGameCode)
//                .setOnItemClickListener(new SwitchGamePopupWindow2.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int gameCode) {
//
//                    }
//
//                    @Override
//                    public void onItemClick(String gameCode) {
//                        getIntent().putExtra(LotteryId.GAME_CODE, gameCode);
//                        recreate();
//                    }
//                })
//                .createPopup()
//                .showAtAnchorView(mLlTitle, VerticalGravity.BELOW, HorizontalGravity.CENTER);
    }

    private void showSwitchPagePopup() {
        if (pagePopup == null) {
            pagePopup = new SwitchPagePopup(this)
                    .setOnItemClickListener(new SwitchPagePopup.OnItemClickListener() {
                        @Override
                        public void onPreviousPage() {
                            pageIndex--;
                            setPageStatus();
                        }

                        @Override
                        public void onNextPage() {
                            pageIndex++;
                            setPageStatus();
                        }

                        @Override
                        public void onItemClick(int currentPage) {
                            pageIndex = currentPage;
                            setPageStatus();
                        }
                    })
                    .createPopup();
        }
        pagePopup.setCurrentPage(pageIndex, mPageCount);
        pagePopup.showAtLocation(rlBottom, Gravity.BOTTOM, 0, 0);
    }


}
