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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.FragmentAdapter;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BetBaseActivity;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.fragment.Lucky28.Lucky28Fragment1;
import com.international.wtw.lottery.fragment.Lucky28.Lucky28Fragment2;
import com.international.wtw.lottery.listener.ShowSelectNumbersInterface;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.international.wtw.lottery.widget.CustomPager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIAOYAN on 2017/7/13.
 */

public class Lucky28Activity extends BetBaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, ShowSelectNumbersInterface {
    private CustomPager contentPage;
    private RadioGroup radioGroup;
    private RadioButton radioButtonhhbs, radioButtontm;
    private Lucky28Fragment1 lucky28Fragment1;
    private Lucky28Fragment2 lucky28Fragment2;
    private Lucky28Fragment2 lucky28Fragment3;
    private List<Fragment> list = new ArrayList<>(); //几个fragment的集合
    private FragmentAdapter fragmentAdapter;
    private boolean IsFeng;
    private EditText et_betting_amount;
    private int current = 0;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.lucy_28);
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
        if (getLotteryType() == event.gameCode) {
            IsFeng = event.isClosed;
            initFragment();
            dismissRedNumberText();
            lucky28Fragment1.close(IsFeng);
            lucky28Fragment2.close(IsFeng);
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lucky28;
    }

    @Override
    public int getLotteryType() {
        return Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY;
    }

    @Override
    protected void initalSubClassViewInital() {
        InitView();
        initFragment();

        list.add(lucky28Fragment1);
        list.add(lucky28Fragment2);
        list.add(lucky28Fragment3);
        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm, list);
        contentPage.setAdapter(fragmentAdapter);
        contentPage.setCurrentItem(0);
        contentPage.setOnPageChangeListener(this);
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
                        lucky28Fragment1.getBet();
                        break;
                    case 1:
                        lucky28Fragment2.getBet();
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
                        lucky28Fragment1.clearbettingseleter();
                        break;
                    case 1:
                        lucky28Fragment2.clearbettingseleter();
                        break;
                }

                dismissRedNumberText();

            }
        });
    }

    private void InitView() {
        contentPage = viewHolder.get(R.id.content_page);
        radioGroup = viewHolder.get(R.id.radioGroup);
        radioButtonhhbs = viewHolder.get(R.id.radio_hhbs);
        radioButtontm = viewHolder.get(R.id.radio_tm);
        radioGroup.setOnCheckedChangeListener(this);

        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);
    }

    private void initFragment() {
        if (null == lucky28Fragment1) {
            lucky28Fragment1 = new Lucky28Fragment1();
        }
        if (null == lucky28Fragment2) {
            lucky28Fragment2 = new Lucky28Fragment2();
        }
        if (null == lucky28Fragment3) {
            lucky28Fragment3 = new Lucky28Fragment2();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_hhbs:
                current = 0;
                contentPage.setCurrentItem(0);
                break;
            case R.id.radio_tm:
                current = 1;
                contentPage.setCurrentItem(1);
                break;
        }

        if (contentPage.getCurrentItem() != current) {
            contentPage.setCurrentItem(current);
        }

        dismissRedNumberText();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.radio_hhbs);
                break;
            case 1:
                radioGroup.check(R.id.radio_tm);
                break;
        }
        dismissRedNumberText();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                        tv_selectnum.setVisibility(View.VISIBLE);
                        tv_selectnum.setText(String.valueOf(sum));
                    }
                    if (null != btn_bet) {
                        btn_bet.setBackgroundColor(getResources().getColor(R.color.textcolortrue));
                    }
                }
            }
        });
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

}
