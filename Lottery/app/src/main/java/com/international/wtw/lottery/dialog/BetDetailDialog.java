package com.international.wtw.lottery.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.BetActivity;
import com.international.wtw.lottery.api.ArrayCallback;
import com.international.wtw.lottery.api.HttpRequest;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;
import com.international.wtw.lottery.json.BetResultBean;
import com.international.wtw.lottery.json.MultiBetItem;
import com.international.wtw.lottery.utils.LotteryUtil;
import com.international.wtw.lottery.utils.SizeUtils;
import com.international.wtw.lottery.widget.RecyclerViewDivider;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/24.
 * 描述：下注明细列表
 */

public class BetDetailDialog extends BaseNiceDialog implements View.OnClickListener {

    private int gameCode;
    private int fragPosition;
    private int selectedId;
    private int betMoney;
    private String round;
    private String comboCode;
    private static List<MultiBetItem> betItems;
    private static OnBetResultListener mOnBetResultListener;
    private int betType;

    public static BetDetailDialog init(int gameCode, int position, int selectedId, int betMoney,
                                       String round, String comboCode, List<MultiBetItem> itemList, OnBetResultListener listener) {
        BetDetailDialog detailDialog = new BetDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("game_code", gameCode);
        bundle.putInt("frag_position", position);
        bundle.putInt("selected_id", selectedId);
        bundle.putInt("bet_money", betMoney);
        bundle.putString("round_num", round);
        bundle.putString("combo_code", comboCode);
        betItems = itemList;
        mOnBetResultListener = listener;
        detailDialog.setArguments(bundle);
        return detailDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        gameCode = bundle.getInt("game_code");
        fragPosition = bundle.getInt("frag_position");
        selectedId = bundle.getInt("selected_id");
        betMoney = bundle.getInt("bet_money");
        round = bundle.getString("round_num");
        comboCode = bundle.getString("combo_code");

        betType = LotteryUtil.get().getBetType(gameCode, fragPosition);
    }

    @Override
    public int intLayoutId() {
        return R.layout.layout_dialog_bet_detail;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        RecyclerView recyclerView = holder.getView(R.id.recycler_view);
        holder.setOnClickListener(R.id.iv_close, this);
        holder.setOnClickListener(R.id.tv_dialog_confirm, this);
        holder.setOnClickListener(R.id.tv_dialog_cancel, this);
        BaseQuickAdapter.OnItemChildClickListener clickListener = new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete:
                        betItems.remove(position);
                        if (betItems.isEmpty()) {
                            dismiss();
                        } else {
                            refreshRecycler(recyclerView, betType, holder);
                            adapter.notifyItemRemoved(position);
                        }
                        break;
                }
            }
        };

        switch (betType) {
            /*
             * 下注方式一:适用于 北京赛车，幸运飞艇，PC蛋蛋（幸运二八），重庆时时彩，广东快乐十分（除连码外），
             * 重庆幸运农场（除连码外），香港六合彩（特码，正码，正码特，正码1-6，半波，一肖、尾数，特码生肖）
             */
            case LotteryUtil.BET_TYPE_ONE:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                        RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
                BaseQuickAdapter<MultiBetItem, BaseViewHolder> adapter
                        = new BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data, betItems) {
                    @Override
                    protected void convert(BaseViewHolder helper, MultiBetItem item) {
                        helper.setText(R.id.tv_bet_type, String.format("%s %s (%s)",
                                item.getTypeName(), item.getBetItem().getName(), item.getBetItem().getOdds()));
                        helper.setText(R.id.tv_bet_money, String.valueOf(betMoney));
                        helper.addOnClickListener(R.id.iv_delete);
                    }
                };
                adapter.setOnItemChildClickListener(clickListener);
                recyclerView.setAdapter(adapter);
                break;
            /*
             * 下注方式二:适用于 广东快乐十分（连码），重庆幸运农场（连码），香港六合彩（连码）
             */
            case LotteryUtil.BET_TYPE_TWO:
            /*
            * 下注方式三:适用于（过关 ，合肖，生肖连，尾数连，全不中）
            */
            case LotteryUtil.BET_TYPE_THREE:
                holder.getView(R.id.tv_bet_name).setVisibility(View.VISIBLE);
                holder.setText(R.id.tv_bet_name, LotteryUtil.get()
                        .getTypeData(gameCode, fragPosition).get(selectedId).getTypeName());
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(),
                        RecyclerViewDivider.HORIZONTAL_DIVIDER, R.drawable.shape_divider_line));
                BaseQuickAdapter<MultiBetItem, BaseViewHolder> adapter2
                        = new BaseQuickAdapter<MultiBetItem, BaseViewHolder>(R.layout.item_bet_data2, betItems) {

                    @Override
                    protected void convert(BaseViewHolder helper, MultiBetItem item) {
                        if (betType == LotteryUtil.BET_TYPE_TWO) {
                            helper.setText(R.id.tv_bet_num, String.valueOf(item.getNumber()));
                        } else {
                            helper.setText(R.id.tv_bet_num, item.getBetItem().getName());
                        }
                    }
                };
                adapter2.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                        if (position == betItems.size() - 1 && betItems.size() % 2 == 1) {
                            return 2;
                        }
                        return 1;
                    }
                });
                adapter2.setOnItemChildClickListener(clickListener);
                recyclerView.setAdapter(adapter2);
                break;
        }

        refreshRecycler(recyclerView, betType, holder);
    }

    private void refreshRecycler(RecyclerView recyclerView, int betType, ViewHolder holder) {

        int total = LotteryUtil.get().getTotalBetNum(gameCode, fragPosition, selectedId, betItems.size());
        holder.setText(R.id.tv_total_count, String.valueOf(total));
        holder.setText(R.id.tv_total_money, String.valueOf(betMoney * total));

        int size = betType == LotteryUtil.BET_TYPE_ONE ? 5 : 10;
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        if (betItems.size() > size) {
            params.height = SizeUtils.dp2px(150);
        } else {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        recyclerView.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_confirm:
                requestBet();
                break;
        }
    }


    private void requestBet() {
        int typeCode = LotteryUtil.get().getTypeCode(gameCode, fragPosition, selectedId);
        switch (betType) {
            case LotteryUtil.BET_TYPE_ONE://下注方式一
                HttpRequest.getInstance().requestBet(this, gameCode, typeCode, round,
                        betItems, betMoney, new ArrayCallback<List<BetResultBean>>() {
                            @Override
                            public void onSuccess(List<BetResultBean> data) {
                                dismiss();
                                if (mOnBetResultListener != null) {
                                    mOnBetResultListener.onBetResult(true, BaseApplication
                                            .getAppContext().getString(R.string.congratulations_on_your_betting_success));
                                }
                            }

                            @Override
                            public void onFailure(String msgCode, String errorMsg) {
                                dismiss();
                                if (mOnBetResultListener != null) {
                                    mOnBetResultListener.onBetResult(false, errorMsg);
                                }
                            }
                        });
                break;
            case LotteryUtil.BET_TYPE_TWO://下注方式二
                StringBuilder sb = new StringBuilder();
                for (MultiBetItem item : betItems) {
                    String text = String.valueOf(item.getNumber());
                    sb.append(text).append(",");
                }
                if (comboCode.startsWith("ip_")) {
                    comboCode = comboCode.replace("ip_", "");
                }
                String number = sb.substring(0, sb.length() - 1);
                request(typeCode, round, comboCode, number, betMoney);
                break;
            case LotteryUtil.BET_TYPE_THREE://下注方式三
                StringBuilder sb2 = new StringBuilder();
                for (MultiBetItem item : betItems) {
                    String key = item.getBetItem().getKey();
                    sb2.append(key.substring(3)).append(",");
                }
                String number2 = sb2.substring(0, sb2.length() - 1);
                request(typeCode, round, null, number2, betMoney);
                break;
        }
    }

    private void request(int typeCode, String round, String comboCode, String number, int betMoney) {
        BetActivity activity = (BetActivity) getActivity();
        activity.showProgressDialog(getString(R.string.loading));
        HttpRequest.getInstance().requestBet(this, gameCode, typeCode, round,
                comboCode, number, String.valueOf(betMoney), new ArrayCallback<List<BetResultBean>>() {
                    @Override
                    public void onSuccess(List<BetResultBean> data) {
                        activity.dismissProgressDialog();
                        dismiss();
                        if (mOnBetResultListener != null) {
                            mOnBetResultListener.onBetResult(true, BaseApplication
                                    .getAppContext().getString(R.string.congratulations_on_your_betting_success));
                        }
                    }

                    @Override
                    public void onFailure(String msgCode, String errorMsg) {
                        activity.dismissProgressDialog();
                        dismiss();
                        if (mOnBetResultListener != null) {
                            mOnBetResultListener.onBetResult(false, errorMsg);
                        }
                    }
                });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //弹窗消失时清空之前的选择
        FragmentActivity activity = getActivity();
        if (activity instanceof BetActivity) {
            BetActivity betActivity = (BetActivity) activity;
            betActivity.clearSelection();
        }
    }

    public interface OnBetResultListener {
        //void onRequest();
        void onBetResult(boolean isSuccess, String msg);
    }
}
