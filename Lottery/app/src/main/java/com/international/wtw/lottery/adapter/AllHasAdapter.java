package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.HasCloseBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/28.
 */

public class AllHasAdapter extends BaseAdapter {

    private Context context;
    private List<HasCloseBean> hasClose;

    public AllHasAdapter(Context context, List<HasCloseBean> hasClose) {
        this.context = context;
        this.hasClose = hasClose;
    }

    @Override
    public int getCount() {
        return hasClose.size();
    }

    @Override
    public Object getItem(int position) {
        return hasClose.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_bet_has_item, null);
            viewHolder.tv_rq = (TextView) view.findViewById(R.id.tv_rq);
            viewHolder.tv_tzds = (TextView) view.findViewById(R.id.tv_tzds);
            viewHolder.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewHolder.tv_ksje = (TextView) view.findViewById(R.id.tv_ksje);
            viewHolder.tv_yjts = (TextView) view.findViewById(R.id.tv_yjts);
            viewHolder.tv_zj = (TextView) view.findViewById(R.id.tv_zj);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_rq.setText(hasClose.get(position).getDateTime());
        viewHolder.tv_tzds.setText(hasClose.get(position).getAllNum());
        viewHolder.tv_xzje.setText(hasClose.get(position).getAllTotal());
        viewHolder.tv_ksje.setText(hasClose.get(position).getAllWinMoney());
        viewHolder.tv_yjts.setText(hasClose.get(position).getAllCut());

        return view;
    }

    class ViewHolder {
        TextView tv_rq, tv_tzds, tv_xzje, tv_ksje, tv_yjts, tv_zj;
    }
}
