package com.international.wtw.lottery.activity.lottery.kuai3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
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
import com.international.wtw.lottery.fragment.bjscpk10.PK10LiangmianpanFragment;
import com.international.wtw.lottery.fragment.bjscpk10.kuai3Fragment;
import com.international.wtw.lottery.newJason.PK10RateModel;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.ClearableEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JSK3Activity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.radio_1)
    RadioButton radio1;
    @BindView(R.id.radio_2)
    RadioButton radio2;
    @BindView(R.id.radio_3)
    RadioButton radio3;
    @BindView(R.id.radio_4)
    RadioButton radio4;
    @BindView(R.id.radio_5)
    RadioButton radio5;
    @BindView(R.id.radio_6)
    RadioButton radio6;
    @BindView(R.id.radio_7)
    RadioButton radio7;
    @BindView(R.id.radio_8)
    RadioButton radio8;
    @BindView(R.id.et_betting_amount)
    ClearableEditText etBettingAmount;
    @BindView(R.id.ll_bottom_remove)
    Button llBottomRemove;
    @BindView(R.id.btn_bet)
    Button btnBet;
    private int current = 0;
    private PK10LiangmianpanFragment fragment_liangmianpan;
    private kuai3Fragment fragment1,fragment2,fragment3,fragment4,fragment5,fragment6,fragment7,fragment8;
    private FragmentManager mFragmentManager;  // Fragment管理器
    boolean IsFeng = false;
    private String expectNo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai3);
        ButterKnife.bind(this);
        betbjpk10TabRadioGroup.setOnCheckedChangeListener(this);
        mFragmentManager = getSupportFragmentManager();
        onCheckedChanged(betbjpk10TabRadioGroup, R.id.radio_1);
        getPK10rate();
    }

    @Override
    public String getLotteryType() {
        return LotteryId.JSKS;
    }

    @Override
    public String getLotteryname() {
        return  "江苏快3";
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
            expectNo = event.getExpectNo();
        }
    }

    /**
     * 获取PK10的投注数据
     */
    private void getPK10rate() {
        String token = SharePreferencesUtil.getString(JSK3Activity.this, LotteryId.TOKEN, "");
        HttpRequest.getInstance().getPlayRate(JSK3Activity.this, token, getLotteryType(), new HttpCallback<PK10RateModel>() {
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
            case R.id.radio_1:
                current = 1;
                break;
            case R.id.radio_2:
                current = 2;
                break;
            case R.id.radio_3:
                current = 3;
                break;
            case R.id.radio_4:
                current = 4;
                break;
            case R.id.radio_5:
                current = 5;
                break;
            case R.id.radio_6:
                current = 6;
                break;
            case R.id.radio_7:
                current = 7;
                break;
            case R.id.radio_8:
                current = 8;
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
            case 1:
                if (fragment1 == null) {
                    fragment1 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","hezhi");
                    fragment1.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                break;
            case 2:
                if (fragment2 == null) {
                    fragment2 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","lian3");
                    fragment2.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                break;
            case 3:
                if (fragment3 == null) {
                    fragment3 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","tong3");
                    fragment3.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment3);
                } else {
                    transaction.show(fragment3);
                }
                break;
            case 4:
                if (fragment4 == null) {
                    fragment4 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","tong2");
                    fragment4.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment4);
                } else {
                    transaction.show(fragment4);
                }
                break;
            case 5:
                if (fragment5 == null) {
                    fragment5 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","kuadu");
                    fragment5.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment5);
                } else {
                    transaction.show(fragment5);
                }
                break;
            case 6:
                if (fragment6 == null) {
                    fragment6 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","paidian");
                    fragment6.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment6);
                } else {
                    transaction.show(fragment6);
                }
                break;
            case 7:
                if (fragment7 == null) {
                    fragment7 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","buchu");
                    fragment7.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment7);
                } else {
                    transaction.show(fragment7);
                }
                break;
            case 8:
                if (fragment8 == null) {
                    fragment8 = new kuai3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("qiu","bichu");
                    fragment8.setArguments(bundle);
                    transaction.add(R.id.frameLayout, fragment8);
                } else {
                    transaction.show(fragment8);
                }
                break;
        }
        transaction.commit();
    }

    //隐藏所有Fragment
    private void hidtFragment(FragmentTransaction fragmentTransaction) {
        if (fragment_liangmianpan != null) {
            fragmentTransaction.hide(fragment_liangmianpan);
        }
        if (fragment1 != null) {
            fragmentTransaction.hide(fragment1);
        }
        if (fragment2 != null) {
            fragmentTransaction.hide(fragment2);
        }
        if (fragment3 != null) {
            fragmentTransaction.hide(fragment3);
        }
        if (fragment4 != null) {
            fragmentTransaction.hide(fragment4);
        }
        if (fragment5 != null) {
            fragmentTransaction.hide(fragment5);
        }
        if (fragment6 != null) {
            fragmentTransaction.hide(fragment6);
        }
        if (fragment7!= null) {
            fragmentTransaction.hide(fragment7);
        }
        if (fragment8 != null) {
            fragmentTransaction.hide(fragment8);
        }
    }


}
