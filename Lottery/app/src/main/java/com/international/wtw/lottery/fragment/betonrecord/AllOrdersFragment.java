package com.international.wtw.lottery.fragment.betonrecord;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.BetRecordNewActivity;
import com.international.wtw.lottery.adapter.AllOutStandAdapter;
import com.international.wtw.lottery.adapter.AllHasAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpLoggingInterceptor;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.view.CustomListView;
import com.international.wtw.lottery.json.HasCloseBean;
import com.international.wtw.lottery.json.SummaryBean;
import com.international.wtw.lottery.json.UnSettlementBean;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class AllOrdersFragment extends Fragment {
    private View view;
    private CustomListView lv_bet_outstand, lv_bet_has;
    private ScrollView slv_no_deposit;
    private FrameLayout fl_no_deposit;
    private TextView tv_click_retry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_allorder, container, false);

        lv_bet_outstand = (CustomListView) view.findViewById(R.id.lv_bet_outstand);
        lv_bet_has = (CustomListView) view.findViewById(R.id.lv_bet_has);

        slv_no_deposit = (ScrollView) view.findViewById(R.id.slv_no_deposit);
        fl_no_deposit = (FrameLayout) view.findViewById(R.id.fl_no_deposit);

        SetData();

        tv_click_retry = (TextView) view.findViewById(R.id.tv_click_retry);
        tv_click_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetData();
            }
        });

        return view;
    }



    private void SetData() {
        String login_oid = SharePreferencesUtil.getString(getActivity(), LotteryId.Login_oid, null);
        HttpRequest.getInstance().getSummary(getActivity(), login_oid, new HttpCallback<SummaryBean>() {
            @Override
            public void onSuccess(SummaryBean data) {
                List<UnSettlementBean> unSettlement = data.getSummary().getUnSettlement();

                List<HasCloseBean> hasClose = data.getSummary().getHasClose();

                if (unSettlement.size() != 0 || hasClose.size() != 0) {
                    AllOutStandAdapter outStandAdapter = new AllOutStandAdapter(getActivity(), unSettlement);
                    lv_bet_outstand.setAdapter(outStandAdapter);
                    lv_bet_outstand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent_outstand = new Intent(getActivity(), BetRecordNewActivity.class);
                            intent_outstand.putExtra("type", "0");
                            intent_outstand.putExtra("time", unSettlement.get(position).getDateTime());
                            startActivity(intent_outstand);
                        }
                    });

                    AllHasAdapter hasAdapter = new AllHasAdapter(getActivity(), hasClose);
                    lv_bet_has.setAdapter(hasAdapter);
                    lv_bet_has.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent_has = new Intent(getActivity(), BetRecordNewActivity.class);
                            intent_has.putExtra("type", "1");
                            intent_has.putExtra("time", hasClose.get(position).getDateTime());
                            startActivity(intent_has);
                        }
                    });
                } else {
                    slv_no_deposit.setVisibility(View.GONE);
                    fl_no_deposit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                slv_no_deposit.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });
    }

}
