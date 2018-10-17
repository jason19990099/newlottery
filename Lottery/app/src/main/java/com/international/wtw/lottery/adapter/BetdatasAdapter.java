package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.BetrecordBydateModel;

import java.util.List;

public class BetdatasAdapter extends BaseAdapter {
    private Context context;
    private List<BetrecordBydateModel.DataBean> data;

    public BetdatasAdapter(Context context,List<BetrecordBydateModel.DataBean> data){
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
            view = LayoutInflater.from(context).inflate(R.layout.adapter_betdata, null);
            viewHolder.tv_data= (TextView) view.findViewById(R.id.tv_data);
            viewHolder.tv_betnum= (TextView) view.findViewById(R.id.tv_betnum);
            viewHolder.tv_bet_money= (TextView) view.findViewById(R.id.tv_bet_money);
            viewHolder.tv_win_money= (TextView) view.findViewById(R.id.tv_win_money);
            viewHolder.ll_all= (LinearLayout) view.findViewById(R.id.ll_all);
            view.setTag(viewHolder);
        } else {
            viewHolder = (viewHolder) view.getTag();
        }

        viewHolder.tv_data.setText(data.get(position).getBetDate());
        viewHolder.tv_betnum.setText(String.valueOf(data.get(position).getCount())+"注");
        viewHolder.tv_win_money.setText(String.valueOf(data.get(position).getWinLoseAmount())+"元");
        viewHolder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    class  viewHolder{
           TextView tv_data,tv_betnum,tv_bet_money,tv_win_money;
           LinearLayout ll_all;

    }
}
