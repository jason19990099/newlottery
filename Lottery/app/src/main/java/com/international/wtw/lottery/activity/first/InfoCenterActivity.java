package com.international.wtw.lottery.activity.first;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.infoCenterAdaper;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.json.LunbotuBean;

/**
 * 消息中心
 */

public class InfoCenterActivity extends BaseActivity implements View.OnClickListener {
    private ListView listView;
    private infoCenterAdaper adaper;
    private LunbotuBean lunbotu;
    private TextView tv_tltle_infor;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_information_center;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        listView = (ListView) findViewById(R.id.lv_info);
        tv_tltle_infor = (TextView) findViewById(R.id.tv_tltle_infor);
        findViewById(R.id.iv_back).setOnClickListener(this);
        lunbotu = (LunbotuBean) getIntent().getSerializableExtra(LotteryId.LUN_BO_TU);
        String title = getIntent().getStringExtra("title");
        tv_tltle_infor.setText(title);
        if (null != lunbotu.getAnnouncements() && lunbotu.getAnnouncements().size() > 0) {
            adaper = new infoCenterAdaper(InfoCenterActivity.this, lunbotu.getAnnouncements());
            listView.setAdapter(adaper);
            adaper.notifyDataSetChanged();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
