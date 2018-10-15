package com.international.wtw.lottery.activity.mine;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.MyMessageAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.newJason.MessageModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import java.util.List;


public class MyMessageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_tltle_infor;
    private ListView listView;
    private FrameLayout fl_no_deposit;
    private String title;
    private MyMessageAdapter adapter;
    private TextView tv_click_retry;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        InitView();
        SetData();
    }

    private void InitView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_tltle_infor = (TextView) findViewById(R.id.tv_tltle_infor);
        listView = (ListView) findViewById(R.id.lv_info);
        fl_no_deposit = (FrameLayout) findViewById(R.id.fl_no_deposit);
        tv_click_retry = (TextView) findViewById(R.id.tv_click_retry);
        tv_click_retry.setOnClickListener(this);

        title = getIntent().getStringExtra("title");
        tv_tltle_infor.setText(title);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_click_retry:
                SetData();
                break;
        }
    }

    private void SetData() {
        String token=SharePreferencesUtil.getString(MyMessageActivity.this,LotteryId.TOKEN,"");
        HttpRequest.getInstance().getMessageList(MyMessageActivity.this, token,"1","100", new HttpCallback<MessageModel>() {
            @Override
            public void onSuccess(MessageModel data) {
                List<MessageModel.DataBean> list = data.getData();
                if (null != list && list.size() != 0) {
                    adapter = new MyMessageAdapter(MyMessageActivity.this, list);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setVisibility(View.VISIBLE);
                    fl_no_deposit.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    fl_no_deposit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                ToastDialog.error(errorMsg).show(getSupportFragmentManager());
                listView.setVisibility(View.GONE);
                fl_no_deposit.setVisibility(View.VISIBLE);
            }
        });
    }


}
