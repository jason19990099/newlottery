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
import com.international.wtw.lottery.fragment.PK10.PK10Fragment1;
import com.international.wtw.lottery.fragment.PK10.PK10Fragment2;
import com.international.wtw.lottery.fragment.PK10.PK10Fragment3;
import com.international.wtw.lottery.fragment.PK10.PK10Fragment4;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 18Steven on 2017/7/11. 新的PK10
 */

public class PK10Activity extends BetBaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ShowSelectNumbersInterface {
    private RadioGroup betbjpk10_tab_RadioGroup;
    private com.international.wtw.lottery.widget.CustomPager betbjpk10_vp;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> list = new ArrayList<>();
    private PK10Fragment1 fragment1;
    private PK10Fragment2 fragment2;
    private PK10Fragment3 fragment3;
    private PK10Fragment4 fragment4;
    private boolean IsFeng;
    private int current = 0;
    private EditText et_betting_amount;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;


    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.LotteryTypeNamePK10);
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
        if (getLotteryType() == event.gameCode) {
            IsFeng = event.isClosed;
            initFragment();
            dismissRedNumberText();
            fragment1.close(IsFeng);
            fragment2.close(IsFeng);
            fragment3.close(IsFeng);
            fragment4.close(IsFeng);
        }
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

    /**
     * 初始化frament
     */
    private void initFragment() {
        if (null == fragment1) {
            fragment1 = new PK10Fragment1();
        }
        if (null == fragment2) {
            fragment2 = new PK10Fragment2();
        }
        if (null == fragment3) {
            fragment3 = new PK10Fragment3();
        }
        if (null == fragment4) {
            fragment4 = new PK10Fragment4();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_betpjpk10;
    }


    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.PJ_PK_10;
    }


    @Override
    protected void initalSubClassViewInital() {
        betbjpk10_tab_RadioGroup = (RadioGroup) findViewById(R.id.betbjpk10_tab_RadioGroup);
        betbjpk10_tab_RadioGroup.setOnCheckedChangeListener(this);
        betbjpk10_vp = (com.international.wtw.lottery.widget.CustomPager) findViewById(R.id.betbjpk10_vp);
        initFragment();
        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);

        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, list);
        betbjpk10_vp.setAdapter(fragmentAdapter);
        betbjpk10_vp.setCurrentItem(0); //设置当前页是第一页
        betbjpk10_vp.setOnPageChangeListener(this);

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
                        fragment1.getBet();
                        break;
                    case 1:
                        fragment2.getBet();
                        break;
                    case 2:
                        fragment3.getBet();
                        break;
                    case 3:
                        fragment4.getBet();
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
                        fragment1.clearbettingseleter();
                        break;
                    case 1:
                        fragment2.clearbettingseleter();
                        break;
                    case 2:
                        fragment3.clearbettingseleter();
                        break;
                    case 3:
                        fragment4.clearbettingseleter();
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
                betbjpk10_tab_RadioGroup.check(R.id.radio_lmp);
                break;
            case 1:
                betbjpk10_tab_RadioGroup.check(R.id.radio_gyjh);
                break;
            case 2:
                betbjpk10_tab_RadioGroup.check(R.id.radio_1_5);
                break;
            case 3:
                betbjpk10_tab_RadioGroup.check(R.id.radio_6_10);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_lmp:
                current = 0;
                betbjpk10_vp.setCurrentItem(0);
                break;
            case R.id.radio_gyjh:
                current = 1;
                betbjpk10_vp.setCurrentItem(1);
                break;
            case R.id.radio_1_5:
                current = 2;
                betbjpk10_vp.setCurrentItem(2);
                break;
            case R.id.radio_6_10:
                current = 3;
                betbjpk10_vp.setCurrentItem(3);
                break;
        }
        if (betbjpk10_vp.getCurrentItem() != current) {
            betbjpk10_vp.setCurrentItem(current);
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

    @Override
    public void onStop() {
        super.onStop();
        BaseApplication app = (BaseApplication) getApplication();
        app.removeFM();
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
                    if (null!=et_betting_amount){
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
