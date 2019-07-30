package com.international.wtw.lottery.fragment.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.FragmentAdapter;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.event.MoneyInfoRefreshEvent;
import com.international.wtw.lottery.fragment.money.PayinRecordFragment;
import com.international.wtw.lottery.fragment.money.RechargeFragment;
import com.international.wtw.lottery.fragment.money.WithdrawRecordFragment;
import com.international.wtw.lottery.fragment.money.WithdrawFragment;
import com.international.wtw.lottery.json.MoneyInfo;
import com.international.wtw.lottery.utils.KeyBoardUtils;
import com.international.wtw.lottery.utils.MoneyInfoManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 描述：资金管理页面
 */

public class MoneyManageFragment extends NewBaseFragment {
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_account_balance)
    TextView mTvAccountBalance;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private RechargeFragment mRechargeFragment;
    private WithdrawFragment mWithdrawFragment;
    private PayinRecordFragment payinRecordFragment;
    private WithdrawRecordFragment mRecordFragment;
    private int position = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_money_manage;
    }

    @Override
    protected void initView() {
        if (null == mRechargeFragment) {
            mRechargeFragment = new RechargeFragment();
        }
        if (null == mWithdrawFragment) {
            mWithdrawFragment = new WithdrawFragment();
        }
        if (null == payinRecordFragment) {
            payinRecordFragment = new PayinRecordFragment();
        }
        if (null == mRecordFragment) {
            mRecordFragment = new WithdrawRecordFragment();
        }
        mFragments.clear();
        mFragments.add(mRechargeFragment);
        mFragments.add(mWithdrawFragment);
        mFragments.add(payinRecordFragment);
        mFragments.add(mRecordFragment);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setCurrentItem(position);
        mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
        //注册监听
        initListener();
    }

    @Override
    protected void initData() {
        MoneyInfo data = MoneyInfoManager.get().getMoneyInfo();
        if (data != null) {
            mTvUserName.setText(data.getUsername());
            mTvAccountBalance.setText(String.format(Locale.US, "¥%.2f", data.getMoney()));
        }
    }

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEvent(MoneyInfoRefreshEvent event) {
        if (event.moneyInfo != null) {
            mTvUserName.setText(event.moneyInfo.getUsername());
            mTvAccountBalance.setText(String.format(Locale.US, "¥%.2f", event.moneyInfo.getMoney()));
        }
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
                        mRadioGroup.check(R.id.rb_depositrecord);
                        break;
                    case 3:
                        mRadioGroup.check(R.id.rb_record);
                        break;
                }
                KeyBoardUtils.hideInputForce(mActivity);
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
                    case R.id.rb_depositrecord:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_record:
                        mViewPager.setCurrentItem(3);
                        break;

                }
                KeyBoardUtils.hideInputForce(mActivity);
            }
        });

    }

    public void setCurrentPosition(int moneyIndex) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(moneyIndex);
        } else {
            position = moneyIndex;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d("MoneyManageFragment", "hidden=" + hidden);
        if (mRechargeFragment != null) {
            mRechargeFragment.setUserVisibleHint(!hidden);
        }
    }
}
