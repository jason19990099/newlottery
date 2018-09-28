package com.international.wtw.lottery.activity.lottery;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.FragmentAdapter;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.base.app.BetBaseActivity;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.fragment.SSCai.SSCaiDoubleSideFragment;
import com.international.wtw.lottery.fragment.SSCai.SSCaiNumberSideFragment;
import com.international.wtw.lottery.fragment.SSCai.SSCaiPosteriorFragment;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.CustomPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SSCaiActivity extends BetBaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ShowSelectNumbersInterface {
    private RadioGroup rg_cj_ssc;
    private CustomPager vp_cj_ssc;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> list = new ArrayList<>(); //几个fragment的集合
    private SSCaiDoubleSideFragment doubleSideFragment;
    private SSCaiNumberSideFragment numberSideFragment;
    private SSCaiPosteriorFragment posteriorFragment;
    private boolean isFeng;
    private String money;
    private EditText et_betting_amount;
    private int current = 0;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;


    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.cq_ssc);
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
//        if (getLotteryType() == event.gameCode) {
//            isFeng = event.isClosed;
//            initFragment();
//            dismissRedNumberText();
//            doubleSideFragment.close(isFeng);
//            numberSideFragment.close(isFeng);
//            posteriorFragment.close(isFeng);
//        }
    }

    /**
     * 让小红点消失
     */
    private void dismissRedNumberText() {
        if (null != tv_selectnum)
            tv_selectnum.setText("0");
        if (null != iv_isselect)
            iv_isselect.setImageResource(R.mipmap.wallet_notselect);
        if (null != btn_bet)
            btn_bet.setBackgroundColor(getResources().getColor(R.color.gray_666666));
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        if (null == doubleSideFragment) {
            doubleSideFragment = new SSCaiDoubleSideFragment();
        }
        if (null == numberSideFragment) {
            numberSideFragment = new SSCaiNumberSideFragment();
        }
        if (null == posteriorFragment) {
            posteriorFragment = new SSCaiPosteriorFragment();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cjssclottery;
    }


    @Override
    public String getLotteryType() {
        return Constants.LOTTERY_TYPE.CJ_LOTTERY+"";
    }


    @Override
    protected void initalSubClassViewInital() {
        rg_cj_ssc = (RadioGroup) findViewById(R.id.rg_cj_ssc);
        rg_cj_ssc.setOnCheckedChangeListener(this);
        vp_cj_ssc = (CustomPager) findViewById(R.id.vp_cj_ssc);
        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);
        initFragment();

        list.add(doubleSideFragment);
        list.add(numberSideFragment);
        list.add(posteriorFragment);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, list);
        vp_cj_ssc.setAdapter(fragmentAdapter);
        vp_cj_ssc.setCurrentItem(0); //设置当前页是第一页
        vp_cj_ssc.addOnPageChangeListener(this);

        //金额输入
        et_betting_amount = (EditText) findViewById(R.id.et_betting_amount);
        new EditTextTools(et_betting_amount, 6, 0);
        et_betting_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals("")) {
                    int i = Integer.parseInt(s1);
                    if (i > 100000) {
                        et_betting_amount.setText("100000");
                        s1 = "100000";
                    }
                    SharePreferencesUtil.addString(getApplicationContext(), LotteryId.LOTTERY_BET_MONEY, s1);
                }


            }
        });
        //下注投注
        findViewById(R.id.btn_bet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_betting_amount.getText().toString().equals("")) {
                    ToastDialog.error(getString(R.string.typein_betmoney)).show(getSupportFragmentManager());
                    return;
                }
                initFragment();
                switch (current) {
                    case 0:
                        doubleSideFragment.getBet();
                        break;
                    case 1:
                        numberSideFragment.getBet();
                        break;
                    case 2:
                        posteriorFragment.getBet();
                        break;
                }
                dismissRedNumberText();
            }
        });

        //清除选项
        findViewById(R.id.ll_bottom_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment();
                switch (current) {
                    case 0:
                        doubleSideFragment.clearBettingSelect();
                        break;
                    case 1:
                        numberSideFragment.clearBettingSelect();
                        break;
                    case 2:
                        posteriorFragment.clearBettingSelect();
                        break;
                }

                dismissRedNumberText();

            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rg_cj_ssc.check(R.id.radio_lmp);
                break;
            case 1:
                rg_cj_ssc.check(R.id.radio_digital);
                break;
            case 2:
                rg_cj_ssc.check(R.id.radio_qzh);
                break;
        }
        dismissRedNumberText();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_lmp:  //两面盘
                current = 0;
                vp_cj_ssc.setCurrentItem(0);
                break;
            case R.id.radio_digital:  //数字盘
                current = 1;
                vp_cj_ssc.setCurrentItem(1);
                break;
            case R.id.radio_qzh: //前中后
                current = 2;
                vp_cj_ssc.setCurrentItem(2);
                break;
        }
        if (vp_cj_ssc.getCurrentItem() != current) {
            vp_cj_ssc.setCurrentItem(current);
        }

        dismissRedNumberText();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyBoardUtils.isShouldHideInput(v, ev)) {
                if (KeyBoardUtils.closeKeyboard(this, v)) {
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseApplication app = (BaseApplication) getApplication();
        app.removeFM();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler)
            handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void showSelextNum(int sum) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sum == 0) {
                    if (null != tv_selectnum)
                        tv_selectnum.setText("0");
                    if (null != iv_isselect)
                        iv_isselect.setImageResource(R.mipmap.wallet_notselect);
                    if (null != btn_bet)
                        btn_bet.setBackgroundColor(getResources().getColor(R.color.gray_666666));
                    if (null != et_betting_amount) {
                        et_betting_amount.setText("");
                    }
                } else if (sum > 0) {
                    if (null != iv_isselect)
                        iv_isselect.setImageResource(R.mipmap.wallet_select);
                    if (null != tv_selectnum) {
                        tv_selectnum.setText(String.valueOf(sum));
                    }
                    if (null != btn_bet) {
                        btn_bet.setBackgroundColor(getResources().getColor(R.color.textcolortrue));
                    }
                }
            }
        });
    }

}
