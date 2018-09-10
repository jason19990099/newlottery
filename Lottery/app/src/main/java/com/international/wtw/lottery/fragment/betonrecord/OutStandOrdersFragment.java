package com.international.wtw.lottery.fragment.betonrecord;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.OutStandAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpLoggingInterceptor;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.SwitchPagePopup;
import com.international.wtw.lottery.json.SummaryDetailsBean;
import com.international.wtw.lottery.json.SummaryDetailsResBean;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OutStandOrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    public int PAGE_SIZE = 20;
    private View view;
    private ListView lv_outstand;
    private OutStandAdapter outStandAdapter;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outstandorder, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.color_primary));

        lv_outstand = (ListView) view.findViewById(R.id.lv_outstand);
        fl_no_deposit = (FrameLayout) view.findViewById(R.id.fl_no_deposit);
        tv_click_retry = (TextView) view.findViewById(R.id.tv_click_retry);
        llCurrentPage = (LinearLayout) view.findViewById(R.id.llCurrentPage);
        tvCurrentPage = (TextView) view.findViewById(R.id.tvCurrentPage);
        tvPreviousPage = (TextView) view.findViewById(R.id.tvPreviousPage);
        tvNextPage = (TextView) view.findViewById(R.id.tvNextPage);
        rlBottom = (RelativeLayout) view.findViewById(R.id.rlBottom);

        tv_click_retry.setOnClickListener(this);
        llCurrentPage.setOnClickListener(this);
        tvPreviousPage.setOnClickListener(this);
        tvNextPage.setOnClickListener(this);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        SetData(time, page, 1);

        return view;
    }


    private void SetData(String datetime, int page, int flag) {
        String login_oid = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, null);
        HttpRequest.getInstance().getOutStandHave(getActivity(), login_oid, page, "20", "0", "", 0, new HttpCallback<SummaryDetailsBean>() {
            @Override
            public void onSuccess(SummaryDetailsBean data) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                summaryDetailsResBeanList = data.getRes();

                initPages(Integer.parseInt(data.getPage().getAllnmb()));

                if (summaryDetailsResBeanList.size() != 0) {
                    outStandAdapter = new OutStandAdapter(getActivity(), summaryDetailsResBeanList);
                    lv_outstand.setAdapter(outStandAdapter);
                } else {
                    if (flag != 0) {
                        lv_outstand.setVisibility(View.GONE);
                        fl_no_deposit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                lv_outstand.setVisibility(View.GONE);
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
        String date = sdf.format(new java.util.Date());
        String time = getTime(date);
        SetData(time, page, 1);
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
            case R.id.tv_click_retry:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new java.util.Date());
                String time = getTime(date);
                LogUtil.e("OutStandOrdersFragment---date -*-*-*-" + date + "-*-*-*-" + time);
                SetData(time, page, 1);
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
            pagePopup = new SwitchPagePopup(getActivity())
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
