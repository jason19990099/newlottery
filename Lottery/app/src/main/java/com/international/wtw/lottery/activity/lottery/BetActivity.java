package com.international.wtw.lottery.activity.lottery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.NewBaseActivity;
import com.international.wtw.lottery.dialog.BetDetailDialog;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.MoreGameTypePopup;
import com.international.wtw.lottery.dialog.SwitchGamePopupWindow;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.easypopup.EasyPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.event.ResetSelectBetDataEvent;
import com.international.wtw.lottery.event.SelectStateChangedEvent;
import com.international.wtw.lottery.fragment.BetFragment;
import com.international.wtw.lottery.fragment.BetFragmentManager;
import com.international.wtw.lottery.fragment.LotteryInfoFragment;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.TypeTitle;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：下注页面
 */

public class BetActivity extends NewBaseActivity {

    public static final String GAME_CODE = "game_code";

    @BindView(R.id.tv_bet_title)
    TextView mTvBetTitle;
    @BindView(R.id.fl_title)
    FrameLayout mFlTitle;
    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.game_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.iv_show_more)
    ImageView mIvShowMore;
    @BindView(R.id.rl_rg_container)
    RelativeLayout mRlRgContainer;
    @BindView(R.id.et_bet_amount)
    EditText mEtBetAmount;
    @BindView(R.id.btn_bet)
    Button mBtnBet;
    //是否已封盘
    private boolean isClosed = true;
    //游戏数字代号
    private int gameCode;
    //下注选项(多选)
    private List<MultiBetItem> selectBeans = new ArrayList<>();
    private int fragPosition;
    private EasyPopup mTypePopup;
    private EasyPopup mMenuPopup;
    private BaseNiceDialog mBetDetailDialog;
    private LotteryInfoFragment mLotteryInfoFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bet;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState); //不保存状态
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
//        gameCode = getIntent().getIntExtra(GAME_CODE, Constants.LOTTERY_TYPE.PJ_PK_10);
        mTvBetTitle.setText(LotteryUtil.get().getPageTitle(gameCode));
        initFragment();
        initBetType();
        mEtBetAmount.addTextChangedListener(new TextWatcher() {
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
                    if (s.length() == 1 && s1.equals("0")) {
                        s.clear();
                    }
                    int i = Integer.parseInt(s1);
                    if (i > 100000) {
                        mEtBetAmount.setText("100000");
                    }
                }
            }
        });
    }

    private void initBetType() {
        final List<TypeTitle> typeTitles = LotteryUtil.get().getTabTitle(gameCode);
        List<TypeTitle> rgTitles = new ArrayList<>();
        for (int i = 0; i < 4 && i < typeTitles.size(); i++) {
            rgTitles.add(typeTitles.get(i));
        }
        setRadioButtons(0, rgTitles);

        //如果超过4项, 则在最后显示一个下拉按钮, 点击该下拉按钮选择其他的选项.
        if (typeTitles.size() > 4) {
            mIvShowMore.setVisibility(View.VISIBLE);
            for (int i = 0; i < 4; i++) {
                typeTitles.remove(0);
            }
            mIvShowMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMoreTypePopup(typeTitles);
                }
            });
        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton checkRadio = (RadioButton) group.findViewById(checkedId);
                fragPosition = (int) checkRadio.getTag();
                setCurrentFragment(fragPosition);
            }
        });
    }

    private void showMoreTypePopup(List<TypeTitle> typeTitles) {
        if (mTypePopup == null) {
            mTypePopup = new MoreGameTypePopup(this, gameCode, typeTitles)
                    .setOnItemSelectListener(new MoreGameTypePopup.OnItemSelectListener() {
                        @Override
                        public void onItemSelect(int position, List<TypeTitle> typeTitles) {
                            setRadioButtons(position, typeTitles);
                        }
                    })
                    .createPopup();
        }
        mTypePopup.showAtAnchorView(mRlRgContainer, VerticalGravity.BELOW, HorizontalGravity.CENTER);
    }

    private void initFragment() {
        mLotteryInfoFragment = LotteryInfoFragment.newInstance(gameCode+"");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_lottery_container, mLotteryInfoFragment)
                .commit();
        BetFragmentManager.clear(gameCode);
        setCurrentFragment(0);
    }

    /**
     * 当前显示的赔率Fragment
     */
    private void setCurrentFragment(int pos) {
        clearSelection();
        BetFragmentManager.setFragment(this, R.id.fl_odds_container, gameCode, pos, isClosed);
    }

    /**
     * 设置RadioGroup中的radioButton, 其中设置每个按钮的tag为其原始索引值.
     */
    public void setRadioButtons(int selectPosition, List<TypeTitle> typeTitles) {
        mRadioGroup.removeAllViews();
        for (int i = 0; i < typeTitles.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTag(typeTitles.get(i).getPosition());
            radioButton.setText(typeTitles.get(i).getTitle());
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioButton.setTextSize(16);
            radioButton.setBackgroundResource(R.drawable.selector_rb_bg);
            radioButton.setTextColor(Color.WHITE);
            radioButton.setGravity(Gravity.CENTER);
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(0, -1, 1);
            mRadioGroup.addView(radioButton, layoutParams);
            if (typeTitles.size() == 1) {
                radioButton.setEnabled(false);
            } else if (selectPosition == i) {
                /*此处使用RadioGroup.check(resId)会造成RadioGroup的onCheckedChanged()多次调用;
                若使用radioButton.setChecked(true)则会造成该radioButton的状态在RadioGroup中不可改变*/
                radioButton.toggle();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ll_title, R.id.iv_menu, R.id.tv_reset, R.id.btn_bet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://点击返回, 直接退出
                finish();
                break;
            case R.id.ll_title://点击中间标题, 弹出切换游戏popup弹窗
                showSwitchGamePopup();
                break;
            case R.id.iv_menu://右上角menu
                showMenuPopup();
                break;
            case R.id.tv_reset://点击重置., 清除用户选择
                clearSelection();
                break;
            case R.id.btn_bet://点击下注按钮, 提交下注数据
                commitBetting();
                break;
        }
    }

    /**
     * 点击中间标题下拉出现的PopupWindow
     */
    private void showSwitchGamePopup() {
        new SwitchGamePopupWindow(this, gameCode)
                .setOnItemClickListener(new SwitchGamePopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int gameCode) {
                        //当切换标题时, 重新给intent中的GAME_CODE赋值, 并重启Activity
                        getIntent().putExtra(GAME_CODE, gameCode);
                        //recreate();
                        //使用recreate()时出现闪烁黑屏, 改用下面方法.
                        finish();
                        startActivity(getIntent());
                    }
                })
                .createPopup()
                //显示在标题栏下方, 水平方向居中对其.
                .showAtAnchorView(mFlTitle, VerticalGravity.BELOW, HorizontalGravity.CENTER);
    }

    /**
     * 点击右上角Menu弹出的PopupWindow, 第一次显示时创建, 之后直接复用.
     */
    private void showMenuPopup() {
        if (mMenuPopup == null) {
            mMenuPopup = new MenuPopupWindow(this)
                    .createPopup();
        }
        //显示在标题栏下面, 水平方向靠右对其
        mMenuPopup.showAtAnchorView(mFlTitle, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
    }

    /**
     * 重置用户选择: 清除selectBeans, 下注数量重置为0, EventBus发送事件到BetFragment去清除已经选择的选项
     */
    public void clearSelection() {
        if (!selectBeans.isEmpty()) {
            selectBeans.clear();
            mTvSelectNum.setText("0");
            mTvSelectNum.setBackgroundResource(R.mipmap.wallet_notselect);
        }
        mEtBetAmount.setText("");
        EventBus.getDefault().post(new ResetSelectBetDataEvent());
    }

    private void commitBetting() {
        BetFragment betFragment = BetFragmentManager.getCurrentFragment(gameCode, fragPosition);
        int selectedId = betFragment.getSelectedId();
        //检查用户的下注选项是否符合规则(是否为空, 是否小于最小选择数或大于最大选中数)
        String checkStr = LotteryUtil.get().checkBetBeans(gameCode, fragPosition, selectedId, selectBeans);
        if (!TextUtils.isEmpty(checkStr)) {
            ToastDialog.error(checkStr).show(getSupportFragmentManager());
            return;
        }
        //用户输入的下注金额
        String money = mEtBetAmount.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastDialog.error(getString(R.string.typein_betmoney)).show(getSupportFragmentManager());
            return;
        }
        //对下注选项排序
        Collections.sort(selectBeans);
        //当前下注期数
        String round = mLotteryInfoFragment.getNextRound();
        if (TextUtils.isEmpty(round)) {
            ToastDialog.error("下注期数为空，请等期数信息加载完成后重试").show(getSupportFragmentManager());
            return;
        }

        //打开下注弹窗, 显示下注详情, 点击确认后请求下注接口(下注接口代码在BetDetailDialog中实现)
        //显示下注结果和提示信息
        //刷新余额
        mBetDetailDialog = BetDetailDialog.init(gameCode, fragPosition, selectedId, Integer.valueOf(money), round,
                betFragment.getComboCode(), selectBeans, new BetDetailDialog.OnBetResultListener() {
                    @Override
                    public void onBetResult(boolean isSuccess, String msg) {
                        //显示下注结果和提示信息
                        ToastDialog.success(msg).show(getSupportFragmentManager());
                        //刷新余额
                        MoneyInfoManager.get().requestMoneyInfo();
                    }
                })
                .setMargin(40)
                .showDialog(getSupportFragmentManager());
    }

    /**
     * 当有新的下注选项被选中, 或者取消选中时, 会执行该方法
     * 根据传过来的item状态, 刷新selectBeans, mTvSelectNum, mBtnBet
     */
    @Subscribe
    public void onEvent(SelectStateChangedEvent event) {
        MultiBetItem betItem = event.mBetItem;
        if (betItem.getIsSelected()) {
            selectBeans.add(betItem);
        } else {
            if (selectBeans.contains(betItem)) {
                selectBeans.remove(betItem);
            }
        }
        if (selectBeans.isEmpty()) {
            mBtnBet.setEnabled(false);
            mTvSelectNum.setText("0");
            mTvSelectNum.setBackgroundResource(R.mipmap.wallet_notselect);
        } else {
            mBtnBet.setEnabled(true);
            mTvSelectNum.setText(String.valueOf(selectBeans.size()));
            mTvSelectNum.setBackgroundResource(R.mipmap.wallet_select);
        }
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
        if (gameCode == event.gameCode) {
            //根据接收到的开/封盘事件, 初始化isClosed作为创建Fragment时的参数传进去. 保证Fragment开/封盘状态正确
            isClosed = event.isClosed;
            //因为进入页面时显示了加载动画, 收到BetClosedEvent时表示开奖信息已经加载完成, 在此隐藏加载动画.
            if (isClosed && mBetDetailDialog != null && mBetDetailDialog.isVisible()) {
                mBetDetailDialog.dismiss();
            } else {
                clearSelection();
            }
        }
    }

    @Override
    protected void onDestroy() {
        clearSelection();//清空选中
        BetFragmentManager.clear(gameCode);//清空缓存的fragment
        EventBus.getDefault().unregister(this);//注销eventBus订阅
        super.onDestroy();
    }

    @Override
    protected boolean useEventBus() {
        return  false;
    }
}
