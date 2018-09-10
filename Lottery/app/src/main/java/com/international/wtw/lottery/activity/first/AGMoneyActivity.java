package com.international.wtw.lottery.activity.first;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.FragmentAdapter;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.fragment.money.RechargeFragment;
import com.international.wtw.lottery.fragment.money.TradeRecordFragment;
import com.international.wtw.lottery.fragment.money.WithdrawFragment;
import com.international.wtw.lottery.json.AgAccountBalance;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class AGMoneyActivity extends NewBaseActivity {

    public static final String CURRENT_POSITION = "current_position";
    public static final String AG_BALANCE = "ag_balance";

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.tv_user_balance_title)
    TextView mTvBalanceTitle;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.rb_deposit)
    RadioButton mRbDeposit;
    @BindView(R.id.rb_withdraw)
    RadioButton mRbWithdraw;
    @BindView(R.id.rb_record)
    RadioButton mRbRecord;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private RechargeFragment mRechargeFragment;
    private WithdrawFragment mWithdrawFragment;
    private TradeRecordFragment mRecordFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_manage;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mTvBalanceTitle.setText(getString(R.string.ag_balance_with_colon));
        mRbDeposit.setText(getString(R.string.ag_recharge));
        mRbWithdraw.setText(getString(R.string.ag_withdraw));
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int position = getIntent().getIntExtra(CURRENT_POSITION, 0);
        double agBalance = getIntent().getDoubleExtra(AG_BALANCE, 0);
        MoneyInfo data = MoneyInfoManager.get().getMoneyInfo();
        if (data != null) {
            mTvUserName.setText(data.getUsername());
        }
        mTvAccountBalance.setText(String.format(Locale.US, "¥%.2f", agBalance));
        if (null == mRechargeFragment) {
            mRechargeFragment = RechargeFragment.newInstance("AG");
        }
        if (null == mWithdrawFragment) {
            mWithdrawFragment = WithdrawFragment.newInstance("AG");
        }
        if (null == mRecordFragment) {
            mRecordFragment = TradeRecordFragment.newInstance("AG");
        }
        mFragments.clear();
        mFragments.add(mRechargeFragment);
        mFragments.add(mWithdrawFragment);
        mFragments.add(mRecordFragment);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(position);
        mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
        //注册监听
        initListener();

        MoneyInfoManager.get().requestAgInfo();
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEvent(AgAccountBalance.BalanceBean event) {
        Logger.d("AgAccountBalance.BalanceBean");
        mTvAccountBalance.setText(String.format(Locale.US, "¥%.2f", event.getAgBalance()));
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRadioGroup.check(R.id.rb_deposit);
                        break;
                    case 1:
                        mRadioGroup.check(R.id.rb_withdraw);
                        break;
                    case 2:
                        mRadioGroup.check(R.id.rb_record);
                        break;
                }
                KeyBoardUtils.hideInputForce(AGMoneyActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_deposit:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_withdraw:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_record:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                KeyBoardUtils.hideInputForce(AGMoneyActivity.this);
            }
        });

    }
}
