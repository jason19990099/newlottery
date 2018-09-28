package com.international.wtw.lottery.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.MainActivity;
import com.international.wtw.lottery.base.app.ViewHolder;
import com.international.wtw.lottery.dialog.MenuPopupWindow;
import com.international.wtw.lottery.dialog.SimpleProgressDialog;
import com.international.wtw.lottery.dialog.easypopup.EasyPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;
import com.international.wtw.lottery.fragment.LotteryInfoFragment;
import com.international.wtw.lottery.json.RightMenu;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public abstract class NewBetBaseActivity   extends AppCompatActivity implements View.OnClickListener {
    public static final String GAME_CODE = "game_code";
    protected ViewHolder viewHolder;
    protected List<RightMenu> mMenuList = new ArrayList<>();
    protected Handler handler = new Handler();
    private View mDownArrow;
    private Context mContext;
    private EasyPopup mMenuPopup;
    private RelativeLayout mRlTitleBar;
    private SimpleProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        viewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        setContentView(viewHolder.getRootView());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); //默认不弹出软键盘
        initRightMenu();
        initalSubClassViewInital();//实现子类的View操作逻辑
        initalUniverseView();//处理通用的点击事件
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LotteryInfoFragment fragment = LotteryInfoFragment.newInstance(getLotteryType());
        ft.replace(R.id.fl_lottery_info_container, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.downArrow:
//                showSwitchGamePopup();
                break;
            case R.id.img_menu:
                SetMenu();
                break;
        }
    }

    public void SetMenu() {
        if (mMenuPopup == null) {
            mMenuPopup = new MenuPopupWindow(this)
                    .createPopup();
        }
        mMenuPopup.showAtAnchorView(mRlTitleBar, VerticalGravity.BELOW, HorizontalGravity.ALIGN_RIGHT);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

//
//    private void showSwitchGamePopup() {
//        new SwitchGamePopupWindow(this, getLotteryType())
//                .setOnItemClickListener(new SwitchGamePopupWindow.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int gameCode) {
//                        switch (gameCode) {
//                            case Constants.LOTTERY_TYPE.PJ_PK_10:
//                                startActivity(new Intent(mContext, PK10Activity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.CJ_LOTTERY:
//                                startActivity(new Intent(mContext, SSCaiActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.ROME_LOTTERY:
//                                startActivity(new Intent(mContext, RomeSSCaiActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.LUCKY_FLY_LOTTERY:
//                                startActivity(new Intent(mContext, LuckyFlyActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY:
//                                startActivity(new Intent(mContext, GDHappyActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY:
//                                startActivity(new Intent(mContext, CJLuckyActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY:
//                                startActivity(new Intent(mContext, MarkSixActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY:
//                                startActivity(new Intent(mContext, Lucky28Activity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.JS_QUICK_3:
//                                startActivity(new Intent(mContext, Quick3Activity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.VENICE_SPEEDBOAT:
//                                startActivity(new Intent(mContext, VeniceActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.SPEED_CAR:
//                                startActivity(new Intent(mContext, SpeedCarActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.SPEED_SSC:
//                                startActivity(new Intent(mContext, SpeedSSCActivity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.HORSE_88:
//                                startActivity(new Intent(mContext, Horse88Activity.class));
//                                break;
//                            case Constants.LOTTERY_TYPE.SPEED_MARK_SIX:
//                                startActivity(new Intent(mContext, SpeedMarkSixActivity.class));
//                                break;
//                        }
//                        finish();
//                    }
//                })
//                .createPopup()
//                .showAtAnchorView(mDownArrow, VerticalGravity.BELOW, HorizontalGravity.CENTER);
//    }


    /**
     * 处理通用的点击事件
     */
    private void initalUniverseView() {
        mRlTitleBar = viewHolder.get(R.id.rl_title_bar);

        View backView = viewHolder.get(R.id.iv_back);
        backView.setOnClickListener(this);

        mDownArrow = viewHolder.get(R.id.downArrow);
        mDownArrow.setOnClickListener(this);

        TextView lotteryTitle = viewHolder.get(R.id.textView_lotteryTypeName);
        lotteryTitle.setText(getLotteryTypeName());

        ImageView img_menu = viewHolder.get(R.id.img_menu);
        img_menu.setOnClickListener(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            Intent intent = new Intent(NewBetBaseActivity.this, MainActivity.class);
            intent.putExtra("position", LotteryId.TYPE_ONE);
            startActivity(intent);
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 获取子类的彩种信息
     */
    protected abstract String getLotteryTypeName();

    /**
     * 获取投注的Id
     *
     * @return
     */
    protected abstract int getLayoutId();


    /**
     * 获取彩票彩种
     */
    public abstract String getLotteryType();

    /**
     * 实现子类的View操作逻辑
     */
    protected abstract void initalSubClassViewInital();


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    private void initRightMenu() {
        mMenuList.add(new RightMenu(R.mipmap.history, getResources().getString(R.string.history)));
        mMenuList.add(new RightMenu(R.mipmap.history, getResources().getString(R.string.rule)));
        mMenuList.add(new RightMenu(R.mipmap.history, getResources().getString(R.string.trend)));
    }

    public void showProgressDialog(@NonNull String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) {
                    mProgressDialog = new SimpleProgressDialog(NewBetBaseActivity.this, message);
                }
                mProgressDialog.setText(message);
                if (!mProgressDialog.isShowing() && !NewBetBaseActivity.this.isFinishing()) {
                    mProgressDialog.show();
                }
            }
        });
    }

    public void dismissProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

}
