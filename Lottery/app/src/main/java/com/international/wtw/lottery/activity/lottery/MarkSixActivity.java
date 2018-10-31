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
import com.international.wtw.lottery.fragment.MarkSix.BanBoFragment;
import com.international.wtw.lottery.fragment.MarkSix.GuoGuanFragment;
import com.international.wtw.lottery.fragment.MarkSix.HeXiaoFragment;
import com.international.wtw.lottery.fragment.MarkSix.LianMaFragment;
import com.international.wtw.lottery.fragment.MarkSix.QBZFragment;
import com.international.wtw.lottery.fragment.MarkSix.SheXLFragment;
import com.international.wtw.lottery.fragment.MarkSix.TeMaFragment;
import com.international.wtw.lottery.fragment.MarkSix.TeMaSxFragment;
import com.international.wtw.lottery.fragment.MarkSix.WeiShuLianFragment;
import com.international.wtw.lottery.fragment.MarkSix.YxWsFragment;
import com.international.wtw.lottery.fragment.MarkSix.ZeMaFragment;
import com.international.wtw.lottery.fragment.MarkSix.ZeMaSixFragment;
import com.international.wtw.lottery.fragment.MarkSix.ZeMaTeFragment;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MarkSixActivity extends BetBaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ShowSelectNumbersInterface {
    private RadioGroup rg_mark_one, rg_mark_two, rg_mark_three;
    private com.international.wtw.lottery.widget.CustomPager vp_mark_six;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> list = new ArrayList<>(); //几个fragment的集合
    private TeMaFragment teMaFragment;  //特码
    private ZeMaFragment zeMaFragment; //正码
    private ZeMaTeFragment zMaTeFragment;  //证码特
    private ZeMaSixFragment zMaSixFragment;  //正码1-6
    private GuoGuanFragment guoGuanFragment;  //过关
    private LianMaFragment lianMaFragment;  //连码
    private BanBoFragment banBoFragment;   //半波
    private YxWsFragment yXwSFragment;     //一肖尾数
    private TeMaSxFragment teMaSXFragment;  //特码生肖
    private HeXiaoFragment heXiaoFragment;   //合肖
    private SheXLFragment sXiaoLianFragment;  //生肖连
    private WeiShuLianFragment weiShuLianFragment;  //尾数连
    private QBZFragment qbzFragment;   //全不中
    private String money;
    private int currentPage = 0;
    private EditText et_betting_amount;
    private boolean isFeng;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.mark_six);
    }


    @Subscribe
    public void onEvent(BetClosedEvent event) {
//        if (getLotteryType() == event.gameCode) {
//            isFeng = event.isClosed;
//            initFragment();
//            dismissRedNumberText();
//            teMaFragment.close(isFeng);
//            zeMaFragment.close(isFeng);
//            zMaTeFragment.close(isFeng);
//            zMaSixFragment.close(isFeng);
//            //guoGuanFragment.close(isFeng);
//            banBoFragment.close(isFeng);
//            yXwSFragment.close(isFeng);
//            teMaSXFragment.close(isFeng);
//            heXiaoFragment.close(isFeng);
//            sXiaoLianFragment.close(isFeng);
//            weiShuLianFragment.close(isFeng);
//            qbzFragment.close(isFeng);
//            lianMaFragment.close(isFeng);
//        }
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        if (null == teMaFragment) {
            teMaFragment = new TeMaFragment();
        }
        if (null == zeMaFragment) {
            zeMaFragment = new ZeMaFragment();
        }
        if (null == zMaTeFragment) {
            zMaTeFragment = new ZeMaTeFragment();
        }
        if (null == zMaSixFragment) {
            zMaSixFragment = new ZeMaSixFragment();
        }
        /*if (null == guoGuanFragment) {
            guoGuanFragment = new GuoGuanFragment();
        }*/
        if (null == banBoFragment) {
            banBoFragment = new BanBoFragment();
        }
        if (null == yXwSFragment) {
            yXwSFragment = new YxWsFragment();
        }
        if (null == teMaSXFragment) {
            teMaSXFragment = new TeMaSxFragment();
        }
        if (null == heXiaoFragment) {
            heXiaoFragment = new HeXiaoFragment();
        }
        if (null == sXiaoLianFragment) {
            sXiaoLianFragment = new SheXLFragment();
        }
        if (null == weiShuLianFragment) {
            weiShuLianFragment = new WeiShuLianFragment();
        }
        if (null == qbzFragment) {
            qbzFragment = new QBZFragment();
        }
        if (null == lianMaFragment) {
            lianMaFragment = new LianMaFragment();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_six;
    }


    @Override
    public String getLotteryType() {
        return Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY+"";
    }


    @Override
    protected void initalSubClassViewInital() {
        rg_mark_one = (RadioGroup) findViewById(R.id.six_rg_one);
        rg_mark_two = (RadioGroup) findViewById(R.id.six_rg_two);
        rg_mark_three = (RadioGroup) findViewById(R.id.six_rg_three);
        rg_mark_one.setOnCheckedChangeListener(this);
        rg_mark_two.setOnCheckedChangeListener(this);
        rg_mark_three.setOnCheckedChangeListener(this);
        vp_mark_six = (com.international.wtw.lottery.widget.CustomPager) findViewById(R.id.six_vp);
        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);
        initFragment();

        list.add(teMaFragment);
        list.add(zeMaFragment);
        list.add(zMaTeFragment);
        list.add(zMaSixFragment);
        //list.add(guoGuanFragment);
        list.add(lianMaFragment);
        list.add(banBoFragment);
        list.add(yXwSFragment);
        list.add(teMaSXFragment);
        list.add(heXiaoFragment);
        list.add(sXiaoLianFragment);
        list.add(weiShuLianFragment);
        list.add(qbzFragment);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, list);
        vp_mark_six.setAdapter(fragmentAdapter);
        vp_mark_six.setCurrentItem(0); //设置当前页是第一页
        vp_mark_six.addOnPageChangeListener(this);

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
                switch (currentPage) {
                    case 0:
                        teMaFragment.getBet();
                        break;
                    case 1:
                        zeMaFragment.getBet();
                        break;
                    case 2:
                        zMaTeFragment.getBet();
                        break;
                    case 3:
                        zMaSixFragment.getBet();
                        break;
                    case 4:
                        lianMaFragment.getBet();
                        break;
                    case 5:
                        banBoFragment.getBet();
                        break;
                    case 6:
                        yXwSFragment.getBet();
                        break;
                    case 7:
                        teMaSXFragment.getBet();
                        break;
                    case 8:
                        heXiaoFragment.getBet();
                        break;
                    case 9:
                        sXiaoLianFragment.getBet();
                        break;
                    case 10:
                        weiShuLianFragment.getBet();
                        break;
                    case 11:
                        qbzFragment.getBet();
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
                switch (currentPage) {
                    case 0:
                        teMaFragment.clearBettingSelect();
                        break;
                    case 1:
                        zeMaFragment.clearBettingSelect();
                        break;
                    case 2:
                        zMaTeFragment.clearBettingSelect();
                        break;
                    case 3:
                        zMaSixFragment.clearBettingSelect();
                        break;
                    case 4:
                        lianMaFragment.clearBettingSelect();
                        break;
                    case 5:
                        banBoFragment.clearBettingSelect();
                        break;
                    case 6:
                        yXwSFragment.clearBettingSelect();
                        break;
                    case 7:
                        teMaSXFragment.clearBettingSelect();
                        break;
                    case 8:
                        heXiaoFragment.clearBettingSelect();
                        break;
                    case 9:
                        sXiaoLianFragment.clearBettingSelect();
                        break;
                    case 10:
                        weiShuLianFragment.clearBettingSelect();
                        break;
                    case 11:
                        qbzFragment.clearBettingSelect();
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
                currentPage = 0;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_one.check(R.id.six_rb_tm);
                break;
            case 1:
                currentPage = 1;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_one.check(R.id.six_rb_zm);
                break;
            case 2:
                currentPage = 2;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_one.check(R.id.six_rb_ztm);
                break;
            case 3:
                currentPage = 3;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_one.check(R.id.six_rb_zm_1_6);
                break;
           /* case 4:
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_two.check(R.id.six_rb_gg);
                break;*/
            case 4:
                currentPage = 4;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_two.check(R.id.six_rb_lm);
                break;
            case 5:
                currentPage = 5;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_two.check(R.id.six_rb_bb);
                break;
            case 6:
                currentPage = 6;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_two.check(R.id.six_rb_1x);
                break;
            case 7:
                currentPage = 7;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                rg_mark_two.check(R.id.six_rb_tmsx);
                break;
            case 8:
                currentPage = 8;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                rg_mark_three.check(R.id.six_rb_hx);
                break;
            case 9:
                currentPage = 9;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                rg_mark_three.check(R.id.six_rb_sxl);
                break;
            case 10:
                currentPage = 10;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                rg_mark_three.check(R.id.six_rb_wsl);
                break;
            case 11:
                currentPage = 11;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                rg_mark_three.check(R.id.six_rb_qbz);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.six_rb_tm: //特码
                currentPage = 0;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(0);
                break;
            case R.id.six_rb_zm:  //正码
                currentPage = 1;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(1);
                break;
            case R.id.six_rb_ztm: //正特码
                currentPage = 2;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(2);
                break;
            case R.id.six_rb_zm_1_6: //正码1-6
                currentPage = 3;
                rg_mark_two.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(3);
                break;

            /*case R.id.six_rb_gg:
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(4);
                currentPlayTypeCode = 12;
                break;*/
            case R.id.six_rb_lm: //连码
                currentPage = 4;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(4);
                break;
            case R.id.six_rb_bb: //半波
                currentPage = 5;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(5);
                break;
            case R.id.six_rb_1x: //一肖
                currentPage = 6;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(6);
                break;

            case R.id.six_rb_tmsx: //特码生肖
                currentPage = 7;
                rg_mark_one.clearCheck();
                rg_mark_three.clearCheck();
                vp_mark_six.setCurrentItem(7);
                break;
            case R.id.six_rb_hx:  //合肖
                currentPage = 8;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                vp_mark_six.setCurrentItem(8);
                break;
            case R.id.six_rb_sxl: //生肖连
                currentPage = 9;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                vp_mark_six.setCurrentItem(9);
                break;
            case R.id.six_rb_wsl: //尾数连
                currentPage = 10;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                vp_mark_six.setCurrentItem(10);
                break;
            case R.id.six_rb_qbz:  //全不中
                currentPage = 11;
                rg_mark_one.clearCheck();
                rg_mark_two.clearCheck();
                vp_mark_six.setCurrentItem(11);
                break;
        }

    }


    /**
     * 设置投注金额
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 获取投注金额
     */
    public String getMoney() {
        return money;
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


    /**
     * 显示开封盘时间底部的状态
     */
    private void dismissRedNumberText() {
        if (null != tv_selectnum)
            tv_selectnum.setText("0");
        if (null != iv_isselect)
            iv_isselect.setImageResource(R.mipmap.wallet_notselect);
        if (null != btn_bet)
            btn_bet.setBackgroundColor(getResources().getColor(R.color.gray_666666));
    }
}
