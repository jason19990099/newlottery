package com.international.wtw.lottery.activity.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.TextUtils;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.utils.SpannableBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaintenanceActivity extends AppCompatActivity {

    public static final String TIME_BEGIN = "time_begin";
    public static final String TIME_END = "time_end";

    @BindView(R.id.tvNotice)
    TextView mTvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ButterKnife.bind(this);

        String begin = getIntent().getStringExtra(TIME_BEGIN);
        String end = getIntent().getStringExtra(TIME_END);
        if (!TextUtils.isEmpty(begin) && !TextUtils.isEmpty(end)) {
            setSpannableText(begin, end);
        }
    }

    private void setSpannableText(String begin, String end) {
        Spannable notifyText = SpannableBuilder.create(this)
                .append("为了保证您游戏的稳定和顺畅，为保证服务的稳定和质量，本站于", R.dimen.sp_12, R.color.white)
                .append(String.format("\"北京时间 %s ~ %s\"", begin, end), R.dimen.sp_12, R.color.money_color)
                .append("进行维护，维护期间给您带来的不便，敬请谅解，感谢支持和配合！", R.dimen.sp_12, R.color.white)
                .build();
        mTvNotice.setText(notifyText);
    }
}
