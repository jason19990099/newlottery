package com.international.wtw.lottery.activity.first;

import android.view.View;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;


/**
 * Created by 18Steven on 2017/9/23.
 */

public class InfoDetailActivity extends BaseActivity {
    private TextView tv_time,tv_title;
    private com.international.wtw.lottery.utils.MyTextView tv_context;
    private String time, text;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_infodetail;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        time = getIntent().getStringExtra("time");
        text = getIntent().getStringExtra("text");
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_context = (com.international.wtw.lottery.utils.MyTextView) findViewById(R.id.tv_context);
        tv_time.setText(time);
        tv_title= (TextView)findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.wlecome));

        tv_context.setText(text);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
