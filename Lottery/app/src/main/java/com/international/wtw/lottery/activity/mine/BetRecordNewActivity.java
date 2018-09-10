package com.international.wtw.lottery.activity.mine;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.HasAdapter;
import com.international.wtw.lottery.adapter.OutStandAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.SwitchPagePopup;
import com.international.wtw.lottery.json.SummaryDetailsBean;
import com.international.wtw.lottery.json.SummaryDetailsResBean;
import com.international.wtw.lottery.utils.SharePreferencesUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by XIAOYAN on 2017/9/28.
 */

public class BetRecordNewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public int PAGE_SIZE = 20;

    private ListView lv_bet_record;
    private ImageView iv_back;
    private TextView tv_rq;

    private OutStandAdapter outStandAdapter;
    private HasAdapter hasAdapter;

    private String type, date;

    private List<SummaryDetailsResBean> summaryDetailsResBeanList;

    private FrameLayout fl_no_deposit;

    private int page = 1;

    private TextView tv_click_retry;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwitchPagePopup pagePopup;
    private LinearLayout llCurrentPage;
    private TextView tvCurrentPage;
    private TextView tvPreviousPage, tvNextPage;
    private int mPageCount = 1;
    private RelativeLayout rlBottom;

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_betrecord_new;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.color_primary));

        lv_bet_record = (ListView) findViewById(R.id.lv_bet_record);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_rq = (TextView) findViewById(R.id.tv_rq);
        fl_no_deposit = (FrameLayout) findViewById(R.id.fl_no_deposit);
        tv_click_retry = (TextView) findViewById(R.id.tv_click_retry);
        llCurrentPage = (LinearLayout) findViewById(R.id.llCurrentPage);
        tvCurrentPage = (TextView) findViewById(R.id.tvCurrentPage);
        tvPreviousPage = (TextView) findViewById(R.id.tvPreviousPage);
        tvNextPage = (TextView) findViewById(R.id.tvNextPage);
        rlBottom = (RelativeLayout) findViewById(R.id.rlBottom);

        iv_back.setOnClickListener(this);
        tv_click_retry.setOnClickListener(this);
        llCurrentPage.setOnClickListener(this);
        tvPreviousPage.setOnClickListener(this);
        tvNextPage.setOnClickListener(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("time");
        tv_rq.setText(date);

        String time = getTime(date);
        SetData1(type, time, page, 1);

    }

    private void SetData1(String type, String datetime, int page, int flag) {
        String login_oid = SharePreferencesUtil.getString(BetRecordNewActivity.this, LotteryId.Login_oid, null);
        HttpRequest.getInstance().getOutStandHave(BetRecordNewActivity.this, login_oid, page, "20", type, datetime, 0, new HttpCallback<SummaryDetailsBean>() {
            @Override
            public void onSuccess(SummaryDetailsBean data) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                summaryDetailsResBeanList = data.getRes();

                initPages(Integer.parseInt(data.getPage().getAllnmb()));

                if (summaryDetailsResBeanList.size() != 0) {
                    if (type.equals("0")) {
                        outStandAdapter = new OutStandAdapter(BetRecordNewActivity.this, summaryDetailsResBeanList);
                        lv_bet_record.setAdapter(outStandAdapter);
                    } else if (type.equals("1")) {
                        hasAdapter = new HasAdapter(BetRecordNewActivity.this, summaryDetailsResBeanList);
                        lv_bet_record.setAdapter(hasAdapter);
                    }
                } else {
                    if (flag != 0) {
                        lv_bet_record.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                lv_bet_record.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });
    }

    public String getTime(String timeString) {
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            long l = d.getTime() / 1000;
            timeStamp = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        page = 1;
        setPageStatus();
    }

    private void setPageStatus() {
        checkStatus();
        tvCurrentPage.setText(String.format(Locale.CHINA, "第%d页", page));
        swipeRefreshLayout.setRefreshing(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        String time = getTime(date);
        SetData1(type, time, page, 1);
    }

    private void checkStatus() {
        if (page == 1 && tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(false);
        } else if (page != 1 && !tvPreviousPage.isEnabled()) {
            tvPreviousPage.setEnabled(true);
        }
        if (page == mPageCount && tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(false);
        } else if (page != mPageCount && !tvNextPage.isEnabled()) {
            tvNextPage.setEnabled(true);
        }
        if (mPageCount <= 1 && llCurrentPage.isEnabled()) {
            llCurrentPage.setEnabled(false);
        } else if (mPageCount > 1 && !llCurrentPage.isEnabled()) {
            llCurrentPage.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_click_retry:
                String time = getTime(date);
                SetData1(type, time, page, 1);
                break;
            case R.id.llCurrentPage:
                showSwitchPagePopup();
                break;
            case R.id.tvPreviousPage:
                page--;
                setPageStatus();
                break;
            case R.id.tvNextPage:
                page++;
                setPageStatus();
                break;
        }
    }

    private void initPages(int allnumb) {
        mPageCount = allnumb % PAGE_SIZE == 0 ? allnumb / PAGE_SIZE : allnumb / PAGE_SIZE + 1;
        checkStatus();
    }

    private void showSwitchPagePopup() {
        if (pagePopup == null) {
            pagePopup = new SwitchPagePopup(this)
                    .setOnItemClickListener(new SwitchPagePopup.OnItemClickListener() {
                        @Override
                        public void onPreviousPage() {
                            page--;
                            setPageStatus();
                        }

                        @Override
                        public void onNextPage() {
                            page++;
                            setPageStatus();
                        }

                        @Override
                        public void onItemClick(int currentPage) {
                            page = currentPage;
                            setPageStatus();
                        }
                    })
                    .createPopup();
        }
        pagePopup.setCurrentPage(page, mPageCount);
        pagePopup.showAtLocation(rlBottom, Gravity.BOTTOM, 0, 0);
    }

}
