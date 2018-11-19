package com.international.wtw.lottery.activity.lottery.dandan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.BaseActivity;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.event.OpenAndClosedEvent;
import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.fragment.bjscpk10.PK10GuanyaheFragment;
import com.international.wtw.lottery.fragment.bjscpk10.PK10LiangmianpanFragment;
import com.international.wtw.lottery.newJason.PK10RateModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * PC蛋蛋
 */
public class PCDDActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.textView_lotteryTypeName)
    TextView textViewLotteryTypeName;
    @BindView(R.id.downArrow)
    LinearLayout downArrow;
    @BindView(R.id.img_menu)
    ImageView imgMenu;
    @BindView(R.id.rl_title_bar)
    RelativeLayout rlTitleBar;
    @BindView(R.id.fl_lottery_info_container)
    FrameLayout flLotteryInfoContainer;
    @BindView(R.id.radio_lmp)
    RadioButton radioLmp;
    @BindView(R.id.radio_gyjh)
    RadioButton radioGyjh;
    @BindView(R.id.radio_1_5)
    RadioButton radio15;
    @BindView(R.id.radio_6_10)
    RadioButton radio610;
    @BindView(R.id.betbjpk10_tab_RadioGroup)
    RadioGroup betbjpk10TabRadioGroup;
    @BindView(R.id.iv_isselect)
    ImageView ivIsselect;
    @BindView(R.id.tv_selectnum)
    TextView tvSelectnum;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tv_betsize)
    TextView tvBetsize;
    private int current = 0;
    private PK10LiangmianpanFragment fragment1;
    private PK10GuanyaheFragment fragment2;
    private FragmentManager mFragmentManager;  // Fragment管理器
    boolean IsFeng = false;
    private String expectNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betpjpk10);
        ButterKnife.bind(this);
        betbjpk10TabRadioGroup.setOnCheckedChangeListener(this);
        mFragmentManager = getSupportFragmentManager();
        onCheckedChanged(betbjpk10TabRadioGroup, R.id.radio_lmp);
        getPK10rate();

        radioLmp.setVisibility(View.VISIBLE);
        radioLmp.setText("混合");
        radioGyjh.setVisibility(View.VISIBLE);
        radioGyjh.setText("特码");

    }

    @Override
    public String getLotteryType() {
        return LotteryId.PCDD;
    }

    @Override
    public String getLotteryname() {
        return getIntent().getStringExtra("lotteryname");
    }

    @Override
    public String getexpectNo() {
        return expectNo;
    }

    @Override
    public boolean isClosed() {
        return IsFeng;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(OpenAndClosedEvent event) {
        if (getLotteryType().equals(event.getGameCode())) {
            IsFeng = event.isClosed();
            expectNo=event.getExpectNo();
        }
    }

    /**
     * 获取PK10的投注数据
     */
    private void getPK10rate() {
        String token = SharePreferencesUtil.getString(PCDDActivity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getPlayRate(PCDDActivity.this, token, getLotteryType(), new HttpCallback<PK10RateModel>() {
            @Override
            public void onSuccess(PK10RateModel data) {
                EventBus.getDefault().postSticky(new Pk10RateEvent(data));
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_lmp:
                current = 0;
                break;
            case R.id.radio_gyjh:
                current = 1;
                break;

        }
        showView(current);
    }


    /**
     * 展示相关页
     */
    private void showView(int current) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hidtFragment(transaction);
        switch (current) {
            case 0:
                if (fragment1 == null) {
                    fragment1 = new PK10LiangmianpanFragment();
                    transaction.add(R.id.frameLayout, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 1:
                if (fragment2 == null) {
                    fragment2 = new PK10GuanyaheFragment();
                    transaction.add(R.id.frameLayout, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;


        }
        transaction.commit();
    }

    //隐藏所有Fragment
    private void hidtFragment(FragmentTransaction fragmentTransaction) {
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }

    }

}
