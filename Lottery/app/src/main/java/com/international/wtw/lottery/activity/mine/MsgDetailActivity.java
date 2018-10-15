package com.international.wtw.lottery.activity.mine;

import android.view.View;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.newJason.MessageDetailModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.utils.TImeUtil;


public class MsgDetailActivity extends BaseActivity {
    private TextView tv_time, tv_title;
    private com.international.wtw.lottery.utils.MyTextView tv_context;
    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msgdetail;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        id = getIntent().getStringExtra("id");
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_context = (com.international.wtw.lottery.utils.MyTextView) findViewById(R.id.tv_context);
        tv_title = (TextView) findViewById(R.id.tv_title);


        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getMessageDrtail();
    }

    /**
     *  获取信息详情
     */
    private void getMessageDrtail() {
        String token = SharePreferencesUtil.getString(MsgDetailActivity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getMessageDetail(MsgDetailActivity.this,token,id,new HttpCallback<MessageDetailModel>() {
            @Override
            public void onSuccess(MessageDetailModel data) {
                tv_title.setText(data.getData().getTitle());
                tv_context.setText(data.getData().getContent());
                tv_time.setText(TImeUtil.getDateStringByTimeSTamp(data.getData().getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });
    }



}
