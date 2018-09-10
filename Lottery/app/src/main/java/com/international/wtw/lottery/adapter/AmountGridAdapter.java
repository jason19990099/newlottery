package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;

import java.util.List;

/**
 * Created by Abin on 2017/9/12.
 * 描述：选择金额的Adapter
 */

public class AmountGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mAmounts;

    public AmountGridAdapter(Context context, List<String> amounts) {
        mContext = context;
        mAmounts = amounts;
    }

    @Override
    public int getCount() {
        return mAmounts == null ? 0 : mAmounts.size();
    }

    @Override
    public Object getItem(int position) {
        return mAmounts == null ? null : mAmounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rechange_amount, null);
            viewHolder = new ViewHolder();
            viewHolder.tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAmount.setText(mAmounts.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tvAmount;
    }
}
