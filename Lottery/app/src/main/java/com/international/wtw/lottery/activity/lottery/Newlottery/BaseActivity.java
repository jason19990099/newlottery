package com.international.wtw.lottery.activity.lottery.Newlottery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.dialog.RecyclerViewDialog;
import com.international.wtw.lottery.dialog.RecyclerViewDialog2;
import com.international.wtw.lottery.event.BetGo;
import com.international.wtw.lottery.event.BetSelectData;
import com.international.wtw.lottery.fragment.LotteryInfoFragment;
import com.international.wtw.lottery.newJason.BetData;
import com.international.wtw.lottery.newJason.Login;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.utils.LogUtil;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;


/**
 * 新的彩票的父类
 */
public abstract class BaseActivity extends FragmentActivity {
    @BindView(R.id.btn_bet)
    Button btnBet;
    private List<BetData> betdatalist; //下注数据的容器
    private RecyclerViewDialog2 mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认不弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //初始化开盘封盘
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        LotteryInfoFragment fragment = LotteryInfoFragment.newInstance(getLotteryType());
        ft.replace(R.id.fl_lottery_info_container, fragment).commit();

        betdatalist = new ArrayList<>();
        betdatalist.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /**
     * 获取彩票彩种
     */
    public abstract String getLotteryType();


    /**
     * 此方法收集下注数据的
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BetSelectData event) {
        if (event.isSelect()) {
            betdatalist.add(event.getBetData());
        } else {
            Iterator<BetData> iterator = betdatalist.iterator();
            while (iterator.hasNext()) {
                BetData betData = iterator.next();
                if (betData.getPlayRateCode().equals(event.getBetData().getPlayRateCode())) {
                    iterator.remove();
                }
            }
            LogUtil.e("=========betdatalist========="+betdatalist.size());
        }


        TextView tv_selectNum = (TextView) findViewById(R.id.tv_betsize);
        String text = "已经选中<font color='#FF0000'>" + betdatalist.size() + "</font>注";
        tv_selectNum.setText(Html.fromHtml(text));
    }

    /**
     * 下注指令
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BetGo event) {
        int size = betdatalist.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                betdatalist.get(i).setAmount(event.getBetMoney());
            }

            mDialog = new RecyclerViewDialog2(this, betdatalist, event.getBetMoney(), new RecyclerViewDialog2.SureCallBack() {
                @Override
                public void onSure() {
                    //请求接口进行下注
                    getBet(event.getExpectNo(), betdatalist);
                }

                @Override
                public void onCancel() {

                }
            });
            mDialog.show();

        } else {
            Toast.makeText(this, "请选择投注项", Toast.LENGTH_LONG).show();
        }

    }


    private void getBet(String expectNo, Object betjason) {

        String token = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.TOKEN, "");
        HttpRequest.getInstance().saveOrders(BaseActivity.this, token, getLotteryType(), expectNo, betjason, new HttpCallback<Login>() {
            @Override
            public void onSuccess(Login data) throws Exception {
                Toast.makeText(BaseActivity.this, "投注成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String msgCode, String errorMsg) {
                Toast.makeText(BaseActivity.this, "投注失败", Toast.LENGTH_LONG).show();
            }
        });
    }


}
