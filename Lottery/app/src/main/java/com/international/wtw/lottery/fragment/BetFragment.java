package com.international.wtw.lottery.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.BetActivity;
import com.international.wtw.lottery.adapter.BetDataAdapter;
import com.international.wtw.lottery.adapter.BetTypeRecyclerAdapter;
import com.international.wtw.lottery.api.ArrayCallback;
import com.international.wtw.lottery.api.HttpCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.Constants;
import com.international.wtw.lottery.base.app.NewBaseFragment;
import com.international.wtw.lottery.dialog.ToastDialog;
import com.international.wtw.lottery.event.BetClosedEvent;
import com.international.wtw.lottery.event.ResetSelectBetDataEvent;
import com.international.wtw.lottery.event.SelectStateChangedEvent;
import com.international.wtw.lottery.json.BetTypeItem;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.json.OddsModel;
import com.international.wtw.lottery.json.Quick3Odds;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.MemoryCacheManager;
import com.international.wtw.lottery.utils.SizeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by XiaoXin on 2017/9/19.
 * 描述：下注fragment
 */

public class BetFragment extends NewBaseFragment {

    public static final String ARG_GAME_CODE = "arg_game_code";
    public static final String ARG_POSITION = "arg_position";
    public static final String ARG_IS_CLOSED = "arg_is_closed";
    public static final String MEMORY_CACHE_KEY_ITEM = "item_odds_%d_%d";
    public static final String MEMORY_CACHE_KEY_TYPE = "type_odds_%d_%d";

    @BindView(R.id.recycler_types)
    RecyclerView mRecyclerTypes;
    @BindView(R.id.recycler_items)
    RecyclerView mRecyclerItems;
    private int mGameCode;
    private int mFragPosition;
    //选择连中(仅连码需要此参数)
    private int typeId;
    //是否已封盘
    public boolean isClosed;
    private BetTypeRecyclerAdapter mTypeAdapter;
    private BetDataAdapter mDataAdapter;
    private List<BetTypeItem> typeData = new ArrayList<>();
    private List<MultiBetItem> betData = new ArrayList<>();
    //当网络请求出错时显示
    private View mErrorView;
    private TextView mTvError;
    //一个变量来计算大单/小单/大双/小双数量  最多只能选一个
    private int selectNum;

    public static BetFragment newInstance(int gameMode, int position, boolean isClosed) {
        BetFragment fragment = new BetFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_GAME_CODE, gameMode);
        arguments.putInt(ARG_POSITION, position);
        arguments.putBoolean(ARG_IS_CLOSED, isClosed);
        fragment.setArguments(arguments);
        return fragment;
    }

    public int getSelectedId() {
        return typeId;
    }

    public String getComboCode() {
        List<BetTypeItem> data = mTypeAdapter.getData();
        if (data.isEmpty()) {
            return null;
        } else {
            return data.get(typeId).getComboCode();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bet;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        mGameCode = bundle.getInt(ARG_GAME_CODE);
        mFragPosition = bundle.getInt(ARG_POSITION);
        isClosed = bundle.getBoolean(ARG_IS_CLOSED);
        initTypeRecycler();
        initEmptyView();
    }

    private void initEmptyView() {
        mErrorView = LayoutInflater.from(mActivity).inflate(R.layout.layout_empty_view, null);
        mTvError = (TextView) mErrorView.findViewById(R.id.tv_error);
        mErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeCode = LotteryUtil.get().getTypeCode(mGameCode, mFragPosition, typeId);
                requestOdds(typeCode);
            }
        });
    }

    @Override
    protected void initData() {
        getTypeData();
        getItemData();
        refreshRecyclerLayout();
    }

    public void getTypeData() {
        if (!isCombo()) {
            //非连码的typeData, 直接从LotteryUtil拿, 为空时隐藏recyclerView
            typeData = LotteryUtil.get().getTypeData(mGameCode, mFragPosition);
            if (!typeData.isEmpty()) {
                mTypeAdapter.setNewData(typeData);
            } else {
                mRecyclerTypes.setVisibility(View.GONE);
            }
        } else {
            /*
            * 对于连码, 上面的RecyclerView显示赔率, 下面只显示数字, 同样上面的赔率也不要总是从接口中获取
            * 首先, 根据gameCode和typeCode从内存中获取数据, 如果内存中不存在赔率数据
            * 如果内存中已经存在了赔率数据(内存中仅保存了App本次启动后的数据, 可以认为是最新的数据),
            * 直接展示内存中的数据, 不再进行网络请求, 减轻服务器压力
            * */
            int typeCode = LotteryUtil.get().getTypeCode(mGameCode, mFragPosition, typeId);
            String key = String.format(Locale.CHINESE, MEMORY_CACHE_KEY_TYPE, mGameCode, typeCode);
            //noinspection unchecked
            typeData = (List<BetTypeItem>) MemoryCacheManager.getInstance().get(key);
            if (typeData == null || typeData.isEmpty()) {
                typeData = LotteryUtil.get().getTypeData(mGameCode, mFragPosition);
                //同时查询最新的数据
                requestOdds(typeCode);
            }
            mTypeAdapter.setNewData(typeData);
        }
    }

    public void getItemData() {
        int typeCode = LotteryUtil.get().getTypeCode(mGameCode, mFragPosition, typeId);
        if (isCombo()) {
            //对于连码的, 赔率显示在了上面RecyclerView, 下面只需要选择下注的数字, 无需网络请求.
            betData = LotteryUtil.get().getBetData(mGameCode, mFragPosition, typeCode, null);
        } else {
            /*
            * 首先, 根据gameCode和typeCode从内存中获取数据, 如果内存中不存在赔率数据
            * 则查询数据库中的数据显示, 并请求接口, 同步服务器与本地数据库中的数据
            * 如果内存中已经存在了赔率数据(内存中仅保存了App本次启动后的数据, 可以认为是最新的数据),
            * 直接展示内存中的数据, 不再进行网络请求, 减轻服务器压力
            * */
            String key = String.format(Locale.CHINESE, MEMORY_CACHE_KEY_ITEM, mGameCode, typeCode);
            //noinspection unchecked
            betData = (List<MultiBetItem>) MemoryCacheManager.getInstance().get(key);
            if (betData == null || betData.isEmpty()) {
                //同时查询最新的数据
                requestOdds(typeCode);
            }
        }
        if (betData != null && !betData.isEmpty()) {
            mDataAdapter.setNewData(betData);
        }
    }

    private void refreshRecyclerLayout() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mRecyclerItems.getLayoutParams();
        if (betData != null && !betData.isEmpty() && betData.get(0).getItemType() == MultiBetItem.TITLE) {
            params.setMargins(0, SizeUtils.dp2px(-5), 0, SizeUtils.dp2px(5));
        } else {
            params.setMargins(0, SizeUtils.dp2px(5), 0, SizeUtils.dp2px(5));
        }
        mRecyclerItems.setLayoutParams(params);
    }

    private void initTypeRecycler() {
        //上部下注类型RecyclerView
        mRecyclerTypes.setHasFixedSize(true);
        mRecyclerTypes.setNestedScrollingEnabled(false);
        mRecyclerTypes.setLayoutManager(new GridLayoutManager(mActivity, LotteryUtil.TOTAL_SPAN_SIZE));
        mTypeAdapter = new BetTypeRecyclerAdapter(typeData);
        mTypeAdapter.setClosed(isClosed);//设置是否封盘
        //设置每个item的宽度
        mTypeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mTypeAdapter.getData().get(position).getSpanSize();
            }
        });
        mRecyclerTypes.setAdapter(mTypeAdapter);
        mTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //切换item的选中状态
                for (int i = 0; i < typeData.size(); i++) {
                    BetTypeItem item = typeData.get(i);
                    if (i == position && !item.isSelected()) {
                        item.setSelected(true);
                        typeId = i;
                        adapter.notifyItemChanged(i, 0);
                        //如果切换了选项, 则重置用户选择.
                        ((BetActivity) mActivity).clearSelection();
                        if (item.getItemType() == BetTypeItem.TYPE_NORMAL) {
                            getItemData();
                        }
                    } else if (i != position && item.isSelected()) {
                        item.setSelected(false);
                        adapter.notifyItemChanged(i, 0);
                    }
                }
            }
        });

        //下注选项recycler
        mRecyclerItems.setHasFixedSize(true);
        mRecyclerItems.setNestedScrollingEnabled(false);
        mRecyclerItems.setLayoutManager(new GridLayoutManager(mActivity, LotteryUtil.TOTAL_SPAN_SIZE));
        mDataAdapter = new BetDataAdapter(mGameCode, betData);
        mDataAdapter.setClosed(isClosed);//设置是否封盘
        //设置每个item的宽度
        mDataAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return mDataAdapter.getData().get(position).getSpanSize();
            }
        });
        mRecyclerItems.setAdapter(mDataAdapter);
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
                switch (item.getItemType()) {
                    case MultiBetItem.CONTENT_TEXT:
                    case MultiBetItem.CONTENT_NUM:
                    case MultiBetItem.CONTENT_LIAN:
                        if (item.getIsSelected()) {
                            item.setIsSelected(false);
                            if (isSpecialFour(item)) {
                                selectNum--;
                            }
                        } else {
                            if (isSpecialFour(item) && selectNum != 0) {
                                ToastDialog.error(getString(R.string.only_select_one)).show(getFragmentManager());
                                return;
                            }
                            item.setIsSelected(true);
                            if (isSpecialFour(item)) {
                                selectNum++;
                            }
                        }
                        //此处有一小坑,直接调用adapter.notifyItemChanged(position);刷新数据会出现视觉延迟
                        adapter.notifyItemChanged(position, 0);
                        EventBus.getDefault().post(new SelectStateChangedEvent(item));
                        break;
                }
            }
        });
    }

    /**
     * item是不是 大单/大双/小单/小双 中的一个
     * 因为这四个只能同时选一个, 选择的时候要判断一下
     */
    private boolean isSpecialFour(MultiBetItem item) {
        if ((mGameCode == Constants.LOTTERY_TYPE.LUCKY_28_LOTTERY && mFragPosition == 0)//PC蛋蛋
                || (mGameCode == Constants.LOTTERY_TYPE.SPEED_MARK_SIX && mFragPosition == 0)//极速六合彩
                || (mGameCode == Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY && mFragPosition == 0)) {//香港六合彩
            if (getString(R.string.dadan).equals(item.getBetItem().getName())
                    || getString(R.string.xiaodan).equals(item.getBetItem().getName())
                    || getString(R.string.dashuang).equals(item.getBetItem().getName())
                    || getString(R.string.xiaoshuang).equals(item.getBetItem().getName())) {
                return true;
            }
        }
        return false;
    }

    private void requestOdds(int typeCode) {
        if (isCombo()) {
            mTypeAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerItems.getParent());
        } else {
            mDataAdapter.setEmptyView(R.layout.listview_loading, (ViewGroup) mRecyclerItems.getParent());
        }
        //如果是江苏快3, 调用查询所有赔率的接口
        if (mGameCode == Constants.LOTTERY_TYPE.JS_QUICK_3) {
            HttpRequest.getInstance().requestAllOdds(this, mGameCode, new HttpCallback<Quick3Odds>() {

                @Override
                public void onSuccess(Quick3Odds data) {
                    betData = LotteryUtil.get().getBetData(mGameCode, data);
                    mDataAdapter.setNewData(betData);
                    refreshRecyclerLayout();
                    //在内存中保存一份
                    String key = String.format(Locale.CHINESE, MEMORY_CACHE_KEY_ITEM, mGameCode, typeCode);
                    MemoryCacheManager.getInstance().put(key, betData);
                }

                @Override
                public void onFailure(String msgCode, String errorMsg) {
                    mDataAdapter.setNewData(null);
                    mTvError.setText(errorMsg);
                    mDataAdapter.setEmptyView(mErrorView);
                }
            });
        } else {
            //非江苏快3的查询单个玩法的接口.
            HttpRequest.getInstance().requestOdds(this, mGameCode, typeCode, new ArrayCallback<List<OddsModel>>() {

                @Override
                public void onSuccess(List<OddsModel> data) {
                    if (isCombo()) {
                        typeData = LotteryUtil.get().getTypeData(mGameCode, data.get(0).list);
                        mTypeAdapter.setNewData(typeData);
                        //在内存中保存一份
                        String key = String.format(Locale.CHINESE, MEMORY_CACHE_KEY_TYPE, mGameCode, typeCode);
                        MemoryCacheManager.getInstance().put(key, typeData);
                    } else {
                        betData = LotteryUtil.get().getBetData(mGameCode, mFragPosition, typeCode, data);
                        mDataAdapter.setNewData(betData);
                        refreshRecyclerLayout();
                        //在内存中保存一份
                        String key = String.format(Locale.CHINESE, MEMORY_CACHE_KEY_ITEM, mGameCode, typeCode);
                        MemoryCacheManager.getInstance().put(key, betData);
                    }
                }

                @Override
                public void onFailure(String msgCode, String errorMsg) {
                    mDataAdapter.setNewData(null);
                    mTvError.setText(errorMsg);
                    mDataAdapter.setEmptyView(mErrorView);
                }
            });
        }
    }

    /**
     * @return 是否是连码: (广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）)
     */
    private boolean isCombo() {
        return (mGameCode == Constants.LOTTERY_TYPE.GD_HAPPY_LOTTERY && mFragPosition == 3)
                || (mGameCode == Constants.LOTTERY_TYPE.CJ_LUCKY_LOTTERY && mFragPosition == 3)
                || (mGameCode == Constants.LOTTERY_TYPE.HK_MARK_SIX_LOTTERY && mFragPosition == 4)
                || (mGameCode == Constants.LOTTERY_TYPE.SPEED_MARK_SIX && mFragPosition == 4);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onEvent(BetClosedEvent event) {
        if (event.gameCode == mGameCode) {
            isClosed = event.isClosed;
            if (mTypeAdapter != null) {
                mTypeAdapter.setClosed(isClosed);
            }
            if (mDataAdapter != null) {
                mDataAdapter.setClosed(isClosed);
            }
        }
    }

    @Subscribe
    public void onEvent(ResetSelectBetDataEvent event) {
        selectNum = 0;
        if (mDataAdapter != null) {
            mDataAdapter.clearSelect();
        }
    }
}
