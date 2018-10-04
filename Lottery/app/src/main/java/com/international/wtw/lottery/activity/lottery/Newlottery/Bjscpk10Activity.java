package com.international.wtw.lottery.activity.lottery.Newlottery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.event.Pk10RateEvent;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.ClearableEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 北京赛车
 */
public class Bjscpk10Activity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.iv_back)
    ImageView ivBack;
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
    @BindView(R.id.view)
    View view;
    @BindView(R.id.iv_isselect)
    ImageView ivIsselect;
    @BindView(R.id.tv_selectnum)
    TextView tvSelectnum;
    @BindView(R.id.et_betting_amount)
    ClearableEditText etBettingAmount;
    @BindView(R.id.ll_bottom_remove)
    Button llBottomRemove;
    @BindView(R.id.btn_bet)
    Button btnBet;
    private int current = 0;

    private NewPK10Fragment fragment1;
    private NewPK10Fragment fragment2;
    private NewPK10Fragment fragment3;
    private NewPK10Fragment fragment4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betpjpk10);
        ButterKnife.bind(this);
        betbjpk10TabRadioGroup.setOnCheckedChangeListener(this);
        initFragment();

        betbjpk10TabRadioGroup.check(0);

        getPK10rate();



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.view, fragment1);
        fragmentTransaction.commit();


    }

    @Override
    public String getLotteryType() {
        return LotteryId.BJSCPK10;
    }



    /**
     * 获取PK10的投注数据
     */
    private void getPK10rate() {
        String token = SharePreferencesUtil.getString(Bjscpk10Activity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getPlayRate(Bjscpk10Activity.this, token,getLotteryType(), new HttpCallback<PK10Rate>() {
            @Override
            public void onSuccess(PK10Rate data)  {
                EventBus.getDefault().post(new Pk10RateEvent(data));
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {

            }
        });
    }
    /**
     * 初始化frament
     */
    private void initFragment() {
        if (null == fragment1) {
            fragment1 = new NewPK10Fragment();
        }
        if (null == fragment2) {
            fragment2 = new NewPK10Fragment();
        }
        if (null == fragment3) {
            fragment3 = new NewPK10Fragment();
        }
        if (null == fragment4) {
            fragment4 = new NewPK10Fragment();
        }

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
            case R.id.radio_1_5:
                current = 2;
                break;
            case R.id.radio_6_10:
                current = 3;
                break;
        }
        showView(current);
        LogUtil.e("===================" + current);


    }


    /**
     * 展示相关页
     *
     * @param current
     */
    private void showView(int current) {
//        FragmentManager fm = getFragmentManager();          //获得Fragment管理器
//        FragmentTransaction ft = fm.beginTransaction();     //开启一个事务
//        hidtFragment(ft);                                  //先隐藏 Fragment

        switch (current) {
            case 0:
                break;
            case 1:
                break;
            case 3:
                break;
            case 4:
                break;
        }


    }

    //隐藏所有Fragment
    private void hidtFragment(FragmentTransaction fragmentTransaction) {
//        if (fragment1 != null) {
//            fragmentTransaction.hide(fragment1);
//        }
//        if (fragment2 != null) {
//            fragmentTransaction.hide(fragment2);
//        }
//        if (fragment3 != null) {
//            fragmentTransaction.hide(fragment3);
//        }
//        if (fragment4 != null) {
//            fragmentTransaction.hide(fragment4);
//        }
    }
}
