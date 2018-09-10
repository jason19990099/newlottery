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
import com.international.wtw.lottery.base.app.BetBaseActivity;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.fragment.GDHappy.GDHappyFragment1;
import com.international.wtw.lottery.fragment.GDHappy.GDHappyFragment2;
import com.international.wtw.lottery.fragment.GDHappy.GDHappyFragment3;
import com.international.wtw.lottery.fragment.GDHappy.GDHappyFragment4;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIAOYAN on 2017/7/12.
 */

public class GDHappyActivity extends BetBaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ShowSelectNumbersInterface {
    private RadioGroup rg_gd_happay;
    private com.international.wtw.lottery.widget.CustomPager vp_gd_happy;
    private GDHappyFragment1 gdHappyFragment1;
    private GDHappyFragment2 gdHappyFragment2;
    private GDHappyFragment3 gdHappyFragment3;
    private GDHappyFragment4 gdHappyFragment4;
    private List<Fragment> list = new ArrayList<>(); //几个fragment的集合
    private FragmentAdapter fragmentAdapter;
    private boolean IsFeng;
    private int current = 0;
    private EditText et_betting_amount;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.LotteryTypeNameGDHappy);
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
        if (getLotteryType() == event.gameCode) {
            IsFeng = event.isClosed;
            initFragment();
            dismissRedNumberText();
            gdHappyFragment1.close(IsFeng);
            gdHappyFragment2.close(IsFeng);
            gdHappyFragment3.close(IsFeng);
            gdHappyFragment4.close(IsFeng);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gd_happy_play;
    }


    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY;
    }


    @Override
    protected void initalSubClassViewInital() {
        initView();
        initFragment();

        list.add(gdHappyFragment1);
        list.add(gdHappyFragment2);
        list.add(gdHappyFragment3);
        list.add(gdHappyFragment4);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, list);
        vp_gd_happy.setAdapter(fragmentAdapter);
        vp_gd_happy.setCurrentItem(0);
        vp_gd_happy.setOnPageChangeListener(this);
    }

    private void initView() {
        rg_gd_happay = (RadioGroup) findViewById(R.id.rg_gd_happay);
        rg_gd_happay.setOnCheckedChangeListener(this);
        vp_gd_happy = (com.international.wtw.lottery.widget.CustomPager) findViewById(R.id.vp_gd_happy);
        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);
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
                        gdHappyFragment1.getBet();
                        break;
                    case 1:
                        gdHappyFragment2.getBet();
                        break;
                    case 2:
                        gdHappyFragment3.getBet();
                        break;
                    case 3:
                        gdHappyFragment4.getBet();
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
                        gdHappyFragment1.clearbettingseleter();
                        break;
                    case 1:
                        gdHappyFragment2.clearbettingseleter();
                        break;
                    case 2:
                        gdHappyFragment3.clearbettingseleter();
                        break;
                    case 3:
                        gdHappyFragment4.clearbettingseleter();
                        break;
                }

                dismissRedNumberText();

            }
        });

    }

    private void initFragment() {
        if (null == gdHappyFragment1) {
            gdHappyFragment1 = new GDHappyFragment1();
        }
        if (null == gdHappyFragment2) {
            gdHappyFragment2 = new GDHappyFragment2();
        }
        if (null == gdHappyFragment3) {
            gdHappyFragment3 = new GDHappyFragment3();
        }
        if (null == gdHappyFragment4) {
            gdHappyFragment4 = new GDHappyFragment4();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                rg_gd_happay.check(R.id.rb_lmp);
                break;
            case 1:
                rg_gd_happay.check(R.id.rb_four);
                break;
            case 2:
                rg_gd_happay.check(R.id.rb_eight);
                break;
            case 3:
                rg_gd_happay.check(R.id.rb_lian);
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
            case R.id.rb_lmp:
                current = 0;
                vp_gd_happy.setCurrentItem(0);
                break;
            case R.id.rb_four:
                current = 1;
                vp_gd_happy.setCurrentItem(1);
                break;
            case R.id.rb_eight:
                current = 2;
                vp_gd_happy.setCurrentItem(2);
                break;
            case R.id.rb_lian:
                current = 3;
                vp_gd_happy.setCurrentItem(3);
                break;
        }
        if (vp_gd_happy.getCurrentItem() != current) {
            vp_gd_happy.setCurrentItem(current);
        }
        dismissRedNumberText();
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
