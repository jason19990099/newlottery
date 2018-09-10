package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.UnSettlementBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/28.
 */

public class AllOutStandAdapter extends BaseAdapter {

    private Context context;
    private List<UnSettlementBean> unSettlement;

    public AllOutStandAdapter(Context context, List<UnSettlementBean> unSettlement) {
        this.context = context;
        this.unSettlement = unSettlement;
    }

    @Override
    public int getCount() {
        return unSettlement.size();
    }

    @Override
    public Object getItem(int position) {
        return unSettlement.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.lv_bet_outstand_item, null);
            viewHolder.tv_rq = (TextView) view.findViewById(R.id.tv_rq);
            viewHolder.tv_tzds = (TextView) view.findViewById(R.id.tv_tzds);
            viewHolder.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewHolder.tv_ksje = (TextView) view.findViewById(R.id.tv_ksje);
            viewHolder.tv_yjts = (TextView) view.findViewById(R.id.tv_yjts);
            viewHolder.tv_yj = (TextView) view.findViewById(R.id.tv_yj);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_rq.setText(unSettlement.get(position).getDateTime());
        viewHolder.tv_tzds.setText(unSettlement.get(position).getAllNum());
        viewHolder.tv_xzje.setText(unSettlement.get(position).getAllMoney());
        viewHolder.tv_ksje.setText(unSettlement.get(position).getAllWinMoney());
        viewHolder.tv_yjts.setText(unSettlement.get(position).getAllCut());

        return view;
    }

    class ViewHolder {
        TextView tv_rq, tv_tzds, tv_xzje, tv_ksje, tv_yjts, tv_yj;
    }
}
