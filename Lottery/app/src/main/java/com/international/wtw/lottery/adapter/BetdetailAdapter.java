package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.BetDetailModel;
import java.util.List;

public class BetdetailAdapter extends BaseAdapter {
    private Context context;
    private List<BetDetailModel.DataBean> data;

    public BetdetailAdapter(Context context,List<BetDetailModel.DataBean> data){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_betdetail, null);
            viewHolder.tv_lotterytype= (TextView) view.findViewById(R.id.tv_lotterytype);
            viewHolder.tv_betnum= (TextView) view.findViewById(R.id.tv_betnum);
            viewHolder.tv_bet_money= (TextView) view.findViewById(R.id.tv_bet_money);
            viewHolder.tv_win_money= (TextView) view.findViewById(R.id.tv_win_money);
            view.setTag(viewHolder);
        } else {
            viewHolder =(viewHolder) view.getTag();
        }
        viewHolder.tv_lotterytype.setText(data.get(position).getGameName());
        viewHolder.tv_betnum.setText(String.valueOf(data.get(position).getCount())+"注");
        viewHolder.tv_win_money.setText(String.valueOf(data.get(position).getWinLoseAmount())+"元");
        viewHolder.tv_bet_money.setText(String.valueOf(data.get(position).getAmount())+"元");
        return view;
    }

    class  viewHolder{
        TextView tv_lotterytype,tv_betnum,tv_bet_money,tv_win_money;
    }
}
