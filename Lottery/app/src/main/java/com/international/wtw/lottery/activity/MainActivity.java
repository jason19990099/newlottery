package com.international.wtw.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BaseActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.NiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewConvertListener;
import com.international.wtw.lottery.event.PaymentCompletedEvent;
import com.international.wtw.lottery.fragment.main.FirstFragment;
import com.international.wtw.lottery.fragment.main.MineFragment;
import com.international.wtw.lottery.fragment.main.MoneyManageFragment;
import com.international.wtw.lottery.fragment.main.TrendFragment;
import com.international.wtw.lottery.utils.SharePreferencesUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 修改内容: 客户反映进入开奖大厅加载慢, 分析可能是因为原MainActivity切换fragment时用的是replace(),
 * 导致每次进入开奖大厅页面时, fragment都重新执行所有生命周期方法, 相当于销毁后重建, 导致加载慢
 * 现修改切换Fragment方式为show()和hide()切换, 切换时不会重新执行生命周期方法.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String EXTRA_CURRENT_FRAGMENT_INDEX = "EXTRA_CURRENT_FRAGMENT_INDEX";
    public static final String EXTRA_CURRENT_MONEY_INDEX = "EXTRA_CURRENT_MONEY_INDEX";

    private FragmentManager mFragmentManager;  // Fragment管理器
    private MoneyManageFragment moneyFragment;
    private long mClickTime;
    private int currentFragmentIndex = -1; //记录当前显示的fragment

    private Fragment[] fragments;
    private View[] mllViews;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_main;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        EventBus.getDefault().register(this);
        View llHome = findViewById(R.id.ll_home);
        View llBetting = findViewById(R.id.ll_betting);
        View llMoney = findViewById(R.id.ll_money);
        View llMine = findViewById(R.id.ll_mine);
        llHome.setOnClickListener(this);
        llBetting.setOnClickListener(this);
        llMoney.setOnClickListener(this);
        llMine.setOnClickListener(this);

        FirstFragment firstFragment = new FirstFragment();
        TrendFragment trendFragment = new TrendFragment();
        moneyFragment = new MoneyManageFragment();
        MineFragment mineFragment = new MineFragment();

        mllViews = new View[]{llHome, llBetting, llMoney, llMine};
        fragments = new Fragment[]{firstFragment, trendFragment, moneyFragment, mineFragment};

        mFragmentManager = getSupportFragmentManager();
        changeShowFragment(0);

        //首次注册的新用户,弹出充值引导弹窗
        initRechargeDialog();
    }

    private void initRechargeDialog() {
        //充值弹窗延时1.5秒弹窗,避免UI阻塞
        boolean isVisitor = SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IS_SHI_WAN, false);
        boolean isNew = SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IS_NEW_REGISTER, false);
        Logger.d("isVisitor: " + isVisitor + ", isNew: " + isNew);
        if (!isVisitor && isNew) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showRechargeDialog();
                }
            }, 1500);
        }
    }

    private void showRechargeDialog() {
        NiceDialog.init()
                .setLayoutId(R.layout.dialog_new_register_to_recharge)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(com.international.wtw.lottery.dialog.nice.ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setOnClickListener(R.id.iv_close, v -> dialog.dismissAllowingStateLoss());
                        holder.setOnClickListener(R.id.btn_cancel, v -> dialog.dismissAllowingStateLoss());
                        holder.setOnClickListener(R.id.btn_recharge, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Logger.d("btn_recharge");
                                dialog.dismissAllowingStateLoss();
                                changeShowFragment(2);
                            }
                        });
                    }
                })
                .setMargin(35)
                .setOutCancel(true)
                .showDialog(getSupportFragmentManager());

        SharePreferencesUtil.addBoolean(getApplicationContext(), LotteryId.IS_NEW_REGISTER, false);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            if (intent.hasExtra(EXTRA_CURRENT_FRAGMENT_INDEX)) {
                int index = intent.getIntExtra(EXTRA_CURRENT_FRAGMENT_INDEX, 0);
                changeShowFragment(index);
                if (index == 2 && intent.hasExtra(EXTRA_CURRENT_MONEY_INDEX)) {
                    int moneyIndex = intent.getIntExtra(EXTRA_CURRENT_MONEY_INDEX, 0);
                    moneyFragment.setCurrentPosition(moneyIndex);
                }
            }
            boolean to_mine_frag = intent.getBooleanExtra("to_mine_frag", false);
            if (to_mine_frag) {
                changeShowFragment(1);
            }
            boolean login_main = intent.getBooleanExtra("Login_Main", false);
            if (login_main) {
                changeShowFragment(0);
            }
        }
        initRechargeDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                changeShowFragment(0);
                break;
            case R.id.ll_betting:
                changeShowFragment(1);
                break;
            case R.id.ll_money:
                if (SharePreferencesUtil.getBoolean(getApplicationContext(), LotteryId.IS_SHI_WAN, false)) {
                    ToastDialog.error(getString(R.string.plz_login_account)).show(getSupportFragmentManager());
                } else {
                    changeShowFragment(2);
                }
                break;
            case R.id.ll_mine:
                changeShowFragment(3);
                break;
        }
    }

    public void showMoneyFragment(int moneyIndex) {
        changeShowFragment(2);
        moneyFragment.setCurrentPosition(moneyIndex);
    }

    /**
     * 根据fragment数组下标，切换需要显示的fragment，并且隐藏当前的fragment
     */
    public void changeShowFragment(int index) {
        changeSelectState(index);
        if (index != currentFragmentIndex) {
            Fragment showFragment = fragments[index];
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (!showFragment.isAdded()) {
                transaction.add(R.id.view_fragment, showFragment);
            }
            if (currentFragmentIndex != -1) {
                transaction.hide(fragments[currentFragmentIndex]);
            }
            transaction.show(showFragment);
            transaction.commitAllowingStateLoss();
            currentFragmentIndex = index;
        }
    }

    private void changeSelectState(int index) {
        for (int i = 0; i < mllViews.length; i++) {
            mllViews[i].setSelected(index == i);
        }
    }

    @Override
    public void onBackPressed() {
        long time = System.currentTimeMillis();
        if (time - mClickTime <= 2000) {
            super.onBackPressed();
            System.exit(0);
        } else {
            mClickTime = time;
            Toast.makeText(this, R.string.exit_if_click_again, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onEvent(PaymentCompletedEvent event) {
        changeShowFragment(0);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
