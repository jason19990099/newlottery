package com.international.wtw.lottery.activity.lottery;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetDataAdapter;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.base.app.BetBaseActivity;
import com.international.wtw.lottery.dialog.BetDetailDialog;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.Quick3Odds;
import com.international.wtw.lottery.utils.EditTextTools;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.MoneyInfoManager;
import com.international.wtw.lottery.utils.SharePreferencesUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quick3Activity extends BetBaseActivity {

    private RecyclerView mRecyclerView;
    private EditText et_betting_amount;
    private TextView tv_selectnum;
    private ImageView iv_isselect;
    private Button btn_bet;
    private View mErrorView;
    private TextView mTvError;

    private BetDataAdapter mDataAdapter;
    private boolean isClosed = true;
    //下注选项(多选)
    private List<MultiBetItem> selectBeans = new ArrayList<>();
    private BaseNiceDialog mBetDetailDialog;

    @Override
    protected String getLotteryTypeName() {
        return getString(R.string.LotteryTypeNameJSQuick3);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quick3;
    }

    @Override
    public String getLotteryType() {
        return Constants.LOTTERY_TYPE.JS_QUICK_3+"";
    }


    @Override
    protected void initalSubClassViewInital() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        et_betting_amount = (EditText) findViewById(R.id.et_betting_amount);
        tv_selectnum = (TextView) findViewById(R.id.tv_selectnum);
        iv_isselect = (ImageView) findViewById(R.id.iv_isselect);
        btn_bet = (Button) findViewById(R.id.btn_bet);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, LotteryUtil.TOTAL_SPAN_SIZE));
//        mDataAdapter = new BetDataAdapter(getLotteryType(), null);
        //设置每个item的宽度
        mDataAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mDataAdapter.getData().get(position).getSpanSize();
            }
        });
        mDataAdapter.setClosed(isClosed);
        mRecyclerView.setAdapter(mDataAdapter);
        mDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isClosed) {
                    return;
                }
                MultiBetItem item = (MultiBetItem) adapter.getItem(position);
                if (item == null) {
                    return;
                }
                if (item.getItemType() != MultiBetItem.TITLE) {
                    item.setIsSelected(!item.getIsSelected());
                    //此处有一小坑,直接调用adapter.notifyItemChanged(position);刷新数据会出现视觉延迟
                    adapter.notifyItemChanged(position, 0);
                    if (item.getIsSelected()) {
                        selectBeans.add(item);
                    } else {
                        if (selectBeans.contains(item)) {
                            selectBeans.remove(item);
                        }
                    }
                    refreshSelect();
                }
            }
        });

        initListener();
        initEmptyView();

        showProgressDialog(getString(R.string.loading));
        requestOdds();
    }

    private void initEmptyView() {
        mErrorView = LayoutInflater.from(this).inflate(R.layout.layout_empty_view, null);
        mTvError = (TextView) mErrorView.findViewById(R.id.tv_error);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(getString(R.string.loading));
                mErrorView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        requestOdds();
                    }
                }, 1000);
            }
        });
    }


    @Subscribe
    public void onEvent(BetClosedEvent event) {
//        if (getLotteryType() == event.gameCode) {
//            dismissProgressDialog();
//            isClosed = event.isClosed;
//            mDataAdapter.setClosed(isClosed);
//            if (isClosed && mBetDetailDialog != null && mBetDetailDialog.isVisible()) {
//                mBetDetailDialog.dismiss();
//            }
//
//            //清除下注信息
//            clearSelection();
//        }
    }

    private void initListener() {
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
                commitBetting();
            }
        });

        //清除选项
        findViewById(R.id.ll_bottom_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
            }
        });
    }

    private void refreshSelect() {
        if (selectBeans.isEmpty()) {
            btn_bet.setBackgroundColor(getResources().getColor(R.color.gray_666666));
            tv_selectnum.setText("0");
            iv_isselect.setImageResource(R.mipmap.wallet_notselect);
        } else {
            btn_bet.setBackgroundColor(getResources().getColor(R.color.textcolortrue));
            tv_selectnum.setText(String.valueOf(selectBeans.size()));
            iv_isselect.setImageResource(R.mipmap.wallet_select);
        }
    }

    public void clearSelection() {
        if (!selectBeans.isEmpty()) {
            selectBeans.clear();
            tv_selectnum.setText("0");
            iv_isselect.setImageResource(R.mipmap.wallet_notselect);
        }
        et_betting_amount.setText("");
        mDataAdapter.clearSelect();
    }

    private void requestOdds() {
//        HttpRequest.getInstance().requestAllOdds(this, getLotteryType(), new HttpCallback<Quick3Odds>() {
//
//            @Override
//            public void onSuccess(Quick3Odds data) {
//                dismissProgressDialog();
////                List<MultiBetItem> betData = LotteryUtil.get().getBetData(getLotteryType(), data);
////                mDataAdapter.setNewData(betData);
//            }
//
//            @Override
//            public void onFailure(String msgCode, String errorMsg) {
//                dismissProgressDialog();
//                mDataAdapter.setNewData(null);
//                mTvError.setText(errorMsg);
//                mDataAdapter.setEmptyView(mErrorView);
//            }
//        });
    }

    private void commitBetting() {
        //检查用户的下注选项是否符合规则(是否为空, 是否小于最小选择数或大于最大选中数)
        if (selectBeans.isEmpty()) {
            ToastDialog.error(getString(R.string.select_betting_item)).show(getSupportFragmentManager());
            return;
        }
        //用户输入的下注金额
        String money = et_betting_amount.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastDialog.error(getString(R.string.typein_betmoney)).show(getSupportFragmentManager());
            return;
        }
        //对下注选项排序
        Collections.sort(selectBeans);
        //当前下注期数
        String round = SharePreferencesUtil.getString(getApplicationContext(), LotteryId.ROUND, "0");
        //打开下注弹窗, 显示下注详情, 点击确认后请求下注接口(下注接口代码在BetDetailDialog中实现)
        //显示下注结果和提示信息
        //刷新余额
//        mBetDetailDialog = BetDetailDialog.init(getLotteryType(), 0, 0, Integer.valueOf(money), round,
//                null, selectBeans, new BetDetailDialog.OnBetResultListener() {
//                    @Override
//                    public void onBetResult(boolean isSuccess, String msg) {
//                        //显示下注结果和提示信息
//                        ToastDialog.success(msg).show(getSupportFragmentManager());
//                        //刷新余额
//                        MoneyInfoManager.get().requestMoneyInfo();
//                    }
//                })
//                .setMargin(40)
//                .showDialog(getSupportFragmentManager());
    }
}
