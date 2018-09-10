package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.BettingHistoryBean;

import java.util.List;

/**
 * Created by Steven on 2017/6/21.  下注历史listview
 */

public class AccountHistoryAdpater extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BettingHistoryBean.TwoWeekHistoryBean> datas;
    private Context context;

    public AccountHistoryAdpater(Context context, List<BettingHistoryBean.TwoWeekHistoryBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        mInflater = LayoutInflater.from(context);//写在这里结局了动画还没加载完点击其他地方导致的bug？等待填充数据的时间验证
        if (view == null) {
            view = mInflater.inflate(R.layout.account_lv_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_bet_time = (TextView) view.findViewById(R.id.tv_bet_time);
            viewHolder.tv_zhudanshu = (TextView) view.findViewById(R.id.tv_zhudanshu);
            viewHolder.tv_betring_money = (TextView) view.findViewById(R.id.tv_betring_money);
            viewHolder.tv_win_or_lose = (TextView) view.findViewById(R.id.tv_win_or_lose);
            viewHolder.tv_tuishui = (TextView) view.findViewById(R.id.tv_tuishui);
            viewHolder.tv_zongji = (TextView) view.findViewById(R.id.tv_zongji);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_bet_time.setText(datas.get(position).getBetTime());
        viewHolder.tv_zhudanshu.setText(datas.get(position).getAllNum());
        viewHolder.tv_betring_money.setText(datas.get(position).getAllMoney());
        viewHolder.tv_win_or_lose.setText(datas.get(position).getAllWin());
        viewHolder.tv_tuishui.setText(datas.get(position).getAllCut());
        viewHolder.tv_zongji.setText(datas.get(position).getAllTrueWin());
        return view;
    }

    class ViewHolder {
        TextView tv_bet_time, tv_zhudanshu, tv_betring_money, tv_win_or_lose, tv_tuishui, tv_zongji;
    }
}
