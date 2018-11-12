package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.TodaySettledOrdersActivity;
import com.international.wtw.lottery.base.LotteryId;
import com.international.wtw.lottery.newJason.BetDetailModel;
import com.international.wtw.lottery.newJason.SettledOrdersModel;

import java.util.List;

public class BetdetailSettledAdapter extends BaseAdapter {
    private Context context;
    private List<SettledOrdersModel.DataBeanX.DataBean> data ;
    public BetdetailSettledAdapter(Context context, List<SettledOrdersModel.DataBeanX.DataBean> data){
        this.context=context;
        this.data=data;
    }
    @Override
    public int getCount() {
        return null==data?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        viewHolder viewHolder;
        if (view == null) {
            viewHolder = new viewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.adapter_settleddetail, null);
            viewHolder.tv_lotterytype= (TextView) view.findViewById(R.id.tv_lotterytype);
            viewHolder.tv_betnum= (TextView) view.findViewById(R.id.tv_betnum);
            viewHolder.tv_bet_money= (TextView) view.findViewById(R.id.tv_bet_money);
            viewHolder.tv_win_money= (TextView) view.findViewById(R.id.tv_win_money);
            viewHolder.ll_all= (LinearLayout) view.findViewById(R.id.ll_all);
            view.setTag(viewHolder);
        } else {
            viewHolder =(viewHolder) view.getTag();
        }
        viewHolder.tv_lotterytype.setText(data.get(position).getGameName()+"\n"+data.get(position).getExpectNo());
        viewHolder.tv_betnum.setText(String.valueOf(data.get(position).getPlayGroupName()+"\n"+data.get(position).getPlayName()));
        viewHolder.tv_bet_money.setText(String.valueOf(data.get(position).getAmount())+"元");
        viewHolder.tv_win_money.setText(String.valueOf(data.get(position).getWinLoseAmount())+"元");
        return view;
    }

    class  viewHolder{
        TextView tv_lotterytype,tv_betnum,tv_bet_money,tv_win_money;
        LinearLayout  ll_all;
    }
}
